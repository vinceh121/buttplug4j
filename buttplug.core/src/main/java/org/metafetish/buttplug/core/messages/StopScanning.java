package org.metafetish.buttplug.core.messages;

import org.metafetish.buttplug.core.ButtplugConsts;
import org.metafetish.buttplug.core.ButtplugMessage;

public class StopScanning extends ButtplugMessage {

	public StopScanning() {
		super(ButtplugConsts.DEFAULT_MSG_ID);
	}

	public StopScanning(final long id) {
		super(id);
	}
}
