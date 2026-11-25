package org.catrobat.catroid.devices.arduino;

import android.util.Log;
import name.antonsmirnov.firmata.IFirmata;
import name.antonsmirnov.firmata.message.AnalogMessage;
import name.antonsmirnov.firmata.message.DigitalMessage;
import name.antonsmirnov.firmata.message.FirmwareVersionMessage;
import name.antonsmirnov.firmata.message.I2cReplyMessage;
import name.antonsmirnov.firmata.message.ProtocolVersionMessage;
import name.antonsmirnov.firmata.message.StringSysexMessage;
import name.antonsmirnov.firmata.message.SysexMessage;
import org.catrobat.catroid.utils.Utils;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/devices/arduino/ArduinoListener.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ArduinoListener implements IFirmata.Listener {
    private static final String TAG = "ArduinoListener";
    private int[] analogPinValue = new int[6];
    private int[] portValue = new int[2];

    @Override // name.antonsmirnov.firmata.IFirmata.Listener
    public void onUnknownByteReceived(int byteValue) {
    }

    @Override // name.antonsmirnov.firmata.IFirmata.Listener
    public void onAnalogMessageReceived(AnalogMessage message) {
        if (message.getValue() > 1023 || message.getValue() < 0) {
            return;
        }
        Log.d(TAG, String.format("Received Analog Message: %d | Value: %d", Integer.valueOf(message.getPin()), Integer.valueOf(message.getValue())));
        this.analogPinValue[message.getPin()] = message.getValue();
    }

    @Override // name.antonsmirnov.firmata.IFirmata.Listener
    public void onDigitalMessageReceived(DigitalMessage message) {
        if (message.getValue() > 255 || message.getValue() < 0) {
            return;
        }
        Log.d(TAG, String.format("Received Digital Message: port: %d, value: %d", Integer.valueOf(message.getPort()), Integer.valueOf(message.getValue())));
        this.portValue[message.getPort()] = message.getValue();
        for (int i = 0; i < 14; i++) {
            Log.d(TAG, String.format("Digital Pin %d Value: %d", Integer.valueOf(i), Integer.valueOf(getDigitalPinValue(i))));
        }
    }

    @Override // name.antonsmirnov.firmata.IFirmata.Listener
    public void onFirmwareVersionMessageReceived(FirmwareVersionMessage message) {
        Log.d(TAG, String.format("Received Firmware Version Message: Name: %s, Version Major: %d, Minor: %d", message.getName(), Integer.valueOf(message.getMajor()), Integer.valueOf(message.getMinor())));
    }

    @Override // name.antonsmirnov.firmata.IFirmata.Listener
    public void onProtocolVersionMessageReceived(ProtocolVersionMessage message) {
        Log.d(TAG, String.format("Received Protocol Version Message: Version Major: %d, Minor: %d", Integer.valueOf(message.getMajor()), Integer.valueOf(message.getMinor())));
    }

    @Override // name.antonsmirnov.firmata.IFirmata.Listener
    public void onSysexMessageReceived(SysexMessage message) {
        Log.d(TAG, "Sysex Message received: " + message.getCommand());
    }

    @Override // name.antonsmirnov.firmata.IFirmata.Listener
    public void onStringSysexMessageReceived(StringSysexMessage message) {
        Log.d(TAG, "String Sysex Message received: " + message.getCommand());
    }

    @Override // name.antonsmirnov.firmata.IFirmata.Listener
    public void onI2cMessageReceived(I2cReplyMessage message) {
        Log.d(TAG, "I2C Message received: " + message.getCommand());
    }

    public int getAnalogPinValue(int pin) {
        if (ArduinoImpl.isValidAnalogPin(pin)) {
            return this.analogPinValue[pin];
        }
        return 0;
    }

    public int getDigitalPinValue(int pin) {
        if (!ArduinoImpl.isValidDigitalPin(pin)) {
            return 0;
        }
        int portFromPin = ArduinoImpl.getPortFromPin(pin);
        return Utils.getBit(this.portValue[portFromPin], ArduinoImpl.getIndexOfPinOnPort(pin));
    }

    public void setDigitalPinValue(int pin, int value) {
        if (ArduinoImpl.isValidDigitalPin(pin)) {
            int portFromPin = ArduinoImpl.getPortFromPin(pin);
            int indexOfPinOnPort = ArduinoImpl.getIndexOfPinOnPort(pin);
            int[] iArr = this.portValue;
            iArr[portFromPin] = Utils.setBit(iArr[portFromPin], indexOfPinOnPort, value);
        }
    }

    public int getPortValue(int port) {
        return this.portValue[port];
    }
}
