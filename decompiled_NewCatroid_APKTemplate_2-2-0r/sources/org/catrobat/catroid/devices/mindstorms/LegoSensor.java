package org.catrobat.catroid.devices.mindstorms;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/devices/mindstorms/LegoSensor.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public interface LegoSensor {
    int getConnectedPort();

    float getLastSensorValue();

    String getName();

    int getUpdateInterval();

    float getValue() throws MindstormsException;

    void updateLastSensorValue();
}
