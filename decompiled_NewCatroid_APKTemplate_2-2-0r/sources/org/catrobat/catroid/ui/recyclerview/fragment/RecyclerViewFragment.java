package org.catrobat.catroid.ui.recyclerview.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.common.Nameable;
import org.catrobat.catroid.common.SharedPreferenceKeys;
import org.catrobat.catroid.merge.NewProjectNameTextWatcher;
import org.catrobat.catroid.ui.BottomBar;
import org.catrobat.catroid.ui.controller.BackpackListManager;
import org.catrobat.catroid.ui.recyclerview.adapter.ExtendedRVAdapter;
import org.catrobat.catroid.ui.recyclerview.adapter.MultiViewSpriteAdapter;
import org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter;
import org.catrobat.catroid.ui.recyclerview.adapter.draganddrop.TouchHelperCallback;
import org.catrobat.catroid.ui.recyclerview.adapter.multiselection.MultiSelectionManager;
import org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog;
import org.catrobat.catroid.ui.recyclerview.dialog.textwatcher.DuplicateInputTextWatcher;
import org.catrobat.catroid.ui.recyclerview.util.UniqueNameProvider;
import org.catrobat.catroid.ui.recyclerview.viewholder.CheckableViewHolder;
import org.catrobat.catroid.utils.ToastUtil;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/fragment/RecyclerViewFragment.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public abstract class RecyclerViewFragment<T extends Nameable> extends Fragment implements ActionMode.Callback, RVAdapter.SelectionListener, RVAdapter.OnItemClickListener<T> {
    protected static final int BACKPACK = 1;
    protected static final int COPY = 2;
    protected static final int DELETE = 3;
    protected static final int IMPORT_LOCAL = 6;
    protected static final int MERGE = 5;
    protected static final int NONE = 0;
    protected static final int RENAME = 4;
    private static final String TAG = "RecyclerViewFragment";
    protected ActionMode actionMode;
    protected ExtendedRVAdapter<T> adapter;
    protected TextView emptyView;
    protected View parentView;
    protected RecyclerView recyclerView;
    protected ItemTouchHelper touchHelper;
    protected String sharedPreferenceDetailsKey = "";
    protected UniqueNameProvider uniqueNameProvider = new UniqueNameProvider();
    protected int itemCountThreshold = 1;
    protected RecyclerView.AdapterDataObserver observer = new RecyclerView.AdapterDataObserver() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment.1
        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public void onChanged() {
            super.onChanged();
            RecyclerViewFragment recyclerViewFragment = RecyclerViewFragment.this;
            recyclerViewFragment.setShowEmptyView(recyclerViewFragment.shouldShowEmptyView());
        }
    };
    protected int actionModeType = 0;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/fragment/RecyclerViewFragment$ActionModeType.class
      classes14.dex
     */
    @Retention(RetentionPolicy.SOURCE)
    @interface ActionModeType {
    }

    protected abstract void copyItems(List<T> selectedItems);

    protected abstract void deleteItems(List<T> selectedItems);

    protected abstract int getDeleteAlertTitleId();

    protected abstract int getRenameDialogHint();

    protected abstract int getRenameDialogTitle();

    protected abstract void initializeAdapter();

    protected abstract boolean isBackpackEmpty();

    protected abstract void packItems(List<T> selectedItems);

    protected abstract void switchToBackpack();

    boolean shouldShowEmptyView() {
        return this.adapter.getItemCount() == 0;
    }

    @Override // android.view.ActionMode.Callback
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        this.actionMode = mode;
        mode.getMenuInflater().inflate(R.menu.context_menu, menu);
        switch (this.actionModeType) {
            case 0:
            case 6:
                return false;
            case 1:
                mode.setTitle(getString(R.string.am_backpack));
                break;
            case 2:
                mode.setTitle(getString(R.string.am_copy));
                break;
            case 3:
                mode.setTitle(getString(R.string.am_delete));
                break;
            case 4:
                mode.setTitle(getString(R.string.am_rename));
                onRename(menu);
                return true;
            case 5:
                this.adapter.selectionMode = 1;
                mode.setTitle(R.string.am_merge);
                break;
        }
        this.adapter.showSettings = false;
        this.adapter.showRipples = false;
        this.adapter.showCheckBoxes = true;
        this.adapter.notifyDataSetChanged();
        return true;
    }

    private void onRename(Menu menu) {
        this.adapter.selectionMode = 0;
        this.adapter.showSettings = false;
        this.adapter.showRipples = false;
        menu.findItem(R.id.confirm).setVisible(false);
        menu.findItem(R.id.overflow).setVisible(false);
        menu.findItem(R.id.toggle_selection).setVisible(false);
        if (this instanceof SpriteListFragment) {
            ((MultiViewSpriteAdapter) this.adapter).setBackgroundVisible(8);
            if (((SpriteListFragment) this).isSingleVisibleSprite()) {
                showRenameDialog(this.adapter.getItems().get(1));
            }
        } else if (this.adapter.getItemCount() == 1) {
            showRenameDialog(this.adapter.getItems().get(0));
        }
        this.adapter.notifyDataSetChanged();
    }

    @Override // android.view.ActionMode.Callback
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        this.actionMode = mode;
        updateSelectionToggle(menu);
        return true;
    }

    @Override // android.view.ActionMode.Callback
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        this.actionMode = mode;
        int itemId = item.getItemId();
        if (itemId == R.id.confirm) {
            handleContextualAction();
            return true;
        }
        if (itemId != R.id.toggle_selection) {
            return false;
        }
        this.adapter.toggleSelection();
        updateSelectionToggle(this.actionMode.getMenu());
        return true;
    }

    @Override // android.view.ActionMode.Callback
    public void onDestroyActionMode(ActionMode mode) {
        resetActionModeParameters();
        this.adapter.clearSelection();
        BottomBar.showBottomBar(getActivity());
        if (this instanceof SpriteListFragment) {
            ((MultiViewSpriteAdapter) this.adapter).setBackgroundVisible(0);
        }
    }

    private void handleContextualAction() {
        if (this.adapter.getSelectedItems().isEmpty()) {
            ActionMode actionMode = this.actionMode;
            if (actionMode != null) {
                actionMode.finish();
                return;
            }
            return;
        }
        int i = this.actionModeType;
        if (i != 0) {
            if (i == 1) {
                packItems(this.adapter.getSelectedItems());
                return;
            }
            if (i == 2) {
                copyItems(this.adapter.getSelectedItems());
                return;
            }
            if (i == 3) {
                showDeleteAlert(this.adapter.getSelectedItems());
                return;
            } else if (i == 5) {
                showMergeDialog(this.adapter.getSelectedItems());
                return;
            } else if (i != 6) {
                return;
            }
        }
        throw new IllegalStateException("ActionModeType not set correctly");
    }

    protected void resetActionModeParameters() {
        this.actionModeType = 0;
        this.actionMode = null;
        this.adapter.showCheckBoxes = false;
        this.adapter.showSettings = true;
        this.adapter.showRipples = true;
        this.adapter.selectionMode = 2;
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewInflate = inflater.inflate(R.layout.fragment_list_view, container, false);
        this.parentView = viewInflate;
        this.recyclerView = (RecyclerView) viewInflate.findViewById(R.id.recycler_view);
        this.emptyView = (TextView) this.parentView.findViewById(R.id.empty_view);
        setShowProgressBar(true);
        setHasOptionsMenu(true);
        return this.parentView;
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle savedInstance) {
        super.onActivityCreated(savedInstance);
        if (getActivity().isFinishing()) {
            return;
        }
        initializeAdapter();
    }

    public void onAdapterReady() {
        this.adapter.showDetails = PreferenceManager.getDefaultSharedPreferences(getActivity()).getBoolean(this.sharedPreferenceDetailsKey, false);
        this.recyclerView.setAdapter(this.adapter);
        this.adapter.projectsSorted = PreferenceManager.getDefaultSharedPreferences(getActivity()).getBoolean(SharedPreferenceKeys.SORT_PROJECTS_PREFERENCE_KEY, false);
        this.adapter.setSelectionListener(this);
        this.adapter.setOnItemClickListener(this);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new TouchHelperCallback(this.adapter));
        this.touchHelper = itemTouchHelper;
        itemTouchHelper.attachToRecyclerView(this.recyclerView);
        setShowProgressBar(false);
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        BackpackListManager.getInstance().loadBackpack();
        this.adapter.notifyDataSetChanged();
        this.adapter.registerAdapterDataObserver(this.observer);
        setShowEmptyView(shouldShowEmptyView());
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        try {
            this.adapter.unregisterAdapterDataObserver(this.observer);
        } catch (IllegalStateException unused) {
            Log.d(TAG, "Observer was not registered");
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        finishActionMode();
    }

    @Override // androidx.fragment.app.Fragment
    public void onPrepareOptionsMenu(Menu menu) {
        ExtendedRVAdapter<T> extendedRVAdapter;
        super.onPrepareOptionsMenu(menu);
        FragmentActivity activity = getActivity();
        if (activity == null || (extendedRVAdapter = this.adapter) == null) {
            return;
        }
        extendedRVAdapter.showDetails = PreferenceManager.getDefaultSharedPreferences(activity).getBoolean(this.sharedPreferenceDetailsKey, false);
        if (menu.findItem(R.id.show_details) != null) {
            menu.findItem(R.id.show_details).setTitle(this.adapter.showDetails ? R.string.hide_details : R.string.show_details);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.backpack /* 2131361924 */:
                prepareBackpackActionMode();
                return true;
            case R.id.copy /* 2131364141 */:
                prepareActionMode(2);
                return true;
            case R.id.delete /* 2131364226 */:
                prepareActionMode(3);
                return true;
            case R.id.merge /* 2131364663 */:
                startActionMode(5);
                return true;
            case R.id.rename /* 2131365129 */:
                startActionMode(4);
                return true;
            case R.id.show_details /* 2131365216 */:
                ExtendedRVAdapter<T> extendedRVAdapter = this.adapter;
                extendedRVAdapter.showDetails = !extendedRVAdapter.showDetails;
                PreferenceManager.getDefaultSharedPreferences(getActivity()).edit().putBoolean(this.sharedPreferenceDetailsKey, this.adapter.showDetails).apply();
                this.adapter.notifyDataSetChanged();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void prepareActionMode(int type) {
        if (this.adapter.getItemCount() != this.itemCountThreshold) {
            startActionMode(type);
        } else if (type == 2) {
            copyItems(this.adapter.getItems().subList(this.itemCountThreshold - 1, this.adapter.getItemCount()));
        } else {
            if (type != 3) {
                return;
            }
            deleteItems(this.adapter.getItems().subList(this.itemCountThreshold - 1, this.adapter.getItemCount()));
        }
    }

    private void prepareBackpackActionMode() {
        this.adapter.getItemCount();
        if (isBackpackEmpty()) {
            startActionMode(1);
        } else if (this.adapter.getItems().isEmpty()) {
            switchToBackpack();
        } else {
            showBackpackModeChooser();
        }
    }

    private void startActionMode(int type) {
        if (this.adapter.getItems().isEmpty()) {
            ToastUtil.showError(getActivity(), R.string.am_empty_list);
            resetActionModeParameters();
        } else {
            this.actionModeType = type;
            this.actionMode = getActivity().startActionMode(this);
            BottomBar.hideBottomBar(getActivity());
        }
    }

    @Override // org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter.SelectionListener
    public void onSelectionChanged(int selectedItemCnt) {
        ActionMode actionMode;
        ActionMode actionMode2 = this.actionMode;
        if (actionMode2 != null && actionMode2.getMenu() != null) {
            updateSelectionToggle(this.actionMode.getMenu());
        }
        int i = this.actionModeType;
        if (i == 0) {
            throw new IllegalStateException("ActionModeType not set Correctly");
        }
        if (i == 1) {
            ActionMode actionMode3 = this.actionMode;
            if (actionMode3 != null) {
                actionMode3.setTitle(getString(R.string.am_backpack) + " " + selectedItemCnt);
                return;
            }
            return;
        }
        if (i == 2) {
            ActionMode actionMode4 = this.actionMode;
            if (actionMode4 != null) {
                actionMode4.setTitle(getString(R.string.am_copy) + " " + selectedItemCnt);
                return;
            }
            return;
        }
        if (i != 3) {
            if (i == 5 && (actionMode = this.actionMode) != null) {
                actionMode.setTitle(getString(R.string.am_merge) + " " + selectedItemCnt);
                return;
            }
            return;
        }
        ActionMode actionMode5 = this.actionMode;
        if (actionMode5 != null) {
            actionMode5.setTitle(getString(R.string.am_delete) + " " + selectedItemCnt);
        }
    }

    protected void updateSelectionToggle(Menu menu) {
        if (this.adapter.selectionMode == 2) {
            MenuItem menuItemFindItem = menu.findItem(R.id.toggle_selection);
            menuItemFindItem.setVisible(true);
            menu.findItem(R.id.overflow).setVisible(true);
            if (this.adapter.getSelectedItems().size() == this.adapter.getSelectableItemCount()) {
                menuItemFindItem.setTitle(R.string.deselect_all);
            } else {
                menuItemFindItem.setTitle(R.string.select_all);
            }
        }
    }

    protected void finishActionMode() {
        ActionMode actionMode;
        this.adapter.clearSelection();
        setShowProgressBar(false);
        int i = this.actionModeType;
        if (i == 0 || i == 6 || (actionMode = this.actionMode) == null) {
            return;
        }
        actionMode.finish();
    }

    public void setShowProgressBar(boolean show) {
        this.parentView.findViewById(R.id.progress_bar).setVisibility(show ? 0 : 8);
        this.recyclerView.setVisibility(show ? 8 : 0);
    }

    void setShowEmptyView(boolean visible) {
        this.emptyView.setVisibility(visible ? 0 : 8);
    }

    @Override // org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter.OnItemClickListener
    public void onItemClick(T item, MultiSelectionManager selectionManager) {
        if (this.actionModeType == 4) {
            showRenameDialog(item);
        } else {
            if (selectionManager == null) {
                return;
            }
            selectionManager.toggleSelection(this.adapter.getItems().indexOf(item));
            onSelectionChanged(selectionManager.getSelectedPositions().size());
            notifyDataSetChanged();
        }
    }

    @Override // org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter.OnItemClickListener
    public void onItemLongClick(T item, CheckableViewHolder holder) {
        this.touchHelper.startDrag(holder);
    }

    public void notifyDataSetChanged() {
        this.adapter.notifyDataSetChanged();
    }

    protected void showBackpackModeChooser() {
        new AlertDialog.Builder(requireContext()).setTitle(R.string.backpack_title).setItems(new CharSequence[]{getString(R.string.pack), getString(R.string.unpack)}, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.m5791x68f3499a(dialogInterface, i);
            }
        }).show();
    }

    /* JADX INFO: renamed from: lambda$showBackpackModeChooser$0$org-catrobat-catroid-ui-recyclerview-fragment-RecyclerViewFragment, reason: not valid java name */
    /* synthetic */ void m5791x68f3499a(DialogInterface dialogInterface, int i) {
        if (i == 0) {
            startActionMode(1);
        } else {
            if (i != 1) {
                return;
            }
            switchToBackpack();
        }
    }

    protected void showDeleteAlert(final List<T> selectedItems) {
        new AlertDialog.Builder(requireContext()).setTitle(getResources().getQuantityString(getDeleteAlertTitleId(), selectedItems.size())).setMessage(R.string.dialog_confirm_delete).setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.m5792x93f6afa0(selectedItems, dialogInterface, i);
            }
        }).setNegativeButton(R.string.cancel, (DialogInterface.OnClickListener) null).setCancelable(false).show();
    }

    /* JADX INFO: renamed from: lambda$showDeleteAlert$1$org-catrobat-catroid-ui-recyclerview-fragment-RecyclerViewFragment, reason: not valid java name */
    /* synthetic */ void m5792x93f6afa0(List list, DialogInterface dialogInterface, int i) {
        deleteItems(list);
    }

    protected void showRenameDialog(final T selectedItem) {
        TextInputDialog.Builder builder = new TextInputDialog.Builder(requireContext());
        builder.setHint(getString(getRenameDialogHint())).setText(selectedItem.getName()).setTextWatcher(new DuplicateInputTextWatcher(this.adapter.getItems())).setPositiveButton(getString(R.string.ok), new TextInputDialog.OnClickListener() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment$$ExternalSyntheticLambda3
            @Override // org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog.OnClickListener
            public final void onPositiveButtonClick(DialogInterface dialogInterface, String str) {
                this.f$0.m5794xef8d10f6(selectedItem, dialogInterface, str);
            }
        });
        builder.setTitle(getRenameDialogTitle()).setNegativeButton(R.string.cancel, (DialogInterface.OnClickListener) null).setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment$$ExternalSyntheticLambda4
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                this.f$0.m5795x8bfb0d55(dialogInterface);
            }
        }).show();
    }

    /* JADX INFO: renamed from: lambda$showRenameDialog$2$org-catrobat-catroid-ui-recyclerview-fragment-RecyclerViewFragment, reason: not valid java name */
    /* synthetic */ void m5794xef8d10f6(Nameable nameable, DialogInterface dialogInterface, String str) {
        renameItem(nameable, str);
    }

    /* JADX INFO: renamed from: lambda$showRenameDialog$3$org-catrobat-catroid-ui-recyclerview-fragment-RecyclerViewFragment, reason: not valid java name */
    /* synthetic */ void m5795x8bfb0d55(DialogInterface dialogInterface) {
        if (this instanceof SpriteListFragment) {
            ((MultiViewSpriteAdapter) this.adapter).setBackgroundVisible(0);
        }
        if (this.actionMode != null) {
            finishActionMode();
        }
    }

    protected void showMergeDialog(final List<T> selectedItems) {
        if (this.adapter.getSelectedItems().size() <= 1) {
            ToastUtil.showError(getContext(), R.string.am_merge_error);
        } else {
            TextInputDialog.Builder builder = new TextInputDialog.Builder(requireContext());
            builder.setHint(getString(R.string.project_name_label)).setTextWatcher(new NewProjectNameTextWatcher()).setPositiveButton(getString(R.string.ok), new TextInputDialog.OnClickListener() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment$$ExternalSyntheticLambda2
                @Override // org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog.OnClickListener
                public final void onPositiveButtonClick(DialogInterface dialogInterface, String str) {
                    this.f$0.m5793xdcebe4ee(selectedItems, dialogInterface, str);
                }
            });
            builder.setTitle(R.string.new_merge_project_dialog_title).setNegativeButton(R.string.cancel, (DialogInterface.OnClickListener) null).show();
        }
        setShowProgressBar(true);
        finishActionMode();
    }

    /* JADX INFO: renamed from: lambda$showMergeDialog$4$org-catrobat-catroid-ui-recyclerview-fragment-RecyclerViewFragment, reason: not valid java name */
    /* synthetic */ void m5793xdcebe4ee(List list, DialogInterface dialogInterface, String str) {
        mergeProjects(list, str);
    }

    protected void renameItem(T item, String newName) {
        item.setName(newName);
        finishActionMode();
    }

    protected void mergeProjects(List<T> selectedProjects, String mergeProjectName) {
        ToastUtil.showSuccess(getContext(), R.string.merging_project_text);
        finishActionMode();
    }
}
