package org.catrobat.catroid.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import com.beust.jcommander.Parameters;
import com.google.common.base.Preconditions;
import com.squareup.picasso.Picasso;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Locale;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.ScratchProgramData;
import org.catrobat.catroid.common.ScratchVisibilityState;
import org.catrobat.catroid.scratchconverter.Client;
import org.catrobat.catroid.scratchconverter.ConversionManager;
import org.catrobat.catroid.scratchconverter.protocol.Job;
import org.catrobat.catroid.transfers.FetchScratchProgramDetailsTask;
import org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter;
import org.catrobat.catroid.ui.recyclerview.adapter.ScratchProgramAdapter;
import org.catrobat.catroid.ui.recyclerview.adapter.multiselection.MultiSelectionManager;
import org.catrobat.catroid.ui.recyclerview.viewholder.CheckableViewHolder;
import org.catrobat.catroid.ui.scratchconverter.JobViewListener;
import org.catrobat.catroid.utils.NumberFormats;
import org.catrobat.catroid.utils.ToastUtil;
import org.catrobat.catroid.utils.Utils;
import org.catrobat.catroid.web.CatrobatWebClient;
import org.catrobat.catroid.web.ScratchDataFetcher;
import org.catrobat.catroid.web.ServerCalls;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/ScratchProgramDetailsActivity.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ScratchProgramDetailsActivity extends BaseActivity implements FetchScratchProgramDetailsTask.ScratchProgramListTaskDelegate, JobViewListener, Client.ProjectDownloadCallback, RVAdapter.OnItemClickListener<ScratchProgramData> {
    public static final String TAG = "ScratchProgramDetailsActivity";
    private static ConversionManager conversionManager;
    private static ScratchDataFetcher dataFetcher = new ServerCalls(CatrobatWebClient.INSTANCE.getClient());
    private ScratchProgramAdapter adapter;
    private Button convertButton;
    private FetchScratchProgramDetailsTask fetchRemixesTask = new FetchScratchProgramDetailsTask();
    private ScratchProgramData programData;
    private ProgressDialog progressDialog;

    @Override // org.catrobat.catroid.scratchconverter.Client.ProjectDownloadCallback
    public void onDownloadProgress(int progress, String url) {
    }

    @Override // org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter.OnItemClickListener
    public void onItemLongClick(ScratchProgramData item, CheckableViewHolder h) {
    }

    @Override // org.catrobat.catroid.ui.scratchconverter.JobViewListener
    public void onJobFinished(final Job job) {
    }

    @Override // org.catrobat.catroid.ui.scratchconverter.JobViewListener
    public void onJobOutput(final Job job, final String[] lines) {
    }

    @Override // org.catrobat.catroid.ui.scratchconverter.JobViewListener
    public void onJobProgress(final Job job, final short progress) {
    }

    @Override // org.catrobat.catroid.ui.scratchconverter.JobViewListener
    public void onJobReady(final Job job) {
    }

    @Override // org.catrobat.catroid.ui.scratchconverter.JobViewListener
    public void onJobStarted(final Job job) {
    }

    @Override // org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter.OnItemClickListener
    public void onSettingsClick(ScratchProgramData item, View view) {
    }

    public static void setConversionManager(final ConversionManager manager) {
        conversionManager = manager;
    }

    @Override // org.catrobat.catroid.ui.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scratch_project_details);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ScratchProgramData scratchProgramData = (ScratchProgramData) getIntent().getParcelableExtra(Constants.INTENT_SCRATCH_PROGRAM_DATA);
        this.programData = scratchProgramData;
        Preconditions.checkState(scratchProgramData != null);
        this.convertButton = (Button) findViewById(R.id.convert_button);
        ((TextView) findViewById(R.id.project_title_view)).setText(this.programData.getTitle());
        ((TextView) findViewById(R.id.instructions_view)).setText(Parameters.DEFAULT_OPTION_PREFIXES);
        if (conversionManager.isJobInProgress(this.programData.getId())) {
            onJobInProgress();
        } else if (conversionManager.isJobDownloading(this.programData.getId())) {
            onJobDownloading();
        } else {
            onJobNotInProgress();
        }
        conversionManager.addJobViewListener(this.programData.getId(), this);
        conversionManager.addGlobalDownloadCallback(this);
        this.convertButton.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.ScratchProgramDetailsActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (ScratchProgramDetailsActivity.conversionManager.getNumberOfJobsInProgress() >= 3) {
                    ToastUtil.showError(ScratchProgramDetailsActivity.this.getApplicationContext(), ScratchProgramDetailsActivity.this.getResources().getQuantityString(R.plurals.error_cannot_convert_more_than_x_programs, 3, 3));
                } else {
                    ScratchProgramDetailsActivity scratchProgramDetailsActivity = ScratchProgramDetailsActivity.this;
                    scratchProgramDetailsActivity.convertProgram(scratchProgramDetailsActivity.programData);
                }
            }
        });
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_remixes);
        ScratchProgramAdapter scratchProgramAdapter = new ScratchProgramAdapter(new ArrayList());
        this.adapter = scratchProgramAdapter;
        scratchProgramAdapter.setOnItemClickListener(this);
        this.adapter.showRipples = false;
        this.adapter.showSettings = false;
        recyclerView.setAdapter(this.adapter);
        if (this.programData.getImage() != null && this.programData.getImage().getUrl() != null) {
            Picasso.get().load(Utils.changeSizeOfScratchImageURL(this.programData.getImage().getUrl().toString(), getResources().getDimensionPixelSize(R.dimen.scratch_project_image_height))).into((ImageView) findViewById(R.id.project_image_view));
        }
        this.fetchRemixesTask.setContext(this).setDelegate(this).setFetcher(dataFetcher);
        this.fetchRemixesTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, Long.valueOf(this.programData.getId()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void convertProgram(ScratchProgramData item) {
        if (conversionManager.getNumberOfJobsInProgress() > 3) {
            ToastUtil.showError(this, getResources().getQuantityString(R.plurals.error_cannot_convert_more_than_x_programs, 3, 3));
            return;
        }
        if (Utils.isDeprecatedScratchProgram(item)) {
            ToastUtil.showError(this, getString(R.string.error_cannot_convert_deprecated_scratch_program_x_x, new Object[]{item.getTitle(), DateFormat.getDateInstance().format(Utils.getScratchSecondReleasePublishedDate())}));
        } else if (conversionManager.isJobInProgress(item.getId())) {
            onJobInProgress();
        } else if (conversionManager.isJobDownloading(item.getId())) {
            onJobDownloading();
        } else {
            conversionManager.convertProgram(item.getId(), item.getTitle(), item.getImage(), false);
            ToastUtil.showSuccess(this, getResources().getQuantityString(R.plurals.scratch_conversion_scheduled_x, 1, 1));
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStart() {
        super.onStart();
        conversionManager.setCurrentActivity(this);
    }

    @Override // org.catrobat.catroid.ui.BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        conversionManager.removeJobViewListener(this.programData.getId(), this);
        conversionManager.removeGlobalDownloadCallback(this);
        this.fetchRemixesTask.cancel(true);
        this.progressDialog.dismiss();
    }

    @Override // org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter.OnItemClickListener
    public void onItemClick(ScratchProgramData item, MultiSelectionManager selectionManager) {
        Intent intent = new Intent(this, (Class<?>) ScratchProgramDetailsActivity.class);
        intent.putExtra(Constants.INTENT_SCRATCH_PROGRAM_DATA, (Parcelable) item);
        startActivityForResult(intent, 1);
    }

    private void onJobNotInProgress() {
        this.convertButton.setEnabled(true);
        this.convertButton.setText(R.string.convert);
    }

    private void onJobInProgress() {
        this.convertButton.setEnabled(false);
        this.convertButton.setText(R.string.converting);
    }

    private void onJobDownloading() {
        this.convertButton.setEnabled(false);
        this.convertButton.setText(R.string.status_downloading);
    }

    @Override // org.catrobat.catroid.transfers.FetchScratchProgramDetailsTask.ScratchProgramListTaskDelegate
    public void onPreExecute() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        this.progressDialog = progressDialog;
        progressDialog.setCancelable(false);
        this.progressDialog.setMessage(getString(R.string.loading));
        this.progressDialog.show();
    }

    @Override // org.catrobat.catroid.transfers.FetchScratchProgramDetailsTask.ScratchProgramListTaskDelegate
    public void onPostExecute(final ScratchProgramData programData) {
        this.progressDialog.dismiss();
        if (programData == null) {
            ToastUtil.showError(this, R.string.error_scratch_project_data_not_available);
        } else {
            this.programData = programData;
            onProgramDataUpdated();
        }
    }

    private void onProgramDataUpdated() {
        ((TextView) findViewById(R.id.project_title_view)).setText(this.programData.getTitle());
        ((TextView) findViewById(R.id.owner_view)).setText(getString(R.string.by_x, new Object[]{this.programData.getOwner()}));
        TextView textView = (TextView) findViewById(R.id.credits_view);
        if (this.programData.getNotesAndCredits() != null && this.programData.getNotesAndCredits().length() > 0) {
            String strReplace = this.programData.getNotesAndCredits().replace("\n\n", "\n");
            findViewById(R.id.credits_title_view).setVisibility(0);
            textView.setText(strReplace);
            textView.setVisibility(0);
        } else {
            findViewById(R.id.credits_title_view).setVisibility(8);
            textView.setVisibility(8);
        }
        TextView textView2 = (TextView) findViewById(R.id.instructions_view);
        String instructions = this.programData.getInstructions();
        String str = Parameters.DEFAULT_OPTION_PREFIXES;
        if (instructions != null) {
            String strReplace2 = this.programData.getInstructions().replace("\n\n", "\n");
            if (strReplace2.length() > 0) {
                str = strReplace2;
            }
            textView2.setText(str);
        } else {
            textView2.setText(Parameters.DEFAULT_OPTION_PREFIXES);
        }
        ((TextView) findViewById(R.id.scratch_project_favorites_text)).setText(NumberFormats.toMetricUnitRepresentation(this.programData.getFavorites()));
        ((TextView) findViewById(R.id.scratch_project_loves_text)).setText(NumberFormats.toMetricUnitRepresentation(this.programData.getLoves()));
        ((TextView) findViewById(R.id.scratch_project_views_text)).setText(NumberFormats.toMetricUnitRepresentation(this.programData.getViews()));
        TextView textView3 = (TextView) findViewById(R.id.date_shared_view);
        TextView textView4 = (TextView) findViewById(R.id.date_modified_view);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.dates_view);
        relativeLayout.setVisibility(8);
        if (this.programData.getSharedDate() != null) {
            textView3.setText(getString(R.string.shared_at_x, new Object[]{DateFormat.getDateInstance(1, Locale.getDefault()).format(this.programData.getSharedDate())}));
            textView3.setVisibility(0);
            relativeLayout.setVisibility(0);
        } else {
            textView3.setVisibility(8);
        }
        if (this.programData.getModifiedDate() != null) {
            textView4.setText(getString(R.string.modified_at_x, new Object[]{DateFormat.getDateInstance(1, Locale.getDefault()).format(this.programData.getSharedDate())}));
            textView4.setVisibility(0);
            relativeLayout.setVisibility(0);
        } else {
            textView4.setVisibility(8);
        }
        findViewById(R.id.project_details_layout).setVisibility(0);
        ScratchVisibilityState visibilityState = this.programData.getVisibilityState();
        if (visibilityState != null && visibilityState != ScratchVisibilityState.PUBLIC) {
            findViewById(R.id.privacy_warning).setVisibility(0);
            this.convertButton.setVisibility(8);
        } else {
            findViewById(R.id.privacy_warning).setVisibility(8);
            this.convertButton.setVisibility(0);
        }
        if (this.programData.getRemixes().size() > 0) {
            findViewById(R.id.remixes_title_view).setVisibility(0);
            findViewById(R.id.recycler_view_remixes).setVisibility(0);
            this.adapter.setItems(this.programData.getRemixes());
            return;
        }
        findViewById(R.id.remixes_title_view).setVisibility(8);
    }

    @Override // org.catrobat.catroid.ui.scratchconverter.JobViewListener
    public void onJobScheduled(final Job job) {
        if (job.getJobID() == this.programData.getId()) {
            onJobInProgress();
        }
    }

    @Override // org.catrobat.catroid.ui.scratchconverter.JobViewListener
    public void onJobFailed(final Job job) {
        if (job.getJobID() == this.programData.getId()) {
            onJobNotInProgress();
        }
    }

    @Override // org.catrobat.catroid.ui.scratchconverter.JobViewListener
    public void onUserCanceledJob(final Job job) {
        if (job.getJobID() == this.programData.getId()) {
            onJobNotInProgress();
        }
    }

    @Override // org.catrobat.catroid.scratchconverter.Client.ProjectDownloadCallback
    public void onDownloadStarted(final String url) {
        if (Utils.extractScratchJobIDFromURL(url) == this.programData.getId()) {
            onJobDownloading();
        }
    }

    @Override // org.catrobat.catroid.scratchconverter.Client.ProjectDownloadCallback
    public void onDownloadFinished(final String catrobatProgramName, final String url) {
        if (Utils.extractScratchJobIDFromURL(url) == this.programData.getId()) {
            onJobNotInProgress();
        }
    }

    @Override // org.catrobat.catroid.scratchconverter.Client.ProjectDownloadCallback
    public void onUserCanceledDownload(final String url) {
        if (Utils.extractScratchJobIDFromURL(url) == this.programData.getId()) {
            onJobNotInProgress();
        }
    }
}
