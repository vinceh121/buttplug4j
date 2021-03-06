package org.metafetish.buttplug.core.messages;

import org.metafetish.buttplug.core.ButtplugConsts;
import org.metafetish.buttplug.core.ButtplugDeviceMessage;

import com.fasterxml.jackson.annotation.JsonProperty;

@Deprecated(since = "Buttplug 1")
public class SingleMotorVibrateCmd extends ButtplugDeviceMessage {

	@JsonProperty(value = "Speed", required = true)
	private double speed;

	public SingleMotorVibrateCmd() {
		this(-1, 0, ButtplugConsts.DEFAULT_MSG_ID);
	}

	public SingleMotorVibrateCmd(final long deviceIndex, final double speed, final long id) {
		super(id, deviceIndex);
		this.setSpeed(speed);
	}

	public double getSpeed() {
		if (this.speed > 1 || this.speed < 0) {
			return 0;
		}
		return this.speed;
	}

	public void setSpeed(final double speed) {
		if (speed > 1) {
			throw new IllegalArgumentException("SingleMotorVibrateCmd cannot have a speed higher than 1!");
		}

		if (speed < 0) {
			throw new IllegalArgumentException("SingleMotorVibrateCmd cannot have a speed lower than 0!");
		}

		this.speed = speed;
	}
}
