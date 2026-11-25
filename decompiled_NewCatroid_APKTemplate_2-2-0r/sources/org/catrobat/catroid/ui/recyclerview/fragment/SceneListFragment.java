package org.catrobat.catroid.ui.recyclerview.fragment;

import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import com.google.android.gms.analytics.ecommerce.Promotion;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.SharedPreferenceKeys;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.io.XstreamSerializer;
import org.catrobat.catroid.io.asynctask.ProjectLoader;
import org.catrobat.catroid.io.asynctask.ProjectLoaderKt;
import org.catrobat.catroid.ui.UiUtils;
import org.catrobat.catroid.ui.controller.BackpackListManager;
import org.catrobat.catroid.ui.recyclerview.adapter.SceneAdapter;
import org.catrobat.catroid.ui.recyclerview.adapter.multiselection.MultiSelectionManager;
import org.catrobat.catroid.ui.recyclerview.backpack.BackpackActivity;
import org.catrobat.catroid.ui.recyclerview.backpack.BackpackSceneFragment;
import org.catrobat.catroid.ui.recyclerview.controller.SceneController;
import org.catrobat.catroid.ui.workspace.WorkspaceLayout;
import org.catrobat.catroid.utils.ToastUtil;
import org.jetbrains.kotlin.org.jline.reader.LineReader;
import org.koin.android.ext.android.ComponentCallbackExtKt;
import org.koin.core.qualifier.Qualifier;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/fragment/SceneListFragment.class
  classes14.dex
 */
/* JADX INFO: compiled from: SceneListFragment.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\u0018\u0000 -2\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00012\u00020\u0003:\u0001-B\u0005¢\u0006\u0002\u0010\u0004J\u0018\u0010\r\u001a\u00020\u000e2\u000e\u0010\u000f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0010H\u0014J\b\u0010\u0011\u001a\u00020\u000eH\u0002J\u0018\u0010\u0012\u001a\u00020\u000e2\u000e\u0010\u000f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0010H\u0014J\b\u0010\u0013\u001a\u00020\u0014H\u0015J\b\u0010\u0015\u001a\u00020\u0014H\u0014J\b\u0010\u0016\u001a\u00020\u0014H\u0014J\b\u0010\u0017\u001a\u00020\u000eH\u0014J\b\u0010\u0018\u001a\u00020\u0019H\u0014J\u001c\u0010\u001a\u001a\u00020\u000e2\b\u0010\u001b\u001a\u0004\u0018\u00010\u00022\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0016J\u0010\u0010\u001e\u001a\u00020\u000e2\u0006\u0010\u001f\u001a\u00020\u0019H\u0016J\u0010\u0010 \u001a\u00020\u000e2\u0006\u0010!\u001a\u00020\"H\u0016J\b\u0010#\u001a\u00020\u000eH\u0016J\u001a\u0010$\u001a\u00020\u000e2\b\u0010\u001b\u001a\u0004\u0018\u00010\u00022\u0006\u0010%\u001a\u00020&H\u0016J\u0018\u0010'\u001a\u00020\u000e2\u000e\u0010\u000f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0010H\u0014J\u001a\u0010(\u001a\u00020\u000e2\b\u0010\u001b\u001a\u0004\u0018\u00010\u00022\u0006\u0010)\u001a\u00020*H\u0014J\b\u0010+\u001a\u00020\u000eH\u0014J\b\u0010,\u001a\u00020\u000eH\u0002R\u001b\u0010\u0005\u001a\u00020\u00068BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006."}, d2 = {"Lorg/catrobat/catroid/ui/recyclerview/fragment/SceneListFragment;", "Lorg/catrobat/catroid/ui/recyclerview/fragment/RecyclerViewFragment;", "Lorg/catrobat/catroid/content/Scene;", "Lorg/catrobat/catroid/io/asynctask/ProjectLoader$ProjectLoadListener;", "()V", "projectManager", "Lorg/catrobat/catroid/ProjectManager;", "getProjectManager", "()Lorg/catrobat/catroid/ProjectManager;", "projectManager$delegate", "Lkotlin/Lazy;", "sceneController", "Lorg/catrobat/catroid/ui/recyclerview/controller/SceneController;", "copyItems", "", "selectedItems", "", "createEmptySceneWithDefaultName", "deleteItems", "getDeleteAlertTitleId", "", "getRenameDialogHint", "getRenameDialogTitle", "initializeAdapter", "isBackpackEmpty", "", "onItemClick", "item", "selectionManager", "Lorg/catrobat/catroid/ui/recyclerview/adapter/multiselection/MultiSelectionManager;", "onLoadFinished", FirebaseAnalytics.Param.SUCCESS, "onPrepareOptionsMenu", LineReader.MENU, "Landroid/view/Menu;", "onResume", "onSettingsClick", Promotion.ACTION_VIEW, "Landroid/view/View;", "packItems", "renameItem", "name", "", "switchToBackpack", "switchToSpriteListFragment", "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SceneListFragment extends RecyclerViewFragment<Scene> implements ProjectLoader.ProjectLoadListener {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String TAG;

    /* JADX INFO: renamed from: projectManager$delegate, reason: from kotlin metadata */
    private final Lazy projectManager;
    private final SceneController sceneController = new SceneController();

    @Override // org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment
    protected int getDeleteAlertTitleId() {
        return R.plurals.delete_scenes;
    }

    @Override // org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment
    protected int getRenameDialogHint() {
        return R.string.scene_name_label;
    }

    @Override // org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment
    protected int getRenameDialogTitle() {
        return R.string.rename_scene_dialog;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public SceneListFragment() {
        final SceneListFragment sceneListFragment = this;
        LazyThreadSafetyMode lazyThreadSafetyMode = LazyThreadSafetyMode.NONE;
        final Qualifier qualifier = null;
        final Object[] objArr = 0 == true ? 1 : 0;
        this.projectManager = LazyKt.lazy(lazyThreadSafetyMode, (Function0) new Function0<ProjectManager>() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.SceneListFragment$special$$inlined$inject$default$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Type inference failed for: r0v4, types: [java.lang.Object, org.catrobat.catroid.ProjectManager] */
            @Override // kotlin.jvm.functions.Function0
            public final ProjectManager invoke() {
                ComponentCallbacks componentCallbacks = sceneListFragment;
                return ComponentCallbackExtKt.getKoin(componentCallbacks).get_scopeRegistry().getRootScope().get(Reflection.getOrCreateKotlinClass(ProjectManager.class), qualifier, objArr);
            }
        });
    }

    private final ProjectManager getProjectManager() {
        return (ProjectManager) this.projectManager.getValue();
    }

    @Override // org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        Project currentProject = getProjectManager().getCurrentProject();
        if (currentProject.getSceneList().size() < 2) {
            getProjectManager().setCurrentlyEditedScene(currentProject.getDefaultScene());
            switchToSpriteListFragment();
        }
        getProjectManager().setCurrentlyEditedScene(currentProject.getDefaultScene());
        FragmentActivity fragmentActivityRequireActivity = requireActivity();
        Intrinsics.checkNotNull(fragmentActivityRequireActivity, "null cannot be cast to non-null type androidx.appcompat.app.AppCompatActivity");
        ActionBar supportActionBar = ((AppCompatActivity) fragmentActivityRequireActivity).getSupportActionBar();
        if (supportActionBar == null) {
            return;
        }
        supportActionBar.setTitle(currentProject.getName());
    }

    private final void switchToSpriteListFragment() {
        getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, new SpriteListFragment(), SpriteListFragment.INSTANCE.getTAG()).commit();
    }

    @Override // org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment, androidx.fragment.app.Fragment
    public void onPrepareOptionsMenu(Menu menu) {
        Intrinsics.checkNotNullParameter(menu, "menu");
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.new_group).setVisible(false);
        menu.findItem(R.id.new_scene).setVisible(false);
    }

    @Override // org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment
    protected void initializeAdapter() {
        this.sharedPreferenceDetailsKey = SharedPreferenceKeys.SHOW_DETAILS_SCENES_PREFERENCE_KEY;
        this.adapter = new SceneAdapter(getProjectManager().getCurrentProject().getSceneList());
        onAdapterReady();
    }

    @Override // org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment
    protected void packItems(List<? extends Scene> selectedItems) {
        Intrinsics.checkNotNullParameter(selectedItems, "selectedItems");
        setShowProgressBar(true);
        Iterator<? extends Scene> it = selectedItems.iterator();
        int i = 0;
        while (it.hasNext()) {
            try {
                BackpackListManager.getInstance().getScenes().add(this.sceneController.pack(it.next()));
                BackpackListManager.getInstance().saveBackpack();
                i++;
            } catch (IOException e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
        }
        if (i > 0) {
            ToastUtil.showSuccess(getActivity(), getResources().getQuantityString(R.plurals.packed_scenes, i, Integer.valueOf(i)));
            switchToBackpack();
        }
        finishActionMode();
    }

    @Override // org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment
    protected boolean isBackpackEmpty() {
        return BackpackListManager.getInstance().getScenes().isEmpty();
    }

    @Override // org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment
    protected void switchToBackpack() {
        FragmentActivity activity = getActivity();
        View viewFindViewById = activity != null ? activity.findViewById(R.id.workspace_layout) : null;
        WorkspaceLayout workspaceLayout = viewFindViewById instanceof WorkspaceLayout ? (WorkspaceLayout) viewFindViewById : null;
        if (workspaceLayout != null && workspaceLayout.getVisibility() == 0) {
            workspaceLayout.openWindow("BackpackScenes", "Рюкзак: Сцены", new Function0<Fragment>() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.SceneListFragment.switchToBackpack.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                public final Fragment invoke() {
                    return new BackpackSceneFragment();
                }
            });
            return;
        }
        Intent intent = new Intent(getActivity(), (Class<?>) BackpackActivity.class);
        intent.putExtra("fragmentPosition", 0);
        startActivity(intent);
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    @Override // org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment
    protected void copyItems(List<? extends Scene> selectedItems) {
        Intrinsics.checkNotNullParameter(selectedItems, "selectedItems");
        setShowProgressBar(true);
        Iterator<? extends Scene> it = selectedItems.iterator();
        int i = 0;
        while (it.hasNext()) {
            try {
                this.adapter.add(this.sceneController.copy(it.next(), getProjectManager().getCurrentProject()));
                i++;
            } catch (IOException e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
        }
        if (i > 0) {
            ToastUtil.showSuccess(getActivity(), getResources().getQuantityString(R.plurals.copied_scenes, i, Integer.valueOf(i)));
        }
        finishActionMode();
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    @Override // org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment
    protected void deleteItems(List<? extends Scene> selectedItems) {
        Intrinsics.checkNotNullParameter(selectedItems, "selectedItems");
        setShowProgressBar(true);
        int i = 0;
        for (Scene scene : selectedItems) {
            try {
                this.sceneController.delete(scene);
            } catch (IOException e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
            this.adapter.remove(scene);
            i++;
        }
        ToastUtil.showSuccess(getActivity(), getResources().getQuantityString(R.plurals.deleted_scenes, i, Integer.valueOf(i)));
        finishActionMode();
        if (this.adapter.getItems().isEmpty()) {
            createEmptySceneWithDefaultName();
        }
        Project currentProject = getProjectManager().getCurrentProject();
        if (currentProject.getSceneList().size() < 2) {
            getProjectManager().setCurrentlyEditedScene(currentProject.getDefaultScene());
            switchToSpriteListFragment();
        }
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    private final void createEmptySceneWithDefaultName() {
        setShowProgressBar(true);
        Project currentProject = getProjectManager().getCurrentProject();
        Scene scene = new Scene(getString(R.string.default_scene_name), currentProject);
        Sprite sprite = new Sprite(getString(R.string.background));
        sprite.look.setZIndex(0);
        scene.addSprite(sprite);
        this.adapter.add(scene);
        if (!currentProject.hasScene()) {
            currentProject.addScene(scene);
        }
        setShowProgressBar(false);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment
    public void renameItem(Scene item, String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        if (!Intrinsics.areEqual(item != null ? item.getName() : null, name2)) {
            if (this.sceneController.rename(item, name2)) {
                Project currentProject = getProjectManager().getCurrentProject();
                XstreamSerializer.getInstance().saveProject(currentProject);
                File directory = currentProject.getDirectory();
                Context applicationContext = requireContext().getApplicationContext();
                Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
                ProjectLoaderKt.loadProject(directory, applicationContext);
                initializeAdapter();
            } else {
                ToastUtil.showError(getActivity(), R.string.error_rename_scene);
            }
        }
        finishActionMode();
    }

    @Override // org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment
    public void onItemClick(Scene item, MultiSelectionManager selectionManager) {
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
        getProjectManager().setCurrentlyEditedScene(item);
        FragmentActivity activity = getActivity();
        View viewFindViewById = activity != null ? activity.findViewById(R.id.workspace_layout) : null;
        WorkspaceLayout workspaceLayout = viewFindViewById instanceof WorkspaceLayout ? (WorkspaceLayout) viewFindViewById : null;
        if (workspaceLayout != null && workspaceLayout.getVisibility() == 0) {
            workspaceLayout.openWindow(SpriteListFragment.INSTANCE.getTAG(), "Спрайты", new Function0<Fragment>() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.SceneListFragment.onItemClick.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                public final Fragment invoke() {
                    return new SpriteListFragment();
                }
            });
            workspaceLayout.removeWindow(TAG, false);
            Fragment fragmentFindFragmentByTag = requireActivity().getSupportFragmentManager().findFragmentByTag(SpriteListFragment.INSTANCE.getTAG());
            SpriteListFragment spriteListFragment = fragmentFindFragmentByTag instanceof SpriteListFragment ? (SpriteListFragment) fragmentFindFragmentByTag : null;
            if (spriteListFragment != null) {
                spriteListFragment.initializeAdapter();
                return;
            }
            return;
        }
        getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, new SpriteListFragment(), SpriteListFragment.INSTANCE.getTAG()).addToBackStack(SpriteListFragment.INSTANCE.getTAG()).commit();
    }

    @Override // org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter.OnItemClickListener
    public void onSettingsClick(final Scene item, View view) {
        Intrinsics.checkNotNullParameter(view, "view");
        final ArrayList arrayList = new ArrayList();
        arrayList.add(item);
        PopupMenu popupMenuCreateSettingsPopUpMenu = UiUtils.createSettingsPopUpMenu(view, requireContext(), R.menu.menu_project_activity, new int[]{R.id.new_group, R.id.new_scene, R.id.show_details, R.id.project_options, R.id.project_files, R.id.project_libs, R.id.editor3d, R.id.edit, R.id.from_local, R.id.from_library});
        Intrinsics.checkNotNullExpressionValue(popupMenuCreateSettingsPopUpMenu, "createSettingsPopUpMenu(...)");
        popupMenuCreateSettingsPopUpMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.SceneListFragment$$ExternalSyntheticLambda0
            @Override // android.widget.PopupMenu.OnMenuItemClickListener
            public final boolean onMenuItemClick(MenuItem menuItem) {
                return SceneListFragment.onSettingsClick$lambda$0(this.f$0, arrayList, item, menuItem);
            }
        });
        popupMenuCreateSettingsPopUpMenu.getMenu().findItem(R.id.backpack).setTitle(R.string.pack);
        popupMenuCreateSettingsPopUpMenu.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean onSettingsClick$lambda$0(SceneListFragment this$0, List itemList, Scene scene, MenuItem menuItem) {
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
                this$0.deleteItems(itemList);
                break;
            case R.id.rename /* 2131365129 */:
                this$0.showRenameDialog(scene);
                break;
        }
        return true;
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    @Override // org.catrobat.catroid.io.asynctask.ProjectLoader.ProjectLoadListener
    public void onLoadFinished(boolean success) {
        if (!success) {
            ToastUtil.showError(getActivity(), R.string.error_load_project);
        } else {
            this.adapter.setItems(getProjectManager().getCurrentProject().getSceneList());
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/fragment/SceneListFragment$Companion.class
      classes14.dex
     */
    /* JADX INFO: compiled from: SceneListFragment.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/catrobat/catroid/ui/recyclerview/fragment/SceneListFragment$Companion;", "", "()V", "TAG", "", "getTAG", "()Ljava/lang/String;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final String getTAG() {
            return SceneListFragment.TAG;
        }
    }

    static {
        Intrinsics.checkNotNullExpressionValue("SceneListFragment", "getSimpleName(...)");
        TAG = "SceneListFragment";
    }
}
