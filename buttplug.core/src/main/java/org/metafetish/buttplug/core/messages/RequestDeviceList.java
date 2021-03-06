package org.metafetish.buttplug.core.messages;

import org.metafetish.buttplug.core.ButtplugConsts;
import org.metafetish.buttplug.core.ButtplugMessage;

public class RequestDeviceList extends ButtplugMessage {

	public RequestDeviceList() {
		this(ButtplugConsts.DEFAULT_MSG_ID);
	}

	public RequestDeviceList(final long id) {
		super(id);
	}
}
