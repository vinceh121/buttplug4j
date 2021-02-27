package org.metafetish.buttplug.core.Messages;

import org.metafetish.buttplug.core.ButtplugConsts;
import org.metafetish.buttplug.core.ButtplugMessage;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestServerInfo extends ButtplugMessage {
	@JsonProperty(value = "ClientName", required = true)
	public String clientName;

	public RequestServerInfo(final String clientName, final long id) {
		super(id);
		this.clientName = clientName;
	}

	@SuppressWarnings("unused")
	private RequestServerInfo() {
		super(ButtplugConsts.DefaultMsgId);
		this.clientName = "";
	}
}
