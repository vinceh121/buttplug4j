package org.metafetish.buttplug.core.Messages;

import org.metafetish.buttplug.core.ButtplugConsts;
import org.metafetish.buttplug.core.ButtplugMessage;

public class RequestDeviceList extends ButtplugMessage {

	public RequestDeviceList() {
		this(ButtplugConsts.DefaultMsgId);
	}

	public RequestDeviceList(final long id) {
		super(id);
	}
}
