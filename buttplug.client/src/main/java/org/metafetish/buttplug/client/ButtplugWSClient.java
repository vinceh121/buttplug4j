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
import java.util.concurrent.CompletableFuture;
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
import org.metafetish.buttplug.core.messages.DeviceAdded;
import org.metafetish.buttplug.core.messages.DeviceList;
import org.metafetish.buttplug.core.messages.DeviceMessageInfo;
import org.metafetish.buttplug.core.messages.DeviceRemoved;
import org.metafetish.buttplug.core.messages.Error;
import org.metafetish.buttplug.core.messages.Log;
import org.metafetish.buttplug.core.messages.Ok;
import org.metafetish.buttplug.core.messages.Ping;
import org.metafetish.buttplug.core.messages.RequestDeviceList;
import org.metafetish.buttplug.core.messages.RequestLog;
import org.metafetish.buttplug.core.messages.RequestServerInfo;
import org.metafetish.buttplug.core.messages.ScanningFinished;
import org.metafetish.buttplug.core.messages.ServerInfo;
import org.metafetish.buttplug.core.messages.StartScanning;
import org.metafetish.buttplug.core.messages.StopAllDevices;
import org.metafetish.buttplug.core.messages.StopScanning;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketExtension;
import com.neovisionaries.ws.client.WebSocketFactory;

public class ButtplugWSClient extends WebSocketAdapter {

	private IDeviceEvent deviceAdded;
	private IDeviceEvent deviceRemoved;
	private IScanningEvent scanningFinished;
	private IErrorEvent erorReceived;
	private ILogEvent logReceived;

	private WebSocket websocket;
	private final ButtplugJsonMessageParser parser;
	private final String clientName;
	private final ConcurrentHashMap<Long, CompletableFuture<ButtplugMessage>> waitingMsgs = new ConcurrentHashMap<>();
	private final ConcurrentHashMap<Long, ButtplugClientDevice> devices = new ConcurrentHashMap<>();

	private Timer pingTimer;
	private final AtomicLong msgId = new AtomicLong(1);

	public ButtplugWSClient(final String aClientName) {
		this.clientName = aClientName;
		this.parser = new ButtplugJsonMessageParser();
	}

	public long getNextMsgId() {
		return this.msgId.getAndIncrement();
	}

	public void Connect(final URI url) throws Exception {
		this.Connect(url, false);
	}

	public void Connect(final URI url, final boolean trustAll) throws Exception {
		this.waitingMsgs.clear();
		this.devices.clear();
		this.msgId.set(1);

		this.websocket = this.getWebSocket(url, trustAll);

		final ButtplugMessage res = this.sendMessage(new RequestServerInfo(this.clientName, this.getNextMsgId())).get();
		if (res instanceof ServerInfo) {
			if (((ServerInfo) res).getMaxPingTime() > 0) {
				this.pingTimer = new Timer("pingTimer", true);
				this.pingTimer.scheduleAtFixedRate(new TimerTask() {
					@Override
					public void run() {
						try {
							ButtplugWSClient.this.onPingTimer();
						} catch (final Exception e) {
							e.printStackTrace();
						}
					}
				}, 0, Math.round((double) ((ServerInfo) res).getMaxPingTime() / 2));
			}

		} else if (res instanceof org.metafetish.buttplug.core.messages.Error) {
			throw new Exception(((org.metafetish.buttplug.core.messages.Error) res).getErrorMessage());
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
		if (this.pingTimer != null) {
			this.pingTimer.cancel();
			this.pingTimer = null;
		}

		if (this.websocket != null) {
			this.websocket.disconnect();
			this.websocket = null;
		}

		int max = 3;
		while (max-- > 0 && this.waitingMsgs.size() != 0) {
			for (final long msgId : this.waitingMsgs.keySet()) {
				final CompletableFuture<ButtplugMessage> val = this.waitingMsgs.remove(msgId);
				if (val != null) {
					val.complete(new Error("Connection closed!", Error.ErrorType.ERROR_UNKNOWN,
							ButtplugConsts.SYSTEM_MSG_ID));
				}
			}
		}
		this.msgId.set(1);
	}

	@Override
	public void onTextMessage(final WebSocket socket, final String buf) {
		try {
			final List<ButtplugMessage> msgs = this.parser.parseJson(buf);

			for (final ButtplugMessage msg : msgs) {
				if (msg.getId() > 0) {
					final CompletableFuture<ButtplugMessage> val = this.waitingMsgs.remove(msg.getId());
					if (val != null) {
						val.complete(msg);
						continue;
					}
				}

				if (msg instanceof Log) {
					if (this.logReceived != null) {
						this.logReceived.logReceived((Log) msg);
					}
				} else if (msg instanceof DeviceAdded) {
					final ButtplugClientDevice device = new ButtplugClientDevice((DeviceAdded) msg);
					this.devices.put(((DeviceAdded) msg).getDeviceIndex(), device);
					if (this.deviceAdded != null) {
						this.deviceAdded.deviceAdded(device);
					}
				} else if (msg instanceof DeviceRemoved) {
					if (this.devices.remove(((DeviceRemoved) msg).getDeviceIndex()) != null
							&& this.deviceRemoved != null) {
						this.deviceRemoved.deviceRemoved(((DeviceRemoved) msg).getDeviceIndex());
					}
				} else if (msg instanceof ScanningFinished) {
					if (this.scanningFinished != null) {
						this.scanningFinished.scanningFinished();
					}
				} else if (msg instanceof org.metafetish.buttplug.core.messages.Error && this.erorReceived != null) {
					this.erorReceived.errorReceived((org.metafetish.buttplug.core.messages.Error) msg);
				}
			}
		} catch (final IOException e) {
			if (this.erorReceived != null) {
				this.erorReceived.errorReceived(new org.metafetish.buttplug.core.messages.Error(e.getMessage(),
						Error.ErrorType.ERROR_UNKNOWN, ButtplugConsts.SYSTEM_MSG_ID));
			} else {
				e.printStackTrace();
			}
		}
	}

	private void onPingTimer() throws Exception {
		try {
			final ButtplugMessage msg = this.sendMessage(new Ping(this.msgId.incrementAndGet())).get();
			if (msg instanceof org.metafetish.buttplug.core.messages.Error) {
				throw new Exception(((org.metafetish.buttplug.core.messages.Error) msg).getErrorMessage());
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
		if (!(res instanceof DeviceList) || ((DeviceList) res).getDevices() == null) {
			if (res instanceof org.metafetish.buttplug.core.messages.Error) {
				throw new Exception(((org.metafetish.buttplug.core.messages.Error) res).getErrorMessage());
			}
			return;
		}

		for (final DeviceMessageInfo d : ((DeviceList) res).getDevices()) {
			if (!this.devices.containsKey(d.getDeviceIndex())) {
				final ButtplugClientDevice device = new ButtplugClientDevice(d);
				if (this.devices.put(d.getDeviceIndex(), device) == null && this.deviceAdded != null) {
					this.deviceAdded.deviceAdded(device);
				}
			}
		}
	}

	public List<ButtplugClientDevice> getDevices() {
		final List<ButtplugClientDevice> devices = new ArrayList<>(this.devices.values());
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

	public CompletableFuture<ButtplugMessage> sendDeviceMessage(final ButtplugClientDevice device,
			final ButtplugDeviceMessage deviceMsg) throws ExecutionException, InterruptedException, IOException {
		final CompletableFuture<ButtplugMessage> promise = new CompletableFuture<>();
		final ButtplugClientDevice dev = this.devices.get(device.getIndex());
		if (dev != null) {
			if (!dev.getAllowedMessages().contains(deviceMsg.getClass().getSimpleName())) {
				promise.complete(new org.metafetish.buttplug.core.messages.Error(
						"Device does not accept message type: " + deviceMsg.getClass().getSimpleName(),
						org.metafetish.buttplug.core.messages.Error.ErrorType.ERROR_DEVICE,
						ButtplugConsts.SYSTEM_MSG_ID));
				return promise;
			}

			deviceMsg.setDeviceIndex(device.getIndex());
			deviceMsg.setId(this.msgId.incrementAndGet());
			return this.sendMessage(deviceMsg);
		} else {
			promise.complete(new org.metafetish.buttplug.core.messages.Error("Device not available.",
					org.metafetish.buttplug.core.messages.Error.ErrorType.ERROR_DEVICE, ButtplugConsts.SYSTEM_MSG_ID));
			return promise;
		}
	}

	protected boolean sendMessageExpectOk(final ButtplugMessage msg)
			throws ExecutionException, InterruptedException, IOException {
		return this.sendMessage(msg).get() instanceof Ok;
	}

	protected CompletableFuture<ButtplugMessage> sendMessage(final ButtplugMessage msg)
			throws ExecutionException, InterruptedException, IOException {
		final CompletableFuture<ButtplugMessage> promise = new CompletableFuture<>();

		this.waitingMsgs.put(msg.getId(), promise);
		if (this.websocket == null) {
			promise.complete(new org.metafetish.buttplug.core.messages.Error("Bad WS state!",
					Error.ErrorType.ERROR_UNKNOWN, ButtplugConsts.SYSTEM_MSG_ID));
			return promise;
		}

		try {
			this.websocket.sendText(this.parser.formatJson(msg));
			this.websocket.flush();
		} catch (final IOException e) {
			promise.complete(new org.metafetish.buttplug.core.messages.Error(e.getMessage(),
					org.metafetish.buttplug.core.messages.Error.ErrorType.ERROR_UNKNOWN, msg.getId()));
		}

		return promise;
	}

	protected WebSocket getWebSocket(final URI url, final boolean trustAll)
			throws IOException, WebSocketException, NoSuchAlgorithmException, KeyManagementException {
		final SSLContext context;
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
		} else {
			context = SSLContext.getDefault();
		}
		return new WebSocketFactory().setSSLContext(context)
				.setConnectionTimeout(2000)
				.createSocket(url)
				.addListener(this)
				.addExtension(WebSocketExtension.PERMESSAGE_DEFLATE)
				.connect();
	}

	public IDeviceEvent getDeviceAdded() {
		return this.deviceAdded;
	}

	public void setDeviceAdded(final IDeviceEvent deviceAdded) {
		this.deviceAdded = deviceAdded;
	}

	public IDeviceEvent getDeviceRemoved() {
		return this.deviceRemoved;
	}

	public void setDeviceRemoved(final IDeviceEvent deviceRemoved) {
		this.deviceRemoved = deviceRemoved;
	}

	public IScanningEvent getScanningFinished() {
		return this.scanningFinished;
	}

	public void setScanningFinished(final IScanningEvent scanningFinished) {
		this.scanningFinished = scanningFinished;
	}

	public IErrorEvent getErorReceived() {
		return this.erorReceived;
	}

	public void setErorReceived(final IErrorEvent erorReceived) {
		this.erorReceived = erorReceived;
	}

	public ILogEvent getLogReceived() {
		return this.logReceived;
	}

	public void setLogReceived(final ILogEvent logReceived) {
		this.logReceived = logReceived;
	}
}
