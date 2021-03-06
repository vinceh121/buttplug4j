package org.metafetish.buttplug.core.Messages;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.metafetish.buttplug.core.ButtplugJsonMessageParser;
import org.metafetish.buttplug.core.ButtplugMessage;
import org.metafetish.buttplug.core.messages.DeviceFeature;
import org.metafetish.buttplug.core.messages.DeviceList;
import org.metafetish.buttplug.core.messages.DeviceMessageInfo;

public class DeviceListTest {

	@Test
	public void test() throws IOException {
		final String testStr = "[{\"DeviceList\":{\"Id\":5,\"Devices\":["
				+ "{\"DeviceIndex\":2,\"DeviceName\":\"foo\",\"DeviceMessages\":"
				+ "{\"VibrateCmd\":{\"FeatureCount\":1,\"StepCount\":[0]}}}"
				+ ",{\"DeviceIndex\":4,\"DeviceName\":\"bar\",\"DeviceMessages\":"
				+ "{\"VibrateCmd\":{\"FeatureCount\":1,\"StepCount\":[0]}}}"
				+ "]}}]";

		final HashMap<String, DeviceFeature> deviceMessages = new HashMap<>();
		deviceMessages.put("VibrateCmd", new DeviceFeature(1, new int[] { 0 }));

		final ButtplugJsonMessageParser parser = new ButtplugJsonMessageParser();
		final List<ButtplugMessage> msgs = parser.parseJson(testStr);

		Assert.assertEquals(1, msgs.size());
		Assert.assertEquals(DeviceList.class, msgs.get(0).getClass());
		Assert.assertEquals(5, msgs.get(0).getId());
		Assert.assertEquals(2, ((DeviceList) msgs.get(0)).getDevices().size());

		final List<DeviceMessageInfo> devs = ((DeviceList) msgs.get(0)).getDevices();
		Assert.assertEquals(2, devs.get(0).getDeviceIndex());
		Assert.assertEquals("foo", devs.get(0).getDeviceName());
		Assert.assertEquals(deviceMessages, devs.get(0).getDeviceMessages());

		Assert.assertEquals(4, devs.get(1).getDeviceIndex());
		Assert.assertEquals("bar", devs.get(1).getDeviceName());
		Assert.assertEquals(deviceMessages, devs.get(1).getDeviceMessages());

		String jsonOut = parser.formatJson(msgs);
		Assert.assertEquals(testStr, jsonOut);

		jsonOut = parser.formatJson(msgs.get(0));
		Assert.assertEquals(testStr, jsonOut);
	}

}
