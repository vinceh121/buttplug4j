package org.metafetish.buttplug.core.messages;

import java.util.Collections;
import java.util.List;

import org.metafetish.buttplug.core.ButtplugConsts;
import org.metafetish.buttplug.core.ButtplugDeviceMessage;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LinearCmd extends ButtplugDeviceMessage {
	@JsonProperty(value = "Vectors", required = true)
	private List<Vector> vectors;

	public LinearCmd() {
		this(-1, Collections.emptyList(), ButtplugConsts.DefaultMsgId);
	}

	public LinearCmd(final long deviceIndex, List<Vector> vectors, final long id) {
		super(id, deviceIndex);
		this.vectors = vectors;
	}

	public static class Vector {
		@JsonProperty(value = "Index", required = true)
		private int index;

		@JsonProperty(value = "Duration", required = true)
		private int duration;

		@JsonProperty(value = "Position", required = true)
		private double position;

		public Vector() {
		}

		public Vector(int index, int duration, double speed) {
			this.index = index;
			this.duration = duration;
			this.position = speed;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

		public int getDuration() {
			return duration;
		}

		public void setDuration(int duration) {
			this.duration = duration;
		}

		public double getPosition() {
			return position;
		}

		public void setPosition(double position) {
			this.position = position;
		}
	}
}
