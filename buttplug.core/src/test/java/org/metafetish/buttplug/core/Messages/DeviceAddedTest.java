package org.metafetish.buttplug.core.Messages;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.metafetish.buttplug.core.ButtplugJsonMessageParser;
import org.metafetish.buttplug.core.ButtplugMessage;
import org.metafetish.buttplug.core.messages.DeviceAdded;
import org.metafetish.buttplug.core.messages.DeviceFeature;

public class DeviceAddedTest {

	@Test
	public void test() throws IOException {
		final String testStr
				= "[{\"DeviceAdded\":{\"Id\":3,\"DeviceIndex\":2,\"DeviceName\":\"foo\",\"DeviceMessages\":{\"VibrateCmd\":{\"FeatureCount\":1,\"StepCount\":[1]}}}}]";
		final HashMap<String, DeviceFeature> deviceMessages = new HashMap<>();
		deviceMessages.put("VibrateCmd", new DeviceFeature(1, new int[] { 1 }));

		final ButtplugJsonMessageParser parser = new ButtplugJsonMessageParser();
		final List<ButtplugMessage> msgs = parser.parseJson(testStr);

		Assert.assertEquals(1, msgs.size());
		Assert.assertEquals(DeviceAdded.class, msgs.get(0).getClass());
		Assert.assertEquals(3, msgs.get(0).getId());
		Assert.assertEquals(2, ((DeviceAdded) msgs.get(0)).getDeviceIndex());
		Assert.assertEquals("foo", ((DeviceAdded) msgs.get(0)).getDeviceName());
		Assert.assertTrue(deviceMessages.equals(((DeviceAdded) msgs.get(0)).getDeviceMessages()));

		String jsonOut = parser.formatJson(msgs);
		Assert.assertEquals(testStr, jsonOut);

		jsonOut = parser.formatJson(msgs.get(0));
		Assert.assertEquals(testStr, jsonOut);
	}

}
