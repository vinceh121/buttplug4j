package org.metafetish.buttplug.core.Messages;

import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.metafetish.buttplug.core.ButtplugJsonMessageParser;
import org.metafetish.buttplug.core.ButtplugMessage;
import org.metafetish.buttplug.core.messages.Log;
import org.metafetish.buttplug.core.messages.RequestLog.ButtplugLogLevel;

public class LogTest {

	@Test
	public void test() throws IOException {
		final String testStr = "[{\"Log\":{\"Id\":7,\"LogLevel\":\"Warn\",\"LogMessage\":\"TestLog\"}}]";

		final ButtplugJsonMessageParser parser = new ButtplugJsonMessageParser();
		final List<ButtplugMessage> msgs = parser.parseJson(testStr);

		Assert.assertEquals(msgs.size(), 1);
		Assert.assertEquals(msgs.get(0).getClass(), Log.class);
		Assert.assertEquals(msgs.get(0).getId(), 7);
		Assert.assertEquals(((Log) msgs.get(0)).getLogLevel(), ButtplugLogLevel.WARN);
		Assert.assertEquals(((Log) msgs.get(0)).getLogMessage(), "TestLog");

		String jsonOut = parser.formatJson(msgs);
		Assert.assertEquals(testStr, jsonOut);

		jsonOut = parser.formatJson(msgs.get(0));
		Assert.assertEquals(testStr, jsonOut);
	}

}
