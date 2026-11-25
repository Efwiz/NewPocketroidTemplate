package org.catrobat.catroid.devices.mindstorms.ev3.sensors;

import org.catrobat.catroid.devices.mindstorms.MindstormsConnection;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/devices/mindstorms/ev3/sensors/EV3TouchSensor.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class EV3TouchSensor extends EV3Sensor {
    private static final int DEFAULT_VALUE = 0;
    public static final String TAG = "EV3TouchSensor";

    public EV3TouchSensor(int port, MindstormsConnection connection) {
        super(port, EV3SensorType.EV3_TOUCH, EV3SensorMode.MODE0, connection);
        this.lastValidValue = 0.0f;
    }

    @Override // org.catrobat.catroid.devices.mindstorms.LegoSensor
    public float getValue() {
        return getPercentValue() > 50 ? 1.0f : 0.0f;
    }
}
