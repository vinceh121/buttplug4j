package org.metafetish.buttplug.core.Messages;

import org.metafetish.buttplug.core.ButtplugConsts;
import org.metafetish.buttplug.core.ButtplugMessage;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Error extends ButtplugMessage {

	@JsonProperty(value = "ErrorCode", required = true)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private ErrorType errorCode;
	@JsonProperty(value = "ErrorMessage", required = true)
	private String errorMessage;

	public Error() {
		this("", ErrorType.ERROR_UNKNOWN, ButtplugConsts.DefaultMsgId);
	}

	public Error(final String errorMessage, final ErrorType errorCode, final long id) {
		super(id);
		this.errorMessage = errorMessage;
		this.errorCode = errorCode;
	}

	public ErrorType getErrorCode() {
		return this.errorCode;
	}

	public void setErrorCode(final ErrorType errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return this.errorMessage;
	}

	public void setErrorMessage(final String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public enum ErrorType {
		ERROR_UNKNOWN, ERROR_INIT, ERROR_PING, ERROR_MSG, ERROR_DEVICE,
	}
}
