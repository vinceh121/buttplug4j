package org.metafetish.buttplug.core.Messages;

import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.metafetish.buttplug.core.ButtplugJsonMessageParser;
import org.metafetish.buttplug.core.ButtplugMessage;

public class TestTest {

	@Test
	public void test() throws IOException {
		final String testStr = "[{\"Test\":{\"Id\":7,\"TestString\":\"TestText\"}}]";

		final ButtplugJsonMessageParser parser = new ButtplugJsonMessageParser();
		final List<ButtplugMessage> msgs = parser.parseJson(testStr);

		Assert.assertEquals(msgs.size(), 1);
		Assert.assertEquals(msgs.get(0).getClass(), org.metafetish.buttplug.core.Messages.Test.class);
		Assert.assertEquals(msgs.get(0).id, 7);
		Assert.assertEquals(((org.metafetish.buttplug.core.Messages.Test) msgs.get(0)).getTestString(), "TestText");

		String jsonOut = parser.formatJson(msgs);
		Assert.assertEquals(testStr, jsonOut);

		jsonOut = parser.formatJson(msgs.get(0));
		Assert.assertEquals(testStr, jsonOut);
	}

}
