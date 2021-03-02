package org.metafetish.buttplug.core.Messages;

import org.metafetish.buttplug.core.ButtplugConsts;
import org.metafetish.buttplug.core.ButtplugDeviceMessage;

public class DeviceRemoved extends ButtplugDeviceMessage {
	public DeviceRemoved() {
		this(-1);
	}

	public DeviceRemoved(final long deviceMessage) {
		super(ButtplugConsts.SYSTEM_MSG_ID, deviceMessage);
	}
}
