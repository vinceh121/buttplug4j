package org.metafetish.buttplug.core.messages;

import org.metafetish.buttplug.core.AbstractRawCmd;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RawReadCmd extends AbstractRawCmd {
	@JsonProperty(value = "ExpectedLength", required = true)
	private int expectedLength;

	@JsonProperty(value = "WaitForData")
	private boolean waitForData;

	public RawReadCmd() {
		this(0, 0, null, 0, false);
	}

	public RawReadCmd(long id, int deviceIndex, String endpoint, int expectedLength, boolean waitForData) {
		super(id, deviceIndex, endpoint);
		this.expectedLength = expectedLength;
		this.waitForData = waitForData;
	}

	public int getExpectedLength() {
		return expectedLength;
	}

	public void setExpectedLength(int expectedLength) {
		this.expectedLength = expectedLength;
	}

	public boolean isWaitForData() {
		return waitForData;
	}

	public void setWaitForData(boolean waitForData) {
		this.waitForData = waitForData;
	}
}
