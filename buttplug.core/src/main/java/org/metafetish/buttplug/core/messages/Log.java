package org.metafetish.buttplug.core.messages;

import org.metafetish.buttplug.core.ButtplugConsts;
import org.metafetish.buttplug.core.ButtplugMessage;
import org.metafetish.buttplug.core.messages.RequestLog.ButtplugLogLevel;

import com.fasterxml.jackson.annotation.JsonProperty;

@Deprecated(since = "Buttplug 2")
public class Log extends ButtplugMessage {
	@JsonProperty(value = "LogLevel", required = true)
	private ButtplugLogLevel logLevel;

	@JsonProperty(value = "LogMessage", required = true)
	private String logMessage;

	public Log() {
		this(ButtplugLogLevel.OFF, "", ButtplugConsts.DEFAULT_MSG_ID);
	}

	public Log(final ButtplugLogLevel logLevel, final String logMessage, final long id) {
		super(id);
		this.logLevel = logLevel;
		this.logMessage = logMessage;
	}

	public ButtplugLogLevel getLogLevel() {
		return this.logLevel;
	}

	public void setLogLevel(final ButtplugLogLevel logLevel) {
		this.logLevel = logLevel;
	}

	public String getLogMessage() {
		return this.logMessage;
	}

	public void setLogMessage(final String logMessage) {
		this.logMessage = logMessage;
	}

}
