package org.catrobat.catroid.devices.mindstorms.nxt;

import org.catrobat.catroid.bluetooth.base.BluetoothDevice;
import org.catrobat.catroid.devices.mindstorms.LegoSensor;
import org.catrobat.catroid.devices.mindstorms.Mindstorms;
import org.catrobat.catroid.formulaeditor.Sensors;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/devices/mindstorms/nxt/LegoNXT.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public interface LegoNXT extends Mindstorms, BluetoothDevice {
    int getBatteryLevel();

    int getKeepAliveTime();

    NXTMotor getMotorA();

    NXTMotor getMotorB();

    NXTMotor getMotorC();

    LegoSensor getSensor1();

    LegoSensor getSensor2();

    LegoSensor getSensor3();

    LegoSensor getSensor4();

    float getSensorValue(Sensors sensor);

    void playTone(int frequency, int duration);

    void stopAllMovements();
}
