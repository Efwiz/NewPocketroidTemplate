package org.catrobat.catroid.ui.recyclerview.backpack;

import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ui.UiUtils;
import org.catrobat.catroid.ui.recyclerview.adapter.ExtendedRVAdapter;
import org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter;
import org.catrobat.catroid.ui.recyclerview.adapter.draganddrop.TouchHelperCallback;
import org.catrobat.catroid.ui.recyclerview.adapter.multiselection.MultiSelectionManager;
import org.catrobat.catroid.ui.recyclerview.viewholder.CheckableViewHolder;
import org.catrobat.catroid.utils.ToastUtil;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/backpack/BackpackRecyclerViewFragment.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public abstract class BackpackRecyclerViewFragment<T> extends Fragment implements ActionMode.Callback, RVAdapter.SelectionListener, RVAdapter.OnItemClickListener<T> {
    protected static final int DELETE = 2;
    protected static final int NONE = 0;
    protected static final int UNPACK = 1;
    protected ActionMode actionMode;
    protected ExtendedRVAdapter<T> adapter;
    protected View parentView;
    protected RecyclerView recyclerView;
    protected ItemTouchHelper touchHelper;
    protected String sharedPreferenceDetailsKey = "";
    public boolean hasDetails = false;
    protected int actionModeType = 0;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/backpack/BackpackRecyclerViewFragment$ActionModeType.class
      classes14.dex
     */
    @Retention(RetentionPolicy.SOURCE)
    @interface ActionModeType {
    }

    protected abstract void deleteItems(List<T> selectedItems);

    protected abstract int getDeleteAlertTitleId();

    protected abstract String getItemName(T item);

    protected abstract void initializeAdapter();

    @Override // android.view.ActionMode.Callback
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    protected abstract void unpackItems(List<T> selectedItems);

    @Override // android.view.ActionMode.Callback
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        int i = this.actionModeType;
        if (i == 0) {
            return false;
        }
        if (i == 1) {
            mode.setTitle(getString(R.string.am_unpack));
        } else if (i == 2) {
            mode.setTitle(getString(R.string.am_delete));
        }
        mode.getMenuInflater().inflate(R.menu.context_menu, menu);
        this.adapter.showCheckBoxes = true;
        this.adapter.showSettings = false;
        this.adapter.showRipples = false;
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
        if (i == 1) {
            unpackItems(this.adapter.getSelectedItems());
        } else {
            if (i != 2) {
                return;
            }
            showDeleteAlert(this.adapter.getSelectedItems());
        }
    }

    protected void resetActionModeParameters() {
        this.actionModeType = 0;
        this.actionMode = null;
        this.adapter.showCheckBoxes = false;
        this.adapter.showSettings = true;
        this.adapter.selectionMode = 2;
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewInflate = inflater.inflate(R.layout.fragment_list_view, container, false);
        this.parentView = viewInflate;
        this.recyclerView = (RecyclerView) viewInflate.findViewById(R.id.recycler_view);
        setHasOptionsMenu(true);
        return this.parentView;
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle savedInstance) {
        super.onActivityCreated(savedInstance);
        initializeAdapter();
    }

    public void onAdapterReady() {
        this.adapter.showDetails = PreferenceManager.getDefaultSharedPreferences(getActivity()).getBoolean(this.sharedPreferenceDetailsKey, false);
        this.recyclerView.setAdapter(this.adapter);
        this.adapter.setSelectionListener(this);
        this.adapter.setOnItemClickListener(this);
        this.adapter.showRipples = false;
        this.adapter.notifyDataSetChanged();
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new TouchHelperCallback(this.adapter));
        this.touchHelper = itemTouchHelper;
        itemTouchHelper.attachToRecyclerView(this.recyclerView);
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        this.adapter.notifyDataSetChanged();
    }

    @Override // androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        finishActionMode();
    }

    @Override // androidx.fragment.app.Fragment
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (this.hasDetails) {
            this.adapter.showDetails = PreferenceManager.getDefaultSharedPreferences(getActivity()).getBoolean(this.sharedPreferenceDetailsKey, false);
            menu.findItem(R.id.show_details).setTitle(this.adapter.showDetails ? R.string.hide_details : R.string.show_details);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.delete) {
            startActionMode(2);
        } else if (itemId == R.id.show_details) {
            ExtendedRVAdapter<T> extendedRVAdapter = this.adapter;
            extendedRVAdapter.showDetails = !extendedRVAdapter.showDetails;
            PreferenceManager.getDefaultSharedPreferences(getActivity()).edit().putBoolean(this.sharedPreferenceDetailsKey, this.adapter.showDetails).apply();
            this.adapter.notifyDataSetChanged();
        } else if (itemId == R.id.unpack) {
            startActionMode(1);
        } else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void startActionMode(int type) {
        if (this.adapter.getItems().isEmpty()) {
            ToastUtil.showError(getActivity(), R.string.am_empty_list);
        } else {
            this.actionModeType = type;
            this.actionMode = getActivity().startActionMode(this);
        }
    }

    @Override // org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter.SelectionListener
    public void onSelectionChanged(int selectedItemCnt) {
        int i = this.actionModeType;
        if (i == 0) {
            throw new IllegalStateException("ActionModeType not set Correctly");
        }
        if (i == 1) {
            this.actionMode.setTitle(getString(R.string.am_unpack) + " " + selectedItemCnt);
        } else {
            if (i != 2) {
                return;
            }
            this.actionMode.setTitle(getString(R.string.am_delete) + " " + selectedItemCnt);
        }
    }

    protected void finishActionMode() {
        this.adapter.clearSelection();
        setShowProgressBar(false);
        if (this.actionModeType != 0) {
            this.actionMode.finish();
        }
    }

    protected void showDeleteAlert(final List<T> selectedItems) {
        new AlertDialog.Builder(getContext()).setTitle(getResources().getQuantityString(getDeleteAlertTitleId(), selectedItems.size())).setMessage(R.string.dialog_confirm_delete).setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.ui.recyclerview.backpack.BackpackRecyclerViewFragment.1
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int id2) {
                BackpackRecyclerViewFragment.this.deleteItems(selectedItems);
            }
        }).setNegativeButton(R.string.cancel, (DialogInterface.OnClickListener) null).setCancelable(false).show();
    }

    public void onItemClick(final T item, MultiSelectionManager selectionManager) {
        if (this.actionModeType != 0) {
            if (selectionManager == null) {
                return;
            }
            selectionManager.toggleSelection(this.adapter.getItems().indexOf(item));
            onSelectionChanged(selectionManager.getSelectedPositions().size());
            this.adapter.notifyDataSetChanged();
            return;
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(Integer.valueOf(R.string.unpack));
        arrayList.add(Integer.valueOf(R.string.delete));
        ArrayList arrayList2 = new ArrayList();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            arrayList2.add(getString(((Integer) it.next()).intValue()));
        }
        new AlertDialog.Builder(requireContext()).setTitle(getItemName(item)).setAdapter(UiUtils.getAlertDialogAdapterForMenuIcons(arrayList, arrayList2, requireContext(), requireActivity()), new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.ui.recyclerview.backpack.BackpackRecyclerViewFragment$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.m5751x249f688f(item, dialogInterface, i);
            }
        }).show();
    }

    /* JADX INFO: renamed from: lambda$onItemClick$0$org-catrobat-catroid-ui-recyclerview-backpack-BackpackRecyclerViewFragment, reason: not valid java name */
    /* synthetic */ void m5751x249f688f(Object obj, DialogInterface dialogInterface, int i) {
        if (i == 0) {
            unpackItems(new ArrayList(Collections.singletonList(obj)));
        } else {
            if (i != 1) {
                return;
            }
            showDeleteAlert(new ArrayList(Collections.singletonList(obj)));
        }
    }

    public void setShowProgressBar(boolean show) {
        this.parentView.findViewById(R.id.progress_bar).setVisibility(show ? 0 : 8);
        this.recyclerView.setVisibility(show ? 8 : 0);
    }

    @Override // org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter.OnItemClickListener
    public void onItemLongClick(T item, CheckableViewHolder holder) {
        onItemClick(item, null);
    }

    @Override // org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter.OnItemClickListener
    public void onSettingsClick(T item, View view) {
        final ArrayList arrayList = new ArrayList(Collections.singletonList(item));
        PopupMenu popupMenuCreateSettingsPopUpMenu = UiUtils.createSettingsPopUpMenu(view, requireContext(), R.menu.menu_backpack_activity, new int[]{R.id.show_details});
        popupMenuCreateSettingsPopUpMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() { // from class: org.catrobat.catroid.ui.recyclerview.backpack.BackpackRecyclerViewFragment$$ExternalSyntheticLambda1
            @Override // android.widget.PopupMenu.OnMenuItemClickListener
            public final boolean onMenuItemClick(MenuItem menuItem) {
                return this.f$0.m5752x8bb354fe(arrayList, menuItem);
            }
        });
        popupMenuCreateSettingsPopUpMenu.show();
    }

    /* JADX INFO: renamed from: lambda$onSettingsClick$1$org-catrobat-catroid-ui-recyclerview-backpack-BackpackRecyclerViewFragment, reason: not valid java name */
    /* synthetic */ boolean m5752x8bb354fe(List list, MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == R.id.delete) {
            showDeleteAlert(list);
            return true;
        }
        if (itemId != R.id.unpack) {
            return true;
        }
        unpackItems(list);
        return true;
    }
}
