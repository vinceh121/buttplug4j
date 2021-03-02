package org.metafetish.buttplug.core.Messages;

import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.metafetish.buttplug.core.ButtplugJsonMessageParser;
import org.metafetish.buttplug.core.ButtplugMessage;
import org.metafetish.buttplug.core.messages.Error;

public class ErrorTest {

	@Test
	public void test() throws IOException {
		final String testStr = "[{\"Error\":{\"Id\":7,\"ErrorCode\":4,\"ErrorMessage\":\"TestError\"}}]";

		final ButtplugJsonMessageParser parser = new ButtplugJsonMessageParser();
		final List<ButtplugMessage> msgs = parser.parseJson(testStr);

		Assert.assertEquals(msgs.size(), 1);
		Assert.assertEquals(msgs.get(0).getClass(), org.metafetish.buttplug.core.messages.Error.class);
		Assert.assertEquals(msgs.get(0).getId(), 7);
		Assert.assertEquals(((Error) msgs.get(0)).getErrorMessage(), "TestError");
		Assert.assertEquals(((Error) msgs.get(0)).getErrorCode(), Error.ErrorType.ERROR_DEVICE);

		String jsonOut = parser.formatJson(msgs);
		Assert.assertEquals(testStr, jsonOut);

		jsonOut = parser.formatJson(msgs.get(0));
		Assert.assertEquals(testStr, jsonOut);
	}

}
