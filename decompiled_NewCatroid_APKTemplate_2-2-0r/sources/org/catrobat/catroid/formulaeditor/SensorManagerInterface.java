package org.catrobat.catroid.formulaeditor;

import android.hardware.Sensor;
import android.hardware.SensorEventListener;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/formulaeditor/SensorManagerInterface.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public interface SensorManagerInterface {
    Sensor getDefaultSensor(int typeLinearAcceleration);

    boolean registerListener(SensorEventListener listener, Sensor sensor, int rate);

    boolean registerListener(SensorCustomEventListener listener, Sensors sensor);

    void unregisterListener(SensorEventListener listener);

    void unregisterListener(SensorCustomEventListener listener);
}
