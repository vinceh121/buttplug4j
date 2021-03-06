package org.metafetish.buttplug.core.messages;

import java.util.Collections;
import java.util.List;

import org.metafetish.buttplug.core.ButtplugConsts;
import org.metafetish.buttplug.core.ButtplugMessage;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeviceList extends ButtplugMessage {

	@JsonProperty(value = "Devices", required = true)
	private List<DeviceMessageInfo> devices;

	public DeviceList() {
		this(Collections.emptyList(), ButtplugConsts.DEFAULT_MSG_ID);
	}

	public DeviceList(final List<DeviceMessageInfo> devices, final long id) {
		super(id);
		this.devices = devices;
	}

	public List<DeviceMessageInfo> getDevices() {
		return this.devices;
	}

	public void setDevices(final List<DeviceMessageInfo> devices) {
		this.devices = devices;
	}
}
