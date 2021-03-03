package me.vinceh121.buttplug4j.vertx.server;

import org.metafetish.buttplug.core.ButtplugMessage;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.RoutingContext;
import me.vinceh121.buttplug4j.vertx.server.impl.ButtplugWebSocketHandlerImpl;

public interface ButtplugWebSocketHandler extends Handler<RoutingContext> {
	public static ButtplugWebSocketHandler create(final Vertx vertx) {
		return new ButtplugWebSocketHandlerImpl(vertx);
	}

	ButtplugWebSocketHandler setButtplugMessageHandler(Handler<ButtplugMessage> handler);

	Handler<ButtplugMessage> getButtplugMessageHandler();
}
