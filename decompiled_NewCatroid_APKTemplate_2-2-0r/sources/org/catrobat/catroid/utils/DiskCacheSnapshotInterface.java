package org.catrobat.catroid.utils;

import java.io.InputStream;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/DiskCacheSnapshotInterface.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public interface DiskCacheSnapshotInterface {
    void close();

    InputStream getInputStream(int index);
}
