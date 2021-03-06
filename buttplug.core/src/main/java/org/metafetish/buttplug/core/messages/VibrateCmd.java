package org.metafetish.buttplug.core.messages;

import java.util.Collections;
import java.util.List;

import org.metafetish.buttplug.core.ButtplugConsts;
import org.metafetish.buttplug.core.ButtplugDeviceMessage;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VibrateCmd extends ButtplugDeviceMessage {
	@JsonProperty(value = "Speeds", required = true)
	private List<Speed> speeds;

	public VibrateCmd() {
		this(-1, Collections.emptyList(), ButtplugConsts.DEFAULT_MSG_ID);
	}

	public VibrateCmd(final long deviceIndex, List<Speed> speeds, final long id) {
		super(id, deviceIndex);
		this.speeds = speeds;
	}

	public static class Speed {
		@JsonProperty(value = "Index", required = true)
		private int index;
		@JsonProperty(value = "Speed", required = true)
		private double speed;

		public Speed() {
		}

		public Speed(int index, double speed) {
			this.index = index;
			this.speed = speed;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

		public double getSpeed() {
			return speed;
		}

		public void setSpeed(double speed) {
			this.speed = speed;
		}
	}
}
