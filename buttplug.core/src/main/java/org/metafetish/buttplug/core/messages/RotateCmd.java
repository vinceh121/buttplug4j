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

	public RotateCmd(final long deviceIndex, final List<Rotation> rotations, final long id) {
		super(id, deviceIndex);
		this.rotations = rotations;
	}

	public List<Rotation> getRotations() {
		return rotations;
	}

	public void setRotations(List<Rotation> rotations) {
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

		public Rotation(final int index) {
			this.index = index;
		}

		public int getIndex() {
			return this.index;
		}

		public void setIndex(final int index) {
			this.index = index;
		}

		public double getSpeed() {
			return this.speed;
		}

		public void setSpeed(final double speed) {
			this.speed = speed;
		}

		public boolean isClockwise() {
			return this.clockwise;
		}

		public void setClockwise(final boolean clockwise) {
			this.clockwise = clockwise;
		}
	}
}
