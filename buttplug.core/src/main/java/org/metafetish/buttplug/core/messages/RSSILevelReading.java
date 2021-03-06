package org.metafetish.buttplug.core.messages;

import org.metafetish.buttplug.core.ButtplugDeviceMessage;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RSSILevelReading extends ButtplugDeviceMessage {
	@JsonProperty(value = "RSSILevel", required = true)
	private double rssiLevel;

	public RSSILevelReading(long id, long deviceIndex, double rssiLevel) {
		super(id, deviceIndex);
		this.rssiLevel = rssiLevel;
	}

	public double getRssiLevel() {
		return rssiLevel;
	}

	public void setRssiLevel(double rssiLevel) {
		this.rssiLevel = rssiLevel;
	}
}
