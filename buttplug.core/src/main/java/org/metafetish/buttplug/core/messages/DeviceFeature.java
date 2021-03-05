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

	public DeviceFeature(int featureCount, int[] stepCount) {
		this.featureCount = featureCount;
		this.stepCount = stepCount;
	}

	public int getFeatureCount() {
		return featureCount;
	}

	public void setFeatureCount(int featureCount) {
		this.featureCount = featureCount;
	}

	public int[] getStepCount() {
		return stepCount;
	}

	public void setStepCount(int[] stepCount) {
		this.stepCount = stepCount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + featureCount;
		result = prime * result + Arrays.hashCode(stepCount);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DeviceFeature other = (DeviceFeature) obj;
		if (featureCount != other.featureCount)
			return false;
		if (!Arrays.equals(stepCount, other.stepCount))
			return false;
		return true;
	}
}
