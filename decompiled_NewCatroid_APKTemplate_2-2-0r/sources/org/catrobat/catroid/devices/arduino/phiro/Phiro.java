package org.catrobat.catroid.devices.arduino.phiro;

import org.catrobat.catroid.bluetooth.base.BluetoothDevice;
import org.catrobat.catroid.formulaeditor.Sensors;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/devices/arduino/phiro/Phiro.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public interface Phiro extends BluetoothDevice {
    int getSensorValue(Sensors sensor);

    void moveLeftMotorBackward(int speed);

    void moveLeftMotorForward(int speed);

    void moveRightMotorBackward(int speed);

    void moveRightMotorForward(int speed);

    void playTone(int toneFrequency, int duration);

    void reportFirmwareVersion();

    void setLeftRGBLightColor(int red, int green, int blue);

    void setRightRGBLightColor(int red, int green, int blue);

    void stopAllMovements();

    void stopLeftMotor();

    void stopRightMotor();
}
