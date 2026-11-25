package org.catrobat.catroid.ui;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentCallbacks;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.textfield.TextInputEditText;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.text.StringsKt;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.FlavoredConstants;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.common.SharedPreferenceKeys;
import org.catrobat.catroid.common.SoundInfo;
import org.catrobat.catroid.content.MyActivityManager;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.StartScript;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.content.bricks.FormulaBrick;
import org.catrobat.catroid.content.bricks.PlaceAtBrick;
import org.catrobat.catroid.content.bricks.PointInDirectionBrick;
import org.catrobat.catroid.content.bricks.SetSizeToBrick;
import org.catrobat.catroid.content.bricks.VisualPlacementBrick;
import org.catrobat.catroid.databinding.ActivityRecyclerBinding;
import org.catrobat.catroid.databinding.DialogNewActorBinding;
import org.catrobat.catroid.databinding.ListActionButtonsBinding;
import org.catrobat.catroid.databinding.ProgressBarBinding;
import org.catrobat.catroid.editor.EditorActivity;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.UserList;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.io.StorageOperations;
import org.catrobat.catroid.io.XstreamSerializer;
import org.catrobat.catroid.io.asynctask.ProjectSaver;
import org.catrobat.catroid.libraries.LibraryManager;
import org.catrobat.catroid.merge.ImportProjectHelper;
import org.catrobat.catroid.pocketmusic.PocketMusicActivity;
import org.catrobat.catroid.sensing.CollisionInformation;
import org.catrobat.catroid.soundrecorder.SoundRecorderActivity;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.stage.TestResult;
import org.catrobat.catroid.ui.controller.ActorsAndObjectsManager;
import org.catrobat.catroid.ui.controller.BackpackListManager;
import org.catrobat.catroid.ui.dialogs.LegoSensorConfigInfoDialog;
import org.catrobat.catroid.ui.fragment.FormulaEditorFragment;
import org.catrobat.catroid.ui.fragment.ProjectFilesFragment;
import org.catrobat.catroid.ui.fragment.ProjectLibsFragment;
import org.catrobat.catroid.ui.fragment.ProjectOptionsFragment;
import org.catrobat.catroid.ui.recyclerview.backpack.ActorAndObjectActivity;
import org.catrobat.catroid.ui.recyclerview.backpack.BackpackActivity;
import org.catrobat.catroid.ui.recyclerview.controller.SceneController;
import org.catrobat.catroid.ui.recyclerview.dialog.NewSpriteDialogFragment;
import org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog;
import org.catrobat.catroid.ui.recyclerview.dialog.textwatcher.DuplicateInputTextWatcher;
import org.catrobat.catroid.ui.recyclerview.fragment.DataListFragment;
import org.catrobat.catroid.ui.recyclerview.fragment.LookListFragment;
import org.catrobat.catroid.ui.recyclerview.fragment.ProjectListFragment;
import org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment;
import org.catrobat.catroid.ui.recyclerview.fragment.SceneListFragment;
import org.catrobat.catroid.ui.recyclerview.fragment.ScriptFragment;
import org.catrobat.catroid.ui.recyclerview.fragment.SoundListFragment;
import org.catrobat.catroid.ui.recyclerview.fragment.SpriteListFragment;
import org.catrobat.catroid.ui.recyclerview.util.UniqueNameProvider;
import org.catrobat.catroid.ui.settingsfragments.SettingsFragment;
import org.catrobat.catroid.ui.workspace.WorkspaceLayout;
import org.catrobat.catroid.utils.ExtensionsKt;
import org.catrobat.catroid.utils.ToastUtil;
import org.catrobat.catroid.utils.Utils;
import org.catrobat.catroid.visualplacement.VisualPlacementActivity;
import org.jetbrains.kotlin.org.jline.reader.LineReader;
import org.koin.android.ext.android.ComponentCallbackExtKt;
import org.koin.core.qualifier.Qualifier;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/ProjectActivity.class
  classes14.dex
 */
/* JADX INFO: compiled from: ProjectActivity.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 F2\u00020\u0001:\u0001FB\u0005¢\u0006\u0002\u0010\u0002J\u001c\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u00162\b\b\u0002\u0010\u0017\u001a\u00020\nH\u0002J\b\u0010\u0018\u001a\u00020\u0014H\u0002J\u001c\u0010\u0019\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u00162\b\b\u0002\u0010\u0017\u001a\u00020\nH\u0002J\u0010\u0010\u001a\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016J\u0012\u0010\u001b\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0002J\u001c\u0010\u001c\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u00162\b\b\u0002\u0010\u0017\u001a\u00020\nH\u0002J\"\u0010\u001d\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u00162\u0006\u0010\u001e\u001a\u00020\n2\u0006\u0010\u001f\u001a\u00020 H\u0002J\b\u0010!\u001a\u00020\u0014H\u0002J\u0006\u0010\"\u001a\u00020\u0014J\u0006\u0010#\u001a\u00020\u0014J\u0006\u0010$\u001a\u00020\u0014J\b\u0010%\u001a\u00020\u0014H\u0002J\u0006\u0010&\u001a\u00020\u0014J\b\u0010'\u001a\u00020\u0014H\u0002J\b\u0010(\u001a\u00020\u0014H\u0002J\u0010\u0010)\u001a\u00020\u00142\u0006\u0010*\u001a\u00020+H\u0002J\"\u0010,\u001a\u00020\u00142\u0006\u0010-\u001a\u00020+2\u0006\u0010.\u001a\u00020+2\b\u0010/\u001a\u0004\u0018\u000100H\u0016J\b\u00101\u001a\u00020\u0014H\u0016J\u0012\u00102\u001a\u00020\u00142\b\u00103\u001a\u0004\u0018\u000104H\u0016J\u0010\u00105\u001a\u00020 2\u0006\u00106\u001a\u000207H\u0016J\u0010\u00108\u001a\u00020 2\u0006\u00109\u001a\u00020:H\u0016J\b\u0010;\u001a\u00020\u0014H\u0014J\b\u0010<\u001a\u00020\u0014H\u0002J\u0012\u0010=\u001a\u00020\u00142\b\u0010>\u001a\u0004\u0018\u00010?H\u0002J\u000e\u0010@\u001a\u00020\u00142\u0006\u0010A\u001a\u00020 J\b\u0010B\u001a\u00020\u0014H\u0002J\b\u0010C\u001a\u00020\u0014H\u0002J\u0012\u0010D\u001a\u00020\u00142\b\u0010E\u001a\u0004\u0018\u000104H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000R\u0016\u0010\u0005\u001a\u0004\u0018\u00010\u00068BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010\u000b\u001a\u00020\f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\r\u0010\u000eR\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006G"}, d2 = {"Lorg/catrobat/catroid/ui/ProjectActivity;", "Lorg/catrobat/catroid/ui/BaseCastActivity;", "()V", "binding", "Lorg/catrobat/catroid/databinding/ActivityRecyclerBinding;", "currentFragment", "Landroidx/fragment/app/Fragment;", "getCurrentFragment", "()Landroidx/fragment/app/Fragment;", "generatedVariableName", "", "projectManager", "Lorg/catrobat/catroid/ProjectManager;", "getProjectManager", "()Lorg/catrobat/catroid/ProjectManager;", "projectManager$delegate", "Lkotlin/Lazy;", "workspaceLayout", "Lorg/catrobat/catroid/ui/workspace/WorkspaceLayout;", "addBackgroundFromUri", "", "uri", "Landroid/net/Uri;", "imageExtension", "addEmptySpriteObject", "addLookFromUri", "addObjectFromUri", "addSoundFromUri", "addSpriteFromUri", "addSpriteObjectFromUri", SchemaSymbols.ATTVAL_EXTENSION, "isObject", "", "handleAddButton", "handleAddLookButton", "handleAddSceneButton", "handleAddSoundButton", "handleAddSpriteButton", "handleAddUserDataButton", "handleAiButton", "handlePlayButton", "loadFragment", "fragmentPosition", "", "onActivityResult", "requestCode", "resultCode", "data", "Landroid/content/Intent;", "onBackPressed", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onCreateOptionsMenu", LineReader.MENU, "Landroid/view/Menu;", "onOptionsItemSelected", "item", "Landroid/view/MenuItem;", "onPause", "recalculatePhysicsCache", "saveProject", "currentProject", "Lorg/catrobat/catroid/content/Project;", "setShowProgressBar", "show", "showLegoSensorConfigInfo", "showPhysicsCacheWarningDialog", "updateBrickFromVisualPlacement", "extras", "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ProjectActivity extends BaseCastActivity {
    public static final String EXTRA_FRAGMENT_POSITION = "fragmentPosition";
    public static final int FRAGMENT_SCENES = 0;
    public static final int FRAGMENT_SPRITES = 1;
    public static final int SPRITE_CAMERA = 3;
    public static final int SPRITE_FILE = 2;
    public static final int SPRITE_FROM_LOCAL = 5;
    public static final int SPRITE_LIBRARY = 1;
    public static final int SPRITE_OBJECT = 4;
    public static final int SPRITE_POCKET_PAINT = 0;
    private ActivityRecyclerBinding binding;
    private String generatedVariableName;

    /* JADX INFO: renamed from: projectManager$delegate, reason: from kotlin metadata */
    private final Lazy projectManager;
    private WorkspaceLayout workspaceLayout;

    /* JADX WARN: Multi-variable type inference failed */
    public ProjectActivity() {
        final ProjectActivity projectActivity = this;
        LazyThreadSafetyMode lazyThreadSafetyMode = LazyThreadSafetyMode.NONE;
        final Qualifier qualifier = null;
        final Object[] objArr = 0 == true ? 1 : 0;
        this.projectManager = LazyKt.lazy(lazyThreadSafetyMode, (Function0) new Function0<ProjectManager>() { // from class: org.catrobat.catroid.ui.ProjectActivity$special$$inlined$inject$default$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Type inference failed for: r0v4, types: [java.lang.Object, org.catrobat.catroid.ProjectManager] */
            @Override // kotlin.jvm.functions.Function0
            public final ProjectManager invoke() {
                ComponentCallbacks componentCallbacks = projectActivity;
                return ComponentCallbackExtKt.getKoin(componentCallbacks).get_scopeRegistry().getRootScope().get(Reflection.getOrCreateKotlinClass(ProjectManager.class), qualifier, objArr);
            }
        });
    }

    private final ProjectManager getProjectManager() {
        return (ProjectManager) this.projectManager.getValue();
    }

    @Override // org.catrobat.catroid.ui.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getProjectManager().getCurrentProject() == null) {
            Log.e("ProjectActivity", "Project is null during onCreate. Redirecting to ProjectListActivity.");
            Intent intent = new Intent(this, (Class<?>) ProjectListActivity.class);
            intent.addFlags(335544320);
            startActivity(intent);
            finish();
            return;
        }
        ActivityRecyclerBinding activityRecyclerBindingInflate = ActivityRecyclerBinding.inflate(getLayoutInflater());
        Intrinsics.checkNotNullExpressionValue(activityRecyclerBindingInflate, "inflate(...)");
        this.binding = activityRecyclerBindingInflate;
        if (isFinishing()) {
            return;
        }
        ActivityRecyclerBinding activityRecyclerBinding = this.binding;
        ActivityRecyclerBinding activityRecyclerBinding2 = null;
        if (activityRecyclerBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityRecyclerBinding = null;
        }
        setContentView(activityRecyclerBinding.getRoot());
        this.workspaceLayout = (WorkspaceLayout) findViewById(R.id.workspace_layout);
        ProjectActivity projectActivity = this;
        if (PreferenceManager.getDefaultSharedPreferences(projectActivity).getBoolean("pref_workspace_project_level", false)) {
            if (getProjectManager().getCurrentlyEditedScene() == null) {
                ProjectManager projectManager = getProjectManager();
                Project currentProject = getProjectManager().getCurrentProject();
                projectManager.setCurrentlyEditedScene(currentProject != null ? currentProject.getDefaultScene() : null);
            }
            if (getProjectManager().getCurrentSprite() == null) {
                ProjectManager projectManager2 = getProjectManager();
                Scene currentlyEditedScene = getProjectManager().getCurrentlyEditedScene();
                projectManager2.setCurrentSprite(currentlyEditedScene != null ? currentlyEditedScene.getBackgroundSprite() : null);
            }
            ActivityRecyclerBinding activityRecyclerBinding3 = this.binding;
            if (activityRecyclerBinding3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activityRecyclerBinding3 = null;
            }
            activityRecyclerBinding3.fragmentContainer.setVisibility(8);
            ActivityRecyclerBinding activityRecyclerBinding4 = this.binding;
            if (activityRecyclerBinding4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activityRecyclerBinding4 = null;
            }
            activityRecyclerBinding4.bottomBar.getRoot().setVisibility(8);
            ActivityRecyclerBinding activityRecyclerBinding5 = this.binding;
            if (activityRecyclerBinding5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activityRecyclerBinding5 = null;
            }
            activityRecyclerBinding5.toolbar.toolbar.setVisibility(8);
            WorkspaceLayout workspaceLayout = this.workspaceLayout;
            if (workspaceLayout != null) {
                workspaceLayout.setVisibility(0);
            }
            Map<String, ? extends Pair<String, ? extends Function0<? extends Fragment>>> mapMapOf = MapsKt.mapOf(TuplesKt.to(SceneListFragment.INSTANCE.getTAG(), new Pair("Сцены", new Function0<SceneListFragment>() { // from class: org.catrobat.catroid.ui.ProjectActivity$onCreate$creators$1
                @Override // kotlin.jvm.functions.Function0
                public final SceneListFragment invoke() {
                    return new SceneListFragment();
                }
            })), TuplesKt.to(SpriteListFragment.INSTANCE.getTAG(), new Pair("Спрайты", new Function0<SpriteListFragment>() { // from class: org.catrobat.catroid.ui.ProjectActivity$onCreate$creators$2
                @Override // kotlin.jvm.functions.Function0
                public final SpriteListFragment invoke() {
                    return new SpriteListFragment();
                }
            })), TuplesKt.to(ScriptFragment.TAG, new Pair("Скрипты", new Function0<ScriptFragment>() { // from class: org.catrobat.catroid.ui.ProjectActivity$onCreate$creators$3
                @Override // kotlin.jvm.functions.Function0
                public final ScriptFragment invoke() {
                    return new ScriptFragment();
                }
            })), TuplesKt.to(LookListFragment.TAG, new Pair("Образы", new Function0<LookListFragment>() { // from class: org.catrobat.catroid.ui.ProjectActivity$onCreate$creators$4
                @Override // kotlin.jvm.functions.Function0
                public final LookListFragment invoke() {
                    return new LookListFragment();
                }
            })), TuplesKt.to(SoundListFragment.TAG, new Pair("Звуки", new Function0<SoundListFragment>() { // from class: org.catrobat.catroid.ui.ProjectActivity$onCreate$creators$5
                @Override // kotlin.jvm.functions.Function0
                public final SoundListFragment invoke() {
                    return new SoundListFragment();
                }
            })));
            WorkspaceLayout workspaceLayout2 = this.workspaceLayout;
            if (workspaceLayout2 != null) {
                workspaceLayout2.restoreLayoutState(mapMapOf);
            }
            WorkspaceLayout workspaceLayout3 = this.workspaceLayout;
            if (workspaceLayout3 != null) {
                workspaceLayout3.post(new Runnable() { // from class: org.catrobat.catroid.ui.ProjectActivity$$ExternalSyntheticLambda12
                    @Override // java.lang.Runnable
                    public final void run() {
                        ProjectActivity.onCreate$lambda$1(this.f$0);
                    }
                });
            }
        } else {
            WorkspaceLayout workspaceLayout4 = this.workspaceLayout;
            if (workspaceLayout4 != null) {
                workspaceLayout4.setVisibility(8);
            }
            ActivityRecyclerBinding activityRecyclerBinding6 = this.binding;
            if (activityRecyclerBinding6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activityRecyclerBinding6 = null;
            }
            setSupportActionBar(activityRecyclerBinding6.toolbar.toolbar);
            ActionBar supportActionBar = getSupportActionBar();
            if (supportActionBar != null) {
                supportActionBar.setDisplayHomeAsUpEnabled(true);
            }
            ActivityRecyclerBinding activityRecyclerBinding7 = this.binding;
            if (activityRecyclerBinding7 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activityRecyclerBinding7 = null;
            }
            setSupportActionBar(activityRecyclerBinding7.toolbar.toolbar);
            ActionBar supportActionBar2 = getSupportActionBar();
            if (supportActionBar2 != null) {
                supportActionBar2.setDisplayHomeAsUpEnabled(true);
            }
            Bundle extras = getIntent().getExtras();
            loadFragment(extras != null ? extras.getInt("fragmentPosition", 0) : 0);
        }
        ProjectUtils.showWarningForSuspiciousBricksOnce(projectActivity);
        showLegoSensorConfigInfo();
        ActivityRecyclerBinding activityRecyclerBinding8 = this.binding;
        if (activityRecyclerBinding8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activityRecyclerBinding2 = activityRecyclerBinding8;
        }
        ListActionButtonsBinding listActionButtonsBinding = activityRecyclerBinding2.bottomBar;
        listActionButtonsBinding.buttonAdd.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.ProjectActivity$$ExternalSyntheticLambda13
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ProjectActivity.onCreate$lambda$4$lambda$2(this.f$0, view);
            }
        });
        listActionButtonsBinding.buttonPlay.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.ProjectActivity$$ExternalSyntheticLambda14
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ProjectActivity.onCreate$lambda$4$lambda$3(this.f$0, view);
            }
        });
        getProjectManager().getCurrentProject().checkIfSpriteNameEqualBackground(projectActivity);
        MyActivityManager.INSTANCE.setProject_activity(this);
        Project currentProject2 = getProjectManager().getCurrentProject();
        if (currentProject2 != null) {
            Log.i(ProjectListFragment.INSTANCE.getTAG(), "Проект '" + currentProject2.getName() + "' загружен, запускаю синхронизацию библиотек...");
            LibraryManager.INSTANCE.syncAndLoadLibraries(currentProject2);
            if (getProjectManager().isNeedsPhysicsCacheWarning()) {
                showPhysicsCacheWarningDialog();
                getProjectManager().setNeedsPhysicsCacheWarning(false);
                return;
            }
            return;
        }
        Log.w(ProjectListFragment.INSTANCE.getTAG(), "Проект успешно загружен, но projectManager.currentProject равен null. Синхронизация пропущена.");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$1(ProjectActivity this$0) {
        WorkspaceLayout workspaceLayout;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        String string = this$0.getSharedPreferences("workspace_prefs", 0).getString("active_tags", "");
        if ((string != null ? string : "").length() != 0 || (workspaceLayout = this$0.workspaceLayout) == null) {
            return;
        }
        workspaceLayout.openWindow(SpriteListFragment.INSTANCE.getTAG(), "Спрайты", new Function0<Fragment>() { // from class: org.catrobat.catroid.ui.ProjectActivity$onCreate$1$1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final Fragment invoke() {
                return new SpriteListFragment();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$4$lambda$2(ProjectActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.handleAddButton();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$4$lambda$3(ProjectActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.handlePlayButton();
    }

    private final void loadFragment(int fragmentPosition) {
        FragmentTransaction fragmentTransactionBeginTransaction = getSupportFragmentManager().beginTransaction();
        Intrinsics.checkNotNullExpressionValue(fragmentTransactionBeginTransaction, "beginTransaction(...)");
        if (fragmentPosition == 0) {
            fragmentTransactionBeginTransaction.replace(R.id.fragment_container, new SceneListFragment(), SceneListFragment.INSTANCE.getTAG());
        } else if (fragmentPosition == 1) {
            fragmentTransactionBeginTransaction.replace(R.id.fragment_container, new SpriteListFragment(), SpriteListFragment.INSTANCE.getTAG());
        } else {
            throw new IllegalArgumentException("Invalid fragmentPosition in Activity.");
        }
        fragmentTransactionBeginTransaction.commit();
    }

    private final Fragment getCurrentFragment() {
        WorkspaceLayout workspaceLayout = this.workspaceLayout;
        if (workspaceLayout != null && workspaceLayout.getVisibility() == 0) {
            Fragment fragmentFindFragmentByTag = getSupportFragmentManager().findFragmentByTag(SpriteListFragment.INSTANCE.getTAG());
            if (fragmentFindFragmentByTag != null) {
                return fragmentFindFragmentByTag;
            }
            Fragment fragmentFindFragmentByTag2 = getSupportFragmentManager().findFragmentByTag(SceneListFragment.INSTANCE.getTAG());
            if (fragmentFindFragmentByTag2 != null) {
                return fragmentFindFragmentByTag2;
            }
        }
        return getSupportFragmentManager().findFragmentById(R.id.fragment_container);
    }

    public final void setShowProgressBar(boolean show) {
        LinearLayout progressBar = ProgressBarBinding.inflate(getLayoutInflater()).progressBar;
        Intrinsics.checkNotNullExpressionValue(progressBar, "progressBar");
        ExtensionsKt.setVisibleOrGone(progressBar, show);
        ActivityRecyclerBinding activityRecyclerBinding = this.binding;
        if (activityRecyclerBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityRecyclerBinding = null;
        }
        FrameLayout fragmentContainer = activityRecyclerBinding.fragmentContainer;
        Intrinsics.checkNotNullExpressionValue(fragmentContainer, "fragmentContainer");
        ExtensionsKt.setVisibleOrGone(fragmentContainer, !show);
    }

    @Override // org.catrobat.catroid.ui.BaseCastActivity, org.catrobat.catroid.ui.BaseActivity, android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        Intrinsics.checkNotNullParameter(menu, "menu");
        getMenuInflater().inflate(R.menu.menu_project_activity, menu);
        menu.findItem(R.id.from_library).setVisible(false);
        menu.findItem(R.id.from_local).setVisible(false);
        menu.findItem(R.id.edit).setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override // org.catrobat.catroid.ui.BaseCastActivity, org.catrobat.catroid.ui.BaseActivity, android.app.Activity
    public boolean onOptionsItemSelected(MenuItem item) {
        Intrinsics.checkNotNullParameter(item, "item");
        WorkspaceLayout workspaceLayout = this.workspaceLayout;
        boolean z = workspaceLayout != null && workspaceLayout.getVisibility() == 0;
        switch (item.getItemId()) {
            case R.id.editor3d /* 2131364368 */:
                startActivity(new Intent(this, (Class<?>) EditorActivity.class));
                return true;
            case R.id.new_scene /* 2131364784 */:
                handleAddSceneButton();
                return true;
            case R.id.project_files /* 2131365061 */:
                if (!z) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProjectFilesFragment(), ProjectFilesFragment.INSTANCE.getTAG()).addToBackStack(ProjectFilesFragment.INSTANCE.getTAG()).commit();
                } else if (workspaceLayout != null) {
                    workspaceLayout.openWindow(ProjectFilesFragment.INSTANCE.getTAG(), "Файлы проекта", new Function0<Fragment>() { // from class: org.catrobat.catroid.ui.ProjectActivity.onOptionsItemSelected.2
                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // kotlin.jvm.functions.Function0
                        public final Fragment invoke() {
                            return new ProjectFilesFragment();
                        }
                    });
                }
                return true;
            case R.id.project_libs /* 2131365066 */:
                if (!z) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProjectLibsFragment(), ProjectLibsFragment.INSTANCE.getTAG()).addToBackStack(ProjectLibsFragment.INSTANCE.getTAG()).commit();
                } else if (workspaceLayout != null) {
                    workspaceLayout.openWindow(ProjectLibsFragment.INSTANCE.getTAG(), "Библиотеки", new Function0<Fragment>() { // from class: org.catrobat.catroid.ui.ProjectActivity.onOptionsItemSelected.3
                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // kotlin.jvm.functions.Function0
                        public final Fragment invoke() {
                            return new ProjectLibsFragment();
                        }
                    });
                }
                return true;
            case R.id.project_options /* 2131365069 */:
                if (!z) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProjectOptionsFragment(), ProjectOptionsFragment.INSTANCE.getTAG()).addToBackStack(ProjectOptionsFragment.INSTANCE.getTAG()).commit();
                } else if (workspaceLayout != null) {
                    workspaceLayout.openWindow(ProjectOptionsFragment.INSTANCE.getTAG(), "Опции проекта", new Function0<Fragment>() { // from class: org.catrobat.catroid.ui.ProjectActivity.onOptionsItemSelected.1
                        /* JADX WARN: Can't rename method to resolve collision */
                        @Override // kotlin.jvm.functions.Function0
                        public final Fragment invoke() {
                            return new ProjectOptionsFragment();
                        }
                    });
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override // org.catrobat.catroid.ui.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        WorkspaceLayout workspaceLayout;
        super.onPause();
        WorkspaceLayout workspaceLayout2 = this.workspaceLayout;
        if (workspaceLayout2 != null && workspaceLayout2.getVisibility() == 0 && (workspaceLayout = this.workspaceLayout) != null) {
            workspaceLayout.saveLayoutState();
        }
        saveProject(getProjectManager().getCurrentProject());
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        WorkspaceLayout workspaceLayout;
        WorkspaceLayout workspaceLayout2 = this.workspaceLayout;
        if (workspaceLayout2 == null || workspaceLayout2.getVisibility() != 0 || (workspaceLayout = this.workspaceLayout) == null || !workspaceLayout.handleBackPressed()) {
            Project currentProject = getProjectManager().getCurrentProject();
            if (currentProject == null) {
                finish();
                return;
            }
            Fragment fragmentFindFragmentById = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            if (!(fragmentFindFragmentById instanceof ProjectOptionsFragment)) {
                saveProject(currentProject);
            }
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStack();
                BottomBar.showBottomBar(this);
                return;
            }
            LibraryManager.INSTANCE.unloadAllLibraries();
            getProjectManager().resetProjectManager();
            boolean z = getProjectManager().getCurrentProject().getSceneList().size() > 1;
            if ((fragmentFindFragmentById instanceof SpriteListFragment) && z) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SceneListFragment(), SceneListFragment.INSTANCE.getTAG()).commit();
            } else {
                super.onBackPressed();
            }
        }
    }

    private final void saveProject(Project currentProject) {
        if (currentProject == null) {
            Utils.setLastUsedProjectName(getApplicationContext(), null);
            return;
        }
        Context applicationContext = getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
        ProjectSaver.saveProjectAsync$default(new ProjectSaver(currentProject, applicationContext), null, null, 3, null);
        Utils.setLastUsedProjectName(getApplicationContext(), currentProject.getName());
    }

    private final void updateBrickFromVisualPlacement(Bundle extras) {
        FormulaBrick formulaBrickFindBrickByHash;
        if (extras == null) {
            return;
        }
        int i = extras.getInt(VisualPlacementActivity.X_COORDINATE_BUNDLE_ARGUMENT);
        int i2 = extras.getInt(VisualPlacementActivity.Y_COORDINATE_BUNDLE_ARGUMENT);
        float f = extras.getFloat(VisualPlacementActivity.ROTATION_ANGLE_BUNDLE_ARGUMENT);
        float f2 = extras.getFloat(VisualPlacementActivity.SIZE_PERCENT_BUNDLE_ARGUMENT);
        int i3 = extras.getInt(SpriteActivity.EXTRA_BRICK_HASH, -1);
        Fragment fragmentFindFragmentByTag = getSupportFragmentManager().findFragmentByTag(FormulaEditorFragment.FORMULA_EDITOR_FRAGMENT_TAG);
        if (fragmentFindFragmentByTag == null) {
            fragmentFindFragmentByTag = getSupportFragmentManager().findFragmentByTag(ScriptFragment.TAG);
        }
        boolean z = fragmentFindFragmentByTag instanceof FormulaEditorFragment;
        PointInDirectionBrick pointInDirectionBrick = null;
        if (z) {
            formulaBrickFindBrickByHash = ((FormulaEditorFragment) fragmentFindFragmentByTag).getFormulaBrick();
        } else {
            formulaBrickFindBrickByHash = fragmentFindFragmentByTag instanceof ScriptFragment ? ((ScriptFragment) fragmentFindFragmentByTag).findBrickByHash(i3) : null;
        }
        if (formulaBrickFindBrickByHash instanceof VisualPlacementBrick) {
            VisualPlacementBrick visualPlacementBrick = (VisualPlacementBrick) formulaBrickFindBrickByHash;
            Script script = visualPlacementBrick.getScript();
            visualPlacementBrick.setCoordinates(i, i2);
            if (!Intrinsics.areEqual(getProjectManager().getCurrentSprite(), getProjectManager().getCurrentlyEditedScene().getBackgroundSprite()) && script != null) {
                boolean z2 = extras.getBoolean(VisualPlacementActivity.EXTRA_IS_VARIABLE, false);
                boolean z3 = extras.getBoolean(VisualPlacementActivity.EXTRA_ROTATION_CHANGED, false);
                boolean z4 = extras.getBoolean(VisualPlacementActivity.EXTRA_SIZE_CHANGED, false);
                if (!z2) {
                    SetSizeToBrick setSizeToBrick = null;
                    for (Brick brick : script.getBrickList()) {
                        if (brick instanceof PointInDirectionBrick) {
                            pointInDirectionBrick = (PointInDirectionBrick) brick;
                        }
                        if (brick instanceof SetSizeToBrick) {
                            setSizeToBrick = (SetSizeToBrick) brick;
                        }
                    }
                    int iIndexOf = script.getBrickList().indexOf(visualPlacementBrick);
                    int i4 = iIndexOf + 1;
                    if (pointInDirectionBrick != null) {
                        if (z3) {
                            script.getBrickList().set(script.getBrickList().indexOf(pointInDirectionBrick), new PointInDirectionBrick(new Formula(Float.valueOf(f + 90))));
                        }
                    } else if (z3) {
                        script.addBrick(i4, new PointInDirectionBrick(new Formula(Float.valueOf(f + 90))));
                        i4 = iIndexOf + 2;
                    }
                    if (setSizeToBrick != null) {
                        if (z4) {
                            script.getBrickList().set(script.getBrickList().indexOf(setSizeToBrick), new SetSizeToBrick(new Formula(Float.valueOf(f2))));
                        }
                    } else if (z4) {
                        script.addBrick(i4, new SetSizeToBrick(new Formula(Float.valueOf(f2))));
                    }
                }
            }
            if (fragmentFindFragmentByTag instanceof ScriptFragment) {
                ((ScriptFragment) fragmentFindFragmentByTag).notifyDataSetChanged();
            } else if (z) {
                ((FormulaEditorFragment) fragmentFindFragmentByTag).updateFragmentAfterVisualPlacement();
            }
        }
    }

    static /* synthetic */ void addBackgroundFromUri$default(ProjectActivity projectActivity, Uri uri, String str, int i, Object obj) throws Throwable {
        if ((i & 2) != 0) {
            str = Constants.DEFAULT_IMAGE_EXTENSION;
        }
        projectActivity.addBackgroundFromUri(uri, str);
    }

    private final void addBackgroundFromUri(Uri uri, String imageExtension) throws Throwable {
        Sprite currentSprite;
        Scene currentlyEditedScene;
        String sanitizedFileName;
        if (uri == null || (currentSprite = getProjectManager().getCurrentSprite()) == null || (currentlyEditedScene = getProjectManager().getCurrentlyEditedScene()) == null) {
            return;
        }
        String strResolveFileName = StorageOperations.resolveFileName(getContentResolver(), uri);
        if (strResolveFileName == null || Intrinsics.areEqual(StorageOperations.getSanitizedFileName(strResolveFileName), Constants.TMP_IMAGE_FILE_NAME)) {
            String name2 = currentSprite.getName();
            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
            strResolveFileName = name2 + imageExtension;
            sanitizedFileName = name2;
        } else {
            sanitizedFileName = StorageOperations.getSanitizedFileName(strResolveFileName);
            Intrinsics.checkNotNullExpressionValue(sanitizedFileName, "getSanitizedFileName(...)");
            Intrinsics.checkNotNull(strResolveFileName);
        }
        String uniqueNameInNameables = new UniqueNameProvider().getUniqueNameInNameables(sanitizedFileName, currentSprite.getLookList());
        try {
            File file = new File(currentlyEditedScene.getDirectory(), Constants.IMAGE_DIRECTORY_NAME);
            File fileCopyUriToDir = StorageOperations.copyUriToDir(getContentResolver(), uri, file, strResolveFileName);
            Utils.removeExifData(file, strResolveFileName);
            LookData lookData = new LookData(uniqueNameInNameables, fileCopyUriToDir);
            currentSprite.getLookList().add(lookData);
            CollisionInformation collisionInformation = lookData.getCollisionInformation();
            if (collisionInformation != null) {
                collisionInformation.calculate();
            }
            Fragment fragmentFindFragmentByTag = getSupportFragmentManager().findFragmentByTag(LookListFragment.TAG);
            LookListFragment lookListFragment = fragmentFindFragmentByTag instanceof LookListFragment ? (LookListFragment) fragmentFindFragmentByTag : null;
            if (lookListFragment != null) {
                lookListFragment.notifyDataSetChanged();
            }
        } catch (IOException e) {
            Log.e("ProjectActivity", Log.getStackTraceString(e));
        }
    }

    static /* synthetic */ void addLookFromUri$default(ProjectActivity projectActivity, Uri uri, String str, int i, Object obj) throws Throwable {
        if ((i & 2) != 0) {
            str = Constants.DEFAULT_IMAGE_EXTENSION;
        }
        projectActivity.addLookFromUri(uri, str);
    }

    private final void addLookFromUri(Uri uri, String imageExtension) throws Throwable {
        Sprite currentSprite;
        Scene currentlyEditedScene;
        String sanitizedFileName;
        if (uri == null || (currentSprite = getProjectManager().getCurrentSprite()) == null || (currentlyEditedScene = getProjectManager().getCurrentlyEditedScene()) == null) {
            return;
        }
        String strResolveFileName = StorageOperations.resolveFileName(getContentResolver(), uri);
        if (strResolveFileName == null || Intrinsics.areEqual(StorageOperations.getSanitizedFileName(strResolveFileName), Constants.TMP_IMAGE_FILE_NAME)) {
            String name2 = currentSprite.getName();
            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
            strResolveFileName = name2 + imageExtension;
            sanitizedFileName = name2;
        } else {
            sanitizedFileName = StorageOperations.getSanitizedFileName(strResolveFileName);
            Intrinsics.checkNotNullExpressionValue(sanitizedFileName, "getSanitizedFileName(...)");
            Intrinsics.checkNotNull(strResolveFileName);
        }
        String uniqueNameInNameables = new UniqueNameProvider().getUniqueNameInNameables(sanitizedFileName, currentSprite.getLookList());
        try {
            File file = new File(currentlyEditedScene.getDirectory(), Constants.IMAGE_DIRECTORY_NAME);
            File fileCopyUriToDir = StorageOperations.copyUriToDir(getContentResolver(), uri, file, strResolveFileName);
            Utils.removeExifData(file, strResolveFileName);
            LookData lookData = new LookData(uniqueNameInNameables, fileCopyUriToDir);
            currentSprite.getLookList().add(lookData);
            CollisionInformation collisionInformation = lookData.getCollisionInformation();
            if (collisionInformation != null) {
                collisionInformation.calculate();
            }
            Fragment fragmentFindFragmentByTag = getSupportFragmentManager().findFragmentByTag(LookListFragment.TAG);
            LookListFragment lookListFragment = fragmentFindFragmentByTag instanceof LookListFragment ? (LookListFragment) fragmentFindFragmentByTag : null;
            if (lookListFragment != null) {
                lookListFragment.notifyDataSetChanged();
            }
        } catch (IOException e) {
            Log.e("ProjectActivity", Log.getStackTraceString(e));
        }
    }

    private final void addSoundFromUri(Uri uri) {
        Sprite currentSprite;
        Scene currentlyEditedScene;
        String str;
        String name2;
        if (uri == null || (currentSprite = getProjectManager().getCurrentSprite()) == null || (currentlyEditedScene = getProjectManager().getCurrentlyEditedScene()) == null) {
            return;
        }
        String strResolveFileName = StorageOperations.resolveFileName(getContentResolver(), uri);
        if (strResolveFileName == null) {
            name2 = currentSprite.getName();
            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
            str = name2 + Constants.DEFAULT_SOUND_EXTENSION;
        } else {
            String sanitizedFileName = StorageOperations.getSanitizedFileName(strResolveFileName);
            Intrinsics.checkNotNullExpressionValue(sanitizedFileName, "getSanitizedFileName(...)");
            Intrinsics.checkNotNull(strResolveFileName);
            str = strResolveFileName;
            name2 = sanitizedFileName;
        }
        try {
            currentSprite.getSoundList().add(new SoundInfo(new UniqueNameProvider().getUniqueNameInNameables(name2, currentSprite.getSoundList()), StorageOperations.copyUriToDir(getContentResolver(), uri, new File(currentlyEditedScene.getDirectory(), Constants.SOUND_DIRECTORY_NAME), str)));
            Fragment fragmentFindFragmentByTag = getSupportFragmentManager().findFragmentByTag(SoundListFragment.TAG);
            SoundListFragment soundListFragment = fragmentFindFragmentByTag instanceof SoundListFragment ? (SoundListFragment) fragmentFindFragmentByTag : null;
            if (soundListFragment != null) {
                soundListFragment.notifyDataSetChanged();
            }
        } catch (IOException e) {
            Log.e("ProjectActivity", Log.getStackTraceString(e));
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int requestCode, int resultCode, Intent data) throws Throwable {
        Bundle extras;
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 7777 || resultCode == 8888) {
            String stringExtra = data != null ? data.getStringExtra(TestResult.TEST_RESULT_MESSAGE) : null;
            ToastUtil.showError(this, stringExtra);
            Object systemService = getSystemService("clipboard");
            Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.content.ClipboardManager");
            ((ClipboardManager) systemService).setPrimaryClip(ClipData.newPlainText("TestResult", StringsKt.trimIndent(getProjectManager().getCurrentProject().getName() + " " + stringExtra)));
        }
        if (resultCode != -1) {
            if (requestCode == 0) {
                addEmptySpriteObject();
            }
            return;
        }
        if (requestCode == 2019) {
            if (data == null || (extras = data.getExtras()) == null) {
                return;
            }
            if (extras.getInt(SpriteActivity.EXTRA_BRICK_HASH, -1) != -1) {
                updateBrickFromVisualPlacement(extras);
                return;
            }
            PlaceAtBrick placeAtBrick = new PlaceAtBrick(extras.getInt(VisualPlacementActivity.X_COORDINATE_BUNDLE_ARGUMENT), extras.getInt(VisualPlacementActivity.Y_COORDINATE_BUNDLE_ARGUMENT));
            Sprite currentSprite = getProjectManager().getCurrentSprite();
            StartScript startScript = new StartScript();
            currentSprite.prependScript(startScript);
            startScript.addBrick(placeAtBrick);
            return;
        }
        switch (requestCode) {
            case 0:
                addSpriteFromUri$default(this, new ImportFromPocketPaintLauncher(this).getPocketPaintCacheUri(), null, 2, null);
                break;
            case 1:
                Intrinsics.checkNotNull(data);
                addSpriteFromUri$default(this, Uri.fromFile(new File(data.getStringExtra(WebViewActivity.MEDIA_FILE_PATH))), null, 2, null);
                break;
            case 2:
                addSpriteFromUri(data != null ? data.getData() : null, Constants.JPEG_IMAGE_EXTENSION);
                break;
            case 3:
                addSpriteFromUri(new ImportFromCameraLauncher(this).getCacheCameraUri(), Constants.JPEG_IMAGE_EXTENSION);
                break;
            case 4:
                Intrinsics.checkNotNull(data);
                addObjectFromUri(Uri.fromFile(new File(data.getStringExtra(WebViewActivity.MEDIA_FILE_PATH))));
                break;
            case 5:
                Intrinsics.checkNotNull(data);
                addBackgroundFromUri$default(this, Uri.fromFile(new File(data.getStringExtra(WebViewActivity.MEDIA_FILE_PATH))), null, 2, null);
                break;
            case 6:
                addBackgroundFromUri(data != null ? data.getData() : null, Constants.JPEG_IMAGE_EXTENSION);
                break;
            case 7:
                addBackgroundFromUri(new ImportFromCameraLauncher(this).getCacheCameraUri(), Constants.JPEG_IMAGE_EXTENSION);
                break;
            case 8:
                addLookFromUri$default(this, new ImportFromPocketPaintLauncher(this).getPocketPaintCacheUri(), null, 2, null);
                break;
            case 9:
                Intrinsics.checkNotNull(data);
                addLookFromUri$default(this, Uri.fromFile(new File(data.getStringExtra(WebViewActivity.MEDIA_FILE_PATH))), null, 2, null);
                break;
            case 10:
                addLookFromUri(data != null ? data.getData() : null, Constants.JPEG_IMAGE_EXTENSION);
                break;
            case 11:
                addLookFromUri(new ImportFromCameraLauncher(this).getCacheCameraUri(), Constants.JPEG_IMAGE_EXTENSION);
                break;
            case 12:
            case 14:
                addSoundFromUri(data != null ? data.getData() : null);
                break;
            case 13:
                Intrinsics.checkNotNull(data);
                addSoundFromUri(Uri.fromFile(new File(data.getStringExtra(WebViewActivity.MEDIA_FILE_PATH))));
                break;
        }
    }

    static /* synthetic */ void addSpriteFromUri$default(ProjectActivity projectActivity, Uri uri, String str, int i, Object obj) {
        if ((i & 2) != 0) {
            str = Constants.DEFAULT_IMAGE_EXTENSION;
        }
        projectActivity.addSpriteFromUri(uri, str);
    }

    private final void addSpriteFromUri(Uri uri, String imageExtension) {
        addSpriteObjectFromUri(uri, imageExtension, false);
    }

    public final void addObjectFromUri(Uri uri) {
        addSpriteObjectFromUri(uri, Constants.CATROBAT_EXTENSION, true);
    }

    private final void addSpriteObjectFromUri(Uri uri, String extension, boolean isObject) {
        String str;
        String sanitizedFileName;
        String uniqueNameInNameables;
        ImportProjectHelper importProjectHelper;
        Scene currentlyEditedScene = getProjectManager().getCurrentlyEditedScene();
        String strResolveFileName = StorageOperations.resolveFileName(getContentResolver(), uri);
        if (strResolveFileName == null || Intrinsics.areEqual(StorageOperations.getSanitizedFileName(strResolveFileName), Constants.TMP_IMAGE_FILE_NAME)) {
            String string = getString(R.string.default_sprite_name);
            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
            str = string + extension;
            sanitizedFileName = string;
        } else {
            sanitizedFileName = StorageOperations.getSanitizedFileName(strResolveFileName);
            Intrinsics.checkNotNullExpressionValue(sanitizedFileName, "getSanitizedFileName(...)");
            Intrinsics.checkNotNull(strResolveFileName);
            str = strResolveFileName;
        }
        String uniqueNameInNameables2 = new UniqueNameProvider().getUniqueNameInNameables(sanitizedFileName, currentlyEditedScene.getSpriteList());
        if (isObject) {
            Intrinsics.checkNotNull(currentlyEditedScene);
            ImportProjectHelper importProjectHelper2 = new ImportProjectHelper(str, currentlyEditedScene, this);
            if (!importProjectHelper2.checkForConflicts()) {
                return;
            }
            importProjectHelper = importProjectHelper2;
            uniqueNameInNameables = new UniqueNameProvider().getUniqueNameInNameables(importProjectHelper2.getSpriteToAddName(), currentlyEditedScene.getSpriteList());
        } else {
            uniqueNameInNameables = uniqueNameInNameables2;
            importProjectHelper = null;
        }
        Intrinsics.checkNotNull(uniqueNameInNameables);
        ContentResolver contentResolver = getContentResolver();
        Fragment currentFragment = getCurrentFragment();
        Intrinsics.checkNotNull(currentFragment);
        new NewSpriteDialogFragment(false, uniqueNameInNameables, str, contentResolver, uri, currentFragment, isObject, importProjectHelper).show(getSupportFragmentManager(), NewSpriteDialogFragment.INSTANCE.getTAG());
    }

    private final void addEmptySpriteObject() {
        String uniqueNameInNameables = new UniqueNameProvider().getUniqueNameInNameables(getString(R.string.default_sprite_name), getProjectManager().getCurrentlyEditedScene().getSpriteList());
        Intrinsics.checkNotNull(uniqueNameInNameables);
        Fragment currentFragment = getCurrentFragment();
        Intrinsics.checkNotNull(currentFragment);
        new NewSpriteDialogFragment(true, uniqueNameInNameables, currentFragment).show(getSupportFragmentManager(), NewSpriteDialogFragment.INSTANCE.getTAG());
    }

    private final void handleAddButton() {
        WorkspaceLayout workspaceLayout = this.workspaceLayout;
        if (workspaceLayout != null && workspaceLayout.getVisibility() == 0) {
            String focusedWindowTag = workspaceLayout.getFocusedWindowTag();
            if (Intrinsics.areEqual(focusedWindowTag, ScriptFragment.TAG)) {
                workspaceLayout.openBrickCategoryWindow();
                return;
            }
            if (Intrinsics.areEqual(focusedWindowTag, LookListFragment.TAG)) {
                handleAddLookButton();
                return;
            }
            if (Intrinsics.areEqual(focusedWindowTag, SoundListFragment.TAG)) {
                handleAddSoundButton();
                return;
            }
            if (Intrinsics.areEqual(focusedWindowTag, DataListFragment.TAG)) {
                handleAddUserDataButton();
                return;
            }
            if (Intrinsics.areEqual(focusedWindowTag, SceneListFragment.INSTANCE.getTAG())) {
                handleAddSceneButton();
                return;
            } else if (Intrinsics.areEqual(focusedWindowTag, SpriteListFragment.INSTANCE.getTAG())) {
                handleAddSpriteButton();
                return;
            } else {
                workspaceLayout.openBrickCategoryWindow();
                return;
            }
        }
        if (getCurrentFragment() instanceof SceneListFragment) {
            handleAddSceneButton();
        } else if (getCurrentFragment() instanceof SpriteListFragment) {
            handleAddSpriteButton();
        }
    }

    public final void handleAddLookButton() {
        final String str;
        View viewInflate = getLayoutInflater().inflate(R.layout.dialog_new_look, (ViewGroup) null);
        final AlertDialog alertDialogCreate = new AlertDialog.Builder(this).setTitle(R.string.new_look_dialog_title).setView(viewInflate).create();
        Intrinsics.checkNotNullExpressionValue(alertDialogCreate, "create(...)");
        if (Intrinsics.areEqual(getProjectManager().getCurrentSprite(), getProjectManager().getCurrentlyEditedScene().getBackgroundSprite())) {
            str = getProjectManager().isCurrentProjectLandscapeMode() ? FlavoredConstants.LIBRARY_BACKGROUNDS_URL_LANDSCAPE : FlavoredConstants.LIBRARY_BACKGROUNDS_URL_PORTRAIT;
        } else {
            str = FlavoredConstants.LIBRARY_LOOKS_URL;
        }
        viewInflate.findViewById(R.id.dialog_new_look_paintroid).setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.ProjectActivity$$ExternalSyntheticLambda15
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ProjectActivity.handleAddLookButton$lambda$5(this.f$0, alertDialogCreate, view);
            }
        });
        viewInflate.findViewById(R.id.dialog_new_look_media_library).setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.ProjectActivity$$ExternalSyntheticLambda16
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ProjectActivity.handleAddLookButton$lambda$6(this.f$0, str, alertDialogCreate, view);
            }
        });
        viewInflate.findViewById(R.id.dialog_new_look_gallery).setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.ProjectActivity$$ExternalSyntheticLambda17
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ProjectActivity.handleAddLookButton$lambda$7(this.f$0, alertDialogCreate, view);
            }
        });
        viewInflate.findViewById(R.id.dialog_new_look_camera).setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.ProjectActivity$$ExternalSyntheticLambda18
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ProjectActivity.handleAddLookButton$lambda$8(this.f$0, alertDialogCreate, view);
            }
        });
        alertDialogCreate.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handleAddLookButton$lambda$5(ProjectActivity this$0, AlertDialog alertDialog, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(alertDialog, "$alertDialog");
        new ImportFromPocketPaintLauncher(this$0).startActivityForResult(8);
        alertDialog.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handleAddLookButton$lambda$6(ProjectActivity this$0, String mediaLibraryUrl, AlertDialog alertDialog, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(mediaLibraryUrl, "$mediaLibraryUrl");
        Intrinsics.checkNotNullParameter(alertDialog, "$alertDialog");
        new ImportFormMediaLibraryLauncher(this$0, mediaLibraryUrl).startActivityForResult(9);
        alertDialog.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handleAddLookButton$lambda$7(ProjectActivity this$0, AlertDialog alertDialog, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(alertDialog, "$alertDialog");
        ProjectActivity projectActivity = this$0;
        String string = this$0.getString(R.string.select_look_from_gallery);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        new ImportFromFileLauncher(projectActivity, "image/*", string).startActivityForResult(10);
        alertDialog.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handleAddLookButton$lambda$8(ProjectActivity this$0, AlertDialog alertDialog, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(alertDialog, "$alertDialog");
        new ImportFromCameraLauncher(this$0).startActivityForResult(11);
        alertDialog.dismiss();
    }

    public final void handleAddSoundButton() {
        View viewInflate = getLayoutInflater().inflate(R.layout.dialog_new_sound, (ViewGroup) null);
        final AlertDialog alertDialogCreate = new AlertDialog.Builder(this).setTitle(R.string.new_sound_dialog_title).setView(viewInflate).create();
        Intrinsics.checkNotNullExpressionValue(alertDialogCreate, "create(...)");
        viewInflate.findViewById(R.id.dialog_new_sound_recorder).setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.ProjectActivity$$ExternalSyntheticLambda23
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ProjectActivity.handleAddSoundButton$lambda$9(this.f$0, alertDialogCreate, view);
            }
        });
        viewInflate.findViewById(R.id.dialog_new_sound_media_library).setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.ProjectActivity$$ExternalSyntheticLambda24
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ProjectActivity.handleAddSoundButton$lambda$10(this.f$0, alertDialogCreate, view);
            }
        });
        viewInflate.findViewById(R.id.dialog_new_sound_gallery).setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.ProjectActivity$$ExternalSyntheticLambda25
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ProjectActivity.handleAddSoundButton$lambda$11(this.f$0, alertDialogCreate, view);
            }
        });
        alertDialogCreate.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handleAddSoundButton$lambda$9(ProjectActivity this$0, AlertDialog alertDialog, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(alertDialog, "$alertDialog");
        this$0.startActivityForResult(new Intent(this$0, (Class<?>) SoundRecorderActivity.class), 12);
        alertDialog.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handleAddSoundButton$lambda$10(ProjectActivity this$0, AlertDialog alertDialog, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(alertDialog, "$alertDialog");
        new ImportFormMediaLibraryLauncher(this$0, FlavoredConstants.LIBRARY_SOUNDS_URL).startActivityForResult(13);
        alertDialog.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handleAddSoundButton$lambda$11(ProjectActivity this$0, AlertDialog alertDialog, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(alertDialog, "$alertDialog");
        ProjectActivity projectActivity = this$0;
        String string = this$0.getString(R.string.sound_select_source);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        new ImportFromFileLauncher(projectActivity, "audio/*", string).startActivityForResult(14);
        alertDialog.dismiss();
    }

    private static final void handleAddSoundButton$lambda$12(ProjectActivity this$0, AlertDialog alertDialog, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(alertDialog, "$alertDialog");
        this$0.startActivity(new Intent(this$0, (Class<?>) PocketMusicActivity.class));
        alertDialog.dismiss();
    }

    public final void handleAddUserDataButton() {
        View viewInflate = getLayoutInflater().inflate(R.layout.dialog_new_user_data, (ViewGroup) null);
        final CheckBox checkBox = (CheckBox) viewInflate.findViewById(R.id.make_list);
        checkBox.setVisibility(0);
        final RadioButton radioButton = (RadioButton) viewInflate.findViewById(R.id.multiplayer);
        if (SettingsFragment.isMultiplayerVariablesPreferenceEnabled(getApplicationContext())) {
            radioButton.setVisibility(0);
            radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: org.catrobat.catroid.ui.ProjectActivity$$ExternalSyntheticLambda19
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    ProjectActivity.handleAddUserDataButton$lambda$13(checkBox, compoundButton, z);
                }
            });
        }
        final RadioButton radioButton2 = (RadioButton) viewInflate.findViewById(R.id.global);
        final ArrayList arrayList = new ArrayList();
        final Project currentProject = getProjectManager().getCurrentProject();
        final Sprite currentSprite = getProjectManager().getCurrentSprite();
        List<UserVariable> userVariables = currentProject.getUserVariables();
        Intrinsics.checkNotNullExpressionValue(userVariables, "getUserVariables(...)");
        arrayList.addAll(userVariables);
        List<UserVariable> multiplayerVariables = currentProject.getMultiplayerVariables();
        Intrinsics.checkNotNullExpressionValue(multiplayerVariables, "getMultiplayerVariables(...)");
        arrayList.addAll(multiplayerVariables);
        List<UserVariable> userVariables2 = currentSprite.getUserVariables();
        Intrinsics.checkNotNullExpressionValue(userVariables2, "getUserVariables(...)");
        arrayList.addAll(userVariables2);
        final ArrayList arrayList2 = new ArrayList();
        List<UserList> userLists = currentProject.getUserLists();
        Intrinsics.checkNotNullExpressionValue(userLists, "getUserLists(...)");
        arrayList2.addAll(userLists);
        List<UserList> userLists2 = currentSprite.getUserLists();
        Intrinsics.checkNotNullExpressionValue(userLists2, "getUserLists(...)");
        arrayList2.addAll(userLists2);
        final DuplicateInputTextWatcher duplicateInputTextWatcher = new DuplicateInputTextWatcher(arrayList);
        TextInputDialog.Builder builder = new TextInputDialog.Builder(this);
        final UniqueNameProvider uniqueNameProviderCreateUniqueNameProvider = builder.createUniqueNameProvider(R.string.default_variable_name);
        final UniqueNameProvider uniqueNameProviderCreateUniqueNameProvider2 = builder.createUniqueNameProvider(R.string.default_list_name);
        this.generatedVariableName = uniqueNameProviderCreateUniqueNameProvider.getUniqueName(getString(R.string.default_variable_name), null);
        builder.setTextWatcher(duplicateInputTextWatcher).setText(this.generatedVariableName).setPositiveButton(getString(R.string.ok), new TextInputDialog.OnClickListener() { // from class: org.catrobat.catroid.ui.ProjectActivity$$ExternalSyntheticLambda20
            @Override // org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog.OnClickListener
            public final void onPositiveButtonClick(DialogInterface dialogInterface, String str) {
                ProjectActivity.handleAddUserDataButton$lambda$15(radioButton2, radioButton, this, checkBox, currentProject, currentSprite, dialogInterface, str);
            }
        });
        final AlertDialog alertDialogCreate = builder.setTitle(R.string.formula_editor_variable_dialog_title).setView(viewInflate).setNegativeButton(getString(R.string.cancel), (DialogInterface.OnClickListener) null).create();
        Intrinsics.checkNotNullExpressionValue(alertDialogCreate, "create(...)");
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: org.catrobat.catroid.ui.ProjectActivity$$ExternalSyntheticLambda21
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                ProjectActivity.handleAddUserDataButton$lambda$16(alertDialogCreate, this, duplicateInputTextWatcher, arrayList2, uniqueNameProviderCreateUniqueNameProvider2, arrayList, uniqueNameProviderCreateUniqueNameProvider, radioButton, compoundButton, z);
            }
        });
        alertDialogCreate.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handleAddUserDataButton$lambda$13(CheckBox checkBox, CompoundButton compoundButton, boolean z) {
        checkBox.setEnabled(!z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handleAddUserDataButton$lambda$15(RadioButton radioButton, RadioButton radioButton2, ProjectActivity this$0, CheckBox checkBox, Project project, Sprite sprite, DialogInterface dialogInterface, String str) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        boolean zIsChecked = radioButton.isChecked();
        boolean zIsChecked2 = radioButton2.isChecked();
        PreferenceManager.getDefaultSharedPreferences(this$0).edit().putBoolean(SharedPreferenceKeys.INDEXING_VARIABLE_PREFERENCE_KEY, false).apply();
        if (checkBox.isChecked()) {
            UserList userList = new UserList(str);
            if (zIsChecked) {
                project.addUserList(userList);
            } else {
                sprite.addUserList(userList);
            }
        } else {
            UserVariable userVariable = new UserVariable(str);
            if (zIsChecked2) {
                project.addMultiplayerVariable(userVariable);
            } else if (zIsChecked) {
                project.addUserVariable(userVariable);
            } else {
                sprite.addUserVariable(userVariable);
            }
        }
        Fragment fragmentFindFragmentByTag = this$0.getSupportFragmentManager().findFragmentByTag(DataListFragment.TAG);
        DataListFragment dataListFragment = fragmentFindFragmentByTag instanceof DataListFragment ? (DataListFragment) fragmentFindFragmentByTag : null;
        if (dataListFragment != null) {
            dataListFragment.notifyDataSetChanged();
            dataListFragment.indexAndSort();
        }
        DataListFragment.INSTANCE.refreshActiveInstance();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handleAddUserDataButton$lambda$16(AlertDialog alertDialog, ProjectActivity this$0, DuplicateInputTextWatcher textWatcher, List lists, UniqueNameProvider uniqueNameProvider, List variables, UniqueNameProvider uniqueNameProvider2, RadioButton radioButton, CompoundButton compoundButton, boolean z) {
        Intrinsics.checkNotNullParameter(alertDialog, "$alertDialog");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(textWatcher, "$textWatcher");
        Intrinsics.checkNotNullParameter(lists, "$lists");
        Intrinsics.checkNotNullParameter(variables, "$variables");
        TextInputEditText textInputEditText = (TextInputEditText) alertDialog.findViewById(R.id.input_edit_text);
        String strValueOf = String.valueOf(textInputEditText != null ? textInputEditText.getText() : null);
        if (z) {
            alertDialog.setTitle(this$0.getString(R.string.formula_editor_list_dialog_title));
            textWatcher.setOriginalScope(lists);
            if (Intrinsics.areEqual(strValueOf, this$0.generatedVariableName)) {
                String uniqueName = uniqueNameProvider.getUniqueName(this$0.getString(R.string.default_list_name), null);
                this$0.generatedVariableName = uniqueName;
                if (textInputEditText != null) {
                    textInputEditText.setText(uniqueName);
                }
            }
        } else {
            alertDialog.setTitle(this$0.getString(R.string.formula_editor_variable_dialog_title));
            textWatcher.setOriginalScope(variables);
            if (Intrinsics.areEqual(strValueOf, this$0.generatedVariableName)) {
                String uniqueName2 = uniqueNameProvider2.getUniqueName(this$0.getString(R.string.default_variable_name), null);
                this$0.generatedVariableName = uniqueName2;
                if (textInputEditText != null) {
                    textInputEditText.setText(uniqueName2);
                }
            }
        }
        radioButton.setEnabled(!z);
    }

    public final void handleAddSceneButton() {
        final Project currentProject = getProjectManager().getCurrentProject();
        String uniqueNameInNameables = new UniqueNameProvider().getUniqueNameInNameables(getResources().getString(R.string.default_scene_name), currentProject.getSceneList());
        TextInputDialog.Builder builder = new TextInputDialog.Builder(this);
        builder.setHint(getString(R.string.scene_name_label)).setText(uniqueNameInNameables).setTextWatcher(new DuplicateInputTextWatcher(currentProject.getSceneList())).setPositiveButton(getString(R.string.ok), new TextInputDialog.OnClickListener() { // from class: org.catrobat.catroid.ui.ProjectActivity$$ExternalSyntheticLambda26
            @Override // org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog.OnClickListener
            public final void onPositiveButtonClick(DialogInterface dialogInterface, String str) {
                ProjectActivity.handleAddSceneButton$lambda$17(this.f$0, currentProject, dialogInterface, str);
            }
        });
        builder.setTitle(R.string.new_scene_dialog).setNegativeButton(R.string.cancel, (DialogInterface.OnClickListener) null).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handleAddSceneButton$lambda$17(ProjectActivity this$0, Project project, DialogInterface dialogInterface, String str) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        project.addScene(SceneController.newSceneWithBackgroundSprite(str, this$0.getString(R.string.background), project));
        if (this$0.getCurrentFragment() instanceof SceneListFragment) {
            Fragment currentFragment = this$0.getCurrentFragment();
            Intrinsics.checkNotNull(currentFragment, "null cannot be cast to non-null type org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment<*>");
            ((RecyclerViewFragment) currentFragment).notifyDataSetChanged();
        } else {
            Intent intent = new Intent(this$0, (Class<?>) ProjectActivity.class);
            intent.putExtra("fragmentPosition", 0);
            this$0.startActivity(intent);
            this$0.finish();
        }
    }

    private final void handleAddSpriteButton() {
        DialogNewActorBinding dialogNewActorBindingInflate = DialogNewActorBinding.inflate(getLayoutInflater());
        Intrinsics.checkNotNullExpressionValue(dialogNewActorBindingInflate, "inflate(...)");
        final AlertDialog alertDialogCreate = new AlertDialog.Builder(this).setTitle(R.string.new_sprite_dialog_title).setView(dialogNewActorBindingInflate.getRoot()).create();
        Intrinsics.checkNotNullExpressionValue(alertDialogCreate, "create(...)");
        dialogNewActorBindingInflate.dialogNewLookPaintroid.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.ProjectActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ProjectActivity.handleAddSpriteButton$lambda$18(this.f$0, alertDialogCreate, view);
            }
        });
        dialogNewActorBindingInflate.dialogNewLookMediaLibrary.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.ProjectActivity$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ProjectActivity.handleAddSpriteButton$lambda$19(this.f$0, alertDialogCreate, view);
            }
        });
        dialogNewActorBindingInflate.dialogNewLookObjectLibrary.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.ProjectActivity$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ProjectActivity.handleAddSpriteButton$lambda$20(this.f$0, alertDialogCreate, view);
            }
        });
        dialogNewActorBindingInflate.dialogNewLookGallery.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.ProjectActivity$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ProjectActivity.handleAddSpriteButton$lambda$21(this.f$0, alertDialogCreate, view);
            }
        });
        dialogNewActorBindingInflate.dialogNewLookCamera.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.ProjectActivity$$ExternalSyntheticLambda6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ProjectActivity.handleAddSpriteButton$lambda$22(this.f$0, alertDialogCreate, view);
            }
        });
        dialogNewActorBindingInflate.dialogNewLookBackpack.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.ProjectActivity$$ExternalSyntheticLambda7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ProjectActivity.handleAddSpriteButton$lambda$23(this.f$0, alertDialogCreate, view);
            }
        });
        dialogNewActorBindingInflate.dialogNewLookFromActorsAndObjects.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.ProjectActivity$$ExternalSyntheticLambda8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ProjectActivity.handleAddSpriteButton$lambda$24(this.f$0, alertDialogCreate, view);
            }
        });
        dialogNewActorBindingInflate.dialogNewLookFromLocal.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.ProjectActivity$$ExternalSyntheticLambda9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ProjectActivity.handleAddSpriteButton$lambda$25(this.f$0, alertDialogCreate, view);
            }
        });
        dialogNewActorBindingInflate.dialogNewLookEmptyObject.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.ProjectActivity$$ExternalSyntheticLambda10
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ProjectActivity.handleAddSpriteButton$lambda$26(this.f$0, alertDialogCreate, view);
            }
        });
        alertDialogCreate.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handleAddSpriteButton$lambda$18(ProjectActivity this$0, AlertDialog alertDialog, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(alertDialog, "$alertDialog");
        new ImportFromPocketPaintLauncher(this$0).startActivityForResult(0);
        alertDialog.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handleAddSpriteButton$lambda$19(ProjectActivity this$0, AlertDialog alertDialog, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(alertDialog, "$alertDialog");
        new ImportFormMediaLibraryLauncher(this$0, FlavoredConstants.LIBRARY_LOOKS_URL).startActivityForResult(1);
        alertDialog.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handleAddSpriteButton$lambda$20(ProjectActivity this$0, AlertDialog alertDialog, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(alertDialog, "$alertDialog");
        new ImportFormMediaLibraryLauncher(this$0, FlavoredConstants.LIBRARY_OBJECT_URL).startActivityForResult(4);
        alertDialog.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handleAddSpriteButton$lambda$21(ProjectActivity this$0, AlertDialog alertDialog, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(alertDialog, "$alertDialog");
        ProjectActivity projectActivity = this$0;
        String string = this$0.getString(R.string.select_look_from_gallery);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        new ImportFromFileLauncher(projectActivity, "image/*", string).startActivityForResult(2);
        alertDialog.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handleAddSpriteButton$lambda$22(ProjectActivity this$0, AlertDialog alertDialog, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(alertDialog, "$alertDialog");
        new ImportFromCameraLauncher(this$0).startActivityForResult(3);
        alertDialog.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handleAddSpriteButton$lambda$23(ProjectActivity this$0, AlertDialog alertDialog, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(alertDialog, "$alertDialog");
        List<Sprite> sprites = BackpackListManager.getInstance().getSprites();
        Intrinsics.checkNotNullExpressionValue(sprites, "getSprites(...)");
        if (!sprites.isEmpty()) {
            Intent intent = new Intent(this$0, (Class<?>) BackpackActivity.class);
            intent.putExtra("fragmentPosition", 1);
            this$0.startActivity(intent);
        } else {
            ToastUtil.showError(this$0, R.string.backpack_empty);
        }
        alertDialog.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handleAddSpriteButton$lambda$24(ProjectActivity this$0, AlertDialog alertDialog, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(alertDialog, "$alertDialog");
        List<Sprite> sprites = ActorsAndObjectsManager.getInstance().getSprites();
        Intrinsics.checkNotNullExpressionValue(sprites, "getSprites(...)");
        if (!sprites.isEmpty()) {
            Intent intent = new Intent(this$0, (Class<?>) ActorAndObjectActivity.class);
            intent.putExtra("fragmentPosition", 0);
            this$0.startActivity(intent);
        } else {
            ToastUtil.showError(this$0, R.string.backpack_empty);
        }
        alertDialog.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handleAddSpriteButton$lambda$25(ProjectActivity this$0, AlertDialog alertDialog, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(alertDialog, "$alertDialog");
        ProjectActivity projectActivity = this$0;
        String string = this$0.getString(R.string.import_sprite_from_project_launcher);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        new ImportFromLocalProjectListLauncher(projectActivity, string).startActivityForResult(5);
        alertDialog.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handleAddSpriteButton$lambda$26(ProjectActivity this$0, AlertDialog alertDialog, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(alertDialog, "$alertDialog");
        this$0.addEmptySpriteObject();
        alertDialog.dismiss();
    }

    private final void handlePlayButton() {
        StageActivity.handlePlayButton(getProjectManager(), this);
    }

    private final void handleAiButton() {
        StageActivity.handleAiButton();
    }

    private final void showLegoSensorConfigInfo() {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean z = defaultSharedPreferences.getBoolean(SettingsFragment.SETTINGS_MINDSTORMS_NXT_SHOW_SENSOR_INFO_BOX_DISABLED, false);
        boolean z2 = defaultSharedPreferences.getBoolean(SettingsFragment.SETTINGS_MINDSTORMS_EV3_SHOW_SENSOR_INFO_BOX_DISABLED, false);
        Brick.ResourcesSet requiredResources = getProjectManager().getCurrentProject().getRequiredResources();
        if (!z && requiredResources.contains(2)) {
            LegoSensorConfigInfoDialog legoSensorConfigInfoDialogNewInstance = LegoSensorConfigInfoDialog.newInstance(0);
            Intrinsics.checkNotNullExpressionValue(legoSensorConfigInfoDialogNewInstance, "newInstance(...)");
            legoSensorConfigInfoDialogNewInstance.show(getSupportFragmentManager(), LegoSensorConfigInfoDialog.DIALOG_FRAGMENT_TAG);
        }
        if (z2 || !requiredResources.contains(20)) {
            return;
        }
        LegoSensorConfigInfoDialog legoSensorConfigInfoDialogNewInstance2 = LegoSensorConfigInfoDialog.newInstance(1);
        Intrinsics.checkNotNullExpressionValue(legoSensorConfigInfoDialogNewInstance2, "newInstance(...)");
        legoSensorConfigInfoDialogNewInstance2.show(getSupportFragmentManager(), LegoSensorConfigInfoDialog.DIALOG_FRAGMENT_TAG);
    }

    private final void showPhysicsCacheWarningDialog() {
        new AlertDialog.Builder(this).setTitle(R.string.physics_warning_title).setMessage(R.string.physics_warning_message).setPositiveButton(R.string.physics_warning_update_now, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.ui.ProjectActivity$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                ProjectActivity.showPhysicsCacheWarningDialog$lambda$27(this.f$0, dialogInterface, i);
            }
        }).setNegativeButton(R.string.physics_warning_later, (DialogInterface.OnClickListener) null).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showPhysicsCacheWarningDialog$lambda$27(ProjectActivity this$0, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.recalculatePhysicsCache();
    }

    private final void recalculatePhysicsCache() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.physics_warning_updating));
        progressDialog.setCancelable(false);
        progressDialog.show();
        new Thread(new Runnable() { // from class: org.catrobat.catroid.ui.ProjectActivity$$ExternalSyntheticLambda22
            @Override // java.lang.Runnable
            public final void run() {
                ProjectActivity.recalculatePhysicsCache$lambda$31(this.f$0, progressDialog);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void recalculatePhysicsCache$lambda$31(final ProjectActivity this$0, final ProgressDialog progressDialog) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(progressDialog, "$progressDialog");
        try {
            Project currentProject = this$0.getProjectManager().getCurrentProject();
            if (currentProject != null && currentProject.getSceneList() != null) {
                for (Scene scene : currentProject.getSceneList()) {
                    if (scene.getSpriteList() != null) {
                        for (Sprite sprite : scene.getSpriteList()) {
                            if (sprite.getLookList() != null) {
                                Iterator<LookData> it = sprite.getLookList().iterator();
                                while (it.hasNext()) {
                                    CollisionInformation collisionInformation = it.next().getCollisionInformation();
                                    if (collisionInformation != null) {
                                        collisionInformation.forceRecalculateAndSave();
                                    }
                                }
                            }
                        }
                    }
                }
                XstreamSerializer.getInstance().saveProject(currentProject);
            }
            this$0.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.ui.ProjectActivity$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    ProjectActivity.recalculatePhysicsCache$lambda$31$lambda$29(progressDialog, this$0);
                }
            });
        } catch (Exception e) {
            Log.e("ProjectActivity", "Failed to recalculate physics cache", e);
            this$0.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.ui.ProjectActivity$$ExternalSyntheticLambda11
                @Override // java.lang.Runnable
                public final void run() {
                    ProjectActivity.recalculatePhysicsCache$lambda$31$lambda$30(progressDialog, this$0);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void recalculatePhysicsCache$lambda$31$lambda$29(ProgressDialog progressDialog, ProjectActivity this$0) {
        Intrinsics.checkNotNullParameter(progressDialog, "$progressDialog");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        progressDialog.dismiss();
        ToastUtil.showSuccess(this$0, this$0.getString(R.string.physics_warning_success));
        Fragment currentFragment = this$0.getCurrentFragment();
        if (currentFragment instanceof RecyclerViewFragment) {
            ((RecyclerViewFragment) currentFragment).notifyDataSetChanged();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void recalculatePhysicsCache$lambda$31$lambda$30(ProgressDialog progressDialog, ProjectActivity this$0) {
        Intrinsics.checkNotNullParameter(progressDialog, "$progressDialog");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        progressDialog.dismiss();
        ToastUtil.showError(this$0, this$0.getString(R.string.physics_warning_error));
    }
}
