package org.catrobat.catroid.utils;

import java.io.IOException;
import java.io.OutputStream;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/DiskCacheEntryEditorInterface.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public interface DiskCacheEntryEditorInterface {
    void abort() throws IOException;

    void commit() throws IOException;

    OutputStream newOutputStream(int index) throws IOException;
}
