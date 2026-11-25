package org.catrobat.catroid.ui.recyclerview.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.analytics.ecommerce.Promotion;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.bricks.ListSelectorBrick;
import org.catrobat.catroid.formulaeditor.UserData;
import org.catrobat.catroid.formulaeditor.UserList;
import org.catrobat.catroid.ui.BottomBar;
import org.catrobat.catroid.ui.UiUtils;
import org.catrobat.catroid.ui.recyclerview.adapter.DataListAdapter;
import org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter;
import org.catrobat.catroid.ui.recyclerview.adapter.multiselection.MultiSelectionManager;
import org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog;
import org.catrobat.catroid.ui.recyclerview.dialog.textwatcher.DuplicateInputTextWatcher;
import org.catrobat.catroid.ui.recyclerview.viewholder.CheckableViewHolder;
import org.catrobat.catroid.utils.ToastUtil;
import org.catrobat.catroid.utils.UserDataUtil;
import org.jetbrains.kotlin.org.jline.reader.LineReader;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/fragment/ListSelectorFragment.class
  classes14.dex
 */
/* JADX INFO: compiled from: ListSelectorFragment.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u008c\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\b\u0018\u0000 >2\u00020\u00012\u00020\u00022\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00040\u0003:\u0002>?B\u0005¢\u0006\u0002\u0010\u0005J\u001a\u0010\u000f\u001a\u00020\u00102\u0010\u0010\u0011\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00040\u000bH\u0002J\b\u0010\u0012\u001a\u00020\u0010H\u0002J\b\u0010\u0013\u001a\u00020\u0010H\u0002J\u0006\u0010\u0014\u001a\u00020\u0010J\u0012\u0010\u0015\u001a\u00020\u00102\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0016J\b\u0010\u0018\u001a\u00020\u0010H\u0002J\u0018\u0010\u0019\u001a\u00020\u00102\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J&\u0010\u001e\u001a\u0004\u0018\u00010\u001f2\u0006\u0010\u001c\u001a\u00020 2\b\u0010!\u001a\u0004\u0018\u00010\"2\b\u0010#\u001a\u0004\u0018\u00010\u0017H\u0016J\u001e\u0010$\u001a\u00020\u00102\n\u0010%\u001a\u0006\u0012\u0002\b\u00030\u00042\b\u0010&\u001a\u0004\u0018\u00010'H\u0016J\u001c\u0010(\u001a\u00020\u00102\n\u0010%\u001a\u0006\u0012\u0002\b\u00030\u00042\u0006\u0010)\u001a\u00020*H\u0016J\u0010\u0010+\u001a\u00020,2\u0006\u0010%\u001a\u00020-H\u0016J\u0010\u0010.\u001a\u00020\u00102\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J\b\u0010/\u001a\u00020\u0010H\u0016J\u0010\u00100\u001a\u00020\u00102\u0006\u00101\u001a\u000202H\u0016J\u001e\u00103\u001a\u00020\u00102\n\u0010%\u001a\u0006\u0012\u0002\b\u00030\u00042\b\u00104\u001a\u0004\u0018\u00010\u001fH\u0016J\b\u00105\u001a\u00020\u0010H\u0016J\u001e\u00106\u001a\u00020\u00102\n\u0010%\u001a\u0006\u0012\u0002\b\u00030\u00042\b\u00107\u001a\u0004\u0018\u000108H\u0002J\u001a\u00109\u001a\u00020\u00102\u0010\u0010\u0011\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00040\u000bH\u0002J\u001a\u0010:\u001a\u00020\u00102\u0010\u0010\u0011\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00040\u000bH\u0002J\u0016\u0010;\u001a\u00020\u00102\f\u0010<\u001a\b\u0012\u0004\u0012\u00020\f0\u000bH\u0002J\b\u0010=\u001a\u00020\u0010H\u0002R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\n\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006@"}, d2 = {"Lorg/catrobat/catroid/ui/recyclerview/fragment/ListSelectorFragment;", "Landroidx/fragment/app/Fragment;", "Lorg/catrobat/catroid/ui/recyclerview/adapter/RVAdapter$SelectionListener;", "Lorg/catrobat/catroid/ui/recyclerview/adapter/RVAdapter$OnItemClickListener;", "Lorg/catrobat/catroid/formulaeditor/UserData;", "()V", "adapter", "Lorg/catrobat/catroid/ui/recyclerview/adapter/DataListAdapter;", "listSelectorInterface", "Lorg/catrobat/catroid/ui/recyclerview/fragment/ListSelectorFragment$ListSelectorInterface;", "preSelection", "", "Lorg/catrobat/catroid/formulaeditor/UserList;", "recyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "deleteItems", "", "selectedItems", "handleContextualAction", "initializeAdapter", "notifyDataSetChanged", "onActivityCreated", "savedInstance", "Landroid/os/Bundle;", "onAdapterReady", "onCreateOptionsMenu", LineReader.MENU, "Landroid/view/Menu;", "inflater", "Landroid/view/MenuInflater;", "onCreateView", "Landroid/view/View;", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "onItemClick", "item", "selectionManager", "Lorg/catrobat/catroid/ui/recyclerview/adapter/multiselection/MultiSelectionManager;", "onItemLongClick", "holder", "Lorg/catrobat/catroid/ui/recyclerview/viewholder/CheckableViewHolder;", "onOptionsItemSelected", "", "Landroid/view/MenuItem;", "onPrepareOptionsMenu", "onResume", "onSelectionChanged", "selectedItemCnt", "", "onSettingsClick", Promotion.ACTION_VIEW, "onStop", "renameItem", "name", "", "showDeleteAlert", "showRenameDialog", "updateSelection", "userLists", "updateTitle", "Companion", "ListSelectorInterface", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ListSelectorFragment extends Fragment implements RVAdapter.SelectionListener, RVAdapter.OnItemClickListener<UserData<?>> {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String TAG;
    private DataListAdapter adapter;
    private ListSelectorInterface listSelectorInterface;
    private List<? extends UserList> preSelection;
    private RecyclerView recyclerView;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/fragment/ListSelectorFragment$ListSelectorInterface.class
      classes14.dex
     */
    /* JADX INFO: compiled from: ListSelectorFragment.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H&¨\u0006\u0007"}, d2 = {"Lorg/catrobat/catroid/ui/recyclerview/fragment/ListSelectorFragment$ListSelectorInterface;", "", "onUserListSelected", "", "userLists", "", "Lorg/catrobat/catroid/formulaeditor/UserList;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface ListSelectorInterface {
        void onUserListSelected(List<? extends UserList> userLists);
    }

    @JvmStatic
    public static final void showFragment(Context context, ListSelectorBrick listSelectorBrick) {
        INSTANCE.showFragment(context, listSelectorBrick);
    }

    private final void updateSelection(List<? extends UserList> userLists) {
        DataListAdapter dataListAdapter = this.adapter;
        if (dataListAdapter != null) {
            dataListAdapter.clearSelection();
        }
        for (UserList userList : userLists) {
            DataListAdapter dataListAdapter2 = this.adapter;
            if (dataListAdapter2 != null) {
                dataListAdapter2.setSelection(userList, true);
            }
        }
        updateTitle();
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Intrinsics.checkNotNullParameter(menu, "menu");
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        inflater.inflate(R.menu.context_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override // androidx.fragment.app.Fragment
    public boolean onOptionsItemSelected(MenuItem item) {
        Intrinsics.checkNotNullParameter(item, "item");
        int itemId = item.getItemId();
        if (itemId == R.id.confirm) {
            handleContextualAction();
        } else if (itemId == R.id.toggle_selection) {
            DataListAdapter dataListAdapter = this.adapter;
            Integer numValueOf = dataListAdapter != null ? Integer.valueOf(dataListAdapter.getItemCount()) : null;
            DataListAdapter dataListAdapter2 = this.adapter;
            if (!Intrinsics.areEqual(numValueOf, dataListAdapter2 != null ? Integer.valueOf(dataListAdapter2.getSelectedItemCount()) : null)) {
                DataListAdapter dataListAdapter3 = this.adapter;
                if (dataListAdapter3 != null) {
                    dataListAdapter3.selectAll();
                }
            } else {
                DataListAdapter dataListAdapter4 = this.adapter;
                if (dataListAdapter4 != null) {
                    dataListAdapter4.clearSelection();
                }
            }
            updateTitle();
        }
        return super.onOptionsItemSelected(item);
    }

    private final void handleContextualAction() {
        DataListAdapter dataListAdapter = this.adapter;
        List<UserData> selectedItems = dataListAdapter != null ? dataListAdapter.getSelectedItems() : null;
        if (selectedItems == null) {
            return;
        }
        ListSelectorInterface listSelectorInterface = this.listSelectorInterface;
        if (listSelectorInterface != null) {
            ArrayList arrayList = new ArrayList();
            for (Object obj : selectedItems) {
                if (obj instanceof UserList) {
                    arrayList.add(obj);
                }
            }
            listSelectorInterface.onUserListSelected(arrayList);
        }
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager != null) {
            fragmentManager.popBackStack();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        View viewInflate = inflater.inflate(R.layout.fragment_list_view, container, false);
        this.recyclerView = (RecyclerView) viewInflate.findViewById(R.id.recycler_view);
        setHasOptionsMenu(true);
        return viewInflate;
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle savedInstance) {
        super.onActivityCreated(savedInstance);
        initializeAdapter();
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        BottomBar.showBottomBar(getActivity());
        BottomBar.hidePlayButton(getActivity());
        BottomBar.hideAiAssistButton(getActivity());
    }

    @Override // androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        BottomBar.hideBottomBar(getActivity());
    }

    @Override // androidx.fragment.app.Fragment
    public void onPrepareOptionsMenu(Menu menu) {
        Intrinsics.checkNotNullParameter(menu, "menu");
        int size = menu.size();
        for (int i = 0; i < size; i++) {
            menu.getItem(i).setVisible(false);
        }
        menu.findItem(R.id.toggle_selection).setEnabled(true).setVisible(true);
        menu.findItem(R.id.confirm).setEnabled(true).setVisible(true);
        super.onPrepareOptionsMenu(menu);
    }

    private final void initializeAdapter() {
        DataListAdapter dataListAdapter = new DataListAdapter(new ArrayList(), new ArrayList(), new ArrayList(), new ArrayList(), ProjectManager.getInstance().getCurrentProject().getUserLists(), ProjectManager.getInstance().getCurrentSprite().getUserLists());
        this.adapter = dataListAdapter;
        dataListAdapter.showCheckBoxes(true);
        onAdapterReady();
    }

    private final void onAdapterReady() {
        RecyclerView recyclerView = this.recyclerView;
        if (recyclerView != null) {
            recyclerView.setAdapter(this.adapter);
        }
        DataListAdapter dataListAdapter = this.adapter;
        if (dataListAdapter != null) {
            dataListAdapter.setSelectionListener(this);
        }
        DataListAdapter dataListAdapter2 = this.adapter;
        if (dataListAdapter2 != null) {
            dataListAdapter2.setOnItemClickListener(this);
        }
        List<? extends UserList> listEmptyList = this.preSelection;
        if (listEmptyList == null) {
            listEmptyList = CollectionsKt.emptyList();
        }
        updateSelection(listEmptyList);
    }

    public final void notifyDataSetChanged() {
        DataListAdapter dataListAdapter = this.adapter;
        if (dataListAdapter != null) {
            dataListAdapter.notifyDataSetChanged();
        }
    }

    private final void deleteItems(List<? extends UserData<?>> selectedItems) {
        for (UserData<?> userData : selectedItems) {
            DataListAdapter dataListAdapter = this.adapter;
            if (dataListAdapter != null) {
                dataListAdapter.remove(userData);
            }
        }
        ProjectManager.getInstance().getCurrentProject().deselectElements(selectedItems);
        ToastUtil.showSuccess(getActivity(), getResources().getQuantityString(R.plurals.deleted_Items, selectedItems.size(), Integer.valueOf(selectedItems.size())));
    }

    private final void showDeleteAlert(final List<? extends UserData<?>> selectedItems) {
        new AlertDialog.Builder(requireContext()).setTitle(R.string.deletion_alert_title).setMessage(R.string.deletion_alert_text).setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.ListSelectorFragment$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                ListSelectorFragment.showDeleteAlert$lambda$1(this.f$0, selectedItems, dialogInterface, i);
            }
        }).setNegativeButton(R.string.cancel, (DialogInterface.OnClickListener) null).setCancelable(false).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showDeleteAlert$lambda$1(ListSelectorFragment this$0, List selectedItems, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(selectedItems, "$selectedItems");
        this$0.deleteItems(selectedItems);
    }

    private final void showRenameDialog(List<? extends UserData<?>> selectedItems) {
        final UserData<?> userData = selectedItems.get(0);
        TextInputDialog.Builder builder = new TextInputDialog.Builder(requireContext());
        DataListAdapter dataListAdapter = this.adapter;
        Intrinsics.checkNotNull(dataListAdapter);
        builder.setHint(getString(R.string.data_label)).setText(userData.getName()).setTextWatcher(new DuplicateInputTextWatcher(dataListAdapter.getItems())).setPositiveButton(getString(R.string.ok), new TextInputDialog.OnClickListener() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.ListSelectorFragment$$ExternalSyntheticLambda1
            @Override // org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog.OnClickListener
            public final void onPositiveButtonClick(DialogInterface dialogInterface, String str) {
                ListSelectorFragment.showRenameDialog$lambda$2(this.f$0, userData, dialogInterface, str);
            }
        });
        builder.setTitle(R.string.rename_data_dialog).setNegativeButton(R.string.cancel, (DialogInterface.OnClickListener) null).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showRenameDialog$lambda$2(ListSelectorFragment this$0, UserData item, DialogInterface dialogInterface, String str) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(item, "$item");
        this$0.renameItem(item, str);
    }

    private final void renameItem(UserData<?> item, String name2) {
        ProjectManager.getInstance().getCurrentProject().updateUserDataReferences(item.getName(), name2, item);
        if (name2 == null) {
            name2 = "";
        }
        UserDataUtil.renameUserData(item, name2);
        DataListAdapter dataListAdapter = this.adapter;
        if (dataListAdapter != null) {
            dataListAdapter.updateDataSet();
        }
    }

    private final void updateTitle() {
        DataListAdapter dataListAdapter = this.adapter;
        if (dataListAdapter != null) {
            int selectedItemCount = dataListAdapter.getSelectedItemCount();
            FragmentActivity activity = getActivity();
            AppCompatActivity appCompatActivity = activity instanceof AppCompatActivity ? (AppCompatActivity) activity : null;
            ActionBar supportActionBar = appCompatActivity != null ? appCompatActivity.getSupportActionBar() : null;
            if (supportActionBar == null) {
                return;
            }
            supportActionBar.setTitle(getResources().getQuantityString(R.plurals.list_selection_plural, selectedItemCount, Integer.valueOf(selectedItemCount)));
        }
    }

    @Override // org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter.SelectionListener
    public void onSelectionChanged(int selectedItemCnt) {
        updateTitle();
    }

    @Override // org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter.OnItemClickListener
    public void onItemClick(UserData<?> item, MultiSelectionManager selectionManager) {
        Intrinsics.checkNotNullParameter(item, "item");
        DataListAdapter dataListAdapter = this.adapter;
        if (dataListAdapter != null) {
            dataListAdapter.toggleSelection(item);
        }
        updateTitle();
    }

    @Override // org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter.OnItemClickListener
    public void onItemLongClick(UserData<?> item, CheckableViewHolder holder) {
        Intrinsics.checkNotNullParameter(item, "item");
        Intrinsics.checkNotNullParameter(holder, "holder");
        onItemClick(item, (MultiSelectionManager) null);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/fragment/ListSelectorFragment$Companion.class
      classes14.dex
     */
    /* JADX INFO: compiled from: ListSelectorFragment.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lorg/catrobat/catroid/ui/recyclerview/fragment/ListSelectorFragment$Companion;", "", "()V", "TAG", "", "showFragment", "", "context", "Landroid/content/Context;", "selectorBrick", "Lorg/catrobat/catroid/content/bricks/ListSelectorBrick;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final void showFragment(Context context, ListSelectorBrick selectorBrick) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(selectorBrick, "selectorBrick");
            AppCompatActivity activityFromContextWrapper = UiUtils.getActivityFromContextWrapper(context);
            if (activityFromContextWrapper == null) {
                return;
            }
            ListSelectorFragment listSelectorFragment = (ListSelectorFragment) activityFromContextWrapper.getSupportFragmentManager().findFragmentByTag(ListSelectorFragment.TAG);
            if (listSelectorFragment == null) {
                listSelectorFragment = new ListSelectorFragment();
                listSelectorFragment.listSelectorInterface = selectorBrick;
                activityFromContextWrapper.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, listSelectorFragment, ListSelectorFragment.TAG).addToBackStack(ListSelectorFragment.TAG).commit();
            }
            listSelectorFragment.preSelection = selectorBrick.getUserLists();
        }
    }

    static {
        Intrinsics.checkNotNullExpressionValue("ListSelectorFragment", "getSimpleName(...)");
        TAG = "ListSelectorFragment";
    }

    @Override // org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter.OnItemClickListener
    public void onSettingsClick(final UserData<?> item, View view) {
        Intrinsics.checkNotNullParameter(item, "item");
        new ArrayList().add(item);
        PopupMenu popupMenuCreateSettingsPopUpMenu = UiUtils.createSettingsPopUpMenu(view, getContext(), R.menu.menu_project_activity, new int[]{R.id.backpack, R.id.copy, R.id.new_group, R.id.new_scene, R.id.show_details, R.id.project_options, R.id.project_files, R.id.project_libs, R.id.from_library, R.id.from_local});
        Intrinsics.checkNotNullExpressionValue(popupMenuCreateSettingsPopUpMenu, "createSettingsPopUpMenu(...)");
        popupMenuCreateSettingsPopUpMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.ListSelectorFragment$$ExternalSyntheticLambda2
            @Override // android.widget.PopupMenu.OnMenuItemClickListener
            public final boolean onMenuItemClick(MenuItem menuItem) {
                return ListSelectorFragment.onSettingsClick$lambda$4(this.f$0, item, menuItem);
            }
        });
        popupMenuCreateSettingsPopUpMenu.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean onSettingsClick$lambda$4(ListSelectorFragment this$0, UserData item, MenuItem menuItem) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(item, "$item");
        int itemId = menuItem.getItemId();
        if (itemId == R.id.delete) {
            this$0.showDeleteAlert(CollectionsKt.listOf(item));
            return true;
        }
        if (itemId != R.id.rename) {
            return true;
        }
        this$0.showRenameDialog(CollectionsKt.listOf(item));
        return true;
    }
}
