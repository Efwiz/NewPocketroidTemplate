package org.catrobat.catroid.ui.recyclerview.fragment;

import android.content.ComponentCallbacks;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import com.google.android.gms.analytics.ecommerce.Promotion;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.SharedPreferenceKeys;
import org.catrobat.catroid.common.SoundInfo;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.ui.UiUtils;
import org.catrobat.catroid.ui.controller.BackpackListManager;
import org.catrobat.catroid.ui.recyclerview.adapter.SoundAdapter;
import org.catrobat.catroid.ui.recyclerview.adapter.multiselection.MultiSelectionManager;
import org.catrobat.catroid.ui.recyclerview.backpack.BackpackActivity;
import org.catrobat.catroid.ui.recyclerview.controller.SoundController;
import org.catrobat.catroid.utils.SnackbarUtil;
import org.catrobat.catroid.utils.ToastUtil;
import org.jetbrains.kotlin.org.jline.reader.LineReader;
import org.koin.android.ext.android.ComponentCallbackExtKt;
import org.koin.core.qualifier.Qualifier;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/fragment/SoundListFragment.class
  classes14.dex
 */
/* JADX INFO: compiled from: SoundListFragment.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 &2\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001:\u0001&B\u0005¢\u0006\u0002\u0010\u0003J\u0018\u0010\f\u001a\u00020\r2\u000e\u0010\u000e\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u000fH\u0014J\u0018\u0010\u0010\u001a\u00020\r2\u000e\u0010\u000e\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u000fH\u0014J\b\u0010\u0011\u001a\u00020\u0012H\u0015J\b\u0010\u0013\u001a\u00020\u0012H\u0014J\b\u0010\u0014\u001a\u00020\u0012H\u0014J\b\u0010\u0015\u001a\u00020\rH\u0016J\b\u0010\u0016\u001a\u00020\u0017H\u0014J\u001a\u0010\u0018\u001a\u00020\r2\b\u0010\u0019\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J\b\u0010\u001c\u001a\u00020\rH\u0016J\u0010\u0010\u001d\u001a\u00020\r2\u0006\u0010\u001e\u001a\u00020\u001fH\u0016J\b\u0010 \u001a\u00020\rH\u0016J\u001c\u0010!\u001a\u00020\r2\b\u0010\u0019\u001a\u0004\u0018\u00010\u00022\b\u0010\"\u001a\u0004\u0018\u00010#H\u0016J\u0018\u0010$\u001a\u00020\r2\u000e\u0010\u000e\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u000fH\u0014J\b\u0010%\u001a\u00020\rH\u0014R\u001b\u0010\u0004\u001a\u00020\u00058BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\u0006\u0010\u0007R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006'"}, d2 = {"Lorg/catrobat/catroid/ui/recyclerview/fragment/SoundListFragment;", "Lorg/catrobat/catroid/ui/recyclerview/fragment/RecyclerViewFragment;", "Lorg/catrobat/catroid/common/SoundInfo;", "()V", "projectManager", "Lorg/catrobat/catroid/ProjectManager;", "getProjectManager", "()Lorg/catrobat/catroid/ProjectManager;", "projectManager$delegate", "Lkotlin/Lazy;", "soundController", "Lorg/catrobat/catroid/ui/recyclerview/controller/SoundController;", "copyItems", "", "selectedItems", "", "deleteItems", "getDeleteAlertTitleId", "", "getRenameDialogHint", "getRenameDialogTitle", "initializeAdapter", "isBackpackEmpty", "", "onItemClick", "item", "selectionManager", "Lorg/catrobat/catroid/ui/recyclerview/adapter/multiselection/MultiSelectionManager;", "onPause", "onPrepareOptionsMenu", LineReader.MENU, "Landroid/view/Menu;", "onResume", "onSettingsClick", Promotion.ACTION_VIEW, "Landroid/view/View;", "packItems", "switchToBackpack", "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SoundListFragment extends RecyclerViewFragment<SoundInfo> {
    public static final String TAG = "SoundListFragment";

    /* JADX INFO: renamed from: projectManager$delegate, reason: from kotlin metadata */
    private final Lazy projectManager;
    private final SoundController soundController = new SoundController();

    @Override // org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment
    protected int getDeleteAlertTitleId() {
        return R.plurals.delete_sounds;
    }

    @Override // org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment
    protected int getRenameDialogHint() {
        return R.string.sound_name_label;
    }

    @Override // org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment
    protected int getRenameDialogTitle() {
        return R.string.rename_sound_dialog;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public SoundListFragment() {
        final SoundListFragment soundListFragment = this;
        LazyThreadSafetyMode lazyThreadSafetyMode = LazyThreadSafetyMode.NONE;
        final Qualifier qualifier = null;
        final Object[] objArr = 0 == true ? 1 : 0;
        this.projectManager = LazyKt.lazy(lazyThreadSafetyMode, (Function0) new Function0<ProjectManager>() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.SoundListFragment$special$$inlined$inject$default$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Type inference failed for: r0v4, types: [java.lang.Object, org.catrobat.catroid.ProjectManager] */
            @Override // kotlin.jvm.functions.Function0
            public final ProjectManager invoke() {
                ComponentCallbacks componentCallbacks = soundListFragment;
                return ComponentCallbackExtKt.getKoin(componentCallbacks).get_scopeRegistry().getRootScope().get(Reflection.getOrCreateKotlinClass(ProjectManager.class), qualifier, objArr);
            }
        });
    }

    private final ProjectManager getProjectManager() {
        return (ProjectManager) this.projectManager.getValue();
    }

    @Override // org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment
    public void initializeAdapter() {
        this.sharedPreferenceDetailsKey = SharedPreferenceKeys.SHOW_DETAILS_SOUNDS_PREFERENCE_KEY;
        this.adapter = new SoundAdapter(getProjectManager().getCurrentSprite().getSoundList());
        this.emptyView.setText(R.string.fragment_sound_text_description);
        onAdapterReady();
    }

    @Override // org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        SnackbarUtil.showHintSnackbar(requireActivity(), R.string.hint_sounds);
    }

    @Override // org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment, androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        this.adapter.stopSound();
    }

    @Override // org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment, androidx.fragment.app.Fragment
    public void onPrepareOptionsMenu(Menu menu) {
        Intrinsics.checkNotNullParameter(menu, "menu");
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.catblocks_reorder_scripts).setVisible(false);
        menu.findItem(R.id.find).setVisible(false);
    }

    @Override // org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment
    protected void packItems(List<? extends SoundInfo> selectedItems) {
        Intrinsics.checkNotNullParameter(selectedItems, "selectedItems");
        setShowProgressBar(true);
        Iterator<? extends SoundInfo> it = selectedItems.iterator();
        int i = 0;
        while (it.hasNext()) {
            try {
                BackpackListManager.getInstance().getBackpackedSounds().add(this.soundController.pack(it.next()));
                BackpackListManager.getInstance().saveBackpack();
                i++;
            } catch (IOException e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
        }
        if (i > 0) {
            ToastUtil.showSuccess(requireContext(), getResources().getQuantityString(R.plurals.packed_sounds, i, Integer.valueOf(i)));
            switchToBackpack();
        }
        finishActionMode();
    }

    @Override // org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment
    protected boolean isBackpackEmpty() {
        return BackpackListManager.getInstance().getBackpackedSounds().isEmpty();
    }

    @Override // org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment
    protected void switchToBackpack() {
        Intent intent = new Intent(requireContext(), (Class<?>) BackpackActivity.class);
        intent.putExtra("fragmentPosition", 3);
        startActivity(intent);
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    @Override // org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment
    protected void copyItems(List<? extends SoundInfo> selectedItems) {
        Intrinsics.checkNotNullParameter(selectedItems, "selectedItems");
        setShowProgressBar(true);
        Scene currentlyEditedScene = getProjectManager().getCurrentlyEditedScene();
        Sprite currentSprite = getProjectManager().getCurrentSprite();
        Iterator<? extends SoundInfo> it = selectedItems.iterator();
        int i = 0;
        while (it.hasNext()) {
            try {
                this.adapter.add(this.soundController.copy(it.next(), currentlyEditedScene, currentSprite));
                i++;
            } catch (IOException e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
        }
        if (i > 0) {
            ToastUtil.showSuccess(requireContext(), getResources().getQuantityString(R.plurals.copied_sounds, i, Integer.valueOf(i)));
        }
        finishActionMode();
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    @Override // org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment
    protected void deleteItems(List<? extends SoundInfo> selectedItems) {
        Intrinsics.checkNotNullParameter(selectedItems, "selectedItems");
        setShowProgressBar(true);
        int i = 0;
        for (SoundInfo soundInfo : selectedItems) {
            try {
                this.soundController.delete(soundInfo);
            } catch (IOException e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
            this.adapter.remove(soundInfo);
            i++;
        }
        ToastUtil.showSuccess(requireContext(), getResources().getQuantityString(R.plurals.deleted_sounds, i, Integer.valueOf(i)));
        finishActionMode();
    }

    @Override // org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment
    public void onItemClick(SoundInfo item, MultiSelectionManager selectionManager) {
        Intrinsics.checkNotNullParameter(selectionManager, "selectionManager");
        if (this.actionModeType == 4) {
            super.onItemClick(item, (MultiSelectionManager) null);
        } else if (this.actionModeType != 0) {
            super.onItemClick(item, selectionManager);
        }
    }

    @Override // org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter.OnItemClickListener
    public void onSettingsClick(final SoundInfo item, View view) {
        final ArrayList arrayList = new ArrayList();
        arrayList.add(item);
        PopupMenu popupMenuCreateSettingsPopUpMenu = UiUtils.createSettingsPopUpMenu(view, requireContext(), R.menu.menu_project_activity, new int[]{R.id.new_group, R.id.new_scene, R.id.show_details, R.id.project_options, R.id.project_files, R.id.project_libs, R.id.edit, R.id.from_local, R.id.from_library});
        Intrinsics.checkNotNullExpressionValue(popupMenuCreateSettingsPopUpMenu, "createSettingsPopUpMenu(...)");
        popupMenuCreateSettingsPopUpMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.SoundListFragment$$ExternalSyntheticLambda0
            @Override // android.widget.PopupMenu.OnMenuItemClickListener
            public final boolean onMenuItemClick(MenuItem menuItem) {
                return SoundListFragment.onSettingsClick$lambda$0(this.f$0, arrayList, item, menuItem);
            }
        });
        popupMenuCreateSettingsPopUpMenu.getMenu().findItem(R.id.backpack).setTitle(R.string.pack);
        popupMenuCreateSettingsPopUpMenu.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean onSettingsClick$lambda$0(SoundListFragment this$0, List itemList, SoundInfo soundInfo, MenuItem menuItem) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(itemList, "$itemList");
        switch (menuItem.getItemId()) {
            case R.id.backpack /* 2131361924 */:
                this$0.packItems(itemList);
                break;
            case R.id.copy /* 2131364141 */:
                this$0.copyItems(itemList);
                break;
            case R.id.delete /* 2131364226 */:
                this$0.showDeleteAlert(itemList);
                break;
            case R.id.rename /* 2131365129 */:
                this$0.showRenameDialog(soundInfo);
                break;
        }
        return true;
    }
}
