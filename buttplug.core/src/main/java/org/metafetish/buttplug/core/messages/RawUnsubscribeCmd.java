package org.metafetish.buttplug.core.messages;

import org.metafetish.buttplug.core.AbstractRawCmd;

public class RawUnsubscribeCmd extends AbstractRawCmd {
	public RawUnsubscribeCmd() {
		this(0, 0, null);
	}

	public RawUnsubscribeCmd(long id, int deviceIndex, String endpoint) {
		super(id, deviceIndex, endpoint);
	}
}
