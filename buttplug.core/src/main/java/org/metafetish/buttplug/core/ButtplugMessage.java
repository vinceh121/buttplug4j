package org.metafetish.buttplug.core;

import org.metafetish.buttplug.core.messages.DeviceAdded;
import org.metafetish.buttplug.core.messages.DeviceList;
import org.metafetish.buttplug.core.messages.DeviceRemoved;
import org.metafetish.buttplug.core.messages.Error;
import org.metafetish.buttplug.core.messages.LinearCmd;
import org.metafetish.buttplug.core.messages.Ok;
import org.metafetish.buttplug.core.messages.Ping;
import org.metafetish.buttplug.core.messages.RequestDeviceList;
import org.metafetish.buttplug.core.messages.RequestServerInfo;
import org.metafetish.buttplug.core.messages.RotateCmd;
import org.metafetish.buttplug.core.messages.ScanningFinished;
import org.metafetish.buttplug.core.messages.ServerInfo;
import org.metafetish.buttplug.core.messages.StartScanning;
import org.metafetish.buttplug.core.messages.StopAllDevices;
import org.metafetish.buttplug.core.messages.StopDeviceCmd;
import org.metafetish.buttplug.core.messages.StopScanning;
import org.metafetish.buttplug.core.messages.VibrateCmd;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

@JsonTypeInfo(include = As.WRAPPER_OBJECT, use = Id.NAME)
@JsonSubTypes({ @JsonSubTypes.Type(value = Ok.class, name = "Ok"),
		@JsonSubTypes.Type(value = Ping.class, name = "Ping"),
		@JsonSubTypes.Type(value = StartScanning.class, name = "StartScanning"),
		@JsonSubTypes.Type(value = StopScanning.class, name = "StopScanning"),
		@JsonSubTypes.Type(value = ScanningFinished.class, name = "ScanningFinished"),
		@JsonSubTypes.Type(value = RequestDeviceList.class, name = "RequestDeviceList"),
		// @JsonSubTypes.Type(value = RequestLog.class, name = "RequestLog"),
		@JsonSubTypes.Type(value = RequestServerInfo.class, name = "RequestServerInfo"),
		@JsonSubTypes.Type(value = ServerInfo.class, name = "ServerInfo"),
		// @JsonSubTypes.Type(value = Log.class, name = "Log"),
		@JsonSubTypes.Type(value = Error.class, name = "Error"),
		// @JsonSubTypes.Type(value = Test.class, name = "Test"),
		@JsonSubTypes.Type(value = DeviceAdded.class, name = "DeviceAdded"),
		@JsonSubTypes.Type(value = DeviceList.class, name = "DeviceList"),
		@JsonSubTypes.Type(value = DeviceRemoved.class, name = "DeviceRemoved"),
		@JsonSubTypes.Type(value = StopAllDevices.class, name = "StopAllDevices"),
		@JsonSubTypes.Type(value = StopDeviceCmd.class, name = "StopDeviceCmd"),
		@JsonSubTypes.Type(value = VibrateCmd.class, name = "VibrateCmd"),
		@JsonSubTypes.Type(value = LinearCmd.class, name = "LinearCmd"),
		@JsonSubTypes.Type(value = RotateCmd.class, name = "RotateCmd"),
// @JsonSubTypes.Type(value = VorzeA10CycloneCmd.class, name =
// "VorzeA10CycloneCmd"),
// @JsonSubTypes.Type(value = FleshlightLaunchFW12Cmd.class, name =
// "FleshlightLaunchFW12Cmd"),
// @JsonSubTypes.Type(value = LovenseCmd.class, name = "LovenseCmd"),
// @JsonSubTypes.Type(value = KiirooCmd.class, name = "KiirooCmd"),
// @JsonSubTypes.Type(value = SingleMotorVibrateCmd.class, name =
// "SingleMotorVibrateCmd")
})
public abstract class ButtplugMessage {

	@JsonProperty(value = "Id", required = true)
	private long id;

	public ButtplugMessage(final long id) {
		this.id = id;
	}

	public long getId() {
		return this.id;
	}

	public void setId(final long id) {
		this.id = id;
	}
}
