package org.metafetish.buttplug.core.messages;

import org.metafetish.buttplug.core.ButtplugConsts;
import org.metafetish.buttplug.core.ButtplugMessage;

public class Ok extends ButtplugMessage {

	public Ok() {
		this(ButtplugConsts.DEFAULT_MSG_ID);
	}

	public Ok(final long id) {
		super(id);
	}
}
