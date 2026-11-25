package org.catrobat.catroid.devices.mindstorms.nxt.sensors;

import org.catrobat.catroid.devices.mindstorms.MindstormsConnection;
import org.catrobat.catroid.devices.mindstorms.MindstormsException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/devices/mindstorms/nxt/sensors/NXTTouchSensor.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class NXTTouchSensor extends NXTSensor {
    private static final int DEFAULT_VALUE = 0;
    public static final String TAG = NXTLightSensor.TAG;

    public NXTTouchSensor(int port, MindstormsConnection connection) {
        super(port, NXTSensorType.TOUCH, NXTSensorMode.BOOL, connection);
        this.lastValidValue = 0.0f;
    }

    @Override // org.catrobat.catroid.devices.mindstorms.LegoSensor
    public float getValue() throws MindstormsException {
        return getScaledValue();
    }
}
