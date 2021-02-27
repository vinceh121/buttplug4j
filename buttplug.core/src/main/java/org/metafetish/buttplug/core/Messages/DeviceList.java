package org.metafetish.buttplug.core.Messages;

import org.metafetish.buttplug.core.ButtplugConsts;
import org.metafetish.buttplug.core.ButtplugMessage;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeviceList extends ButtplugMessage {

	@JsonProperty(value = "Devices", required = true)
	public DeviceMessageInfo[] devices;

	public DeviceList(final DeviceMessageInfo[] devices, final long id) {
		super(id);
		this.devices = devices;
	}

	@SuppressWarnings("unused")
	private DeviceList() {
		super(ButtplugConsts.DefaultMsgId);
		this.devices = new DeviceMessageInfo[] {};
	}
}
