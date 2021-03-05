package me.vinceh121.buttplug4j.vertx.server.impl;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.metafetish.buttplug.core.ButtplugMessage;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import me.vinceh121.buttplug4j.vertx.server.ButtplugWebSocketHandler;
import me.vinceh121.buttplug4j.vertx.server.ButtplugWebSocketMessageHandler;

public class ButtplugWebSocketMessageHandlerImpl implements ButtplugWebSocketMessageHandler {
	private final ButtplugWebSocketHandler delegate;
	private final Queue<MsgHandlerWrapper> generalHandlers;
	private final Map<Long, List<MsgHandlerWrapper>> mapId;
	private final Map<String, List<MsgHandlerWrapper>> mapType;

	public ButtplugWebSocketMessageHandlerImpl() {
		this.delegate = ButtplugWebSocketHandler.create();
		this.delegate.setButtplugMessageHandler(this::handleMessage);
		this.generalHandlers = new ConcurrentLinkedQueue<>();
		this.mapId = new ConcurrentHashMap<>();
		this.mapType = new ConcurrentHashMap<>();
	}

	private void handleMessage(final ButtplugMessage msg) {
		for (final MsgHandlerWrapper wrap : this.generalHandlers) {
			if (wrap.single) {
				this.generalHandlers.remove(wrap);
			}
			wrap.handler.handle(msg);
		}

		final List<MsgHandlerWrapper> listId = this.mapId.get(msg.getId());
		if (listId != null && listId.size() != 0) {
			for (final MsgHandlerWrapper wrap : listId) {
				if (wrap.single) {
					listId.remove(wrap);
				}
				wrap.handler.handle(msg);
			}
		}

		final List<MsgHandlerWrapper> listType = this.mapType.get(msg.getClass().getName());
		if (listType != null && listType.size() != 0) {
			for (final MsgHandlerWrapper wrap : listType) {
				if (wrap.single) {
					listType.remove(wrap);
				}
				wrap.handler.handle(msg);
			}
		}
	}

	@Override
	public void handle(final RoutingContext event) {
		this.delegate.handle(event);
	}

	@Override
	public void addMessageHandler(final Handler<ButtplugMessage> handler) {
		this.generalHandlers.add(new MsgHandlerWrapper(handler, false));
	}

	@Override
	public void addMessageHandler(final Handler<ButtplugMessage> handler, final String messageType) {
		final List<MsgHandlerWrapper> list;
		if (this.mapType.containsKey(messageType)) {
			list = this.mapType.get(messageType);
		} else {
			list = new Vector<>();
			this.mapType.put(messageType, list);
		}
		list.add(new MsgHandlerWrapper(handler, false));
	}

	@Override
	public void addMessageHandler(final Handler<ButtplugMessage> handler, final long messageId) {
		final List<MsgHandlerWrapper> list;
		if (this.mapId.containsKey(messageId)) {
			list = this.mapId.get(messageId);
		} else {
			list = new Vector<>();
			this.mapId.put(messageId, list);
		}
		list.add(new MsgHandlerWrapper(handler, false));
	}

	@Override
	public void receiveSingleMessage(final Handler<ButtplugMessage> handler) {
		this.generalHandlers.add(new MsgHandlerWrapper(handler, true));
	}

	@Override
	public void receiveSingleMessage(final Handler<ButtplugMessage> handler, final String messageType) {
		final List<MsgHandlerWrapper> list;
		if (this.mapType.containsKey(messageType)) {
			list = this.mapType.get(messageType);
		} else {
			list = new Vector<>();
			this.mapType.put(messageType, list);
		}
		list.add(new MsgHandlerWrapper(handler, true));
	}

	@Override
	public void receiveSingleMessage(final Handler<ButtplugMessage> handler, final long messageId) {
		final List<MsgHandlerWrapper> list;
		if (this.mapId.containsKey(messageId)) {
			list = this.mapId.get(messageId);
		} else {
			list = new Vector<>();
			this.mapId.put(messageId, list);
		}
		list.add(new MsgHandlerWrapper(handler, true));
	}

	private class MsgHandlerWrapper {
		private final Handler<ButtplugMessage> handler;
		private final boolean single;

		public MsgHandlerWrapper(final Handler<ButtplugMessage> handler, final boolean single) {
			this.handler = handler;
			this.single = single;
		}
	}

}
