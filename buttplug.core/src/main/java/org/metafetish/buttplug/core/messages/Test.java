package org.metafetish.buttplug.core.messages;

import org.metafetish.buttplug.core.ButtplugConsts;
import org.metafetish.buttplug.core.ButtplugMessage;

import com.fasterxml.jackson.annotation.JsonProperty;

@Deprecated(since = "Buttplug 2")
public class Test extends ButtplugMessage {

	@JsonProperty(value = "TestString", required = true)
	private String testString;

	public Test() {
		this("", ButtplugConsts.DEFAULT_MSG_ID);
	}

	public Test(final String testString, final long id) {
		super(id);
		this.testString = testString;
	}

	public String getTestString() {
		return this.testString;
	}

	public void setTestString(final String testString) {
		if (testString.contentEquals("Error")) { // XXX this is a good idea?
			throw new IllegalArgumentException("Got an Error Message");
		}
		this.testString = testString;
	}
}
