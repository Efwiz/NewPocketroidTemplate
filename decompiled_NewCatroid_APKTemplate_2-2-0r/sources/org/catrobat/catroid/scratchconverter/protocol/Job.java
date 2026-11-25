package org.catrobat.catroid.scratchconverter.protocol;

import android.net.Uri;
import android.util.SparseArray;
import com.google.android.gms.common.images.WebImage;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/scratchconverter/protocol/Job.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class Job {
    private short downloadProgress;
    private WebImage image;
    private long jobID;
    private String title;
    private State state = State.UNSCHEDULED;
    private short progress = 0;
    private DownloadState downloadState = DownloadState.NOT_READY;
    private String downloadURL = null;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/scratchconverter/protocol/Job$State.class
      classes14.dex
     */
    public enum State {
        UNSCHEDULED(-2),
        SCHEDULED(-1),
        READY(0),
        RUNNING(1),
        FINISHED(2),
        FAILED(3);

        private static SparseArray<State> states = new SparseArray<>();
        private int state;

        static {
            for (State state : values()) {
                states.put(state.state, state);
            }
        }

        State(final int state) {
            this.state = state;
        }

        public static State valueOf(int state) {
            return states.get(state);
        }

        public boolean isInProgress() {
            return this == SCHEDULED || this == READY || this == RUNNING;
        }

        public int getStateID() {
            return this.state;
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/scratchconverter/protocol/Job$DownloadState.class
      classes14.dex
     */
    public enum DownloadState {
        NOT_READY(0),
        READY(1),
        DOWNLOADING(2),
        DOWNLOADED(3),
        CANCELED(4);

        private static SparseArray<DownloadState> downloadStates = new SparseArray<>();
        private int downloadState;

        static {
            for (DownloadState downloadState : values()) {
                downloadStates.put(downloadState.downloadState, downloadState);
            }
        }

        DownloadState(final int downloadState) {
            this.downloadState = downloadState;
        }

        public static DownloadState valueOf(int downloadState) {
            return downloadStates.get(downloadState);
        }

        public int getDownloadStateID() {
            return this.downloadState;
        }
    }

    public Job(long jobID, String title, WebImage image) {
        this.jobID = jobID;
        this.title = title;
        this.image = image;
    }

    public static Job fromJson(JSONObject data) throws JSONException {
        State stateValueOf = State.valueOf(data.getInt(JsonKeys.JsonJobDataKeys.STATE.toString()));
        long j = data.getLong(JsonKeys.JsonJobDataKeys.JOB_ID.toString());
        String string = data.getString(JsonKeys.JsonJobDataKeys.TITLE.toString());
        WebImage webImage = null;
        String string2 = data.isNull(JsonKeys.JsonJobDataKeys.IMAGE_URL.toString()) ? null : data.getString(JsonKeys.JsonJobDataKeys.IMAGE_URL.toString());
        if (string2 != null) {
            int[] iArr = {480, 360};
            webImage = new WebImage(Uri.parse(string2), iArr[0], iArr[1]);
        }
        short s = (short) data.getInt(JsonKeys.JsonJobDataKeys.PROGRESS.toString());
        String string3 = data.getString(JsonKeys.JsonJobDataKeys.DOWNLOAD_URL.toString());
        Job job = new Job(j, string, webImage);
        job.state = stateValueOf;
        job.progress = s;
        job.downloadURL = string3;
        return job;
    }

    public State getState() {
        return this.state;
    }

    public boolean isInProgress() {
        return this.state.isInProgress();
    }

    public void setState(State state) {
        this.state = state;
    }

    public long getJobID() {
        return this.jobID;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public short getProgress() {
        return this.progress;
    }

    public void setProgress(short progress) {
        this.progress = progress;
    }

    public short getDownloadProgress() {
        return this.downloadProgress;
    }

    public void setDownloadProgress(short downloadProgress) {
        this.downloadProgress = downloadProgress;
    }

    public WebImage getImage() {
        return this.image;
    }

    public void setImage(WebImage image) {
        this.image = image;
    }

    public DownloadState getDownloadState() {
        return this.downloadState;
    }

    public void setDownloadState(DownloadState downloadState) {
        this.downloadState = downloadState;
    }

    public String getDownloadURL() {
        return this.downloadURL;
    }

    public void setDownloadURL(String downloadURL) {
        this.downloadURL = downloadURL;
    }
}
