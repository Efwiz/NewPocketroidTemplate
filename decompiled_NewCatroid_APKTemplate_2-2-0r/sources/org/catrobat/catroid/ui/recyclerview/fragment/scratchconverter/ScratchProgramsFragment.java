package org.catrobat.catroid.ui.recyclerview.fragment.scratchconverter;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import java.io.File;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.common.FlavoredConstants;
import org.catrobat.catroid.io.asynctask.ProjectLoader;
import org.catrobat.catroid.scratchconverter.protocol.Job;
import org.catrobat.catroid.ui.ProjectActivity;
import org.catrobat.catroid.ui.ScratchConverterActivity;
import org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter;
import org.catrobat.catroid.ui.recyclerview.adapter.ScratchJobAdapter;
import org.catrobat.catroid.ui.recyclerview.adapter.multiselection.MultiSelectionManager;
import org.catrobat.catroid.ui.recyclerview.viewholder.CheckableViewHolder;
import org.catrobat.catroid.utils.FileMetaDataExtractor;
import org.catrobat.catroid.utils.ToastUtil;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/fragment/scratchconverter/ScratchProgramsFragment.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ScratchProgramsFragment extends Fragment implements ScratchConverterActivity.OnJobListListener, RVAdapter.OnItemClickListener<Job>, ProjectLoader.ProjectLoadListener {
    public static final String TAG = "ScratchProgramsFragment";
    private ScratchJobAdapter finishedJobsAdapter;
    private LinearLayout finishedJobsLayout;
    private RecyclerView finishedJobsRecyclerView;
    private View parent;
    private ScratchJobAdapter runningJobsAdapter;
    private LinearLayout runningJobsLayout;
    private RecyclerView runningJobsRecyclerView;

    @Override // org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter.OnItemClickListener
    public void onItemLongClick(Job item, CheckableViewHolder holder) {
    }

    @Override // org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter.OnItemClickListener
    public void onSettingsClick(Job item, View view) {
    }

    public void initializeAdapters(ScratchJobAdapter runningJobsAdapter, ScratchJobAdapter finishedJobsAdapter) {
        this.runningJobsAdapter = runningJobsAdapter;
        this.finishedJobsAdapter = finishedJobsAdapter;
        runningJobsAdapter.setOnItemClickListener(this);
        finishedJobsAdapter.setOnItemClickListener(this);
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewInflate = inflater.inflate(R.layout.fragment_scratch_programs, container, false);
        this.parent = viewInflate;
        this.runningJobsLayout = (LinearLayout) viewInflate.findViewById(R.id.programs_in_progress);
        this.finishedJobsLayout = (LinearLayout) this.parent.findViewById(R.id.programs_finished);
        this.runningJobsRecyclerView = (RecyclerView) this.parent.findViewById(R.id.recycler_view_in_progress);
        this.finishedJobsRecyclerView = (RecyclerView) this.parent.findViewById(R.id.recycler_view_finished);
        return this.parent;
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.runningJobsRecyclerView.setAdapter(this.runningJobsAdapter);
        this.finishedJobsRecyclerView.setAdapter(this.finishedJobsAdapter);
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        setShowProgressBar(false);
    }

    @Override // org.catrobat.catroid.ui.ScratchConverterActivity.OnJobListListener
    public void onJobListChanged() {
        this.runningJobsAdapter.notifyDataSetChanged();
        this.finishedJobsAdapter.notifyDataSetChanged();
    }

    @Override // org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter.OnItemClickListener
    public void onItemClick(Job item, MultiSelectionManager selectionManager) {
        if (item.getState() == Job.State.FAILED) {
            ToastUtil.showError(getActivity(), R.string.error_cannot_open_failed_scratch_program);
            return;
        }
        if (item.getDownloadState() == Job.DownloadState.DOWNLOADING) {
            new AlertDialog.Builder(getActivity()).setTitle(R.string.warning).setMessage(R.string.error_cannot_open_currently_downloading_scratch_program).setNeutralButton(R.string.close, (DialogInterface.OnClickListener) null).create().show();
            return;
        }
        if (item.getDownloadState() == Job.DownloadState.NOT_READY || item.getDownloadState() == Job.DownloadState.CANCELED) {
            new AlertDialog.Builder(getContext()).setTitle(R.string.warning).setMessage(R.string.error_cannot_open_not_yet_downloaded_scratch_program).setNeutralButton(R.string.close, (DialogInterface.OnClickListener) null).create().show();
            return;
        }
        File file = new File(FlavoredConstants.DEFAULT_ROOT_DIRECTORY, FileMetaDataExtractor.encodeSpecialCharsForFileSystem(item.getTitle()));
        if (!file.exists()) {
            new AlertDialog.Builder(getContext()).setTitle(R.string.warning).setMessage(R.string.error_cannot_open_not_existing_scratch_program).setNeutralButton(R.string.close, (DialogInterface.OnClickListener) null).create().show();
        } else {
            setShowProgressBar(true);
            new ProjectLoader(file, getContext()).setListener(this).loadProjectAsync();
        }
    }

    public void setShowProgressBar(boolean show) {
        this.parent.findViewById(R.id.progress_bar).setVisibility(show ? 0 : 8);
        this.runningJobsLayout.setVisibility(show ? 8 : 0);
        this.finishedJobsLayout.setVisibility(show ? 8 : 0);
    }

    @Override // org.catrobat.catroid.io.asynctask.ProjectLoader.ProjectLoadListener
    public void onLoadFinished(boolean success) {
        if (success) {
            Intent intent = new Intent(getActivity(), (Class<?>) ProjectActivity.class);
            intent.putExtra("fragmentPosition", 0);
            startActivity(intent);
        } else {
            setShowProgressBar(false);
            ToastUtil.showError(getContext(), R.string.error_load_project);
        }
    }
}
