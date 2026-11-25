package org.catrobat.catroid.scratchconverter.protocol;

import android.util.Log;
import com.google.common.base.Preconditions;
import com.koushikdutta.async.http.WebSocket;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import org.catrobat.catroid.scratchconverter.Client;
import org.catrobat.catroid.scratchconverter.protocol.message.Message;
import org.catrobat.catroid.scratchconverter.protocol.message.base.BaseMessage;
import org.catrobat.catroid.scratchconverter.protocol.message.job.JobMessage;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/scratchconverter/protocol/WebSocketMessageListener.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class WebSocketMessageListener implements MessageListener, WebSocket.StringCallback {
    private static final String TAG = "WebSocketMessageListener";
    private BaseMessageHandler baseMessageHandler = null;
    private Map<Long, JobHandler> jobHandlers = Collections.synchronizedMap(new LinkedHashMap());

    @Override // org.catrobat.catroid.scratchconverter.protocol.MessageListener
    public void setBaseMessageHandler(final BaseMessageHandler baseMessageHandler) {
        this.baseMessageHandler = baseMessageHandler;
    }

    @Override // com.koushikdutta.async.http.WebSocket.StringCallback
    public synchronized void onStringAvailable(String s) {
        if (s == null) {
            return;
        }
        try {
            String str = TAG;
            Log.d(str, "Receiving new message: " + s);
            JSONObject jSONObject = new JSONObject(s);
            if (jSONObject.length() == 0) {
                return;
            }
            Message.CategoryType categoryTypeValueOf = Message.CategoryType.valueOf(jSONObject.getInt(JsonKeys.CATEGORY.toString()));
            int i = AnonymousClass1.$SwitchMap$org$catrobat$catroid$scratchconverter$protocol$message$Message$CategoryType[categoryTypeValueOf.ordinal()];
            if (i == 1) {
                this.baseMessageHandler.onBaseMessage(BaseMessage.fromJson(jSONObject));
            } else if (i == 2) {
                long j = jSONObject.getJSONObject(JsonKeys.DATA.toString()).getLong(JsonKeys.JsonDataKeys.JOB_ID.toString());
                JobHandler jobHandler = this.jobHandlers.get(Long.valueOf(j));
                if (jobHandler != null) {
                    jobHandler.onJobMessage(JobMessage.fromJson(jSONObject));
                } else {
                    Log.w(str, "No JobHandler registered for job with ID: " + j);
                }
            } else {
                Log.w(str, "Message of unsupported category-type " + categoryTypeValueOf + " received");
            }
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/scratchconverter/protocol/WebSocketMessageListener$1.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.scratchconverter.protocol.WebSocketMessageListener$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$catrobat$catroid$scratchconverter$protocol$message$Message$CategoryType;

        static {
            int[] iArr = new int[Message.CategoryType.values().length];
            $SwitchMap$org$catrobat$catroid$scratchconverter$protocol$message$Message$CategoryType = iArr;
            try {
                iArr[Message.CategoryType.BASE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$scratchconverter$protocol$message$Message$CategoryType[Message.CategoryType.JOB.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    @Override // org.catrobat.catroid.scratchconverter.protocol.MessageListener
    public boolean isJobInProgress(long jobID) {
        JobHandler jobHandler = this.jobHandlers.get(Long.valueOf(jobID));
        if (jobHandler == null) {
            return false;
        }
        return jobHandler.isInProgress();
    }

    @Override // org.catrobat.catroid.scratchconverter.protocol.MessageListener
    public void onUserCanceledConversion(long jobID) {
        JobHandler jobHandler = this.jobHandlers.get(Long.valueOf(jobID));
        if (jobHandler == null) {
            return;
        }
        jobHandler.onUserCanceledConversion();
    }

    @Override // org.catrobat.catroid.scratchconverter.protocol.MessageListener
    public int getNumberOfJobsInProgress() {
        Iterator<JobHandler> it = this.jobHandlers.values().iterator();
        int i = 0;
        while (it.hasNext()) {
            if (it.next().isInProgress()) {
                i++;
            }
        }
        return i;
    }

    private JobHandler createOrUseExistingJobHandlerForJobIfPossible(final Job job, final boolean force, final Client.ConvertCallback convertCallback) {
        JobHandler jobHandler = this.jobHandlers.get(Long.valueOf(job.getJobID()));
        if (jobHandler != null) {
            Log.d(TAG, "JobHandler for jobID " + job.getJobID() + " already exists!");
            if (!force && jobHandler.isInProgress()) {
                return null;
            }
            jobHandler.setCallback(convertCallback);
            return jobHandler;
        }
        Log.d(TAG, "Creating new JobHandler for jobID " + job.getJobID());
        JobHandler jobHandler2 = new JobHandler(job, convertCallback);
        this.jobHandlers.put(Long.valueOf(jobHandler2.getJobID()), jobHandler2);
        return jobHandler2;
    }

    @Override // org.catrobat.catroid.scratchconverter.protocol.MessageListener
    public boolean scheduleJob(final Job job, final boolean force, final Client.ConvertCallback convertCallback) {
        JobHandler jobHandlerCreateOrUseExistingJobHandlerForJobIfPossible = createOrUseExistingJobHandlerForJobIfPossible(job, force, convertCallback);
        if (jobHandlerCreateOrUseExistingJobHandlerForJobIfPossible == null) {
            return false;
        }
        jobHandlerCreateOrUseExistingJobHandlerForJobIfPossible.onJobScheduled();
        return true;
    }

    @Override // org.catrobat.catroid.scratchconverter.protocol.MessageListener
    public Client.ProjectDownloadCallback restoreJobIfRunning(Job job, Client.ConvertCallback convertCallback) {
        JobHandler jobHandlerCreateOrUseExistingJobHandlerForJobIfPossible = createOrUseExistingJobHandlerForJobIfPossible(job, true, convertCallback);
        Preconditions.checkState(jobHandlerCreateOrUseExistingJobHandlerForJobIfPossible != null);
        if (job.getState() != Job.State.FINISHED || job.getDownloadURL() == null) {
            return null;
        }
        return jobHandlerCreateOrUseExistingJobHandlerForJobIfPossible;
    }
}
