package org.metafetish.buttplug.core.messages;

import org.metafetish.buttplug.core.ButtplugConsts;
import org.metafetish.buttplug.core.ButtplugDeviceMessage;

public class StopDeviceCmd extends ButtplugDeviceMessage {

	public StopDeviceCmd() {
		super(ButtplugConsts.DefaultMsgId, -1);
	}

	public StopDeviceCmd(final long deviceIndex, final long id) {
		super(id, deviceIndex);
	}
}
