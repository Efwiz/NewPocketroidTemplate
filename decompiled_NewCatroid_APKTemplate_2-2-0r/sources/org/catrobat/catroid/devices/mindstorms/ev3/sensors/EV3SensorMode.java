package org.catrobat.catroid.devices.mindstorms.ev3.sensors;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/devices/mindstorms/ev3/sensors/EV3SensorMode.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public enum EV3SensorMode {
    MODE0(0),
    MODE1(1),
    MODE2(2),
    MODE3(3),
    MODE4(4),
    MODE5(5),
    MODE6(6),
    MODE7(7);

    private int sensorModeValue;

    EV3SensorMode(int sensorModeValue) {
        this.sensorModeValue = sensorModeValue;
    }

    public byte getByte() {
        return (byte) this.sensorModeValue;
    }
}
