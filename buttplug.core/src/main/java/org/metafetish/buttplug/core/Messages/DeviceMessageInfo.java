package org.metafetish.buttplug.core.Messages;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeviceMessageInfo {

	@JsonProperty(value = "DeviceIndex", required = true)
	private long deviceIndex;

	@JsonProperty(value = "DeviceName", required = true)
	private String deviceName;

	@JsonProperty(value = "DeviceMessages", required = true)
	private String[] deviceMessages;

	public DeviceMessageInfo() {
		this(-1, "", new String[0]);
	}

	public DeviceMessageInfo(final long deviceIndex, final String deviceName, final String[] deviceMessages) {
		this.deviceName = deviceName;
		this.deviceIndex = deviceIndex;
		this.deviceMessages = deviceMessages;
	}

	public long getDeviceIndex() {
		return this.deviceIndex;
	}

	public void setDeviceIndex(final long deviceIndex) {
		this.deviceIndex = deviceIndex;
	}

	public String getDeviceName() {
		return this.deviceName;
	}

	public void setDeviceName(final String deviceName) {
		this.deviceName = deviceName;
	}

	public String[] getDeviceMessages() {
		return this.deviceMessages;
	}

	public void setDeviceMessages(final String[] deviceMessages) {
		this.deviceMessages = deviceMessages;
	}
}
