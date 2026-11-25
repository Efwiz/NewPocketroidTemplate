package org.catrobat.catroid.scratchconverter;

import com.google.android.gms.common.images.WebImage;
import java.util.Date;
import org.catrobat.catroid.scratchconverter.protocol.Job;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/scratchconverter/Client.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public interface Client {
    public static final long INVALID_CLIENT_ID = -1;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/scratchconverter/Client$ConnectAuthCallback.class
      classes14.dex
     */
    public interface ConnectAuthCallback {
        void onAuthenticationFailure(ClientException ex);

        void onConnectionClosed(ClientException ex);

        void onConnectionFailure(ClientException ex);

        void onSuccess(long clientID);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/scratchconverter/Client$ConvertCallback.class
      classes14.dex
     */
    public interface ConvertCallback {
        void onConversionAlreadyFinished(Job job, ProjectDownloadCallback downloadCallback, String downloadURL);

        void onConversionFailure(Job job, ClientException ex);

        void onConversionFinished(Job job, ProjectDownloadCallback downloadCallback, String downloadURL, Date cachedDate);

        void onConversionReady(Job job);

        void onConversionStart(Job job);

        void onError(String errorMessage);

        void onInfo(double supportedCatrobatLanguageVersion, Job[] jobs);

        void onJobOutput(Job job, String[] lines);

        void onJobProgress(Job job, short progress);

        void onJobScheduled(Job job);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/scratchconverter/Client$ProjectDownloadCallback.class
      classes14.dex
     */
    public interface ProjectDownloadCallback {
        void onDownloadFinished(String catrobatProgramName, String url);

        void onDownloadProgress(int progress, String url);

        void onDownloadStarted(String url);

        void onUserCanceledDownload(String url);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/scratchconverter/Client$State.class
      classes14.dex
     */
    public enum State {
        NOT_CONNECTED,
        CONNECTED,
        CONNECTED_AUTHENTICATED
    }

    void close();

    void connectAndAuthenticate(ConnectAuthCallback connectAuthCallback);

    void convertProgram(long jobID, String title, WebImage image, boolean verbose, boolean force);

    int getNumberOfJobsInProgress();

    boolean isAuthenticated();

    boolean isClosed();

    boolean isJobInProgress(long jobID);

    void onUserCanceledConversion(long jobID);

    void retrieveInfo();

    void setConvertCallback(ConvertCallback convertCallback);
}
