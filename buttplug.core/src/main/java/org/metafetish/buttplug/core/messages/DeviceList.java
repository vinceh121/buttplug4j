package org.metafetish.buttplug.core.messages;

import org.metafetish.buttplug.core.ButtplugConsts;
import org.metafetish.buttplug.core.ButtplugMessage;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeviceList extends ButtplugMessage {

	@JsonProperty(value = "Devices", required = true)
	private DeviceMessageInfo[] devices;

	public DeviceList() {
		this(new DeviceMessageInfo[0], ButtplugConsts.DefaultMsgId);
	}

	public DeviceList(final DeviceMessageInfo[] devices, final long id) {
		super(id);
		this.devices = devices;
	}

	public DeviceMessageInfo[] getDevices() {
		return this.devices;
	}

	public void setDevices(final DeviceMessageInfo[] devices) {
		this.devices = devices;
	}
}
