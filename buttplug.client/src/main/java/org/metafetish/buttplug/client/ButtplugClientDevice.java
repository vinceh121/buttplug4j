package org.metafetish.buttplug.client;

import java.util.HashMap;
import java.util.Map;

import org.metafetish.buttplug.core.messages.DeviceAdded;
import org.metafetish.buttplug.core.messages.DeviceFeature;
import org.metafetish.buttplug.core.messages.DeviceMessageInfo;
import org.metafetish.buttplug.core.messages.DeviceRemoved;

public class ButtplugClientDevice {
	private long index;
	private String name;
	private Map<String, DeviceFeature> allowedMessages;

	public ButtplugClientDevice(final DeviceMessageInfo aDevInfo) {
		this.index = aDevInfo.getDeviceIndex();
		this.name = aDevInfo.getDeviceName();
		this.allowedMessages = aDevInfo.getDeviceMessages();
	}

	public ButtplugClientDevice(final DeviceAdded aDevInfo) {
		this.index = aDevInfo.getDeviceIndex();
		this.name = aDevInfo.getDeviceName();
		this.allowedMessages = aDevInfo.getDeviceMessages();
	}

	public ButtplugClientDevice(final DeviceRemoved aDevInfo) {
		this.index = aDevInfo.getDeviceIndex();
		this.name = "";
		this.allowedMessages = new HashMap<>();
	}

	public long getIndex() {
		return this.index;
	}

	public void setIndex(final long index) {
		this.index = index;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public Map<String, DeviceFeature> getAllowedMessages() {
		return this.allowedMessages;
	}

	public void setAllowedMessages(final Map<String, DeviceFeature> allowedMessages) {
		this.allowedMessages = allowedMessages;
	}
}
