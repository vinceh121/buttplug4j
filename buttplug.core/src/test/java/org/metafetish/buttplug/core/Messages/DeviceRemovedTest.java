package org.metafetish.buttplug.core.Messages;

import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.metafetish.buttplug.core.ButtplugJsonMessageParser;
import org.metafetish.buttplug.core.ButtplugMessage;

public class DeviceRemovedTest {

	@Test
	public void test() throws IOException {
		final String testStr = "[{\"DeviceRemoved\":{\"Id\":3,\"DeviceIndex\":2}}]";

		final ButtplugJsonMessageParser parser = new ButtplugJsonMessageParser();
		final List<ButtplugMessage> msgs = parser.parseJson(testStr);

		Assert.assertEquals(1, msgs.size());
		Assert.assertEquals(DeviceRemoved.class, msgs.get(0).getClass());
		Assert.assertEquals(3, msgs.get(0).id);
		Assert.assertEquals(2, ((DeviceRemoved) msgs.get(0)).deviceIndex);

		String jsonOut = parser.formatJson(msgs);
		Assert.assertEquals(testStr, jsonOut);

		jsonOut = parser.formatJson(msgs.get(0));
		Assert.assertEquals(testStr, jsonOut);
	}

}
