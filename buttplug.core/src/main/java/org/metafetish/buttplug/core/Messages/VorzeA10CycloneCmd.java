package org.metafetish.buttplug.core.Messages;

import org.metafetish.buttplug.core.ButtplugConsts;
import org.metafetish.buttplug.core.ButtplugDeviceMessage;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VorzeA10CycloneCmd extends ButtplugDeviceMessage {

	@JsonProperty(value = "Clockwise", required = true)
	public boolean clockwise;
	@JsonProperty(value = "Speed", required = true)
	private int speed;

	public VorzeA10CycloneCmd(final long deviceIndex, final int speed, final boolean clockwise, final long id) {
		super(id, deviceIndex);
		this.SetSpeed(speed);
		this.clockwise = clockwise;
	}

	@SuppressWarnings("unused")
	private VorzeA10CycloneCmd() {
		super(ButtplugConsts.DefaultMsgId, -1);
		this.SetSpeed(0);
		this.clockwise = false;
	}

	public int GetSpeed() {
		if (this.speed > 99 || this.speed < 0) {
			return 0;
		}
		return this.speed;
	}

	public void SetSpeed(final int speed) {
		if (speed > 99) {
			throw new IllegalArgumentException("VorzeA10CycloneCmd cannot have a speed higher than 99!");
		}

		if (speed < 0) {
			throw new IllegalArgumentException("VorzeA10CycloneCmd cannot have a speed lower than 0!");
		}

		this.speed = speed;
	}
}
