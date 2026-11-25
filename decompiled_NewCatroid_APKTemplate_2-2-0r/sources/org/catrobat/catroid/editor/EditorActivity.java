package org.catrobat.catroid.editor;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GestureDetectorCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidFragmentApplication;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Json;
import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.raptor.GameObject;
import org.catrobat.catroid.raptor.SceneData;
import org.catrobat.catroid.raptor.SceneManager;
import org.catrobat.catroid.raptor.ThreeDManager;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/editor/EditorActivity.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class EditorActivity extends AppCompatActivity implements AndroidFragmentApplication.Callbacks, HierarchyAdapter.OnItemClickListener {
    private static final String AUTOSAVE_FILE_NAME = "_recovery_autosave.rscene";
    private Thread.UncaughtExceptionHandler defaultCrashHandler;
    private DrawerLayout drawerLayout;
    public EditorListener editorListener;
    private Gizmo gizmo;
    private HierarchyAdapter hierarchyAdapter;
    private ListView hierarchyListView;
    private RecyclerView hierarchyRecyclerView;
    private InspectorManager inspectorManager;
    private View quickActions;
    private SceneManager sceneManager;
    private ThreeDManager threeDManager;
    private ItemTouchHelper touchHelper;
    private UndoManager undoManager;
    private final List<HierarchyAdapter.HierarchyItem> hierarchyItems = new ArrayList();
    private final List<GameObject> hierarchyObjects = new ArrayList();
    private GameObject currentSelectedObject = null;

    @Override // com.badlogic.gdx.backends.android.AndroidFragmentApplication.Callbacks
    public void exit() {
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupCrashHandler();
        setContentView(R.layout.editor_activity);
        setupToolbarAndDrawer();
        if (savedInstanceState == null) {
            EditorFragment editorFragment = new EditorFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, editorFragment).commitNow();
            this.editorListener = editorFragment.getListener();
        }
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) { // from class: org.catrobat.catroid.editor.EditorActivity.1
            @Override // androidx.activity.OnBackPressedCallback
            public void handleOnBackPressed() {
                if (EditorActivity.this.drawerLayout.isDrawerOpen(8388611)) {
                    EditorActivity.this.drawerLayout.closeDrawer(8388611);
                } else if (EditorActivity.this.drawerLayout.isDrawerOpen(GravityCompat.END)) {
                    EditorActivity.this.drawerLayout.closeDrawer(GravityCompat.END);
                }
            }
        });
    }

    public void onEditorReady(SceneManager manager, ThreeDManager TDmanager) {
        this.sceneManager = manager;
        this.threeDManager = TDmanager;
        this.inspectorManager = new InspectorManager(this, this.sceneManager, this.threeDManager);
        this.undoManager = new UndoManager(this);
        findViewById(R.id.btn_undo).setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.editor.EditorActivity$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m5022x8051cc39(view);
            }
        });
        findViewById(R.id.btn_redo).setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.editor.EditorActivity$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m5023x433e3598(view);
            }
        });
        EditorFragment editorFragment = (EditorFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (editorFragment != null) {
            EditorListener listener = editorFragment.getListener();
            this.editorListener = listener;
            Gizmo gizmo = listener.getGizmo();
            this.gizmo = gizmo;
            if (gizmo != null) {
                this.inspectorManager.setGizmo(gizmo);
            }
        }
        runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.editor.EditorActivity$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5024x62a9ef7();
            }
        });
    }

    /* JADX INFO: renamed from: lambda$onEditorReady$0$org-catrobat-catroid-editor-EditorActivity, reason: not valid java name */
    /* synthetic */ void m5022x8051cc39(View view) {
        this.undoManager.undo();
        EditorListener editorListener = this.editorListener;
        if (editorListener == null || editorListener.getGizmo() == null) {
            return;
        }
        onObjectSelected(this.currentSelectedObject, false);
    }

    /* JADX INFO: renamed from: lambda$onEditorReady$1$org-catrobat-catroid-editor-EditorActivity, reason: not valid java name */
    /* synthetic */ void m5023x433e3598(View view) {
        this.undoManager.redo();
        if (this.editorListener != null) {
            onObjectSelected(this.currentSelectedObject, false);
        }
    }

    /* JADX INFO: renamed from: lambda$onEditorReady$2$org-catrobat-catroid-editor-EditorActivity, reason: not valid java name */
    /* synthetic */ void m5024x62a9ef7() {
        setupUI();
        checkForRecovery();
    }

    public void onObjectSelected(GameObject go) {
        onObjectSelected(go, true);
    }

    public void onObjectSelected(final GameObject go, final boolean showInspector) {
        runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.editor.EditorActivity$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5025xc4dc4e9a(go, showInspector);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$onObjectSelected$3$org-catrobat-catroid-editor-EditorActivity, reason: not valid java name */
    /* synthetic */ void m5025xc4dc4e9a(GameObject gameObject, boolean z) {
        DrawerLayout drawerLayout;
        InspectorManager inspectorManager = this.inspectorManager;
        if (inspectorManager != null && this.currentSelectedObject != gameObject) {
            inspectorManager.m5171x5f6795b(gameObject);
            this.currentSelectedObject = gameObject;
        }
        this.quickActions.setVisibility(gameObject != null ? 0 : 8);
        if (gameObject != null && z && (drawerLayout = this.drawerLayout) != null) {
            drawerLayout.openDrawer(GravityCompat.END);
        }
        HierarchyAdapter hierarchyAdapter = this.hierarchyAdapter;
        if (hierarchyAdapter != null) {
            hierarchyAdapter.setSelectedObject(gameObject);
        }
        if (this.editorListener.getGizmo() != null) {
            this.editorListener.getGizmo().setSelectedObject(gameObject);
        }
    }

    private void setupToolbarAndDrawer() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, this.drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        this.drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() { // from class: org.catrobat.catroid.editor.EditorActivity.2
            @Override // androidx.drawerlayout.widget.DrawerLayout.DrawerListener
            public void onDrawerClosed(View drawerView) {
            }

            @Override // androidx.drawerlayout.widget.DrawerLayout.DrawerListener
            public void onDrawerStateChanged(int newState) {
            }

            @Override // androidx.drawerlayout.widget.DrawerLayout.DrawerListener
            public void onDrawerSlide(View drawerView, float slideOffset) {
                View viewFindViewById = EditorActivity.this.findViewById(R.id.main_content_coordinator);
                View viewFindViewById2 = EditorActivity.this.findViewById(R.id.left_drawer);
                if (viewFindViewById == null || drawerView == null) {
                    return;
                }
                if (drawerView.getId() == viewFindViewById2.getId()) {
                    viewFindViewById.setTranslationX(drawerView.getWidth() * slideOffset);
                } else {
                    viewFindViewById.setTranslationX((-drawerView.getWidth()) * slideOffset);
                }
            }

            @Override // androidx.drawerlayout.widget.DrawerLayout.DrawerListener
            public void onDrawerOpened(View drawerView) {
                drawerView.requestLayout();
            }
        });
        actionBarDrawerToggle.syncState();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setupUI() {
        this.hierarchyRecyclerView = (RecyclerView) findViewById(R.id.hierarchy_recyclerview);
        DraggableLinearLayoutManager draggableLinearLayoutManager = new DraggableLinearLayoutManager(this);
        this.hierarchyRecyclerView.setLayoutManager(draggableLinearLayoutManager);
        HierarchyAdapter hierarchyAdapter = new HierarchyAdapter(this);
        this.hierarchyAdapter = hierarchyAdapter;
        this.hierarchyRecyclerView.setAdapter(hierarchyAdapter);
        this.quickActions = findViewById(R.id.quick_actions_panel);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new HierarchyDragCallback(this.hierarchyAdapter, this.sceneManager, this, draggableLinearLayoutManager));
        this.touchHelper = itemTouchHelper;
        itemTouchHelper.attachToRecyclerView(this.hierarchyRecyclerView);
        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean("pref_pc_mode_enabled", false)) {
            findViewById(R.id.btn_cam_w).setVisibility(8);
            findViewById(R.id.btn_cam_a).setVisibility(8);
            findViewById(R.id.btn_cam_s).setVisibility(8);
            findViewById(R.id.btn_cam_d).setVisibility(8);
            findViewById(R.id.btn_cam_q).setVisibility(8);
            findViewById(R.id.btn_cam_e).setVisibility(8);
            findViewById(R.id.btn_cam_shift).setVisibility(8);
        }
        this.hierarchyRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() { // from class: org.catrobat.catroid.editor.EditorActivity.3
            private final GestureDetectorCompat gestureDetector;

            @Override // androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            }

            @Override // androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            }

            {
                this.gestureDetector = new GestureDetectorCompat(EditorActivity.this.hierarchyRecyclerView.getContext(), new GestureDetector.SimpleOnGestureListener() { // from class: org.catrobat.catroid.editor.EditorActivity.3.1
                    @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
                    public void onLongPress(MotionEvent e) {
                        RecyclerView.ViewHolder childViewHolder;
                        View viewFindChildViewUnder = EditorActivity.this.hierarchyRecyclerView.findChildViewUnder(e.getX(), e.getY());
                        if (viewFindChildViewUnder == null || (childViewHolder = EditorActivity.this.hierarchyRecyclerView.getChildViewHolder(viewFindChildViewUnder)) == null) {
                            return;
                        }
                        EditorActivity.this.drawerLayout.requestDisallowInterceptTouchEvent(true);
                        EditorActivity.this.touchHelper.startDrag(childViewHolder);
                    }
                });
            }

            @Override // androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                this.gestureDetector.onTouchEvent(e);
                if (e.getAction() == 1 || e.getAction() == 3) {
                    EditorActivity.this.drawerLayout.requestDisallowInterceptTouchEvent(false);
                }
                return false;
            }
        });
        findViewById(R.id.btn_add_empty).setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.editor.EditorActivity$$ExternalSyntheticLambda27
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m5041lambda$setupUI$4$orgcatrobatcatroideditorEditorActivity(view);
            }
        });
        findViewById(R.id.btn_add_cube).setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.editor.EditorActivity$$ExternalSyntheticLambda30
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m5042lambda$setupUI$5$orgcatrobatcatroideditorEditorActivity(view);
            }
        });
        findViewById(R.id.btn_add_sphere).setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.editor.EditorActivity$$ExternalSyntheticLambda31
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m5043lambda$setupUI$6$orgcatrobatcatroideditorEditorActivity(view);
            }
        });
        updateHierarchy();
        setupCameraButton(R.id.btn_cam_w, 0.0f, 0.0f, 1.0f);
        setupCameraButton(R.id.btn_cam_s, 0.0f, 0.0f, -1.0f);
        setupCameraButton(R.id.btn_cam_a, -1.0f, 0.0f, 0.0f);
        setupCameraButton(R.id.btn_cam_d, 1.0f, 0.0f, 0.0f);
        setupCameraButton(R.id.btn_cam_q, 0.0f, -1.0f, 0.0f);
        setupCameraButton(R.id.btn_cam_e, 0.0f, 1.0f, 0.0f);
        ((Button) findViewById(R.id.btn_cam_shift)).setOnTouchListener(new View.OnTouchListener() { // from class: org.catrobat.catroid.editor.EditorActivity$$ExternalSyntheticLambda32
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return this.f$0.m5044lambda$setupUI$7$orgcatrobatcatroideditorEditorActivity(view, motionEvent);
            }
        });
        ((EditText) findViewById(R.id.hierarchy_search)).addTextChangedListener(new TextWatcher() { // from class: org.catrobat.catroid.editor.EditorActivity.4
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (EditorActivity.this.hierarchyAdapter != null) {
                    EditorActivity.this.hierarchyAdapter.filter(s.toString());
                }
            }
        });
        findViewById(R.id.btn_quick_inspector).setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.editor.EditorActivity$$ExternalSyntheticLambda34
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m5045lambda$setupUI$8$orgcatrobatcatroideditorEditorActivity(view);
            }
        });
        findViewById(R.id.btn_mass_delete).setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.editor.EditorActivity$$ExternalSyntheticLambda35
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m5031lambda$setupUI$12$orgcatrobatcatroideditorEditorActivity(view);
            }
        });
        findViewById(R.id.btn_quick_rotate_to_cam).setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.editor.EditorActivity$$ExternalSyntheticLambda36
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m5032lambda$setupUI$13$orgcatrobatcatroideditorEditorActivity(view);
            }
        });
        findViewById(R.id.btn_quick_duplicate).setOnLongClickListener(new View.OnLongClickListener() { // from class: org.catrobat.catroid.editor.EditorActivity$$ExternalSyntheticLambda37
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                return this.f$0.m5036lambda$setupUI$17$orgcatrobatcatroideditorEditorActivity(view);
            }
        });
        findViewById(R.id.btn_quick_move_to_cam).setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.editor.EditorActivity$$ExternalSyntheticLambda38
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m5037lambda$setupUI$18$orgcatrobatcatroideditorEditorActivity(view);
            }
        });
        findViewById(R.id.btn_quick_duplicate).setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.editor.EditorActivity$$ExternalSyntheticLambda39
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m5038lambda$setupUI$19$orgcatrobatcatroideditorEditorActivity(view);
            }
        });
        findViewById(R.id.btn_quick_focus).setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.editor.EditorActivity$$ExternalSyntheticLambda28
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m5039lambda$setupUI$20$orgcatrobatcatroideditorEditorActivity(view);
            }
        });
        findViewById(R.id.btn_quick_delete).setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.editor.EditorActivity$$ExternalSyntheticLambda29
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m5040lambda$setupUI$21$orgcatrobatcatroideditorEditorActivity(view);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$setupUI$4$org-catrobat-catroid-editor-EditorActivity, reason: not valid java name */
    /* synthetic */ void m5041lambda$setupUI$4$orgcatrobatcatroideditorEditorActivity(View view) {
        updateAndSelect(this.sceneManager.createGameObject("Empty"));
    }

    /* JADX INFO: renamed from: lambda$setupUI$5$org-catrobat-catroid-editor-EditorActivity, reason: not valid java name */
    /* synthetic */ void m5042lambda$setupUI$5$orgcatrobatcatroideditorEditorActivity(View view) {
        GameObject gameObjectCreatePrimitive = this.sceneManager.createPrimitive("cube");
        if (gameObjectCreatePrimitive != null) {
            updateAndSelect(gameObjectCreatePrimitive);
        }
    }

    /* JADX INFO: renamed from: lambda$setupUI$6$org-catrobat-catroid-editor-EditorActivity, reason: not valid java name */
    /* synthetic */ void m5043lambda$setupUI$6$orgcatrobatcatroideditorEditorActivity(View view) {
        GameObject gameObjectCreatePrimitive = this.sceneManager.createPrimitive("sphere");
        if (gameObjectCreatePrimitive != null) {
            updateAndSelect(gameObjectCreatePrimitive);
        }
    }

    /* JADX INFO: renamed from: lambda$setupUI$7$org-catrobat-catroid-editor-EditorActivity, reason: not valid java name */
    /* synthetic */ boolean m5044lambda$setupUI$7$orgcatrobatcatroideditorEditorActivity(View view, MotionEvent motionEvent) {
        if (this.editorListener == null) {
            return false;
        }
        if (motionEvent.getAction() == 0) {
            this.editorListener.onCameraAccelerate(true);
        } else if (motionEvent.getAction() == 1) {
            this.editorListener.onCameraAccelerate(false);
        }
        return true;
    }

    /* JADX INFO: renamed from: lambda$setupUI$8$org-catrobat-catroid-editor-EditorActivity, reason: not valid java name */
    /* synthetic */ void m5045lambda$setupUI$8$orgcatrobatcatroideditorEditorActivity(View view) {
        if (this.currentSelectedObject != null) {
            this.drawerLayout.openDrawer(GravityCompat.END);
        }
    }

    /* JADX INFO: renamed from: lambda$setupUI$12$org-catrobat-catroid-editor-EditorActivity, reason: not valid java name */
    /* synthetic */ void m5031lambda$setupUI$12$orgcatrobatcatroideditorEditorActivity(View view) {
        final String strTrim = ((EditText) findViewById(R.id.hierarchy_search)).getText().toString().trim();
        if (strTrim.isEmpty()) {
            return;
        }
        new AlertDialog.Builder(this, 2131952355).setTitle(R.string.editor_3d_mass_delete_title).setMessage(getString(R.string.editor_3d_mass_delete_msg, new Object[]{strTrim})).setPositiveButton(R.string.editor_3d_delete_all, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.editor.EditorActivity$$ExternalSyntheticLambda17
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.m5030lambda$setupUI$11$orgcatrobatcatroideditorEditorActivity(strTrim, dialogInterface, i);
            }
        }).setNegativeButton(R.string.cancel, (DialogInterface.OnClickListener) null).show();
    }

    /* JADX INFO: renamed from: lambda$setupUI$11$org-catrobat-catroid-editor-EditorActivity, reason: not valid java name */
    /* synthetic */ void m5030lambda$setupUI$11$orgcatrobatcatroideditorEditorActivity(final String str, DialogInterface dialogInterface, int i) {
        Gdx.f82app.postRunnable(new Runnable() { // from class: org.catrobat.catroid.editor.EditorActivity$$ExternalSyntheticLambda18
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5029lambda$setupUI$10$orgcatrobatcatroideditorEditorActivity(str);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$setupUI$10$org-catrobat-catroid-editor-EditorActivity, reason: not valid java name */
    /* synthetic */ void m5029lambda$setupUI$10$orgcatrobatcatroideditorEditorActivity(String str) {
        Commands.CompositeCommand compositeCommand = new Commands.CompositeCommand();
        final ArrayList<GameObject> arrayList = new ArrayList();
        for (GameObject gameObject : this.sceneManager.getAllGameObjects().values()) {
            if (gameObject.f592name.toLowerCase().contains(str.toLowerCase())) {
                arrayList.add(gameObject);
            }
        }
        for (GameObject gameObject2 : arrayList) {
            compositeCommand.addCommand(new Commands.DeleteCommand(this.sceneManager, gameObject2));
            this.sceneManager.removeGameObject(gameObject2);
        }
        if (!compositeCommand.isEmpty()) {
            this.undoManager.pushCommand(compositeCommand);
        }
        runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.editor.EditorActivity$$ExternalSyntheticLambda16
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5046lambda$setupUI$9$orgcatrobatcatroideditorEditorActivity(arrayList);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$setupUI$9$org-catrobat-catroid-editor-EditorActivity, reason: not valid java name */
    /* synthetic */ void m5046lambda$setupUI$9$orgcatrobatcatroideditorEditorActivity(List list) {
        updateHierarchy();
        onObjectSelected(null, false);
        Toast.makeText(this, getString(R.string.editor_3d_removed_objects_toast, new Object[]{Integer.valueOf(list.size())}), 0).show();
    }

    /* JADX INFO: renamed from: lambda$setupUI$13$org-catrobat-catroid-editor-EditorActivity, reason: not valid java name */
    /* synthetic */ void m5032lambda$setupUI$13$orgcatrobatcatroideditorEditorActivity(View view) {
        if (this.currentSelectedObject == null || this.threeDManager == null) {
            return;
        }
        Vector3 vector3 = new Vector3(this.currentSelectedObject.transform.position);
        Quaternion quaternion = new Quaternion(this.currentSelectedObject.transform.rotation);
        Vector3 vector4 = new Vector3(this.currentSelectedObject.transform.scale);
        this.currentSelectedObject.transform.rotation.setFromCross(new Vector3(0.0f, 0.0f, -1.0f), this.threeDManager.getCamera().direction.cpy());
        this.sceneManager.rebuildGameObject(this.currentSelectedObject);
        UndoManager undoManager = this.undoManager;
        if (undoManager != null) {
            undoManager.pushCommand(new Commands.TransformCommand(this.sceneManager, this.currentSelectedObject, vector3, quaternion, vector4));
        }
        Toast.makeText(this, R.string.editor_3d_aligned_to_cam, 0).show();
        onObjectSelected(this.currentSelectedObject, false);
    }

    /* JADX INFO: renamed from: lambda$setupUI$17$org-catrobat-catroid-editor-EditorActivity, reason: not valid java name */
    /* synthetic */ boolean m5036lambda$setupUI$17$orgcatrobatcatroideditorEditorActivity(View view) {
        if (this.currentSelectedObject == null) {
            return false;
        }
        View viewInflate = getLayoutInflater().inflate(R.layout.dialog_array_duplicate, (ViewGroup) null);
        final EditText editText = (EditText) viewInflate.findViewById(R.id.edit_array_count);
        final EditText editText2 = (EditText) viewInflate.findViewById(R.id.edit_array_step);
        final Spinner spinner = (Spinner) viewInflate.findViewById(R.id.spinner_array_axis);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, new String[]{getString(R.string.editor_3d_axis_x), getString(R.string.editor_3d_axis_y), getString(R.string.editor_3d_axis_z), getString(R.string.editor_3d_axis_cam_forward)});
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter((SpinnerAdapter) arrayAdapter);
        new AlertDialog.Builder(this, 2131952355).setView(viewInflate).setPositiveButton(R.string.editor_3d_clone, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.editor.EditorActivity$$ExternalSyntheticLambda14
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.m5035lambda$setupUI$16$orgcatrobatcatroideditorEditorActivity(editText, editText2, spinner, dialogInterface, i);
            }
        }).setNegativeButton(R.string.cancel, (DialogInterface.OnClickListener) null).show();
        return true;
    }

    /* JADX INFO: renamed from: lambda$setupUI$16$org-catrobat-catroid-editor-EditorActivity, reason: not valid java name */
    /* synthetic */ void m5035lambda$setupUI$16$orgcatrobatcatroideditorEditorActivity(EditText editText, EditText editText2, Spinner spinner, DialogInterface dialogInterface, int i) {
        try {
            final int i2 = Integer.parseInt(editText.getText().toString());
            final float f = Float.parseFloat(editText2.getText().toString());
            final int selectedItemPosition = spinner.getSelectedItemPosition();
            final GameObject gameObject = this.currentSelectedObject;
            Gdx.f82app.postRunnable(new Runnable() { // from class: org.catrobat.catroid.editor.EditorActivity$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m5034lambda$setupUI$15$orgcatrobatcatroideditorEditorActivity(selectedItemPosition, f, gameObject, i2);
                }
            });
        } catch (Exception unused) {
            Toast.makeText(this, R.string.editor_3d_error_inputs, 0).show();
        }
    }

    /* JADX INFO: renamed from: lambda$setupUI$15$org-catrobat-catroid-editor-EditorActivity, reason: not valid java name */
    /* synthetic */ void m5034lambda$setupUI$15$orgcatrobatcatroideditorEditorActivity(int i, float f, GameObject gameObject, final int i2) {
        Commands.CompositeCommand compositeCommand = new Commands.CompositeCommand();
        Vector3 vector3 = new Vector3();
        if (i == 0) {
            vector3.set(f, 0.0f, 0.0f);
        } else if (i == 1) {
            vector3.set(0.0f, f, 0.0f);
        } else if (i == 2) {
            vector3.set(0.0f, 0.0f, f);
        } else {
            vector3.set(this.threeDManager.getCamera().direction).scl(f);
        }
        for (int i3 = 0; i3 < i2; i3++) {
            GameObject gameObjectCloneGameObject = this.sceneManager.cloneGameObject(gameObject, null);
            if (gameObjectCloneGameObject != null) {
                gameObjectCloneGameObject.transform.position.add(vector3);
                this.sceneManager.updateWorldTransforms();
                compositeCommand.addCommand(new Commands.AddCommand(this.sceneManager, gameObjectCloneGameObject));
                gameObject = gameObjectCloneGameObject;
            }
        }
        if (!compositeCommand.isEmpty()) {
            this.undoManager.pushCommand(compositeCommand);
        }
        runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.editor.EditorActivity$$ExternalSyntheticLambda33
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5033lambda$setupUI$14$orgcatrobatcatroideditorEditorActivity(i2);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$setupUI$14$org-catrobat-catroid-editor-EditorActivity, reason: not valid java name */
    /* synthetic */ void m5033lambda$setupUI$14$orgcatrobatcatroideditorEditorActivity(int i) {
        updateHierarchy();
        Toast.makeText(this, getString(R.string.editor_3d_created_objects_toast, new Object[]{Integer.valueOf(i)}), 0).show();
    }

    /* JADX INFO: renamed from: lambda$setupUI$18$org-catrobat-catroid-editor-EditorActivity, reason: not valid java name */
    /* synthetic */ void m5037lambda$setupUI$18$orgcatrobatcatroideditorEditorActivity(View view) {
        ThreeDManager threeDManager;
        if (this.currentSelectedObject == null || (threeDManager = this.threeDManager) == null) {
            return;
        }
        this.currentSelectedObject.transform.position.set(threeDManager.getCamera().position.cpy());
        this.sceneManager.rebuildGameObject(this.currentSelectedObject);
        Toast.makeText(this, R.string.editor_3d_moved_to_view, 0).show();
    }

    /* JADX INFO: renamed from: lambda$setupUI$19$org-catrobat-catroid-editor-EditorActivity, reason: not valid java name */
    /* synthetic */ void m5038lambda$setupUI$19$orgcatrobatcatroideditorEditorActivity(View view) {
        GameObject gameObjectCloneGameObject;
        GameObject gameObject = this.currentSelectedObject;
        if (gameObject == null || (gameObjectCloneGameObject = this.sceneManager.cloneGameObject(gameObject, null)) == null || gameObjectCloneGameObject.transform == null) {
            return;
        }
        this.sceneManager.updateWorldTransforms();
        UndoManager undoManager = this.undoManager;
        if (undoManager != null) {
            undoManager.pushCommand(new Commands.AddCommand(this.sceneManager, gameObjectCloneGameObject));
        }
        updateHierarchy();
        onObjectSelected(gameObjectCloneGameObject, false);
        Toast.makeText(this, R.string.editor_3d_duplicated, 0).show();
    }

    /* JADX INFO: renamed from: lambda$setupUI$20$org-catrobat-catroid-editor-EditorActivity, reason: not valid java name */
    /* synthetic */ void m5039lambda$setupUI$20$orgcatrobatcatroideditorEditorActivity(View view) {
        GameObject gameObject = this.currentSelectedObject;
        if (gameObject == null || this.threeDManager == null) {
            return;
        }
        Vector3 translation = gameObject.transform.worldTransform.getTranslation(new Vector3());
        Camera camera = this.threeDManager.getCamera();
        camera.position.set(translation.x + 7.0f, translation.y + 7.0f, translation.z + 7.0f);
        camera.up.set(0.0f, 1.0f, 0.0f);
        camera.lookAt(translation);
        camera.update();
        Toast.makeText(this, getString(R.string.editor_3d_focused_on, new Object[]{this.currentSelectedObject.f592name}), 0).show();
    }

    /* JADX INFO: renamed from: lambda$setupUI$21$org-catrobat-catroid-editor-EditorActivity, reason: not valid java name */
    /* synthetic */ void m5040lambda$setupUI$21$orgcatrobatcatroideditorEditorActivity(View view) {
        if (this.currentSelectedObject != null) {
            this.undoManager.pushCommand(new Commands.DeleteCommand(this.sceneManager, this.currentSelectedObject));
            this.sceneManager.removeGameObject(this.currentSelectedObject);
            onObjectSelected(null);
            updateHierarchy();
        }
    }

    @Override // org.catrobat.catroid.editor.HierarchyAdapter.OnItemClickListener
    public void onItemClick(GameObject gameObject) {
        this.gizmo.setSelectedObject(gameObject);
        onObjectSelected(gameObject);
        this.drawerLayout.closeDrawer(8388611);
    }

    private void setupCameraButton(int buttonId, final float vx, final float vy, final float vz) {
        ((Button) findViewById(buttonId)).setOnTouchListener(new View.OnTouchListener() { // from class: org.catrobat.catroid.editor.EditorActivity$$ExternalSyntheticLambda13
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return this.f$0.m5027xd2d32ed2(vx, vy, vz, view, motionEvent);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$setupCameraButton$22$org-catrobat-catroid-editor-EditorActivity, reason: not valid java name */
    /* synthetic */ boolean m5027xd2d32ed2(float f, float f2, float f3, View view, MotionEvent motionEvent) {
        if (this.editorListener == null) {
            return false;
        }
        if (motionEvent.getAction() == 0) {
            this.editorListener.onCameraMove(f, f2, f3);
        } else if (motionEvent.getAction() == 1) {
            this.editorListener.onCameraMove(-f, -f2, -f3);
        }
        return true;
    }

    private void updateAndSelect(GameObject go) {
        updateHierarchy();
        onObjectSelected(go);
        this.drawerLayout.closeDrawer(8388611);
    }

    public InspectorManager getInspectorManager() {
        return this.inspectorManager;
    }

    public void updateHierarchy() {
        if (this.sceneManager == null || this.hierarchyAdapter == null) {
            return;
        }
        this.hierarchyItems.clear();
        ArrayList arrayList = new ArrayList();
        for (GameObject gameObject : this.sceneManager.getAllGameObjects().values()) {
            if (gameObject.parentId == null) {
                arrayList.add(gameObject);
            }
        }
        arrayList.sort(Comparator.comparing(new Function() { // from class: org.catrobat.catroid.editor.EditorActivity$$ExternalSyntheticLambda11
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((GameObject) obj).f592name.toLowerCase();
            }
        }));
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            addGameObjectToHierarchyList((GameObject) it.next(), 0);
        }
        this.hierarchyAdapter.updateData(this.hierarchyItems);
    }

    private void addGameObjectToHierarchyList(GameObject go, int depth) {
        this.hierarchyItems.add(new HierarchyAdapter.HierarchyItem(go, String.join("", Collections.nCopies(depth, "    ")) + go.f592name));
        if (go.childrenIds.isEmpty()) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        Iterator<String> it = go.childrenIds.iterator();
        while (it.hasNext()) {
            GameObject gameObjectFindGameObject = this.sceneManager.findGameObject(it.next());
            if (gameObjectFindGameObject != null) {
                arrayList.add(gameObjectFindGameObject);
            }
        }
        arrayList.sort(Comparator.comparing(new Function() { // from class: org.catrobat.catroid.editor.EditorActivity$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((GameObject) obj).f592name.toLowerCase();
            }
        }));
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            addGameObjectToHierarchyList((GameObject) it2.next(), depth + 1);
        }
    }

    @Override // android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.editor_tools_menu, menu);
        getMenuInflater().inflate(R.menu.editor_toolbar_menu, menu);
        return true;
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        EditorListener editorListener = this.editorListener;
        if (editorListener != null) {
            if (itemId == R.id.tool_hand) {
                editorListener.setCurrentTool(EditorTool.HAND);
                return true;
            }
            if (itemId == R.id.tool_translate) {
                editorListener.setCurrentTool(EditorTool.TRANSLATE);
                return true;
            }
            if (itemId == R.id.tool_rotate) {
                editorListener.setCurrentTool(EditorTool.ROTATE);
                return true;
            }
            if (itemId == R.id.tool_scale) {
                editorListener.setCurrentTool(EditorTool.SCALE);
                return true;
            }
        }
        if (itemId == R.id.action_save_scene) {
            showSaveSceneDialog();
            return true;
        }
        if (itemId == R.id.action_load_scene) {
            showLoadSceneDialog();
            return true;
        }
        if (itemId == R.id.action_scene_settings) {
            showSceneSettingsDialog();
            return true;
        }
        if (itemId == R.id.action_clear_scene) {
            new AlertDialog.Builder(this, 2131952355).setTitle(R.string.editor_3d_clear_scene_title).setMessage(R.string.editor_3d_clear_scene_msg).setPositiveButton(R.string.editor_3d_clear_btn, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.editor.EditorActivity$$ExternalSyntheticLambda40
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.m5026x8d2c5008(dialogInterface, i);
                }
            }).setNegativeButton(R.string.cancel, (DialogInterface.OnClickListener) null).show();
            return true;
        }
        if (itemId == R.id.action_exit) {
            showExitConfirmationDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /* JADX INFO: renamed from: lambda$onOptionsItemSelected$25$org-catrobat-catroid-editor-EditorActivity, reason: not valid java name */
    /* synthetic */ void m5026x8d2c5008(DialogInterface dialogInterface, int i) {
        EditorStateManager.clearCache();
        EditorListener editorListener = this.editorListener;
        if (editorListener != null) {
            editorListener.resetEngine();
        }
        Toast.makeText(this, R.string.editor_3d_cleared_toast, 0).show();
    }

    private void cacheCurrentScene() {
        SceneManager sceneManager = this.sceneManager;
        if (sceneManager != null) {
            EditorStateManager.cacheScene(this.sceneManager.getJson().toJson(sceneManager.getCurrentSceneData()));
            Gdx.f82app.log("EditorActivity", "Scene JSON cached.");
        }
    }

    private void showSaveSceneDialog() {
        showSaveSceneDialog(null);
    }

    public void onEngineReset(SceneManager manager, ThreeDManager engine) {
        this.sceneManager = manager;
        this.threeDManager = engine;
        this.inspectorManager = new InspectorManager(this, this.sceneManager, this.threeDManager);
        EditorFragment editorFragment = (EditorFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (editorFragment != null && editorFragment.getListener() != null) {
            Gizmo gizmo = editorFragment.getListener().getGizmo();
            this.gizmo = gizmo;
            if (gizmo != null) {
                this.inspectorManager.setGizmo(gizmo);
            }
        }
        runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.editor.EditorActivity$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.setupUI();
            }
        });
    }

    private int libGdxColorToAndroidColor(Color gdxColor) {
        return android.graphics.Color.argb((int) (gdxColor.a * 255.0f), (int) (gdxColor.r * 255.0f), (int) (gdxColor.g * 255.0f), (int) (gdxColor.b * 255.0f));
    }

    private void showSceneSettingsDialog() {
        SeekBar seekBar;
        View viewInflate = getLayoutInflater().inflate(R.layout.dialog_scene_settings, (ViewGroup) null);
        final Button button = (Button) viewInflate.findViewById(R.id.btn_sky_color);
        SeekBar seekBar2 = (SeekBar) viewInflate.findViewById(R.id.seekbar_ambient_light);
        TextView textView = (TextView) viewInflate.findViewById(R.id.text_ambient_value);
        final TextView textView2 = (TextView) viewInflate.findViewById(R.id.text_skybox_path);
        Button button2 = (Button) viewInflate.findViewById(R.id.btn_select_skybox);
        final ImageButton imageButton = (ImageButton) viewInflate.findViewById(R.id.btn_clear_skybox);
        final EditText editText = (EditText) viewInflate.findViewById(R.id.edit_shadow_distance);
        final Spinner spinner = (Spinner) viewInflate.findViewById(R.id.spinner_shadow_resolution);
        Button button3 = (Button) viewInflate.findViewById(R.id.btn_apply_shadows);
        final CheckBox checkBox = (CheckBox) viewInflate.findViewById(R.id.checkbox_csm);
        final View viewFindViewById = viewInflate.findViewById(R.id.layout_csm_factor);
        final EditText editText2 = (EditText) viewInflate.findViewById(R.id.edit_csm_factor);
        final TextView textView3 = (TextView) viewInflate.findViewById(R.id.text_csm_hint);
        String[] strArr = {"2", "64", "128", "256", "512", "1024", "2048", "4096", "8192", "16384", "32768"};
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.simple_spinner_item_white_text, strArr);
        arrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_white_text);
        spinner.setAdapter((SpinnerAdapter) arrayAdapter);
        ThreeDManager threeDManager = this.threeDManager;
        if (threeDManager != null) {
            editText.setText(String.valueOf(threeDManager.getShadowSize()));
            editText.setText(String.valueOf(this.threeDManager.getShadowSize()));
            checkBox.setChecked(this.threeDManager.isCSMEnabled());
            editText2.setText(String.valueOf(this.threeDManager.getCsmSplitFactor()));
            String strValueOf = String.valueOf((int) this.threeDManager.getShadowResolution());
            seekBar = seekBar2;
            for (int i = 0; i < 11; i++) {
                if (strArr[i].equals(strValueOf)) {
                    spinner.setSelection(i);
                    break;
                }
            }
        } else {
            seekBar = seekBar2;
        }
        final Runnable runnable = new Runnable() { // from class: org.catrobat.catroid.editor.EditorActivity$$ExternalSyntheticLambda41
            @Override // java.lang.Runnable
            public final void run() {
                EditorActivity.lambda$showSceneSettingsDialog$26(checkBox, viewFindViewById, textView3);
            }
        };
        runnable.run();
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: org.catrobat.catroid.editor.EditorActivity$$ExternalSyntheticLambda42
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                runnable.run();
            }
        });
        button3.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.editor.EditorActivity$$ExternalSyntheticLambda43
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m5051x9a47c532(editText, spinner, checkBox, editText2, view);
            }
        });
        float f = this.sceneManager.ambientIntensity;
        final int iLibGdxColorToAndroidColor = libGdxColorToAndroidColor(new Color(this.sceneManager.skyR, this.sceneManager.skyG, this.sceneManager.skyB, 1.0f));
        button.setBackgroundColor(iLibGdxColorToAndroidColor);
        textView2.setText((this.sceneManager.skyboxPath == null || this.sceneManager.skyboxPath.isEmpty()) ? "None" : this.sceneManager.skyboxPath);
        imageButton.setVisibility((this.sceneManager.skyboxPath == null || this.sceneManager.skyboxPath.isEmpty()) ? 8 : 0);
        button2.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.editor.EditorActivity$$ExternalSyntheticLambda44
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m5052x5d342e91(textView2, imageButton, view);
            }
        });
        imageButton.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.editor.EditorActivity$$ExternalSyntheticLambda45
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m5053x1d853cbb(textView2, imageButton, view);
            }
        });
        SeekBar seekBar3 = seekBar;
        seekBar3.setProgress((int) (100.0f * f));
        textView.setText(String.format("%.2f", Float.valueOf(f)));
        seekBar3.setOnSeekBarChangeListener(new AnonymousClass5(textView));
        button.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.editor.EditorActivity$$ExternalSyntheticLambda46
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m5056x664a78d8(iLibGdxColorToAndroidColor, button, view);
            }
        });
        final EditText editText3 = (EditText) viewInflate.findViewById(R.id.edit_point_lights);
        final EditText editText4 = (EditText) viewInflate.findViewById(R.id.edit_spot_lights);
        final EditText editText5 = (EditText) viewInflate.findViewById(R.id.edit_dir_lights);
        final EditText editText6 = (EditText) viewInflate.findViewById(R.id.edit_bones);
        Button button4 = (Button) viewInflate.findViewById(R.id.btn_apply_performance);
        ThreeDManager.SceneSettings sceneSettings = this.threeDManager.getSceneSettings();
        editText3.setText(String.valueOf(sceneSettings.numPointLights));
        editText4.setText(String.valueOf(sceneSettings.numSpotLights));
        editText5.setText(String.valueOf(sceneSettings.numDirectionalLights));
        editText6.setText(String.valueOf(sceneSettings.numBones));
        final AlertDialog alertDialogCreate = new AlertDialog.Builder(this, 2131952355).setTitle("Scene Settings").setView(viewInflate).setPositiveButton("Close", (DialogInterface.OnClickListener) null).create();
        button4.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.editor.EditorActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m5058xec234b96(editText3, editText4, editText5, editText6, alertDialogCreate, view);
            }
        });
        alertDialogCreate.show();
    }

    static /* synthetic */ void lambda$showSceneSettingsDialog$26(CheckBox checkBox, View view, TextView textView) {
        boolean zIsChecked = checkBox.isChecked();
        view.setVisibility(zIsChecked ? 0 : 8);
        textView.setVisibility(zIsChecked ? 0 : 8);
    }

    /* JADX INFO: renamed from: lambda$showSceneSettingsDialog$28$org-catrobat-catroid-editor-EditorActivity, reason: not valid java name */
    /* synthetic */ void m5051x9a47c532(EditText editText, Spinner spinner, CheckBox checkBox, EditText editText2, View view) {
        try {
            float f = Float.parseFloat(editText.getText().toString());
            int i = Integer.parseInt(spinner.getSelectedItem().toString());
            boolean zIsChecked = checkBox.isChecked();
            float f2 = Float.parseFloat(editText2.getText().toString());
            ThreeDManager threeDManager = this.threeDManager;
            if (threeDManager != null) {
                threeDManager.setShadowSettings(f, i, zIsChecked, f2);
                SceneManager sceneManager = this.sceneManager;
                if (sceneManager != null && sceneManager.getCurrentSceneData() != null) {
                    this.sceneManager.getCurrentSceneData().useCSM = zIsChecked;
                    this.sceneManager.getCurrentSceneData().csmSplitFactor = f2;
                }
                Toast.makeText(this, "Shadow settings applied.", 0).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Invalid shadow values.", 0).show();
            e.printStackTrace();
        }
    }

    /* JADX INFO: renamed from: lambda$showSceneSettingsDialog$29$org-catrobat-catroid-editor-EditorActivity, reason: not valid java name */
    /* synthetic */ void m5052x5d342e91(TextView textView, ImageButton imageButton, View view) {
        showSkyboxPicker(textView, imageButton);
    }

    /* JADX INFO: renamed from: lambda$showSceneSettingsDialog$30$org-catrobat-catroid-editor-EditorActivity, reason: not valid java name */
    /* synthetic */ void m5053x1d853cbb(TextView textView, ImageButton imageButton, View view) {
        this.sceneManager.setSkybox(null);
        textView.setText("None");
        imageButton.setVisibility(8);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/editor/EditorActivity$5.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.editor.EditorActivity$5, reason: invalid class name */
    class AnonymousClass5 implements SeekBar.OnSeekBarChangeListener {
        final /* synthetic */ TextView val$ambientValueText;

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        AnonymousClass5(final TextView val$ambientValueText) {
            this.val$ambientValueText = val$ambientValueText;
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            this.val$ambientValueText.setText(String.format("%.2f", Float.valueOf(progress / 100.0f)));
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onStopTrackingTouch(SeekBar seekBar) {
            final float progress = seekBar.getProgress() / 100.0f;
            Gdx.f82app.postRunnable(new Runnable() { // from class: org.catrobat.catroid.editor.EditorActivity$5$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m5060xa853a1da(progress);
                }
            });
        }

        /* JADX INFO: renamed from: lambda$onStopTrackingTouch$0$org-catrobat-catroid-editor-EditorActivity$5, reason: not valid java name */
        /* synthetic */ void m5060xa853a1da(float f) {
            EditorActivity.this.sceneManager.setBackgroundLightIntensity(f);
        }
    }

    /* JADX INFO: renamed from: lambda$showSceneSettingsDialog$33$org-catrobat-catroid-editor-EditorActivity, reason: not valid java name */
    /* synthetic */ void m5056x664a78d8(int i, final Button button, View view) {
        ColorPickerDialogBuilder.with(this).setTitle("Choose Sky Color").initialColor(i).wheelType(ColorPickerView.WHEEL_TYPE.FLOWER).density(12).setPositiveButton("OK", new ColorPickerClickListener() { // from class: org.catrobat.catroid.editor.EditorActivity$$ExternalSyntheticLambda23
            @Override // com.flask.colorpicker.builder.ColorPickerClickListener
            public final void onClick(DialogInterface dialogInterface, int i2, Integer[] numArr) {
                this.f$0.m5055xa35e0f79(button, dialogInterface, i2, numArr);
            }
        }).setNegativeButton("Cancel", (DialogInterface.OnClickListener) null).build().show();
    }

    /* JADX INFO: renamed from: lambda$showSceneSettingsDialog$32$org-catrobat-catroid-editor-EditorActivity, reason: not valid java name */
    /* synthetic */ void m5055xa35e0f79(Button button, DialogInterface dialogInterface, int i, Integer[] numArr) {
        button.setBackgroundColor(i);
        android.graphics.Color.alpha(i);
        final float fRed = android.graphics.Color.red(i) / 255.0f;
        final float fGreen = android.graphics.Color.green(i) / 255.0f;
        final float fBlue = android.graphics.Color.blue(i) / 255.0f;
        Gdx.f82app.postRunnable(new Runnable() { // from class: org.catrobat.catroid.editor.EditorActivity$$ExternalSyntheticLambda24
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5054xe071a61a(fRed, fGreen, fBlue);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$showSceneSettingsDialog$31$org-catrobat-catroid-editor-EditorActivity, reason: not valid java name */
    /* synthetic */ void m5054xe071a61a(float f, float f2, float f3) {
        this.sceneManager.setSkyColor(f, f2, f3);
    }

    /* JADX INFO: renamed from: lambda$showSceneSettingsDialog$35$org-catrobat-catroid-editor-EditorActivity, reason: not valid java name */
    /* synthetic */ void m5058xec234b96(EditText editText, EditText editText2, EditText editText3, EditText editText4, final AlertDialog alertDialog, View view) {
        try {
            final ThreeDManager.SceneSettings sceneSettings = new ThreeDManager.SceneSettings();
            sceneSettings.numPointLights = Integer.parseInt(editText.getText().toString());
            sceneSettings.numSpotLights = Integer.parseInt(editText2.getText().toString());
            sceneSettings.numDirectionalLights = Integer.parseInt(editText3.getText().toString());
            sceneSettings.numBones = Math.min(110, Integer.parseInt(editText4.getText().toString()));
            new AlertDialog.Builder(this, 2131952355).setTitle("Restart 3D Engine").setMessage("Applying these settings requires reloading the 3D editor. Unsaved changes might be lost. Continue?").setPositiveButton("Restart", new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.editor.EditorActivity$$ExternalSyntheticLambda9
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.m5057x2936e237(sceneSettings, alertDialog, dialogInterface, i);
                }
            }).setNegativeButton("Cancel", (DialogInterface.OnClickListener) null).show();
        } catch (NumberFormatException unused) {
            Toast.makeText(this, "Please enter valid numbers.", 0).show();
        }
    }

    /* JADX INFO: renamed from: lambda$showSceneSettingsDialog$34$org-catrobat-catroid-editor-EditorActivity, reason: not valid java name */
    /* synthetic */ void m5057x2936e237(ThreeDManager.SceneSettings sceneSettings, AlertDialog alertDialog, DialogInterface dialogInterface, int i) {
        EditorListener editorListener = this.editorListener;
        if (editorListener != null) {
            editorListener.resetEngine(null, sceneSettings);
            Toast.makeText(this, "3D Engine restarted with new settings.", 0).show();
            alertDialog.dismiss();
        }
    }

    private void showSkyboxPicker(final TextView pathTextView, final View clearButton) {
        File[] fileArrListFiles = ProjectManager.getInstance().getCurrentProject().getFilesDir().listFiles();
        if (fileArrListFiles == null) {
            Toast.makeText(this, "Cannot access project files.", 0).show();
            return;
        }
        final ArrayList arrayList = new ArrayList();
        for (File file : fileArrListFiles) {
            String lowerCase = file.getName().toLowerCase();
            if (lowerCase.endsWith(Constants.DEFAULT_IMAGE_EXTENSION) || lowerCase.endsWith(Constants.JPEG_IMAGE_EXTENSION) || lowerCase.endsWith(".jpeg") || lowerCase.endsWith(".hdr")) {
                arrayList.add(file.getName());
            }
        }
        if (arrayList.isEmpty()) {
            Toast.makeText(this, "No suitable image files found in project.", 0).show();
        } else {
            new AlertDialog.Builder(this, 2131952355).setTitle("Select Skybox Texture").setItems((CharSequence[]) arrayList.toArray(new String[0]), new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.editor.EditorActivity$$ExternalSyntheticLambda22
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.m5059xcaf55e6a(arrayList, pathTextView, clearButton, dialogInterface, i);
                }
            }).show();
        }
    }

    /* JADX INFO: renamed from: lambda$showSkyboxPicker$36$org-catrobat-catroid-editor-EditorActivity, reason: not valid java name */
    /* synthetic */ void m5059xcaf55e6a(List list, TextView textView, View view, DialogInterface dialogInterface, int i) {
        String str = (String) list.get(i);
        this.sceneManager.skyboxPath = str;
        this.sceneManager.setSkybox(str);
        textView.setText(str);
        view.setVisibility(0);
    }

    private void showExitConfirmationDialog() {
        new AlertDialog.Builder(this, 2131952355).setTitle(R.string.editor_3d_exit_title).setMessage(R.string.editor_3d_exit_msg).setPositiveButton(R.string.editor_3d_save_exit, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.editor.EditorActivity$$ExternalSyntheticLambda25
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.m5047x2a77a76c(dialogInterface, i);
            }
        }).setNeutralButton(R.string.editor_3d_exit_no_save, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.editor.EditorActivity$$ExternalSyntheticLambda26
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.m5048xed6410cb(dialogInterface, i);
            }
        }).setNegativeButton(R.string.cancel, (DialogInterface.OnClickListener) null).show();
    }

    /* JADX INFO: renamed from: lambda$showExitConfirmationDialog$37$org-catrobat-catroid-editor-EditorActivity, reason: not valid java name */
    /* synthetic */ void m5047x2a77a76c(DialogInterface dialogInterface, int i) {
        showSaveSceneDialog(new Runnable() { // from class: org.catrobat.catroid.editor.EditorActivity$$ExternalSyntheticLambda15
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.finish();
            }
        });
    }

    /* JADX INFO: renamed from: lambda$showExitConfirmationDialog$38$org-catrobat-catroid-editor-EditorActivity, reason: not valid java name */
    /* synthetic */ void m5048xed6410cb(DialogInterface dialogInterface, int i) {
        finish();
    }

    private void showSaveSceneDialog(final Runnable onSaveComplete) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, 2131952355);
        final EditText editText = new EditText(builder.getContext());
        editText.setHint("my_level_1");
        builder.setTitle(R.string.editor_3d_save_scene_as).setMessage(R.string.editor_3d_enter_file_name).setView(editText).setPositiveButton(R.string.save, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.editor.EditorActivity$$ExternalSyntheticLambda21
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.m5050x128fd452(editText, onSaveComplete, dialogInterface, i);
            }
        }).setNegativeButton(R.string.cancel, (DialogInterface.OnClickListener) null).show();
    }

    /* JADX INFO: renamed from: lambda$showSaveSceneDialog$39$org-catrobat-catroid-editor-EditorActivity, reason: not valid java name */
    /* synthetic */ void m5050x128fd452(EditText editText, Runnable runnable, DialogInterface dialogInterface, int i) {
        String string = editText.getText().toString();
        if (string.isEmpty()) {
            Toast.makeText(this, R.string.editor_3d_file_name_empty, 0).show();
            return;
        }
        String str = string + ".rscene";
        this.sceneManager.saveScene(Gdx.files.absolute(new File(ProjectManager.getInstance().getCurrentProject().getFilesDir(), str).getAbsolutePath()));
        Toast.makeText(this, getString(R.string.editor_3d_scene_saved, new Object[]{str}), 0).show();
        if (runnable != null) {
            runnable.run();
        }
    }

    private void showLoadSceneDialog() {
        final File[] fileArrListFiles = ProjectManager.getInstance().getCurrentProject().getFilesDir().listFiles(new FilenameFilter() { // from class: org.catrobat.catroid.editor.EditorActivity$$ExternalSyntheticLambda7
            @Override // java.io.FilenameFilter
            public final boolean accept(File file, String str) {
                return str.toLowerCase().endsWith(".rscene");
            }
        });
        if (fileArrListFiles == null || fileArrListFiles.length == 0) {
            Toast.makeText(this, R.string.editor_3d_no_scenes_found, 0).show();
            return;
        }
        String[] strArr = new String[fileArrListFiles.length];
        for (int i = 0; i < fileArrListFiles.length; i++) {
            strArr[i] = fileArrListFiles[i].getName();
        }
        new AlertDialog.Builder(this, 2131952355).setTitle(R.string.editor_3d_load_scene).setItems(strArr, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.editor.EditorActivity$$ExternalSyntheticLambda8
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                this.f$0.m5049xe4f42492(fileArrListFiles, dialogInterface, i2);
            }
        }).show();
    }

    /* JADX INFO: renamed from: lambda$showLoadSceneDialog$41$org-catrobat-catroid-editor-EditorActivity, reason: not valid java name */
    /* synthetic */ void m5049xe4f42492(File[] fileArr, DialogInterface dialogInterface, int i) {
        File file = fileArr[i];
        FileHandle fileHandleAbsolute = Gdx.files.absolute(file.getAbsolutePath());
        EditorFragment editorFragment = (EditorFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (editorFragment == null || editorFragment.getListener() == null) {
            return;
        }
        ThreeDManager.SceneSettings sceneSettings = new ThreeDManager.SceneSettings();
        try {
            SceneData sceneData = (SceneData) new Json().fromJson(SceneData.class, fileHandleAbsolute.readString());
            if (sceneData != null && sceneData.renderSettings != null) {
                sceneSettings = sceneData.renderSettings;
            }
        } catch (Exception e) {
            Gdx.f82app.error("EditorActivity", "Could not parse scene settings, using defaults.", e);
        }
        editorFragment.getListener().resetEngine(fileHandleAbsolute, sceneSettings);
        updateHierarchy();
        onObjectSelected(null);
        Toast.makeText(this, getString(R.string.editor_3d_loading_scene, new Object[]{file.getName()}), 0).show();
    }

    private void checkForRecovery() {
        final File file = new File(ProjectManager.getInstance().getCurrentProject().getFilesDir(), AUTOSAVE_FILE_NAME);
        if (file.exists()) {
            new AlertDialog.Builder(this, 2131952355).setTitle(R.string.editor_3d_crash_recovery_title).setMessage(R.string.editor_3d_crash_recovery_msg).setCancelable(false).setPositiveButton(R.string.editor_3d_recover_btn, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.editor.EditorActivity$$ExternalSyntheticLambda19
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.m5021x807b2530(file, dialogInterface, i);
                }
            }).setNegativeButton(R.string.editor_3d_discard_btn, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.editor.EditorActivity$$ExternalSyntheticLambda20
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    file.delete();
                }
            }).show();
        }
    }

    /* JADX INFO: renamed from: lambda$checkForRecovery$42$org-catrobat-catroid-editor-EditorActivity, reason: not valid java name */
    /* synthetic */ void m5021x807b2530(File file, DialogInterface dialogInterface, int i) {
        try {
            try {
                this.sceneManager.loadScene(Gdx.files.absolute(file.getAbsolutePath()));
                updateHierarchy();
                onObjectSelected(null, false);
                Toast.makeText(this, R.string.editor_3d_recovered_success, 1).show();
            } catch (Exception unused) {
                Toast.makeText(this, R.string.editor_3d_recovered_failed, 0).show();
            }
        } finally {
            file.delete();
        }
    }

    public UndoManager getUndoManager() {
        return this.undoManager;
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        cacheCurrentScene();
        super.onPause();
    }

    private void setupCrashHandler() {
        this.defaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() { // from class: org.catrobat.catroid.editor.EditorActivity$$ExternalSyntheticLambda12
            @Override // java.lang.Thread.UncaughtExceptionHandler
            public final void uncaughtException(Thread thread, Throwable th) {
                this.f$0.m5028xa288947e(thread, th);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$setupCrashHandler$44$org-catrobat-catroid-editor-EditorActivity, reason: not valid java name */
    /* synthetic */ void m5028xa288947e(Thread thread, Throwable th) {
        if (this.sceneManager != null) {
            try {
                Log.e("EditorCrash", "FATAL CRASH DETECTED! Attempting emergency save...");
                File file = new File(ProjectManager.getInstance().getCurrentProject().getFilesDir(), AUTOSAVE_FILE_NAME);
                String json = this.sceneManager.getJson().toJson(this.sceneManager.getCurrentSceneData());
                FileWriter fileWriter = new FileWriter(file);
                try {
                    fileWriter.write(json);
                    fileWriter.close();
                    Log.e("EditorCrash", "Emergency save SUCCESSFUL: " + file.getAbsolutePath());
                } catch (Throwable th2) {
                    try {
                        fileWriter.close();
                    } catch (Throwable th3) {
                        th2.addSuppressed(th3);
                    }
                    throw th2;
                }
            } catch (Exception e) {
                Log.e("EditorCrash", "Emergency save FAILED!", e);
            }
        }
        Thread.UncaughtExceptionHandler uncaughtExceptionHandler = this.defaultCrashHandler;
        if (uncaughtExceptionHandler != null) {
            uncaughtExceptionHandler.uncaughtException(thread, th);
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        Thread.UncaughtExceptionHandler uncaughtExceptionHandler = this.defaultCrashHandler;
        if (uncaughtExceptionHandler != null) {
            Thread.setDefaultUncaughtExceptionHandler(uncaughtExceptionHandler);
        }
    }
}
