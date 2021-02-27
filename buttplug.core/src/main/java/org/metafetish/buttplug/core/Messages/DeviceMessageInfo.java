package org.metafetish.buttplug.core.Messages;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeviceMessageInfo {

	@JsonProperty(value = "DeviceIndex", required = true)
	public long deviceIndex;

	@JsonProperty(value = "DeviceName", required = true)
	public String deviceName;

	@JsonProperty(value = "DeviceMessages", required = true)
	public String[] deviceMessages;

	public DeviceMessageInfo(final long deviceIndex, final String deviceName, final String[] deviceMessages) {
		this.deviceName = deviceName;
		this.deviceIndex = deviceIndex;
		this.deviceMessages = deviceMessages;
	}

	@SuppressWarnings("unused")
	private DeviceMessageInfo() {
		this.deviceName = "";
		this.deviceIndex = -1;
		this.deviceMessages = new String[] {};
	}
}
