package org.metafetish.buttplug.core;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class AbstractRawCmd extends ButtplugDeviceMessage {
	@JsonProperty(value = "Endpoint", required = true)
	private String endpoint;

	public AbstractRawCmd(final long id, final int deviceIndex, final String endpoint) {
		super(id, deviceIndex);
		this.endpoint = endpoint;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}
}
