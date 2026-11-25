package org.catrobat.catroid.devices.mindstorms;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/devices/mindstorms/MindstormsMotor.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public interface MindstormsMotor {
    void move(int speed);

    void move(int speed, int degrees);

    void move(int speed, int degrees, boolean reply);

    void stop();
}
