package org.metafetish.buttplug.core.messages;

import org.metafetish.buttplug.core.ButtplugConsts;
import org.metafetish.buttplug.core.ButtplugMessage;

public class StopAllDevices extends ButtplugMessage {

	public StopAllDevices() {
		super(ButtplugConsts.DEFAULT_MSG_ID);
	}

	public StopAllDevices(final long id) {
		super(id);
	}
}
