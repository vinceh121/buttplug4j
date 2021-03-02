package org.metafetish.buttplug.core.messages;

import org.metafetish.buttplug.core.ButtplugConsts;
import org.metafetish.buttplug.core.ButtplugMessage;

public class StopAllDevices extends ButtplugMessage {

	public StopAllDevices() {
		super(ButtplugConsts.DefaultMsgId);
	}

	public StopAllDevices(final long id) {
		super(id);
	}
}
