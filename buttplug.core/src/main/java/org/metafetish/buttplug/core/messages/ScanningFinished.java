package org.metafetish.buttplug.core.messages;

import org.metafetish.buttplug.core.ButtplugConsts;
import org.metafetish.buttplug.core.ButtplugMessage;

public class ScanningFinished extends ButtplugMessage {

	public ScanningFinished() {
		super(ButtplugConsts.SYSTEM_MSG_ID);
	}
}
