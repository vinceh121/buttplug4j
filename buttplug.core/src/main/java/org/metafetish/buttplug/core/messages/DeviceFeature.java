package org.metafetish.buttplug.core.messages;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeviceFeature {
	@JsonProperty(value = "FeatureCount")
	private int featureCount;

	@JsonProperty(value = "StepCount")
	private int[] stepCount;

	public DeviceFeature() {
	}

	public DeviceFeature(final int featureCount, final int[] stepCount) {
		this.featureCount = featureCount;
		this.stepCount = stepCount;
	}

	public int getFeatureCount() {
		return this.featureCount;
	}

	public void setFeatureCount(final int featureCount) {
		this.featureCount = featureCount;
	}

	public int[] getStepCount() {
		return this.stepCount;
	}

	public void setStepCount(final int[] stepCount) {
		this.stepCount = stepCount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.featureCount;
		result = prime * result + Arrays.hashCode(this.stepCount);
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final DeviceFeature other = (DeviceFeature) obj;
		if (this.featureCount != other.featureCount) {
			return false;
		}
		if (!Arrays.equals(this.stepCount, other.stepCount)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "DeviceFeature [featureCount="
				+ this.featureCount
				+ ", stepCount="
				+ Arrays.toString(this.stepCount)
				+ "]";
	}
}
