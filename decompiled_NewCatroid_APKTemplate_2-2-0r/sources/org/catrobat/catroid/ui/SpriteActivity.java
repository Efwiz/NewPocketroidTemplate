package org.catrobat.catroid.ui;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import com.google.android.material.textfield.TextInputEditText;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import kotlin.Pair;
import kotlin.jvm.functions.Function0;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.cast.CastManager;
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
import org.catrobat.catroid.content.bricks.PlaceAtBrick;
import org.catrobat.catroid.content.bricks.PointInDirectionBrick;
import org.catrobat.catroid.content.bricks.SetSizeToBrick;
import org.catrobat.catroid.content.bricks.VisualPlacementBrick;
import org.catrobat.catroid.formulaeditor.UserList;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.io.StorageOperations;
import org.catrobat.catroid.io.asynctask.ProjectSaver;
import org.catrobat.catroid.pocketmusic.PocketMusicActivity;
import org.catrobat.catroid.soundrecorder.SoundRecorderActivity;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.stage.TestResult;
import org.catrobat.catroid.ui.controller.RecentBrickListManager;
import org.catrobat.catroid.ui.fragment.AddBrickFragment;
import org.catrobat.catroid.ui.fragment.BrickCategoryFragment;
import org.catrobat.catroid.ui.fragment.FormulaEditorFragment;
import org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog;
import org.catrobat.catroid.ui.recyclerview.dialog.dialoginterface.NewItemInterface;
import org.catrobat.catroid.ui.recyclerview.dialog.textwatcher.DuplicateInputTextWatcher;
import org.catrobat.catroid.ui.recyclerview.fragment.CatblocksScriptFragment;
import org.catrobat.catroid.ui.recyclerview.fragment.DataListFragment;
import org.catrobat.catroid.ui.recyclerview.fragment.ListSelectorFragment;
import org.catrobat.catroid.ui.recyclerview.fragment.LookListFragment;
import org.catrobat.catroid.ui.recyclerview.fragment.ScriptFragment;
import org.catrobat.catroid.ui.recyclerview.fragment.SoundListFragment;
import org.catrobat.catroid.ui.recyclerview.util.UniqueNameProvider;
import org.catrobat.catroid.ui.settingsfragments.SettingsFragment;
import org.catrobat.catroid.ui.workspace.WorkspaceLayout;
import org.catrobat.catroid.utils.SnackbarUtil;
import org.catrobat.catroid.utils.ToastUtil;
import org.catrobat.catroid.utils.Utils;
import org.catrobat.catroid.visualplacement.VisualPlacementActivity;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/SpriteActivity.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SpriteActivity extends BaseActivity {
    public static final int BACKGROUND_CAMERA = 7;
    public static final int BACKGROUND_FILE = 6;
    public static final int BACKGROUND_LIBRARY = 5;
    public static final int BACKGROUND_POCKET_PAINT = 4;
    public static final int EDIT_LOOK = 2020;
    public static final String EXTRA_BRICK_HASH = "BRICK_HASH";
    public static final String EXTRA_FRAGMENT_POSITION = "fragmentPosition";
    public static final String EXTRA_ROTATION = "ROTATION";
    public static final String EXTRA_TEXT = "TEXT";
    public static final String EXTRA_TEXT_ALIGNMENT = "TEXT_ALIGNMENT";
    public static final String EXTRA_TEXT_COLOR = "TEXT_COLOR";
    public static final String EXTRA_TEXT_SIZE = "TEXT_SIZE";
    public static final String EXTRA_X_TRANSFORM = "X";
    public static final String EXTRA_Y_TRANSFORM = "Y";
    public static final int FRAGMENT_LOOKS = 1;
    public static final int FRAGMENT_SCRIPTS = 0;
    public static final int FRAGMENT_SOUNDS = 2;
    public static final int LOOK_CAMERA = 11;
    public static final int LOOK_FILE = 10;
    public static final int LOOK_LIBRARY = 9;
    public static final int LOOK_POCKET_PAINT = 8;
    public static final int REQUEST_CODE_VISUAL_PLACEMENT = 2019;
    public static final int SOUND_FILE = 14;
    public static final int SOUND_LIBRARY = 13;
    public static final int SOUND_RECORD = 12;
    public static final int SPRITE_CAMERA = 3;
    public static final int SPRITE_FILE = 2;
    public static final int SPRITE_LIBRARY = 1;
    public static final int SPRITE_POCKET_PAINT = 0;
    public static final String TAG = "SpriteActivity";
    private LookData currentLookData;
    private Project currentProject;
    private Scene currentScene;
    private Sprite currentSprite;
    private String generatedVariableName;
    private NewItemInterface<LookData> onNewLookListener;
    private NewItemInterface<SoundInfo> onNewSoundListener;
    private NewItemInterface<Sprite> onNewSpriteListener;
    private ProjectManager projectManager;
    private WorkspaceLayout workspaceLayout;
    private boolean isUndoMenuItemVisible = false;
    private boolean areTabsAdded = false;
    private Bundle visualPlacementResult = null;

    @Override // org.catrobat.catroid.ui.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isFinishing()) {
            return;
        }
        ProjectManager projectManager = ProjectManager.getInstance();
        this.projectManager = projectManager;
        this.currentProject = projectManager.getCurrentProject();
        this.currentSprite = this.projectManager.getCurrentSprite();
        this.currentScene = this.projectManager.getCurrentlyEditedScene();
        setContentView(R.layout.activity_sprite);
        this.workspaceLayout = (WorkspaceLayout) findViewById(R.id.workspace_layout);
        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean("pref_free_window_mode", false)) {
            findViewById(R.id.fragment_container).setVisibility(8);
            findViewById(R.id.bottom_bar).setVisibility(8);
            findViewById(R.id.fragment_container).setVisibility(8);
            View viewFindViewById = findViewById(R.id.toolbar);
            if (viewFindViewById != null) {
                viewFindViewById.setVisibility(8);
            }
            if (getSupportActionBar() != null) {
                getSupportActionBar().hide();
            }
            this.workspaceLayout.setVisibility(0);
            HashMap map = new HashMap();
            map.put(ScriptFragment.TAG, new Pair("Скрипты", new Function0() { // from class: org.catrobat.catroid.ui.SpriteActivity$$ExternalSyntheticLambda8
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return new ScriptFragment();
                }
            }));
            map.put(LookListFragment.TAG, new Pair("Образы", new Function0() { // from class: org.catrobat.catroid.ui.SpriteActivity$$ExternalSyntheticLambda9
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return new LookListFragment();
                }
            }));
            map.put(SoundListFragment.TAG, new Pair("Звуки", new Function0() { // from class: org.catrobat.catroid.ui.SpriteActivity$$ExternalSyntheticLambda10
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return new SoundListFragment();
                }
            }));
            this.workspaceLayout.restoreLayoutState(map);
        } else {
            this.workspaceLayout.setVisibility(8);
            setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(createActionBarTitle());
            if (RecentBrickListManager.getInstance().getRecentBricks(true).size() == 0) {
                RecentBrickListManager.getInstance().loadRecentBricks();
            }
            Bundle extras = getIntent().getExtras();
            int i = extras != null ? extras.getInt("fragmentPosition", 0) : 0;
            SpriteActivityOnTabSelectedListenerKt.loadFragment(this, i);
            SpriteActivityOnTabSelectedListenerKt.addTabLayout(this, i);
            this.areTabsAdded = true;
        }
        MyActivityManager.INSTANCE.setSprite_activity(this);
    }

    public String createActionBarTitle() {
        Project project = this.currentProject;
        if (project != null && project.getSceneList() != null && this.currentProject.getSceneList().size() == 1) {
            return this.currentSprite.getName();
        }
        return this.currentScene.getName() + ": " + this.currentSprite.getName();
    }

    Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentById(R.id.fragment_container);
    }

    @Override // org.catrobat.catroid.ui.BaseActivity, android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_script_activity, menu);
        this.optionsMenu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    public void showUndo(boolean visible) {
        if (this.optionsMenu != null) {
            this.optionsMenu.findItem(R.id.menu_undo).setVisible(visible);
            if (visible) {
                ProjectManager.getInstance().changedProject(this.currentProject.getName());
            }
        }
    }

    public void checkForChange() {
        if (this.optionsMenu != null) {
            if (this.optionsMenu.findItem(R.id.menu_undo).isVisible()) {
                ProjectManager.getInstance().changedProject(this.currentProject.getName());
            } else {
                ProjectManager.getInstance().resetChangedFlag(this.currentProject);
            }
        }
    }

    public void setUndoMenuItemVisibility(boolean isVisible) {
        this.isUndoMenuItemVisible = isVisible;
    }

    @Override // android.app.Activity
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (getCurrentFragment() instanceof ScriptFragment) {
            menu.findItem(R.id.comment_in_out).setVisible(true);
            showUndo(this.isUndoMenuItemVisible);
        } else if (getCurrentFragment() instanceof LookListFragment) {
            showUndo(this.isUndoMenuItemVisible);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override // org.catrobat.catroid.ui.BaseActivity, android.app.Activity
    public boolean onOptionsItemSelected(MenuItem item) {
        LookData lookData;
        boolean z = (getCurrentFragment() instanceof ScriptFragment) && ((ScriptFragment) getCurrentFragment()).isCurrentlyMoving();
        if (item.getItemId() == 16908332 && z) {
            ((ScriptFragment) getCurrentFragment()).highlightMovingItem();
            return true;
        }
        if (item.getItemId() == R.id.menu_undo && (getCurrentFragment() instanceof LookListFragment)) {
            setUndoMenuItemVisibility(false);
            showUndo(this.isUndoMenuItemVisible);
            Fragment currentFragment = getCurrentFragment();
            if (currentFragment instanceof LookListFragment) {
                LookListFragment lookListFragment = (LookListFragment) currentFragment;
                if (!lookListFragment.undo() && (lookData = this.currentLookData) != null) {
                    lookListFragment.deleteItem(lookData);
                    this.currentLookData.dispose();
                    this.currentLookData = null;
                }
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override // org.catrobat.catroid.ui.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        super.onPause();
        WorkspaceLayout workspaceLayout = this.workspaceLayout;
        if (workspaceLayout != null && workspaceLayout.getVisibility() == 0) {
            this.workspaceLayout.saveLayoutState();
        }
        saveProject();
        RecentBrickListManager.getInstance().saveRecentBrickList();
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        WorkspaceLayout workspaceLayout = this.workspaceLayout;
        if (workspaceLayout != null && workspaceLayout.getVisibility() == 0 && this.workspaceLayout.handleBackPressed()) {
            return;
        }
        saveProject();
        Fragment currentFragment = getCurrentFragment();
        if (currentFragment instanceof ScriptFragment) {
            ScriptFragment scriptFragment = (ScriptFragment) currentFragment;
            if (scriptFragment.isCurrentlyMoving()) {
                scriptFragment.cancelMove();
                return;
            } else if (scriptFragment.isFinderOpen()) {
                scriptFragment.closeFinder();
                return;
            } else if (scriptFragment.isCurrentlyHighlighted()) {
                scriptFragment.cancelHighlighting();
                return;
            }
        } else if (currentFragment instanceof FormulaEditorFragment) {
            ((FormulaEditorFragment) currentFragment).exitFormulaEditorFragment();
            return;
        } else if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            if (currentFragment instanceof BrickCategoryFragment) {
                SnackbarUtil.showHintSnackbar(this, R.string.hint_scripts);
            } else if (currentFragment instanceof AddBrickFragment) {
                SnackbarUtil.showHintSnackbar(this, R.string.hint_category);
            }
            getSupportFragmentManager().popBackStack();
            return;
        }
        super.onBackPressed();
    }

    private void saveProject() {
        this.currentProject = ProjectManager.getInstance().getCurrentProject();
        new ProjectSaver(this.currentProject, getApplicationContext()).saveProjectAsync();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int requestCode, int resultCode, Intent data) throws Throwable {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 7777 || resultCode == 8888) {
            String stringExtra = data.getStringExtra(TestResult.TEST_RESULT_MESSAGE);
            ToastUtil.showError(this, stringExtra);
            ((ClipboardManager) getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("TestResult", ProjectManager.getInstance().getCurrentProject().getName() + "\n" + stringExtra));
        }
        if (resultCode != -1) {
            if (SettingsFragment.isCastSharedPreferenceEnabled(this) && this.projectManager.getCurrentProject().isCastProject() && !CastManager.getInstance().isConnected()) {
                CastManager.getInstance().openDeviceSelectorOrDisconnectDialog(this);
                return;
            }
            return;
        }
        if (requestCode != 2019) {
            switch (requestCode) {
                case 0:
                    addSpriteFromUri(new ImportFromPocketPaintLauncher(this).getPocketPaintCacheUri());
                    break;
                case 1:
                    addSpriteFromUri(Uri.fromFile(new File(data.getStringExtra(WebViewActivity.MEDIA_FILE_PATH))));
                    break;
                case 2:
                    addSpriteFromUri(data.getData(), Constants.JPEG_IMAGE_EXTENSION);
                    break;
                case 3:
                    addSpriteFromUri(new ImportFromCameraLauncher(this).getCacheCameraUri(), Constants.JPEG_IMAGE_EXTENSION);
                    break;
                case 4:
                    addBackgroundFromUri(new ImportFromPocketPaintLauncher(this).getPocketPaintCacheUri());
                    break;
                case 5:
                    addBackgroundFromUri(Uri.fromFile(new File(data.getStringExtra(WebViewActivity.MEDIA_FILE_PATH))));
                    break;
                case 6:
                    addBackgroundFromUri(data.getData(), Constants.JPEG_IMAGE_EXTENSION);
                    break;
                case 7:
                    addBackgroundFromUri(new ImportFromCameraLauncher(this).getCacheCameraUri(), Constants.JPEG_IMAGE_EXTENSION);
                    break;
                case 8:
                    addLookFromUri(new ImportFromPocketPaintLauncher(this).getPocketPaintCacheUri());
                    setUndoMenuItemVisibility(true);
                    break;
                case 9:
                    addLookFromUri(Uri.fromFile(new File(data.getStringExtra(WebViewActivity.MEDIA_FILE_PATH))));
                    setUndoMenuItemVisibility(true);
                    break;
                case 10:
                    addLookFromUri(data.getData(), Constants.JPEG_IMAGE_EXTENSION);
                    setUndoMenuItemVisibility(true);
                    break;
                case 11:
                    addLookFromUri(new ImportFromCameraLauncher(this).getCacheCameraUri(), Constants.JPEG_IMAGE_EXTENSION);
                    setUndoMenuItemVisibility(true);
                    break;
                case 12:
                case 14:
                    addSoundFromUri(data.getData());
                    break;
                case 13:
                    addSoundFromUri(Uri.fromFile(new File(data.getStringExtra(WebViewActivity.MEDIA_FILE_PATH))));
                    break;
            }
            return;
        }
        Bundle extras = data.getExtras();
        this.visualPlacementResult = extras;
        if (extras == null || extras.getInt(EXTRA_BRICK_HASH, -1) == -1) {
            return;
        }
        updateBrickFromVisualPlacement(this.visualPlacementResult);
    }

    private void updateBrickFromVisualPlacement(Bundle extras) {
        Brick formulaBrick;
        if (extras == null) {
            return;
        }
        int i = extras.getInt(VisualPlacementActivity.X_COORDINATE_BUNDLE_ARGUMENT);
        int i2 = extras.getInt(VisualPlacementActivity.Y_COORDINATE_BUNDLE_ARGUMENT);
        float f = extras.getFloat(VisualPlacementActivity.ROTATION_ANGLE_BUNDLE_ARGUMENT);
        float f2 = extras.getFloat(VisualPlacementActivity.SIZE_PERCENT_BUNDLE_ARGUMENT);
        int i3 = extras.getInt(EXTRA_BRICK_HASH);
        Fragment fragmentFindFragmentByTag = getSupportFragmentManager().findFragmentByTag(FormulaEditorFragment.FORMULA_EDITOR_FRAGMENT_TAG);
        if (fragmentFindFragmentByTag == null) {
            fragmentFindFragmentByTag = getSupportFragmentManager().findFragmentByTag(ScriptFragment.TAG);
        }
        if (fragmentFindFragmentByTag == null) {
            fragmentFindFragmentByTag = getCurrentFragment();
        }
        boolean z = fragmentFindFragmentByTag instanceof ScriptFragment;
        PointInDirectionBrick pointInDirectionBrick = null;
        if (z) {
            formulaBrick = ((ScriptFragment) fragmentFindFragmentByTag).findBrickByHash(i3);
        } else {
            formulaBrick = fragmentFindFragmentByTag instanceof FormulaEditorFragment ? ((FormulaEditorFragment) fragmentFindFragmentByTag).getFormulaBrick() : null;
        }
        if (formulaBrick instanceof VisualPlacementBrick) {
            VisualPlacementBrick visualPlacementBrick = (VisualPlacementBrick) formulaBrick;
            Script script = visualPlacementBrick.getScript();
            visualPlacementBrick.setCoordinates(i, i2);
            if (!ProjectManager.getInstance().getCurrentSprite().equals(this.currentScene.getBackgroundSprite()) && script != null) {
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
                            script.getBrickList().set(script.getBrickList().indexOf(pointInDirectionBrick), new PointInDirectionBrick(f + 90.0f));
                        }
                    } else if (z3) {
                        script.addBrick(i4, new PointInDirectionBrick(f + 90.0f));
                        i4 = iIndexOf + 2;
                    }
                    if (setSizeToBrick != null) {
                        if (z4) {
                            script.getBrickList().set(script.getBrickList().indexOf(setSizeToBrick), new SetSizeToBrick(f2));
                        }
                    } else if (z4) {
                        script.addBrick(i4, new SetSizeToBrick(f2));
                    }
                }
            }
            if (z) {
                ((ScriptFragment) fragmentFindFragmentByTag).notifyDataSetChanged();
            } else if (fragmentFindFragmentByTag instanceof FormulaEditorFragment) {
                ((FormulaEditorFragment) fragmentFindFragmentByTag).updateFragmentAfterVisualPlacement();
            }
        }
        setUndoMenuItemVisibility(extras.getBoolean(VisualPlacementActivity.CHANGED_COORDINATES));
    }

    public void registerOnNewSpriteListener(NewItemInterface<Sprite> listener) {
        this.onNewSpriteListener = listener;
    }

    public void registerOnNewLookListener(NewItemInterface<LookData> listener) {
        this.onNewLookListener = listener;
    }

    public void registerOnNewSoundListener(NewItemInterface<SoundInfo> listener) {
        this.onNewSoundListener = listener;
    }

    private void addSpriteFromUri(final Uri uri) {
        addSpriteFromUri(uri, Constants.DEFAULT_IMAGE_EXTENSION);
    }

    private void addSpriteFromUri(final Uri uri, String imageExtension) {
        String sanitizedFileName;
        final Bundle bundle = this.visualPlacementResult;
        this.visualPlacementResult = null;
        final String strResolveFileName = StorageOperations.resolveFileName(getContentResolver(), uri);
        if (strResolveFileName == null || StorageOperations.getSanitizedFileName(strResolveFileName).equals(Constants.TMP_IMAGE_FILE_NAME)) {
            String string = getString(R.string.default_sprite_name);
            strResolveFileName = string + imageExtension;
            sanitizedFileName = string;
        } else {
            sanitizedFileName = StorageOperations.getSanitizedFileName(strResolveFileName);
        }
        String uniqueNameInNameables = new UniqueNameProvider().getUniqueNameInNameables(sanitizedFileName, this.currentScene.getSpriteList());
        TextInputDialog.Builder builder = new TextInputDialog.Builder(this);
        builder.setHint(getString(R.string.sprite_name_label)).setText(uniqueNameInNameables).setTextWatcher(new DuplicateInputTextWatcher(this.currentScene.getSpriteList())).setPositiveButton(getString(R.string.ok), new TextInputDialog.OnClickListener() { // from class: org.catrobat.catroid.ui.SpriteActivity$$ExternalSyntheticLambda22
            @Override // org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog.OnClickListener
            public final void onPositiveButtonClick(DialogInterface dialogInterface, String str) throws Throwable {
                this.f$0.m5615lambda$addSpriteFromUri$0$orgcatrobatcatroiduiSpriteActivity(bundle, uri, strResolveFileName, dialogInterface, str);
            }
        });
        builder.setTitle(R.string.new_sprite_dialog_title).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.ui.SpriteActivity$$ExternalSyntheticLambda23
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                SpriteActivity.lambda$addSpriteFromUri$1(dialogInterface, i);
            }
        }).show();
    }

    /* JADX INFO: renamed from: lambda$addSpriteFromUri$0$org-catrobat-catroid-ui-SpriteActivity, reason: not valid java name */
    /* synthetic */ void m5615lambda$addSpriteFromUri$0$orgcatrobatcatroiduiSpriteActivity(Bundle bundle, Uri uri, String str, DialogInterface dialogInterface, String str2) throws Throwable {
        Sprite sprite = new Sprite(str2);
        this.currentScene.addSprite(sprite);
        StartScript startScript = new StartScript();
        sprite.addScript(startScript);
        if (bundle != null) {
            int i = bundle.getInt(VisualPlacementActivity.X_COORDINATE_BUNDLE_ARGUMENT);
            int i2 = bundle.getInt(VisualPlacementActivity.Y_COORDINATE_BUNDLE_ARGUMENT);
            float f = bundle.getFloat(VisualPlacementActivity.ROTATION_ANGLE_BUNDLE_ARGUMENT);
            float f2 = bundle.getFloat(VisualPlacementActivity.SIZE_PERCENT_BUNDLE_ARGUMENT);
            startScript.addBrick(new PointInDirectionBrick(f + 90.0f));
            startScript.addBrick(new SetSizeToBrick(f2));
            startScript.addBrick(new PlaceAtBrick(i, i2));
        } else {
            startScript.addBrick(new PlaceAtBrick(0, 0));
        }
        try {
            File file = new File(this.currentScene.getDirectory(), Constants.IMAGE_DIRECTORY_NAME);
            File fileCopyUriToDir = StorageOperations.copyUriToDir(getContentResolver(), uri, file, str);
            Utils.removeExifData(file, str);
            LookData lookData = new LookData(str2, fileCopyUriToDir);
            sprite.getLookList().add(lookData);
            lookData.getCollisionInformation().calculate();
        } catch (IOException e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }
        NewItemInterface<Sprite> newItemInterface = this.onNewSpriteListener;
        if (newItemInterface != null) {
            newItemInterface.addItem(sprite);
            Fragment currentFragment = getCurrentFragment();
            if (currentFragment instanceof ScriptFragment) {
                ((ScriptFragment) currentFragment).notifyDataSetChanged();
            }
        }
    }

    static /* synthetic */ void lambda$addSpriteFromUri$1(DialogInterface dialogInterface, int i) {
        try {
            if (Constants.MEDIA_LIBRARY_CACHE_DIRECTORY.exists()) {
                StorageOperations.deleteDir(Constants.MEDIA_LIBRARY_CACHE_DIRECTORY);
            }
        } catch (IOException e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }
    }

    private void addBackgroundFromUri(Uri uri) throws Throwable {
        addBackgroundFromUri(uri, Constants.DEFAULT_IMAGE_EXTENSION);
    }

    private void addBackgroundFromUri(Uri uri, String imageExtension) throws Throwable {
        String sanitizedFileName;
        String strResolveFileName = StorageOperations.resolveFileName(getContentResolver(), uri);
        if (strResolveFileName == null || StorageOperations.getSanitizedFileName(strResolveFileName).equals(Constants.TMP_IMAGE_FILE_NAME)) {
            String name2 = this.currentSprite.getName();
            strResolveFileName = name2 + imageExtension;
            sanitizedFileName = name2;
        } else {
            sanitizedFileName = StorageOperations.getSanitizedFileName(strResolveFileName);
        }
        String uniqueNameInNameables = new UniqueNameProvider().getUniqueNameInNameables(sanitizedFileName, this.currentSprite.getLookList());
        try {
            File file = new File(this.currentScene.getDirectory(), Constants.IMAGE_DIRECTORY_NAME);
            File fileCopyUriToDir = StorageOperations.copyUriToDir(getContentResolver(), uri, file, strResolveFileName);
            Utils.removeExifData(file, strResolveFileName);
            LookData lookData = new LookData(uniqueNameInNameables, fileCopyUriToDir);
            this.currentSprite.getLookList().add(lookData);
            lookData.getCollisionInformation().calculate();
            NewItemInterface<LookData> newItemInterface = this.onNewLookListener;
            if (newItemInterface != null) {
                newItemInterface.addItem(lookData);
            }
        } catch (IOException e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }
    }

    private void addLookFromUri(Uri uri) throws Throwable {
        addLookFromUri(uri, Constants.DEFAULT_IMAGE_EXTENSION);
    }

    private void addLookFromUri(Uri uri, String imageExtension) throws Throwable {
        String sanitizedFileName;
        String strResolveFileName = StorageOperations.resolveFileName(getContentResolver(), uri);
        if (strResolveFileName == null || StorageOperations.getSanitizedFileName(strResolveFileName).equals(Constants.TMP_IMAGE_FILE_NAME)) {
            String name2 = this.currentSprite.getName();
            strResolveFileName = name2 + imageExtension;
            sanitizedFileName = name2;
        } else {
            sanitizedFileName = StorageOperations.getSanitizedFileName(strResolveFileName);
        }
        String uniqueNameInNameables = new UniqueNameProvider().getUniqueNameInNameables(sanitizedFileName, this.currentSprite.getLookList());
        try {
            File file = new File(this.currentScene.getDirectory(), Constants.IMAGE_DIRECTORY_NAME);
            File fileCopyUriToDir = StorageOperations.copyUriToDir(getContentResolver(), uri, file, strResolveFileName);
            Utils.removeExifData(file, strResolveFileName);
            LookData lookData = new LookData(uniqueNameInNameables, fileCopyUriToDir);
            this.currentLookData = lookData;
            this.currentSprite.getLookList().add(lookData);
            lookData.getCollisionInformation().calculate();
            NewItemInterface<LookData> newItemInterface = this.onNewLookListener;
            if (newItemInterface != null) {
                newItemInterface.addItem(lookData);
            }
        } catch (IOException e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }
    }

    private void addSoundFromUri(Uri uri) {
        String str;
        String sanitizedFileName;
        String strResolveFileName = StorageOperations.resolveFileName(getContentResolver(), uri);
        if (strResolveFileName == null) {
            sanitizedFileName = this.currentSprite.getName();
            str = sanitizedFileName + Constants.DEFAULT_SOUND_EXTENSION;
        } else {
            str = strResolveFileName;
            sanitizedFileName = StorageOperations.getSanitizedFileName(strResolveFileName);
        }
        try {
            SoundInfo soundInfo = new SoundInfo(new UniqueNameProvider().getUniqueNameInNameables(sanitizedFileName, this.currentSprite.getSoundList()), StorageOperations.copyUriToDir(getContentResolver(), uri, new File(this.currentScene.getDirectory(), Constants.SOUND_DIRECTORY_NAME), str));
            this.currentSprite.getSoundList().add(soundInfo);
            NewItemInterface<SoundInfo> newItemInterface = this.onNewSoundListener;
            if (newItemInterface != null) {
                newItemInterface.addItem(soundInfo);
            }
        } catch (IOException e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }
    }

    public void handleAiAssistButton(View view) {
        ai_assist_dialog();
    }

    public void handleAddButton(View view) {
        WorkspaceLayout workspaceLayout = this.workspaceLayout;
        if (workspaceLayout != null && workspaceLayout.getVisibility() == 0) {
            String focusedWindowTag = this.workspaceLayout.getFocusedWindowTag();
            if (focusedWindowTag == null || focusedWindowTag.equals(ScriptFragment.TAG)) {
                this.workspaceLayout.openBrickCategoryWindow();
                return;
            }
            if (DataListFragment.TAG.equals(focusedWindowTag)) {
                handleAddUserDataButton();
                return;
            }
            if (LookListFragment.TAG.equals(focusedWindowTag)) {
                handleAddLookButton();
                return;
            } else if (SoundListFragment.TAG.equals(focusedWindowTag)) {
                handleAddSoundButton();
                return;
            } else {
                this.workspaceLayout.openBrickCategoryWindow();
                return;
            }
        }
        if (getCurrentFragment() instanceof ScriptFragment) {
            ((ScriptFragment) getCurrentFragment()).handleAddButton();
            return;
        }
        if (getCurrentFragment() instanceof CatblocksScriptFragment) {
            ((CatblocksScriptFragment) getCurrentFragment()).handleAddButton();
            return;
        }
        if (getCurrentFragment() instanceof DataListFragment) {
            handleAddUserDataButton();
            return;
        }
        if (getCurrentFragment() instanceof LookListFragment) {
            handleAddLookButton();
            return;
        }
        if (getCurrentFragment() instanceof SoundListFragment) {
            handleAddSoundButton();
        }
        if (getCurrentFragment() instanceof ListSelectorFragment) {
            handleAddUserListButton();
        }
    }

    public void handleAddSpriteButton() {
        View viewInflate = View.inflate(this, R.layout.dialog_new_look, null);
        final AlertDialog alertDialogCreate = new AlertDialog.Builder(this).setTitle(R.string.new_sprite_dialog_title).setView(viewInflate).create();
        viewInflate.findViewById(R.id.dialog_new_look_paintroid).setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.SpriteActivity$$ExternalSyntheticLambda24
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m5627xc9fde36f(alertDialogCreate, view);
            }
        });
        viewInflate.findViewById(R.id.dialog_new_look_media_library).setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.SpriteActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m5628x211bd44e(alertDialogCreate, view);
            }
        });
        viewInflate.findViewById(R.id.dialog_new_look_gallery).setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.SpriteActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m5629x7839c52d(alertDialogCreate, view);
            }
        });
        viewInflate.findViewById(R.id.dialog_new_look_camera).setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.SpriteActivity$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m5630xcf57b60c(alertDialogCreate, view);
            }
        });
        alertDialogCreate.show();
    }

    /* JADX INFO: renamed from: lambda$handleAddSpriteButton$2$org-catrobat-catroid-ui-SpriteActivity, reason: not valid java name */
    /* synthetic */ void m5627xc9fde36f(AlertDialog alertDialog, View view) {
        new ImportFromPocketPaintLauncher(this).startActivityForResult(0);
        alertDialog.dismiss();
    }

    /* JADX INFO: renamed from: lambda$handleAddSpriteButton$3$org-catrobat-catroid-ui-SpriteActivity, reason: not valid java name */
    /* synthetic */ void m5628x211bd44e(AlertDialog alertDialog, View view) {
        new ImportFormMediaLibraryLauncher(this, FlavoredConstants.LIBRARY_LOOKS_URL).startActivityForResult(1);
        alertDialog.dismiss();
    }

    /* JADX INFO: renamed from: lambda$handleAddSpriteButton$4$org-catrobat-catroid-ui-SpriteActivity, reason: not valid java name */
    /* synthetic */ void m5629x7839c52d(AlertDialog alertDialog, View view) {
        new ImportFromFileLauncher(this, "image/*", getString(R.string.select_look_from_gallery)).startActivityForResult(2);
        alertDialog.dismiss();
    }

    /* JADX INFO: renamed from: lambda$handleAddSpriteButton$5$org-catrobat-catroid-ui-SpriteActivity, reason: not valid java name */
    /* synthetic */ void m5630xcf57b60c(AlertDialog alertDialog, View view) {
        new ImportFromCameraLauncher(this).startActivityForResult(3);
        alertDialog.dismiss();
    }

    public void handleAddBackgroundButton() {
        final String str;
        View viewInflate = View.inflate(this, R.layout.dialog_new_look, null);
        final AlertDialog alertDialogCreate = new AlertDialog.Builder(this).setTitle(R.string.new_look_dialog_title).setView(viewInflate).create();
        if (this.projectManager.isCurrentProjectLandscapeMode()) {
            str = FlavoredConstants.LIBRARY_BACKGROUNDS_URL_LANDSCAPE;
        } else {
            str = FlavoredConstants.LIBRARY_BACKGROUNDS_URL_PORTRAIT;
        }
        viewInflate.findViewById(R.id.dialog_new_look_paintroid).setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.SpriteActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m5616xb7898762(alertDialogCreate, view);
            }
        });
        viewInflate.findViewById(R.id.dialog_new_look_media_library).setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.SpriteActivity$$ExternalSyntheticLambda11
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m5617xea77841(str, alertDialogCreate, view);
            }
        });
        viewInflate.findViewById(R.id.dialog_new_look_gallery).setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.SpriteActivity$$ExternalSyntheticLambda17
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m5618x65c56920(alertDialogCreate, view);
            }
        });
        viewInflate.findViewById(R.id.dialog_new_look_camera).setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.SpriteActivity$$ExternalSyntheticLambda18
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m5619xbce359ff(alertDialogCreate, view);
            }
        });
        alertDialogCreate.show();
    }

    /* JADX INFO: renamed from: lambda$handleAddBackgroundButton$6$org-catrobat-catroid-ui-SpriteActivity, reason: not valid java name */
    /* synthetic */ void m5616xb7898762(AlertDialog alertDialog, View view) {
        new ImportFromPocketPaintLauncher(this).startActivityForResult(4);
        alertDialog.dismiss();
    }

    /* JADX INFO: renamed from: lambda$handleAddBackgroundButton$7$org-catrobat-catroid-ui-SpriteActivity, reason: not valid java name */
    /* synthetic */ void m5617xea77841(String str, AlertDialog alertDialog, View view) {
        new ImportFormMediaLibraryLauncher(this, str).startActivityForResult(5);
        alertDialog.dismiss();
    }

    /* JADX INFO: renamed from: lambda$handleAddBackgroundButton$8$org-catrobat-catroid-ui-SpriteActivity, reason: not valid java name */
    /* synthetic */ void m5618x65c56920(AlertDialog alertDialog, View view) {
        new ImportFromFileLauncher(this, "image/*", getString(R.string.select_look_from_gallery)).startActivityForResult(6);
        alertDialog.dismiss();
    }

    /* JADX INFO: renamed from: lambda$handleAddBackgroundButton$9$org-catrobat-catroid-ui-SpriteActivity, reason: not valid java name */
    /* synthetic */ void m5619xbce359ff(AlertDialog alertDialog, View view) {
        new ImportFromCameraLauncher(this).startActivityForResult(7);
        alertDialog.dismiss();
    }

    public void handleAddLookButton() {
        final String str;
        View viewInflate = View.inflate(this, R.layout.dialog_new_look, null);
        final AlertDialog alertDialogCreate = new AlertDialog.Builder(this).setTitle(R.string.new_look_dialog_title).setView(viewInflate).create();
        if (this.currentSprite.equals(this.currentScene.getBackgroundSprite())) {
            if (this.projectManager.isCurrentProjectLandscapeMode()) {
                str = FlavoredConstants.LIBRARY_BACKGROUNDS_URL_LANDSCAPE;
            } else {
                str = FlavoredConstants.LIBRARY_BACKGROUNDS_URL_PORTRAIT;
            }
        } else {
            str = FlavoredConstants.LIBRARY_LOOKS_URL;
        }
        viewInflate.findViewById(R.id.dialog_new_look_paintroid).setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.SpriteActivity$$ExternalSyntheticLambda13
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m5620x4e4a653a(alertDialogCreate, view);
            }
        });
        viewInflate.findViewById(R.id.dialog_new_look_media_library).setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.SpriteActivity$$ExternalSyntheticLambda14
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m5621xa5685619(str, alertDialogCreate, view);
            }
        });
        viewInflate.findViewById(R.id.dialog_new_look_gallery).setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.SpriteActivity$$ExternalSyntheticLambda15
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m5622xfc8646f8(alertDialogCreate, view);
            }
        });
        viewInflate.findViewById(R.id.dialog_new_look_camera).setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.SpriteActivity$$ExternalSyntheticLambda16
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m5623x53a437d7(alertDialogCreate, view);
            }
        });
        alertDialogCreate.show();
    }

    /* JADX INFO: renamed from: lambda$handleAddLookButton$10$org-catrobat-catroid-ui-SpriteActivity, reason: not valid java name */
    /* synthetic */ void m5620x4e4a653a(AlertDialog alertDialog, View view) {
        new ImportFromPocketPaintLauncher(this).startActivityForResult(8);
        alertDialog.dismiss();
    }

    /* JADX INFO: renamed from: lambda$handleAddLookButton$11$org-catrobat-catroid-ui-SpriteActivity, reason: not valid java name */
    /* synthetic */ void m5621xa5685619(String str, AlertDialog alertDialog, View view) {
        new ImportFormMediaLibraryLauncher(this, str).startActivityForResult(9);
        alertDialog.dismiss();
    }

    /* JADX INFO: renamed from: lambda$handleAddLookButton$12$org-catrobat-catroid-ui-SpriteActivity, reason: not valid java name */
    /* synthetic */ void m5622xfc8646f8(AlertDialog alertDialog, View view) {
        new ImportFromFileLauncher(this, "image/*", getString(R.string.select_look_from_gallery)).startActivityForResult(10);
        alertDialog.dismiss();
    }

    /* JADX INFO: renamed from: lambda$handleAddLookButton$13$org-catrobat-catroid-ui-SpriteActivity, reason: not valid java name */
    /* synthetic */ void m5623x53a437d7(AlertDialog alertDialog, View view) {
        new ImportFromCameraLauncher(this).startActivityForResult(11);
        alertDialog.dismiss();
    }

    public void handleAddSoundButton() {
        View viewInflate = View.inflate(this, R.layout.dialog_new_sound, null);
        final AlertDialog alertDialogCreate = new AlertDialog.Builder(this).setTitle(R.string.new_sound_dialog_title).setView(viewInflate).create();
        viewInflate.findViewById(R.id.dialog_new_sound_recorder).setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.SpriteActivity$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m5624xe8e468ee(alertDialogCreate, view);
            }
        });
        viewInflate.findViewById(R.id.dialog_new_sound_media_library).setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.SpriteActivity$$ExternalSyntheticLambda6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m5625x400259cd(alertDialogCreate, view);
            }
        });
        viewInflate.findViewById(R.id.dialog_new_sound_gallery).setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.SpriteActivity$$ExternalSyntheticLambda7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m5626x97204aac(alertDialogCreate, view);
            }
        });
        alertDialogCreate.show();
    }

    /* JADX INFO: renamed from: lambda$handleAddSoundButton$14$org-catrobat-catroid-ui-SpriteActivity, reason: not valid java name */
    /* synthetic */ void m5624xe8e468ee(AlertDialog alertDialog, View view) {
        startActivityForResult(new Intent(this, (Class<?>) SoundRecorderActivity.class), 12);
        alertDialog.dismiss();
    }

    /* JADX INFO: renamed from: lambda$handleAddSoundButton$15$org-catrobat-catroid-ui-SpriteActivity, reason: not valid java name */
    /* synthetic */ void m5625x400259cd(AlertDialog alertDialog, View view) {
        new ImportFormMediaLibraryLauncher(this, FlavoredConstants.LIBRARY_SOUNDS_URL).startActivityForResult(13);
        alertDialog.dismiss();
    }

    /* JADX INFO: renamed from: lambda$handleAddSoundButton$16$org-catrobat-catroid-ui-SpriteActivity, reason: not valid java name */
    /* synthetic */ void m5626x97204aac(AlertDialog alertDialog, View view) {
        new ImportFromFileLauncher(this, "audio/*", getString(R.string.sound_select_source)).startActivityForResult(14);
        alertDialog.dismiss();
    }

    private /* synthetic */ void lambda$handleAddSoundButton$17(AlertDialog alertDialog, View view) {
        startActivity(new Intent(this, (Class<?>) PocketMusicActivity.class));
        alertDialog.dismiss();
    }

    public void ai_assist_dialog() {
        View viewInflate = View.inflate(this, R.layout.dialog_ai_assist, null);
        TextInputDialog.Builder builder = new TextInputDialog.Builder(this);
        builder.setPositiveButton(getString(R.string.ok), new TextInputDialog.OnClickListener() { // from class: org.catrobat.catroid.ui.SpriteActivity$$ExternalSyntheticLambda4
            @Override // org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog.OnClickListener
            public final void onPositiveButtonClick(DialogInterface dialogInterface, String str) {
                Log.d("ab", str);
            }
        });
        builder.setTitle(R.string.ai_assist).setView(viewInflate).setNegativeButton(getString(R.string.cancel), (DialogInterface.OnClickListener) null).create().show();
    }

    public void handleAddUserDataButton() {
        View viewInflate = View.inflate(this, R.layout.dialog_new_user_data, null);
        final CheckBox checkBox = (CheckBox) viewInflate.findViewById(R.id.make_list);
        checkBox.setVisibility(0);
        final RadioButton radioButton = (RadioButton) viewInflate.findViewById(R.id.multiplayer);
        if (SettingsFragment.isMultiplayerVariablesPreferenceEnabled(getApplicationContext())) {
            radioButton.setVisibility(0);
            radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: org.catrobat.catroid.ui.SpriteActivity$$ExternalSyntheticLambda19
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    checkBox.setEnabled(!z);
                }
            });
        }
        final RadioButton radioButton2 = (RadioButton) viewInflate.findViewById(R.id.global);
        final ArrayList arrayList = new ArrayList();
        ProjectManager projectManager = ProjectManager.getInstance();
        this.currentSprite = projectManager.getCurrentSprite();
        Project currentProject = projectManager.getCurrentProject();
        this.currentProject = currentProject;
        arrayList.addAll(currentProject.getUserVariables());
        arrayList.addAll(this.currentProject.getMultiplayerVariables());
        arrayList.addAll(this.currentSprite.getUserVariables());
        final ArrayList arrayList2 = new ArrayList();
        arrayList2.addAll(this.currentProject.getUserLists());
        arrayList2.addAll(this.currentSprite.getUserLists());
        final DuplicateInputTextWatcher duplicateInputTextWatcher = new DuplicateInputTextWatcher(arrayList);
        TextInputDialog.Builder builder = new TextInputDialog.Builder(this);
        final UniqueNameProvider uniqueNameProviderCreateUniqueNameProvider = builder.createUniqueNameProvider(R.string.default_variable_name);
        final UniqueNameProvider uniqueNameProviderCreateUniqueNameProvider2 = builder.createUniqueNameProvider(R.string.default_list_name);
        this.generatedVariableName = uniqueNameProviderCreateUniqueNameProvider.getUniqueName(getString(R.string.default_variable_name), null);
        builder.setTextWatcher(duplicateInputTextWatcher).setText(this.generatedVariableName).setPositiveButton(getString(R.string.ok), new TextInputDialog.OnClickListener() { // from class: org.catrobat.catroid.ui.SpriteActivity$$ExternalSyntheticLambda20
            @Override // org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog.OnClickListener
            public final void onPositiveButtonClick(DialogInterface dialogInterface, String str) {
                this.f$0.m5631x6d4669b1(radioButton2, radioButton, checkBox, dialogInterface, str);
            }
        });
        final AlertDialog alertDialogCreate = builder.setTitle(R.string.formula_editor_variable_dialog_title).setView(viewInflate).setNegativeButton(getString(R.string.cancel), (DialogInterface.OnClickListener) null).create();
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: org.catrobat.catroid.ui.SpriteActivity$$ExternalSyntheticLambda21
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                this.f$0.m5632xc4645a90(alertDialogCreate, duplicateInputTextWatcher, arrayList2, uniqueNameProviderCreateUniqueNameProvider2, arrayList, uniqueNameProviderCreateUniqueNameProvider, radioButton, compoundButton, z);
            }
        });
        alertDialogCreate.show();
    }

    /* JADX INFO: renamed from: lambda$handleAddUserDataButton$20$org-catrobat-catroid-ui-SpriteActivity, reason: not valid java name */
    /* synthetic */ void m5631x6d4669b1(RadioButton radioButton, RadioButton radioButton2, CheckBox checkBox, DialogInterface dialogInterface, String str) {
        boolean zIsChecked = radioButton.isChecked();
        boolean zIsChecked2 = radioButton2.isChecked();
        PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean(SharedPreferenceKeys.INDEXING_VARIABLE_PREFERENCE_KEY, false).apply();
        if (checkBox.isChecked()) {
            UserList userList = new UserList(str);
            if (zIsChecked) {
                this.currentProject.addUserList(userList);
            } else {
                this.currentSprite.addUserList(userList);
            }
        } else {
            UserVariable userVariable = new UserVariable(str);
            if (zIsChecked2) {
                this.currentProject.addMultiplayerVariable(userVariable);
            } else if (zIsChecked) {
                this.currentProject.addUserVariable(userVariable);
            } else {
                this.currentSprite.addUserVariable(userVariable);
            }
        }
        if (getCurrentFragment() instanceof DataListFragment) {
            ((DataListFragment) getCurrentFragment()).notifyDataSetChanged();
            ((DataListFragment) getCurrentFragment()).indexAndSort();
        }
        DataListFragment.refreshActiveInstance();
    }

    /* JADX INFO: renamed from: lambda$handleAddUserDataButton$21$org-catrobat-catroid-ui-SpriteActivity, reason: not valid java name */
    /* synthetic */ void m5632xc4645a90(AlertDialog alertDialog, DuplicateInputTextWatcher duplicateInputTextWatcher, List list, UniqueNameProvider uniqueNameProvider, List list2, UniqueNameProvider uniqueNameProvider2, RadioButton radioButton, CompoundButton compoundButton, boolean z) {
        TextInputEditText textInputEditText = (TextInputEditText) alertDialog.findViewById(R.id.input_edit_text);
        String string = textInputEditText.getText().toString();
        if (z) {
            alertDialog.setTitle(getString(R.string.formula_editor_list_dialog_title));
            duplicateInputTextWatcher.setOriginalScope(list);
            if (string.equals(this.generatedVariableName)) {
                String uniqueName = uniqueNameProvider.getUniqueName(getString(R.string.default_list_name), null);
                this.generatedVariableName = uniqueName;
                textInputEditText.setText(uniqueName);
            }
        } else {
            alertDialog.setTitle(getString(R.string.formula_editor_variable_dialog_title));
            duplicateInputTextWatcher.setOriginalScope(list2);
            if (string.equals(this.generatedVariableName)) {
                String uniqueName2 = uniqueNameProvider2.getUniqueName(getString(R.string.default_variable_name), null);
                this.generatedVariableName = uniqueName2;
                textInputEditText.setText(uniqueName2);
            }
        }
        radioButton.setEnabled(!z);
    }

    public void handleAddUserListButton() {
        View viewInflate = View.inflate(this, R.layout.dialog_new_user_data, null);
        final RadioButton radioButton = (RadioButton) viewInflate.findViewById(R.id.global);
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(this.currentProject.getUserLists());
        arrayList.addAll(this.currentSprite.getUserLists());
        new TextInputDialog.Builder(this).setTextWatcher(new DuplicateInputTextWatcher(arrayList)).setPositiveButton(getString(R.string.ok), new TextInputDialog.OnClickListener() { // from class: org.catrobat.catroid.ui.SpriteActivity$$ExternalSyntheticLambda12
            @Override // org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog.OnClickListener
            public final void onPositiveButtonClick(DialogInterface dialogInterface, String str) {
                this.f$0.m5633xac2cd463(radioButton, dialogInterface, str);
            }
        }).setTitle(R.string.formula_editor_list_dialog_title).setView(viewInflate).create().show();
    }

    /* JADX INFO: renamed from: lambda$handleAddUserListButton$22$org-catrobat-catroid-ui-SpriteActivity, reason: not valid java name */
    /* synthetic */ void m5633xac2cd463(RadioButton radioButton, DialogInterface dialogInterface, String str) {
        boolean zIsChecked = radioButton.isChecked();
        UserList userList = new UserList(str);
        if (zIsChecked) {
            this.currentProject.addUserList(userList);
        } else {
            this.currentSprite.addUserList(userList);
        }
        if (getCurrentFragment() instanceof ListSelectorFragment) {
            ((ListSelectorFragment) getCurrentFragment()).notifyDataSetChanged();
        }
    }

    public void handlePlayButton(View view) {
        Fragment currentFragment = getCurrentFragment();
        if (currentFragment instanceof ScriptFragment) {
            ScriptFragment scriptFragment = (ScriptFragment) currentFragment;
            if (scriptFragment.isCurrentlyHighlighted()) {
                scriptFragment.cancelHighlighting();
            }
            if (scriptFragment.isCurrentlyMoving()) {
                ((ScriptFragment) getCurrentFragment()).highlightMovingItem();
                return;
            }
        }
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack((String) null, 1);
        }
        StageActivity.handlePlayButton(this.projectManager, this);
    }

    @Override // android.app.Activity
    public ActionMode startActionMode(ActionMode.Callback callback) {
        if (SpriteActivityOnTabSelectedListenerKt.isFragmentWithTablayout(getCurrentFragment())) {
            removeTabs();
        }
        return super.startActionMode(callback);
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public void onActionModeFinished(ActionMode mode) {
        Fragment currentFragment = getCurrentFragment();
        if (SpriteActivityOnTabSelectedListenerKt.isFragmentWithTablayout(currentFragment) && (!(currentFragment instanceof ScriptFragment) || !((ScriptFragment) currentFragment).isFinderOpen())) {
            addTabs();
        }
        super.onActionModeFinished(mode);
    }

    public void setCurrentSprite(Sprite sprite) {
        this.currentSprite = sprite;
    }

    public void setCurrentSceneAndSprite(Scene scene, Sprite sprite) {
        this.currentScene = scene;
        this.currentSprite = sprite;
    }

    public void removeTabs() {
        if (this.areTabsAdded) {
            SpriteActivityOnTabSelectedListenerKt.removeTabLayout(this);
            this.areTabsAdded = false;
        }
    }

    public void addTabs() {
        if (this.areTabsAdded) {
            return;
        }
        SpriteActivityOnTabSelectedListenerKt.addTabLayout(this, SpriteActivityOnTabSelectedListenerKt.getTabPositionInSpriteActivity(getCurrentFragment()));
        this.areTabsAdded = true;
    }
}
