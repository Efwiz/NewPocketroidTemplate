package org.catrobat.catroid.devices.mindstorms.ev3.sensors;

import org.catrobat.catroid.devices.mindstorms.MindstormsConnection;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/devices/mindstorms/ev3/sensors/HiTechnicColorSensor.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class HiTechnicColorSensor extends EV3Sensor {
    private static final int DEFAULT_VALUE = 0;
    private static final int SENSOR_VALUE_READ_LENGTH = 1;
    public static final String TAG = "HiTechnicColorSensor";

    public HiTechnicColorSensor(int port, MindstormsConnection connection, EV3SensorMode mode) {
        super(port, EV3SensorType.IIC, mode, connection);
        this.lastValidValue = 0.0f;
    }

    @Override // org.catrobat.catroid.devices.mindstorms.LegoSensor
    public float getValue() {
        return getRawValue(1)[0] & 255;
    }
}
