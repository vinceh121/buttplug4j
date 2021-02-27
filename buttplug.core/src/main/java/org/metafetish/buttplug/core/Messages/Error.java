package org.metafetish.buttplug.core.Messages;

import org.metafetish.buttplug.core.ButtplugConsts;
import org.metafetish.buttplug.core.ButtplugMessage;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Error extends ButtplugMessage {

	@JsonProperty(value = "ErrorCode", required = true)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	public ErrorClass errorCode;
	@JsonProperty(value = "ErrorMessage", required = true)
	public String errorMessage;

	public Error(final String errorMessage, final ErrorClass errorCode, final long id) {
		super(id);
		this.errorMessage = errorMessage;
		this.errorCode = errorCode;
	}

	@SuppressWarnings("unused")
	private Error() {
		super(ButtplugConsts.DefaultMsgId);
		this.errorMessage = "";
		this.errorCode = ErrorClass.ERROR_UNKNOWN;
	}

	public enum ErrorClass {
		ERROR_UNKNOWN, ERROR_INIT, ERROR_PING, ERROR_MSG, ERROR_DEVICE,
	}
}
