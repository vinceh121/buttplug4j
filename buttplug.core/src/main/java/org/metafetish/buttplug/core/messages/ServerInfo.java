package org.metafetish.buttplug.core.messages;

import org.metafetish.buttplug.core.ButtplugConsts;
import org.metafetish.buttplug.core.ButtplugMessage;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ServerInfo extends ButtplugMessage {
	@JsonProperty(value = "MessageVersion", required = true)
	private int messageVersion;

	@JsonProperty(value = "MaxPingTime", required = true)
	private long maxPingTime;

	@JsonProperty(value = "ServerName", required = true)
	private String serverName;

	public ServerInfo() {
		this("", 1, 0, ButtplugConsts.DEFAULT_MSG_ID);
	}

	public ServerInfo(final String serverName, final int messageVersion, final long maxPingTime, final long id) {
		super(id);

		this.serverName = serverName;
		this.messageVersion = messageVersion;
		this.maxPingTime = maxPingTime;
	}

	public int getMessageVersion() {
		return this.messageVersion;
	}

	public void setMessageVersion(final int messageVersion) {
		this.messageVersion = messageVersion;
	}

	public long getMaxPingTime() {
		return this.maxPingTime;
	}

	public void setMaxPingTime(final long maxPingTime) {
		this.maxPingTime = maxPingTime;
	}

	public String getServerName() {
		return this.serverName;
	}

	public void setServerName(final String serverName) {
		this.serverName = serverName;
	}
}
