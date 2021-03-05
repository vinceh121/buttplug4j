package org.metafetish.buttplug.core.Messages;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import org.metafetish.buttplug.core.messages.RequestLog.ButtplugLogLevel;

@SuppressWarnings("deprecation")
public class ButtplugLogLevelTest {

	@Test
	public void test() throws IOException {
		for (final ButtplugLogLevel lvl : ButtplugLogLevel.values()) {
			Assert.assertEquals(lvl.name().toLowerCase(), lvl.toString().toLowerCase());
			Assert.assertEquals(lvl.name(), lvl.toString().toUpperCase());
			Assert.assertNotEquals(lvl.name(), lvl.toString());
		}
	}

}
