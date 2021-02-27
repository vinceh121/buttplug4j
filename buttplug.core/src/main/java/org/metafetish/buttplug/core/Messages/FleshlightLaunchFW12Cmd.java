package org.metafetish.buttplug.core.Messages;

import org.metafetish.buttplug.core.ButtplugConsts;
import org.metafetish.buttplug.core.ButtplugDeviceMessage;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FleshlightLaunchFW12Cmd extends ButtplugDeviceMessage {

	@JsonProperty(value = "Speed", required = true)
	private int speed;
	@JsonProperty(value = "Position", required = true)
	private int position;

	public FleshlightLaunchFW12Cmd(final long deviceIndex, final int speed, final int position, final long id) {
		super(id, deviceIndex);

		this.SetSpeed(speed);
		this.SetPosition(position);
	}

	@SuppressWarnings("unused")
	private FleshlightLaunchFW12Cmd() {
		super(ButtplugConsts.DefaultMsgId, -1);

		this.SetSpeed(0);
		this.SetPosition(0);
	}

	public int GetSpeed() {
		if (this.speed > 99 || this.speed < 0) {
			return 0;
		}
		return this.speed;
	}

	public void SetSpeed(final int speed) {
		if (speed > 99) {
			throw new IllegalArgumentException("FleshlightLaunchFW12Cmd cannot have a speed higher than 99!");
		}

		if (speed < 0) {
			throw new IllegalArgumentException("FleshlightLaunchFW12Cmd cannot have a speed lower than 0!");
		}

		this.speed = speed;
	}

	public int GetPosition() {
		if (this.position > 99 || this.position < 0) {
			return 0;
		}
		return this.position;
	}

	public void SetPosition(final int position) {
		if (position > 99) {
			throw new IllegalArgumentException("FleshlightLaunchFW12Cmd cannot have a position higher than 99!");
		}

		if (position < 0) {
			throw new IllegalArgumentException("FleshlightLaunchFW12Cmd cannot have a position lower than 0!");
		}

		this.position = position;
	}
}
