package org.metafetish.buttplug.core.Messages;

import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.metafetish.buttplug.core.ButtplugJsonMessageParser;
import org.metafetish.buttplug.core.ButtplugMessage;

public class FleshlightLaunchFW12CmdTest {

	@Test
	public void test() throws IOException {
		final String testStr
				= "[{\"FleshlightLaunchFW12Cmd\":{\"Id\":7,\"DeviceIndex\":3,\"Speed\":15,\"Position\":85}}]";

		final ButtplugJsonMessageParser parser = new ButtplugJsonMessageParser();
		final List<ButtplugMessage> msgs = parser.parseJson(testStr);

		Assert.assertEquals(msgs.size(), 1);
		Assert.assertEquals(msgs.get(0).getClass(), FleshlightLaunchFW12Cmd.class);
		Assert.assertEquals(msgs.get(0).getId(), 7);
		Assert.assertEquals(((FleshlightLaunchFW12Cmd) msgs.get(0)).getDeviceIndex(), 3);
		Assert.assertEquals(((FleshlightLaunchFW12Cmd) msgs.get(0)).getSpeed(), 15);
		Assert.assertEquals(((FleshlightLaunchFW12Cmd) msgs.get(0)).getPosition(), 85);

		String jsonOut = parser.formatJson(msgs);
		Assert.assertEquals(testStr, jsonOut);

		jsonOut = parser.formatJson(msgs.get(0));
		Assert.assertEquals(testStr, jsonOut);
	}

}
