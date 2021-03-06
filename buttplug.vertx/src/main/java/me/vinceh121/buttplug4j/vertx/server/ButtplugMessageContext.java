package me.vinceh121.buttplug4j.vertx.server;

import org.metafetish.buttplug.core.ButtplugMessage;

import io.vertx.core.http.ServerWebSocket;
import io.vertx.ext.web.RoutingContext;

public class ButtplugMessageContext {
	private RoutingContext routingContext;
	private ServerWebSocket webSocket;
	private ButtplugMessage message;

	public ButtplugMessageContext() {
	}

	public ButtplugMessageContext(final RoutingContext routingContext, final ServerWebSocket webSocket,
			final ButtplugMessage message) {
		this.routingContext = routingContext;
		this.webSocket = webSocket;
		this.message = message;
	}

	public RoutingContext getRoutingContext() {
		return this.routingContext;
	}

	public void setRoutingContext(final RoutingContext routingContext) {
		this.routingContext = routingContext;
	}

	public ServerWebSocket getWebSocket() {
		return this.webSocket;
	}

	public void setWebSocket(final ServerWebSocket webSocket) {
		this.webSocket = webSocket;
	}

	public ButtplugMessage getMessage() {
		return this.message;
	}

	public void setMessage(final ButtplugMessage message) {
		this.message = message;
	}
}
