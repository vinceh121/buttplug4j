package org.metafetish.buttplug.core.messages;

import org.metafetish.buttplug.core.AbstractRawCmd;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RawReading extends AbstractRawCmd {
	@JsonProperty(value = "Data", required = true)
	private int[] data;

	public RawReading() {
		this(0, 0, null, new int[0]);
	}

	public RawReading(long id, int deviceIndex, String endpoint, int[] data) {
		super(id, deviceIndex, endpoint);
		this.data = data;
	}

	public int[] getData() {
		return data;
	}

	public void setData(int[] data) {
		this.data = data;
	}
}
