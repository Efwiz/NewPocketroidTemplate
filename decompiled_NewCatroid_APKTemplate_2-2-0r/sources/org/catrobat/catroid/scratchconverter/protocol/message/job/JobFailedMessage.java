package org.catrobat.catroid.scratchconverter.protocol.message.job;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/scratchconverter/protocol/message/job/JobFailedMessage.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class JobFailedMessage extends JobMessage {
    private final String message;

    public JobFailedMessage(final long jobID, final String message) {
        super(jobID);
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
