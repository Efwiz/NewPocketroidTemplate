package org.catrobat.catroid.ui.scratchconverter;

import org.catrobat.catroid.scratchconverter.protocol.Job;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/scratchconverter/BaseInfoViewListener.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public interface BaseInfoViewListener {
    void onError(String errorMessage);

    void onJobsInfo(Job[] jobs);
}
