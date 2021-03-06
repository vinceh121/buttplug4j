package org.metafetish.buttplug.core.messages;

import org.metafetish.buttplug.core.ButtplugDeviceMessage;

public class RSSILevelCmd extends ButtplugDeviceMessage {

	public RSSILevelCmd(long id, long deviceIndex) {
		super(id, deviceIndex);
	}
}
