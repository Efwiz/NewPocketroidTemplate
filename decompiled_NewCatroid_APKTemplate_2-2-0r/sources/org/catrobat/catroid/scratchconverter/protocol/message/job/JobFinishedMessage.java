package org.catrobat.catroid.scratchconverter.protocol.message.job;

import java.util.Date;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/scratchconverter/protocol/message/job/JobFinishedMessage.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class JobFinishedMessage extends JobMessage {
    private final Date cachedDate;
    private final String downloadURL;

    public JobFinishedMessage(final long jobID, final String downloadURL, final Date cachedDate) {
        super(jobID);
        this.downloadURL = downloadURL;
        this.cachedDate = cachedDate;
    }

    public String getDownloadURL() {
        return this.downloadURL;
    }

    public Date getCachedDate() {
        return this.cachedDate;
    }
}
