package org.metafetish.buttplug.core;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class ButtplugDeviceMessage extends ButtplugMessage {

	@JsonProperty(value = "DeviceIndex", required = true)
	private long deviceIndex;

	public ButtplugDeviceMessage(final long id, final long deviceIndex) {
		super(id);
		this.deviceIndex = deviceIndex;
	}

	public long getDeviceIndex() {
		return this.deviceIndex;
	}

	public void setDeviceIndex(final long deviceIndex) {
		this.deviceIndex = deviceIndex;
	}
}
