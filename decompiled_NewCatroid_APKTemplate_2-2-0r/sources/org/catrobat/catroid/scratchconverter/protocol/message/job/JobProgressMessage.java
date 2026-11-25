package org.catrobat.catroid.scratchconverter.protocol.message.job;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/scratchconverter/protocol/message/job/JobProgressMessage.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class JobProgressMessage extends JobMessage {
    private final short progress;

    public JobProgressMessage(final long jobID, final short progress) {
        super(jobID);
        this.progress = progress;
    }

    public short getProgress() {
        return this.progress;
    }
}
