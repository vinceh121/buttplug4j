package org.metafetish.buttplug.core.Messages;

import org.metafetish.buttplug.core.ButtplugConsts;
import org.metafetish.buttplug.core.ButtplugDeviceMessage;

public class StopDeviceCmd extends ButtplugDeviceMessage {

	public StopDeviceCmd(final long deviceIndex, final long id) {
		super(id, deviceIndex);
	}

	@SuppressWarnings("unused")
	private StopDeviceCmd() {
		super(ButtplugConsts.DefaultMsgId, -1);
	}
}
