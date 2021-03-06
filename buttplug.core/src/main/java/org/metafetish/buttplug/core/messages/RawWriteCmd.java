package org.metafetish.buttplug.core.messages;

import org.metafetish.buttplug.core.AbstractRawCmd;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RawWriteCmd extends AbstractRawCmd {
	@JsonProperty(value = "Data", required = true)
	private int[] data;

	@JsonProperty(value = "Data")
	private boolean writeWithResponse;

	public RawWriteCmd() {
		this(0, 0, null, new int[0], false);
	}

	public RawWriteCmd(long id, int deviceIndex, final String endpoint, int[] data, boolean writeWithResponse) {
		super(id, deviceIndex, endpoint);
		this.data = data;
		this.writeWithResponse = writeWithResponse;
	}

	public int[] getData() {
		return data;
	}

	public void setData(int[] data) {
		this.data = data;
	}

	public boolean isWriteWithResponse() {
		return writeWithResponse;
	}

	public void setWriteWithResponse(boolean writeWithResponse) {
		this.writeWithResponse = writeWithResponse;
	}

}
