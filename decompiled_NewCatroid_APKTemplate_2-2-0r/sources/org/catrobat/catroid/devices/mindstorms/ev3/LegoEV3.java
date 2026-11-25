package org.catrobat.catroid.devices.mindstorms.ev3;

import org.catrobat.catroid.bluetooth.base.BluetoothDevice;
import org.catrobat.catroid.devices.mindstorms.LegoSensor;
import org.catrobat.catroid.devices.mindstorms.Mindstorms;
import org.catrobat.catroid.formulaeditor.Sensors;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/devices/mindstorms/ev3/LegoEV3.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public interface LegoEV3 extends Mindstorms, BluetoothDevice {
    EV3Motor getMotorA();

    EV3Motor getMotorB();

    EV3Motor getMotorC();

    EV3Motor getMotorD();

    LegoSensor getSensor1();

    LegoSensor getSensor2();

    LegoSensor getSensor3();

    LegoSensor getSensor4();

    float getSensorValue(Sensors sensor);

    @Override // org.catrobat.catroid.bluetooth.base.BluetoothDevice
    boolean isAlive();

    void moveMotorSpeed(byte outputField, int chainLayer, int speed);

    void moveMotorStepsSpeed(byte outputField, int chainLayer, int speed, int step1Tacho, int step2Tacho, int step3Tacho, boolean brake);

    void playTone(int frequency, int duration, int volumeInPercent);

    void setLed(int ledStatus);

    void stopAllMovements();

    void stopMotor(byte outputField, int chainLayer, boolean brake);
}
