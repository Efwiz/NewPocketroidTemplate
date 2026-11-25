package org.catrobat.catroid.ui.recyclerview.fragment.scratchconverter;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.ScratchProgramData;
import org.catrobat.catroid.common.ScratchSearchResult;
import org.catrobat.catroid.common.SharedPreferenceKeys;
import org.catrobat.catroid.scratchconverter.ConversionManager;
import org.catrobat.catroid.transfers.SearchScratchProgramsTask;
import org.catrobat.catroid.ui.ScratchProgramDetailsActivity;
import org.catrobat.catroid.ui.ViewUtils;
import org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter;
import org.catrobat.catroid.ui.recyclerview.adapter.ScratchProgramAdapter;
import org.catrobat.catroid.ui.recyclerview.adapter.multiselection.MultiSelectionManager;
import org.catrobat.catroid.ui.recyclerview.viewholder.CheckableViewHolder;
import org.catrobat.catroid.utils.ToastUtil;
import org.catrobat.catroid.utils.Utils;
import org.catrobat.catroid.web.CatrobatWebClient;
import org.catrobat.catroid.web.ScratchDataFetcher;
import org.catrobat.catroid.web.ServerCalls;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/fragment/scratchconverter/ScratchSearchResultsFragment.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ScratchSearchResultsFragment extends Fragment implements ActionMode.Callback, RVAdapter.SelectionListener, RVAdapter.OnItemClickListener<ScratchProgramData> {
    private static final int CONVERT = 1;
    private static final int NONE = 0;
    public static final String TAG = "ScratchSearchResultsFragment";
    private ActionMode actionMode;
    private ScratchProgramAdapter adapter;
    private ConversionManager conversionManager;
    private RecyclerView recyclerView;
    private SearchScratchProgramsTask searchTask;
    private SearchView searchView;
    private ScratchDataFetcher dataFetcher = new ServerCalls(CatrobatWebClient.INSTANCE.getClient());
    private OnQueryListener onQueryListener = new OnQueryListener();
    private SearchTaskDelegate searchTaskDelegate = new SearchTaskDelegate();
    private int actionModeType = 0;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/fragment/scratchconverter/ScratchSearchResultsFragment$ActionModeType.class
      classes14.dex
     */
    @Retention(RetentionPolicy.SOURCE)
    @interface ActionModeType {
    }

    @Override // org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter.OnItemClickListener
    public void onItemLongClick(final ScratchProgramData item, CheckableViewHolder holder) {
    }

    @Override // android.view.ActionMode.Callback
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/fragment/scratchconverter/ScratchSearchResultsFragment$OnQueryListener.class
      classes14.dex
     */
    class OnQueryListener implements SearchView.OnQueryTextListener {
        @Override // android.widget.SearchView.OnQueryTextListener
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        OnQueryListener() {
        }

        @Override // android.widget.SearchView.OnQueryTextListener
        public boolean onQueryTextChange(String newText) {
            if (newText.isEmpty()) {
                ScratchSearchResultsFragment.this.searchTaskDelegate.startSearch(null);
                return false;
            }
            ScratchSearchResultsFragment.this.searchTaskDelegate.startSearch(newText);
            return false;
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/fragment/scratchconverter/ScratchSearchResultsFragment$SearchTaskDelegate.class
      classes14.dex
     */
    class SearchTaskDelegate implements SearchScratchProgramsTask.SearchScratchProgramsTaskDelegate {
        @Override // org.catrobat.catroid.transfers.SearchScratchProgramsTask.SearchScratchProgramsTaskDelegate
        public void onPreExecute() {
        }

        SearchTaskDelegate() {
        }

        void startSearch(String query) {
            clearPendingSearch();
            ScratchSearchResultsFragment.this.searchTask = new SearchScratchProgramsTask().setDelegate(this).setFetcher(ScratchSearchResultsFragment.this.dataFetcher);
            if (query != null) {
                ScratchSearchResultsFragment.this.searchTask.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, query);
            } else {
                ScratchSearchResultsFragment.this.searchTask.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, new String[0]);
            }
        }

        void clearPendingSearch() {
            if (ScratchSearchResultsFragment.this.searchTask != null) {
                ScratchSearchResultsFragment.this.searchTask.cancel(true);
            }
        }

        @Override // org.catrobat.catroid.transfers.SearchScratchProgramsTask.SearchScratchProgramsTaskDelegate
        public void onPostExecute(ScratchSearchResult result) {
            if (result == null) {
                ToastUtil.showError(ScratchSearchResultsFragment.this.getActivity(), R.string.search_failed);
            } else if (result.getProgramDataList() != null) {
                ScratchSearchResultsFragment.this.adapter.setItems(result.getProgramDataList());
            }
        }
    }

    public void setConversionManager(ConversionManager conversionManager) {
        this.conversionManager = conversionManager;
    }

    @Override // android.view.ActionMode.Callback
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        int i = this.actionModeType;
        if (i == 0) {
            return false;
        }
        if (i == 1) {
            mode.setTitle(getString(R.string.am_convert));
        }
        mode.getMenuInflater().inflate(R.menu.context_menu, menu);
        this.adapter.showCheckBoxes = true;
        this.adapter.notifyDataSetChanged();
        return true;
    }

    @Override // android.view.ActionMode.Callback
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        if (item.getItemId() != R.id.confirm) {
            return false;
        }
        handleContextualAction();
        return true;
    }

    @Override // android.view.ActionMode.Callback
    public void onDestroyActionMode(ActionMode mode) {
        resetActionModeParameters();
        this.adapter.clearSelection();
    }

    private void handleContextualAction() {
        if (this.adapter.getSelectedItems().isEmpty()) {
            this.actionMode.finish();
            return;
        }
        int i = this.actionModeType;
        if (i == 0) {
            throw new IllegalStateException("ActionModeType not set Correctly");
        }
        if (i != 1) {
            return;
        }
        convertItems(this.adapter.getSelectedItems());
    }

    private void resetActionModeParameters() {
        this.actionModeType = 0;
        this.actionMode = null;
        this.adapter.showCheckBoxes = false;
        this.adapter.selectionMode = 2;
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewInflate = inflater.inflate(R.layout.fragment_scratch_results, container, false);
        this.recyclerView = (RecyclerView) viewInflate.findViewById(R.id.recycler_view);
        this.searchView = (SearchView) viewInflate.findViewById(R.id.search_view_scratch);
        setHasOptionsMenu(true);
        return viewInflate;
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle savedInstance) {
        super.onActivityCreated(savedInstance);
        ScratchProgramAdapter scratchProgramAdapter = new ScratchProgramAdapter(new ArrayList());
        this.adapter = scratchProgramAdapter;
        scratchProgramAdapter.showDetails = PreferenceManager.getDefaultSharedPreferences(getActivity()).getBoolean(SharedPreferenceKeys.SHOW_DETAILS_SCRATCH_PROJECTS_PREFERENCE_KEY, false);
        this.recyclerView.setAdapter(this.adapter);
        this.adapter.setSelectionListener(this);
        this.adapter.setOnItemClickListener(this);
        this.adapter.showRipples = false;
        this.searchView.setOnQueryTextListener(this.onQueryListener);
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        ViewUtils.showKeyboard(this.searchView);
        this.searchTaskDelegate.startSearch(null);
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        ViewUtils.hideKeyboard(getView());
    }

    @Override // androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        this.searchTaskDelegate.clearPendingSearch();
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_scratch_projects, menu);
    }

    @Override // androidx.fragment.app.Fragment
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        this.adapter.showDetails = PreferenceManager.getDefaultSharedPreferences(getActivity()).getBoolean(SharedPreferenceKeys.SHOW_DETAILS_SCRATCH_PROJECTS_PREFERENCE_KEY, false);
        menu.findItem(R.id.show_details).setTitle(this.adapter.showDetails ? R.string.hide_details : R.string.show_details);
    }

    @Override // androidx.fragment.app.Fragment
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.convert) {
            startActionMode(1);
        } else if (itemId == R.id.show_details) {
            ScratchProgramAdapter scratchProgramAdapter = this.adapter;
            scratchProgramAdapter.showDetails = !scratchProgramAdapter.showDetails;
            PreferenceManager.getDefaultSharedPreferences(getActivity()).edit().putBoolean(SharedPreferenceKeys.SHOW_DETAILS_SCRATCH_PROJECTS_PREFERENCE_KEY, this.adapter.showDetails).apply();
            this.adapter.notifyDataSetChanged();
        } else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void startActionMode(int type) {
        if (this.adapter.getItems().isEmpty()) {
            ToastUtil.showError(getActivity(), R.string.am_empty_list);
            resetActionModeParameters();
        } else {
            this.actionModeType = type;
            this.actionMode = getActivity().startActionMode(this);
        }
    }

    private void finishActionMode() {
        this.adapter.clearSelection();
        if (this.actionModeType != 0) {
            this.actionMode.finish();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void convertItems(List<ScratchProgramData> selectedItems) {
        if (this.conversionManager.getNumberOfJobsInProgress() > 3) {
            ToastUtil.showError(getActivity(), getResources().getQuantityString(R.plurals.error_cannot_convert_more_than_x_programs, 3, 3));
            return;
        }
        int i = 0;
        for (ScratchProgramData scratchProgramData : selectedItems) {
            if (Utils.isDeprecatedScratchProgram(scratchProgramData)) {
                ToastUtil.showError(getActivity(), getString(R.string.error_cannot_convert_deprecated_scratch_program_x_x, scratchProgramData.getTitle(), DateFormat.getDateInstance().format(Utils.getScratchSecondReleasePublishedDate())));
            } else if (!this.conversionManager.isJobInProgress(scratchProgramData.getId())) {
                this.conversionManager.convertProgram(scratchProgramData.getId(), scratchProgramData.getTitle(), scratchProgramData.getImage(), false);
                i++;
            }
        }
        if (i > 0) {
            ToastUtil.showSuccess(getActivity(), getResources().getQuantityString(R.plurals.scratch_conversion_scheduled_x, i, Integer.valueOf(i)));
        }
        finishActionMode();
    }

    @Override // org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter.SelectionListener
    public void onSelectionChanged(int selectedItemCnt) {
        if (this.actionModeType == 1) {
            this.actionMode.setTitle(getString(R.string.am_convert) + " " + selectedItemCnt);
            return;
        }
        throw new IllegalStateException("ActionModeType not set correctly");
    }

    @Override // org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter.OnItemClickListener
    public void onItemClick(ScratchProgramData item, MultiSelectionManager selectionManager) {
        if (this.actionModeType == 0) {
            ScratchProgramDetailsActivity.setConversionManager(this.conversionManager);
            Intent intent = new Intent(getActivity(), (Class<?>) ScratchProgramDetailsActivity.class);
            intent.putExtra(Constants.INTENT_SCRATCH_PROGRAM_DATA, (Parcelable) item);
            startActivity(intent);
        }
    }

    @Override // org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter.OnItemClickListener
    public void onSettingsClick(final ScratchProgramData item, View view) {
        PopupMenu popupMenu = new PopupMenu(getContext(), view);
        popupMenu.getMenu().add(getString(R.string.convert));
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.scratchconverter.ScratchSearchResultsFragment.1
            @Override // android.widget.PopupMenu.OnMenuItemClickListener
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (!ScratchSearchResultsFragment.this.getString(R.string.convert).equals(menuItem.getTitle())) {
                    return true;
                }
                ScratchSearchResultsFragment.this.convertItems(new ArrayList(Collections.singletonList(item)));
                return true;
            }
        });
        popupMenu.show();
    }
}
