package org.catrobat.catroid.editor;

import android.view.View;
import android.view.inputmethod.InputMethodManager;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.DebugDrawer;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.raptor.CameraComponent;
import org.catrobat.catroid.raptor.ColliderShapeData;
import org.catrobat.catroid.raptor.GameObject;
import org.catrobat.catroid.raptor.KeyframeComponent;
import org.catrobat.catroid.raptor.KeyframeData;
import org.catrobat.catroid.raptor.PhysicsComponent;
import org.catrobat.catroid.raptor.SceneManager;
import org.catrobat.catroid.raptor.ThreeDManager;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/editor/EditorListener.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class EditorListener extends ApplicationAdapter {
    private final EditorActivity activity;
    private EditorCameraController cameraController;
    private ModelBatch debugBatch;
    private GestureDetector gestureDetector;
    private Gizmo gizmo;
    private ModelBatch gizmoBatch;
    private ModelInstance keyframeProxyInstance;
    private Model keyframeProxyModel;
    private SceneManager sceneManager;
    private ThreeDManager threeDManager;
    private boolean showColliders = false;
    private boolean isPcMode = false;
    private Vector3 tempVec3 = new Vector3();

    public EditorListener(EditorActivity activity) {
        this.activity = activity;
    }

    public void setPcMode(boolean isPcMode) {
        this.isPcMode = isPcMode;
        EditorCameraController editorCameraController = this.cameraController;
        if (editorCameraController != null) {
            editorCameraController.isPcMode = isPcMode;
        }
    }

    @Override // com.badlogic.gdx.ApplicationAdapter, com.badlogic.gdx.ApplicationListener
    public void create() {
        resetEngine();
        this.activity.onEditorReady(this.sceneManager, this.threeDManager);
        this.debugBatch = new ModelBatch();
        ModelBuilder modelBuilder = new ModelBuilder();
        Material material = new Material(ColorAttribute.createDiffuse(Color.CYAN));
        modelBuilder.begin();
        modelBuilder.part("top", 4, 9L, material).cone(1.0f, 1.0f, 1.0f, 4);
        Matrix4 matrix4Rotate = new Matrix4().setToTranslation(0.0f, -1.0f, 0.0f).rotate(Vector3.X, 180.0f);
        MeshPartBuilder meshPartBuilderPart = modelBuilder.part("bottom", 4, 9L, material);
        meshPartBuilderPart.cone(1.0f, 1.0f, 1.0f, 4);
        meshPartBuilderPart.setVertexTransform(matrix4Rotate);
        this.keyframeProxyModel = modelBuilder.end();
        this.keyframeProxyInstance = new ModelInstance(this.keyframeProxyModel);
    }

    @Override // com.badlogic.gdx.ApplicationAdapter, com.badlogic.gdx.ApplicationListener
    public void resize(int width, int height) {
        ThreeDManager threeDManager = this.threeDManager;
        if (threeDManager != null) {
            threeDManager.resize(width, height);
        }
        super.resize(width, height);
    }

    public void resetEngine() {
        resetEngine(null, new ThreeDManager.SceneSettings());
    }

    public void resetEngine(FileHandle sceneToLoad) {
        resetEngine(sceneToLoad, new ThreeDManager.SceneSettings());
    }

    public ThreeDManager getThreeDManager() {
        return this.threeDManager;
    }

    public void resetEngine(final FileHandle sceneToLoad, final ThreeDManager.SceneSettings settings) {
        Gdx.f82app.postRunnable(new Runnable() { // from class: org.catrobat.catroid.editor.EditorListener$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5067lambda$resetEngine$0$orgcatrobatcatroideditorEditorListener(settings, sceneToLoad);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$resetEngine$0$org-catrobat-catroid-editor-EditorListener, reason: not valid java name */
    /* synthetic */ void m5067lambda$resetEngine$0$orgcatrobatcatroideditorEditorListener(ThreeDManager.SceneSettings sceneSettings, FileHandle fileHandle) {
        ThreeDManager threeDManager = this.threeDManager;
        if (threeDManager != null) {
            threeDManager.dispose();
        }
        ModelBatch modelBatch = this.gizmoBatch;
        if (modelBatch != null) {
            modelBatch.dispose();
        }
        ThreeDManager threeDManager2 = new ThreeDManager();
        this.threeDManager = threeDManager2;
        threeDManager2.init(sceneSettings);
        this.threeDManager.enableRealisticRendering(true);
        this.threeDManager.setSkyColor(0.1f, 0.2f, 0.3f);
        this.threeDManager.createGrid(100.0f, 100);
        this.threeDManager.setEditorMode(true);
        SceneManager sceneManager = new SceneManager(this.threeDManager);
        this.sceneManager = sceneManager;
        sceneManager.setEditorMode(true);
        this.gizmoBatch = new ModelBatch();
        Camera camera = this.threeDManager.getCamera();
        camera.position.set(10.0f, 10.0f, 10.0f);
        camera.lookAt(0.0f, 0.0f, 0.0f);
        EditorCameraController editorCameraController = new EditorCameraController(camera);
        this.cameraController = editorCameraController;
        editorCameraController.isPcMode = this.isPcMode;
        this.gizmo = new Gizmo(this.activity, this.sceneManager, camera);
        setupInputProcessor();
        this.activity.onEngineReset(this.sceneManager, this.threeDManager);
        if (fileHandle != null) {
            this.sceneManager.loadScene(fileHandle);
            final EditorActivity editorActivity = this.activity;
            Objects.requireNonNull(editorActivity);
            editorActivity.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.editor.EditorListener$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    editorActivity.updateHierarchy();
                }
            });
        }
        Gdx.f82app.log("EditorListener", "3D Engine has been reset.");
    }

    public void onCameraMove(float vx, float vy, float vz) {
        EditorCameraController editorCameraController = this.cameraController;
        if (editorCameraController != null) {
            editorCameraController.velocity.add(vx, vy, vz);
        }
    }

    private void setupInputProcessor() {
        this.gestureDetector = new GestureDetector(new GestureDetector.GestureAdapter() { // from class: org.catrobat.catroid.editor.EditorListener.1
            @Override // com.badlogic.gdx.input.GestureDetector.GestureAdapter, com.badlogic.gdx.input.GestureDetector.GestureListener
            public boolean touchDown(float x, float y, int pointer, int button) {
                if (EditorListener.this.isPcMode && button != 0) {
                    return false;
                }
                if (EditorListener.this.gizmo.touchDown(EditorListener.this.threeDManager.getCamera().getPickRay(x, y))) {
                    EditorListener.this.cameraController.enabled = false;
                    return true;
                }
                EditorListener.this.cameraController.enabled = true;
                return EditorListener.this.cameraController.touchDown(x, y, pointer, button);
            }

            @Override // com.badlogic.gdx.input.GestureDetector.GestureAdapter, com.badlogic.gdx.input.GestureDetector.GestureListener
            public boolean pan(float x, float y, float deltaX, float deltaY) {
                if (EditorListener.this.gizmo.isDragging()) {
                    EditorListener.this.gizmo.touchDragged(EditorListener.this.threeDManager.getCamera().getPickRay(x, y));
                    return true;
                }
                return EditorListener.this.cameraController.pan(x, y, deltaX, deltaY);
            }

            @Override // com.badlogic.gdx.input.GestureDetector.GestureAdapter, com.badlogic.gdx.input.GestureDetector.GestureListener
            public boolean tap(float x, float y, int count, int button) {
                if (EditorListener.this.gizmo.isDragging()) {
                    return true;
                }
                GameObject objectByRaycast = EditorListener.this.sceneManager.getObjectByRaycast(EditorListener.this.threeDManager.getCamera().getPickRay(x, y));
                if (objectByRaycast == null) {
                    ColliderShapeData selectedCollider = EditorListener.this.activity.getInspectorManager().getSelectedCollider();
                    GameObject selectedObject = EditorListener.this.gizmo.getSelectedObject();
                    if (selectedCollider != null) {
                        EditorListener.this.activity.getInspectorManager().setSelectedCollider(null);
                        EditorListener.this.gizmo.setSelected(selectedObject, null);
                        EditorListener.this.activity.onObjectSelected(selectedObject, false);
                    } else {
                        EditorListener.this.gizmo.setSelected(null, null);
                        EditorListener.this.activity.onObjectSelected(null, false);
                    }
                } else {
                    EditorListener.this.activity.getInspectorManager().setSelectedCollider(null);
                    EditorListener.this.gizmo.setSelected(objectByRaycast, null);
                    EditorListener.this.activity.onObjectSelected(objectByRaycast, false);
                }
                return true;
            }

            @Override // com.badlogic.gdx.input.GestureDetector.GestureAdapter, com.badlogic.gdx.input.GestureDetector.GestureListener
            public boolean panStop(float x, float y, int pointer, int button) {
                if (EditorListener.this.gizmo.isDragging()) {
                    EditorListener.this.gizmo.touchUp();
                }
                EditorListener.this.cameraController.enabled = true;
                return true;
            }

            @Override // com.badlogic.gdx.input.GestureDetector.GestureAdapter, com.badlogic.gdx.input.GestureDetector.GestureListener
            public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
                return EditorListener.this.cameraController.pinch(initialPointer1, initialPointer2, pointer1, pointer2);
            }

            @Override // com.badlogic.gdx.input.GestureDetector.GestureAdapter, com.badlogic.gdx.input.GestureDetector.GestureListener
            public void pinchStop() {
                EditorListener.this.cameraController.pinchStop();
            }
        });
        AnonymousClass2 anonymousClass2 = new AnonymousClass2();
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(anonymousClass2);
        inputMultiplexer.addProcessor(this.gestureDetector);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/editor/EditorListener$2.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.editor.EditorListener$2, reason: invalid class name */
    class AnonymousClass2 extends InputAdapter {
        AnonymousClass2() {
        }

        @Override // com.badlogic.gdx.InputAdapter, com.badlogic.gdx.InputProcessor
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            if (EditorListener.this.activity == null) {
                return false;
            }
            EditorListener.this.activity.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.editor.EditorListener$2$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m5068lambda$touchDown$0$orgcatrobatcatroideditorEditorListener$2();
                }
            });
            return false;
        }

        /* JADX INFO: renamed from: lambda$touchDown$0$org-catrobat-catroid-editor-EditorListener$2, reason: not valid java name */
        /* synthetic */ void m5068lambda$touchDown$0$orgcatrobatcatroideditorEditorListener$2() {
            View currentFocus = EditorListener.this.activity.getCurrentFocus();
            if (currentFocus != null) {
                currentFocus.clearFocus();
                ((InputMethodManager) EditorListener.this.activity.getSystemService("input_method")).hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
            }
            View viewFindViewById = EditorListener.this.activity.findViewById(R.id.fragment_container);
            if (viewFindViewById != null) {
                viewFindViewById.requestFocus();
            }
        }

        @Override // com.badlogic.gdx.InputAdapter, com.badlogic.gdx.InputProcessor
        public boolean scrolled(float amountX, float amountY) {
            if (!EditorListener.this.isPcMode || EditorListener.this.cameraController == null) {
                return false;
            }
            EditorListener.this.cameraController.baseMoveSpeed -= amountY * 2.0f;
            if (EditorListener.this.cameraController.baseMoveSpeed < 0.5f) {
                EditorListener.this.cameraController.baseMoveSpeed = 0.5f;
            }
            if (EditorListener.this.cameraController.baseMoveSpeed <= 50.0f) {
                return true;
            }
            EditorListener.this.cameraController.baseMoveSpeed = 50.0f;
            return true;
        }
    }

    public void onCameraAccelerate(boolean accelerate) {
        EditorCameraController editorCameraController = this.cameraController;
        if (editorCameraController != null) {
            editorCameraController.isAccelerating = accelerate;
        }
    }

    private void selectObjectAt(int screenX, int screenY) {
        GameObject objectByRaycast = this.sceneManager.getObjectByRaycast(this.threeDManager.getCamera().getPickRay(screenX, screenY));
        this.gizmo.setSelectedObject(objectByRaycast);
        this.activity.onObjectSelected(objectByRaycast, false);
    }

    public void setColliderVisibility(boolean visible) {
        this.showColliders = visible;
    }

    public void setCurrentTool(EditorTool tool) {
        Gizmo gizmo = this.gizmo;
        if (gizmo != null) {
            gizmo.setCurrentTool(tool);
        }
    }

    @Override // com.badlogic.gdx.ApplicationAdapter, com.badlogic.gdx.ApplicationListener
    public void render() {
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
        Gdx.gl.glClear(16640);
        EditorCameraController editorCameraController = this.cameraController;
        if (editorCameraController != null) {
            editorCameraController.update(Gdx.graphics.getDeltaTime());
        }
        SceneManager sceneManager = this.sceneManager;
        if (sceneManager != null) {
            sceneManager.update(Gdx.graphics.getDeltaTime());
        }
        this.threeDManager.update(Gdx.graphics.getDeltaTime());
        this.threeDManager.render();
        renderKeyframeVisuals();
        DebugDrawer debugDrawer = this.threeDManager.getDebugDrawer();
        if (debugDrawer != null) {
            debugDrawer.begin(this.threeDManager.getCamera());
            for (GameObject gameObject : this.sceneManager.getAllGameObjects().values()) {
                CameraComponent cameraComponent = (CameraComponent) gameObject.getComponent(CameraComponent.class);
                if (cameraComponent != null) {
                    this.threeDManager.renderCameraFrustum(gameObject.transform.toMatrix(), cameraComponent.fieldOfView, cameraComponent.farPlane);
                }
            }
            debugDrawer.end();
        }
        Map<String, ModelInstance> editorProxies = this.threeDManager.getEditorProxies();
        if (!editorProxies.isEmpty()) {
            for (Map.Entry<String, ModelInstance> entry : editorProxies.entrySet()) {
                GameObject gameObjectFindGameObject = this.sceneManager.findGameObject(entry.getKey());
                if (gameObjectFindGameObject != null) {
                    entry.getValue().transform.set(gameObjectFindGameObject.transform.position, gameObjectFindGameObject.transform.rotation);
                }
            }
            this.gizmoBatch.begin(this.threeDManager.getCamera());
            Iterator<ModelInstance> it = editorProxies.values().iterator();
            while (it.hasNext()) {
                this.gizmoBatch.render(it.next());
            }
            this.gizmoBatch.end();
        }
        GameObject selectedObject = this.gizmo.getSelectedObject();
        if (this.showColliders && selectedObject != null) {
            PhysicsComponent physicsComponent = (PhysicsComponent) selectedObject.getComponent(PhysicsComponent.class);
            ModelInstance modelInstance = this.threeDManager.getModelInstance(selectedObject.f591id);
            if (physicsComponent != null && modelInstance != null && !physicsComponent.colliders.isEmpty()) {
                ColliderShapeData selectedCollider = this.activity.getInspectorManager().getSelectedCollider();
                this.threeDManager.getWireframeBatch().begin(this.threeDManager.getCamera());
                Iterator<ColliderShapeData> it2 = physicsComponent.colliders.iterator();
                while (it2.hasNext()) {
                    ColliderShapeData next = it2.next();
                    Color color = next == selectedCollider ? Color.YELLOW : Color.GREEN;
                    ThreeDManager threeDManager = this.threeDManager;
                    threeDManager.renderWireframeShape(threeDManager.getWireframeBatch(), next, modelInstance.transform, color);
                }
                this.threeDManager.getWireframeBatch().end();
            }
        }
        Gizmo gizmo = this.gizmo;
        if (gizmo != null) {
            gizmo.render(this.gizmoBatch);
        }
    }

    private void renderKeyframeVisuals() {
        GameObject selectedObject = this.gizmo.getSelectedObject();
        if (selectedObject == null || !selectedObject.hasComponent(KeyframeComponent.class)) {
            return;
        }
        KeyframeComponent keyframeComponent = (KeyframeComponent) selectedObject.getComponent(KeyframeComponent.class);
        synchronized (keyframeComponent.keyframes) {
            if (keyframeComponent.keyframes.isEmpty()) {
                return;
            }
            this.debugBatch.begin(this.cameraController.camera);
            Iterator<KeyframeData> it = keyframeComponent.keyframes.iterator();
            while (it.hasNext()) {
                this.keyframeProxyInstance.transform.setToTranslation(it.next().position).scale(0.5f, 0.5f, 0.5f);
                this.debugBatch.render(this.keyframeProxyInstance);
            }
            this.debugBatch.end();
            DebugDrawer debugDrawer = this.threeDManager.getDebugDrawer();
            if (debugDrawer == null) {
                return;
            }
            int i = 0;
            while (i < keyframeComponent.keyframes.size() - 1) {
                Vector3 vector3 = keyframeComponent.keyframes.get(i).position;
                i++;
                Vector3 vector4 = keyframeComponent.keyframes.get(i).position;
                this.tempVec3.set(1.0f, 1.0f, 1.0f);
                debugDrawer.begin(this.cameraController.camera);
                debugDrawer.drawLine(vector3, vector4, this.tempVec3);
                debugDrawer.end();
            }
        }
    }

    @Override // com.badlogic.gdx.ApplicationAdapter, com.badlogic.gdx.ApplicationListener
    public void dispose() {
        ThreeDManager threeDManager = this.threeDManager;
        if (threeDManager != null) {
            threeDManager.dispose();
        }
        ModelBatch modelBatch = this.gizmoBatch;
        if (modelBatch != null) {
            modelBatch.dispose();
        }
        ModelBatch modelBatch2 = this.debugBatch;
        if (modelBatch2 != null) {
            modelBatch2.dispose();
        }
        Model model = this.keyframeProxyModel;
        if (model != null) {
            model.dispose();
        }
    }

    public SceneManager getSceneManager() {
        return this.sceneManager;
    }

    public Gizmo getGizmo() {
        return this.gizmo;
    }
}
