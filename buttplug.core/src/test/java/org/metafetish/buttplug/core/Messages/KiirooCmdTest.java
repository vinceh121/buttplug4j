package org.metafetish.buttplug.core.Messages;

import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.metafetish.buttplug.core.ButtplugJsonMessageParser;
import org.metafetish.buttplug.core.ButtplugMessage;
import org.metafetish.buttplug.core.messages.KiirooCmd;


@SuppressWarnings("deprecation")
public class KiirooCmdTest {

	@Test
	public void test() throws IOException {
		final String testStr = "[{\"KiirooCmd\":{\"Id\":7,\"DeviceIndex\":3,\"Command\":\"4;\"}}]";

		final ButtplugJsonMessageParser parser = new ButtplugJsonMessageParser();
		final List<ButtplugMessage> msgs = parser.parseJson(testStr);

		Assert.assertEquals(msgs.size(), 1);
		Assert.assertEquals(msgs.get(0).getClass(), KiirooCmd.class);
		Assert.assertEquals(msgs.get(0).getId(), 7);
		Assert.assertEquals(((KiirooCmd) msgs.get(0)).getDeviceIndex(), 3);
		Assert.assertEquals(((KiirooCmd) msgs.get(0)).getDeviceCmd(), "4;");

		String jsonOut = parser.formatJson(msgs);
		Assert.assertEquals(testStr, jsonOut);

		jsonOut = parser.formatJson(msgs.get(0));
		Assert.assertEquals(testStr, jsonOut);
	}

}
