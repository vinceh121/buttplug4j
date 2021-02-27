package org.metafetish.buttplug.client;

import java.net.URI;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.metafetish.buttplug.core.Messages.SingleMotorVibrateCmd;
import org.metafetish.buttplug.core.Messages.StopAllDevices;

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
			if (dev.allowedMessages.contains(SingleMotorVibrateCmd.class.getSimpleName())) {
				client.sendDeviceMessage(dev, new SingleMotorVibrateCmd(dev.index, 0.5, client.getNextMsgId()));
			}
		}

		Thread.sleep(1000);
		Assert.assertTrue(client.sendMessageExpectOk(new StopAllDevices(client.getNextMsgId())));

		client.Disconnect();
	}
}
