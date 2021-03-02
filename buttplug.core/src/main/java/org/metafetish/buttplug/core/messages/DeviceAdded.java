package org.metafetish.buttplug.core.messages;

import org.metafetish.buttplug.core.ButtplugConsts;
import org.metafetish.buttplug.core.ButtplugDeviceMessage;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeviceAdded extends ButtplugDeviceMessage {
	@JsonProperty(value = "DeviceName", required = true)
	private String deviceName;

	@JsonProperty(value = "DeviceMessages", required = true)
	private String[] deviceMessages;

	public DeviceAdded() {
		this(0, "", new String[0]);
	}

	public DeviceAdded(final long deviceIndex, final String deviceName, final String[] deviceMessages) {
		super(ButtplugConsts.SYSTEM_MSG_ID, deviceIndex);

		this.deviceName = deviceName;
		this.deviceMessages = deviceMessages;
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
