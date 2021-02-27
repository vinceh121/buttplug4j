package org.metafetish.buttplug.client;

import java.io.IOException;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicLong;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.metafetish.buttplug.core.ButtplugConsts;
import org.metafetish.buttplug.core.ButtplugDeviceMessage;
import org.metafetish.buttplug.core.ButtplugJsonMessageParser;
import org.metafetish.buttplug.core.ButtplugMessage;
import org.metafetish.buttplug.core.Messages.DeviceAdded;
import org.metafetish.buttplug.core.Messages.DeviceList;
import org.metafetish.buttplug.core.Messages.DeviceMessageInfo;
import org.metafetish.buttplug.core.Messages.DeviceRemoved;
import org.metafetish.buttplug.core.Messages.Error;
import org.metafetish.buttplug.core.Messages.Log;
import org.metafetish.buttplug.core.Messages.Ok;
import org.metafetish.buttplug.core.Messages.Ping;
import org.metafetish.buttplug.core.Messages.RequestDeviceList;
import org.metafetish.buttplug.core.Messages.RequestLog;
import org.metafetish.buttplug.core.Messages.RequestServerInfo;
import org.metafetish.buttplug.core.Messages.ScanningFinished;
import org.metafetish.buttplug.core.Messages.ServerInfo;
import org.metafetish.buttplug.core.Messages.StartScanning;
import org.metafetish.buttplug.core.Messages.StopAllDevices;
import org.metafetish.buttplug.core.Messages.StopScanning;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SettableListenableFuture;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketExtension;
import com.neovisionaries.ws.client.WebSocketFactory;

public class ButtplugWSClient extends WebSocketAdapter {

	public IDeviceEvent deviceAdded;

	public IDeviceEvent deviceRemoved;
	public IScanningEvent scanningFinished;
	public IErrorEvent erorReceived;
	public ILogEvent logReceived;

	private WebSocket websocket;
	private final ButtplugJsonMessageParser _parser;
	private final Object sendLock = new Object();
	private final String _clientName;
	private int _messageSchemaVersion;
	private final ConcurrentHashMap<Long, SettableListenableFuture<ButtplugMessage>> _waitingMsgs
			= new ConcurrentHashMap<>();
	private final ConcurrentHashMap<Long, ButtplugClientDevice> _devices = new ConcurrentHashMap<>();

	private Timer _pingTimer;
	private final AtomicLong msgId = new AtomicLong(1);

	public ButtplugWSClient(final String aClientName) {
		this._clientName = aClientName;
		this._parser = new ButtplugJsonMessageParser();
	}

	public long getNextMsgId() {
		return this.msgId.getAndIncrement();
	}

	public void Connect(final URI url) throws Exception {
		this.Connect(url, false);
	}

	public void Connect(final URI url, final boolean trustAll) throws Exception {

		this._waitingMsgs.clear();
		this._devices.clear();
		this.msgId.set(1);

		this.websocket = this.getWebSocket(url, trustAll);

		final ButtplugMessage res
				= this.sendMessage(new RequestServerInfo(this._clientName, this.getNextMsgId())).get();
		if (res instanceof ServerInfo) {
			if (((ServerInfo) res).maxPingTime > 0) {
				this._pingTimer = new Timer("pingTimer", true);
				this._pingTimer.scheduleAtFixedRate(new TimerTask() {
					@Override
					public void run() {
						try {
							ButtplugWSClient.this.onPingTimer();
						} catch (final Exception e) {
							e.printStackTrace();
						}
					}
				}, 0, Math.round((double) ((ServerInfo) res).maxPingTime / 2));
			}

		} else if (res instanceof org.metafetish.buttplug.core.Messages.Error) {
			throw new Exception(((org.metafetish.buttplug.core.Messages.Error) res).errorMessage);
		} else {
			throw new Exception("Unexpected message returned: " + res.getClass().getName());
		}
	}

	/*
	 * public void onClose(int statusCode, String reason) {
	 * this.session = null;
	 * }
	 * 
	 * //
	 * public void onConnect(Session session) {
	 * this.session = session;
	 * }
	 */
	public void Disconnect() {
		if (this._pingTimer != null) {
			this._pingTimer.cancel();
			this._pingTimer = null;
		}

		if (this.websocket != null) {
			this.websocket.disconnect();
			this.websocket = null;
		}

		int max = 3;
		while (max-- > 0 && this._waitingMsgs.size() != 0) {
			for (final long msgId : this._waitingMsgs.keySet()) {
				final SettableListenableFuture<ButtplugMessage> val = this._waitingMsgs.remove(msgId);
				if (val != null) {
					val.set(new Error("Connection closed!", Error.ErrorClass.ERROR_UNKNOWN,
							ButtplugConsts.SystemMsgId));
				}
			}
		}
		this.msgId.set(1);
	}

	@Override
	public void onTextMessage(final WebSocket socket, final String buf) {
		try {
			final List<ButtplugMessage> msgs = this._parser.parseJson(buf);

			for (final ButtplugMessage msg : msgs) {
				if (msg.id > 0) {
					final SettableListenableFuture<ButtplugMessage> val = this._waitingMsgs.remove(msg.id);
					if (val != null) {
						val.set(msg);
						continue;
					}
				}

				if (msg instanceof Log) {
					if (this.logReceived != null) {
						this.logReceived.logReceived((Log) msg);
					}
				} else if (msg instanceof DeviceAdded) {
					final ButtplugClientDevice device = new ButtplugClientDevice((DeviceAdded) msg);
					this._devices.put(((DeviceAdded) msg).deviceIndex, device);
					if (this.deviceAdded != null) {
						this.deviceAdded.deviceAdded(device);
					}
				} else if (msg instanceof DeviceRemoved) {
					if ((this._devices.remove(((DeviceRemoved) msg).deviceIndex) != null)
							&& (this.deviceRemoved != null)) {
						this.deviceRemoved.deviceRemoved(((DeviceRemoved) msg).deviceIndex);
					}
				} else if (msg instanceof ScanningFinished) {
					if (this.scanningFinished != null) {
						this.scanningFinished.scanningFinished();
					}
				} else if ((msg instanceof org.metafetish.buttplug.core.Messages.Error)
						&& (this.erorReceived != null)) {
					this.erorReceived.errorReceived((org.metafetish.buttplug.core.Messages.Error) msg);
				}
			}
		} catch (final IOException e) {
			if (this.erorReceived != null) {
				this.erorReceived.errorReceived(new org.metafetish.buttplug.core.Messages.Error(e.getMessage(),
						Error.ErrorClass.ERROR_UNKNOWN, ButtplugConsts.SystemMsgId));
			} else {
				e.printStackTrace();
			}
		}
	}

	private void onPingTimer() throws Exception {
		try {
			final ButtplugMessage msg = this.sendMessage(new Ping(this.msgId.incrementAndGet())).get();
			if (msg instanceof org.metafetish.buttplug.core.Messages.Error) {
				throw new Exception(((org.metafetish.buttplug.core.Messages.Error) msg).errorMessage);
			}
		} catch (final Throwable e) {
			if (this.websocket != null) {
				this.websocket.disconnect();
				this.websocket = null;
			}
			throw e;
		}
	}

	public void requestDeviceList() throws Exception {
		final ButtplugMessage res = this.sendMessage(new RequestDeviceList(this.msgId.incrementAndGet())).get();
		if (!(res instanceof DeviceList) || ((DeviceList) res).devices == null) {
			if (res instanceof org.metafetish.buttplug.core.Messages.Error) {
				throw new Exception(((org.metafetish.buttplug.core.Messages.Error) res).errorMessage);
			}
			return;
		}

		for (final DeviceMessageInfo d : ((DeviceList) res).devices) {
			if (!this._devices.containsKey(d.deviceIndex)) {
				final ButtplugClientDevice device = new ButtplugClientDevice(d);
				if ((this._devices.put(d.deviceIndex, device) == null) && (this.deviceAdded != null)) {
					this.deviceAdded.deviceAdded(device);
				}
			}
		}
	}

	public List<ButtplugClientDevice> getDevices() {
		final List<ButtplugClientDevice> devices = new ArrayList<>(this._devices.values());
		return devices;
	}

	public boolean startScanning() throws ExecutionException, InterruptedException, IOException {
		return this.sendMessageExpectOk(new StartScanning(this.msgId.incrementAndGet()));
	}

	public boolean stopScanning() throws ExecutionException, InterruptedException, IOException {
		return this.sendMessageExpectOk(new StopScanning(this.msgId.incrementAndGet()));
	}

	public boolean stopAllDevices() throws ExecutionException, InterruptedException, IOException {
		return this.sendMessageExpectOk(new StopAllDevices(this.msgId.incrementAndGet()));
	}

	public boolean requestLog(final RequestLog.ButtplugLogLevel aLogLevel)
			throws ExecutionException, InterruptedException, IOException {
		return this.sendMessageExpectOk(new RequestLog(aLogLevel, this.msgId.getAndIncrement()));
	}

	public ListenableFuture<ButtplugMessage> sendDeviceMessage(final ButtplugClientDevice device,
			final ButtplugDeviceMessage deviceMsg) throws ExecutionException, InterruptedException, IOException {
		final SettableListenableFuture<ButtplugMessage> promise = new SettableListenableFuture<>();
		final ButtplugClientDevice dev = this._devices.get(device.index);
		if (dev != null) {
			if (!dev.allowedMessages.contains(deviceMsg.getClass().getSimpleName())) {
				promise.set(new org.metafetish.buttplug.core.Messages.Error(
						"Device does not accept message type: " + deviceMsg.getClass().getSimpleName(),
						org.metafetish.buttplug.core.Messages.Error.ErrorClass.ERROR_DEVICE,
						ButtplugConsts.SystemMsgId));
				return promise;
			}

			deviceMsg.deviceIndex = device.index;
			deviceMsg.id = this.msgId.incrementAndGet();
			return this.sendMessage(deviceMsg);
		} else {
			promise.set(new org.metafetish.buttplug.core.Messages.Error("Device not available.",
					org.metafetish.buttplug.core.Messages.Error.ErrorClass.ERROR_DEVICE, ButtplugConsts.SystemMsgId));
			return promise;
		}
	}

	protected boolean sendMessageExpectOk(final ButtplugMessage msg)
			throws ExecutionException, InterruptedException, IOException {
		return this.sendMessage(msg).get() instanceof Ok;
	}

	protected ListenableFuture<ButtplugMessage> sendMessage(final ButtplugMessage msg)
			throws ExecutionException, InterruptedException, IOException {
		final SettableListenableFuture<ButtplugMessage> promise = new SettableListenableFuture<>();

		this._waitingMsgs.put(msg.id, promise);
		if (this.websocket == null) {
			promise.set(new org.metafetish.buttplug.core.Messages.Error("Bad WS state!", Error.ErrorClass.ERROR_UNKNOWN,
					ButtplugConsts.SystemMsgId));
			return promise;
		}

		try {
			this.websocket.sendText(this._parser.formatJson(msg));
			this.websocket.flush();
		} catch (final IOException e) {
			promise.set(new org.metafetish.buttplug.core.Messages.Error(e.getMessage(),
					org.metafetish.buttplug.core.Messages.Error.ErrorClass.ERROR_UNKNOWN, msg.id));
		}

		return promise;
	}

	protected WebSocket getWebSocket(final URI url, final boolean trustAll)
			throws IOException, WebSocketException, NoSuchAlgorithmException, KeyManagementException {
		SSLContext context = SSLContext.getDefault();
		if (trustAll) {
			context = SSLContext.getInstance("TLS");
			context.init(null, new TrustManager[] { new X509TrustManager() {
				@Override
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				@Override
				public void checkClientTrusted(final X509Certificate[] certs, final String authType) {
				}

				@Override
				public void checkServerTrusted(final X509Certificate[] certs, final String authType) {
				}
			} }, null);
		}
		return new WebSocketFactory().setSSLContext(context)
				.setConnectionTimeout(2000)
				.createSocket(url)
				.addListener(this)
				.addExtension(WebSocketExtension.PERMESSAGE_DEFLATE)
				.connect();
	}
}
