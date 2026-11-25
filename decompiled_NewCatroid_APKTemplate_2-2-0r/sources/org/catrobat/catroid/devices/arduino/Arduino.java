package org.catrobat.catroid.devices.arduino;

import org.catrobat.catroid.bluetooth.base.BluetoothDevice;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/devices/arduino/Arduino.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public interface Arduino extends BluetoothDevice {
    double getAnalogArduinoPin(int analogPinNumber);

    double getDigitalArduinoPin(int digitalPinNumber);

    void setAnalogArduinoPin(int pin, int value);

    void setDigitalArduinoPin(int digitalPinNumber, int pinValue);
}
