package org.catrobat.catroid.scratchconverter;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.common.images.WebImage;
import com.google.common.base.Preconditions;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.SharedPreferenceKeys;
import org.catrobat.catroid.scratchconverter.protocol.Job;
import org.catrobat.catroid.ui.MainMenuActivity;
import org.catrobat.catroid.ui.dialogs.ScratchReconvertDialog;
import org.catrobat.catroid.ui.scratchconverter.BaseInfoViewListener;
import org.catrobat.catroid.ui.scratchconverter.JobViewListener;
import org.catrobat.catroid.utils.ToastUtil;
import org.catrobat.catroid.utils.Utils;
import org.catrobat.catroid.web.GlobalProjectDownloadQueue;
import org.catrobat.catroid.web.ProjectDownloader;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/scratchconverter/ScratchConversionManager.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ScratchConversionManager implements ConversionManager {
    private static final String TAG = "ScratchConversionManager";
    private Set<BaseInfoViewListener> baseInfoViewListeners;
    private final Client client;
    private AppCompatActivity currentActivity;
    private Map<String, Client.ProjectDownloadCallback> downloadCallbacks = new HashMap();
    private Set<Client.ProjectDownloadCallback> globalDownloadCallbacks = Collections.synchronizedSet(new HashSet());
    private Set<JobViewListener> globalJobViewListeners;
    private Map<Long, Set<JobViewListener>> jobViewListeners;
    private boolean shutdown;
    private final boolean verbose;

    public ScratchConversionManager(AppCompatActivity rootActivity, Client client, boolean verbose) {
        this.currentActivity = rootActivity;
        this.client = client;
        this.verbose = verbose;
        client.setConvertCallback(this);
        this.jobViewListeners = Collections.synchronizedMap(new HashMap());
        this.globalJobViewListeners = Collections.synchronizedSet(new HashSet());
        this.baseInfoViewListeners = Collections.synchronizedSet(new HashSet());
        this.shutdown = false;
    }

    @Override // org.catrobat.catroid.scratchconverter.ConversionManager
    public void setCurrentActivity(AppCompatActivity activity) {
        this.currentActivity = activity;
    }

    @Override // org.catrobat.catroid.scratchconverter.ConversionManager
    public void addGlobalDownloadCallback(final Client.ProjectDownloadCallback callback) {
        this.globalDownloadCallbacks.add(callback);
    }

    @Override // org.catrobat.catroid.scratchconverter.ConversionManager
    public boolean removeGlobalDownloadCallback(final Client.ProjectDownloadCallback callback) {
        return this.globalDownloadCallbacks.remove(callback);
    }

    @Override // org.catrobat.catroid.scratchconverter.ConversionManager
    public boolean isJobInProgress(long jobID) {
        return this.client.isJobInProgress(jobID);
    }

    @Override // org.catrobat.catroid.scratchconverter.ConversionManager
    public boolean isJobDownloading(long jobID) {
        return readDownloadStateFromDisk(jobID) == Job.DownloadState.DOWNLOADING;
    }

    @Override // org.catrobat.catroid.scratchconverter.ConversionManager
    public int getNumberOfJobsInProgress() {
        return this.client.getNumberOfJobsInProgress();
    }

    @Override // org.catrobat.catroid.scratchconverter.ConversionManager
    public void connectAndAuthenticate() {
        this.client.connectAndAuthenticate(this);
    }

    @Override // org.catrobat.catroid.scratchconverter.ConversionManager
    public void shutdown() {
        this.shutdown = true;
        if (this.client.isClosed()) {
            return;
        }
        this.client.close();
    }

    @Override // org.catrobat.catroid.scratchconverter.ConversionManager
    public void convertProgram(long jobID, String title, WebImage image, boolean force) {
        updateDownloadStateOnDisk(jobID, Job.DownloadState.NOT_READY);
        this.client.convertProgram(jobID, title, image, this.verbose, force);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void closeAllActivities() {
        if (this.shutdown) {
            return;
        }
        Intent intent = new Intent(this.currentActivity.getApplicationContext(), (Class<?>) MainMenuActivity.class);
        intent.addFlags(67108864);
        this.currentActivity.startActivity(intent);
    }

    @Override // org.catrobat.catroid.scratchconverter.Client.ConnectAuthCallback
    public void onSuccess(long clientID) {
        SharedPreferences.Editor editorEdit = PreferenceManager.getDefaultSharedPreferences(this.currentActivity.getApplicationContext()).edit();
        editorEdit.putLong(SharedPreferenceKeys.SCRATCH_CONVERTER_CLIENT_ID_PREFERENCE_KEY, clientID);
        editorEdit.apply();
        Log.i(TAG, "Connection established (clientID: " + clientID + Constant.AFTER_QUTO);
        Preconditions.checkState(this.client.isAuthenticated());
        this.client.retrieveInfo();
    }

    @Override // org.catrobat.catroid.scratchconverter.Client.ConnectAuthCallback
    public void onConnectionClosed(ClientException ex) {
        Log.d(TAG, "Connection closed!");
        final String message = ex.getMessage();
        this.currentActivity.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.scratchconverter.ScratchConversionManager.1
            @Override // java.lang.Runnable
            public void run() {
                if (message != null) {
                    Log.e(ScratchConversionManager.TAG, message);
                }
                if (!ScratchConversionManager.this.shutdown) {
                    ToastUtil.showError(ScratchConversionManager.this.currentActivity, R.string.connection_lost_or_closed_by_server);
                }
                ScratchConversionManager.this.closeAllActivities();
            }
        });
    }

    @Override // org.catrobat.catroid.scratchconverter.Client.ConnectAuthCallback
    public void onConnectionFailure(ClientException ex) {
        Log.e(TAG, ex.getMessage());
        this.currentActivity.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.scratchconverter.ScratchConversionManager.2
            @Override // java.lang.Runnable
            public void run() {
                ToastUtil.showError(ScratchConversionManager.this.currentActivity, R.string.connection_failed);
                ScratchConversionManager.this.closeAllActivities();
            }
        });
    }

    @Override // org.catrobat.catroid.scratchconverter.Client.ConnectAuthCallback
    public void onAuthenticationFailure(ClientException ex) {
        Log.e(TAG, ex.getMessage());
        this.currentActivity.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.scratchconverter.ScratchConversionManager.3
            @Override // java.lang.Runnable
            public void run() {
                ToastUtil.showError(ScratchConversionManager.this.currentActivity, R.string.authentication_failed);
                ScratchConversionManager.this.closeAllActivities();
            }
        });
    }

    @Override // org.catrobat.catroid.scratchconverter.ConversionManager
    public void addBaseInfoViewListener(BaseInfoViewListener baseInfoViewListener) {
        this.baseInfoViewListeners.add(baseInfoViewListener);
    }

    @Override // org.catrobat.catroid.scratchconverter.ConversionManager
    public boolean removeBaseInfoViewListener(BaseInfoViewListener baseInfoViewListener) {
        return this.baseInfoViewListeners.remove(baseInfoViewListener);
    }

    @Override // org.catrobat.catroid.scratchconverter.ConversionManager
    public void addGlobalJobViewListener(JobViewListener jobViewListener) {
        this.globalJobViewListeners.add(jobViewListener);
    }

    @Override // org.catrobat.catroid.scratchconverter.ConversionManager
    public boolean removeGlobalJobViewListener(JobViewListener jobViewListener) {
        return this.globalJobViewListeners.remove(jobViewListener);
    }

    @Override // org.catrobat.catroid.scratchconverter.ConversionManager
    public void addJobViewListener(long jobID, JobViewListener jobViewListener) {
        Set<JobViewListener> hashSet = this.jobViewListeners.get(Long.valueOf(jobID));
        if (hashSet == null) {
            hashSet = new HashSet<>();
        }
        hashSet.add(jobViewListener);
        this.jobViewListeners.put(Long.valueOf(jobID), hashSet);
    }

    @Override // org.catrobat.catroid.scratchconverter.ConversionManager
    public boolean removeJobViewListener(long jobID, JobViewListener jobViewListener) {
        Set<JobViewListener> set = this.jobViewListeners.get(Long.valueOf(jobID));
        return set != null && set.remove(jobViewListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public JobViewListener[] getJobViewListeners(long jobID) {
        HashSet hashSet = new HashSet();
        Set<JobViewListener> set = this.jobViewListeners.get(Long.valueOf(jobID));
        if (set != null) {
            hashSet.addAll(set);
        }
        hashSet.addAll(this.globalJobViewListeners);
        return (JobViewListener[]) hashSet.toArray(new JobViewListener[hashSet.size()]);
    }

    @Override // org.catrobat.catroid.scratchconverter.Client.ConvertCallback
    public void onInfo(final double supportedCatrobatLanguageVersion, final Job[] jobs) {
        for (Job job : jobs) {
            job.setDownloadState(readDownloadStateFromDisk(job.getJobID()));
        }
        this.currentActivity.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.scratchconverter.ScratchConversionManager.4
            @Override // java.lang.Runnable
            public void run() {
                Log.i(ScratchConversionManager.TAG, "Supported Catrobat Language version: " + supportedCatrobatLanguageVersion);
                Iterator it = ScratchConversionManager.this.baseInfoViewListeners.iterator();
                while (it.hasNext()) {
                    ((BaseInfoViewListener) it.next()).onJobsInfo(jobs);
                }
                if (1.18d < supportedCatrobatLanguageVersion) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ScratchConversionManager.this.currentActivity);
                    builder.setTitle(R.string.warning);
                    builder.setMessage(R.string.error_scratch_converter_outdated_app_version);
                    builder.setNeutralButton(R.string.close, (DialogInterface.OnClickListener) null);
                    builder.create().show();
                }
            }
        });
    }

    @Override // org.catrobat.catroid.scratchconverter.Client.ConvertCallback
    public void onJobScheduled(final Job job) {
        this.currentActivity.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.scratchconverter.ScratchConversionManager.5
            @Override // java.lang.Runnable
            public void run() {
                for (JobViewListener jobViewListener : ScratchConversionManager.this.getJobViewListeners(job.getJobID())) {
                    jobViewListener.onJobScheduled(job);
                }
            }
        });
    }

    @Override // org.catrobat.catroid.scratchconverter.Client.ConvertCallback
    public void onConversionReady(final Job job) {
        Log.i(TAG, "Conversion ready!");
        this.currentActivity.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.scratchconverter.ScratchConversionManager.6
            @Override // java.lang.Runnable
            public void run() {
                for (JobViewListener jobViewListener : ScratchConversionManager.this.getJobViewListeners(job.getJobID())) {
                    jobViewListener.onJobReady(job);
                }
            }
        });
    }

    @Override // org.catrobat.catroid.scratchconverter.Client.ConvertCallback
    public void onConversionStart(final Job job) {
        Log.i(TAG, "Conversion started!");
        this.currentActivity.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.scratchconverter.ScratchConversionManager.7
            @Override // java.lang.Runnable
            public void run() {
                ToastUtil.showSuccess(ScratchConversionManager.this.currentActivity, ScratchConversionManager.this.currentActivity.getString(R.string.scratch_conversion_started));
                for (JobViewListener jobViewListener : ScratchConversionManager.this.getJobViewListeners(job.getJobID())) {
                    jobViewListener.onJobStarted(job);
                }
            }
        });
    }

    @Override // org.catrobat.catroid.scratchconverter.Client.ConvertCallback
    public void onConversionFinished(Job job, Client.ProjectDownloadCallback downloadCallback, final String downloadURL, final Date cachedUTCDate) {
        Log.i(TAG, "Conversion finished!");
        updateDownloadStateOnDisk(job.getJobID(), Job.DownloadState.READY);
        conversionFinished(job, downloadCallback, downloadURL, cachedUTCDate);
    }

    @Override // org.catrobat.catroid.scratchconverter.Client.ConvertCallback
    public void onConversionAlreadyFinished(Job job, Client.ProjectDownloadCallback downloadCallback, String downloadURL) {
        if (readDownloadStateFromDisk(job.getJobID()) == Job.DownloadState.NOT_READY) {
            updateDownloadStateOnDisk(job.getJobID(), Job.DownloadState.READY);
        }
        conversionFinished(job, downloadCallback, downloadURL, null);
    }

    private void conversionFinished(final Job job, final Client.ProjectDownloadCallback downloadCallback, final String downloadURL, final Date cachedUTCDate) {
        final String str = Constants.SCRATCH_CONVERTER_BASE_URL.substring(0, Constants.SCRATCH_CONVERTER_BASE_URL.length() - 1) + downloadURL;
        final Job.DownloadState downloadStateFromDisk = readDownloadStateFromDisk(job.getJobID());
        if (downloadStateFromDisk == Job.DownloadState.READY || downloadStateFromDisk == Job.DownloadState.DOWNLOADING) {
            this.currentActivity.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.scratchconverter.ScratchConversionManager.8
                @Override // java.lang.Runnable
                public void run() {
                    for (JobViewListener jobViewListener : ScratchConversionManager.this.getJobViewListeners(job.getJobID())) {
                        jobViewListener.onJobFinished(job);
                    }
                    ScratchConversionManager.this.downloadCallbacks.put(downloadURL, downloadCallback);
                    if (downloadStateFromDisk == Job.DownloadState.DOWNLOADING) {
                        Log.i(ScratchConversionManager.TAG, "Download of converted project is already RUNNNING!!");
                        ScratchConversionManager.this.onDownloadStarted(str);
                        return;
                    }
                    Log.i(ScratchConversionManager.TAG, "Downloading missed converted project...");
                    if (cachedUTCDate != null) {
                        ScratchReconvertDialog scratchReconvertDialog = new ScratchReconvertDialog();
                        scratchReconvertDialog.setContext(ScratchConversionManager.this.currentActivity);
                        scratchReconvertDialog.setCachedDate(cachedUTCDate);
                        scratchReconvertDialog.setReconvertDialogCallback(new ScratchReconvertDialog.ReconvertDialogCallback() { // from class: org.catrobat.catroid.scratchconverter.ScratchConversionManager.8.1
                            @Override // org.catrobat.catroid.ui.dialogs.ScratchReconvertDialog.ReconvertDialogCallback
                            public void onDownloadExistingProgram() {
                                ScratchConversionManager.this.downloadProgram(str);
                            }

                            @Override // org.catrobat.catroid.ui.dialogs.ScratchReconvertDialog.ReconvertDialogCallback
                            public void onReconvertProgram() {
                                ScratchConversionManager.this.convertProgram(job.getJobID(), job.getTitle(), job.getImage(), true);
                            }

                            @Override // org.catrobat.catroid.ui.dialogs.ScratchReconvertDialog.ReconvertDialogCallback
                            public void onUserCanceledConversion() {
                                ScratchConversionManager.this.client.onUserCanceledConversion(job.getJobID());
                                for (JobViewListener jobViewListener2 : ScratchConversionManager.this.getJobViewListeners(job.getJobID())) {
                                    jobViewListener2.onUserCanceledJob(job);
                                }
                            }
                        });
                        scratchReconvertDialog.show(ScratchConversionManager.this.currentActivity.getSupportFragmentManager(), ScratchReconvertDialog.DIALOG_FRAGMENT_TAG);
                        return;
                    }
                    ScratchConversionManager.this.downloadProgram(str);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void downloadProgram(final String fullDownloadURL) {
        Log.d(TAG, "Start download: " + fullDownloadURL);
        new ProjectDownloader(GlobalProjectDownloadQueue.INSTANCE.getQueue(), fullDownloadURL, this).download(this.currentActivity);
    }

    @Override // org.catrobat.catroid.scratchconverter.Client.ConvertCallback
    public void onConversionFailure(final Job job, final ClientException ex) {
        Log.e(TAG, "Conversion failed: " + ex.getMessage());
        this.currentActivity.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.scratchconverter.ScratchConversionManager.9
            @Override // java.lang.Runnable
            public void run() {
                Job job2 = job;
                if (job2 != null) {
                    for (JobViewListener jobViewListener : ScratchConversionManager.this.getJobViewListeners(job2.getJobID())) {
                        jobViewListener.onJobFailed(job);
                    }
                    ToastUtil.showError(ScratchConversionManager.this.currentActivity, ScratchConversionManager.this.currentActivity.getResources().getString(R.string.error_specific_scratch_program_conversion_failed_x, job.getTitle()));
                    return;
                }
                ToastUtil.showError(ScratchConversionManager.this.currentActivity, R.string.error_scratch_program_conversion_failed);
                ScratchConversionManager.this.closeAllActivities();
            }
        });
    }

    @Override // org.catrobat.catroid.scratchconverter.Client.ConvertCallback
    public void onError(final String errorMessage) {
        this.currentActivity.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.scratchconverter.ScratchConversionManager.10
            @Override // java.lang.Runnable
            public void run() {
                Iterator it = ScratchConversionManager.this.baseInfoViewListeners.iterator();
                while (it.hasNext()) {
                    ((BaseInfoViewListener) it.next()).onError(errorMessage);
                }
            }
        });
    }

    @Override // org.catrobat.catroid.scratchconverter.Client.ConvertCallback
    public void onJobOutput(final Job job, final String[] lines) {
        this.currentActivity.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.scratchconverter.ScratchConversionManager.11
            @Override // java.lang.Runnable
            public void run() {
                for (JobViewListener jobViewListener : ScratchConversionManager.this.getJobViewListeners(job.getJobID())) {
                    jobViewListener.onJobOutput(job, lines);
                }
            }
        });
    }

    @Override // org.catrobat.catroid.scratchconverter.Client.ConvertCallback
    public void onJobProgress(final Job job, final short progress) {
        this.currentActivity.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.scratchconverter.ScratchConversionManager.12
            @Override // java.lang.Runnable
            public void run() {
                for (JobViewListener jobViewListener : ScratchConversionManager.this.getJobViewListeners(job.getJobID())) {
                    jobViewListener.onJobProgress(job, progress);
                }
            }
        });
    }

    private void updateDownloadStateOnDisk(final long jobID, final Job.DownloadState downloadState) {
        Log.d(TAG, "Update download-state of program on disk");
        try {
            SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.currentActivity.getApplicationContext());
            SharedPreferences.Editor editorEdit = defaultSharedPreferences.edit();
            String string = defaultSharedPreferences.getString(SharedPreferenceKeys.SCRATCH_CONVERTER_DOWNLOAD_STATE_PREFERENCE_KEY, null);
            HashMap map = new HashMap();
            if (string != null) {
                JSONObject jSONObject = new JSONObject(string);
                Iterator<String> itKeys = jSONObject.keys();
                while (itKeys.hasNext()) {
                    String next = itKeys.next();
                    map.put(next, jSONObject.getString(next));
                }
            }
            map.put(Long.toString(jobID), Integer.toString(downloadState.getDownloadStateID()));
            Log.d(TAG, map.toString());
            editorEdit.putString(SharedPreferenceKeys.SCRATCH_CONVERTER_DOWNLOAD_STATE_PREFERENCE_KEY, new JSONObject(map).toString());
            editorEdit.apply();
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private Job.DownloadState readDownloadStateFromDisk(final long jobID) {
        Log.d(TAG, "Read download-state of program from disk");
        try {
            String string = PreferenceManager.getDefaultSharedPreferences(this.currentActivity.getApplicationContext()).getString(SharedPreferenceKeys.SCRATCH_CONVERTER_DOWNLOAD_STATE_PREFERENCE_KEY, null);
            HashMap map = new HashMap();
            if (string != null) {
                JSONObject jSONObject = new JSONObject(string);
                Iterator<String> itKeys = jSONObject.keys();
                while (itKeys.hasNext()) {
                    String next = itKeys.next();
                    map.put(next, jSONObject.getString(next));
                }
            }
            String str = (String) map.get(Long.toString(jobID));
            if (str == null) {
                return Job.DownloadState.NOT_READY;
            }
            return Job.DownloadState.valueOf(Integer.parseInt(str));
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
            return Job.DownloadState.NOT_READY;
        }
    }

    @Override // org.catrobat.catroid.scratchconverter.Client.ProjectDownloadCallback
    public void onDownloadStarted(final String url) {
        updateDownloadStateOnDisk(Utils.extractScratchJobIDFromURL(url), Job.DownloadState.DOWNLOADING);
        this.currentActivity.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.scratchconverter.ScratchConversionManager.13
            @Override // java.lang.Runnable
            public void run() {
                Client.ProjectDownloadCallback projectDownloadCallback = (Client.ProjectDownloadCallback) ScratchConversionManager.this.downloadCallbacks.get(url);
                if (projectDownloadCallback != null) {
                    projectDownloadCallback.onDownloadStarted(url);
                }
                Iterator it = ScratchConversionManager.this.globalDownloadCallbacks.iterator();
                while (it.hasNext()) {
                    ((Client.ProjectDownloadCallback) it.next()).onDownloadStarted(url);
                }
            }
        });
    }

    @Override // org.catrobat.catroid.scratchconverter.Client.ProjectDownloadCallback
    public void onDownloadProgress(final int progress, final String url) {
        this.currentActivity.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.scratchconverter.ScratchConversionManager.14
            @Override // java.lang.Runnable
            public void run() {
                Client.ProjectDownloadCallback projectDownloadCallback = (Client.ProjectDownloadCallback) ScratchConversionManager.this.downloadCallbacks.get(url);
                if (projectDownloadCallback != null) {
                    projectDownloadCallback.onDownloadProgress(progress, url);
                }
                Iterator it = ScratchConversionManager.this.globalDownloadCallbacks.iterator();
                while (it.hasNext()) {
                    ((Client.ProjectDownloadCallback) it.next()).onDownloadProgress(progress, url);
                }
            }
        });
    }

    @Override // org.catrobat.catroid.scratchconverter.Client.ProjectDownloadCallback
    public void onDownloadFinished(final String catrobatProgramName, final String url) {
        updateDownloadStateOnDisk(Utils.extractScratchJobIDFromURL(url), Job.DownloadState.DOWNLOADED);
        this.currentActivity.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.scratchconverter.ScratchConversionManager.15
            @Override // java.lang.Runnable
            public void run() {
                Client.ProjectDownloadCallback projectDownloadCallback = (Client.ProjectDownloadCallback) ScratchConversionManager.this.downloadCallbacks.get(url);
                if (projectDownloadCallback != null) {
                    projectDownloadCallback.onDownloadFinished(catrobatProgramName, url);
                }
                Iterator it = ScratchConversionManager.this.globalDownloadCallbacks.iterator();
                while (it.hasNext()) {
                    ((Client.ProjectDownloadCallback) it.next()).onDownloadFinished(catrobatProgramName, url);
                }
            }
        });
    }

    @Override // org.catrobat.catroid.scratchconverter.Client.ProjectDownloadCallback
    public void onUserCanceledDownload(String url) {
        updateDownloadStateOnDisk(Utils.extractScratchJobIDFromURL(url), Job.DownloadState.CANCELED);
        Client.ProjectDownloadCallback projectDownloadCallback = this.downloadCallbacks.get(url);
        if (projectDownloadCallback != null) {
            projectDownloadCallback.onUserCanceledDownload(url);
        }
        Iterator<Client.ProjectDownloadCallback> it = this.globalDownloadCallbacks.iterator();
        while (it.hasNext()) {
            it.next().onUserCanceledDownload(url);
        }
    }
}
