package org.metafetish.buttplug.core.Messages;

import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.metafetish.buttplug.core.ButtplugJsonMessageParser;
import org.metafetish.buttplug.core.ButtplugMessage;
import org.metafetish.buttplug.core.messages.RequestLog;
import org.metafetish.buttplug.core.messages.RequestLog.ButtplugLogLevel;

public class RequestLogTest {

	@Test
	public void test() throws IOException {
		final String testStr = "[{\"RequestLog\":{\"Id\":7,\"LogLevel\":\"Error\"}}]";

		final ButtplugJsonMessageParser parser = new ButtplugJsonMessageParser();
		final List<ButtplugMessage> msgs = parser.parseJson(testStr);

		Assert.assertEquals(msgs.size(), 1);
		Assert.assertEquals(msgs.get(0).getClass(), RequestLog.class);
		Assert.assertEquals(msgs.get(0).getId(), 7);
		Assert.assertEquals(((RequestLog) msgs.get(0)).getLogLevel(), ButtplugLogLevel.ERROR);

		String jsonOut = parser.formatJson(msgs);
		Assert.assertEquals(testStr, jsonOut);

		jsonOut = parser.formatJson(msgs.get(0));
		Assert.assertEquals(testStr, jsonOut);
	}

}
