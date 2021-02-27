package org.metafetish.buttplug.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.metafetish.buttplug.core.Messages.DeviceAdded;
import org.metafetish.buttplug.core.Messages.DeviceMessageInfo;
import org.metafetish.buttplug.core.Messages.DeviceRemoved;

public class ButtplugClientDevice {
	public long index;

	public String name;

	public List<String> allowedMessages;

	public ButtplugClientDevice(final DeviceMessageInfo aDevInfo) {
		this.index = aDevInfo.deviceIndex;
		this.name = aDevInfo.deviceName;
		this.allowedMessages = Arrays.asList(aDevInfo.deviceMessages);
	}

	public ButtplugClientDevice(final DeviceAdded aDevInfo) {
		this.index = aDevInfo.deviceIndex;
		this.name = aDevInfo.deviceName;
		this.allowedMessages = Arrays.asList(aDevInfo.deviceMessages);
	}

	public ButtplugClientDevice(final DeviceRemoved aDevInfo) {
		this.index = aDevInfo.deviceIndex;
		this.name = "";
		this.allowedMessages = new ArrayList<>();
	}
}
