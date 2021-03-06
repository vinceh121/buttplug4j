package org.metafetish.buttplug.core.messages;

import org.metafetish.buttplug.core.ButtplugConsts;
import org.metafetish.buttplug.core.ButtplugMessage;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestServerInfo extends ButtplugMessage {
	@JsonProperty(value = "ClientName", required = true)
	private String clientName;

	@JsonProperty(value = "MessageVersion", required = true)
	private int messageVersion;

	public RequestServerInfo() {
		this("", 0, ButtplugConsts.DEFAULT_MSG_ID);
	}

	public RequestServerInfo(final String clientName, final int messageVersion, final long id) {
		super(id);
		this.clientName = clientName;
		this.messageVersion = messageVersion;
	}

	public String getClientName() {
		return this.clientName;
	}

	public void setClientName(final String clientName) {
		this.clientName = clientName;
	}

	public int getMessageVersion() {
		return this.messageVersion;
	}

	public void setMessageVersion(final int messageVersion) {
		this.messageVersion = messageVersion;
	}

}
