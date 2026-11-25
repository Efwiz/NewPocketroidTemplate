package org.catrobat.catroid.devices.mindstorms.nxt.sensors;

import org.catrobat.catroid.devices.mindstorms.MindstormsConnection;
import org.catrobat.catroid.devices.mindstorms.MindstormsException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/devices/mindstorms/nxt/sensors/NXTLightSensor.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class NXTLightSensor extends NXTSensor {
    private static final int DEFAULT_VALUE = 50;
    public static final String TAG = "NXTLightSensor";

    public NXTLightSensor(int port, MindstormsConnection connection) {
        super(port, NXTSensorType.LIGHT_INACTIVE, NXTSensorMode.Percent, connection);
        this.lastValidValue = 50.0f;
    }

    @Override // org.catrobat.catroid.devices.mindstorms.LegoSensor
    public float getValue() throws MindstormsException {
        return getScaledValue();
    }
}
