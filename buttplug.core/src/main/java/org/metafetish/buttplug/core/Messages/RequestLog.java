package org.metafetish.buttplug.core.Messages;

import org.metafetish.buttplug.core.ButtplugConsts;
import org.metafetish.buttplug.core.ButtplugMessage;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

public class RequestLog extends ButtplugMessage {

	@JsonProperty(value = "LogLevel", required = true)
	public ButtplugLogLevel logLevel;

	@SuppressWarnings("unused")
	private RequestLog() {
		super(ButtplugConsts.DefaultMsgId);
		this.logLevel = ButtplugLogLevel.OFF;
	}

	public RequestLog(final ButtplugLogLevel logLevel, final long id) {
		super(id);
		this.logLevel = logLevel;
	}

	public enum ButtplugLogLevel {
		OFF("Off"), FATAL("Fatal"), ERROR("Error"), WARN("Warn"), INFO("Info"), DEBUG("Debug"), TRACE("Trace");

		private String jsonName;

		ButtplugLogLevel(final String jsonName) {
			this.jsonName = jsonName;
		}

		@JsonValue
		@Override
		public String toString() {
			return this.jsonName;
		}
	}
}
