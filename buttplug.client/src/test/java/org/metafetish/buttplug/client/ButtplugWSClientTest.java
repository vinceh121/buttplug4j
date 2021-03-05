package org.metafetish.buttplug.client;

import java.net.URI;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.metafetish.buttplug.core.messages.StopAllDevices;
import org.metafetish.buttplug.core.messages.VibrateCmd;

public class ButtplugWSClientTest {

	@Ignore
	@Test
	public void TestConnect() throws Exception {
		final ButtplugWSClient client = new ButtplugWSClient("Java Test");
		client.Connect(new URI("wss://localhost:12345/buttplug"), true);
		client.startScanning();

		Thread.sleep(5000);
		client.requestDeviceList();
		for (final ButtplugClientDevice dev : client.getDevices()) {
			if (dev.getAllowedMessages().containsKey(VibrateCmd.class.getSimpleName())) {
				client.sendDeviceMessage(dev,
						new VibrateCmd(dev.getIndex(), Collections.singletonList(new VibrateCmd.Speed(0, 0.5)), 0));
			}
		}

		Thread.sleep(1000);
		Assert.assertTrue(client.sendMessageExpectOk(new StopAllDevices(client.getNextMsgId())));

		client.Disconnect();
	}
}
