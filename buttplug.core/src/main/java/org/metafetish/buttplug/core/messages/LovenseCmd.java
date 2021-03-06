package org.metafetish.buttplug.core.messages;

import org.metafetish.buttplug.core.ButtplugConsts;
import org.metafetish.buttplug.core.ButtplugDeviceMessage;

import com.fasterxml.jackson.annotation.JsonProperty;

@Deprecated
public class LovenseCmd extends ButtplugDeviceMessage {

	@JsonProperty(value = "Command", required = true)
	private String deviceCmd;

	public LovenseCmd() {
		this(-1, "", ButtplugConsts.DEFAULT_MSG_ID);
	}

	public LovenseCmd(final long deviceIndex, final String deviceCmd, final long id) {
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
