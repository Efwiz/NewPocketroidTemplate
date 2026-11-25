package org.catrobat.catroid.scratchconverter.protocol.message.job;

import java.util.Arrays;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/scratchconverter/protocol/message/job/JobOutputMessage.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class JobOutputMessage extends JobMessage {
    private final String[] lines;

    public JobOutputMessage(final long jobID, final String[] lines) {
        super(jobID);
        this.lines = lines != null ? (String[]) Arrays.copyOf(lines, lines.length) : new String[0];
    }

    public String[] getLines() {
        String[] strArr = this.lines;
        return (String[]) Arrays.copyOf(strArr, strArr.length);
    }
}
