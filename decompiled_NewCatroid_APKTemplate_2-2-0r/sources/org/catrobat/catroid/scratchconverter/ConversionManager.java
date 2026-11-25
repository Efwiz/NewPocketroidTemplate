package org.catrobat.catroid.scratchconverter;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.common.images.WebImage;
import org.catrobat.catroid.ui.scratchconverter.BaseInfoViewListener;
import org.catrobat.catroid.ui.scratchconverter.JobViewListener;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/scratchconverter/ConversionManager.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public interface ConversionManager extends Client.ConnectAuthCallback, Client.ConvertCallback, Client.ProjectDownloadCallback {
    void addBaseInfoViewListener(BaseInfoViewListener baseInfoViewListener);

    void addGlobalDownloadCallback(Client.ProjectDownloadCallback callback);

    void addGlobalJobViewListener(JobViewListener jobViewListener);

    void addJobViewListener(long jobID, JobViewListener jobViewListener);

    void connectAndAuthenticate();

    void convertProgram(long jobID, String title, WebImage image, boolean force);

    int getNumberOfJobsInProgress();

    boolean isJobDownloading(long jobID);

    boolean isJobInProgress(long jobID);

    boolean removeBaseInfoViewListener(BaseInfoViewListener baseInfoViewListener);

    boolean removeGlobalDownloadCallback(Client.ProjectDownloadCallback callback);

    boolean removeGlobalJobViewListener(JobViewListener jobViewListener);

    boolean removeJobViewListener(long jobID, JobViewListener jobViewListener);

    void setCurrentActivity(AppCompatActivity activity);

    void shutdown();
}
