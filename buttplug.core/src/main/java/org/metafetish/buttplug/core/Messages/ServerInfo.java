package org.metafetish.buttplug.core.Messages;

import org.metafetish.buttplug.core.ButtplugConsts;
import org.metafetish.buttplug.core.ButtplugMessage;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ServerInfo extends ButtplugMessage {
	@JsonProperty(value = "MajorVersion", required = true)
	private int majorVersion = 0;

	@JsonProperty(value = "MinorVersion", required = true)
	private int minorVersion = 0;

	@JsonProperty(value = "BuildVersion", required = true)
	private int buildVersion = 1;

	@JsonProperty(value = "MessageVersion", required = true)
	private int messageVersion;

	@JsonProperty(value = "MaxPingTime", required = true)
	private long maxPingTime;

	@JsonProperty(value = "ServerName", required = true)
	private String serverName;

	public ServerInfo() {
		this("", 1, 0, ButtplugConsts.DefaultMsgId);
	}

	public ServerInfo(final String serverName, final int messageVersion, final long maxPingTime, final long id) {
		super(id);

		this.serverName = serverName;
		this.messageVersion = messageVersion;
		this.maxPingTime = maxPingTime;
	}

	public int getMajorVersion() {
		return this.majorVersion;
	}

	public void setMajorVersion(final int majorVersion) {
		this.majorVersion = majorVersion;
	}

	public int getMinorVersion() {
		return this.minorVersion;
	}

	public void setMinorVersion(final int minorVersion) {
		this.minorVersion = minorVersion;
	}

	public int getBuildVersion() {
		return this.buildVersion;
	}

	public void setBuildVersion(final int buildVersion) {
		this.buildVersion = buildVersion;
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
