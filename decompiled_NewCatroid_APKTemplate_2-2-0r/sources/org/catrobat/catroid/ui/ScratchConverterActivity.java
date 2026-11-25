package org.catrobat.catroid.ui;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.common.SharedPreferenceKeys;
import org.catrobat.catroid.scratchconverter.Client;
import org.catrobat.catroid.scratchconverter.ConversionManager;
import org.catrobat.catroid.scratchconverter.ScratchConversionManager;
import org.catrobat.catroid.scratchconverter.WebSocketClient;
import org.catrobat.catroid.scratchconverter.protocol.Job;
import org.catrobat.catroid.scratchconverter.protocol.WebSocketMessageListener;
import org.catrobat.catroid.ui.recyclerview.adapter.ScratchJobAdapter;
import org.catrobat.catroid.ui.recyclerview.fragment.scratchconverter.ScratchProgramsFragment;
import org.catrobat.catroid.ui.recyclerview.fragment.scratchconverter.ScratchSearchResultsFragment;
import org.catrobat.catroid.ui.scratchconverter.BaseInfoViewListener;
import org.catrobat.catroid.ui.scratchconverter.JobViewListener;
import org.catrobat.catroid.utils.Utils;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/ScratchConverterActivity.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ScratchConverterActivity extends BaseActivity implements BaseInfoViewListener, JobViewListener, Client.ProjectDownloadCallback {
    public static final int FRAGMENT_PROJECTS = 1;
    public static final int FRAGMENT_SEARCH = 0;
    public static final String TAG = "ScratchConverterActivity";
    private static Client client;
    private View bottomBar;
    private ConversionManager conversionManager;
    private OnJobListListener jobListListener;
    private ScratchProgramsFragment scratchProjectsFragment;
    private ScratchSearchResultsFragment searchResultsFragment;
    private List<Job> runningJobs = new ArrayList();
    private List<Job> finishedJobs = new ArrayList();

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/ScratchConverterActivity$FragmentPosition.class
      classes14.dex
     */
    @Retention(RetentionPolicy.SOURCE)
    @interface FragmentPosition {
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/ScratchConverterActivity$OnJobListListener.class
      classes14.dex
     */
    public interface OnJobListListener {
        void onJobListChanged();
    }

    @Override // org.catrobat.catroid.scratchconverter.Client.ProjectDownloadCallback
    public void onDownloadProgress(int progress, String url) {
    }

    @Override // org.catrobat.catroid.ui.scratchconverter.BaseInfoViewListener
    public void onError(String errorMessage) {
    }

    @Override // org.catrobat.catroid.ui.scratchconverter.JobViewListener
    public void onJobOutput(Job job, String[] lines) {
    }

    @Override // org.catrobat.catroid.ui.scratchconverter.JobViewListener
    public void onJobProgress(Job job, short progress) {
    }

    public static void setClient(final Client converterClient) {
        client = converterClient;
    }

    @Override // org.catrobat.catroid.ui.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scratch_converter);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        client = new WebSocketClient(PreferenceManager.getDefaultSharedPreferences(this).getLong(SharedPreferenceKeys.SCRATCH_CONVERTER_CLIENT_ID_PREFERENCE_KEY, -1L), new WebSocketMessageListener());
        ScratchConversionManager scratchConversionManager = new ScratchConversionManager(this, client, false);
        this.conversionManager = scratchConversionManager;
        scratchConversionManager.addBaseInfoViewListener(this);
        this.conversionManager.addGlobalJobViewListener(this);
        this.conversionManager.addGlobalDownloadCallback(this);
        ScratchSearchResultsFragment scratchSearchResultsFragment = new ScratchSearchResultsFragment();
        this.searchResultsFragment = scratchSearchResultsFragment;
        scratchSearchResultsFragment.setConversionManager(this.conversionManager);
        ScratchProgramsFragment scratchProgramsFragment = new ScratchProgramsFragment();
        this.scratchProjectsFragment = scratchProgramsFragment;
        scratchProgramsFragment.initializeAdapters(new ScratchJobAdapter(this.runningJobs), new ScratchJobAdapter(this.finishedJobs));
        this.jobListListener = this.scratchProjectsFragment;
        View viewFindViewById = findViewById(R.id.bottom_bar);
        this.bottomBar = viewFindViewById;
        viewFindViewById.setVisibility(8);
        this.bottomBar.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.ScratchConverterActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (ScratchConverterActivity.this.searchResultsFragment.isVisible()) {
                    ScratchConverterActivity.this.switchToFragment(1);
                }
                if (ScratchConverterActivity.this.scratchProjectsFragment.isVisible()) {
                    ScratchConverterActivity.this.switchToFragment(0);
                }
            }
        });
        this.searchResultsFragment.setConversionManager(this.conversionManager);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, this.searchResultsFragment).commit();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void switchToFragment(int fragmentPosition) {
        FragmentTransaction fragmentTransactionBeginTransaction = getSupportFragmentManager().beginTransaction();
        if (fragmentPosition != 0) {
            if (fragmentPosition != 1) {
                return;
            } else {
                fragmentTransactionBeginTransaction.replace(R.id.fragment_container, this.scratchProjectsFragment).addToBackStack(ScratchProgramsFragment.TAG);
            }
        } else if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            fragmentTransactionBeginTransaction.replace(R.id.fragment_container, this.searchResultsFragment);
        }
        fragmentTransactionBeginTransaction.commit();
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStart() {
        super.onStart();
        this.conversionManager.setCurrentActivity(this);
        if (client.isAuthenticated()) {
            return;
        }
        this.conversionManager.connectAndAuthenticate();
    }

    @Override // org.catrobat.catroid.ui.BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        this.conversionManager.shutdown();
    }

    @Override // org.catrobat.catroid.ui.scratchconverter.BaseInfoViewListener
    public void onJobsInfo(Job[] jobs) {
        this.runningJobs.clear();
        this.finishedJobs.clear();
        for (Job job : jobs) {
            if (job.isInProgress()) {
                this.runningJobs.add(job);
            } else if (job.getState() != Job.State.UNSCHEDULED) {
                this.finishedJobs.add(job);
            }
        }
        updateBottomBar();
        this.jobListListener.onJobListChanged();
    }

    private void updateBottomBar() {
        TextView textView = (TextView) this.bottomBar.findViewById(R.id.title_view);
        TextView textView2 = (TextView) this.bottomBar.findViewById(R.id.details_view);
        textView.setText(getResources().getQuantityString(R.plurals.status_in_progress_x_jobs, this.runningJobs.size(), Integer.valueOf(this.runningJobs.size())));
        textView2.setText(getResources().getQuantityString(R.plurals.status_completed_x_jobs, this.finishedJobs.size(), Integer.valueOf(this.finishedJobs.size())));
        if (this.runningJobs.size() > 0 || this.finishedJobs.size() > 0) {
            this.bottomBar.setVisibility(0);
        } else {
            this.bottomBar.setVisibility(8);
        }
    }

    @Override // org.catrobat.catroid.ui.scratchconverter.JobViewListener
    public void onJobScheduled(Job job) {
        if (!listContainsJob(this.runningJobs, job)) {
            this.runningJobs.add(0, job);
        }
        updateBottomBar();
        this.jobListListener.onJobListChanged();
    }

    @Override // org.catrobat.catroid.ui.scratchconverter.JobViewListener
    public void onJobReady(Job job) {
        this.jobListListener.onJobListChanged();
    }

    @Override // org.catrobat.catroid.ui.scratchconverter.JobViewListener
    public void onJobStarted(Job job) {
        if (!listContainsJob(this.runningJobs, job)) {
            this.runningJobs.add(0, job);
        }
        updateBottomBar();
        this.jobListListener.onJobListChanged();
    }

    @Override // org.catrobat.catroid.ui.scratchconverter.JobViewListener
    public void onJobFinished(Job job) {
        removeFromList(this.runningJobs, job);
        if (!listContainsJob(this.finishedJobs, job)) {
            this.finishedJobs.add(0, job);
        }
        updateBottomBar();
        this.jobListListener.onJobListChanged();
    }

    @Override // org.catrobat.catroid.ui.scratchconverter.JobViewListener
    public void onJobFailed(Job job) {
        removeFromList(this.runningJobs, job);
        if (!listContainsJob(this.finishedJobs, job)) {
            this.finishedJobs.add(0, job);
        }
        updateBottomBar();
        this.jobListListener.onJobListChanged();
    }

    @Override // org.catrobat.catroid.ui.scratchconverter.JobViewListener
    public void onUserCanceledJob(Job job) {
        removeFromList(this.runningJobs, job);
        if (!listContainsJob(this.finishedJobs, job)) {
            this.finishedJobs.add(0, job);
        }
        updateBottomBar();
        this.jobListListener.onJobListChanged();
    }

    @Override // org.catrobat.catroid.scratchconverter.Client.ProjectDownloadCallback
    public void onDownloadStarted(String url) {
        long jExtractScratchJobIDFromURL = Utils.extractScratchJobIDFromURL(url);
        for (Job job : this.finishedJobs) {
            if (job.getJobID() == jExtractScratchJobIDFromURL) {
                job.setDownloadState(Job.DownloadState.DOWNLOADING);
            }
        }
        this.jobListListener.onJobListChanged();
    }

    @Override // org.catrobat.catroid.scratchconverter.Client.ProjectDownloadCallback
    public void onDownloadFinished(String catrobatProgramName, String url) {
        long jExtractScratchJobIDFromURL = Utils.extractScratchJobIDFromURL(url);
        for (Job job : this.finishedJobs) {
            if (job.getJobID() == jExtractScratchJobIDFromURL) {
                job.setDownloadState(Job.DownloadState.DOWNLOADED);
            }
        }
        this.jobListListener.onJobListChanged();
    }

    @Override // org.catrobat.catroid.scratchconverter.Client.ProjectDownloadCallback
    public void onUserCanceledDownload(String url) {
        long jExtractScratchJobIDFromURL = Utils.extractScratchJobIDFromURL(url);
        for (Job job : this.finishedJobs) {
            if (job.getJobID() == jExtractScratchJobIDFromURL) {
                job.setDownloadState(Job.DownloadState.CANCELED);
            }
        }
        this.jobListListener.onJobListChanged();
    }

    private boolean listContainsJob(List<Job> list, Job job) {
        Iterator<Job> it = list.iterator();
        while (it.hasNext()) {
            if (it.next().getJobID() == job.getJobID()) {
                return true;
            }
        }
        return false;
    }

    private boolean removeFromList(List<Job> list, Job job) {
        for (Job job2 : list) {
            if (job2.getJobID() == job.getJobID()) {
                list.remove(job2);
                return true;
            }
        }
        return false;
    }
}
