package org.catrobat.catroid.raptor;

import android.util.Log;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import net.mgsx.gltf.scene3d.lights.PointLightEx;
import net.mgsx.gltf.scene3d.lights.SpotLightEx;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.EasingFunctions;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/SceneManager.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SceneManager {
    private static final long MAX_LOADING_TIME_PER_FRAME_MS = 8;
    private final List<CameraAttachment> cameraAttachments;
    public final ThreeDManager engine;
    private final Json json;
    public String skyboxPath;
    private final Queue<GameObject> loadingQueue = new LinkedList();
    private final Map<String, GameObject> gameObjects = new ConcurrentHashMap();
    private boolean isEditorMode = false;
    private GameObject mainCameraObject = null;
    private boolean isPlaying = false;
    private final Vector3 tmpPos = new Vector3();
    private final Quaternion tmpRot = new Quaternion();
    private final Vector3 tmpScale = new Vector3();
    private final Matrix4 tmpMat1 = new Matrix4();
    private final Matrix4 tmpMat2 = new Matrix4();
    private final Matrix4 tmpMat3 = new Matrix4();
    private FogComponent cachedFogComponent = null;
    private final List<BoneAttachment> activeAttachments = new ArrayList();
    private final Vector3 tmpKeyframePos = new Vector3();
    private final Quaternion tmpKeyframeRot = new Quaternion();
    private final Vector3 tmpKeyframeScale = new Vector3();
    private final Matrix4 tmpMatInv = new Matrix4();
    private final Ray tmpRayLocal = new Ray();
    private final Vector3 tmpIntersectionLocal = new Vector3();
    private final Vector3 tmpIntersectionWorld = new Vector3();
    private final BoundingBox tmpBounds = new BoundingBox();
    public float skyR = 0.1f;
    public float skyG = 0.2f;
    public float skyB = 0.3f;
    public float ambientIntensity = 1.0f;

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/SceneManager$BoneAttachment.class
      classes14.dex
     */
    static class BoneAttachment {
        String boneName;
        String childId;
        Vector3 localPosOffset = new Vector3();
        Quaternion localRotOffset = new Quaternion();
        String parentModelId;

        public BoneAttachment(String child, String parent, String bone) {
            this.childId = child;
            this.parentModelId = parent;
            this.boneName = bone;
        }
    }

    public void attachObjectToBone(String childId, String parentId, String boneName, float offsetX, float offsetY, float offsetZ) {
        detachObject(childId);
        BoneAttachment boneAttachment = new BoneAttachment(childId, parentId, boneName);
        boneAttachment.localPosOffset.set(offsetX, offsetY, offsetZ);
        this.activeAttachments.add(boneAttachment);
    }

    public void detachObject(final String childId) {
        this.activeAttachments.removeIf(new Predicate() { // from class: org.catrobat.catroid.raptor.SceneManager$$ExternalSyntheticLambda14
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((SceneManager.BoneAttachment) obj).childId.equals(childId);
            }
        });
    }

    public void setEditorMode(boolean isEditor) {
        this.isEditorMode = isEditor;
    }

    public void findAndSetMainCamera() {
        this.mainCameraObject = null;
        for (GameObject gameObject : this.gameObjects.values()) {
            CameraComponent cameraComponent = (CameraComponent) gameObject.getComponent(CameraComponent.class);
            if (cameraComponent != null && cameraComponent.isMainCamera) {
                this.mainCameraObject = gameObject;
                return;
            }
        }
    }

    public SceneManager(ThreeDManager lowLevelEngine) {
        Json json = new Json();
        this.json = json;
        this.cameraAttachments = new ArrayList();
        this.engine = lowLevelEngine;
        lowLevelEngine.setSceneManager(this);
        json.setIgnoreUnknownFields(true);
        json.setUsePrototypes(false);
        json.addClassTag("ParticleSystem3D", ParticleSystem3DComponent.class);
        json.addClassTag("MinMaxCurve", ParticleSystem3DComponent.MinMaxCurve.class);
        json.addClassTag("MinMaxGradient", ParticleSystem3DComponent.MinMaxGradient.class);
        json.addClassTag("Burst", ParticleSystem3DComponent.Burst.class);
        json.addClassTag("EmissionModule", ParticleSystem3DComponent.EmissionModule.class);
        json.addClassTag("ShapeModule", ParticleSystem3DComponent.ShapeModule.class);
        json.addClassTag("CollisionPlane", ParticleSystem3DComponent.CollisionPlane.class);
        json.addClassTag("SubEmitterEntry", ParticleSystem3DComponent.SubEmitterEntry.class);
    }

    private void synchronizeTransformsFromEngine() {
        for (GameObject gameObject : this.gameObjects.values()) {
            btRigidBody physicsBody = this.engine.getPhysicsBody(gameObject.f591id);
            if (physicsBody != null && !physicsBody.isStaticObject()) {
                Matrix4 worldTransform = physicsBody.getWorldTransform();
                worldTransform.getTranslation(this.tmpPos);
                worldTransform.getRotation(this.tmpRot, true);
                if (gameObject.parentId == null) {
                    gameObject.transform.position.set(this.tmpPos);
                    gameObject.transform.rotation.set(this.tmpRot);
                } else {
                    GameObject gameObjectFindGameObject = findGameObject(gameObject.parentId);
                    if (gameObjectFindGameObject != null && Math.abs(gameObjectFindGameObject.transform.worldTransform.det()) >= 1.0E-6f) {
                        this.tmpMat1.set(gameObjectFindGameObject.transform.worldTransform).inv();
                        this.tmpMat2.set(this.tmpPos, this.tmpRot, gameObject.transform.scale);
                        this.tmpMat3.set(this.tmpMat1).mul(this.tmpMat2);
                        this.tmpMat3.getTranslation(gameObject.transform.position);
                        this.tmpMat3.getRotation(gameObject.transform.rotation, true);
                    }
                }
            }
        }
    }

    public void update(float delta) {
        ModelInstance modelInstance;
        GameObject gameObjectFindGameObject;
        Camera camera;
        processLoadingQueue();
        if (!this.isEditorMode) {
            updateKeyframeAnimations(delta);
            synchronizeTransformsFromEngine();
        }
        if (!this.cameraAttachments.isEmpty() && (camera = this.engine.getCamera()) != null) {
            Matrix4 matrix4Inv = new Matrix4(camera.view).inv();
            for (CameraAttachment cameraAttachment : this.cameraAttachments) {
                GameObject gameObjectFindGameObject2 = findGameObject(cameraAttachment.childId);
                if (gameObjectFindGameObject2 != null) {
                    gameObjectFindGameObject2.transform.worldTransform.set(matrix4Inv).mul(cameraAttachment.localOffsetMat);
                    if (gameObjectFindGameObject2.parentId == null) {
                        gameObjectFindGameObject2.transform.worldTransform.getTranslation(gameObjectFindGameObject2.transform.position);
                        gameObjectFindGameObject2.transform.worldTransform.getRotation(gameObjectFindGameObject2.transform.rotation, true);
                    }
                }
            }
        }
        updateWorldTransforms();
        for (GameObject gameObject : this.gameObjects.values()) {
            applyTransformToEngine(gameObject);
            if (gameObject.hasComponent(LightComponent.class)) {
                applyLightAndTransform(gameObject);
            }
        }
        GameObject gameObject2 = this.mainCameraObject;
        if (gameObject2 != null) {
            Matrix4 matrix4 = gameObject2.transform.worldTransform;
            matrix4.getTranslation(this.tmpPos);
            matrix4.getRotation(this.tmpRot, true);
            this.engine.setCameraPosition(this.tmpPos.x, this.tmpPos.y, this.tmpPos.z);
            this.engine.setCameraRotation(this.tmpRot);
        }
        for (BoneAttachment boneAttachment : this.activeAttachments) {
            GameObject gameObjectFindGameObject3 = findGameObject(boneAttachment.childId);
            if (gameObjectFindGameObject3 != null && (modelInstance = this.engine.getModelInstance(boneAttachment.parentModelId)) != null) {
                Node node = modelInstance.getNode(boneAttachment.boneName, true);
                if (node != null) {
                    gameObjectFindGameObject3.transform.worldTransform.set(modelInstance.transform);
                    gameObjectFindGameObject3.transform.worldTransform.mul(node.globalTransform);
                    gameObjectFindGameObject3.transform.worldTransform.translate(boneAttachment.localPosOffset);
                    gameObjectFindGameObject3.transform.worldTransform.scl(gameObjectFindGameObject3.transform.scale);
                    this.engine.setWorldTransform(gameObjectFindGameObject3.f591id, gameObjectFindGameObject3.transform.worldTransform);
                    if (gameObjectFindGameObject3.parentId != null && (gameObjectFindGameObject = findGameObject(gameObjectFindGameObject3.parentId)) != null) {
                        Matrix4 matrix4Mul = new Matrix4(gameObjectFindGameObject.transform.worldTransform).inv().mul(gameObjectFindGameObject3.transform.worldTransform);
                        matrix4Mul.getTranslation(gameObjectFindGameObject3.transform.position);
                        matrix4Mul.getRotation(gameObjectFindGameObject3.transform.rotation, true);
                    } else {
                        gameObjectFindGameObject3.transform.worldTransform.getTranslation(gameObjectFindGameObject3.transform.position);
                        gameObjectFindGameObject3.transform.worldTransform.getRotation(gameObjectFindGameObject3.transform.rotation, true);
                    }
                    updateTransformRecursive(gameObjectFindGameObject3, findGameObject(gameObjectFindGameObject3.parentId));
                    applyTransformToEngineRecursive(gameObjectFindGameObject3);
                } else {
                    Log.e("SceneManager", "ОШИБКА ПРИВЯЗКИ: Кость '" + boneAttachment.boneName + "' не найдена в модели!");
                }
            }
        }
    }

    private void updateKeyframeAnimations(float delta) {
        for (GameObject gameObject : this.gameObjects.values()) {
            KeyframeComponent keyframeComponent = (KeyframeComponent) gameObject.getComponent(KeyframeComponent.class);
            if (keyframeComponent != null && keyframeComponent.isPlaying && keyframeComponent.keyframes.size() >= 2) {
                keyframeComponent.currentTime += delta;
                float duration = keyframeComponent.getDuration();
                if (keyframeComponent.currentTime > duration) {
                    if (keyframeComponent.looping) {
                        keyframeComponent.currentTime %= duration;
                    } else {
                        keyframeComponent.isPlaying = false;
                        keyframeComponent.currentTime = duration;
                    }
                }
                KeyframeData keyframeData = null;
                KeyframeData keyframeData2 = null;
                for (KeyframeData keyframeData3 : keyframeComponent.keyframes) {
                    if (keyframeData3.time > keyframeComponent.currentTime) {
                        keyframeData = keyframeData3;
                        break;
                    }
                    keyframeData2 = keyframeData3;
                }
                if (keyframeData == null) {
                    gameObject.transform.position.set(keyframeData2.position);
                    gameObject.transform.rotation.set(keyframeData2.rotation);
                    gameObject.transform.scale.set(keyframeData2.scale);
                } else {
                    float f = keyframeData.time - keyframeData2.time;
                    float fCalculate = EasingFunctions.calculate(keyframeData2.easingToNext, f > 1.0E-4f ? MathUtils.clamp((keyframeComponent.currentTime - keyframeData2.time) / f, 0.0f, 1.0f) : 1.0f, 1.0f, 0.0f, 1.0f);
                    this.tmpKeyframePos.set(keyframeData2.position).lerp(keyframeData.position, fCalculate);
                    this.tmpKeyframeRot.set(keyframeData2.rotation).slerp(keyframeData.rotation, fCalculate);
                    this.tmpKeyframeScale.set(keyframeData2.scale).lerp(keyframeData.scale, fCalculate);
                    gameObject.transform.position.set(this.tmpKeyframePos);
                    gameObject.transform.rotation.set(this.tmpKeyframeRot);
                    gameObject.transform.scale.set(this.tmpKeyframeScale);
                }
            }
        }
    }

    public void playKeyframeAnimation(String objectId) {
        KeyframeComponent keyframeComponent;
        GameObject gameObjectFindGameObject = findGameObject(objectId);
        if (gameObjectFindGameObject == null || (keyframeComponent = (KeyframeComponent) gameObjectFindGameObject.getComponent(KeyframeComponent.class)) == null) {
            return;
        }
        keyframeComponent.isPlaying = true;
    }

    public void stopKeyframeAnimation(String objectId) {
        KeyframeComponent keyframeComponent;
        GameObject gameObjectFindGameObject = findGameObject(objectId);
        if (gameObjectFindGameObject == null || (keyframeComponent = (KeyframeComponent) gameObjectFindGameObject.getComponent(KeyframeComponent.class)) == null) {
            return;
        }
        keyframeComponent.isPlaying = false;
    }

    public void setKeyframeAnimationTime(String objectId, float time) {
        KeyframeComponent keyframeComponent;
        GameObject gameObjectFindGameObject = findGameObject(objectId);
        if (gameObjectFindGameObject == null || (keyframeComponent = (KeyframeComponent) gameObjectFindGameObject.getComponent(KeyframeComponent.class)) == null) {
            return;
        }
        keyframeComponent.currentTime = time;
        updateKeyframeAnimations(0.0f);
        updateWorldTransforms();
    }

    private void processLoadingQueue() {
        if (this.loadingQueue.isEmpty()) {
            return;
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        while (!this.loadingQueue.isEmpty() && System.currentTimeMillis() - jCurrentTimeMillis <= 8) {
            GameObject gameObjectPoll = this.loadingQueue.poll();
            if (gameObjectPoll != null) {
                this.gameObjects.put(gameObjectPoll.f591id, gameObjectPoll);
                m5457x9eeb7ea3(gameObjectPoll);
            }
        }
    }

    public void updateWorldTransforms() {
        for (GameObject gameObject : this.gameObjects.values()) {
            if (gameObject.parentId == null) {
                updateTransformRecursive(gameObject, null);
            }
        }
    }

    private void updateTransformRecursive(GameObject current, GameObject parent) {
        current.transform.worldTransform.set(current.transform.position, current.transform.rotation, current.transform.scale);
        if (parent != null) {
            current.transform.worldTransform.mulLeft(parent.transform.worldTransform);
        }
        for (int i = 0; i < current.childrenIds.size(); i++) {
            GameObject gameObjectFindGameObject = findGameObject(current.childrenIds.get(i));
            if (gameObjectFindGameObject != null) {
                updateTransformRecursive(gameObjectFindGameObject, current);
            }
        }
    }

    private void loadPrefabForObject(GameObject rootGo, PrefabComponent prefabComp) {
        if (prefabComp.spawnedInstances != null) {
            for (String str : prefabComp.spawnedInstances) {
                GameObject gameObjectFindGameObject = findGameObject(str);
                if (gameObjectFindGameObject != null) {
                    rootGo.childrenIds.remove(str);
                    removeGameObject(gameObjectFindGameObject);
                }
            }
            prefabComp.spawnedInstances.clear();
        } else {
            prefabComp.spawnedInstances = new ArrayList();
        }
        if (prefabComp.prefabFilePath == null || prefabComp.prefabFilePath.isEmpty()) {
            return;
        }
        File file = ProjectManager.getInstance().getCurrentProject().getFile(prefabComp.prefabFilePath);
        if (file == null || !file.exists()) {
            Gdx.f82app.error("Prefab", "Prefab file not found: " + prefabComp.prefabFilePath);
            return;
        }
        try {
            String string = Gdx.files.absolute(file.getAbsolutePath()).readString();
            Json json = new Json();
            json.setUsePrototypes(false);
            json.setIgnoreUnknownFields(true);
            SceneData sceneData = (SceneData) json.fromJson(SceneData.class, string);
            if (sceneData != null && sceneData.gameObjects != null) {
                HashMap map = new HashMap();
                ArrayList<GameObject> arrayList = new ArrayList();
                Array.ArrayIterator<GameObject> it = sceneData.gameObjects.iterator();
                while (it.hasNext()) {
                    GameObject next = it.next();
                    String str2 = rootGo.f591id + "_prefab_" + next.f591id;
                    map.put(next.f591id, str2);
                    next.f591id = str2;
                    next.f592name = "[Prefab] " + next.f592name;
                    next.isPrefabInstance = true;
                    if (next.parentId == null) {
                        arrayList.add(next);
                    }
                }
                Array.ArrayIterator<GameObject> it2 = sceneData.gameObjects.iterator();
                while (it2.hasNext()) {
                    GameObject next2 = it2.next();
                    if (next2.parentId != null) {
                        next2.parentId = (String) map.get(next2.parentId);
                    } else {
                        next2.parentId = rootGo.f591id;
                    }
                    ArrayList arrayList2 = new ArrayList();
                    Iterator<String> it3 = next2.childrenIds.iterator();
                    while (it3.hasNext()) {
                        arrayList2.add((String) map.get(it3.next()));
                    }
                    next2.childrenIds = arrayList2;
                    this.gameObjects.put(next2.f591id, next2);
                    prefabComp.spawnedInstances.add(next2.f591id);
                }
                for (GameObject gameObject : arrayList) {
                    if (!rootGo.childrenIds.contains(gameObject.f591id)) {
                        rootGo.childrenIds.add(gameObject.f591id);
                    }
                }
                Array.ArrayIterator<GameObject> it4 = sceneData.gameObjects.iterator();
                while (it4.hasNext()) {
                    m5457x9eeb7ea3(it4.next());
                }
            }
        } catch (Exception e) {
            Gdx.f82app.error("Prefab", "Error loading prefab", e);
        }
    }

    private String generateUniqueName(String baseName) {
        String strReplaceAll = baseName.replaceAll(" \\(\\d+\\)$", "");
        if (!this.gameObjects.containsKey(strReplaceAll)) {
            return strReplaceAll;
        }
        int i = 1;
        while (this.gameObjects.containsKey(strReplaceAll + " (" + i + Constant.AFTER_QUTO)) {
            i++;
        }
        return strReplaceAll + " (" + i + Constant.AFTER_QUTO;
    }

    private void applyTransformToEngine(GameObject go) {
        if (go == null) {
            return;
        }
        Quaternion quaternion = new Quaternion();
        go.transform.worldTransform.getRotation(quaternion, true);
        if (Float.isNaN(quaternion.x) || Float.isNaN(quaternion.y) || Float.isNaN(quaternion.z) || Float.isNaN(quaternion.w)) {
            Log.e("SceneManager_Apply", "CRITICAL: NaN rotation detected for GameObject '" + go.f591id + "'. Aborting transform update for this frame to prevent engine corruption.");
            return;
        }
        this.engine.setWorldTransform(go.f591id, go.transform.worldTransform);
        if (go.hasComponent(ParticleComponent.class)) {
            this.engine.updateParticleTransform(go.f591id, go.transform.worldTransform);
        }
        if (go.hasComponent(ParticleSystem3DComponent.class)) {
            this.engine.updateParticleTransform3D(go.f591id, go.transform.worldTransform);
        }
    }

    public void setPosition(GameObject go, Vector3 position) {
        go.transform.position.set(position);
    }

    public void setRotation(GameObject go, Quaternion rotation) {
        go.transform.rotation.set(rotation);
    }

    public void setScale(GameObject go, Vector3 scale) {
        go.transform.scale.set(scale);
    }

    public void loadAndReplaceScene(final FileHandle fileHandle) {
        Gdx.f82app.postRunnable(new Runnable() { // from class: org.catrobat.catroid.raptor.SceneManager$$ExternalSyntheticLambda19
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5453xb27884e2(fileHandle);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$loadAndReplaceScene$1$org-catrobat-catroid-raptor-SceneManager, reason: not valid java name */
    /* synthetic */ void m5453xb27884e2(FileHandle fileHandle) {
        clearScene_internal();
        if (fileHandle == null || !fileHandle.exists()) {
            Gdx.f82app.error("SceneManager", "Scene file handle is null or does not exist.");
            return;
        }
        String string = fileHandle.readString();
        this.json.setUsePrototypes(false);
        this.json.setIgnoreUnknownFields(true);
        SceneData sceneData = (SceneData) this.json.fromJson(SceneData.class, string);
        if (sceneData == null) {
            return;
        }
        setBackgroundLightIntensity(sceneData.ambientIntensity);
        setSkyColor(sceneData.skyR, sceneData.skyG, sceneData.skyB);
        this.engine.setShadowSettings(sceneData.shadowSize > 0.0f ? sceneData.shadowSize : 100.0f, (int) (sceneData.shadowResolution > 0.0f ? sceneData.shadowResolution : 2048.0f), sceneData.useCSM, sceneData.csmSplitFactor >= 1.0f ? sceneData.csmSplitFactor : 4.0f);
        if (sceneData.gameObjects == null) {
            return;
        }
        Array.ArrayIterator<GameObject> it = sceneData.gameObjects.iterator();
        while (it.hasNext()) {
            GameObject next = it.next();
            this.gameObjects.put(next.f591id, next);
        }
        ThreeDManager threeDManager = this.engine;
        if (threeDManager == null || threeDManager.isDisposed()) {
            Log.i("SceneManager", "Aborted scene rebuilding: ThreeDManager is disposed.");
            return;
        }
        Array.ArrayIterator<GameObject> it2 = sceneData.gameObjects.iterator();
        while (it2.hasNext()) {
            m5457x9eeb7ea3(it2.next());
        }
        findAndSetMainCamera();
        Gdx.f82app.log("SceneManager", "Applying loaded skybox: " + this.skyboxPath);
        String str = sceneData.skyboxPath;
        this.skyboxPath = str;
        setSkybox(str);
    }

    private void createRenderableForGameObject(GameObject go) {
        String absolutePath;
        RenderComponent renderComponent = (RenderComponent) go.getComponent(RenderComponent.class);
        if (renderComponent == null || renderComponent.modelFileName == null || renderComponent.modelFileName.isEmpty()) {
            return;
        }
        String str = renderComponent.modelFileName;
        if (str.startsWith("primitive:")) {
            if (str.equals("primitive:cube")) {
                this.engine.createCube(go.f591id);
                return;
            } else {
                if (str.equals("primitive:sphere")) {
                    this.engine.createSphere(go.f591id);
                    return;
                }
                return;
            }
        }
        if (str.startsWith("assets://")) {
            absolutePath = str.substring("assets://".length());
        } else {
            File file = ProjectManager.getInstance().getCurrentProject().getFile(str);
            if (file != null && file.exists()) {
                absolutePath = file.getAbsolutePath();
            } else {
                Gdx.f82app.error("SceneManager", "Rebuild failed: Model file not found: " + str);
                return;
            }
        }
        if (this.engine.createObject(go.f591id, absolutePath)) {
            return;
        }
        Gdx.f82app.error("SceneManager", "Rebuild failed: Could not create render object for " + go.f591id);
    }

    private void rebuildComponentsForGameObject(GameObject go) {
        this.engine.setWorldTransform(go.f591id, go.transform.worldTransform);
        Log.d("PhysicsDebug", "Rebuilding components for '" + go.f592name + "' with final World Transform:\n" + go.transform.worldTransform);
        PhysicsComponent physicsComponent = (PhysicsComponent) go.getComponent(PhysicsComponent.class);
        if (physicsComponent != null) {
            if (physicsComponent.colliders != null && !physicsComponent.colliders.isEmpty()) {
                this.engine.setPhysicsStateFromComponent(go.f591id, physicsComponent, go.transform.worldTransform);
            } else {
                this.engine.setPhysicsState(go.f591id, physicsComponent.state, physicsComponent.shape, physicsComponent.mass, go.transform.worldTransform);
            }
            this.engine.setFriction(go.f591id, physicsComponent.friction);
            this.engine.setRestitution(go.f591id, physicsComponent.restitution);
        }
        if (((LightComponent) go.getComponent(LightComponent.class)) != null) {
            this.engine.createEditorProxy(go.f591id);
            applyLightAndTransform(go);
        }
        CameraComponent cameraComponent = (CameraComponent) go.getComponent(CameraComponent.class);
        if (cameraComponent != null) {
            this.engine.createCameraProxy(go.f591id);
            if (cameraComponent.isMainCamera) {
                applyCameraComponentToEngine(go, cameraComponent);
            }
        }
        playAnimationFromComponent(go);
        MaterialComponent materialComponent = (MaterialComponent) go.getComponent(MaterialComponent.class);
        if (materialComponent != null) {
            this.engine.applyPBRMaterial(go.f591id, materialComponent);
        }
    }

    public GameObject createGameObject(String baseName) {
        String str = baseName;
        int i = 1;
        while (this.gameObjects.containsKey(str)) {
            str = baseName + " (" + i + Constant.AFTER_QUTO;
            i++;
        }
        GameObject gameObject = new GameObject(str);
        this.gameObjects.put(gameObject.f591id, gameObject);
        return gameObject;
    }

    public boolean renameGameObject(GameObject go, String newName) {
        GameObject gameObjectFindGameObject;
        if (go == null || newName == null || newName.isEmpty() || this.gameObjects.containsKey(newName)) {
            return false;
        }
        String str = go.f591id;
        if (str.equals(newName)) {
            return true;
        }
        if (go.parentId != null && (gameObjectFindGameObject = findGameObject(go.parentId)) != null) {
            gameObjectFindGameObject.childrenIds.remove(str);
            gameObjectFindGameObject.childrenIds.add(newName);
        }
        Iterator<String> it = go.childrenIds.iterator();
        while (it.hasNext()) {
            GameObject gameObjectFindGameObject2 = findGameObject(it.next());
            if (gameObjectFindGameObject2 != null) {
                gameObjectFindGameObject2.parentId = newName;
            }
        }
        this.gameObjects.remove(str);
        go.f591id = newName;
        go.f592name = newName;
        this.gameObjects.put(go.f591id, go);
        this.engine.renameObject(str, newName);
        return true;
    }

    public GameObject duplicateGameObject(GameObject original) {
        GameObject gameObjectFindGameObject;
        String str;
        if (original == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        HashMap map = new HashMap();
        GameObject gameObjectCloneRecursive = cloneRecursive(original, arrayList, map);
        for (GameObject gameObject : arrayList) {
            if (gameObject.parentId != null && (str = map.get(gameObject.parentId)) != null) {
                gameObject.parentId = str;
            }
        }
        if (gameObjectCloneRecursive != null) {
            gameObjectCloneRecursive.parentId = original.parentId;
        }
        for (GameObject gameObject2 : arrayList) {
            this.gameObjects.put(gameObject2.f591id, gameObject2);
            m5457x9eeb7ea3(gameObject2);
        }
        if (gameObjectCloneRecursive != null && gameObjectCloneRecursive.parentId != null && (gameObjectFindGameObject = findGameObject(gameObjectCloneRecursive.parentId)) != null && !gameObjectFindGameObject.childrenIds.contains(gameObjectCloneRecursive.f591id)) {
            gameObjectFindGameObject.childrenIds.add(gameObjectCloneRecursive.f591id);
        }
        updateWorldTransforms();
        return gameObjectCloneRecursive;
    }

    private GameObject cloneRecursive(GameObject original, List<GameObject> copies, Map<String, String> idMapping) {
        GameObject gameObject = (GameObject) this.json.fromJson(GameObject.class, this.json.toJson(original));
        gameObject.f592name = generateUniqueName(original.f592name);
        gameObject.f591id = gameObject.f592name;
        copies.add(gameObject);
        idMapping.put(original.f591id, gameObject.f591id);
        ArrayList arrayList = new ArrayList(gameObject.childrenIds);
        gameObject.childrenIds.clear();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            GameObject gameObjectFindGameObject = findGameObject((String) it.next());
            if (gameObjectFindGameObject != null) {
                gameObject.childrenIds.add(cloneRecursive(gameObjectFindGameObject, copies, idMapping).f591id);
            }
        }
        return gameObject;
    }

    private GameObject duplicateRecursive(GameObject original, Map<String, String> idMapping) {
        GameObject gameObject = (GameObject) this.json.fromJson(GameObject.class, this.json.toJson(original));
        String strGenerateUniqueName = generateUniqueName(original.f592name);
        gameObject.f592name = strGenerateUniqueName;
        gameObject.f591id = strGenerateUniqueName;
        idMapping.put(original.f591id, gameObject.f591id);
        this.gameObjects.put(gameObject.f591id, gameObject);
        ArrayList arrayList = new ArrayList(gameObject.childrenIds);
        gameObject.childrenIds.clear();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            GameObject gameObjectFindGameObject = findGameObject((String) it.next());
            if (gameObjectFindGameObject != null) {
                GameObject gameObjectDuplicateRecursive = duplicateRecursive(gameObjectFindGameObject, idMapping);
                gameObjectDuplicateRecursive.parentId = gameObject.f591id;
                gameObject.childrenIds.add(gameObjectDuplicateRecursive.f591id);
            }
        }
        rebuildGameObject(gameObject);
        return gameObject;
    }

    public GameObject getObjectByRaycast(Ray ray) {
        GameObject gameObject = null;
        float f = Float.MAX_VALUE;
        for (GameObject gameObject2 : this.gameObjects.values()) {
            if (gameObject2.isActive) {
                ModelInstance modelInstance = this.engine.getModelInstance(gameObject2.f591id);
                if (modelInstance == null && (gameObject2.hasComponent(LightComponent.class) || gameObject2.hasComponent(CameraComponent.class) || gameObject2.hasComponent(ParticleComponent.class))) {
                    modelInstance = this.engine.getEditorProxies().get(gameObject2.f591id);
                }
                if (modelInstance != null) {
                    float fCheckRayIntersection = checkRayIntersection(ray, modelInstance);
                    if (fCheckRayIntersection >= 0.0f && fCheckRayIntersection < f) {
                        gameObject = gameObject2;
                        f = fCheckRayIntersection;
                    }
                }
            }
        }
        return gameObject;
    }

    private float checkRayIntersection(Ray ray, ModelInstance instance) {
        instance.calculateBoundingBox(this.tmpBounds);
        this.tmpMatInv.set(instance.transform).inv();
        this.tmpRayLocal.set(ray).mul(this.tmpMatInv);
        if (!Intersector.intersectRayBounds(this.tmpRayLocal, this.tmpBounds, this.tmpIntersectionLocal)) {
            return -1.0f;
        }
        this.tmpIntersectionWorld.set(this.tmpIntersectionLocal).mul(instance.transform);
        return ray.origin.dst(this.tmpIntersectionWorld);
    }

    public void rotate(GameObject go, Quaternion deltaRotation) {
        if (go == null) {
            return;
        }
        TransformComponent transformComponent = go.transform;
        Vector3 vector3Cpy = transformComponent.scale.cpy();
        this.engine.setScale(go.f591id, 1.0f, 1.0f, 1.0f);
        transformComponent.rotation.mulLeft(deltaRotation);
        this.engine.setRotation(go.f591id, transformComponent.rotation);
        this.engine.setScale(go.f591id, vector3Cpy.x, vector3Cpy.y, vector3Cpy.z);
        transformComponent.scale.set(vector3Cpy);
        applyLightAndTransform(go);
    }

    public SceneData getCurrentSceneData() {
        SceneData sceneData = new SceneData();
        for (GameObject gameObject : this.gameObjects.values()) {
            if (!gameObject.isPrefabInstance) {
                GameObject gameObject2 = (GameObject) this.json.fromJson(GameObject.class, this.json.toJson(gameObject));
                PrefabComponent prefabComponent = (PrefabComponent) gameObject2.getComponent(PrefabComponent.class);
                if (prefabComponent != null && prefabComponent.spawnedInstances != null) {
                    gameObject2.childrenIds.removeAll(prefabComponent.spawnedInstances);
                }
                sceneData.gameObjects.add(gameObject);
            }
        }
        sceneData.skyR = this.skyR;
        sceneData.skyG = this.skyG;
        sceneData.skyB = this.skyB;
        sceneData.skyboxPath = this.skyboxPath;
        sceneData.ambientIntensity = this.ambientIntensity;
        sceneData.shadowSize = this.engine.getShadowSize();
        sceneData.shadowResolution = this.engine.getShadowResolution();
        sceneData.renderSettings = this.engine.getSceneSettings();
        return sceneData;
    }

    public void loadSceneFromData(SceneData sceneData) {
        if (sceneData == null) {
            return;
        }
        clearScene_internal();
        setBackgroundLightIntensity(sceneData.ambientIntensity);
        setSkyColor(sceneData.skyR, sceneData.skyG, sceneData.skyB);
        this.engine.setShadowSettings(sceneData.shadowSize > 0.0f ? sceneData.shadowSize : 100.0f, (int) (sceneData.shadowResolution > 0.0f ? sceneData.shadowResolution : 2048.0f), sceneData.useCSM, sceneData.csmSplitFactor >= 1.0f ? sceneData.csmSplitFactor : 4.0f);
        if (sceneData.gameObjects == null) {
            return;
        }
        Array.ArrayIterator<GameObject> it = sceneData.gameObjects.iterator();
        while (it.hasNext()) {
            GameObject next = it.next();
            this.gameObjects.put(next.f591id, next);
            m5457x9eeb7ea3(next);
        }
        findAndSetMainCamera();
        Gdx.f82app.log("SceneManager", "Scene loaded from cache.");
        Gdx.f82app.log("SceneManager", "Applying loaded skybox: " + this.skyboxPath);
        String str = sceneData.skyboxPath;
        this.skyboxPath = str;
        setSkybox(str);
    }

    public void setSkybox(final String texturePath) {
        this.skyboxPath = texturePath;
        Gdx.f82app.postRunnable(new Runnable() { // from class: org.catrobat.catroid.raptor.SceneManager$$ExternalSyntheticLambda26
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5468lambda$setSkybox$2$orgcatrobatcatroidraptorSceneManager(texturePath);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$setSkybox$2$org-catrobat-catroid-raptor-SceneManager, reason: not valid java name */
    /* synthetic */ void m5468lambda$setSkybox$2$orgcatrobatcatroidraptorSceneManager(String str) {
        if (str == null || str.isEmpty()) {
            this.engine.m5477lambda$setSkybox$15$orgcatrobatcatroidraptorThreeDManager(null);
            return;
        }
        File file = ProjectManager.getInstance().getCurrentProject().getFile(str);
        if (file != null && file.exists()) {
            this.engine.m5477lambda$setSkybox$15$orgcatrobatcatroidraptorThreeDManager(file.getAbsolutePath());
        } else {
            Gdx.f82app.error("SceneManager", "Skybox texture not found, clearing: " + str);
            this.engine.m5477lambda$setSkybox$15$orgcatrobatcatroidraptorThreeDManager(null);
        }
    }

    public void removeGameObject(final GameObject go) {
        if (go == null) {
            return;
        }
        if (Gdx.f82app != null) {
            Gdx.f82app.postRunnable(new Runnable() { // from class: org.catrobat.catroid.raptor.SceneManager$$ExternalSyntheticLambda17
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m5458x52cc2e3a(go);
                }
            });
        } else {
            m5458x52cc2e3a(go);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: removeGameObjectInternal, reason: merged with bridge method [inline-methods] */
    public void m5458x52cc2e3a(GameObject go) {
        GameObject gameObjectFindGameObject;
        if (go == null) {
            return;
        }
        Iterator it = new ArrayList(go.childrenIds).iterator();
        while (it.hasNext()) {
            GameObject gameObjectFindGameObject2 = findGameObject((String) it.next());
            if (gameObjectFindGameObject2 != null) {
                m5458x52cc2e3a(gameObjectFindGameObject2);
            }
        }
        if (go.parentId != null && (gameObjectFindGameObject = findGameObject(go.parentId)) != null) {
            gameObjectFindGameObject.childrenIds.remove(go.f591id);
        }
        this.gameObjects.remove(go.f591id);
        this.engine.removeObject(go.f591id);
        if (go.hasComponent(LightComponent.class)) {
            this.engine.removePBRLight(go.f591id);
            this.engine.removeEditorProxy(go.f591id);
        }
        if (go.hasComponent(ParticleComponent.class)) {
            this.engine.removeParticleEffect(go.f591id);
            this.engine.removeEditorProxy(go.f591id);
        }
        if (go.hasComponent(ParticleSystem3DComponent.class)) {
            this.engine.removeParticleEffect3D(go.f591id);
            this.engine.removeEditorProxy(go.f591id);
        }
    }

    public void setRenderComponent(final GameObject go, final String modelFileName) {
        Gdx.f82app.postRunnable(new Runnable() { // from class: org.catrobat.catroid.raptor.SceneManager$$ExternalSyntheticLambda21
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5467xe24192c9(go, modelFileName);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$setRenderComponent$4$org-catrobat-catroid-raptor-SceneManager, reason: not valid java name */
    /* synthetic */ void m5467xe24192c9(GameObject gameObject, String str) {
        RenderComponent renderComponent = (RenderComponent) gameObject.getComponent(RenderComponent.class);
        if (renderComponent == null) {
            renderComponent = new RenderComponent();
            gameObject.addComponent(renderComponent);
        }
        renderComponent.modelFileName = str;
        if (this.engine.objectExists(gameObject.f591id).booleanValue()) {
            this.engine.removeModelOnly(gameObject.f591id);
        }
        m5457x9eeb7ea3(gameObject);
    }

    public GameObject findGameObject(String id2) {
        if (id2 == null) {
            return null;
        }
        return this.gameObjects.get(id2);
    }

    public Map<String, GameObject> getAllGameObjects() {
        return this.gameObjects;
    }

    static /* synthetic */ boolean lambda$setAnimationComponent$5(Component component) {
        return component instanceof AnimationComponent;
    }

    public void setAnimationComponent(GameObject go, AnimationComponent animComponent) {
        if (go == null) {
            return;
        }
        go.components.removeIf(new Predicate() { // from class: org.catrobat.catroid.raptor.SceneManager$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return SceneManager.lambda$setAnimationComponent$5((Component) obj);
            }
        });
        if (animComponent != null) {
            Log.d("SceneManager", "Is added: " + go.addComponent(animComponent));
        }
        playAnimationFromComponent(go);
    }

    public void setPhysicsComponent(final GameObject go, final ThreeDManager.PhysicsState state, final ThreeDManager.PhysicsShape shape, final float mass) {
        Gdx.f82app.postRunnable(new Runnable() { // from class: org.catrobat.catroid.raptor.SceneManager$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5465xa2127c0e(go, state, mass, shape);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$setPhysicsComponent$6$org-catrobat-catroid-raptor-SceneManager, reason: not valid java name */
    /* synthetic */ void m5465xa2127c0e(GameObject gameObject, ThreeDManager.PhysicsState physicsState, float f, ThreeDManager.PhysicsShape physicsShape) {
        PhysicsComponent physicsComponent = (PhysicsComponent) gameObject.getComponent(PhysicsComponent.class);
        if (physicsComponent == null) {
            physicsComponent = new PhysicsComponent();
            gameObject.addComponent(physicsComponent);
        }
        physicsComponent.state = physicsState;
        physicsComponent.mass = f;
        physicsComponent.colliders.clear();
        if (physicsState == ThreeDManager.PhysicsState.STATIC || physicsState == ThreeDManager.PhysicsState.DYNAMIC) {
            ColliderShapeData colliderShapeData = new ColliderShapeData();
            int i = AnonymousClass1.$SwitchMap$org$catrobat$catroid$raptor$ThreeDManager$PhysicsShape[physicsShape.ordinal()];
            if (i == 1) {
                colliderShapeData.type = ColliderShapeData.ShapeType.SPHERE;
            } else if (i == 2) {
                colliderShapeData.type = ColliderShapeData.ShapeType.CAPSULE;
            } else {
                colliderShapeData.type = ColliderShapeData.ShapeType.BOX;
            }
            physicsComponent.colliders.add(colliderShapeData);
        }
        updateWorldTransforms();
        this.engine.setPhysicsState(gameObject.f591id, physicsState, physicsShape, f, gameObject.transform.worldTransform);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/SceneManager$1.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.raptor.SceneManager$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$catrobat$catroid$raptor$LightComponent$LightType;
        static final /* synthetic */ int[] $SwitchMap$org$catrobat$catroid$raptor$ThreeDManager$PhysicsShape;

        static {
            int[] iArr = new int[ThreeDManager.PhysicsShape.values().length];
            $SwitchMap$org$catrobat$catroid$raptor$ThreeDManager$PhysicsShape = iArr;
            try {
                iArr[ThreeDManager.PhysicsShape.SPHERE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$raptor$ThreeDManager$PhysicsShape[ThreeDManager.PhysicsShape.CAPSULE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$raptor$ThreeDManager$PhysicsShape[ThreeDManager.PhysicsShape.BOX.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            int[] iArr2 = new int[LightComponent.LightType.values().length];
            $SwitchMap$org$catrobat$catroid$raptor$LightComponent$LightType = iArr2;
            try {
                iArr2[LightComponent.LightType.SPOT.ordinal()] = 1;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$raptor$LightComponent$LightType[LightComponent.LightType.POINT.ordinal()] = 2;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$raptor$LightComponent$LightType[LightComponent.LightType.DIRECTIONAL.ordinal()] = 3;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    public void setPhysicsComponent(final GameObject go, final PhysicsComponent component) {
        Gdx.f82app.postRunnable(new Runnable() { // from class: org.catrobat.catroid.raptor.SceneManager$$ExternalSyntheticLambda22
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5466xb0dce64c(go, component);
            }
        });
    }

    static /* synthetic */ boolean lambda$setPhysicsComponent$7(Component component) {
        return component instanceof PhysicsComponent;
    }

    /* JADX INFO: renamed from: lambda$setPhysicsComponent$8$org-catrobat-catroid-raptor-SceneManager, reason: not valid java name */
    /* synthetic */ void m5466xb0dce64c(GameObject gameObject, PhysicsComponent physicsComponent) {
        gameObject.components.removeIf(new Predicate() { // from class: org.catrobat.catroid.raptor.SceneManager$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return SceneManager.lambda$setPhysicsComponent$7((Component) obj);
            }
        });
        gameObject.addComponent(physicsComponent);
        if (physicsComponent.colliders != null && !physicsComponent.colliders.isEmpty()) {
            this.engine.setPhysicsStateFromComponent(gameObject.f591id, physicsComponent);
        } else {
            updateWorldTransforms();
            this.engine.setPhysicsState(gameObject.f591id, physicsComponent.state, physicsComponent.shape, physicsComponent.mass, gameObject.transform.worldTransform);
        }
    }

    public void setLightComponent(final GameObject go, final LightComponent lightData) {
        Gdx.f82app.postRunnable(new Runnable() { // from class: org.catrobat.catroid.raptor.SceneManager$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5462xf80523e4(go, lightData);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$setLightComponent$10$org-catrobat-catroid-raptor-SceneManager, reason: not valid java name */
    /* synthetic */ void m5462xf80523e4(GameObject gameObject, LightComponent lightComponent) {
        boolean zHasComponent = gameObject.hasComponent(LightComponent.class);
        gameObject.components.removeIf(new Predicate() { // from class: org.catrobat.catroid.raptor.SceneManager$$ExternalSyntheticLambda28
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return SceneManager.lambda$setLightComponent$9((Component) obj);
            }
        });
        gameObject.addComponent(lightComponent);
        if (!zHasComponent) {
            this.engine.createEditorProxy(gameObject.f591id);
        }
        applyLightAndTransform(gameObject);
    }

    static /* synthetic */ boolean lambda$setLightComponent$9(Component component) {
        return component instanceof LightComponent;
    }

    public void setCameraComponent(final GameObject go, final CameraComponent cameraData) {
        Gdx.f82app.postRunnable(new Runnable() { // from class: org.catrobat.catroid.raptor.SceneManager$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5461x75884c49(go, cameraData);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$setCameraComponent$12$org-catrobat-catroid-raptor-SceneManager, reason: not valid java name */
    /* synthetic */ void m5461x75884c49(GameObject gameObject, CameraComponent cameraComponent) {
        boolean zHasComponent = gameObject.hasComponent(CameraComponent.class);
        gameObject.components.removeIf(new Predicate() { // from class: org.catrobat.catroid.raptor.SceneManager$$ExternalSyntheticLambda20
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return SceneManager.lambda$setCameraComponent$11((Component) obj);
            }
        });
        gameObject.addComponent(cameraComponent);
        if (!zHasComponent) {
            this.engine.createCameraProxy(gameObject.f591id);
        }
        if (cameraComponent.isMainCamera) {
            applyCameraComponentToEngine(gameObject, cameraComponent);
        }
        findAndSetMainCamera();
    }

    static /* synthetic */ boolean lambda$setCameraComponent$11(Component component) {
        return component instanceof CameraComponent;
    }

    private void applyCameraComponentToEngine(GameObject go, CameraComponent camComp) {
        this.engine.setCameraFov(camComp.fieldOfView, camComp.nearPlane, camComp.farPlane);
        this.engine.setCameraPosition(go.transform.position.x, go.transform.position.y, go.transform.position.z);
        this.engine.setCameraRotation(go.transform.rotation);
    }

    public void playAnimation(GameObject go, String animationName, int loops, float speed, float transitionTime) {
        this.engine.playAnimation(go.f591id, animationName, loops, speed, transitionTime);
    }

    public void stopAnimation(GameObject go) {
        this.engine.stopAnimation(go.f591id);
    }

    public void castRay(String rayName, Vector3 from, Vector3 direction) {
        this.engine.castRay(rayName, from, direction);
    }

    public GameObject getRaycastHitObject(String rayName) {
        String raycastHitObjectId = this.engine.getRaycastHitObjectId(rayName);
        if (raycastHitObjectId == null || raycastHitObjectId.isEmpty()) {
            return null;
        }
        return findGameObject(raycastHitObjectId);
    }

    public float getRaycastDistance(String rayName) {
        return this.engine.getRaycastDistance(rayName);
    }

    public void setBackgroundLightIntensity(float ambientIntensity) {
        this.ambientIntensity = ambientIntensity;
        this.engine.setBackgroundLightIntensity(ambientIntensity);
    }

    public void setSkyColor(float r, float g, float b) {
        this.skyR = r;
        this.skyG = g;
        this.skyB = b;
        this.engine.setSkyColor(r, g, b);
    }

    public GameObject findObjectByName(String name2) {
        if (name2 != null && !name2.isEmpty()) {
            for (GameObject gameObject : this.gameObjects.values()) {
                if (name2.equalsIgnoreCase(gameObject.f592name)) {
                    return gameObject;
                }
            }
        }
        return null;
    }

    public List<GameObject> findObjectsByName(String name2) {
        ArrayList arrayList = new ArrayList();
        if (name2 != null && !name2.isEmpty()) {
            for (GameObject gameObject : this.gameObjects.values()) {
                if (name2.equalsIgnoreCase(gameObject.f592name)) {
                    arrayList.add(gameObject);
                }
            }
        }
        return arrayList;
    }

    public void saveScene(FileHandle fileHandle) {
        SceneData currentSceneData = getCurrentSceneData();
        this.json.setOutputType(JsonWriter.OutputType.json);
        this.json.setUsePrototypes(false);
        this.json.setIgnoreUnknownFields(true);
        fileHandle.writeString(this.json.prettyPrint(currentSceneData), false);
        Gdx.f82app.log("SceneManager", "Scene saved to " + fileHandle.path());
    }

    public void loadScene(final FileHandle fileHandle) {
        Gdx.f82app.postRunnable(new Runnable() { // from class: org.catrobat.catroid.raptor.SceneManager$$ExternalSyntheticLambda18
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5454lambda$loadScene$13$orgcatrobatcatroidraptorSceneManager(fileHandle);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$loadScene$13$org-catrobat-catroid-raptor-SceneManager, reason: not valid java name */
    /* synthetic */ void m5454lambda$loadScene$13$orgcatrobatcatroidraptorSceneManager(FileHandle fileHandle) {
        if (fileHandle == null || !fileHandle.exists()) {
            Gdx.f82app.error("SceneManager", "Scene file handle is null or does not exist.");
            return;
        }
        String string = fileHandle.readString();
        this.json.setUsePrototypes(false);
        this.json.setIgnoreUnknownFields(true);
        SceneData sceneData = (SceneData) this.json.fromJson(SceneData.class, string);
        setBackgroundLightIntensity(sceneData.ambientIntensity);
        setSkyColor(sceneData.skyR, sceneData.skyG, sceneData.skyB);
        this.engine.setShadowSettings(sceneData.shadowSize > 0.0f ? sceneData.shadowSize : 100.0f, (int) (sceneData.shadowResolution > 0.0f ? sceneData.shadowResolution : 2048.0f), sceneData.useCSM, sceneData.csmSplitFactor >= 1.0f ? sceneData.csmSplitFactor : 4.0f);
        if (sceneData.gameObjects == null) {
            return;
        }
        Array.ArrayIterator<GameObject> it = sceneData.gameObjects.iterator();
        while (it.hasNext()) {
            GameObject next = it.next();
            this.gameObjects.put(next.f591id, next);
            rebuildGameObject(next);
        }
        findAndSetMainCamera();
        Gdx.f82app.log("SceneManager", "Scene build commands issued.");
        Gdx.f82app.log("SceneManager", "Applying loaded skybox: " + this.skyboxPath);
        String str = sceneData.skyboxPath;
        this.skyboxPath = str;
        setSkybox(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: rebuildGameObject_internal, reason: merged with bridge method [inline-methods] */
    public void m5457x9eeb7ea3(GameObject go) {
        GameObject gameObjectFindGameObject;
        String absolutePath;
        ThreeDManager threeDManager = this.engine;
        if (threeDManager == null || threeDManager.isDisposed()) {
            return;
        }
        Log.d("PhysicsDebug", "============================================================");
        Log.d("PhysicsDebug", "=== Rebuilding GameObject: '" + go.f592name + "' (ID: " + go.f591id + Constant.AFTER_QUTO);
        Log.d("PhysicsDebug", "============================================================");
        RenderComponent renderComponent = (RenderComponent) go.getComponent(RenderComponent.class);
        if (renderComponent != null && renderComponent.modelFileName != null && !renderComponent.modelFileName.isEmpty()) {
            String str = renderComponent.modelFileName;
            if (str.startsWith("primitive:")) {
                if (str.equals("primitive:cylinder")) {
                    this.engine.createCylinder(go.f591id);
                } else if (str.equals("primitive:cube")) {
                    this.engine.createCube(go.f591id);
                } else if (str.equals("primitive:sphere")) {
                    this.engine.createSphere(go.f591id);
                }
            } else {
                if (str.startsWith("assets://")) {
                    absolutePath = str.substring("assets://".length());
                } else {
                    File file = ProjectManager.getInstance().getCurrentProject().getFile(str);
                    if (file == null || !file.exists()) {
                        Gdx.f82app.error("SceneManager", "Rebuild failed: Model file not found: " + str);
                        return;
                    }
                    absolutePath = file.getAbsolutePath();
                }
                if (!this.engine.createObject(go.f591id, absolutePath)) {
                    Gdx.f82app.error("SceneManager", "Rebuild failed: Could not create render object for " + go.f591id);
                    return;
                }
            }
        }
        updateWorldTransforms();
        this.engine.setWorldTransform(go.f591id, go.transform.worldTransform);
        Log.d("PhysicsDebug", "Transform for '" + go.f592name + "':");
        Log.d("PhysicsDebug", "  - Local Position: " + go.transform.position);
        Log.d("PhysicsDebug", "  - Local Rotation: " + go.transform.rotation);
        Log.d("PhysicsDebug", "  - Local Scale:    " + go.transform.scale);
        Log.d("PhysicsDebug", "  - World Transform Matrix:\n" + go.transform.worldTransform);
        if (go.parentId != null && (gameObjectFindGameObject = findGameObject(go.parentId)) != null) {
            Log.d("PhysicsDebug", "  - Parent ('" + gameObjectFindGameObject.f592name + "') World Transform:\n" + gameObjectFindGameObject.transform.worldTransform);
        }
        PhysicsComponent physicsComponent = (PhysicsComponent) go.getComponent(PhysicsComponent.class);
        if (physicsComponent != null) {
            Matrix4 matrix4 = new Matrix4(go.transform.worldTransform);
            if (physicsComponent.state == ThreeDManager.PhysicsState.MESH_STATIC) {
                Log.d("PhysicsDebug", "MESH_STATIC detected. Using rotation/scale only for physics baking.");
                matrix4.setTranslation(0.0f, 0.0f, 0.0f);
            }
            if (physicsComponent.colliders != null && !physicsComponent.colliders.isEmpty()) {
                this.engine.setPhysicsStateFromComponent(go.f591id, physicsComponent, matrix4);
            } else {
                this.engine.setPhysicsState(go.f591id, physicsComponent.state, physicsComponent.shape, physicsComponent.mass, matrix4);
            }
            this.engine.setFriction(go.f591id, physicsComponent.friction);
            this.engine.setRestitution(go.f591id, physicsComponent.restitution);
        }
        if (((LightComponent) go.getComponent(LightComponent.class)) != null) {
            this.engine.createEditorProxy(go.f591id);
            applyLightAndTransform(go);
        }
        CameraComponent cameraComponent = (CameraComponent) go.getComponent(CameraComponent.class);
        if (cameraComponent != null) {
            this.engine.createCameraProxy(go.f591id);
            if (cameraComponent.isMainCamera) {
                Gdx.f82app.log("SceneManager", "MainCamera found: " + go.f592name + ". Applying its transform.");
                applyCameraComponentToEngine(go, cameraComponent);
            }
        }
        playAnimationFromComponent(go);
        MaterialComponent materialComponent = (MaterialComponent) go.getComponent(MaterialComponent.class);
        if (materialComponent != null) {
            this.engine.applyPBRMaterial(go.f591id, materialComponent);
        }
        PostProcessingComponent postProcessingComponent = (PostProcessingComponent) go.getComponent(PostProcessingComponent.class);
        if (postProcessingComponent != null) {
            this.engine.updatePostProcessing(postProcessingComponent);
        }
        ParticleSystem3DComponent particleSystem3DComponent = (ParticleSystem3DComponent) go.getComponent(ParticleSystem3DComponent.class);
        if (particleSystem3DComponent != null) {
            this.engine.createParticleProxy(go.f591id);
            this.engine.updateParticleEffect3D(go.f591id, particleSystem3DComponent, go.transform.worldTransform);
        }
        ParticleComponent particleComponent = (ParticleComponent) go.getComponent(ParticleComponent.class);
        if (particleComponent != null && particleSystem3DComponent == null) {
            this.engine.createParticleProxy(go.f591id);
            this.engine.updateParticleEffect(go.f591id, particleComponent, go.transform.worldTransform);
        }
        KeyframeComponent keyframeComponent = (KeyframeComponent) go.getComponent(KeyframeComponent.class);
        if (keyframeComponent != null && !keyframeComponent.keyframes.isEmpty()) {
            KeyframeData keyframeData = keyframeComponent.keyframes.get(0);
            go.transform.position.set(keyframeData.position);
            go.transform.rotation.set(keyframeData.rotation);
            go.transform.scale.set(keyframeData.scale);
            this.engine.setWorldTransform(go.f591id, go.transform.toMatrix());
            if (keyframeComponent.autoStart && !this.isEditorMode) {
                keyframeComponent.isPlaying = true;
                keyframeComponent.currentTime = 0.0f;
            }
        }
        PrefabComponent prefabComponent = (PrefabComponent) go.getComponent(PrefabComponent.class);
        if (prefabComponent != null) {
            loadPrefabForObject(go, prefabComponent);
        }
    }

    public void repositionObjectAndChildren(GameObject root, Vector3 newWorldPosition) {
        if (root == null) {
            return;
        }
        repositionRecursive(root, new Vector3(newWorldPosition).sub(root.transform.worldTransform.getTranslation(new Vector3())));
        updateWorldTransforms();
    }

    private void applyTransformToEngineRecursive(GameObject go) {
        if (go == null) {
            return;
        }
        this.engine.setWorldTransform(go.f591id, go.transform.worldTransform);
        PhysicsComponent physicsComponent = (PhysicsComponent) go.getComponent(PhysicsComponent.class);
        if (physicsComponent != null && (physicsComponent.state == ThreeDManager.PhysicsState.STATIC || physicsComponent.state == ThreeDManager.PhysicsState.MESH_STATIC)) {
            Log.d("SceneManager", "Re-creating static physics body for '" + go.f592name + "' after move.");
            this.engine.removePhysicsBody(go.f591id);
            Matrix4 matrix4 = new Matrix4(go.transform.worldTransform);
            if (physicsComponent.state == ThreeDManager.PhysicsState.MESH_STATIC) {
                matrix4.setTranslation(0.0f, 0.0f, 0.0f);
            }
            if (physicsComponent.colliders != null && !physicsComponent.colliders.isEmpty()) {
                this.engine.setPhysicsStateFromComponent(go.f591id, physicsComponent, matrix4);
            } else {
                this.engine.setPhysicsState(go.f591id, physicsComponent.state, physicsComponent.shape, physicsComponent.mass, matrix4);
            }
        }
        Iterator<String> it = go.childrenIds.iterator();
        while (it.hasNext()) {
            GameObject gameObjectFindGameObject = findGameObject(it.next());
            if (gameObjectFindGameObject != null) {
                applyTransformToEngineRecursive(gameObjectFindGameObject);
            }
        }
    }

    private void repositionRecursive(GameObject current, Vector3 delta) {
        current.transform.position.add(delta);
        updateTransformRecursive(current, findGameObject(current.parentId));
        this.engine.setWorldTransform(current.f591id, current.transform.worldTransform);
        PhysicsComponent physicsComponent = (PhysicsComponent) current.getComponent(PhysicsComponent.class);
        if (physicsComponent != null && (physicsComponent.state == ThreeDManager.PhysicsState.STATIC || physicsComponent.state == ThreeDManager.PhysicsState.MESH_STATIC)) {
            this.engine.removePhysicsBody(current.f591id);
            Matrix4 matrix4 = new Matrix4(current.transform.worldTransform);
            if (physicsComponent.state == ThreeDManager.PhysicsState.MESH_STATIC) {
                matrix4.setTranslation(0.0f, 0.0f, 0.0f);
            }
            if (physicsComponent.colliders != null && !physicsComponent.colliders.isEmpty()) {
                this.engine.setPhysicsStateFromComponent(current.f591id, physicsComponent, matrix4);
            } else {
                this.engine.setPhysicsState(current.f591id, physicsComponent.state, physicsComponent.shape, physicsComponent.mass, matrix4);
            }
        }
        Iterator<String> it = current.childrenIds.iterator();
        while (it.hasNext()) {
            GameObject gameObjectFindGameObject = findGameObject(it.next());
            if (gameObjectFindGameObject != null) {
                repositionRecursive_Child(gameObjectFindGameObject, current);
            }
        }
    }

    private void repositionRecursive_Child(GameObject current, GameObject newParent) {
        updateTransformRecursive(current, newParent);
        this.engine.setWorldTransform(current.f591id, current.transform.worldTransform);
        PhysicsComponent physicsComponent = (PhysicsComponent) current.getComponent(PhysicsComponent.class);
        if (physicsComponent != null && (physicsComponent.state == ThreeDManager.PhysicsState.STATIC || physicsComponent.state == ThreeDManager.PhysicsState.MESH_STATIC)) {
            this.engine.removePhysicsBody(current.f591id);
            Matrix4 matrix4 = new Matrix4(current.transform.worldTransform);
            if (physicsComponent.state == ThreeDManager.PhysicsState.MESH_STATIC) {
                matrix4.setTranslation(0.0f, 0.0f, 0.0f);
            }
            if (physicsComponent.colliders != null && !physicsComponent.colliders.isEmpty()) {
                this.engine.setPhysicsStateFromComponent(current.f591id, physicsComponent, matrix4);
            } else {
                this.engine.setPhysicsState(current.f591id, physicsComponent.state, physicsComponent.shape, physicsComponent.mass, matrix4);
            }
        }
        Iterator<String> it = current.childrenIds.iterator();
        while (it.hasNext()) {
            GameObject gameObjectFindGameObject = findGameObject(it.next());
            if (gameObjectFindGameObject != null) {
                repositionRecursive_Child(gameObjectFindGameObject, current);
            }
        }
    }

    public void rebuildGameObject(final GameObject go) {
        Gdx.f82app.postRunnable(new Runnable() { // from class: org.catrobat.catroid.raptor.SceneManager$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5457x9eeb7ea3(go);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearScene_internal() {
        this.engine.clearScene();
        this.gameObjects.clear();
        this.cameraAttachments.clear();
        this.activeAttachments.clear();
        this.loadingQueue.clear();
        this.skyboxPath = null;
        this.cachedFogComponent = null;
    }

    public void clearScene() {
        Gdx.f82app.postRunnable(new Runnable() { // from class: org.catrobat.catroid.raptor.SceneManager$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.clearScene_internal();
            }
        });
    }

    public void playAnimationFromComponent(final GameObject go) {
        final AnimationComponent animationComponent = (AnimationComponent) go.getComponent(AnimationComponent.class);
        if (animationComponent == null || animationComponent.animationName == null) {
            Gdx.f82app.postRunnable(new Runnable() { // from class: org.catrobat.catroid.raptor.SceneManager$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m5455x67cc0833(go);
                }
            });
        } else {
            Gdx.f82app.postRunnable(new Runnable() { // from class: org.catrobat.catroid.raptor.SceneManager$$ExternalSyntheticLambda12
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m5456x6f313d52(go, animationComponent);
                }
            });
        }
    }

    /* JADX INFO: renamed from: lambda$playAnimationFromComponent$15$org-catrobat-catroid-raptor-SceneManager, reason: not valid java name */
    /* synthetic */ void m5455x67cc0833(GameObject gameObject) {
        this.engine.stopAnimation(gameObject.f591id);
    }

    /* JADX INFO: renamed from: lambda$playAnimationFromComponent$16$org-catrobat-catroid-raptor-SceneManager, reason: not valid java name */
    /* synthetic */ void m5456x6f313d52(GameObject gameObject, AnimationComponent animationComponent) {
        this.engine.playAnimation(gameObject.f591id, animationComponent.animationName, animationComponent.loops, animationComponent.speed, animationComponent.transitionTime);
    }

    public Array<String> getAnimationNames(GameObject go) {
        return this.engine.getAnimationNames(go.f591id);
    }

    private void applyTransform(GameObject go) {
        if (go == null) {
            return;
        }
        TransformComponent transformComponent = go.transform;
        if (this.engine.objectExists(go.f591id).booleanValue()) {
            this.engine.setPosition(go.f591id, transformComponent.position.x, transformComponent.position.y, transformComponent.position.z);
            this.engine.setRotation(go.f591id, transformComponent.rotation);
            this.engine.setScale(go.f591id, transformComponent.scale.x, transformComponent.scale.y, transformComponent.scale.z);
        }
    }

    public void removeRenderComponent(GameObject go) {
        if (go == null || !go.hasComponent(RenderComponent.class)) {
            return;
        }
        go.components.removeIf(new Predicate() { // from class: org.catrobat.catroid.raptor.SceneManager$$ExternalSyntheticLambda15
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return SceneManager.lambda$removeRenderComponent$17((Component) obj);
            }
        });
        this.engine.removeObject(go.f591id);
    }

    static /* synthetic */ boolean lambda$removeRenderComponent$17(Component component) {
        return component instanceof RenderComponent;
    }

    public void removePhysicsComponent(GameObject go) {
        if (go == null || !go.hasComponent(PhysicsComponent.class)) {
            return;
        }
        go.components.removeIf(new Predicate() { // from class: org.catrobat.catroid.raptor.SceneManager$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return SceneManager.lambda$removePhysicsComponent$18((Component) obj);
            }
        });
        this.engine.removePhysicsBody(go.f591id);
    }

    static /* synthetic */ boolean lambda$removePhysicsComponent$18(Component component) {
        return component instanceof PhysicsComponent;
    }

    static /* synthetic */ boolean lambda$removeLightComponent$19(Component component) {
        return component instanceof LightComponent;
    }

    public void removeLightComponent(GameObject go) {
        if (go == null) {
            return;
        }
        go.components.removeIf(new Predicate() { // from class: org.catrobat.catroid.raptor.SceneManager$$ExternalSyntheticLambda23
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return SceneManager.lambda$removeLightComponent$19((Component) obj);
            }
        });
        this.engine.removePBRLight(go.f591id);
        this.engine.removeEditorProxy(go.f591id);
    }

    private void applyLightAndTransform(GameObject go) {
        if (go == null) {
            return;
        }
        if (go.hasComponent(LightComponent.class)) {
            this.engine.updateEditorProxyPosition(go.f591id, go.transform.worldTransform.getTranslation(new Vector3()));
        }
        LightComponent lightComponent = (LightComponent) go.getComponent(LightComponent.class);
        if (lightComponent == null) {
            return;
        }
        if (!isObjectActiveInHierarchy(go)) {
            this.engine.removePBRLight(go.f591id);
            return;
        }
        go.transform.worldTransform.getTranslation(this.tmpPos);
        go.transform.worldTransform.getRotation(this.tmpRot, true);
        float f = lightComponent.color.r;
        float f2 = lightComponent.color.g;
        float f3 = lightComponent.color.b;
        int i = AnonymousClass1.$SwitchMap$org$catrobat$catroid$raptor$LightComponent$LightType[lightComponent.type.ordinal()];
        if (i == 1) {
            Vector3 vector3 = this.tmpScale;
            vector3.set(0.0f, 0.0f, -1.0f);
            this.tmpRot.transform(vector3);
            this.engine.setSpotLight(go.f591id, this.tmpPos.x, this.tmpPos.y, this.tmpPos.z, vector3.x, vector3.y, vector3.z, f, f2, f3, lightComponent.intensity, lightComponent.cutoffAngle, lightComponent.exponent, lightComponent.range);
            return;
        }
        if (i == 2) {
            this.engine.setPointLight(go.f591id, this.tmpPos.x, this.tmpPos.y, this.tmpPos.z, f, f2, f3, lightComponent.intensity, lightComponent.range);
            return;
        }
        if (i != 3) {
            return;
        }
        this.engine.removePBRLight(go.f591id);
        Vector3 vector4 = this.tmpScale;
        vector4.set(0.0f, 0.0f, -1.0f);
        this.tmpRot.transform(vector4);
        this.engine.setRealisticSunLight(vector4.x, vector4.y, vector4.z, lightComponent.intensity);
        this.engine.setSunLightColor(f, f2, f3);
        lightComponent.direction.set(vector4);
    }

    public void setRestitution(String id2, float restitution) {
        this.engine.setRestitution(id2, restitution);
    }

    public void setFriction(String id2, float friction) {
        this.engine.setFriction(id2, friction);
    }

    public Json getJson() {
        return this.json;
    }

    public GameObject createPrimitive(String type) {
        GameObject gameObjectCreateGameObject = createGameObject(type.substring(0, 1).toUpperCase() + type.substring(1));
        RenderComponent renderComponent = new RenderComponent();
        renderComponent.modelFileName = "primitive:" + type;
        gameObjectCreateGameObject.addComponent(renderComponent);
        MaterialComponent materialComponent = new MaterialComponent();
        gameObjectCreateGameObject.addComponent(materialComponent);
        gameObjectCreateGameObject.transform.scale = new Vector3(0.1f, 0.1f, 0.1f);
        applyTransform(gameObjectCreateGameObject);
        this.engine.applyPBRMaterial(gameObjectCreateGameObject.f591id, materialComponent);
        rebuildGameObject(gameObjectCreateGameObject);
        return gameObjectCreateGameObject;
    }

    public boolean isLoading() {
        return !this.loadingQueue.isEmpty();
    }

    public void setMaterialComponent(final GameObject go, final MaterialComponent component) {
        Gdx.f82app.postRunnable(new Runnable() { // from class: org.catrobat.catroid.raptor.SceneManager$$ExternalSyntheticLambda13
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5463x99c25e69(go, component);
            }
        });
    }

    static /* synthetic */ boolean lambda$setMaterialComponent$20(Component component) {
        return component instanceof MaterialComponent;
    }

    /* JADX INFO: renamed from: lambda$setMaterialComponent$21$org-catrobat-catroid-raptor-SceneManager, reason: not valid java name */
    /* synthetic */ void m5463x99c25e69(GameObject gameObject, MaterialComponent materialComponent) {
        gameObject.components.removeIf(new Predicate() { // from class: org.catrobat.catroid.raptor.SceneManager$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return SceneManager.lambda$setMaterialComponent$20((Component) obj);
            }
        });
        gameObject.addComponent(materialComponent);
        this.engine.applyPBRMaterial(gameObject.f591id, materialComponent);
    }

    public boolean isObjectActiveInHierarchy(GameObject go) {
        if (go == null || !go.isActive) {
            return false;
        }
        if (go.parentId != null) {
            return isObjectActiveInHierarchy(findGameObject(go.parentId));
        }
        return true;
    }

    public void setObjectActive(GameObject go, boolean active) {
        if (go == null || go.isActive == active) {
            return;
        }
        go.isActive = active;
        updateVisibilityRecursive(go);
        updateLightStateRecursive(go);
    }

    private void updateLightStateRecursive(GameObject go) {
        if (go == null) {
            return;
        }
        if (go.hasComponent(LightComponent.class)) {
            applyLightAndTransform(go);
        }
        Iterator<String> it = go.childrenIds.iterator();
        while (it.hasNext()) {
            updateLightStateRecursive(findGameObject(it.next()));
        }
    }

    private void updateVisibilityRecursive(GameObject go) {
        if (go == null) {
            return;
        }
        this.engine.setObjectVisibility(go.f591id, isObjectActiveInHierarchy(go));
        Iterator<String> it = go.childrenIds.iterator();
        while (it.hasNext()) {
            updateVisibilityRecursive(findGameObject(it.next()));
        }
    }

    public void setParent(final GameObject child, final GameObject parent) {
        Gdx.f82app.postRunnable(new Runnable() { // from class: org.catrobat.catroid.raptor.SceneManager$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5464lambda$setParent$22$orgcatrobatcatroidraptorSceneManager(child, parent);
            }
        });
    }

    public void removeParent(final GameObject child) {
        Gdx.f82app.postRunnable(new Runnable() { // from class: org.catrobat.catroid.raptor.SceneManager$$ExternalSyntheticLambda27
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5460lambda$removeParent$23$orgcatrobatcatroidraptorSceneManager(child);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$removeParent$23$org-catrobat-catroid-raptor-SceneManager, reason: not valid java name */
    /* synthetic */ void m5460lambda$removeParent$23$orgcatrobatcatroidraptorSceneManager(GameObject gameObject) {
        m5464lambda$setParent$22$orgcatrobatcatroidraptorSceneManager(gameObject, null);
    }

    /* JADX INFO: renamed from: setParentInternal, reason: merged with bridge method [inline-methods] */
    public void m5464lambda$setParent$22$orgcatrobatcatroidraptorSceneManager(GameObject child, GameObject parent) {
        GameObject gameObjectFindGameObject;
        if (child == null || child == parent) {
            return;
        }
        if (parent != null && isDescendant(parent, child)) {
            Log.e("SceneManager", "Cannot set parent: would create a cycle in hierarchy.");
            return;
        }
        updateWorldTransforms();
        Matrix4 matrix4 = new Matrix4(child.transform.worldTransform);
        Vector3 vector3 = new Vector3();
        Quaternion quaternion = new Quaternion();
        Vector3 vector4 = new Vector3();
        child.transform.worldTransform.getTranslation(vector3);
        child.transform.worldTransform.getRotation(quaternion, true);
        child.transform.worldTransform.getScale(vector4);
        if (child.parentId != null && (gameObjectFindGameObject = findGameObject(child.parentId)) != null) {
            gameObjectFindGameObject.childrenIds.remove(child.f591id);
        }
        child.parentId = null;
        if (parent != null) {
            child.parentId = parent.f591id;
            if (!parent.childrenIds.contains(child.f591id)) {
                parent.childrenIds.add(child.f591id);
            }
            Matrix4 matrix5 = parent.transform.worldTransform;
            if (Math.abs(matrix5.det()) < 1.0E-6f) {
                Log.e("SceneManager", "Cannot attach to a parent with zero scale.");
                return;
            }
            Matrix4 matrix4Mul = new Matrix4(matrix5).inv().mul(matrix4);
            matrix4Mul.getTranslation(child.transform.position);
            matrix4Mul.getRotation(child.transform.rotation, true);
            matrix4Mul.getScale(child.transform.scale);
        } else {
            child.transform.position.set(vector3);
            child.transform.rotation.set(quaternion);
            child.transform.scale.set(vector4);
        }
        updateWorldTransforms();
    }

    private boolean isDescendant(GameObject potentialDescendant, GameObject ancestor) {
        if (potentialDescendant.parentId == null) {
            return false;
        }
        if (potentialDescendant.parentId.equals(ancestor.f591id)) {
            return true;
        }
        GameObject gameObjectFindGameObject = findGameObject(potentialDescendant.parentId);
        if (gameObjectFindGameObject == null) {
            return false;
        }
        return isDescendant(gameObjectFindGameObject, ancestor);
    }

    public GameObject cloneGameObject(GameObject original, String newName) {
        if (newName == null) {
            newName = generateUniqueName(original.f592name);
        }
        if (original == null || newName.isEmpty() || this.gameObjects.containsKey(newName)) {
            return null;
        }
        final ArrayList arrayList = new ArrayList();
        GameObject gameObjectCloneRecursiveInternal = cloneRecursiveInternal(original, arrayList, new HashMap(), newName);
        for (GameObject gameObject : arrayList) {
            this.gameObjects.put(gameObject.f591id, gameObject);
        }
        updateWorldTransforms();
        Gdx.f82app.postRunnable(new Runnable() { // from class: org.catrobat.catroid.raptor.SceneManager$$ExternalSyntheticLambda24
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5451x3eef01e6(arrayList);
            }
        });
        return gameObjectCloneRecursiveInternal;
    }

    /* JADX INFO: renamed from: lambda$cloneGameObject$24$org-catrobat-catroid-raptor-SceneManager, reason: not valid java name */
    /* synthetic */ void m5451x3eef01e6(List list) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            GameObject gameObject = (GameObject) it.next();
            m5457x9eeb7ea3(gameObject);
            MaterialComponent materialComponent = (MaterialComponent) gameObject.getComponent(MaterialComponent.class);
            if (materialComponent != null) {
                this.engine.applyPBRMaterial(gameObject.f591id, materialComponent);
            }
        }
    }

    private GameObject cloneRecursiveInternal(GameObject original, List<GameObject> list, Map<String, String> idMap, String newName) {
        GameObject gameObject = (GameObject) this.json.fromJson(GameObject.class, this.json.toJson(original));
        if (newName == null) {
            newName = generateUniqueName(original.f592name);
        }
        gameObject.f591id = newName;
        gameObject.f592name = gameObject.f591id;
        gameObject.parentId = null;
        list.add(gameObject);
        idMap.put(original.f591id, gameObject.f591id);
        ArrayList arrayList = new ArrayList(gameObject.childrenIds);
        gameObject.childrenIds.clear();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            GameObject gameObjectFindGameObject = findGameObject((String) it.next());
            if (gameObjectFindGameObject != null) {
                GameObject gameObjectCloneRecursiveInternal = cloneRecursiveInternal(gameObjectFindGameObject, list, idMap, null);
                gameObjectCloneRecursiveInternal.parentId = gameObject.f591id;
                gameObject.childrenIds.add(gameObjectCloneRecursiveInternal.f591id);
            }
        }
        return gameObject;
    }

    public void loadAndAddScene(final FileHandle fileHandle) {
        Gdx.f82app.postRunnable(new Runnable() { // from class: org.catrobat.catroid.raptor.SceneManager$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5452x448e8d93(fileHandle);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$loadAndAddScene$25$org-catrobat-catroid-raptor-SceneManager, reason: not valid java name */
    /* synthetic */ void m5452x448e8d93(FileHandle fileHandle) {
        if (fileHandle == null || !fileHandle.exists()) {
            Gdx.f82app.error("SceneManager", "Additive scene file not found.");
            return;
        }
        String string = fileHandle.readString();
        this.json.setUsePrototypes(false);
        this.json.setIgnoreUnknownFields(true);
        SceneData sceneData = (SceneData) this.json.fromJson(SceneData.class, string);
        if (sceneData == null || sceneData.gameObjects == null) {
            return;
        }
        ArrayList<GameObject> arrayList = new ArrayList();
        HashMap map = new HashMap();
        Array.ArrayIterator<GameObject> it = sceneData.gameObjects.iterator();
        while (it.hasNext()) {
            GameObject next = it.next();
            String str = next.f591id;
            String strGenerateUniqueName = generateUniqueName(str);
            next.f591id = strGenerateUniqueName;
            next.f592name = strGenerateUniqueName;
            arrayList.add(next);
            map.put(str, strGenerateUniqueName);
        }
        for (GameObject gameObject : arrayList) {
            if (gameObject.parentId != null) {
                gameObject.parentId = (String) map.get(gameObject.parentId);
            }
            ArrayList arrayList2 = new ArrayList();
            Iterator<String> it2 = gameObject.childrenIds.iterator();
            while (it2.hasNext()) {
                String str2 = (String) map.get(it2.next());
                if (str2 != null) {
                    arrayList2.add(str2);
                }
            }
            gameObject.childrenIds = arrayList2;
        }
        for (GameObject gameObject2 : arrayList) {
            this.gameObjects.put(gameObject2.f591id, gameObject2);
        }
        Iterator it3 = arrayList.iterator();
        while (it3.hasNext()) {
            m5457x9eeb7ea3((GameObject) it3.next());
        }
    }

    public void bakeByPrefix(String prefix, String newId) {
        this.engine.bakeObjectsByPrefix(prefix, newId);
        ArrayList arrayList = new ArrayList();
        for (String str : this.gameObjects.keySet()) {
            if (str.startsWith(prefix) && !str.equals("player") && !str.equals(newId)) {
                arrayList.add(str);
            }
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            this.gameObjects.remove((String) it.next());
        }
        if (!this.gameObjects.containsKey(newId)) {
            this.gameObjects.put(newId, new GameObject(newId));
        }
        updateWorldTransforms();
    }

    private void syncCameraAttachmentsToGameObjects() {
        ModelInstance modelInstance;
        for (CameraAttachment cameraAttachment : this.cameraAttachments) {
            GameObject gameObjectFindGameObject = findGameObject(cameraAttachment.childId);
            if (gameObjectFindGameObject != null && (modelInstance = this.engine.getModelInstance(cameraAttachment.childId)) != null) {
                gameObjectFindGameObject.transform.worldTransform.set(modelInstance.transform);
                if (gameObjectFindGameObject.parentId == null) {
                    gameObjectFindGameObject.transform.worldTransform.getTranslation(gameObjectFindGameObject.transform.position);
                    gameObjectFindGameObject.transform.worldTransform.getRotation(gameObjectFindGameObject.transform.rotation, true);
                }
            }
        }
    }

    public void promoteLightToGameObject(String lightId) {
        if (this.gameObjects.containsKey(lightId)) {
            return;
        }
        LightComponent lightComponent = new LightComponent();
        Vector3 vector3 = new Vector3();
        Quaternion quaternion = new Quaternion();
        if (this.engine.getPointLights().containsKey(lightId)) {
            lightComponent.type = LightComponent.LightType.POINT;
            PointLightEx pointLightEx = this.engine.getPointLights().get(lightId);
            if (pointLightEx == null) {
                return;
            }
            lightComponent.color.set(pointLightEx.color);
            lightComponent.intensity = pointLightEx.intensity;
            lightComponent.range = pointLightEx.range != null ? pointLightEx.range.floatValue() : 0.0f;
            vector3.set(pointLightEx.position);
        } else {
            if (!this.engine.getSpotLights().containsKey(lightId)) {
                return;
            }
            lightComponent.type = LightComponent.LightType.SPOT;
            SpotLightEx spotLightEx = this.engine.getSpotLights().get(lightId);
            if (spotLightEx == null) {
                return;
            }
            lightComponent.color.set(spotLightEx.color);
            lightComponent.intensity = spotLightEx.intensity;
            lightComponent.range = spotLightEx.range != null ? spotLightEx.range.floatValue() : 0.0f;
            lightComponent.cutoffAngle = (spotLightEx.cutoffAngle + 1000.0f) * 0.18f;
            lightComponent.exponent = spotLightEx.exponent / 1000.0f;
            vector3.set(spotLightEx.position);
            Vector3 vector4 = new Vector3(0.0f, 0.0f, -1.0f);
            Vector3 vector3Nor = spotLightEx.direction.cpy().nor();
            if (vector4.isCollinearOpposite(vector3Nor, 0.01f)) {
                quaternion.setEulerAngles(0.0f, 180.0f, 0.0f);
            } else if (vector4.isCollinear(vector3Nor, 0.01f)) {
                quaternion.idt();
            } else {
                quaternion.setFromCross(vector4, vector3Nor);
            }
        }
        GameObject gameObject = new GameObject(lightId);
        gameObject.transform.position.set(vector3);
        gameObject.transform.rotation.set(quaternion);
        gameObject.transform.scale.set(1.0f, 1.0f, 1.0f);
        gameObject.transform.worldTransform.set(vector3, quaternion, gameObject.transform.scale);
        gameObject.addComponent(lightComponent);
        RenderComponent renderComponent = new RenderComponent();
        renderComponent.modelFileName = "primitive:sphere";
        gameObject.addComponent(renderComponent);
        gameObject.transform.scale.set(0.01f, 0.01f, 0.01f);
        MaterialComponent materialComponent = new MaterialComponent();
        materialComponent.baseColor = new Color(0.0f, 0.0f, 0.0f, 0.0f);
        materialComponent.metallic = 0.0f;
        materialComponent.roughness = 1.0f;
        gameObject.addComponent(materialComponent);
        this.gameObjects.put(lightId, gameObject);
        m5457x9eeb7ea3(gameObject);
    }

    public void applyShaderToImage(String filename, String vertexCode, String fragmentCode) {
        this.engine.applyShaderToImage(filename, vertexCode, fragmentCode);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/SceneManager$CameraAttachment.class
      classes14.dex
     */
    static class CameraAttachment {
        String childId;
        Matrix4 localOffsetMat;

        public CameraAttachment(String id2, Matrix4 offset) {
            Matrix4 matrix4 = new Matrix4();
            this.localOffsetMat = matrix4;
            this.childId = id2;
            matrix4.set(offset);
        }
    }

    public void attachObjectToCamera(String objectId) {
        if (this.gameObjects.containsKey(objectId)) {
            detachObjectFromCamera(objectId);
            GameObject gameObjectFindGameObject = findGameObject(objectId);
            Camera camera = this.engine.getCamera();
            if (gameObjectFindGameObject == null || camera == null) {
                return;
            }
            this.cameraAttachments.add(new CameraAttachment(objectId, new Matrix4(new Matrix4(camera.view).inv()).inv().mul(gameObjectFindGameObject.transform.worldTransform)));
            return;
        }
        this.engine.attachObjectToCamera(objectId);
    }

    public void detachObjectFromCamera(final String objectId) {
        this.cameraAttachments.removeIf(new Predicate() { // from class: org.catrobat.catroid.raptor.SceneManager$$ExternalSyntheticLambda25
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((SceneManager.CameraAttachment) obj).childId.equals(objectId);
            }
        });
        this.engine.detachObjectFromCamera(objectId);
    }

    public void setCameraTracking(String targetId, int mode, float px, float py, float pz, float yaw, float pitch, float roll) {
        this.engine.setCameraTracking(targetId, mode, px, py, pz, yaw, pitch, roll);
    }

    public void removeObjectsByPrefix(final String prefix) {
        if (prefix == null || prefix.isEmpty()) {
            return;
        }
        Gdx.f82app.postRunnable(new Runnable() { // from class: org.catrobat.catroid.raptor.SceneManager$$ExternalSyntheticLambda16
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5459x8b9f594e(prefix);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$removeObjectsByPrefix$27$org-catrobat-catroid-raptor-SceneManager, reason: not valid java name */
    /* synthetic */ void m5459x8b9f594e(String str) {
        ArrayList arrayList = new ArrayList();
        for (Map.Entry<String, GameObject> entry : this.gameObjects.entrySet()) {
            if (entry.getKey().startsWith(str)) {
                arrayList.add(entry.getValue());
            }
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            removeGameObject((GameObject) it.next());
        }
        this.engine.removeObjectsByPrefix(str);
    }
}
