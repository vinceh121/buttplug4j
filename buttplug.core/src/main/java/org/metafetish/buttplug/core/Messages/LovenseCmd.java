package org.metafetish.buttplug.core.Messages;

import org.metafetish.buttplug.core.ButtplugConsts;
import org.metafetish.buttplug.core.ButtplugDeviceMessage;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LovenseCmd extends ButtplugDeviceMessage {

	@JsonProperty(value = "Command", required = true)
	public String deviceCmd;

	public LovenseCmd(final long deviceIndex, final String deviceCmd, final long id) {
		super(id, deviceIndex);
		this.deviceCmd = deviceCmd;
	}

	@SuppressWarnings("unused")
	private LovenseCmd() {
		super(ButtplugConsts.DefaultMsgId, -1);
		this.deviceCmd = "";
	}
}
