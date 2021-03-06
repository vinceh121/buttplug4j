package me.vinceh121.buttplug4j.vertx.server.impl;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import me.vinceh121.buttplug4j.vertx.server.ButtplugMessageContext;
import me.vinceh121.buttplug4j.vertx.server.ButtplugWebSocketHandler;
import me.vinceh121.buttplug4j.vertx.server.ButtplugWebSocketMessageHandler;

public class ButtplugWebSocketMessageHandlerImpl implements ButtplugWebSocketMessageHandler {
	private final ButtplugWebSocketHandler delegate;
	private final Queue<Handler<Throwable>> failureHandlers;
	private final Queue<MsgHandlerWrapper> generalHandlers;
	private final Map<Long, List<MsgHandlerWrapper>> mapId;
	private final Map<String, List<MsgHandlerWrapper>> mapType;

	public ButtplugWebSocketMessageHandlerImpl() {
		this.delegate = ButtplugWebSocketHandler.create();
		this.delegate.setButtplugMessageHandler(this::handleMessage);
		this.delegate.setFailureHandler(this::handleFailure);
		this.failureHandlers = new ConcurrentLinkedQueue<>();
		this.generalHandlers = new ConcurrentLinkedQueue<>();
		this.mapId = new ConcurrentHashMap<>();
		this.mapType = new ConcurrentHashMap<>();
	}

	private void handleFailure(final Throwable t) {
		for (final Handler<Throwable> handlers : this.failureHandlers) {
			handlers.handle(t);
		}
	}

	private void handleMessage(final ButtplugMessageContext ctx) {
		for (final MsgHandlerWrapper wrap : this.generalHandlers) {
			if (wrap.single) {
				this.generalHandlers.remove(wrap);
			}
			wrap.handler.handle(ctx);
		}

		final List<MsgHandlerWrapper> listId = this.mapId.get(ctx.getMessage().getId());
		if (listId != null && listId.size() != 0) {
			for (final MsgHandlerWrapper wrap : listId) {
				if (wrap.single) {
					listId.remove(wrap);
				}
				wrap.handler.handle(ctx);
			}
		}

		final List<MsgHandlerWrapper> listType = this.mapType.get(ctx.getMessage().getClass().getSimpleName());
		if (listType != null && listType.size() != 0) {
			for (final MsgHandlerWrapper wrap : listType) {
				if (wrap.single) {
					listType.remove(wrap);
				}
				wrap.handler.handle(ctx);
			}
		}
	}

	@Override
	public void addFailureHandler(final Handler<Throwable> handler) {
		this.failureHandlers.add(handler);
	}

	@Override
	public void handle(final RoutingContext event) {
		this.delegate.handle(event);
	}

	@Override
	public void addMessageHandler(final Handler<ButtplugMessageContext> handler) {
		this.generalHandlers.add(new MsgHandlerWrapper(handler, false));
	}

	@Override
	public void addMessageHandler(final String messageType, final Handler<ButtplugMessageContext> handler) {
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
	public void addMessageHandler(final long messageId, final Handler<ButtplugMessageContext> handler) {
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
	public void receiveSingleMessage(final Handler<ButtplugMessageContext> handler) {
		this.generalHandlers.add(new MsgHandlerWrapper(handler, true));
	}

	@Override
	public void receiveSingleMessage(final String messageType, final Handler<ButtplugMessageContext> handler) {
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
	public void receiveSingleMessage(final long messageId, final Handler<ButtplugMessageContext> handler) {
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
		private final Handler<ButtplugMessageContext> handler;
		private final boolean single;

		public MsgHandlerWrapper(final Handler<ButtplugMessageContext> handler, final boolean single) {
			this.handler = handler;
			this.single = single;
		}
	}

}
