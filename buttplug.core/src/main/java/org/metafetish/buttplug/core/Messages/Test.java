package org.metafetish.buttplug.core.Messages;

import org.metafetish.buttplug.core.ButtplugConsts;
import org.metafetish.buttplug.core.ButtplugMessage;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Test extends ButtplugMessage {

	@JsonProperty(value = "TestString", required = true)
	private String testString;

	public Test(final String testString, final long id) {
		super(id);
		this.testString = testString;
	}

	@SuppressWarnings("unused")
	private Test() {
		super(ButtplugConsts.DefaultMsgId);
		this.testString = "";
	}

	public String getTestString() {
		return this.testString;
	}

	public void setTestString(final String testString) {
		if (testString.contentEquals("Error")) {
			throw new IllegalArgumentException("Got an Error Message");
		}
		this.testString = testString;
	}
}
