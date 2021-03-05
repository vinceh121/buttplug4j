package org.metafetish.buttplug.core.Messages;

import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.metafetish.buttplug.core.ButtplugJsonMessageParser;
import org.metafetish.buttplug.core.ButtplugMessage;
import org.metafetish.buttplug.core.messages.RequestServerInfo;

public class RequestServerInfoTest {

	@Test
	public void test() throws IOException {
		final String testStr = "[{\"RequestServerInfo\":{\"Id\":7,\"ClientName\":\"UnitTest\",\"MessageVersion\":2}}]";

		final ButtplugJsonMessageParser parser = new ButtplugJsonMessageParser();
		final List<ButtplugMessage> msgs = parser.parseJson(testStr);

		Assert.assertEquals(msgs.size(), 1);
		Assert.assertEquals(msgs.get(0).getClass(), RequestServerInfo.class);
		Assert.assertEquals(msgs.get(0).getId(), 7);
		Assert.assertEquals(((RequestServerInfo) msgs.get(0)).getClientName(), "UnitTest");

		String jsonOut = parser.formatJson(msgs);
		Assert.assertEquals(testStr, jsonOut);

		jsonOut = parser.formatJson(msgs.get(0));
		Assert.assertEquals(testStr, jsonOut);
	}

}
