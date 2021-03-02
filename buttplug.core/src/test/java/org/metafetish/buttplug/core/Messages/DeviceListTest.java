package org.metafetish.buttplug.core.Messages;

import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.metafetish.buttplug.core.ButtplugJsonMessageParser;
import org.metafetish.buttplug.core.ButtplugMessage;

public class DeviceListTest {

	@Test
	public void test() throws IOException {
		final String testStr
				= "[{\"DeviceList\":{\"Id\":5,\"Devices\":[{\"DeviceIndex\":2,\"DeviceName\":\"foo\",\"DeviceMessages\":[\"foo-cmd-1\",\"foo-cmd-2\"]},{\"DeviceIndex\":4,\"DeviceName\":\"bar\",\"DeviceMessages\":[\"bar-cmd-1\",\"bar-cmd-2\"]}]}}]";

		final ButtplugJsonMessageParser parser = new ButtplugJsonMessageParser();
		final List<ButtplugMessage> msgs = parser.parseJson(testStr);

		Assert.assertEquals(1, msgs.size());
		Assert.assertEquals(DeviceList.class, msgs.get(0).getClass());
		Assert.assertEquals(5, msgs.get(0).getId());
		Assert.assertEquals(2, ((DeviceList) msgs.get(0)).getDevices().length);

		final DeviceMessageInfo[] devs = ((DeviceList) msgs.get(0)).getDevices();
		Assert.assertEquals(2, devs[0].getDeviceIndex());
		Assert.assertEquals("foo", devs[0].getDeviceName());
		Assert.assertArrayEquals(new String[] { "foo-cmd-1", "foo-cmd-2" }, devs[0].getDeviceMessages());

		Assert.assertEquals(4, devs[1].getDeviceIndex());
		Assert.assertEquals("bar", devs[1].getDeviceName());
		Assert.assertArrayEquals(new String[] { "bar-cmd-1", "bar-cmd-2" }, devs[1].getDeviceMessages());

		String jsonOut = parser.formatJson(msgs);
		Assert.assertEquals(testStr, jsonOut);

		jsonOut = parser.formatJson(msgs.get(0));
		Assert.assertEquals(testStr, jsonOut);
	}

}
