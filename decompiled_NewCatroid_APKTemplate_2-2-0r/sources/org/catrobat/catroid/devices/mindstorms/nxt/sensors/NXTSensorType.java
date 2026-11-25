package org.catrobat.catroid.devices.mindstorms.nxt.sensors;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/devices/mindstorms/nxt/sensors/NXTSensorType.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public enum NXTSensorType {
    NO_SENSOR(0),
    TOUCH(1),
    TEMPERATURE(2),
    LIGHT_ACTIVE(5),
    LIGHT_INACTIVE(6),
    SOUND_DB(7),
    SOUND_DBA(8),
    LOW_SPEED(10),
    LOW_SPEED_9V(11);

    private int sensorTypeValue;

    NXTSensorType(int sensorTypeValue) {
        this.sensorTypeValue = sensorTypeValue;
    }

    public byte getByte() {
        return (byte) this.sensorTypeValue;
    }
}
