package org.catrobat.catroid.stage;

import org.catrobat.catroid.devices.mindstorms.MindstormsException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/stage/StageResourceInterface.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public interface StageResourceInterface {
    void destroy();

    void initialise() throws MindstormsException;

    void pause();

    void start() throws MindstormsException;
}
