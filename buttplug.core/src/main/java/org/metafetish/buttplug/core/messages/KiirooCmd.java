package org.metafetish.buttplug.core.messages;

import org.metafetish.buttplug.core.ButtplugConsts;
import org.metafetish.buttplug.core.ButtplugDeviceMessage;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KiirooCmd extends ButtplugDeviceMessage {

	@JsonProperty(value = "Command", required = true)
	private String deviceCmd;

	public KiirooCmd() {
		this(-1, "", ButtplugConsts.DefaultMsgId);
	}

	public KiirooCmd(final long deviceIndex, final String deviceCmd, final long id) {
		super(id, deviceIndex);
		this.deviceCmd = deviceCmd;
	}

	public String getDeviceCmd() {
		return this.deviceCmd;
	}

	public void setDeviceCmd(final String deviceCmd) {
		this.deviceCmd = deviceCmd;
	}
}
