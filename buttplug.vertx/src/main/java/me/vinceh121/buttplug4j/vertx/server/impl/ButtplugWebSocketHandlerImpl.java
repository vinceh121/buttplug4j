package me.vinceh121.buttplug4j.vertx.server.impl;

import java.io.IOException;
import java.util.List;

import org.metafetish.buttplug.core.ButtplugJsonMessageParser;
import org.metafetish.buttplug.core.ButtplugMessage;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import me.vinceh121.buttplug4j.vertx.server.ButtplugWebSocketHandler;

public class ButtplugWebSocketHandlerImpl implements ButtplugWebSocketHandler {
	private final ButtplugJsonMessageParser bpParser;
	private Handler<ButtplugMessage> handler;
	private Handler<Throwable> failureHandler;

	public ButtplugWebSocketHandlerImpl() {
		this.bpParser = new ButtplugJsonMessageParser();
	}

	@Override
	public void handle(final RoutingContext event) {
		event.request().toWebSocket().onSuccess(ws -> {
			ws.textMessageHandler(str -> {
				try {
					final List<ButtplugMessage> l = bpParser.parseJson(str);
					for (final ButtplugMessage msg : l) {
						this.handler.handle(msg); // XXX is this good or does it need some async vertx magic?
					}
				} catch (final IOException e) {
					this.failureHandler.handle(e);
				}
			});
		}).onFailure(this.failureHandler);
	}

	@Override
	public ButtplugWebSocketHandler setButtplugMessageHandler(final Handler<ButtplugMessage> handler) {
		this.handler = handler;
		return this;
	}

	@Override
	public Handler<ButtplugMessage> getButtplugMessageHandler() {
		return this.handler;
	}

	@Override
	public Handler<Throwable> getFailureHandler() {
		return failureHandler;
	}

	@Override
	public ButtplugWebSocketHandler setFailureHandler(Handler<Throwable> failureHandler) {
		this.failureHandler = failureHandler;
		return this;
	}

}
