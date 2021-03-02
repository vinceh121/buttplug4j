package org.metafetish.buttplug.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.metafetish.buttplug.core.messages.DeviceAdded;
import org.metafetish.buttplug.core.messages.DeviceMessageInfo;
import org.metafetish.buttplug.core.messages.DeviceRemoved;

public class ButtplugClientDevice {
	private long index;
	private String name;
	private List<String> allowedMessages;

	public ButtplugClientDevice(final DeviceMessageInfo aDevInfo) {
		this.index = aDevInfo.getDeviceIndex();
		this.name = aDevInfo.getDeviceName();
		this.allowedMessages = Arrays.asList(aDevInfo.getDeviceMessages());
	}

	public ButtplugClientDevice(final DeviceAdded aDevInfo) {
		this.index = aDevInfo.getDeviceIndex();
		this.name = aDevInfo.getDeviceName();
		this.allowedMessages = Arrays.asList(aDevInfo.getDeviceMessages());
	}

	public ButtplugClientDevice(final DeviceRemoved aDevInfo) {
		this.index = aDevInfo.getDeviceIndex();
		this.name = "";
		this.allowedMessages = new ArrayList<>();
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

	public List<String> getAllowedMessages() {
		return this.allowedMessages;
	}

	public void setAllowedMessages(final List<String> allowedMessages) {
		this.allowedMessages = allowedMessages;
	}
}
