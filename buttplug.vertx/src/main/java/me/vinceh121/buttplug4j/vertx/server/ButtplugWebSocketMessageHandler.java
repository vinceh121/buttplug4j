package me.vinceh121.buttplug4j.vertx.server;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

public interface ButtplugWebSocketMessageHandler extends Handler<RoutingContext> {
	void addFailureHandler(Handler<Throwable> handler);

	void addMessageHandler(Handler<ButtplugMessageContext> handler);

	void addMessageHandler(String messageType, Handler<ButtplugMessageContext> handler);

	void addMessageHandler(long messageId, Handler<ButtplugMessageContext> handler);

	void receiveSingleMessage(Handler<ButtplugMessageContext> handler);

	void receiveSingleMessage(String messageType, Handler<ButtplugMessageContext> handler);

	void receiveSingleMessage(long messageId, Handler<ButtplugMessageContext> handler);
}
