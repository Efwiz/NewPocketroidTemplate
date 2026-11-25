package org.catrobat.catroid.devices.mindstorms.nxt.sensors;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/devices/mindstorms/nxt/sensors/NXTSensorMode.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public enum NXTSensorMode {
    RAW(0),
    BOOL(32),
    Percent(128);

    private int sensorModeValue;

    NXTSensorMode(int sensorModeValue) {
        this.sensorModeValue = sensorModeValue;
    }

    public byte getByte() {
        return (byte) this.sensorModeValue;
    }
}
