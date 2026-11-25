package org.catrobat.catroid.ui.recyclerview.fragment;

import android.content.ComponentCallbacks;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.analytics.ecommerce.Promotion;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.FlavoredConstants;
import org.catrobat.catroid.common.SharedPreferenceKeys;
import org.catrobat.catroid.content.GroupItemSprite;
import org.catrobat.catroid.content.GroupSprite;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.io.StorageOperations;
import org.catrobat.catroid.merge.ImportProjectHelper;
import org.catrobat.catroid.ui.ProjectListActivity;
import org.catrobat.catroid.ui.SpriteActivity;
import org.catrobat.catroid.ui.UiUtils;
import org.catrobat.catroid.ui.WebViewActivity;
import org.catrobat.catroid.ui.controller.BackpackListManager;
import org.catrobat.catroid.ui.recyclerview.adapter.MultiViewSpriteAdapter;
import org.catrobat.catroid.ui.recyclerview.adapter.draganddrop.TouchHelperAdapterInterface;
import org.catrobat.catroid.ui.recyclerview.adapter.draganddrop.TouchHelperCallback;
import org.catrobat.catroid.ui.recyclerview.adapter.multiselection.MultiSelectionManager;
import org.catrobat.catroid.ui.recyclerview.backpack.BackpackActivity;
import org.catrobat.catroid.ui.recyclerview.backpack.BackpackSpriteFragment;
import org.catrobat.catroid.ui.recyclerview.controller.SpriteController;
import org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog;
import org.catrobat.catroid.ui.recyclerview.dialog.textwatcher.DuplicateInputTextWatcher;
import org.catrobat.catroid.ui.recyclerview.util.UniqueNameProvider;
import org.catrobat.catroid.ui.recyclerview.viewholder.CheckableViewHolder;
import org.catrobat.catroid.ui.workspace.WorkspaceLayout;
import org.catrobat.catroid.utils.SnackbarUtil;
import org.catrobat.catroid.utils.ToastUtil;
import org.jetbrains.kotlin.org.jline.reader.LineReader;
import org.koin.android.ext.android.ComponentCallbackExtKt;
import org.koin.core.qualifier.Qualifier;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/fragment/SpriteListFragment.class
  classes14.dex
 */
/* JADX INFO: compiled from: SpriteListFragment.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000v\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0006\b\u0007\u0018\u0000 <2\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001:\u0002<=B\u0005¢\u0006\u0002\u0010\u0003J\u0012\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0002H\u0002J\u0012\u0010\u0013\u001a\u00020\u00112\b\u0010\u0014\u001a\u0004\u0018\u00010\u0002H\u0002J\u0018\u0010\u0015\u001a\u00020\u00112\u000e\u0010\u0016\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0017H\u0014J\u0018\u0010\u0018\u001a\u00020\u00112\u000e\u0010\u0016\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0017H\u0014J\b\u0010\u0019\u001a\u00020\u001aH\u0015J\b\u0010\u001b\u001a\u00020\u001aH\u0014J\b\u0010\u001c\u001a\u00020\u001aH\u0014J\u0010\u0010\u001d\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u00020\u001eH\u0002J\b\u0010\u001f\u001a\u00020\u0011H\u0016J\b\u0010 \u001a\u00020\u0006H\u0014J\"\u0010!\u001a\u00020\u00112\u0006\u0010\"\u001a\u00020\u001a2\u0006\u0010#\u001a\u00020\u001a2\b\u0010$\u001a\u0004\u0018\u00010%H\u0016J\b\u0010&\u001a\u00020\u0011H\u0016J\u001c\u0010'\u001a\u00020\u00112\b\u0010\u0014\u001a\u0004\u0018\u00010\u00022\b\u0010(\u001a\u0004\u0018\u00010)H\u0016J\u001a\u0010*\u001a\u00020\u00112\b\u0010\u0014\u001a\u0004\u0018\u00010\u00022\u0006\u0010+\u001a\u00020,H\u0016J\u0010\u0010-\u001a\u00020\u00062\u0006\u0010\u0014\u001a\u00020\u001eH\u0016J\u0010\u0010.\u001a\u00020\u00112\u0006\u0010/\u001a\u000200H\u0016J\b\u00101\u001a\u00020\u0011H\u0016J\u001a\u00102\u001a\u00020\u00112\b\u0010\u0014\u001a\u0004\u0018\u00010\u00022\u0006\u00103\u001a\u000204H\u0016J\u0018\u00105\u001a\u00020\u00112\u000e\u0010\u0016\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0017H\u0014J\u001a\u00106\u001a\u00020\u00112\b\u0010\u0014\u001a\u0004\u0018\u00010\u00022\u0006\u00107\u001a\u000208H\u0014J\b\u00109\u001a\u00020\u0006H\u0016J\b\u0010:\u001a\u00020\u0011H\u0002J\b\u0010;\u001a\u00020\u0011H\u0014R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0002X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0005\u001a\u00020\u00068F¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0007R\u001b\u0010\b\u001a\u00020\t8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\f\u0010\r\u001a\u0004\b\n\u0010\u000bR\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006>"}, d2 = {"Lorg/catrobat/catroid/ui/recyclerview/fragment/SpriteListFragment;", "Lorg/catrobat/catroid/ui/recyclerview/fragment/RecyclerViewFragment;", "Lorg/catrobat/catroid/content/Sprite;", "()V", "currentSprite", "isSingleVisibleSprite", "", "()Z", "projectManager", "Lorg/catrobat/catroid/ProjectManager;", "getProjectManager", "()Lorg/catrobat/catroid/ProjectManager;", "projectManager$delegate", "Lkotlin/Lazy;", "spriteController", "Lorg/catrobat/catroid/ui/recyclerview/controller/SpriteController;", "addFromLibrary", "", "selectedItem", "addFromLocalProject", "item", "copyItems", "selectedItems", "", "deleteItems", "getDeleteAlertTitleId", "", "getRenameDialogHint", "getRenameDialogTitle", "handleSelectedOptionItem", "Landroid/view/MenuItem;", "initializeAdapter", "isBackpackEmpty", "onActivityResult", "requestCode", "resultCode", "data", "Landroid/content/Intent;", "onAdapterReady", "onItemClick", "selectionManager", "Lorg/catrobat/catroid/ui/recyclerview/adapter/multiselection/MultiSelectionManager;", "onItemLongClick", "holder", "Lorg/catrobat/catroid/ui/recyclerview/viewholder/CheckableViewHolder;", "onOptionsItemSelected", "onPrepareOptionsMenu", LineReader.MENU, "Landroid/view/Menu;", "onResume", "onSettingsClick", Promotion.ACTION_VIEW, "Landroid/view/View;", "packItems", "renameItem", "name", "", "shouldShowEmptyView", "showNewGroupDialog", "switchToBackpack", "Companion", "MultiViewTouchHelperCallback", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SpriteListFragment extends RecyclerViewFragment<Sprite> {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final int IMPORT_OBJECT_REQUEST_CODE = 0;
    private static final String TAG;
    private Sprite currentSprite;

    /* JADX INFO: renamed from: projectManager$delegate, reason: from kotlin metadata */
    private final Lazy projectManager;
    private final SpriteController spriteController = new SpriteController();

    @Override // org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment
    protected int getDeleteAlertTitleId() {
        return R.plurals.delete_sprites;
    }

    @Override // org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment
    protected int getRenameDialogHint() {
        return R.string.sprite_name_label;
    }

    @Override // org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment
    protected int getRenameDialogTitle() {
        return R.string.rename_sprite_dialog;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public SpriteListFragment() {
        final SpriteListFragment spriteListFragment = this;
        LazyThreadSafetyMode lazyThreadSafetyMode = LazyThreadSafetyMode.NONE;
        final Qualifier qualifier = null;
        final Object[] objArr = 0 == true ? 1 : 0;
        this.projectManager = LazyKt.lazy(lazyThreadSafetyMode, (Function0) new Function0<ProjectManager>() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.SpriteListFragment$special$$inlined$inject$default$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Type inference failed for: r0v4, types: [java.lang.Object, org.catrobat.catroid.ProjectManager] */
            @Override // kotlin.jvm.functions.Function0
            public final ProjectManager invoke() {
                ComponentCallbacks componentCallbacks = spriteListFragment;
                return ComponentCallbackExtKt.getKoin(componentCallbacks).get_scopeRegistry().getRootScope().get(Reflection.getOrCreateKotlinClass(ProjectManager.class), qualifier, objArr);
            }
        });
    }

    private final ProjectManager getProjectManager() {
        return (ProjectManager) this.projectManager.getValue();
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/fragment/SpriteListFragment$MultiViewTouchHelperCallback.class
      classes14.dex
     */
    /* JADX INFO: compiled from: SpriteListFragment.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\b\u0080\u0004\u0018\u00002\u00020\u0001B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004J\u001a\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/catrobat/catroid/ui/recyclerview/fragment/SpriteListFragment$MultiViewTouchHelperCallback;", "Lorg/catrobat/catroid/ui/recyclerview/adapter/draganddrop/TouchHelperCallback;", "adapterInterface", "Lorg/catrobat/catroid/ui/recyclerview/adapter/draganddrop/TouchHelperAdapterInterface;", "(Lorg/catrobat/catroid/ui/recyclerview/fragment/SpriteListFragment;Lorg/catrobat/catroid/ui/recyclerview/adapter/draganddrop/TouchHelperAdapterInterface;)V", "onSelectedChanged", "", "viewHolder", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "actionState", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public final class MultiViewTouchHelperCallback extends TouchHelperCallback {
        public MultiViewTouchHelperCallback(TouchHelperAdapterInterface touchHelperAdapterInterface) {
            super(touchHelperAdapterInterface);
        }

        @Override // org.catrobat.catroid.ui.recyclerview.adapter.draganddrop.TouchHelperCallback, androidx.recyclerview.widget.ItemTouchHelper.Callback
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
            super.onSelectedChanged(viewHolder, actionState);
            if (actionState == 0) {
                List<Sprite> items = SpriteListFragment.this.adapter.getItems();
                for (Sprite sprite : items) {
                    if (!(sprite instanceof GroupSprite) && sprite != null && sprite.toBeConverted()) {
                        items.set(items.indexOf(sprite), SpriteListFragment.this.spriteController.convert(sprite));
                    }
                }
                for (Sprite sprite2 : items) {
                    if (sprite2 instanceof GroupSprite) {
                        GroupSprite groupSprite = (GroupSprite) sprite2;
                        groupSprite.setCollapsed(groupSprite.isCollapsed());
                    }
                }
                SpriteListFragment.this.adapter.notifyDataSetChanged();
            }
        }
    }

    @Override // org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment
    public boolean shouldShowEmptyView() {
        return this.adapter.getItemCount() == 1;
    }

    @Override // org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment, androidx.fragment.app.Fragment
    public void onResume() {
        String name2;
        initializeAdapter();
        super.onResume();
        SnackbarUtil.showHintSnackbar(requireActivity(), R.string.hint_objects);
        Project currentProject = getProjectManager().getCurrentProject();
        if (currentProject.getSceneList().size() < 2) {
            name2 = currentProject.getName();
            Intrinsics.checkNotNull(name2);
        } else {
            Scene currentlyEditedScene = getProjectManager().getCurrentlyEditedScene();
            name2 = currentProject.getName() + ": " + currentlyEditedScene.getName();
        }
        PreferenceManager.getDefaultSharedPreferences(requireContext()).edit().putBoolean(SharedPreferenceKeys.INDEXING_VARIABLE_PREFERENCE_KEY, false).apply();
        FragmentActivity fragmentActivityRequireActivity = requireActivity();
        Intrinsics.checkNotNull(fragmentActivityRequireActivity, "null cannot be cast to non-null type androidx.appcompat.app.AppCompatActivity");
        ActionBar supportActionBar = ((AppCompatActivity) fragmentActivityRequireActivity).getSupportActionBar();
        if (supportActionBar == null) {
            return;
        }
        supportActionBar.setTitle(name2);
    }

    @Override // org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment
    public void onAdapterReady() {
        super.onAdapterReady();
        this.touchHelper = new ItemTouchHelper(new MultiViewTouchHelperCallback(this.adapter));
        this.touchHelper.attachToRecyclerView(this.recyclerView);
    }

    @Override // org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment, androidx.fragment.app.Fragment
    public void onPrepareOptionsMenu(Menu menu) {
        Intrinsics.checkNotNullParameter(menu, "menu");
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.new_group).setVisible(true);
        menu.findItem(R.id.editor3d).setVisible(true);
    }

    @Override // org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment, androidx.fragment.app.Fragment
    public boolean onOptionsItemSelected(MenuItem item) {
        Intrinsics.checkNotNullParameter(item, "item");
        this.itemCountThreshold = 2;
        if (item.getItemId() == R.id.new_group) {
            showNewGroupDialog();
            return true;
        }
        handleSelectedOptionItem(item);
        return true;
    }

    private final void handleSelectedOptionItem(MenuItem item) {
        if (this.adapter.getItems().size() == 1) {
            ToastUtil.showError(getActivity(), R.string.am_empty_list);
            resetActionModeParameters();
        } else {
            super.onOptionsItemSelected(item);
        }
    }

    private final void showNewGroupDialog() {
        TextInputDialog.Builder builder = new TextInputDialog.Builder(requireContext());
        builder.setHint(getString(R.string.sprite_group_name_label)).setTextWatcher(new DuplicateInputTextWatcher(this.adapter.getItems())).setText(new UniqueNameProvider().getUniqueNameInNameables(getString(R.string.default_group_name), this.adapter.getItems())).setPositiveButton(getString(R.string.ok), new TextInputDialog.OnClickListener() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.SpriteListFragment$$ExternalSyntheticLambda1
            @Override // org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog.OnClickListener
            public final void onPositiveButtonClick(DialogInterface dialogInterface, String str) {
                SpriteListFragment.showNewGroupDialog$lambda$0(this.f$0, dialogInterface, str);
            }
        });
        builder.setTitle(R.string.new_group).setNegativeButton(R.string.cancel, (DialogInterface.OnClickListener) null).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Type inference incomplete: some casts might be missing */
    public static final void showNewGroupDialog$lambda$0(SpriteListFragment this$0, DialogInterface dialogInterface, String str) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.adapter.add(new GroupSprite(str));
    }

    @Override // org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment
    public void initializeAdapter() {
        this.sharedPreferenceDetailsKey = SharedPreferenceKeys.SHOW_DETAILS_SPRITES_PREFERENCE_KEY;
        this.adapter = new MultiViewSpriteAdapter(getProjectManager().getCurrentlyEditedScene().getSpriteList());
        this.emptyView.setText(R.string.fragment_sprite_text_description);
        onAdapterReady();
    }

    @Override // org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment
    protected void packItems(List<? extends Sprite> selectedItems) {
        Intrinsics.checkNotNullParameter(selectedItems, "selectedItems");
        setShowProgressBar(true);
        Iterator<? extends Sprite> it = selectedItems.iterator();
        int i = 0;
        while (it.hasNext()) {
            try {
                BackpackListManager.getInstance().getSprites().add(this.spriteController.pack(it.next()));
                BackpackListManager.getInstance().saveBackpack();
                i++;
            } catch (IOException e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
        }
        if (i > 0) {
            ToastUtil.showSuccess(requireContext(), getResources().getQuantityString(R.plurals.packed_sprites, i, Integer.valueOf(i)));
            switchToBackpack();
        }
        finishActionMode();
    }

    @Override // org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment
    protected boolean isBackpackEmpty() {
        return BackpackListManager.getInstance().getSprites().isEmpty();
    }

    @Override // org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment
    protected void switchToBackpack() {
        FragmentActivity activity = getActivity();
        View viewFindViewById = activity != null ? activity.findViewById(R.id.workspace_layout) : null;
        WorkspaceLayout workspaceLayout = viewFindViewById instanceof WorkspaceLayout ? (WorkspaceLayout) viewFindViewById : null;
        if (workspaceLayout != null && workspaceLayout.getVisibility() == 0) {
            workspaceLayout.openWindow("BackpackSprites", "Рюкзак: Спрайты", new Function0<Fragment>() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.SpriteListFragment.switchToBackpack.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                public final Fragment invoke() {
                    return new BackpackSpriteFragment();
                }
            });
            return;
        }
        Intent intent = new Intent(requireContext(), (Class<?>) BackpackActivity.class);
        intent.putExtra("fragmentPosition", 1);
        startActivity(intent);
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    @Override // org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment
    protected void copyItems(List<? extends Sprite> selectedItems) {
        Intrinsics.checkNotNullParameter(selectedItems, "selectedItems");
        setShowProgressBar(true);
        Project currentProject = getProjectManager().getCurrentProject();
        Scene currentlyEditedScene = getProjectManager().getCurrentlyEditedScene();
        Iterator<? extends Sprite> it = selectedItems.iterator();
        int i = 0;
        while (it.hasNext()) {
            try {
                this.adapter.add(this.spriteController.copy(it.next(), currentProject, currentlyEditedScene));
                i++;
            } catch (IOException e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
        }
        if (i > 0) {
            ToastUtil.showSuccess(requireContext(), getResources().getQuantityString(R.plurals.copied_sprites, i, Integer.valueOf(i)));
        }
        finishActionMode();
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    @Override // org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment
    protected void deleteItems(List<? extends Sprite> selectedItems) {
        Intrinsics.checkNotNullParameter(selectedItems, "selectedItems");
        setShowProgressBar(true);
        int i = 0;
        for (Sprite sprite : selectedItems) {
            if (sprite instanceof GroupSprite) {
                for (GroupItemSprite groupItemSprite : ((GroupSprite) sprite).getGroupItems()) {
                    groupItemSprite.setConvertToSprite(true);
                    this.adapter.getItems().set(this.adapter.getItems().indexOf(groupItemSprite), this.spriteController.convert(groupItemSprite));
                }
                this.adapter.notifyDataSetChanged();
            }
            this.spriteController.delete(sprite);
            this.adapter.remove(sprite);
            i++;
        }
        ToastUtil.showSuccess(requireContext(), getResources().getQuantityString(R.plurals.deleted_sprites, i, Integer.valueOf(i)));
        finishActionMode();
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Uri uriFromFile;
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == -1) {
            if (data != null && data.hasExtra("merge")) {
                uriFromFile = Uri.fromFile(new File(data.getStringExtra("merge")));
            } else {
                uriFromFile = Uri.fromFile(new File(data != null ? data.getStringExtra(WebViewActivity.MEDIA_FILE_PATH) : null));
            }
            Scene currentlyEditedScene = getProjectManager().getCurrentlyEditedScene();
            String strResolveFileName = StorageOperations.resolveFileName(requireActivity().getContentResolver(), uriFromFile);
            if (strResolveFileName == null || Intrinsics.areEqual(StorageOperations.getSanitizedFileName(strResolveFileName), Constants.TMP_IMAGE_FILE_NAME)) {
                String string = getString(R.string.default_sprite_name);
                Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
                strResolveFileName = string + Constants.CATROBAT_EXTENSION;
            } else {
                Intrinsics.checkNotNull(strResolveFileName);
            }
            Intrinsics.checkNotNull(currentlyEditedScene);
            FragmentActivity fragmentActivityRequireActivity = requireActivity();
            Intrinsics.checkNotNullExpressionValue(fragmentActivityRequireActivity, "requireActivity(...)");
            ImportProjectHelper importProjectHelper = new ImportProjectHelper(strResolveFileName, currentlyEditedScene, fragmentActivityRequireActivity);
            if (importProjectHelper.checkForConflicts()) {
                Sprite sprite = this.currentSprite;
                if (sprite != null) {
                    importProjectHelper.addObjectDataToNewSprite(sprite);
                } else {
                    importProjectHelper.rejectImportDialog(null);
                }
            }
        }
    }

    private final void addFromLibrary(Sprite selectedItem) {
        this.currentSprite = selectedItem;
        Intent intent = new Intent(requireContext(), (Class<?>) WebViewActivity.class);
        intent.putExtra("url", FlavoredConstants.LIBRARY_OBJECT_URL);
        startActivityForResult(intent, 0);
    }

    private final void addFromLocalProject(Sprite item) {
        this.currentSprite = item;
        Intent intent = new Intent(requireContext(), (Class<?>) ProjectListActivity.class);
        intent.putExtra("merge", getString(R.string.import_sprite_from_project_launcher));
        startActivityForResult(intent, 0);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment
    public void renameItem(Sprite item, String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        if (item != null) {
            item.rename(name2);
        }
        finishActionMode();
    }

    @Override // org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment
    public void onItemClick(Sprite item, MultiSelectionManager selectionManager) {
        if (item instanceof GroupSprite) {
            GroupSprite groupSprite = (GroupSprite) item;
            groupSprite.setCollapsed(!groupSprite.isCollapsed());
            this.adapter.notifyDataSetChanged();
            return;
        }
        int i = this.actionModeType;
        if (i != 0) {
            if (i == 4) {
                super.onItemClick(item, (MultiSelectionManager) null);
                return;
            } else {
                super.onItemClick(item, selectionManager);
                return;
            }
        }
        getProjectManager().setCurrentSprite(item);
        FragmentActivity activity = getActivity();
        View viewFindViewById = activity != null ? activity.findViewById(R.id.workspace_layout) : null;
        WorkspaceLayout workspaceLayout = viewFindViewById instanceof WorkspaceLayout ? (WorkspaceLayout) viewFindViewById : null;
        if (workspaceLayout != null && workspaceLayout.getVisibility() == 0) {
            String str = ScriptFragment.TAG;
            Intrinsics.checkNotNull(str);
            if (workspaceLayout.isWindowOpen(str)) {
                workspaceLayout.replaceWindowContent(str, new Function0<Fragment>() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.SpriteListFragment.onItemClick.1
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // kotlin.jvm.functions.Function0
                    public final Fragment invoke() {
                        return new ScriptFragment();
                    }
                });
            } else {
                workspaceLayout.openWindow(str, "Скрипты", new Function0<Fragment>() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.SpriteListFragment.onItemClick.2
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // kotlin.jvm.functions.Function0
                    public final Fragment invoke() {
                        return new ScriptFragment();
                    }
                });
            }
            String str2 = LookListFragment.TAG;
            Intrinsics.checkNotNull(str2);
            if (workspaceLayout.isWindowOpen(str2)) {
                workspaceLayout.replaceWindowContent(str2, new Function0<Fragment>() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.SpriteListFragment.onItemClick.3
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // kotlin.jvm.functions.Function0
                    public final Fragment invoke() {
                        return new LookListFragment();
                    }
                });
            }
            String str3 = SoundListFragment.TAG;
            Intrinsics.checkNotNull(str3);
            if (workspaceLayout.isWindowOpen(str3)) {
                workspaceLayout.replaceWindowContent(str3, new Function0<Fragment>() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.SpriteListFragment.onItemClick.4
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // kotlin.jvm.functions.Function0
                    public final Fragment invoke() {
                        return new SoundListFragment();
                    }
                });
            }
            workspaceLayout.removeWindow(TAG, false);
            return;
        }
        Intent intent = new Intent(requireContext(), (Class<?>) SpriteActivity.class);
        intent.putExtra("fragmentPosition", 0);
        startActivity(intent);
    }

    @Override // org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment
    public void onItemLongClick(Sprite item, CheckableViewHolder holder) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        super.onItemLongClick(item, holder);
    }

    @Override // org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter.OnItemClickListener
    public void onSettingsClick(final Sprite item, View view) {
        Intrinsics.checkNotNullParameter(view, "view");
        final ArrayList arrayList = new ArrayList();
        arrayList.add(item);
        List listMutableListOf = CollectionsKt.mutableListOf(Integer.valueOf(R.id.new_group), Integer.valueOf(R.id.project_options), Integer.valueOf(R.id.project_files), Integer.valueOf(R.id.project_libs), Integer.valueOf(R.id.editor3d), Integer.valueOf(R.id.new_scene), Integer.valueOf(R.id.show_details), Integer.valueOf(R.id.edit));
        boolean z = item instanceof GroupSprite;
        if (z) {
            listMutableListOf.add(Integer.valueOf(R.id.backpack));
            listMutableListOf.add(Integer.valueOf(R.id.copy));
            listMutableListOf.add(Integer.valueOf(R.id.from_library));
            listMutableListOf.add(Integer.valueOf(R.id.from_local));
        }
        PopupMenu popupMenuCreateSettingsPopUpMenu = UiUtils.createSettingsPopUpMenu(view, requireContext(), R.menu.menu_project_activity, CollectionsKt.toIntArray(listMutableListOf));
        Intrinsics.checkNotNullExpressionValue(popupMenuCreateSettingsPopUpMenu, "createSettingsPopUpMenu(...)");
        popupMenuCreateSettingsPopUpMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.SpriteListFragment$$ExternalSyntheticLambda0
            @Override // android.widget.PopupMenu.OnMenuItemClickListener
            public final boolean onMenuItemClick(MenuItem menuItem) {
                return SpriteListFragment.onSettingsClick$lambda$1(this.f$0, arrayList, item, menuItem);
            }
        });
        if (!z) {
            popupMenuCreateSettingsPopUpMenu.getMenu().findItem(R.id.backpack).setTitle(R.string.pack);
            popupMenuCreateSettingsPopUpMenu.getMenu().removeItem(R.id.from_local);
        }
        popupMenuCreateSettingsPopUpMenu.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean onSettingsClick$lambda$1(SpriteListFragment this$0, List itemList, Sprite sprite, MenuItem menuItem) {
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
            case R.id.from_library /* 2131364490 */:
                this$0.addFromLibrary(sprite);
                break;
            case R.id.from_local /* 2131364491 */:
                this$0.addFromLocalProject(sprite);
                break;
            case R.id.rename /* 2131365129 */:
                this$0.showRenameDialog(sprite);
                break;
        }
        return true;
    }

    public final boolean isSingleVisibleSprite() {
        return this.adapter.getItems().size() == 2 && !(this.adapter.getItems().get(1) instanceof GroupSprite);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/fragment/SpriteListFragment$Companion.class
      classes14.dex
     */
    /* JADX INFO: compiled from: SpriteListFragment.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lorg/catrobat/catroid/ui/recyclerview/fragment/SpriteListFragment$Companion;", "", "()V", "IMPORT_OBJECT_REQUEST_CODE", "", "TAG", "", "getTAG", "()Ljava/lang/String;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final String getTAG() {
            return SpriteListFragment.TAG;
        }
    }

    static {
        Intrinsics.checkNotNullExpressionValue("SpriteListFragment", "getSimpleName(...)");
        TAG = "SpriteListFragment";
    }
}
