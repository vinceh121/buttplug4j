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

	public ButtplugMessageContext(RoutingContext routingContext, ServerWebSocket webSocket, ButtplugMessage message) {
		this.routingContext = routingContext;
		this.webSocket = webSocket;
		this.message = message;
	}

	public RoutingContext getRoutingContext() {
		return routingContext;
	}

	public void setRoutingContext(RoutingContext routingContext) {
		this.routingContext = routingContext;
	}

	public ServerWebSocket getWebSocket() {
		return webSocket;
	}

	public void setWebSocket(ServerWebSocket webSocket) {
		this.webSocket = webSocket;
	}

	public ButtplugMessage getMessage() {
		return message;
	}

	public void setMessage(ButtplugMessage message) {
		this.message = message;
	}
}
