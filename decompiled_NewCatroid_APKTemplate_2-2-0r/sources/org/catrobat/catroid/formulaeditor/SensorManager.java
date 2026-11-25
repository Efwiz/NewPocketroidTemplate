package org.catrobat.catroid.formulaeditor;

import android.hardware.Sensor;
import android.hardware.SensorEventListener;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/formulaeditor/SensorManager.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SensorManager implements SensorManagerInterface {
    private final android.hardware.SensorManager sensorManager;

    @Override // org.catrobat.catroid.formulaeditor.SensorManagerInterface
    public boolean registerListener(SensorCustomEventListener listener, Sensors sensor) {
        return false;
    }

    @Override // org.catrobat.catroid.formulaeditor.SensorManagerInterface
    public void unregisterListener(SensorCustomEventListener listener) {
    }

    public SensorManager(android.hardware.SensorManager sensorManager) {
        this.sensorManager = sensorManager;
    }

    @Override // org.catrobat.catroid.formulaeditor.SensorManagerInterface
    public void unregisterListener(SensorEventListener listener) {
        this.sensorManager.unregisterListener(listener);
    }

    @Override // org.catrobat.catroid.formulaeditor.SensorManagerInterface
    public boolean registerListener(SensorEventListener listener, Sensor sensor, int rate) {
        return this.sensorManager.registerListener(listener, sensor, rate);
    }

    @Override // org.catrobat.catroid.formulaeditor.SensorManagerInterface
    public Sensor getDefaultSensor(int type) {
        return this.sensorManager.getDefaultSensor(type);
    }
}
