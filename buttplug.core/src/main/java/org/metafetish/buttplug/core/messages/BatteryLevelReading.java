package org.metafetish.buttplug.core.messages;

import org.metafetish.buttplug.core.ButtplugDeviceMessage;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BatteryLevelReading extends ButtplugDeviceMessage {
	@JsonProperty(value = "BatteryLevel", required = true)
	private double batteryLevel;

	public BatteryLevelReading(long id, long deviceIndex, double batteryLevel) {
		super(id, deviceIndex);
		this.batteryLevel = batteryLevel;
	}

	public double getBatteryLevel() {
		return batteryLevel;
	}

	public void setBatteryLevel(double batteryLevel) {
		this.batteryLevel = batteryLevel;
	}
}
