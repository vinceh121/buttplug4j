package org.metafetish.buttplug.core.messages;

import org.metafetish.buttplug.core.ButtplugConsts;
import org.metafetish.buttplug.core.ButtplugDeviceMessage;

import com.fasterxml.jackson.annotation.JsonProperty;

@Deprecated(since = "Buttplug 2")
public class VorzeA10CycloneCmd extends ButtplugDeviceMessage {

	@JsonProperty(value = "Clockwise", required = true)
	private boolean clockwise;
	@JsonProperty(value = "Speed", required = true)
	private int speed;

	public VorzeA10CycloneCmd() {
		this(-1, 0, false, ButtplugConsts.DEFAULT_MSG_ID);
	}

	public VorzeA10CycloneCmd(final long deviceIndex, final int speed, final boolean clockwise, final long id) {
		super(id, deviceIndex);
		this.setSpeed(speed);
		this.clockwise = clockwise;
	}

	public int getSpeed() {
		if (this.speed > 99 || this.speed < 0) {
			return 0;
		}
		return this.speed;
	}

	public void setSpeed(final int speed) {
		if (speed > 99) {
			throw new IllegalArgumentException("VorzeA10CycloneCmd cannot have a speed higher than 99!");
		}

		if (speed < 0) {
			throw new IllegalArgumentException("VorzeA10CycloneCmd cannot have a speed lower than 0!");
		}

		this.speed = speed;
	}

	public boolean isClockwise() {
		return this.clockwise;
	}

	public void setClockwise(final boolean clockwise) {
		this.clockwise = clockwise;
	}
}
