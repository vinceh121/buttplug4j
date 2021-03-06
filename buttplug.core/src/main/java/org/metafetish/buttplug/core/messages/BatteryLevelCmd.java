package org.metafetish.buttplug.core.messages;

import org.metafetish.buttplug.core.ButtplugDeviceMessage;

public class BatteryLevelCmd extends ButtplugDeviceMessage {

	public BatteryLevelCmd(long id, long deviceIndex) {
		super(id, deviceIndex);
	}
}
