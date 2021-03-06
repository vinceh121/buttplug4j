package org.metafetish.buttplug.core.messages;

import org.metafetish.buttplug.core.ButtplugConsts;
import org.metafetish.buttplug.core.ButtplugMessage;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

@Deprecated
public class RequestLog extends ButtplugMessage {

	@JsonProperty(value = "LogLevel", required = true)
	private ButtplugLogLevel logLevel;

	public RequestLog() {
		this(ButtplugLogLevel.OFF, ButtplugConsts.DEFAULT_MSG_ID);
	}

	public RequestLog(final ButtplugLogLevel logLevel, final long id) {
		super(id);
		this.logLevel = logLevel;
	}

	public ButtplugLogLevel getLogLevel() {
		return this.logLevel;
	}

	public void setLogLevel(final ButtplugLogLevel logLevel) {
		this.logLevel = logLevel;
	}

	public enum ButtplugLogLevel {
		OFF("Off"), FATAL("Fatal"), ERROR("Error"), WARN("Warn"), INFO("Info"), DEBUG("Debug"), TRACE("Trace");

		private final String jsonName;

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
