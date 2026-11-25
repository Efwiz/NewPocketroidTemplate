package org.catrobat.catroid.devices.mindstorms.ev3.sensors;

import org.catrobat.catroid.devices.mindstorms.MindstormsConnection;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/devices/mindstorms/ev3/sensors/EV3InfraredSensor.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class EV3InfraredSensor extends EV3Sensor {
    private static final int DEFAULT_VALUE = 50;
    public static final String TAG = "EV3InfraredSensor";

    public EV3InfraredSensor(int port, MindstormsConnection connection) {
        super(port, EV3SensorType.EV3_INFRARED, EV3SensorMode.MODE0, connection);
        this.lastValidValue = 50.0f;
    }

    @Override // org.catrobat.catroid.devices.mindstorms.LegoSensor
    public float getValue() {
        return getPercentValue();
    }
}
