package org.catrobat.catroid.ui.recyclerview.fragment;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.NfcTagData;
import org.catrobat.catroid.nfc.NfcHandler;
import org.catrobat.catroid.ui.UiUtils;
import org.catrobat.catroid.ui.recyclerview.adapter.ExtendedRVAdapter;
import org.catrobat.catroid.ui.recyclerview.adapter.NfcTagAdapter;
import org.catrobat.catroid.ui.recyclerview.adapter.multiselection.MultiSelectionManager;
import org.catrobat.catroid.utils.ToastUtil;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/fragment/NfcTagListFragment.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class NfcTagListFragment extends RecyclerViewFragment<NfcTagData> {
    public static final String TAG = "NfcTagListFragment";
    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;

    @Override // org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment
    protected int getDeleteAlertTitleId() {
        return R.plurals.delete_nfc_tags;
    }

    @Override // org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment
    protected int getRenameDialogHint() {
        return R.string.nfc_tag_name_label;
    }

    @Override // org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment
    protected int getRenameDialogTitle() {
        return R.string.rename_nfctag_dialog;
    }

    @Override // org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment
    protected boolean isBackpackEmpty() {
        return true;
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.nfcAdapter = NfcAdapter.getDefaultAdapter(getActivity());
        Intent intentAddFlags = new Intent(getActivity(), getActivity().getClass()).addFlags(536870912);
        if (Build.VERSION.SDK_INT >= 31) {
            this.pendingIntent = PendingIntent.getActivity(getActivity(), 0, intentAddFlags, 67108864);
        } else {
            this.pendingIntent = PendingIntent.getActivity(getActivity(), 0, intentAddFlags, 67108864);
        }
        NfcAdapter nfcAdapter = this.nfcAdapter;
        if (nfcAdapter != null && !nfcAdapter.isEnabled()) {
            ToastUtil.showError(getActivity(), R.string.nfc_not_activated);
            startActivity(new Intent("android.settings.NFC_SETTINGS"));
        } else if (this.nfcAdapter == null) {
            ToastUtil.showError(getActivity(), R.string.no_nfc_available);
        }
    }

    @Override // org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        NfcAdapter nfcAdapter = this.nfcAdapter;
        if (nfcAdapter != null) {
            nfcAdapter.enableForegroundDispatch(getActivity(), this.pendingIntent, null, null);
        }
    }

    @Override // org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment, androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        NfcAdapter nfcAdapter = this.nfcAdapter;
        if (nfcAdapter != null) {
            nfcAdapter.disableForegroundDispatch(getActivity());
        }
    }

    @Override // org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment
    protected void initializeAdapter() {
        List<NfcTagData> nfcTagList = ProjectManager.getInstance().getCurrentSprite().getNfcTagList();
        this.sharedPreferenceDetailsKey = "showDetailsNfcTags";
        this.adapter = new NfcTagAdapter(nfcTagList);
        this.emptyView.setText(R.string.fragment_nfctag_text_description);
        onAdapterReady();
    }

    @Override // org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment, androidx.fragment.app.Fragment
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.backpack).setVisible(false);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void onNewIntent(Intent intent) {
        String tagIdFromIntent = NfcHandler.getTagIdFromIntent(intent);
        NfcHandler.setLastNfcTagId(tagIdFromIntent);
        NfcHandler.setLastNfcTagMessage(NfcHandler.getMessageFromIntent(intent));
        if (tagIdFromIntent != null) {
            NfcTagData nfcTagData = new NfcTagData();
            nfcTagData.setName(this.uniqueNameProvider.getUniqueNameInNameables(getString(R.string.default_tag_name), this.adapter.getItems()));
            nfcTagData.setNfcTagUid(tagIdFromIntent);
            if (addItem(nfcTagData, this.adapter)) {
                return;
            }
            Log.e(TAG, "NFC Tag has already been added.");
            return;
        }
        Log.e(TAG, "NFC Tag does not have a UID.");
    }

    public boolean addItem(NfcTagData item, ExtendedRVAdapter<NfcTagData> localAdapter) {
        if (localAdapter.getItems().contains(item)) {
            return false;
        }
        return localAdapter.add(item);
    }

    @Override // org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment
    protected void packItems(List<NfcTagData> selectedItems) {
        throw new IllegalStateException(TAG + ": NfcTags cannot be backpacked.");
    }

    @Override // org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment
    protected void switchToBackpack() {
        throw new IllegalStateException(TAG + ": NfcTags cannot be backpacked.");
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    @Override // org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment
    protected void copyItems(List<NfcTagData> list) {
        setShowProgressBar(true);
        for (NfcTagData nfcTagData : list) {
            String uniqueNameInNameables = this.uniqueNameProvider.getUniqueNameInNameables(nfcTagData.getName(), this.adapter.getItems());
            NfcTagData nfcTagData2 = new NfcTagData();
            nfcTagData2.setName(uniqueNameInNameables);
            nfcTagData2.setNfcTagUid(nfcTagData.getNfcTagUid());
            this.adapter.add(nfcTagData2);
        }
        ToastUtil.showSuccess(getActivity(), getResources().getQuantityString(R.plurals.copied_nfc_tags, list.size(), Integer.valueOf(list.size())));
        finishActionMode();
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    @Override // org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment
    protected void deleteItems(List<NfcTagData> list) {
        setShowEmptyView(true);
        Iterator<NfcTagData> it = list.iterator();
        while (it.hasNext()) {
            this.adapter.remove(it.next());
        }
        ToastUtil.showSuccess(getActivity(), getResources().getQuantityString(R.plurals.deleted_nfc_tags, list.size(), Integer.valueOf(list.size())));
        finishActionMode();
    }

    @Override // org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment
    public void onItemClick(NfcTagData item, MultiSelectionManager selectionManager) {
        if (this.actionModeType == 4) {
            super.onItemClick(item, (MultiSelectionManager) null);
        }
    }

    @Override // org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter.OnItemClickListener
    public void onSettingsClick(final NfcTagData item, View view) {
        final ArrayList arrayList = new ArrayList();
        arrayList.add(item);
        PopupMenu popupMenuCreateSettingsPopUpMenu = UiUtils.createSettingsPopUpMenu(view, requireContext(), R.menu.menu_project_activity, new int[]{R.id.new_group, R.id.new_scene, R.id.show_details, R.id.project_options, R.id.project_files, R.id.project_libs, R.id.edit, R.id.from_library, R.id.from_local});
        popupMenuCreateSettingsPopUpMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.NfcTagListFragment$$ExternalSyntheticLambda0
            @Override // android.widget.PopupMenu.OnMenuItemClickListener
            public final boolean onMenuItemClick(MenuItem menuItem) {
                return this.f$0.m5785x12f57f98(arrayList, item, menuItem);
            }
        });
        popupMenuCreateSettingsPopUpMenu.show();
    }

    /* JADX INFO: renamed from: lambda$onSettingsClick$0$org-catrobat-catroid-ui-recyclerview-fragment-NfcTagListFragment, reason: not valid java name */
    /* synthetic */ boolean m5785x12f57f98(List list, NfcTagData nfcTagData, MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == R.id.copy) {
            copyItems(list);
            return true;
        }
        if (itemId == R.id.delete) {
            deleteItems(list);
            return true;
        }
        if (itemId != R.id.rename) {
            return true;
        }
        showRenameDialog(nfcTagData);
        return true;
    }
}
