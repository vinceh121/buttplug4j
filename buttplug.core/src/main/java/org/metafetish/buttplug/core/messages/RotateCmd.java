package org.metafetish.buttplug.core.messages;

import java.util.Collections;
import java.util.List;

import org.metafetish.buttplug.core.ButtplugConsts;
import org.metafetish.buttplug.core.ButtplugDeviceMessage;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RotateCmd extends ButtplugDeviceMessage {
	@JsonProperty(value = "Rotations", required = true)
	private List<Rotation> rotations;

	public RotateCmd() {
		this(-1, Collections.emptyList(), ButtplugConsts.DEFAULT_MSG_ID);
	}

	public RotateCmd(final long deviceIndex, List<Rotation> rotations, final long id) {
		super(id, deviceIndex);
		this.rotations = rotations;
	}

	public static class Rotation {
		@JsonProperty(value = "Index", required = true)
		private int index;

		@JsonProperty(value = "Speed", required = true)
		private double speed;

		@JsonProperty(value = "Clockwise", required = true)
		private boolean clockwise;

		public Rotation() {
		}

		public Rotation(int index) {
			this.index = index;
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

		public boolean isClockwise() {
			return clockwise;
		}

		public void setClockwise(boolean clockwise) {
			this.clockwise = clockwise;
		}
	}
}
