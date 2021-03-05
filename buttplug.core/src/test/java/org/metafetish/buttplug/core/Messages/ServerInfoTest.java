package org.metafetish.buttplug.core.Messages;

import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.metafetish.buttplug.core.ButtplugJsonMessageParser;
import org.metafetish.buttplug.core.ButtplugMessage;
import org.metafetish.buttplug.core.messages.ServerInfo;

public class ServerInfoTest {

	@Test
	public void test() throws IOException {
		final String testStr
				= "[{\"ServerInfo\":{\"Id\":1,\"MessageVersion\":1,\"MaxPingTime\":500,\"ServerName\":\"Websocket Server\"}}]";

		final ButtplugJsonMessageParser parser = new ButtplugJsonMessageParser();
		final List<ButtplugMessage> msgs = parser.parseJson(testStr);

		Assert.assertEquals(msgs.size(), 1);
		Assert.assertEquals(msgs.get(0).getClass(), ServerInfo.class);
		Assert.assertEquals(msgs.get(0).getId(), 1);
		Assert.assertEquals(((ServerInfo) msgs.get(0)).getMessageVersion(), 1);
		Assert.assertEquals(((ServerInfo) msgs.get(0)).getMaxPingTime(), 500);
		Assert.assertEquals(((ServerInfo) msgs.get(0)).getServerName(), "Websocket Server");

		String jsonOut = parser.formatJson(msgs);
		Assert.assertEquals(testStr, jsonOut);

		jsonOut = parser.formatJson(msgs.get(0));
		Assert.assertEquals(testStr, jsonOut);
	}
}
