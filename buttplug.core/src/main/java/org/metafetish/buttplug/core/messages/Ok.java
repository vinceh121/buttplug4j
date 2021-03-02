package org.metafetish.buttplug.core.messages;

import org.metafetish.buttplug.core.ButtplugConsts;
import org.metafetish.buttplug.core.ButtplugMessage;

public class Ok extends ButtplugMessage {

	public Ok() {
		this(ButtplugConsts.DefaultMsgId);
	}

	public Ok(final long id) {
		super(id);
	}
}
