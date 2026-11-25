package org.catrobat.catroid.scratchconverter.protocol;

import org.catrobat.catroid.scratchconverter.Client;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/scratchconverter/protocol/MessageListener.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public interface MessageListener {
    int getNumberOfJobsInProgress();

    boolean isJobInProgress(long jobID);

    void onUserCanceledConversion(long jobID);

    Client.ProjectDownloadCallback restoreJobIfRunning(Job job, Client.ConvertCallback callback);

    boolean scheduleJob(Job job, boolean force, Client.ConvertCallback callback);

    void setBaseMessageHandler(BaseMessageHandler baseMessageHandler);
}
