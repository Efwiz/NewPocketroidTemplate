package org.catrobat.catroid.editor;

import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.exifinterface.media.ExifInterface;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Predicate;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.content.EasingFunctions;
import org.catrobat.catroid.raptor.AnimationComponent;
import org.catrobat.catroid.raptor.CameraComponent;
import org.catrobat.catroid.raptor.ColliderShapeData;
import org.catrobat.catroid.raptor.Component;
import org.catrobat.catroid.raptor.FogComponent;
import org.catrobat.catroid.raptor.GameObject;
import org.catrobat.catroid.raptor.KeyframeComponent;
import org.catrobat.catroid.raptor.KeyframeData;
import org.catrobat.catroid.raptor.LightComponent;
import org.catrobat.catroid.raptor.MaterialComponent;
import org.catrobat.catroid.raptor.ParticleComponent;
import org.catrobat.catroid.raptor.ParticleCurvePoint;
import org.catrobat.catroid.raptor.ParticleSystem3DComponent;
import org.catrobat.catroid.raptor.PhysicsComponent;
import org.catrobat.catroid.raptor.PostProcessingComponent;
import org.catrobat.catroid.raptor.PostProcessingData;
import org.catrobat.catroid.raptor.PrefabComponent;
import org.catrobat.catroid.raptor.RenderComponent;
import org.catrobat.catroid.raptor.SceneManager;
import org.catrobat.catroid.raptor.ScriptComponent;
import org.catrobat.catroid.raptor.ThreeDManager;
import org.catrobat.catroid.raptor.TransformComponent;
import org.catrobat.catroid.ui.SpriteActivity;
import org.slf4j.Marker;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/editor/InspectorManager.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class InspectorManager {
    private final EditorActivity activity;
    private final LinearLayout container;
    private Gizmo gizmo;
    private final LayoutInflater inflater;
    private final TextView inspectorTitle;
    private SceneManager sceneManager;
    private GameObject selectedObject;
    private final ThreeDManager threeDManager;
    private ColliderShapeData selectedCollider = null;
    private final Vector3 tempPosition = new Vector3();
    private final Quaternion tempRotation = new Quaternion();
    private final Vector3 tempScale = new Vector3(1.0f, 1.0f, 1.0f);
    private boolean isPreviewingAnimation = false;
    private Handler ps3dUpdateHandler = new Handler(Looper.getMainLooper());
    private Runnable pendingPS3DUpdate = null;
    private boolean isShowingColliders = false;

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/editor/InspectorManager$BoolConsumer.class
      classes14.dex
     */
    interface BoolConsumer {
        void accept(boolean value);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/editor/InspectorManager$ColorConsumer.class
      classes14.dex
     */
    interface ColorConsumer {
        void accept(Color value);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/editor/InspectorManager$EnumConsumer.class
      classes14.dex
     */
    interface EnumConsumer<T> {
        void accept(T value);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/editor/InspectorManager$FloatConsumer.class
      classes14.dex
     */
    interface FloatConsumer {
        void accept(float value);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/editor/InspectorManager$ModuleContentBuilder.class
      classes14.dex
     */
    interface ModuleContentBuilder {
        LinearLayout build();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/editor/InspectorManager$QuaternionUpdater.class
      classes14.dex
     */
    interface QuaternionUpdater {
        void update(GameObject go, Quaternion quat);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/editor/InspectorManager$StringConsumer.class
      classes14.dex
     */
    interface StringConsumer {
        void accept(String value);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/editor/InspectorManager$StringUpdater.class
      classes14.dex
     */
    interface StringUpdater {
        void update(String value);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/editor/InspectorManager$Vector3Updater.class
      classes14.dex
     */
    interface Vector3Updater {
        void update(GameObject go, Vector3 vec);
    }

    static /* synthetic */ void lambda$createFogView$52(DialogInterface dialogInterface, int i) {
    }

    static /* synthetic */ void lambda$createLightView$300(DialogInterface dialogInterface, int i) {
    }

    public InspectorManager(EditorActivity activity, SceneManager sceneManager, ThreeDManager threeDManager) {
        this.activity = activity;
        this.inflater = activity.getLayoutInflater();
        this.sceneManager = sceneManager;
        this.threeDManager = threeDManager;
        this.container = (LinearLayout) activity.findViewById(R.id.inspector_container);
        this.inspectorTitle = (TextView) activity.findViewById(R.id.inspector_title);
    }

    public void setGizmo(Gizmo gizmo) {
        this.gizmo = gizmo;
    }

    public ColliderShapeData getSelectedCollider() {
        return this.selectedCollider;
    }

    public void updateSceneManager(SceneManager newManager) {
        this.sceneManager = newManager;
    }

    public GameObject getSelectedObject() {
        return this.selectedObject;
    }

    /* JADX INFO: renamed from: populateInspector, reason: merged with bridge method [inline-methods] */
    public void m5171x5f6795b(final GameObject go) {
        this.selectedObject = go;
        this.container.removeAllViews();
        if (go == null) {
            this.inspectorTitle.setText("Inspector");
            return;
        }
        this.inspectorTitle.setText(go.f592name);
        View viewInflate = this.inflater.inflate(R.layout.inspector_active_toggle, (ViewGroup) this.container, false);
        CheckBox checkBox = (CheckBox) viewInflate.findViewById(R.id.checkbox_is_active);
        checkBox.setChecked(go.isActive);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda116
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                this.f$0.m5317x3977647d(compoundButton, z);
            }
        });
        final EditText editText = (EditText) viewInflate.findViewById(R.id.edit_object_name);
        editText.setText(go.f592name);
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda117
            @Override // android.view.View.OnFocusChangeListener
            public final void onFocusChange(View view, boolean z) {
                this.f$0.m5318xf2eef21c(editText, view, z);
            }
        });
        this.container.addView(viewInflate);
        createTransformView(go);
        if (go.hasComponent(RenderComponent.class)) {
            createRenderView(go);
        }
        if (go.hasComponent(PhysicsComponent.class)) {
            createPhysicsView(go);
        }
        if (go.hasComponent(LightComponent.class)) {
            createLightView(go);
        }
        if (go.hasComponent(AnimationComponent.class)) {
            createAnimationView(go);
        }
        if (go.hasComponent(CameraComponent.class)) {
            createCameraView(go);
        }
        if (go.hasComponent(MaterialComponent.class)) {
            createMaterialView(go);
        }
        if (go.hasComponent(PostProcessingComponent.class)) {
            createPostProcessingView(go);
        }
        if (go.hasComponent(ParticleComponent.class)) {
            createParticleView(go);
        }
        if (go.hasComponent(ParticleSystem3DComponent.class)) {
            createParticleSystem3DView(go);
        }
        if (go.hasComponent(FogComponent.class)) {
            createFogView(go);
        }
        if (go.hasComponent(KeyframeComponent.class)) {
            createKeyframeView(go);
        }
        if (go.hasComponent(PrefabComponent.class)) {
            createPrefabView(go);
        }
        Iterator it = go.getComponents(ScriptComponent.class).iterator();
        while (it.hasNext()) {
            createScriptView(go, (ScriptComponent) it.next());
        }
        View viewInflate2 = this.inflater.inflate(R.layout.inspector_footer, (ViewGroup) this.container, false);
        viewInflate2.findViewById(R.id.btn_add_component).setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda118
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m5319xac667fbb(go, view);
            }
        });
        this.container.addView(viewInflate2);
        View view = new View(this.activity);
        int i = (int) (this.activity.getResources().getDisplayMetrics().density * 16.0f);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, 1);
        layoutParams.setMargins(0, i, 0, i);
        view.setLayoutParams(layoutParams);
        view.setBackgroundColor(1090519039);
        this.container.addView(view);
        Button button = new Button(this.activity, null, 0, 2131952417);
        button.setText(R.string.editor_3d_clone);
        button.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda119
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m5320x65de0d5a(go, view2);
            }
        });
        this.container.addView(button);
        Button button2 = new Button(this.activity);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-1, -2);
        layoutParams2.setMargins(0, (int) (this.activity.getResources().getDisplayMetrics().density * 8.0f), 0, 0);
        button2.setLayoutParams(layoutParams2);
        button2.setText(R.string.editor_3d_delete_object);
        button2.setTextColor(android.graphics.Color.parseColor("#FF5252"));
        button2.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda120
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.m5322xd8cd2898(go, view2);
            }
        });
        this.container.addView(button2);
    }

    /* JADX INFO: renamed from: lambda$populateInspector$0$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5317x3977647d(CompoundButton compoundButton, boolean z) {
        GameObject gameObject = this.selectedObject;
        if (gameObject != null) {
            this.sceneManager.setObjectActive(gameObject, z);
            this.activity.updateHierarchy();
        }
    }

    /* JADX INFO: renamed from: lambda$populateInspector$1$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5318xf2eef21c(EditText editText, View view, boolean z) {
        if (z || this.selectedObject == null) {
            return;
        }
        String string = editText.getText().toString();
        String str = this.selectedObject.f592name;
        if (string.equals(str)) {
            return;
        }
        if (this.sceneManager.renameGameObject(this.selectedObject, string)) {
            this.activity.updateHierarchy();
        } else {
            Toast.makeText(this.activity, "Invalid or duplicate name!", 0).show();
            editText.setText(str);
        }
    }

    /* JADX INFO: renamed from: lambda$populateInspector$2$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5319xac667fbb(GameObject gameObject, View view) {
        showAddComponentDialog(gameObject);
    }

    /* JADX INFO: renamed from: lambda$populateInspector$3$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5320x65de0d5a(GameObject gameObject, View view) {
        GameObject gameObjectDuplicateGameObject = this.sceneManager.duplicateGameObject(gameObject);
        if (gameObjectDuplicateGameObject != null) {
            this.activity.onObjectSelected(gameObjectDuplicateGameObject);
            this.activity.updateHierarchy();
        }
    }

    /* JADX INFO: renamed from: lambda$populateInspector$5$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5322xd8cd2898(final GameObject gameObject, View view) {
        new AlertDialog.Builder(this.activity).setTitle(R.string.editor_3d_delete_object).setMessage(this.activity.getString(R.string.editor_3d_delete_object_confirm, new Object[]{gameObject.f592name})).setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda324
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.m5321x1f559af9(gameObject, dialogInterface, i);
            }
        }).setNegativeButton(R.string.cancel, (DialogInterface.OnClickListener) null).show();
    }

    /* JADX INFO: renamed from: lambda$populateInspector$4$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5321x1f559af9(GameObject gameObject, DialogInterface dialogInterface, int i) {
        this.sceneManager.removeGameObject(gameObject);
        this.activity.onObjectSelected(null);
        this.activity.updateHierarchy();
    }

    private void createPrefabView(final GameObject go) {
        addComponentHeader(R.string.component_prefab, true, false, new Runnable() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda98
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5305x83011f84(go);
            }
        });
        View viewInflate = this.inflater.inflate(R.layout.inspector_script, (ViewGroup) this.container, false);
        setWhiteTextToAllChildren((ViewGroup) viewInflate);
        TextView textView = (TextView) viewInflate.findViewById(R.id.text_script_path);
        Button button = (Button) viewInflate.findViewById(R.id.btn_select_script);
        final PrefabComponent prefabComponent = (PrefabComponent) go.getComponent(PrefabComponent.class);
        textView.setText(prefabComponent.prefabFilePath != null ? prefabComponent.prefabFilePath : "None (Select .rscene)");
        button.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda100
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m5306x3c78ad23(prefabComponent, go, view);
            }
        });
        this.container.addView(viewInflate);
    }

    /* JADX INFO: renamed from: lambda$createPrefabView$7$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5305x83011f84(GameObject gameObject) {
        PrefabComponent prefabComponent = (PrefabComponent) gameObject.getComponent(PrefabComponent.class);
        if (prefabComponent != null && prefabComponent.spawnedInstances != null) {
            Iterator<String> it = prefabComponent.spawnedInstances.iterator();
            while (it.hasNext()) {
                GameObject gameObjectFindGameObject = this.sceneManager.findGameObject(it.next());
                if (gameObjectFindGameObject != null) {
                    this.sceneManager.removeGameObject(gameObjectFindGameObject);
                }
            }
            gameObject.childrenIds.removeAll(prefabComponent.spawnedInstances);
        }
        gameObject.components.removeIf(new Predicate() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda187
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return InspectorManager.lambda$createPrefabView$6((Component) obj);
            }
        });
        m5171x5f6795b(gameObject);
        this.activity.updateHierarchy();
    }

    static /* synthetic */ boolean lambda$createPrefabView$6(Component component) {
        return component instanceof PrefabComponent;
    }

    /* JADX INFO: renamed from: lambda$createPrefabView$8$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5306x3c78ad23(PrefabComponent prefabComponent, GameObject gameObject, View view) {
        showPrefabPicker(prefabComponent, gameObject);
    }

    private void showPrefabPicker(final PrefabComponent prefab, final GameObject rootGo) {
        File[] fileArrListFiles = ProjectManager.getInstance().getCurrentProject().getFilesDir().listFiles(new FilenameFilter() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda6
            @Override // java.io.FilenameFilter
            public final boolean accept(File file, String str) {
                return str.toLowerCase().endsWith(".rscene");
            }
        });
        if (fileArrListFiles == null || fileArrListFiles.length == 0) {
            Toast.makeText(this.activity, "No .rscene files found", 0).show();
            return;
        }
        final String[] strArr = new String[fileArrListFiles.length];
        for (int i = 0; i < fileArrListFiles.length; i++) {
            strArr[i] = fileArrListFiles[i].getName();
        }
        new android.app.AlertDialog.Builder(this.activity).setTitle("Select Prefab").setItems(strArr, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda7
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                this.f$0.m5342xf443b2c2(prefab, strArr, rootGo, dialogInterface, i2);
            }
        }).show();
    }

    /* JADX INFO: renamed from: lambda$showPrefabPicker$10$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5342xf443b2c2(PrefabComponent prefabComponent, String[] strArr, GameObject gameObject, DialogInterface dialogInterface, int i) {
        prefabComponent.prefabFilePath = strArr[i];
        this.sceneManager.rebuildGameObject(gameObject);
        m5171x5f6795b(gameObject);
        this.activity.updateHierarchy();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setWhiteTextToAllChildren(ViewGroup vg) {
        for (int i = 0; i < vg.getChildCount(); i++) {
            View childAt = vg.getChildAt(i);
            if (childAt instanceof TextView) {
                TextView textView = (TextView) childAt;
                textView.setTextColor(-1);
                textView.setHintTextColor(-3355444);
            } else if (childAt instanceof ViewGroup) {
                setWhiteTextToAllChildren((ViewGroup) childAt);
            }
        }
    }

    private void createKeyframeView(final GameObject go) {
        boolean z = false;
        addComponentHeader(R.string.component_keyframe, true, false, new Runnable() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda306
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5168x66a0b540(go);
            }
        });
        View viewInflate = this.inflater.inflate(R.layout.inspector_keyframe_component, (ViewGroup) this.container, false);
        final KeyframeComponent keyframeComponent = (KeyframeComponent) go.getComponent(KeyframeComponent.class);
        CheckBox checkBox = (CheckBox) viewInflate.findViewById(R.id.keyframe_autostart);
        CheckBox checkBox2 = (CheckBox) viewInflate.findViewById(R.id.keyframe_loop);
        checkBox.setChecked(keyframeComponent.autoStart);
        checkBox2.setChecked(keyframeComponent.looping);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda307
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                keyframeComponent.autoStart = z2;
            }
        });
        checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda308
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                keyframeComponent.looping = z2;
            }
        });
        ImageButton imageButton = (ImageButton) viewInflate.findViewById(R.id.btn_keyframe_play);
        ImageButton imageButton2 = (ImageButton) viewInflate.findViewById(R.id.btn_keyframe_stop);
        SeekBar seekBar = (SeekBar) viewInflate.findViewById(R.id.keyframe_timeline_slider);
        imageButton.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda309
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m5169x93075e1d(go, keyframeComponent, view);
            }
        });
        imageButton2.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda310
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m5170x4c7eebbc(keyframeComponent, go, view);
            }
        });
        seekBar.setMax(1000);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: org.catrobat.catroid.editor.InspectorManager.1
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar2) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar2) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar2, int progress, boolean fromUser) {
                if (fromUser) {
                    InspectorManager.this.sceneManager.setKeyframeAnimationTime(go.f591id, (progress / 1000.0f) * keyframeComponent.getDuration());
                    InspectorManager.this.m5171x5f6795b(go);
                }
            }
        });
        LinearLayout linearLayout = (LinearLayout) viewInflate.findViewById(R.id.keyframe_list_container);
        linearLayout.removeAllViews();
        EditorActivity editorActivity = this.activity;
        ArrayAdapter arrayAdapter = new ArrayAdapter(editorActivity, R.layout.simple_spinner_item_white_text, editorActivity.getResources().getStringArray(R.array.brick_easing_types));
        arrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_white_text);
        int i = 0;
        while (i < keyframeComponent.keyframes.size()) {
            final KeyframeData keyframeData = keyframeComponent.keyframes.get(i);
            View viewInflate2 = this.inflater.inflate(R.layout.inspector_keyframe_item, linearLayout, z);
            final EditText editText = (EditText) viewInflate2.findViewById(R.id.edit_keyframe_time);
            Spinner spinner = (Spinner) viewInflate2.findViewById(R.id.spinner_keyframe_easing);
            ImageButton imageButton3 = (ImageButton) viewInflate2.findViewById(R.id.btn_delete_keyframe);
            editText.setText(String.format(Locale.US, "%.2f", Float.valueOf(keyframeData.time)));
            View view = viewInflate;
            LinearLayout linearLayout2 = linearLayout;
            editText.setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda311
                @Override // android.view.View.OnFocusChangeListener
                public final void onFocusChange(View view2, boolean z2) {
                    this.f$0.m5172xbf6e06fa(editText, keyframeData, keyframeComponent, go, view2, z2);
                }
            });
            editText.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda312
                @Override // android.widget.TextView.OnEditorActionListener
                public final boolean onEditorAction(TextView textView, int i2, KeyEvent keyEvent) {
                    return InspectorManager.lambda$createKeyframeView$19(editText, textView, i2, keyEvent);
                }
            });
            spinner.setAdapter((SpinnerAdapter) arrayAdapter);
            spinner.setSelection(keyframeData.easingToNext.ordinal());
            if (i == 0) {
                imageButton3.setVisibility(8);
            }
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: org.catrobat.catroid.editor.InspectorManager.2
                @Override // android.widget.AdapterView.OnItemSelectedListener
                public void onNothingSelected(AdapterView<?> parent) {
                }

                @Override // android.widget.AdapterView.OnItemSelectedListener
                public void onItemSelected(AdapterView<?> parent, View view2, int pos, long id2) {
                    keyframeData.easingToNext = EasingFunctions.EasingType.values()[pos];
                }
            });
            imageButton3.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda314
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    this.f$0.m5173x692bc043(keyframeComponent, keyframeData, go, view2);
                }
            });
            viewInflate2.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda315
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    this.f$0.m5174x22a34de2(go, keyframeData, view2);
                }
            });
            Gizmo gizmo = this.gizmo;
            if (gizmo != null && gizmo.getSelectedKeyframe() == keyframeData) {
                viewInflate2.setBackgroundColor(1436129791);
            }
            linearLayout2.addView(viewInflate2);
            i++;
            linearLayout = linearLayout2;
            viewInflate = view;
            z = false;
        }
        View view2 = viewInflate;
        ((Button) view2.findViewById(R.id.btn_add_keyframe)).setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda316
            @Override // android.view.View.OnClickListener
            public final void onClick(View view3) {
                this.f$0.m5175xdc1adb81(go, keyframeComponent, view3);
            }
        });
        this.container.addView(view2);
    }

    static /* synthetic */ boolean lambda$createKeyframeView$11(Component component) {
        return component instanceof KeyframeComponent;
    }

    /* JADX INFO: renamed from: lambda$createKeyframeView$12$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5168x66a0b540(GameObject gameObject) {
        gameObject.components.removeIf(new Predicate() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda287
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return InspectorManager.lambda$createKeyframeView$11((Component) obj);
            }
        });
        m5171x5f6795b(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createKeyframeView$15$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5169x93075e1d(GameObject gameObject, KeyframeComponent keyframeComponent, View view) {
        if (!this.isPreviewingAnimation) {
            KeyframeData keyframeData = ((KeyframeComponent) gameObject.getComponent(KeyframeComponent.class)).keyframes.get(0);
            this.tempPosition.set(keyframeData.position);
            this.tempRotation.set(keyframeData.rotation);
            this.tempScale.set(keyframeData.scale);
            this.isPreviewingAnimation = true;
        }
        keyframeComponent.isPlaying = true;
        keyframeComponent.currentTime = 0.0f;
        this.sceneManager.setEditorMode(false);
    }

    /* JADX INFO: renamed from: lambda$createKeyframeView$16$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5170x4c7eebbc(KeyframeComponent keyframeComponent, GameObject gameObject, View view) {
        keyframeComponent.currentTime = 0.0f;
        keyframeComponent.isPlaying = false;
        this.sceneManager.setEditorMode(true);
        if (this.isPreviewingAnimation) {
            gameObject.transform.position.set(this.tempPosition);
            gameObject.transform.rotation.set(this.tempRotation);
            gameObject.transform.scale.set(this.tempScale);
            this.isPreviewingAnimation = false;
            m5171x5f6795b(gameObject);
        }
    }

    /* JADX INFO: renamed from: lambda$createKeyframeView$18$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5172xbf6e06fa(EditText editText, KeyframeData keyframeData, KeyframeComponent keyframeComponent, final GameObject gameObject, View view, boolean z) {
        if (z) {
            return;
        }
        try {
            keyframeData.time = Math.max(0.0f, Float.parseFloat(editText.getText().toString()));
            keyframeComponent.sortKeyframes();
            this.activity.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda11
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m5171x5f6795b(gameObject);
                }
            });
        } catch (Exception unused) {
            editText.setText(String.format(Locale.US, "%.2f", Float.valueOf(keyframeData.time)));
        }
    }

    static /* synthetic */ boolean lambda$createKeyframeView$19(EditText editText, TextView textView, int i, KeyEvent keyEvent) {
        if (i == 6) {
            editText.clearFocus();
        }
        return false;
    }

    /* JADX INFO: renamed from: lambda$createKeyframeView$20$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5173x692bc043(KeyframeComponent keyframeComponent, KeyframeData keyframeData, GameObject gameObject, View view) {
        keyframeComponent.keyframes.remove(keyframeData);
        m5171x5f6795b(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createKeyframeView$21$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5174x22a34de2(GameObject gameObject, KeyframeData keyframeData, View view) {
        Gizmo gizmo = this.gizmo;
        if (gizmo != null) {
            gizmo.setSelectedKeyframe(gameObject, keyframeData);
        }
        m5171x5f6795b(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createKeyframeView$22$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5175xdc1adb81(GameObject gameObject, KeyframeComponent keyframeComponent, View view) {
        KeyframeData keyframeData = new KeyframeData();
        keyframeData.position.set(gameObject.transform.position);
        keyframeData.rotation.set(gameObject.transform.rotation);
        keyframeData.scale.set(gameObject.transform.scale);
        keyframeData.time = keyframeComponent.getDuration() + 1.0f;
        keyframeComponent.keyframes.add(keyframeData);
        keyframeComponent.sortKeyframes();
        m5171x5f6795b(gameObject);
    }

    private int libGdxColorToAndroidColor(Color gdxColor) {
        return android.graphics.Color.argb((int) (gdxColor.a * 255.0f), (int) (gdxColor.r * 255.0f), (int) (gdxColor.g * 255.0f), (int) (gdxColor.b * 255.0f));
    }

    private void createAnimationView(final GameObject go) {
        addComponentHeader(R.string.component_animation, true, false, new Runnable() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda180
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5160x5cb51dc3(go);
            }
        });
        View viewInflate = this.inflater.inflate(R.layout.inspector_animation, (ViewGroup) this.container, false);
        setWhiteTextToAllChildren((ViewGroup) viewInflate);
        Spinner spinner = (Spinner) viewInflate.findViewById(R.id.spinner_animations);
        EditText editText = (EditText) viewInflate.findViewById(R.id.edit_anim_speed);
        EditText editText2 = (EditText) viewInflate.findViewById(R.id.edit_anim_transition);
        CheckBox checkBox = (CheckBox) viewInflate.findViewById(R.id.checkbox_anim_loop);
        Array<String> animationNames = this.sceneManager.getAnimationNames(go);
        ArrayList arrayList = new ArrayList();
        arrayList.add("None");
        Array.ArrayIterator<String> it = animationNames.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next());
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter(this.activity, R.layout.simple_spinner_item_white_text, arrayList);
        arrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_white_text);
        spinner.setAdapter((SpinnerAdapter) arrayAdapter);
        final AnimationComponent animationComponent = (AnimationComponent) go.getComponent(AnimationComponent.class);
        if (animationComponent == null) {
            animationComponent = new AnimationComponent();
            go.addComponent(animationComponent);
        }
        if (animationComponent.animationName != null) {
            spinner.setSelection(Math.max(0, arrayList.indexOf(animationComponent.animationName)));
        } else {
            spinner.setSelection(0);
        }
        editText.setText(String.valueOf(animationComponent.speed));
        editText2.setText(String.valueOf(animationComponent.transitionTime));
        checkBox.setChecked(animationComponent.loops == -1);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: org.catrobat.catroid.editor.InspectorManager.3
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> parent) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id2) {
                String str = (String) parent.getItemAtPosition(position);
                AnimationComponent animationComponent2 = animationComponent;
                if (str.equals("None")) {
                    str = null;
                }
                animationComponent2.animationName = str;
                InspectorManager.this.sceneManager.playAnimationFromComponent(go);
            }
        });
        addSimpleTextListener(editText, new StringUpdater() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda181
            @Override // org.catrobat.catroid.editor.InspectorManager.StringUpdater
            public final void update(String str) {
                this.f$0.m5161x162cab62(animationComponent, go, str);
            }
        });
        addSimpleTextListener(editText2, new StringUpdater() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda182
            @Override // org.catrobat.catroid.editor.InspectorManager.StringUpdater
            public final void update(String str) {
                this.f$0.m5162xcfa43901(animationComponent, go, str);
            }
        });
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda183
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                this.f$0.m5163x891bc6a0(animationComponent, go, compoundButton, z);
            }
        });
        this.container.addView(viewInflate);
    }

    static /* synthetic */ boolean lambda$createAnimationView$23(Component component) {
        return component instanceof AnimationComponent;
    }

    /* JADX INFO: renamed from: lambda$createAnimationView$24$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5160x5cb51dc3(GameObject gameObject) {
        gameObject.components.removeIf(new Predicate() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda172
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return InspectorManager.lambda$createAnimationView$23((Component) obj);
            }
        });
        this.sceneManager.playAnimationFromComponent(gameObject);
        m5171x5f6795b(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createAnimationView$25$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5161x162cab62(AnimationComponent animationComponent, GameObject gameObject, String str) {
        try {
            animationComponent.speed = Float.parseFloat(str);
        } catch (Exception unused) {
        }
        this.sceneManager.playAnimationFromComponent(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createAnimationView$26$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5162xcfa43901(AnimationComponent animationComponent, GameObject gameObject, String str) {
        try {
            animationComponent.transitionTime = Float.parseFloat(str);
        } catch (Exception unused) {
        }
        this.sceneManager.playAnimationFromComponent(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createAnimationView$27$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5163x891bc6a0(AnimationComponent animationComponent, GameObject gameObject, CompoundButton compoundButton, boolean z) {
        animationComponent.loops = z ? -1 : 1;
        this.sceneManager.playAnimationFromComponent(gameObject);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/editor/InspectorManager$4.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.editor.InspectorManager$4, reason: invalid class name */
    class AnonymousClass4 implements Runnable {
        final /* synthetic */ GameObject val$go;

        AnonymousClass4(final GameObject val$go) {
            this.val$go = val$go;
        }

        static /* synthetic */ boolean lambda$run$0(Component component) {
            return component instanceof CameraComponent;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.val$go.components.removeIf(new Predicate() { // from class: org.catrobat.catroid.editor.InspectorManager$4$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return InspectorManager.AnonymousClass4.lambda$run$0((Component) obj);
                }
            });
            InspectorManager.this.sceneManager.engine.removeEditorProxy(this.val$go.f591id);
            InspectorManager.this.m5171x5f6795b(this.val$go);
        }
    }

    private void createCameraView(final GameObject go) {
        addComponentHeader(R.string.component_camera, true, false, new AnonymousClass4(go));
        View viewInflate = this.inflater.inflate(R.layout.inspector_camera, (ViewGroup) this.container, false);
        setWhiteTextToAllChildren((ViewGroup) viewInflate);
        final CameraComponent cameraComponent = (CameraComponent) go.getComponent(CameraComponent.class);
        CheckBox checkBox = (CheckBox) viewInflate.findViewById(R.id.check_main_camera);
        final EditText editText = (EditText) viewInflate.findViewById(R.id.edit_cam_fov);
        final EditText editText2 = (EditText) viewInflate.findViewById(R.id.edit_cam_near);
        final EditText editText3 = (EditText) viewInflate.findViewById(R.id.edit_cam_far);
        checkBox.setChecked(cameraComponent.isMainCamera);
        editText.setText(String.valueOf(cameraComponent.fieldOfView));
        editText2.setText(String.valueOf(cameraComponent.nearPlane));
        editText3.setText(String.valueOf(cameraComponent.farPlane));
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda234
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                this.f$0.m5164x495916b2(go, cameraComponent, compoundButton, z);
            }
        });
        DelayedTextWatcher delayedTextWatcher = new DelayedTextWatcher(new Runnable() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda235
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5165x2d0a451(cameraComponent, editText, editText2, editText3, go);
            }
        });
        editText.addTextChangedListener(delayedTextWatcher);
        editText2.addTextChangedListener(delayedTextWatcher);
        editText3.addTextChangedListener(delayedTextWatcher);
        this.container.addView(viewInflate);
    }

    /* JADX INFO: renamed from: lambda$createCameraView$28$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5164x495916b2(GameObject gameObject, CameraComponent cameraComponent, CompoundButton compoundButton, boolean z) {
        if (z) {
            for (GameObject gameObject2 : this.sceneManager.getAllGameObjects().values()) {
                CameraComponent cameraComponent2 = (CameraComponent) gameObject2.getComponent(CameraComponent.class);
                if (gameObject2 != gameObject && cameraComponent2 != null) {
                    cameraComponent2.isMainCamera = false;
                }
            }
        }
        cameraComponent.isMainCamera = z;
        this.sceneManager.setCameraComponent(gameObject, cameraComponent);
        if (z) {
            m5171x5f6795b(gameObject);
        }
    }

    /* JADX INFO: renamed from: lambda$createCameraView$29$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5165x2d0a451(CameraComponent cameraComponent, EditText editText, EditText editText2, EditText editText3, GameObject gameObject) {
        try {
            cameraComponent.fieldOfView = Float.parseFloat(editText.getText().toString());
            cameraComponent.nearPlane = Float.parseFloat(editText2.getText().toString());
            cameraComponent.farPlane = Float.parseFloat(editText3.getText().toString());
            this.sceneManager.setCameraComponent(gameObject, cameraComponent);
        } catch (Exception unused) {
        }
    }

    private void createTransformView(GameObject go) {
        addComponentHeader(R.string.component_transform, false, false, null);
        View viewInflate = this.inflater.inflate(R.layout.inspector_transform, (ViewGroup) this.container, false);
        if (viewInflate instanceof ViewGroup) {
            setWhiteTextToAllChildren((ViewGroup) viewInflate);
        }
        ImageButton imageButton = (ImageButton) viewInflate.findViewById(R.id.btn_align_position_with_view);
        ImageButton imageButton2 = (ImageButton) viewInflate.findViewById(R.id.btn_align_rotation_with_view);
        imageButton.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda88
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m5311x2edbb970(view);
            }
        });
        imageButton2.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda99
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m5312xe853470f(view);
            }
        });
        TransformComponent transformComponent = go.transform;
        updateVector3Fields(viewInflate, R.id.edit_pos_x, R.id.edit_pos_y, R.id.edit_pos_z, transformComponent.position);
        updateEulerFields(viewInflate, R.id.edit_rot_x, R.id.edit_rot_y, R.id.edit_rot_z, transformComponent.rotation);
        updateVector3Fields(viewInflate, R.id.edit_scale_x, R.id.edit_scale_y, R.id.edit_scale_z, transformComponent.scale);
        final SceneManager sceneManager = this.sceneManager;
        Objects.requireNonNull(sceneManager);
        addVector3Listener(go, viewInflate, R.id.edit_pos_x, R.id.edit_pos_y, R.id.edit_pos_z, new Vector3Updater() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda110
            @Override // org.catrobat.catroid.editor.InspectorManager.Vector3Updater
            public final void update(GameObject gameObject, Vector3 vector3) {
                sceneManager.setPosition(gameObject, vector3);
            }
        });
        final SceneManager sceneManager2 = this.sceneManager;
        Objects.requireNonNull(sceneManager2);
        addEulerListener(go, viewInflate, R.id.edit_rot_x, R.id.edit_rot_y, R.id.edit_rot_z, new QuaternionUpdater() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda122
            @Override // org.catrobat.catroid.editor.InspectorManager.QuaternionUpdater
            public final void update(GameObject gameObject, Quaternion quaternion) {
                sceneManager2.setRotation(gameObject, quaternion);
            }
        });
        final SceneManager sceneManager3 = this.sceneManager;
        Objects.requireNonNull(sceneManager3);
        addVector3Listener(go, viewInflate, R.id.edit_scale_x, R.id.edit_scale_y, R.id.edit_scale_z, new Vector3Updater() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda133
            @Override // org.catrobat.catroid.editor.InspectorManager.Vector3Updater
            public final void update(GameObject gameObject, Vector3 vector3) {
                sceneManager3.setScale(gameObject, vector3);
            }
        });
        this.container.addView(viewInflate);
    }

    /* JADX INFO: renamed from: lambda$createTransformView$30$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5311x2edbb970(View view) {
        ThreeDManager threeDManager = this.threeDManager;
        if (threeDManager == null || this.selectedObject == null) {
            return;
        }
        this.selectedObject.transform.position.set(threeDManager.getCameraPosition());
        m5171x5f6795b(this.selectedObject);
    }

    /* JADX INFO: renamed from: lambda$createTransformView$31$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5312xe853470f(View view) {
        if (this.threeDManager == null || this.selectedObject == null) {
            return;
        }
        Quaternion quaternion = new Quaternion();
        this.threeDManager.getCamera().view.getRotation(quaternion, true).conjugate();
        this.selectedObject.transform.rotation.set(quaternion);
        m5171x5f6795b(this.selectedObject);
    }

    private void createRenderView(final GameObject go) {
        addComponentHeader(R.string.component_render, true, false, new Runnable() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda248
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5307x239cc32a(go);
            }
        });
        View viewInflate = this.inflater.inflate(R.layout.inspector_render, (ViewGroup) this.container, false);
        setWhiteTextToAllChildren((ViewGroup) viewInflate);
        RenderComponent renderComponent = (RenderComponent) go.getComponent(RenderComponent.class);
        TextView textView = (TextView) viewInflate.findViewById(R.id.text_model_path);
        Button button = (Button) viewInflate.findViewById(R.id.btn_select_model);
        String str = renderComponent.modelFileName != null ? renderComponent.modelFileName : "No model selected";
        if (str.startsWith("primitive:")) {
            String strSubstring = str.substring("primitive:".length());
            textView.setText("Primitive Shape (" + (strSubstring.substring(0, 1).toUpperCase() + strSubstring.substring(1)) + Constant.AFTER_QUTO);
            button.setVisibility(8);
        } else {
            textView.setText(str);
            button.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda249
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.m5308xdd1450c9(go, view);
                }
            });
        }
        this.container.addView(viewInflate);
    }

    /* JADX INFO: renamed from: lambda$createRenderView$32$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5307x239cc32a(GameObject gameObject) {
        this.sceneManager.removeRenderComponent(gameObject);
        m5171x5f6795b(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createRenderView$33$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5308xdd1450c9(GameObject gameObject, View view) {
        showModelPicker(gameObject);
    }

    private void createMaterialView(final GameObject go) {
        addComponentHeader(R.string.component_material, true, false, new Runnable() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda83
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5183x4eea26d8(go);
            }
        });
        View viewInflate = this.inflater.inflate(R.layout.inspector_material, (ViewGroup) this.container, false);
        setWhiteTextToAllChildren((ViewGroup) viewInflate);
        final MaterialComponent materialComponent = (MaterialComponent) go.getComponent(MaterialComponent.class);
        setupColorPicker(go, (Button) viewInflate.findViewById(R.id.btn_material_color), materialComponent.baseColor, new ColorConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda86
            @Override // org.catrobat.catroid.editor.InspectorManager.ColorConsumer
            public final void accept(Color color) {
                materialComponent.baseColor.set(color);
            }
        });
        setupTextureSlot(go, viewInflate.findViewById(R.id.slot_base_color_texture), materialComponent.baseColorTexturePath, new StringConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda87
            @Override // org.catrobat.catroid.editor.InspectorManager.StringConsumer
            public final void accept(String str) {
                materialComponent.baseColorTexturePath = str;
            }
        });
        setupSlider(go, viewInflate.findViewById(R.id.slider_metallic), materialComponent.metallic, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda89
            @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
            public final void accept(float f) {
                materialComponent.metallic = f;
            }
        });
        setupSlider(go, viewInflate.findViewById(R.id.slider_roughness), materialComponent.roughness, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda90
            @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
            public final void accept(float f) {
                materialComponent.roughness = f;
            }
        });
        setupColorPicker(go, (Button) viewInflate.findViewById(R.id.btn_emissive_color), materialComponent.emissiveColor, new ColorConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda91
            @Override // org.catrobat.catroid.editor.InspectorManager.ColorConsumer
            public final void accept(Color color) {
                materialComponent.emissiveColor.set(color);
            }
        });
        setupTextureSlot(go, viewInflate.findViewById(R.id.slot_emissive_texture), materialComponent.emissiveTexturePath, new StringConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda92
            @Override // org.catrobat.catroid.editor.InspectorManager.StringConsumer
            public final void accept(String str) {
                materialComponent.emissiveTexturePath = str;
            }
        });
        setupTextureSlot(go, viewInflate.findViewById(R.id.slot_normal_texture), materialComponent.normalTexturePath, new StringConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda93
            @Override // org.catrobat.catroid.editor.InspectorManager.StringConsumer
            public final void accept(String str) {
                materialComponent.normalTexturePath = str;
            }
        });
        setupTextureSlot(go, viewInflate.findViewById(R.id.slot_mr_texture), materialComponent.metallicRoughnessTexturePath, new StringConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda94
            @Override // org.catrobat.catroid.editor.InspectorManager.StringConsumer
            public final void accept(String str) {
                materialComponent.metallicRoughnessTexturePath = str;
            }
        });
        setupTextureSlot(go, viewInflate.findViewById(R.id.slot_occlusion_texture), materialComponent.occlusionTexturePath, new StringConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda95
            @Override // org.catrobat.catroid.editor.InspectorManager.StringConsumer
            public final void accept(String str) {
                materialComponent.occlusionTexturePath = str;
            }
        });
        LinearLayout linearLayout = (LinearLayout) viewInflate.findViewById(R.id.material_params_container);
        if (linearLayout == null && (viewInflate instanceof LinearLayout)) {
            linearLayout = (LinearLayout) viewInflate;
        }
        if (linearLayout != null) {
            addSimpleFloatInput(linearLayout, "UV Scale X", materialComponent.uvScaleX, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda84
                @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                public final void accept(float f) {
                    this.f$0.m5184xc4644d19(materialComponent, go, f);
                }
            });
            addSimpleFloatInput(linearLayout, "UV Scale Y", materialComponent.uvScaleY, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda85
                @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                public final void accept(float f) {
                    this.f$0.m5185x7ddbdab8(materialComponent, go, f);
                }
            });
        }
        this.container.addView(viewInflate);
    }

    static /* synthetic */ boolean lambda$createMaterialView$34(Component component) {
        return component instanceof MaterialComponent;
    }

    /* JADX INFO: renamed from: lambda$createMaterialView$35$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5183x4eea26d8(GameObject gameObject) {
        gameObject.components.removeIf(new Predicate() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda155
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return InspectorManager.lambda$createMaterialView$34((Component) obj);
            }
        });
        if (gameObject.hasComponent(RenderComponent.class)) {
            this.threeDManager.applyPBRMaterial(gameObject.f591id, new MaterialComponent());
        }
        m5171x5f6795b(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createMaterialView$45$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5184xc4644d19(MaterialComponent materialComponent, GameObject gameObject, float f) {
        materialComponent.uvScaleX = f;
        this.sceneManager.setMaterialComponent(gameObject, materialComponent);
    }

    /* JADX INFO: renamed from: lambda$createMaterialView$46$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5185x7ddbdab8(MaterialComponent materialComponent, GameObject gameObject, float f) {
        materialComponent.uvScaleY = f;
        this.sceneManager.setMaterialComponent(gameObject, materialComponent);
    }

    private void createFogView(final GameObject go) {
        addComponentHeader(R.string.component_fog, true, false, new Runnable() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda150
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5166xc38c9a3b(go);
            }
        });
        View viewInflate = this.inflater.inflate(R.layout.inspector_fog, (ViewGroup) this.container, false);
        setWhiteTextToAllChildren((ViewGroup) viewInflate);
        final FogComponent fogComponent = (FogComponent) go.getComponent(FogComponent.class);
        CheckBox checkBox = (CheckBox) viewInflate.findViewById(R.id.fog_enabled);
        Spinner spinner = (Spinner) viewInflate.findViewById(R.id.spinner_fog_type);
        final Button button = (Button) viewInflate.findViewById(R.id.btn_fog_color);
        final View viewFindViewById = viewInflate.findViewById(R.id.layout_fog_exp);
        EditText editText = (EditText) viewInflate.findViewById(R.id.edit_fog_density);
        final View viewFindViewById2 = viewInflate.findViewById(R.id.layout_fog_linear);
        EditText editText2 = (EditText) viewInflate.findViewById(R.id.edit_fog_start);
        EditText editText3 = (EditText) viewInflate.findViewById(R.id.edit_fog_end);
        EditText editText4 = (EditText) viewInflate.findViewById(R.id.edit_fog_height);
        checkBox.setChecked(fogComponent.isEnabled);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this.activity, R.layout.simple_spinner_item_white_text, FogComponent.FogType.values());
        arrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_white_text);
        spinner.setAdapter((SpinnerAdapter) arrayAdapter);
        spinner.setSelection(fogComponent.type.ordinal());
        button.setBackgroundColor(libGdxColorToAndroidColor(fogComponent.color));
        editText.setText(String.valueOf(fogComponent.density));
        editText2.setText(String.valueOf(fogComponent.startDistance));
        editText3.setText(String.valueOf(fogComponent.endDistance));
        editText4.setText(String.valueOf(fogComponent.heightFalloff));
        final Runnable runnable = new Runnable() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda151
            @Override // java.lang.Runnable
            public final void run() {
                InspectorManager.lambda$createFogView$49(fogComponent, viewFindViewById, viewFindViewById2);
            }
        };
        runnable.run();
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda152
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                fogComponent.isEnabled = z;
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: org.catrobat.catroid.editor.InspectorManager.5
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> parent) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id2) {
                fogComponent.type = FogComponent.FogType.values()[position];
                runnable.run();
            }
        });
        final int iLibGdxColorToAndroidColor = libGdxColorToAndroidColor(fogComponent.color);
        button.setBackgroundColor(iLibGdxColorToAndroidColor);
        button.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda153
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m5167x99b0fc61(iLibGdxColorToAndroidColor, button, fogComponent, view);
            }
        });
        addSimpleTextListener(editText, new StringUpdater() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda154
            @Override // org.catrobat.catroid.editor.InspectorManager.StringUpdater
            public final void update(String str) {
                fogComponent.density = Float.parseFloat(str);
            }
        });
        addSimpleTextListener(editText2, new StringUpdater() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda156
            @Override // org.catrobat.catroid.editor.InspectorManager.StringUpdater
            public final void update(String str) {
                fogComponent.startDistance = Float.parseFloat(str);
            }
        });
        addSimpleTextListener(editText3, new StringUpdater() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda157
            @Override // org.catrobat.catroid.editor.InspectorManager.StringUpdater
            public final void update(String str) {
                fogComponent.endDistance = Float.parseFloat(str);
            }
        });
        addSimpleTextListener(editText4, new StringUpdater() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda158
            @Override // org.catrobat.catroid.editor.InspectorManager.StringUpdater
            public final void update(String str) {
                fogComponent.heightFalloff = Float.parseFloat(str);
            }
        });
        this.container.addView(viewInflate);
    }

    static /* synthetic */ boolean lambda$createFogView$47(Component component) {
        return component instanceof FogComponent;
    }

    /* JADX INFO: renamed from: lambda$createFogView$48$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5166xc38c9a3b(GameObject gameObject) {
        gameObject.components.removeIf(new Predicate() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda189
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return InspectorManager.lambda$createFogView$47((Component) obj);
            }
        });
        m5171x5f6795b(gameObject);
    }

    static /* synthetic */ void lambda$createFogView$49(FogComponent fogComponent, View view, View view2) {
        FogComponent.FogType fogType = fogComponent.type;
        view.setVisibility((fogType == FogComponent.FogType.EXPONENTIAL || fogType == FogComponent.FogType.EXPONENTIAL_SQUARED) ? 0 : 8);
        view2.setVisibility(fogType == FogComponent.FogType.LINEAR ? 0 : 8);
    }

    /* JADX INFO: renamed from: lambda$createFogView$53$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5167x99b0fc61(int i, final Button button, final FogComponent fogComponent, View view) {
        ColorPickerDialogBuilder.with(this.activity).setTitle("Choose color").initialColor(i).wheelType(ColorPickerView.WHEEL_TYPE.FLOWER).density(12).setPositiveButton("OK", new ColorPickerClickListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda2
            @Override // com.flask.colorpicker.builder.ColorPickerClickListener
            public final void onClick(DialogInterface dialogInterface, int i2, Integer[] numArr) {
                InspectorManager.lambda$createFogView$51(button, fogComponent, dialogInterface, i2, numArr);
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda3
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                InspectorManager.lambda$createFogView$52(dialogInterface, i2);
            }
        }).build().show();
    }

    static /* synthetic */ void lambda$createFogView$51(Button button, FogComponent fogComponent, DialogInterface dialogInterface, int i, Integer[] numArr) {
        button.setBackgroundColor(i);
        fogComponent.color.set(android.graphics.Color.red(i) / 255.0f, android.graphics.Color.green(i) / 255.0f, android.graphics.Color.blue(i) / 255.0f, 1.0f);
    }

    private void createPostProcessingView(final GameObject go) {
        final PostProcessingComponent postProcessingComponent = (PostProcessingComponent) go.getComponent(PostProcessingComponent.class);
        addComponentHeader(R.string.component_postprocessing, true, false, new Runnable() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda319
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5301x94ac5e23(go, postProcessingComponent);
            }
        });
        View viewInflate = this.inflater.inflate(R.layout.inspector_postprocessing_main, (ViewGroup) this.container, false);
        setWhiteTextToAllChildren((ViewGroup) viewInflate);
        CheckBox checkBox = (CheckBox) viewInflate.findViewById(R.id.check_pp_active);
        checkBox.setChecked(postProcessingComponent.isActive);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda320
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                this.f$0.m5302x4e23ebc2(postProcessingComponent, compoundButton, z);
            }
        });
        EditText editText = (EditText) viewInflate.findViewById(R.id.edit_pp_quality);
        editText.setText(String.format(Locale.US, "%.2f", Float.valueOf(postProcessingComponent.qualityScale)));
        addSimpleTextListener(editText, new StringUpdater() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda321
            @Override // org.catrobat.catroid.editor.InspectorManager.StringUpdater
            public final void update(String str) {
                this.f$0.m5303x3e6a176c(postProcessingComponent, str);
            }
        });
        LinearLayout linearLayout = (LinearLayout) viewInflate.findViewById(R.id.container_effects_list);
        linearLayout.removeAllViews();
        for (int i = 0; i < postProcessingComponent.effects.size(); i++) {
            addEffectUiItem(linearLayout, postProcessingComponent.effects.get(i), postProcessingComponent, go);
        }
        ((Button) viewInflate.findViewById(R.id.btn_add_effect)).setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda322
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m5304xf7e1a50b(postProcessingComponent, go, view);
            }
        });
        this.container.addView(viewInflate);
    }

    /* JADX INFO: renamed from: lambda$createPostProcessingView$58$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5301x94ac5e23(GameObject gameObject, PostProcessingComponent postProcessingComponent) {
        gameObject.components.remove(postProcessingComponent);
        ThreeDManager threeDManager = this.threeDManager;
        if (threeDManager != null) {
            threeDManager.postprocessingEnabled = false;
            postProcessingComponent.isActive = false;
            this.threeDManager.updatePostProcessing(postProcessingComponent);
        }
        m5171x5f6795b(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createPostProcessingView$59$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5302x4e23ebc2(PostProcessingComponent postProcessingComponent, CompoundButton compoundButton, boolean z) {
        postProcessingComponent.isActive = z;
        this.threeDManager.updatePostProcessing(postProcessingComponent);
    }

    /* JADX INFO: renamed from: lambda$createPostProcessingView$60$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5303x3e6a176c(PostProcessingComponent postProcessingComponent, String str) {
        try {
            postProcessingComponent.qualityScale = Float.parseFloat(str);
            this.threeDManager.updatePostProcessing(postProcessingComponent);
        } catch (Exception unused) {
        }
    }

    /* JADX INFO: renamed from: lambda$createPostProcessingView$61$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5304xf7e1a50b(PostProcessingComponent postProcessingComponent, GameObject gameObject, View view) {
        showAddEffectDialog(postProcessingComponent, gameObject);
    }

    private void addEffectColorParam(LinearLayout parent, final String label, final Color initialColor, final ColorConsumer onUpdate) {
        View viewInflate = this.inflater.inflate(R.layout.inspector_param_color, (ViewGroup) parent, false);
        ((TextView) viewInflate.findViewById(R.id.text_param_name)).setText(label);
        final Button button = (Button) viewInflate.findViewById(R.id.btn_color_picker);
        button.setBackgroundColor(libGdxColorToAndroidColor(initialColor));
        button.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda217
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m5080xf85b8c97(label, initialColor, button, onUpdate, view);
            }
        });
        parent.addView(viewInflate);
    }

    /* JADX INFO: renamed from: lambda$addEffectColorParam$63$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5080xf85b8c97(String str, Color color, final Button button, final ColorConsumer colorConsumer, View view) {
        ColorPickerDialogBuilder.with(this.activity).setTitle("Choose " + str).initialColor(libGdxColorToAndroidColor(color)).wheelType(ColorPickerView.WHEEL_TYPE.FLOWER).density(12).setPositiveButton("OK", new ColorPickerClickListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda148
            @Override // com.flask.colorpicker.builder.ColorPickerClickListener
            public final void onClick(DialogInterface dialogInterface, int i, Integer[] numArr) {
                InspectorManager.lambda$addEffectColorParam$62(button, colorConsumer, dialogInterface, i, numArr);
            }
        }).setNegativeButton("Cancel", (DialogInterface.OnClickListener) null).build().show();
    }

    static /* synthetic */ void lambda$addEffectColorParam$62(Button button, ColorConsumer colorConsumer, DialogInterface dialogInterface, int i, Integer[] numArr) {
        button.setBackgroundColor(i);
        colorConsumer.accept(new Color(android.graphics.Color.red(i) / 255.0f, android.graphics.Color.green(i) / 255.0f, android.graphics.Color.blue(i) / 255.0f, android.graphics.Color.alpha(i) / 255.0f));
    }

    private void addEffectUiItem(LinearLayout container, final PostProcessingData data, final PostProcessingComponent pp, final GameObject go) {
        View viewInflate = this.inflater.inflate(R.layout.inspector_effect_item, (ViewGroup) container, false);
        ((TextView) viewInflate.findViewById(R.id.text_effect_name)).setText(data.getType());
        ((ImageButton) viewInflate.findViewById(R.id.btn_delete_effect)).setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m5106xdcc73393(pp, data, go, view);
            }
        });
        CheckBox checkBox = (CheckBox) viewInflate.findViewById(R.id.check_effect_enable);
        checkBox.setChecked(data.isEnabled);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda20
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                this.f$0.m5107x963ec132(data, pp, compoundButton, z);
            }
        });
        LinearLayout linearLayout = (LinearLayout) viewInflate.findViewById(R.id.container_effect_params);
        if (data instanceof PostProcessingData.Bloom) {
            final PostProcessingData.Bloom bloom = (PostProcessingData.Bloom) data;
            addFloatParam(linearLayout, "Threshold", bloom.threshold, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda32
                @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                public final void accept(float f) {
                    this.f$0.m5108x4fb64ed1(bloom, pp, f);
                }
            });
            addFloatParam(linearLayout, "Intensity", bloom.intensity, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda45
                @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                public final void accept(float f) {
                    this.f$0.m5109x92ddc70(bloom, pp, f);
                }
            });
            addFloatParam(linearLayout, "Blur Amount", bloom.blurAmount, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda57
                @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                public final void accept(float f) {
                    this.f$0.m5110xc2a56a0f(bloom, pp, f);
                }
            });
            addFloatParam(linearLayout, "Blur Size", bloom.size, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda69
                @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                public final void accept(float f) {
                    this.f$0.m5111x7c1cf7ae(bloom, pp, f);
                }
            });
            addFloatParam(linearLayout, "Blur Passes", bloom.blurPasses, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda75
                @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                public final void accept(float f) {
                    this.f$0.m5112x6c632358(bloom, pp, f);
                }
            });
        } else if (data instanceof PostProcessingData.Levels) {
            final PostProcessingData.Levels levels = (PostProcessingData.Levels) data;
            addFloatParam(linearLayout, ExifInterface.TAG_CONTRAST, levels.contrast, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda76
                @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                public final void accept(float f) {
                    this.f$0.m5113x25dab0f7(levels, pp, f);
                }
            });
            addFloatParam(linearLayout, ExifInterface.TAG_SATURATION, levels.saturation, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda78
                @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                public final void accept(float f) {
                    this.f$0.m5114xdf523e96(levels, pp, f);
                }
            });
            addFloatParam(linearLayout, ExifInterface.TAG_GAMMA, levels.gamma, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda79
                @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                public final void accept(float f) {
                    this.f$0.m5115x98c9cc35(levels, pp, f);
                }
            });
        } else if (data instanceof PostProcessingData.RayTracing) {
            final PostProcessingData.RayTracing rayTracing = (PostProcessingData.RayTracing) data;
            addFloatParam(linearLayout, "Reflectivity Multi", rayTracing.reflectivity, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda9
                @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                public final void accept(float f) {
                    this.f$0.m5116x524159d4(rayTracing, pp, f);
                }
            });
            addFloatParam(linearLayout, "Edge Fade", rayTracing.edgeFade, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda10
                @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                public final void accept(float f) {
                    this.f$0.m5117xbb8e773(rayTracing, pp, f);
                }
            });
            addFloatParam(linearLayout, "Thickness", rayTracing.thickness, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda12
                @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                public final void accept(float f) {
                    this.f$0.m5118xc5307512(rayTracing, pp, f);
                }
            });
            addFloatParam(linearLayout, "Distance", rayTracing.maxDistance, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda13
                @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                public final void accept(float f) {
                    this.f$0.m5119x7ea802b1(rayTracing, pp, f);
                }
            });
            addFloatParam(linearLayout, "Stride", rayTracing.stride, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda14
                @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                public final void accept(float f) {
                    this.f$0.m5120x381f9050(rayTracing, pp, f);
                }
            });
            addFloatParam(linearLayout, "Steps", rayTracing.steps, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda15
                @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                public final void accept(float f) {
                    this.f$0.m5121xf1971def(rayTracing, pp, f);
                }
            });
        } else if (data instanceof PostProcessingData.VolumetricFog) {
            final PostProcessingData.VolumetricFog volumetricFog = (PostProcessingData.VolumetricFog) data;
            addFloatParam(linearLayout, "Density", volumetricFog.density, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda16
                @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                public final void accept(float f) {
                    this.f$0.m5122xe1dd4999(volumetricFog, pp, f);
                }
            });
            addFloatParam(linearLayout, "Scattering", volumetricFog.scattering, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda17
                @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                public final void accept(float f) {
                    this.f$0.m5123x9b54d738(volumetricFog, pp, f);
                }
            });
            addFloatParam(linearLayout, "Max Distance", volumetricFog.maxDistance, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda18
                @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                public final void accept(float f) {
                    this.f$0.m5124x54cc64d7(volumetricFog, pp, f);
                }
            });
            addFloatParam(linearLayout, "Steps", volumetricFog.steps, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda19
                @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                public final void accept(float f) {
                    this.f$0.m5125xe43f276(volumetricFog, pp, f);
                }
            });
        } else if (data instanceof PostProcessingData.Upscaler) {
            final PostProcessingData.Upscaler upscaler = (PostProcessingData.Upscaler) data;
            addFloatParam(linearLayout, ExifInterface.TAG_SHARPNESS, upscaler.sharpness, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda21
                @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                public final void accept(float f) {
                    this.f$0.m5126xc7bb8015(upscaler, pp, f);
                }
            });
        } else if (data instanceof PostProcessingData.HeightFog) {
            final PostProcessingData.HeightFog heightFog = (PostProcessingData.HeightFog) data;
            addFloatParam(linearLayout, "Density", heightFog.density, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda23
                @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                public final void accept(float f) {
                    this.f$0.m5127x81330db4(heightFog, pp, f);
                }
            });
            addFloatParam(linearLayout, "Falloff", heightFog.falloff, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda24
                @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                public final void accept(float f) {
                    this.f$0.m5128x3aaa9b53(heightFog, pp, f);
                }
            });
            addFloatParam(linearLayout, "Height Offset", heightFog.height, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda25
                @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                public final void accept(float f) {
                    this.f$0.m5129xf42228f2(heightFog, pp, f);
                }
            });
            addEffectColorParam(linearLayout, "Fog Color", heightFog.color, new ColorConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda26
                @Override // org.catrobat.catroid.editor.InspectorManager.ColorConsumer
                public final void accept(Color color) {
                    this.f$0.m5130xad99b691(heightFog, pp, color);
                }
            });
        } else if (data instanceof PostProcessingData.DepthOfField) {
            final PostProcessingData.DepthOfField depthOfField = (PostProcessingData.DepthOfField) data;
            CheckBox checkBox2 = new CheckBox(this.activity);
            checkBox2.setText("Auto Focus (Center Screen)");
            checkBox2.setTextColor(-1);
            checkBox2.setChecked(depthOfField.autoFocus);
            linearLayout.addView(checkBox2);
            final LinearLayout linearLayout2 = new LinearLayout(this.activity);
            linearLayout2.setOrientation(1);
            linearLayout.addView(linearLayout2);
            addFloatParam(linearLayout2, "Auto Focus Speed", depthOfField.autoFocusSpeed, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda27
                @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                public final void accept(float f) {
                    this.f$0.m5131x67114430(depthOfField, pp, f);
                }
            });
            final LinearLayout linearLayout3 = new LinearLayout(this.activity);
            linearLayout3.setOrientation(1);
            linearLayout.addView(linearLayout3);
            addFloatParam(linearLayout3, "Manual Focus Dist", depthOfField.focusDistance, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda28
                @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                public final void accept(float f) {
                    this.f$0.m5132x57576fda(depthOfField, pp, f);
                }
            });
            final Runnable runnable = new Runnable() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda29
                @Override // java.lang.Runnable
                public final void run() {
                    InspectorManager.lambda$addEffectUiItem$91(linearLayout2, depthOfField, linearLayout3);
                }
            };
            runnable.run();
            checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda30
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    this.f$0.m5133xca468b18(depthOfField, runnable, pp, compoundButton, z);
                }
            });
            addFloatParam(linearLayout, "Focus Range", depthOfField.focusRange, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda31
                @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                public final void accept(float f) {
                    this.f$0.m5134x83be18b7(depthOfField, pp, f);
                }
            });
            addFloatParam(linearLayout, "Transition", depthOfField.transition, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda34
                @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                public final void accept(float f) {
                    this.f$0.m5135x3d35a656(depthOfField, pp, f);
                }
            });
            addFloatParam(linearLayout, "Blur Size", depthOfField.blurSize, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda35
                @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                public final void accept(float f) {
                    this.f$0.m5136xf6ad33f5(depthOfField, pp, f);
                }
            });
        } else if (data instanceof PostProcessingData.GodRays) {
            final PostProcessingData.GodRays godRays = (PostProcessingData.GodRays) data;
            addFloatParam(linearLayout, "Exposure", godRays.exposure, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda36
                @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                public final void accept(float f) {
                    this.f$0.m5137xb024c194(godRays, pp, f);
                }
            });
            addFloatParam(linearLayout, "Decay", godRays.decay, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda37
                @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                public final void accept(float f) {
                    this.f$0.m5138x699c4f33(godRays, pp, f);
                }
            });
            addFloatParam(linearLayout, "Density", godRays.density, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda38
                @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                public final void accept(float f) {
                    this.f$0.m5139x2313dcd2(godRays, pp, f);
                }
            });
            addFloatParam(linearLayout, "Weight", godRays.weight, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda39
                @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                public final void accept(float f) {
                    this.f$0.m5140xdc8b6a71(godRays, pp, f);
                }
            });
        } else if (data instanceof PostProcessingData.SSAO) {
            final PostProcessingData.SSAO ssao = (PostProcessingData.SSAO) data;
            addFloatParam(linearLayout, "Bias", ssao.bias, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda40
                @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                public final void accept(float f) {
                    this.f$0.m5081x4905e150(ssao, pp, f);
                }
            });
            addFloatParam(linearLayout, "Intensity", ssao.intensity, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda41
                @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                public final void accept(float f) {
                    this.f$0.m5082x27d6eef(ssao, pp, f);
                }
            });
            addFloatParam(linearLayout, "Radius", ssao.radius, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda42
                @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                public final void accept(float f) {
                    this.f$0.m5083xbbf4fc8e(ssao, pp, f);
                }
            });
        } else if (data instanceof PostProcessingData.Vignette) {
            final PostProcessingData.Vignette vignette = (PostProcessingData.Vignette) data;
            addFloatParam(linearLayout, "Intensity", vignette.intensity, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda43
                @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                public final void accept(float f) {
                    this.f$0.m5084x756c8a2d(vignette, pp, f);
                }
            });
            addFloatParam(linearLayout, ExifInterface.TAG_SATURATION, vignette.saturation, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda46
                @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                public final void accept(float f) {
                    this.f$0.m5085x2ee417cc(vignette, pp, f);
                }
            });
        } else if (data instanceof PostProcessingData.Grain) {
            final PostProcessingData.Grain grain = (PostProcessingData.Grain) data;
            addFloatParam(linearLayout, "Amount", grain.amount, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda47
                @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                public final void accept(float f) {
                    this.f$0.m5086xe85ba56b(grain, pp, f);
                }
            });
        } else if (data instanceof PostProcessingData.Chromatic) {
            final PostProcessingData.Chromatic chromatic = (PostProcessingData.Chromatic) data;
            addFloatParam(linearLayout, "Max Distort", chromatic.maxDistortion, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda48
                @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                public final void accept(float f) {
                    this.f$0.m5087xa1d3330a(chromatic, pp, f);
                }
            });
            addFloatParam(linearLayout, "Strength", chromatic.strength, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda49
                @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                public final void accept(float f) {
                    this.f$0.m5088x5b4ac0a9(chromatic, pp, f);
                }
            });
        } else if (data instanceof PostProcessingData.RadialBlur) {
            final PostProcessingData.RadialBlur radialBlur = (PostProcessingData.RadialBlur) data;
            addFloatParam(linearLayout, "Blur Passes", radialBlur.blurPasses, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda50
                @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                public final void accept(float f) {
                    this.f$0.m5089x14c24e48(radialBlur, pp, f);
                }
            });
            addFloatParam(linearLayout, "Strength", radialBlur.strength, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda51
                @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                public final void accept(float f) {
                    this.f$0.m5090xce39dbe7(radialBlur, pp, f);
                }
            });
            addFloatParam(linearLayout, "Blur Size", radialBlur.size, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda52
                @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                public final void accept(float f) {
                    this.f$0.m5091xbe800791(radialBlur, pp, f);
                }
            });
        } else if (data instanceof PostProcessingData.OldTv) {
            final PostProcessingData.OldTv oldTv = (PostProcessingData.OldTv) data;
            addFloatParam(linearLayout, "Noise", oldTv.strength, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda53
                @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                public final void accept(float f) {
                    this.f$0.m5092x77f79530(oldTv, pp, f);
                }
            });
        } else if (!(data instanceof PostProcessingData.Crt) && !(data instanceof PostProcessingData.Fisheye)) {
            if (data instanceof PostProcessingData.Water) {
                final PostProcessingData.Water water = (PostProcessingData.Water) data;
                addFloatParam(linearLayout, "Speed", water.speed, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda54
                    @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                    public final void accept(float f) {
                        this.f$0.m5093x316f22cf(water, pp, f);
                    }
                });
                addFloatParam(linearLayout, "Amount", water.amount, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda56
                    @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                    public final void accept(float f) {
                        this.f$0.m5094xeae6b06e(water, pp, f);
                    }
                });
            } else if (data instanceof PostProcessingData.MotionBlur) {
                final PostProcessingData.MotionBlur motionBlur = (PostProcessingData.MotionBlur) data;
                addFloatParam(linearLayout, "Blur Opacity", motionBlur.blurOpacity, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda58
                    @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                    public final void accept(float f) {
                        this.f$0.m5095xa45e3e0d(motionBlur, pp, f);
                    }
                });
            } else if (data instanceof PostProcessingData.LensFlare) {
                final PostProcessingData.LensFlare lensFlare = (PostProcessingData.LensFlare) data;
                addFloatParam(linearLayout, "Intensity", lensFlare.intensity, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda59
                    @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                    public final void accept(float f) {
                        this.f$0.m5096x5dd5cbac(lensFlare, pp, f);
                    }
                });
                addFloatParam(linearLayout, "Threshold", lensFlare.threshold, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda60
                    @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                    public final void accept(float f) {
                        this.f$0.m5097x174d594b(lensFlare, pp, f);
                    }
                });
                addFloatParam(linearLayout, "Dispersal", lensFlare.dispersal, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda61
                    @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                    public final void accept(float f) {
                        this.f$0.m5098xd0c4e6ea(lensFlare, pp, f);
                    }
                });
                addFloatParam(linearLayout, "Size", lensFlare.size, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda62
                    @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                    public final void accept(float f) {
                        this.f$0.m5099x8a3c7489(lensFlare, pp, f);
                    }
                });
            } else if (data instanceof PostProcessingData.Gaussian) {
                final PostProcessingData.Gaussian gaussian = (PostProcessingData.Gaussian) data;
                addFloatParam(linearLayout, "Amount", gaussian.amount, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda63
                    @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                    public final void accept(float f) {
                        this.f$0.m5100x43b40228(gaussian, pp, f);
                    }
                });
                addFloatParam(linearLayout, "Blur Passes", gaussian.passes, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda64
                    @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                    public final void accept(float f) {
                        this.f$0.m5101x33fa2dd2(gaussian, pp, f);
                    }
                });
                addFloatParam(linearLayout, "Blur Size", gaussian.size, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda65
                    @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                    public final void accept(float f) {
                        this.f$0.m5102xed71bb71(gaussian, pp, f);
                    }
                });
            } else if (data instanceof PostProcessingData.Zoom) {
                final PostProcessingData.Zoom zoom = (PostProcessingData.Zoom) data;
                addFloatParam(linearLayout, "Strength", zoom.zoom, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda67
                    @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                    public final void accept(float f) {
                        this.f$0.m5103xa6e94910(zoom, pp, f);
                    }
                });
                addFloatParam(linearLayout, "Origin X", zoom.originX, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda68
                    @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                    public final void accept(float f) {
                        this.f$0.m5104x6060d6af(zoom, pp, f);
                    }
                });
                addFloatParam(linearLayout, "Origin Y", zoom.originY, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda70
                    @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                    public final void accept(float f) {
                        this.f$0.m5105x19d8644e(zoom, pp, f);
                    }
                });
            } else if (data instanceof PostProcessingData.EyeAdaptation) {
                final PostProcessingData.EyeAdaptation eyeAdaptation = (PostProcessingData.EyeAdaptation) data;
                addFloatParam(linearLayout, "Target Luminance", eyeAdaptation.targetLuminance, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda71
                    @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                    public final void accept(float f) {
                        eyeAdaptation.targetLuminance = f;
                    }
                });
                addFloatParam(linearLayout, "Speed", eyeAdaptation.speed, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda72
                    @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                    public final void accept(float f) {
                        eyeAdaptation.speed = f;
                    }
                });
                addFloatParam(linearLayout, "Min Exposure", eyeAdaptation.minExposure, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda73
                    @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                    public final void accept(float f) {
                        eyeAdaptation.minExposure = f;
                    }
                });
                addFloatParam(linearLayout, "Max Exposure", eyeAdaptation.maxExposure, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda74
                    @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                    public final void accept(float f) {
                        eyeAdaptation.maxExposure = f;
                    }
                });
            }
        }
        container.addView(viewInflate);
    }

    /* JADX INFO: renamed from: lambda$addEffectUiItem$64$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5106xdcc73393(PostProcessingComponent postProcessingComponent, PostProcessingData postProcessingData, GameObject gameObject, View view) {
        postProcessingComponent.effects.remove(postProcessingData);
        this.threeDManager.updatePostProcessing(postProcessingComponent);
        m5171x5f6795b(gameObject);
    }

    /* JADX INFO: renamed from: lambda$addEffectUiItem$65$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5107x963ec132(PostProcessingData postProcessingData, PostProcessingComponent postProcessingComponent, CompoundButton compoundButton, boolean z) {
        postProcessingData.isEnabled = z;
        this.threeDManager.updatePostProcessing(postProcessingComponent);
    }

    /* JADX INFO: renamed from: lambda$addEffectUiItem$66$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5108x4fb64ed1(PostProcessingData.Bloom bloom, PostProcessingComponent postProcessingComponent, float f) {
        bloom.threshold = f;
        updatePP(postProcessingComponent);
    }

    /* JADX INFO: renamed from: lambda$addEffectUiItem$67$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5109x92ddc70(PostProcessingData.Bloom bloom, PostProcessingComponent postProcessingComponent, float f) {
        bloom.intensity = f;
        updatePP(postProcessingComponent);
    }

    /* JADX INFO: renamed from: lambda$addEffectUiItem$68$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5110xc2a56a0f(PostProcessingData.Bloom bloom, PostProcessingComponent postProcessingComponent, float f) {
        bloom.blurAmount = f;
        updatePP(postProcessingComponent);
    }

    /* JADX INFO: renamed from: lambda$addEffectUiItem$69$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5111x7c1cf7ae(PostProcessingData.Bloom bloom, PostProcessingComponent postProcessingComponent, float f) {
        bloom.size = f;
        updatePP(postProcessingComponent);
    }

    /* JADX INFO: renamed from: lambda$addEffectUiItem$70$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5112x6c632358(PostProcessingData.Bloom bloom, PostProcessingComponent postProcessingComponent, float f) {
        int i = (int) f;
        if (i <= 0) {
            i = 1;
        }
        bloom.blurPasses = i;
        updatePP(postProcessingComponent);
    }

    /* JADX INFO: renamed from: lambda$addEffectUiItem$71$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5113x25dab0f7(PostProcessingData.Levels levels, PostProcessingComponent postProcessingComponent, float f) {
        levels.contrast = f;
        updatePP(postProcessingComponent);
    }

    /* JADX INFO: renamed from: lambda$addEffectUiItem$72$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5114xdf523e96(PostProcessingData.Levels levels, PostProcessingComponent postProcessingComponent, float f) {
        levels.saturation = f;
        updatePP(postProcessingComponent);
    }

    /* JADX INFO: renamed from: lambda$addEffectUiItem$73$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5115x98c9cc35(PostProcessingData.Levels levels, PostProcessingComponent postProcessingComponent, float f) {
        levels.gamma = f;
        updatePP(postProcessingComponent);
    }

    /* JADX INFO: renamed from: lambda$addEffectUiItem$74$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5116x524159d4(PostProcessingData.RayTracing rayTracing, PostProcessingComponent postProcessingComponent, float f) {
        rayTracing.reflectivity = f;
        updatePP(postProcessingComponent);
    }

    /* JADX INFO: renamed from: lambda$addEffectUiItem$75$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5117xbb8e773(PostProcessingData.RayTracing rayTracing, PostProcessingComponent postProcessingComponent, float f) {
        rayTracing.edgeFade = f;
        updatePP(postProcessingComponent);
    }

    /* JADX INFO: renamed from: lambda$addEffectUiItem$76$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5118xc5307512(PostProcessingData.RayTracing rayTracing, PostProcessingComponent postProcessingComponent, float f) {
        rayTracing.thickness = f;
        updatePP(postProcessingComponent);
    }

    /* JADX INFO: renamed from: lambda$addEffectUiItem$77$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5119x7ea802b1(PostProcessingData.RayTracing rayTracing, PostProcessingComponent postProcessingComponent, float f) {
        rayTracing.maxDistance = f;
        updatePP(postProcessingComponent);
    }

    /* JADX INFO: renamed from: lambda$addEffectUiItem$78$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5120x381f9050(PostProcessingData.RayTracing rayTracing, PostProcessingComponent postProcessingComponent, float f) {
        rayTracing.stride = f;
        updatePP(postProcessingComponent);
    }

    /* JADX INFO: renamed from: lambda$addEffectUiItem$79$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5121xf1971def(PostProcessingData.RayTracing rayTracing, PostProcessingComponent postProcessingComponent, float f) {
        rayTracing.steps = (int) f;
        updatePP(postProcessingComponent);
    }

    /* JADX INFO: renamed from: lambda$addEffectUiItem$80$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5122xe1dd4999(PostProcessingData.VolumetricFog volumetricFog, PostProcessingComponent postProcessingComponent, float f) {
        volumetricFog.density = f;
        updatePP(postProcessingComponent);
    }

    /* JADX INFO: renamed from: lambda$addEffectUiItem$81$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5123x9b54d738(PostProcessingData.VolumetricFog volumetricFog, PostProcessingComponent postProcessingComponent, float f) {
        volumetricFog.scattering = f;
        updatePP(postProcessingComponent);
    }

    /* JADX INFO: renamed from: lambda$addEffectUiItem$82$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5124x54cc64d7(PostProcessingData.VolumetricFog volumetricFog, PostProcessingComponent postProcessingComponent, float f) {
        volumetricFog.maxDistance = f;
        updatePP(postProcessingComponent);
    }

    /* JADX INFO: renamed from: lambda$addEffectUiItem$83$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5125xe43f276(PostProcessingData.VolumetricFog volumetricFog, PostProcessingComponent postProcessingComponent, float f) {
        volumetricFog.steps = (int) f;
        updatePP(postProcessingComponent);
    }

    /* JADX INFO: renamed from: lambda$addEffectUiItem$84$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5126xc7bb8015(PostProcessingData.Upscaler upscaler, PostProcessingComponent postProcessingComponent, float f) {
        upscaler.sharpness = f;
        updatePP(postProcessingComponent);
    }

    /* JADX INFO: renamed from: lambda$addEffectUiItem$85$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5127x81330db4(PostProcessingData.HeightFog heightFog, PostProcessingComponent postProcessingComponent, float f) {
        heightFog.density = f;
        updatePP(postProcessingComponent);
    }

    /* JADX INFO: renamed from: lambda$addEffectUiItem$86$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5128x3aaa9b53(PostProcessingData.HeightFog heightFog, PostProcessingComponent postProcessingComponent, float f) {
        heightFog.falloff = f;
        updatePP(postProcessingComponent);
    }

    /* JADX INFO: renamed from: lambda$addEffectUiItem$87$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5129xf42228f2(PostProcessingData.HeightFog heightFog, PostProcessingComponent postProcessingComponent, float f) {
        heightFog.height = f;
        updatePP(postProcessingComponent);
    }

    /* JADX INFO: renamed from: lambda$addEffectUiItem$88$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5130xad99b691(PostProcessingData.HeightFog heightFog, PostProcessingComponent postProcessingComponent, Color color) {
        heightFog.color.set(color);
        updatePP(postProcessingComponent);
    }

    /* JADX INFO: renamed from: lambda$addEffectUiItem$89$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5131x67114430(PostProcessingData.DepthOfField depthOfField, PostProcessingComponent postProcessingComponent, float f) {
        depthOfField.autoFocusSpeed = f;
        updatePP(postProcessingComponent);
    }

    /* JADX INFO: renamed from: lambda$addEffectUiItem$90$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5132x57576fda(PostProcessingData.DepthOfField depthOfField, PostProcessingComponent postProcessingComponent, float f) {
        depthOfField.focusDistance = f;
        updatePP(postProcessingComponent);
    }

    static /* synthetic */ void lambda$addEffectUiItem$91(LinearLayout linearLayout, PostProcessingData.DepthOfField depthOfField, LinearLayout linearLayout2) {
        linearLayout.setVisibility(depthOfField.autoFocus ? 0 : 8);
        linearLayout2.setAlpha(depthOfField.autoFocus ? 0.4f : 1.0f);
        for (int i = 0; i < linearLayout2.getChildCount(); i++) {
            linearLayout2.getChildAt(i).setEnabled(!depthOfField.autoFocus);
            linearLayout2.getChildAt(i).setClickable(!depthOfField.autoFocus);
        }
    }

    /* JADX INFO: renamed from: lambda$addEffectUiItem$92$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5133xca468b18(PostProcessingData.DepthOfField depthOfField, Runnable runnable, PostProcessingComponent postProcessingComponent, CompoundButton compoundButton, boolean z) {
        depthOfField.autoFocus = z;
        runnable.run();
        updatePP(postProcessingComponent);
    }

    /* JADX INFO: renamed from: lambda$addEffectUiItem$93$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5134x83be18b7(PostProcessingData.DepthOfField depthOfField, PostProcessingComponent postProcessingComponent, float f) {
        depthOfField.focusRange = f;
        updatePP(postProcessingComponent);
    }

    /* JADX INFO: renamed from: lambda$addEffectUiItem$94$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5135x3d35a656(PostProcessingData.DepthOfField depthOfField, PostProcessingComponent postProcessingComponent, float f) {
        depthOfField.transition = f;
        updatePP(postProcessingComponent);
    }

    /* JADX INFO: renamed from: lambda$addEffectUiItem$95$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5136xf6ad33f5(PostProcessingData.DepthOfField depthOfField, PostProcessingComponent postProcessingComponent, float f) {
        depthOfField.blurSize = f;
        updatePP(postProcessingComponent);
    }

    /* JADX INFO: renamed from: lambda$addEffectUiItem$96$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5137xb024c194(PostProcessingData.GodRays godRays, PostProcessingComponent postProcessingComponent, float f) {
        godRays.exposure = f;
        updatePP(postProcessingComponent);
    }

    /* JADX INFO: renamed from: lambda$addEffectUiItem$97$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5138x699c4f33(PostProcessingData.GodRays godRays, PostProcessingComponent postProcessingComponent, float f) {
        godRays.decay = f;
        updatePP(postProcessingComponent);
    }

    /* JADX INFO: renamed from: lambda$addEffectUiItem$98$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5139x2313dcd2(PostProcessingData.GodRays godRays, PostProcessingComponent postProcessingComponent, float f) {
        godRays.density = f;
        updatePP(postProcessingComponent);
    }

    /* JADX INFO: renamed from: lambda$addEffectUiItem$99$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5140xdc8b6a71(PostProcessingData.GodRays godRays, PostProcessingComponent postProcessingComponent, float f) {
        godRays.weight = f;
        updatePP(postProcessingComponent);
    }

    /* JADX INFO: renamed from: lambda$addEffectUiItem$100$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5081x4905e150(PostProcessingData.SSAO ssao, PostProcessingComponent postProcessingComponent, float f) {
        ssao.bias = f;
        updatePP(postProcessingComponent);
    }

    /* JADX INFO: renamed from: lambda$addEffectUiItem$101$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5082x27d6eef(PostProcessingData.SSAO ssao, PostProcessingComponent postProcessingComponent, float f) {
        ssao.intensity = f;
        updatePP(postProcessingComponent);
    }

    /* JADX INFO: renamed from: lambda$addEffectUiItem$102$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5083xbbf4fc8e(PostProcessingData.SSAO ssao, PostProcessingComponent postProcessingComponent, float f) {
        ssao.radius = f;
        updatePP(postProcessingComponent);
    }

    /* JADX INFO: renamed from: lambda$addEffectUiItem$103$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5084x756c8a2d(PostProcessingData.Vignette vignette, PostProcessingComponent postProcessingComponent, float f) {
        vignette.intensity = f;
        updatePP(postProcessingComponent);
    }

    /* JADX INFO: renamed from: lambda$addEffectUiItem$104$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5085x2ee417cc(PostProcessingData.Vignette vignette, PostProcessingComponent postProcessingComponent, float f) {
        vignette.saturation = f;
        updatePP(postProcessingComponent);
    }

    /* JADX INFO: renamed from: lambda$addEffectUiItem$105$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5086xe85ba56b(PostProcessingData.Grain grain, PostProcessingComponent postProcessingComponent, float f) {
        grain.amount = f;
        updatePP(postProcessingComponent);
    }

    /* JADX INFO: renamed from: lambda$addEffectUiItem$106$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5087xa1d3330a(PostProcessingData.Chromatic chromatic, PostProcessingComponent postProcessingComponent, float f) {
        chromatic.maxDistortion = f;
        updatePP(postProcessingComponent);
    }

    /* JADX INFO: renamed from: lambda$addEffectUiItem$107$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5088x5b4ac0a9(PostProcessingData.Chromatic chromatic, PostProcessingComponent postProcessingComponent, float f) {
        chromatic.strength = f;
        updatePP(postProcessingComponent);
    }

    /* JADX INFO: renamed from: lambda$addEffectUiItem$108$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5089x14c24e48(PostProcessingData.RadialBlur radialBlur, PostProcessingComponent postProcessingComponent, float f) {
        int i = (int) f;
        if (i <= 0) {
            i = 1;
        }
        radialBlur.blurPasses = i;
        updatePP(postProcessingComponent);
    }

    /* JADX INFO: renamed from: lambda$addEffectUiItem$109$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5090xce39dbe7(PostProcessingData.RadialBlur radialBlur, PostProcessingComponent postProcessingComponent, float f) {
        radialBlur.strength = f;
        updatePP(postProcessingComponent);
    }

    /* JADX INFO: renamed from: lambda$addEffectUiItem$110$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5091xbe800791(PostProcessingData.RadialBlur radialBlur, PostProcessingComponent postProcessingComponent, float f) {
        radialBlur.size = f;
        updatePP(postProcessingComponent);
    }

    /* JADX INFO: renamed from: lambda$addEffectUiItem$111$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5092x77f79530(PostProcessingData.OldTv oldTv, PostProcessingComponent postProcessingComponent, float f) {
        oldTv.strength = f;
        updatePP(postProcessingComponent);
    }

    /* JADX INFO: renamed from: lambda$addEffectUiItem$112$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5093x316f22cf(PostProcessingData.Water water, PostProcessingComponent postProcessingComponent, float f) {
        water.speed = f;
        updatePP(postProcessingComponent);
    }

    /* JADX INFO: renamed from: lambda$addEffectUiItem$113$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5094xeae6b06e(PostProcessingData.Water water, PostProcessingComponent postProcessingComponent, float f) {
        water.amount = f;
        updatePP(postProcessingComponent);
    }

    /* JADX INFO: renamed from: lambda$addEffectUiItem$114$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5095xa45e3e0d(PostProcessingData.MotionBlur motionBlur, PostProcessingComponent postProcessingComponent, float f) {
        motionBlur.blurOpacity = Math.max(0.0f, Math.min(0.99f, f));
        updatePP(postProcessingComponent);
    }

    /* JADX INFO: renamed from: lambda$addEffectUiItem$115$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5096x5dd5cbac(PostProcessingData.LensFlare lensFlare, PostProcessingComponent postProcessingComponent, float f) {
        lensFlare.intensity = f;
        updatePP(postProcessingComponent);
    }

    /* JADX INFO: renamed from: lambda$addEffectUiItem$116$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5097x174d594b(PostProcessingData.LensFlare lensFlare, PostProcessingComponent postProcessingComponent, float f) {
        lensFlare.threshold = f;
        updatePP(postProcessingComponent);
    }

    /* JADX INFO: renamed from: lambda$addEffectUiItem$117$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5098xd0c4e6ea(PostProcessingData.LensFlare lensFlare, PostProcessingComponent postProcessingComponent, float f) {
        lensFlare.dispersal = f;
        updatePP(postProcessingComponent);
    }

    /* JADX INFO: renamed from: lambda$addEffectUiItem$118$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5099x8a3c7489(PostProcessingData.LensFlare lensFlare, PostProcessingComponent postProcessingComponent, float f) {
        lensFlare.size = f;
        updatePP(postProcessingComponent);
    }

    /* JADX INFO: renamed from: lambda$addEffectUiItem$119$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5100x43b40228(PostProcessingData.Gaussian gaussian, PostProcessingComponent postProcessingComponent, float f) {
        gaussian.amount = f;
        updatePP(postProcessingComponent);
    }

    /* JADX INFO: renamed from: lambda$addEffectUiItem$120$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5101x33fa2dd2(PostProcessingData.Gaussian gaussian, PostProcessingComponent postProcessingComponent, float f) {
        int i = (int) f;
        if (i <= 0) {
            i = 1;
        }
        gaussian.passes = i;
        updatePP(postProcessingComponent);
    }

    /* JADX INFO: renamed from: lambda$addEffectUiItem$121$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5102xed71bb71(PostProcessingData.Gaussian gaussian, PostProcessingComponent postProcessingComponent, float f) {
        gaussian.size = f;
        updatePP(postProcessingComponent);
    }

    /* JADX INFO: renamed from: lambda$addEffectUiItem$122$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5103xa6e94910(PostProcessingData.Zoom zoom, PostProcessingComponent postProcessingComponent, float f) {
        zoom.zoom = f;
        updatePP(postProcessingComponent);
    }

    /* JADX INFO: renamed from: lambda$addEffectUiItem$123$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5104x6060d6af(PostProcessingData.Zoom zoom, PostProcessingComponent postProcessingComponent, float f) {
        zoom.originX = f;
        updatePP(postProcessingComponent);
    }

    /* JADX INFO: renamed from: lambda$addEffectUiItem$124$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5105x19d8644e(PostProcessingData.Zoom zoom, PostProcessingComponent postProcessingComponent, float f) {
        zoom.originY = f;
        updatePP(postProcessingComponent);
    }

    private void updatePP(PostProcessingComponent pp) {
        ThreeDManager threeDManager = this.threeDManager;
        if (threeDManager != null) {
            threeDManager.updatePostProcessing(pp);
        }
    }

    private void addFloatParam(LinearLayout parent, String name2, float value, final FloatConsumer onChange) {
        View viewInflate = this.inflater.inflate(R.layout.inspector_param_float, (ViewGroup) parent, false);
        ((TextView) viewInflate.findViewById(R.id.text_param_name)).setText(name2);
        EditText editText = (EditText) viewInflate.findViewById(R.id.edit_param_value);
        editText.setText(String.format(Locale.US, "%.3f", Float.valueOf(value)));
        addSimpleTextListener(editText, new StringUpdater() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda1
            @Override // org.catrobat.catroid.editor.InspectorManager.StringUpdater
            public final void update(String str) {
                onChange.accept(Float.parseFloat(str));
            }
        });
        parent.addView(viewInflate);
    }

    private void setupFloatParam(View parent, int viewId, String label, float initialValue, final FloatConsumer onUpdate) {
        View viewFindViewById = parent.findViewById(viewId);
        ((TextView) viewFindViewById.findViewById(R.id.text_param_name)).setText(label);
        EditText editText = (EditText) viewFindViewById.findViewById(R.id.edit_param_value);
        editText.setText(String.format(Locale.US, "%.2f", Float.valueOf(initialValue)));
        addSimpleTextListener(editText, new StringUpdater() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda145
            @Override // org.catrobat.catroid.editor.InspectorManager.StringUpdater
            public final void update(String str) {
                onUpdate.accept(Float.parseFloat(str));
            }
        });
    }

    private void createParticleSystem3DView(final GameObject go) {
        int i;
        int i2;
        float f;
        AttributeSet attributeSet;
        int i3;
        final ParticleSystem3DComponent particleSystem3DComponent = (ParticleSystem3DComponent) go.getComponent(ParticleSystem3DComponent.class);
        if (particleSystem3DComponent == null) {
            return;
        }
        addComponentHeader(R.string.component_particle_3d, true, false, new Runnable() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda166
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5186x98377a09(go);
            }
        });
        LinearLayout linearLayout = new LinearLayout(this.activity);
        linearLayout.setOrientation(1);
        int i4 = (int) (this.activity.getResources().getDisplayMetrics().density * 8.0f);
        linearLayout.setPadding(i4, i4, i4, i4);
        this.container.addView(linearLayout);
        addSectionHeader(linearLayout, this.activity.getString(R.string.particle_main));
        addCheckbox(linearLayout, this.activity.getString(R.string.particle_looping), particleSystem3DComponent.looping, new BoolConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda253
            @Override // org.catrobat.catroid.editor.InspectorManager.BoolConsumer
            public final void accept(boolean z) {
                this.f$0.m5187x51af07a8(particleSystem3DComponent, go, z);
            }
        });
        addCheckbox(linearLayout, this.activity.getString(R.string.particle_prewarm), particleSystem3DComponent.prewarm, new BoolConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda265
            @Override // org.catrobat.catroid.editor.InspectorManager.BoolConsumer
            public final void accept(boolean z) {
                this.f$0.m5188xb269547(particleSystem3DComponent, go, z);
            }
        });
        addSimpleFloatInput(linearLayout, this.activity.getString(R.string.particle_duration), particleSystem3DComponent.duration, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda276
            @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
            public final void accept(float f2) {
                this.f$0.m5189xc49e22e6(particleSystem3DComponent, go, f2);
            }
        });
        addSimpleFloatInput(linearLayout, this.activity.getString(R.string.particle_max_particles), particleSystem3DComponent.maxParticles, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda277
            @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
            public final void accept(float f2) {
                this.f$0.m5190x7e15b085(particleSystem3DComponent, go, f2);
            }
        });
        addMinMaxCurveEditor(linearLayout, this.activity.getString(R.string.particle_start_lifetime), particleSystem3DComponent.startLifetime, go);
        addMinMaxCurveEditor(linearLayout, this.activity.getString(R.string.particle_start_speed), particleSystem3DComponent.startSpeed, go);
        addMinMaxCurveEditor(linearLayout, this.activity.getString(R.string.particle_start_size), particleSystem3DComponent.startSize, go);
        addMinMaxCurveEditor(linearLayout, this.activity.getString(R.string.particle_gravity_modifier), particleSystem3DComponent.gravityModifier, go);
        addSpinnerEnum(linearLayout, this.activity.getString(R.string.particle_sim_space), ParticleSystem3DComponent.SimulationSpace.values(), particleSystem3DComponent.simulationSpace.ordinal(), new EnumConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda278
            @Override // org.catrobat.catroid.editor.InspectorManager.EnumConsumer
            public final void accept(Object obj) {
                this.f$0.m5191x378d3e24(particleSystem3DComponent, go, (ParticleSystem3DComponent.SimulationSpace) obj);
            }
        });
        addModuleSection(linearLayout, this.activity.getString(R.string.particle_emission), particleSystem3DComponent.emission.enabled, new BoolConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda279
            @Override // org.catrobat.catroid.editor.InspectorManager.BoolConsumer
            public final void accept(boolean z) {
                this.f$0.m5192xf104cbc3(particleSystem3DComponent, go, z);
            }
        }, new ModuleContentBuilder() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda281
            @Override // org.catrobat.catroid.editor.InspectorManager.ModuleContentBuilder
            public final LinearLayout build() {
                return this.f$0.m5198x80a0bb88(particleSystem3DComponent, go);
            }
        });
        addModuleSection(linearLayout, this.activity.getString(R.string.particle_shape), particleSystem3DComponent.shape.enabled, new BoolConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda282
            @Override // org.catrobat.catroid.editor.InspectorManager.BoolConsumer
            public final void accept(boolean z) {
                this.f$0.m5199x3a184927(particleSystem3DComponent, go, z);
            }
        }, new ModuleContentBuilder() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda283
            @Override // org.catrobat.catroid.editor.InspectorManager.ModuleContentBuilder
            public final LinearLayout build() {
                return this.f$0.m5212xdbf91845(particleSystem3DComponent, go);
            }
        });
        addModuleSection(linearLayout, this.activity.getString(R.string.particle_velocity_over_lifetime), particleSystem3DComponent.velocityOverLifetime.enabled, new BoolConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda177
            @Override // org.catrobat.catroid.editor.InspectorManager.BoolConsumer
            public final void accept(boolean z) {
                this.f$0.m5213x9570a5e4(particleSystem3DComponent, go, z);
            }
        }, new ModuleContentBuilder() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda188
            @Override // org.catrobat.catroid.editor.InspectorManager.ModuleContentBuilder
            public final LinearLayout build() {
                return this.f$0.m5214x85b6d18e(particleSystem3DComponent, go);
            }
        });
        addModuleSection(linearLayout, this.activity.getString(R.string.particle_force_over_lifetime), particleSystem3DComponent.forceOverLifetime.enabled, new BoolConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda199
            @Override // org.catrobat.catroid.editor.InspectorManager.BoolConsumer
            public final void accept(boolean z) {
                this.f$0.m5215x3f2e5f2d(particleSystem3DComponent, go, z);
            }
        }, new ModuleContentBuilder() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda210
            @Override // org.catrobat.catroid.editor.InspectorManager.ModuleContentBuilder
            public final LinearLayout build() {
                return this.f$0.m5216xf8a5eccc(particleSystem3DComponent, go);
            }
        });
        addModuleSection(linearLayout, this.activity.getString(R.string.particle_color_over_lifetime), particleSystem3DComponent.colorOverLifetime.enabled, new BoolConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda221
            @Override // org.catrobat.catroid.editor.InspectorManager.BoolConsumer
            public final void accept(boolean z) {
                this.f$0.m5217xb21d7a6b(particleSystem3DComponent, go, z);
            }
        }, new ModuleContentBuilder() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda233
            @Override // org.catrobat.catroid.editor.InspectorManager.ModuleContentBuilder
            public final LinearLayout build() {
                return this.f$0.m5218x6b95080a(particleSystem3DComponent, go);
            }
        });
        addModuleSection(linearLayout, this.activity.getString(R.string.particle_size_over_lifetime), particleSystem3DComponent.sizeOverLifetime.enabled, new BoolConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda244
            @Override // org.catrobat.catroid.editor.InspectorManager.BoolConsumer
            public final void accept(boolean z) {
                this.f$0.m5219x250c95a9(particleSystem3DComponent, go, z);
            }
        }, new ModuleContentBuilder() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda250
            @Override // org.catrobat.catroid.editor.InspectorManager.ModuleContentBuilder
            public final LinearLayout build() {
                return this.f$0.m5221x97fbb0e7(particleSystem3DComponent, go);
            }
        });
        addModuleSection(linearLayout, this.activity.getString(R.string.particle_rotation_over_lifetime), particleSystem3DComponent.rotationOverLifetime.enabled, new BoolConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda251
            @Override // org.catrobat.catroid.editor.InspectorManager.BoolConsumer
            public final void accept(boolean z) {
                this.f$0.m5222x51733e86(particleSystem3DComponent, go, z);
            }
        }, new ModuleContentBuilder() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda252
            @Override // org.catrobat.catroid.editor.InspectorManager.ModuleContentBuilder
            public final LinearLayout build() {
                return this.f$0.m5224xfb30f7cf(particleSystem3DComponent, go);
            }
        });
        addModuleSection(linearLayout, this.activity.getString(R.string.particle_noise), particleSystem3DComponent.noise.enabled, new BoolConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda254
            @Override // org.catrobat.catroid.editor.InspectorManager.BoolConsumer
            public final void accept(boolean z) {
                this.f$0.m5225xb4a8856e(particleSystem3DComponent, go, z);
            }
        }, new ModuleContentBuilder() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda255
            @Override // org.catrobat.catroid.editor.InspectorManager.ModuleContentBuilder
            public final LinearLayout build() {
                return this.f$0.m5235x2a22abaf(particleSystem3DComponent, go);
            }
        });
        addModuleSection(linearLayout, this.activity.getString(R.string.particle_collision), particleSystem3DComponent.collision.enabled, new BoolConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda256
            @Override // org.catrobat.catroid.editor.InspectorManager.BoolConsumer
            public final void accept(boolean z) {
                this.f$0.m5236xe39a394e(particleSystem3DComponent, go, z);
            }
        }, new ModuleContentBuilder() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda257
            @Override // org.catrobat.catroid.editor.InspectorManager.ModuleContentBuilder
            public final LinearLayout build() {
                return this.f$0.m5248xcc037acd(particleSystem3DComponent, go);
            }
        });
        addModuleSection(linearLayout, this.activity.getString(R.string.particle_sub_emitters), particleSystem3DComponent.subEmitters.enabled, new BoolConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda259
            @Override // org.catrobat.catroid.editor.InspectorManager.BoolConsumer
            public final void accept(boolean z) {
                this.f$0.m5249x857b086c(particleSystem3DComponent, go, z);
            }
        }, new ModuleContentBuilder() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda260
            @Override // org.catrobat.catroid.editor.InspectorManager.ModuleContentBuilder
            public final LinearLayout build() {
                return this.f$0.m5257x2b0b36c4(particleSystem3DComponent, go);
            }
        });
        addModuleSection(linearLayout, this.activity.getString(R.string.particle_trails), particleSystem3DComponent.trails.enabled, new BoolConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda261
            @Override // org.catrobat.catroid.editor.InspectorManager.BoolConsumer
            public final void accept(boolean z) {
                this.f$0.m5258xe482c463(particleSystem3DComponent, go, z);
            }
        }, new ModuleContentBuilder() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda262
            @Override // org.catrobat.catroid.editor.InspectorManager.ModuleContentBuilder
            public final LinearLayout build() {
                return this.f$0.m5265x2d9641c7(particleSystem3DComponent, go);
            }
        });
        addModuleSection(linearLayout, this.activity.getString(R.string.particle_tex_animation), particleSystem3DComponent.textureSheetAnimation.enabled, new BoolConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda263
            @Override // org.catrobat.catroid.editor.InspectorManager.BoolConsumer
            public final void accept(boolean z) {
                this.f$0.m5266xe70dcf66(particleSystem3DComponent, go, z);
            }
        }, new ModuleContentBuilder() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda264
            @Override // org.catrobat.catroid.editor.InspectorManager.ModuleContentBuilder
            public final LinearLayout build() {
                return this.f$0.m5270xccec05e2(particleSystem3DComponent, go);
            }
        });
        addSectionHeader(linearLayout, this.activity.getString(R.string.particle_renderer));
        ParticleSystem3DComponent.RenderMode[] renderModeArr = {ParticleSystem3DComponent.RenderMode.BILLBOARD, ParticleSystem3DComponent.RenderMode.STRETCHED_BILLBOARD, ParticleSystem3DComponent.RenderMode.HORIZONTAL_BILLBOARD, ParticleSystem3DComponent.RenderMode.VERTICAL_BILLBOARD, ParticleSystem3DComponent.RenderMode.MESH};
        int i5 = 0;
        while (true) {
            if (i5 >= 5) {
                i = 0;
                break;
            } else {
                if (renderModeArr[i5] == particleSystem3DComponent.renderer.renderMode) {
                    i = i5;
                    break;
                }
                i5++;
            }
        }
        addSpinnerEnum(linearLayout, this.activity.getString(R.string.particle_render_mode), renderModeArr, i, new EnumConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda266
            @Override // org.catrobat.catroid.editor.InspectorManager.EnumConsumer
            public final void accept(Object obj) {
                this.f$0.m5271x86639381(particleSystem3DComponent, go, (ParticleSystem3DComponent.RenderMode) obj);
            }
        });
        if (particleSystem3DComponent.renderer.renderMode == ParticleSystem3DComponent.RenderMode.STRETCHED_BILLBOARD) {
            addSimpleFloatInput(linearLayout, this.activity.getString(R.string.particle_length_scale), particleSystem3DComponent.renderer.lengthScale, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda267
                @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                public final void accept(float f2) {
                    this.f$0.m5272x3fdb2120(particleSystem3DComponent, go, f2);
                }
            });
            addSimpleFloatInput(linearLayout, this.activity.getString(R.string.particle_speed_scale), particleSystem3DComponent.renderer.speedScale, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda268
                @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                public final void accept(float f2) {
                    this.f$0.m5273xf952aebf(particleSystem3DComponent, go, f2);
                }
            });
        }
        int i6 = -1;
        if (particleSystem3DComponent.renderer.renderMode == ParticleSystem3DComponent.RenderMode.MESH) {
            ParticleSystem3DComponent.MeshType[] meshTypeArr = {ParticleSystem3DComponent.MeshType.CUBE, ParticleSystem3DComponent.MeshType.SPHERE_LOW, ParticleSystem3DComponent.MeshType.CYLINDER_LOW, ParticleSystem3DComponent.MeshType.CUSTOM};
            int i7 = 0;
            while (true) {
                if (i7 >= 4) {
                    i3 = 0;
                    break;
                } else {
                    if (meshTypeArr[i7] == particleSystem3DComponent.renderer.meshType) {
                        i3 = i7;
                        break;
                    }
                    i7++;
                }
            }
            i2 = -2;
            f = 1.0f;
            int i8 = i3;
            attributeSet = null;
            addSpinnerEnum(linearLayout, this.activity.getString(R.string.particle_mesh_shape), meshTypeArr, i8, new EnumConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda270
                @Override // org.catrobat.catroid.editor.InspectorManager.EnumConsumer
                public final void accept(Object obj) {
                    this.f$0.m5274xe998da69(particleSystem3DComponent, go, (ParticleSystem3DComponent.MeshType) obj);
                }
            });
            if (particleSystem3DComponent.renderer.meshType == ParticleSystem3DComponent.MeshType.CUSTOM) {
                LinearLayout linearLayout2 = new LinearLayout(this.activity);
                linearLayout2.setOrientation(0);
                linearLayout2.setGravity(16);
                TextView textView = new TextView(this.activity);
                textView.setText(particleSystem3DComponent.renderer.meshPath != null ? particleSystem3DComponent.renderer.meshPath : "None");
                i6 = -1;
                textView.setTextColor(-1);
                textView.setLayoutParams(new LinearLayout.LayoutParams(0, -2, 1.0f));
                linearLayout2.addView(textView);
                Button button = new Button(this.activity, null, 0, android.R.style.Widget.Material.Button.Small);
                button.setText("Set .glb");
                button.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda271
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.m5276x5c87f5a7(particleSystem3DComponent, go, view);
                    }
                });
                linearLayout2.addView(button);
                linearLayout.addView(linearLayout2);
            } else {
                i6 = -1;
            }
            addCheckbox(linearLayout, this.activity.getString(R.string.particle_align_to_velocity), particleSystem3DComponent.renderer.alignToVelocity, new BoolConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda272
                @Override // org.catrobat.catroid.editor.InspectorManager.BoolConsumer
                public final void accept(boolean z) {
                    this.f$0.m5277x15ff8346(particleSystem3DComponent, go, z);
                }
            });
        } else {
            i2 = -2;
            f = 1.0f;
            attributeSet = null;
        }
        addCheckbox(linearLayout, this.activity.getString(R.string.particle_additive_blend), particleSystem3DComponent.renderer.isAdditive, new BoolConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda273
            @Override // org.catrobat.catroid.editor.InspectorManager.BoolConsumer
            public final void accept(boolean z) {
                this.f$0.m5278xcf7710e5(particleSystem3DComponent, go, z);
            }
        });
        LinearLayout linearLayout3 = new LinearLayout(this.activity);
        linearLayout3.setOrientation(0);
        linearLayout3.setGravity(16);
        TextView textView2 = new TextView(this.activity);
        textView2.setText(particleSystem3DComponent.renderer.texturePath != null ? particleSystem3DComponent.renderer.texturePath : "Default");
        textView2.setTextColor(i6);
        textView2.setLayoutParams(new LinearLayout.LayoutParams(0, i2, f));
        linearLayout3.addView(textView2);
        Button button2 = new Button(this.activity, attributeSet, 0, android.R.style.Widget.Material.Button.Small);
        button2.setText("Set");
        button2.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda274
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m5280x42662c23(particleSystem3DComponent, go, view);
            }
        });
        linearLayout3.addView(button2);
        ImageButton imageButton = new ImageButton(this.activity);
        imageButton.setImageResource(android.R.drawable.ic_menu_close_clear_cancel);
        imageButton.setBackgroundColor(0);
        imageButton.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda275
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m5281xfbddb9c2(particleSystem3DComponent, go, view);
            }
        });
        linearLayout3.addView(imageButton);
        linearLayout.addView(linearLayout3);
    }

    static /* synthetic */ boolean lambda$createParticleSystem3DView$131(Component component) {
        return component instanceof ParticleSystem3DComponent;
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$132$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5186x98377a09(GameObject gameObject) {
        gameObject.components.removeIf(new Predicate() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda97
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return InspectorManager.lambda$createParticleSystem3DView$131((Component) obj);
            }
        });
        ThreeDManager threeDManager = this.threeDManager;
        if (threeDManager != null) {
            threeDManager.removeParticleEffect3D(gameObject.f591id);
        }
        ThreeDManager threeDManager2 = this.threeDManager;
        if (threeDManager2 != null) {
            threeDManager2.removeEditorProxy(gameObject.f591id);
        }
        m5171x5f6795b(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$133$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5187x51af07a8(ParticleSystem3DComponent particleSystem3DComponent, GameObject gameObject, boolean z) {
        particleSystem3DComponent.looping = z;
        updatePS3DImmediate(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$134$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5188xb269547(ParticleSystem3DComponent particleSystem3DComponent, GameObject gameObject, boolean z) {
        particleSystem3DComponent.prewarm = z;
        updatePS3DImmediate(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$135$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5189xc49e22e6(ParticleSystem3DComponent particleSystem3DComponent, GameObject gameObject, float f) {
        particleSystem3DComponent.duration = f;
        m5157x10c9d7e4(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$136$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5190x7e15b085(ParticleSystem3DComponent particleSystem3DComponent, GameObject gameObject, float f) {
        particleSystem3DComponent.maxParticles = (int) f;
        m5157x10c9d7e4(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$137$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5191x378d3e24(ParticleSystem3DComponent particleSystem3DComponent, GameObject gameObject, ParticleSystem3DComponent.SimulationSpace simulationSpace) {
        particleSystem3DComponent.simulationSpace = simulationSpace;
        updatePS3DImmediate(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$138$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5192xf104cbc3(ParticleSystem3DComponent particleSystem3DComponent, GameObject gameObject, boolean z) {
        particleSystem3DComponent.emission.enabled = z;
        m5157x10c9d7e4(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$144$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ LinearLayout m5198x80a0bb88(final ParticleSystem3DComponent particleSystem3DComponent, final GameObject gameObject) {
        LinearLayout linearLayout = new LinearLayout(this.activity);
        linearLayout.setOrientation(1);
        addMinMaxCurveEditor(linearLayout, this.activity.getString(R.string.particle_rate_over_time), particleSystem3DComponent.emission.rateOverTime, gameObject);
        addMinMaxCurveEditor(linearLayout, this.activity.getString(R.string.particle_rate_over_distance), particleSystem3DComponent.emission.rateOverDistance, gameObject);
        addSectionHeader(linearLayout, this.activity.getString(R.string.particle_bursts));
        for (final int i = 0; i < particleSystem3DComponent.emission.bursts.size(); i++) {
            final ParticleSystem3DComponent.Burst burst = particleSystem3DComponent.emission.bursts.get(i);
            LinearLayout linearLayout2 = new LinearLayout(this.activity);
            linearLayout2.setOrientation(0);
            addSmallFloatInput(linearLayout2, this.activity.getString(R.string.particle_time), burst.time, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda190
                @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                public final void accept(float f) {
                    this.f$0.m5193xaa7c5962(burst, gameObject, f);
                }
            });
            addSmallFloatInput(linearLayout2, this.activity.getString(R.string.particle_count), burst.count.constantMax, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda191
                @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                public final void accept(float f) {
                    this.f$0.m5194x9ac2850c(burst, gameObject, f);
                }
            });
            addSmallFloatInput(linearLayout2, this.activity.getString(R.string.particle_prob), burst.probability, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda192
                @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                public final void accept(float f) {
                    this.f$0.m5195x543a12ab(burst, gameObject, f);
                }
            });
            Button button = new Button(this.activity, null, 0, android.R.style.Widget.Material.Button.Small);
            button.setText(SpriteActivity.EXTRA_X_TRANSFORM);
            button.setTextColor(-65536);
            button.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda193
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.m5196xdb1a04a(particleSystem3DComponent, i, gameObject, view);
                }
            });
            linearLayout2.addView(button);
            linearLayout.addView(linearLayout2);
        }
        Button button2 = new Button(this.activity, null, 0, android.R.style.Widget.Material.Button.Small);
        button2.setText(Marker.ANY_NON_NULL_MARKER);
        button2.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda194
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m5197xc7292de9(particleSystem3DComponent, gameObject, view);
            }
        });
        linearLayout.addView(button2);
        return linearLayout;
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$139$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5193xaa7c5962(ParticleSystem3DComponent.Burst burst, GameObject gameObject, float f) {
        burst.time = f;
        m5157x10c9d7e4(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$140$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5194x9ac2850c(ParticleSystem3DComponent.Burst burst, GameObject gameObject, float f) {
        burst.count = new ParticleSystem3DComponent.MinMaxCurve(f);
        m5157x10c9d7e4(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$141$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5195x543a12ab(ParticleSystem3DComponent.Burst burst, GameObject gameObject, float f) {
        burst.probability = f;
        m5157x10c9d7e4(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$142$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5196xdb1a04a(ParticleSystem3DComponent particleSystem3DComponent, int i, GameObject gameObject, View view) {
        particleSystem3DComponent.emission.bursts.remove(i);
        m5157x10c9d7e4(gameObject);
        m5171x5f6795b(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$143$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5197xc7292de9(ParticleSystem3DComponent particleSystem3DComponent, GameObject gameObject, View view) {
        particleSystem3DComponent.emission.bursts.add(new ParticleSystem3DComponent.Burst(0.0f, 30.0f));
        m5157x10c9d7e4(gameObject);
        m5171x5f6795b(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$145$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5199x3a184927(ParticleSystem3DComponent particleSystem3DComponent, GameObject gameObject, boolean z) {
        particleSystem3DComponent.shape.enabled = z;
        m5157x10c9d7e4(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$158$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ LinearLayout m5212xdbf91845(final ParticleSystem3DComponent particleSystem3DComponent, final GameObject gameObject) {
        LinearLayout linearLayout = new LinearLayout(this.activity);
        linearLayout.setOrientation(1);
        addSpinnerEnum(linearLayout, this.activity.getString(R.string.particle_shape_type), ParticleSystem3DComponent.ShapeType.values(), particleSystem3DComponent.shape.type.ordinal(), new EnumConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda131
            @Override // org.catrobat.catroid.editor.InspectorManager.EnumConsumer
            public final void accept(Object obj) {
                this.f$0.m5200xf38fd6c6(particleSystem3DComponent, gameObject, (ParticleSystem3DComponent.ShapeType) obj);
            }
        });
        switch (AnonymousClass15.$SwitchMap$org$catrobat$catroid$raptor$ParticleSystem3DComponent$ShapeType[particleSystem3DComponent.shape.type.ordinal()]) {
            case 1:
                addSimpleFloatInput(linearLayout, this.activity.getString(R.string.particle_angle), particleSystem3DComponent.shape.coneAngle, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda135
                    @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                    public final void accept(float f) {
                        this.f$0.m5201xad076465(particleSystem3DComponent, gameObject, f);
                    }
                });
                addSimpleFloatInput(linearLayout, this.activity.getString(R.string.particle_radius), particleSystem3DComponent.shape.coneRadius, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda136
                    @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                    public final void accept(float f) {
                        this.f$0.m5202x667ef204(particleSystem3DComponent, gameObject, f);
                    }
                });
                addSimpleFloatInput(linearLayout, this.activity.getString(R.string.particle_length), particleSystem3DComponent.shape.coneLength, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda137
                    @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                    public final void accept(float f) {
                        this.f$0.m5203x1ff67fa3(particleSystem3DComponent, gameObject, f);
                    }
                });
                break;
            case 2:
            case 3:
                addSimpleFloatInput(linearLayout, this.activity.getString(R.string.particle_radius), particleSystem3DComponent.shape.sphereRadius, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda138
                    @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                    public final void accept(float f) {
                        this.f$0.m5204x103cab4d(particleSystem3DComponent, gameObject, f);
                    }
                });
                break;
            case 4:
                addSimpleFloatInput(linearLayout, SpriteActivity.EXTRA_X_TRANSFORM, particleSystem3DComponent.shape.boxSize.x, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda139
                    @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                    public final void accept(float f) {
                        this.f$0.m5205xc9b438ec(particleSystem3DComponent, gameObject, f);
                    }
                });
                addSimpleFloatInput(linearLayout, SpriteActivity.EXTRA_Y_TRANSFORM, particleSystem3DComponent.shape.boxSize.y, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda140
                    @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                    public final void accept(float f) {
                        this.f$0.m5206x832bc68b(particleSystem3DComponent, gameObject, f);
                    }
                });
                addSimpleFloatInput(linearLayout, "Z", particleSystem3DComponent.shape.boxSize.z, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda141
                    @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                    public final void accept(float f) {
                        this.f$0.m5207x3ca3542a(particleSystem3DComponent, gameObject, f);
                    }
                });
                break;
            case 5:
                addSimpleFloatInput(linearLayout, this.activity.getString(R.string.particle_radius), particleSystem3DComponent.shape.circleRadius, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda142
                    @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                    public final void accept(float f) {
                        this.f$0.m5208xf61ae1c9(particleSystem3DComponent, gameObject, f);
                    }
                });
                addSimpleFloatInput(linearLayout, this.activity.getString(R.string.particle_arc), particleSystem3DComponent.shape.circleArc, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda143
                    @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                    public final void accept(float f) {
                        this.f$0.m5209xaf926f68(particleSystem3DComponent, gameObject, f);
                    }
                });
                break;
            case 6:
                addSimpleFloatInput(linearLayout, this.activity.getString(R.string.particle_length), particleSystem3DComponent.shape.edgeLength, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda132
                    @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                    public final void accept(float f) {
                        this.f$0.m5210x6909fd07(particleSystem3DComponent, gameObject, f);
                    }
                });
                break;
        }
        addSpinnerEnum(linearLayout, this.activity.getString(R.string.particle_emit_from), ParticleSystem3DComponent.EmitFrom.values(), particleSystem3DComponent.shape.emitFrom.ordinal(), new EnumConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda134
            @Override // org.catrobat.catroid.editor.InspectorManager.EnumConsumer
            public final void accept(Object obj) {
                this.f$0.m5211x22818aa6(particleSystem3DComponent, gameObject, (ParticleSystem3DComponent.EmitFrom) obj);
            }
        });
        return linearLayout;
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$146$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5200xf38fd6c6(ParticleSystem3DComponent particleSystem3DComponent, GameObject gameObject, ParticleSystem3DComponent.ShapeType shapeType) {
        particleSystem3DComponent.shape.type = shapeType;
        m5157x10c9d7e4(gameObject);
        m5171x5f6795b(gameObject);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/editor/InspectorManager$15.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.editor.InspectorManager$15, reason: invalid class name */
    static /* synthetic */ class AnonymousClass15 {
        static final /* synthetic */ int[] $SwitchMap$org$catrobat$catroid$raptor$ColliderShapeData$ShapeType;
        static final /* synthetic */ int[] $SwitchMap$org$catrobat$catroid$raptor$ParticleSystem3DComponent$CurveMode;
        static final /* synthetic */ int[] $SwitchMap$org$catrobat$catroid$raptor$ParticleSystem3DComponent$GradientMode;
        static final /* synthetic */ int[] $SwitchMap$org$catrobat$catroid$raptor$ParticleSystem3DComponent$ShapeType;

        static {
            int[] iArr = new int[ParticleSystem3DComponent.ShapeType.values().length];
            $SwitchMap$org$catrobat$catroid$raptor$ParticleSystem3DComponent$ShapeType = iArr;
            try {
                iArr[ParticleSystem3DComponent.ShapeType.CONE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$raptor$ParticleSystem3DComponent$ShapeType[ParticleSystem3DComponent.ShapeType.SPHERE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$raptor$ParticleSystem3DComponent$ShapeType[ParticleSystem3DComponent.ShapeType.HEMISPHERE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$raptor$ParticleSystem3DComponent$ShapeType[ParticleSystem3DComponent.ShapeType.BOX.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$raptor$ParticleSystem3DComponent$ShapeType[ParticleSystem3DComponent.ShapeType.CIRCLE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$raptor$ParticleSystem3DComponent$ShapeType[ParticleSystem3DComponent.ShapeType.EDGE.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            int[] iArr2 = new int[ParticleSystem3DComponent.CurveMode.values().length];
            $SwitchMap$org$catrobat$catroid$raptor$ParticleSystem3DComponent$CurveMode = iArr2;
            try {
                iArr2[ParticleSystem3DComponent.CurveMode.CONSTANT.ordinal()] = 1;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$raptor$ParticleSystem3DComponent$CurveMode[ParticleSystem3DComponent.CurveMode.RANDOM_BETWEEN_TWO_CONSTANTS.ordinal()] = 2;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$raptor$ParticleSystem3DComponent$CurveMode[ParticleSystem3DComponent.CurveMode.CURVE.ordinal()] = 3;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$raptor$ParticleSystem3DComponent$CurveMode[ParticleSystem3DComponent.CurveMode.RANDOM_BETWEEN_TWO_CURVES.ordinal()] = 4;
            } catch (NoSuchFieldError unused10) {
            }
            int[] iArr3 = new int[ParticleSystem3DComponent.GradientMode.values().length];
            $SwitchMap$org$catrobat$catroid$raptor$ParticleSystem3DComponent$GradientMode = iArr3;
            try {
                iArr3[ParticleSystem3DComponent.GradientMode.COLOR.ordinal()] = 1;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$raptor$ParticleSystem3DComponent$GradientMode[ParticleSystem3DComponent.GradientMode.RANDOM_BETWEEN_TWO_COLORS.ordinal()] = 2;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$raptor$ParticleSystem3DComponent$GradientMode[ParticleSystem3DComponent.GradientMode.GRADIENT.ordinal()] = 3;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$raptor$ParticleSystem3DComponent$GradientMode[ParticleSystem3DComponent.GradientMode.RANDOM_BETWEEN_TWO_GRADIENTS.ordinal()] = 4;
            } catch (NoSuchFieldError unused14) {
            }
            int[] iArr4 = new int[ColliderShapeData.ShapeType.values().length];
            $SwitchMap$org$catrobat$catroid$raptor$ColliderShapeData$ShapeType = iArr4;
            try {
                iArr4[ColliderShapeData.ShapeType.BOX.ordinal()] = 1;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$raptor$ColliderShapeData$ShapeType[ColliderShapeData.ShapeType.SPHERE.ordinal()] = 2;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$raptor$ColliderShapeData$ShapeType[ColliderShapeData.ShapeType.CAPSULE.ordinal()] = 3;
            } catch (NoSuchFieldError unused17) {
            }
        }
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$147$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5201xad076465(ParticleSystem3DComponent particleSystem3DComponent, GameObject gameObject, float f) {
        particleSystem3DComponent.shape.coneAngle = f;
        m5157x10c9d7e4(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$148$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5202x667ef204(ParticleSystem3DComponent particleSystem3DComponent, GameObject gameObject, float f) {
        particleSystem3DComponent.shape.coneRadius = f;
        m5157x10c9d7e4(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$149$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5203x1ff67fa3(ParticleSystem3DComponent particleSystem3DComponent, GameObject gameObject, float f) {
        particleSystem3DComponent.shape.coneLength = f;
        m5157x10c9d7e4(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$150$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5204x103cab4d(ParticleSystem3DComponent particleSystem3DComponent, GameObject gameObject, float f) {
        particleSystem3DComponent.shape.sphereRadius = f;
        m5157x10c9d7e4(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$151$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5205xc9b438ec(ParticleSystem3DComponent particleSystem3DComponent, GameObject gameObject, float f) {
        particleSystem3DComponent.shape.boxSize.x = f;
        m5157x10c9d7e4(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$152$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5206x832bc68b(ParticleSystem3DComponent particleSystem3DComponent, GameObject gameObject, float f) {
        particleSystem3DComponent.shape.boxSize.y = f;
        m5157x10c9d7e4(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$153$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5207x3ca3542a(ParticleSystem3DComponent particleSystem3DComponent, GameObject gameObject, float f) {
        particleSystem3DComponent.shape.boxSize.z = f;
        m5157x10c9d7e4(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$154$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5208xf61ae1c9(ParticleSystem3DComponent particleSystem3DComponent, GameObject gameObject, float f) {
        particleSystem3DComponent.shape.circleRadius = f;
        m5157x10c9d7e4(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$155$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5209xaf926f68(ParticleSystem3DComponent particleSystem3DComponent, GameObject gameObject, float f) {
        particleSystem3DComponent.shape.circleArc = f;
        m5157x10c9d7e4(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$156$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5210x6909fd07(ParticleSystem3DComponent particleSystem3DComponent, GameObject gameObject, float f) {
        particleSystem3DComponent.shape.edgeLength = f;
        m5157x10c9d7e4(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$157$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5211x22818aa6(ParticleSystem3DComponent particleSystem3DComponent, GameObject gameObject, ParticleSystem3DComponent.EmitFrom emitFrom) {
        particleSystem3DComponent.shape.emitFrom = emitFrom;
        m5157x10c9d7e4(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$159$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5213x9570a5e4(ParticleSystem3DComponent particleSystem3DComponent, GameObject gameObject, boolean z) {
        particleSystem3DComponent.velocityOverLifetime.enabled = z;
        m5157x10c9d7e4(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$160$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ LinearLayout m5214x85b6d18e(ParticleSystem3DComponent particleSystem3DComponent, GameObject gameObject) {
        LinearLayout linearLayout = new LinearLayout(this.activity);
        linearLayout.setOrientation(1);
        addMinMaxCurveEditor(linearLayout, this.activity.getString(R.string.particle_linear_x), particleSystem3DComponent.velocityOverLifetime.x, gameObject);
        addMinMaxCurveEditor(linearLayout, this.activity.getString(R.string.particle_linear_y), particleSystem3DComponent.velocityOverLifetime.y, gameObject);
        addMinMaxCurveEditor(linearLayout, this.activity.getString(R.string.particle_linear_z), particleSystem3DComponent.velocityOverLifetime.z, gameObject);
        addMinMaxCurveEditor(linearLayout, this.activity.getString(R.string.particle_orbital_y), particleSystem3DComponent.velocityOverLifetime.orbitalY, gameObject);
        addMinMaxCurveEditor(linearLayout, this.activity.getString(R.string.particle_radial), particleSystem3DComponent.velocityOverLifetime.radial, gameObject);
        addMinMaxCurveEditor(linearLayout, this.activity.getString(R.string.particle_speed_modifier), particleSystem3DComponent.velocityOverLifetime.speedModifier, gameObject);
        return linearLayout;
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$161$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5215x3f2e5f2d(ParticleSystem3DComponent particleSystem3DComponent, GameObject gameObject, boolean z) {
        particleSystem3DComponent.forceOverLifetime.enabled = z;
        m5157x10c9d7e4(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$162$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ LinearLayout m5216xf8a5eccc(ParticleSystem3DComponent particleSystem3DComponent, GameObject gameObject) {
        LinearLayout linearLayout = new LinearLayout(this.activity);
        linearLayout.setOrientation(1);
        addMinMaxCurveEditor(linearLayout, this.activity.getString(R.string.particle_force_x), particleSystem3DComponent.forceOverLifetime.x, gameObject);
        addMinMaxCurveEditor(linearLayout, this.activity.getString(R.string.particle_force_y), particleSystem3DComponent.forceOverLifetime.y, gameObject);
        addMinMaxCurveEditor(linearLayout, this.activity.getString(R.string.particle_force_z), particleSystem3DComponent.forceOverLifetime.z, gameObject);
        return linearLayout;
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$163$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5217xb21d7a6b(ParticleSystem3DComponent particleSystem3DComponent, GameObject gameObject, boolean z) {
        particleSystem3DComponent.colorOverLifetime.enabled = z;
        m5157x10c9d7e4(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$164$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ LinearLayout m5218x6b95080a(ParticleSystem3DComponent particleSystem3DComponent, GameObject gameObject) {
        LinearLayout linearLayout = new LinearLayout(this.activity);
        linearLayout.setOrientation(1);
        addMinMaxGradientEditor(linearLayout, this.activity.getString(R.string.particle_color), particleSystem3DComponent.colorOverLifetime.color, gameObject);
        return linearLayout;
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$165$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5219x250c95a9(ParticleSystem3DComponent particleSystem3DComponent, GameObject gameObject, boolean z) {
        particleSystem3DComponent.sizeOverLifetime.enabled = z;
        m5157x10c9d7e4(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$167$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ LinearLayout m5221x97fbb0e7(final ParticleSystem3DComponent particleSystem3DComponent, final GameObject gameObject) {
        LinearLayout linearLayout = new LinearLayout(this.activity);
        linearLayout.setOrientation(1);
        addCheckbox(linearLayout, this.activity.getString(R.string.particle_separate_axes), particleSystem3DComponent.sizeOverLifetime.separateAxes, new BoolConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda222
            @Override // org.catrobat.catroid.editor.InspectorManager.BoolConsumer
            public final void accept(boolean z) {
                this.f$0.m5220xde842348(particleSystem3DComponent, gameObject, z);
            }
        });
        if (particleSystem3DComponent.sizeOverLifetime.separateAxes) {
            addMinMaxCurveEditor(linearLayout, SpriteActivity.EXTRA_X_TRANSFORM, particleSystem3DComponent.sizeOverLifetime.sizeX, gameObject);
            addMinMaxCurveEditor(linearLayout, SpriteActivity.EXTRA_Y_TRANSFORM, particleSystem3DComponent.sizeOverLifetime.sizeY, gameObject);
            addMinMaxCurveEditor(linearLayout, "Z", particleSystem3DComponent.sizeOverLifetime.sizeZ, gameObject);
        } else {
            addMinMaxCurveEditor(linearLayout, this.activity.getString(R.string.particle_size), particleSystem3DComponent.sizeOverLifetime.size, gameObject);
        }
        return linearLayout;
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$166$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5220xde842348(ParticleSystem3DComponent particleSystem3DComponent, GameObject gameObject, boolean z) {
        particleSystem3DComponent.sizeOverLifetime.separateAxes = z;
        m5157x10c9d7e4(gameObject);
        m5171x5f6795b(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$168$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5222x51733e86(ParticleSystem3DComponent particleSystem3DComponent, GameObject gameObject, boolean z) {
        particleSystem3DComponent.rotationOverLifetime.enabled = z;
        m5157x10c9d7e4(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$170$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ LinearLayout m5224xfb30f7cf(final ParticleSystem3DComponent particleSystem3DComponent, final GameObject gameObject) {
        LinearLayout linearLayout = new LinearLayout(this.activity);
        linearLayout.setOrientation(1);
        addCheckbox(linearLayout, this.activity.getString(R.string.particle_separate_axes), particleSystem3DComponent.rotationOverLifetime.separateAxes, new BoolConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda178
            @Override // org.catrobat.catroid.editor.InspectorManager.BoolConsumer
            public final void accept(boolean z) {
                this.f$0.m5223xaeacc25(particleSystem3DComponent, gameObject, z);
            }
        });
        if (particleSystem3DComponent.rotationOverLifetime.separateAxes) {
            addMinMaxCurveEditor(linearLayout, SpriteActivity.EXTRA_X_TRANSFORM, particleSystem3DComponent.rotationOverLifetime.angularVelocityX, gameObject);
            addMinMaxCurveEditor(linearLayout, SpriteActivity.EXTRA_Y_TRANSFORM, particleSystem3DComponent.rotationOverLifetime.angularVelocityY, gameObject);
            addMinMaxCurveEditor(linearLayout, "Z", particleSystem3DComponent.rotationOverLifetime.angularVelocityZ, gameObject);
        } else {
            addMinMaxCurveEditor(linearLayout, this.activity.getString(R.string.particle_angular_velocity), particleSystem3DComponent.rotationOverLifetime.angularVelocity, gameObject);
        }
        return linearLayout;
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$169$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5223xaeacc25(ParticleSystem3DComponent particleSystem3DComponent, GameObject gameObject, boolean z) {
        particleSystem3DComponent.rotationOverLifetime.separateAxes = z;
        m5157x10c9d7e4(gameObject);
        m5171x5f6795b(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$171$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5225xb4a8856e(ParticleSystem3DComponent particleSystem3DComponent, GameObject gameObject, boolean z) {
        particleSystem3DComponent.noise.enabled = z;
        m5157x10c9d7e4(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$181$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ LinearLayout m5235x2a22abaf(final ParticleSystem3DComponent particleSystem3DComponent, final GameObject gameObject) {
        LinearLayout linearLayout = new LinearLayout(this.activity);
        linearLayout.setOrientation(1);
        addSimpleFloatInput(linearLayout, this.activity.getString(R.string.particle_strength), particleSystem3DComponent.noise.strength, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda237
            @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
            public final void accept(float f) {
                this.f$0.m5226x6e20130d(particleSystem3DComponent, gameObject, f);
            }
        });
        addSimpleFloatInput(linearLayout, this.activity.getString(R.string.particle_frequency), particleSystem3DComponent.noise.frequency, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda238
            @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
            public final void accept(float f) {
                this.f$0.m5227x2797a0ac(particleSystem3DComponent, gameObject, f);
            }
        });
        addSimpleFloatInput(linearLayout, this.activity.getString(R.string.particle_octaves), particleSystem3DComponent.noise.octaves, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda239
            @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
            public final void accept(float f) {
                this.f$0.m5228xe10f2e4b(particleSystem3DComponent, gameObject, f);
            }
        });
        addSimpleFloatInput(linearLayout, this.activity.getString(R.string.particle_scroll_speed), particleSystem3DComponent.noise.scrollSpeed, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda240
            @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
            public final void accept(float f) {
                this.f$0.m5229x9a86bbea(particleSystem3DComponent, gameObject, f);
            }
        });
        addCheckbox(linearLayout, this.activity.getString(R.string.particle_damping), particleSystem3DComponent.noise.damping, new BoolConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda241
            @Override // org.catrobat.catroid.editor.InspectorManager.BoolConsumer
            public final void accept(boolean z) {
                this.f$0.m5230x53fe4989(particleSystem3DComponent, gameObject, z);
            }
        });
        addCheckbox(linearLayout, this.activity.getString(R.string.particle_separate_axes), particleSystem3DComponent.noise.separateAxes, new BoolConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda242
            @Override // org.catrobat.catroid.editor.InspectorManager.BoolConsumer
            public final void accept(boolean z) {
                this.f$0.m5231xd75d728(particleSystem3DComponent, gameObject, z);
            }
        });
        if (particleSystem3DComponent.noise.separateAxes) {
            addSimpleFloatInput(linearLayout, SpriteActivity.EXTRA_X_TRANSFORM, particleSystem3DComponent.noise.strengthX, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda243
                @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                public final void accept(float f) {
                    this.f$0.m5232xc6ed64c7(particleSystem3DComponent, gameObject, f);
                }
            });
            addSimpleFloatInput(linearLayout, SpriteActivity.EXTRA_Y_TRANSFORM, particleSystem3DComponent.noise.strengthY, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda245
                @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                public final void accept(float f) {
                    this.f$0.m5233x8064f266(particleSystem3DComponent, gameObject, f);
                }
            });
            addSimpleFloatInput(linearLayout, "Z", particleSystem3DComponent.noise.strengthZ, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda246
                @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                public final void accept(float f) {
                    this.f$0.m5234x70ab1e10(particleSystem3DComponent, gameObject, f);
                }
            });
        }
        return linearLayout;
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$172$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5226x6e20130d(ParticleSystem3DComponent particleSystem3DComponent, GameObject gameObject, float f) {
        particleSystem3DComponent.noise.strength = f;
        m5157x10c9d7e4(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$173$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5227x2797a0ac(ParticleSystem3DComponent particleSystem3DComponent, GameObject gameObject, float f) {
        particleSystem3DComponent.noise.frequency = f;
        m5157x10c9d7e4(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$174$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5228xe10f2e4b(ParticleSystem3DComponent particleSystem3DComponent, GameObject gameObject, float f) {
        particleSystem3DComponent.noise.octaves = Math.max(1, (int) f);
        m5157x10c9d7e4(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$175$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5229x9a86bbea(ParticleSystem3DComponent particleSystem3DComponent, GameObject gameObject, float f) {
        particleSystem3DComponent.noise.scrollSpeed = f;
        m5157x10c9d7e4(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$176$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5230x53fe4989(ParticleSystem3DComponent particleSystem3DComponent, GameObject gameObject, boolean z) {
        particleSystem3DComponent.noise.damping = z;
        m5157x10c9d7e4(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$177$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5231xd75d728(ParticleSystem3DComponent particleSystem3DComponent, GameObject gameObject, boolean z) {
        particleSystem3DComponent.noise.separateAxes = z;
        m5157x10c9d7e4(gameObject);
        m5171x5f6795b(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$178$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5232xc6ed64c7(ParticleSystem3DComponent particleSystem3DComponent, GameObject gameObject, float f) {
        particleSystem3DComponent.noise.strengthX = f;
        m5157x10c9d7e4(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$179$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5233x8064f266(ParticleSystem3DComponent particleSystem3DComponent, GameObject gameObject, float f) {
        particleSystem3DComponent.noise.strengthY = f;
        m5157x10c9d7e4(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$180$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5234x70ab1e10(ParticleSystem3DComponent particleSystem3DComponent, GameObject gameObject, float f) {
        particleSystem3DComponent.noise.strengthZ = f;
        m5157x10c9d7e4(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$182$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5236xe39a394e(ParticleSystem3DComponent particleSystem3DComponent, GameObject gameObject, boolean z) {
        particleSystem3DComponent.collision.enabled = z;
        m5157x10c9d7e4(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$194$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ LinearLayout m5248xcc037acd(final ParticleSystem3DComponent particleSystem3DComponent, final GameObject gameObject) {
        LinearLayout linearLayout = new LinearLayout(this.activity);
        linearLayout.setOrientation(1);
        addSpinnerEnum(linearLayout, this.activity.getString(R.string.particle_mode), ParticleSystem3DComponent.CollisionMode.values(), particleSystem3DComponent.collision.mode.ordinal(), new EnumConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda195
            @Override // org.catrobat.catroid.editor.InspectorManager.EnumConsumer
            public final void accept(Object obj) {
                this.f$0.m5237x9d11c6ed(particleSystem3DComponent, gameObject, (ParticleSystem3DComponent.CollisionMode) obj);
            }
        });
        addSimpleFloatInput(linearLayout, this.activity.getString(R.string.particle_bounce), particleSystem3DComponent.collision.bounce, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda197
            @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
            public final void accept(float f) {
                this.f$0.m5238x5689548c(particleSystem3DComponent, gameObject, f);
            }
        });
        addSimpleFloatInput(linearLayout, this.activity.getString(R.string.particle_dampen), particleSystem3DComponent.collision.dampen, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda198
            @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
            public final void accept(float f) {
                this.f$0.m5239x1000e22b(particleSystem3DComponent, gameObject, f);
            }
        });
        addSimpleFloatInput(linearLayout, this.activity.getString(R.string.particle_lifetime_loss), particleSystem3DComponent.collision.lifetimeLoss, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda200
            @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
            public final void accept(float f) {
                this.f$0.m5240xc9786fca(particleSystem3DComponent, gameObject, f);
            }
        });
        addSimpleFloatInput(linearLayout, this.activity.getString(R.string.particle_min_kill_speed), particleSystem3DComponent.collision.minKillSpeed, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda201
            @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
            public final void accept(float f) {
                this.f$0.m5241x82effd69(particleSystem3DComponent, gameObject, f);
            }
        });
        addSimpleFloatInput(linearLayout, this.activity.getString(R.string.particle_radius_scale), particleSystem3DComponent.collision.radiusScale, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda202
            @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
            public final void accept(float f) {
                this.f$0.m5242x3c678b08(particleSystem3DComponent, gameObject, f);
            }
        });
        addSpinnerEnum(linearLayout, this.activity.getString(R.string.particle_quality), ParticleSystem3DComponent.CollisionQuality.values(), particleSystem3DComponent.collision.quality.ordinal(), new EnumConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda203
            @Override // org.catrobat.catroid.editor.InspectorManager.EnumConsumer
            public final void accept(Object obj) {
                this.f$0.m5243xf5df18a7(particleSystem3DComponent, gameObject, (ParticleSystem3DComponent.CollisionQuality) obj);
            }
        });
        if (particleSystem3DComponent.collision.mode == ParticleSystem3DComponent.CollisionMode.PLANES) {
            addSectionHeader(linearLayout, this.activity.getString(R.string.particle_collision_planes));
            for (final int i = 0; i < particleSystem3DComponent.collision.planes.size(); i++) {
                final ParticleSystem3DComponent.CollisionPlane collisionPlane = particleSystem3DComponent.collision.planes.get(i);
                LinearLayout linearLayout2 = new LinearLayout(this.activity);
                linearLayout2.setOrientation(1);
                addSimpleFloatInput(linearLayout2, "Point Y", collisionPlane.point.y, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda204
                    @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                    public final void accept(float f) {
                        this.f$0.m5244xe6254451(collisionPlane, gameObject, f);
                    }
                });
                addSimpleFloatInput(linearLayout2, "Normal Y", collisionPlane.normal.y, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda205
                    @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                    public final void accept(float f) {
                        this.f$0.m5245x9f9cd1f0(collisionPlane, gameObject, f);
                    }
                });
                Button button = new Button(this.activity, null, 0, android.R.style.Widget.Material.Button.Small);
                button.setText(SpriteActivity.EXTRA_X_TRANSFORM);
                button.setTextColor(-65536);
                button.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda206
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.m5246x59145f8f(particleSystem3DComponent, i, gameObject, view);
                    }
                });
                linearLayout2.addView(button);
                linearLayout.addView(linearLayout2);
            }
            Button button2 = new Button(this.activity, null, 0, android.R.style.Widget.Material.Button.Small);
            button2.setText(Marker.ANY_NON_NULL_MARKER);
            button2.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda196
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.m5247x128bed2e(particleSystem3DComponent, gameObject, view);
                }
            });
            linearLayout.addView(button2);
        }
        return linearLayout;
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$183$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5237x9d11c6ed(ParticleSystem3DComponent particleSystem3DComponent, GameObject gameObject, ParticleSystem3DComponent.CollisionMode collisionMode) {
        particleSystem3DComponent.collision.mode = collisionMode;
        m5157x10c9d7e4(gameObject);
        m5171x5f6795b(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$184$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5238x5689548c(ParticleSystem3DComponent particleSystem3DComponent, GameObject gameObject, float f) {
        particleSystem3DComponent.collision.bounce = f;
        m5157x10c9d7e4(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$185$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5239x1000e22b(ParticleSystem3DComponent particleSystem3DComponent, GameObject gameObject, float f) {
        particleSystem3DComponent.collision.dampen = f;
        m5157x10c9d7e4(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$186$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5240xc9786fca(ParticleSystem3DComponent particleSystem3DComponent, GameObject gameObject, float f) {
        particleSystem3DComponent.collision.lifetimeLoss = f;
        m5157x10c9d7e4(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$187$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5241x82effd69(ParticleSystem3DComponent particleSystem3DComponent, GameObject gameObject, float f) {
        particleSystem3DComponent.collision.minKillSpeed = f;
        m5157x10c9d7e4(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$188$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5242x3c678b08(ParticleSystem3DComponent particleSystem3DComponent, GameObject gameObject, float f) {
        particleSystem3DComponent.collision.radiusScale = f;
        m5157x10c9d7e4(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$189$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5243xf5df18a7(ParticleSystem3DComponent particleSystem3DComponent, GameObject gameObject, ParticleSystem3DComponent.CollisionQuality collisionQuality) {
        particleSystem3DComponent.collision.quality = collisionQuality;
        m5157x10c9d7e4(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$190$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5244xe6254451(ParticleSystem3DComponent.CollisionPlane collisionPlane, GameObject gameObject, float f) {
        collisionPlane.point.y = f;
        m5157x10c9d7e4(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$191$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5245x9f9cd1f0(ParticleSystem3DComponent.CollisionPlane collisionPlane, GameObject gameObject, float f) {
        collisionPlane.normal.y = f;
        m5157x10c9d7e4(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$192$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5246x59145f8f(ParticleSystem3DComponent particleSystem3DComponent, int i, GameObject gameObject, View view) {
        particleSystem3DComponent.collision.planes.remove(i);
        m5157x10c9d7e4(gameObject);
        m5171x5f6795b(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$193$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5247x128bed2e(ParticleSystem3DComponent particleSystem3DComponent, GameObject gameObject, View view) {
        particleSystem3DComponent.collision.planes.add(new ParticleSystem3DComponent.CollisionPlane());
        m5157x10c9d7e4(gameObject);
        m5171x5f6795b(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$195$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5249x857b086c(ParticleSystem3DComponent particleSystem3DComponent, GameObject gameObject, boolean z) {
        particleSystem3DComponent.subEmitters.enabled = z;
        m5157x10c9d7e4(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$203$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ LinearLayout m5257x2b0b36c4(final ParticleSystem3DComponent particleSystem3DComponent, final GameObject gameObject) {
        LinearLayout linearLayout = new LinearLayout(this.activity);
        int i = 1;
        linearLayout.setOrientation(1);
        final int i2 = 0;
        while (i2 < particleSystem3DComponent.subEmitters.entries.size()) {
            final ParticleSystem3DComponent.SubEmitterEntry subEmitterEntry = particleSystem3DComponent.subEmitters.entries.get(i2);
            LinearLayout linearLayout2 = new LinearLayout(this.activity);
            linearLayout2.setOrientation(i);
            addSpinnerEnum(linearLayout2, this.activity.getString(R.string.particle_trigger), ParticleSystem3DComponent.SubEmitterTrigger.values(), subEmitterEntry.trigger.ordinal(), new EnumConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda22
                @Override // org.catrobat.catroid.editor.InspectorManager.EnumConsumer
                public final void accept(Object obj) {
                    this.f$0.m5250x3ef2960b(subEmitterEntry, gameObject, (ParticleSystem3DComponent.SubEmitterTrigger) obj);
                }
            });
            final Button button = new Button(this.activity, null, 0, android.R.style.Widget.Material.Button.Small);
            button.setText((subEmitterEntry.subEmitterObjectId == null || subEmitterEntry.subEmitterObjectId.isEmpty()) ? "Select Object..." : subEmitterEntry.subEmitterObjectId);
            button.setTextColor(-1);
            button.setAllCaps(false);
            button.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda33
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.m5252xb1e1b149(gameObject, subEmitterEntry, button, view);
                }
            });
            linearLayout2.addView(button);
            addSimpleFloatInput(linearLayout2, this.activity.getString(R.string.particle_probability), subEmitterEntry.probability, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda44
                @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                public final void accept(float f) {
                    this.f$0.m5253x6b593ee8(subEmitterEntry, gameObject, f);
                }
            });
            addSimpleFloatInput(linearLayout2, this.activity.getString(R.string.particle_emit_count), subEmitterEntry.emitCount, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda55
                @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                public final void accept(float f) {
                    this.f$0.m5254xfea48de7(subEmitterEntry, gameObject, f);
                }
            });
            Button button2 = new Button(this.activity, null, 0, android.R.style.Widget.Material.Button.Small);
            button2.setText(SpriteActivity.EXTRA_X_TRANSFORM);
            button2.setTextColor(-65536);
            button2.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda66
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.m5255xb81c1b86(particleSystem3DComponent, i2, gameObject, view);
                }
            });
            linearLayout2.addView(button2);
            linearLayout.addView(linearLayout2);
            i2++;
            i = 1;
        }
        Button button3 = new Button(this.activity, null, 0, android.R.style.Widget.Material.Button.Small);
        button3.setText(Marker.ANY_NON_NULL_MARKER);
        button3.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda77
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m5256x7193a925(particleSystem3DComponent, gameObject, view);
            }
        });
        linearLayout.addView(button3);
        return linearLayout;
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$196$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5250x3ef2960b(ParticleSystem3DComponent.SubEmitterEntry subEmitterEntry, GameObject gameObject, ParticleSystem3DComponent.SubEmitterTrigger subEmitterTrigger) {
        subEmitterEntry.trigger = subEmitterTrigger;
        m5157x10c9d7e4(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$198$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5252xb1e1b149(final GameObject gameObject, final ParticleSystem3DComponent.SubEmitterEntry subEmitterEntry, final Button button, View view) {
        final ArrayList arrayList = new ArrayList();
        for (GameObject gameObject2 : this.sceneManager.getAllGameObjects().values()) {
            if (gameObject2.hasComponent(ParticleSystem3DComponent.class) && !gameObject2.f591id.equals(gameObject.f591id)) {
                arrayList.add(gameObject2.f591id);
            }
        }
        if (arrayList.isEmpty()) {
            Toast.makeText(this.activity, "No other objects with Particle System 3D found", 0).show();
        } else {
            new AlertDialog.Builder(this.activity, 2131952279).setTitle("Select Sub Emitter").setItems((CharSequence[]) arrayList.toArray(new String[0]), new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda228
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.m5251xf86a23aa(subEmitterEntry, arrayList, button, gameObject, dialogInterface, i);
                }
            }).show();
        }
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$197$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5251xf86a23aa(ParticleSystem3DComponent.SubEmitterEntry subEmitterEntry, List list, Button button, GameObject gameObject, DialogInterface dialogInterface, int i) {
        subEmitterEntry.subEmitterObjectId = (String) list.get(i);
        button.setText(subEmitterEntry.subEmitterObjectId);
        updatePS3DImmediate(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$199$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5253x6b593ee8(ParticleSystem3DComponent.SubEmitterEntry subEmitterEntry, GameObject gameObject, float f) {
        subEmitterEntry.probability = f;
        m5157x10c9d7e4(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$200$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5254xfea48de7(ParticleSystem3DComponent.SubEmitterEntry subEmitterEntry, GameObject gameObject, float f) {
        subEmitterEntry.emitCount = Math.max(0, (int) f);
        m5157x10c9d7e4(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$201$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5255xb81c1b86(ParticleSystem3DComponent particleSystem3DComponent, int i, GameObject gameObject, View view) {
        particleSystem3DComponent.subEmitters.entries.remove(i);
        m5157x10c9d7e4(gameObject);
        m5171x5f6795b(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$202$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5256x7193a925(ParticleSystem3DComponent particleSystem3DComponent, GameObject gameObject, View view) {
        particleSystem3DComponent.subEmitters.entries.add(new ParticleSystem3DComponent.SubEmitterEntry());
        m5157x10c9d7e4(gameObject);
        m5171x5f6795b(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$204$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5258xe482c463(ParticleSystem3DComponent particleSystem3DComponent, GameObject gameObject, boolean z) {
        particleSystem3DComponent.trails.enabled = z;
        m5157x10c9d7e4(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$211$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ LinearLayout m5265x2d9641c7(final ParticleSystem3DComponent particleSystem3DComponent, final GameObject gameObject) {
        LinearLayout linearLayout = new LinearLayout(this.activity);
        linearLayout.setOrientation(1);
        addSimpleFloatInput(linearLayout, this.activity.getString(R.string.particle_ratio), particleSystem3DComponent.trails.ratio, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda258
            @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
            public final void accept(float f) {
                this.f$0.m5259x9dfa5202(particleSystem3DComponent, gameObject, f);
            }
        });
        addSimpleFloatInput(linearLayout, this.activity.getString(R.string.particle_lifetime), particleSystem3DComponent.trails.lifetime, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda269
            @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
            public final void accept(float f) {
                this.f$0.m5260x5771dfa1(particleSystem3DComponent, gameObject, f);
            }
        });
        addSimpleFloatInput(linearLayout, this.activity.getString(R.string.particle_min_vertex_dist), particleSystem3DComponent.trails.minimumVertexDistance, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda280
            @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
            public final void accept(float f) {
                this.f$0.m5261x10e96d40(particleSystem3DComponent, gameObject, f);
            }
        });
        addCheckbox(linearLayout, this.activity.getString(R.string.particle_world_space), particleSystem3DComponent.trails.worldSpace, new BoolConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda291
            @Override // org.catrobat.catroid.editor.InspectorManager.BoolConsumer
            public final void accept(boolean z) {
                this.f$0.m5262xca60fadf(particleSystem3DComponent, gameObject, z);
            }
        });
        addCheckbox(linearLayout, this.activity.getString(R.string.particle_die_with_particles), particleSystem3DComponent.trails.dieWithParticles, new BoolConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda302
            @Override // org.catrobat.catroid.editor.InspectorManager.BoolConsumer
            public final void accept(boolean z) {
                this.f$0.m5263x83d8887e(particleSystem3DComponent, gameObject, z);
            }
        });
        addCheckbox(linearLayout, this.activity.getString(R.string.particle_inherit_color), particleSystem3DComponent.trails.inheritParticleColor, new BoolConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda313
            @Override // org.catrobat.catroid.editor.InspectorManager.BoolConsumer
            public final void accept(boolean z) {
                this.f$0.m5264x741eb428(particleSystem3DComponent, gameObject, z);
            }
        });
        addMinMaxCurveEditor(linearLayout, this.activity.getString(R.string.particle_width_over_trail), particleSystem3DComponent.trails.widthOverTrail, gameObject);
        return linearLayout;
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$205$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5259x9dfa5202(ParticleSystem3DComponent particleSystem3DComponent, GameObject gameObject, float f) {
        particleSystem3DComponent.trails.ratio = f;
        m5157x10c9d7e4(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$206$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5260x5771dfa1(ParticleSystem3DComponent particleSystem3DComponent, GameObject gameObject, float f) {
        particleSystem3DComponent.trails.lifetime = f;
        m5157x10c9d7e4(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$207$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5261x10e96d40(ParticleSystem3DComponent particleSystem3DComponent, GameObject gameObject, float f) {
        particleSystem3DComponent.trails.minimumVertexDistance = f;
        m5157x10c9d7e4(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$208$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5262xca60fadf(ParticleSystem3DComponent particleSystem3DComponent, GameObject gameObject, boolean z) {
        particleSystem3DComponent.trails.worldSpace = z;
        m5157x10c9d7e4(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$209$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5263x83d8887e(ParticleSystem3DComponent particleSystem3DComponent, GameObject gameObject, boolean z) {
        particleSystem3DComponent.trails.dieWithParticles = z;
        m5157x10c9d7e4(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$210$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5264x741eb428(ParticleSystem3DComponent particleSystem3DComponent, GameObject gameObject, boolean z) {
        particleSystem3DComponent.trails.inheritParticleColor = z;
        m5157x10c9d7e4(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$212$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5266xe70dcf66(ParticleSystem3DComponent particleSystem3DComponent, GameObject gameObject, boolean z) {
        particleSystem3DComponent.textureSheetAnimation.enabled = z;
        m5157x10c9d7e4(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$216$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ LinearLayout m5270xccec05e2(final ParticleSystem3DComponent particleSystem3DComponent, final GameObject gameObject) {
        LinearLayout linearLayout = new LinearLayout(this.activity);
        linearLayout.setOrientation(1);
        addSimpleFloatInput(linearLayout, this.activity.getString(R.string.particle_tiles_x), particleSystem3DComponent.textureSheetAnimation.tilesX, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda213
            @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
            public final void accept(float f) {
                this.f$0.m5267xa0855d05(particleSystem3DComponent, gameObject, f);
            }
        });
        addSimpleFloatInput(linearLayout, this.activity.getString(R.string.particle_tiles_y), particleSystem3DComponent.textureSheetAnimation.tilesY, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda214
            @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
            public final void accept(float f) {
                this.f$0.m5268x59fceaa4(particleSystem3DComponent, gameObject, f);
            }
        });
        addSimpleFloatInput(linearLayout, this.activity.getString(R.string.particle_cycles), particleSystem3DComponent.textureSheetAnimation.cycles, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda215
            @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
            public final void accept(float f) {
                this.f$0.m5269x13747843(particleSystem3DComponent, gameObject, f);
            }
        });
        addMinMaxCurveEditor(linearLayout, this.activity.getString(R.string.particle_frame_over_time), particleSystem3DComponent.textureSheetAnimation.frameOverTime, gameObject);
        return linearLayout;
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$213$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5267xa0855d05(ParticleSystem3DComponent particleSystem3DComponent, GameObject gameObject, float f) {
        particleSystem3DComponent.textureSheetAnimation.tilesX = Math.max(1, (int) f);
        m5157x10c9d7e4(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$214$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5268x59fceaa4(ParticleSystem3DComponent particleSystem3DComponent, GameObject gameObject, float f) {
        particleSystem3DComponent.textureSheetAnimation.tilesY = Math.max(1, (int) f);
        m5157x10c9d7e4(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$215$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5269x13747843(ParticleSystem3DComponent particleSystem3DComponent, GameObject gameObject, float f) {
        particleSystem3DComponent.textureSheetAnimation.cycles = Math.max(1, (int) f);
        m5157x10c9d7e4(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$217$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5271x86639381(ParticleSystem3DComponent particleSystem3DComponent, GameObject gameObject, ParticleSystem3DComponent.RenderMode renderMode) {
        particleSystem3DComponent.renderer.renderMode = renderMode;
        updatePS3DImmediate(gameObject);
        m5171x5f6795b(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$218$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5272x3fdb2120(ParticleSystem3DComponent particleSystem3DComponent, GameObject gameObject, float f) {
        particleSystem3DComponent.renderer.lengthScale = f;
        m5157x10c9d7e4(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$219$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5273xf952aebf(ParticleSystem3DComponent particleSystem3DComponent, GameObject gameObject, float f) {
        particleSystem3DComponent.renderer.speedScale = f;
        m5157x10c9d7e4(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$220$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5274xe998da69(ParticleSystem3DComponent particleSystem3DComponent, GameObject gameObject, ParticleSystem3DComponent.MeshType meshType) {
        particleSystem3DComponent.renderer.meshType = meshType;
        updatePS3DImmediate(gameObject);
        m5171x5f6795b(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$222$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5276x5c87f5a7(final ParticleSystem3DComponent particleSystem3DComponent, final GameObject gameObject, View view) {
        File[] fileArrListFiles = ProjectManager.getInstance().getCurrentProject().getFilesDir().listFiles();
        if (fileArrListFiles == null) {
            return;
        }
        final ArrayList arrayList = new ArrayList();
        for (File file : fileArrListFiles) {
            String lowerCase = file.getName().toLowerCase();
            if (lowerCase.endsWith(".glb") || lowerCase.endsWith(".gltf") || lowerCase.endsWith(".obj")) {
                arrayList.add(file.getName());
            }
        }
        if (arrayList.isEmpty()) {
            Toast.makeText(this.activity, "No 3D models found", 0).show();
        } else {
            new AlertDialog.Builder(this.activity, 2131952279).setTitle(R.string.particle_mesh_shape).setItems((CharSequence[]) arrayList.toArray(new String[0]), new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda289
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.f$0.m5275xa3106808(particleSystem3DComponent, arrayList, gameObject, dialogInterface, i);
                }
            }).show();
        }
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$221$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5275xa3106808(ParticleSystem3DComponent particleSystem3DComponent, List list, GameObject gameObject, DialogInterface dialogInterface, int i) {
        particleSystem3DComponent.renderer.meshPath = (String) list.get(i);
        updatePS3DImmediate(gameObject);
        m5171x5f6795b(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$223$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5277x15ff8346(ParticleSystem3DComponent particleSystem3DComponent, GameObject gameObject, boolean z) {
        particleSystem3DComponent.renderer.alignToVelocity = z;
        updatePS3DImmediate(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$224$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5278xcf7710e5(ParticleSystem3DComponent particleSystem3DComponent, GameObject gameObject, boolean z) {
        particleSystem3DComponent.renderer.isAdditive = z;
        updatePS3DImmediate(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$226$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5280x42662c23(final ParticleSystem3DComponent particleSystem3DComponent, final GameObject gameObject, View view) {
        showTexturePicker(new StringConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda185
            @Override // org.catrobat.catroid.editor.InspectorManager.StringConsumer
            public final void accept(String str) {
                this.f$0.m5279x88ee9e84(particleSystem3DComponent, gameObject, str);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$225$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5279x88ee9e84(ParticleSystem3DComponent particleSystem3DComponent, GameObject gameObject, String str) {
        particleSystem3DComponent.renderer.texturePath = str;
        updatePS3DImmediate(gameObject);
        m5171x5f6795b(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleSystem3DView$227$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5281xfbddb9c2(ParticleSystem3DComponent particleSystem3DComponent, GameObject gameObject, View view) {
        particleSystem3DComponent.renderer.texturePath = null;
        updatePS3DImmediate(gameObject);
        m5171x5f6795b(gameObject);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: updatePS3D, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public void m5157x10c9d7e4(final GameObject go) {
        Runnable runnable = this.pendingPS3DUpdate;
        if (runnable != null) {
            this.ps3dUpdateHandler.removeCallbacks(runnable);
        }
        Runnable runnable2 = new Runnable() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda121
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5345xc898fa2a(go);
            }
        };
        this.pendingPS3DUpdate = runnable2;
        this.ps3dUpdateHandler.postDelayed(runnable2, 300L);
    }

    /* JADX INFO: renamed from: lambda$updatePS3D$229$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5345xc898fa2a(final GameObject gameObject) {
        final ParticleSystem3DComponent particleSystem3DComponent;
        if (this.threeDManager == null || gameObject == null || (particleSystem3DComponent = (ParticleSystem3DComponent) gameObject.getComponent(ParticleSystem3DComponent.class)) == null) {
            return;
        }
        Gdx.f82app.postRunnable(new Runnable() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda247
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5344xf216c8b(gameObject, particleSystem3DComponent);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$updatePS3D$228$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5344xf216c8b(GameObject gameObject, ParticleSystem3DComponent particleSystem3DComponent) {
        this.threeDManager.updateParticleEffect3D(gameObject.f591id, particleSystem3DComponent, gameObject.transform.worldTransform);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updatePS3DImmediate(final GameObject go) {
        final ParticleSystem3DComponent particleSystem3DComponent;
        if (this.threeDManager == null || go == null || (particleSystem3DComponent = (ParticleSystem3DComponent) go.getComponent(ParticleSystem3DComponent.class)) == null) {
            return;
        }
        Gdx.f82app.postRunnable(new Runnable() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda106
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5346x629c0897(go, particleSystem3DComponent);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$updatePS3DImmediate$230$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5346x629c0897(GameObject gameObject, ParticleSystem3DComponent particleSystem3DComponent) {
        this.threeDManager.updateParticleEffect3D(gameObject.f591id, particleSystem3DComponent, gameObject.transform.worldTransform);
    }

    private void addSectionHeader(LinearLayout parent, String title) {
        TextView textView = new TextView(this.activity);
        textView.setText(title);
        textView.setTextColor(-1);
        textView.setTextSize(13.0f);
        textView.setTypeface(null, 1);
        textView.setPadding(0, 20, 0, 8);
        parent.addView(textView);
    }

    private void addCheckbox(LinearLayout parent, String label, boolean initialValue, final BoolConsumer onChange) {
        CheckBox checkBox = new CheckBox(this.activity);
        checkBox.setText(label);
        checkBox.setTextColor(-1);
        checkBox.setChecked(initialValue);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda290
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                onChange.accept(z);
            }
        });
        parent.addView(checkBox);
    }

    private void addStringInput(LinearLayout parent, String label, String initialValue, final StringConsumer onChange) {
        LinearLayout linearLayout = new LinearLayout(this.activity);
        linearLayout.setOrientation(0);
        linearLayout.setGravity(16);
        TextView textView = new TextView(this.activity);
        textView.setText(label + ": ");
        textView.setTextColor(-3355444);
        textView.setTextSize(12.0f);
        linearLayout.addView(textView);
        EditText editText = new EditText(this.activity);
        if (initialValue == null) {
            initialValue = "";
        }
        editText.setText(initialValue);
        editText.setTextColor(-1);
        editText.setTextSize(12.0f);
        editText.setLayoutParams(new LinearLayout.LayoutParams(0, -2, 1.0f));
        addSimpleTextListener(editText, new StringUpdater() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda184
            @Override // org.catrobat.catroid.editor.InspectorManager.StringUpdater
            public final void update(String str) {
                onChange.accept(str);
            }
        });
        linearLayout.addView(editText);
        parent.addView(linearLayout);
    }

    private void addSmallFloatInput(LinearLayout parent, String label, float value, final FloatConsumer onChange) {
        LinearLayout linearLayout = new LinearLayout(this.activity);
        linearLayout.setOrientation(1);
        linearLayout.setPadding(4, 0, 4, 0);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(0, -2, 1.0f));
        TextView textView = new TextView(this.activity);
        textView.setText(label);
        textView.setTextColor(-3355444);
        textView.setTextSize(10.0f);
        linearLayout.addView(textView);
        EditText editText = new EditText(this.activity);
        editText.setText(String.format(Locale.US, "%.2f", Float.valueOf(value)));
        editText.setTextColor(-1);
        editText.setTextSize(12.0f);
        editText.setInputType(12290);
        addSimpleTextListener(editText, new StringUpdater() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda209
            @Override // org.catrobat.catroid.editor.InspectorManager.StringUpdater
            public final void update(String str) {
                onChange.accept(Float.parseFloat(str));
            }
        });
        linearLayout.addView(editText);
        parent.addView(linearLayout);
    }

    private <T extends Enum<T>> void addSpinnerEnum(LinearLayout parent, String label, final T[] values, int selectedIdx, final EnumConsumer<T> onChange) {
        LinearLayout linearLayout = new LinearLayout(this.activity);
        linearLayout.setOrientation(0);
        linearLayout.setGravity(16);
        linearLayout.setPadding(0, 4, 0, 4);
        TextView textView = new TextView(this.activity);
        textView.setText(label + ": ");
        textView.setTextColor(-3355444);
        textView.setTextSize(12.0f);
        linearLayout.addView(textView);
        Spinner spinner = new Spinner(this.activity);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this.activity, R.layout.simple_spinner_item_white_text, values);
        arrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_white_text);
        spinner.setAdapter((SpinnerAdapter) arrayAdapter);
        spinner.setSelection(Math.min(selectedIdx, values.length - 1));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: org.catrobat.catroid.editor.InspectorManager.6
            private boolean isFirstCall = true;

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> p) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> p, View v, int pos, long id2) {
                if (this.isFirstCall) {
                    this.isFirstCall = false;
                } else {
                    onChange.accept(values[pos]);
                }
            }
        });
        spinner.setLayoutParams(new LinearLayout.LayoutParams(0, -2, 1.0f));
        linearLayout.addView(spinner);
        parent.addView(linearLayout);
    }

    private void addModuleSection(LinearLayout parent, final String title, boolean enabled, final BoolConsumer onToggle, final ModuleContentBuilder contentBuilder) {
        LinearLayout linearLayoutBuild;
        LinearLayout linearLayout = new LinearLayout(this.activity);
        linearLayout.setOrientation(1);
        linearLayout.setPadding(0, 8, 0, 8);
        LinearLayout linearLayout2 = new LinearLayout(this.activity);
        linearLayout2.setOrientation(0);
        linearLayout2.setGravity(16);
        linearLayout2.setPadding(0, 12, 0, 4);
        final CheckBox checkBox = new CheckBox(this.activity);
        checkBox.setChecked(enabled);
        checkBox.setTag("initializing");
        linearLayout2.addView(checkBox);
        final TextView textView = new TextView(this.activity);
        textView.setText(title + (enabled ? " ▼" : " ▶"));
        textView.setTextColor(enabled ? -1 : -7829368);
        textView.setTextSize(14.0f);
        textView.setTypeface(null, 1);
        textView.setPadding(8, 0, 0, 0);
        linearLayout2.addView(textView);
        linearLayout.addView(linearLayout2);
        final LinearLayout linearLayout3 = new LinearLayout(this.activity);
        linearLayout3.setOrientation(1);
        linearLayout3.setPadding(16, 0, 0, 0);
        linearLayout3.setVisibility(enabled ? 0 : 8);
        linearLayout.addView(linearLayout3);
        if (enabled && (linearLayoutBuild = contentBuilder.build()) != null) {
            linearLayout3.addView(linearLayoutBuild);
        }
        View view = new View(this.activity);
        view.setLayoutParams(new LinearLayout.LayoutParams(-1, 1));
        view.setBackgroundColor(822083583);
        linearLayout.addView(view);
        checkBox.setTag(null);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda211
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                InspectorManager.lambda$addModuleSection$234(onToggle, textView, title, linearLayout3, contentBuilder, compoundButton, z);
            }
        });
        textView.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda212
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                InspectorManager.lambda$addModuleSection$235(checkBox, linearLayout3, textView, title, view2);
            }
        });
        parent.addView(linearLayout);
    }

    static /* synthetic */ void lambda$addModuleSection$234(BoolConsumer boolConsumer, TextView textView, String str, LinearLayout linearLayout, ModuleContentBuilder moduleContentBuilder, CompoundButton compoundButton, boolean z) {
        LinearLayout linearLayoutBuild;
        if ("initializing".equals(compoundButton.getTag())) {
            return;
        }
        boolConsumer.accept(z);
        textView.setText(str + (z ? " ▼" : " ▶"));
        textView.setTextColor(z ? -1 : -7829368);
        linearLayout.removeAllViews();
        if (z && (linearLayoutBuild = moduleContentBuilder.build()) != null) {
            linearLayout.addView(linearLayoutBuild);
        }
        linearLayout.setVisibility(z ? 0 : 8);
    }

    static /* synthetic */ void lambda$addModuleSection$235(CheckBox checkBox, LinearLayout linearLayout, TextView textView, String str, View view) {
        if (checkBox.isChecked()) {
            boolean z = linearLayout.getVisibility() == 0;
            linearLayout.setVisibility(z ? 8 : 0);
            textView.setText(str + (z ? " ▶" : " ▼"));
        }
    }

    private void addMinMaxCurveEditor(LinearLayout parent, String label, final ParticleSystem3DComponent.MinMaxCurve curve, final GameObject go) {
        LinearLayout linearLayout = new LinearLayout(this.activity);
        linearLayout.setOrientation(1);
        linearLayout.setPadding(0, 4, 0, 4);
        LinearLayout linearLayout2 = new LinearLayout(this.activity);
        linearLayout2.setOrientation(0);
        linearLayout2.setGravity(16);
        TextView textView = new TextView(this.activity);
        textView.setText(label);
        textView.setTextColor(-3355444);
        textView.setTextSize(12.0f);
        textView.setLayoutParams(new LinearLayout.LayoutParams(0, -2, 1.0f));
        linearLayout2.addView(textView);
        Spinner spinner = new Spinner(this.activity);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this.activity, R.layout.simple_spinner_item_white_text, new String[]{"Constant", "Random 2 Const", "Curve", "Random 2 Curves"});
        arrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_white_text);
        spinner.setAdapter((SpinnerAdapter) arrayAdapter);
        spinner.setSelection(curve.mode.ordinal());
        spinner.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
        linearLayout2.addView(spinner);
        linearLayout.addView(linearLayout2);
        final LinearLayout linearLayout3 = new LinearLayout(this.activity);
        linearLayout3.setOrientation(1);
        linearLayout.addView(linearLayout3);
        final Runnable runnable = new Runnable() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda227
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5147x96057e8(linearLayout3, curve, go);
            }
        };
        runnable.run();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: org.catrobat.catroid.editor.InspectorManager.7
            private boolean isFirstCall = true;

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> p) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> p, View v, int pos, long id2) {
                if (this.isFirstCall) {
                    this.isFirstCall = false;
                    return;
                }
                ParticleSystem3DComponent.CurveMode curveMode = ParticleSystem3DComponent.CurveMode.values()[pos];
                if (curveMode != curve.mode) {
                    curve.mode = curveMode;
                    if ((curveMode == ParticleSystem3DComponent.CurveMode.CURVE || curveMode == ParticleSystem3DComponent.CurveMode.RANDOM_BETWEEN_TWO_CURVES) && curve.curve.isEmpty()) {
                        curve.curve.add(new ParticleCurvePoint<>(0.0f, Float.valueOf(curve.constantMax)));
                        curve.curve.add(new ParticleCurvePoint<>(1.0f, Float.valueOf(curve.constantMax)));
                    }
                    if (curveMode == ParticleSystem3DComponent.CurveMode.RANDOM_BETWEEN_TWO_CURVES && curve.curveMin.isEmpty()) {
                        curve.curveMin.add(new ParticleCurvePoint<>(0.0f, Float.valueOf(curve.constantMin)));
                        curve.curveMin.add(new ParticleCurvePoint<>(1.0f, Float.valueOf(curve.constantMin)));
                    }
                    InspectorManager.this.updatePS3DImmediate(go);
                    runnable.run();
                }
            }
        });
        parent.addView(linearLayout);
    }

    /* JADX INFO: renamed from: lambda$addMinMaxCurveEditor$241$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5147x96057e8(LinearLayout linearLayout, final ParticleSystem3DComponent.MinMaxCurve minMaxCurve, final GameObject gameObject) {
        linearLayout.removeAllViews();
        int i = AnonymousClass15.$SwitchMap$org$catrobat$catroid$raptor$ParticleSystem3DComponent$CurveMode[minMaxCurve.mode.ordinal()];
        if (i == 1) {
            addSimpleFloatInput(linearLayout, "Value", minMaxCurve.constantMax, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda220
                @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                public final void accept(float f) {
                    this.f$0.m5142x333bf5c2(minMaxCurve, gameObject, f);
                }
            });
            return;
        }
        if (i == 2) {
            LinearLayout linearLayout2 = new LinearLayout(this.activity);
            linearLayout2.setOrientation(0);
            addSmallFloatInput(linearLayout2, "Min", minMaxCurve.constantMin, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda223
                @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                public final void accept(float f) {
                    this.f$0.m5143xecb38361(minMaxCurve, gameObject, f);
                }
            });
            addSmallFloatInput(linearLayout2, "Max", minMaxCurve.constantMax, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda224
                @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                public final void accept(float f) {
                    this.f$0.m5144xa62b1100(minMaxCurve, gameObject, f);
                }
            });
            linearLayout.addView(linearLayout2);
            return;
        }
        if (i == 3) {
            addSimpleFloatInput(linearLayout, "Multiplier", minMaxCurve.multiplier, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda225
                @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
                public final void accept(float f) {
                    this.f$0.m5145x5fa29e9f(minMaxCurve, gameObject, f);
                }
            });
            buildCurveGraphForMinMaxCurve(linearLayout, minMaxCurve.curve, gameObject);
            return;
        }
        if (i != 4) {
            return;
        }
        addSimpleFloatInput(linearLayout, "Multiplier", minMaxCurve.multiplier, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda226
            @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
            public final void accept(float f) {
                this.f$0.m5146x4fe8ca49(minMaxCurve, gameObject, f);
            }
        });
        TextView textView = new TextView(this.activity);
        textView.setText("Max Curve:");
        textView.setTextColor(-1);
        textView.setTextSize(11.0f);
        linearLayout.addView(textView);
        buildCurveGraphForMinMaxCurve(linearLayout, minMaxCurve.curve, gameObject);
        TextView textView2 = new TextView(this.activity);
        textView2.setText("Min Curve:");
        textView2.setTextColor(-1);
        textView2.setTextSize(11.0f);
        linearLayout.addView(textView2);
        buildCurveGraphForMinMaxCurve(linearLayout, minMaxCurve.curveMin, gameObject);
    }

    /* JADX INFO: renamed from: lambda$addMinMaxCurveEditor$236$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5142x333bf5c2(ParticleSystem3DComponent.MinMaxCurve minMaxCurve, GameObject gameObject, float f) {
        minMaxCurve.constantMax = f;
        minMaxCurve.constantMin = f;
        m5157x10c9d7e4(gameObject);
    }

    /* JADX INFO: renamed from: lambda$addMinMaxCurveEditor$237$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5143xecb38361(ParticleSystem3DComponent.MinMaxCurve minMaxCurve, GameObject gameObject, float f) {
        minMaxCurve.constantMin = f;
        m5157x10c9d7e4(gameObject);
    }

    /* JADX INFO: renamed from: lambda$addMinMaxCurveEditor$238$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5144xa62b1100(ParticleSystem3DComponent.MinMaxCurve minMaxCurve, GameObject gameObject, float f) {
        minMaxCurve.constantMax = f;
        m5157x10c9d7e4(gameObject);
    }

    /* JADX INFO: renamed from: lambda$addMinMaxCurveEditor$239$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5145x5fa29e9f(ParticleSystem3DComponent.MinMaxCurve minMaxCurve, GameObject gameObject, float f) {
        minMaxCurve.multiplier = f;
        m5157x10c9d7e4(gameObject);
    }

    /* JADX INFO: renamed from: lambda$addMinMaxCurveEditor$240$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5146x4fe8ca49(ParticleSystem3DComponent.MinMaxCurve minMaxCurve, GameObject gameObject, float f) {
        minMaxCurve.multiplier = f;
        m5157x10c9d7e4(gameObject);
    }

    private void buildCurveGraphForMinMaxCurve(LinearLayout container, final List<ParticleCurvePoint<Float>> curvePoints, final GameObject go) {
        float fFloatValue = 0.0f;
        float fFloatValue2 = 1.0f;
        for (ParticleCurvePoint<Float> particleCurvePoint : curvePoints) {
            if (particleCurvePoint.value.floatValue() < fFloatValue) {
                fFloatValue = particleCurvePoint.value.floatValue();
            }
            if (particleCurvePoint.value.floatValue() > fFloatValue2) {
                fFloatValue2 = particleCurvePoint.value.floatValue();
            }
        }
        float f = fFloatValue2 - fFloatValue;
        float f2 = (f >= 0.1f ? f : 1.0f) * 0.1f;
        final float f3 = fFloatValue - f2;
        final float f4 = fFloatValue2 + f2;
        final CurveEditorView curveEditorView = new CurveEditorView(this.activity);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, (int) (this.activity.getResources().getDisplayMetrics().density * 120.0f));
        layoutParams.setMargins(0, 4, 0, 4);
        curveEditorView.setLayoutParams(layoutParams);
        curveEditorView.setData(curvePoints, f3, f4, new Runnable() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda126
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5153x717413c9(go);
            }
        });
        container.addView(curveEditorView);
        LinearLayout linearLayout = new LinearLayout(this.activity);
        linearLayout.setOrientation(0);
        linearLayout.setGravity(GravityCompat.END);
        Button button = new Button(this.activity, null, 0, android.R.style.Widget.Material.Button.Small);
        button.setText("+ Point");
        button.setTextSize(10.0f);
        button.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda127
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m5155x9ddabca6(curvePoints, curveEditorView, f3, f4, go, view);
            }
        });
        linearLayout.addView(button);
        Button button2 = new Button(this.activity, null, 0, android.R.style.Widget.Material.Button.Small);
        button2.setText("Del Sel");
        button2.setTextSize(10.0f);
        button2.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda128
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m5156x57524a45(curveEditorView, go, view);
            }
        });
        linearLayout.addView(button2);
        Button button3 = new Button(this.activity, null, 0, android.R.style.Widget.Material.Button.Small);
        button3.setText("Clear");
        button3.setTextSize(10.0f);
        button3.setTextColor(android.graphics.Color.parseColor("#FF8A80"));
        button3.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda129
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m5158xca416583(curvePoints, curveEditorView, go, view);
            }
        });
        linearLayout.addView(button3);
        container.addView(linearLayout);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX INFO: renamed from: lambda$buildCurveGraphForMinMaxCurve$245$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5155x9ddabca6(List list, CurveEditorView curveEditorView, float f, float f2, final GameObject gameObject, View view) {
        list.add(new ParticleCurvePoint(list.isEmpty() ? 0.5f : Math.min(1.0f, ((ParticleCurvePoint) list.get(list.size() - 1)).time + 0.2f), Float.valueOf(list.isEmpty() ? 1.0f : ((Float) ((ParticleCurvePoint) list.get(list.size() - 1)).value).floatValue())));
        Collections.sort(list, new Comparator() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda174
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return Float.compare(((ParticleCurvePoint) obj).time, ((ParticleCurvePoint) obj2).time);
            }
        });
        curveEditorView.setData(list, f, f2, new Runnable() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda175
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5154xe4632f07(gameObject);
            }
        });
        m5157x10c9d7e4(gameObject);
    }

    /* JADX INFO: renamed from: lambda$buildCurveGraphForMinMaxCurve$246$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5156x57524a45(CurveEditorView curveEditorView, GameObject gameObject, View view) {
        if (curveEditorView.deleteSelectedPoint()) {
            m5157x10c9d7e4(gameObject);
        }
    }

    /* JADX INFO: renamed from: lambda$buildCurveGraphForMinMaxCurve$248$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5158xca416583(List list, CurveEditorView curveEditorView, final GameObject gameObject, View view) {
        list.clear();
        curveEditorView.setData(list, 0.0f, 1.0f, new Runnable() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda96
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5157x10c9d7e4(gameObject);
            }
        });
        m5157x10c9d7e4(gameObject);
    }

    private void addMinMaxGradientEditor(LinearLayout parent, String label, final ParticleSystem3DComponent.MinMaxGradient gradient, final GameObject go) {
        LinearLayout linearLayout = new LinearLayout(this.activity);
        linearLayout.setOrientation(1);
        linearLayout.setPadding(0, 4, 0, 4);
        LinearLayout linearLayout2 = new LinearLayout(this.activity);
        linearLayout2.setOrientation(0);
        linearLayout2.setGravity(16);
        TextView textView = new TextView(this.activity);
        textView.setText(label);
        textView.setTextColor(-3355444);
        textView.setTextSize(12.0f);
        textView.setLayoutParams(new LinearLayout.LayoutParams(0, -2, 1.0f));
        linearLayout2.addView(textView);
        Spinner spinner = new Spinner(this.activity);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this.activity, R.layout.simple_spinner_item_white_text, new String[]{"Color", "Random 2 Colors", "Gradient", "Random 2 Gradients"});
        arrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_white_text);
        spinner.setAdapter((SpinnerAdapter) arrayAdapter);
        spinner.setSelection(gradient.mode.ordinal());
        linearLayout2.addView(spinner);
        linearLayout.addView(linearLayout2);
        final LinearLayout linearLayout3 = new LinearLayout(this.activity);
        linearLayout3.setOrientation(1);
        linearLayout.addView(linearLayout3);
        final Runnable runnable = new Runnable() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda186
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5151x6684643b(linearLayout3, gradient, go);
            }
        };
        runnable.run();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: org.catrobat.catroid.editor.InspectorManager.8
            boolean firstCall = true;

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> p) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> p, View v, int pos, long id2) {
                if (this.firstCall) {
                    this.firstCall = false;
                    return;
                }
                ParticleSystem3DComponent.GradientMode gradientMode = ParticleSystem3DComponent.GradientMode.values()[pos];
                if (gradientMode != gradient.mode) {
                    gradient.mode = gradientMode;
                    if ((gradientMode == ParticleSystem3DComponent.GradientMode.GRADIENT || gradientMode == ParticleSystem3DComponent.GradientMode.RANDOM_BETWEEN_TWO_GRADIENTS) && gradient.gradient.isEmpty()) {
                        gradient.gradient.add(new ParticleCurvePoint<>(0.0f, new Color(gradient.colorMax)));
                        gradient.gradient.add(new ParticleCurvePoint<>(1.0f, new Color(gradient.colorMax)));
                    }
                    if (gradientMode == ParticleSystem3DComponent.GradientMode.RANDOM_BETWEEN_TWO_GRADIENTS && gradient.gradientMin.isEmpty()) {
                        gradient.gradientMin.add(new ParticleCurvePoint<>(0.0f, new Color(gradient.colorMin)));
                        gradient.gradientMin.add(new ParticleCurvePoint<>(1.0f, new Color(gradient.colorMin)));
                    }
                    InspectorManager.this.m5157x10c9d7e4(go);
                    runnable.run();
                }
            }
        });
        parent.addView(linearLayout);
    }

    /* JADX INFO: renamed from: lambda$addMinMaxGradientEditor$252$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5151x6684643b(LinearLayout linearLayout, final ParticleSystem3DComponent.MinMaxGradient minMaxGradient, final GameObject gameObject) {
        linearLayout.removeAllViews();
        int i = AnonymousClass15.$SwitchMap$org$catrobat$catroid$raptor$ParticleSystem3DComponent$GradientMode[minMaxGradient.mode.ordinal()];
        if (i == 1) {
            addEffectColorParam(linearLayout, "Color", minMaxGradient.colorMax, new ColorConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda80
                @Override // org.catrobat.catroid.editor.InspectorManager.ColorConsumer
                public final void accept(Color color) {
                    this.f$0.m5148x34f1d53(minMaxGradient, gameObject, color);
                }
            });
            return;
        }
        if (i == 2) {
            addEffectColorParam(linearLayout, "Color Min", minMaxGradient.colorMin, new ColorConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda81
                @Override // org.catrobat.catroid.editor.InspectorManager.ColorConsumer
                public final void accept(Color color) {
                    this.f$0.m5149xf39548fd(minMaxGradient, gameObject, color);
                }
            });
            addEffectColorParam(linearLayout, "Color Max", minMaxGradient.colorMax, new ColorConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda82
                @Override // org.catrobat.catroid.editor.InspectorManager.ColorConsumer
                public final void accept(Color color) {
                    this.f$0.m5150xad0cd69c(minMaxGradient, gameObject, color);
                }
            });
            return;
        }
        if (i == 3) {
            buildGradientEditor(linearLayout, minMaxGradient.gradient, gameObject);
            return;
        }
        if (i != 4) {
            return;
        }
        TextView textView = new TextView(this.activity);
        textView.setText("Max Gradient:");
        textView.setTextColor(-1);
        textView.setTextSize(11.0f);
        linearLayout.addView(textView);
        buildGradientEditor(linearLayout, minMaxGradient.gradient, gameObject);
        TextView textView2 = new TextView(this.activity);
        textView2.setText("Min Gradient:");
        textView2.setTextColor(-1);
        textView2.setTextSize(11.0f);
        linearLayout.addView(textView2);
        buildGradientEditor(linearLayout, minMaxGradient.gradientMin, gameObject);
    }

    /* JADX INFO: renamed from: lambda$addMinMaxGradientEditor$249$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5148x34f1d53(ParticleSystem3DComponent.MinMaxGradient minMaxGradient, GameObject gameObject, Color color) {
        minMaxGradient.colorMax.set(color);
        minMaxGradient.colorMin.set(color);
        m5157x10c9d7e4(gameObject);
    }

    /* JADX INFO: renamed from: lambda$addMinMaxGradientEditor$250$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5149xf39548fd(ParticleSystem3DComponent.MinMaxGradient minMaxGradient, GameObject gameObject, Color color) {
        minMaxGradient.colorMin.set(color);
        m5157x10c9d7e4(gameObject);
    }

    /* JADX INFO: renamed from: lambda$addMinMaxGradientEditor$251$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5150xad0cd69c(ParticleSystem3DComponent.MinMaxGradient minMaxGradient, GameObject gameObject, Color color) {
        minMaxGradient.colorMax.set(color);
        m5157x10c9d7e4(gameObject);
    }

    private void buildGradientEditor(LinearLayout container, final List<ParticleCurvePoint<Color>> gradientPoints, final GameObject go) {
        LinearLayout linearLayout = new LinearLayout(this.activity);
        linearLayout.setOrientation(1);
        container.addView(linearLayout);
        final AnonymousClass9 anonymousClass9 = new AnonymousClass9(linearLayout, gradientPoints, go);
        anonymousClass9.run();
        Button button = new Button(this.activity, null, 0, android.R.style.Widget.Material.Button.Small);
        button.setText("+ Add Color Key");
        button.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda236
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m5159x5e025c3f(gradientPoints, go, anonymousClass9, view);
            }
        });
        container.addView(button);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/editor/InspectorManager$9.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.editor.InspectorManager$9, reason: invalid class name */
    class AnonymousClass9 implements Runnable {
        final /* synthetic */ GameObject val$go;
        final /* synthetic */ List val$gradientPoints;
        final /* synthetic */ LinearLayout val$listLayout;

        AnonymousClass9(final LinearLayout val$listLayout, final List val$gradientPoints, final GameObject val$go) {
            this.val$listLayout = val$listLayout;
            this.val$gradientPoints = val$gradientPoints;
            this.val$go = val$go;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.lang.Runnable
        public void run() {
            this.val$listLayout.removeAllViews();
            Collections.sort(this.val$gradientPoints, new Comparator() { // from class: org.catrobat.catroid.editor.InspectorManager$9$$ExternalSyntheticLambda1
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return Float.compare(((ParticleCurvePoint) obj).time, ((ParticleCurvePoint) obj2).time);
                }
            });
            for (int i = 0; i < this.val$gradientPoints.size(); i++) {
                final ParticleCurvePoint particleCurvePoint = (ParticleCurvePoint) this.val$gradientPoints.get(i);
                LinearLayout linearLayout = new LinearLayout(InspectorManager.this.activity);
                linearLayout.setOrientation(0);
                linearLayout.setGravity(16);
                linearLayout.setPadding(0, 2, 0, 2);
                EditText editText = new EditText(InspectorManager.this.activity);
                editText.setText(String.format(Locale.US, "%.2f", Float.valueOf(particleCurvePoint.time)));
                editText.setTextColor(-1);
                editText.setTextSize(11.0f);
                editText.setInputType(8194);
                editText.setLayoutParams(new LinearLayout.LayoutParams(0, -2, 0.3f));
                InspectorManager inspectorManager = InspectorManager.this;
                final GameObject gameObject = this.val$go;
                inspectorManager.addSimpleTextListener(editText, new StringUpdater() { // from class: org.catrobat.catroid.editor.InspectorManager$9$$ExternalSyntheticLambda2
                    @Override // org.catrobat.catroid.editor.InspectorManager.StringUpdater
                    public final void update(String str) {
                        this.f$0.m5351lambda$run$1$orgcatrobatcatroideditorInspectorManager$9(particleCurvePoint, gameObject, str);
                    }
                });
                linearLayout.addView(editText);
                final Button button = new Button(InspectorManager.this.activity);
                Color color = (Color) particleCurvePoint.value;
                final int iArgb = android.graphics.Color.argb((int) (color.a * 255.0f), (int) (color.r * 255.0f), (int) (color.g * 255.0f), (int) (color.b * 255.0f));
                button.setBackgroundColor(iArgb);
                button.setLayoutParams(new LinearLayout.LayoutParams((int) (InspectorManager.this.activity.getResources().getDisplayMetrics().density * 48.0f), (int) (InspectorManager.this.activity.getResources().getDisplayMetrics().density * 32.0f)));
                final GameObject gameObject2 = this.val$go;
                button.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.editor.InspectorManager$9$$ExternalSyntheticLambda3
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.m5353lambda$run$3$orgcatrobatcatroideditorInspectorManager$9(iArgb, particleCurvePoint, button, gameObject2, view);
                    }
                });
                linearLayout.addView(button);
                TextView textView = new TextView(InspectorManager.this.activity);
                textView.setText(String.format(Locale.US, " a:%.0f%%", Float.valueOf(color.a * 100.0f)));
                textView.setTextColor(-7829368);
                textView.setTextSize(10.0f);
                linearLayout.addView(textView);
                ImageButton imageButton = new ImageButton(InspectorManager.this.activity);
                imageButton.setImageResource(android.R.drawable.ic_delete);
                imageButton.setBackgroundColor(0);
                final List list = this.val$gradientPoints;
                final GameObject gameObject3 = this.val$go;
                imageButton.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.editor.InspectorManager$9$$ExternalSyntheticLambda4
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.m5354lambda$run$4$orgcatrobatcatroideditorInspectorManager$9(list, particleCurvePoint, gameObject3, this, view);
                    }
                });
                linearLayout.addView(imageButton);
                this.val$listLayout.addView(linearLayout);
            }
        }

        /* JADX INFO: renamed from: lambda$run$1$org-catrobat-catroid-editor-InspectorManager$9, reason: not valid java name */
        /* synthetic */ void m5351lambda$run$1$orgcatrobatcatroideditorInspectorManager$9(ParticleCurvePoint particleCurvePoint, GameObject gameObject, String str) {
            try {
                particleCurvePoint.time = Math.max(0.0f, Math.min(1.0f, Float.parseFloat(str)));
                InspectorManager.this.m5157x10c9d7e4(gameObject);
            } catch (Exception unused) {
            }
        }

        /* JADX INFO: renamed from: lambda$run$3$org-catrobat-catroid-editor-InspectorManager$9, reason: not valid java name */
        /* synthetic */ void m5353lambda$run$3$orgcatrobatcatroideditorInspectorManager$9(int i, final ParticleCurvePoint particleCurvePoint, final Button button, final GameObject gameObject, View view) {
            ColorPickerDialogBuilder.with(InspectorManager.this.activity).setTitle("Pick Color").initialColor(i).wheelType(ColorPickerView.WHEEL_TYPE.FLOWER).density(12).showAlphaSlider(true).setPositiveButton("OK", new ColorPickerClickListener() { // from class: org.catrobat.catroid.editor.InspectorManager$9$$ExternalSyntheticLambda0
                @Override // com.flask.colorpicker.builder.ColorPickerClickListener
                public final void onClick(DialogInterface dialogInterface, int i2, Integer[] numArr) {
                    this.f$0.m5352lambda$run$2$orgcatrobatcatroideditorInspectorManager$9(particleCurvePoint, button, gameObject, dialogInterface, i2, numArr);
                }
            }).setNegativeButton("Cancel", (DialogInterface.OnClickListener) null).build().show();
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX INFO: renamed from: lambda$run$2$org-catrobat-catroid-editor-InspectorManager$9, reason: not valid java name */
        /* synthetic */ void m5352lambda$run$2$orgcatrobatcatroideditorInspectorManager$9(ParticleCurvePoint particleCurvePoint, Button button, GameObject gameObject, DialogInterface dialogInterface, int i, Integer[] numArr) {
            ((Color) particleCurvePoint.value).set(android.graphics.Color.red(i) / 255.0f, android.graphics.Color.green(i) / 255.0f, android.graphics.Color.blue(i) / 255.0f, android.graphics.Color.alpha(i) / 255.0f);
            button.setBackgroundColor(i);
            InspectorManager.this.m5157x10c9d7e4(gameObject);
        }

        /* JADX INFO: renamed from: lambda$run$4$org-catrobat-catroid-editor-InspectorManager$9, reason: not valid java name */
        /* synthetic */ void m5354lambda$run$4$orgcatrobatcatroideditorInspectorManager$9(List list, ParticleCurvePoint particleCurvePoint, GameObject gameObject, Runnable runnable, View view) {
            list.remove(particleCurvePoint);
            InspectorManager.this.m5157x10c9d7e4(gameObject);
            runnable.run();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX INFO: renamed from: lambda$buildGradientEditor$253$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5159x5e025c3f(List list, GameObject gameObject, Runnable runnable, View view) {
        Color color;
        float fMin = list.isEmpty() ? 0.0f : Math.min(1.0f, ((ParticleCurvePoint) list.get(list.size() - 1)).time + 0.25f);
        if (list.isEmpty()) {
            color = new Color(1.0f, 1.0f, 1.0f, 1.0f);
        } else {
            color = new Color((Color) ((ParticleCurvePoint) list.get(list.size() - 1)).value);
        }
        list.add(new ParticleCurvePoint(fMin, color));
        m5157x10c9d7e4(gameObject);
        runnable.run();
    }

    private void createParticleView(final GameObject go) {
        addComponentHeader(R.string.component_particle_legacy, true, false, new Runnable() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda159
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5282x7de8cf47(go);
            }
        });
        LinearLayout linearLayout = new LinearLayout(this.activity);
        linearLayout.setOrientation(1);
        this.container.addView(linearLayout);
        final ParticleComponent particleComponent = (ParticleComponent) go.getComponent(ParticleComponent.class);
        particleComponent.migrateOldDataIfNeeded();
        View viewInflate = this.inflater.inflate(R.layout.inspector_particle, (ViewGroup) linearLayout, false);
        setWhiteTextToAllChildren((ViewGroup) viewInflate);
        linearLayout.addView(viewInflate);
        CheckBox checkBox = (CheckBox) viewInflate.findViewById(R.id.p_looping);
        checkBox.setText(R.string.particle_looping);
        checkBox.setChecked(particleComponent.looping);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda162
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                this.f$0.m5283x37605ce6(particleComponent, go, compoundButton, z);
            }
        });
        setupFloatParam(viewInflate, R.id.p_duration, this.activity.getString(R.string.particle_duration), particleComponent.duration, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda163
            @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
            public final void accept(float f) {
                this.f$0.m5284xf0d7ea85(particleComponent, go, f);
            }
        });
        setupFloatParam(viewInflate, R.id.p_start_lifetime, this.activity.getString(R.string.particle_start_lifetime), particleComponent.startLifetime, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda164
            @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
            public final void accept(float f) {
                this.f$0.m5285xaa4f7824(particleComponent, go, f);
            }
        });
        setupFloatParam(viewInflate, R.id.p_max_particles, this.activity.getString(R.string.particle_max_particles), particleComponent.maxParticles, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda165
            @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
            public final void accept(float f) {
                this.f$0.m5286x63c705c3(particleComponent, go, f);
            }
        });
        setupFloatParam(viewInflate, R.id.p_emission_rate, this.activity.getString(R.string.particle_emission_rate), particleComponent.emissionRate, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda167
            @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
            public final void accept(float f) {
                this.f$0.m5287x540d316d(particleComponent, go, f);
            }
        });
        hideOldFields(viewInflate);
        setupSpawnShapeUI(linearLayout, particleComponent, go);
        setupFloatParam(linearLayout, R.id.p_start_size, this.activity.getString(R.string.particle_base_size_mult), particleComponent.baseSize, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda168
            @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
            public final void accept(float f) {
                this.f$0.m5288xd84bf0c(particleComponent, go, f);
            }
        });
        addSimpleFloatInput(linearLayout, this.activity.getString(R.string.particle_base_size), particleComponent.baseSize, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda169
            @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
            public final void accept(float f) {
                this.f$0.m5289xc6fc4cab(particleComponent, go, f);
            }
        });
        setupFloatGraphEditor(linearLayout, this.activity.getString(R.string.particle_size_over_lifetime), particleComponent.sizeGraph, go, 0.0f, 3.0f);
        setupFloatGraphEditor(linearLayout, "Speed (Along Shape)", particleComponent.speedGraph, go, 0.0f, 10.0f);
        setupFloatParam(viewInflate, R.id.p_cone_angle, this.activity.getString(R.string.particle_spread_angle), particleComponent.coneAngle, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda170
            @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
            public final void accept(float f) {
                this.f$0.m5290x8073da4a(particleComponent, go, f);
            }
        });
        setupFloatGraphEditor(linearLayout, this.activity.getString(R.string.particle_gravity_y), particleComponent.gravityGraph, go, -10.0f, 10.0f);
        setupFloatGraphEditor(linearLayout, this.activity.getString(R.string.particle_vortex), particleComponent.vortexGraph, go, -10.0f, 10.0f);
        setupFloatGraphEditor(linearLayout, this.activity.getString(R.string.particle_turbulence), particleComponent.turbulenceGraph, go, 0.0f, 10.0f);
        setupFloatGraphEditor(linearLayout, this.activity.getString(R.string.particle_rotation_deg), particleComponent.rotationGraph, go, -180.0f, 180.0f);
        setupColorGraphEditor(linearLayout, this.activity.getString(R.string.particle_color_over_lifetime), particleComponent.colorGraph, go);
        TextView textView = (TextView) viewInflate.findViewById(R.id.text_texture_path);
        Button button = (Button) viewInflate.findViewById(R.id.btn_select_texture);
        ImageButton imageButton = (ImageButton) viewInflate.findViewById(R.id.btn_clear_texture);
        CheckBox checkBox2 = (CheckBox) viewInflate.findViewById(R.id.p_is_additive);
        textView.setText(particleComponent.texturePath != null ? particleComponent.texturePath : "Default");
        button.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda171
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m5292xf362f588(particleComponent, go, view);
            }
        });
        imageButton.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda160
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m5293xacda8327(particleComponent, go, view);
            }
        });
        checkBox2.setText(R.string.particle_additive_blend);
        checkBox2.setChecked(particleComponent.isAdditive);
        checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda161
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                this.f$0.m5294x665210c6(particleComponent, go, compoundButton, z);
            }
        });
    }

    static /* synthetic */ boolean lambda$createParticleView$254(Component component) {
        return component instanceof ParticleComponent;
    }

    /* JADX INFO: renamed from: lambda$createParticleView$255$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5282x7de8cf47(GameObject gameObject) {
        gameObject.components.removeIf(new Predicate() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda219
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return InspectorManager.lambda$createParticleView$254((Component) obj);
            }
        });
        ThreeDManager threeDManager = this.threeDManager;
        if (threeDManager != null) {
            threeDManager.removeParticleEffect(gameObject.f591id);
        }
        ThreeDManager threeDManager2 = this.threeDManager;
        if (threeDManager2 != null) {
            threeDManager2.removeEditorProxy(gameObject.f591id);
        }
        m5171x5f6795b(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleView$256$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5283x37605ce6(ParticleComponent particleComponent, GameObject gameObject, CompoundButton compoundButton, boolean z) {
        particleComponent.looping = z;
        m5330x886d5cd4(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleView$257$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5284xf0d7ea85(ParticleComponent particleComponent, GameObject gameObject, float f) {
        particleComponent.duration = f;
        m5330x886d5cd4(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleView$258$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5285xaa4f7824(ParticleComponent particleComponent, GameObject gameObject, float f) {
        particleComponent.startLifetime = f;
        m5330x886d5cd4(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleView$259$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5286x63c705c3(ParticleComponent particleComponent, GameObject gameObject, float f) {
        particleComponent.maxParticles = (int) f;
        m5330x886d5cd4(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleView$260$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5287x540d316d(ParticleComponent particleComponent, GameObject gameObject, float f) {
        particleComponent.emissionRate = f;
        m5330x886d5cd4(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleView$261$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5288xd84bf0c(ParticleComponent particleComponent, GameObject gameObject, float f) {
        particleComponent.baseSize = f;
        m5330x886d5cd4(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleView$262$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5289xc6fc4cab(ParticleComponent particleComponent, GameObject gameObject, float f) {
        particleComponent.baseSize = f;
        m5330x886d5cd4(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleView$263$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5290x8073da4a(ParticleComponent particleComponent, GameObject gameObject, float f) {
        particleComponent.coneAngle = f;
        m5330x886d5cd4(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleView$264$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5291x39eb67e9(ParticleComponent particleComponent, GameObject gameObject, String str) {
        particleComponent.texturePath = str;
        m5330x886d5cd4(gameObject);
        m5171x5f6795b(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleView$265$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5292xf362f588(final ParticleComponent particleComponent, final GameObject gameObject, View view) {
        showTexturePicker(new StringConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda323
            @Override // org.catrobat.catroid.editor.InspectorManager.StringConsumer
            public final void accept(String str) {
                this.f$0.m5291x39eb67e9(particleComponent, gameObject, str);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$createParticleView$266$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5293xacda8327(ParticleComponent particleComponent, GameObject gameObject, View view) {
        particleComponent.texturePath = null;
        m5330x886d5cd4(gameObject);
        m5171x5f6795b(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createParticleView$267$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5294x665210c6(ParticleComponent particleComponent, GameObject gameObject, CompoundButton compoundButton, boolean z) {
        particleComponent.isAdditive = z;
        m5330x886d5cd4(gameObject);
    }

    private void hideOldFields(View view) {
        int[] iArr = {R.id.p_start_speed, R.id.p_start_size, R.id.p_gravity, R.id.p_end_size, R.id.p_start_rotation, R.id.p_rotation_over_lifetime, R.id.p_cone_radius};
        for (int i = 0; i < 7; i++) {
            View viewFindViewById = view.findViewById(iArr[i]);
            if (viewFindViewById != null) {
                viewFindViewById.setVisibility(8);
            }
        }
        View viewFindViewById2 = view.findViewById(R.id.p_start_color);
        if (viewFindViewById2 == null || !(viewFindViewById2.getParent() instanceof View)) {
            return;
        }
        ((View) viewFindViewById2.getParent()).setVisibility(8);
    }

    private void addSimpleFloatInput(LinearLayout parent, String label, float val, final FloatConsumer onChange) {
        View viewInflate = this.inflater.inflate(R.layout.inspector_param_float, (ViewGroup) parent, false);
        setWhiteTextToAllChildren((ViewGroup) viewInflate);
        ((TextView) viewInflate.findViewById(R.id.text_param_name)).setText(label);
        EditText editText = (EditText) viewInflate.findViewById(R.id.edit_param_value);
        editText.setText(String.format(Locale.US, "%.2f", Float.valueOf(val)));
        addSimpleTextListener(editText, new StringUpdater() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda4
            @Override // org.catrobat.catroid.editor.InspectorManager.StringUpdater
            public final void update(String str) {
                onChange.accept(Float.parseFloat(str));
            }
        });
        parent.addView(viewInflate);
    }

    private void setupSpawnShapeUI(LinearLayout container, final ParticleComponent p, final GameObject go) {
        TextView textView = new TextView(this.activity);
        textView.setText("Spawn Shape");
        textView.setTextColor(-1);
        textView.setTextSize(14.0f);
        textView.setPadding(0, 20, 0, 5);
        container.addView(textView);
        Spinner spinner = new Spinner(this.activity);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this.activity, R.layout.simple_spinner_item_white_text, ParticleComponent.SpawnShape.values());
        arrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_white_text);
        spinner.setAdapter((SpinnerAdapter) arrayAdapter);
        spinner.setSelection(p.spawnShape.ordinal());
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: org.catrobat.catroid.editor.InspectorManager.10
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> parent) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id2) {
                p.spawnShape = ParticleComponent.SpawnShape.values()[position];
                InspectorManager.this.m5330x886d5cd4(go);
            }
        });
        container.addView(spinner);
        this.inflater.inflate(R.layout.inspector_transform, (ViewGroup) null);
        addSimpleFloatInput(container, "Size X (Radius/Width)", p.spawnSize.x, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda101
            @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
            public final void accept(float f) {
                this.f$0.m5332x425c06ae(p, go, f);
            }
        });
        addSimpleFloatInput(container, "Size Y (Height)", p.spawnSize.y, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda102
            @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
            public final void accept(float f) {
                this.f$0.m5333x32a23258(p, go, f);
            }
        });
        addSimpleFloatInput(container, "Size Z (Depth)", p.spawnSize.z, new FloatConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda103
            @Override // org.catrobat.catroid.editor.InspectorManager.FloatConsumer
            public final void accept(float f) {
                this.f$0.m5334xec19bff7(p, go, f);
            }
        });
        CheckBox checkBox = new CheckBox(this.activity);
        checkBox.setText("Spawn on Surface Only");
        checkBox.setTextColor(-1);
        checkBox.setChecked(p.spawnOnSurface);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda104
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                this.f$0.m5335xa5914d96(p, go, compoundButton, z);
            }
        });
        container.addView(checkBox);
    }

    /* JADX INFO: renamed from: lambda$setupSpawnShapeUI$269$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5332x425c06ae(ParticleComponent particleComponent, GameObject gameObject, float f) {
        particleComponent.spawnSize.x = f;
        m5330x886d5cd4(gameObject);
    }

    /* JADX INFO: renamed from: lambda$setupSpawnShapeUI$270$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5333x32a23258(ParticleComponent particleComponent, GameObject gameObject, float f) {
        particleComponent.spawnSize.y = f;
        m5330x886d5cd4(gameObject);
    }

    /* JADX INFO: renamed from: lambda$setupSpawnShapeUI$271$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5334xec19bff7(ParticleComponent particleComponent, GameObject gameObject, float f) {
        particleComponent.spawnSize.z = f;
        m5330x886d5cd4(gameObject);
    }

    /* JADX INFO: renamed from: lambda$setupSpawnShapeUI$272$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5335xa5914d96(ParticleComponent particleComponent, GameObject gameObject, CompoundButton compoundButton, boolean z) {
        particleComponent.spawnOnSurface = z;
        m5330x886d5cd4(gameObject);
    }

    private void setupFloatGraphEditor(LinearLayout container, String title, final List<ParticleCurvePoint<Float>> graph, final GameObject go, float defaultMin, float defaultMax) {
        LinearLayout linearLayout = new LinearLayout(this.activity);
        linearLayout.setOrientation(0);
        linearLayout.setGravity(16);
        linearLayout.setPadding(0, 24, 0, 8);
        TextView textView = new TextView(this.activity);
        textView.setText(title);
        textView.setTextColor(-1);
        textView.setTextSize(14.0f);
        textView.setTypeface(null, 1);
        textView.setLayoutParams(new LinearLayout.LayoutParams(0, -2, 1.0f));
        linearLayout.addView(textView);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
        layoutParams.setMargins(16, 0, 4, 0);
        TextView textView2 = new TextView(this.activity);
        textView2.setText("Min:");
        textView2.setTextColor(-7829368);
        textView2.setTextSize(10.0f);
        textView2.setLayoutParams(layoutParams);
        linearLayout.addView(textView2);
        final EditText editText = new EditText(this.activity);
        editText.setText(String.format(Locale.US, "%.1f", Float.valueOf(defaultMin)));
        editText.setTextColor(-1);
        editText.setTextSize(12.0f);
        editText.setInputType(12290);
        editText.setMinWidth(100);
        linearLayout.addView(editText);
        TextView textView3 = new TextView(this.activity);
        textView3.setText("Max:");
        textView3.setTextColor(-7829368);
        textView3.setTextSize(10.0f);
        textView3.setLayoutParams(layoutParams);
        linearLayout.addView(textView3);
        final EditText editText2 = new EditText(this.activity);
        editText2.setText(String.format(Locale.US, "%.1f", Float.valueOf(defaultMax)));
        editText2.setTextColor(-1);
        editText2.setTextSize(12.0f);
        editText2.setInputType(12290);
        editText2.setMinWidth(100);
        linearLayout.addView(editText2);
        container.addView(linearLayout);
        final CurveEditorView curveEditorView = new CurveEditorView(this.activity);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-1, (int) (this.activity.getResources().getDisplayMetrics().density * 150.0f));
        layoutParams2.setMargins(0, 0, 0, 8);
        curveEditorView.setLayoutParams(layoutParams2);
        final Runnable runnable = new Runnable() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda107
            @Override // java.lang.Runnable
            public final void run() {
                InspectorManager.lambda$setupFloatGraphEditor$273(editText, editText2, curveEditorView);
            }
        };
        addSimpleTextListener(editText, new StringUpdater() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda108
            @Override // org.catrobat.catroid.editor.InspectorManager.StringUpdater
            public final void update(String str) {
                runnable.run();
            }
        });
        addSimpleTextListener(editText2, new StringUpdater() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda109
            @Override // org.catrobat.catroid.editor.InspectorManager.StringUpdater
            public final void update(String str) {
                runnable.run();
            }
        });
        curveEditorView.setData(graph, defaultMin, defaultMax, new Runnable() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda112
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5326x6bc0884d(go);
            }
        });
        container.addView(curveEditorView);
        LinearLayout linearLayout2 = new LinearLayout(this.activity);
        linearLayout2.setOrientation(0);
        linearLayout2.setGravity(GravityCompat.END);
        Button button = new Button(this.activity, null, 0, android.R.style.Widget.Material.Button.Small);
        button.setText("Clear");
        button.setTextColor(android.graphics.Color.parseColor("#FF8A80"));
        button.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda113
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m5328xdeafa38b(graph, editText, editText2, curveEditorView, go, view);
            }
        });
        Button button2 = new Button(this.activity, null, 0, android.R.style.Widget.Material.Button.Small);
        button2.setText("Del Selected");
        button2.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda114
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m5329x9827312a(curveEditorView, go, view);
            }
        });
        Button button3 = new Button(this.activity, null, 0, android.R.style.Widget.Material.Button.Small);
        button3.setText("Add Point");
        button3.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda115
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m5331x41e4ea73(editText, editText2, graph, curveEditorView, go, view);
            }
        });
        linearLayout2.addView(button);
        View view = new View(this.activity);
        view.setLayoutParams(new LinearLayout.LayoutParams(16, 1));
        linearLayout2.addView(view);
        linearLayout2.addView(button2);
        View view2 = new View(this.activity);
        view2.setLayoutParams(new LinearLayout.LayoutParams(16, 1));
        linearLayout2.addView(view2);
        linearLayout2.addView(button3);
        container.addView(linearLayout2);
    }

    static /* synthetic */ void lambda$setupFloatGraphEditor$273(EditText editText, EditText editText2, CurveEditorView curveEditorView) {
        try {
            float f = Float.parseFloat(editText.getText().toString());
            float f2 = Float.parseFloat(editText2.getText().toString());
            if (f >= f2) {
                f2 = 0.1f + f;
            }
            curveEditorView.setRange(f, f2);
        } catch (Exception unused) {
        }
    }

    /* JADX INFO: renamed from: lambda$setupFloatGraphEditor$278$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5328xdeafa38b(List list, EditText editText, EditText editText2, CurveEditorView curveEditorView, final GameObject gameObject, View view) {
        float f;
        list.clear();
        float f2 = 0.0f;
        try {
            f2 = Float.parseFloat(editText.getText().toString());
            f = Float.parseFloat(editText2.getText().toString());
        } catch (Exception unused) {
            f = 1.0f;
        }
        curveEditorView.setData(list, f2, f, new Runnable() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda318
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5327x253815ec(gameObject);
            }
        });
        m5330x886d5cd4(gameObject);
    }

    /* JADX INFO: renamed from: lambda$setupFloatGraphEditor$279$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5329x9827312a(CurveEditorView curveEditorView, GameObject gameObject, View view) {
        if (!curveEditorView.deleteSelectedPoint()) {
            Toast.makeText(this.activity, "Select a point on graph first", 0).show();
        } else {
            m5330x886d5cd4(gameObject);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX INFO: renamed from: lambda$setupFloatGraphEditor$281$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5331x41e4ea73(EditText editText, EditText editText2, List list, CurveEditorView curveEditorView, final GameObject gameObject, View view) {
        float f;
        float f2 = 0.0f;
        try {
            f2 = Float.parseFloat(editText.getText().toString());
            f = Float.parseFloat(editText2.getText().toString());
        } catch (Exception unused) {
            f = 1.0f;
        }
        float f3 = list.isEmpty() ? 0.5f : ((ParticleCurvePoint) list.get(list.size() - 1)).time + 0.2f;
        float f4 = f3 <= 1.0f ? f3 : 1.0f;
        float fFloatValue = (f2 + f) / 2.0f;
        if (!list.isEmpty()) {
            fFloatValue = ((Float) ((ParticleCurvePoint) list.get(list.size() - 1)).value).floatValue();
        }
        list.add(new ParticleCurvePoint(f4, Float.valueOf(fFloatValue)));
        curveEditorView.setData(list, f2, f, new Runnable() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5330x886d5cd4(gameObject);
            }
        });
        m5330x886d5cd4(gameObject);
    }

    private void setupColorGraphEditor(LinearLayout container, String title, final List<ParticleCurvePoint<Color>> graph, final GameObject go) {
        TextView textView = new TextView(this.activity);
        textView.setText(title);
        textView.setTextColor(-1);
        textView.setTextSize(14.0f);
        textView.setPadding(0, 20, 0, 5);
        container.addView(textView);
        LinearLayout linearLayout = new LinearLayout(this.activity);
        linearLayout.setOrientation(1);
        container.addView(linearLayout);
        Button button = new Button(this.activity, null, 0, android.R.style.Widget.Material.Button.Small);
        button.setText("+ Add Color Keyframe");
        container.addView(button);
        final AnonymousClass11 anonymousClass11 = new AnonymousClass11(linearLayout, go, graph);
        anonymousClass11.run();
        button.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda218
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m5323x59116899(graph, go, anonymousClass11, view);
            }
        });
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/editor/InspectorManager$11.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.editor.InspectorManager$11, reason: invalid class name */
    class AnonymousClass11 implements Runnable {
        final /* synthetic */ GameObject val$go;
        final /* synthetic */ List val$graph;
        final /* synthetic */ LinearLayout val$listLayout;

        AnonymousClass11(final LinearLayout val$listLayout, final GameObject val$go, final List val$graph) {
            this.val$listLayout = val$listLayout;
            this.val$go = val$go;
            this.val$graph = val$graph;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.lang.Runnable
        public void run() {
            this.val$listLayout.removeAllViews();
            ((ParticleComponent) this.val$go.getComponent(ParticleComponent.class)).sortGraphs();
            for (int i = 0; i < this.val$graph.size(); i++) {
                final ParticleCurvePoint particleCurvePoint = (ParticleCurvePoint) this.val$graph.get(i);
                View viewInflate = InspectorManager.this.inflater.inflate(R.layout.inspector_graph_row_color, (ViewGroup) this.val$listLayout, false);
                InspectorManager.this.setWhiteTextToAllChildren((ViewGroup) viewInflate);
                EditText editText = (EditText) viewInflate.findViewById(R.id.edit_time);
                final Button button = (Button) viewInflate.findViewById(R.id.btn_color);
                ImageButton imageButton = (ImageButton) viewInflate.findViewById(R.id.btn_delete);
                editText.setText(String.format(Locale.US, "%.2f", Float.valueOf(particleCurvePoint.time)));
                Color color = (Color) particleCurvePoint.value;
                final int iArgb = android.graphics.Color.argb((int) (color.a * 255.0f), (int) (color.r * 255.0f), (int) (color.g * 255.0f), (int) (color.b * 255.0f));
                button.setBackgroundColor(iArgb);
                InspectorManager inspectorManager = InspectorManager.this;
                final GameObject gameObject = this.val$go;
                inspectorManager.addSimpleTextListener(editText, new StringUpdater() { // from class: org.catrobat.catroid.editor.InspectorManager$11$$ExternalSyntheticLambda1
                    @Override // org.catrobat.catroid.editor.InspectorManager.StringUpdater
                    public final void update(String str) {
                        this.f$0.m5347lambda$run$0$orgcatrobatcatroideditorInspectorManager$11(particleCurvePoint, gameObject, str);
                    }
                });
                final GameObject gameObject2 = this.val$go;
                button.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.editor.InspectorManager$11$$ExternalSyntheticLambda2
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.m5349lambda$run$2$orgcatrobatcatroideditorInspectorManager$11(iArgb, particleCurvePoint, button, gameObject2, view);
                    }
                });
                final List list = this.val$graph;
                final GameObject gameObject3 = this.val$go;
                imageButton.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.editor.InspectorManager$11$$ExternalSyntheticLambda3
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f$0.m5350lambda$run$3$orgcatrobatcatroideditorInspectorManager$11(list, particleCurvePoint, gameObject3, view);
                    }
                });
                this.val$listLayout.addView(viewInflate);
            }
        }

        /* JADX INFO: renamed from: lambda$run$0$org-catrobat-catroid-editor-InspectorManager$11, reason: not valid java name */
        /* synthetic */ void m5347lambda$run$0$orgcatrobatcatroideditorInspectorManager$11(ParticleCurvePoint particleCurvePoint, GameObject gameObject, String str) {
            try {
                particleCurvePoint.time = Math.max(0.0f, Math.min(1.0f, Float.parseFloat(str)));
                InspectorManager.this.m5330x886d5cd4(gameObject);
            } catch (Exception unused) {
            }
        }

        /* JADX INFO: renamed from: lambda$run$2$org-catrobat-catroid-editor-InspectorManager$11, reason: not valid java name */
        /* synthetic */ void m5349lambda$run$2$orgcatrobatcatroideditorInspectorManager$11(int i, final ParticleCurvePoint particleCurvePoint, final Button button, final GameObject gameObject, View view) {
            ColorPickerDialogBuilder.with(InspectorManager.this.activity).setTitle("Pick Color").initialColor(i).setPositiveButton("OK", new ColorPickerClickListener() { // from class: org.catrobat.catroid.editor.InspectorManager$11$$ExternalSyntheticLambda0
                @Override // com.flask.colorpicker.builder.ColorPickerClickListener
                public final void onClick(DialogInterface dialogInterface, int i2, Integer[] numArr) {
                    this.f$0.m5348lambda$run$1$orgcatrobatcatroideditorInspectorManager$11(particleCurvePoint, button, gameObject, dialogInterface, i2, numArr);
                }
            }).build().show();
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX INFO: renamed from: lambda$run$1$org-catrobat-catroid-editor-InspectorManager$11, reason: not valid java name */
        /* synthetic */ void m5348lambda$run$1$orgcatrobatcatroideditorInspectorManager$11(ParticleCurvePoint particleCurvePoint, Button button, GameObject gameObject, DialogInterface dialogInterface, int i, Integer[] numArr) {
            ((Color) particleCurvePoint.value).set(android.graphics.Color.red(i) / 255.0f, android.graphics.Color.green(i) / 255.0f, android.graphics.Color.blue(i) / 255.0f, android.graphics.Color.alpha(i) / 255.0f);
            button.setBackgroundColor(i);
            InspectorManager.this.m5330x886d5cd4(gameObject);
        }

        /* JADX INFO: renamed from: lambda$run$3$org-catrobat-catroid-editor-InspectorManager$11, reason: not valid java name */
        /* synthetic */ void m5350lambda$run$3$orgcatrobatcatroideditorInspectorManager$11(List list, ParticleCurvePoint particleCurvePoint, GameObject gameObject, View view) {
            list.remove(particleCurvePoint);
            InspectorManager.this.m5330x886d5cd4(gameObject);
            run();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX INFO: renamed from: lambda$setupColorGraphEditor$282$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5323x59116899(List list, GameObject gameObject, Runnable runnable, View view) {
        float f;
        Color color;
        if (list.isEmpty()) {
            f = 0.0f;
        } else {
            f = ((ParticleCurvePoint) list.get(list.size() + (-1))).time >= 1.0f ? 1.0f : ((ParticleCurvePoint) list.get(list.size() - 1)).time + 0.2f;
        }
        if (f > 1.0f) {
            f = 1.0f;
        }
        if (list.isEmpty()) {
            color = new Color(1.0f, 1.0f, 1.0f, 1.0f);
        } else {
            color = new Color((Color) ((ParticleCurvePoint) list.get(list.size() - 1)).value);
        }
        list.add(new ParticleCurvePoint(f, color));
        m5330x886d5cd4(gameObject);
        runnable.run();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: updateParticles, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public void m5330x886d5cd4(GameObject go) {
        ThreeDManager threeDManager = this.threeDManager;
        if (threeDManager != null) {
            threeDManager.updateParticleEffect(go.f591id, (ParticleComponent) go.getComponent(ParticleComponent.class), go.transform.worldTransform);
        }
    }

    private void showAddEffectDialog(final PostProcessingComponent pp, final GameObject go) {
        new AlertDialog.Builder(this.activity).setTitle("Add Effect").setItems(new String[]{this.activity.getString(R.string.pp_effect_bloom), this.activity.getString(R.string.pp_effect_vignette), this.activity.getString(R.string.pp_effect_levels), this.activity.getString(R.string.pp_effect_grain), this.activity.getString(R.string.pp_effect_fxaa), this.activity.getString(R.string.pp_effect_chromatic), this.activity.getString(R.string.pp_effect_radial_blur), this.activity.getString(R.string.pp_effect_old_tv), this.activity.getString(R.string.pp_effect_crt), this.activity.getString(R.string.pp_effect_fisheye), this.activity.getString(R.string.pp_effect_water), this.activity.getString(R.string.pp_effect_motion_blur), this.activity.getString(R.string.pp_effect_lens_flare), this.activity.getString(R.string.pp_effect_gaussian), this.activity.getString(R.string.pp_effect_zoom), this.activity.getString(R.string.pp_effect_aces), this.activity.getString(R.string.pp_effect_eye_adaptation), this.activity.getString(R.string.pp_effect_ssr), this.activity.getString(R.string.pp_effect_ssao), this.activity.getString(R.string.pp_effect_height_fog), this.activity.getString(R.string.pp_effect_dof), this.activity.getString(R.string.pp_effect_god_rays), this.activity.getString(R.string.pp_effect_volumetric_fog), this.activity.getString(R.string.pp_effect_fsr)}, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda216
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.m5340xf7d3db50(pp, go, dialogInterface, i);
            }
        }).show();
    }

    /* JADX INFO: renamed from: lambda$showAddEffectDialog$283$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5340xf7d3db50(PostProcessingComponent postProcessingComponent, GameObject gameObject, DialogInterface dialogInterface, int i) {
        switch (i) {
            case 0:
                postProcessingComponent.effects.add(new PostProcessingData.Bloom());
                break;
            case 1:
                postProcessingComponent.effects.add(new PostProcessingData.Vignette());
                break;
            case 2:
                postProcessingComponent.effects.add(new PostProcessingData.Levels());
                break;
            case 3:
                postProcessingComponent.effects.add(new PostProcessingData.Grain());
                break;
            case 4:
                postProcessingComponent.effects.add(new PostProcessingData.Fxaa());
                break;
            case 5:
                postProcessingComponent.effects.add(new PostProcessingData.Chromatic());
                break;
            case 6:
                postProcessingComponent.effects.add(new PostProcessingData.RadialBlur());
                break;
            case 7:
                postProcessingComponent.effects.add(new PostProcessingData.OldTv());
                break;
            case 8:
                postProcessingComponent.effects.add(new PostProcessingData.Crt());
                break;
            case 9:
                postProcessingComponent.effects.add(new PostProcessingData.Fisheye());
                break;
            case 10:
                postProcessingComponent.effects.add(new PostProcessingData.Water());
                break;
            case 11:
                postProcessingComponent.effects.add(new PostProcessingData.MotionBlur());
                break;
            case 12:
                postProcessingComponent.effects.add(new PostProcessingData.LensFlare());
                break;
            case 13:
                postProcessingComponent.effects.add(new PostProcessingData.Gaussian());
                break;
            case 14:
                postProcessingComponent.effects.add(new PostProcessingData.Zoom());
                break;
            case 15:
                postProcessingComponent.effects.add(new PostProcessingData.ACES());
                break;
            case 16:
                postProcessingComponent.effects.add(new PostProcessingData.EyeAdaptation());
                break;
            case 17:
                postProcessingComponent.effects.add(new PostProcessingData.RayTracing());
                break;
            case 18:
                postProcessingComponent.effects.add(new PostProcessingData.SSAO());
                break;
            case 19:
                postProcessingComponent.effects.add(new PostProcessingData.HeightFog());
                break;
            case 20:
                postProcessingComponent.effects.add(new PostProcessingData.DepthOfField());
                break;
            case 21:
                postProcessingComponent.effects.add(new PostProcessingData.GodRays());
                break;
            case 22:
                postProcessingComponent.effects.add(new PostProcessingData.VolumetricFog());
                break;
            case 23:
                postProcessingComponent.effects.add(new PostProcessingData.Upscaler());
                break;
        }
        this.threeDManager.updatePostProcessing(postProcessingComponent);
        m5171x5f6795b(gameObject);
    }

    private void showModelPicker(final GameObject go) {
        File[] fileArrListFiles = ProjectManager.getInstance().getCurrentProject().getFilesDir().listFiles();
        if (fileArrListFiles == null) {
            Toast.makeText(this.activity, "Could not read project files.", 0).show();
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (File file : fileArrListFiles) {
            String lowerCase = file.getName().toLowerCase();
            if (lowerCase.endsWith(".obj") || lowerCase.endsWith(".gltf") || lowerCase.endsWith(".glb")) {
                arrayList.add(file);
            }
        }
        if (arrayList.isEmpty()) {
            Toast.makeText(this.activity, "No 3D models (.obj, .gltf, .glb) found in project files.", 1).show();
            return;
        }
        final String[] strArr = new String[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            strArr[i] = ((File) arrayList.get(i)).getName();
        }
        new AlertDialog.Builder(this.activity).setTitle("Select a 3D Model").setItems(strArr, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda130
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                this.f$0.m5341xe8c94ff2(strArr, go, dialogInterface, i2);
            }
        }).show();
    }

    /* JADX INFO: renamed from: lambda$showModelPicker$284$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5341xe8c94ff2(String[] strArr, GameObject gameObject, DialogInterface dialogInterface, int i) {
        this.sceneManager.setRenderComponent(gameObject, strArr[i]);
        m5171x5f6795b(gameObject);
    }

    private void createPhysicsView(final GameObject go) {
        addComponentHeader(R.string.component_physics, true, true, new Runnable() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda292
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5295xffcfb59a(go);
            }
        });
        View viewInflate = this.inflater.inflate(R.layout.inspector_physics, (ViewGroup) this.container, false);
        setWhiteTextToAllChildren((ViewGroup) viewInflate);
        final PhysicsComponent physicsComponent = (PhysicsComponent) go.getComponent(PhysicsComponent.class);
        Spinner spinner = (Spinner) viewInflate.findViewById(R.id.spinner_physics_state);
        final View viewFindViewById = viewInflate.findViewById(R.id.layout_physics_mass);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this.activity, R.layout.simple_spinner_item_white_text, this.activity.getResources().getStringArray(R.array.editor_3d_physics_states));
        arrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_white_text);
        spinner.setAdapter((SpinnerAdapter) arrayAdapter);
        spinner.setSelection(physicsComponent.state.ordinal());
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: org.catrobat.catroid.editor.InspectorManager.12
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> parent) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id2) {
                physicsComponent.state = ThreeDManager.PhysicsState.values()[position];
                viewFindViewById.setVisibility(physicsComponent.state == ThreeDManager.PhysicsState.DYNAMIC ? 0 : 8);
                InspectorManager.this.sceneManager.setPhysicsComponent(go, physicsComponent);
            }
        });
        LinearLayout linearLayout = (LinearLayout) viewInflate.findViewById(R.id.colliders_container);
        linearLayout.removeAllViews();
        for (ColliderShapeData colliderShapeData : physicsComponent.colliders) {
            View viewInflate2 = this.inflater.inflate(R.layout.inspector_collider_item, (ViewGroup) linearLayout, false);
            populateColliderItemView(go, viewInflate2, colliderShapeData);
            linearLayout.addView(viewInflate2);
        }
        viewInflate.findViewById(R.id.btn_add_box_collider).setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda293
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m5296xb9474339(physicsComponent, go, view);
            }
        });
        viewInflate.findViewById(R.id.btn_add_capsule_collider).setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda294
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m5297x72bed0d8(physicsComponent, go, view);
            }
        });
        EditText editText = (EditText) viewInflate.findViewById(R.id.edit_physics_mass);
        EditText editText2 = (EditText) viewInflate.findViewById(R.id.edit_physics_friction);
        EditText editText3 = (EditText) viewInflate.findViewById(R.id.edit_physics_restitution);
        viewFindViewById.setVisibility(physicsComponent.state != ThreeDManager.PhysicsState.DYNAMIC ? 8 : 0);
        editText.setText(String.valueOf(physicsComponent.mass));
        editText2.setText(String.valueOf(physicsComponent.friction));
        editText3.setText(String.valueOf(physicsComponent.restitution));
        addSimpleTextListener(editText, new StringUpdater() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda295
            @Override // org.catrobat.catroid.editor.InspectorManager.StringUpdater
            public final void update(String str) {
                this.f$0.m5298x2c365e77(physicsComponent, go, str);
            }
        });
        addSimpleTextListener(editText2, new StringUpdater() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda296
            @Override // org.catrobat.catroid.editor.InspectorManager.StringUpdater
            public final void update(String str) {
                this.f$0.m5299x1c7c8a21(physicsComponent, go, str);
            }
        });
        addSimpleTextListener(editText3, new StringUpdater() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda297
            @Override // org.catrobat.catroid.editor.InspectorManager.StringUpdater
            public final void update(String str) {
                this.f$0.m5300xd5f417c0(physicsComponent, go, str);
            }
        });
        this.container.addView(viewInflate);
    }

    static /* synthetic */ boolean lambda$createPhysicsView$285(Component component) {
        return component instanceof PhysicsComponent;
    }

    /* JADX INFO: renamed from: lambda$createPhysicsView$286$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5295xffcfb59a(GameObject gameObject) {
        gameObject.components.removeIf(new Predicate() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda144
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return InspectorManager.lambda$createPhysicsView$285((Component) obj);
            }
        });
        this.sceneManager.removePhysicsComponent(gameObject);
        m5171x5f6795b(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createPhysicsView$287$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5296xb9474339(PhysicsComponent physicsComponent, GameObject gameObject, View view) {
        ColliderShapeData colliderShapeData = new ColliderShapeData();
        colliderShapeData.type = ColliderShapeData.ShapeType.BOX;
        colliderShapeData.size.set(1.0f, 1.0f, 1.0f);
        physicsComponent.colliders.add(colliderShapeData);
        this.sceneManager.setPhysicsComponent(gameObject, physicsComponent);
        m5171x5f6795b(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createPhysicsView$288$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5297x72bed0d8(PhysicsComponent physicsComponent, GameObject gameObject, View view) {
        ColliderShapeData colliderShapeData = new ColliderShapeData();
        colliderShapeData.type = ColliderShapeData.ShapeType.CAPSULE;
        colliderShapeData.radius = 0.5f;
        colliderShapeData.size.set(0.5f, 2.0f, 0.5f);
        physicsComponent.colliders.add(colliderShapeData);
        this.sceneManager.setPhysicsComponent(gameObject, physicsComponent);
        m5171x5f6795b(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createPhysicsView$289$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5298x2c365e77(PhysicsComponent physicsComponent, GameObject gameObject, String str) {
        try {
            physicsComponent.mass = Float.parseFloat(str);
            this.sceneManager.setPhysicsComponent(gameObject, physicsComponent);
        } catch (Exception unused) {
        }
    }

    /* JADX INFO: renamed from: lambda$createPhysicsView$290$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5299x1c7c8a21(PhysicsComponent physicsComponent, GameObject gameObject, String str) {
        try {
            physicsComponent.friction = Float.parseFloat(str);
            this.sceneManager.setFriction(gameObject.f591id, physicsComponent.friction);
        } catch (Exception unused) {
        }
    }

    /* JADX INFO: renamed from: lambda$createPhysicsView$291$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5300xd5f417c0(PhysicsComponent physicsComponent, GameObject gameObject, String str) {
        try {
            physicsComponent.restitution = Float.parseFloat(str);
            this.sceneManager.setRestitution(gameObject.f591id, physicsComponent.restitution);
        } catch (Exception unused) {
        }
    }

    public void setSelectedCollider(ColliderShapeData collider) {
        this.selectedCollider = collider;
    }

    private void populateColliderItemView(final GameObject go, View itemView, final ColliderShapeData collider) {
        EditText editText;
        EditText editText2;
        EditText editText3;
        final PhysicsComponent physicsComponent = (PhysicsComponent) go.getComponent(PhysicsComponent.class);
        TextView textView = (TextView) itemView.findViewById(R.id.collider_title);
        View viewFindViewById = itemView.findViewById(R.id.collider_content_layout);
        ImageButton imageButton = (ImageButton) itemView.findViewById(R.id.btn_duplicate_collider);
        ImageButton imageButton2 = (ImageButton) itemView.findViewById(R.id.btn_delete_collider);
        final EditText editText4 = (EditText) itemView.findViewById(R.id.edit_collider_cx);
        final EditText editText5 = (EditText) itemView.findViewById(R.id.edit_collider_cy);
        final EditText editText6 = (EditText) itemView.findViewById(R.id.edit_collider_cz);
        View viewFindViewById2 = itemView.findViewById(R.id.layout_collider_size);
        final EditText editText7 = (EditText) itemView.findViewById(R.id.edit_collider_sx);
        EditText editText8 = (EditText) itemView.findViewById(R.id.edit_collider_sy);
        EditText editText9 = (EditText) itemView.findViewById(R.id.edit_collider_sz);
        TextView textView2 = (TextView) itemView.findViewById(R.id.label_collider_sx);
        TextView textView3 = (TextView) itemView.findViewById(R.id.label_collider_sz);
        View viewFindViewById3 = itemView.findViewById(R.id.layout_collider_radius);
        EditText editText10 = (EditText) itemView.findViewById(R.id.edit_collider_radius);
        viewFindViewById.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda229
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m5313x9264f0ff(collider, go, view);
            }
        });
        if (collider == this.selectedCollider) {
            itemView.setBackgroundColor(1436129791);
        } else {
            itemView.setBackgroundColor(-12566464);
        }
        imageButton.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda230
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m5314x4bdc7e9e(collider, physicsComponent, go, view);
            }
        });
        imageButton2.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda231
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m5315x5540c3d(physicsComponent, collider, go, view);
            }
        });
        textView.setText(collider.type.toString() + " Collider");
        editText4.setText(String.format(Locale.US, "%.3f", Float.valueOf(collider.centerOffset.x)));
        editText5.setText(String.format(Locale.US, "%.3f", Float.valueOf(collider.centerOffset.y)));
        editText6.setText(String.format(Locale.US, "%.3f", Float.valueOf(collider.centerOffset.z)));
        if (collider.type == ColliderShapeData.ShapeType.BOX) {
            viewFindViewById2.setVisibility(0);
            viewFindViewById3.setVisibility(8);
            editText7.setVisibility(0);
            editText7.setText(String.format(Locale.US, "%.3f", Float.valueOf(collider.size.x)));
            editText2 = editText8;
            editText2.setVisibility(0);
            editText2.setText(String.format(Locale.US, "%.3f", Float.valueOf(collider.size.y)));
            editText = editText9;
            editText.setVisibility(0);
            editText.setText(String.format(Locale.US, "%.3f", Float.valueOf(collider.size.z)));
            textView2.setVisibility(0);
            textView3.setVisibility(0);
            editText3 = editText10;
        } else {
            editText = editText9;
            if (collider.type == ColliderShapeData.ShapeType.SPHERE) {
                editText2 = editText8;
                viewFindViewById2.setVisibility(8);
                viewFindViewById3.setVisibility(0);
                editText3 = editText10;
                editText3.setText(String.format(Locale.US, "%.3", Float.valueOf(collider.radius)));
            } else {
                editText2 = editText8;
                editText3 = editText10;
                viewFindViewById2.setVisibility(0);
                viewFindViewById3.setVisibility(0);
                editText3.setText(String.format(Locale.US, "%.3f", Float.valueOf(collider.radius)));
                editText2.setText(String.format(Locale.US, "%.3f", Float.valueOf(collider.size.y)));
                editText7.setVisibility(8);
                editText.setVisibility(8);
                textView2.setVisibility(8);
                textView3.setVisibility(8);
            }
        }
        final EditText editText11 = editText;
        final EditText editText12 = editText2;
        final EditText editText13 = editText3;
        DelayedTextWatcher delayedTextWatcher = new DelayedTextWatcher(new Runnable() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda232
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5316xbecb99dc(collider, editText4, editText5, editText6, editText7, editText12, editText11, editText13, go, physicsComponent);
            }
        });
        editText4.addTextChangedListener(delayedTextWatcher);
        editText5.addTextChangedListener(delayedTextWatcher);
        editText6.addTextChangedListener(delayedTextWatcher);
        editText7.addTextChangedListener(delayedTextWatcher);
        editText2.addTextChangedListener(delayedTextWatcher);
        editText11.addTextChangedListener(delayedTextWatcher);
        editText13.addTextChangedListener(delayedTextWatcher);
    }

    /* JADX INFO: renamed from: lambda$populateColliderItemView$292$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5313x9264f0ff(ColliderShapeData colliderShapeData, GameObject gameObject, View view) {
        this.selectedCollider = colliderShapeData;
        Gizmo gizmo = this.gizmo;
        if (gizmo != null) {
            gizmo.setSelected(gameObject, colliderShapeData);
        }
        m5171x5f6795b(gameObject);
    }

    /* JADX INFO: renamed from: lambda$populateColliderItemView$293$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5314x4bdc7e9e(ColliderShapeData colliderShapeData, PhysicsComponent physicsComponent, GameObject gameObject, View view) {
        ColliderShapeData colliderShapeData2 = new ColliderShapeData();
        colliderShapeData2.type = colliderShapeData.type;
        colliderShapeData2.centerOffset.set(colliderShapeData.centerOffset).add(0.1f, 0.0f, 0.0f);
        colliderShapeData2.size.set(colliderShapeData.size);
        colliderShapeData2.radius = colliderShapeData.radius;
        physicsComponent.colliders.add(colliderShapeData2);
        this.sceneManager.setPhysicsComponent(gameObject, physicsComponent);
        m5171x5f6795b(gameObject);
    }

    /* JADX INFO: renamed from: lambda$populateColliderItemView$294$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5315x5540c3d(PhysicsComponent physicsComponent, ColliderShapeData colliderShapeData, GameObject gameObject, View view) {
        physicsComponent.colliders.remove(colliderShapeData);
        if (this.selectedCollider == colliderShapeData) {
            this.selectedCollider = null;
            this.gizmo.setSelected(gameObject, null);
        }
        this.sceneManager.setPhysicsComponent(gameObject, physicsComponent);
        m5171x5f6795b(gameObject);
    }

    /* JADX INFO: renamed from: lambda$populateColliderItemView$295$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5316xbecb99dc(ColliderShapeData colliderShapeData, EditText editText, EditText editText2, EditText editText3, EditText editText4, EditText editText5, EditText editText6, EditText editText7, GameObject gameObject, PhysicsComponent physicsComponent) {
        try {
            colliderShapeData.centerOffset.set(Float.parseFloat(editText.getText().toString()), Float.parseFloat(editText2.getText().toString()), Float.parseFloat(editText3.getText().toString()));
            int i = AnonymousClass15.$SwitchMap$org$catrobat$catroid$raptor$ColliderShapeData$ShapeType[colliderShapeData.type.ordinal()];
            if (i == 1) {
                colliderShapeData.size.set(Float.parseFloat(editText4.getText().toString()), Float.parseFloat(editText5.getText().toString()), Float.parseFloat(editText6.getText().toString()));
            } else if (i == 2) {
                colliderShapeData.radius = Float.parseFloat(editText7.getText().toString());
            } else if (i == 3) {
                colliderShapeData.radius = Float.parseFloat(editText7.getText().toString());
                colliderShapeData.size.y = Float.parseFloat(editText5.getText().toString());
            }
            this.sceneManager.setPhysicsComponent(gameObject, physicsComponent);
        } catch (NumberFormatException unused) {
        }
    }

    private void createLightView(final GameObject go) {
        addComponentHeader(R.string.component_light, true, false, new Runnable() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda298
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5176x24e3b04b(go);
            }
        });
        View viewInflate = this.inflater.inflate(R.layout.inspector_light, (ViewGroup) this.container, false);
        if (viewInflate instanceof ViewGroup) {
            setWhiteTextToAllChildren((ViewGroup) viewInflate);
        }
        final LightComponent lightComponent = (LightComponent) go.getComponent(LightComponent.class);
        final Spinner spinner = (Spinner) viewInflate.findViewById(R.id.spinner_light_type);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this.activity, R.layout.simple_spinner_item_white_text, new String[]{this.activity.getString(R.string.light_type_directional), this.activity.getString(R.string.light_type_point), this.activity.getString(R.string.light_type_spot)});
        arrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_white_text);
        spinner.setAdapter((SpinnerAdapter) arrayAdapter);
        spinner.setSelection(lightComponent.type.ordinal());
        EditText editText = (EditText) viewInflate.findViewById(R.id.edit_light_intensity);
        EditText editText2 = (EditText) viewInflate.findViewById(R.id.edit_light_range);
        EditText editText3 = (EditText) viewInflate.findViewById(R.id.edit_light_angle);
        final View viewFindViewById = viewInflate.findViewById(R.id.layout_light_range);
        final View viewFindViewById2 = viewInflate.findViewById(R.id.layout_light_spot);
        EditText editText4 = (EditText) viewInflate.findViewById(R.id.edit_light_exponent);
        final Button button = (Button) viewInflate.findViewById(R.id.btn_light_color);
        editText4.setText(String.valueOf(lightComponent.exponent));
        spinner.setSelection(lightComponent.type.ordinal());
        editText.setText(String.valueOf(lightComponent.intensity));
        editText2.setText(String.valueOf(lightComponent.range));
        editText3.setText(String.valueOf(lightComponent.cutoffAngle));
        final Runnable runnable = new Runnable() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda299
            @Override // java.lang.Runnable
            public final void run() {
                InspectorManager.lambda$createLightView$298(spinner, viewFindViewById, viewFindViewById2);
            }
        };
        runnable.run();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: org.catrobat.catroid.editor.InspectorManager.13
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> parent) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id2) {
                lightComponent.type = LightComponent.LightType.values()[position];
                InspectorManager.this.sceneManager.setLightComponent(go, lightComponent);
                runnable.run();
            }
        });
        final int iLibGdxColorToAndroidColor = libGdxColorToAndroidColor(lightComponent.color);
        button.setBackgroundColor(iLibGdxColorToAndroidColor);
        button.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda300
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m5178xe495a827(iLibGdxColorToAndroidColor, button, lightComponent, go, view);
            }
        });
        addSimpleTextListener(editText4, new StringUpdater() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda301
            @Override // org.catrobat.catroid.editor.InspectorManager.StringUpdater
            public final void update(String str) {
                this.f$0.m5179x9e0d35c6(lightComponent, go, str);
            }
        });
        addSimpleTextListener(editText, new StringUpdater() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda303
            @Override // org.catrobat.catroid.editor.InspectorManager.StringUpdater
            public final void update(String str) {
                this.f$0.m5180x5784c365(lightComponent, go, str);
            }
        });
        addSimpleTextListener(editText2, new StringUpdater() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda304
            @Override // org.catrobat.catroid.editor.InspectorManager.StringUpdater
            public final void update(String str) {
                this.f$0.m5181x10fc5104(lightComponent, go, str);
            }
        });
        addSimpleTextListener(editText3, new StringUpdater() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda305
            @Override // org.catrobat.catroid.editor.InspectorManager.StringUpdater
            public final void update(String str) {
                this.f$0.m5182xca73dea3(lightComponent, go, str);
            }
        });
        this.container.addView(viewInflate);
    }

    static /* synthetic */ boolean lambda$createLightView$296(Component component) {
        return component instanceof LightComponent;
    }

    /* JADX INFO: renamed from: lambda$createLightView$297$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5176x24e3b04b(GameObject gameObject) {
        gameObject.components.removeIf(new Predicate() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda288
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return InspectorManager.lambda$createLightView$296((Component) obj);
            }
        });
        this.sceneManager.removeLightComponent(gameObject);
        m5171x5f6795b(gameObject);
    }

    static /* synthetic */ void lambda$createLightView$298(Spinner spinner, View view, View view2) {
        LightComponent.LightType lightType = LightComponent.LightType.values()[spinner.getSelectedItemPosition()];
        view.setVisibility((lightType == LightComponent.LightType.POINT || lightType == LightComponent.LightType.SPOT) ? 0 : 8);
        view2.setVisibility(lightType == LightComponent.LightType.SPOT ? 0 : 8);
    }

    /* JADX INFO: renamed from: lambda$createLightView$301$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5178xe495a827(int i, final Button button, final LightComponent lightComponent, final GameObject gameObject, View view) {
        ColorPickerDialogBuilder.with(this.activity).setTitle("Choose color").initialColor(i).wheelType(ColorPickerView.WHEEL_TYPE.FLOWER).density(12).setPositiveButton("OK", new ColorPickerClickListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda123
            @Override // com.flask.colorpicker.builder.ColorPickerClickListener
            public final void onClick(DialogInterface dialogInterface, int i2, Integer[] numArr) {
                this.f$0.m5177x97d2cb89(button, lightComponent, gameObject, dialogInterface, i2, numArr);
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda124
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                InspectorManager.lambda$createLightView$300(dialogInterface, i2);
            }
        }).build().show();
    }

    /* JADX INFO: renamed from: lambda$createLightView$299$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5177x97d2cb89(Button button, LightComponent lightComponent, GameObject gameObject, DialogInterface dialogInterface, int i, Integer[] numArr) {
        button.setBackgroundColor(i);
        lightComponent.color.set(android.graphics.Color.red(i) / 255.0f, android.graphics.Color.green(i) / 255.0f, android.graphics.Color.blue(i) / 255.0f, 1.0f);
        this.sceneManager.setLightComponent(gameObject, lightComponent);
    }

    /* JADX INFO: renamed from: lambda$createLightView$302$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5179x9e0d35c6(LightComponent lightComponent, GameObject gameObject, String str) {
        try {
            lightComponent.exponent = Float.parseFloat(str);
            this.sceneManager.setLightComponent(gameObject, lightComponent);
        } catch (Exception unused) {
        }
    }

    /* JADX INFO: renamed from: lambda$createLightView$303$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5180x5784c365(LightComponent lightComponent, GameObject gameObject, String str) {
        try {
            lightComponent.intensity = Float.parseFloat(str);
            this.sceneManager.setLightComponent(gameObject, lightComponent);
        } catch (Exception unused) {
        }
    }

    /* JADX INFO: renamed from: lambda$createLightView$304$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5181x10fc5104(LightComponent lightComponent, GameObject gameObject, String str) {
        try {
            lightComponent.range = Float.parseFloat(str);
            this.sceneManager.setLightComponent(gameObject, lightComponent);
        } catch (Exception unused) {
        }
    }

    /* JADX INFO: renamed from: lambda$createLightView$305$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5182xca73dea3(LightComponent lightComponent, GameObject gameObject, String str) {
        try {
            lightComponent.cutoffAngle = Float.parseFloat(str);
            this.sceneManager.setLightComponent(gameObject, lightComponent);
        } catch (Exception unused) {
        }
    }

    private void createScriptView(final GameObject go, final ScriptComponent scriptComp) {
        addComponentHeader(R.string.component_script, true, false, new Runnable() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda146
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5309xcfab141b(go, scriptComp);
            }
        });
        View viewInflate = this.inflater.inflate(R.layout.inspector_script, (ViewGroup) this.container, false);
        setWhiteTextToAllChildren((ViewGroup) viewInflate);
        TextView textView = (TextView) viewInflate.findViewById(R.id.text_script_path);
        Button button = (Button) viewInflate.findViewById(R.id.btn_select_script);
        if (scriptComp.scriptPath != null && !scriptComp.scriptPath.isEmpty()) {
            textView.setText(scriptComp.scriptPath);
        } else {
            textView.setText("None (Select a script)");
        }
        button.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda147
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m5310x8922a1ba(scriptComp, view);
            }
        });
        this.container.addView(viewInflate);
    }

    /* JADX INFO: renamed from: lambda$createScriptView$306$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5309xcfab141b(GameObject gameObject, ScriptComponent scriptComponent) {
        gameObject.components.remove(scriptComponent);
        m5171x5f6795b(gameObject);
    }

    /* JADX INFO: renamed from: lambda$createScriptView$307$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5310x8922a1ba(ScriptComponent scriptComponent, View view) {
        showScriptPicker(scriptComponent);
    }

    private void showScriptPicker(final ScriptComponent scriptComp) {
        File filesDir = ProjectManager.getInstance().getCurrentProject().getFilesDir();
        if (!filesDir.exists()) {
            filesDir.mkdirs();
        }
        File[] fileArrListFiles = filesDir.listFiles(new FilenameFilter() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda207
            @Override // java.io.FilenameFilter
            public final boolean accept(File file, String str) {
                return str.toLowerCase().endsWith(".kts");
            }
        });
        if (fileArrListFiles == null || fileArrListFiles.length == 0) {
            Toast.makeText(this.activity, "No script files (.kts) found in 'scripts' folder.", 1).show();
            return;
        }
        final String[] strArr = new String[fileArrListFiles.length];
        for (int i = 0; i < fileArrListFiles.length; i++) {
            strArr[i] = fileArrListFiles[i].getName();
        }
        new AlertDialog.Builder(this.activity).setTitle("Select a Script").setItems(strArr, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda208
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                this.f$0.m5343xaf69458e(strArr, scriptComp, dialogInterface, i2);
            }
        }).show();
    }

    /* JADX INFO: renamed from: lambda$showScriptPicker$309$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5343xaf69458e(String[] strArr, ScriptComponent scriptComponent, DialogInterface dialogInterface, int i) {
        scriptComponent.scriptPath = strArr[i];
        m5171x5f6795b(this.selectedObject);
    }

    private void showAddComponentDialog(final GameObject go) {
        new AlertDialog.Builder(this.activity).setTitle("Add Component").setItems(new String[]{this.activity.getString(R.string.component_render), this.activity.getString(R.string.component_physics), this.activity.getString(R.string.component_light), this.activity.getString(R.string.component_animation), this.activity.getString(R.string.component_camera), this.activity.getString(R.string.component_material), this.activity.getString(R.string.component_postprocessing), this.activity.getString(R.string.component_particle_legacy), this.activity.getString(R.string.component_particle_3d), this.activity.getString(R.string.component_keyframe), this.activity.getString(R.string.component_prefab)}, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda149
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.m5339xbd0c238d(go, dialogInterface, i);
            }
        }).show();
    }

    /* JADX INFO: renamed from: lambda$showAddComponentDialog$310$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5339xbd0c238d(GameObject gameObject, DialogInterface dialogInterface, int i) {
        switch (i) {
            case 0:
                if (!gameObject.hasComponent(RenderComponent.class)) {
                    this.sceneManager.setRenderComponent(gameObject, "myModel.glb");
                }
                break;
            case 1:
                if (!gameObject.hasComponent(PhysicsComponent.class)) {
                    this.sceneManager.setPhysicsComponent(gameObject, ThreeDManager.PhysicsState.STATIC, ThreeDManager.PhysicsShape.BOX, 1.0f);
                }
                break;
            case 2:
                if (!gameObject.hasComponent(LightComponent.class)) {
                    this.sceneManager.setLightComponent(gameObject, new LightComponent());
                }
                break;
            case 3:
                if (!gameObject.hasComponent(AnimationComponent.class)) {
                    this.sceneManager.setAnimationComponent(gameObject, new AnimationComponent());
                }
                break;
            case 4:
                if (!gameObject.hasComponent(CameraComponent.class)) {
                    this.sceneManager.setCameraComponent(gameObject, new CameraComponent());
                }
                break;
            case 5:
                if (!gameObject.hasComponent(MaterialComponent.class)) {
                    this.sceneManager.setMaterialComponent(gameObject, new MaterialComponent());
                }
                break;
            case 6:
                if (!gameObject.hasComponent(PostProcessingComponent.class)) {
                    PostProcessingComponent postProcessingComponent = new PostProcessingComponent();
                    gameObject.addComponent(postProcessingComponent);
                    this.sceneManager.engine.updatePostProcessing(postProcessingComponent);
                }
                break;
            case 7:
                if (!gameObject.hasComponent(ParticleComponent.class)) {
                    gameObject.addComponent(new ParticleComponent());
                    this.sceneManager.engine.createParticleProxy(gameObject.f591id);
                    m5330x886d5cd4(gameObject);
                }
                break;
            case 8:
                if (!gameObject.hasComponent(ParticleSystem3DComponent.class)) {
                    ParticleSystem3DComponent particleSystem3DComponent = new ParticleSystem3DComponent();
                    gameObject.addComponent(particleSystem3DComponent);
                    this.sceneManager.engine.createParticleProxy(gameObject.f591id);
                    this.sceneManager.engine.updateParticleEffect3D(gameObject.f591id, particleSystem3DComponent, gameObject.transform.worldTransform);
                }
                break;
            case 9:
                if (!gameObject.hasComponent(KeyframeComponent.class)) {
                    gameObject.addComponent(new KeyframeComponent());
                }
                break;
            case 10:
                if (!gameObject.hasComponent(PrefabComponent.class)) {
                    gameObject.addComponent(new PrefabComponent());
                }
                break;
        }
        m5171x5f6795b(gameObject);
    }

    private void addComponentHeader(int titleResId, boolean canBeDeleted, boolean hasVisibilityToggle, final Runnable onDelete) {
        View viewInflate = this.inflater.inflate(R.layout.inspector_component_header, (ViewGroup) this.container, false);
        TextView textView = (TextView) viewInflate.findViewById(R.id.header_title);
        ImageButton imageButton = (ImageButton) viewInflate.findViewById(R.id.btn_delete_component);
        final ImageButton imageButton2 = (ImageButton) viewInflate.findViewById(R.id.btn_toggle_visibility);
        textView.setText(this.activity.getString(titleResId));
        if (hasVisibilityToggle) {
            imageButton2.setVisibility(0);
            imageButton2.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.m5079xcba3e444(imageButton2, view);
                }
            });
        }
        if (canBeDeleted) {
            imageButton.setVisibility(0);
            imageButton.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda111
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    onDelete.run();
                }
            });
        } else {
            imageButton.setVisibility(8);
        }
        this.container.addView(viewInflate);
    }

    /* JADX INFO: renamed from: lambda$addComponentHeader$311$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5079xcba3e444(ImageButton imageButton, View view) {
        EditorFragment editorFragment = (EditorFragment) this.activity.getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (editorFragment == null || editorFragment.getListener() == null) {
            return;
        }
        this.isShowingColliders = !this.isShowingColliders;
        editorFragment.getListener().setColliderVisibility(this.isShowingColliders);
        imageButton.setAlpha(this.isShowingColliders ? 1.0f : 0.5f);
    }

    private void addVector3Listener(final GameObject go, View parent, int xId, int yId, int zId, final Vector3Updater updater) {
        final EditText editText = (EditText) parent.findViewById(xId);
        final EditText editText2 = (EditText) parent.findViewById(yId);
        final EditText editText3 = (EditText) parent.findViewById(zId);
        DelayedTextWatcher delayedTextWatcher = new DelayedTextWatcher(new Runnable() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda105
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5152x6542bc88(editText, editText2, editText3, updater, go);
            }
        });
        editText.addTextChangedListener(delayedTextWatcher);
        editText2.addTextChangedListener(delayedTextWatcher);
        editText3.addTextChangedListener(delayedTextWatcher);
    }

    /* JADX INFO: renamed from: lambda$addVector3Listener$313$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5152x6542bc88(EditText editText, EditText editText2, EditText editText3, Vector3Updater vector3Updater, GameObject gameObject) {
        if (this.selectedObject != null) {
            if (editText.hasFocus() || editText2.hasFocus() || editText3.hasFocus()) {
                try {
                    vector3Updater.update(gameObject, new Vector3(Float.parseFloat(editText.getText().toString()), Float.parseFloat(editText2.getText().toString()), Float.parseFloat(editText3.getText().toString())));
                } catch (NumberFormatException unused) {
                }
            }
        }
    }

    private void addEulerListener(final GameObject go, View parent, int pId, int yId, int rId, final QuaternionUpdater updater) {
        final EditText editText = (EditText) parent.findViewById(pId);
        final EditText editText2 = (EditText) parent.findViewById(yId);
        final EditText editText3 = (EditText) parent.findViewById(rId);
        DelayedTextWatcher delayedTextWatcher = new DelayedTextWatcher(new Runnable() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda176
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5141xd1a1052e(editText, editText2, editText3, updater, go);
            }
        });
        editText.addTextChangedListener(delayedTextWatcher);
        editText2.addTextChangedListener(delayedTextWatcher);
        editText3.addTextChangedListener(delayedTextWatcher);
    }

    /* JADX INFO: renamed from: lambda$addEulerListener$314$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5141xd1a1052e(EditText editText, EditText editText2, EditText editText3, QuaternionUpdater quaternionUpdater, GameObject gameObject) {
        if (this.selectedObject != null) {
            if (editText.hasFocus() || editText2.hasFocus() || editText3.hasFocus()) {
                try {
                    quaternionUpdater.update(gameObject, new Quaternion().setEulerAngles(Float.parseFloat(editText2.getText().toString()), Float.parseFloat(editText.getText().toString()), Float.parseFloat(editText3.getText().toString())));
                } catch (NumberFormatException unused) {
                }
            }
        }
    }

    private void updateVector3Fields(View parent, int xId, int yId, int zId, Vector3 vec) {
        ((EditText) parent.findViewById(xId)).setText(String.format(Locale.US, "%.3f", Float.valueOf(vec.x)));
        ((EditText) parent.findViewById(yId)).setText(String.format(Locale.US, "%.3f", Float.valueOf(vec.y)));
        ((EditText) parent.findViewById(zId)).setText(String.format(Locale.US, "%.3f", Float.valueOf(vec.z)));
    }

    private void updateEulerFields(View parent, int pId, int yId, int rId, Quaternion q) {
        ((EditText) parent.findViewById(pId)).setText(String.format(Locale.US, "%.1f", Float.valueOf(q.getPitch())));
        ((EditText) parent.findViewById(yId)).setText(String.format(Locale.US, "%.1f", Float.valueOf(q.getYaw())));
        ((EditText) parent.findViewById(rId)).setText(String.format(Locale.US, "%.1f", Float.valueOf(q.getRoll())));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addSimpleTextListener(final EditText editText, final StringUpdater updater) {
        editText.addTextChangedListener(new DelayedTextWatcher(new Runnable() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda179
            @Override // java.lang.Runnable
            public final void run() {
                InspectorManager.lambda$addSimpleTextListener$315(editText, updater);
            }
        }));
    }

    static /* synthetic */ void lambda$addSimpleTextListener$315(EditText editText, StringUpdater stringUpdater) {
        if (editText.hasFocus()) {
            stringUpdater.update(editText.getText().toString());
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/editor/InspectorManager$DelayedTextWatcher.class
      classes14.dex
     */
    private static class DelayedTextWatcher implements TextWatcher {
        private final Runnable action;

        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public DelayedTextWatcher(Runnable action) {
            this.action = action;
        }

        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable s) {
            this.action.run();
        }
    }

    private void setupSlider(final GameObject go, View sliderLayout, float initialValue, final FloatConsumer onUpdate) {
        SeekBar seekBar = (SeekBar) sliderLayout.findViewById(R.id.seekbar);
        final TextView textView = (TextView) sliderLayout.findViewById(R.id.text_value);
        seekBar.setProgress((int) (100.0f * initialValue));
        textView.setText(String.format(Locale.US, "%.2f", Float.valueOf(initialValue)));
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: org.catrobat.catroid.editor.InspectorManager.14
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar2) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar2, int progress, boolean fromUser) {
                if (fromUser) {
                    textView.setText(String.format(Locale.US, "%.2f", Float.valueOf(progress / 100.0f)));
                }
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar2) {
                onUpdate.accept(seekBar2.getProgress() / 100.0f);
                SceneManager sceneManager = InspectorManager.this.sceneManager;
                GameObject gameObject = go;
                sceneManager.setMaterialComponent(gameObject, (MaterialComponent) gameObject.getComponent(MaterialComponent.class));
            }
        });
    }

    private void setupColorPicker(final GameObject go, final Button colorButton, final Color initialColor, final ColorConsumer onUpdate) {
        colorButton.setBackgroundColor(libGdxColorToAndroidColor(initialColor));
        colorButton.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda286
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m5325x7ba0ee13(initialColor, colorButton, onUpdate, go, view);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$setupColorPicker$317$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5325x7ba0ee13(Color color, final Button button, final ColorConsumer colorConsumer, final GameObject gameObject, View view) {
        ColorPickerDialogBuilder.with(this.activity).setTitle("Choose Color").initialColor(libGdxColorToAndroidColor(color)).wheelType(ColorPickerView.WHEEL_TYPE.FLOWER).density(12).setPositiveButton("OK", new ColorPickerClickListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda317
            @Override // com.flask.colorpicker.builder.ColorPickerClickListener
            public final void onClick(DialogInterface dialogInterface, int i, Integer[] numArr) {
                this.f$0.m5324xc2296074(button, colorConsumer, gameObject, dialogInterface, i, numArr);
            }
        }).setNegativeButton("Cancel", (DialogInterface.OnClickListener) null).build().show();
    }

    /* JADX INFO: renamed from: lambda$setupColorPicker$316$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5324xc2296074(Button button, ColorConsumer colorConsumer, GameObject gameObject, DialogInterface dialogInterface, int i, Integer[] numArr) {
        button.setBackgroundColor(i);
        colorConsumer.accept(new Color(android.graphics.Color.red(i) / 255.0f, android.graphics.Color.green(i) / 255.0f, android.graphics.Color.blue(i) / 255.0f, android.graphics.Color.alpha(i) / 255.0f));
        this.sceneManager.setMaterialComponent(gameObject, (MaterialComponent) gameObject.getComponent(MaterialComponent.class));
    }

    private void setupTextureSlot(final GameObject go, View textureSlotLayout, String currentPath, final StringConsumer onUpdate) {
        TextView textView = (TextView) textureSlotLayout.findViewById(R.id.text_texture_path);
        Button button = (Button) textureSlotLayout.findViewById(R.id.btn_select_texture);
        ImageButton imageButton = (ImageButton) textureSlotLayout.findViewById(R.id.btn_clear_texture);
        if (currentPath != null && !currentPath.isEmpty()) {
            textView.setText(currentPath);
            imageButton.setVisibility(0);
        } else {
            textView.setText("None");
            imageButton.setVisibility(8);
        }
        button.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda284
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m5337xfe101669(onUpdate, go, view);
            }
        });
        imageButton.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda285
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m5338xee564213(onUpdate, go, view);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$setupTextureSlot$319$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5337xfe101669(final StringConsumer stringConsumer, final GameObject gameObject, View view) {
        showTexturePicker(new StringConsumer() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda173
            @Override // org.catrobat.catroid.editor.InspectorManager.StringConsumer
            public final void accept(String str) {
                this.f$0.m5336x449888ca(stringConsumer, gameObject, str);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$setupTextureSlot$318$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5336x449888ca(StringConsumer stringConsumer, GameObject gameObject, String str) {
        stringConsumer.accept(str);
        this.sceneManager.setMaterialComponent(gameObject, (MaterialComponent) gameObject.getComponent(MaterialComponent.class));
        m5171x5f6795b(gameObject);
    }

    /* JADX INFO: renamed from: lambda$setupTextureSlot$320$org-catrobat-catroid-editor-InspectorManager, reason: not valid java name */
    /* synthetic */ void m5338xee564213(StringConsumer stringConsumer, GameObject gameObject, View view) {
        stringConsumer.accept(null);
        this.sceneManager.setMaterialComponent(gameObject, (MaterialComponent) gameObject.getComponent(MaterialComponent.class));
        m5171x5f6795b(gameObject);
    }

    private void showTexturePicker(final StringConsumer onTextureSelected) {
        File[] fileArrListFiles = ProjectManager.getInstance().getCurrentProject().getFilesDir().listFiles();
        if (fileArrListFiles == null) {
            return;
        }
        final ArrayList arrayList = new ArrayList();
        for (File file : fileArrListFiles) {
            String lowerCase = file.getName().toLowerCase();
            if (lowerCase.endsWith(Constants.DEFAULT_IMAGE_EXTENSION) || lowerCase.endsWith(Constants.JPEG_IMAGE_EXTENSION) || lowerCase.endsWith(".jpeg")) {
                arrayList.add(file.getName());
            }
        }
        if (arrayList.isEmpty()) {
            Toast.makeText(this.activity, "No image files (.png, .jpg) found in project.", 0).show();
        } else {
            new AlertDialog.Builder(this.activity).setTitle("Select Texture").setItems((CharSequence[]) arrayList.toArray(new String[0]), new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.editor.InspectorManager$$ExternalSyntheticLambda125
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    onTextureSelected.accept((String) arrayList.get(i));
                }
            }).show();
        }
    }
}
