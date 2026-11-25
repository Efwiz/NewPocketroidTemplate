package org.catrobat.catroid.devices.arduino.phiro;

import android.util.Log;
import name.antonsmirnov.firmata.IFirmata;
import name.antonsmirnov.firmata.message.AnalogMessage;
import name.antonsmirnov.firmata.message.DigitalMessage;
import name.antonsmirnov.firmata.message.FirmwareVersionMessage;
import name.antonsmirnov.firmata.message.I2cReplyMessage;
import name.antonsmirnov.firmata.message.ProtocolVersionMessage;
import name.antonsmirnov.firmata.message.StringSysexMessage;
import name.antonsmirnov.firmata.message.SysexMessage;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/devices/arduino/phiro/PhiroListener.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
class PhiroListener implements IFirmata.Listener {
    private static final String TAG = "PhiroListener";
    private int frontLeftSensor = 0;
    private int frontRightSensor = 0;
    private int sideLeftSensor = 0;
    private int sideRightSensor = 0;
    private int bottomLeftSensor = 0;
    private int bottomRightSensor = 0;

    @Override // name.antonsmirnov.firmata.IFirmata.Listener
    public void onUnknownByteReceived(int byteValue) {
    }

    PhiroListener() {
    }

    @Override // name.antonsmirnov.firmata.IFirmata.Listener
    public void onAnalogMessageReceived(AnalogMessage message) {
        if (message.getValue() > 1023 || message.getValue() < 0) {
            return;
        }
        int pin = message.getPin();
        if (pin == 0) {
            this.sideRightSensor = message.getValue();
            return;
        }
        if (pin == 1) {
            this.frontRightSensor = message.getValue();
            return;
        }
        if (pin == 2) {
            this.bottomRightSensor = message.getValue();
            return;
        }
        if (pin == 3) {
            this.bottomLeftSensor = message.getValue();
        } else if (pin == 4) {
            this.frontLeftSensor = message.getValue();
        } else {
            if (pin != 5) {
                return;
            }
            this.sideLeftSensor = message.getValue();
        }
    }

    @Override // name.antonsmirnov.firmata.IFirmata.Listener
    public void onDigitalMessageReceived(DigitalMessage message) {
        Log.d(TAG, String.format("Received Digital Message: pin: %d, value: %d", Integer.valueOf(message.getPort()), Integer.valueOf(message.getValue())));
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

    public int getFrontLeftSensor() {
        return this.frontLeftSensor;
    }

    public int getFrontRightSensor() {
        return this.frontRightSensor;
    }

    public int getSideLeftSensor() {
        return this.sideLeftSensor;
    }

    public int getSideRightSensor() {
        return this.sideRightSensor;
    }

    public int getBottomLeftSensor() {
        return this.bottomLeftSensor;
    }

    public int getBottomRightSensor() {
        return this.bottomRightSensor;
    }
}
