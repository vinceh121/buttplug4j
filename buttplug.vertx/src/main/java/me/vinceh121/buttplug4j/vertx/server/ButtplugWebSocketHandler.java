package me.vinceh121.buttplug4j.vertx.server;

import org.metafetish.buttplug.core.ButtplugMessage;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import me.vinceh121.buttplug4j.vertx.server.impl.ButtplugWebSocketHandlerImpl;

public interface ButtplugWebSocketHandler extends Handler<RoutingContext> {
	public static ButtplugWebSocketHandler create() {
		return new ButtplugWebSocketHandlerImpl();
	}

	/**
	 * Sets the handler that will receive parsed Buttplug messages
	 *
	 * @param handler
	 * @return this to be fluent
	 */
	ButtplugWebSocketHandler setButtplugMessageHandler(Handler<ButtplugMessage> handler);

	/**
	 * @return the Buttplug messages handler
	 */
	Handler<ButtplugMessage> getButtplugMessageHandler();

	/**
	 * Sets the handler that will receive websocket handshake and parse errors
	 *
	 * @param failureHandler the failure handler
	 * @return this to be fluent
	 */
	ButtplugWebSocketHandler setFailureHandler(Handler<Throwable> failureHandler);

	/**
	 * @return the failure handler
	 */
	Handler<Throwable> getFailureHandler();
}
