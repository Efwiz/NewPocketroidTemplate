package org.catrobat.catroid.utils;

import java.io.File;
import java.io.IOException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/DiskCacheBackendInterface.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public interface DiskCacheBackendInterface {
    void delete() throws IOException;

    DiskCacheEntryEditorInterface edit(String key) throws IOException;

    DiskCacheSnapshotInterface get(String key) throws IOException;

    File getDirectory();

    long maxSize();

    boolean remove(String key) throws IOException;
}
