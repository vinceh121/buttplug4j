package org.metafetish.buttplug.client;

import org.metafetish.buttplug.core.messages.Error;

public interface IErrorEvent {
	void errorReceived(Error err);
}
