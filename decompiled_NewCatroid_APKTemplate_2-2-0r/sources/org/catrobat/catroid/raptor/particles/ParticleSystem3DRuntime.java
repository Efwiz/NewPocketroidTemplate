package org.catrobat.catroid.raptor.particles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.mgsx.gltf.loaders.glb.GLBLoader;
import net.mgsx.gltf.loaders.gltf.GLTFLoader;
import net.mgsx.gltf.scene3d.scene.SceneAsset;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.raptor.ParticleSystem3DComponent;
import org.catrobat.catroid.raptor.ThreeDManager;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/particles/ParticleSystem3DRuntime.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ParticleSystem3DRuntime implements Disposable {
    private static final int FLOATS_PER_VERTEX = 9;
    private static final int INDICES_PER_PARTICLE = 6;
    private static final int MAX_TRAIL_INDICES = 24576;
    private static final int MAX_TRAIL_VERTS = 8192;
    private static final int TRAIL_FLOATS_PER_VERT = 9;
    private static final int VERTICES_PER_PARTICLE = 4;
    private Mesh billboardMesh;
    private Camera camera;
    private boolean[] collidedThisFrame;
    private float[] collisionNormalX;
    private float[] collisionNormalY;
    private float[] collisionNormalZ;
    private float[] colorA;
    private float[] colorB;
    private float[] colorG;
    private float[] colorR;
    private ParticleSystem3DComponent config;
    private ThreeDManager engineRef;
    private boolean[] hasTrail;
    private short[] indices;
    private boolean isAdditive;
    private boolean isPlaying;
    private float[] life;
    private float[] maxLife;
    private int maxParticles;
    private MeshParticleBatcher meshBatcher;
    private String ownerObjectId;
    private float[] posX;
    private float[] posY;
    private float[] posZ;
    private float[] prevPosX;
    private float[] prevPosY;
    private float[] prevPosZ;
    private float[] randomSeed;
    private float[] rotVelocity;
    private float[] rotation;
    private float[] rotationX;
    private float[] rotationY;
    private float[] rotationZ;
    private ShaderProgram shader;
    private float[] size;
    private float[] sortDistances;
    private int[] sortedIndices;
    private float[] startSizeVal;
    private SubEmitterCallback subEmitterCallback;
    private Texture texture;
    private List<List<TrailPoint>> trailData;
    private short[] trailIndices;
    private Mesh trailMesh;
    private float[] trailVertices;
    private float[] velX;
    private float[] velY;
    private float[] velZ;
    private float[] vertices;
    private boolean glInitialized = false;
    private boolean meshBatcherDirty = false;
    private boolean meshBatcherReady = false;
    private int aliveCount = 0;
    private Matrix4 emitterTransform = new Matrix4();
    private Matrix4 prevEmitterTransform = new Matrix4();
    private Vector3 emitterWorldPos = new Vector3();
    private Vector3 prevEmitterWorldPos = new Vector3();
    private float emissionAccumulator = 0.0f;
    private float distanceAccumulator = 0.0f;
    private float elapsedTime = 0.0f;
    private boolean hasPrewarmed = false;
    private Random rng = new Random();
    private final Vector3 tmpV1 = new Vector3();
    private final Vector3 tmpV2 = new Vector3();
    private final Vector3 tmpV3 = new Vector3();
    private final Vector3 tmpV4 = new Vector3();
    private final Quaternion tmpQ = new Quaternion();
    private final Matrix4 tmpMat = new Matrix4();
    private final Color tmpColor = new Color();

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/particles/ParticleSystem3DRuntime$SubEmitterCallback.class
      classes14.dex
     */
    public interface SubEmitterCallback {
        void onSubEmit(String subEmitterObjectId, Vector3 position, Vector3 velocity);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/particles/ParticleSystem3DRuntime$TrailPoint.class
      classes14.dex
     */
    private static class TrailPoint {
        float a;
        float age;
        float b;
        float g;
        float r;
        float width;
        float x;
        float y;
        float z;

        private TrailPoint() {
        }
    }

    public ParticleSystem3DRuntime(ParticleSystem3DComponent config, String ownerObjectId, Camera camera, ThreeDManager engine) {
        this.isPlaying = false;
        this.config = config;
        this.ownerObjectId = ownerObjectId;
        this.camera = camera;
        this.engineRef = engine;
        int i = config.maxParticles;
        this.maxParticles = i;
        allocateArrays(i);
        this.isPlaying = config.isPlaying;
    }

    private void ensureGLInitialized() {
        if (this.glInitialized) {
            return;
        }
        buildMesh(this.maxParticles);
        buildShader();
        if (this.config.renderer.renderMode == ParticleSystem3DComponent.RenderMode.MESH) {
            initMeshBatcher();
        }
        this.glInitialized = true;
    }

    private void initMeshBatcher() {
        MeshParticleBatcher meshParticleBatcher = this.meshBatcher;
        if (meshParticleBatcher != null) {
            meshParticleBatcher.dispose();
        }
        this.meshBatcher = new MeshParticleBatcher(this.maxParticles);
        if (this.config.renderer.meshType == ParticleSystem3DComponent.MeshType.CUSTOM && this.config.renderer.meshPath != null && !this.config.renderer.meshPath.isEmpty()) {
            try {
                loadCustomMeshForBatcher(this.config.renderer.meshPath);
                return;
            } catch (Exception e) {
                Gdx.f82app.error("ParticleRuntime", "Failed to load custom mesh: " + this.config.renderer.meshPath, e);
                this.meshBatcher.loadPrimitive(ParticleSystem3DComponent.MeshType.CUBE);
                return;
            }
        }
        this.meshBatcher.loadPrimitive(this.config.renderer.meshType);
    }

    private void loadCustomMeshForBatcher(String meshPath) {
        File file = ProjectManager.getInstance().getCurrentProject().getFile(meshPath);
        if (file == null || !file.exists()) {
            Gdx.f82app.error("ParticleRuntime", "Mesh file not found: " + meshPath);
            this.meshBatcher.loadPrimitive(ParticleSystem3DComponent.MeshType.CUBE);
            return;
        }
        FileHandle fileHandleAbsolute = Gdx.files.absolute(file.getAbsolutePath());
        String lowerCase = meshPath.toLowerCase();
        Model modelLoadModel = null;
        try {
            if (lowerCase.endsWith(".glb")) {
                SceneAsset sceneAssetLoad = new GLBLoader().load(fileHandleAbsolute, true);
                if (sceneAssetLoad != null && sceneAssetLoad.scene != null) {
                    modelLoadModel = sceneAssetLoad.scene.model;
                }
            } else if (lowerCase.endsWith(".gltf")) {
                SceneAsset sceneAssetLoad2 = new GLTFLoader().load(fileHandleAbsolute, true);
                if (sceneAssetLoad2 != null && sceneAssetLoad2.scene != null) {
                    modelLoadModel = sceneAssetLoad2.scene.model;
                }
            } else if (lowerCase.endsWith(".obj")) {
                modelLoadModel = new ObjLoader().loadModel(fileHandleAbsolute, true);
            }
        } catch (Exception e) {
            Gdx.f82app.error("ParticleRuntime", "Error loading model: " + meshPath, e);
        }
        if (modelLoadModel == null) {
            Gdx.f82app.error("ParticleRuntime", "Model was null, fallback to cube");
            this.meshBatcher.loadPrimitive(ParticleSystem3DComponent.MeshType.CUBE);
        } else {
            this.meshBatcher.loadFromModel(modelLoadModel);
            Gdx.f82app.log("ParticleRuntime", "Custom particle mesh loaded: " + meshPath);
        }
    }

    public void setMeshModel(Model model) {
        if (this.meshBatcher == null) {
            this.meshBatcher = new MeshParticleBatcher(this.maxParticles);
        }
        this.meshBatcher.loadFromModel(model);
    }

    private void allocateArrays(int max) {
        this.posX = new float[max];
        this.posY = new float[max];
        this.posZ = new float[max];
        this.velX = new float[max];
        this.velY = new float[max];
        this.velZ = new float[max];
        this.life = new float[max];
        this.maxLife = new float[max];
        this.randomSeed = new float[max];
        this.size = new float[max];
        this.startSizeVal = new float[max];
        this.rotation = new float[max];
        this.rotVelocity = new float[max];
        this.colorR = new float[max];
        this.colorG = new float[max];
        this.colorB = new float[max];
        this.colorA = new float[max];
        this.prevPosX = new float[max];
        this.prevPosY = new float[max];
        this.prevPosZ = new float[max];
        this.collidedThisFrame = new boolean[max];
        this.collisionNormalX = new float[max];
        this.collisionNormalY = new float[max];
        this.collisionNormalZ = new float[max];
        this.hasTrail = new boolean[max];
        this.rotationX = new float[max];
        this.rotationY = new float[max];
        this.rotationZ = new float[max];
        this.trailData = new ArrayList(max);
        for (int i = 0; i < max; i++) {
            this.trailData.add(new ArrayList());
        }
    }

    public void setTransform(Matrix4 transform) {
        this.prevEmitterTransform.set(this.emitterTransform);
        this.emitterTransform.set(transform);
        this.prevEmitterWorldPos.set(this.emitterWorldPos);
        this.emitterTransform.getTranslation(this.emitterWorldPos);
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public void setSubEmitterCallback(SubEmitterCallback cb) {
        this.subEmitterCallback = cb;
    }

    public void play() {
        this.isPlaying = true;
        this.elapsedTime = 0.0f;
        this.emissionAccumulator = 0.0f;
        this.hasPrewarmed = false;
    }

    public void stop() {
        this.isPlaying = false;
    }

    public void clear() {
        this.aliveCount = 0;
    }

    public int getAliveCount() {
        return this.aliveCount;
    }

    public void update(float delta) {
        if (this.isPlaying || this.aliveCount != 0) {
            if (this.config.prewarm && this.config.looping && !this.hasPrewarmed) {
                this.hasPrewarmed = true;
                float f = this.config.duration;
                for (float f2 = 0.0f; f2 < f; f2 += 0.016666668f) {
                    updateInternal(0.016666668f);
                }
                return;
            }
            updateInternal(delta);
        }
    }

    private void updateInternal(float delta) {
        this.elapsedTime += delta;
        boolean z = this.isPlaying;
        if (!this.config.looping && this.elapsedTime > this.config.duration + this.config.startDelay.evaluate(0.0f)) {
            z = false;
        }
        if (this.config.looping && this.elapsedTime > this.config.duration) {
            this.elapsedTime -= this.config.duration;
        }
        if (z && this.config.emission.enabled) {
            doEmission(delta);
        }
        for (int i = this.aliveCount - 1; i >= 0; i--) {
            float[] fArr = this.life;
            float f = fArr[i] - delta;
            fArr[i] = f;
            if (f <= 0.0f) {
                onParticleDeath(i);
                killParticle(i);
            } else {
                float f2 = 1.0f - (f / this.maxLife[i]);
                float f3 = this.randomSeed[i];
                this.prevPosX[i] = this.posX[i];
                this.prevPosY[i] = this.posY[i];
                this.prevPosZ[i] = this.posZ[i];
                if (this.config.velocityOverLifetime.enabled) {
                    ParticleSystem3DComponent.VelocityOverLifetimeModule velocityOverLifetimeModule = this.config.velocityOverLifetime;
                    float[] fArr2 = this.velX;
                    fArr2[i] = fArr2[i] + (velocityOverLifetimeModule.x.evaluate(f2, f3) * delta);
                    float[] fArr3 = this.velY;
                    fArr3[i] = fArr3[i] + (velocityOverLifetimeModule.y.evaluate(f2, f3) * delta);
                    float[] fArr4 = this.velZ;
                    fArr4[i] = fArr4[i] + (velocityOverLifetimeModule.z.evaluate(f2, f3) * delta);
                    float fEvaluate = velocityOverLifetimeModule.orbitalY.evaluate(f2, f3);
                    if (fEvaluate != 0.0f) {
                        applyOrbitalVelocity(i, 0.0f, fEvaluate, 0.0f, delta);
                    }
                    float fEvaluate2 = velocityOverLifetimeModule.orbitalX.evaluate(f2, f3);
                    if (fEvaluate2 != 0.0f) {
                        applyOrbitalVelocity(i, fEvaluate2, 0.0f, 0.0f, delta);
                    }
                    float fEvaluate3 = velocityOverLifetimeModule.radial.evaluate(f2, f3);
                    if (fEvaluate3 != 0.0f) {
                        this.tmpV1.set(this.posX[i], this.posY[i], this.posZ[i]).sub(this.emitterWorldPos);
                        if (this.tmpV1.len() > 0.001f) {
                            this.tmpV1.nor().scl(fEvaluate3 * delta);
                            float[] fArr5 = this.velX;
                            fArr5[i] = fArr5[i] + this.tmpV1.x;
                            float[] fArr6 = this.velY;
                            fArr6[i] = fArr6[i] + this.tmpV1.y;
                            float[] fArr7 = this.velZ;
                            fArr7[i] = fArr7[i] + this.tmpV1.z;
                        }
                    }
                    float fEvaluate4 = velocityOverLifetimeModule.speedModifier.evaluate(f2, f3);
                    if (fEvaluate4 != 1.0f) {
                        float[] fArr8 = this.velX;
                        fArr8[i] = fArr8[i] * fEvaluate4;
                        float[] fArr9 = this.velY;
                        fArr9[i] = fArr9[i] * fEvaluate4;
                        float[] fArr10 = this.velZ;
                        fArr10[i] = fArr10[i] * fEvaluate4;
                    }
                }
                if (this.config.forceOverLifetime.enabled) {
                    ParticleSystem3DComponent.ForceOverLifetimeModule forceOverLifetimeModule = this.config.forceOverLifetime;
                    float[] fArr11 = this.velX;
                    fArr11[i] = fArr11[i] + (forceOverLifetimeModule.x.evaluate(f2, f3) * delta);
                    float[] fArr12 = this.velY;
                    fArr12[i] = fArr12[i] + (forceOverLifetimeModule.y.evaluate(f2, f3) * delta);
                    float[] fArr13 = this.velZ;
                    fArr13[i] = fArr13[i] + (forceOverLifetimeModule.z.evaluate(f2, f3) * delta);
                }
                float fEvaluate5 = this.config.gravityModifier.evaluate(f2, f3);
                if (fEvaluate5 != 0.0f) {
                    float[] fArr14 = this.velY;
                    fArr14[i] = fArr14[i] - ((fEvaluate5 * 9.81f) * delta);
                }
                if (this.config.noise.enabled) {
                    applyNoise(i, f2, delta);
                }
                float[] fArr15 = this.posX;
                fArr15[i] = fArr15[i] + (this.velX[i] * delta);
                float[] fArr16 = this.posY;
                fArr16[i] = fArr16[i] + (this.velY[i] * delta);
                float[] fArr17 = this.posZ;
                fArr17[i] = fArr17[i] + (this.velZ[i] * delta);
                this.collidedThisFrame[i] = false;
                if (this.config.collision.enabled) {
                    handleCollision(i, delta);
                }
                if (this.config.sizeOverLifetime.enabled) {
                    this.size[i] = this.startSizeVal[i] * this.config.sizeOverLifetime.size.evaluate(f2, f3);
                }
                if (this.config.colorOverLifetime.enabled) {
                    Color colorEvaluate = this.config.colorOverLifetime.color.evaluate(f2, f3);
                    this.colorR[i] = colorEvaluate.r;
                    this.colorG[i] = colorEvaluate.g;
                    this.colorB[i] = colorEvaluate.b;
                    this.colorA[i] = colorEvaluate.a;
                }
                if (this.config.rotationOverLifetime.enabled) {
                    float fEvaluate6 = this.config.rotationOverLifetime.angularVelocity.evaluate(f2, f3);
                    float[] fArr18 = this.rotation;
                    float f4 = fEvaluate6 * delta;
                    fArr18[i] = fArr18[i] + f4;
                    if (this.config.rotationOverLifetime.separateAxes) {
                        float[] fArr19 = this.rotationX;
                        fArr19[i] = fArr19[i] + (this.config.rotationOverLifetime.angularVelocityX.evaluate(f2, f3) * delta);
                        float[] fArr20 = this.rotationY;
                        fArr20[i] = fArr20[i] + (this.config.rotationOverLifetime.angularVelocityY.evaluate(f2, f3) * delta);
                        float[] fArr21 = this.rotationZ;
                        fArr21[i] = fArr21[i] + (this.config.rotationOverLifetime.angularVelocityZ.evaluate(f2, f3) * delta);
                    } else {
                        float[] fArr22 = this.rotationX;
                        fArr22[i] = fArr22[i] + 0.0f;
                        float[] fArr23 = this.rotationY;
                        fArr23[i] = fArr23[i] + 0.0f;
                        float[] fArr24 = this.rotationZ;
                        fArr24[i] = fArr24[i] + f4;
                    }
                }
                if (this.config.trails.enabled && this.hasTrail[i]) {
                    updateTrail(i, f2, delta);
                }
            }
        }
    }

    private void doEmission(float delta) {
        ParticleSystem3DComponent.EmissionModule emissionModule = this.config.emission;
        this.emissionAccumulator += emissionModule.rateOverTime.evaluate(this.elapsedTime / this.config.duration) * delta;
        float fEvaluate = this.emissionAccumulator + (emissionModule.rateOverDistance.evaluate(this.elapsedTime / this.config.duration) * this.emitterWorldPos.dst(this.prevEmitterWorldPos));
        int iEvaluate = (int) fEvaluate;
        this.emissionAccumulator = fEvaluate - iEvaluate;
        for (ParticleSystem3DComponent.Burst burst : emissionModule.bursts) {
            float f = burst.time;
            float f2 = this.elapsedTime;
            if (f2 >= f && f2 - delta < f && this.rng.nextFloat() <= burst.probability) {
                iEvaluate += (int) burst.count.evaluate(0.0f, this.rng.nextFloat());
            }
        }
        for (int i = 0; i < iEvaluate && this.aliveCount < this.maxParticles; i++) {
            emitParticle();
        }
    }

    public void emitBurstAt(Vector3 worldPosition, int count) {
        Vector3 vector3 = new Vector3(this.emitterWorldPos);
        Matrix4 matrix4 = new Matrix4(this.emitterTransform);
        this.emitterWorldPos.set(worldPosition);
        this.emitterTransform.idt().setToTranslation(worldPosition);
        for (int i = 0; i < count && this.aliveCount < this.maxParticles; i++) {
            emitParticle();
        }
        this.emitterWorldPos.set(vector3);
        this.emitterTransform.set(matrix4);
    }

    public ParticleSystem3DComponent getConfig() {
        return this.config;
    }

    private void emitParticle() {
        int i = this.aliveCount;
        this.aliveCount = i + 1;
        float fNextFloat = this.rng.nextFloat();
        this.randomSeed[i] = fNextFloat;
        this.maxLife[i] = this.config.startLifetime.evaluate(0.0f, fNextFloat);
        this.life[i] = this.maxLife[i];
        this.startSizeVal[i] = this.config.startSize.evaluate(0.0f, fNextFloat);
        this.size[i] = this.startSizeVal[i];
        Color colorEvaluate = this.config.startColor.evaluate(0.0f, fNextFloat);
        this.colorR[i] = colorEvaluate.r;
        this.colorG[i] = colorEvaluate.g;
        this.colorB[i] = colorEvaluate.b;
        this.colorA[i] = colorEvaluate.a;
        this.rotation[i] = this.config.startRotation.evaluate(0.0f, fNextFloat);
        if (this.config.threeDStartRotation) {
            this.rotationX[i] = this.config.startRotationX.evaluate(0.0f, fNextFloat);
            this.rotationY[i] = this.config.startRotationY.evaluate(0.0f, fNextFloat);
            this.rotationZ[i] = this.config.startRotationZ.evaluate(0.0f, fNextFloat);
        } else {
            this.rotationX[i] = 0.0f;
            this.rotationY[i] = 0.0f;
            this.rotationZ[i] = this.rotation[i];
        }
        if (this.config.flipRotation > 0.0f && this.rng.nextFloat() < this.config.flipRotation) {
            float[] fArr = this.rotation;
            fArr[i] = fArr[i] + 180.0f;
        }
        this.rotVelocity[i] = 0.0f;
        Vector3 vector3 = this.tmpV1;
        Vector3 vector4 = this.tmpV2;
        generateFromShape(vector3, vector4);
        vector3.mul(this.emitterTransform);
        vector4.rot(this.emitterTransform).nor();
        this.posX[i] = vector3.x;
        this.posY[i] = vector3.y;
        this.posZ[i] = vector3.z;
        this.prevPosX[i] = vector3.x;
        this.prevPosY[i] = vector3.y;
        this.prevPosZ[i] = vector3.z;
        float fEvaluate = this.config.startSpeed.evaluate(0.0f, fNextFloat);
        this.velX[i] = vector4.x * fEvaluate;
        this.velY[i] = vector4.y * fEvaluate;
        this.velZ[i] = vector4.z * fEvaluate;
        if (this.config.trails.enabled && this.trailData != null) {
            this.hasTrail[i] = this.rng.nextFloat() <= this.config.trails.ratio;
            if (this.hasTrail[i] && i < this.trailData.size()) {
                this.trailData.get(i).clear();
                TrailPoint trailPoint = new TrailPoint();
                trailPoint.x = this.posX[i];
                trailPoint.y = this.posY[i];
                trailPoint.z = this.posZ[i];
                trailPoint.age = 0.0f;
                trailPoint.r = this.colorR[i];
                trailPoint.g = this.colorG[i];
                trailPoint.b = this.colorB[i];
                trailPoint.a = this.colorA[i];
                trailPoint.width = this.size[i];
                this.trailData.get(i).add(trailPoint);
            }
        } else {
            this.hasTrail[i] = false;
        }
        this.collidedThisFrame[i] = false;
        if (!this.config.subEmitters.enabled || this.subEmitterCallback == null) {
            return;
        }
        for (ParticleSystem3DComponent.SubEmitterEntry subEmitterEntry : this.config.subEmitters.entries) {
            if (subEmitterEntry.trigger == ParticleSystem3DComponent.SubEmitterTrigger.BIRTH && this.rng.nextFloat() <= subEmitterEntry.probability) {
                this.subEmitterCallback.onSubEmit(subEmitterEntry.subEmitterObjectId, new Vector3(this.posX[i], this.posY[i], this.posZ[i]), new Vector3(this.velX[i], this.velY[i], this.velZ[i]));
            }
        }
    }

    private void generateFromShape(Vector3 outPos, Vector3 outDir) {
        ParticleSystem3DComponent.ShapeModule shapeModule = this.config.shape;
        if (!shapeModule.enabled) {
            outPos.set(0.0f, 0.0f, 0.0f);
            outDir.set(0.0f, 1.0f, 0.0f);
            return;
        }
        switch (AnonymousClass1.$SwitchMap$org$catrobat$catroid$raptor$ParticleSystem3DComponent$ShapeType[shapeModule.type.ordinal()]) {
            case 1:
                generateCone(outPos, outDir, shapeModule);
                break;
            case 2:
                generateSphere(outPos, outDir, shapeModule.sphereRadius, false);
                break;
            case 3:
                generateSphere(outPos, outDir, shapeModule.sphereRadius, true);
                break;
            case 4:
                generateBox(outPos, outDir, shapeModule);
                break;
            case 5:
                generateCircle(outPos, outDir, shapeModule);
                break;
            case 6:
                generateEdge(outPos, outDir, shapeModule);
                break;
            default:
                outPos.set(0.0f, 0.0f, 0.0f);
                outDir.set(0.0f, 1.0f, 0.0f);
                break;
        }
        if (shapeModule.randomizeDirection) {
            outDir.lerp(this.tmpV3.set((this.rng.nextFloat() * 2.0f) - 1.0f, (this.rng.nextFloat() * 2.0f) - 1.0f, (this.rng.nextFloat() * 2.0f) - 1.0f).nor(), 0.5f).nor();
        }
        if (shapeModule.spherizeDirection > 0.0f) {
            outDir.lerp(this.tmpV3.set(outPos).nor(), shapeModule.spherizeDirection).nor();
        }
        if (shapeModule.randomizePosition > 0.0f) {
            outPos.x += ((this.rng.nextFloat() * 2.0f) - 1.0f) * shapeModule.randomizePosition;
            outPos.y += ((this.rng.nextFloat() * 2.0f) - 1.0f) * shapeModule.randomizePosition;
            outPos.z += ((this.rng.nextFloat() * 2.0f) - 1.0f) * shapeModule.randomizePosition;
        }
    }

    private void generateCone(Vector3 outPos, Vector3 outDir, ParticleSystem3DComponent.ShapeModule s) {
        float fNextFloat = this.rng.nextFloat() * 6.2831855f;
        float fSqrt = 1.0f;
        if (s.emitFrom != ParticleSystem3DComponent.EmitFrom.SURFACE && s.emitFrom != ParticleSystem3DComponent.EmitFrom.EDGE) {
            fSqrt = (float) Math.sqrt(this.rng.nextFloat());
        }
        float f = s.coneRadius * fSqrt;
        outPos.set(MathUtils.cos(fNextFloat) * f, 0.0f, MathUtils.sin(fNextFloat) * f);
        float f2 = s.coneAngle * 0.017453292f * 0.5f;
        float fSin = MathUtils.sin(f2) * fSqrt;
        outDir.set(MathUtils.cos(fNextFloat) * fSin, MathUtils.cos(f2), MathUtils.sin(fNextFloat) * fSin).nor();
        if (s.emitFrom != ParticleSystem3DComponent.EmitFrom.VOLUME || s.coneLength <= 0.0f) {
            return;
        }
        float fNextFloat2 = this.rng.nextFloat();
        outPos.y += s.coneLength * fNextFloat2;
        float fTan = fNextFloat2 * MathUtils.tan(f2) * s.coneLength;
        outPos.x += MathUtils.cos(fNextFloat) * fTan;
        outPos.z += MathUtils.sin(fNextFloat) * fTan;
    }

    private void generateSphere(Vector3 outPos, Vector3 outDir, float radius, boolean hemisphere) {
        float fNextFloat = this.rng.nextFloat() * 6.2831855f;
        float fAcos = (float) Math.acos(hemisphere ? this.rng.nextFloat() : (this.rng.nextFloat() * 2.0f) - 1.0f);
        float fSin = MathUtils.sin(fAcos);
        outDir.set(MathUtils.cos(fNextFloat) * fSin, MathUtils.cos(fAcos), fSin * MathUtils.sin(fNextFloat)).nor();
        if (this.config.shape.emitFrom == ParticleSystem3DComponent.EmitFrom.SURFACE) {
            outPos.set(outDir).scl(radius);
        } else {
            outPos.set(outDir).scl(radius * ((float) Math.cbrt(this.rng.nextFloat())));
        }
    }

    private void generateBox(Vector3 outPos, Vector3 outDir, ParticleSystem3DComponent.ShapeModule s) {
        Vector3 vector3Scl = this.tmpV3.set(s.boxSize).scl(0.5f);
        if (s.emitFrom == ParticleSystem3DComponent.EmitFrom.SURFACE) {
            int iNextInt = this.rng.nextInt(6);
            float fNextFloat = (this.rng.nextFloat() * 2.0f) - 1.0f;
            float fNextFloat2 = (this.rng.nextFloat() * 2.0f) - 1.0f;
            if (iNextInt == 0) {
                outPos.set(vector3Scl.x, fNextFloat * vector3Scl.y, fNextFloat2 * vector3Scl.z);
                outDir.set(1.0f, 0.0f, 0.0f);
                return;
            }
            if (iNextInt == 1) {
                outPos.set(-vector3Scl.x, fNextFloat * vector3Scl.y, fNextFloat2 * vector3Scl.z);
                outDir.set(-1.0f, 0.0f, 0.0f);
                return;
            }
            if (iNextInt == 2) {
                outPos.set(fNextFloat * vector3Scl.x, vector3Scl.y, fNextFloat2 * vector3Scl.z);
                outDir.set(0.0f, 1.0f, 0.0f);
                return;
            }
            if (iNextInt == 3) {
                outPos.set(fNextFloat * vector3Scl.x, -vector3Scl.y, fNextFloat2 * vector3Scl.z);
                outDir.set(0.0f, -1.0f, 0.0f);
                return;
            } else if (iNextInt == 4) {
                outPos.set(fNextFloat * vector3Scl.x, fNextFloat2 * vector3Scl.y, vector3Scl.z);
                outDir.set(0.0f, 0.0f, 1.0f);
                return;
            } else {
                if (iNextInt != 5) {
                    return;
                }
                outPos.set(fNextFloat * vector3Scl.x, fNextFloat2 * vector3Scl.y, -vector3Scl.z);
                outDir.set(0.0f, 0.0f, -1.0f);
                return;
            }
        }
        outPos.set(((this.rng.nextFloat() * 2.0f) - 1.0f) * vector3Scl.x, ((this.rng.nextFloat() * 2.0f) - 1.0f) * vector3Scl.y, ((this.rng.nextFloat() * 2.0f) - 1.0f) * vector3Scl.z);
        outDir.set(0.0f, 1.0f, 0.0f);
    }

    private void generateCircle(Vector3 outPos, Vector3 outDir, ParticleSystem3DComponent.ShapeModule s) {
        float fNextFloat = this.rng.nextFloat() * s.circleArc * 0.017453292f;
        float fSqrt = s.emitFrom == ParticleSystem3DComponent.EmitFrom.SURFACE ? s.circleRadius : s.circleRadius * ((float) Math.sqrt(this.rng.nextFloat()));
        outPos.set(MathUtils.cos(fNextFloat) * fSqrt, 0.0f, MathUtils.sin(fNextFloat) * fSqrt);
        outDir.set(0.0f, 1.0f, 0.0f);
    }

    private void generateEdge(Vector3 outPos, Vector3 outDir, ParticleSystem3DComponent.ShapeModule s) {
        outPos.set((this.rng.nextFloat() - 0.5f) * s.edgeLength, 0.0f, 0.0f);
        outDir.set(0.0f, 1.0f, 0.0f);
    }

    private void applyOrbitalVelocity(int idx, float orbX, float orbY, float orbZ, float delta) {
        this.tmpV1.set(this.posX[idx] - this.emitterWorldPos.x, this.posY[idx] - this.emitterWorldPos.y, this.posZ[idx] - this.emitterWorldPos.z);
        if (orbY != 0.0f) {
            float f = orbY * delta * 0.017453292f;
            float fCos = MathUtils.cos(f);
            float fSin = MathUtils.sin(f);
            float f2 = (this.tmpV1.x * fCos) - (this.tmpV1.z * fSin);
            float f3 = (this.tmpV1.x * fSin) + (this.tmpV1.z * fCos);
            this.posX[idx] = this.emitterWorldPos.x + f2;
            this.posZ[idx] = this.emitterWorldPos.z + f3;
        }
        if (orbX != 0.0f) {
            float f4 = orbX * delta * 0.017453292f;
            float fCos2 = MathUtils.cos(f4);
            float fSin2 = MathUtils.sin(f4);
            float f5 = (this.tmpV1.y * fCos2) - (this.tmpV1.z * fSin2);
            float f6 = (this.tmpV1.y * fSin2) + (this.tmpV1.z * fCos2);
            this.posY[idx] = this.emitterWorldPos.y + f5;
            this.posZ[idx] = this.emitterWorldPos.z + f6;
        }
    }

    private void applyNoise(int idx, float normalizedLife, float delta) {
        ParticleSystem3DComponent.NoiseModule noiseModule = this.config.noise;
        float f = noiseModule.strength;
        float f2 = 1.0f;
        if (noiseModule.damping) {
            f *= 1.0f - normalizedLife;
        }
        float f3 = noiseModule.frequency;
        float f4 = this.elapsedTime * noiseModule.scrollSpeed;
        float f5 = this.randomSeed[idx] * 1000.0f;
        float fSin = ((float) Math.sin(((this.posX[idx] + f5) * f3) + f4)) * ((float) Math.cos(((this.posY[idx] + (f5 * 0.7f)) * f3 * 1.3f) + (0.8f * f4)));
        float fSin2 = ((float) Math.sin(((this.posY[idx] + (f5 * 1.1f)) * f3) + (f4 * 1.1f))) * ((float) Math.cos(((this.posZ[idx] + (f5 * 0.5f)) * f3 * 0.9f) + (1.2f * f4)));
        float fSin3 = ((float) Math.sin(((this.posZ[idx] + (f5 * 0.9f)) * f3) + (0.7f * f4))) * ((float) Math.cos(((this.posX[idx] + (1.3f * f5)) * f3 * 1.1f) + (0.9f * f4)));
        for (int i = 1; i < noiseModule.octaves; i++) {
            f2 *= 0.5f;
            float f6 = (1 << i) * f3;
            fSin += ((float) Math.sin(((this.posX[idx] + f5) * f6) + f4)) * f2;
            fSin2 += ((float) Math.sin(((this.posY[idx] + f5) * f6) + f4)) * f2;
            fSin3 += ((float) Math.sin(((this.posZ[idx] + f5) * f6) + f4)) * f2;
        }
        float f7 = noiseModule.separateAxes ? noiseModule.strengthX : f;
        float f8 = noiseModule.separateAxes ? noiseModule.strengthY : f;
        if (noiseModule.separateAxes) {
            f = noiseModule.strengthZ;
        }
        float[] fArr = this.velX;
        fArr[idx] = fArr[idx] + (fSin * f7 * delta);
        float[] fArr2 = this.velY;
        fArr2[idx] = fArr2[idx] + (fSin2 * f8 * delta);
        float[] fArr3 = this.velZ;
        fArr3[idx] = fArr3[idx] + (fSin3 * f * delta);
    }

    private void handleCollision(int idx, float delta) {
        ParticleSystem3DComponent.CollisionModule collisionModule = this.config.collision;
        if (collisionModule.mode == ParticleSystem3DComponent.CollisionMode.PLANES) {
            handlePlaneCollision(idx, collisionModule);
        } else if (collisionModule.mode == ParticleSystem3DComponent.CollisionMode.WORLD_PHYSICS) {
            handleWorldCollision(idx, collisionModule, delta);
        }
    }

    private void handlePlaneCollision(int idx, ParticleSystem3DComponent.CollisionModule col) {
        for (ParticleSystem3DComponent.CollisionPlane collisionPlane : col.planes) {
            this.tmpV1.set(this.posX[idx] - collisionPlane.point.x, this.posY[idx] - collisionPlane.point.y, this.posZ[idx] - collisionPlane.point.z);
            float fDot = this.tmpV1.dot(collisionPlane.normal);
            float f = this.size[idx] * 0.5f * col.radiusScale;
            if (fDot < f) {
                float[] fArr = this.posX;
                float f2 = f - fDot;
                fArr[idx] = fArr[idx] + (collisionPlane.normal.x * f2);
                float[] fArr2 = this.posY;
                fArr2[idx] = fArr2[idx] + (collisionPlane.normal.y * f2);
                float[] fArr3 = this.posZ;
                fArr3[idx] = fArr3[idx] + (collisionPlane.normal.z * f2);
                reflectVelocity(idx, collisionPlane.normal.x, collisionPlane.normal.y, collisionPlane.normal.z, col);
                this.collidedThisFrame[idx] = true;
                onParticleCollision(idx);
            }
        }
    }

    private void handleWorldCollision(int idx, ParticleSystem3DComponent.CollisionModule col, float delta) {
        int i;
        if (this.engineRef == null) {
            return;
        }
        if (col.quality == ParticleSystem3DComponent.CollisionQuality.LOW) {
            i = 4;
        } else {
            i = col.quality == ParticleSystem3DComponent.CollisionQuality.MEDIUM ? 2 : 1;
        }
        if ((((int) (this.elapsedTime * 60.0f)) + idx) % i != 0) {
            return;
        }
        this.tmpV1.set(this.prevPosX[idx], this.prevPosY[idx], this.prevPosZ[idx]);
        this.tmpV2.set(this.posX[idx] - this.prevPosX[idx], this.posY[idx] - this.prevPosY[idx], this.posZ[idx] - this.prevPosZ[idx]);
        float fLen = this.tmpV2.len();
        if (fLen < 0.001f) {
            return;
        }
        String str = "particle_col_" + this.ownerObjectId + "_" + idx;
        this.engineRef.castRay(str, this.tmpV1, this.tmpV2.nor().scl((this.size[idx] * col.radiusScale) + fLen));
        if (!this.engineRef.getRayDidHit(str) || this.engineRef.getRaycastDistance(str) > fLen + (this.size[idx] * col.radiusScale * 0.5f)) {
            return;
        }
        this.posX[idx] = this.engineRef.getRayHitPointX(str);
        this.posY[idx] = this.engineRef.getRayHitPointY(str);
        this.posZ[idx] = this.engineRef.getRayHitPointZ(str);
        float rayHitNormalX = this.engineRef.getRayHitNormalX(str);
        float rayHitNormalY = this.engineRef.getRayHitNormalY(str);
        float rayHitNormalZ = this.engineRef.getRayHitNormalZ(str);
        float[] fArr = this.posX;
        fArr[idx] = fArr[idx] + (this.size[idx] * rayHitNormalX * col.radiusScale * 0.5f);
        float[] fArr2 = this.posY;
        fArr2[idx] = fArr2[idx] + (this.size[idx] * rayHitNormalY * col.radiusScale * 0.5f);
        float[] fArr3 = this.posZ;
        fArr3[idx] = fArr3[idx] + (this.size[idx] * rayHitNormalZ * col.radiusScale * 0.5f);
        reflectVelocity(idx, rayHitNormalX, rayHitNormalY, rayHitNormalZ, col);
        this.collidedThisFrame[idx] = true;
        this.collisionNormalX[idx] = rayHitNormalX;
        this.collisionNormalY[idx] = rayHitNormalY;
        this.collisionNormalZ[idx] = rayHitNormalZ;
        float[] fArr4 = this.life;
        fArr4[idx] = fArr4[idx] - (this.maxLife[idx] * col.lifetimeLoss);
        float f = this.velX[idx];
        float f2 = this.velY[idx];
        float f3 = (f * f) + (f2 * f2);
        float f4 = this.velZ[idx];
        if (((float) Math.sqrt(f3 + (f4 * f4))) < col.minKillSpeed) {
            this.life[idx] = 0.0f;
        }
        onParticleCollision(idx);
    }

    private void reflectVelocity(int idx, float nx, float ny, float nz, ParticleSystem3DComponent.CollisionModule col) {
        float[] fArr = this.velX;
        float f = fArr[idx];
        float f2 = ((f * nx) + (this.velY[idx] * ny) + (this.velZ[idx] * nz)) * 2.0f;
        fArr[idx] = (f - (nx * f2)) * col.bounce;
        float[] fArr2 = this.velY;
        fArr2[idx] = (fArr2[idx] - (ny * f2)) * col.bounce;
        float[] fArr3 = this.velZ;
        fArr3[idx] = (fArr3[idx] - (f2 * nz)) * col.bounce;
        if (col.dampen > 0.0f) {
            float[] fArr4 = this.velX;
            fArr4[idx] = fArr4[idx] * (1.0f - col.dampen);
            float[] fArr5 = this.velY;
            fArr5[idx] = fArr5[idx] * (1.0f - col.dampen);
            float[] fArr6 = this.velZ;
            fArr6[idx] = fArr6[idx] * (1.0f - col.dampen);
        }
    }

    private void onParticleDeath(int idx) {
        if (!this.config.subEmitters.enabled || this.subEmitterCallback == null) {
            return;
        }
        for (ParticleSystem3DComponent.SubEmitterEntry subEmitterEntry : this.config.subEmitters.entries) {
            if (subEmitterEntry.trigger == ParticleSystem3DComponent.SubEmitterTrigger.DEATH && this.rng.nextFloat() <= subEmitterEntry.probability) {
                this.subEmitterCallback.onSubEmit(subEmitterEntry.subEmitterObjectId, new Vector3(this.posX[idx], this.posY[idx], this.posZ[idx]), new Vector3(this.velX[idx], this.velY[idx], this.velZ[idx]));
            }
        }
    }

    private void onParticleCollision(int idx) {
        if (!this.config.subEmitters.enabled || this.subEmitterCallback == null) {
            return;
        }
        for (ParticleSystem3DComponent.SubEmitterEntry subEmitterEntry : this.config.subEmitters.entries) {
            if (subEmitterEntry.trigger == ParticleSystem3DComponent.SubEmitterTrigger.COLLISION && this.rng.nextFloat() <= subEmitterEntry.probability) {
                this.subEmitterCallback.onSubEmit(subEmitterEntry.subEmitterObjectId, new Vector3(this.posX[idx], this.posY[idx], this.posZ[idx]), new Vector3(this.collisionNormalX[idx], this.collisionNormalY[idx], this.collisionNormalZ[idx]));
            }
        }
    }

    private void killParticle(int idx) {
        int i = this.aliveCount - 1;
        if (idx != i) {
            float[] fArr = this.posX;
            fArr[idx] = fArr[i];
            float[] fArr2 = this.posY;
            fArr2[idx] = fArr2[i];
            float[] fArr3 = this.posZ;
            fArr3[idx] = fArr3[i];
            float[] fArr4 = this.velX;
            fArr4[idx] = fArr4[i];
            float[] fArr5 = this.velY;
            fArr5[idx] = fArr5[i];
            float[] fArr6 = this.velZ;
            fArr6[idx] = fArr6[i];
            float[] fArr7 = this.prevPosX;
            fArr7[idx] = fArr7[i];
            float[] fArr8 = this.prevPosY;
            fArr8[idx] = fArr8[i];
            float[] fArr9 = this.prevPosZ;
            fArr9[idx] = fArr9[i];
            float[] fArr10 = this.life;
            fArr10[idx] = fArr10[i];
            float[] fArr11 = this.maxLife;
            fArr11[idx] = fArr11[i];
            float[] fArr12 = this.randomSeed;
            fArr12[idx] = fArr12[i];
            float[] fArr13 = this.size;
            fArr13[idx] = fArr13[i];
            float[] fArr14 = this.startSizeVal;
            fArr14[idx] = fArr14[i];
            float[] fArr15 = this.rotation;
            fArr15[idx] = fArr15[i];
            float[] fArr16 = this.rotVelocity;
            fArr16[idx] = fArr16[i];
            float[] fArr17 = this.rotationX;
            fArr17[idx] = fArr17[i];
            float[] fArr18 = this.rotationY;
            fArr18[idx] = fArr18[i];
            float[] fArr19 = this.rotationZ;
            fArr19[idx] = fArr19[i];
            float[] fArr20 = this.colorR;
            fArr20[idx] = fArr20[i];
            float[] fArr21 = this.colorG;
            fArr21[idx] = fArr21[i];
            float[] fArr22 = this.colorB;
            fArr22[idx] = fArr22[i];
            float[] fArr23 = this.colorA;
            fArr23[idx] = fArr23[i];
            boolean[] zArr = this.collidedThisFrame;
            zArr[idx] = zArr[i];
            boolean[] zArr2 = this.hasTrail;
            zArr2[idx] = zArr2[i];
            List<List<TrailPoint>> list = this.trailData;
            if (list != null && idx < list.size() && i < this.trailData.size()) {
                List<TrailPoint> list2 = this.trailData.get(idx);
                List<List<TrailPoint>> list3 = this.trailData;
                list3.set(idx, list3.get(i));
                this.trailData.set(i, list2);
            }
        }
        this.aliveCount--;
    }

    private void updateTrail(int idx, float normalizedLife, float delta) {
        List<List<TrailPoint>> list = this.trailData;
        if (list == null || idx >= list.size()) {
            return;
        }
        List<TrailPoint> list2 = this.trailData.get(idx);
        if (list2 == null) {
            this.trailData.set(idx, new ArrayList());
            list2 = this.trailData.get(idx);
        }
        float f = this.maxLife[idx] * this.config.trails.lifetime;
        for (int size = list2.size() - 1; size >= 0; size--) {
            list2.get(size).age += delta;
            if (list2.get(size).age > f) {
                list2.remove(size);
            }
        }
        TrailPoint trailPoint = list2.isEmpty() ? null : list2.get(list2.size() - 1);
        if ((trailPoint == null ? Float.MAX_VALUE : (float) Math.sqrt(((this.posX[idx] - trailPoint.x) * (this.posX[idx] - trailPoint.x)) + ((this.posY[idx] - trailPoint.y) * (this.posY[idx] - trailPoint.y)) + ((this.posZ[idx] - trailPoint.z) * (this.posZ[idx] - trailPoint.z)))) >= this.config.trails.minimumVertexDistance) {
            TrailPoint trailPoint2 = new TrailPoint();
            trailPoint2.x = this.posX[idx];
            trailPoint2.y = this.posY[idx];
            trailPoint2.z = this.posZ[idx];
            trailPoint2.age = 0.0f;
            if (this.config.trails.inheritParticleColor) {
                trailPoint2.r = this.colorR[idx];
                trailPoint2.g = this.colorG[idx];
                trailPoint2.b = this.colorB[idx];
                trailPoint2.a = this.colorA[idx];
            } else {
                Color colorEvaluate = this.config.trails.colorOverLifetime.evaluate(normalizedLife);
                trailPoint2.r = colorEvaluate.r;
                trailPoint2.g = colorEvaluate.g;
                trailPoint2.b = colorEvaluate.b;
                trailPoint2.a = colorEvaluate.a;
            }
            trailPoint2.width = this.size[idx] * this.config.trails.widthOverTrail.evaluate(list2.isEmpty() ? 0.0f : 1.0f);
            list2.add(trailPoint2);
        }
    }

    private void buildMesh(int maxParticles) {
        int i = maxParticles * 4;
        int i2 = maxParticles * 6;
        this.vertices = new float[maxParticles * 36];
        this.indices = new short[i2];
        for (int i3 = 0; i3 < maxParticles; i3++) {
            int i4 = i3 * 4;
            int i5 = i3 * 6;
            short[] sArr = this.indices;
            short s = (short) i4;
            sArr[i5] = s;
            sArr[i5 + 1] = (short) (i4 + 1);
            short s2 = (short) (i4 + 2);
            sArr[i5 + 2] = s2;
            sArr[i5 + 3] = s2;
            sArr[i5 + 4] = (short) (i4 + 3);
            sArr[i5 + 5] = s;
        }
        Mesh mesh = new Mesh(false, i, i2, new VertexAttribute(1, 3, ShaderProgram.POSITION_ATTRIBUTE), new VertexAttribute(16, 2, "a_texCoord0"), new VertexAttribute(4, 4, ShaderProgram.COLOR_ATTRIBUTE));
        this.billboardMesh = mesh;
        mesh.setIndices(this.indices);
    }

    private void buildShader() {
        ShaderProgram shaderProgram = new ShaderProgram("attribute vec3 a_position;\nattribute vec2 a_texCoord0;\nattribute vec4 a_color;\nuniform mat4 u_projViewTrans;\nvarying vec2 v_texCoord;\nvarying vec4 v_color;\nvoid main() {\n    v_texCoord = a_texCoord0;\n    v_color = a_color;\n    gl_Position = u_projViewTrans * vec4(a_position, 1.0);\n}\n", "#ifdef GL_ES\nprecision mediump float;\n#endif\nvarying vec2 v_texCoord;\nvarying vec4 v_color;\nuniform sampler2D u_texture;\nvoid main() {\n    vec4 texColor = texture2D(u_texture, v_texCoord);\n    gl_FragColor = texColor * v_color;\n    if (gl_FragColor.a < 0.01) discard;\n}\n");
        this.shader = shaderProgram;
        if (!shaderProgram.isCompiled()) {
            Gdx.f82app.error("ParticleShader", "COMPILATION FAILED: " + this.shader.getLog());
            this.shader.dispose();
            this.shader = null;
            return;
        }
        Gdx.f82app.log("ParticleShader", "Particle shader compiled successfully.");
    }

    private void sortByDistance() {
        int[] iArr = this.sortedIndices;
        if (iArr == null || iArr.length < this.maxParticles) {
            int i = this.maxParticles;
            this.sortedIndices = new int[i];
            this.sortDistances = new float[i];
        }
        Vector3 vector3 = this.camera.position;
        for (int i2 = 0; i2 < this.aliveCount; i2++) {
            this.sortedIndices[i2] = i2;
            float f = this.posX[i2] - vector3.x;
            float f2 = this.posY[i2] - vector3.y;
            float f3 = this.posZ[i2] - vector3.z;
            this.sortDistances[i2] = -((f * f) + (f2 * f2) + (f3 * f3));
        }
        for (int i3 = 1; i3 < this.aliveCount; i3++) {
            int i4 = this.sortedIndices[i3];
            float f4 = this.sortDistances[i3];
            int i5 = i3 - 1;
            while (i5 >= 0) {
                float[] fArr = this.sortDistances;
                float f5 = fArr[i5];
                if (f5 <= f4) {
                    break;
                }
                int[] iArr2 = this.sortedIndices;
                int i6 = i5 + 1;
                iArr2[i6] = iArr2[i5];
                fArr[i6] = f5;
                i5--;
            }
            int i7 = i5 + 1;
            this.sortedIndices[i7] = i4;
            this.sortDistances[i7] = f4;
        }
    }

    public void render() {
        int iMax;
        int iMax2;
        int i;
        int i2;
        float f;
        float f2;
        float f3;
        float f4;
        float f5;
        float f6;
        float f7;
        int i3;
        int i4;
        float f8;
        float f9;
        float f10;
        if (this.aliveCount == 0 || this.camera == null) {
            return;
        }
        ensureGLInitialized();
        if (this.config.renderer.renderMode == ParticleSystem3DComponent.RenderMode.MESH) {
            renderMeshParticles();
            return;
        }
        ShaderProgram shaderProgram = this.shader;
        if (shaderProgram == null || !shaderProgram.isCompiled()) {
            return;
        }
        int i5 = 1;
        if (this.config.renderer.sortMode == 1) {
            sortByDistance();
        }
        boolean z = this.config.textureSheetAnimation.enabled;
        if (z) {
            iMax = Math.max(1, this.config.textureSheetAnimation.tilesX);
            iMax2 = Math.max(1, this.config.textureSheetAnimation.tilesY);
            i = iMax * iMax2;
        } else {
            iMax = 1;
            iMax2 = 1;
            i = 1;
        }
        Vector3 vector3Nor = this.tmpV3.set(this.camera.direction).crs(this.camera.up).nor();
        Vector3 vector3Nor2 = this.tmpV4.set(vector3Nor).crs(this.camera.direction).nor();
        int i6 = 0;
        int i7 = 0;
        while (true) {
            i2 = this.aliveCount;
            if (i6 >= i2) {
                break;
            }
            int i8 = this.config.renderer.sortMode == i5 ? this.sortedIndices[i6] : i6;
            float f11 = this.size[i8] * 0.5f;
            float f12 = this.posX[i8];
            float f13 = this.posY[i8];
            float f14 = this.posZ[i8];
            float f15 = this.rotation[i8] * 0.017453292f;
            float fCos = MathUtils.cos(f15);
            float fSin = MathUtils.sin(f15);
            ParticleSystem3DComponent.RendererModule rendererModule = this.config.renderer;
            int i9 = i6;
            int i10 = AnonymousClass1.$SwitchMap$org$catrobat$catroid$raptor$ParticleSystem3DComponent$RenderMode[rendererModule.renderMode.ordinal()];
            float f16 = 1.0f;
            if (i10 == 1) {
                float f17 = this.posX[i8] - this.prevPosX[i8];
                float f18 = this.posY[i8] - this.prevPosY[i8];
                float f19 = this.posZ[i8] - this.prevPosZ[i8];
                float f20 = f18;
                float fSqrt = (float) Math.sqrt((f17 * f17) + (f18 * f18) + (f19 * f19));
                if (fSqrt < 1.0E-4f) {
                    fSqrt = 1.0f;
                    f20 = 1.0f;
                    f17 = 0.0f;
                    f19 = 0.0f;
                }
                float f21 = f17 / fSqrt;
                float f22 = f20 / fSqrt;
                float f23 = f19 / fSqrt;
                float f24 = (rendererModule.lengthScale * f11) + (fSqrt * rendererModule.speedScale);
                float f25 = (this.camera.direction.z * f22) - (this.camera.direction.y * f23);
                float f26 = (this.camera.direction.x * f23) - (this.camera.direction.z * f21);
                float f27 = (this.camera.direction.y * f21) - (this.camera.direction.x * f22);
                float f28 = f26;
                float fSqrt2 = (float) Math.sqrt((f25 * f25) + (f26 * f26) + (f27 * f27));
                if (fSqrt2 < 1.0E-4f) {
                    f2 = 1.0f;
                    f = 1.0f;
                    f27 = 0.0f;
                    f28 = 0.0f;
                } else {
                    f = fSqrt2;
                    f2 = f25;
                }
                float f29 = f27 / f;
                f3 = (f2 / f) * f11;
                float f30 = (f28 / f) * f11;
                float f31 = f29 * f11;
                float f32 = f21 * f24;
                f11 = f22 * f24;
                float f33 = f23 * f24;
                f4 = f31;
                f5 = f32;
                f6 = f30;
                f7 = f33;
            } else if (i10 == 2) {
                float f34 = f11 * fCos;
                float f35 = fSin * 0.0f;
                float f36 = f34 - f35;
                float f37 = f11 * fSin;
                float f38 = fCos * 0.0f;
                float f39 = f37 + f38;
                f5 = f38 - f37;
                float f40 = f34 + f35;
                f11 = 0.0f;
                f4 = f39;
                f7 = f40;
                f6 = 0.0f;
                f3 = f36;
            } else if (i10 == 3) {
                Vector3 vector3Nor3 = this.tmpV2.set(this.tmpV1.set(this.camera.position.x - f12, 0.0f, this.camera.position.z - f14).nor()).crs(Vector3.Y).nor();
                float f41 = vector3Nor3.x * f11;
                f4 = vector3Nor3.z * f11;
                f3 = f41;
                f7 = 0.0f;
                f6 = 0.0f;
                f5 = 0.0f;
            } else {
                float f42 = (vector3Nor.x * fCos) + (vector3Nor2.x * fSin);
                float f43 = (vector3Nor.y * fCos) + (vector3Nor2.y * fSin);
                float f44 = (vector3Nor.z * fCos) + (vector3Nor2.z * fSin);
                float f45 = ((-vector3Nor.x) * fSin) + (vector3Nor2.x * fCos);
                float f46 = ((-vector3Nor.y) * fSin) + (vector3Nor2.y * fCos);
                float f47 = ((-vector3Nor.z) * fSin) + (vector3Nor2.z * fCos);
                f3 = f42 * f11;
                f6 = f43 * f11;
                f4 = f44 * f11;
                f5 = f45 * f11;
                f7 = f47 * f11;
                f11 = f46 * f11;
            }
            if (!z || i <= 1) {
                i3 = iMax;
                i4 = iMax2;
                f8 = 1.0f;
                f9 = 0.0f;
                f10 = 0.0f;
            } else {
                int iClamp = MathUtils.clamp((int) (this.config.textureSheetAnimation.frameOverTime.evaluate(1.0f - (this.life[i8] / this.maxLife[i8]), this.randomSeed[i8]) * i), 0, i - 1);
                i3 = iMax;
                float f48 = 1.0f / i3;
                i4 = iMax2;
                float f49 = 1.0f / i4;
                float f50 = (iClamp % iMax) * f48;
                f9 = (iClamp / iMax) * f49;
                f8 = f48 + f50;
                f16 = f9 + f49;
                f10 = f50;
            }
            int i11 = i4;
            int i12 = i3;
            float floatBits = Color.toFloatBits(this.colorR[i8], this.colorG[i8], this.colorB[i8], this.colorA[i8]);
            float[] fArr = this.vertices;
            float f51 = f12 - f3;
            fArr[i7] = f51 - f5;
            float f52 = f13 - f6;
            fArr[i7 + 1] = f52 - f11;
            float f53 = f14 - f4;
            fArr[i7 + 2] = f53 - f7;
            fArr[i7 + 3] = f10;
            fArr[i7 + 4] = f16;
            fArr[i7 + 5] = floatBits;
            float f54 = f12 + f3;
            fArr[i7 + 6] = f54 - f5;
            float f55 = f13 + f6;
            fArr[i7 + 7] = f55 - f11;
            float f56 = f14 + f4;
            fArr[i7 + 8] = f56 - f7;
            fArr[i7 + 9] = f8;
            fArr[i7 + 10] = f16;
            fArr[i7 + 11] = floatBits;
            fArr[i7 + 12] = f54 + f5;
            fArr[i7 + 13] = f55 + f11;
            fArr[i7 + 14] = f56 + f7;
            fArr[i7 + 15] = f8;
            fArr[i7 + 16] = f9;
            fArr[i7 + 17] = floatBits;
            fArr[i7 + 18] = f51 + f5;
            fArr[i7 + 19] = f52 + f11;
            fArr[i7 + 20] = f53 + f7;
            fArr[i7 + 21] = f10;
            fArr[i7 + 22] = f9;
            fArr[i7 + 23] = floatBits;
            i6 = i9 + 1;
            i7 += 24;
            z = z;
            vector3Nor2 = vector3Nor2;
            i = i;
            vector3Nor = vector3Nor;
            iMax2 = i11;
            iMax = i12;
            i5 = 1;
        }
        if (i2 == 0) {
            return;
        }
        this.billboardMesh.setVertices(this.vertices, 0, i2 * 36);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        if (this.config.renderer.isAdditive) {
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, 1);
        } else {
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        }
        Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
        Gdx.gl.glDepthMask(!this.config.renderer.isAdditive);
        this.shader.begin();
        this.shader.setUniformMatrix("u_projViewTrans", this.camera.combined);
        Texture texture = this.texture;
        if (texture != null) {
            texture.bind(0);
            this.shader.setUniformi("u_texture", 0);
        }
        this.billboardMesh.render(this.shader, 4, 0, this.aliveCount * 6);
        this.shader.end();
        if (this.config.trails.enabled) {
            renderTrails();
        }
        Gdx.gl.glDepthMask(true);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/particles/ParticleSystem3DRuntime$1.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.raptor.particles.ParticleSystem3DRuntime$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$catrobat$catroid$raptor$ParticleSystem3DComponent$RenderMode;
        static final /* synthetic */ int[] $SwitchMap$org$catrobat$catroid$raptor$ParticleSystem3DComponent$ShapeType;

        static {
            int[] iArr = new int[ParticleSystem3DComponent.RenderMode.values().length];
            $SwitchMap$org$catrobat$catroid$raptor$ParticleSystem3DComponent$RenderMode = iArr;
            try {
                iArr[ParticleSystem3DComponent.RenderMode.STRETCHED_BILLBOARD.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$raptor$ParticleSystem3DComponent$RenderMode[ParticleSystem3DComponent.RenderMode.HORIZONTAL_BILLBOARD.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$raptor$ParticleSystem3DComponent$RenderMode[ParticleSystem3DComponent.RenderMode.VERTICAL_BILLBOARD.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$raptor$ParticleSystem3DComponent$RenderMode[ParticleSystem3DComponent.RenderMode.BILLBOARD.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            int[] iArr2 = new int[ParticleSystem3DComponent.ShapeType.values().length];
            $SwitchMap$org$catrobat$catroid$raptor$ParticleSystem3DComponent$ShapeType = iArr2;
            try {
                iArr2[ParticleSystem3DComponent.ShapeType.CONE.ordinal()] = 1;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$raptor$ParticleSystem3DComponent$ShapeType[ParticleSystem3DComponent.ShapeType.SPHERE.ordinal()] = 2;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$raptor$ParticleSystem3DComponent$ShapeType[ParticleSystem3DComponent.ShapeType.HEMISPHERE.ordinal()] = 3;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$raptor$ParticleSystem3DComponent$ShapeType[ParticleSystem3DComponent.ShapeType.BOX.ordinal()] = 4;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$raptor$ParticleSystem3DComponent$ShapeType[ParticleSystem3DComponent.ShapeType.CIRCLE.ordinal()] = 5;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$raptor$ParticleSystem3DComponent$ShapeType[ParticleSystem3DComponent.ShapeType.EDGE.ordinal()] = 6;
            } catch (NoSuchFieldError unused10) {
            }
        }
    }

    private void renderMeshParticles() {
        ShaderProgram shaderProgram;
        if (this.meshBatcherDirty || this.meshBatcher == null) {
            MeshParticleBatcher meshParticleBatcher = this.meshBatcher;
            if (meshParticleBatcher != null) {
                meshParticleBatcher.dispose();
                this.meshBatcher = null;
            }
            initMeshBatcher();
            this.meshBatcherDirty = false;
        }
        this.meshBatcher.ensureGLReady();
        this.meshBatcher.batch(this.aliveCount, this.posX, this.posY, this.posZ, this.size, this.rotationX, this.rotationY, this.rotationZ, this.colorR, this.colorG, this.colorB, this.colorA, this.velX, this.velY, this.velZ, this.config.renderer.alignToVelocity);
        Vector3 vector3Nor = new Vector3(1.0f, -1.5f, 1.0f).nor();
        Color color = new Color(0.4f, 0.4f, 0.4f, 1.0f);
        ThreeDManager threeDManager = this.engineRef;
        if (threeDManager != null) {
            vector3Nor = threeDManager.getSunLightDirection().cpy().nor();
            color.set(0.3f, 0.3f, 0.3f, 1.0f);
        }
        this.meshBatcher.render(this.camera, vector3Nor, color, this.config.renderer.isAdditive);
        if (this.config.trails.enabled && (shaderProgram = this.shader) != null && shaderProgram.isCompiled()) {
            renderTrails();
        }
    }

    private void ensureTrailMesh() {
        if (this.trailMesh != null) {
            return;
        }
        this.trailVertices = new float[73728];
        this.trailIndices = new short[24576];
        this.trailMesh = new Mesh(false, 8192, 24576, new VertexAttribute(1, 3, ShaderProgram.POSITION_ATTRIBUTE), new VertexAttribute(16, 2, "a_texCoord0"), new VertexAttribute(4, 4, ShaderProgram.COLOR_ATTRIBUTE));
    }

    /* JADX WARN: Code duplicated, block: B:15:0x0031  */
    private void renderTrails() {
        int i;
        int i2;
        int i3;
        if (this.trailData == null || !this.config.trails.enabled) {
            return;
        }
        ensureTrailMesh();
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        int i7 = 0;
        while (i4 < this.aliveCount) {
            if (this.hasTrail[i4]) {
                List<TrailPoint> list = this.trailData.get(i4);
                if (list.size() < 2) {
                    i = i4;
                } else {
                    float f = this.maxLife[i4] * this.config.trails.lifetime;
                    int i8 = 0;
                    while (i8 < list.size() - 1 && (i2 = i5 + 4) <= 8192) {
                        TrailPoint trailPoint = list.get(i8);
                        int i9 = i8 + 1;
                        TrailPoint trailPoint2 = list.get(i9);
                        float f2 = trailPoint2.x - trailPoint.x;
                        float f3 = trailPoint2.y - trailPoint.y;
                        float f4 = trailPoint2.z - trailPoint.z;
                        int i10 = i4;
                        float fSqrt = (float) Math.sqrt((f2 * f2) + (f3 * f3) + (f4 * f4));
                        if (fSqrt < 1.0E-4f) {
                            i3 = i5;
                        } else {
                            float f5 = f2 / fSqrt;
                            float f6 = f3 / fSqrt;
                            float f7 = f4 / fSqrt;
                            float f8 = (this.camera.direction.z * f6) - (this.camera.direction.y * f7);
                            float f9 = (f7 * this.camera.direction.x) - (this.camera.direction.z * f5);
                            float f10 = (f5 * this.camera.direction.y) - (f6 * this.camera.direction.x);
                            i3 = i5;
                            float fSqrt2 = (float) Math.sqrt((f8 * f8) + (f9 * f9) + (f10 * f10));
                            if (fSqrt2 >= 1.0E-4f) {
                                float f11 = f8 / fSqrt2;
                                float f12 = f9 / fSqrt2;
                                float f13 = f10 / fSqrt2;
                                float fClamp = MathUtils.clamp(trailPoint.age / f, 0.0f, 1.0f);
                                float fClamp2 = MathUtils.clamp(trailPoint2.age / f, 0.0f, 1.0f);
                                float fEvaluate = trailPoint.width * this.config.trails.widthOverTrail.evaluate(fClamp) * 0.5f;
                                float fEvaluate2 = trailPoint2.width * this.config.trails.widthOverTrail.evaluate(fClamp2) * 0.5f;
                                int i11 = i7;
                                float floatBits = Color.toFloatBits(trailPoint.r, trailPoint.g, trailPoint.b, trailPoint.a * (1.0f - fClamp));
                                float floatBits2 = Color.toFloatBits(trailPoint2.r, trailPoint2.g, trailPoint2.b, trailPoint2.a * (1.0f - fClamp2));
                                float size = i8 / (list.size() - 1);
                                float size2 = i9 / (list.size() - 1);
                                float f14 = f11 * fEvaluate;
                                this.trailVertices[i6] = trailPoint.x - f14;
                                float f15 = f12 * fEvaluate;
                                this.trailVertices[i6 + 1] = trailPoint.y - f15;
                                float f16 = fEvaluate * f13;
                                this.trailVertices[i6 + 2] = trailPoint.z - f16;
                                float[] fArr = this.trailVertices;
                                fArr[i6 + 3] = size;
                                fArr[i6 + 4] = 0.0f;
                                fArr[i6 + 5] = floatBits;
                                fArr[i6 + 6] = trailPoint.x + f14;
                                this.trailVertices[i6 + 7] = trailPoint.y + f15;
                                this.trailVertices[i6 + 8] = trailPoint.z + f16;
                                float[] fArr2 = this.trailVertices;
                                fArr2[i6 + 9] = size;
                                fArr2[i6 + 10] = 1.0f;
                                fArr2[i6 + 11] = floatBits;
                                float f17 = f11 * fEvaluate2;
                                fArr2[i6 + 12] = trailPoint2.x - f17;
                                float f18 = f12 * fEvaluate2;
                                this.trailVertices[i6 + 13] = trailPoint2.y - f18;
                                float f19 = f13 * fEvaluate2;
                                this.trailVertices[i6 + 14] = trailPoint2.z - f19;
                                float[] fArr3 = this.trailVertices;
                                fArr3[i6 + 15] = size2;
                                fArr3[i6 + 16] = 0.0f;
                                fArr3[i6 + 17] = floatBits2;
                                fArr3[i6 + 18] = trailPoint2.x + f17;
                                this.trailVertices[i6 + 19] = trailPoint2.y + f18;
                                this.trailVertices[i6 + 20] = trailPoint2.z + f19;
                                float[] fArr4 = this.trailVertices;
                                fArr4[i6 + 21] = size2;
                                int i12 = i6 + 23;
                                fArr4[i6 + 22] = 1.0f;
                                i6 += 24;
                                fArr4[i12] = floatBits2;
                                short[] sArr = this.trailIndices;
                                sArr[i11] = (short) i3;
                                short s = (short) (i3 + 1);
                                sArr[i11 + 1] = s;
                                short s2 = (short) (i3 + 2);
                                sArr[i11 + 2] = s2;
                                sArr[i11 + 3] = s;
                                sArr[i11 + 4] = (short) (i3 + 3);
                                sArr[i11 + 5] = s2;
                                i7 = i11 + 6;
                                i5 = i2;
                            }
                            i8 = i9;
                            i4 = i10;
                            f = f;
                            list = list;
                        }
                        i5 = i3;
                        i8 = i9;
                        i4 = i10;
                        f = f;
                        list = list;
                    }
                    i = i4;
                    i7 = i7;
                }
            } else {
                i = i4;
            }
            i4 = i + 1;
        }
        if (i5 == 0) {
            return;
        }
        this.trailMesh.setVertices(this.trailVertices, 0, i6);
        this.trailMesh.setIndices(this.trailIndices, 0, i7);
        this.shader.begin();
        this.shader.setUniformMatrix("u_projViewTrans", this.camera.combined);
        Texture texture = this.texture;
        if (texture != null) {
            texture.bind(0);
            this.shader.setUniformi("u_texture", 0);
        }
        this.trailMesh.render(this.shader, 4, 0, i7);
        this.shader.end();
    }

    public void reconfigure(ParticleSystem3DComponent newConfig) {
        boolean z = false;
        boolean z2 = newConfig.maxParticles != this.maxParticles;
        boolean z3 = this.config.renderer.renderMode != newConfig.renderer.renderMode;
        boolean z4 = this.config.renderer.meshType != newConfig.renderer.meshType;
        String str = this.config.renderer.meshPath;
        String str2 = newConfig.renderer.meshPath;
        if ((str == null) != (str2 == null) || (str != null && !str.equals(str2))) {
            z = true;
        }
        this.config = newConfig;
        if (z2) {
            int i = newConfig.maxParticles;
            this.maxParticles = i;
            this.aliveCount = Math.min(this.aliveCount, i);
            allocateArrays(this.maxParticles);
            if (this.glInitialized) {
                Mesh mesh = this.billboardMesh;
                if (mesh != null) {
                    mesh.dispose();
                }
                buildMesh(this.maxParticles);
            }
            this.meshBatcherDirty = true;
        }
        if (z3 || z4 || z) {
            this.meshBatcherDirty = true;
        }
        this.isPlaying = newConfig.isPlaying;
        this.isAdditive = newConfig.renderer.isAdditive;
    }

    @Override // com.badlogic.gdx.utils.Disposable
    public void dispose() {
        Mesh mesh = this.billboardMesh;
        if (mesh != null) {
            mesh.dispose();
        }
        Mesh mesh2 = this.trailMesh;
        if (mesh2 != null) {
            mesh2.dispose();
        }
        ShaderProgram shaderProgram = this.shader;
        if (shaderProgram != null) {
            shaderProgram.dispose();
        }
        MeshParticleBatcher meshParticleBatcher = this.meshBatcher;
        if (meshParticleBatcher != null) {
            meshParticleBatcher.dispose();
            this.meshBatcher = null;
        }
        this.billboardMesh = null;
        this.trailMesh = null;
        this.shader = null;
        this.glInitialized = false;
    }
}
