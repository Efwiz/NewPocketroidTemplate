package org.catrobat.catroid.scratchconverter.protocol.message.base;

import java.util.Arrays;
import org.catrobat.catroid.scratchconverter.protocol.Job;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/scratchconverter/protocol/message/base/InfoMessage.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class InfoMessage extends BaseMessage {
    private final double catrobatLanguageVersion;
    private final Job[] jobList;

    public InfoMessage(final double catrobatLanguageVersion, final Job[] jobList) {
        this.catrobatLanguageVersion = catrobatLanguageVersion;
        this.jobList = jobList != null ? (Job[]) Arrays.copyOf(jobList, jobList.length) : new Job[0];
    }

    public double getCatrobatLanguageVersion() {
        return this.catrobatLanguageVersion;
    }

    public Job[] getJobList() {
        Job[] jobArr = this.jobList;
        return (Job[]) Arrays.copyOf(jobArr, jobArr.length);
    }
}
