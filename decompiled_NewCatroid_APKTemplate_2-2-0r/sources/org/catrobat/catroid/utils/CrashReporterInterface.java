package org.catrobat.catroid.utils;

import android.content.Context;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/CrashReporterInterface.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public interface CrashReporterInterface {
    void initialize(Context context);

    void logException(Throwable exception);
}
