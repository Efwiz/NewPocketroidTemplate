package org.catrobat.catroid.ui.recyclerview.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;
import androidx.lifecycle.LifecycleOwnerKt;
import com.google.android.gms.analytics.ecommerce.Promotion;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CancellationException;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import name.antonsmirnov.firmata.FormatHelper;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.ai.KoveAutocompleteController;
import org.catrobat.catroid.codeanalysis.AnalysisManager;
import org.catrobat.catroid.codeanalysis.AnalysisResult;
import org.catrobat.catroid.codeanalysis.CodeAnalyzer;
import org.catrobat.catroid.codeanalysis.GlobalAnalysisContext;
import org.catrobat.catroid.codeanalysis.Severity;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.ScreenValues;
import org.catrobat.catroid.content.BrickInfo;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.StartScript;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.content.bricks.CompositeBrick;
import org.catrobat.catroid.content.bricks.EmptyEventBrick;
import org.catrobat.catroid.content.bricks.FormulaBrick;
import org.catrobat.catroid.content.bricks.ScriptBrick;
import org.catrobat.catroid.content.bricks.UserDataBrick;
import org.catrobat.catroid.content.bricks.UserDefinedBrick;
import org.catrobat.catroid.content.bricks.UserDefinedReceiverBrick;
import org.catrobat.catroid.content.bricks.VisualPlacementBrick;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InternToExternGenerator;
import org.catrobat.catroid.formulaeditor.Sensors;
import org.catrobat.catroid.formulaeditor.UserData;
import org.catrobat.catroid.formulaeditor.UserList;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.io.StorageOperations;
import org.catrobat.catroid.io.XstreamSerializer;
import org.catrobat.catroid.io.asynctask.ProjectLoader;
import org.catrobat.catroid.io.asynctask.ProjectSaver;
import org.catrobat.catroid.ui.BottomBar;
import org.catrobat.catroid.ui.ScriptFinder;
import org.catrobat.catroid.ui.SpriteActivity;
import org.catrobat.catroid.ui.UiUtils;
import org.catrobat.catroid.ui.controller.BackpackListManager;
import org.catrobat.catroid.ui.controller.RecentBrickListManager;
import org.catrobat.catroid.ui.dialogs.RuntimeConsoleActivity;
import org.catrobat.catroid.ui.dragndrop.BrickListView;
import org.catrobat.catroid.ui.fragment.AddBrickFragment;
import org.catrobat.catroid.ui.fragment.BrickCategoryFragment;
import org.catrobat.catroid.ui.fragment.BrickSearchFragment;
import org.catrobat.catroid.ui.fragment.CategoryBricksFactory;
import org.catrobat.catroid.ui.fragment.UserDefinedBrickListFragment;
import org.catrobat.catroid.ui.recyclerview.adapter.BrickAdapter;
import org.catrobat.catroid.ui.recyclerview.backpack.BackpackActivity;
import org.catrobat.catroid.ui.recyclerview.backpack.BackpackScriptFragment;
import org.catrobat.catroid.ui.recyclerview.controller.BrickController;
import org.catrobat.catroid.ui.recyclerview.controller.ScriptController;
import org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog;
import org.catrobat.catroid.ui.recyclerview.dialog.textwatcher.DuplicateInputTextWatcher;
import org.catrobat.catroid.ui.recyclerview.util.UniqueNameProvider;
import org.catrobat.catroid.ui.settingsfragments.SettingsFragment;
import org.catrobat.catroid.ui.workspace.WorkspaceLayout;
import org.catrobat.catroid.utils.BlockClipboard;
import org.catrobat.catroid.utils.BrickCollapseManager;
import org.catrobat.catroid.utils.SnackbarUtil;
import org.catrobat.catroid.utils.ToastUtil;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/fragment/ScriptFragment.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ScriptFragment extends ListFragment implements ActionMode.Callback, BrickAdapter.OnBrickClickListener, BrickAdapter.SelectionListener, BrickCategoryFragment.OnCategorySelectedListener, AddBrickFragment.OnAddBrickListener, ProjectLoader.ProjectLoadListener, BrickAdapter.OnScriptChangedListener {
    private static final int BACKPACK = 1;
    private static final String BRICK_TAG = "brickToFocus";
    private static final int CATBLOCKS = 5;
    private static final int COMMENT = 4;
    private static final int COPY = 2;
    private static final boolean DEBUG_SPRITE_PRINTER = false;
    private static final int DELETE = 3;
    private static final int NONE = 0;
    private static final String SCRIPT_TAG = "scriptToFocus";
    public static final String TAG = "ScriptFragment";
    private ActionMode actionMode;
    private SpriteActivity activity;
    private BrickAdapter adapter;
    private View analysisStatusIndicator;
    private Brick brickToFocus;
    private CodeAnalyzer codeAnalyzer;
    private String currentSceneName;
    private Sprite currentSprite;
    private String currentSpriteName;
    private TextView errorCountText;
    private BrickListView listView;
    private Parcelable savedListViewState;
    private transient List<UserList> savedLocalLists;
    private transient List<UserVariable> savedLocalUserVariables;
    private List<UserVariable> savedMultiplayerVariables;
    private List<UserList> savedUserLists;
    private List<UserVariable> savedUserVariables;
    private ScriptFinder scriptFinder;
    private Script scriptToFocus;
    private int undoBrickPosition;
    private TextView warningCountText;
    private int actionModeType = 0;
    private ScriptController scriptController = new ScriptController();
    private BrickController brickController = new BrickController();
    private Job codeAnalysisJob = null;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/fragment/ScriptFragment$ActionModeType.class
      classes14.dex
     */
    @Retention(RetentionPolicy.SOURCE)
    @interface ActionModeType {
    }

    @Override // android.view.ActionMode.Callback
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    public static ScriptFragment newInstance(Brick brickToFocus) {
        ScriptFragment scriptFragment = new ScriptFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(BRICK_TAG, brickToFocus);
        scriptFragment.setArguments(bundle);
        return scriptFragment;
    }

    public static ScriptFragment newInstance(Script scriptToFocus) {
        ScriptFragment scriptFragment = new ScriptFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(SCRIPT_TAG, scriptToFocus);
        scriptFragment.setArguments(bundle);
        return scriptFragment;
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.brickToFocus = (Brick) arguments.get(BRICK_TAG);
            this.scriptToFocus = (Script) arguments.get(SCRIPT_TAG);
        }
        this.codeAnalyzer = new CodeAnalyzer(requireContext());
    }

    @Override // android.view.ActionMode.Callback
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        mode.getMenuInflater().inflate(R.menu.context_menu, menu);
        int i = this.actionModeType;
        if (i == 0) {
            this.adapter.setCheckBoxMode(0);
            this.actionMode.finish();
            return false;
        }
        if (i == 1) {
            this.adapter.setCheckBoxMode(2);
            mode.setTitle(getString(R.string.am_backpack));
        } else if (i == 2) {
            this.adapter.setCheckBoxMode(3);
            mode.setTitle(getString(R.string.am_copy));
        } else if (i == 3) {
            this.adapter.setCheckBoxMode(1);
            mode.setTitle(getString(R.string.am_delete));
        } else if (i == 4) {
            this.adapter.selectAllCommentedOutBricks();
            this.adapter.setCheckBoxMode(1);
            mode.setTitle(getString(R.string.comment_in_out));
        }
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
        BottomBar.showBottomBar(getActivity());
    }

    private void handleContextualAction() {
        if (this.adapter.isEmpty()) {
            this.actionMode.finish();
        }
        int i = this.actionModeType;
        if (i == 0) {
            throw new IllegalStateException("ActionModeType not set correctly");
        }
        if (i == 1) {
            showNewScriptGroupAlert(this.adapter.getSelectedItems());
            return;
        }
        if (i == 2) {
            copy(this.adapter.getSelectedItems());
        } else if (i == 3) {
            showDeleteAlert(this.adapter.getSelectedItems());
        } else {
            if (i != 4) {
                return;
            }
            toggleComments(this.adapter.getSelectedItems());
        }
    }

    private void resetActionModeParameters() {
        this.actionModeType = 0;
        this.actionMode = null;
        this.adapter.setCheckBoxMode(0);
    }

    @Override // androidx.fragment.app.ListFragment, androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int height;
        View viewInflate = View.inflate(getActivity(), R.layout.fragment_script, null);
        this.listView = (BrickListView) viewInflate.findViewById(android.R.id.list);
        if (ScreenValues.currentScreenResolution != null) {
            height = (int) (((double) ScreenValues.currentScreenResolution.getHeight()) / 2.5d);
        } else {
            height = ScreenValues.DEFAULT_SCREEN_RESOLUTION.getHeight();
        }
        View view = new View(getActivity());
        view.setLayoutParams(new AbsListView.LayoutParams(-1, height));
        this.listView.addFooterView(view, null, false);
        this.listView.setPadding(0, 0, 0, 0);
        this.listView.setClipToPadding(false);
        this.listView.setScrollBarStyle(0);
        this.listView.setOnScrollListener(new AbsListView.OnScrollListener() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.ScriptFragment.1
            @Override // android.widget.AbsListView.OnScrollListener
            public void onScroll(AbsListView view2, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }

            @Override // android.widget.AbsListView.OnScrollListener
            public void onScrollStateChanged(AbsListView view2, int scrollState) {
                if (scrollState == 1) {
                    KoveAutocompleteController.getInstance().dismissSuggestions();
                }
            }
        });
        if (getActivity() instanceof SpriteActivity) {
            this.activity = (SpriteActivity) getActivity();
        }
        SettingsFragment.setToChosenLanguage(getActivity());
        ScriptFinder scriptFinder = (ScriptFinder) viewInflate.findViewById(R.id.findview);
        this.scriptFinder = scriptFinder;
        scriptFinder.setOnResultFoundListener(new ScriptFinder.OnResultFoundListener() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.ScriptFragment$$ExternalSyntheticLambda11
            @Override // org.catrobat.catroid.ui.ScriptFinder.OnResultFoundListener
            public final void onResultFound(int i, int i2, int i3, int i4, TextView textView) {
                this.f$0.m5798xf9bcf822(i, i2, i3, i4, textView);
            }
        });
        this.scriptFinder.setOnCloseListener(new ScriptFinder.OnCloseListener() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.ScriptFragment$$ExternalSyntheticLambda12
            @Override // org.catrobat.catroid.ui.ScriptFinder.OnCloseListener
            public final void onClose() {
                this.f$0.m5799x8dfb67c1();
            }
        });
        this.scriptFinder.setOnOpenListener(new ScriptFinder.OnOpenListener() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.ScriptFragment$$ExternalSyntheticLambda1
            @Override // org.catrobat.catroid.ui.ScriptFinder.OnOpenListener
            public final void onOpen() {
                this.f$0.m5800x2239d760();
            }
        });
        this.analysisStatusIndicator = viewInflate.findViewById(R.id.analysis_status_indicator);
        this.errorCountText = (TextView) viewInflate.findViewById(R.id.error_count_text);
        this.warningCountText = (TextView) viewInflate.findViewById(R.id.warning_count_text);
        View view2 = this.analysisStatusIndicator;
        if (view2 != null) {
            view2.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.ScriptFragment$$ExternalSyntheticLambda2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view3) {
                    this.f$0.m5801xb67846ff(view3);
                }
            });
        }
        setHasOptionsMenu(true);
        return viewInflate;
    }

    /* JADX INFO: renamed from: lambda$onCreateView$0$org-catrobat-catroid-ui-recyclerview-fragment-ScriptFragment, reason: not valid java name */
    /* synthetic */ void m5798xf9bcf822(int i, int i2, int i3, int i4, TextView textView) {
        Project currentProject = ProjectManager.getInstance().getCurrentProject();
        Scene scene = currentProject.getSceneList().get(i);
        Sprite sprite = scene.getSpriteList().get(i2);
        textView.setText(createActionBarTitle(currentProject, scene, sprite));
        ProjectManager.getInstance().setCurrentSceneAndSprite(scene.getName(), sprite.getName());
        this.currentSprite = sprite;
        this.adapter.updateItems(sprite);
        this.adapter.notifyDataSetChanged();
        this.listView.smoothScrollToPosition(i3);
        highlightBrickAtIndex(i3);
        hideKeyboard();
        refreshFastScroll();
    }

    /* JADX INFO: renamed from: lambda$onCreateView$1$org-catrobat-catroid-ui-recyclerview-fragment-ScriptFragment, reason: not valid java name */
    /* synthetic */ void m5799x8dfb67c1() {
        this.listView.cancelHighlighting();
        finishActionMode();
        SpriteActivity spriteActivity = this.activity;
        if (spriteActivity == null || spriteActivity.isFinishing()) {
            return;
        }
        this.activity.setCurrentSceneAndSprite(ProjectManager.getInstance().getCurrentlyEditedScene(), ProjectManager.getInstance().getCurrentSprite());
        this.activity.getSupportActionBar().setTitle(this.activity.createActionBarTitle());
        this.activity.addTabs();
        this.activity.findViewById(R.id.toolbar).setVisibility(0);
    }

    /* JADX INFO: renamed from: lambda$onCreateView$2$org-catrobat-catroid-ui-recyclerview-fragment-ScriptFragment, reason: not valid java name */
    /* synthetic */ void m5800x2239d760() {
        SpriteActivity spriteActivity = this.activity;
        if (spriteActivity != null) {
            spriteActivity.removeTabs();
            this.activity.findViewById(R.id.toolbar).setVisibility(8);
        }
    }

    /* JADX INFO: renamed from: lambda$onCreateView$3$org-catrobat-catroid-ui-recyclerview-fragment-ScriptFragment, reason: not valid java name */
    /* synthetic */ void m5801xb67846ff(View view) {
        showAnalysisSummaryDialog();
    }

    public String createActionBarTitle(Project currentProject, Scene currentScene, Sprite currentSprite) {
        if (currentProject.getSceneList().size() == 1) {
            return currentSprite.getName();
        }
        return currentScene.getName() + ": " + currentSprite.getName();
    }

    private void highlightBrickAtIndex(int index) {
        this.listView.getBrickPositionsToHighlight().clear();
        this.listView.getBrickPositionsToHighlight().add(Integer.valueOf(index));
    }

    private void hideKeyboard() {
        ((InputMethodManager) getContext().getSystemService("input_method")).hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }

    private static void clearBrickViewsReflection(Brick brick) {
        if (brick == null) {
            return;
        }
        for (Class<?> superclass = brick.getClass(); superclass != null && superclass != Object.class; superclass = superclass.getSuperclass()) {
            for (Field field : superclass.getDeclaredFields()) {
                try {
                    if (View.class.isAssignableFrom(field.getType())) {
                        field.setAccessible(true);
                        field.set(brick, null);
                    }
                } catch (Exception unused) {
                }
            }
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        Job job = this.codeAnalysisJob;
        if (job != null) {
            job.cancel((CancellationException) null);
            this.codeAnalysisJob = null;
        }
        Sprite sprite = this.currentSprite;
        if (sprite == null || sprite.getScriptList() == null) {
            return;
        }
        Iterator<Script> it = this.currentSprite.getScriptList().iterator();
        while (it.hasNext()) {
            List<Brick> bricksFromScript = getBricksFromScript(it.next());
            if (bricksFromScript != null) {
                Iterator<Brick> it2 = bricksFromScript.iterator();
                while (it2.hasNext()) {
                    clearBrickViewsReflection(it2.next());
                }
            }
        }
    }

    @Override // androidx.fragment.app.ListFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        SpriteActivity spriteActivity;
        super.onDestroyView();
        if (this.scriptFinder.isOpen() && (spriteActivity = this.activity) != null) {
            spriteActivity.findViewById(R.id.toolbar).setVisibility(0);
        }
        BrickAdapter brickAdapter = this.adapter;
        if (brickAdapter != null) {
            brickAdapter.setSelectionListener(null);
            this.adapter.setOnItemClickListener(null);
            this.adapter.setOnScriptChangedListener(null);
            this.adapter = null;
        }
        this.listView = null;
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Project currentProject = ProjectManager.getInstance().getCurrentProject();
        Scene currentlyEditedScene = ProjectManager.getInstance().getCurrentlyEditedScene();
        this.currentSprite = ProjectManager.getInstance().getCurrentSprite();
        currentProject.getBroadcastMessageContainer().update();
        BrickAdapter brickAdapter = new BrickAdapter(this.currentSprite);
        this.adapter = brickAdapter;
        brickAdapter.setSelectionListener(this);
        this.adapter.setOnItemClickListener(this);
        this.adapter.setOnScriptChangedListener(this);
        this.listView.setAdapter((ListAdapter) this.adapter);
        this.listView.setOnItemClickListener(this.adapter);
        this.listView.setOnItemLongClickListener(this.adapter);
        if (this.currentSprite.equals(currentlyEditedScene.getBackgroundSprite())) {
            InternToExternGenerator.setInternExternLanguageConverterMap(Sensors.OBJECT_NUMBER_OF_LOOKS, Integer.valueOf(R.string.formula_editor_object_number_of_backgrounds));
        } else {
            InternToExternGenerator.setInternExternLanguageConverterMap(Sensors.OBJECT_NUMBER_OF_LOOKS, Integer.valueOf(R.string.formula_editor_object_number_of_looks));
        }
    }

    @Override // org.catrobat.catroid.ui.recyclerview.adapter.BrickAdapter.OnScriptChangedListener
    public void onScriptChanged() {
        runCodeAnalysis();
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        BrickListView brickListView;
        BrickAdapter brickAdapter;
        super.onResume();
        Project currentProject = ProjectManager.getInstance().getCurrentProject();
        Scene currentlyEditedScene = ProjectManager.getInstance().getCurrentlyEditedScene();
        Sprite currentSprite = ProjectManager.getInstance().getCurrentSprite();
        if (currentSprite != null) {
            this.currentSprite = currentSprite;
        }
        ActionBar supportActionBar = getActivity() instanceof AppCompatActivity ? ((AppCompatActivity) getActivity()).getSupportActionBar() : null;
        if (supportActionBar != null && currentProject != null && currentlyEditedScene != null && currentSprite != null) {
            if (currentProject.getSceneList().size() > 1) {
                supportActionBar.setTitle(currentlyEditedScene.getName() + ": " + currentSprite.getName());
            } else {
                supportActionBar.setTitle(currentSprite.getName());
            }
        }
        if (BackpackListManager.getInstance().isBackpackEmpty()) {
            BackpackListManager.getInstance().loadBackpack();
        }
        BottomBar.showBottomBar(getActivity());
        BottomBar.showPlayButton(getActivity());
        BottomBar.showAddButton(getActivity());
        BottomBar.showAiAssistButton(getActivity());
        if (currentSprite != null && (brickAdapter = this.adapter) != null) {
            brickAdapter.updateItems(currentSprite);
        }
        Parcelable parcelable = this.savedListViewState;
        if (parcelable != null && (brickListView = this.listView) != null) {
            brickListView.onRestoreInstanceState(parcelable);
        }
        scrollToFocusItem();
        SnackbarUtil.showHintSnackbar(getActivity(), R.string.hint_scripts);
        runCodeAnalysis();
        refreshFastScroll();
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        Job job = this.codeAnalysisJob;
        if (job != null) {
            job.cancel((CancellationException) null);
            this.codeAnalysisJob = null;
        }
        KoveAutocompleteController.getInstance().cancelActiveJob();
        KoveAutocompleteController.getInstance().dismissSuggestions();
        try {
            if (!(getActivity() instanceof RuntimeConsoleActivity)) {
                new ProjectSaver(ProjectManager.getInstance().getCurrentProject(), getContext()).saveProjectAsync();
            }
        } catch (Exception e) {
            Log.e(TAG, "'onPause' Critical Error");
            e.printStackTrace();
        }
        this.savedListViewState = this.listView.onSaveInstanceState();
        if (getActivity() instanceof SpriteActivity) {
            ((SpriteActivity) getActivity()).setUndoMenuItemVisibility(false);
        }
        AnalysisManager.INSTANCE.clearResults();
    }

    private void runCodeAnalysis() {
        final ArrayList arrayList;
        boolean z = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean(SettingsFragment.SETTINGS_CODE_ANALIZE, true);
        Job job = this.codeAnalysisJob;
        if (job != null) {
            job.cancel((CancellationException) null);
            this.codeAnalysisJob = null;
        }
        AnalysisManager.INSTANCE.clearResults();
        if (!z) {
            BrickAdapter brickAdapter = this.adapter;
            if (brickAdapter != null) {
                brickAdapter.notifyDataSetChanged();
                return;
            }
            return;
        }
        Sprite currentSprite = ProjectManager.getInstance().getCurrentSprite();
        if (currentSprite == null) {
            return;
        }
        synchronized (currentSprite) {
            arrayList = new ArrayList(currentSprite.getScriptList());
        }
        this.codeAnalysisJob = BuildersKt.launch(LifecycleOwnerKt.getLifecycleScope(this), Dispatchers.getIO(), CoroutineStart.DEFAULT, new Function2() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.ScriptFragment$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return this.f$0.m5804x4383ca83(arrayList, (CoroutineScope) obj, (Continuation) obj2);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$runCodeAnalysis$5$org-catrobat-catroid-ui-recyclerview-fragment-ScriptFragment, reason: not valid java name */
    /* synthetic */ Object m5804x4383ca83(List list, CoroutineScope coroutineScope, Continuation continuation) {
        Map<Brick, AnalysisResult> mapAnalyzeScript;
        HashMap map = new HashMap();
        try {
            GlobalAnalysisContext globalAnalysisContextBuild = GlobalAnalysisContext.INSTANCE.build();
            int size = list.size();
            for (int i = 0; i < size && i < list.size(); i++) {
                Script script = (Script) list.get(i);
                if (script != null && (mapAnalyzeScript = this.codeAnalyzer.analyzeScript(script, globalAnalysisContextBuild)) != null) {
                    map.putAll(mapAnalyzeScript);
                }
            }
            AnalysisManager.INSTANCE.updateResults(map);
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.ScriptFragment$$ExternalSyntheticLambda9
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m5803xaf455ae4();
                    }
                });
            }
        } catch (Throwable th) {
            Log.e(TAG, "Error in analysis background job", th);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: lambda$runCodeAnalysis$4$org-catrobat-catroid-ui-recyclerview-fragment-ScriptFragment, reason: not valid java name */
    /* synthetic */ void m5803xaf455ae4() {
        if (ProjectManager.getInstance().getCurrentProject() == null) {
            Log.d(TAG, "Project already closed, skipping UI update");
            return;
        }
        BrickAdapter brickAdapter = this.adapter;
        if (brickAdapter != null) {
            brickAdapter.notifyDataSetChanged();
        }
        updateAnalysisIndicator();
    }

    @Override // androidx.fragment.app.Fragment
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem menuItemFindItem = menu.findItem(R.id.show_details);
        if (menuItemFindItem != null) {
            menuItemFindItem.setVisible(false);
        }
        MenuItem menuItemFindItem2 = menu.findItem(R.id.rename);
        if (menuItemFindItem2 != null) {
            menuItemFindItem2.setVisible(false);
        }
        MenuItem menuItemFindItem3 = menu.findItem(R.id.catblocks_reorder_scripts);
        if (menuItemFindItem3 != null) {
            menuItemFindItem3.setVisible(false);
        }
        MenuItem menuItemFindItem4 = menu.findItem(R.id.find);
        if (menuItemFindItem4 != null) {
            menuItemFindItem4.setVisible(true);
        }
        MenuItem menuItemFindItem5 = menu.findItem(R.id.menu_toggle_indentation);
        if (menuItemFindItem5 != null) {
            menuItemFindItem5.setVisible(true);
            menuItemFindItem5.setTitle(PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("pref_enable_brick_indentation", false) ? R.string.menu_disable_indentation : R.string.menu_enable_indentation);
        }
        super.onPrepareOptionsMenu(menu);
    }

    @Override // androidx.fragment.app.Fragment
    public boolean onOptionsItemSelected(MenuItem item) {
        if (this.listView.isCurrentlyMoving()) {
            this.listView.highlightMovingItem();
            return true;
        }
        if (this.listView.isCurrentlyHighlighted()) {
            this.listView.cancelHighlighting();
        }
        switch (item.getItemId()) {
            case R.id.backpack /* 2131361924 */:
                prepareBackpackActionMode();
                return true;
            case R.id.comment_in_out /* 2131364122 */:
                startActionMode(4);
                return true;
            case R.id.copy /* 2131364141 */:
                prepareActionMode(2);
                return true;
            case R.id.delete /* 2131364226 */:
                prepareActionMode(3);
                return true;
            case R.id.find /* 2131364423 */:
                this.scriptFinder.open();
                return true;
            case R.id.menu_toggle_indentation /* 2131364660 */:
                SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                defaultSharedPreferences.edit().putBoolean("pref_enable_brick_indentation", !defaultSharedPreferences.getBoolean("pref_enable_brick_indentation", true)).apply();
                this.adapter.notifyDataSetChanged();
                getActivity().invalidateOptionsMenu();
                return true;
            case R.id.menu_undo /* 2131364662 */:
                loadProjectAfterUndoOption();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void notifyDataSetChanged() {
        BrickAdapter brickAdapter = this.adapter;
        if (brickAdapter != null) {
            brickAdapter.notifyDataSetChanged();
        }
    }

    public boolean isCurrentlyMoving() {
        BrickListView brickListView = this.listView;
        if (brickListView != null) {
            return brickListView.isCurrentlyMoving();
        }
        return false;
    }

    public void highlightMovingItem() {
        this.listView.highlightMovingItem();
    }

    public void cancelMove() {
        this.listView.cancelMove();
        this.adapter.updateItems(ProjectManager.getInstance().getCurrentSprite());
    }

    public boolean isCurrentlyHighlighted() {
        return this.listView.isCurrentlyHighlighted();
    }

    public void cancelHighlighting() {
        this.listView.cancelHighlighting();
    }

    private void showCategoryFragment() {
        BrickCategoryFragment brickCategoryFragment = new BrickCategoryFragment();
        brickCategoryFragment.setOnCategorySelectedListener(this);
        getFragmentManager().beginTransaction().add(R.id.fragment_container, brickCategoryFragment, BrickCategoryFragment.BRICK_CATEGORY_FRAGMENT_TAG).addToBackStack(BrickCategoryFragment.BRICK_CATEGORY_FRAGMENT_TAG).commit();
    }

    @Override // org.catrobat.catroid.ui.fragment.BrickCategoryFragment.OnCategorySelectedListener
    public void onCategorySelected(String category) {
        Fragment fragmentNewInstance;
        String str;
        Fragment fragmentFindFragmentById = getParentFragmentManager().findFragmentById(R.id.fragment_container);
        if (category.equals(getContext().getString(R.string.category_user_bricks))) {
            fragmentNewInstance = UserDefinedBrickListFragment.newInstance(this);
            str = UserDefinedBrickListFragment.USER_DEFINED_BRICK_LIST_FRAGMENT_TAG;
        } else if ((fragmentFindFragmentById instanceof AddBrickFragment) || category.equals(getContext().getString(R.string.category_search_bricks))) {
            fragmentNewInstance = BrickSearchFragment.newInstance(this, category);
            str = BrickSearchFragment.BRICK_SEARCH_FRAGMENT_TAG;
        } else {
            fragmentNewInstance = AddBrickFragment.newInstance(category, this);
            str = AddBrickFragment.ADD_BRICK_FRAGMENT_TAG;
        }
        getFragmentManager().beginTransaction().add(R.id.fragment_container, fragmentNewInstance, str).addToBackStack(null).commit();
    }

    protected void prepareActionMode(int type) {
        if (this.adapter.getCount() != 1) {
            startActionMode(type);
            return;
        }
        if (type == 2) {
            copy(this.adapter.getItems());
        } else if (type == 3) {
            delete(this.adapter.getItems());
        } else {
            startActionMode(type);
        }
    }

    private void prepareBackpackActionMode() {
        if (this.adapter.getItems().size() == 1) {
            showNewScriptGroupAlert(this.adapter.getItems());
            return;
        }
        if (BackpackListManager.getInstance().getBackpackedScriptGroups().isEmpty()) {
            startActionMode(1);
        } else if (this.adapter.isEmpty()) {
            switchToBackpack();
        } else {
            showBackpackModeChooser();
        }
    }

    private void startActionMode(int type) {
        if (this.adapter.isEmpty()) {
            ToastUtil.showError(getActivity(), R.string.am_empty_list);
            return;
        }
        this.actionModeType = type;
        this.actionMode = getActivity().startActionMode(this);
        BottomBar.hideBottomBar(getActivity());
    }

    @Override // org.catrobat.catroid.ui.recyclerview.adapter.BrickAdapter.SelectionListener
    public void onSelectionChanged(int selectedItemCnt) {
        int i = this.actionModeType;
        if (i == 0) {
            throw new IllegalStateException("ActionModeType not set Correctly");
        }
        if (i == 1) {
            this.actionMode.setTitle(getString(R.string.am_backpack) + " " + selectedItemCnt);
            return;
        }
        if (i == 2) {
            this.actionMode.setTitle(getString(R.string.am_copy) + " " + selectedItemCnt);
        } else if (i == 3) {
            this.actionMode.setTitle(getString(R.string.am_delete) + " " + selectedItemCnt);
        } else {
            if (i != 4) {
                return;
            }
            this.actionMode.setTitle(getString(R.string.comment_in_out) + " " + selectedItemCnt);
        }
    }

    public void finishActionMode() {
        this.adapter.clearSelection();
        if (this.actionModeType != 0) {
            this.actionMode.finish();
        }
    }

    public Brick findBrickByHash(int hashCode) {
        return this.adapter.findByHash(hashCode);
    }

    public void handleAddButton() {
        if (this.listView.isCurrentlyHighlighted()) {
            this.listView.cancelHighlighting();
        }
        if (this.listView.isCurrentlyMoving()) {
            this.listView.highlightMovingItem();
            return;
        }
        if (getActivity() instanceof SpriteActivity) {
            ((SpriteActivity) getActivity()).setUndoMenuItemVisibility(false);
        }
        showCategoryFragment();
    }

    @Override // org.catrobat.catroid.ui.fragment.AddBrickFragment.OnAddBrickListener
    public void addBrick(Brick brick) {
        KoveAutocompleteController.getInstance().cancelActiveJob();
        KoveAutocompleteController.getInstance().dismissSuggestions();
        try {
            if (!brick.getClass().equals(UserDefinedReceiverBrick.class) && !brick.getClass().equals(UserDefinedBrick.class)) {
                RecentBrickListManager.getInstance().addBrick(brick.clone());
            }
        } catch (CloneNotSupportedException e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }
        addBrick(brick, ProjectManager.getInstance().getCurrentSprite(), this.adapter, this.listView);
    }

    public void addBrick(Brick brick, Sprite sprite, BrickAdapter brickAdapter, BrickListView brickListView) {
        if (brickAdapter.getCount() == 0) {
            if (brick instanceof ScriptBrick) {
                sprite.addScript(brick.getScript());
            } else {
                StartScript startScript = new StartScript();
                startScript.addBrick(brick);
                sprite.addScript(startScript);
            }
            brickAdapter.updateItems(sprite);
            return;
        }
        if (brickAdapter.getCount() == 1 && !(brick instanceof ScriptBrick)) {
            sprite.getScriptList().get(0).addBrick(brick);
            brickAdapter.updateItems(sprite);
        } else {
            int firstVisiblePosition = brickListView.getFirstVisiblePosition();
            brickAdapter.addItem((((brickListView.getLastVisiblePosition() + 1) - firstVisiblePosition) / 2) + firstVisiblePosition, brick);
            brickListView.startMoving(brick);
        }
    }

    @Override // org.catrobat.catroid.ui.recyclerview.adapter.BrickAdapter.OnBrickClickListener
    public void onBrickClick(final Brick brick, final int position) {
        if (this.listView.isCurrentlyHighlighted()) {
            this.listView.cancelHighlighting();
            return;
        }
        KoveAutocompleteController.getInstance().onUserActivity(ProjectManager.getInstance().getCurrentSprite(), brick.getScript(), brick.getPositionInScript(), this.adapter);
        final List<Integer> contextMenuItems = getContextMenuItems(brick);
        ArrayList arrayList = new ArrayList();
        Iterator<Integer> it = contextMenuItems.iterator();
        while (it.hasNext()) {
            arrayList.add(getString(it.next().intValue()));
        }
        ArrayAdapter alertDialogAdapterForMenuIcons = UiUtils.getAlertDialogAdapterForMenuIcons(contextMenuItems, arrayList, requireContext(), requireActivity());
        View viewInflate = LayoutInflater.from(getContext()).inflate(R.layout.dialog_brick_context, (ViewGroup) null);
        View view = brick.getView(getContext());
        brick.disableSpinners();
        ViewGroup viewGroup = (ViewGroup) viewInflate.findViewById(R.id.brick_view_container);
        final int i = (int) (getContext().getResources().getDisplayMetrics().density * 200.0f);
        ScrollView scrollView = new ScrollView(getContext()) { // from class: org.catrobat.catroid.ui.recyclerview.fragment.ScriptFragment.2
            @Override // android.widget.ScrollView, android.widget.FrameLayout, android.view.View
            protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
                int mode = View.MeasureSpec.getMode(heightMeasureSpec);
                int size = View.MeasureSpec.getSize(heightMeasureSpec);
                int i2 = i;
                if (size > i2 || mode == 0) {
                    heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(i2, Integer.MIN_VALUE);
                }
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            }
        };
        scrollView.addView(view);
        viewGroup.addView(scrollView);
        ((TextView) viewInflate.findViewById(R.id.brick_description)).setText(BrickInfo.getDescription(brick));
        AnalysisResult resultFor = AnalysisManager.INSTANCE.getResultFor(brick);
        LinearLayout linearLayout = (LinearLayout) viewInflate.findViewById(R.id.warning_layout);
        if (resultFor != null) {
            TextView textView = (TextView) viewInflate.findViewById(R.id.warning_title);
            TextView textView2 = (TextView) viewInflate.findViewById(R.id.warning_message);
            String str = resultFor.getSeverity() == Severity.ERROR ? "Ошибка:" : "Предупреждение:";
            int i2 = resultFor.getSeverity() == Severity.ERROR ? -48060 : -17613;
            textView.setText(str);
            textView.setTextColor(i2);
            textView2.setText(resultFor.getMessage());
            linearLayout.setVisibility(0);
        } else {
            linearLayout.setVisibility(8);
        }
        new AlertDialog.Builder(getContext()).setCustomTitle(viewInflate).setAdapter(alertDialogAdapterForMenuIcons, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.ScriptFragment$$ExternalSyntheticLambda7
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i3) {
                this.f$0.m5797xc2b33116(contextMenuItems, brick, position, dialogInterface, i3);
            }
        }).show();
    }

    /* JADX INFO: renamed from: lambda$onBrickClick$6$org-catrobat-catroid-ui-recyclerview-fragment-ScriptFragment, reason: not valid java name */
    /* synthetic */ void m5797xc2b33116(List list, Brick brick, int i, DialogInterface dialogInterface, int i2) {
        handleContextMenuItemClick(((Integer) list.get(i2)).intValue(), brick, i);
    }

    public static List<Integer> getContextMenuItems(Brick brick) {
        ArrayList arrayList = new ArrayList();
        if (brick instanceof CompositeBrick) {
            if (BrickCollapseManager.INSTANCE.isCollapsed(brick)) {
                arrayList.add(Integer.valueOf(R.string.brick_context_dialog_expand));
            } else {
                arrayList.add(Integer.valueOf(R.string.brick_context_dialog_collapse));
            }
        }
        if (brick instanceof UserDefinedReceiverBrick) {
            arrayList.add(Integer.valueOf(R.string.backpack_add));
            arrayList.add(Integer.valueOf(R.string.brick_context_dialog_delete_definition));
            arrayList.add(Integer.valueOf(R.string.brick_context_dialog_move_definition));
            arrayList.add(Integer.valueOf(R.string.brick_context_dialog_help));
            return arrayList;
        }
        arrayList.add(Integer.valueOf(R.string.brick_context_dialog_cut));
        if (BlockClipboard.getInstance().getLatest() != null) {
            arrayList.add(Integer.valueOf(R.string.brick_context_dialog_paste_below));
            arrayList.add(Integer.valueOf(R.string.brick_context_dialog_clipboard_history));
        }
        if (brick instanceof ScriptBrick) {
            arrayList.add(Integer.valueOf(R.string.backpack_add));
            if (!(brick instanceof EmptyEventBrick)) {
                arrayList.add(Integer.valueOf(brick.isCommentedOut() ? R.string.brick_context_dialog_comment_in_script : R.string.brick_context_dialog_comment_out_script));
            }
            arrayList.add(Integer.valueOf(R.string.brick_context_dialog_copy_script));
            arrayList.add(Integer.valueOf(R.string.brick_context_dialog_delete_script));
            if ((brick instanceof FormulaBrick) && ((FormulaBrick) brick).hasEditableFormulaField()) {
                arrayList.add(Integer.valueOf(R.string.brick_context_dialog_formula_edit_brick));
            }
            arrayList.add(Integer.valueOf(R.string.brick_context_dialog_move_script));
            arrayList.add(Integer.valueOf(R.string.brick_context_dialog_help));
        } else {
            arrayList.add(Integer.valueOf(R.string.brick_context_dialog_copy_brick));
            if (brick.consistsOfMultipleParts()) {
                arrayList.add(Integer.valueOf(R.string.brick_context_dialog_highlight_brick_parts));
            }
            arrayList.add(Integer.valueOf(R.string.brick_context_dialog_delete_brick));
            arrayList.add(Integer.valueOf(brick.isCommentedOut() ? R.string.brick_context_dialog_comment_in : R.string.brick_context_dialog_comment_out));
            if ((brick instanceof VisualPlacementBrick) && ((VisualPlacementBrick) brick).areAllBrickFieldsNumbers()) {
                arrayList.add(Integer.valueOf(R.string.brick_option_place_visually));
            }
            if ((brick instanceof FormulaBrick) && ((FormulaBrick) brick).hasEditableFormulaField()) {
                arrayList.add(Integer.valueOf(R.string.brick_context_dialog_formula_edit_brick));
            }
            if (brick.equals(brick.getAllParts().get(0))) {
                arrayList.add(Integer.valueOf(R.string.brick_context_dialog_move_brick));
            }
            if (brick.hasHelpPage()) {
                arrayList.add(Integer.valueOf(R.string.brick_context_dialog_help));
            }
        }
        arrayList.add(Integer.valueOf(R.string.brick_context_dialog_system_info));
        return arrayList;
    }

    private void handleContextMenuItemClick(int itemId, Brick brick, int position) {
        showUndo(false);
        if (itemId == R.string.backpack_add) {
            ArrayList arrayList = new ArrayList();
            brick.addToFlatList(arrayList);
            showNewScriptGroupAlert(arrayList);
        }
        if (itemId == R.string.brick_context_dialog_system_info) {
            showDetailedSystemInfoDialog(brick);
            return;
        }
        if (itemId == R.string.brick_option_place_visually) {
            VisualPlacementBrick visualPlacementBrick = (VisualPlacementBrick) brick;
            visualPlacementBrick.placeVisually(visualPlacementBrick.getXBrickField(), visualPlacementBrick.getYBrickField());
            return;
        }
        switch (itemId) {
            case R.string.brick_context_dialog_clipboard_history /* 2131886405 */:
                showClipboardHistoryDialog(brick);
                break;
            case R.string.brick_context_dialog_collapse /* 2131886406 */:
            case R.string.brick_context_dialog_expand /* 2131886417 */:
                BrickCollapseManager.INSTANCE.toggleCollapsed(brick);
                this.adapter.updateItems(ProjectManager.getInstance().getCurrentSprite());
                break;
            case R.string.brick_context_dialog_comment_in /* 2131886407 */:
            case R.string.brick_context_dialog_comment_in_script /* 2131886408 */:
                Iterator<Brick> it = brick.getAllParts().iterator();
                while (it.hasNext()) {
                    it.next().setCommentedOut(false);
                }
                this.adapter.notifyDataSetChanged();
                break;
            case R.string.brick_context_dialog_comment_out /* 2131886409 */:
            case R.string.brick_context_dialog_comment_out_script /* 2131886410 */:
                Iterator<Brick> it2 = brick.getAllParts().iterator();
                while (it2.hasNext()) {
                    it2.next().setCommentedOut(true);
                }
                this.adapter.notifyDataSetChanged();
                break;
            case R.string.brick_context_dialog_copy_brick /* 2131886411 */:
            case R.string.brick_context_dialog_copy_script /* 2131886412 */:
                try {
                    Brick brickClone = brick.getAllParts().get(0).clone();
                    this.adapter.addItem(position, brickClone);
                    this.listView.startMoving(brickClone);
                } catch (CloneNotSupportedException e) {
                    ToastUtil.showError(getContext(), R.string.error_copying_brick);
                    Log.e(TAG, Log.getStackTraceString(e));
                    return;
                }
                break;
            case R.string.brick_context_dialog_cut /* 2131886413 */:
                if (copyProjectForUndoOption()) {
                    showUndo(true);
                    setUndoBrickPosition(brick);
                }
                BlockClipboard.getInstance().copy(brick.getAllParts());
                Sprite currentSprite = ProjectManager.getInstance().getCurrentSprite();
                this.brickController.delete(brick.getAllParts(), currentSprite);
                this.adapter.updateItems(currentSprite);
                break;
            case R.string.brick_context_dialog_delete_brick /* 2131886414 */:
            case R.string.brick_context_dialog_delete_script /* 2131886416 */:
                showDeleteAlert(brick.getAllParts());
                break;
            case R.string.brick_context_dialog_delete_definition /* 2131886415 */:
                showDeleteAlert(brick.getAllParts());
                break;
            case R.string.brick_context_dialog_formula_edit_brick /* 2131886418 */:
                ((FormulaBrick) brick).onClick(this.listView);
                break;
            case R.string.brick_context_dialog_help /* 2131886419 */:
                openWebViewWithHelpPage(brick);
                break;
            case R.string.brick_context_dialog_highlight_brick_parts /* 2131886420 */:
                List<Brick> allParts = brick.getAllParts();
                ArrayList arrayList2 = new ArrayList();
                Iterator<Brick> it3 = allParts.iterator();
                while (it3.hasNext()) {
                    arrayList2.add(Integer.valueOf(this.adapter.getPosition(it3.next())));
                }
                this.listView.highlightControlStructureBricks(arrayList2);
                break;
            case R.string.brick_context_dialog_move_brick /* 2131886421 */:
            case R.string.brick_context_dialog_move_definition /* 2131886422 */:
            case R.string.brick_context_dialog_move_script /* 2131886423 */:
                onBrickLongClick(brick, position);
                break;
            case R.string.brick_context_dialog_paste_below /* 2131886424 */:
                List<Brick> latest = BlockClipboard.getInstance().getLatest();
                if (latest != null) {
                    pasteBricksBelow(latest, brick);
                }
                break;
        }
    }

    private void pasteBricksBelow(List<Brick> originalBricks, Brick targetBrick) {
        if (originalBricks == null || originalBricks.isEmpty()) {
            return;
        }
        Sprite currentSprite = ProjectManager.getInstance().getCurrentSprite();
        ArrayList<Brick> arrayList = new ArrayList();
        Iterator<Brick> it = originalBricks.iterator();
        while (it.hasNext()) {
            try {
                arrayList.add(it.next().clone());
            } catch (CloneNotSupportedException e) {
                Log.e(TAG, "Ошибка клонирования при вставке", e);
            }
        }
        if (arrayList.isEmpty()) {
            return;
        }
        if (copyProjectForUndoOption()) {
            showUndo(true);
        }
        if (arrayList.get(0) instanceof ScriptBrick) {
            for (Brick brick : arrayList) {
                if (brick instanceof ScriptBrick) {
                    currentSprite.addScript(((ScriptBrick) brick).getScript());
                }
            }
        } else {
            List<Brick> dragAndDropTargetList = targetBrick.getDragAndDropTargetList();
            int positionInDragAndDropTargetList = targetBrick.getPositionInDragAndDropTargetList() + 1;
            for (int i = 0; i < arrayList.size(); i++) {
                Brick brick2 = (Brick) arrayList.get(i);
                brick2.setParent(targetBrick.getParent());
                int i2 = positionInDragAndDropTargetList + i;
                if (i2 <= dragAndDropTargetList.size()) {
                    dragAndDropTargetList.add(i2, brick2);
                } else {
                    dragAndDropTargetList.add(brick2);
                }
            }
        }
        this.adapter.updateItems(currentSprite);
    }

    private void showClipboardHistoryDialog(final Brick targetBrick) {
        final List<List<Brick>> history = BlockClipboard.getInstance().getHistory();
        if (history.isEmpty()) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        int i = 0;
        while (i < history.size()) {
            List<Brick> list = history.get(i);
            StringBuilder sb = new StringBuilder();
            i++;
            sb.append(i).append(". ");
            if (list.size() == 1) {
                sb.append(list.get(0).getClass().getSimpleName());
            } else {
                sb.append("Group (").append(list.size()).append(" bricks): ");
                for (int i2 = 0; i2 < Math.min(3, list.size()); i2++) {
                    if (i2 > 0) {
                        sb.append(", ");
                    }
                    sb.append(list.get(i2).getClass().getSimpleName());
                }
                if (list.size() > 3) {
                    sb.append("...");
                }
            }
            arrayList.add(sb.toString());
        }
        new AlertDialog.Builder(getContext()).setTitle(getContext().getString(R.string.brick_context_dialog_clipboard_history)).setItems((CharSequence[]) arrayList.toArray(new CharSequence[0]), new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.ScriptFragment$$ExternalSyntheticLambda8
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i3) {
                this.f$0.m5807xe2f67c78(history, targetBrick, dialogInterface, i3);
            }
        }).setNegativeButton(getContext().getString(R.string.cancel), (DialogInterface.OnClickListener) null).show();
    }

    /* JADX INFO: renamed from: lambda$showClipboardHistoryDialog$7$org-catrobat-catroid-ui-recyclerview-fragment-ScriptFragment, reason: not valid java name */
    /* synthetic */ void m5807xe2f67c78(List list, Brick brick, DialogInterface dialogInterface, int i) {
        pasteBricksBelow((List) list.get(i), brick);
    }

    private void openWebViewWithHelpPage(Brick brick) {
        startActivity(new Intent("android.intent.action.VIEW", Uri.parse(brick.getHelpUrl(new CategoryBricksFactory().getBrickCategory(brick, ProjectManager.getInstance().getCurrentSprite() == ProjectManager.getInstance().getCurrentlyEditedScene().getBackgroundSprite(), getContext())))));
    }

    @Override // org.catrobat.catroid.ui.recyclerview.adapter.BrickAdapter.OnBrickClickListener
    public boolean onBrickLongClick(Brick brick, int position) {
        showUndo(false);
        if (this.listView.isCurrentlyHighlighted()) {
            this.listView.cancelHighlighting();
            return true;
        }
        this.listView.startMoving(brick);
        return true;
    }

    private void showBackpackModeChooser() {
        new AlertDialog.Builder(getContext()).setTitle(R.string.backpack_title).setItems(new CharSequence[]{getString(R.string.pack), getString(R.string.unpack)}, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.ScriptFragment$$ExternalSyntheticLambda10
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.m5806x60893d59(dialogInterface, i);
            }
        }).show();
    }

    /* JADX INFO: renamed from: lambda$showBackpackModeChooser$8$org-catrobat-catroid-ui-recyclerview-fragment-ScriptFragment, reason: not valid java name */
    /* synthetic */ void m5806x60893d59(DialogInterface dialogInterface, int i) {
        if (i == 0) {
            startActionMode(1);
        } else {
            if (i != 1) {
                return;
            }
            switchToBackpack();
        }
    }

    public void showNewScriptGroupAlert(final List<Brick> selectedBricks) {
        TextInputDialog.Builder builder = new TextInputDialog.Builder(getContext());
        DuplicateInputTextWatcher duplicateInputTextWatcher = new DuplicateInputTextWatcher(null);
        duplicateInputTextWatcher.setScope(BackpackListManager.getInstance().getBackpackedScriptGroups());
        builder.setText(new UniqueNameProvider().getUniqueName(getString(R.string.default_script_group_name), BackpackListManager.getInstance().getBackpackedScriptGroups()));
        builder.setHint(getString(R.string.script_group_label)).setTextWatcher(duplicateInputTextWatcher).setPositiveButton(getString(R.string.ok), new TextInputDialog.OnClickListener() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.ScriptFragment$$ExternalSyntheticLambda5
            @Override // org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog.OnClickListener
            public final void onPositiveButtonClick(DialogInterface dialogInterface, String str) {
                this.f$0.m5808xaf21e268(selectedBricks, dialogInterface, str);
            }
        });
        builder.setTitle(R.string.new_group).setNegativeButton(R.string.cancel, (DialogInterface.OnClickListener) null).show();
    }

    /* JADX INFO: renamed from: lambda$showNewScriptGroupAlert$9$org-catrobat-catroid-ui-recyclerview-fragment-ScriptFragment, reason: not valid java name */
    /* synthetic */ void m5808xaf21e268(List list, DialogInterface dialogInterface, String str) {
        pack(str, list);
    }

    public void pack(String name2, List<Brick> selectedBricks) {
        try {
            this.scriptController.pack(name2, selectedBricks);
            ToastUtil.showSuccess(getActivity(), getString(R.string.packed_script_group));
            switchToBackpack();
        } catch (CloneNotSupportedException e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }
        finishActionMode();
    }

    private void switchToBackpack() {
        View viewFindViewById = getActivity() != null ? getActivity().findViewById(R.id.workspace_layout) : null;
        if (viewFindViewById != null && viewFindViewById.getVisibility() == 0) {
            ((WorkspaceLayout) viewFindViewById).openWindow("Backpack_Scripts", "Рюкзак: Скрипты", new Function0() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.ScriptFragment$$ExternalSyntheticLambda3
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return new BackpackScriptFragment();
                }
            });
            return;
        }
        Intent intent = new Intent(getActivity(), (Class<?>) BackpackActivity.class);
        intent.putExtra("fragmentPosition", 4);
        startActivity(intent);
    }

    /* JADX WARN: Code duplicated, block: B:11:0x002e  */
    private void switchToCatblocks() {
        UUID brickID;
        int firstVisiblePosition = this.listView.getFirstVisiblePosition();
        if (this.listView.getCount() <= 0 || firstVisiblePosition < 0) {
            brickID = null;
        } else {
            Object itemAtPosition = this.listView.getItemAtPosition(firstVisiblePosition);
            if (itemAtPosition instanceof Brick) {
                Brick brick = (Brick) itemAtPosition;
                if (brick instanceof ScriptBrick) {
                    brickID = brick.getScript().getScriptId();
                } else {
                    brickID = brick.getBrickID();
                }
            } else {
                brickID = null;
            }
        }
        SettingsFragment.setUseCatBlocks(getContext(), true);
        CatblocksScriptFragment catblocksScriptFragment = new CatblocksScriptFragment(brickID);
        FragmentTransaction fragmentTransactionBeginTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransactionBeginTransaction.replace(R.id.fragment_container, catblocksScriptFragment, CatblocksScriptFragment.INSTANCE.getTAG());
        fragmentTransactionBeginTransaction.commit();
    }

    private void copy(List<Brick> selectedBricks) {
        Sprite currentSprite = ProjectManager.getInstance().getCurrentSprite();
        this.brickController.copy(selectedBricks, currentSprite);
        this.adapter.updateItems(currentSprite);
        finishActionMode();
    }

    private void showDeleteAlert(List<Brick> selectedBricks) {
        if (selectedBricks.size() > 0 && copyProjectForUndoOption()) {
            showUndo(true);
            this.undoBrickPosition = this.adapter.getPosition(selectedBricks.get(0));
        }
        delete(selectedBricks);
    }

    private void delete(List<Brick> selectedItems) {
        Sprite currentSprite = ProjectManager.getInstance().getCurrentSprite();
        this.brickController.delete(selectedItems, currentSprite);
        this.adapter.updateItems(currentSprite);
        finishActionMode();
    }

    private void toggleComments(List<Brick> selectedBricks) {
        for (Brick brick : this.adapter.getItems()) {
            brick.setCommentedOut(selectedBricks.contains(brick));
        }
        finishActionMode();
    }

    public void setUndoBrickPosition(Brick brick) {
        this.undoBrickPosition = this.adapter.getPosition(brick);
    }

    public boolean copyProjectForUndoOption() {
        ProjectManager projectManager = ProjectManager.getInstance();
        this.currentSpriteName = projectManager.getCurrentSprite().getName();
        this.currentSceneName = projectManager.getCurrentlyEditedScene().getName();
        Project currentProject = projectManager.getCurrentProject();
        XstreamSerializer.getInstance().saveProject(currentProject);
        File file = new File(currentProject.getDirectory(), Constants.CODE_XML_FILE_NAME);
        File file2 = new File(currentProject.getDirectory(), Constants.UNDO_CODE_XML_FILE_NAME);
        if (!file.exists()) {
            return false;
        }
        try {
            StorageOperations.transferData(file, file2);
            saveVariables();
            return true;
        } catch (IOException e) {
            Log.e(TAG, "Copying project " + currentProject.getName() + " failed.", e);
            return false;
        }
    }

    public void loadProjectAfterUndoOption() {
        Project currentProject = ProjectManager.getInstance().getCurrentProject();
        File file = new File(currentProject.getDirectory(), Constants.CODE_XML_FILE_NAME);
        File file2 = new File(currentProject.getDirectory(), Constants.UNDO_CODE_XML_FILE_NAME);
        if (file.exists()) {
            try {
                StorageOperations.transferData(file2, file);
                new ProjectLoader(currentProject.getDirectory(), getContext()).setListener(this).loadProjectAsync();
            } catch (IOException e) {
                Log.e(TAG, "Replaceing project " + currentProject.getName() + " failed.", e);
            }
        }
    }

    @Override // org.catrobat.catroid.io.asynctask.ProjectLoader.ProjectLoadListener
    public void onLoadFinished(boolean success) {
        if (!success || !isAdded() || getActivity() == null || getActivity().isFinishing() || getActivity().isDestroyed()) {
            return;
        }
        ProjectManager.getInstance().setCurrentSceneAndSprite(this.currentSceneName, this.currentSpriteName);
        if (checkVariables()) {
            loadVariables();
        }
        refreshFragmentAfterUndo();
        refreshFastScroll();
    }

    private void saveVariables() {
        ProjectManager projectManager = ProjectManager.getInstance();
        Sprite currentSprite = projectManager.getCurrentSprite();
        Project currentProject = projectManager.getCurrentProject();
        this.savedUserVariables = currentProject.getUserVariablesCopy();
        this.savedMultiplayerVariables = currentProject.getMultiplayerVariablesCopy();
        this.savedUserLists = currentProject.getUserListsCopy();
        this.savedLocalUserVariables = currentSprite.getUserVariablesCopy();
        this.savedLocalLists = currentSprite.getUserListsCopy();
    }

    public boolean checkVariables() {
        ProjectManager projectManager = ProjectManager.getInstance();
        Sprite currentSprite = projectManager.getCurrentSprite();
        Project currentProject = projectManager.getCurrentProject();
        return currentProject.hasUserDataChanged(currentProject.getUserVariables(), this.savedUserVariables) || currentProject.hasUserDataChanged(currentProject.getMultiplayerVariables(), this.savedMultiplayerVariables) || currentProject.hasUserDataChanged(currentProject.getUserLists(), this.savedUserLists) || currentSprite.hasUserDataChanged(currentSprite.getUserVariables(), this.savedLocalUserVariables) || currentSprite.hasUserDataChanged(currentSprite.getUserLists(), this.savedLocalLists);
    }

    private void loadVariables() {
        ProjectManager projectManager = ProjectManager.getInstance();
        Sprite currentSprite = projectManager.getCurrentSprite();
        Project currentProject = projectManager.getCurrentProject();
        currentProject.restoreUserDataValues(currentProject.getUserVariables(), this.savedUserVariables);
        currentProject.restoreUserDataValues(currentProject.getMultiplayerVariables(), this.savedMultiplayerVariables);
        currentProject.restoreUserDataValues(currentProject.getUserLists(), this.savedUserLists);
        currentSprite.restoreUserDataValues(currentSprite.getUserVariables(), this.savedLocalUserVariables);
        currentSprite.restoreUserDataValues(currentSprite.getUserLists(), this.savedLocalLists);
    }

    private void refreshFragmentAfterUndo() {
        if (!isAdded() || getActivity() == null || getActivity().isFinishing() || getActivity().isDestroyed()) {
            return;
        }
        Sprite currentSprite = ProjectManager.getInstance().getCurrentSprite();
        this.currentSprite = currentSprite;
        BrickAdapter brickAdapter = this.adapter;
        if (brickAdapter != null && currentSprite != null) {
            brickAdapter.updateItems(currentSprite);
            this.adapter.notifyDataSetChanged();
        }
        runCodeAnalysis();
        BrickListView brickListView = this.listView;
        if (brickListView != null) {
            if (this.undoBrickPosition < brickListView.getFirstVisiblePosition() || this.undoBrickPosition > this.listView.getLastVisiblePosition()) {
                this.listView.post(new Runnable() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.ScriptFragment$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m5802x7bb9ca3a();
                    }
                });
            }
        }
    }

    /* JADX INFO: renamed from: lambda$refreshFragmentAfterUndo$10$org-catrobat-catroid-ui-recyclerview-fragment-ScriptFragment, reason: not valid java name */
    /* synthetic */ void m5802x7bb9ca3a() {
        BrickListView brickListView = this.listView;
        if (brickListView != null) {
            brickListView.setSelection(this.undoBrickPosition);
        }
    }

    public void showUndo(boolean visible) {
        if (getActivity() instanceof SpriteActivity) {
            ((SpriteActivity) getActivity()).showUndo(visible);
        }
    }

    private void scrollToFocusItem() {
        if (this.scriptToFocus == null && this.brickToFocus == null) {
            return;
        }
        final int i = 0;
        while (true) {
            if (i >= this.listView.getAdapter().getCount()) {
                i = -1;
                break;
            }
            Object itemAtPosition = this.listView.getItemAtPosition(i);
            if (itemAtPosition instanceof Brick) {
                Brick brick = (Brick) itemAtPosition;
                Brick brick2 = this.brickToFocus;
                if ((brick2 != null && brick == brick2) || (this.scriptToFocus != null && brick.getScript() == this.scriptToFocus)) {
                    break;
                }
            }
            i++;
        }
        if (i == -1) {
            return;
        }
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.ScriptFragment.3
                @Override // java.lang.Runnable
                public void run() {
                    ScriptFragment.this.listView.setSelection(i);
                }
            });
        }
        this.scriptToFocus = null;
        this.brickToFocus = null;
    }

    public int getActionModeType() {
        return this.actionModeType;
    }

    public boolean isFinderOpen() {
        return this.scriptFinder.isOpen();
    }

    public void closeFinder() {
        if (this.scriptFinder.isClosed()) {
            return;
        }
        this.scriptFinder.close();
    }

    private void updateAnalysisIndicator() {
        if (this.analysisStatusIndicator == null || this.errorCountText == null || this.warningCountText == null) {
            return;
        }
        int errorCount = AnalysisManager.INSTANCE.getErrorCount();
        int warningCount = AnalysisManager.INSTANCE.getWarningCount();
        if (errorCount == 0 && warningCount == 0) {
            this.analysisStatusIndicator.setVisibility(8);
            return;
        }
        this.analysisStatusIndicator.setVisibility(0);
        this.errorCountText.setText(String.valueOf(errorCount));
        this.warningCountText.setText(String.valueOf(warningCount));
        View viewFindViewById = this.analysisStatusIndicator.findViewById(R.id.error_dot);
        if (viewFindViewById != null) {
            viewFindViewById.setVisibility(errorCount > 0 ? 0 : 8);
        }
        this.errorCountText.setVisibility(errorCount > 0 ? 0 : 8);
        View viewFindViewById2 = this.analysisStatusIndicator.findViewById(R.id.warning_dot);
        if (viewFindViewById2 != null) {
            viewFindViewById2.setVisibility(warningCount > 0 ? 0 : 8);
        }
        this.warningCountText.setVisibility(warningCount > 0 ? 0 : 8);
    }

    private void showAnalysisSummaryDialog() {
        int color;
        Map<Brick, AnalysisResult> results = AnalysisManager.INSTANCE.getResults();
        if (results.isEmpty() || getContext() == null) {
            return;
        }
        Context context = getContext();
        float f = context.getResources().getDisplayMetrics().density;
        ScrollView scrollView = new ScrollView(context);
        int i = (int) (16.0f * f);
        int i2 = (int) (12.0f * f);
        scrollView.setPadding(i, i2, i, i2);
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(1);
        final AlertDialog[] alertDialogArr = new AlertDialog[1];
        for (Iterator<Map.Entry<Brick, AnalysisResult>> it = results.entrySet().iterator(); it.hasNext(); it = it) {
            Map.Entry<Brick, AnalysisResult> next = it.next();
            final Brick key = next.getKey();
            AnalysisResult value = next.getValue();
            LinearLayout linearLayout2 = new LinearLayout(context);
            linearLayout2.setOrientation(0);
            linearLayout2.setGravity(16);
            linearLayout2.setPadding(i2, i2, i2, i2);
            StateListDrawable stateListDrawable = new StateListDrawable();
            GradientDrawable gradientDrawable = new GradientDrawable();
            gradientDrawable.setColor(context.getResources().getColor(R.color.surface_card_pressed));
            float f2 = 8.0f * f;
            gradientDrawable.setCornerRadius(f2);
            GradientDrawable gradientDrawable2 = new GradientDrawable();
            gradientDrawable2.setColor(context.getResources().getColor(R.color.surface_card));
            gradientDrawable2.setCornerRadius(f2);
            stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, gradientDrawable);
            stateListDrawable.addState(new int[]{android.R.attr.state_focused}, gradientDrawable);
            stateListDrawable.addState(new int[0], gradientDrawable2);
            linearLayout2.setBackground(stateListDrawable);
            linearLayout2.setClickable(true);
            linearLayout2.setFocusable(true);
            linearLayout2.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.ScriptFragment$$ExternalSyntheticLambda4
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.m5805x34bfe3f(key, alertDialogArr, view);
                }
            });
            View view = new View(context);
            if (value.getSeverity() == Severity.ERROR) {
                color = context.getResources().getColor(R.color.red);
            } else {
                color = context.getResources().getColor(R.color.orange);
            }
            view.setBackgroundColor(color);
            int i3 = (int) (4.0f * f);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(i3, -1);
            layoutParams.setMargins(0, 0, i2, 0);
            view.setLayoutParams(layoutParams);
            linearLayout2.addView(view);
            LinearLayout linearLayout3 = new LinearLayout(context);
            linearLayout3.setOrientation(1);
            linearLayout3.setLayoutParams(new LinearLayout.LayoutParams(0, -2, 1.0f));
            TextView textView = new TextView(context);
            textView.setText(value.getMessage());
            textView.setTextColor(context.getResources().getColor(R.color.solid_white));
            textView.setTextSize(14.0f);
            textView.setPadding(0, 0, 0, i3);
            linearLayout3.addView(textView);
            TextView textView2 = new TextView(context);
            textView2.setText(context.getString(R.string.analysis_click_to_jump));
            textView2.setTextColor(context.getResources().getColor(R.color.text_secondary));
            textView2.setTextSize(11.0f);
            linearLayout3.addView(textView2);
            linearLayout2.addView(linearLayout3);
            LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-1, -2);
            layoutParams2.setMargins(0, 0, 0, (int) f2);
            linearLayout2.setLayoutParams(layoutParams2);
            linearLayout.addView(linearLayout2);
        }
        scrollView.addView(linearLayout);
        AlertDialog alertDialogCreate = new AlertDialog.Builder(context, 2131952355).setTitle(R.string.analysis_dialog_title).setView(scrollView).setPositiveButton("OK", (DialogInterface.OnClickListener) null).create();
        alertDialogArr[0] = alertDialogCreate;
        alertDialogCreate.show();
    }

    /* JADX INFO: renamed from: lambda$showAnalysisSummaryDialog$11$org-catrobat-catroid-ui-recyclerview-fragment-ScriptFragment, reason: not valid java name */
    /* synthetic */ void m5805x34bfe3f(Brick brick, AlertDialog[] alertDialogArr, View view) {
        boolean z = false;
        for (Brick parent = brick.getParent(); parent != null; parent = parent.getParent()) {
            if (BrickCollapseManager.INSTANCE.isCollapsed(parent)) {
                BrickCollapseManager.INSTANCE.setCollapsed(parent, false);
                z = true;
            }
        }
        if (z) {
            this.adapter.updateItems(ProjectManager.getInstance().getCurrentSprite());
        }
        int position = this.adapter.getPosition(brick);
        if (position != -1) {
            this.listView.smoothScrollToPosition(position);
            highlightBrickAtIndex(position);
            this.listView.invalidate();
        }
        AlertDialog alertDialog = alertDialogArr[0];
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
    }

    private void showDetailedSystemInfoDialog(Brick brick) {
        Context context = getContext();
        if (context == null) {
            return;
        }
        float f = context.getResources().getDisplayMetrics().density;
        ScrollView scrollView = new ScrollView(context);
        int i = (int) (16.0f * f);
        int i2 = (int) (f * 12.0f);
        scrollView.setPadding(i, i2, i, i2);
        TextView textView = new TextView(context);
        textView.setText(getBrickSystemInfo(brick));
        textView.setTextColor(context.getResources().getColor(R.color.solid_white));
        textView.setTextSize(13.0f);
        textView.setTypeface(Typeface.create(Typeface.MONOSPACE, 0));
        scrollView.addView(textView);
        new AlertDialog.Builder(context).setTitle(R.string.dialog_add_brick_system_info_title).setView(scrollView).setPositiveButton("OK", (DialogInterface.OnClickListener) null).show();
    }

    private String getBrickSystemInfo(Brick brick) {
        Map<Brick.BrickData, UserData> allBrickDataWithValues;
        Map<Brick.BrickField, Formula> allFormulaFieldsWithFormulas;
        StringBuilder sb = new StringBuilder("Class Name:\n");
        sb.append(brick.getClass().getName()).append("\n---------------------------------------\n\nSuperclass:\n");
        Class<? super Object> superclass = brick.getClass().getSuperclass();
        sb.append(superclass != null ? superclass.getSimpleName() : "None").append("\n---------------------------------------\n\n");
        if ((brick instanceof FormulaBrick) && (allFormulaFieldsWithFormulas = ((FormulaBrick) brick).getAllFormulaFieldsWithFormulas()) != null && !allFormulaFieldsWithFormulas.isEmpty()) {
            sb.append("Formula Fields (BrickFields):\n");
            for (Map.Entry<Brick.BrickField, Formula> entry : allFormulaFieldsWithFormulas.entrySet()) {
                sb.append("  * ").append(entry.getKey()).append(" = ").append(entry.getValue().getTrimmedFormulaString(getContext())).append("\n");
            }
            sb.append("---------------------------------------\n\n");
        }
        if ((brick instanceof UserDataBrick) && (allBrickDataWithValues = ((UserDataBrick) brick).getAllBrickDataWithValues()) != null && !allBrickDataWithValues.isEmpty()) {
            sb.append("Data Fields (UserData):\n");
            for (Map.Entry<Brick.BrickData, UserData> entry2 : allBrickDataWithValues.entrySet()) {
                sb.append("  * ").append(entry2.getKey()).append(" = ").append(entry2.getValue()).append("\n");
            }
            sb.append("---------------------------------------\n\n");
        }
        sb.append("Declared Fields:\n");
        Field[] declaredFields = brick.getClass().getDeclaredFields();
        if (declaredFields.length > 0) {
            for (Field field : declaredFields) {
                try {
                    field.setAccessible(true);
                    String name2 = field.getName();
                    if (!name2.startsWith("$") && !name2.equals(CommonClassNames.SERIAL_VERSION_UID_FIELD_NAME) && !name2.equals("Companion")) {
                        String simpleName = field.getType().getSimpleName();
                        Object obj = field.get(brick);
                        sb.append("  * ").append(name2).append(" (").append(simpleName).append(") = ").append(obj != null ? obj.toString() : "null").append("\n");
                    }
                } catch (Exception unused) {
                }
            }
        } else {
            sb.append("  * No declared fields found\n");
        }
        sb.append("---------------------------------------\n\n");
        return sb.toString();
    }

    private void printSpriteScriptsToLog(Sprite sprite) {
        Context context;
        if (sprite == null || (context = getContext()) == null) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("\n==================================================\n SCRIPTS FOR SPRITE: ");
        sb.append(sprite.getName().toUpperCase()).append("\n==================================================\n");
        List<Script> scriptList = sprite.getScriptList();
        if (scriptList == null || scriptList.isEmpty()) {
            sb.append(" (Скрипты отсутствуют)\n");
        } else {
            for (Script script : scriptList) {
                sb.append("\n").append(formatScriptHeader(script)).append("\n");
                printBricks(getBricksFromScript(script), 1, sb, context);
            }
        }
        sb.append("==================================================\n");
        String string = sb.toString();
        int i = 0;
        while (i <= string.length() / 2000) {
            int i2 = i * 2000;
            i++;
            int iMin = Math.min(i * 2000, string.length());
            if (i2 < iMin) {
                Log.d("SpritePrinter", string.substring(i2, iMin));
            }
        }
    }

    private void printBricks(List<Brick> bricks, int indentLevel, StringBuilder sb, Context context) {
        List<Brick> secondaryNestedBricks;
        if (bricks == null || bricks.isEmpty()) {
            return;
        }
        for (Brick brick : bricks) {
            boolean z = brick instanceof CompositeBrick;
            String indentString = getIndentString(indentLevel);
            String brick2 = formatBrick(brick, context);
            if (brick.isCommentedOut()) {
                sb.append(indentString).append("// [ОТКЛЮЧЕН] ").append(brick2).append("\n");
            } else {
                sb.append(indentString).append(brick2).append("\n");
            }
            if (z) {
                CompositeBrick compositeBrick = (CompositeBrick) brick;
                List<Brick> nestedBricks = compositeBrick.getNestedBricks();
                if (nestedBricks != null && !nestedBricks.isEmpty()) {
                    printBricks(nestedBricks, indentLevel + 1, sb, context);
                }
                if (compositeBrick.hasSecondaryList() && (secondaryNestedBricks = compositeBrick.getSecondaryNestedBricks()) != null && !secondaryNestedBricks.isEmpty()) {
                    sb.append(getIndentString(indentLevel)).append("IfLogicElseBrick\n");
                    printBricks(secondaryNestedBricks, indentLevel + 1, sb, context);
                }
                sb.append(indentString).append(getEndBlockName(brick)).append("\n");
            }
        }
    }

    private String getEndBlockName(Brick brick) {
        String simpleName = brick.getClass().getSimpleName();
        if (simpleName.contains("Forever") || simpleName.contains("LoopEndless")) {
            return "LoopEndBrick";
        }
        if (simpleName.contains("If") || simpleName.contains("IfThen") || simpleName.contains("IfLogic")) {
            return "IfThenLogicEndBrick";
        }
        if (simpleName.contains("Repeat") || simpleName.contains("ForVariable") || simpleName.contains("ForItem")) {
            return "LoopEndBrick";
        }
        if (simpleName.contains("TryCatchFinally")) {
            return "TryCatchFinallyEndBrick";
        }
        return "EndBlock";
    }

    private String formatScriptHeader(Script script) {
        String simpleName = script.getClass().getSimpleName();
        simpleName.hashCode();
        switch (simpleName) {
            case "WhenScript":
                return "При нажатии на спрайт:";
            case "WhenBackgroundChangesScript":
                return "Когда фон меняется:";
            case "StartScript":
                return "При запуске сцены:";
            case "BroadcastScript":
                return "Когда я получу вещание:";
            case "WhenTouchDownScript":
                return "При касании экрана:";
            case "WhenConditionScript":
                return "Когда условие становится истинным:";
            case "WhenClonedScript":
                return "Когда я начинаю как клон:";
            default:
                return "Скрипт [" + simpleName + "]:";
        }
    }

    private String formatBrick(Brick brick, Context context) {
        Object obj;
        Map<Brick.BrickField, Formula> allFormulaFieldsWithFormulas;
        String string;
        StringBuilder sb = new StringBuilder(brick.getClass().getSimpleName());
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        if ((brick instanceof FormulaBrick) && (allFormulaFieldsWithFormulas = ((FormulaBrick) brick).getAllFormulaFieldsWithFormulas()) != null) {
            for (Map.Entry<Brick.BrickField, Formula> entry : allFormulaFieldsWithFormulas.entrySet()) {
                if (entry.getValue() == null) {
                    string = "null";
                } else {
                    try {
                        string = entry.getValue().getTrimmedFormulaString(context);
                    } catch (Exception unused) {
                        string = entry.getValue().toString();
                    }
                }
                linkedHashMap.put(entry.getKey(), string);
            }
        }
        ArrayList arrayList = new ArrayList();
        for (Map.Entry entry2 : linkedHashMap.entrySet()) {
            arrayList.add(((Brick.BrickField) entry2.getKey()).name() + ": '" + ((String) entry2.getValue()) + FormatHelper.QUOTE);
        }
        for (Class<?> superclass = brick.getClass(); superclass != null && superclass != Object.class; superclass = superclass.getSuperclass()) {
            for (Field field : superclass.getDeclaredFields()) {
                try {
                    field.setAccessible(true);
                    String name2 = field.getName();
                    if (!name2.startsWith("$") && !name2.equals(CommonClassNames.SERIAL_VERSION_UID_FIELD_NAME) && !name2.equals("Companion") && !name2.equals("parent") && !name2.equals(Promotion.ACTION_VIEW) && !name2.equals("checkbox") && !name2.equals("formulaMap") && !name2.equals("brickFieldToTextViewIdMap") && !name2.contains("cached") && !name2.contains("spinnerValues") && !Modifier.isStatic(field.getModifiers())) {
                        Class<?> type = field.getType();
                        if ((type.isPrimitive() || type.isEnum() || type == String.class || Number.class.isAssignableFrom(type) || type == Boolean.class) && (obj = field.get(brick)) != null) {
                            Iterator it = linkedHashMap.keySet().iterator();
                            do {
                                if (!it.hasNext()) {
                                    arrayList.add(name2 + ": " + obj.toString());
                                    break;
                                }
                            } while (!((Brick.BrickField) it.next()).name().equalsIgnoreCase(name2));
                        }
                    }
                } catch (Exception unused2) {
                }
            }
        }
        if (!arrayList.isEmpty()) {
            sb.append(" [").append(TextUtils.join(", ", arrayList)).append("]");
        }
        return sb.toString();
    }

    private List<Brick> getBricksFromScript(Script script) {
        try {
            try {
                return (List) script.getClass().getMethod("getBrickList", new Class[0]).invoke(script, new Object[0]);
            } catch (Exception unused) {
                return new ArrayList();
            }
        } catch (Exception unused2) {
            return (List) script.getClass().getMethod("getBricks", new Class[0]).invoke(script, new Object[0]);
        }
    }

    private String getIndentString(int level) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sb.append("  ");
        }
        return sb.toString();
    }

    private void refreshFastScroll() {
        BrickListView brickListView = this.listView;
        if (brickListView != null) {
            brickListView.setFastScrollEnabled(false);
            this.listView.setFastScrollEnabled(true);
            this.listView.setFastScrollAlwaysVisible(true);
        }
    }
}
