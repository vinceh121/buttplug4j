package me.vinceh121.buttplug4j.vertx.server;

import org.metafetish.buttplug.core.ButtplugMessage;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

public interface ButtplugWebSocketMessageHandler extends Handler<RoutingContext> {
	void addMessageHandler(Handler<ButtplugMessage> handler);

	void addMessageHandler(Handler<ButtplugMessage> handler, String messageType);

	void addMessageHandler(Handler<ButtplugMessage> handler, long messageId);

	void receiveSingleMessage(Handler<ButtplugMessage> handler);

	void receiveSingleMessage(Handler<ButtplugMessage> handler, String messageType);

	void receiveSingleMessage(Handler<ButtplugMessage> handler, long messageId);
}
