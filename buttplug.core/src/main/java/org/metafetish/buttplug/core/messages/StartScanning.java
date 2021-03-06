package org.metafetish.buttplug.core.messages;

import org.metafetish.buttplug.core.ButtplugConsts;
import org.metafetish.buttplug.core.ButtplugMessage;

public class StartScanning extends ButtplugMessage {

	public StartScanning() {
		super(ButtplugConsts.DEFAULT_MSG_ID);
	}

	public StartScanning(final long id) {
		super(id);
	}
}
