package org.metafetish.buttplug.core.messages;

import org.metafetish.buttplug.core.ButtplugConsts;
import org.metafetish.buttplug.core.ButtplugMessage;

public class Ping extends ButtplugMessage {

	public Ping() {
		this(ButtplugConsts.DEFAULT_MSG_ID);
	}

	public Ping(final long id) {
		super(id);
	}
}
