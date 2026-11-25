package org.catrobat.catroid.scratchconverter.protocol.message.job;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/scratchconverter/protocol/message/job/JobRunningMessage.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class JobRunningMessage extends JobMessage {
    private final String jobImageURL;
    private final String jobTitle;

    public JobRunningMessage(final long jobID, final String jobTitle, final String jobImageURL) {
        super(jobID);
        this.jobTitle = jobTitle;
        this.jobImageURL = jobImageURL;
    }

    public String getJobTitle() {
        return this.jobTitle;
    }

    public String getJobImageURL() {
        return this.jobImageURL;
    }
}
