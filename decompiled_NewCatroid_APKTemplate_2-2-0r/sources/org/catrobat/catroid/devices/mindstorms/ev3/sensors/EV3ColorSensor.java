package org.catrobat.catroid.devices.mindstorms.ev3.sensors;

import org.catrobat.catroid.devices.mindstorms.MindstormsConnection;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/devices/mindstorms/ev3/sensors/EV3ColorSensor.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class EV3ColorSensor extends EV3Sensor {
    private static final int DEFAULT_VALUE = 50;
    private static final int SENSOR_VALUE_READ_LENGTH = 1;
    public static final String TAG = "EV3ColorSensor";

    public EV3ColorSensor(int port, MindstormsConnection connection, EV3SensorMode mode) {
        super(port, EV3SensorType.EV3_COLOR, mode, connection);
        this.lastValidValue = 50.0f;
    }

    @Override // org.catrobat.catroid.devices.mindstorms.LegoSensor
    public float getValue() {
        int percentValue;
        if (this.sensorMode == EV3SensorMode.MODE2) {
            percentValue = getRawValue(1)[0] & 255;
        } else {
            percentValue = getPercentValue();
        }
        return percentValue;
    }
}
