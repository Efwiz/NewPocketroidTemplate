package org.catrobat.catroid.scratchconverter.protocol;

import android.net.Uri;
import android.util.Log;
import com.google.android.gms.common.images.WebImage;
import com.google.common.base.Preconditions;
import com.huawei.hms.ml.language.common.utils.Constant;
import org.catrobat.catroid.scratchconverter.Client;
import org.catrobat.catroid.scratchconverter.ClientException;
import org.catrobat.catroid.scratchconverter.protocol.message.job.JobAlreadyRunningMessage;
import org.catrobat.catroid.scratchconverter.protocol.message.job.JobFailedMessage;
import org.catrobat.catroid.scratchconverter.protocol.message.job.JobFinishedMessage;
import org.catrobat.catroid.scratchconverter.protocol.message.job.JobMessage;
import org.catrobat.catroid.scratchconverter.protocol.message.job.JobOutputMessage;
import org.catrobat.catroid.scratchconverter.protocol.message.job.JobProgressMessage;
import org.catrobat.catroid.scratchconverter.protocol.message.job.JobReadyMessage;
import org.catrobat.catroid.scratchconverter.protocol.message.job.JobRunningMessage;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/scratchconverter/protocol/JobHandler.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class JobHandler implements Client.ProjectDownloadCallback {
    private static final String TAG = "JobHandler";
    private Client.ConvertCallback callback;
    private final Job job;

    @Override // org.catrobat.catroid.scratchconverter.Client.ProjectDownloadCallback
    public void onDownloadProgress(int progress, String url) {
    }

    public JobHandler(final Job job, final Client.ConvertCallback callback) {
        Preconditions.checkArgument(job != null);
        this.job = job;
        this.callback = callback;
    }

    public boolean isInProgress() {
        return this.job.isInProgress();
    }

    public void onJobScheduled() {
        Log.d(TAG, "Setting job as scheduled (jobID: " + this.job.getJobID() + Constant.AFTER_QUTO);
        this.job.setState(Job.State.SCHEDULED);
        this.callback.onJobScheduled(this.job);
    }

    @Override // org.catrobat.catroid.scratchconverter.Client.ProjectDownloadCallback
    public void onDownloadStarted(final String url) {
        Log.d(TAG, "Download started - Job ID is: " + this.job.getJobID());
        this.job.setDownloadState(Job.DownloadState.DOWNLOADING);
        this.job.setState(Job.State.FINISHED);
    }

    @Override // org.catrobat.catroid.scratchconverter.Client.ProjectDownloadCallback
    public void onDownloadFinished(final String programName, final String url) {
        Log.d(TAG, "Download finished - Resetting job with ID: " + this.job.getJobID());
        this.job.setDownloadState(Job.DownloadState.DOWNLOADED);
        this.job.setState(Job.State.FINISHED);
    }

    @Override // org.catrobat.catroid.scratchconverter.Client.ProjectDownloadCallback
    public void onUserCanceledDownload(final String url) {
        Log.d(TAG, "User canceled download - Resetting job with ID: " + this.job.getJobID());
        this.job.setDownloadState(Job.DownloadState.CANCELED);
        this.job.setState(Job.State.FINISHED);
    }

    public void onUserCanceledConversion() {
        Log.d(TAG, "User canceled conversion - Resetting job with ID: " + this.job.getJobID());
        this.job.setState(Job.State.FINISHED);
    }

    public Job getJob() {
        return this.job;
    }

    public long getJobID() {
        return this.job.getJobID();
    }

    public void setCallback(Client.ConvertCallback callback) {
        this.callback = callback;
    }

    public void onJobMessage(final JobMessage jobMessage) {
        Preconditions.checkArgument(this.job.getJobID() == jobMessage.getJobID());
        Preconditions.checkState(this.job.getState().isInProgress());
        int i = AnonymousClass1.$SwitchMap$org$catrobat$catroid$scratchconverter$protocol$Job$State[this.job.getState().ordinal()];
        if (i != 1) {
            if (i != 2) {
                if (i == 3) {
                    if (jobMessage instanceof JobProgressMessage) {
                        handleJobProgressMessage((JobProgressMessage) jobMessage);
                        return;
                    }
                    if (jobMessage instanceof JobOutputMessage) {
                        handleJobOutputMessage((JobOutputMessage) jobMessage);
                        return;
                    } else if (jobMessage instanceof JobFinishedMessage) {
                        handleJobFinishedMessage((JobFinishedMessage) jobMessage);
                        return;
                    } else if (jobMessage instanceof JobFailedMessage) {
                        handleJobFailedMessage((JobFailedMessage) jobMessage);
                        return;
                    }
                }
            } else if (jobMessage instanceof JobRunningMessage) {
                handleJobRunningMessage((JobRunningMessage) jobMessage);
                return;
            }
        } else {
            if (jobMessage instanceof JobReadyMessage) {
                handleJobReadyMessage((JobReadyMessage) jobMessage);
                return;
            }
            if (jobMessage instanceof JobAlreadyRunningMessage) {
                handleJobAlreadyRunningMessage((JobAlreadyRunningMessage) jobMessage);
                return;
            } else if (jobMessage instanceof JobFinishedMessage) {
                handleJobFinishedMessage((JobFinishedMessage) jobMessage);
                return;
            } else if (jobMessage instanceof JobFailedMessage) {
                handleJobFailedMessage((JobFailedMessage) jobMessage);
                return;
            }
        }
        Log.w(TAG, "Unable to handle message of type in current state " + this.job.getState());
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/scratchconverter/protocol/JobHandler$1.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.scratchconverter.protocol.JobHandler$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$catrobat$catroid$scratchconverter$protocol$Job$State;

        static {
            int[] iArr = new int[Job.State.values().length];
            $SwitchMap$org$catrobat$catroid$scratchconverter$protocol$Job$State = iArr;
            try {
                iArr[Job.State.SCHEDULED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$scratchconverter$protocol$Job$State[Job.State.READY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$scratchconverter$protocol$Job$State[Job.State.RUNNING.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    private void handleJobReadyMessage(final JobReadyMessage jobReadyMessage) {
        Preconditions.checkArgument(getJob().getJobID() == jobReadyMessage.getJobID());
        Preconditions.checkState(this.job.getState() == Job.State.SCHEDULED);
        this.job.setState(Job.State.READY);
        this.callback.onConversionReady(this.job);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [kotlin.jvm.functions.Function0, long] */
    /* JADX WARN: Type inference failed for: r0v7, types: [kotlin.jvm.functions.Function0, org.catrobat.catroid.scratchconverter.protocol.Job] */
    /* JADX WARN: Type inference failed for: r0v8, types: [long, void] */
    /* JADX WARN: Type inference failed for: r2v0, types: [long, void] */
    private void handleJobAlreadyRunningMessage(final JobAlreadyRunningMessage jobAlreadyRunningMessage) {
        ?? jobID = getJob().getJobID();
        Preconditions.checkArgument(jobID == jobAlreadyRunningMessage.setEnabledChangedCallback$activity_release(jobID));
        Preconditions.checkState(this.job.getState() == Job.State.SCHEDULED);
        ?? r0 = this.job;
        r0.setState(Job.State.READY);
        handleJobRunningMessage(new JobRunningMessage(jobAlreadyRunningMessage.setEnabledChangedCallback$activity_release(r0), jobAlreadyRunningMessage.getJobTitle(), jobAlreadyRunningMessage.getJobImageURL()));
    }

    private void handleJobRunningMessage(final JobRunningMessage jobRunningMessage) {
        Preconditions.checkArgument(getJob().getJobID() == jobRunningMessage.getJobID());
        Preconditions.checkState(this.job.getState() == Job.State.READY);
        this.job.setTitle(jobRunningMessage.getJobTitle());
        String jobImageURL = jobRunningMessage.getJobImageURL();
        if (jobImageURL != null) {
            int[] iArr = {480, 360};
            this.job.setImage(new WebImage(Uri.parse(jobImageURL), iArr[0], iArr[1]));
        }
        this.job.setState(Job.State.RUNNING);
        this.callback.onConversionStart(this.job);
    }

    private void handleJobProgressMessage(final JobProgressMessage jobProgressMessage) {
        Preconditions.checkArgument(getJob().getJobID() == jobProgressMessage.getJobID());
        Preconditions.checkState(this.job.getState() == Job.State.RUNNING);
        this.job.setProgress(jobProgressMessage.getProgress());
        this.callback.onJobProgress(this.job, jobProgressMessage.getProgress());
    }

    private void handleJobOutputMessage(final JobOutputMessage jobOutputMessage) {
        Preconditions.checkArgument(getJob().getJobID() == jobOutputMessage.getJobID());
        Preconditions.checkState(this.job.getState() == Job.State.RUNNING);
        String[] lines = jobOutputMessage.getLines();
        for (String str : lines) {
            Log.d(TAG, str);
        }
        this.callback.onJobOutput(this.job, lines);
    }

    private void handleJobFinishedMessage(final JobFinishedMessage jobFinishedMessage) {
        boolean z = true;
        Preconditions.checkArgument(getJob().getJobID() == jobFinishedMessage.getJobID());
        if (this.job.getState() != Job.State.SCHEDULED && this.job.getState() != Job.State.RUNNING) {
            z = false;
        }
        Preconditions.checkState(z);
        this.job.setState(Job.State.FINISHED);
        this.job.setDownloadURL(jobFinishedMessage.getDownloadURL());
        this.callback.onConversionFinished(this.job, this, jobFinishedMessage.getDownloadURL(), jobFinishedMessage.getCachedDate());
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [androidx.activity.Cancellable, long] */
    /* JADX WARN: Type inference failed for: r2v0, types: [long, void] */
    private void handleJobFailedMessage(final JobFailedMessage jobFailedMessage) {
        ?? jobID = getJob().getJobID();
        boolean z = true;
        Preconditions.checkArgument(jobID == jobFailedMessage.removeCancellable(jobID));
        if (this.job.getState() != Job.State.SCHEDULED && this.job.getState() != Job.State.RUNNING) {
            z = false;
        }
        Preconditions.checkState(z);
        this.job.setState(Job.State.FAILED);
        this.callback.onConversionFailure(this.job, new ClientException("Job failed - Reason: " + jobFailedMessage.getMessage()));
    }
}
