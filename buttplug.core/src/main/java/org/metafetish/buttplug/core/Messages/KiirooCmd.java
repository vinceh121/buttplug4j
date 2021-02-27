package org.metafetish.buttplug.core.Messages;

import org.metafetish.buttplug.core.ButtplugConsts;
import org.metafetish.buttplug.core.ButtplugDeviceMessage;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KiirooCmd extends ButtplugDeviceMessage {

	@JsonProperty(value = "Command", required = true)
	public String deviceCmd;

	public KiirooCmd(final long deviceIndex, final String deviceCmd, final long id) {
		super(id, deviceIndex);
		this.deviceCmd = deviceCmd;
	}

	@SuppressWarnings("unused")
	private KiirooCmd() {
		super(ButtplugConsts.DefaultMsgId, -1);
		this.deviceCmd = "";
	}
}
