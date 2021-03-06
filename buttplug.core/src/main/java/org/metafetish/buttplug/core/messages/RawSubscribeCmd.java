package org.metafetish.buttplug.core.messages;

import org.metafetish.buttplug.core.AbstractRawCmd;

public class RawSubscribeCmd extends AbstractRawCmd {
	public RawSubscribeCmd() {
		this(0, 0, null);
	}

	public RawSubscribeCmd(long id, int deviceIndex, String endpoint) {
		super(id, deviceIndex, endpoint);
	}
}
