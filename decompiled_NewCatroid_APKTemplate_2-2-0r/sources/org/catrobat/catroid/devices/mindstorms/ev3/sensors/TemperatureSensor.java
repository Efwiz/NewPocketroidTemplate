package org.catrobat.catroid.devices.mindstorms.ev3.sensors;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import org.catrobat.catroid.devices.mindstorms.MindstormsConnection;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/devices/mindstorms/ev3/sensors/TemperatureSensor.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class TemperatureSensor extends EV3Sensor {
    private static final int DEFAULT_VALUE = 0;
    private static final int SENSOR_VALUE_READ_LENGTH = 4;
    public static final String TAG = "TemperatureSensor";

    public TemperatureSensor(int port, MindstormsConnection connection, EV3SensorMode mode) {
        super(port, EV3SensorType.NXT_TEMPERATURE, mode, connection);
        this.lastValidValue = 0.0f;
    }

    @Override // org.catrobat.catroid.devices.mindstorms.LegoSensor
    public float getValue() {
        return ByteBuffer.wrap(getSiValue(4)).order(ByteOrder.LITTLE_ENDIAN).getFloat();
    }
}
