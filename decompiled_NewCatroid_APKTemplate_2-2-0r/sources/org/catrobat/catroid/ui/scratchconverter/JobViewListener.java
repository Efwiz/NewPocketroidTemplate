package org.catrobat.catroid.ui.scratchconverter;

import org.catrobat.catroid.scratchconverter.protocol.Job;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/scratchconverter/JobViewListener.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public interface JobViewListener {
    void onJobFailed(Job job);

    void onJobFinished(Job job);

    void onJobOutput(Job job, String[] lines);

    void onJobProgress(Job job, short progress);

    void onJobReady(Job job);

    void onJobScheduled(Job job);

    void onJobStarted(Job job);

    void onUserCanceledJob(Job job);
}
