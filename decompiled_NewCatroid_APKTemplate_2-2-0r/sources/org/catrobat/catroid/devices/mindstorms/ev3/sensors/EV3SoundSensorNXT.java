package org.catrobat.catroid.devices.mindstorms.ev3.sensors;

import org.catrobat.catroid.devices.mindstorms.MindstormsConnection;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/devices/mindstorms/ev3/sensors/EV3SoundSensorNXT.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class EV3SoundSensorNXT extends EV3Sensor {
    private static final int DEFAULT_VALUE = 0;

    public EV3SoundSensorNXT(int port, MindstormsConnection connection, EV3SensorMode mode) {
        super(port, EV3SensorType.NXT_SOUND, mode, connection);
        this.lastValidValue = 0.0f;
    }

    @Override // org.catrobat.catroid.devices.mindstorms.LegoSensor
    public float getValue() {
        return getPercentValue();
    }
}
