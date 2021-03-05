package org.metafetish.buttplug.core.messages;

import java.util.HashMap;
import java.util.Map;

import org.metafetish.buttplug.core.ButtplugConsts;
import org.metafetish.buttplug.core.ButtplugDeviceMessage;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeviceAdded extends ButtplugDeviceMessage {
	@JsonProperty(value = "DeviceName", required = true)
	private String deviceName;

	@JsonProperty(value = "DeviceMessages", required = true)
	private Map<String, DeviceFeature> deviceMessages;

	public DeviceAdded() {
		this(0, "", new HashMap<>());
	}

	public DeviceAdded(final long deviceIndex, final String deviceName,
			final Map<String, DeviceFeature> deviceMessages) {
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

	public Map<String, DeviceFeature> getDeviceMessages() {
		return this.deviceMessages;
	}

	public void setDeviceMessages(final Map<String, DeviceFeature> deviceMessages) {
		this.deviceMessages = deviceMessages;
	}
}
