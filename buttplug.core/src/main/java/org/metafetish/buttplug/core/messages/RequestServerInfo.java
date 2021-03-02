package org.metafetish.buttplug.core.messages;

import org.metafetish.buttplug.core.ButtplugConsts;
import org.metafetish.buttplug.core.ButtplugMessage;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestServerInfo extends ButtplugMessage {
	@JsonProperty(value = "ClientName", required = true)
	private String clientName;

	public RequestServerInfo() {
		this("", ButtplugConsts.DefaultMsgId);
	}

	public RequestServerInfo(final String clientName, final long id) {
		super(id);
		this.clientName = clientName;
	}

	public String getClientName() {
		return this.clientName;
	}

	public void setClientName(final String clientName) {
		this.clientName = clientName;
	}
}
