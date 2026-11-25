package org.catrobat.catroid.raptor;

import android.util.Log;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cubemap;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Attribute;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.RenderableProvider;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.DepthTestAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.g3d.environment.SpotLight;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.model.Animation;
import com.badlogic.gdx.graphics.g3d.model.MeshPart;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.model.NodePart;
import com.badlogic.gdx.graphics.g3d.particles.ParticleController;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffect;
import com.badlogic.gdx.graphics.g3d.particles.ParticleShader;
import com.badlogic.gdx.graphics.g3d.particles.ParticleSystem;
import com.badlogic.gdx.graphics.g3d.particles.batches.BillboardParticleBatch;
import com.badlogic.gdx.graphics.g3d.particles.batches.PointSpriteParticleBatch;
import com.badlogic.gdx.graphics.g3d.particles.emitters.RegularEmitter;
import com.badlogic.gdx.graphics.g3d.particles.influencers.ColorInfluencer;
import com.badlogic.gdx.graphics.g3d.particles.influencers.DynamicsInfluencer;
import com.badlogic.gdx.graphics.g3d.particles.influencers.DynamicsModifier;
import com.badlogic.gdx.graphics.g3d.particles.influencers.Influencer;
import com.badlogic.gdx.graphics.g3d.particles.influencers.RegionInfluencer;
import com.badlogic.gdx.graphics.g3d.particles.influencers.ScaleInfluencer;
import com.badlogic.gdx.graphics.g3d.particles.influencers.SpawnInfluencer;
import com.badlogic.gdx.graphics.g3d.particles.renderers.BillboardRenderer;
import com.badlogic.gdx.graphics.g3d.particles.values.CylinderSpawnShapeValue;
import com.badlogic.gdx.graphics.g3d.particles.values.EllipseSpawnShapeValue;
import com.badlogic.gdx.graphics.g3d.particles.values.PointSpawnShapeValue;
import com.badlogic.gdx.graphics.g3d.particles.values.PrimitiveSpawnShapeValue;
import com.badlogic.gdx.graphics.g3d.particles.values.RectangleSpawnShapeValue;
import com.badlogic.gdx.graphics.g3d.particles.values.ScaledNumericValue;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.graphics.g3d.utils.DefaultShaderProvider;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ShaderProvider;
import com.badlogic.gdx.graphics.g3d.utils.shapebuilders.CylinderShapeBuilder;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Vector4;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.DebugDrawer;
import com.badlogic.gdx.physics.bullet.collision.ClosestRayResultCallback;
import com.badlogic.gdx.physics.bullet.collision.ContactResultCallback;
import com.badlogic.gdx.physics.bullet.collision.btBoxShape;
import com.badlogic.gdx.physics.bullet.collision.btBroadphaseInterface;
import com.badlogic.gdx.physics.bullet.collision.btBvhTriangleMeshShape;
import com.badlogic.gdx.physics.bullet.collision.btCapsuleShape;
import com.badlogic.gdx.physics.bullet.collision.btCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.collision.btCollisionDispatcher;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObjectWrapper;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.physics.bullet.collision.btCompoundShape;
import com.badlogic.gdx.physics.bullet.collision.btDbvtBroadphase;
import com.badlogic.gdx.physics.bullet.collision.btDefaultCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.collision.btDispatcher;
import com.badlogic.gdx.physics.bullet.collision.btManifoldPoint;
import com.badlogic.gdx.physics.bullet.collision.btSphereShape;
import com.badlogic.gdx.physics.bullet.collision.btStridingMeshInterface;
import com.badlogic.gdx.physics.bullet.collision.btTriangleIndexVertexArray;
import com.badlogic.gdx.physics.bullet.dynamics.btConstraintSolver;
import com.badlogic.gdx.physics.bullet.dynamics.btDiscreteDynamicsWorld;
import com.badlogic.gdx.physics.bullet.dynamics.btFixedConstraint;
import com.badlogic.gdx.physics.bullet.dynamics.btGeneric6DofSpringConstraint;
import com.badlogic.gdx.physics.bullet.dynamics.btHingeConstraint;
import com.badlogic.gdx.physics.bullet.dynamics.btPoint2PointConstraint;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.badlogic.gdx.physics.bullet.dynamics.btSequentialImpulseConstraintSolver;
import com.badlogic.gdx.physics.bullet.dynamics.btTypedConstraint;
import com.badlogic.gdx.physics.bullet.linearmath.btDefaultMotionState;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.async.AsyncExecutor;
import com.badlogic.gdx.utils.async.AsyncTask;
import com.crashinvaders.vfx.VfxManager;
import com.crashinvaders.vfx.effects.BloomEffect;
import com.crashinvaders.vfx.effects.ChainVfxEffect;
import com.crashinvaders.vfx.effects.ChromaticAberrationEffect;
import com.crashinvaders.vfx.effects.CrtEffect;
import com.crashinvaders.vfx.effects.FilmGrainEffect;
import com.crashinvaders.vfx.effects.FisheyeEffect;
import com.crashinvaders.vfx.effects.FxaaEffect;
import com.crashinvaders.vfx.effects.GaussianBlurEffect;
import com.crashinvaders.vfx.effects.LevelsEffect;
import com.crashinvaders.vfx.effects.MotionBlurEffect;
import com.crashinvaders.vfx.effects.OldTvEffect;
import com.crashinvaders.vfx.effects.ShaderVfxEffect;
import com.crashinvaders.vfx.effects.VfxEffect;
import com.crashinvaders.vfx.effects.VignettingEffect;
import com.crashinvaders.vfx.effects.WaterDistortionEffect;
import com.crashinvaders.vfx.effects.ZoomEffect;
import com.crashinvaders.vfx.effects.util.MixEffect;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import name.antonsmirnov.firmata.FormatHelper;
import net.mgsx.gltf.loaders.glb.GLBLoader;
import net.mgsx.gltf.loaders.gltf.GLTFLoader;
import net.mgsx.gltf.scene3d.attributes.PBRColorAttribute;
import net.mgsx.gltf.scene3d.attributes.PBRCubemapAttribute;
import net.mgsx.gltf.scene3d.attributes.PBRFloatAttribute;
import net.mgsx.gltf.scene3d.attributes.PBRTextureAttribute;
import net.mgsx.gltf.scene3d.lights.DirectionalLightEx;
import net.mgsx.gltf.scene3d.lights.DirectionalShadowLight;
import net.mgsx.gltf.scene3d.lights.PointLightEx;
import net.mgsx.gltf.scene3d.lights.SpotLightEx;
import net.mgsx.gltf.scene3d.scene.CascadeShadowMap;
import net.mgsx.gltf.scene3d.scene.Scene;
import net.mgsx.gltf.scene3d.scene.SceneAsset;
import net.mgsx.gltf.scene3d.scene.SceneSkybox;
import net.mgsx.gltf.scene3d.shaders.PBRShaderConfig;
import net.mgsx.gltf.scene3d.shaders.PBRShaderProvider;
import net.mgsx.gltf.scene3d.utils.IBLBuilder;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.RenderTextureManager;
import org.catrobat.catroid.raptor.particles.ParticleSystem3DRuntime;
import org.catrobat.catroid.raptor.postprocessing.AutoLensFlareEffect;
import org.catrobat.catroid.raptor.postprocessing.CustomScreenShaderEffect;
import org.catrobat.catroid.raptor.postprocessing.CustomShaderAttribute;
import org.catrobat.catroid.raptor.postprocessing.DepthOfFieldEffect;
import org.catrobat.catroid.raptor.postprocessing.ExposureEffect;
import org.catrobat.catroid.raptor.postprocessing.EyeAdaptationManager;
import org.catrobat.catroid.raptor.postprocessing.GodRaysEffect;
import org.catrobat.catroid.raptor.postprocessing.HeightFogEffect;
import org.catrobat.catroid.raptor.postprocessing.LinearizeEffect;
import org.catrobat.catroid.raptor.postprocessing.SsaoEffect;
import org.catrobat.catroid.raptor.postprocessing.SsgiEffect;
import org.catrobat.catroid.raptor.postprocessing.SsrRayTracingEffect;
import org.catrobat.catroid.raptor.postprocessing.TonemappingEffect;
import org.catrobat.catroid.raptor.postprocessing.UniversalPBRShaderProvider;
import org.catrobat.catroid.raptor.postprocessing.VolumetricFogEffect;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.stage.StageListener;
import org.catrobat.catroid.utils.ModelPathProcessor;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/ThreeDManager.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ThreeDManager implements Disposable {
    private static final boolean LOG_THREED_MANAGER_DEBUG = false;
    private static final float MAX_HEARING_DISTANCE = 250.0f;
    private PointSpriteParticleBatch batchAdditive;
    private PointSpriteParticleBatch batchNormal;
    private SpriteBatch blitBatch;
    private OrthographicCamera blitCamera;
    private Texture brdfLUT;
    private btBroadphaseInterface broadphase;
    private BloomEffect cachedBloomEffect;
    private GaussianBlurEffect cachedGaussianBlurEffect;
    private LevelsEffect cachedLevelsEffect;
    private MotionBlurEffect cachedMotionBlurEffect;
    private VignettingEffect cachedVignetteEffect;
    private PerspectiveCamera camera;
    private Model cameraProxyModel;
    private CollisionCallback collisionCallback;
    private btCollisionConfiguration collisionConfig;
    private NameAccumulatingContactCallback contactListCallback;
    private CascadeShadowMap csm;
    private SceneSettings currentSettings;
    private PostProcessingData.Upscaler currentUpscaler;
    private CustomScreenShaderEffect customScreenEffect;
    private ShaderProvider customShaderProvider;
    private DebugDrawer debugDrawer;
    private Texture defaultParticleTexture;
    private ShaderProvider defaultShaderProvider;
    private ModelBatch depthBatch;
    private FrameBuffer depthFbo;
    private ShaderProgram depthShader;
    private DepthShader depthShaderWrapper;
    private Cubemap diffuseCubemap;
    private btDispatcher dispatcher;
    private DepthOfFieldEffect dofEffect;
    private btDiscreteDynamicsWorld dynamicsWorld;
    private Environment environment;
    private ExposureEffect exposureEffect;
    private EyeAdaptationManager eyeAdaptationManager;
    private ShaderProgram fsrShader;
    private GodRaysEffect godRaysEffect;
    private ModelInstance gridInstance;
    private Scene gridScene;
    private HeightFogEffect heightFogEffect;
    private IBLBuilderCompat iblBuilderCompat;
    private int lastScreenHeight;
    private int lastScreenWidth;
    private int lastTouchX;
    private int lastTouchY;
    private Model lightProxyModel;
    private LinearizeEffect linearizeEffect;
    private SceneManager manager;
    private ModelBatch materialBatch;
    private FrameBuffer materialFbo;
    private ModelBatch modelBatch;
    private ModelBuilder modelBuilder;
    private PanoramicConverter panoramicConverter;
    private ModelBatch particleModelBatch;
    private Model particleProxyModel;
    private ParticleSystem particleSystem;
    private DirectionalLightEx pbrLight;
    private SsrRayTracingEffect rayTracingEffect;
    private int renderHeight;
    private int renderWidth;
    private FrameBuffer sceneFbo2;
    private TextureRegion sceneFboRegion;
    private net.mgsx.gltf.scene3d.scene.SceneManager sceneManager;
    private SceneSkybox skybox;
    private Cubemap skyboxCubemap;
    private btConstraintSolver solver;
    private Cubemap specularCubemap;
    private SsaoEffect ssaoEffect;
    private SsgiEffect ssgiEffect;
    private TonemappingEffect tonemappingEffect;
    private VfxManager vfxManager;
    private VolumetricFogEffect volumetricFogEffect;
    private ModelBatch wireframeBatch;
    private Model wireframeBoxModel;
    private Model wireframeCylinderModel;
    private Model wireframeSphereModel;
    private final Array<RaySensor> continuousRaySensors = new Array<>();
    private final Map<String, AudioAsset> loadedAudioAssets = new HashMap();
    private final List<PlayableAudio> active3DSounds = new ArrayList();
    private float globalSoundVolume = 1.0f;
    private final Vector3 cameraRight = new Vector3();
    private final Vector3 soundToListener = new Vector3();
    private final boolean debugEnabled = false;
    public Color skyColor = new Color(0.0f, 0.0f, 0.0f, 0.0f);
    private boolean editorMode = false;
    private Map<String, Model> loadedModels = new HashMap();
    private Map<String, Texture> loadedTextures = new HashMap();
    private Map<String, ModelInstance> sceneObjects = new HashMap();
    private Map<String, DirectionalLight> directionalLights = new HashMap();
    private final BoundingBox bounds1 = new BoundingBox();
    private final BoundingBox bounds2 = new BoundingBox();
    private Map<String, btRigidBody> physicsBodies = new HashMap();
    private Map<String, RayCastResult> rayCastResults = new HashMap();
    private final Map<String, Object> customUniforms = new HashMap();
    private float time = 0.0f;
    private boolean realisticMode = false;
    private final Map<String, PointLightEx> pointLights = new HashMap();
    private final Map<String, SpotLightEx> spotLights = new HashMap();
    private final Set<String> gltfObjectIds = new HashSet();
    private final Map<String, AnimationController> animationControllers = new HashMap();
    private final Map<String, ModelInstance> editorProxies = new HashMap();
    public String cameraTargetId = null;
    private final Vector3 cameraOffset = new Vector3();
    private float cameraDistance = 10.0f;
    private float cameraPitch = 20.0f;
    private float cameraYaw = 0.0f;
    private Map<String, btTypedConstraint> physicsConstraints = new HashMap();
    private final Set<String> inactiveRenderObjects = new HashSet();
    private final Map<String, btRigidBody> inactivePhysicsBodies = new HashMap();
    private final Map<String, Scene> inactivePbrScenes = new HashMap();
    private final Vector3 tmpPos = new Vector3();
    private final Quaternion tmpRot = new Quaternion();
    private final Vector3 tmpScale = new Vector3();
    public boolean postprocessingEnabled = false;
    public final Map<String, ParticleEffect> activeParticleEffects = new HashMap();
    private final List<ParticleEffect> effectsNormal = new ArrayList();
    private final List<ParticleEffect> effectsAdditive = new ArrayList();
    private final Map<String, BillboardParticleBatch> managedBatches = new HashMap();
    private final List<VfxEffect> userEffects = new ArrayList();
    private final Set<String> hiddenSpawnIds = new HashSet();
    private final Map<String, AIComponent> activeAIs = new HashMap();
    private final Map<String, Vector3> aiLastKnownPositions = new HashMap();
    private final Map<String, Boolean> aiHasLostTarget = new HashMap();
    private final Map<String, Vector3> aiSmoothedDirection = new HashMap();
    private float shakeIntensity = 0.0f;
    private float shakeDuration = 0.0f;
    private float shakeTimer = 0.0f;
    private final Vector3 currentShakeOffset = new Vector3();
    private boolean touchRotationEnabled = false;
    private float sensitivity = 0.2f;
    private boolean blockByUI = true;
    private float rotationAreaX = 0.0f;
    private float rotationAreaY = 0.0f;
    private float rotationAreaW = 100.0f;
    private float rotationAreaH = 100.0f;
    private int activePointerId = -1;
    private final ByteBuffer dofPixelsBuffer = BufferUtils.newByteBuffer(16);
    private final Vector3 tpTargetPos = new Vector3();
    private final Vector3 tpLookAt = new Vector3();
    private final Quaternion tpRot = new Quaternion();
    private final Quaternion tpPitchRot = new Quaternion();
    private final Vector3 tpPosOffset = new Vector3();
    private final Vector3 slCenter = new Vector3();
    private final Vector3 slForward = new Vector3();
    private int maxActivePointLights = 15;
    private final List<PointLightEx> sortedPointLights = new ArrayList();
    public String cameraTrackTargetId = null;
    public int cameraTrackMode = 0;
    public final Vector3 cameraTrackPosOffset = new Vector3();
    public final Quaternion cameraTrackRotOffset = new Quaternion();
    private float renderScale = 1.0f;
    private int aspectMode = 0;
    private final Map<String, SceneAsset> loadedGltfAssets = new HashMap();
    private boolean disposed = false;
    public final List<CameraAttachment> cameraAttachments = new ArrayList();
    private final Matrix4 tmpMat1 = new Matrix4();
    private final Map<String, ParticleSystem3DRuntime> activeParticleRuntimes3D = new HashMap();
    private boolean useCSM = false;
    private float csmSplitFactor = 4.0f;
    private final Vector3 tmpSensorPos = new Vector3();
    private final Vector3 tmpSensorDir = new Vector3();
    private final Vector3 tmpSensorEnd = new Vector3();
    private final Map<String, Quaternion> modelRotationOffsets = new HashMap();
    private final Map<String, Array<Vector3>> targetTrails = new HashMap();
    private float trailTimer = 0.0f;
    private final Vector3 aiCurrentPos = new Vector3();
    private final Vector3 aiFinalMoveTarget = new Vector3();
    private final Vector3 aiRealTargetPos = new Vector3();
    private final Vector3 aiDesiredDir = new Vector3();
    private final Vector3 aiBestMoveDir = new Vector3();
    private final Vector3 aiRayDir = new Vector3();
    private final Vector3 aiStepPos = new Vector3();
    private final Map<String, Array<Vector3>> aiPathHistory = new HashMap();
    private final Map<String, Array<DeadEndZone>> aiDeadEnds = new HashMap();
    private final Map<String, Vector3> aiLastTargetPos = new HashMap();
    private final Map<String, Vector3> aiBacktrackTarget = new HashMap();
    private final Map<String, Integer> aiSlideDirs = new HashMap();
    private final Map<String, Float> aiCurrentYaws = new HashMap();
    private boolean particleSystemInitialized = false;
    private final Map<FrameBuffer, BufferPipeline> bufferPipelines = new HashMap();
    public PostProcessingComponent currentConfig = new PostProcessingComponent();
    private boolean isVfxCapturing = false;
    private final Vector3 cyanColorVec = new Vector3(Color.CYAN.r, Color.CYAN.g, Color.CYAN.b);
    private final Vector3 tmpVec = new Vector3();
    private final List<BoneAttachment> activeAttachments = new ArrayList();
    private boolean isDepthRenderEnabled = false;
    private int targetFps = 0;
    private float renderTimeAccumulator = 0.0f;
    private Texture lastRenderedTexture = null;
    private float currentShadowSize = 100.0f;
    private int currentShadowResolution = 2048;
    private final Map<String, Array<Disposable>> physicsResources = new HashMap();
    private final List<BoneOverride> boneOverrides = new ArrayList();
    private final AsyncExecutor voxelExecutor = new AsyncExecutor(1);

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/ThreeDManager$PhysicsShape.class
      classes14.dex
     */
    public enum PhysicsShape {
        BOX,
        SPHERE,
        CAPSULE
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/ThreeDManager$PhysicsState.class
      classes14.dex
     */
    public enum PhysicsState {
        NONE,
        STATIC,
        DYNAMIC,
        MESH_STATIC,
        KINEMATIC
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/ThreeDManager$SceneSettings.class
      classes14.dex
     */
    public static class SceneSettings {
        public int numPointLights = 5;
        public int numSpotLights = 2;
        public int numDirectionalLights = 1;
        public int numBones = 110;
    }

    public static float map(float value, float inMin, float inMax, float outMin, float outMax) {
        return (((value - inMin) * (outMax - outMin)) / (inMax - inMin)) + outMin;
    }

    public ModelBatch getWireframeBatch() {
        return this.wireframeBatch;
    }

    public void resize(int width, int height) {
        net.mgsx.gltf.scene3d.scene.SceneManager sceneManager;
        this.lastScreenWidth = width;
        this.lastScreenHeight = height;
        float f = width;
        this.camera.viewportWidth = f;
        float f2 = height;
        this.camera.viewportHeight = f2;
        this.camera.update();
        float f3 = this.renderScale;
        if (f3 < 1.0f || this.aspectMode != 0) {
            int i = (int) (f2 * f3);
            this.renderHeight = i;
            int i2 = this.aspectMode;
            if (i2 == 1) {
                int i3 = (int) (i * 1.3333334f);
                this.renderWidth = i3;
                if (i3 > ((int) (f * f3))) {
                    int i4 = (int) (f3 * f);
                    this.renderWidth = i4;
                    this.renderHeight = (int) (i4 * 0.75f);
                }
            } else if (i2 == 2) {
                int i5 = (int) (i * 1.7777778f);
                this.renderWidth = i5;
                if (i5 > ((int) (f * f3))) {
                    int i6 = (int) (f3 * f);
                    this.renderWidth = i6;
                    this.renderHeight = (int) (i6 * 0.5625f);
                }
            } else if (i2 == 3) {
                this.renderWidth = i;
                if (i > ((int) (f * f3))) {
                    int i7 = (int) (f3 * f);
                    this.renderWidth = i7;
                    this.renderHeight = i7;
                }
            } else {
                this.renderWidth = (int) (f * f3);
                this.renderHeight = (int) (f3 * f2);
            }
            this.camera.viewportWidth = this.renderWidth;
            this.camera.viewportHeight = this.renderHeight;
            this.camera.update();
        } else {
            this.renderWidth = width;
            this.renderHeight = height;
        }
        VfxManager vfxManager = this.vfxManager;
        if (vfxManager != null) {
            vfxManager.resize(this.renderWidth, this.renderHeight);
        }
        OrthographicCamera orthographicCamera = this.blitCamera;
        if (orthographicCamera != null) {
            orthographicCamera.setToOrtho(false, f, f2);
            this.blitCamera.update();
        }
        FrameBuffer frameBuffer = this.sceneFbo2;
        if (frameBuffer != null) {
            frameBuffer.dispose();
        }
        FrameBuffer frameBuffer2 = new FrameBuffer(Pixmap.Format.RGBA8888, this.renderWidth, this.renderHeight, true);
        this.sceneFbo2 = frameBuffer2;
        this.sceneFboRegion.setRegion(frameBuffer2.getColorBufferTexture());
        this.sceneFboRegion.flip(false, true);
        FrameBuffer frameBuffer3 = this.depthFbo;
        if (frameBuffer3 != null) {
            frameBuffer3.dispose();
        }
        FrameBuffer frameBuffer4 = new FrameBuffer(Pixmap.Format.RGBA8888, this.renderWidth, this.renderHeight, true);
        this.depthFbo = frameBuffer4;
        frameBuffer4.getColorBufferTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        FrameBuffer frameBuffer5 = this.materialFbo;
        if (frameBuffer5 != null) {
            frameBuffer5.dispose();
        }
        this.materialFbo = new FrameBuffer(Pixmap.Format.RGBA8888, this.renderWidth / 2, this.renderHeight / 2, true);
        PostProcessingComponent postProcessingComponent = this.currentConfig;
        if (postProcessingComponent != null) {
            updatePostProcessing(postProcessingComponent);
        }
        if (!this.realisticMode || (sceneManager = this.sceneManager) == null) {
            return;
        }
        sceneManager.updateViewport(this.renderWidth, this.renderHeight);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/ThreeDManager$AudioAsset.class
      classes14.dex
     */
    private static class AudioAsset {
        public final String filePath;
        public Music music;
        public Sound sound;

        public AudioAsset(FileHandle fileHandle, boolean asMusic) {
            this.sound = null;
            this.music = null;
            this.filePath = fileHandle.path();
            if (asMusic) {
                this.music = Gdx.audio.newMusic(fileHandle);
            } else {
                this.sound = Gdx.audio.newSound(fileHandle);
            }
        }

        public void dispose() {
            Sound sound = this.sound;
            if (sound != null) {
                sound.dispose();
            }
            Music music = this.music;
            if (music != null) {
                music.dispose();
            }
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/ThreeDManager$RaySensor.class
      classes14.dex
     */
    private static class RaySensor {
        String attachedObjectId;
        float distance;
        Vector3 localDirection;
        Vector3 localOffset;
        String rayName;

        private RaySensor() {
            this.localOffset = new Vector3();
            this.localDirection = new Vector3();
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/ThreeDManager$RayCastResult.class
      classes14.dex
     */
    private static class RayCastResult {
        public boolean hasHit;
        public float hitDistance;
        public final Vector3 hitNormal;
        public String hitObjectId;
        public final Vector3 hitPoint;

        private RayCastResult() {
            this.hasHit = false;
            this.hitObjectId = "";
            this.hitDistance = -1.0f;
            this.hitPoint = new Vector3();
            this.hitNormal = new Vector3();
        }
    }

    public boolean isDisposed() {
        return this.disposed;
    }

    public void init() {
        init(new SceneSettings());
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void init(SceneSettings sceneSettings) {
        this.currentSettings = sceneSettings;
        this.modelBuilder = new ModelBuilder();
        this.defaultShaderProvider = new DefaultShaderProvider();
        this.modelBatch = new ModelBatch(this.defaultShaderProvider);
        this.environment = new Environment();
        setAmbientLight(0.4f, 0.4f, 0.4f);
        this.camera = new PerspectiveCamera(67.0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        setCameraPosition(100.0f, 100.0f, 100.0f);
        cameraLookAt(0.0f, 0.0f, 0.0f);
        this.camera.near = 0.1f;
        this.camera.far = 2500.0f;
        Bullet.init();
        this.collisionConfig = new btDefaultCollisionConfiguration();
        this.dispatcher = new btCollisionDispatcher(this.collisionConfig);
        this.broadphase = new btDbvtBroadphase();
        this.solver = new btSequentialImpulseConstraintSolver();
        btDiscreteDynamicsWorld btdiscretedynamicsworld = new btDiscreteDynamicsWorld(this.dispatcher, this.broadphase, this.solver, this.collisionConfig);
        this.dynamicsWorld = btdiscretedynamicsworld;
        btdiscretedynamicsworld.setGravity(new Vector3(0.0f, -9.81f, 0.0f));
        this.dynamicsWorld.getSolverInfo().setNumIterations(20);
        DebugDrawer debugDrawer = new DebugDrawer();
        this.debugDrawer = debugDrawer;
        debugDrawer.setDebugMode(32769);
        this.dynamicsWorld.setDebugDrawer(this.debugDrawer);
        Object[] objArr = 0;
        this.collisionCallback = new CollisionCallback();
        PBRShaderConfig pBRShaderConfigCreateDefaultConfig = PBRShaderProvider.createDefaultConfig();
        pBRShaderConfigCreateDefaultConfig.numBones = sceneSettings.numBones;
        pBRShaderConfigCreateDefaultConfig.numPointLights = sceneSettings.numPointLights;
        pBRShaderConfigCreateDefaultConfig.numSpotLights = sceneSettings.numSpotLights;
        pBRShaderConfigCreateDefaultConfig.numDirectionalLights = sceneSettings.numDirectionalLights;
        UniversalPBRShaderProvider universalPBRShaderProvider = new UniversalPBRShaderProvider(pBRShaderConfigCreateDefaultConfig);
        DefaultShader.Config config = universalPBRShaderProvider.config;
        config.numPointLights = sceneSettings.numPointLights;
        config.numSpotLights = sceneSettings.numSpotLights;
        config.numDirectionalLights = sceneSettings.numDirectionalLights;
        config.numBones = sceneSettings.numBones;
        net.mgsx.gltf.scene3d.scene.SceneManager sceneManager = new net.mgsx.gltf.scene3d.scene.SceneManager(universalPBRShaderProvider, PBRShaderProvider.createDefaultDepth(config.numBones));
        this.sceneManager = sceneManager;
        sceneManager.setCamera(this.camera);
        DirectionalShadowLight directionalShadowLight = new DirectionalShadowLight(2048, 2048);
        directionalShadowLight.direction.set(1.0f, -1.5f, 1.0f).nor();
        directionalShadowLight.color.set(Color.WHITE);
        directionalShadowLight.intensity = 5.0f;
        directionalShadowLight.getCamera().far = 1000.0f;
        this.sceneManager.environment.add((DirectionalLight) directionalShadowLight);
        this.pbrLight = directionalShadowLight;
        IBLBuilder iBLBuilderCreateOutdoor = IBLBuilder.createOutdoor(directionalShadowLight);
        this.diffuseCubemap = iBLBuilderCreateOutdoor.buildIrradianceMap(256);
        this.specularCubemap = iBLBuilderCreateOutdoor.buildRadianceMap(10);
        iBLBuilderCreateOutdoor.dispose();
        this.brdfLUT = new Texture(Gdx.files.classpath("net/mgsx/gltf/shaders/brdfLUT.png"));
        this.sceneManager.environment.set(new PBRTextureAttribute(PBRTextureAttribute.BRDFLUTTexture, this.brdfLUT));
        this.sceneManager.environment.set(new PBRCubemapAttribute(PBRCubemapAttribute.DiffuseEnv, this.diffuseCubemap));
        this.sceneManager.environment.set(new PBRCubemapAttribute(PBRCubemapAttribute.SpecularEnv, this.specularCubemap));
        this.lightProxyModel = this.modelBuilder.createSphere(0.25f, 0.25f, 0.25f, 8, 8, new Material(ColorAttribute.createDiffuse(Color.YELLOW)), 9L);
        this.particleProxyModel = this.modelBuilder.createSphere(0.25f, 0.25f, 0.25f, 8, 8, new Material(ColorAttribute.createDiffuse(Color.MAGENTA)), 9L);
        Material material = new Material(ColorAttribute.createDiffuse(Color.CYAN));
        Model modelCreateBox = this.modelBuilder.createBox(0.4f, 0.4f, 0.4f, material, 9L);
        Model modelCreateCone = this.modelBuilder.createCone(0.4f, 0.5f, 0.4f, 4, material, 9L);
        ModelBuilder modelBuilder = new ModelBuilder();
        modelBuilder.begin();
        modelBuilder.part("box", modelCreateBox.meshes.get(0), 4, material);
        Node node = modelBuilder.node();
        node.f104id = "cone";
        node.rotation.set(Vector3.X, 90.0f);
        node.translation.set(0.0f, 0.0f, -0.2f);
        modelBuilder.part("cone", modelCreateCone.meshes.get(0), 4, material);
        this.cameraProxyModel = modelBuilder.end();
        this.wireframeBatch = new ModelBatch();
        Material material2 = new Material(ColorAttribute.createDiffuse(Color.WHITE));
        this.wireframeBoxModel = this.modelBuilder.createBox(1.0f, 1.0f, 1.0f, 1, material2, 9L);
        this.wireframeSphereModel = this.modelBuilder.createSphere(1.0f, 1.0f, 1.0f, 16, 12, 1, material2, 9L);
        this.wireframeCylinderModel = this.modelBuilder.createCylinder(1.0f, 2.0f, 1.0f, 16, 1, material2, 9L);
        this.vfxManager = new VfxManager(Pixmap.Format.RGBA8888);
        this.sceneFboRegion = new TextureRegion();
        this.blitBatch = new SpriteBatch();
        this.blitCamera = new OrthographicCamera();
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        ShaderProgram shaderProgram = new ShaderProgram("attribute vec3 a_position;\nattribute vec3 a_normal;\n#ifdef boneWeight0Flag\nattribute vec2 a_boneWeight0;\n#endif\n#ifdef boneWeight1Flag\nattribute vec2 a_boneWeight1;\n#endif\n#ifdef boneWeight2Flag\nattribute vec2 a_boneWeight2;\n#endif\n#ifdef boneWeight3Flag\nattribute vec2 a_boneWeight3;\n#endif\n#ifdef numBones\nuniform mat4 u_bones[numBones];\n#endif\nuniform mat4 u_projViewTrans;\nuniform mat4 u_worldTrans;\nuniform mat4 u_viewTrans;\nvarying vec3 v_normal;\nvarying float v_viewZ;\nvoid main() {\n    mat4 skinning = mat4(1.0);\n    #ifdef numBones\n    skinning = (a_boneWeight0.y * u_bones[int(a_boneWeight0.x)])\n               + (a_boneWeight1.y * u_bones[int(a_boneWeight1.x)])\n               + (a_boneWeight2.y * u_bones[int(a_boneWeight2.x)])\n               + (a_boneWeight3.y * u_bones[int(a_boneWeight3.x)]);\n    #endif\n    vec4 wpos = u_worldTrans * skinning * vec4(a_position, 1.0);\n    vec4 vpos = u_viewTrans * wpos;\n    v_viewZ = -vpos.z;\n    v_normal = normalize(mat3(u_worldTrans) * a_normal);\n    gl_Position = u_projViewTrans * wpos;\n}", "#ifdef GL_ES\nprecision highp float;\n#endif\nvarying vec3 v_normal;\nvarying float v_viewZ;\nuniform float u_farPlane;\nuniform float u_objReflectivity;\nvec2 packNormal(vec3 v) {\n    vec2 p = v.xy * (1.0 / (abs(v.x) + abs(v.y) + abs(v.z)));\n    if (v.z <= 0.0) p = (1.0 - abs(p.yx)) * vec2(v.x >= 0.0 ? 1.0 : -1.0, v.y >= 0.0 ? 1.0 : -1.0);\n    return p * 0.5 + 0.5;\n}\nvec2 packDepth(float d) {\n    float n = clamp(d / u_farPlane, 0.0, 1.0);\n    vec2 enc = vec2(1.0, 255.0) * n;\n    enc = fract(enc);\n    enc.x -= enc.y * (1.0/255.0);\n    return enc;\n}\nvoid main() {\n    vec2 d = packDepth(v_viewZ);\n    gl_FragColor = vec4(d.x, d.y, packNormal(normalize(v_normal)).x, u_objReflectivity * 0.00001 + packNormal(normalize(v_normal)).y);\n}");
        this.depthShader = shaderProgram;
        if (!shaderProgram.isCompiled()) {
            Log.e("3DManager", "Depth Shader Error: " + this.depthShader.getLog());
        }
        this.depthShaderWrapper = new DepthShader(this.depthShader);
        this.depthBatch = new ModelBatch(new DefaultShaderProvider() { // from class: org.catrobat.catroid.raptor.ThreeDManager.1
            @Override // com.badlogic.gdx.graphics.g3d.utils.DefaultShaderProvider, com.badlogic.gdx.graphics.g3d.utils.BaseShaderProvider
            protected Shader createShader(Renderable renderable) {
                return ThreeDManager.this.depthShaderWrapper;
            }
        });
        this.materialBatch = new ModelBatch(new MaterialShaderProvider());
        ShaderProgram shaderProgram2 = new ShaderProgram("attribute vec4 a_position;\nattribute vec4 a_color;\nattribute vec2 a_texCoord0;\nuniform mat4 u_projTrans;\nvarying vec4 v_color;\nvarying vec2 v_texCoords;\nvoid main() {\n    v_color = a_color;\n    v_texCoords = a_texCoord0;\n    gl_Position = u_projTrans * a_position;\n}", "#ifdef GL_ES\nprecision highp float;\n#endif\nvarying vec4 v_color;\nvarying vec2 v_texCoords;\nuniform sampler2D u_texture;\nuniform vec2 u_texelSize;\nuniform float u_sharpness;\nvoid main() {\n    vec3 e = texture2D(u_texture, v_texCoords).rgb;\n    vec3 b = texture2D(u_texture, v_texCoords + vec2(0.0, -u_texelSize.y)).rgb;\n    vec3 d = texture2D(u_texture, v_texCoords + vec2(-u_texelSize.x, 0.0)).rgb;\n    vec3 f = texture2D(u_texture, v_texCoords + vec2(u_texelSize.x, 0.0)).rgb;\n    vec3 h = texture2D(u_texture, v_texCoords + vec2(0.0, u_texelSize.y)).rgb;\n    \n    vec3 minRGB = min(min(min(b, d), min(e, f)), h);\n    vec3 maxRGB = max(max(max(b, d), max(e, f)), h);\n    vec3 contrast = maxRGB - minRGB;\n    \n    vec3 w = u_sharpness * (1.0 - contrast);\n    w = clamp(w, 0.0, 1.0);\n    \n    vec3 finalColor = (b + d + f + h) * w + e;\n    finalColor = finalColor / (4.0 * w + 1.0);\n    gl_FragColor = vec4(finalColor, 1.0) * v_color;\n}");
        this.fsrShader = shaderProgram2;
        if (!shaderProgram2.isCompiled()) {
            Gdx.f82app.error("FSR Shader", this.fsrShader.getLog());
        }
        this.eyeAdaptationManager = new EyeAdaptationManager();
        this.currentConfig.isActive = false;
        this.currentConfig.effects.clear();
        this.currentConfig.qualityScale = 1.0f;
        updatePostProcessing(this.currentConfig);
        this.panoramicConverter = new PanoramicConverter();
        this.iblBuilderCompat = new IBLBuilderCompat();
        createDefaultParticleTexture();
        this.contactListCallback = new NameAccumulatingContactCallback();
    }

    public void setObjectCustomShader(String objectId, final String vertexCode, final String fragmentCode) {
        final ModelInstance modelInstance = this.sceneObjects.get(objectId);
        if (modelInstance == null) {
            return;
        }
        Gdx.f82app.postRunnable(new Runnable() { // from class: org.catrobat.catroid.raptor.ThreeDManager$$ExternalSyntheticLambda18
            @Override // java.lang.Runnable
            public final void run() {
                ThreeDManager.lambda$setObjectCustomShader$0(vertexCode, fragmentCode, modelInstance);
            }
        });
    }

    static /* synthetic */ void lambda$setObjectCustomShader$0(String str, String str2, ModelInstance modelInstance) {
        try {
            ShaderProgram shaderProgram = new ShaderProgram(str, str2);
            if (!shaderProgram.isCompiled()) {
                throw new Exception("Shader compile error: " + shaderProgram.getLog());
            }
            CustomShaderAttribute customShaderAttribute = new CustomShaderAttribute(shaderProgram);
            Array.ArrayIterator<Material> it = modelInstance.materials.iterator();
            while (it.hasNext()) {
                it.next().set(customShaderAttribute);
            }
        } catch (Exception e) {
            Gdx.f82app.error("3DManager", "Failed to compile object shader: " + e.getMessage());
        }
    }

    public void setObjectShaderUniform(String objectId, final String name2, final float v1, final float v2, final float v3, final int paramCount) {
        CustomShaderAttribute customShaderAttribute;
        ModelInstance modelInstance = this.sceneObjects.get(objectId);
        if (modelInstance == null) {
            return;
        }
        Array.ArrayIterator<Material> it = modelInstance.materials.iterator();
        while (true) {
            if (!it.hasNext()) {
                customShaderAttribute = null;
                break;
            }
            Material next = it.next();
            if (next.has(CustomShaderAttribute.Type)) {
                customShaderAttribute = (CustomShaderAttribute) next.get(CustomShaderAttribute.Type);
                break;
            }
        }
        final CustomShaderAttribute customShaderAttribute2 = customShaderAttribute;
        if (customShaderAttribute2 == null) {
            return;
        }
        Gdx.f82app.postRunnable(new Runnable() { // from class: org.catrobat.catroid.raptor.ThreeDManager$$ExternalSyntheticLambda25
            @Override // java.lang.Runnable
            public final void run() {
                ThreeDManager.lambda$setObjectShaderUniform$1(paramCount, customShaderAttribute2, name2, v1, v2, v3);
            }
        });
    }

    static /* synthetic */ void lambda$setObjectShaderUniform$1(int i, CustomShaderAttribute customShaderAttribute, String str, float f, float f2, float f3) {
        if (i == 1) {
            customShaderAttribute.uniforms.put("u_" + str, Float.valueOf(f));
        } else if (i == 2) {
            customShaderAttribute.uniforms.put("u_" + str, new Vector2(f, f2));
        } else if (i == 3) {
            customShaderAttribute.uniforms.put("u_" + str, new Vector3(f, f2, f3));
        }
    }

    public void setFreeCamera() {
        this.cameraTargetId = null;
        this.cameraTrackMode = 0;
        this.cameraTrackTargetId = null;
    }

    public void setThirdPersonCamera(String targetObjectId, float distance, float height, float pitch) {
        ModelInstance modelInstance = this.sceneObjects.get(targetObjectId);
        if (modelInstance == null) {
            Gdx.f82app.error("3DManager", "Camera target object not found: " + targetObjectId);
            this.cameraTargetId = null;
            return;
        }
        this.cameraTrackMode = 0;
        this.cameraTargetId = targetObjectId;
        this.cameraDistance = distance;
        this.cameraOffset.set(0.0f, height, 0.0f);
        this.cameraPitch = pitch;
        this.cameraYaw = modelInstance.transform.getRotation(new Quaternion()).getYaw();
    }

    public void setCameraTracking(String targetId, int mode, float px, float py, float pz, float yaw, float pitch, float roll) {
        this.cameraTrackMode = mode;
        if (mode == 0) {
            this.cameraTrackTargetId = null;
            return;
        }
        this.cameraTargetId = null;
        this.cameraTrackTargetId = targetId;
        this.cameraTrackPosOffset.set(px, py, pz);
        this.cameraTrackRotOffset.setEulerAngles(yaw, pitch, roll);
    }

    private void updateCameraTracking() {
        String str = this.cameraTrackTargetId;
        if (str == null || this.cameraTrackMode == 0) {
            return;
        }
        ModelInstance modelInstance = this.sceneObjects.get(str);
        if (modelInstance == null) {
            modelInstance = this.editorProxies.get(this.cameraTrackTargetId);
        }
        if (modelInstance == null) {
            return;
        }
        int i = this.cameraTrackMode;
        if (i == 1 || i == 3) {
            modelInstance.transform.getTranslation(this.tmpPos);
            this.tmpPos.add(this.cameraTrackPosOffset);
            this.camera.position.set(this.tmpPos);
        }
        int i2 = this.cameraTrackMode;
        if (i2 == 2 || i2 == 3) {
            modelInstance.transform.getRotation(this.tmpRot, true);
            this.tmpRot.mul(this.cameraTrackRotOffset);
            this.camera.direction.set(0.0f, 0.0f, -1.0f);
            this.tmpRot.transform(this.camera.direction);
            this.camera.up.set(0.0f, 1.0f, 0.0f);
            this.tmpRot.transform(this.camera.up);
        }
        this.camera.update();
    }

    public Map<String, PointLightEx> getPointLights() {
        return this.pointLights;
    }

    public Map<String, SpotLightEx> getSpotLights() {
        return this.spotLights;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/ThreeDManager$CameraAttachment.class
      classes14.dex
     */
    public static class CameraAttachment {
        public Matrix4 localOffsetMat;
        public String objectId;

        public CameraAttachment(String id2, Matrix4 offset) {
            Matrix4 matrix4 = new Matrix4();
            this.localOffsetMat = matrix4;
            this.objectId = id2;
            matrix4.set(offset);
        }
    }

    public void attachObjectToCamera(String objectId) {
        detachObjectFromCamera(objectId);
        ModelInstance modelInstance = this.sceneObjects.get(objectId);
        if (modelInstance == null) {
            modelInstance = this.editorProxies.get(objectId);
        }
        if (modelInstance == null) {
            return;
        }
        this.cameraAttachments.add(new CameraAttachment(objectId, new Matrix4(new Matrix4(this.camera.view).inv()).inv().mul(modelInstance.transform)));
    }

    public void detachObjectFromCamera(final String objectId) {
        this.cameraAttachments.removeIf(new Predicate() { // from class: org.catrobat.catroid.raptor.ThreeDManager$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((ThreeDManager.CameraAttachment) obj).objectId.equals(objectId);
            }
        });
    }

    private void updateCameraAttachments() {
        if (this.cameraAttachments.isEmpty()) {
            return;
        }
        Matrix4 matrix4Inv = new Matrix4(this.camera.view).inv();
        for (CameraAttachment cameraAttachment : this.cameraAttachments) {
            ModelInstance modelInstance = this.sceneObjects.get(cameraAttachment.objectId);
            if (modelInstance == null) {
                modelInstance = this.editorProxies.get(cameraAttachment.objectId);
            }
            if (modelInstance != null) {
                this.tmpMat1.set(matrix4Inv).mul(cameraAttachment.localOffsetMat);
                modelInstance.transform.set(this.tmpMat1);
                setWorldTransform(cameraAttachment.objectId, this.tmpMat1);
            }
        }
    }

    public void setMaxActivePointLights(int maxLights) {
        this.maxActivePointLights = Math.max(0, maxLights);
    }

    private void updateActivePointLights() {
        if (this.sceneManager == null || this.pointLights.isEmpty()) {
            return;
        }
        Iterator<PointLightEx> it = this.pointLights.values().iterator();
        while (it.hasNext()) {
            this.sceneManager.environment.remove((PointLight) it.next());
        }
        if (this.maxActivePointLights <= 0) {
            return;
        }
        this.sortedPointLights.clear();
        this.sortedPointLights.addAll(this.pointLights.values());
        final Vector3 vector3 = this.camera.position;
        this.sortedPointLights.sort(new Comparator() { // from class: org.catrobat.catroid.raptor.ThreeDManager$$ExternalSyntheticLambda22
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                Vector3 vector4 = vector3;
                return Float.compare(vector4.dst2(((PointLightEx) obj).position), vector4.dst2(((PointLightEx) obj2).position));
            }
        });
        int iMin = Math.min(this.sortedPointLights.size(), this.maxActivePointLights);
        for (int i = 0; i < iMin; i++) {
            this.sceneManager.environment.add((PointLight) this.sortedPointLights.get(i));
        }
    }

    public void updateParticleEffect3D(String objectId, final ParticleSystem3DComponent data, Matrix4 transform) {
        Texture textureLoadTexture;
        Texture textureLoadTexture2;
        ParticleSystem3DRuntime particleSystem3DRuntime = this.activeParticleRuntimes3D.get(objectId);
        if (particleSystem3DRuntime != null) {
            particleSystem3DRuntime.reconfigure(data);
            particleSystem3DRuntime.setTransform(transform);
            Texture texture = this.defaultParticleTexture;
            if (data.renderer.texturePath != null && !data.renderer.texturePath.isEmpty() && (textureLoadTexture2 = loadTexture(data.renderer.texturePath)) != null) {
                texture = textureLoadTexture2;
            }
            particleSystem3DRuntime.setTexture(texture);
            return;
        }
        Texture texture2 = this.defaultParticleTexture;
        if (data.renderer.texturePath != null && !data.renderer.texturePath.isEmpty() && (textureLoadTexture = loadTexture(data.renderer.texturePath)) != null) {
            texture2 = textureLoadTexture;
        }
        ParticleSystem3DRuntime particleSystem3DRuntime2 = new ParticleSystem3DRuntime(data, objectId, this.camera, this);
        particleSystem3DRuntime2.setTexture(texture2);
        particleSystem3DRuntime2.setTransform(transform);
        particleSystem3DRuntime2.setSubEmitterCallback(new ParticleSystem3DRuntime.SubEmitterCallback() { // from class: org.catrobat.catroid.raptor.ThreeDManager$$ExternalSyntheticLambda15
            @Override // org.catrobat.catroid.raptor.particles.ParticleSystem3DRuntime.SubEmitterCallback
            public final void onSubEmit(String str, Vector3 vector3, Vector3 vector4) {
                this.f$0.m5482xba5a89ab(data, str, vector3, vector4);
            }
        });
        particleSystem3DRuntime2.play();
        this.activeParticleRuntimes3D.put(objectId, particleSystem3DRuntime2);
    }

    /* JADX INFO: renamed from: lambda$updateParticleEffect3D$4$org-catrobat-catroid-raptor-ThreeDManager, reason: not valid java name */
    /* synthetic */ void m5482xba5a89ab(ParticleSystem3DComponent particleSystem3DComponent, String str, Vector3 vector3, Vector3 vector4) {
        int iEvaluate;
        ParticleSystem3DComponent config;
        ParticleSystem3DRuntime particleSystem3DRuntime = this.activeParticleRuntimes3D.get(str);
        if (particleSystem3DRuntime == null) {
            return;
        }
        Iterator<ParticleSystem3DComponent.SubEmitterEntry> it = particleSystem3DComponent.subEmitters.entries.iterator();
        while (true) {
            if (!it.hasNext()) {
                iEvaluate = 0;
                break;
            }
            ParticleSystem3DComponent.SubEmitterEntry next = it.next();
            if (next.subEmitterObjectId.equals(str)) {
                iEvaluate = next.emitCount;
                break;
            }
        }
        if (iEvaluate <= 0) {
            Iterator<Map.Entry<String, ParticleSystem3DRuntime>> it2 = this.activeParticleRuntimes3D.entrySet().iterator();
            while (true) {
                if (!it2.hasNext()) {
                    config = null;
                    break;
                }
                Map.Entry<String, ParticleSystem3DRuntime> next2 = it2.next();
                if (next2.getKey().equals(str)) {
                    config = next2.getValue().getConfig();
                    break;
                }
            }
            if (config == null || !config.emission.enabled || (iEvaluate = (int) config.emission.rateOverTime.evaluate(0.0f)) <= 0) {
                iEvaluate = 10;
            }
        }
        particleSystem3DRuntime.emitBurstAt(vector3, iEvaluate);
    }

    public void removeParticleEffect3D(String objectId) {
        ParticleSystem3DRuntime particleSystem3DRuntimeRemove = this.activeParticleRuntimes3D.remove(objectId);
        if (particleSystem3DRuntimeRemove != null) {
            particleSystem3DRuntimeRemove.dispose();
        }
    }

    public void updateParticleTransform3D(String objectId, Matrix4 transform) {
        ParticleSystem3DRuntime particleSystem3DRuntime = this.activeParticleRuntimes3D.get(objectId);
        if (particleSystem3DRuntime != null) {
            particleSystem3DRuntime.setTransform(transform);
        }
    }

    private void updateParticles3D(float delta) {
        for (ParticleSystem3DRuntime particleSystem3DRuntime : this.activeParticleRuntimes3D.values()) {
            particleSystem3DRuntime.setCamera(this.camera);
            particleSystem3DRuntime.update(delta);
        }
    }

    private void renderParticles3D() {
        if (this.activeParticleRuntimes3D.isEmpty()) {
            return;
        }
        Iterator<ParticleSystem3DRuntime> it = this.activeParticleRuntimes3D.values().iterator();
        while (it.hasNext()) {
            it.next().render();
        }
    }

    public float getCsmSplitFactor() {
        return this.csmSplitFactor;
    }

    public boolean isCSMEnabled() {
        return this.useCSM;
    }

    public void attachRaySensor(String rayName, String objectId, float offX, float offY, float offZ, float dirX, float dirY, float dirZ, float distance) {
        this.continuousRaySensors.removeValue(getSensorByName(rayName), false);
        RaySensor raySensor = new RaySensor();
        raySensor.rayName = rayName;
        raySensor.attachedObjectId = objectId;
        raySensor.localOffset.set(offX, offY, offZ);
        raySensor.localDirection.set(dirX, dirY, dirZ).nor();
        raySensor.distance = distance;
        this.continuousRaySensors.add(raySensor);
    }

    private RaySensor getSensorByName(String name2) {
        Array.ArrayIterator<RaySensor> it = this.continuousRaySensors.iterator();
        while (it.hasNext()) {
            RaySensor next = it.next();
            if (next.rayName.equals(name2)) {
                return next;
            }
        }
        return null;
    }

    private void updateContinuousSensors() {
        Array.ArrayIterator<RaySensor> it = this.continuousRaySensors.iterator();
        while (it.hasNext()) {
            RaySensor next = it.next();
            ModelInstance modelInstance = this.sceneObjects.get(next.attachedObjectId);
            if (modelInstance != null) {
                Matrix4 matrix4 = modelInstance.transform;
                this.tmpSensorPos.set(next.localOffset).mul(matrix4);
                this.tmpSensorDir.set(next.localDirection).rot(matrix4).nor();
                this.tmpSensorEnd.set(this.tmpSensorPos).add(this.tmpSensorDir.scl(next.distance));
                ClosestRayResultCallback closestRayResultCallback = new ClosestRayResultCallback(this.tmpSensorPos, this.tmpSensorEnd);
                this.dynamicsWorld.rayTest(this.tmpSensorPos, this.tmpSensorEnd, closestRayResultCallback);
                RayCastResult rayCastResult = this.rayCastResults.get(next.rayName);
                if (rayCastResult == null) {
                    rayCastResult = new RayCastResult();
                    this.rayCastResults.put(next.rayName, rayCastResult);
                }
                if (closestRayResultCallback.hasHit()) {
                    rayCastResult.hasHit = true;
                    closestRayResultCallback.getHitPointWorld(rayCastResult.hitPoint);
                    closestRayResultCallback.getHitNormalWorld(rayCastResult.hitNormal);
                    rayCastResult.hitDistance = this.tmpSensorPos.dst(rayCastResult.hitPoint);
                    btCollisionObject collisionObject = closestRayResultCallback.getCollisionObject();
                    rayCastResult.hitObjectId = "";
                    for (Map.Entry<String, btRigidBody> entry : this.physicsBodies.entrySet()) {
                        if (entry.getValue().equals(collisionObject)) {
                            rayCastResult.hitObjectId = entry.getKey();
                            break;
                        }
                    }
                } else {
                    rayCastResult.hasHit = false;
                    rayCastResult.hitObjectId = "";
                    rayCastResult.hitDistance = -1.0f;
                }
                closestRayResultCallback.dispose();
            }
        }
    }

    public void setSoundInstanceVolume(String instanceName, float volume) {
        for (PlayableAudio playableAudio : this.active3DSounds) {
            if (instanceName.equals(playableAudio.getInstanceName())) {
                playableAudio.setBaseVolume(volume / 100.0f);
            }
        }
    }

    public void setSoundInstancePitch(String instanceName, float pitch) {
        for (PlayableAudio playableAudio : this.active3DSounds) {
            if (instanceName.equals(playableAudio.getInstanceName())) {
                playableAudio.setPitch(pitch);
            }
        }
    }

    public void setAnimationSpeed(String objectId, float speed) {
        AnimationController animationController = this.animationControllers.get(objectId);
        if (animationController == null || animationController.current == null) {
            return;
        }
        animationController.current.speed = speed;
    }

    public void setParticleEmissionRate(String objectId, float rate) {
        ParticleEffect particleEffect = this.activeParticleEffects.get(objectId);
        if (particleEffect != null) {
            Array.ArrayIterator<ParticleController> it = particleEffect.getControllers().iterator();
            while (it.hasNext()) {
                ParticleController next = it.next();
                if (next.emitter instanceof RegularEmitter) {
                    RegularEmitter regularEmitter = (RegularEmitter) next.emitter;
                    regularEmitter.getEmission().setHigh(rate);
                    if (rate <= 0.01f) {
                        regularEmitter.getEmission().setLow(0.0f);
                    }
                }
            }
        }
    }

    public void rotateCameraRelative(float pitch, float yaw, float roll) {
        if (yaw != 0.0f) {
            this.camera.rotate(Vector3.Y, yaw);
        }
        if (pitch != 0.0f) {
            this.tmpVec.set(this.camera.direction).crs(this.camera.up).nor();
            this.camera.rotate(this.tmpVec, pitch);
        }
        if (roll != 0.0f) {
            PerspectiveCamera perspectiveCamera = this.camera;
            perspectiveCamera.rotate(perspectiveCamera.direction, roll);
        }
        this.camera.update();
    }

    public void configureTouchRotation(boolean enabled, float sensitivity, boolean blockByUI, float x, float y, float w, float h) {
        this.touchRotationEnabled = enabled;
        this.sensitivity = sensitivity;
        this.blockByUI = blockByUI;
        this.rotationAreaX = x;
        this.rotationAreaY = y;
        this.rotationAreaW = w;
        this.rotationAreaH = h;
    }

    public boolean handleTouchDown(int screenX, int screenY, int pointer, Stage uiStage, Stage stage2d) {
        if (this.touchRotationEnabled && this.activePointerId == -1) {
            float width = (this.rotationAreaX / 100.0f) * Gdx.graphics.getWidth();
            float height = (this.rotationAreaY / 100.0f) * Gdx.graphics.getHeight();
            float width2 = (this.rotationAreaW / 100.0f) * Gdx.graphics.getWidth();
            float height2 = (this.rotationAreaH / 100.0f) * Gdx.graphics.getHeight();
            float height3 = Gdx.graphics.getHeight() - screenY;
            float f = screenX;
            if (f >= width && f <= width + width2 && height3 >= height && height3 <= height + height2) {
                if (this.blockByUI) {
                    float f2 = screenY;
                    Vector2 vector2ScreenToStageCoordinates = uiStage.screenToStageCoordinates(new Vector2(f, f2));
                    Actor actorHit = uiStage.hit(vector2ScreenToStageCoordinates.x, vector2ScreenToStageCoordinates.y, true);
                    if (actorHit != null && actorHit != uiStage.getRoot() && actorHit.getListeners().size > 0) {
                        return false;
                    }
                    Vector2 vector2ScreenToStageCoordinates2 = stage2d.screenToStageCoordinates(new Vector2(f, f2));
                    Actor actorHit2 = stage2d.hit(vector2ScreenToStageCoordinates2.x, vector2ScreenToStageCoordinates2.y, true);
                    if (actorHit2 != null && actorHit2 != stage2d.getRoot() && actorHit2.getListeners().size > 0) {
                        return false;
                    }
                }
                this.activePointerId = pointer;
                this.lastTouchX = screenX;
                this.lastTouchY = screenY;
            }
        }
        return false;
    }

    public void handleTouchDragged(int screenX, int screenY, int pointer) {
        if (this.activePointerId == pointer) {
            float f = screenX - this.lastTouchX;
            float f2 = screenY - this.lastTouchY;
            float f3 = this.sensitivity;
            addCameraRotation((-f) * f3, (-f2) * f3);
            this.lastTouchX = screenX;
            this.lastTouchY = screenY;
        }
    }

    public void handleTouchUp(int pointer) {
        if (this.activePointerId == pointer) {
            this.activePointerId = -1;
        }
    }

    public void setCameraFov(float fov) {
        this.camera.fieldOfView = fov;
        this.camera.update();
    }

    public void startCameraShake(float intensity, float duration) {
        this.shakeIntensity = intensity;
        this.shakeDuration = duration;
        this.shakeTimer = duration;
    }

    private void applyCameraEffects(float delta) {
        float f = this.shakeTimer;
        if (f > 0.0f) {
            float f2 = this.shakeIntensity * (f / this.shakeDuration);
            this.currentShakeOffset.set(((float) (Math.random() - 0.5d)) * 2.0f * f2, ((float) (Math.random() - 0.5d)) * 2.0f * f2, ((float) (Math.random() - 0.5d)) * 2.0f * f2);
            this.camera.position.add(this.currentShakeOffset);
            this.shakeTimer -= delta;
            this.camera.update();
        }
    }

    public void setAI(String objectId, AIComponent settings) {
        if (settings == null || settings.mode == AIComponent.Mode.OFF) {
            this.activeAIs.remove(objectId);
            this.modelRotationOffsets.remove(objectId);
        } else {
            this.activeAIs.put(objectId, settings);
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/ThreeDManager$DeadEndZone.class
      classes14.dex
     */
    private static class DeadEndZone {
        public final Vector3 position;
        public final float radius;

        public DeadEndZone(Vector3 position, float radius) {
            this.position = position;
            this.radius = radius;
        }
    }

    /* JADX WARN: Code duplicated, block: B:114:0x0310  */
    /* JADX WARN: Code duplicated, block: B:115:0x031d  */
    /* JADX WARN: Code duplicated, block: B:117:0x0327  */
    /* JADX WARN: Code duplicated, block: B:120:0x0351  */
    /* JADX WARN: Code duplicated, block: B:121:0x0358  */
    /* JADX WARN: Code duplicated, block: B:124:0x0370  */
    /* JADX WARN: Code duplicated, block: B:130:0x0385  */
    /* JADX WARN: Code duplicated, block: B:133:0x0399 A[LOOP:4: B:129:0x0383->B:133:0x0399, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:136:0x03a1  */
    /* JADX WARN: Code duplicated, block: B:139:0x03b5  */
    /* JADX WARN: Code duplicated, block: B:142:0x03c5  */
    /* JADX WARN: Code duplicated, block: B:145:0x03d9 A[LOOP:6: B:140:0x03bf->B:145:0x03d9, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:148:0x03de  */
    /* JADX WARN: Code duplicated, block: B:152:0x03f7  */
    /* JADX WARN: Code duplicated, block: B:155:0x040d  */
    /* JADX WARN: Code duplicated, block: B:159:0x0442  */
    /* JADX WARN: Code duplicated, block: B:161:0x044c  */
    /* JADX WARN: Code duplicated, block: B:162:0x0452  */
    /* JADX WARN: Code duplicated, block: B:178:0x04f4  */
    /* JADX WARN: Code duplicated, block: B:181:0x0507  */
    /* JADX WARN: Code duplicated, block: B:183:0x0531  */
    /* JADX WARN: Code duplicated, block: B:186:0x0545  */
    /* JADX WARN: Code duplicated, block: B:197:0x0585  */
    /* JADX WARN: Code duplicated, block: B:199:0x058a  */
    /* JADX WARN: Code duplicated, block: B:202:0x0594  */
    /* JADX WARN: Code duplicated, block: B:203:0x05a9  */
    /* JADX WARN: Code duplicated, block: B:206:0x05c4  */
    /* JADX WARN: Code duplicated, block: B:216:0x05ee  */
    /* JADX WARN: Code duplicated, block: B:218:0x0618  */
    /* JADX WARN: Code duplicated, block: B:221:0x062c  */
    /* JADX WARN: Code duplicated, block: B:224:0x0649 A[LOOP:9: B:219:0x0626->B:224:0x0649, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:228:0x0657  */
    /* JADX WARN: Code duplicated, block: B:231:0x066c  */
    /* JADX WARN: Code duplicated, block: B:234:0x0673  */
    /* JADX WARN: Code duplicated, block: B:237:0x0680  */
    /* JADX WARN: Code duplicated, block: B:240:0x068a  */
    /* JADX WARN: Code duplicated, block: B:242:0x06a0  */
    /* JADX WARN: Code duplicated, block: B:244:0x06ba  */
    /* JADX WARN: Code duplicated, block: B:246:0x06cf  */
    /* JADX WARN: Code duplicated, block: B:248:0x06dd  */
    /* JADX WARN: Code duplicated, block: B:251:0x06e3  */
    /* JADX WARN: Code duplicated, block: B:256:0x0707  */
    /* JADX WARN: Code duplicated, block: B:260:0x071e  */
    /* JADX WARN: Code duplicated, block: B:262:0x0721  */
    /* JADX WARN: Code duplicated, block: B:263:0x072c A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:264:0x072e  */
    /* JADX WARN: Code duplicated, block: B:267:0x07ab  */
    /* JADX WARN: Code duplicated, block: B:269:0x07c1  */
    /* JADX WARN: Code duplicated, block: B:270:0x07c4  */
    /* JADX WARN: Code duplicated, block: B:272:0x07d0  */
    /* JADX WARN: Code duplicated, block: B:273:0x07d3  */
    /* JADX WARN: Code duplicated, block: B:276:0x07e9  */
    /* JADX WARN: Code duplicated, block: B:277:0x0822  */
    /* JADX WARN: Code duplicated, block: B:292:0x089b  */
    /* JADX WARN: Code duplicated, block: B:294:0x08bd  */
    /* JADX WARN: Code duplicated, block: B:298:0x08d7  */
    /* JADX WARN: Code duplicated, block: B:302:0x08fa  */
    /* JADX WARN: Code duplicated, block: B:304:0x090a  */
    /* JADX WARN: Code duplicated, block: B:305:0x090f  */
    /* JADX WARN: Code duplicated, block: B:333:0x0477 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:335:0x0465 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:343:0x039e A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:344:0x0397 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:347:0x03eb A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:348:0x03db A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:349:0x03d7 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:350:0x055e A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:351:0x0557 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:352:? A[LOOP:7: B:184:0x053f->B:352:?, LOOP_END, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:356:0x0710 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:357:0x064c A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:358:0x0642 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:361:0x06e6 A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:66:0x01b1  */
    /* JADX WARN: Code duplicated, block: B:92:0x0288  */
    private void updateAIProcessing(float delta) {
        Vector3 vector3;
        float f;
        Vector3 vector4;
        Vector3 vector3Nor;
        float fMin;
        boolean z;
        int iIntValue;
        int i;
        int i2;
        float f2;
        int i3;
        Iterator<Map.Entry<String, AIComponent>> it;
        Iterator<Map.Entry<String, AIComponent>> it2;
        float fDst;
        Array<DeadEndZone> array;
        String str;
        float fDot;
        Array.ArrayIterator<DeadEndZone> it3;
        DeadEndZone next;
        Array.ArrayIterator<DeadEndZone> arrayIterator;
        String str2;
        float fDot2;
        float fLen2;
        float f3;
        float fSqrt;
        ClosestRayResultCallback closestRayResultCallback;
        btCollisionObject collisionObject;
        Iterator<Map.Entry<String, btRigidBody>> it4;
        String key;
        Map.Entry<String, btRigidBody> next2;
        ClosestRayResultCallback closestRayResultCallback2;
        float f4;
        float fLen3;
        Quaternion quaternion;
        float f5;
        btRigidBody btrigidbody;
        Quaternion quaternion2;
        Quaternion quaternion3;
        boolean z2;
        Array<Vector3> arrayComputeIfAbsent;
        float f6;
        ClosestRayResultCallback closestRayResultCallback3;
        btCollisionObject collisionObject2;
        Iterator<Map.Entry<String, btRigidBody>> it5;
        String key2;
        Map.Entry<String, btRigidBody> next3;
        Array<Vector3> array2;
        Vector3 vector5;
        int i4;
        Array<DeadEndZone> arrayComputeIfAbsent2;
        int i5;
        Vector3 vector6;
        Array.ArrayIterator<DeadEndZone> it6;
        boolean z3;
        Vector3 vector7;
        boolean z4;
        boolean z5;
        boolean z6;
        boolean z7;
        String key3;
        if (this.editorMode) {
            return;
        }
        float f7 = this.trailTimer + delta;
        this.trailTimer = f7;
        float f8 = 0.5f;
        boolean z8 = false;
        if (f7 >= 0.5f) {
            this.trailTimer = 0.0f;
            HashSet<String> hashSet = new HashSet();
            for (AIComponent aIComponent : this.activeAIs.values()) {
                if (aIComponent.mode == AIComponent.Mode.FOLLOW && !aIComponent.targetId.isEmpty()) {
                    hashSet.add(aIComponent.targetId);
                }
            }
            for (String str3 : hashSet) {
                ModelInstance modelInstance = this.sceneObjects.get(str3);
                if (modelInstance != null) {
                    Array<Vector3> arrayComputeIfAbsent3 = this.targetTrails.computeIfAbsent(str3, new Function() { // from class: org.catrobat.catroid.raptor.ThreeDManager$$ExternalSyntheticLambda7
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            return ThreeDManager.lambda$updateAIProcessing$5((String) obj);
                        }
                    });
                    modelInstance.transform.getTranslation(this.tmpPos);
                    if (arrayComputeIfAbsent3.size == 0 || arrayComputeIfAbsent3.peek().dst(this.tmpPos) > 2.0f) {
                        arrayComputeIfAbsent3.add(new Vector3(this.tmpPos));
                        if (arrayComputeIfAbsent3.size > 40) {
                            arrayComputeIfAbsent3.removeIndex(0);
                        }
                    }
                }
            }
        }
        Iterator<Map.Entry<String, AIComponent>> it7 = this.activeAIs.entrySet().iterator();
        while (it7.hasNext()) {
            Map.Entry<String, AIComponent> next4 = it7.next();
            String key4 = next4.getKey();
            AIComponent value = next4.getValue();
            ModelInstance modelInstance2 = this.sceneObjects.get(key4);
            if (modelInstance2 != null) {
                modelInstance2.transform.getTranslation(this.aiCurrentPos);
                String str4 = "";
                if (value.mode == AIComponent.Mode.FOLLOW && !value.targetId.isEmpty()) {
                    ModelInstance modelInstance3 = this.sceneObjects.get(value.targetId);
                    if (modelInstance3 == null) {
                        modelInstance3 = this.editorProxies.get(value.targetId);
                    }
                    if (modelInstance3 != null) {
                        modelInstance3.transform.getTranslation(this.aiRealTargetPos);
                        float fDst2 = this.aiCurrentPos.dst(this.aiRealTargetPos);
                        if (!(fDst2 <= value.detectionRange ? true : z8) || value.detectionRange <= 0.1f) {
                            z4 = z8;
                        } else {
                            this.aiDesiredDir.set(this.aiRealTargetPos).sub(this.aiCurrentPos).nor();
                            Vector3 vector3Add = new Vector3(this.aiCurrentPos).add(this.aiDesiredDir.x * fDst2, this.aiDesiredDir.y * fDst2, this.aiDesiredDir.z * fDst2);
                            ClosestRayResultCallback closestRayResultCallback4 = new ClosestRayResultCallback(this.aiCurrentPos, vector3Add);
                            this.dynamicsWorld.rayTest(this.aiCurrentPos, vector3Add, closestRayResultCallback4);
                            if (closestRayResultCallback4.hasHit()) {
                                btCollisionObject collisionObject3 = closestRayResultCallback4.getCollisionObject();
                                Iterator<Map.Entry<String, btRigidBody>> it8 = this.physicsBodies.entrySet().iterator();
                                while (true) {
                                    if (!it8.hasNext()) {
                                        key3 = "";
                                        break;
                                    }
                                    Map.Entry<String, btRigidBody> next5 = it8.next();
                                    if (next5.getValue().equals(collisionObject3)) {
                                        key3 = next5.getKey();
                                        break;
                                    }
                                }
                                if (key3.equals(value.targetId)) {
                                    z4 = true;
                                } else {
                                    Vector3 vector8 = new Vector3();
                                    closestRayResultCallback4.getHitPointWorld(vector8);
                                    if (this.aiCurrentPos.dst(vector8) > fDst2 - f8) {
                                        z4 = true;
                                    } else {
                                        z4 = z8;
                                    }
                                }
                            } else {
                                z4 = true;
                            }
                            closestRayResultCallback4.dispose();
                        }
                        if (z4) {
                            this.aiFinalMoveTarget.set(this.aiRealTargetPos);
                            this.aiLastKnownPositions.put(key4, new Vector3(this.aiRealTargetPos));
                            this.aiHasLostTarget.put(key4, Boolean.valueOf(z8));
                        } else {
                            Vector3 vector9 = this.aiLastKnownPositions.get(key4);
                            if (vector9 == null) {
                                z5 = z8;
                            } else if (this.aiCurrentPos.dst(vector9) > value.stopDistance + f8) {
                                this.aiFinalMoveTarget.set(vector9);
                                z5 = true;
                            } else {
                                this.aiHasLostTarget.put(key4, true);
                                z5 = z8;
                            }
                            if (!z5 || this.aiHasLostTarget.getOrDefault(key4, Boolean.valueOf(z8)).booleanValue()) {
                                Array<Vector3> array3 = this.targetTrails.get(value.targetId);
                                if (array3 == null) {
                                    z6 = false;
                                    break;
                                }
                                int i6 = array3.size - 1;
                                while (true) {
                                    if (i6 < 0) {
                                        z6 = false;
                                        break;
                                    }
                                    Vector3 vector10 = array3.get(i6);
                                    float fDst3 = this.aiCurrentPos.dst(vector10);
                                    if (fDst3 <= value.detectionRange) {
                                        this.aiDesiredDir.set(vector10).sub(this.aiCurrentPos).nor();
                                        Vector3 vector3Add2 = new Vector3(this.aiCurrentPos).add(this.aiDesiredDir.x * fDst3, this.aiDesiredDir.y * fDst3, this.aiDesiredDir.z * fDst3);
                                        ClosestRayResultCallback closestRayResultCallback5 = new ClosestRayResultCallback(this.aiCurrentPos, vector3Add2);
                                        this.dynamicsWorld.rayTest(this.aiCurrentPos, vector3Add2, closestRayResultCallback5);
                                        if (closestRayResultCallback5.hasHit()) {
                                            Vector3 vector11 = new Vector3();
                                            closestRayResultCallback5.getHitPointWorld(vector11);
                                            if (this.aiCurrentPos.dst(vector11) < fDst3 - f8) {
                                                z7 = false;
                                            } else {
                                                z7 = true;
                                            }
                                        } else {
                                            z7 = true;
                                        }
                                        closestRayResultCallback5.dispose();
                                        if (z7) {
                                            this.aiFinalMoveTarget.set(vector10);
                                            z6 = true;
                                            break;
                                        }
                                    }
                                    i6--;
                                }
                                if (!z6) {
                                    value.currentVelocity.lerp(Vector3.Zero, delta * 5.0f);
                                    stopPhysicsVelocity(key4);
                                }
                                z8 = false;
                            }
                        }
                        vector3 = this.aiLastTargetPos.get(key4);
                        if (vector3 == null) {
                            this.aiLastTargetPos.put(key4, new Vector3(this.aiFinalMoveTarget));
                        } else if (vector3.dst(this.aiFinalMoveTarget) > 15.0f) {
                            this.aiDeadEnds.remove(key4);
                            this.aiPathHistory.remove(key4);
                            this.aiBacktrackTarget.remove(key4);
                            this.aiLastTargetPos.put(key4, new Vector3(this.aiFinalMoveTarget));
                        }
                        if (this.aiCurrentPos.dst(value.lastPosition) < 0.02f) {
                            value.stuckTimer += delta;
                        } else {
                            value.stuckTimer = 0.0f;
                        }
                        value.lastPosition.set(this.aiCurrentPos);
                        f = 1.5f;
                        if (value.stuckTimer > 0.8f) {
                            array2 = this.aiPathHistory.get(key4);
                            vector5 = null;
                            if (array2 != null) {
                                i4 = array2.size - 1;
                                while (true) {
                                    if (i4 >= 0) {
                                        i4 = -1;
                                        break;
                                    }
                                    vector7 = array2.get(i4);
                                    if (vector7.dst(this.aiCurrentPos) >= 6.0f) {
                                        vector5 = vector7;
                                        break;
                                    }
                                    i4--;
                                }
                                if (i4 != -1) {
                                    arrayComputeIfAbsent2 = this.aiDeadEnds.computeIfAbsent(key4, new Function() { // from class: org.catrobat.catroid.raptor.ThreeDManager$$ExternalSyntheticLambda8
                                        @Override // java.util.function.Function
                                        public final Object apply(Object obj) {
                                            return ThreeDManager.lambda$updateAIProcessing$6((String) obj);
                                        }
                                    });
                                    int i7 = i4 + 1;
                                    i5 = i7;
                                    while (i5 < array2.size) {
                                        vector6 = array2.get(i5);
                                        it6 = arrayComputeIfAbsent2.iterator();
                                        while (true) {
                                            if (it6.hasNext()) {
                                                if (it6.next().position.dst(vector6) < f) {
                                                    z3 = true;
                                                    break;
                                                }
                                            } else {
                                                z3 = false;
                                                break;
                                            }
                                        }
                                        if (!z3) {
                                            arrayComputeIfAbsent2.add(new DeadEndZone(new Vector3(vector6), 3.0f));
                                        }
                                        i5++;
                                        f = 1.5f;
                                    }
                                    if (arrayComputeIfAbsent2.size > 30) {
                                        arrayComputeIfAbsent2.removeRange(0, arrayComputeIfAbsent2.size - 30);
                                    }
                                    array2.truncate(i7);
                                    this.aiBacktrackTarget.put(key4, new Vector3(vector5));
                                }
                            }
                            if (vector5 == null) {
                                this.aiDeadEnds.computeIfAbsent(key4, new Function() { // from class: org.catrobat.catroid.raptor.ThreeDManager$$ExternalSyntheticLambda9
                                    @Override // java.util.function.Function
                                    public final Object apply(Object obj) {
                                        return ThreeDManager.lambda$updateAIProcessing$7((String) obj);
                                    }
                                }).add(new DeadEndZone(new Vector3(this.aiCurrentPos), 4.5f));
                                this.aiDesiredDir.scl(-1.0f);
                            }
                            value.stuckTimer = 0.0f;
                        }
                        vector4 = this.aiBacktrackTarget.get(key4);
                        if (vector4 != null) {
                            if (this.aiCurrentPos.dst(vector4) < 1.2f) {
                                this.aiBacktrackTarget.remove(key4);
                            } else {
                                this.aiFinalMoveTarget.set(vector4);
                            }
                        }
                        if (this.aiCurrentPos.dst(this.aiFinalMoveTarget) <= value.stopDistance) {
                            value.currentVelocity.lerp(Vector3.Zero, delta * 5.0f);
                            stopPhysicsVelocity(key4);
                            f8 = 0.5f;
                            z8 = false;
                        } else {
                            this.aiDesiredDir.set(this.aiFinalMoveTarget).sub(this.aiCurrentPos);
                            if (((float) Math.sqrt((this.aiDesiredDir.x * this.aiDesiredDir.x) + (this.aiDesiredDir.z * this.aiDesiredDir.z))) >= 1.3f) {
                                this.aiDesiredDir.y = 0.0f;
                                this.aiDesiredDir.nor();
                            } else {
                                this.aiDesiredDir.y = 0.0f;
                                this.aiDesiredDir.nor();
                            }
                            vector3Nor = new Vector3(value.currentVelocity).nor();
                            if (vector3Nor.isZero()) {
                                vector3Nor.set(this.aiDesiredDir);
                            }
                            fMin = Math.min(3.0f, Math.max(1.2f, value.detectionRange));
                            if (value.avoidObstacles) {
                                Vector3 vector3Add3 = new Vector3(this.aiCurrentPos).add(this.aiDesiredDir.x * fMin, 0.0f, this.aiDesiredDir.z * fMin);
                                closestRayResultCallback3 = new ClosestRayResultCallback(this.aiCurrentPos, vector3Add3);
                                this.dynamicsWorld.rayTest(this.aiCurrentPos, vector3Add3, closestRayResultCallback3);
                                if (closestRayResultCallback3.hasHit()) {
                                    collisionObject2 = closestRayResultCallback3.getCollisionObject();
                                    it5 = this.physicsBodies.entrySet().iterator();
                                    while (true) {
                                        if (it5.hasNext()) {
                                            key2 = "";
                                            break;
                                        }
                                        next3 = it5.next();
                                        if (next3.getValue().equals(collisionObject2)) {
                                            key2 = next3.getKey();
                                            break;
                                        }
                                    }
                                    if (key2.equals(value.targetId)) {
                                        z = false;
                                    } else {
                                        z = false;
                                    }
                                } else {
                                    z = false;
                                }
                                closestRayResultCallback3.dispose();
                            } else {
                                z = false;
                            }
                            this.aiBestMoveDir.set(this.aiDesiredDir);
                            if (!z) {
                                this.aiBestMoveDir.set(this.aiDesiredDir);
                                this.aiSlideDirs.put(key4, 0);
                                it = it7;
                            } else {
                                iIntValue = this.aiSlideDirs.getOrDefault(key4, 0).intValue();
                                i = 5;
                                i2 = 5;
                                f2 = -1000.0f;
                                i3 = 0;
                                while (i3 < 11) {
                                    if (iIntValue == -1) {
                                        this.aiRayDir.set(this.aiDesiredDir).rotate(Vector3.Y, (i3 - 5) * 15.0f);
                                        if (value.avoidObstacles) {
                                            Vector3 vector3Add4 = new Vector3(this.aiCurrentPos).add(this.aiRayDir.x * fMin, 0.0f, this.aiRayDir.z * fMin);
                                            closestRayResultCallback = new ClosestRayResultCallback(this.aiCurrentPos, vector3Add4);
                                            this.dynamicsWorld.rayTest(this.aiCurrentPos, vector3Add4, closestRayResultCallback);
                                            if (closestRayResultCallback.hasHit()) {
                                                collisionObject = closestRayResultCallback.getCollisionObject();
                                                it4 = this.physicsBodies.entrySet().iterator();
                                                while (true) {
                                                    if (!it4.hasNext()) {
                                                        it2 = it7;
                                                        key = str4;
                                                        break;
                                                    }
                                                    next2 = it4.next();
                                                    it2 = it7;
                                                    if (next2.getValue().equals(collisionObject)) {
                                                        key = next2.getKey();
                                                        break;
                                                    }
                                                    it7 = it2;
                                                }
                                                if (key.equals(value.targetId)) {
                                                }
                                                closestRayResultCallback.dispose();
                                            } else {
                                                it2 = it7;
                                            }
                                            fDst = fMin;
                                            closestRayResultCallback.dispose();
                                        } else {
                                            it2 = it7;
                                            fDst = fMin;
                                        }
                                        array = this.aiDeadEnds.get(key4);
                                        if (array != null) {
                                            it3 = array.iterator();
                                            while (it3.hasNext()) {
                                                next = it3.next();
                                                if (this.aiCurrentPos.dst(next.position) < fMin * 1.5f) {
                                                    this.tmpVec.set(next.position).sub(this.aiCurrentPos);
                                                    fDot2 = this.tmpVec.dot(this.aiRayDir);
                                                    if (fDot2 > 0.0f) {
                                                        fLen2 = this.tmpVec.len2() - (fDot2 * fDot2);
                                                        arrayIterator = it3;
                                                        f3 = next.radius * next.radius;
                                                        if (fLen2 < f3) {
                                                            str2 = str4;
                                                            fSqrt = fDot2 - ((float) Math.sqrt(f3 - fLen2));
                                                            if (fSqrt <= 0.0f) {
                                                            }
                                                        }
                                                    } else {
                                                        arrayIterator = it3;
                                                    }
                                                    str2 = str4;
                                                } else {
                                                    arrayIterator = it3;
                                                    str2 = str4;
                                                }
                                                str4 = str2;
                                                it3 = arrayIterator;
                                            }
                                        }
                                        str = str4;
                                        fDot = this.aiRayDir.dot(this.aiDesiredDir) + ((fDst / fMin) * 2.5f) + (this.aiRayDir.dot(vector3Nor) * 0.4f);
                                        if (fDot > f2) {
                                            this.aiBestMoveDir.set(this.aiRayDir);
                                            f2 = fDot;
                                            i2 = i3;
                                        }
                                    } else {
                                        this.aiRayDir.set(this.aiDesiredDir).rotate(Vector3.Y, (i3 - 5) * 15.0f);
                                        if (value.avoidObstacles) {
                                            Vector3 vector3Add5 = new Vector3(this.aiCurrentPos).add(this.aiRayDir.x * fMin, 0.0f, this.aiRayDir.z * fMin);
                                            closestRayResultCallback = new ClosestRayResultCallback(this.aiCurrentPos, vector3Add5);
                                            this.dynamicsWorld.rayTest(this.aiCurrentPos, vector3Add5, closestRayResultCallback);
                                            if (closestRayResultCallback.hasHit()) {
                                                collisionObject = closestRayResultCallback.getCollisionObject();
                                                it4 = this.physicsBodies.entrySet().iterator();
                                                while (true) {
                                                    if (!it4.hasNext()) {
                                                        it2 = it7;
                                                        key = str4;
                                                        break;
                                                    }
                                                    next2 = it4.next();
                                                    it2 = it7;
                                                    if (next2.getValue().equals(collisionObject)) {
                                                        key = next2.getKey();
                                                        break;
                                                    }
                                                    it7 = it2;
                                                }
                                                if (key.equals(value.targetId)) {
                                                }
                                                closestRayResultCallback.dispose();
                                            } else {
                                                it2 = it7;
                                            }
                                            fDst = fMin;
                                            closestRayResultCallback.dispose();
                                        } else {
                                            it2 = it7;
                                            fDst = fMin;
                                        }
                                        array = this.aiDeadEnds.get(key4);
                                        if (array != null) {
                                            it3 = array.iterator();
                                            while (it3.hasNext()) {
                                                next = it3.next();
                                                if (this.aiCurrentPos.dst(next.position) < fMin * 1.5f) {
                                                    this.tmpVec.set(next.position).sub(this.aiCurrentPos);
                                                    fDot2 = this.tmpVec.dot(this.aiRayDir);
                                                    if (fDot2 > 0.0f) {
                                                        fLen2 = this.tmpVec.len2() - (fDot2 * fDot2);
                                                        arrayIterator = it3;
                                                        f3 = next.radius * next.radius;
                                                        if (fLen2 < f3) {
                                                            str2 = str4;
                                                            fSqrt = fDot2 - ((float) Math.sqrt(f3 - fLen2));
                                                            if (fSqrt <= 0.0f) {
                                                            }
                                                        }
                                                    } else {
                                                        arrayIterator = it3;
                                                    }
                                                    str2 = str4;
                                                } else {
                                                    arrayIterator = it3;
                                                    str2 = str4;
                                                }
                                                str4 = str2;
                                                it3 = arrayIterator;
                                            }
                                        }
                                        str = str4;
                                        fDot = this.aiRayDir.dot(this.aiDesiredDir) + ((fDst / fMin) * 2.5f) + (this.aiRayDir.dot(vector3Nor) * 0.4f);
                                        if (fDot > f2) {
                                            this.aiBestMoveDir.set(this.aiRayDir);
                                            f2 = fDot;
                                            i2 = i3;
                                        }
                                    }
                                    i3++;
                                    str4 = str;
                                    it7 = it2;
                                    i = 5;
                                }
                                it = it7;
                                if (iIntValue == 0) {
                                    if (i2 < 5) {
                                        this.aiSlideDirs.put(key4, -1);
                                    } else if (i2 > 5) {
                                        this.aiSlideDirs.put(key4, 1);
                                    }
                                }
                            }
                            Vector3 vector3ComputeIfAbsent = this.aiSmoothedDirection.computeIfAbsent(key4, new Function() { // from class: org.catrobat.catroid.raptor.ThreeDManager$$ExternalSyntheticLambda10
                                @Override // java.util.function.Function
                                public final Object apply(Object obj) {
                                    return this.f$0.m5479xbbd75802((String) obj);
                                }
                            });
                            vector3ComputeIfAbsent.lerp(this.aiBestMoveDir, 3.0f * delta).nor();
                            value.currentVelocity.lerp(new Vector3(vector3ComputeIfAbsent).scl(value.speed), 4.0f * delta);
                            this.aiStepPos.set(value.currentVelocity).nor().scl(0.6f);
                            this.aiStepPos.add(this.aiCurrentPos).add(0.0f, value.stepHeight, 0.0f);
                            Vector3 vector3Add6 = new Vector3(this.aiStepPos).add(0.0f, -(value.stepHeight * 2.5f), 0.0f);
                            closestRayResultCallback2 = new ClosestRayResultCallback(this.aiStepPos, vector3Add6);
                            this.dynamicsWorld.rayTest(this.aiStepPos, vector3Add6, closestRayResultCallback2);
                            if (closestRayResultCallback2.hasHit()) {
                                Vector3 vector12 = new Vector3();
                                closestRayResultCallback2.getHitPointWorld(vector12);
                                f6 = vector12.y;
                                if (f6 > this.aiCurrentPos.y + 0.1f) {
                                    f4 = 5.0f;
                                } else if (f6 < this.aiCurrentPos.y - 0.2f) {
                                    f4 = -5.0f;
                                } else {
                                    f4 = 0.0f;
                                }
                            } else {
                                f4 = 0.0f;
                            }
                            closestRayResultCallback2.dispose();
                            fLen3 = value.currentVelocity.len2();
                            quaternion = new Quaternion();
                            if (fLen3 > 0.01f) {
                                float degrees = (float) Math.toDegrees(Math.atan2(value.currentVelocity.x, value.currentVelocity.z));
                                float fLerpAngleDeg = MathUtils.lerpAngleDeg(this.aiCurrentYaws.getOrDefault(key4, Float.valueOf(degrees)).floatValue(), degrees, 3.5f * delta);
                                this.aiCurrentYaws.put(key4, Float.valueOf(fLerpAngleDeg));
                                f5 = 0.0f;
                                quaternion.setEulerAngles(fLerpAngleDeg, 0.0f, 0.0f);
                            } else {
                                f5 = 0.0f;
                                quaternion.setEulerAngles(this.aiCurrentYaws.getOrDefault(key4, Float.valueOf(0.0f)).floatValue(), 0.0f, 0.0f);
                            }
                            btrigidbody = this.physicsBodies.get(key4);
                            if (btrigidbody == null) {
                                this.aiCurrentPos.add(value.currentVelocity.x * delta, f4 * delta, value.currentVelocity.z * delta);
                                quaternion2 = new Quaternion(quaternion);
                                quaternion3 = this.modelRotationOffsets.get(key4);
                                if (quaternion3 != null) {
                                    quaternion2.mul(quaternion3);
                                }
                                modelInstance2.transform.set(this.aiCurrentPos, quaternion2, modelInstance2.transform.getScale(this.tmpScale));
                            } else {
                                this.aiCurrentPos.add(value.currentVelocity.x * delta, f4 * delta, value.currentVelocity.z * delta);
                                quaternion2 = new Quaternion(quaternion);
                                quaternion3 = this.modelRotationOffsets.get(key4);
                                if (quaternion3 != null) {
                                    quaternion2.mul(quaternion3);
                                }
                                modelInstance2.transform.set(this.aiCurrentPos, quaternion2, modelInstance2.transform.getScale(this.tmpScale));
                            }
                            if (this.aiBacktrackTarget.containsKey(key4)) {
                                z2 = false;
                            } else {
                                arrayComputeIfAbsent = this.aiPathHistory.computeIfAbsent(key4, new Function() { // from class: org.catrobat.catroid.raptor.ThreeDManager$$ExternalSyntheticLambda12
                                    @Override // java.util.function.Function
                                    public final Object apply(Object obj) {
                                        return ThreeDManager.lambda$updateAIProcessing$9((String) obj);
                                    }
                                });
                                if (arrayComputeIfAbsent.size != 0) {
                                    arrayComputeIfAbsent.add(new Vector3(this.aiCurrentPos));
                                    if (arrayComputeIfAbsent.size > 50) {
                                        z2 = false;
                                        arrayComputeIfAbsent.removeIndex(0);
                                    } else {
                                        z2 = false;
                                    }
                                } else {
                                    arrayComputeIfAbsent.add(new Vector3(this.aiCurrentPos));
                                    if (arrayComputeIfAbsent.size > 50) {
                                        z2 = false;
                                        arrayComputeIfAbsent.removeIndex(0);
                                    } else {
                                        z2 = false;
                                    }
                                }
                            }
                            it7 = it;
                            z8 = z2;
                            f8 = 0.5f;
                        }
                    }
                } else {
                    if (!value.targetId.isEmpty()) {
                        ModelInstance modelInstance4 = this.sceneObjects.get(value.targetId);
                        if (modelInstance4 == null) {
                            modelInstance4 = this.editorProxies.get(value.targetId);
                        }
                        if (modelInstance4 != null) {
                            modelInstance4.transform.getTranslation(value.targetPos);
                        } else {
                            String[] strArrSplit = value.targetId.trim().split("[,;\\s]+");
                            if (strArrSplit.length >= 3) {
                                try {
                                    value.targetPos.set(Float.parseFloat(strArrSplit[0]), Float.parseFloat(strArrSplit[1]), Float.parseFloat(strArrSplit[2]));
                                } catch (NumberFormatException unused) {
                                }
                            }
                        }
                    }
                    this.aiFinalMoveTarget.set(value.targetPos);
                    vector3 = this.aiLastTargetPos.get(key4);
                    if (vector3 == null) {
                        this.aiLastTargetPos.put(key4, new Vector3(this.aiFinalMoveTarget));
                    } else if (vector3.dst(this.aiFinalMoveTarget) > 15.0f) {
                        this.aiDeadEnds.remove(key4);
                        this.aiPathHistory.remove(key4);
                        this.aiBacktrackTarget.remove(key4);
                        this.aiLastTargetPos.put(key4, new Vector3(this.aiFinalMoveTarget));
                    }
                    if (this.aiCurrentPos.dst(value.lastPosition) < 0.02f) {
                        value.stuckTimer += delta;
                    } else {
                        value.stuckTimer = 0.0f;
                    }
                    value.lastPosition.set(this.aiCurrentPos);
                    f = 1.5f;
                    if (value.stuckTimer > 0.8f) {
                        array2 = this.aiPathHistory.get(key4);
                        vector5 = null;
                        if (array2 != null && array2.size > 1) {
                            i4 = array2.size - 1;
                            while (true) {
                                if (i4 >= 0) {
                                    i4 = -1;
                                    break;
                                }
                                vector7 = array2.get(i4);
                                if (vector7.dst(this.aiCurrentPos) >= 6.0f) {
                                    vector5 = vector7;
                                    break;
                                }
                                i4--;
                            }
                            if (i4 != -1) {
                                arrayComputeIfAbsent2 = this.aiDeadEnds.computeIfAbsent(key4, new Function() { // from class: org.catrobat.catroid.raptor.ThreeDManager$$ExternalSyntheticLambda8
                                    @Override // java.util.function.Function
                                    public final Object apply(Object obj) {
                                        return ThreeDManager.lambda$updateAIProcessing$6((String) obj);
                                    }
                                });
                                int i8 = i4 + 1;
                                i5 = i8;
                                while (i5 < array2.size) {
                                    vector6 = array2.get(i5);
                                    it6 = arrayComputeIfAbsent2.iterator();
                                    while (true) {
                                        if (it6.hasNext()) {
                                            if (it6.next().position.dst(vector6) < f) {
                                                z3 = true;
                                                break;
                                            }
                                        } else {
                                            z3 = false;
                                            break;
                                        }
                                    }
                                    if (!z3) {
                                        arrayComputeIfAbsent2.add(new DeadEndZone(new Vector3(vector6), 3.0f));
                                    }
                                    i5++;
                                    f = 1.5f;
                                }
                                if (arrayComputeIfAbsent2.size > 30) {
                                    arrayComputeIfAbsent2.removeRange(0, arrayComputeIfAbsent2.size - 30);
                                }
                                array2.truncate(i8);
                                this.aiBacktrackTarget.put(key4, new Vector3(vector5));
                            }
                        }
                        if (vector5 == null) {
                            this.aiDeadEnds.computeIfAbsent(key4, new Function() { // from class: org.catrobat.catroid.raptor.ThreeDManager$$ExternalSyntheticLambda9
                                @Override // java.util.function.Function
                                public final Object apply(Object obj) {
                                    return ThreeDManager.lambda$updateAIProcessing$7((String) obj);
                                }
                            }).add(new DeadEndZone(new Vector3(this.aiCurrentPos), 4.5f));
                            this.aiDesiredDir.scl(-1.0f);
                        }
                        value.stuckTimer = 0.0f;
                    }
                    vector4 = this.aiBacktrackTarget.get(key4);
                    if (vector4 != null) {
                        if (this.aiCurrentPos.dst(vector4) < 1.2f) {
                            this.aiBacktrackTarget.remove(key4);
                        } else {
                            this.aiFinalMoveTarget.set(vector4);
                        }
                    }
                    if (this.aiCurrentPos.dst(this.aiFinalMoveTarget) <= value.stopDistance) {
                        value.currentVelocity.lerp(Vector3.Zero, delta * 5.0f);
                        stopPhysicsVelocity(key4);
                        f8 = 0.5f;
                        z8 = false;
                    } else {
                        this.aiDesiredDir.set(this.aiFinalMoveTarget).sub(this.aiCurrentPos);
                        if (((float) Math.sqrt((this.aiDesiredDir.x * this.aiDesiredDir.x) + (this.aiDesiredDir.z * this.aiDesiredDir.z))) >= 1.3f && Math.abs(this.aiFinalMoveTarget.y - this.aiCurrentPos.y) > 1.5f && !value.currentVelocity.isZero(0.01f)) {
                            this.aiDesiredDir.set(value.currentVelocity).nor();
                        } else {
                            this.aiDesiredDir.y = 0.0f;
                            this.aiDesiredDir.nor();
                        }
                        vector3Nor = new Vector3(value.currentVelocity).nor();
                        if (vector3Nor.isZero()) {
                            vector3Nor.set(this.aiDesiredDir);
                        }
                        fMin = Math.min(3.0f, Math.max(1.2f, value.detectionRange));
                        if (value.avoidObstacles) {
                            Vector3 vector3Add7 = new Vector3(this.aiCurrentPos).add(this.aiDesiredDir.x * fMin, 0.0f, this.aiDesiredDir.z * fMin);
                            closestRayResultCallback3 = new ClosestRayResultCallback(this.aiCurrentPos, vector3Add7);
                            this.dynamicsWorld.rayTest(this.aiCurrentPos, vector3Add7, closestRayResultCallback3);
                            if (closestRayResultCallback3.hasHit()) {
                                collisionObject2 = closestRayResultCallback3.getCollisionObject();
                                it5 = this.physicsBodies.entrySet().iterator();
                                while (true) {
                                    if (it5.hasNext()) {
                                        key2 = "";
                                        break;
                                    }
                                    next3 = it5.next();
                                    if (next3.getValue().equals(collisionObject2)) {
                                        key2 = next3.getKey();
                                        break;
                                    }
                                }
                                if (key2.equals(value.targetId) || key2.equals(key4)) {
                                    z = false;
                                } else {
                                    Vector3 vector13 = new Vector3();
                                    closestRayResultCallback3.getHitPointWorld(vector13);
                                    if (this.aiCurrentPos.dst(vector13) < 0.85f * fMin) {
                                        z = true;
                                    } else {
                                        z = false;
                                    }
                                }
                            } else {
                                z = false;
                            }
                            closestRayResultCallback3.dispose();
                        } else {
                            z = false;
                        }
                        this.aiBestMoveDir.set(this.aiDesiredDir);
                        if (!z) {
                            this.aiBestMoveDir.set(this.aiDesiredDir);
                            this.aiSlideDirs.put(key4, 0);
                            it = it7;
                        } else {
                            iIntValue = this.aiSlideDirs.getOrDefault(key4, 0).intValue();
                            i = 5;
                            i2 = 5;
                            f2 = -1000.0f;
                            i3 = 0;
                            while (i3 < 11) {
                                if ((iIntValue == -1 || i3 <= i) && (iIntValue != 1 || i3 >= i)) {
                                    this.aiRayDir.set(this.aiDesiredDir).rotate(Vector3.Y, (i3 - 5) * 15.0f);
                                    if (value.avoidObstacles) {
                                        Vector3 vector3Add8 = new Vector3(this.aiCurrentPos).add(this.aiRayDir.x * fMin, 0.0f, this.aiRayDir.z * fMin);
                                        closestRayResultCallback = new ClosestRayResultCallback(this.aiCurrentPos, vector3Add8);
                                        this.dynamicsWorld.rayTest(this.aiCurrentPos, vector3Add8, closestRayResultCallback);
                                        if (closestRayResultCallback.hasHit()) {
                                            collisionObject = closestRayResultCallback.getCollisionObject();
                                            it4 = this.physicsBodies.entrySet().iterator();
                                            while (true) {
                                                if (!it4.hasNext()) {
                                                    it2 = it7;
                                                    key = str4;
                                                    break;
                                                }
                                                next2 = it4.next();
                                                it2 = it7;
                                                if (next2.getValue().equals(collisionObject)) {
                                                    key = next2.getKey();
                                                    break;
                                                }
                                                it7 = it2;
                                            }
                                            if (key.equals(value.targetId) && !key.equals(key4)) {
                                                Vector3 vector14 = new Vector3();
                                                closestRayResultCallback.getHitPointWorld(vector14);
                                                fDst = this.aiCurrentPos.dst(vector14);
                                            }
                                            closestRayResultCallback.dispose();
                                        } else {
                                            it2 = it7;
                                        }
                                        fDst = fMin;
                                        closestRayResultCallback.dispose();
                                    } else {
                                        it2 = it7;
                                        fDst = fMin;
                                    }
                                    array = this.aiDeadEnds.get(key4);
                                    if (array != null) {
                                        it3 = array.iterator();
                                        while (it3.hasNext()) {
                                            next = it3.next();
                                            if (this.aiCurrentPos.dst(next.position) < fMin * 1.5f) {
                                                this.tmpVec.set(next.position).sub(this.aiCurrentPos);
                                                fDot2 = this.tmpVec.dot(this.aiRayDir);
                                                if (fDot2 > 0.0f) {
                                                    fLen2 = this.tmpVec.len2() - (fDot2 * fDot2);
                                                    arrayIterator = it3;
                                                    f3 = next.radius * next.radius;
                                                    if (fLen2 < f3) {
                                                        str2 = str4;
                                                        fSqrt = fDot2 - ((float) Math.sqrt(f3 - fLen2));
                                                        if (fSqrt <= 0.0f && fSqrt < fDst) {
                                                            fDst = fSqrt;
                                                        }
                                                    }
                                                } else {
                                                    arrayIterator = it3;
                                                }
                                                str2 = str4;
                                            } else {
                                                arrayIterator = it3;
                                                str2 = str4;
                                            }
                                            str4 = str2;
                                            it3 = arrayIterator;
                                        }
                                    }
                                    str = str4;
                                    fDot = this.aiRayDir.dot(this.aiDesiredDir) + ((fDst / fMin) * 2.5f) + (this.aiRayDir.dot(vector3Nor) * 0.4f);
                                    if (fDot > f2) {
                                        this.aiBestMoveDir.set(this.aiRayDir);
                                        f2 = fDot;
                                        i2 = i3;
                                    }
                                } else {
                                    it2 = it7;
                                    str = str4;
                                }
                                i3++;
                                str4 = str;
                                it7 = it2;
                                i = 5;
                            }
                            it = it7;
                            if (iIntValue == 0) {
                                if (i2 < 5) {
                                    this.aiSlideDirs.put(key4, -1);
                                } else if (i2 > 5) {
                                    this.aiSlideDirs.put(key4, 1);
                                }
                            }
                        }
                        Vector3 vector3ComputeIfAbsent2 = this.aiSmoothedDirection.computeIfAbsent(key4, new Function() { // from class: org.catrobat.catroid.raptor.ThreeDManager$$ExternalSyntheticLambda10
                            @Override // java.util.function.Function
                            public final Object apply(Object obj) {
                                return this.f$0.m5479xbbd75802((String) obj);
                            }
                        });
                        vector3ComputeIfAbsent2.lerp(this.aiBestMoveDir, 3.0f * delta).nor();
                        value.currentVelocity.lerp(new Vector3(vector3ComputeIfAbsent2).scl(value.speed), 4.0f * delta);
                        this.aiStepPos.set(value.currentVelocity).nor().scl(0.6f);
                        this.aiStepPos.add(this.aiCurrentPos).add(0.0f, value.stepHeight, 0.0f);
                        Vector3 vector3Add9 = new Vector3(this.aiStepPos).add(0.0f, -(value.stepHeight * 2.5f), 0.0f);
                        closestRayResultCallback2 = new ClosestRayResultCallback(this.aiStepPos, vector3Add9);
                        this.dynamicsWorld.rayTest(this.aiStepPos, vector3Add9, closestRayResultCallback2);
                        if (closestRayResultCallback2.hasHit()) {
                            Vector3 vector15 = new Vector3();
                            closestRayResultCallback2.getHitPointWorld(vector15);
                            f6 = vector15.y;
                            if (f6 > this.aiCurrentPos.y + 0.1f) {
                                f4 = 5.0f;
                            } else if (f6 < this.aiCurrentPos.y - 0.2f) {
                                f4 = -5.0f;
                            } else {
                                f4 = 0.0f;
                            }
                        } else {
                            f4 = 0.0f;
                        }
                        closestRayResultCallback2.dispose();
                        fLen3 = value.currentVelocity.len2();
                        quaternion = new Quaternion();
                        if (fLen3 > 0.01f) {
                            float degrees2 = (float) Math.toDegrees(Math.atan2(value.currentVelocity.x, value.currentVelocity.z));
                            float fLerpAngleDeg2 = MathUtils.lerpAngleDeg(this.aiCurrentYaws.getOrDefault(key4, Float.valueOf(degrees2)).floatValue(), degrees2, 3.5f * delta);
                            this.aiCurrentYaws.put(key4, Float.valueOf(fLerpAngleDeg2));
                            f5 = 0.0f;
                            quaternion.setEulerAngles(fLerpAngleDeg2, 0.0f, 0.0f);
                        } else {
                            f5 = 0.0f;
                            quaternion.setEulerAngles(this.aiCurrentYaws.getOrDefault(key4, Float.valueOf(0.0f)).floatValue(), 0.0f, 0.0f);
                        }
                        btrigidbody = this.physicsBodies.get(key4);
                        if (btrigidbody == null && btrigidbody.getInvMass() > f5) {
                            btrigidbody.activate();
                            float f9 = value.currentVelocity.x;
                            if (f4 == f5) {
                                f4 = btrigidbody.getLinearVelocity().y;
                            }
                            btrigidbody.setLinearVelocity(new Vector3(f9, f4, value.currentVelocity.z));
                            Matrix4 worldTransform = btrigidbody.getWorldTransform();
                            Vector3 vector16 = new Vector3();
                            worldTransform.getTranslation(vector16);
                            worldTransform.set(vector16, quaternion);
                            btrigidbody.setWorldTransform(worldTransform);
                            if (modelInstance2 != null) {
                                Quaternion quaternion4 = new Quaternion(quaternion);
                                Quaternion quaternion5 = this.modelRotationOffsets.get(key4);
                                if (quaternion5 != null) {
                                    quaternion4.mul(quaternion5);
                                }
                                modelInstance2.transform.getScale(this.tmpScale);
                                modelInstance2.transform.set(vector16, quaternion4, this.tmpScale);
                            }
                        } else {
                            this.aiCurrentPos.add(value.currentVelocity.x * delta, f4 * delta, value.currentVelocity.z * delta);
                            quaternion2 = new Quaternion(quaternion);
                            quaternion3 = this.modelRotationOffsets.get(key4);
                            if (quaternion3 != null) {
                                quaternion2.mul(quaternion3);
                            }
                            modelInstance2.transform.set(this.aiCurrentPos, quaternion2, modelInstance2.transform.getScale(this.tmpScale));
                        }
                        if (this.aiBacktrackTarget.containsKey(key4)) {
                            arrayComputeIfAbsent = this.aiPathHistory.computeIfAbsent(key4, new Function() { // from class: org.catrobat.catroid.raptor.ThreeDManager$$ExternalSyntheticLambda12
                                @Override // java.util.function.Function
                                public final Object apply(Object obj) {
                                    return ThreeDManager.lambda$updateAIProcessing$9((String) obj);
                                }
                            });
                            if (arrayComputeIfAbsent.size != 0 || arrayComputeIfAbsent.peek().dst(this.aiCurrentPos) > 1.5f) {
                                arrayComputeIfAbsent.add(new Vector3(this.aiCurrentPos));
                                if (arrayComputeIfAbsent.size > 50) {
                                    z2 = false;
                                    arrayComputeIfAbsent.removeIndex(0);
                                } else {
                                    z2 = false;
                                }
                            } else {
                                z2 = false;
                            }
                        } else {
                            z2 = false;
                        }
                        it7 = it;
                        z8 = z2;
                        f8 = 0.5f;
                    }
                }
            }
        }
    }

    static /* synthetic */ Array lambda$updateAIProcessing$5(String str) {
        return new Array();
    }

    static /* synthetic */ Array lambda$updateAIProcessing$6(String str) {
        return new Array();
    }

    static /* synthetic */ Array lambda$updateAIProcessing$7(String str) {
        return new Array();
    }

    /* JADX INFO: renamed from: lambda$updateAIProcessing$8$org-catrobat-catroid-raptor-ThreeDManager, reason: not valid java name */
    /* synthetic */ Vector3 m5479xbbd75802(String str) {
        return new Vector3(this.aiBestMoveDir);
    }

    static /* synthetic */ Array lambda$updateAIProcessing$9(String str) {
        return new Array();
    }

    public boolean hasActiveAI(String objectId) {
        return this.activeAIs.containsKey(objectId);
    }

    private Vector3 calculateRayAvoidance(String ownerId, Vector3 origin, Vector3 rayDir, String targetId, float weight) {
        Vector3 vector3 = new Vector3();
        String str = "ai_ray_" + ownerId + rayDir.hashCode();
        castRay(str, origin, rayDir);
        if (getRayDidHit(str)) {
            String raycastHitObjectId = getRaycastHitObjectId(str);
            if (!raycastHitObjectId.equals(targetId) && !raycastHitObjectId.equals(ownerId)) {
                vector3.set(new Vector3(getRayHitNormalX(str), 0.0f, getRayHitNormalZ(str))).scl((1.0f - (getRaycastDistance(str) / rayDir.len())) * weight * 5.0f);
            }
        }
        return vector3;
    }

    public void setRenderResolution(float scale, int mode) {
        this.renderScale = Math.max(0.05f, Math.min(1.0f, scale));
        this.aspectMode = mode;
        Gdx.f82app.postRunnable(new Runnable() { // from class: org.catrobat.catroid.raptor.ThreeDManager.2
            @Override // java.lang.Runnable
            public void run() {
                ThreeDManager.this.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            }
        });
    }

    private void stopPhysicsVelocity(String id2) {
        btRigidBody btrigidbody = this.physicsBodies.get(id2);
        if (btrigidbody != null) {
            btrigidbody.setLinearVelocity(new Vector3(0.0f, btrigidbody.getLinearVelocity().y, 0.0f));
        }
    }

    public void flagObjectForHiddenSpawn(String objectId) {
        this.hiddenSpawnIds.add(objectId);
    }

    public void createParticleProxy(String ownerId) {
        if (this.editorProxies.containsKey(ownerId) || this.particleProxyModel == null) {
            return;
        }
        this.editorProxies.put(ownerId, new ModelInstance(this.particleProxyModel));
    }

    private BillboardParticleBatch getBatchFor(Texture texture, boolean isAdditive) {
        BlendingAttribute blendingAttribute;
        DepthTestAttribute depthTestAttribute;
        ensureParticleSystemInitialized();
        String str = texture.getTextureObjectHandle() + (isAdditive ? "_ADD" : "_NRM");
        BillboardParticleBatch billboardParticleBatch = this.managedBatches.get(str);
        if (billboardParticleBatch == null) {
            Gdx.f82app.log("ParticleBatchManager", "Creating new batch for key: " + str);
            if (isAdditive) {
                blendingAttribute = new BlendingAttribute(GL20.GL_SRC_ALPHA, 1, 1.0f);
                depthTestAttribute = new DepthTestAttribute(515, false);
            } else {
                blendingAttribute = new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA, 1.0f);
                depthTestAttribute = new DepthTestAttribute(515, true);
            }
            billboardParticleBatch = new BillboardParticleBatch(ParticleShader.AlignMode.Screen, true, 1000, blendingAttribute, depthTestAttribute);
            billboardParticleBatch.setCamera(this.camera);
            billboardParticleBatch.setTexture(texture);
            this.particleSystem.add(billboardParticleBatch);
            this.managedBatches.put(str, billboardParticleBatch);
        }
        billboardParticleBatch.setTexture(texture);
        return billboardParticleBatch;
    }

    private void updateParticles(float delta) {
        if (!this.particleSystemInitialized || this.activeParticleEffects.isEmpty()) {
            return;
        }
        this.particleSystem.begin();
        for (ParticleEffect particleEffect : this.activeParticleEffects.values()) {
            particleEffect.update(delta);
            particleEffect.draw();
        }
        this.particleSystem.end();
    }

    private void createDefaultParticleTexture() {
        Gdx.f82app.log("PARTICLE_DEBUG", "Creating default white gradient texture...");
        Pixmap pixmap = new Pixmap(64, 64, Pixmap.Format.RGBA8888);
        float f = 64 / 2.0f;
        for (int i = 0; i < 64; i++) {
            for (int i2 = 0; i2 < 64; i2++) {
                float f2 = i - f;
                float f3 = i2 - f;
                float fMax = 1.0f - Math.max(0.0f, 1.0f - (((float) Math.sqrt((f2 * f2) + (f3 * f3))) / f));
                pixmap.setColor(1.0f, 1.0f, 1.0f, 1.0f - (fMax * fMax));
                pixmap.drawPixel(i, i2);
            }
        }
        this.defaultParticleTexture = new Texture(pixmap);
        pixmap.dispose();
        Gdx.f82app.log("PARTICLE_DEBUG", "Default texture created. Handle: " + this.defaultParticleTexture.getTextureObjectHandle());
    }

    private void ensureParticleSystemInitialized() {
        if (this.particleSystemInitialized) {
            return;
        }
        Gdx.f82app.log("ParticleSystem", "First particle effect created. Initializing system...");
        this.particleModelBatch = new ModelBatch(new DefaultShaderProvider() { // from class: org.catrobat.catroid.raptor.ThreeDManager.3
            @Override // com.badlogic.gdx.graphics.g3d.utils.DefaultShaderProvider, com.badlogic.gdx.graphics.g3d.utils.BaseShaderProvider
            protected Shader createShader(Renderable renderable) {
                return new ParticleShader(renderable, new ParticleShader.Config());
            }
        });
        this.particleSystem = new ParticleSystem();
        PointSpriteParticleBatch pointSpriteParticleBatch = new PointSpriteParticleBatch();
        this.batchNormal = pointSpriteParticleBatch;
        pointSpriteParticleBatch.setCamera(this.camera);
        this.particleSystem.add(this.batchNormal);
        PointSpriteParticleBatch pointSpriteParticleBatch2 = new PointSpriteParticleBatch();
        this.batchAdditive = pointSpriteParticleBatch2;
        pointSpriteParticleBatch2.setCamera(this.camera);
        this.particleSystem.add(this.batchAdditive);
        this.particleSystemInitialized = true;
    }

    public void updateParticleEffect(final String objectId, final ParticleComponent data, final Matrix4 transform) {
        removeParticleEffect(objectId);
        data.migrateOldDataIfNeeded();
        data.sortGraphs();
        Gdx.f82app.postRunnable(new Runnable() { // from class: org.catrobat.catroid.raptor.ThreeDManager$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5481x87c28c53(data, objectId, transform);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$updateParticleEffect$10$org-catrobat-catroid-raptor-ThreeDManager, reason: not valid java name */
    /* synthetic */ void m5481x87c28c53(ParticleComponent particleComponent, String str, Matrix4 matrix4) {
        Texture textureLoadTexture;
        Texture texture = this.defaultParticleTexture;
        if (particleComponent.texturePath != null && !particleComponent.texturePath.isEmpty() && (textureLoadTexture = loadTexture(particleComponent.texturePath)) != null) {
            texture = textureLoadTexture;
        }
        if (texture == null) {
            return;
        }
        BillboardParticleBatch batchFor = getBatchFor(texture, particleComponent.isAdditive);
        RegularEmitter regularEmitter = new RegularEmitter();
        ParticleController particleController = new ParticleController("PC_" + str, regularEmitter, new BillboardRenderer(batchFor), new Influencer[0]);
        regularEmitter.setContinuous(particleComponent.looping);
        regularEmitter.getDuration().setLow(particleComponent.duration * 1000.0f);
        regularEmitter.getEmission().setHigh(particleComponent.emissionRate);
        regularEmitter.getLife().setHigh(particleComponent.startLifetime * 1000.0f);
        regularEmitter.setMaxParticleCount(particleComponent.maxParticles);
        particleController.influencers.add(new SpawnInfluencer(getPrimitiveSpawnShapeValue(particleComponent)));
        DynamicsInfluencer dynamicsInfluencer = new DynamicsInfluencer();
        if (!particleComponent.speedGraph.isEmpty()) {
            DynamicsModifier.PolarAcceleration polarAcceleration = new DynamicsModifier.PolarAcceleration();
            polarAcceleration.phiValue.setHigh(0.0f, 360.0f);
            polarAcceleration.thetaValue.setHigh(0.0f, particleComponent.coneAngle);
            applyFloatGraph(polarAcceleration.strengthValue, particleComponent.speedGraph);
            dynamicsInfluencer.velocities.add(polarAcceleration);
        }
        if (!particleComponent.gravityGraph.isEmpty()) {
            DynamicsModifier.PolarAcceleration polarAcceleration2 = new DynamicsModifier.PolarAcceleration();
            polarAcceleration2.thetaValue.setHigh(180.0f);
            applyFloatGraph(polarAcceleration2.strengthValue, particleComponent.gravityGraph);
            dynamicsInfluencer.velocities.add(polarAcceleration2);
        }
        if (!particleComponent.vortexGraph.isEmpty()) {
            DynamicsModifier.TangentialAcceleration tangentialAcceleration = new DynamicsModifier.TangentialAcceleration();
            tangentialAcceleration.phiValue.setHigh(0.0f);
            tangentialAcceleration.thetaValue.setHigh(90.0f);
            applyFloatGraph(tangentialAcceleration.strengthValue, particleComponent.vortexGraph);
            dynamicsInfluencer.velocities.add(tangentialAcceleration);
        }
        if (!particleComponent.turbulenceGraph.isEmpty()) {
            DynamicsModifier.BrownianAcceleration brownianAcceleration = new DynamicsModifier.BrownianAcceleration();
            applyFloatGraph(brownianAcceleration.strengthValue, particleComponent.turbulenceGraph);
            dynamicsInfluencer.velocities.add(brownianAcceleration);
        }
        if (!particleComponent.rotationGraph.isEmpty()) {
            DynamicsModifier.Rotational2D rotational2D = new DynamicsModifier.Rotational2D();
            applyFloatGraph(rotational2D.strengthValue, particleComponent.rotationGraph);
            dynamicsInfluencer.velocities.add(rotational2D);
        }
        particleController.influencers.add(dynamicsInfluencer);
        ColorInfluencer.Single single = new ColorInfluencer.Single();
        applyColorGraph(single, particleComponent.colorGraph);
        particleController.influencers.add(single);
        ScaleInfluencer scaleInfluencer = new ScaleInfluencer();
        applyFloatGraph(scaleInfluencer.value, particleComponent.sizeGraph);
        scaleInfluencer.value.setHigh(particleComponent.baseSize);
        particleController.influencers.add(scaleInfluencer);
        particleController.influencers.add(new RegionInfluencer.Single(new TextureRegion(texture)));
        ParticleEffect particleEffect = new ParticleEffect();
        particleEffect.getControllers().add(particleController);
        particleEffect.init();
        particleEffect.start();
        particleEffect.setTransform(matrix4);
        this.particleSystem.add(particleEffect);
        this.activeParticleEffects.put(str, particleEffect);
    }

    private static PrimitiveSpawnShapeValue getPrimitiveSpawnShapeValue(ParticleComponent data) {
        PrimitiveSpawnShapeValue pointSpawnShapeValue;
        int i = AnonymousClass6.$SwitchMap$org$catrobat$catroid$raptor$ParticleComponent$SpawnShape[data.spawnShape.ordinal()];
        if (i == 1) {
            pointSpawnShapeValue = new PointSpawnShapeValue();
        } else if (i == 2) {
            pointSpawnShapeValue = new RectangleSpawnShapeValue();
            pointSpawnShapeValue.setDimensions(data.spawnSize.x * 2.0f, data.spawnSize.y * 2.0f, data.spawnSize.z * 2.0f);
        } else if (i == 3) {
            pointSpawnShapeValue = new EllipseSpawnShapeValue();
            pointSpawnShapeValue.setDimensions(data.spawnSize.x * 2.0f, data.spawnSize.y * 2.0f, data.spawnSize.z * 2.0f);
        } else {
            pointSpawnShapeValue = new CylinderSpawnShapeValue();
            pointSpawnShapeValue.setDimensions(data.spawnSize.x * 2.0f, data.spawnSize.y, data.spawnSize.z * 2.0f);
        }
        pointSpawnShapeValue.setEdges(data.spawnOnSurface);
        return pointSpawnShapeValue;
    }

    private void applyFloatGraph(ScaledNumericValue libgdxVal, List<ParticleCurvePoint<Float>> graph) {
        if (graph.isEmpty()) {
            return;
        }
        float[] fArr = new float[graph.size()];
        float[] fArr2 = new float[graph.size()];
        for (int i = 0; i < graph.size(); i++) {
            fArr[i] = graph.get(i).time;
            fArr2[i] = graph.get(i).value.floatValue();
        }
        libgdxVal.setTimeline(fArr);
        libgdxVal.setScaling(fArr2);
        libgdxVal.setHigh(1.0f);
    }

    private void applyColorGraph(ColorInfluencer.Single colorInf, List<ParticleCurvePoint<Color>> graph) {
        if (graph.isEmpty()) {
            return;
        }
        float[] fArr = new float[graph.size()];
        float[] fArr2 = new float[graph.size() * 3];
        float[] fArr3 = new float[graph.size()];
        for (int i = 0; i < graph.size(); i++) {
            ParticleCurvePoint<Color> particleCurvePoint = graph.get(i);
            fArr[i] = particleCurvePoint.time;
            int i2 = i * 3;
            fArr2[i2] = particleCurvePoint.value.r;
            fArr2[i2 + 1] = particleCurvePoint.value.g;
            fArr2[i2 + 2] = particleCurvePoint.value.b;
            fArr3[i] = particleCurvePoint.value.a;
        }
        colorInf.colorValue.setTimeline(fArr);
        colorInf.colorValue.setColors(fArr2);
        colorInf.alphaValue.setTimeline(fArr);
        colorInf.alphaValue.setScaling(fArr3);
        colorInf.alphaValue.setHigh(1.0f);
    }

    public void removeParticleEffect(String objectId) {
        ParticleEffect particleEffectRemove = this.activeParticleEffects.remove(objectId);
        if (particleEffectRemove != null) {
            Gdx.f82app.log("ParticleLifecycle", "Removing effect: " + objectId);
            this.particleSystem.remove(particleEffectRemove);
            particleEffectRemove.dispose();
        }
    }

    public void updateParticleTransform(String objectId, Matrix4 transform) {
        ParticleEffect particleEffect = this.activeParticleEffects.get(objectId);
        if (particleEffect != null) {
            particleEffect.setTransform(transform);
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/ThreeDManager$BufferPipeline.class
      classes14.dex
     */
    public class BufferPipeline implements Disposable {
        public OptimizedBloomEffect bloomEffect;
        public FrameBuffer depthFbo;
        public DepthOfFieldEffect dofEffect;
        public ExposureEffect exposureEffect;
        public FxaaEffect fxaaEffect;
        public GaussianBlurEffect gaussianBlurEffect;
        public GodRaysEffect godRaysEffect;
        public HeightFogEffect heightFogEffect;
        public LevelsEffect levelsEffect;
        public LinearizeEffect linearizeEffect;
        public FrameBuffer materialFbo;
        public MotionBlurEffect motionBlurEffect;
        public SsrRayTracingEffect rayTracingEffect;
        public SsaoEffect ssaoEffect;
        public SsgiEffect ssgiEffect;
        public TonemappingEffect tonemappingEffect;
        public VfxManager vfxManager;
        public VignettingEffect vignetteEffect;
        public VolumetricFogEffect volumetricFogEffect;

        public BufferPipeline(int width, int height) {
            VfxManager vfxManager = new VfxManager(Pixmap.Format.RGBA8888);
            this.vfxManager = vfxManager;
            vfxManager.resize(width, height);
            FrameBuffer frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, width, height, true);
            this.depthFbo = frameBuffer;
            frameBuffer.getColorBufferTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
            this.materialFbo = new FrameBuffer(Pixmap.Format.RGBA8888, width / 2, height / 2, true);
        }

        @Override // com.badlogic.gdx.utils.Disposable
        public void dispose() {
            VfxManager vfxManager = this.vfxManager;
            if (vfxManager != null) {
                vfxManager.dispose();
            }
            FrameBuffer frameBuffer = this.depthFbo;
            if (frameBuffer != null) {
                frameBuffer.dispose();
            }
            FrameBuffer frameBuffer2 = this.materialFbo;
            if (frameBuffer2 != null) {
                frameBuffer2.dispose();
            }
        }
    }

    public void setupBufferPipeline(final FrameBuffer targetFbo, final int width, final int height, final boolean enable) {
        Gdx.f82app.postRunnable(new Runnable() { // from class: org.catrobat.catroid.raptor.ThreeDManager$$ExternalSyntheticLambda16
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5478x4bbcbe4f(enable, targetFbo, width, height);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$setupBufferPipeline$11$org-catrobat-catroid-raptor-ThreeDManager, reason: not valid java name */
    /* synthetic */ void m5478x4bbcbe4f(boolean z, FrameBuffer frameBuffer, int i, int i2) {
        if (!z) {
            BufferPipeline bufferPipelineRemove = this.bufferPipelines.remove(frameBuffer);
            if (bufferPipelineRemove != null) {
                bufferPipelineRemove.dispose();
                return;
            }
            return;
        }
        BufferPipeline bufferPipeline = this.bufferPipelines.get(frameBuffer);
        if (bufferPipeline == null || bufferPipeline.vfxManager.getWidth() != i || bufferPipeline.vfxManager.getHeight() != i2) {
            if (bufferPipeline != null) {
                bufferPipeline.dispose();
            }
            bufferPipeline = new BufferPipeline(i, i2);
            this.bufferPipelines.put(frameBuffer, bufferPipeline);
        }
        configurePipelineEffects(bufferPipeline, this.currentConfig);
    }

    public void removeBufferPipeline(final FrameBuffer targetFbo) {
        Gdx.f82app.postRunnable(new Runnable() { // from class: org.catrobat.catroid.raptor.ThreeDManager$$ExternalSyntheticLambda26
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5473x816bcf(targetFbo);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$removeBufferPipeline$12$org-catrobat-catroid-raptor-ThreeDManager, reason: not valid java name */
    /* synthetic */ void m5473x816bcf(FrameBuffer frameBuffer) {
        BufferPipeline bufferPipelineRemove = this.bufferPipelines.remove(frameBuffer);
        if (bufferPipelineRemove != null) {
            bufferPipelineRemove.dispose();
        }
    }

    private void configurePipelineEffects(BufferPipeline p, PostProcessingComponent config) {
        if (p == null || p.vfxManager == null) {
            return;
        }
        p.vfxManager.removeAllEffects();
        if (config == null || !config.isActive) {
            return;
        }
        if (config.hasEffect(PostProcessingData.ACES.class) || config.hasEffect(PostProcessingData.EyeAdaptation.class)) {
            if (p.linearizeEffect == null) {
                p.linearizeEffect = new LinearizeEffect();
            }
            p.vfxManager.addEffect(p.linearizeEffect);
        }
        if (config.hasEffect(PostProcessingData.EyeAdaptation.class)) {
            if (p.exposureEffect == null) {
                p.exposureEffect = new ExposureEffect();
            }
            p.vfxManager.addEffect(p.exposureEffect);
        }
        for (PostProcessingData postProcessingData : config.effects) {
            if (postProcessingData.isEnabled && !(postProcessingData instanceof PostProcessingData.Upscaler)) {
                if (postProcessingData instanceof PostProcessingData.SSAO) {
                    PostProcessingData.SSAO ssao = (PostProcessingData.SSAO) postProcessingData;
                    if (p.ssaoEffect == null) {
                        p.ssaoEffect = new SsaoEffect();
                    }
                    p.ssaoEffect.setParams(ssao.radius, ssao.intensity, ssao.bias);
                    p.vfxManager.addEffect(p.ssaoEffect);
                } else if (postProcessingData instanceof PostProcessingData.RayTracing) {
                    PostProcessingData.RayTracing rayTracing = (PostProcessingData.RayTracing) postProcessingData;
                    if (p.rayTracingEffect == null) {
                        p.rayTracingEffect = new SsrRayTracingEffect();
                    }
                    p.rayTracingEffect.setParams(rayTracing.steps, rayTracing.reflectivity, rayTracing.thickness, rayTracing.maxDistance, rayTracing.stride, rayTracing.edgeFade);
                    p.vfxManager.addEffect(p.rayTracingEffect);
                } else if (postProcessingData instanceof PostProcessingData.SSGI) {
                    PostProcessingData.SSGI ssgi = (PostProcessingData.SSGI) postProcessingData;
                    if (p.ssgiEffect == null) {
                        p.ssgiEffect = new SsgiEffect();
                    }
                    p.ssgiEffect.setParams(ssgi.radius, ssgi.intensity, ssgi.bias, ssgi.ssaoStrength, new Color(ssgi.baseAlbedoR, ssgi.baseAlbedoG, ssgi.baseAlbedoB, 1.0f), ssgi.flipDepth);
                    p.vfxManager.addEffect(p.ssgiEffect);
                } else if (postProcessingData instanceof PostProcessingData.GodRays) {
                    PostProcessingData.GodRays godRays = (PostProcessingData.GodRays) postProcessingData;
                    if (p.godRaysEffect == null) {
                        p.godRaysEffect = new GodRaysEffect();
                    }
                    p.godRaysEffect.exposure = godRays.exposure;
                    p.godRaysEffect.decay = godRays.decay;
                    p.godRaysEffect.density = godRays.density;
                    p.godRaysEffect.weight = godRays.weight;
                    p.godRaysEffect.setSunDirection(getSunLightDirection());
                    p.vfxManager.addEffect(p.godRaysEffect);
                } else if (postProcessingData instanceof PostProcessingData.HeightFog) {
                    PostProcessingData.HeightFog heightFog = (PostProcessingData.HeightFog) postProcessingData;
                    if (p.heightFogEffect == null) {
                        p.heightFogEffect = new HeightFogEffect();
                    }
                    p.heightFogEffect.fogDensity = heightFog.density;
                    p.heightFogEffect.heightFalloff = heightFog.falloff;
                    p.heightFogEffect.fogHeight = heightFog.height;
                    p.heightFogEffect.fogColor.set(heightFog.color);
                    p.vfxManager.addEffect(p.heightFogEffect);
                } else if (postProcessingData instanceof PostProcessingData.VolumetricFog) {
                    PostProcessingData.VolumetricFog volumetricFog = (PostProcessingData.VolumetricFog) postProcessingData;
                    if (p.volumetricFogEffect == null) {
                        p.volumetricFogEffect = new VolumetricFogEffect();
                    }
                    DirectionalLightEx directionalLightEx = this.pbrLight;
                    if (directionalLightEx instanceof DirectionalShadowLight) {
                        DirectionalShadowLight directionalShadowLight = (DirectionalShadowLight) directionalLightEx;
                        p.volumetricFogEffect.setShadowMap(directionalShadowLight.getFrameBuffer().getColorBufferTexture(), directionalShadowLight.getCamera().combined);
                        p.volumetricFogEffect.setLightParams(directionalShadowLight.direction, directionalShadowLight.color);
                    }
                    p.volumetricFogEffect.setParams(volumetricFog.density, volumetricFog.scattering, volumetricFog.steps, volumetricFog.maxDistance);
                    p.vfxManager.addEffect(p.volumetricFogEffect);
                } else if (postProcessingData instanceof PostProcessingData.DepthOfField) {
                    PostProcessingData.DepthOfField depthOfField = (PostProcessingData.DepthOfField) postProcessingData;
                    if (p.dofEffect == null) {
                        p.dofEffect = new DepthOfFieldEffect();
                    }
                    p.dofEffect.focusDistance = depthOfField.focusDistance;
                    p.dofEffect.focusRange = depthOfField.focusRange;
                    p.dofEffect.blurSize = depthOfField.blurSize;
                    p.dofEffect.transition = depthOfField.transition;
                    p.vfxManager.addEffect(p.dofEffect);
                } else if (postProcessingData instanceof PostProcessingData.Bloom) {
                    PostProcessingData.Bloom bloom = (PostProcessingData.Bloom) postProcessingData;
                    if (p.bloomEffect == null) {
                        p.bloomEffect = new OptimizedBloomEffect(bloom.size);
                    }
                    p.bloomEffect.setBlurPasses(bloom.blurPasses);
                    p.bloomEffect.setBlurAmount(bloom.blurAmount);
                    p.bloomEffect.setThreshold(bloom.threshold);
                    p.bloomEffect.setBloomIntensity(bloom.intensity);
                    p.vfxManager.addEffect(p.bloomEffect);
                } else if (postProcessingData instanceof PostProcessingData.Vignette) {
                    PostProcessingData.Vignette vignette = (PostProcessingData.Vignette) postProcessingData;
                    if (p.vignetteEffect == null) {
                        p.vignetteEffect = new VignettingEffect(false);
                    }
                    p.vignetteEffect.setIntensity(vignette.intensity);
                    p.vignetteEffect.setSaturation(vignette.saturation);
                    p.vfxManager.addEffect(p.vignetteEffect);
                } else if (postProcessingData instanceof PostProcessingData.Levels) {
                    PostProcessingData.Levels levels = (PostProcessingData.Levels) postProcessingData;
                    if (p.levelsEffect == null) {
                        p.levelsEffect = new LevelsEffect();
                    }
                    p.levelsEffect.setContrast(levels.contrast);
                    p.levelsEffect.setSaturation(levels.saturation);
                    p.levelsEffect.setGamma(levels.gamma);
                    p.vfxManager.addEffect(p.levelsEffect);
                } else if (postProcessingData instanceof PostProcessingData.Grain) {
                    FilmGrainEffect filmGrainEffect = new FilmGrainEffect();
                    filmGrainEffect.setNoiseAmount(((PostProcessingData.Grain) postProcessingData).amount);
                    p.vfxManager.addEffect(filmGrainEffect);
                } else if (postProcessingData instanceof PostProcessingData.Chromatic) {
                    PostProcessingData.Chromatic chromatic = (PostProcessingData.Chromatic) postProcessingData;
                    ChromaticAberrationEffect chromaticAberrationEffect = new ChromaticAberrationEffect((int) chromatic.maxDistortion);
                    chromaticAberrationEffect.setMaxDistortion(chromatic.strength);
                    p.vfxManager.addEffect(chromaticAberrationEffect);
                } else if (postProcessingData instanceof PostProcessingData.RadialBlur) {
                    PostProcessingData.RadialBlur radialBlur = (PostProcessingData.RadialBlur) postProcessingData;
                    OptimizedRadialBlurEffect optimizedRadialBlurEffect = new OptimizedRadialBlurEffect(radialBlur.blurPasses, radialBlur.size);
                    optimizedRadialBlurEffect.setStrength(radialBlur.strength);
                    p.vfxManager.addEffect(optimizedRadialBlurEffect);
                } else if (postProcessingData instanceof PostProcessingData.OldTv) {
                    OldTvEffect oldTvEffect = new OldTvEffect();
                    oldTvEffect.setTime(((PostProcessingData.OldTv) postProcessingData).strength);
                    p.vfxManager.addEffect(oldTvEffect);
                } else if (postProcessingData instanceof PostProcessingData.Gaussian) {
                    PostProcessingData.Gaussian gaussian = (PostProcessingData.Gaussian) postProcessingData;
                    if (p.gaussianBlurEffect == null) {
                        p.gaussianBlurEffect = new OptimizedGaussianBlurEffect(gaussian.size);
                    }
                    p.gaussianBlurEffect.setPasses(gaussian.passes);
                    p.gaussianBlurEffect.setAmount(gaussian.amount);
                    p.vfxManager.addEffect(p.gaussianBlurEffect);
                } else if (postProcessingData instanceof PostProcessingData.Zoom) {
                    PostProcessingData.Zoom zoom = (PostProcessingData.Zoom) postProcessingData;
                    ZoomEffect zoomEffect = new ZoomEffect();
                    zoomEffect.setZoom(zoom.zoom);
                    zoomEffect.setOrigin(zoom.originX, zoom.originY);
                    p.vfxManager.addEffect(zoomEffect);
                } else if (postProcessingData instanceof PostProcessingData.Crt) {
                    p.vfxManager.addEffect(new CrtEffect());
                } else if (postProcessingData instanceof PostProcessingData.Fisheye) {
                    p.vfxManager.addEffect(new FisheyeEffect());
                } else if (postProcessingData instanceof PostProcessingData.Water) {
                    PostProcessingData.Water water = (PostProcessingData.Water) postProcessingData;
                    p.vfxManager.addEffect(new WaterDistortionEffect(water.amount, water.speed));
                } else if (postProcessingData instanceof PostProcessingData.MotionBlur) {
                    PostProcessingData.MotionBlur motionBlur = (PostProcessingData.MotionBlur) postProcessingData;
                    if (p.motionBlurEffect == null) {
                        p.motionBlurEffect = new MotionBlurEffect(Pixmap.Format.RGBA8888, MixEffect.Method.MIX, motionBlur.blurOpacity);
                    }
                    p.vfxManager.addEffect(p.motionBlurEffect);
                } else if (postProcessingData instanceof PostProcessingData.Fxaa) {
                    if (p.fxaaEffect == null) {
                        p.fxaaEffect = new FxaaEffect();
                    }
                    p.vfxManager.addEffect(p.fxaaEffect);
                }
            }
        }
        if (config.hasEffect(PostProcessingData.ACES.class)) {
            if (p.tonemappingEffect == null) {
                p.tonemappingEffect = new TonemappingEffect();
            }
            p.vfxManager.addEffect(p.tonemappingEffect);
        }
    }

    private void renderDepthAndMaterial(Camera targetCamera, FrameBuffer targetDepthFbo, FrameBuffer targetMaterialFbo) {
        net.mgsx.gltf.scene3d.scene.SceneManager sceneManager;
        net.mgsx.gltf.scene3d.scene.SceneManager sceneManager2;
        targetDepthFbo.begin();
        Gdx.gl.glViewport(0, 0, targetDepthFbo.getWidth(), targetDepthFbo.getHeight());
        Gdx.gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        Gdx.gl.glClear(16640);
        this.depthBatch.begin(targetCamera);
        if (this.realisticMode && (sceneManager2 = this.sceneManager) != null) {
            this.depthBatch.render(sceneManager2.getRenderableProviders());
        }
        for (Map.Entry<String, ModelInstance> entry : this.sceneObjects.entrySet()) {
            if (!this.inactiveRenderObjects.contains(entry.getKey())) {
                ModelInstance value = entry.getValue();
                Array.ArrayIterator<Material> it = value.materials.iterator();
                while (true) {
                    if (it.hasNext()) {
                        Material next = it.next();
                        if (next.has(BlendingAttribute.Type) && ((BlendingAttribute) next.get(BlendingAttribute.Type)).opacity < 0.1f) {
                            break;
                        }
                    } else {
                        this.depthBatch.render(value);
                        break;
                    }
                }
            }
        }
        this.depthBatch.end();
        targetDepthFbo.end();
        targetMaterialFbo.begin();
        Gdx.gl.glViewport(0, 0, targetMaterialFbo.getWidth(), targetMaterialFbo.getHeight());
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        Gdx.gl.glClear(16640);
        this.materialBatch.begin(targetCamera);
        if (this.realisticMode && (sceneManager = this.sceneManager) != null) {
            this.materialBatch.render(sceneManager.getRenderableProviders());
        }
        this.materialBatch.render(this.sceneObjects.values());
        this.materialBatch.end();
        targetMaterialFbo.end();
    }

    public void configureVfx(VfxManager vfx, PostProcessingComponent config) {
        configureVfx(vfx, config, this.camera);
    }

    public void configureVfx(VfxManager vfx, PostProcessingComponent config, Camera effectCamera) {
        if (vfx == null) {
            return;
        }
        vfx.removeAllEffects();
        if (!config.isActive) {
            ChainVfxEffect chainVfxEffect = this.customScreenEffect;
            if (chainVfxEffect != null) {
                vfx.addEffect(chainVfxEffect);
                return;
            }
            return;
        }
        if (config.hasEffect(PostProcessingData.ACES.class) || config.hasEffect(PostProcessingData.EyeAdaptation.class)) {
            if (this.linearizeEffect == null) {
                this.linearizeEffect = new LinearizeEffect();
            }
            vfx.addEffect(this.linearizeEffect);
        }
        if (config.hasEffect(PostProcessingData.EyeAdaptation.class)) {
            if (this.exposureEffect == null) {
                this.exposureEffect = new ExposureEffect();
            }
            vfx.addEffect(this.exposureEffect);
        }
        for (PostProcessingData postProcessingData : config.effects) {
            if (postProcessingData.isEnabled && !(postProcessingData instanceof PostProcessingData.Upscaler)) {
                if (postProcessingData instanceof PostProcessingData.SSAO) {
                    setDepthRender(true);
                    PostProcessingData.SSAO ssao = (PostProcessingData.SSAO) postProcessingData;
                    if (this.ssaoEffect == null) {
                        this.ssaoEffect = new SsaoEffect();
                    }
                    this.ssaoEffect.setCamera(effectCamera);
                    this.ssaoEffect.setDepthTexture(this.depthFbo.getColorBufferTexture());
                    this.ssaoEffect.setParams(ssao.radius, ssao.intensity, ssao.bias);
                    vfx.addEffect(this.ssaoEffect);
                } else if (postProcessingData instanceof PostProcessingData.RayTracing) {
                    setDepthRender(true);
                    PostProcessingData.RayTracing rayTracing = (PostProcessingData.RayTracing) postProcessingData;
                    if (this.rayTracingEffect == null) {
                        this.rayTracingEffect = new SsrRayTracingEffect();
                    }
                    this.rayTracingEffect.setCamera(effectCamera);
                    this.rayTracingEffect.setDepthTexture(this.depthFbo.getColorBufferTexture());
                    this.rayTracingEffect.setParams(rayTracing.steps, rayTracing.reflectivity, rayTracing.thickness, rayTracing.maxDistance, rayTracing.stride, rayTracing.edgeFade);
                    vfx.addEffect(this.rayTracingEffect);
                } else if (postProcessingData instanceof PostProcessingData.SSGI) {
                    setDepthRender(true);
                    PostProcessingData.SSGI ssgi = (PostProcessingData.SSGI) postProcessingData;
                    if (this.ssgiEffect == null) {
                        this.ssgiEffect = new SsgiEffect();
                    }
                    this.ssgiEffect.setCamera(effectCamera);
                    this.ssgiEffect.setDepthTexture(this.depthFbo.getColorBufferTexture());
                    this.ssgiEffect.setParams(ssgi.radius, ssgi.intensity, ssgi.bias, ssgi.ssaoStrength, new Color(ssgi.baseAlbedoR, ssgi.baseAlbedoG, ssgi.baseAlbedoB, 1.0f), ssgi.flipDepth);
                    vfx.addEffect(this.ssgiEffect);
                } else if (postProcessingData instanceof PostProcessingData.GodRays) {
                    setDepthRender(true);
                    PostProcessingData.GodRays godRays = (PostProcessingData.GodRays) postProcessingData;
                    if (this.godRaysEffect == null) {
                        this.godRaysEffect = new GodRaysEffect();
                    }
                    this.godRaysEffect.setCamera(effectCamera);
                    this.godRaysEffect.setDepthTexture(this.depthFbo.getColorBufferTexture());
                    this.godRaysEffect.setSunDirection(getSunLightDirection());
                    this.godRaysEffect.exposure = godRays.exposure;
                    this.godRaysEffect.decay = godRays.decay;
                    this.godRaysEffect.density = godRays.density;
                    this.godRaysEffect.weight = godRays.weight;
                    vfx.addEffect(this.godRaysEffect);
                } else if (postProcessingData instanceof PostProcessingData.HeightFog) {
                    setDepthRender(true);
                    PostProcessingData.HeightFog heightFog = (PostProcessingData.HeightFog) postProcessingData;
                    if (this.heightFogEffect == null) {
                        this.heightFogEffect = new HeightFogEffect();
                    }
                    this.heightFogEffect.setCamera(effectCamera);
                    this.heightFogEffect.setDepthTexture(this.depthFbo.getColorBufferTexture());
                    this.heightFogEffect.fogDensity = heightFog.density;
                    this.heightFogEffect.heightFalloff = heightFog.falloff;
                    this.heightFogEffect.fogHeight = heightFog.height;
                    this.heightFogEffect.fogColor.set(heightFog.color);
                    vfx.addEffect(this.heightFogEffect);
                } else if (postProcessingData instanceof PostProcessingData.VolumetricFog) {
                    setDepthRender(true);
                    PostProcessingData.VolumetricFog volumetricFog = (PostProcessingData.VolumetricFog) postProcessingData;
                    if (this.volumetricFogEffect == null) {
                        this.volumetricFogEffect = new VolumetricFogEffect();
                    }
                    this.volumetricFogEffect.setCamera(effectCamera);
                    this.volumetricFogEffect.setDepthTexture(this.depthFbo.getColorBufferTexture());
                    DirectionalLightEx directionalLightEx = this.pbrLight;
                    if (directionalLightEx instanceof DirectionalShadowLight) {
                        DirectionalShadowLight directionalShadowLight = (DirectionalShadowLight) directionalLightEx;
                        this.volumetricFogEffect.setShadowMap(directionalShadowLight.getFrameBuffer().getColorBufferTexture(), directionalShadowLight.getCamera().combined);
                        this.volumetricFogEffect.setLightParams(directionalShadowLight.direction, directionalShadowLight.color);
                    }
                    this.volumetricFogEffect.setParams(volumetricFog.density, volumetricFog.scattering, volumetricFog.steps, volumetricFog.maxDistance);
                    vfx.addEffect(this.volumetricFogEffect);
                } else if (postProcessingData instanceof PostProcessingData.DepthOfField) {
                    setDepthRender(true);
                    PostProcessingData.DepthOfField depthOfField = (PostProcessingData.DepthOfField) postProcessingData;
                    if (this.dofEffect == null) {
                        this.dofEffect = new DepthOfFieldEffect();
                    }
                    this.dofEffect.setCamera(effectCamera);
                    this.dofEffect.setDepthTexture(this.depthFbo.getColorBufferTexture());
                    this.dofEffect.focusDistance = depthOfField.focusDistance;
                    this.dofEffect.focusRange = depthOfField.focusRange;
                    this.dofEffect.blurSize = depthOfField.blurSize;
                    this.dofEffect.transition = depthOfField.transition;
                    vfx.addEffect(this.dofEffect);
                } else if (postProcessingData instanceof PostProcessingData.Bloom) {
                    PostProcessingData.Bloom bloom = (PostProcessingData.Bloom) postProcessingData;
                    if (this.cachedBloomEffect == null) {
                        this.cachedBloomEffect = new OptimizedBloomEffect(bloom.size);
                    }
                    this.cachedBloomEffect.setBlurPasses(bloom.blurPasses);
                    this.cachedBloomEffect.setBlurAmount(bloom.blurAmount);
                    this.cachedBloomEffect.setThreshold(bloom.threshold);
                    this.cachedBloomEffect.setBloomIntensity(bloom.intensity);
                    vfx.addEffect(this.cachedBloomEffect);
                } else if (postProcessingData instanceof PostProcessingData.Vignette) {
                    PostProcessingData.Vignette vignette = (PostProcessingData.Vignette) postProcessingData;
                    if (this.cachedVignetteEffect == null) {
                        this.cachedVignetteEffect = new VignettingEffect(false);
                    }
                    this.cachedVignetteEffect.setIntensity(vignette.intensity);
                    this.cachedVignetteEffect.setSaturation(vignette.saturation);
                    vfx.addEffect(this.cachedVignetteEffect);
                } else if (postProcessingData instanceof PostProcessingData.Levels) {
                    PostProcessingData.Levels levels = (PostProcessingData.Levels) postProcessingData;
                    if (this.cachedLevelsEffect == null) {
                        this.cachedLevelsEffect = new LevelsEffect();
                    }
                    this.cachedLevelsEffect.setContrast(levels.contrast);
                    this.cachedLevelsEffect.setSaturation(levels.saturation);
                    this.cachedLevelsEffect.setGamma(levels.gamma);
                    vfx.addEffect(this.cachedLevelsEffect);
                } else if (postProcessingData instanceof PostProcessingData.Grain) {
                    FilmGrainEffect filmGrainEffect = new FilmGrainEffect();
                    filmGrainEffect.setNoiseAmount(((PostProcessingData.Grain) postProcessingData).amount);
                    vfx.addEffect(filmGrainEffect);
                } else if (postProcessingData instanceof PostProcessingData.Chromatic) {
                    PostProcessingData.Chromatic chromatic = (PostProcessingData.Chromatic) postProcessingData;
                    ChromaticAberrationEffect chromaticAberrationEffect = new ChromaticAberrationEffect((int) chromatic.maxDistortion);
                    chromaticAberrationEffect.setMaxDistortion(chromatic.strength);
                    vfx.addEffect(chromaticAberrationEffect);
                } else if (postProcessingData instanceof PostProcessingData.Fxaa) {
                    vfx.addEffect(new FxaaEffect());
                } else if (postProcessingData instanceof PostProcessingData.RadialBlur) {
                    PostProcessingData.RadialBlur radialBlur = (PostProcessingData.RadialBlur) postProcessingData;
                    OptimizedRadialBlurEffect optimizedRadialBlurEffect = new OptimizedRadialBlurEffect(radialBlur.blurPasses, radialBlur.size);
                    optimizedRadialBlurEffect.setStrength(radialBlur.strength);
                    vfx.addEffect(optimizedRadialBlurEffect);
                } else if (postProcessingData instanceof PostProcessingData.OldTv) {
                    OldTvEffect oldTvEffect = new OldTvEffect();
                    oldTvEffect.setTime(((PostProcessingData.OldTv) postProcessingData).strength);
                    vfx.addEffect(oldTvEffect);
                } else if (postProcessingData instanceof PostProcessingData.Gaussian) {
                    PostProcessingData.Gaussian gaussian = (PostProcessingData.Gaussian) postProcessingData;
                    if (this.cachedGaussianBlurEffect == null) {
                        this.cachedGaussianBlurEffect = new OptimizedGaussianBlurEffect(gaussian.size);
                    }
                    this.cachedGaussianBlurEffect.setPasses(gaussian.passes);
                    this.cachedGaussianBlurEffect.setAmount(gaussian.amount);
                    vfx.addEffect(this.cachedGaussianBlurEffect);
                } else if (postProcessingData instanceof PostProcessingData.Zoom) {
                    PostProcessingData.Zoom zoom = (PostProcessingData.Zoom) postProcessingData;
                    ZoomEffect zoomEffect = new ZoomEffect();
                    zoomEffect.setZoom(zoom.zoom);
                    zoomEffect.setOrigin(zoom.originX, zoom.originY);
                    vfx.addEffect(zoomEffect);
                } else if (postProcessingData instanceof PostProcessingData.Crt) {
                    vfx.addEffect(new CrtEffect());
                } else if (postProcessingData instanceof PostProcessingData.Fisheye) {
                    vfx.addEffect(new FisheyeEffect());
                } else if (postProcessingData instanceof PostProcessingData.Water) {
                    PostProcessingData.Water water = (PostProcessingData.Water) postProcessingData;
                    vfx.addEffect(new WaterDistortionEffect(water.amount, water.speed));
                } else if (postProcessingData instanceof PostProcessingData.MotionBlur) {
                    PostProcessingData.MotionBlur motionBlur = (PostProcessingData.MotionBlur) postProcessingData;
                    if (this.cachedMotionBlurEffect == null) {
                        this.cachedMotionBlurEffect = new MotionBlurEffect(Pixmap.Format.RGBA8888, MixEffect.Method.MIX, motionBlur.blurOpacity);
                    }
                    vfx.addEffect(this.cachedMotionBlurEffect);
                } else if (postProcessingData instanceof PostProcessingData.LensFlare) {
                    PostProcessingData.LensFlare lensFlare = (PostProcessingData.LensFlare) postProcessingData;
                    AutoLensFlareEffect autoLensFlareEffect = new AutoLensFlareEffect();
                    autoLensFlareEffect.setThreshold(lensFlare.threshold);
                    autoLensFlareEffect.setIntensity(lensFlare.intensity * 2.0f);
                    autoLensFlareEffect.setDispersal(lensFlare.dispersal);
                    autoLensFlareEffect.setSize(lensFlare.size);
                    vfx.addEffect(autoLensFlareEffect);
                }
            }
        }
        if (config.hasEffect(PostProcessingData.ACES.class)) {
            if (this.tonemappingEffect == null) {
                this.tonemappingEffect = new TonemappingEffect();
            }
            vfx.addEffect(this.tonemappingEffect);
        }
        ChainVfxEffect chainVfxEffect2 = this.customScreenEffect;
        if (chainVfxEffect2 != null) {
            vfx.addEffect(chainVfxEffect2);
        }
    }

    public void updatePostProcessing(final PostProcessingComponent config) {
        this.currentConfig = config;
        Gdx.f82app.postRunnable(new Runnable() { // from class: org.catrobat.catroid.raptor.ThreeDManager$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5483x15c711fa(config);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$updatePostProcessing$13$org-catrobat-catroid-raptor-ThreeDManager, reason: not valid java name */
    /* synthetic */ void m5483x15c711fa(PostProcessingComponent postProcessingComponent) {
        if (this.vfxManager == null) {
            return;
        }
        this.postprocessingEnabled = postProcessingComponent.isActive;
        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();
        float fMax = Math.max(0.01f, Math.min(1.0f, postProcessingComponent.qualityScale));
        int i = (int) (width * fMax);
        if (this.vfxManager.getWidth() != i || this.vfxManager.getHeight() != ((int) (height * fMax))) {
            this.vfxManager.resize(i, (int) (height * fMax));
        }
        configureVfx(this.vfxManager, postProcessingComponent);
    }

    public void setObjectVisibility(String objectId, boolean visible) {
        Scene scene;
        ModelInstance modelInstance = this.sceneObjects.get(objectId);
        if (modelInstance == null) {
            return;
        }
        if (visible) {
            btRigidBody btrigidbodyRemove = this.inactivePhysicsBodies.remove(objectId);
            if (btrigidbodyRemove != null) {
                this.dynamicsWorld.addRigidBody(btrigidbodyRemove);
                this.physicsBodies.put(objectId, btrigidbodyRemove);
            }
        } else {
            btRigidBody btrigidbodyRemove2 = this.physicsBodies.remove(objectId);
            if (btrigidbodyRemove2 != null) {
                this.dynamicsWorld.removeRigidBody(btrigidbodyRemove2);
                this.inactivePhysicsBodies.put(objectId, btrigidbodyRemove2);
            }
        }
        if (visible) {
            this.inactiveRenderObjects.remove(objectId);
            if (this.realisticMode && this.inactivePbrScenes.containsKey(objectId)) {
                this.sceneManager.addScene(this.inactivePbrScenes.remove(objectId));
                return;
            }
            return;
        }
        this.inactiveRenderObjects.add(objectId);
        if (this.realisticMode) {
            Array.ArrayIterator<RenderableProvider> it = this.sceneManager.getRenderableProviders().iterator();
            while (true) {
                if (!it.hasNext()) {
                    scene = null;
                    break;
                }
                RenderableProvider next = it.next();
                if (next instanceof Scene) {
                    scene = (Scene) next;
                    if (scene.modelInstance == modelInstance) {
                        break;
                    }
                }
            }
            if (scene != null) {
                this.sceneManager.removeScene(scene);
                this.inactivePbrScenes.put(objectId, scene);
            }
        }
    }

    public void setSceneManager(SceneManager manager) {
        this.manager = manager;
    }

    @Deprecated
    public void setObjectRenderState(String objectId, boolean active) {
        setObjectVisibility(objectId, active);
    }

    public void setObjectPhysicsState(String objectId, boolean active) {
        if (active) {
            btRigidBody btrigidbodyRemove = this.inactivePhysicsBodies.remove(objectId);
            if (btrigidbodyRemove != null) {
                this.dynamicsWorld.addRigidBody(btrigidbodyRemove);
                this.physicsBodies.put(objectId, btrigidbodyRemove);
                return;
            }
            return;
        }
        btRigidBody btrigidbodyRemove2 = this.physicsBodies.remove(objectId);
        if (btrigidbodyRemove2 != null) {
            this.dynamicsWorld.removeRigidBody(btrigidbodyRemove2);
            this.inactivePhysicsBodies.put(objectId, btrigidbodyRemove2);
        }
    }

    public SceneSettings getSceneSettings() {
        SceneSettings sceneSettings = this.currentSettings;
        return sceneSettings != null ? sceneSettings : new SceneSettings();
    }

    public void setCameraFov(float fieldOfView, float near, float far) {
        this.camera.fieldOfView = fieldOfView;
        this.camera.near = near;
        this.camera.far = far;
        this.camera.update();
    }

    public DebugDrawer getDebugDrawer() {
        return this.debugDrawer;
    }

    public void createCameraProxy(String ownerId) {
        if (this.editorProxies.containsKey(ownerId) || this.cameraProxyModel == null) {
            return;
        }
        this.editorProxies.put(ownerId, new ModelInstance(this.cameraProxyModel));
    }

    public boolean createPoint2PointConstraint(String constraintId, String objectIdA, String objectIdB, Vector3 pivotInA, Vector3 pivotInB) {
        if (this.physicsConstraints.containsKey(constraintId)) {
            return false;
        }
        btRigidBody btrigidbody = this.physicsBodies.get(objectIdA);
        btRigidBody btrigidbody2 = this.physicsBodies.get(objectIdB);
        if (btrigidbody == null || btrigidbody2 == null) {
            Gdx.f82app.error("3DManager", "Cannot create constraint: one or both bodies not found.");
            return false;
        }
        btPoint2PointConstraint btpoint2pointconstraint = new btPoint2PointConstraint(btrigidbody, btrigidbody2, pivotInA, pivotInB);
        this.dynamicsWorld.addConstraint(btpoint2pointconstraint, true);
        this.physicsConstraints.put(constraintId, btpoint2pointconstraint);
        return true;
    }

    public void removeConstraint(String constraintId) {
        btTypedConstraint bttypedconstraintRemove = this.physicsConstraints.remove(constraintId);
        if (bttypedconstraintRemove != null) {
            this.dynamicsWorld.removeConstraint(bttypedconstraintRemove);
            bttypedconstraintRemove.dispose();
        }
    }

    public void renderCameraFrustum(Matrix4 ownerTransform, float fov, float far) {
        if (this.debugDrawer == null) {
            return;
        }
        PerspectiveCamera perspectiveCamera = new PerspectiveCamera(fov, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        perspectiveCamera.far = far;
        Quaternion rotation = ownerTransform.getRotation(new Quaternion());
        perspectiveCamera.position.set(ownerTransform.getTranslation(new Vector3()));
        perspectiveCamera.direction.set(0.0f, 0.0f, -1.0f);
        rotation.transform(perspectiveCamera.direction);
        perspectiveCamera.up.set(0.0f, 1.0f, 0.0f);
        rotation.transform(perspectiveCamera.up);
        perspectiveCamera.update();
        Vector3[] vector3Arr = perspectiveCamera.frustum.planePoints;
        this.debugDrawer.drawLine(vector3Arr[4], vector3Arr[5], this.cyanColorVec);
        this.debugDrawer.drawLine(vector3Arr[5], vector3Arr[6], this.cyanColorVec);
        this.debugDrawer.drawLine(vector3Arr[6], vector3Arr[7], this.cyanColorVec);
        this.debugDrawer.drawLine(vector3Arr[7], vector3Arr[4], this.cyanColorVec);
        this.debugDrawer.drawLine(vector3Arr[0], vector3Arr[1], this.cyanColorVec);
        this.debugDrawer.drawLine(vector3Arr[1], vector3Arr[2], this.cyanColorVec);
        this.debugDrawer.drawLine(vector3Arr[2], vector3Arr[3], this.cyanColorVec);
        this.debugDrawer.drawLine(vector3Arr[3], vector3Arr[0], this.cyanColorVec);
        this.debugDrawer.drawLine(vector3Arr[0], vector3Arr[4], this.cyanColorVec);
        this.debugDrawer.drawLine(vector3Arr[1], vector3Arr[5], this.cyanColorVec);
        this.debugDrawer.drawLine(vector3Arr[2], vector3Arr[6], this.cyanColorVec);
        this.debugDrawer.drawLine(vector3Arr[3], vector3Arr[7], this.cyanColorVec);
    }

    public void setCameraRotation(Quaternion rotation) {
        this.camera.direction.set(0.0f, 0.0f, -1.0f);
        rotation.transform(this.camera.direction);
        this.camera.up.set(0.0f, 1.0f, 0.0f);
        rotation.transform(this.camera.up);
        this.camera.update();
    }

    public void setEditorMode(boolean enabled) {
        this.editorMode = enabled;
    }

    public void setPhysicsStateFromComponent(String objectId, PhysicsComponent component) {
        ModelInstance modelInstance = this.sceneObjects.get(objectId);
        if (modelInstance == null) {
            return;
        }
        removePhysicsBody(objectId);
        float f = component.state == PhysicsState.DYNAMIC ? component.mass : 0.0f;
        if (component.state == PhysicsState.NONE) {
            return;
        }
        if (component.state == PhysicsState.MESH_STATIC) {
            if (this.gltfObjectIds.contains(objectId)) {
                createGltfMeshPhysicsBody(objectId, modelInstance);
                return;
            } else {
                createMeshPhysicsBody(objectId, modelInstance);
                return;
            }
        }
        if (component.colliders.isEmpty()) {
            Gdx.f82app.error("3DManager", "setPhysicsStateFromComponent called with no colliders for object: " + objectId);
        } else {
            createCompoundPhysicsBody(objectId, modelInstance, component, f);
        }
    }

    private void createCompoundPhysicsBody(String objectId, ModelInstance instance, PhysicsComponent component, float mass) {
        btCollisionShape btsphereshape;
        Log.d("PhysicsDebug", "--- Creating Compound Physics Body for '" + objectId + "' ---");
        Log.d("PhysicsDebug", "  - Input Mass: " + mass);
        Log.d("PhysicsDebug", "  - Instance World Transform on entry:\n" + instance.transform);
        btCompoundShape btcompoundshape = new btCompoundShape();
        Array<Disposable> array = new Array<>();
        array.add(btcompoundshape);
        Matrix4 matrix4 = instance.transform;
        float[] fArr = matrix4.val;
        boolean z = fArr[0] < 0.0f;
        boolean z2 = fArr[5] < 0.0f;
        boolean z3 = fArr[10] < 0.0f;
        Log.d("PhysicsDebug", "  - Axis flip detection: X=" + z + ", Y=" + z2 + ", Z=" + z3);
        for (ColliderShapeData colliderShapeData : component.colliders) {
            Vector3 vector3 = new Vector3(colliderShapeData.centerOffset);
            if (z) {
                vector3.x *= -1.0f;
            }
            if (z2) {
                vector3.y *= -1.0f;
            }
            if (z3) {
                vector3.z *= -1.0f;
            }
            Log.d("PhysicsDebug", "  - Processing Collider #(тут был номер, но я его убрал): Type=" + colliderShapeData.type + ", Local Offset=" + colliderShapeData.centerOffset + ", Size=" + colliderShapeData.size);
            Log.d("PhysicsDebug", "    - Original Offset: " + colliderShapeData.centerOffset);
            Log.d("PhysicsDebug", "    - Corrected Offset for Physics: " + vector3);
            int i = AnonymousClass6.$SwitchMap$org$catrobat$catroid$raptor$ColliderShapeData$ShapeType[colliderShapeData.type.ordinal()];
            if (i == 1) {
                btsphereshape = new btSphereShape(colliderShapeData.radius);
            } else if (i == 2) {
                btsphereshape = new btCapsuleShape(colliderShapeData.radius, Math.max(0.0f, colliderShapeData.size.y - (colliderShapeData.radius * 2.0f)));
            } else {
                btsphereshape = new btBoxShape(colliderShapeData.size.cpy().scl(0.5f));
            }
            array.add(btsphereshape);
            Matrix4 toTranslation = new Matrix4().setToTranslation(vector3);
            btcompoundshape.addChildShape(toTranslation, btsphereshape);
            Log.d("PhysicsDebug", "    - Added child shape with local transform:\n" + toTranslation);
        }
        Vector3 scale = matrix4.getScale(new Vector3());
        if (scale.x == 0.0f) {
            scale.x = 1.0E-4f;
        }
        if (scale.y == 0.0f) {
            scale.y = 1.0E-4f;
        }
        if (scale.z == 0.0f) {
            scale.z = 1.0E-4f;
        }
        btcompoundshape.setLocalScaling(scale);
        Log.d("PhysicsDebug", "  - Compound Shape Local Scaling set to (abs value): " + scale);
        Vector3 vector4 = new Vector3();
        if (mass > 0.0f) {
            btcompoundshape.calculateLocalInertia(mass, vector4);
        }
        Vector3 vector5 = new Vector3();
        Quaternion quaternion = new Quaternion();
        matrix4.getTranslation(vector5);
        matrix4.getRotation(quaternion, true);
        Matrix4 matrix5 = new Matrix4(vector5, quaternion, new Vector3(1.0f, 1.0f, 1.0f));
        Log.d("PhysicsDebug", "  - Final RigidBody Transform for creation:\n" + matrix5);
        btRigidBody.btRigidBodyConstructionInfo btrigidbodyconstructioninfo = new btRigidBody.btRigidBodyConstructionInfo(mass, new btDefaultMotionState(matrix5), btcompoundshape, vector4);
        btRigidBody btrigidbody = new btRigidBody(btrigidbodyconstructioninfo);
        if (mass > 0.0f) {
            btrigidbody.setAngularFactor(1.0f);
            btrigidbody.setDamping(0.5f, 0.5f);
        }
        btDiscreteDynamicsWorld btdiscretedynamicsworld = this.dynamicsWorld;
        if (btdiscretedynamicsworld != null && !this.disposed) {
            btdiscretedynamicsworld.addRigidBody(btrigidbody);
        } else {
            Log.w("ThreeDManager", "Aborted adding rigid body: dynamicsWorld is null or disposed.");
        }
        this.physicsBodies.put(objectId, btrigidbody);
        btrigidbody.userData = array;
        this.physicsResources.put(objectId, array);
        btrigidbodyconstructioninfo.dispose();
        Log.d("PhysicsDebug", "  - SUCCESS: Body created. Final transform from physics world:\n" + btrigidbody.getWorldTransform());
        Log.d("PhysicsDebug", "--------------------------------------------------------");
    }

    public void setPhysicsStateFromComponent(String objectId, PhysicsComponent component, Matrix4 worldTransform) {
        ModelInstance modelInstance = this.sceneObjects.get(objectId);
        if (modelInstance == null) {
            return;
        }
        modelInstance.transform.set(worldTransform);
        removePhysicsBody(objectId);
        float f = component.state == PhysicsState.DYNAMIC ? component.mass : 0.0f;
        if (component.state == PhysicsState.NONE) {
            return;
        }
        if (component.state == PhysicsState.MESH_STATIC) {
            if (this.gltfObjectIds.contains(objectId)) {
                createGltfMeshPhysicsBody(objectId, modelInstance);
                return;
            } else {
                createMeshPhysicsBody(objectId, modelInstance);
                return;
            }
        }
        if (component.colliders.isEmpty()) {
            Gdx.f82app.error("3DManager", "setPhysicsStateFromComponent called with no colliders for object: " + objectId);
        } else {
            createCompoundPhysicsBody(objectId, modelInstance, component, f);
        }
    }

    public void renderWireframeShape(ModelBatch batch, ColliderShapeData shapeData, Matrix4 parentTransform, Color color) {
        if (batch == null) {
            return;
        }
        Matrix4 matrix4 = new Matrix4(parentTransform);
        int i = AnonymousClass6.$SwitchMap$org$catrobat$catroid$raptor$ColliderShapeData$ShapeType[shapeData.type.ordinal()];
        if (i == 1) {
            ModelInstance modelInstance = new ModelInstance(this.wireframeSphereModel);
            modelInstance.transform.scl(shapeData.radius * 2.0f);
            modelInstance.transform.mulLeft(matrix4.translate(shapeData.centerOffset));
            ((ColorAttribute) modelInstance.materials.get(0).get(ColorAttribute.Diffuse)).color.set(color);
            batch.render(modelInstance);
            return;
        }
        if (i != 2) {
            if (i != 3) {
                return;
            }
            ModelInstance modelInstance2 = new ModelInstance(this.wireframeBoxModel);
            modelInstance2.transform.scl(shapeData.size);
            modelInstance2.transform.mulLeft(matrix4.translate(shapeData.centerOffset));
            ((ColorAttribute) modelInstance2.materials.get(0).get(ColorAttribute.Diffuse)).color.set(color);
            batch.render(modelInstance2);
            return;
        }
        float fMax = Math.max(0.0f, shapeData.size.y - (shapeData.radius * 2.0f));
        Vector3 vector3 = shapeData.centerOffset;
        ModelInstance modelInstance3 = new ModelInstance(this.wireframeCylinderModel);
        modelInstance3.transform.scl(shapeData.radius * 2.0f, fMax, shapeData.radius * 2.0f);
        modelInstance3.transform.mulLeft(matrix4.cpy().translate(vector3));
        ((ColorAttribute) modelInstance3.materials.get(0).get(ColorAttribute.Diffuse)).color.set(color);
        batch.render(modelInstance3);
        ModelInstance modelInstance4 = new ModelInstance(this.wireframeSphereModel);
        this.tmpVec.set(vector3).add(0.0f, fMax / 2.0f, 0.0f);
        modelInstance4.transform.scl(shapeData.radius * 2.0f);
        modelInstance4.transform.mulLeft(matrix4.cpy().translate(this.tmpVec));
        ((ColorAttribute) modelInstance4.materials.get(0).get(ColorAttribute.Diffuse)).color.set(color);
        batch.render(modelInstance4);
        ModelInstance modelInstance5 = new ModelInstance(this.wireframeSphereModel);
        this.tmpVec.set(vector3).add(0.0f, (-fMax) / 2.0f, 0.0f);
        modelInstance5.transform.scl(shapeData.radius * 2.0f);
        modelInstance5.transform.mulLeft(matrix4.cpy().translate(this.tmpVec));
        ((ColorAttribute) modelInstance5.materials.get(0).get(ColorAttribute.Diffuse)).color.set(color);
        batch.render(modelInstance5);
    }

    public void setBackgroundLightIntensity(float intensity) {
        net.mgsx.gltf.scene3d.scene.SceneManager sceneManager = this.sceneManager;
        if (sceneManager != null) {
            ColorAttribute colorAttribute = (ColorAttribute) sceneManager.environment.get(ColorAttribute.AmbientLight);
            if (colorAttribute == null) {
                this.sceneManager.environment.set(new ColorAttribute(ColorAttribute.AmbientLight, intensity, intensity, intensity, 1.0f));
            } else {
                colorAttribute.color.set(intensity, intensity, intensity, 1.0f);
            }
            try {
                Method declaredMethod = Attribute.class.getDeclaredMethod("register", String.class);
                declaredMethod.setAccessible(true);
                this.sceneManager.environment.set(new PBRFloatAttribute(((Long) declaredMethod.invoke(null, "envIntensity")).longValue(), intensity));
            } catch (Exception e) {
                Gdx.f82app.error("ThreeDManager", "Failed to register envIntensity via reflection: " + e.getMessage());
            }
        }
    }

    public void setPointLight(String lightId, float x, float y, float z, float r, float g, float b, float intensity, float range) {
        net.mgsx.gltf.scene3d.scene.SceneManager sceneManager;
        SpotLightEx spotLightExRemove = this.spotLights.remove(lightId);
        if (spotLightExRemove != null && (sceneManager = this.sceneManager) != null) {
            sceneManager.environment.remove((SpotLight) spotLightExRemove);
        }
        PointLightEx pointLightEx = this.pointLights.get(lightId);
        if (pointLightEx == null) {
            pointLightEx = new PointLightEx();
            this.pointLights.put(lightId, pointLightEx);
        }
        pointLightEx.position.set(x, y, z);
        pointLightEx.color.set(r, g, b, 1.0f);
        pointLightEx.intensity = intensity;
        pointLightEx.range = range > 0.0f ? Float.valueOf(range) : null;
    }

    public void setSpotLight(String lightId, float x, float y, float z, float dirX, float dirY, float dirZ, float r, float g, float b, float intensity, float cutoffAngle, float softness, float range) {
        net.mgsx.gltf.scene3d.scene.SceneManager sceneManager;
        PointLightEx pointLightExRemove = this.pointLights.remove(lightId);
        if (pointLightExRemove != null && (sceneManager = this.sceneManager) != null) {
            sceneManager.environment.remove((PointLight) pointLightExRemove);
        }
        SpotLightEx spotLightEx = this.spotLights.get(lightId);
        if (spotLightEx == null) {
            spotLightEx = new SpotLightEx();
            this.spotLights.put(lightId, spotLightEx);
            net.mgsx.gltf.scene3d.scene.SceneManager sceneManager2 = this.sceneManager;
            if (sceneManager2 != null) {
                sceneManager2.environment.add((SpotLight) spotLightEx);
            }
        }
        spotLightEx.position.set(x, y, z);
        spotLightEx.direction.set(dirX, dirY, dirZ).nor();
        spotLightEx.color.set(r, g, b, 1.0f);
        spotLightEx.intensity = intensity;
        spotLightEx.setConeDeg(cutoffAngle, cutoffAngle * (1.0f - Math.max(0.0f, Math.min(1.0f, softness))));
        spotLightEx.range = range > 0.0f ? Float.valueOf(range) : null;
    }

    @Deprecated
    public void setSpotLightOld(String lightId, float x, float y, float z, float dirX, float dirY, float dirZ, float r, float g, float b, float intensity, float cutoffAngle, float exponent, float range) {
        net.mgsx.gltf.scene3d.scene.SceneManager sceneManager;
        PointLightEx pointLightExRemove = this.pointLights.remove(lightId);
        if (pointLightExRemove != null && (sceneManager = this.sceneManager) != null) {
            sceneManager.environment.remove((PointLight) pointLightExRemove);
        }
        SpotLightEx spotLightEx = this.spotLights.get(lightId);
        if (spotLightEx == null) {
            spotLightEx = new SpotLightEx();
            this.spotLights.put(lightId, spotLightEx);
            this.sceneManager.environment.add((SpotLight) spotLightEx);
        }
        spotLightEx.position.set(x, y, z);
        spotLightEx.direction.set(dirX, dirY, dirZ).nor();
        spotLightEx.color.set(r, g, b, 1.0f);
        spotLightEx.intensity = intensity;
        spotLightEx.cutoffAngle = map(cutoffAngle, 0.0f, 360.0f, -1000.0f, 1000.0f);
        spotLightEx.exponent = exponent * 1000.0f;
        spotLightEx.range = range > 0.0f ? Float.valueOf(range) : null;
    }

    public boolean removePBRLight(String lightId) {
        boolean z;
        PointLightEx pointLightExRemove = this.pointLights.remove(lightId);
        if (pointLightExRemove != null) {
            net.mgsx.gltf.scene3d.scene.SceneManager sceneManager = this.sceneManager;
            if (sceneManager != null) {
                sceneManager.environment.remove((PointLight) pointLightExRemove);
            }
            z = true;
        } else {
            z = false;
        }
        SpotLightEx spotLightExRemove = this.spotLights.remove(lightId);
        if (spotLightExRemove == null) {
            return z;
        }
        net.mgsx.gltf.scene3d.scene.SceneManager sceneManager2 = this.sceneManager;
        if (sceneManager2 == null) {
            return true;
        }
        sceneManager2.environment.remove((SpotLight) spotLightExRemove);
        return true;
    }

    public void setObjectAnisotropicFilter(String objectId, float level) {
        Texture texture;
        ModelInstance modelInstance = this.sceneObjects.get(objectId);
        if (modelInstance == null) {
            return;
        }
        Array.ArrayIterator<Material> it = modelInstance.materials.iterator();
        while (it.hasNext()) {
            for (Attribute attribute : it.next()) {
                if ((attribute instanceof TextureAttribute) && (texture = (Texture) ((TextureAttribute) attribute).textureDescription.texture) != null) {
                    texture.setAnisotropicFilter(level);
                }
            }
        }
    }

    public void setFog(float r, float g, float b, float density) {
        ColorAttribute colorAttribute = new ColorAttribute(ColorAttribute.Fog, r, g, b, 1.0f);
        if (density > 0.0f) {
            net.mgsx.gltf.scene3d.scene.SceneManager sceneManager = this.sceneManager;
            if (sceneManager != null) {
                sceneManager.environment.set(colorAttribute);
            }
            this.environment.set(colorAttribute);
            this.camera.far = 1.0f / density;
        } else {
            this.camera.far = 1000.0f;
            net.mgsx.gltf.scene3d.scene.SceneManager sceneManager2 = this.sceneManager;
            if (sceneManager2 != null) {
                sceneManager2.environment.remove(ColorAttribute.Fog);
            }
            this.environment.remove(ColorAttribute.Fog);
        }
        this.camera.update();
    }

    public void setAngularFactor(String objectId, float x, float y, float z) {
        btRigidBody btrigidbody = this.physicsBodies.get(objectId);
        if (btrigidbody != null) {
            btrigidbody.setAngularFactor(new Vector3(x, y, z));
            btrigidbody.activate();
        }
    }

    public void setSkyColor(float r, float g, float b) {
        this.skyColor.set(r, g, b, 1.0f);
    }

    public Vector3 getCameraPosition() {
        return this.camera.position;
    }

    public void renameObject(String oldId, String newId) {
        if (this.sceneObjects.containsKey(oldId)) {
            Map<String, ModelInstance> map = this.sceneObjects;
            map.put(newId, map.remove(oldId));
        }
        if (this.physicsBodies.containsKey(oldId)) {
            Map<String, btRigidBody> map2 = this.physicsBodies;
            map2.put(newId, map2.remove(oldId));
        }
        if (this.physicsResources.containsKey(oldId)) {
            Map<String, Array<Disposable>> map3 = this.physicsResources;
            map3.put(newId, map3.remove(oldId));
        }
        if (this.animationControllers.containsKey(oldId)) {
            Map<String, AnimationController> map4 = this.animationControllers;
            map4.put(newId, map4.remove(oldId));
        }
        if (this.gltfObjectIds.contains(oldId)) {
            this.gltfObjectIds.remove(oldId);
            this.gltfObjectIds.add(newId);
        }
        if (this.editorProxies.containsKey(oldId)) {
            Map<String, ModelInstance> map5 = this.editorProxies;
            map5.put(newId, map5.remove(oldId));
        }
        if (this.pointLights.containsKey(oldId)) {
            Map<String, PointLightEx> map6 = this.pointLights;
            map6.put(newId, map6.remove(oldId));
        }
        if (this.spotLights.containsKey(oldId)) {
            Map<String, SpotLightEx> map7 = this.spotLights;
            map7.put(newId, map7.remove(oldId));
        }
    }

    public Vector3 getCameraDirection() {
        return this.camera.direction;
    }

    public Matrix4 getWorldTransform(String objectId) {
        ModelInstance modelInstance = this.sceneObjects.get(objectId);
        if (modelInstance != null) {
            return modelInstance.transform;
        }
        return null;
    }

    public void setPhysicsState(String objectId, PhysicsState state, PhysicsShape shape, float mass, Matrix4 worldTransform) {
        ModelInstance modelInstance = this.sceneObjects.get(objectId);
        if (modelInstance == null) {
            return;
        }
        modelInstance.transform.set(worldTransform);
        setPhysicsState(objectId, state, shape, mass);
    }

    public void setShadowSettings(float size, int resolution, boolean enableCSM, float csmFactor) {
        this.currentShadowSize = Math.max(1.0f, size);
        boolean z = this.useCSM != enableCSM;
        this.useCSM = enableCSM;
        this.csmSplitFactor = Math.max(1.0f, csmFactor);
        if (this.currentShadowResolution != resolution || z) {
            this.currentShadowResolution = resolution;
            Gdx.f82app.postRunnable(new Runnable() { // from class: org.catrobat.catroid.raptor.ThreeDManager$$ExternalSyntheticLambda20
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.recreateShadowLight();
                }
            });
        }
    }

    public void setShadowSettings(float size, int resolution) {
        setShadowSettings(size, resolution, this.useCSM, 4.0f);
    }

    public float getShadowSize() {
        return this.currentShadowSize;
    }

    public float getShadowResolution() {
        return this.currentShadowResolution;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void recreateShadowLight() {
        net.mgsx.gltf.scene3d.scene.SceneManager sceneManager = this.sceneManager;
        if (sceneManager == null) {
            return;
        }
        if (this.pbrLight != null) {
            sceneManager.environment.remove((DirectionalLight) this.pbrLight);
            Object obj = this.pbrLight;
            if (obj instanceof Disposable) {
                ((Disposable) obj).dispose();
            }
        }
        CascadeShadowMap cascadeShadowMap = this.csm;
        if (cascadeShadowMap != null) {
            cascadeShadowMap.dispose();
            this.csm = null;
            this.sceneManager.setCascadeShadowMap(null);
        }
        int i = this.currentShadowResolution;
        DirectionalShadowLight directionalShadowLight = new DirectionalShadowLight(i, i);
        directionalShadowLight.direction.set(1.0f, -1.5f, 1.0f).nor();
        directionalShadowLight.color.set(Color.WHITE);
        directionalShadowLight.intensity = 5.0f;
        this.sceneManager.environment.add((DirectionalLight) directionalShadowLight);
        this.pbrLight = directionalShadowLight;
        this.sceneManager.environment.set(new PBRFloatAttribute(PBRFloatAttribute.ShadowBias, 5.0E-4f));
        if (this.useCSM) {
            CascadeShadowMap cascadeShadowMap2 = new CascadeShadowMap(3);
            this.csm = cascadeShadowMap2;
            this.sceneManager.setCascadeShadowMap(cascadeShadowMap2);
        }
        updateProceduralIBL();
    }

    private void updateShadowLight() {
        CascadeShadowMap cascadeShadowMap;
        DirectionalLightEx directionalLightEx = this.pbrLight;
        if (directionalLightEx instanceof DirectionalShadowLight) {
            DirectionalShadowLight directionalShadowLight = (DirectionalShadowLight) directionalLightEx;
            if (this.useCSM && (cascadeShadowMap = this.csm) != null) {
                cascadeShadowMap.setCascades(this.camera, directionalShadowLight, this.currentShadowSize, this.csmSplitFactor);
                return;
            }
            this.slCenter.set(this.camera.position);
            this.slForward.set(this.camera.direction).scl(this.currentShadowSize * 0.4f);
            this.slForward.y = 0.0f;
            this.slCenter.add(this.slForward);
            this.slCenter.y = 0.0f;
            directionalShadowLight.setCenter(this.slCenter);
            Camera camera = directionalShadowLight.getCamera();
            camera.viewportWidth = this.currentShadowSize;
            camera.viewportHeight = this.currentShadowSize;
            float f = this.currentShadowSize * 2.0f;
            camera.near = (-f) / 2.0f;
            camera.far = f / 2.0f;
            camera.update();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/ThreeDManager$BoneAttachment.class
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
        this.activeAttachments.removeIf(new Predicate() { // from class: org.catrobat.catroid.raptor.ThreeDManager$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((ThreeDManager.BoneAttachment) obj).childId.equals(childId);
            }
        });
    }

    public void update(float delta) {
        if (this.cameraTargetId != null) {
            updateThirdPersonCamera();
        } else if (this.cameraTrackMode != 0 && this.cameraTrackTargetId != null) {
            updateCameraTracking();
        }
        updateCameraAttachments();
        updateActivePointLights();
        updateShadowLight();
        Iterator<AnimationController> it = this.animationControllers.values().iterator();
        while (it.hasNext()) {
            it.next().update(delta);
        }
        if (this.realisticMode) {
            this.sceneManager.update(delta);
        }
        float f = this.time + delta;
        this.time = f;
        if (this.customShaderProvider != null) {
            this.customUniforms.put("u_time", Float.valueOf(f));
            this.customUniforms.put("u_resolution", new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
            this.customUniforms.put("u_cameraPosition", this.camera.position);
            this.customUniforms.put("u_cameraDirection", this.camera.direction);
            this.customUniforms.put("u_viewMatrix", this.camera.view);
            this.customUniforms.put("u_projectionMatrix", this.camera.projection);
        }
        if (!this.editorMode) {
            updateContinuousSensors();
            updateAIProcessing(delta);
            this.dynamicsWorld.stepSimulation(delta, 5, 0.016666668f);
            for (Map.Entry<String, btRigidBody> entry : this.physicsBodies.entrySet()) {
                String key = entry.getKey();
                btRigidBody value = entry.getValue();
                if (((StageListener) Objects.requireNonNull(StageActivity.getActiveStageListener())).sceneManager == null || ((StageListener) Objects.requireNonNull(StageActivity.getActiveStageListener())).sceneManager.findGameObject(key) == null) {
                    ModelInstance modelInstance = this.sceneObjects.get(key);
                    if (modelInstance != null && !value.isStaticObject() && value.isActive()) {
                        Matrix4 worldTransform = value.getWorldTransform();
                        worldTransform.getTranslation(this.tmpPos);
                        worldTransform.getRotation(this.tmpRot, true);
                        Quaternion quaternion = this.modelRotationOffsets.get(key);
                        if (quaternion != null) {
                            this.tmpRot.mul(quaternion);
                        }
                        modelInstance.transform.getScale(this.tmpScale);
                        modelInstance.transform.set(this.tmpPos, this.tmpRot, this.tmpScale);
                    }
                }
            }
        }
        Matrix4 matrix4 = new Matrix4();
        for (BoneOverride boneOverride : this.boneOverrides) {
            ModelInstance modelInstance2 = this.sceneObjects.get(boneOverride.modelId);
            ModelInstance modelInstance3 = this.sceneObjects.get(boneOverride.targetId);
            if (modelInstance2 != null && modelInstance3 != null) {
                Node node = modelInstance2.getNode(boneOverride.boneName, true);
                if (node != null) {
                    matrix4.set(modelInstance2.transform).inv().mul(modelInstance3.transform);
                    node.globalTransform.set(matrix4);
                    modelInstance2.calculateTransforms();
                } else {
                    Log.e("ThreeDManager", "ОШИБКА IK: Кость '" + boneOverride.boneName + "' не найдена!");
                }
            }
        }
        if (!this.editorMode) {
            update3DAudio();
        }
        applyCameraEffects(delta);
    }

    private void update3DAudio() {
        float f;
        float fDot;
        if (this.active3DSounds.isEmpty()) {
            return;
        }
        Vector3 vector3 = this.camera.position;
        this.cameraRight.set(this.camera.direction).crs(this.camera.up).nor();
        Iterator<PlayableAudio> it = this.active3DSounds.iterator();
        while (it.hasNext()) {
            PlayableAudio next = it.next();
            if (!next.isPlaying()) {
                next.dispose();
                it.remove();
            } else {
                String attachedObjectId = next.getAttachedObjectId();
                if (attachedObjectId != null) {
                    ModelInstance modelInstance = this.sceneObjects.get(attachedObjectId);
                    if (modelInstance != null) {
                        modelInstance.transform.getTranslation(this.tmpPos);
                        next.setPosition(this.tmpPos);
                    } else {
                        next.stop();
                        next.dispose();
                        it.remove();
                    }
                }
                float fDst = vector3.dst(next.getPosition());
                float maxDistance = next.getMaxDistance();
                if (fDst > maxDistance) {
                    f = 0.0f;
                    fDot = 0.0f;
                } else {
                    f = this.globalSoundVolume * (1.0f - (fDst / maxDistance));
                    this.soundToListener.set(next.getPosition()).sub(vector3).nor();
                    fDot = this.soundToListener.dot(this.cameraRight);
                }
                next.update3D(f, fDot);
            }
        }
    }

    public void set3DSoundMaxDistance(String instanceName, float maxDistance) {
        for (PlayableAudio playableAudio : this.active3DSounds) {
            if (instanceName.equals(playableAudio.getInstanceName())) {
                playableAudio.setMaxDistance(maxDistance);
                return;
            }
        }
    }

    public void updateSoundPosition(String instanceName, float x, float y, float z) {
        if (instanceName == null || instanceName.isEmpty()) {
            return;
        }
        for (PlayableAudio playableAudio : this.active3DSounds) {
            if (instanceName.equals(playableAudio.getInstanceName())) {
                playableAudio.setAttachedObjectId(null);
                playableAudio.setPosition(new Vector3(x, y, z));
                return;
            }
        }
    }

    public boolean createSpringConstraint(String constraintId, String objectIdA, String objectIdB, Matrix4 frameInA, Matrix4 frameInB) {
        if (this.physicsConstraints.containsKey(constraintId)) {
            return false;
        }
        btRigidBody btrigidbody = this.physicsBodies.get(objectIdA);
        btRigidBody btrigidbody2 = this.physicsBodies.get(objectIdB);
        if (btrigidbody2 == null) {
            Gdx.f82app.error("3DManager", "Cannot create spring constraint: bodyB not found.");
            return false;
        }
        if (btrigidbody == null) {
            Gdx.f82app.error("3DManager", "Cannot create spring constraint: bodyA not found.");
            return false;
        }
        btGeneric6DofSpringConstraint btgeneric6dofspringconstraint = new btGeneric6DofSpringConstraint(btrigidbody, btrigidbody2, frameInA, frameInB, true);
        this.dynamicsWorld.addConstraint(btgeneric6dofspringconstraint, true);
        this.physicsConstraints.put(constraintId, btgeneric6dofspringconstraint);
        return true;
    }

    public boolean createHingeConstraint(String constraintId, String objectIdA, String objectIdB, Vector3 pivotInA, Vector3 axisInA, Vector3 pivotInB, Vector3 axisInB) {
        if (this.physicsConstraints.containsKey(constraintId)) {
            return false;
        }
        btRigidBody btrigidbody = this.physicsBodies.get(objectIdA);
        btRigidBody btrigidbody2 = this.physicsBodies.get(objectIdB);
        if (btrigidbody == null || btrigidbody2 == null) {
            Gdx.f82app.error("3DManager", "Cannot create hinge: one or both bodies not found.");
            return false;
        }
        btHingeConstraint bthingeconstraint = new btHingeConstraint(btrigidbody, btrigidbody2, pivotInA, pivotInB, axisInA, axisInB, true);
        this.dynamicsWorld.addConstraint(bthingeconstraint, true);
        this.physicsConstraints.put(constraintId, bthingeconstraint);
        return true;
    }

    public void setHingeMotorTarget(String constraintId, float targetAngleDegrees, float maxMotorImpulse) {
        btTypedConstraint bttypedconstraint = this.physicsConstraints.get(constraintId);
        if (bttypedconstraint instanceof btHingeConstraint) {
            btHingeConstraint bthingeconstraint = (btHingeConstraint) bttypedconstraint;
            bthingeconstraint.enableAngularMotor(true, 0.0f, maxMotorImpulse);
            bthingeconstraint.setMotorTarget(targetAngleDegrees * 0.017453292f, 0.1f);
        }
    }

    public void setAngularSpringProperties(String constraintId, int axisIndex, float stiffness, float damping) {
        btTypedConstraint bttypedconstraint = this.physicsConstraints.get(constraintId);
        if (bttypedconstraint instanceof btGeneric6DofSpringConstraint) {
            btGeneric6DofSpringConstraint btgeneric6dofspringconstraint = (btGeneric6DofSpringConstraint) bttypedconstraint;
            int i = axisIndex + 3;
            btgeneric6dofspringconstraint.enableSpring(i, true);
            btgeneric6dofspringconstraint.setStiffness(i, stiffness);
            btgeneric6dofspringconstraint.setDamping(i, damping);
        }
    }

    public btRigidBody getPhysicsBody(String objectId) {
        return this.physicsBodies.get(objectId);
    }

    private void updateThirdPersonCamera() {
        ModelInstance modelInstance = this.sceneObjects.get(this.cameraTargetId);
        if (modelInstance == null) {
            setFreeCamera();
            return;
        }
        modelInstance.transform.getTranslation(this.tpTargetPos);
        this.tpLookAt.set(this.tpTargetPos).add(this.cameraOffset);
        this.tpRot.set(Vector3.Y, this.cameraYaw);
        this.tpPitchRot.set(Vector3.X, -this.cameraPitch);
        this.tpRot.mul(this.tpPitchRot);
        this.tpPosOffset.set(0.0f, 0.0f, this.cameraDistance).mul(this.tpRot);
        this.camera.position.set(this.tpLookAt).add(this.tpPosOffset);
        this.camera.lookAt(this.tpLookAt);
        this.camera.up.set(Vector3.Y);
        this.camera.update();
    }

    public void addCameraRotation(float yawDelta, float pitchDelta) {
        if (this.cameraTargetId != null) {
            this.cameraYaw += yawDelta;
            float f = this.cameraPitch + pitchDelta;
            this.cameraPitch = f;
            if (f > 89.0f) {
                this.cameraPitch = 89.0f;
            }
            if (this.cameraPitch < -89.0f) {
                this.cameraPitch = -89.0f;
                return;
            }
            return;
        }
        this.camera.view.getRotation(this.tmpRot);
        this.camera.rotate(Vector3.Y, yawDelta);
        this.tmpVec.set(this.camera.direction).crs(this.camera.up).nor();
        this.camera.rotate(this.tmpVec, pitchDelta);
        this.camera.update();
    }

    public void setCameraRotation(float yaw, float pitch) {
        if (this.cameraTargetId == null) {
            return;
        }
        this.cameraYaw = yaw;
        this.cameraPitch = pitch;
        if (pitch > 89.0f) {
            this.cameraPitch = 89.0f;
        }
        if (this.cameraPitch < -89.0f) {
            this.cameraPitch = -89.0f;
        }
    }

    public void playSoundAt(String instanceName, String soundName, float x, float y, float z, float volume, float pitch, boolean loop) {
        playSoundInternal(instanceName, soundName, volume, pitch, loop, new Vector3(x, y, z), null);
    }

    public void playSoundAttached(String instanceName, String soundName, String objectId, float volume, float pitch, boolean loop) {
        ModelInstance modelInstance = this.sceneObjects.get(objectId);
        if (modelInstance == null) {
            return;
        }
        playSoundInternal(instanceName, soundName, volume, pitch, loop, modelInstance.transform.getTranslation(new Vector3()), objectId);
    }

    public boolean prepareAudio(String soundName, String fileName, boolean asMusic) {
        if (soundName != null && !soundName.isEmpty() && !this.loadedAudioAssets.containsKey(soundName)) {
            try {
                File file = ProjectManager.getInstance().getCurrentProject().getFile(fileName);
                if (file != null && file.exists()) {
                    this.loadedAudioAssets.put(soundName, new AudioAsset(Gdx.files.absolute(file.getAbsolutePath()), asMusic));
                    return true;
                }
            } catch (Exception e) {
                Gdx.f82app.error("ThreeDManager_Audio", "Failed to load audio: " + fileName, e);
            }
        }
        return false;
    }

    private void playSoundInternal(String instanceName, String soundName, float volume, float pitch, boolean loop, Vector3 position, String attachedToId) {
        PlayableAudio soundWrapper;
        if (instanceName == null || instanceName.isEmpty()) {
            return;
        }
        stopSound(instanceName);
        AudioAsset audioAsset = this.loadedAudioAssets.get(soundName);
        if (audioAsset == null) {
            return;
        }
        if (audioAsset.music != null) {
            soundWrapper = new MusicWrapper(Gdx.audio.newMusic(Gdx.files.absolute(audioAsset.filePath)), volume, pitch, loop);
        } else {
            soundWrapper = new SoundWrapper(audioAsset.sound, volume, pitch, loop);
        }
        soundWrapper.setInstanceName(instanceName);
        soundWrapper.setPosition(position);
        soundWrapper.setAttachedObjectId(attachedToId);
        soundWrapper.play();
        this.active3DSounds.add(soundWrapper);
    }

    public void stopSound(String instanceName) {
        Iterator<PlayableAudio> it = this.active3DSounds.iterator();
        while (it.hasNext()) {
            PlayableAudio next = it.next();
            if (next.getInstanceName().equals(instanceName)) {
                next.stop();
                next.dispose();
                it.remove();
                return;
            }
        }
    }

    public void setGlobalSoundVolume(float volume) {
        this.globalSoundVolume = Math.max(0.0f, Math.min(1.0f, volume));
    }

    public Array<String> getAnimationNames(String objectId) {
        ModelInstance modelInstance = this.sceneObjects.get(objectId);
        Array<String> array = new Array<>();
        if (modelInstance != null && modelInstance.animations.size > 0) {
            Array.ArrayIterator<Animation> it = modelInstance.animations.iterator();
            while (it.hasNext()) {
                array.add(it.next().f102id);
            }
        }
        return array;
    }

    /* JADX INFO: renamed from: setSkybox, reason: merged with bridge method [inline-methods] */
    public void m5477lambda$setSkybox$15$orgcatrobatcatroidraptorThreeDManager(final String panoramicTexturePath) {
        if (!this.realisticMode) {
            Gdx.f82app.error("Skybox", "Skybox can only be enabled in realistic rendering mode.");
            return;
        }
        if (this.panoramicConverter == null) {
            Gdx.f82app.error("Skybox", "PanoramicConverter is not initialized yet. Deferring task.");
            Gdx.f82app.postRunnable(new Runnable() { // from class: org.catrobat.catroid.raptor.ThreeDManager$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m5477lambda$setSkybox$15$orgcatrobatcatroidraptorThreeDManager(panoramicTexturePath);
                }
            });
            return;
        }
        if (panoramicTexturePath == null || panoramicTexturePath.isEmpty()) {
            if (this.skybox != null) {
                this.sceneManager.setSkyBox(null);
                this.skybox.dispose();
                this.skybox = null;
            }
            Cubemap cubemap = this.skyboxCubemap;
            if (cubemap != null) {
                cubemap.dispose();
                this.skyboxCubemap = null;
            }
            updateProceduralIBL();
            Gdx.f82app.log("Skybox", "Skybox cleared. Reverted to procedural IBL.");
            return;
        }
        try {
            FileHandle fileHandleAbsolute = Gdx.files.absolute(panoramicTexturePath);
            if (!fileHandleAbsolute.exists()) {
                Gdx.f82app.error("Skybox", "Skybox texture not found: " + panoramicTexturePath);
                return;
            }
            Texture texture = new Texture(fileHandleAbsolute);
            Cubemap cubemap2 = this.skyboxCubemap;
            if (cubemap2 != null) {
                cubemap2.dispose();
            }
            SceneSkybox sceneSkybox = this.skybox;
            if (sceneSkybox != null) {
                sceneSkybox.dispose();
            }
            this.skyboxCubemap = this.panoramicConverter.convert(texture, 1024);
            SceneSkybox sceneSkybox2 = new SceneSkybox(this.skyboxCubemap);
            this.skybox = sceneSkybox2;
            this.sceneManager.setSkyBox(sceneSkybox2);
            m5480x9aff91e6(this.skyboxCubemap);
            texture.dispose();
            Gdx.f82app.log("Skybox", "Skybox and its Image-Based Lighting set successfully.");
        } catch (Exception e) {
            Gdx.f82app.error("Skybox", "Failed to set skybox", e);
        }
    }

    private void updateProceduralIBL() {
        Cubemap cubemap = this.diffuseCubemap;
        if (cubemap != null) {
            cubemap.dispose();
        }
        Cubemap cubemap2 = this.specularCubemap;
        if (cubemap2 != null) {
            cubemap2.dispose();
        }
        IBLBuilder iBLBuilderCreateOutdoor = IBLBuilder.createOutdoor(this.pbrLight);
        this.diffuseCubemap = iBLBuilderCreateOutdoor.buildIrradianceMap(256);
        this.specularCubemap = iBLBuilderCreateOutdoor.buildRadianceMap(10);
        iBLBuilderCreateOutdoor.dispose();
        this.sceneManager.environment.set(new PBRCubemapAttribute(PBRCubemapAttribute.DiffuseEnv, this.diffuseCubemap));
        this.sceneManager.environment.set(new PBRCubemapAttribute(PBRCubemapAttribute.SpecularEnv, this.specularCubemap));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: updateIBLFromCubemap, reason: merged with bridge method [inline-methods] */
    public void m5480x9aff91e6(final Cubemap sourceCubemap) {
        if (this.iblBuilderCompat == null) {
            Gdx.f82app.error("IBL", "IBLBuilderCompat not initialized yet. Deferring IBL update.");
            Gdx.f82app.postRunnable(new Runnable() { // from class: org.catrobat.catroid.raptor.ThreeDManager$$ExternalSyntheticLambda14
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m5480x9aff91e6(sourceCubemap);
                }
            });
            return;
        }
        Cubemap cubemap = this.diffuseCubemap;
        if (cubemap != null) {
            cubemap.dispose();
        }
        Cubemap cubemap2 = this.specularCubemap;
        if (cubemap2 != null) {
            cubemap2.dispose();
        }
        this.diffuseCubemap = this.iblBuilderCompat.buildIrradianceMap(sourceCubemap, 32);
        this.specularCubemap = this.iblBuilderCompat.buildRadianceMap(sourceCubemap, 128, 5);
        this.sceneManager.environment.set(new PBRCubemapAttribute(PBRCubemapAttribute.DiffuseEnv, this.diffuseCubemap));
        this.sceneManager.environment.set(new PBRCubemapAttribute(PBRCubemapAttribute.SpecularEnv, this.specularCubemap));
        Gdx.f82app.log("IBL", "Image-Based Lighting recalculated from skybox texture via Compat builder.");
    }

    public void enableRealisticRendering(boolean enabled) {
        this.realisticMode = enabled;
    }

    public void setVelocity(String objectId, float vx, float vy, float vz) {
        btRigidBody btrigidbody = this.physicsBodies.get(objectId);
        if (btrigidbody == null || btrigidbody.getInvMass() <= 0.0f) {
            return;
        }
        btrigidbody.activate();
        btrigidbody.setLinearVelocity(new Vector3(vx, vy, vz));
    }

    public void setGravity(float x, float y, float z) {
        this.dynamicsWorld.setGravity(new Vector3(x, y, z));
    }

    public void renderShadowsOnly() {
        this.camera.update();
        if (this.realisticMode) {
            Gdx.gl.glEnable(GL20.GL_CULL_FACE);
            Gdx.gl.glCullFace(1028);
            this.sceneManager.renderShadows();
            Gdx.gl.glCullFace(GL20.GL_BACK);
        }
    }

    public void renderColorsOnly(int vpWidth, int vpHeight) {
        this.camera.update();
        Gdx.gl.glViewport(0, 0, vpWidth, vpHeight);
        if (this.realisticMode) {
            if (this.skyColor.a != 0.0f) {
                Gdx.gl.glClearColor(this.skyColor.r, this.skyColor.g, this.skyColor.b, this.skyColor.a);
                Gdx.gl.glClear(16640);
            }
            this.sceneManager.renderMirror();
            this.sceneManager.renderTransmission();
            this.sceneManager.renderColors();
        } else {
            if (this.skyColor.a != 0.0f) {
                Gdx.gl.glClearColor(this.skyColor.r, this.skyColor.g, this.skyColor.b, this.skyColor.a);
                Gdx.gl.glClear(16640);
            }
            this.modelBatch.begin(this.camera);
            for (Map.Entry<String, ModelInstance> entry : this.sceneObjects.entrySet()) {
                if (!this.inactiveRenderObjects.contains(entry.getKey())) {
                    this.modelBatch.render(entry.getValue(), this.environment);
                }
            }
            this.modelBatch.end();
        }
        if (!this.particleSystemInitialized || this.activeParticleEffects.isEmpty()) {
            return;
        }
        this.particleModelBatch.begin(this.camera);
        this.particleModelBatch.render(this.particleSystem);
        this.particleModelBatch.end();
        if (!this.effectsNormal.isEmpty()) {
            Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
            Gdx.gl.glDepthMask(true);
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            this.batchNormal.setCamera(this.camera);
            this.batchNormal.begin();
            Iterator<ParticleEffect> it = this.effectsNormal.iterator();
            while (it.hasNext()) {
                it.next().draw();
            }
            this.batchNormal.end();
        }
        if (this.effectsAdditive.isEmpty()) {
            return;
        }
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, 1);
        Gdx.gl.glDepthMask(false);
        this.batchAdditive.setCamera(this.camera);
        this.batchAdditive.begin();
        Iterator<ParticleEffect> it2 = this.effectsAdditive.iterator();
        while (it2.hasNext()) {
            it2.next().draw();
        }
        this.batchAdditive.end();
        Gdx.gl.glDepthMask(true);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    }

    private void drawFinalTextureToScreen(Texture texture) {
        float f;
        float f2;
        int width = this.lastScreenWidth;
        if (width <= 0) {
            width = Gdx.graphics.getWidth();
        }
        int height = this.lastScreenHeight;
        if (height <= 0) {
            height = Gdx.graphics.getHeight();
        }
        Gdx.gl.glViewport(0, 0, width, height);
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        Gdx.gl.glClear(16384);
        float f3 = width;
        float f4 = height;
        this.blitCamera.setToOrtho(false, f3, f4);
        this.blitCamera.update();
        this.blitBatch.setProjectionMatrix(this.blitCamera.combined);
        float f5 = f3 / f4;
        float f6 = this.renderWidth / this.renderHeight;
        if (f5 > f6) {
            f2 = f4;
            f = f6 * f4;
        } else {
            f = f3;
            f2 = f3 / f6;
        }
        this.blitBatch.begin();
        this.blitBatch.draw(texture, (f3 - f) / 2.0f, (f4 - f2) / 2.0f, f, f2, 0, 0, texture.getWidth(), texture.getHeight(), false, true);
        this.blitBatch.end();
    }

    public void setTargetFps(int fps) {
        this.targetFps = fps;
    }

    public void setDepthRender(boolean value) {
        this.isDepthRenderEnabled = value;
    }

    /* JADX WARN: Code duplicated, block: B:103:0x0271 A[Catch: Exception -> 0x0676, TryCatch #4 {Exception -> 0x0676, blocks: (B:3:0x0002, B:5:0x001a, B:7:0x0026, B:16:0x003d, B:18:0x0041, B:20:0x005b, B:22:0x005f, B:23:0x0068, B:24:0x0072, B:26:0x0078, B:29:0x008d, B:30:0x0099, B:32:0x009f, B:34:0x00ad, B:37:0x00bf, B:38:0x00c5, B:40:0x00ce, B:42:0x00d2, B:43:0x00da, B:45:0x00e0, B:47:0x00ea, B:49:0x00ee, B:52:0x00f4, B:54:0x00f8, B:57:0x0127, B:60:0x0159, B:63:0x0167, B:64:0x016a, B:66:0x016f, B:68:0x0173, B:70:0x0180, B:73:0x0187, B:75:0x019a, B:77:0x019e, B:74:0x0198, B:78:0x01a2, B:80:0x01c1, B:82:0x01c5, B:83:0x01ce, B:85:0x01e7, B:86:0x01ff, B:88:0x0203, B:90:0x020d, B:91:0x0221, B:93:0x0225, B:94:0x0237, B:96:0x023b, B:97:0x0256, B:99:0x025a, B:101:0x0264, B:103:0x0271, B:104:0x0291, B:107:0x0297, B:109:0x029b, B:110:0x029e, B:112:0x02bc, B:114:0x02d9, B:116:0x02ea, B:117:0x02f3, B:118:0x034b, B:120:0x034f, B:122:0x0355, B:125:0x035a, B:126:0x03af, B:128:0x0411, B:130:0x041d, B:132:0x042b, B:134:0x0433, B:135:0x048d, B:129:0x0417, B:136:0x04b6, B:138:0x04ba, B:140:0x04c0, B:147:0x04cc, B:148:0x04d1, B:150:0x04da, B:153:0x04e0, B:154:0x04ea, B:155:0x04fb, B:157:0x04ff, B:159:0x0507, B:160:0x0521, B:178:0x05a2, B:180:0x05a6, B:182:0x05ae, B:184:0x05cf, B:185:0x05f8, B:187:0x05fe, B:188:0x0608, B:189:0x060d, B:191:0x0615, B:192:0x0638, B:194:0x063e, B:195:0x0648, B:196:0x0658, B:198:0x065d, B:161:0x0528, B:163:0x052c, B:166:0x0537, B:168:0x0540, B:170:0x054c, B:171:0x0566, B:172:0x0577, B:174:0x057d, B:176:0x058f, B:177:0x059d, B:167:0x053a, B:164:0x052f, B:200:0x066e, B:202:0x0672, B:11:0x002d, B:12:0x0035), top: B:233:0x0002 }] */
    /* JADX WARN: Code duplicated, block: B:106:0x0295  */
    /* JADX WARN: Code duplicated, block: B:107:0x0297 A[Catch: Exception -> 0x0676, TryCatch #4 {Exception -> 0x0676, blocks: (B:3:0x0002, B:5:0x001a, B:7:0x0026, B:16:0x003d, B:18:0x0041, B:20:0x005b, B:22:0x005f, B:23:0x0068, B:24:0x0072, B:26:0x0078, B:29:0x008d, B:30:0x0099, B:32:0x009f, B:34:0x00ad, B:37:0x00bf, B:38:0x00c5, B:40:0x00ce, B:42:0x00d2, B:43:0x00da, B:45:0x00e0, B:47:0x00ea, B:49:0x00ee, B:52:0x00f4, B:54:0x00f8, B:57:0x0127, B:60:0x0159, B:63:0x0167, B:64:0x016a, B:66:0x016f, B:68:0x0173, B:70:0x0180, B:73:0x0187, B:75:0x019a, B:77:0x019e, B:74:0x0198, B:78:0x01a2, B:80:0x01c1, B:82:0x01c5, B:83:0x01ce, B:85:0x01e7, B:86:0x01ff, B:88:0x0203, B:90:0x020d, B:91:0x0221, B:93:0x0225, B:94:0x0237, B:96:0x023b, B:97:0x0256, B:99:0x025a, B:101:0x0264, B:103:0x0271, B:104:0x0291, B:107:0x0297, B:109:0x029b, B:110:0x029e, B:112:0x02bc, B:114:0x02d9, B:116:0x02ea, B:117:0x02f3, B:118:0x034b, B:120:0x034f, B:122:0x0355, B:125:0x035a, B:126:0x03af, B:128:0x0411, B:130:0x041d, B:132:0x042b, B:134:0x0433, B:135:0x048d, B:129:0x0417, B:136:0x04b6, B:138:0x04ba, B:140:0x04c0, B:147:0x04cc, B:148:0x04d1, B:150:0x04da, B:153:0x04e0, B:154:0x04ea, B:155:0x04fb, B:157:0x04ff, B:159:0x0507, B:160:0x0521, B:178:0x05a2, B:180:0x05a6, B:182:0x05ae, B:184:0x05cf, B:185:0x05f8, B:187:0x05fe, B:188:0x0608, B:189:0x060d, B:191:0x0615, B:192:0x0638, B:194:0x063e, B:195:0x0648, B:196:0x0658, B:198:0x065d, B:161:0x0528, B:163:0x052c, B:166:0x0537, B:168:0x0540, B:170:0x054c, B:171:0x0566, B:172:0x0577, B:174:0x057d, B:176:0x058f, B:177:0x059d, B:167:0x053a, B:164:0x052f, B:200:0x066e, B:202:0x0672, B:11:0x002d, B:12:0x0035), top: B:233:0x0002 }] */
    /* JADX WARN: Code duplicated, block: B:109:0x029b A[Catch: Exception -> 0x0676, TryCatch #4 {Exception -> 0x0676, blocks: (B:3:0x0002, B:5:0x001a, B:7:0x0026, B:16:0x003d, B:18:0x0041, B:20:0x005b, B:22:0x005f, B:23:0x0068, B:24:0x0072, B:26:0x0078, B:29:0x008d, B:30:0x0099, B:32:0x009f, B:34:0x00ad, B:37:0x00bf, B:38:0x00c5, B:40:0x00ce, B:42:0x00d2, B:43:0x00da, B:45:0x00e0, B:47:0x00ea, B:49:0x00ee, B:52:0x00f4, B:54:0x00f8, B:57:0x0127, B:60:0x0159, B:63:0x0167, B:64:0x016a, B:66:0x016f, B:68:0x0173, B:70:0x0180, B:73:0x0187, B:75:0x019a, B:77:0x019e, B:74:0x0198, B:78:0x01a2, B:80:0x01c1, B:82:0x01c5, B:83:0x01ce, B:85:0x01e7, B:86:0x01ff, B:88:0x0203, B:90:0x020d, B:91:0x0221, B:93:0x0225, B:94:0x0237, B:96:0x023b, B:97:0x0256, B:99:0x025a, B:101:0x0264, B:103:0x0271, B:104:0x0291, B:107:0x0297, B:109:0x029b, B:110:0x029e, B:112:0x02bc, B:114:0x02d9, B:116:0x02ea, B:117:0x02f3, B:118:0x034b, B:120:0x034f, B:122:0x0355, B:125:0x035a, B:126:0x03af, B:128:0x0411, B:130:0x041d, B:132:0x042b, B:134:0x0433, B:135:0x048d, B:129:0x0417, B:136:0x04b6, B:138:0x04ba, B:140:0x04c0, B:147:0x04cc, B:148:0x04d1, B:150:0x04da, B:153:0x04e0, B:154:0x04ea, B:155:0x04fb, B:157:0x04ff, B:159:0x0507, B:160:0x0521, B:178:0x05a2, B:180:0x05a6, B:182:0x05ae, B:184:0x05cf, B:185:0x05f8, B:187:0x05fe, B:188:0x0608, B:189:0x060d, B:191:0x0615, B:192:0x0638, B:194:0x063e, B:195:0x0648, B:196:0x0658, B:198:0x065d, B:161:0x0528, B:163:0x052c, B:166:0x0537, B:168:0x0540, B:170:0x054c, B:171:0x0566, B:172:0x0577, B:174:0x057d, B:176:0x058f, B:177:0x059d, B:167:0x053a, B:164:0x052f, B:200:0x066e, B:202:0x0672, B:11:0x002d, B:12:0x0035), top: B:233:0x0002 }] */
    /* JADX WARN: Code duplicated, block: B:116:0x02ea A[Catch: Exception -> 0x0676, TryCatch #4 {Exception -> 0x0676, blocks: (B:3:0x0002, B:5:0x001a, B:7:0x0026, B:16:0x003d, B:18:0x0041, B:20:0x005b, B:22:0x005f, B:23:0x0068, B:24:0x0072, B:26:0x0078, B:29:0x008d, B:30:0x0099, B:32:0x009f, B:34:0x00ad, B:37:0x00bf, B:38:0x00c5, B:40:0x00ce, B:42:0x00d2, B:43:0x00da, B:45:0x00e0, B:47:0x00ea, B:49:0x00ee, B:52:0x00f4, B:54:0x00f8, B:57:0x0127, B:60:0x0159, B:63:0x0167, B:64:0x016a, B:66:0x016f, B:68:0x0173, B:70:0x0180, B:73:0x0187, B:75:0x019a, B:77:0x019e, B:74:0x0198, B:78:0x01a2, B:80:0x01c1, B:82:0x01c5, B:83:0x01ce, B:85:0x01e7, B:86:0x01ff, B:88:0x0203, B:90:0x020d, B:91:0x0221, B:93:0x0225, B:94:0x0237, B:96:0x023b, B:97:0x0256, B:99:0x025a, B:101:0x0264, B:103:0x0271, B:104:0x0291, B:107:0x0297, B:109:0x029b, B:110:0x029e, B:112:0x02bc, B:114:0x02d9, B:116:0x02ea, B:117:0x02f3, B:118:0x034b, B:120:0x034f, B:122:0x0355, B:125:0x035a, B:126:0x03af, B:128:0x0411, B:130:0x041d, B:132:0x042b, B:134:0x0433, B:135:0x048d, B:129:0x0417, B:136:0x04b6, B:138:0x04ba, B:140:0x04c0, B:147:0x04cc, B:148:0x04d1, B:150:0x04da, B:153:0x04e0, B:154:0x04ea, B:155:0x04fb, B:157:0x04ff, B:159:0x0507, B:160:0x0521, B:178:0x05a2, B:180:0x05a6, B:182:0x05ae, B:184:0x05cf, B:185:0x05f8, B:187:0x05fe, B:188:0x0608, B:189:0x060d, B:191:0x0615, B:192:0x0638, B:194:0x063e, B:195:0x0648, B:196:0x0658, B:198:0x065d, B:161:0x0528, B:163:0x052c, B:166:0x0537, B:168:0x0540, B:170:0x054c, B:171:0x0566, B:172:0x0577, B:174:0x057d, B:176:0x058f, B:177:0x059d, B:167:0x053a, B:164:0x052f, B:200:0x066e, B:202:0x0672, B:11:0x002d, B:12:0x0035), top: B:233:0x0002 }] */
    /* JADX WARN: Code duplicated, block: B:120:0x034f A[Catch: Exception -> 0x0676, TryCatch #4 {Exception -> 0x0676, blocks: (B:3:0x0002, B:5:0x001a, B:7:0x0026, B:16:0x003d, B:18:0x0041, B:20:0x005b, B:22:0x005f, B:23:0x0068, B:24:0x0072, B:26:0x0078, B:29:0x008d, B:30:0x0099, B:32:0x009f, B:34:0x00ad, B:37:0x00bf, B:38:0x00c5, B:40:0x00ce, B:42:0x00d2, B:43:0x00da, B:45:0x00e0, B:47:0x00ea, B:49:0x00ee, B:52:0x00f4, B:54:0x00f8, B:57:0x0127, B:60:0x0159, B:63:0x0167, B:64:0x016a, B:66:0x016f, B:68:0x0173, B:70:0x0180, B:73:0x0187, B:75:0x019a, B:77:0x019e, B:74:0x0198, B:78:0x01a2, B:80:0x01c1, B:82:0x01c5, B:83:0x01ce, B:85:0x01e7, B:86:0x01ff, B:88:0x0203, B:90:0x020d, B:91:0x0221, B:93:0x0225, B:94:0x0237, B:96:0x023b, B:97:0x0256, B:99:0x025a, B:101:0x0264, B:103:0x0271, B:104:0x0291, B:107:0x0297, B:109:0x029b, B:110:0x029e, B:112:0x02bc, B:114:0x02d9, B:116:0x02ea, B:117:0x02f3, B:118:0x034b, B:120:0x034f, B:122:0x0355, B:125:0x035a, B:126:0x03af, B:128:0x0411, B:130:0x041d, B:132:0x042b, B:134:0x0433, B:135:0x048d, B:129:0x0417, B:136:0x04b6, B:138:0x04ba, B:140:0x04c0, B:147:0x04cc, B:148:0x04d1, B:150:0x04da, B:153:0x04e0, B:154:0x04ea, B:155:0x04fb, B:157:0x04ff, B:159:0x0507, B:160:0x0521, B:178:0x05a2, B:180:0x05a6, B:182:0x05ae, B:184:0x05cf, B:185:0x05f8, B:187:0x05fe, B:188:0x0608, B:189:0x060d, B:191:0x0615, B:192:0x0638, B:194:0x063e, B:195:0x0648, B:196:0x0658, B:198:0x065d, B:161:0x0528, B:163:0x052c, B:166:0x0537, B:168:0x0540, B:170:0x054c, B:171:0x0566, B:172:0x0577, B:174:0x057d, B:176:0x058f, B:177:0x059d, B:167:0x053a, B:164:0x052f, B:200:0x066e, B:202:0x0672, B:11:0x002d, B:12:0x0035), top: B:233:0x0002 }] */
    /* JADX WARN: Code duplicated, block: B:128:0x0411 A[Catch: Exception -> 0x0676, TryCatch #4 {Exception -> 0x0676, blocks: (B:3:0x0002, B:5:0x001a, B:7:0x0026, B:16:0x003d, B:18:0x0041, B:20:0x005b, B:22:0x005f, B:23:0x0068, B:24:0x0072, B:26:0x0078, B:29:0x008d, B:30:0x0099, B:32:0x009f, B:34:0x00ad, B:37:0x00bf, B:38:0x00c5, B:40:0x00ce, B:42:0x00d2, B:43:0x00da, B:45:0x00e0, B:47:0x00ea, B:49:0x00ee, B:52:0x00f4, B:54:0x00f8, B:57:0x0127, B:60:0x0159, B:63:0x0167, B:64:0x016a, B:66:0x016f, B:68:0x0173, B:70:0x0180, B:73:0x0187, B:75:0x019a, B:77:0x019e, B:74:0x0198, B:78:0x01a2, B:80:0x01c1, B:82:0x01c5, B:83:0x01ce, B:85:0x01e7, B:86:0x01ff, B:88:0x0203, B:90:0x020d, B:91:0x0221, B:93:0x0225, B:94:0x0237, B:96:0x023b, B:97:0x0256, B:99:0x025a, B:101:0x0264, B:103:0x0271, B:104:0x0291, B:107:0x0297, B:109:0x029b, B:110:0x029e, B:112:0x02bc, B:114:0x02d9, B:116:0x02ea, B:117:0x02f3, B:118:0x034b, B:120:0x034f, B:122:0x0355, B:125:0x035a, B:126:0x03af, B:128:0x0411, B:130:0x041d, B:132:0x042b, B:134:0x0433, B:135:0x048d, B:129:0x0417, B:136:0x04b6, B:138:0x04ba, B:140:0x04c0, B:147:0x04cc, B:148:0x04d1, B:150:0x04da, B:153:0x04e0, B:154:0x04ea, B:155:0x04fb, B:157:0x04ff, B:159:0x0507, B:160:0x0521, B:178:0x05a2, B:180:0x05a6, B:182:0x05ae, B:184:0x05cf, B:185:0x05f8, B:187:0x05fe, B:188:0x0608, B:189:0x060d, B:191:0x0615, B:192:0x0638, B:194:0x063e, B:195:0x0648, B:196:0x0658, B:198:0x065d, B:161:0x0528, B:163:0x052c, B:166:0x0537, B:168:0x0540, B:170:0x054c, B:171:0x0566, B:172:0x0577, B:174:0x057d, B:176:0x058f, B:177:0x059d, B:167:0x053a, B:164:0x052f, B:200:0x066e, B:202:0x0672, B:11:0x002d, B:12:0x0035), top: B:233:0x0002 }] */
    /* JADX WARN: Code duplicated, block: B:129:0x0417 A[Catch: Exception -> 0x0676, TryCatch #4 {Exception -> 0x0676, blocks: (B:3:0x0002, B:5:0x001a, B:7:0x0026, B:16:0x003d, B:18:0x0041, B:20:0x005b, B:22:0x005f, B:23:0x0068, B:24:0x0072, B:26:0x0078, B:29:0x008d, B:30:0x0099, B:32:0x009f, B:34:0x00ad, B:37:0x00bf, B:38:0x00c5, B:40:0x00ce, B:42:0x00d2, B:43:0x00da, B:45:0x00e0, B:47:0x00ea, B:49:0x00ee, B:52:0x00f4, B:54:0x00f8, B:57:0x0127, B:60:0x0159, B:63:0x0167, B:64:0x016a, B:66:0x016f, B:68:0x0173, B:70:0x0180, B:73:0x0187, B:75:0x019a, B:77:0x019e, B:74:0x0198, B:78:0x01a2, B:80:0x01c1, B:82:0x01c5, B:83:0x01ce, B:85:0x01e7, B:86:0x01ff, B:88:0x0203, B:90:0x020d, B:91:0x0221, B:93:0x0225, B:94:0x0237, B:96:0x023b, B:97:0x0256, B:99:0x025a, B:101:0x0264, B:103:0x0271, B:104:0x0291, B:107:0x0297, B:109:0x029b, B:110:0x029e, B:112:0x02bc, B:114:0x02d9, B:116:0x02ea, B:117:0x02f3, B:118:0x034b, B:120:0x034f, B:122:0x0355, B:125:0x035a, B:126:0x03af, B:128:0x0411, B:130:0x041d, B:132:0x042b, B:134:0x0433, B:135:0x048d, B:129:0x0417, B:136:0x04b6, B:138:0x04ba, B:140:0x04c0, B:147:0x04cc, B:148:0x04d1, B:150:0x04da, B:153:0x04e0, B:154:0x04ea, B:155:0x04fb, B:157:0x04ff, B:159:0x0507, B:160:0x0521, B:178:0x05a2, B:180:0x05a6, B:182:0x05ae, B:184:0x05cf, B:185:0x05f8, B:187:0x05fe, B:188:0x0608, B:189:0x060d, B:191:0x0615, B:192:0x0638, B:194:0x063e, B:195:0x0648, B:196:0x0658, B:198:0x065d, B:161:0x0528, B:163:0x052c, B:166:0x0537, B:168:0x0540, B:170:0x054c, B:171:0x0566, B:172:0x0577, B:174:0x057d, B:176:0x058f, B:177:0x059d, B:167:0x053a, B:164:0x052f, B:200:0x066e, B:202:0x0672, B:11:0x002d, B:12:0x0035), top: B:233:0x0002 }] */
    /* JADX WARN: Code duplicated, block: B:136:0x04b6 A[Catch: Exception -> 0x0676, TryCatch #4 {Exception -> 0x0676, blocks: (B:3:0x0002, B:5:0x001a, B:7:0x0026, B:16:0x003d, B:18:0x0041, B:20:0x005b, B:22:0x005f, B:23:0x0068, B:24:0x0072, B:26:0x0078, B:29:0x008d, B:30:0x0099, B:32:0x009f, B:34:0x00ad, B:37:0x00bf, B:38:0x00c5, B:40:0x00ce, B:42:0x00d2, B:43:0x00da, B:45:0x00e0, B:47:0x00ea, B:49:0x00ee, B:52:0x00f4, B:54:0x00f8, B:57:0x0127, B:60:0x0159, B:63:0x0167, B:64:0x016a, B:66:0x016f, B:68:0x0173, B:70:0x0180, B:73:0x0187, B:75:0x019a, B:77:0x019e, B:74:0x0198, B:78:0x01a2, B:80:0x01c1, B:82:0x01c5, B:83:0x01ce, B:85:0x01e7, B:86:0x01ff, B:88:0x0203, B:90:0x020d, B:91:0x0221, B:93:0x0225, B:94:0x0237, B:96:0x023b, B:97:0x0256, B:99:0x025a, B:101:0x0264, B:103:0x0271, B:104:0x0291, B:107:0x0297, B:109:0x029b, B:110:0x029e, B:112:0x02bc, B:114:0x02d9, B:116:0x02ea, B:117:0x02f3, B:118:0x034b, B:120:0x034f, B:122:0x0355, B:125:0x035a, B:126:0x03af, B:128:0x0411, B:130:0x041d, B:132:0x042b, B:134:0x0433, B:135:0x048d, B:129:0x0417, B:136:0x04b6, B:138:0x04ba, B:140:0x04c0, B:147:0x04cc, B:148:0x04d1, B:150:0x04da, B:153:0x04e0, B:154:0x04ea, B:155:0x04fb, B:157:0x04ff, B:159:0x0507, B:160:0x0521, B:178:0x05a2, B:180:0x05a6, B:182:0x05ae, B:184:0x05cf, B:185:0x05f8, B:187:0x05fe, B:188:0x0608, B:189:0x060d, B:191:0x0615, B:192:0x0638, B:194:0x063e, B:195:0x0648, B:196:0x0658, B:198:0x065d, B:161:0x0528, B:163:0x052c, B:166:0x0537, B:168:0x0540, B:170:0x054c, B:171:0x0566, B:172:0x0577, B:174:0x057d, B:176:0x058f, B:177:0x059d, B:167:0x053a, B:164:0x052f, B:200:0x066e, B:202:0x0672, B:11:0x002d, B:12:0x0035), top: B:233:0x0002 }] */
    /* JADX WARN: Code duplicated, block: B:144:0x04c7  */
    /* JADX WARN: Code duplicated, block: B:146:0x04ca A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:147:0x04cc A[Catch: Exception -> 0x0676, TryCatch #4 {Exception -> 0x0676, blocks: (B:3:0x0002, B:5:0x001a, B:7:0x0026, B:16:0x003d, B:18:0x0041, B:20:0x005b, B:22:0x005f, B:23:0x0068, B:24:0x0072, B:26:0x0078, B:29:0x008d, B:30:0x0099, B:32:0x009f, B:34:0x00ad, B:37:0x00bf, B:38:0x00c5, B:40:0x00ce, B:42:0x00d2, B:43:0x00da, B:45:0x00e0, B:47:0x00ea, B:49:0x00ee, B:52:0x00f4, B:54:0x00f8, B:57:0x0127, B:60:0x0159, B:63:0x0167, B:64:0x016a, B:66:0x016f, B:68:0x0173, B:70:0x0180, B:73:0x0187, B:75:0x019a, B:77:0x019e, B:74:0x0198, B:78:0x01a2, B:80:0x01c1, B:82:0x01c5, B:83:0x01ce, B:85:0x01e7, B:86:0x01ff, B:88:0x0203, B:90:0x020d, B:91:0x0221, B:93:0x0225, B:94:0x0237, B:96:0x023b, B:97:0x0256, B:99:0x025a, B:101:0x0264, B:103:0x0271, B:104:0x0291, B:107:0x0297, B:109:0x029b, B:110:0x029e, B:112:0x02bc, B:114:0x02d9, B:116:0x02ea, B:117:0x02f3, B:118:0x034b, B:120:0x034f, B:122:0x0355, B:125:0x035a, B:126:0x03af, B:128:0x0411, B:130:0x041d, B:132:0x042b, B:134:0x0433, B:135:0x048d, B:129:0x0417, B:136:0x04b6, B:138:0x04ba, B:140:0x04c0, B:147:0x04cc, B:148:0x04d1, B:150:0x04da, B:153:0x04e0, B:154:0x04ea, B:155:0x04fb, B:157:0x04ff, B:159:0x0507, B:160:0x0521, B:178:0x05a2, B:180:0x05a6, B:182:0x05ae, B:184:0x05cf, B:185:0x05f8, B:187:0x05fe, B:188:0x0608, B:189:0x060d, B:191:0x0615, B:192:0x0638, B:194:0x063e, B:195:0x0648, B:196:0x0658, B:198:0x065d, B:161:0x0528, B:163:0x052c, B:166:0x0537, B:168:0x0540, B:170:0x054c, B:171:0x0566, B:172:0x0577, B:174:0x057d, B:176:0x058f, B:177:0x059d, B:167:0x053a, B:164:0x052f, B:200:0x066e, B:202:0x0672, B:11:0x002d, B:12:0x0035), top: B:233:0x0002 }] */
    /* JADX WARN: Code duplicated, block: B:153:0x04e0 A[Catch: Exception -> 0x0676, TryCatch #4 {Exception -> 0x0676, blocks: (B:3:0x0002, B:5:0x001a, B:7:0x0026, B:16:0x003d, B:18:0x0041, B:20:0x005b, B:22:0x005f, B:23:0x0068, B:24:0x0072, B:26:0x0078, B:29:0x008d, B:30:0x0099, B:32:0x009f, B:34:0x00ad, B:37:0x00bf, B:38:0x00c5, B:40:0x00ce, B:42:0x00d2, B:43:0x00da, B:45:0x00e0, B:47:0x00ea, B:49:0x00ee, B:52:0x00f4, B:54:0x00f8, B:57:0x0127, B:60:0x0159, B:63:0x0167, B:64:0x016a, B:66:0x016f, B:68:0x0173, B:70:0x0180, B:73:0x0187, B:75:0x019a, B:77:0x019e, B:74:0x0198, B:78:0x01a2, B:80:0x01c1, B:82:0x01c5, B:83:0x01ce, B:85:0x01e7, B:86:0x01ff, B:88:0x0203, B:90:0x020d, B:91:0x0221, B:93:0x0225, B:94:0x0237, B:96:0x023b, B:97:0x0256, B:99:0x025a, B:101:0x0264, B:103:0x0271, B:104:0x0291, B:107:0x0297, B:109:0x029b, B:110:0x029e, B:112:0x02bc, B:114:0x02d9, B:116:0x02ea, B:117:0x02f3, B:118:0x034b, B:120:0x034f, B:122:0x0355, B:125:0x035a, B:126:0x03af, B:128:0x0411, B:130:0x041d, B:132:0x042b, B:134:0x0433, B:135:0x048d, B:129:0x0417, B:136:0x04b6, B:138:0x04ba, B:140:0x04c0, B:147:0x04cc, B:148:0x04d1, B:150:0x04da, B:153:0x04e0, B:154:0x04ea, B:155:0x04fb, B:157:0x04ff, B:159:0x0507, B:160:0x0521, B:178:0x05a2, B:180:0x05a6, B:182:0x05ae, B:184:0x05cf, B:185:0x05f8, B:187:0x05fe, B:188:0x0608, B:189:0x060d, B:191:0x0615, B:192:0x0638, B:194:0x063e, B:195:0x0648, B:196:0x0658, B:198:0x065d, B:161:0x0528, B:163:0x052c, B:166:0x0537, B:168:0x0540, B:170:0x054c, B:171:0x0566, B:172:0x0577, B:174:0x057d, B:176:0x058f, B:177:0x059d, B:167:0x053a, B:164:0x052f, B:200:0x066e, B:202:0x0672, B:11:0x002d, B:12:0x0035), top: B:233:0x0002 }] */
    /* JADX WARN: Code duplicated, block: B:154:0x04ea A[Catch: Exception -> 0x0676, TryCatch #4 {Exception -> 0x0676, blocks: (B:3:0x0002, B:5:0x001a, B:7:0x0026, B:16:0x003d, B:18:0x0041, B:20:0x005b, B:22:0x005f, B:23:0x0068, B:24:0x0072, B:26:0x0078, B:29:0x008d, B:30:0x0099, B:32:0x009f, B:34:0x00ad, B:37:0x00bf, B:38:0x00c5, B:40:0x00ce, B:42:0x00d2, B:43:0x00da, B:45:0x00e0, B:47:0x00ea, B:49:0x00ee, B:52:0x00f4, B:54:0x00f8, B:57:0x0127, B:60:0x0159, B:63:0x0167, B:64:0x016a, B:66:0x016f, B:68:0x0173, B:70:0x0180, B:73:0x0187, B:75:0x019a, B:77:0x019e, B:74:0x0198, B:78:0x01a2, B:80:0x01c1, B:82:0x01c5, B:83:0x01ce, B:85:0x01e7, B:86:0x01ff, B:88:0x0203, B:90:0x020d, B:91:0x0221, B:93:0x0225, B:94:0x0237, B:96:0x023b, B:97:0x0256, B:99:0x025a, B:101:0x0264, B:103:0x0271, B:104:0x0291, B:107:0x0297, B:109:0x029b, B:110:0x029e, B:112:0x02bc, B:114:0x02d9, B:116:0x02ea, B:117:0x02f3, B:118:0x034b, B:120:0x034f, B:122:0x0355, B:125:0x035a, B:126:0x03af, B:128:0x0411, B:130:0x041d, B:132:0x042b, B:134:0x0433, B:135:0x048d, B:129:0x0417, B:136:0x04b6, B:138:0x04ba, B:140:0x04c0, B:147:0x04cc, B:148:0x04d1, B:150:0x04da, B:153:0x04e0, B:154:0x04ea, B:155:0x04fb, B:157:0x04ff, B:159:0x0507, B:160:0x0521, B:178:0x05a2, B:180:0x05a6, B:182:0x05ae, B:184:0x05cf, B:185:0x05f8, B:187:0x05fe, B:188:0x0608, B:189:0x060d, B:191:0x0615, B:192:0x0638, B:194:0x063e, B:195:0x0648, B:196:0x0658, B:198:0x065d, B:161:0x0528, B:163:0x052c, B:166:0x0537, B:168:0x0540, B:170:0x054c, B:171:0x0566, B:172:0x0577, B:174:0x057d, B:176:0x058f, B:177:0x059d, B:167:0x053a, B:164:0x052f, B:200:0x066e, B:202:0x0672, B:11:0x002d, B:12:0x0035), top: B:233:0x0002 }] */
    /* JADX WARN: Code duplicated, block: B:157:0x04ff A[Catch: Exception -> 0x0676, TryCatch #4 {Exception -> 0x0676, blocks: (B:3:0x0002, B:5:0x001a, B:7:0x0026, B:16:0x003d, B:18:0x0041, B:20:0x005b, B:22:0x005f, B:23:0x0068, B:24:0x0072, B:26:0x0078, B:29:0x008d, B:30:0x0099, B:32:0x009f, B:34:0x00ad, B:37:0x00bf, B:38:0x00c5, B:40:0x00ce, B:42:0x00d2, B:43:0x00da, B:45:0x00e0, B:47:0x00ea, B:49:0x00ee, B:52:0x00f4, B:54:0x00f8, B:57:0x0127, B:60:0x0159, B:63:0x0167, B:64:0x016a, B:66:0x016f, B:68:0x0173, B:70:0x0180, B:73:0x0187, B:75:0x019a, B:77:0x019e, B:74:0x0198, B:78:0x01a2, B:80:0x01c1, B:82:0x01c5, B:83:0x01ce, B:85:0x01e7, B:86:0x01ff, B:88:0x0203, B:90:0x020d, B:91:0x0221, B:93:0x0225, B:94:0x0237, B:96:0x023b, B:97:0x0256, B:99:0x025a, B:101:0x0264, B:103:0x0271, B:104:0x0291, B:107:0x0297, B:109:0x029b, B:110:0x029e, B:112:0x02bc, B:114:0x02d9, B:116:0x02ea, B:117:0x02f3, B:118:0x034b, B:120:0x034f, B:122:0x0355, B:125:0x035a, B:126:0x03af, B:128:0x0411, B:130:0x041d, B:132:0x042b, B:134:0x0433, B:135:0x048d, B:129:0x0417, B:136:0x04b6, B:138:0x04ba, B:140:0x04c0, B:147:0x04cc, B:148:0x04d1, B:150:0x04da, B:153:0x04e0, B:154:0x04ea, B:155:0x04fb, B:157:0x04ff, B:159:0x0507, B:160:0x0521, B:178:0x05a2, B:180:0x05a6, B:182:0x05ae, B:184:0x05cf, B:185:0x05f8, B:187:0x05fe, B:188:0x0608, B:189:0x060d, B:191:0x0615, B:192:0x0638, B:194:0x063e, B:195:0x0648, B:196:0x0658, B:198:0x065d, B:161:0x0528, B:163:0x052c, B:166:0x0537, B:168:0x0540, B:170:0x054c, B:171:0x0566, B:172:0x0577, B:174:0x057d, B:176:0x058f, B:177:0x059d, B:167:0x053a, B:164:0x052f, B:200:0x066e, B:202:0x0672, B:11:0x002d, B:12:0x0035), top: B:233:0x0002 }] */
    /* JADX WARN: Code duplicated, block: B:159:0x0507 A[Catch: Exception -> 0x0676, TryCatch #4 {Exception -> 0x0676, blocks: (B:3:0x0002, B:5:0x001a, B:7:0x0026, B:16:0x003d, B:18:0x0041, B:20:0x005b, B:22:0x005f, B:23:0x0068, B:24:0x0072, B:26:0x0078, B:29:0x008d, B:30:0x0099, B:32:0x009f, B:34:0x00ad, B:37:0x00bf, B:38:0x00c5, B:40:0x00ce, B:42:0x00d2, B:43:0x00da, B:45:0x00e0, B:47:0x00ea, B:49:0x00ee, B:52:0x00f4, B:54:0x00f8, B:57:0x0127, B:60:0x0159, B:63:0x0167, B:64:0x016a, B:66:0x016f, B:68:0x0173, B:70:0x0180, B:73:0x0187, B:75:0x019a, B:77:0x019e, B:74:0x0198, B:78:0x01a2, B:80:0x01c1, B:82:0x01c5, B:83:0x01ce, B:85:0x01e7, B:86:0x01ff, B:88:0x0203, B:90:0x020d, B:91:0x0221, B:93:0x0225, B:94:0x0237, B:96:0x023b, B:97:0x0256, B:99:0x025a, B:101:0x0264, B:103:0x0271, B:104:0x0291, B:107:0x0297, B:109:0x029b, B:110:0x029e, B:112:0x02bc, B:114:0x02d9, B:116:0x02ea, B:117:0x02f3, B:118:0x034b, B:120:0x034f, B:122:0x0355, B:125:0x035a, B:126:0x03af, B:128:0x0411, B:130:0x041d, B:132:0x042b, B:134:0x0433, B:135:0x048d, B:129:0x0417, B:136:0x04b6, B:138:0x04ba, B:140:0x04c0, B:147:0x04cc, B:148:0x04d1, B:150:0x04da, B:153:0x04e0, B:154:0x04ea, B:155:0x04fb, B:157:0x04ff, B:159:0x0507, B:160:0x0521, B:178:0x05a2, B:180:0x05a6, B:182:0x05ae, B:184:0x05cf, B:185:0x05f8, B:187:0x05fe, B:188:0x0608, B:189:0x060d, B:191:0x0615, B:192:0x0638, B:194:0x063e, B:195:0x0648, B:196:0x0658, B:198:0x065d, B:161:0x0528, B:163:0x052c, B:166:0x0537, B:168:0x0540, B:170:0x054c, B:171:0x0566, B:172:0x0577, B:174:0x057d, B:176:0x058f, B:177:0x059d, B:167:0x053a, B:164:0x052f, B:200:0x066e, B:202:0x0672, B:11:0x002d, B:12:0x0035), top: B:233:0x0002 }] */
    /* JADX WARN: Code duplicated, block: B:161:0x0528 A[Catch: Exception -> 0x0676, TryCatch #4 {Exception -> 0x0676, blocks: (B:3:0x0002, B:5:0x001a, B:7:0x0026, B:16:0x003d, B:18:0x0041, B:20:0x005b, B:22:0x005f, B:23:0x0068, B:24:0x0072, B:26:0x0078, B:29:0x008d, B:30:0x0099, B:32:0x009f, B:34:0x00ad, B:37:0x00bf, B:38:0x00c5, B:40:0x00ce, B:42:0x00d2, B:43:0x00da, B:45:0x00e0, B:47:0x00ea, B:49:0x00ee, B:52:0x00f4, B:54:0x00f8, B:57:0x0127, B:60:0x0159, B:63:0x0167, B:64:0x016a, B:66:0x016f, B:68:0x0173, B:70:0x0180, B:73:0x0187, B:75:0x019a, B:77:0x019e, B:74:0x0198, B:78:0x01a2, B:80:0x01c1, B:82:0x01c5, B:83:0x01ce, B:85:0x01e7, B:86:0x01ff, B:88:0x0203, B:90:0x020d, B:91:0x0221, B:93:0x0225, B:94:0x0237, B:96:0x023b, B:97:0x0256, B:99:0x025a, B:101:0x0264, B:103:0x0271, B:104:0x0291, B:107:0x0297, B:109:0x029b, B:110:0x029e, B:112:0x02bc, B:114:0x02d9, B:116:0x02ea, B:117:0x02f3, B:118:0x034b, B:120:0x034f, B:122:0x0355, B:125:0x035a, B:126:0x03af, B:128:0x0411, B:130:0x041d, B:132:0x042b, B:134:0x0433, B:135:0x048d, B:129:0x0417, B:136:0x04b6, B:138:0x04ba, B:140:0x04c0, B:147:0x04cc, B:148:0x04d1, B:150:0x04da, B:153:0x04e0, B:154:0x04ea, B:155:0x04fb, B:157:0x04ff, B:159:0x0507, B:160:0x0521, B:178:0x05a2, B:180:0x05a6, B:182:0x05ae, B:184:0x05cf, B:185:0x05f8, B:187:0x05fe, B:188:0x0608, B:189:0x060d, B:191:0x0615, B:192:0x0638, B:194:0x063e, B:195:0x0648, B:196:0x0658, B:198:0x065d, B:161:0x0528, B:163:0x052c, B:166:0x0537, B:168:0x0540, B:170:0x054c, B:171:0x0566, B:172:0x0577, B:174:0x057d, B:176:0x058f, B:177:0x059d, B:167:0x053a, B:164:0x052f, B:200:0x066e, B:202:0x0672, B:11:0x002d, B:12:0x0035), top: B:233:0x0002 }] */
    /* JADX WARN: Code duplicated, block: B:163:0x052c A[Catch: Exception -> 0x0676, TryCatch #4 {Exception -> 0x0676, blocks: (B:3:0x0002, B:5:0x001a, B:7:0x0026, B:16:0x003d, B:18:0x0041, B:20:0x005b, B:22:0x005f, B:23:0x0068, B:24:0x0072, B:26:0x0078, B:29:0x008d, B:30:0x0099, B:32:0x009f, B:34:0x00ad, B:37:0x00bf, B:38:0x00c5, B:40:0x00ce, B:42:0x00d2, B:43:0x00da, B:45:0x00e0, B:47:0x00ea, B:49:0x00ee, B:52:0x00f4, B:54:0x00f8, B:57:0x0127, B:60:0x0159, B:63:0x0167, B:64:0x016a, B:66:0x016f, B:68:0x0173, B:70:0x0180, B:73:0x0187, B:75:0x019a, B:77:0x019e, B:74:0x0198, B:78:0x01a2, B:80:0x01c1, B:82:0x01c5, B:83:0x01ce, B:85:0x01e7, B:86:0x01ff, B:88:0x0203, B:90:0x020d, B:91:0x0221, B:93:0x0225, B:94:0x0237, B:96:0x023b, B:97:0x0256, B:99:0x025a, B:101:0x0264, B:103:0x0271, B:104:0x0291, B:107:0x0297, B:109:0x029b, B:110:0x029e, B:112:0x02bc, B:114:0x02d9, B:116:0x02ea, B:117:0x02f3, B:118:0x034b, B:120:0x034f, B:122:0x0355, B:125:0x035a, B:126:0x03af, B:128:0x0411, B:130:0x041d, B:132:0x042b, B:134:0x0433, B:135:0x048d, B:129:0x0417, B:136:0x04b6, B:138:0x04ba, B:140:0x04c0, B:147:0x04cc, B:148:0x04d1, B:150:0x04da, B:153:0x04e0, B:154:0x04ea, B:155:0x04fb, B:157:0x04ff, B:159:0x0507, B:160:0x0521, B:178:0x05a2, B:180:0x05a6, B:182:0x05ae, B:184:0x05cf, B:185:0x05f8, B:187:0x05fe, B:188:0x0608, B:189:0x060d, B:191:0x0615, B:192:0x0638, B:194:0x063e, B:195:0x0648, B:196:0x0658, B:198:0x065d, B:161:0x0528, B:163:0x052c, B:166:0x0537, B:168:0x0540, B:170:0x054c, B:171:0x0566, B:172:0x0577, B:174:0x057d, B:176:0x058f, B:177:0x059d, B:167:0x053a, B:164:0x052f, B:200:0x066e, B:202:0x0672, B:11:0x002d, B:12:0x0035), top: B:233:0x0002 }] */
    /* JADX WARN: Code duplicated, block: B:164:0x052f A[Catch: Exception -> 0x0676, TryCatch #4 {Exception -> 0x0676, blocks: (B:3:0x0002, B:5:0x001a, B:7:0x0026, B:16:0x003d, B:18:0x0041, B:20:0x005b, B:22:0x005f, B:23:0x0068, B:24:0x0072, B:26:0x0078, B:29:0x008d, B:30:0x0099, B:32:0x009f, B:34:0x00ad, B:37:0x00bf, B:38:0x00c5, B:40:0x00ce, B:42:0x00d2, B:43:0x00da, B:45:0x00e0, B:47:0x00ea, B:49:0x00ee, B:52:0x00f4, B:54:0x00f8, B:57:0x0127, B:60:0x0159, B:63:0x0167, B:64:0x016a, B:66:0x016f, B:68:0x0173, B:70:0x0180, B:73:0x0187, B:75:0x019a, B:77:0x019e, B:74:0x0198, B:78:0x01a2, B:80:0x01c1, B:82:0x01c5, B:83:0x01ce, B:85:0x01e7, B:86:0x01ff, B:88:0x0203, B:90:0x020d, B:91:0x0221, B:93:0x0225, B:94:0x0237, B:96:0x023b, B:97:0x0256, B:99:0x025a, B:101:0x0264, B:103:0x0271, B:104:0x0291, B:107:0x0297, B:109:0x029b, B:110:0x029e, B:112:0x02bc, B:114:0x02d9, B:116:0x02ea, B:117:0x02f3, B:118:0x034b, B:120:0x034f, B:122:0x0355, B:125:0x035a, B:126:0x03af, B:128:0x0411, B:130:0x041d, B:132:0x042b, B:134:0x0433, B:135:0x048d, B:129:0x0417, B:136:0x04b6, B:138:0x04ba, B:140:0x04c0, B:147:0x04cc, B:148:0x04d1, B:150:0x04da, B:153:0x04e0, B:154:0x04ea, B:155:0x04fb, B:157:0x04ff, B:159:0x0507, B:160:0x0521, B:178:0x05a2, B:180:0x05a6, B:182:0x05ae, B:184:0x05cf, B:185:0x05f8, B:187:0x05fe, B:188:0x0608, B:189:0x060d, B:191:0x0615, B:192:0x0638, B:194:0x063e, B:195:0x0648, B:196:0x0658, B:198:0x065d, B:161:0x0528, B:163:0x052c, B:166:0x0537, B:168:0x0540, B:170:0x054c, B:171:0x0566, B:172:0x0577, B:174:0x057d, B:176:0x058f, B:177:0x059d, B:167:0x053a, B:164:0x052f, B:200:0x066e, B:202:0x0672, B:11:0x002d, B:12:0x0035), top: B:233:0x0002 }] */
    /* JADX WARN: Code duplicated, block: B:166:0x0537 A[Catch: Exception -> 0x0676, TryCatch #4 {Exception -> 0x0676, blocks: (B:3:0x0002, B:5:0x001a, B:7:0x0026, B:16:0x003d, B:18:0x0041, B:20:0x005b, B:22:0x005f, B:23:0x0068, B:24:0x0072, B:26:0x0078, B:29:0x008d, B:30:0x0099, B:32:0x009f, B:34:0x00ad, B:37:0x00bf, B:38:0x00c5, B:40:0x00ce, B:42:0x00d2, B:43:0x00da, B:45:0x00e0, B:47:0x00ea, B:49:0x00ee, B:52:0x00f4, B:54:0x00f8, B:57:0x0127, B:60:0x0159, B:63:0x0167, B:64:0x016a, B:66:0x016f, B:68:0x0173, B:70:0x0180, B:73:0x0187, B:75:0x019a, B:77:0x019e, B:74:0x0198, B:78:0x01a2, B:80:0x01c1, B:82:0x01c5, B:83:0x01ce, B:85:0x01e7, B:86:0x01ff, B:88:0x0203, B:90:0x020d, B:91:0x0221, B:93:0x0225, B:94:0x0237, B:96:0x023b, B:97:0x0256, B:99:0x025a, B:101:0x0264, B:103:0x0271, B:104:0x0291, B:107:0x0297, B:109:0x029b, B:110:0x029e, B:112:0x02bc, B:114:0x02d9, B:116:0x02ea, B:117:0x02f3, B:118:0x034b, B:120:0x034f, B:122:0x0355, B:125:0x035a, B:126:0x03af, B:128:0x0411, B:130:0x041d, B:132:0x042b, B:134:0x0433, B:135:0x048d, B:129:0x0417, B:136:0x04b6, B:138:0x04ba, B:140:0x04c0, B:147:0x04cc, B:148:0x04d1, B:150:0x04da, B:153:0x04e0, B:154:0x04ea, B:155:0x04fb, B:157:0x04ff, B:159:0x0507, B:160:0x0521, B:178:0x05a2, B:180:0x05a6, B:182:0x05ae, B:184:0x05cf, B:185:0x05f8, B:187:0x05fe, B:188:0x0608, B:189:0x060d, B:191:0x0615, B:192:0x0638, B:194:0x063e, B:195:0x0648, B:196:0x0658, B:198:0x065d, B:161:0x0528, B:163:0x052c, B:166:0x0537, B:168:0x0540, B:170:0x054c, B:171:0x0566, B:172:0x0577, B:174:0x057d, B:176:0x058f, B:177:0x059d, B:167:0x053a, B:164:0x052f, B:200:0x066e, B:202:0x0672, B:11:0x002d, B:12:0x0035), top: B:233:0x0002 }] */
    /* JADX WARN: Code duplicated, block: B:167:0x053a A[Catch: Exception -> 0x0676, TryCatch #4 {Exception -> 0x0676, blocks: (B:3:0x0002, B:5:0x001a, B:7:0x0026, B:16:0x003d, B:18:0x0041, B:20:0x005b, B:22:0x005f, B:23:0x0068, B:24:0x0072, B:26:0x0078, B:29:0x008d, B:30:0x0099, B:32:0x009f, B:34:0x00ad, B:37:0x00bf, B:38:0x00c5, B:40:0x00ce, B:42:0x00d2, B:43:0x00da, B:45:0x00e0, B:47:0x00ea, B:49:0x00ee, B:52:0x00f4, B:54:0x00f8, B:57:0x0127, B:60:0x0159, B:63:0x0167, B:64:0x016a, B:66:0x016f, B:68:0x0173, B:70:0x0180, B:73:0x0187, B:75:0x019a, B:77:0x019e, B:74:0x0198, B:78:0x01a2, B:80:0x01c1, B:82:0x01c5, B:83:0x01ce, B:85:0x01e7, B:86:0x01ff, B:88:0x0203, B:90:0x020d, B:91:0x0221, B:93:0x0225, B:94:0x0237, B:96:0x023b, B:97:0x0256, B:99:0x025a, B:101:0x0264, B:103:0x0271, B:104:0x0291, B:107:0x0297, B:109:0x029b, B:110:0x029e, B:112:0x02bc, B:114:0x02d9, B:116:0x02ea, B:117:0x02f3, B:118:0x034b, B:120:0x034f, B:122:0x0355, B:125:0x035a, B:126:0x03af, B:128:0x0411, B:130:0x041d, B:132:0x042b, B:134:0x0433, B:135:0x048d, B:129:0x0417, B:136:0x04b6, B:138:0x04ba, B:140:0x04c0, B:147:0x04cc, B:148:0x04d1, B:150:0x04da, B:153:0x04e0, B:154:0x04ea, B:155:0x04fb, B:157:0x04ff, B:159:0x0507, B:160:0x0521, B:178:0x05a2, B:180:0x05a6, B:182:0x05ae, B:184:0x05cf, B:185:0x05f8, B:187:0x05fe, B:188:0x0608, B:189:0x060d, B:191:0x0615, B:192:0x0638, B:194:0x063e, B:195:0x0648, B:196:0x0658, B:198:0x065d, B:161:0x0528, B:163:0x052c, B:166:0x0537, B:168:0x0540, B:170:0x054c, B:171:0x0566, B:172:0x0577, B:174:0x057d, B:176:0x058f, B:177:0x059d, B:167:0x053a, B:164:0x052f, B:200:0x066e, B:202:0x0672, B:11:0x002d, B:12:0x0035), top: B:233:0x0002 }] */
    /* JADX WARN: Code duplicated, block: B:170:0x054c A[Catch: Exception -> 0x0676, TryCatch #4 {Exception -> 0x0676, blocks: (B:3:0x0002, B:5:0x001a, B:7:0x0026, B:16:0x003d, B:18:0x0041, B:20:0x005b, B:22:0x005f, B:23:0x0068, B:24:0x0072, B:26:0x0078, B:29:0x008d, B:30:0x0099, B:32:0x009f, B:34:0x00ad, B:37:0x00bf, B:38:0x00c5, B:40:0x00ce, B:42:0x00d2, B:43:0x00da, B:45:0x00e0, B:47:0x00ea, B:49:0x00ee, B:52:0x00f4, B:54:0x00f8, B:57:0x0127, B:60:0x0159, B:63:0x0167, B:64:0x016a, B:66:0x016f, B:68:0x0173, B:70:0x0180, B:73:0x0187, B:75:0x019a, B:77:0x019e, B:74:0x0198, B:78:0x01a2, B:80:0x01c1, B:82:0x01c5, B:83:0x01ce, B:85:0x01e7, B:86:0x01ff, B:88:0x0203, B:90:0x020d, B:91:0x0221, B:93:0x0225, B:94:0x0237, B:96:0x023b, B:97:0x0256, B:99:0x025a, B:101:0x0264, B:103:0x0271, B:104:0x0291, B:107:0x0297, B:109:0x029b, B:110:0x029e, B:112:0x02bc, B:114:0x02d9, B:116:0x02ea, B:117:0x02f3, B:118:0x034b, B:120:0x034f, B:122:0x0355, B:125:0x035a, B:126:0x03af, B:128:0x0411, B:130:0x041d, B:132:0x042b, B:134:0x0433, B:135:0x048d, B:129:0x0417, B:136:0x04b6, B:138:0x04ba, B:140:0x04c0, B:147:0x04cc, B:148:0x04d1, B:150:0x04da, B:153:0x04e0, B:154:0x04ea, B:155:0x04fb, B:157:0x04ff, B:159:0x0507, B:160:0x0521, B:178:0x05a2, B:180:0x05a6, B:182:0x05ae, B:184:0x05cf, B:185:0x05f8, B:187:0x05fe, B:188:0x0608, B:189:0x060d, B:191:0x0615, B:192:0x0638, B:194:0x063e, B:195:0x0648, B:196:0x0658, B:198:0x065d, B:161:0x0528, B:163:0x052c, B:166:0x0537, B:168:0x0540, B:170:0x054c, B:171:0x0566, B:172:0x0577, B:174:0x057d, B:176:0x058f, B:177:0x059d, B:167:0x053a, B:164:0x052f, B:200:0x066e, B:202:0x0672, B:11:0x002d, B:12:0x0035), top: B:233:0x0002 }] */
    /* JADX WARN: Code duplicated, block: B:174:0x057d A[Catch: Exception -> 0x0676, TryCatch #4 {Exception -> 0x0676, blocks: (B:3:0x0002, B:5:0x001a, B:7:0x0026, B:16:0x003d, B:18:0x0041, B:20:0x005b, B:22:0x005f, B:23:0x0068, B:24:0x0072, B:26:0x0078, B:29:0x008d, B:30:0x0099, B:32:0x009f, B:34:0x00ad, B:37:0x00bf, B:38:0x00c5, B:40:0x00ce, B:42:0x00d2, B:43:0x00da, B:45:0x00e0, B:47:0x00ea, B:49:0x00ee, B:52:0x00f4, B:54:0x00f8, B:57:0x0127, B:60:0x0159, B:63:0x0167, B:64:0x016a, B:66:0x016f, B:68:0x0173, B:70:0x0180, B:73:0x0187, B:75:0x019a, B:77:0x019e, B:74:0x0198, B:78:0x01a2, B:80:0x01c1, B:82:0x01c5, B:83:0x01ce, B:85:0x01e7, B:86:0x01ff, B:88:0x0203, B:90:0x020d, B:91:0x0221, B:93:0x0225, B:94:0x0237, B:96:0x023b, B:97:0x0256, B:99:0x025a, B:101:0x0264, B:103:0x0271, B:104:0x0291, B:107:0x0297, B:109:0x029b, B:110:0x029e, B:112:0x02bc, B:114:0x02d9, B:116:0x02ea, B:117:0x02f3, B:118:0x034b, B:120:0x034f, B:122:0x0355, B:125:0x035a, B:126:0x03af, B:128:0x0411, B:130:0x041d, B:132:0x042b, B:134:0x0433, B:135:0x048d, B:129:0x0417, B:136:0x04b6, B:138:0x04ba, B:140:0x04c0, B:147:0x04cc, B:148:0x04d1, B:150:0x04da, B:153:0x04e0, B:154:0x04ea, B:155:0x04fb, B:157:0x04ff, B:159:0x0507, B:160:0x0521, B:178:0x05a2, B:180:0x05a6, B:182:0x05ae, B:184:0x05cf, B:185:0x05f8, B:187:0x05fe, B:188:0x0608, B:189:0x060d, B:191:0x0615, B:192:0x0638, B:194:0x063e, B:195:0x0648, B:196:0x0658, B:198:0x065d, B:161:0x0528, B:163:0x052c, B:166:0x0537, B:168:0x0540, B:170:0x054c, B:171:0x0566, B:172:0x0577, B:174:0x057d, B:176:0x058f, B:177:0x059d, B:167:0x053a, B:164:0x052f, B:200:0x066e, B:202:0x0672, B:11:0x002d, B:12:0x0035), top: B:233:0x0002 }] */
    /* JADX WARN: Code duplicated, block: B:184:0x05cf A[Catch: Exception -> 0x0676, TryCatch #4 {Exception -> 0x0676, blocks: (B:3:0x0002, B:5:0x001a, B:7:0x0026, B:16:0x003d, B:18:0x0041, B:20:0x005b, B:22:0x005f, B:23:0x0068, B:24:0x0072, B:26:0x0078, B:29:0x008d, B:30:0x0099, B:32:0x009f, B:34:0x00ad, B:37:0x00bf, B:38:0x00c5, B:40:0x00ce, B:42:0x00d2, B:43:0x00da, B:45:0x00e0, B:47:0x00ea, B:49:0x00ee, B:52:0x00f4, B:54:0x00f8, B:57:0x0127, B:60:0x0159, B:63:0x0167, B:64:0x016a, B:66:0x016f, B:68:0x0173, B:70:0x0180, B:73:0x0187, B:75:0x019a, B:77:0x019e, B:74:0x0198, B:78:0x01a2, B:80:0x01c1, B:82:0x01c5, B:83:0x01ce, B:85:0x01e7, B:86:0x01ff, B:88:0x0203, B:90:0x020d, B:91:0x0221, B:93:0x0225, B:94:0x0237, B:96:0x023b, B:97:0x0256, B:99:0x025a, B:101:0x0264, B:103:0x0271, B:104:0x0291, B:107:0x0297, B:109:0x029b, B:110:0x029e, B:112:0x02bc, B:114:0x02d9, B:116:0x02ea, B:117:0x02f3, B:118:0x034b, B:120:0x034f, B:122:0x0355, B:125:0x035a, B:126:0x03af, B:128:0x0411, B:130:0x041d, B:132:0x042b, B:134:0x0433, B:135:0x048d, B:129:0x0417, B:136:0x04b6, B:138:0x04ba, B:140:0x04c0, B:147:0x04cc, B:148:0x04d1, B:150:0x04da, B:153:0x04e0, B:154:0x04ea, B:155:0x04fb, B:157:0x04ff, B:159:0x0507, B:160:0x0521, B:178:0x05a2, B:180:0x05a6, B:182:0x05ae, B:184:0x05cf, B:185:0x05f8, B:187:0x05fe, B:188:0x0608, B:189:0x060d, B:191:0x0615, B:192:0x0638, B:194:0x063e, B:195:0x0648, B:196:0x0658, B:198:0x065d, B:161:0x0528, B:163:0x052c, B:166:0x0537, B:168:0x0540, B:170:0x054c, B:171:0x0566, B:172:0x0577, B:174:0x057d, B:176:0x058f, B:177:0x059d, B:167:0x053a, B:164:0x052f, B:200:0x066e, B:202:0x0672, B:11:0x002d, B:12:0x0035), top: B:233:0x0002 }] */
    /* JADX WARN: Code duplicated, block: B:187:0x05fe A[Catch: Exception -> 0x0676, LOOP:4: B:185:0x05f8->B:187:0x05fe, LOOP_END, TryCatch #4 {Exception -> 0x0676, blocks: (B:3:0x0002, B:5:0x001a, B:7:0x0026, B:16:0x003d, B:18:0x0041, B:20:0x005b, B:22:0x005f, B:23:0x0068, B:24:0x0072, B:26:0x0078, B:29:0x008d, B:30:0x0099, B:32:0x009f, B:34:0x00ad, B:37:0x00bf, B:38:0x00c5, B:40:0x00ce, B:42:0x00d2, B:43:0x00da, B:45:0x00e0, B:47:0x00ea, B:49:0x00ee, B:52:0x00f4, B:54:0x00f8, B:57:0x0127, B:60:0x0159, B:63:0x0167, B:64:0x016a, B:66:0x016f, B:68:0x0173, B:70:0x0180, B:73:0x0187, B:75:0x019a, B:77:0x019e, B:74:0x0198, B:78:0x01a2, B:80:0x01c1, B:82:0x01c5, B:83:0x01ce, B:85:0x01e7, B:86:0x01ff, B:88:0x0203, B:90:0x020d, B:91:0x0221, B:93:0x0225, B:94:0x0237, B:96:0x023b, B:97:0x0256, B:99:0x025a, B:101:0x0264, B:103:0x0271, B:104:0x0291, B:107:0x0297, B:109:0x029b, B:110:0x029e, B:112:0x02bc, B:114:0x02d9, B:116:0x02ea, B:117:0x02f3, B:118:0x034b, B:120:0x034f, B:122:0x0355, B:125:0x035a, B:126:0x03af, B:128:0x0411, B:130:0x041d, B:132:0x042b, B:134:0x0433, B:135:0x048d, B:129:0x0417, B:136:0x04b6, B:138:0x04ba, B:140:0x04c0, B:147:0x04cc, B:148:0x04d1, B:150:0x04da, B:153:0x04e0, B:154:0x04ea, B:155:0x04fb, B:157:0x04ff, B:159:0x0507, B:160:0x0521, B:178:0x05a2, B:180:0x05a6, B:182:0x05ae, B:184:0x05cf, B:185:0x05f8, B:187:0x05fe, B:188:0x0608, B:189:0x060d, B:191:0x0615, B:192:0x0638, B:194:0x063e, B:195:0x0648, B:196:0x0658, B:198:0x065d, B:161:0x0528, B:163:0x052c, B:166:0x0537, B:168:0x0540, B:170:0x054c, B:171:0x0566, B:172:0x0577, B:174:0x057d, B:176:0x058f, B:177:0x059d, B:167:0x053a, B:164:0x052f, B:200:0x066e, B:202:0x0672, B:11:0x002d, B:12:0x0035), top: B:233:0x0002 }] */
    /* JADX WARN: Code duplicated, block: B:191:0x0615 A[Catch: Exception -> 0x0676, TryCatch #4 {Exception -> 0x0676, blocks: (B:3:0x0002, B:5:0x001a, B:7:0x0026, B:16:0x003d, B:18:0x0041, B:20:0x005b, B:22:0x005f, B:23:0x0068, B:24:0x0072, B:26:0x0078, B:29:0x008d, B:30:0x0099, B:32:0x009f, B:34:0x00ad, B:37:0x00bf, B:38:0x00c5, B:40:0x00ce, B:42:0x00d2, B:43:0x00da, B:45:0x00e0, B:47:0x00ea, B:49:0x00ee, B:52:0x00f4, B:54:0x00f8, B:57:0x0127, B:60:0x0159, B:63:0x0167, B:64:0x016a, B:66:0x016f, B:68:0x0173, B:70:0x0180, B:73:0x0187, B:75:0x019a, B:77:0x019e, B:74:0x0198, B:78:0x01a2, B:80:0x01c1, B:82:0x01c5, B:83:0x01ce, B:85:0x01e7, B:86:0x01ff, B:88:0x0203, B:90:0x020d, B:91:0x0221, B:93:0x0225, B:94:0x0237, B:96:0x023b, B:97:0x0256, B:99:0x025a, B:101:0x0264, B:103:0x0271, B:104:0x0291, B:107:0x0297, B:109:0x029b, B:110:0x029e, B:112:0x02bc, B:114:0x02d9, B:116:0x02ea, B:117:0x02f3, B:118:0x034b, B:120:0x034f, B:122:0x0355, B:125:0x035a, B:126:0x03af, B:128:0x0411, B:130:0x041d, B:132:0x042b, B:134:0x0433, B:135:0x048d, B:129:0x0417, B:136:0x04b6, B:138:0x04ba, B:140:0x04c0, B:147:0x04cc, B:148:0x04d1, B:150:0x04da, B:153:0x04e0, B:154:0x04ea, B:155:0x04fb, B:157:0x04ff, B:159:0x0507, B:160:0x0521, B:178:0x05a2, B:180:0x05a6, B:182:0x05ae, B:184:0x05cf, B:185:0x05f8, B:187:0x05fe, B:188:0x0608, B:189:0x060d, B:191:0x0615, B:192:0x0638, B:194:0x063e, B:195:0x0648, B:196:0x0658, B:198:0x065d, B:161:0x0528, B:163:0x052c, B:166:0x0537, B:168:0x0540, B:170:0x054c, B:171:0x0566, B:172:0x0577, B:174:0x057d, B:176:0x058f, B:177:0x059d, B:167:0x053a, B:164:0x052f, B:200:0x066e, B:202:0x0672, B:11:0x002d, B:12:0x0035), top: B:233:0x0002 }] */
    /* JADX WARN: Code duplicated, block: B:194:0x063e A[Catch: Exception -> 0x0676, LOOP:5: B:192:0x0638->B:194:0x063e, LOOP_END, TryCatch #4 {Exception -> 0x0676, blocks: (B:3:0x0002, B:5:0x001a, B:7:0x0026, B:16:0x003d, B:18:0x0041, B:20:0x005b, B:22:0x005f, B:23:0x0068, B:24:0x0072, B:26:0x0078, B:29:0x008d, B:30:0x0099, B:32:0x009f, B:34:0x00ad, B:37:0x00bf, B:38:0x00c5, B:40:0x00ce, B:42:0x00d2, B:43:0x00da, B:45:0x00e0, B:47:0x00ea, B:49:0x00ee, B:52:0x00f4, B:54:0x00f8, B:57:0x0127, B:60:0x0159, B:63:0x0167, B:64:0x016a, B:66:0x016f, B:68:0x0173, B:70:0x0180, B:73:0x0187, B:75:0x019a, B:77:0x019e, B:74:0x0198, B:78:0x01a2, B:80:0x01c1, B:82:0x01c5, B:83:0x01ce, B:85:0x01e7, B:86:0x01ff, B:88:0x0203, B:90:0x020d, B:91:0x0221, B:93:0x0225, B:94:0x0237, B:96:0x023b, B:97:0x0256, B:99:0x025a, B:101:0x0264, B:103:0x0271, B:104:0x0291, B:107:0x0297, B:109:0x029b, B:110:0x029e, B:112:0x02bc, B:114:0x02d9, B:116:0x02ea, B:117:0x02f3, B:118:0x034b, B:120:0x034f, B:122:0x0355, B:125:0x035a, B:126:0x03af, B:128:0x0411, B:130:0x041d, B:132:0x042b, B:134:0x0433, B:135:0x048d, B:129:0x0417, B:136:0x04b6, B:138:0x04ba, B:140:0x04c0, B:147:0x04cc, B:148:0x04d1, B:150:0x04da, B:153:0x04e0, B:154:0x04ea, B:155:0x04fb, B:157:0x04ff, B:159:0x0507, B:160:0x0521, B:178:0x05a2, B:180:0x05a6, B:182:0x05ae, B:184:0x05cf, B:185:0x05f8, B:187:0x05fe, B:188:0x0608, B:189:0x060d, B:191:0x0615, B:192:0x0638, B:194:0x063e, B:195:0x0648, B:196:0x0658, B:198:0x065d, B:161:0x0528, B:163:0x052c, B:166:0x0537, B:168:0x0540, B:170:0x054c, B:171:0x0566, B:172:0x0577, B:174:0x057d, B:176:0x058f, B:177:0x059d, B:167:0x053a, B:164:0x052f, B:200:0x066e, B:202:0x0672, B:11:0x002d, B:12:0x0035), top: B:233:0x0002 }] */
    /* JADX WARN: Code duplicated, block: B:198:0x065d A[Catch: Exception -> 0x0676, TryCatch #4 {Exception -> 0x0676, blocks: (B:3:0x0002, B:5:0x001a, B:7:0x0026, B:16:0x003d, B:18:0x0041, B:20:0x005b, B:22:0x005f, B:23:0x0068, B:24:0x0072, B:26:0x0078, B:29:0x008d, B:30:0x0099, B:32:0x009f, B:34:0x00ad, B:37:0x00bf, B:38:0x00c5, B:40:0x00ce, B:42:0x00d2, B:43:0x00da, B:45:0x00e0, B:47:0x00ea, B:49:0x00ee, B:52:0x00f4, B:54:0x00f8, B:57:0x0127, B:60:0x0159, B:63:0x0167, B:64:0x016a, B:66:0x016f, B:68:0x0173, B:70:0x0180, B:73:0x0187, B:75:0x019a, B:77:0x019e, B:74:0x0198, B:78:0x01a2, B:80:0x01c1, B:82:0x01c5, B:83:0x01ce, B:85:0x01e7, B:86:0x01ff, B:88:0x0203, B:90:0x020d, B:91:0x0221, B:93:0x0225, B:94:0x0237, B:96:0x023b, B:97:0x0256, B:99:0x025a, B:101:0x0264, B:103:0x0271, B:104:0x0291, B:107:0x0297, B:109:0x029b, B:110:0x029e, B:112:0x02bc, B:114:0x02d9, B:116:0x02ea, B:117:0x02f3, B:118:0x034b, B:120:0x034f, B:122:0x0355, B:125:0x035a, B:126:0x03af, B:128:0x0411, B:130:0x041d, B:132:0x042b, B:134:0x0433, B:135:0x048d, B:129:0x0417, B:136:0x04b6, B:138:0x04ba, B:140:0x04c0, B:147:0x04cc, B:148:0x04d1, B:150:0x04da, B:153:0x04e0, B:154:0x04ea, B:155:0x04fb, B:157:0x04ff, B:159:0x0507, B:160:0x0521, B:178:0x05a2, B:180:0x05a6, B:182:0x05ae, B:184:0x05cf, B:185:0x05f8, B:187:0x05fe, B:188:0x0608, B:189:0x060d, B:191:0x0615, B:192:0x0638, B:194:0x063e, B:195:0x0648, B:196:0x0658, B:198:0x065d, B:161:0x0528, B:163:0x052c, B:166:0x0537, B:168:0x0540, B:170:0x054c, B:171:0x0566, B:172:0x0577, B:174:0x057d, B:176:0x058f, B:177:0x059d, B:167:0x053a, B:164:0x052f, B:200:0x066e, B:202:0x0672, B:11:0x002d, B:12:0x0035), top: B:233:0x0002 }] */
    /* JADX WARN: Code duplicated, block: B:239:0x008d A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:240:0x008c A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:241:0x00bf A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:259:0x016a A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:263:0x058f A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:265:0x0577 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:267:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:26:0x0078 A[Catch: Exception -> 0x0676, TryCatch #4 {Exception -> 0x0676, blocks: (B:3:0x0002, B:5:0x001a, B:7:0x0026, B:16:0x003d, B:18:0x0041, B:20:0x005b, B:22:0x005f, B:23:0x0068, B:24:0x0072, B:26:0x0078, B:29:0x008d, B:30:0x0099, B:32:0x009f, B:34:0x00ad, B:37:0x00bf, B:38:0x00c5, B:40:0x00ce, B:42:0x00d2, B:43:0x00da, B:45:0x00e0, B:47:0x00ea, B:49:0x00ee, B:52:0x00f4, B:54:0x00f8, B:57:0x0127, B:60:0x0159, B:63:0x0167, B:64:0x016a, B:66:0x016f, B:68:0x0173, B:70:0x0180, B:73:0x0187, B:75:0x019a, B:77:0x019e, B:74:0x0198, B:78:0x01a2, B:80:0x01c1, B:82:0x01c5, B:83:0x01ce, B:85:0x01e7, B:86:0x01ff, B:88:0x0203, B:90:0x020d, B:91:0x0221, B:93:0x0225, B:94:0x0237, B:96:0x023b, B:97:0x0256, B:99:0x025a, B:101:0x0264, B:103:0x0271, B:104:0x0291, B:107:0x0297, B:109:0x029b, B:110:0x029e, B:112:0x02bc, B:114:0x02d9, B:116:0x02ea, B:117:0x02f3, B:118:0x034b, B:120:0x034f, B:122:0x0355, B:125:0x035a, B:126:0x03af, B:128:0x0411, B:130:0x041d, B:132:0x042b, B:134:0x0433, B:135:0x048d, B:129:0x0417, B:136:0x04b6, B:138:0x04ba, B:140:0x04c0, B:147:0x04cc, B:148:0x04d1, B:150:0x04da, B:153:0x04e0, B:154:0x04ea, B:155:0x04fb, B:157:0x04ff, B:159:0x0507, B:160:0x0521, B:178:0x05a2, B:180:0x05a6, B:182:0x05ae, B:184:0x05cf, B:185:0x05f8, B:187:0x05fe, B:188:0x0608, B:189:0x060d, B:191:0x0615, B:192:0x0638, B:194:0x063e, B:195:0x0648, B:196:0x0658, B:198:0x065d, B:161:0x0528, B:163:0x052c, B:166:0x0537, B:168:0x0540, B:170:0x054c, B:171:0x0566, B:172:0x0577, B:174:0x057d, B:176:0x058f, B:177:0x059d, B:167:0x053a, B:164:0x052f, B:200:0x066e, B:202:0x0672, B:11:0x002d, B:12:0x0035), top: B:233:0x0002 }] */
    /* JADX WARN: Code duplicated, block: B:32:0x009f A[Catch: Exception -> 0x0676, TryCatch #4 {Exception -> 0x0676, blocks: (B:3:0x0002, B:5:0x001a, B:7:0x0026, B:16:0x003d, B:18:0x0041, B:20:0x005b, B:22:0x005f, B:23:0x0068, B:24:0x0072, B:26:0x0078, B:29:0x008d, B:30:0x0099, B:32:0x009f, B:34:0x00ad, B:37:0x00bf, B:38:0x00c5, B:40:0x00ce, B:42:0x00d2, B:43:0x00da, B:45:0x00e0, B:47:0x00ea, B:49:0x00ee, B:52:0x00f4, B:54:0x00f8, B:57:0x0127, B:60:0x0159, B:63:0x0167, B:64:0x016a, B:66:0x016f, B:68:0x0173, B:70:0x0180, B:73:0x0187, B:75:0x019a, B:77:0x019e, B:74:0x0198, B:78:0x01a2, B:80:0x01c1, B:82:0x01c5, B:83:0x01ce, B:85:0x01e7, B:86:0x01ff, B:88:0x0203, B:90:0x020d, B:91:0x0221, B:93:0x0225, B:94:0x0237, B:96:0x023b, B:97:0x0256, B:99:0x025a, B:101:0x0264, B:103:0x0271, B:104:0x0291, B:107:0x0297, B:109:0x029b, B:110:0x029e, B:112:0x02bc, B:114:0x02d9, B:116:0x02ea, B:117:0x02f3, B:118:0x034b, B:120:0x034f, B:122:0x0355, B:125:0x035a, B:126:0x03af, B:128:0x0411, B:130:0x041d, B:132:0x042b, B:134:0x0433, B:135:0x048d, B:129:0x0417, B:136:0x04b6, B:138:0x04ba, B:140:0x04c0, B:147:0x04cc, B:148:0x04d1, B:150:0x04da, B:153:0x04e0, B:154:0x04ea, B:155:0x04fb, B:157:0x04ff, B:159:0x0507, B:160:0x0521, B:178:0x05a2, B:180:0x05a6, B:182:0x05ae, B:184:0x05cf, B:185:0x05f8, B:187:0x05fe, B:188:0x0608, B:189:0x060d, B:191:0x0615, B:192:0x0638, B:194:0x063e, B:195:0x0648, B:196:0x0658, B:198:0x065d, B:161:0x0528, B:163:0x052c, B:166:0x0537, B:168:0x0540, B:170:0x054c, B:171:0x0566, B:172:0x0577, B:174:0x057d, B:176:0x058f, B:177:0x059d, B:167:0x053a, B:164:0x052f, B:200:0x066e, B:202:0x0672, B:11:0x002d, B:12:0x0035), top: B:233:0x0002 }] */
    /* JADX WARN: Code duplicated, block: B:50:0x00f1 A[EDGE_INSN: B:50:0x00f1->B:51:0x00f2 BREAK  A[LOOP:2: B:43:0x00da->B:254:0x00da]] */
    /* JADX WARN: Code duplicated, block: B:57:0x0127 A[Catch: Exception -> 0x0676, TryCatch #4 {Exception -> 0x0676, blocks: (B:3:0x0002, B:5:0x001a, B:7:0x0026, B:16:0x003d, B:18:0x0041, B:20:0x005b, B:22:0x005f, B:23:0x0068, B:24:0x0072, B:26:0x0078, B:29:0x008d, B:30:0x0099, B:32:0x009f, B:34:0x00ad, B:37:0x00bf, B:38:0x00c5, B:40:0x00ce, B:42:0x00d2, B:43:0x00da, B:45:0x00e0, B:47:0x00ea, B:49:0x00ee, B:52:0x00f4, B:54:0x00f8, B:57:0x0127, B:60:0x0159, B:63:0x0167, B:64:0x016a, B:66:0x016f, B:68:0x0173, B:70:0x0180, B:73:0x0187, B:75:0x019a, B:77:0x019e, B:74:0x0198, B:78:0x01a2, B:80:0x01c1, B:82:0x01c5, B:83:0x01ce, B:85:0x01e7, B:86:0x01ff, B:88:0x0203, B:90:0x020d, B:91:0x0221, B:93:0x0225, B:94:0x0237, B:96:0x023b, B:97:0x0256, B:99:0x025a, B:101:0x0264, B:103:0x0271, B:104:0x0291, B:107:0x0297, B:109:0x029b, B:110:0x029e, B:112:0x02bc, B:114:0x02d9, B:116:0x02ea, B:117:0x02f3, B:118:0x034b, B:120:0x034f, B:122:0x0355, B:125:0x035a, B:126:0x03af, B:128:0x0411, B:130:0x041d, B:132:0x042b, B:134:0x0433, B:135:0x048d, B:129:0x0417, B:136:0x04b6, B:138:0x04ba, B:140:0x04c0, B:147:0x04cc, B:148:0x04d1, B:150:0x04da, B:153:0x04e0, B:154:0x04ea, B:155:0x04fb, B:157:0x04ff, B:159:0x0507, B:160:0x0521, B:178:0x05a2, B:180:0x05a6, B:182:0x05ae, B:184:0x05cf, B:185:0x05f8, B:187:0x05fe, B:188:0x0608, B:189:0x060d, B:191:0x0615, B:192:0x0638, B:194:0x063e, B:195:0x0648, B:196:0x0658, B:198:0x065d, B:161:0x0528, B:163:0x052c, B:166:0x0537, B:168:0x0540, B:170:0x054c, B:171:0x0566, B:172:0x0577, B:174:0x057d, B:176:0x058f, B:177:0x059d, B:167:0x053a, B:164:0x052f, B:200:0x066e, B:202:0x0672, B:11:0x002d, B:12:0x0035), top: B:233:0x0002 }] */
    /* JADX WARN: Code duplicated, block: B:63:0x0167 A[Catch: Exception -> 0x0676, TryCatch #4 {Exception -> 0x0676, blocks: (B:3:0x0002, B:5:0x001a, B:7:0x0026, B:16:0x003d, B:18:0x0041, B:20:0x005b, B:22:0x005f, B:23:0x0068, B:24:0x0072, B:26:0x0078, B:29:0x008d, B:30:0x0099, B:32:0x009f, B:34:0x00ad, B:37:0x00bf, B:38:0x00c5, B:40:0x00ce, B:42:0x00d2, B:43:0x00da, B:45:0x00e0, B:47:0x00ea, B:49:0x00ee, B:52:0x00f4, B:54:0x00f8, B:57:0x0127, B:60:0x0159, B:63:0x0167, B:64:0x016a, B:66:0x016f, B:68:0x0173, B:70:0x0180, B:73:0x0187, B:75:0x019a, B:77:0x019e, B:74:0x0198, B:78:0x01a2, B:80:0x01c1, B:82:0x01c5, B:83:0x01ce, B:85:0x01e7, B:86:0x01ff, B:88:0x0203, B:90:0x020d, B:91:0x0221, B:93:0x0225, B:94:0x0237, B:96:0x023b, B:97:0x0256, B:99:0x025a, B:101:0x0264, B:103:0x0271, B:104:0x0291, B:107:0x0297, B:109:0x029b, B:110:0x029e, B:112:0x02bc, B:114:0x02d9, B:116:0x02ea, B:117:0x02f3, B:118:0x034b, B:120:0x034f, B:122:0x0355, B:125:0x035a, B:126:0x03af, B:128:0x0411, B:130:0x041d, B:132:0x042b, B:134:0x0433, B:135:0x048d, B:129:0x0417, B:136:0x04b6, B:138:0x04ba, B:140:0x04c0, B:147:0x04cc, B:148:0x04d1, B:150:0x04da, B:153:0x04e0, B:154:0x04ea, B:155:0x04fb, B:157:0x04ff, B:159:0x0507, B:160:0x0521, B:178:0x05a2, B:180:0x05a6, B:182:0x05ae, B:184:0x05cf, B:185:0x05f8, B:187:0x05fe, B:188:0x0608, B:189:0x060d, B:191:0x0615, B:192:0x0638, B:194:0x063e, B:195:0x0648, B:196:0x0658, B:198:0x065d, B:161:0x0528, B:163:0x052c, B:166:0x0537, B:168:0x0540, B:170:0x054c, B:171:0x0566, B:172:0x0577, B:174:0x057d, B:176:0x058f, B:177:0x059d, B:167:0x053a, B:164:0x052f, B:200:0x066e, B:202:0x0672, B:11:0x002d, B:12:0x0035), top: B:233:0x0002 }] */
    /* JADX WARN: Code duplicated, block: B:66:0x016f A[Catch: Exception -> 0x0676, TryCatch #4 {Exception -> 0x0676, blocks: (B:3:0x0002, B:5:0x001a, B:7:0x0026, B:16:0x003d, B:18:0x0041, B:20:0x005b, B:22:0x005f, B:23:0x0068, B:24:0x0072, B:26:0x0078, B:29:0x008d, B:30:0x0099, B:32:0x009f, B:34:0x00ad, B:37:0x00bf, B:38:0x00c5, B:40:0x00ce, B:42:0x00d2, B:43:0x00da, B:45:0x00e0, B:47:0x00ea, B:49:0x00ee, B:52:0x00f4, B:54:0x00f8, B:57:0x0127, B:60:0x0159, B:63:0x0167, B:64:0x016a, B:66:0x016f, B:68:0x0173, B:70:0x0180, B:73:0x0187, B:75:0x019a, B:77:0x019e, B:74:0x0198, B:78:0x01a2, B:80:0x01c1, B:82:0x01c5, B:83:0x01ce, B:85:0x01e7, B:86:0x01ff, B:88:0x0203, B:90:0x020d, B:91:0x0221, B:93:0x0225, B:94:0x0237, B:96:0x023b, B:97:0x0256, B:99:0x025a, B:101:0x0264, B:103:0x0271, B:104:0x0291, B:107:0x0297, B:109:0x029b, B:110:0x029e, B:112:0x02bc, B:114:0x02d9, B:116:0x02ea, B:117:0x02f3, B:118:0x034b, B:120:0x034f, B:122:0x0355, B:125:0x035a, B:126:0x03af, B:128:0x0411, B:130:0x041d, B:132:0x042b, B:134:0x0433, B:135:0x048d, B:129:0x0417, B:136:0x04b6, B:138:0x04ba, B:140:0x04c0, B:147:0x04cc, B:148:0x04d1, B:150:0x04da, B:153:0x04e0, B:154:0x04ea, B:155:0x04fb, B:157:0x04ff, B:159:0x0507, B:160:0x0521, B:178:0x05a2, B:180:0x05a6, B:182:0x05ae, B:184:0x05cf, B:185:0x05f8, B:187:0x05fe, B:188:0x0608, B:189:0x060d, B:191:0x0615, B:192:0x0638, B:194:0x063e, B:195:0x0648, B:196:0x0658, B:198:0x065d, B:161:0x0528, B:163:0x052c, B:166:0x0537, B:168:0x0540, B:170:0x054c, B:171:0x0566, B:172:0x0577, B:174:0x057d, B:176:0x058f, B:177:0x059d, B:167:0x053a, B:164:0x052f, B:200:0x066e, B:202:0x0672, B:11:0x002d, B:12:0x0035), top: B:233:0x0002 }] */
    /* JADX WARN: Code duplicated, block: B:67:0x0172  */
    /* JADX WARN: Code duplicated, block: B:74:0x0198 A[Catch: Exception -> 0x0676, TryCatch #4 {Exception -> 0x0676, blocks: (B:3:0x0002, B:5:0x001a, B:7:0x0026, B:16:0x003d, B:18:0x0041, B:20:0x005b, B:22:0x005f, B:23:0x0068, B:24:0x0072, B:26:0x0078, B:29:0x008d, B:30:0x0099, B:32:0x009f, B:34:0x00ad, B:37:0x00bf, B:38:0x00c5, B:40:0x00ce, B:42:0x00d2, B:43:0x00da, B:45:0x00e0, B:47:0x00ea, B:49:0x00ee, B:52:0x00f4, B:54:0x00f8, B:57:0x0127, B:60:0x0159, B:63:0x0167, B:64:0x016a, B:66:0x016f, B:68:0x0173, B:70:0x0180, B:73:0x0187, B:75:0x019a, B:77:0x019e, B:74:0x0198, B:78:0x01a2, B:80:0x01c1, B:82:0x01c5, B:83:0x01ce, B:85:0x01e7, B:86:0x01ff, B:88:0x0203, B:90:0x020d, B:91:0x0221, B:93:0x0225, B:94:0x0237, B:96:0x023b, B:97:0x0256, B:99:0x025a, B:101:0x0264, B:103:0x0271, B:104:0x0291, B:107:0x0297, B:109:0x029b, B:110:0x029e, B:112:0x02bc, B:114:0x02d9, B:116:0x02ea, B:117:0x02f3, B:118:0x034b, B:120:0x034f, B:122:0x0355, B:125:0x035a, B:126:0x03af, B:128:0x0411, B:130:0x041d, B:132:0x042b, B:134:0x0433, B:135:0x048d, B:129:0x0417, B:136:0x04b6, B:138:0x04ba, B:140:0x04c0, B:147:0x04cc, B:148:0x04d1, B:150:0x04da, B:153:0x04e0, B:154:0x04ea, B:155:0x04fb, B:157:0x04ff, B:159:0x0507, B:160:0x0521, B:178:0x05a2, B:180:0x05a6, B:182:0x05ae, B:184:0x05cf, B:185:0x05f8, B:187:0x05fe, B:188:0x0608, B:189:0x060d, B:191:0x0615, B:192:0x0638, B:194:0x063e, B:195:0x0648, B:196:0x0658, B:198:0x065d, B:161:0x0528, B:163:0x052c, B:166:0x0537, B:168:0x0540, B:170:0x054c, B:171:0x0566, B:172:0x0577, B:174:0x057d, B:176:0x058f, B:177:0x059d, B:167:0x053a, B:164:0x052f, B:200:0x066e, B:202:0x0672, B:11:0x002d, B:12:0x0035), top: B:233:0x0002 }] */
    /* JADX WARN: Code duplicated, block: B:77:0x019e A[Catch: Exception -> 0x0676, TryCatch #4 {Exception -> 0x0676, blocks: (B:3:0x0002, B:5:0x001a, B:7:0x0026, B:16:0x003d, B:18:0x0041, B:20:0x005b, B:22:0x005f, B:23:0x0068, B:24:0x0072, B:26:0x0078, B:29:0x008d, B:30:0x0099, B:32:0x009f, B:34:0x00ad, B:37:0x00bf, B:38:0x00c5, B:40:0x00ce, B:42:0x00d2, B:43:0x00da, B:45:0x00e0, B:47:0x00ea, B:49:0x00ee, B:52:0x00f4, B:54:0x00f8, B:57:0x0127, B:60:0x0159, B:63:0x0167, B:64:0x016a, B:66:0x016f, B:68:0x0173, B:70:0x0180, B:73:0x0187, B:75:0x019a, B:77:0x019e, B:74:0x0198, B:78:0x01a2, B:80:0x01c1, B:82:0x01c5, B:83:0x01ce, B:85:0x01e7, B:86:0x01ff, B:88:0x0203, B:90:0x020d, B:91:0x0221, B:93:0x0225, B:94:0x0237, B:96:0x023b, B:97:0x0256, B:99:0x025a, B:101:0x0264, B:103:0x0271, B:104:0x0291, B:107:0x0297, B:109:0x029b, B:110:0x029e, B:112:0x02bc, B:114:0x02d9, B:116:0x02ea, B:117:0x02f3, B:118:0x034b, B:120:0x034f, B:122:0x0355, B:125:0x035a, B:126:0x03af, B:128:0x0411, B:130:0x041d, B:132:0x042b, B:134:0x0433, B:135:0x048d, B:129:0x0417, B:136:0x04b6, B:138:0x04ba, B:140:0x04c0, B:147:0x04cc, B:148:0x04d1, B:150:0x04da, B:153:0x04e0, B:154:0x04ea, B:155:0x04fb, B:157:0x04ff, B:159:0x0507, B:160:0x0521, B:178:0x05a2, B:180:0x05a6, B:182:0x05ae, B:184:0x05cf, B:185:0x05f8, B:187:0x05fe, B:188:0x0608, B:189:0x060d, B:191:0x0615, B:192:0x0638, B:194:0x063e, B:195:0x0648, B:196:0x0658, B:198:0x065d, B:161:0x0528, B:163:0x052c, B:166:0x0537, B:168:0x0540, B:170:0x054c, B:171:0x0566, B:172:0x0577, B:174:0x057d, B:176:0x058f, B:177:0x059d, B:167:0x053a, B:164:0x052f, B:200:0x066e, B:202:0x0672, B:11:0x002d, B:12:0x0035), top: B:233:0x0002 }] */
    /* JADX WARN: Code duplicated, block: B:85:0x01e7 A[Catch: Exception -> 0x0676, TryCatch #4 {Exception -> 0x0676, blocks: (B:3:0x0002, B:5:0x001a, B:7:0x0026, B:16:0x003d, B:18:0x0041, B:20:0x005b, B:22:0x005f, B:23:0x0068, B:24:0x0072, B:26:0x0078, B:29:0x008d, B:30:0x0099, B:32:0x009f, B:34:0x00ad, B:37:0x00bf, B:38:0x00c5, B:40:0x00ce, B:42:0x00d2, B:43:0x00da, B:45:0x00e0, B:47:0x00ea, B:49:0x00ee, B:52:0x00f4, B:54:0x00f8, B:57:0x0127, B:60:0x0159, B:63:0x0167, B:64:0x016a, B:66:0x016f, B:68:0x0173, B:70:0x0180, B:73:0x0187, B:75:0x019a, B:77:0x019e, B:74:0x0198, B:78:0x01a2, B:80:0x01c1, B:82:0x01c5, B:83:0x01ce, B:85:0x01e7, B:86:0x01ff, B:88:0x0203, B:90:0x020d, B:91:0x0221, B:93:0x0225, B:94:0x0237, B:96:0x023b, B:97:0x0256, B:99:0x025a, B:101:0x0264, B:103:0x0271, B:104:0x0291, B:107:0x0297, B:109:0x029b, B:110:0x029e, B:112:0x02bc, B:114:0x02d9, B:116:0x02ea, B:117:0x02f3, B:118:0x034b, B:120:0x034f, B:122:0x0355, B:125:0x035a, B:126:0x03af, B:128:0x0411, B:130:0x041d, B:132:0x042b, B:134:0x0433, B:135:0x048d, B:129:0x0417, B:136:0x04b6, B:138:0x04ba, B:140:0x04c0, B:147:0x04cc, B:148:0x04d1, B:150:0x04da, B:153:0x04e0, B:154:0x04ea, B:155:0x04fb, B:157:0x04ff, B:159:0x0507, B:160:0x0521, B:178:0x05a2, B:180:0x05a6, B:182:0x05ae, B:184:0x05cf, B:185:0x05f8, B:187:0x05fe, B:188:0x0608, B:189:0x060d, B:191:0x0615, B:192:0x0638, B:194:0x063e, B:195:0x0648, B:196:0x0658, B:198:0x065d, B:161:0x0528, B:163:0x052c, B:166:0x0537, B:168:0x0540, B:170:0x054c, B:171:0x0566, B:172:0x0577, B:174:0x057d, B:176:0x058f, B:177:0x059d, B:167:0x053a, B:164:0x052f, B:200:0x066e, B:202:0x0672, B:11:0x002d, B:12:0x0035), top: B:233:0x0002 }] */
    /* JADX WARN: Code duplicated, block: B:93:0x0225 A[Catch: Exception -> 0x0676, TryCatch #4 {Exception -> 0x0676, blocks: (B:3:0x0002, B:5:0x001a, B:7:0x0026, B:16:0x003d, B:18:0x0041, B:20:0x005b, B:22:0x005f, B:23:0x0068, B:24:0x0072, B:26:0x0078, B:29:0x008d, B:30:0x0099, B:32:0x009f, B:34:0x00ad, B:37:0x00bf, B:38:0x00c5, B:40:0x00ce, B:42:0x00d2, B:43:0x00da, B:45:0x00e0, B:47:0x00ea, B:49:0x00ee, B:52:0x00f4, B:54:0x00f8, B:57:0x0127, B:60:0x0159, B:63:0x0167, B:64:0x016a, B:66:0x016f, B:68:0x0173, B:70:0x0180, B:73:0x0187, B:75:0x019a, B:77:0x019e, B:74:0x0198, B:78:0x01a2, B:80:0x01c1, B:82:0x01c5, B:83:0x01ce, B:85:0x01e7, B:86:0x01ff, B:88:0x0203, B:90:0x020d, B:91:0x0221, B:93:0x0225, B:94:0x0237, B:96:0x023b, B:97:0x0256, B:99:0x025a, B:101:0x0264, B:103:0x0271, B:104:0x0291, B:107:0x0297, B:109:0x029b, B:110:0x029e, B:112:0x02bc, B:114:0x02d9, B:116:0x02ea, B:117:0x02f3, B:118:0x034b, B:120:0x034f, B:122:0x0355, B:125:0x035a, B:126:0x03af, B:128:0x0411, B:130:0x041d, B:132:0x042b, B:134:0x0433, B:135:0x048d, B:129:0x0417, B:136:0x04b6, B:138:0x04ba, B:140:0x04c0, B:147:0x04cc, B:148:0x04d1, B:150:0x04da, B:153:0x04e0, B:154:0x04ea, B:155:0x04fb, B:157:0x04ff, B:159:0x0507, B:160:0x0521, B:178:0x05a2, B:180:0x05a6, B:182:0x05ae, B:184:0x05cf, B:185:0x05f8, B:187:0x05fe, B:188:0x0608, B:189:0x060d, B:191:0x0615, B:192:0x0638, B:194:0x063e, B:195:0x0648, B:196:0x0658, B:198:0x065d, B:161:0x0528, B:163:0x052c, B:166:0x0537, B:168:0x0540, B:170:0x054c, B:171:0x0566, B:172:0x0577, B:174:0x057d, B:176:0x058f, B:177:0x059d, B:167:0x053a, B:164:0x052f, B:200:0x066e, B:202:0x0672, B:11:0x002d, B:12:0x0035), top: B:233:0x0002 }] */
    /* JADX WARN: Code duplicated, block: B:96:0x023b A[Catch: Exception -> 0x0676, TryCatch #4 {Exception -> 0x0676, blocks: (B:3:0x0002, B:5:0x001a, B:7:0x0026, B:16:0x003d, B:18:0x0041, B:20:0x005b, B:22:0x005f, B:23:0x0068, B:24:0x0072, B:26:0x0078, B:29:0x008d, B:30:0x0099, B:32:0x009f, B:34:0x00ad, B:37:0x00bf, B:38:0x00c5, B:40:0x00ce, B:42:0x00d2, B:43:0x00da, B:45:0x00e0, B:47:0x00ea, B:49:0x00ee, B:52:0x00f4, B:54:0x00f8, B:57:0x0127, B:60:0x0159, B:63:0x0167, B:64:0x016a, B:66:0x016f, B:68:0x0173, B:70:0x0180, B:73:0x0187, B:75:0x019a, B:77:0x019e, B:74:0x0198, B:78:0x01a2, B:80:0x01c1, B:82:0x01c5, B:83:0x01ce, B:85:0x01e7, B:86:0x01ff, B:88:0x0203, B:90:0x020d, B:91:0x0221, B:93:0x0225, B:94:0x0237, B:96:0x023b, B:97:0x0256, B:99:0x025a, B:101:0x0264, B:103:0x0271, B:104:0x0291, B:107:0x0297, B:109:0x029b, B:110:0x029e, B:112:0x02bc, B:114:0x02d9, B:116:0x02ea, B:117:0x02f3, B:118:0x034b, B:120:0x034f, B:122:0x0355, B:125:0x035a, B:126:0x03af, B:128:0x0411, B:130:0x041d, B:132:0x042b, B:134:0x0433, B:135:0x048d, B:129:0x0417, B:136:0x04b6, B:138:0x04ba, B:140:0x04c0, B:147:0x04cc, B:148:0x04d1, B:150:0x04da, B:153:0x04e0, B:154:0x04ea, B:155:0x04fb, B:157:0x04ff, B:159:0x0507, B:160:0x0521, B:178:0x05a2, B:180:0x05a6, B:182:0x05ae, B:184:0x05cf, B:185:0x05f8, B:187:0x05fe, B:188:0x0608, B:189:0x060d, B:191:0x0615, B:192:0x0638, B:194:0x063e, B:195:0x0648, B:196:0x0658, B:198:0x065d, B:161:0x0528, B:163:0x052c, B:166:0x0537, B:168:0x0540, B:170:0x054c, B:171:0x0566, B:172:0x0577, B:174:0x057d, B:176:0x058f, B:177:0x059d, B:167:0x053a, B:164:0x052f, B:200:0x066e, B:202:0x0672, B:11:0x002d, B:12:0x0035), top: B:233:0x0002 }] */
    public void render() {
        VfxManager vfxManager;
        boolean z;
        boolean z2;
        Texture texture;
        int width;
        int height;
        Iterator<ParticleEffect> it;
        Iterator<ParticleEffect> it2;
        net.mgsx.gltf.scene3d.scene.SceneManager sceneManager;
        Texture texture2;
        float width2;
        float height2;
        float f;
        float f2;
        float f3;
        float f4;
        float f5;
        float f6;
        VfxManager vfxManager2;
        PostProcessingData.EyeAdaptation eyeAdaptation;
        ExposureEffect exposureEffect;
        PostProcessingComponent postProcessingComponent;
        PostProcessingData.DepthOfField depthOfField;
        SsrRayTracingEffect ssrRayTracingEffect;
        HeightFogEffect heightFogEffect;
        GodRaysEffect godRaysEffect;
        DirectionalLightEx directionalLightEx;
        net.mgsx.gltf.scene3d.scene.SceneManager sceneManager2;
        float f7;
        int i;
        int i2;
        float f8;
        float f9;
        DepthOfFieldEffect depthOfFieldEffect;
        int i3;
        int i4;
        int i5;
        float f10;
        ModelInstance value;
        Array.ArrayIterator<Material> it3;
        Material next;
        net.mgsx.gltf.scene3d.scene.SceneManager sceneManager3;
        try {
            float deltaTime = Gdx.graphics.getDeltaTime();
            updateParticles(deltaTime);
            updateParticles3D(deltaTime);
            this.camera.update();
            int i6 = this.targetFps;
            if (i6 > 0) {
                float f11 = this.renderTimeAccumulator + deltaTime;
                this.renderTimeAccumulator = f11;
                float f12 = 1.0f / i6;
                if (f11 >= f12 || this.lastRenderedTexture == null) {
                    this.renderTimeAccumulator = Math.max(0.0f, f11 - f12);
                } else {
                    z = false;
                }
                if (z && this.isDepthRenderEnabled) {
                    this.depthFbo.begin();
                    Gdx.gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
                    Gdx.gl.glClear(16640);
                    this.depthBatch.begin(this.camera);
                    if (this.realisticMode && (sceneManager3 = this.sceneManager) != null) {
                        this.depthBatch.render(sceneManager3.getRenderableProviders());
                    }
                    for (Map.Entry<String, ModelInstance> entry : this.sceneObjects.entrySet()) {
                        if (this.inactiveRenderObjects.contains(entry.getKey())) {
                            value = entry.getValue();
                            it3 = value.materials.iterator();
                            while (true) {
                                if (it3.hasNext()) {
                                    next = it3.next();
                                    if (!next.has(BlendingAttribute.Type) && ((BlendingAttribute) next.get(BlendingAttribute.Type)).opacity < 0.1f) {
                                        break;
                                    }
                                } else {
                                    this.depthBatch.render(value);
                                    break;
                                }
                            }
                        }
                    }
                    this.depthBatch.end();
                    postProcessingComponent = this.currentConfig;
                    if (postProcessingComponent != null || !postProcessingComponent.isActive) {
                        depthOfField = null;
                        break;
                    }
                    Iterator<PostProcessingData> it4 = this.currentConfig.effects.iterator();
                    while (true) {
                        if (!it4.hasNext()) {
                            depthOfField = null;
                            break;
                        }
                        PostProcessingData next2 = it4.next();
                        if ((next2 instanceof PostProcessingData.DepthOfField) && next2.isEnabled) {
                            depthOfField = (PostProcessingData.DepthOfField) next2;
                            break;
                        }
                    }
                    if (depthOfField != null && depthOfField.autoFocus) {
                        this.dofPixelsBuffer.clear();
                        Gdx.gl.glReadPixels(this.depthFbo.getWidth() / 2, this.depthFbo.getHeight() / 2, 2, 2, GL20.GL_RGBA, 5121, this.dofPixelsBuffer);
                        this.dofPixelsBuffer.rewind();
                        f7 = 0.0f;
                        i2 = 0;
                        for (i = 0; i < 4; i++) {
                            i3 = this.dofPixelsBuffer.get() & 255;
                            int i7 = this.dofPixelsBuffer.get() & 255;
                            i4 = this.dofPixelsBuffer.get() & 255;
                            i5 = this.dofPixelsBuffer.get() & 255;
                            f10 = (i3 / 255.0f) + (i7 / 65025.0f);
                            if (i3 == 128 && i4 != 128) {
                                f10 = (i4 / 255.0f) + (i5 / 65025.0f);
                            }
                            if (f10 > 1.0E-4f) {
                                f7 += f10;
                                i2++;
                            }
                        }
                        if (i2 > 0) {
                            f8 = f7 / i2;
                        } else {
                            f8 = 1.0f;
                        }
                        f9 = f8 * this.camera.far;
                        if (!Float.isNaN(depthOfField.focusDistance) || depthOfField.focusDistance <= 0.0f) {
                            depthOfField.focusDistance = f9;
                        } else {
                            depthOfField.focusDistance += (f9 - depthOfField.focusDistance) * Math.min(depthOfField.autoFocusSpeed * deltaTime, 1.0f);
                        }
                        depthOfFieldEffect = this.dofEffect;
                        if (depthOfFieldEffect != null) {
                            depthOfFieldEffect.focusDistance = depthOfField.focusDistance;
                        }
                    }
                    this.depthFbo.end();
                    this.materialFbo.begin();
                    Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
                    Gdx.gl.glClear(16640);
                    this.materialBatch.begin(this.camera);
                    if (this.realisticMode && (sceneManager2 = this.sceneManager) != null) {
                        this.materialBatch.render(sceneManager2.getRenderableProviders());
                    }
                    this.materialBatch.render(this.sceneObjects.values());
                    this.materialBatch.end();
                    this.materialFbo.end();
                    ssrRayTracingEffect = this.rayTracingEffect;
                    if (ssrRayTracingEffect != null) {
                        ssrRayTracingEffect.setDepthTexture(this.depthFbo.getColorBufferTexture());
                        this.rayTracingEffect.setMaterialTexture(this.materialFbo.getColorBufferTexture());
                    }
                    if (this.ssaoEffect != null && this.currentConfig.hasEffect(PostProcessingData.SSAO.class)) {
                        this.ssaoEffect.setDepthTexture(this.depthFbo.getColorBufferTexture());
                        this.ssaoEffect.setCamera(this.camera);
                    }
                    heightFogEffect = this.heightFogEffect;
                    if (heightFogEffect != null) {
                        heightFogEffect.setDepthTexture(this.depthFbo.getColorBufferTexture());
                        this.heightFogEffect.setCamera(this.camera);
                    }
                    godRaysEffect = this.godRaysEffect;
                    if (godRaysEffect != null) {
                        godRaysEffect.setDepthTexture(this.depthFbo.getColorBufferTexture());
                        this.godRaysEffect.setCamera(this.camera);
                        this.godRaysEffect.setSunDirection(getSunLightDirection());
                    }
                    if (this.volumetricFogEffect != null && this.currentConfig.hasEffect(PostProcessingData.VolumetricFog.class)) {
                        this.volumetricFogEffect.setCamera(this.camera);
                        directionalLightEx = this.pbrLight;
                        if (directionalLightEx instanceof DirectionalShadowLight) {
                            DirectionalShadowLight directionalShadowLight = (DirectionalShadowLight) directionalLightEx;
                            this.volumetricFogEffect.setShadowMap(directionalShadowLight.getFrameBuffer().getColorBufferTexture(), directionalShadowLight.getCamera().combined);
                            this.volumetricFogEffect.setLightParams(directionalShadowLight.direction, directionalShadowLight.color);
                        }
                    }
                }
                if (this.postprocessingEnabled) {
                    if (z) {
                        vfxManager2 = this.vfxManager;
                        if (vfxManager2 != null) {
                            vfxManager2.update(deltaTime);
                        }
                        renderShadowsOnly();
                        this.sceneFbo2.begin();
                        renderColorsOnly(this.renderWidth, this.renderHeight);
                        this.sceneFbo2.end();
                        if (this.currentConfig.hasEffect(PostProcessingData.EyeAdaptation.class) && (eyeAdaptation = (PostProcessingData.EyeAdaptation) this.currentConfig.effects.stream().filter(new Predicate() { // from class: org.catrobat.catroid.raptor.ThreeDManager$$ExternalSyntheticLambda17
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                return ThreeDManager.lambda$render$17((PostProcessingData) obj);
                            }
                        }).findFirst().orElse(null)) != null) {
                            this.eyeAdaptationManager.update(deltaTime, this.sceneFbo2.getColorBufferTexture(), eyeAdaptation);
                            exposureEffect = this.exposureEffect;
                            if (exposureEffect != null) {
                                exposureEffect.setExposure(this.eyeAdaptationManager.getCurrentExposure());
                            }
                        }
                        this.vfxManager.cleanUpBuffers();
                        this.vfxManager.beginInputCapture();
                        this.isVfxCapturing = true;
                        this.blitCamera.update();
                        this.blitBatch.setProjectionMatrix(this.blitCamera.combined);
                        this.blitBatch.begin();
                        this.blitBatch.draw(this.sceneFboRegion, 0.0f, 0.0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
                        this.blitBatch.end();
                        this.vfxManager.endInputCapture();
                        this.isVfxCapturing = false;
                        this.vfxManager.applyEffects();
                        applyUniformsToScreenShader();
                        this.lastRenderedTexture = this.vfxManager.getResultBuffer().getTexture();
                    }
                    texture2 = this.lastRenderedTexture;
                    if (texture2 != null) {
                        if (this.renderScale >= 1.0f && this.aspectMode == 0) {
                            this.blitCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
                            this.blitCamera.update();
                            this.blitBatch.setProjectionMatrix(this.blitCamera.combined);
                            this.blitBatch.begin();
                            this.blitBatch.draw(this.lastRenderedTexture, 0.0f, 0.0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 0, 0, this.lastRenderedTexture.getWidth(), this.lastRenderedTexture.getHeight(), false, true);
                            this.blitBatch.end();
                            return;
                        }
                        texture2.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
                        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
                        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
                        Gdx.gl.glClear(16384);
                        this.blitCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
                        this.blitCamera.update();
                        this.blitBatch.setProjectionMatrix(this.blitCamera.combined);
                        width2 = Gdx.graphics.getWidth();
                        height2 = Gdx.graphics.getHeight();
                        f = width2 / height2;
                        f2 = this.renderWidth / this.renderHeight;
                        if (f > f2) {
                            f4 = height2;
                            f3 = f2 * height2;
                        } else {
                            f3 = width2;
                            f4 = width2 / f2;
                        }
                        f5 = (width2 - f3) / 2.0f;
                        f6 = (height2 - f4) / 2.0f;
                        if (this.currentUpscaler == null && this.currentConfig.qualityScale < 1.0f) {
                            this.blitBatch.setShader(this.fsrShader);
                            this.blitBatch.begin();
                            this.fsrShader.setUniformf("u_texelSize", 1.0f / this.lastRenderedTexture.getWidth(), 1.0f / this.lastRenderedTexture.getHeight());
                            this.fsrShader.setUniformf("u_sharpness", this.currentUpscaler.sharpness);
                            SpriteBatch spriteBatch = this.blitBatch;
                            Texture texture3 = this.lastRenderedTexture;
                            spriteBatch.draw(texture3, f5, f6, f3, f4, 0, 0, texture3.getWidth(), this.lastRenderedTexture.getHeight(), false, true);
                            this.blitBatch.end();
                            this.blitBatch.setShader(null);
                            return;
                        }
                        this.blitBatch.begin();
                        SpriteBatch spriteBatch2 = this.blitBatch;
                        Texture texture4 = this.lastRenderedTexture;
                        spriteBatch2.draw(texture4, f5, f6, f3, f4, 0, 0, texture4.getWidth(), this.lastRenderedTexture.getHeight(), false, true);
                        this.blitBatch.end();
                        return;
                    }
                    return;
                }
                if (this.targetFps <= 0 || this.renderScale < 1.0f || this.aspectMode != 0) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                if (z) {
                    if (z2) {
                        this.sceneFbo2.begin();
                    }
                    this.camera.update();
                    if (this.realisticMode && (sceneManager = this.sceneManager) != null) {
                        if (z2) {
                            sceneManager.updateViewport(this.renderWidth, this.renderHeight);
                        } else {
                            sceneManager.updateViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
                        }
                    }
                    if (this.realisticMode) {
                        if (this.skyColor.a != 0.0f) {
                            Gdx.gl.glClearColor(this.skyColor.r, this.skyColor.g, this.skyColor.b, this.skyColor.a);
                            Gdx.gl.glClear(16640);
                        }
                        this.sceneManager.render();
                    } else {
                        GL20 gl20 = Gdx.gl;
                        if (z2) {
                            width = this.renderWidth;
                        } else {
                            width = Gdx.graphics.getWidth();
                        }
                        if (z2) {
                            height = this.renderHeight;
                        } else {
                            height = Gdx.graphics.getHeight();
                        }
                        gl20.glViewport(0, 0, width, height);
                        if (this.skyColor.a != 0.0f) {
                            Gdx.gl.glClearColor(this.skyColor.r, this.skyColor.g, this.skyColor.b, this.skyColor.a);
                            Gdx.gl.glClear(16640);
                        }
                        this.modelBatch.begin(this.camera);
                        for (Map.Entry<String, ModelInstance> entry2 : this.sceneObjects.entrySet()) {
                            if (!this.inactiveRenderObjects.contains(entry2.getKey())) {
                                this.modelBatch.render(entry2.getValue(), this.environment);
                            }
                        }
                        this.modelBatch.end();
                    }
                    if (this.particleSystemInitialized && !this.activeParticleEffects.isEmpty()) {
                        this.particleModelBatch.begin(this.camera);
                        this.particleModelBatch.render(this.particleSystem);
                        this.particleModelBatch.end();
                        if (!this.effectsNormal.isEmpty()) {
                            Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
                            Gdx.gl.glDepthMask(true);
                            Gdx.gl.glEnable(GL20.GL_BLEND);
                            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
                            this.batchNormal.setCamera(this.camera);
                            this.batchNormal.begin();
                            it2 = this.effectsNormal.iterator();
                            while (it2.hasNext()) {
                                it2.next().draw();
                            }
                            this.batchNormal.end();
                        }
                        if (!this.effectsAdditive.isEmpty()) {
                            Gdx.gl.glEnable(GL20.GL_BLEND);
                            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, 1);
                            Gdx.gl.glDepthMask(false);
                            this.batchAdditive.setCamera(this.camera);
                            this.batchAdditive.begin();
                            it = this.effectsAdditive.iterator();
                            while (it.hasNext()) {
                                it.next().draw();
                            }
                            this.batchAdditive.end();
                            Gdx.gl.glDepthMask(true);
                            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
                        }
                    }
                    renderParticles3D();
                    if (z2) {
                        this.sceneFbo2.end();
                        this.lastRenderedTexture = this.sceneFbo2.getColorBufferTexture();
                    }
                }
                if (z2 || (texture = this.lastRenderedTexture) == null) {
                }
                drawFinalTextureToScreen(texture);
                return;
            }
            this.renderTimeAccumulator = 0.0f;
            z = true;
            if (z) {
                this.depthFbo.begin();
                Gdx.gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
                Gdx.gl.glClear(16640);
                this.depthBatch.begin(this.camera);
                if (this.realisticMode) {
                    this.depthBatch.render(sceneManager3.getRenderableProviders());
                }
                while (r9.hasNext()) {
                    if (this.inactiveRenderObjects.contains(entry.getKey())) {
                        value = entry.getValue();
                        it3 = value.materials.iterator();
                        while (true) {
                            if (it3.hasNext()) {
                                next = it3.next();
                                if (!next.has(BlendingAttribute.Type)) {
                                }
                            } else {
                                this.depthBatch.render(value);
                                break;
                                break;
                            }
                        }
                    }
                }
                this.depthBatch.end();
                postProcessingComponent = this.currentConfig;
                if (postProcessingComponent != null) {
                    depthOfField = null;
                    break;
                } else {
                    depthOfField = null;
                    break;
                }
                if (depthOfField != null) {
                    this.dofPixelsBuffer.clear();
                    Gdx.gl.glReadPixels(this.depthFbo.getWidth() / 2, this.depthFbo.getHeight() / 2, 2, 2, GL20.GL_RGBA, 5121, this.dofPixelsBuffer);
                    this.dofPixelsBuffer.rewind();
                    f7 = 0.0f;
                    i2 = 0;
                    while (i < 4) {
                        i3 = this.dofPixelsBuffer.get() & 255;
                        int i8 = this.dofPixelsBuffer.get() & 255;
                        i4 = this.dofPixelsBuffer.get() & 255;
                        i5 = this.dofPixelsBuffer.get() & 255;
                        f10 = (i3 / 255.0f) + (i8 / 65025.0f);
                        if (i3 == 128) {
                            f10 = (i4 / 255.0f) + (i5 / 65025.0f);
                        }
                        if (f10 > 1.0E-4f) {
                            f7 += f10;
                            i2++;
                        }
                    }
                    if (i2 > 0) {
                        f8 = f7 / i2;
                    } else {
                        f8 = 1.0f;
                    }
                    f9 = f8 * this.camera.far;
                    if (!Float.isNaN(depthOfField.focusDistance)) {
                        depthOfField.focusDistance = f9;
                    } else {
                        depthOfField.focusDistance = f9;
                    }
                    depthOfFieldEffect = this.dofEffect;
                    if (depthOfFieldEffect != null) {
                        depthOfFieldEffect.focusDistance = depthOfField.focusDistance;
                    }
                }
                this.depthFbo.end();
                this.materialFbo.begin();
                Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
                Gdx.gl.glClear(16640);
                this.materialBatch.begin(this.camera);
                if (this.realisticMode) {
                    this.materialBatch.render(sceneManager2.getRenderableProviders());
                }
                this.materialBatch.render(this.sceneObjects.values());
                this.materialBatch.end();
                this.materialFbo.end();
                ssrRayTracingEffect = this.rayTracingEffect;
                if (ssrRayTracingEffect != null) {
                    ssrRayTracingEffect.setDepthTexture(this.depthFbo.getColorBufferTexture());
                    this.rayTracingEffect.setMaterialTexture(this.materialFbo.getColorBufferTexture());
                }
                if (this.ssaoEffect != null) {
                    this.ssaoEffect.setDepthTexture(this.depthFbo.getColorBufferTexture());
                    this.ssaoEffect.setCamera(this.camera);
                }
                heightFogEffect = this.heightFogEffect;
                if (heightFogEffect != null) {
                    heightFogEffect.setDepthTexture(this.depthFbo.getColorBufferTexture());
                    this.heightFogEffect.setCamera(this.camera);
                }
                godRaysEffect = this.godRaysEffect;
                if (godRaysEffect != null) {
                    godRaysEffect.setDepthTexture(this.depthFbo.getColorBufferTexture());
                    this.godRaysEffect.setCamera(this.camera);
                    this.godRaysEffect.setSunDirection(getSunLightDirection());
                }
                if (this.volumetricFogEffect != null) {
                    this.volumetricFogEffect.setCamera(this.camera);
                    directionalLightEx = this.pbrLight;
                    if (directionalLightEx instanceof DirectionalShadowLight) {
                        DirectionalShadowLight directionalShadowLight2 = (DirectionalShadowLight) directionalLightEx;
                        this.volumetricFogEffect.setShadowMap(directionalShadowLight2.getFrameBuffer().getColorBufferTexture(), directionalShadowLight2.getCamera().combined);
                        this.volumetricFogEffect.setLightParams(directionalShadowLight2.direction, directionalShadowLight2.color);
                    }
                }
            }
            if (this.postprocessingEnabled) {
                if (z) {
                    vfxManager2 = this.vfxManager;
                    if (vfxManager2 != null) {
                        vfxManager2.update(deltaTime);
                    }
                    renderShadowsOnly();
                    this.sceneFbo2.begin();
                    renderColorsOnly(this.renderWidth, this.renderHeight);
                    this.sceneFbo2.end();
                    if (this.currentConfig.hasEffect(PostProcessingData.EyeAdaptation.class)) {
                        this.eyeAdaptationManager.update(deltaTime, this.sceneFbo2.getColorBufferTexture(), eyeAdaptation);
                        exposureEffect = this.exposureEffect;
                        if (exposureEffect != null) {
                            exposureEffect.setExposure(this.eyeAdaptationManager.getCurrentExposure());
                        }
                    }
                    this.vfxManager.cleanUpBuffers();
                    this.vfxManager.beginInputCapture();
                    this.isVfxCapturing = true;
                    this.blitCamera.update();
                    this.blitBatch.setProjectionMatrix(this.blitCamera.combined);
                    this.blitBatch.begin();
                    this.blitBatch.draw(this.sceneFboRegion, 0.0f, 0.0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
                    this.blitBatch.end();
                    this.vfxManager.endInputCapture();
                    this.isVfxCapturing = false;
                    this.vfxManager.applyEffects();
                    applyUniformsToScreenShader();
                    this.lastRenderedTexture = this.vfxManager.getResultBuffer().getTexture();
                }
                texture2 = this.lastRenderedTexture;
                if (texture2 != null) {
                    if (this.renderScale >= 1.0f) {
                        this.blitCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
                        this.blitCamera.update();
                        this.blitBatch.setProjectionMatrix(this.blitCamera.combined);
                        this.blitBatch.begin();
                        this.blitBatch.draw(this.lastRenderedTexture, 0.0f, 0.0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 0, 0, this.lastRenderedTexture.getWidth(), this.lastRenderedTexture.getHeight(), false, true);
                        this.blitBatch.end();
                        return;
                    }
                    texture2.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
                    Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
                    Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
                    Gdx.gl.glClear(16384);
                    this.blitCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
                    this.blitCamera.update();
                    this.blitBatch.setProjectionMatrix(this.blitCamera.combined);
                    width2 = Gdx.graphics.getWidth();
                    height2 = Gdx.graphics.getHeight();
                    f = width2 / height2;
                    f2 = this.renderWidth / this.renderHeight;
                    if (f > f2) {
                        f4 = height2;
                        f3 = f2 * height2;
                    } else {
                        f3 = width2;
                        f4 = width2 / f2;
                    }
                    f5 = (width2 - f3) / 2.0f;
                    f6 = (height2 - f4) / 2.0f;
                    if (this.currentUpscaler == null) {
                    }
                    this.blitBatch.begin();
                    SpriteBatch spriteBatch3 = this.blitBatch;
                    Texture texture5 = this.lastRenderedTexture;
                    spriteBatch3.draw(texture5, f5, f6, f3, f4, 0, 0, texture5.getWidth(), this.lastRenderedTexture.getHeight(), false, true);
                    this.blitBatch.end();
                    return;
                }
                return;
            }
            if (this.targetFps <= 0) {
                z2 = true;
            } else {
                z2 = true;
            }
            if (z) {
                if (z2) {
                    this.sceneFbo2.begin();
                }
                this.camera.update();
                if (this.realisticMode) {
                    if (z2) {
                        sceneManager.updateViewport(this.renderWidth, this.renderHeight);
                    } else {
                        sceneManager.updateViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
                    }
                }
                if (this.realisticMode) {
                    if (this.skyColor.a != 0.0f) {
                        Gdx.gl.glClearColor(this.skyColor.r, this.skyColor.g, this.skyColor.b, this.skyColor.a);
                        Gdx.gl.glClear(16640);
                    }
                    this.sceneManager.render();
                } else {
                    GL20 gl21 = Gdx.gl;
                    if (z2) {
                        width = this.renderWidth;
                    } else {
                        width = Gdx.graphics.getWidth();
                    }
                    if (z2) {
                        height = this.renderHeight;
                    } else {
                        height = Gdx.graphics.getHeight();
                    }
                    gl21.glViewport(0, 0, width, height);
                    if (this.skyColor.a != 0.0f) {
                        Gdx.gl.glClearColor(this.skyColor.r, this.skyColor.g, this.skyColor.b, this.skyColor.a);
                        Gdx.gl.glClear(16640);
                    }
                    this.modelBatch.begin(this.camera);
                    while (r2.hasNext()) {
                        if (!this.inactiveRenderObjects.contains(entry2.getKey())) {
                            this.modelBatch.render(entry2.getValue(), this.environment);
                        }
                    }
                    this.modelBatch.end();
                }
                if (this.particleSystemInitialized) {
                    this.particleModelBatch.begin(this.camera);
                    this.particleModelBatch.render(this.particleSystem);
                    this.particleModelBatch.end();
                    if (!this.effectsNormal.isEmpty()) {
                        Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
                        Gdx.gl.glDepthMask(true);
                        Gdx.gl.glEnable(GL20.GL_BLEND);
                        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
                        this.batchNormal.setCamera(this.camera);
                        this.batchNormal.begin();
                        it2 = this.effectsNormal.iterator();
                        while (it2.hasNext()) {
                            it2.next().draw();
                        }
                        this.batchNormal.end();
                    }
                    if (!this.effectsAdditive.isEmpty()) {
                        Gdx.gl.glEnable(GL20.GL_BLEND);
                        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, 1);
                        Gdx.gl.glDepthMask(false);
                        this.batchAdditive.setCamera(this.camera);
                        this.batchAdditive.begin();
                        it = this.effectsAdditive.iterator();
                        while (it.hasNext()) {
                            it.next().draw();
                        }
                        this.batchAdditive.end();
                        Gdx.gl.glDepthMask(true);
                        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
                    }
                }
                renderParticles3D();
                if (z2) {
                    this.sceneFbo2.end();
                    this.lastRenderedTexture = this.sceneFbo2.getColorBufferTexture();
                }
            }
            if (z2) {
            }
        } catch (Exception e) {
            Log.e("ThreeDManager", "FATAL 3D RENDER ERROR", e);
            e.printStackTrace();
            if (this.isVfxCapturing && (vfxManager = this.vfxManager) != null) {
                try {
                    vfxManager.endInputCapture();
                } catch (Exception unused) {
                }
                this.isVfxCapturing = false;
            }
            try {
                this.depthFbo.end();
            } catch (Exception unused2) {
            }
            try {
                this.depthBatch.end();
            } catch (Exception unused3) {
            }
            try {
                this.modelBatch.end();
            } catch (Exception unused4) {
            }
            try {
                this.particleModelBatch.end();
            } catch (Exception unused5) {
            }
            try {
                FrameBuffer frameBuffer = this.sceneFbo2;
                if (frameBuffer != null) {
                    frameBuffer.end();
                }
            } catch (Exception unused6) {
            }
        }
    }

    static /* synthetic */ boolean lambda$render$17(PostProcessingData postProcessingData) {
        return postProcessingData instanceof PostProcessingData.EyeAdaptation;
    }

    private <T, E> T getKeyByValue(Map<T, E> map, E value) {
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public void setObjectColor(String objectId, float r, float g, float b) {
        ModelInstance modelInstance = this.sceneObjects.get(objectId);
        if (modelInstance == null) {
            return;
        }
        Array.ArrayIterator<Material> it = modelInstance.materials.iterator();
        while (it.hasNext()) {
            Material next = it.next();
            next.remove(TextureAttribute.Diffuse);
            next.set(ColorAttribute.createDiffuse(r, g, b, 1.0f));
        }
    }

    public void setObjectTexture(String objectId, String texturePath) {
        Texture texture;
        ModelInstance modelInstance = this.sceneObjects.get(objectId);
        if (modelInstance == null || texturePath == null || texturePath.isEmpty()) {
            return;
        }
        if (texturePath.startsWith("buffer://")) {
            TextureRegion textureRegion = RenderTextureManager.INSTANCE.getTextureRegion(texturePath.substring(9));
            texture = textureRegion != null ? textureRegion.getTexture() : null;
        } else {
            Texture texture2 = this.loadedTextures.get(texturePath);
            if (texture2 == null) {
                try {
                    FileHandle fileHandleAbsolute = Gdx.files.absolute(texturePath);
                    if (!fileHandleAbsolute.exists()) {
                        Gdx.f82app.error("3DManager", "Texture file not found: " + texturePath);
                        return;
                    }
                    Texture texture3 = new Texture(fileHandleAbsolute);
                    texture3.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
                    this.loadedTextures.put(texturePath, texture3);
                    texture = texture3;
                } catch (Exception e) {
                    Gdx.f82app.error("3DManager", "Could not load texture: " + texturePath, e);
                    return;
                }
            } else {
                texture = texture2;
            }
        }
        if (texture == null) {
            return;
        }
        Array.ArrayIterator<Material> it = modelInstance.materials.iterator();
        while (it.hasNext()) {
            Material next = it.next();
            next.set(TextureAttribute.createDiffuse(texture));
            next.set(new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA));
        }
    }

    private void createMeshPhysicsBody(String objectId, ModelInstance instance) {
        btBvhTriangleMeshShape btbvhtrianglemeshshape = new btBvhTriangleMeshShape((btStridingMeshInterface) new btTriangleIndexVertexArray(instance.model.meshParts), true);
        Vector3 vector3 = new Vector3();
        instance.transform.getScale(vector3);
        btbvhtrianglemeshshape.setLocalScaling(vector3);
        Matrix4 matrix4 = new Matrix4();
        Vector3 vector4 = new Vector3();
        instance.transform.getTranslation(vector4);
        Quaternion quaternion = new Quaternion();
        instance.transform.getRotation(quaternion);
        matrix4.set(vector4, quaternion);
        btRigidBody.btRigidBodyConstructionInfo btrigidbodyconstructioninfo = new btRigidBody.btRigidBodyConstructionInfo(0.0f, new btDefaultMotionState(matrix4), btbvhtrianglemeshshape, new Vector3(0.0f, 0.0f, 0.0f));
        btRigidBody btrigidbody = new btRigidBody(btrigidbodyconstructioninfo);
        this.dynamicsWorld.addRigidBody(btrigidbody);
        this.physicsBodies.put(objectId, btrigidbody);
        btrigidbodyconstructioninfo.dispose();
    }

    public boolean checkCollision(String objectId1, String objectId2) {
        btRigidBody btrigidbody = this.physicsBodies.get(objectId1);
        btRigidBody btrigidbody2 = this.physicsBodies.get(objectId2);
        if (btrigidbody == null || btrigidbody2 == null) {
            return false;
        }
        this.collisionCallback.collided = false;
        this.dynamicsWorld.contactPairTest(btrigidbody, btrigidbody2, this.collisionCallback);
        return this.collisionCallback.collided;
    }

    public void castRay(Vector3 from, Vector3 direction, RayCastResult outResult) {
        this.tmpPos.set(from).add(direction.x * this.camera.far, direction.y * this.camera.far, direction.z * this.camera.far);
        ClosestRayResultCallback closestRayResultCallback = new ClosestRayResultCallback(from, this.tmpPos);
        closestRayResultCallback.setFlags(0L);
        this.dynamicsWorld.rayTest(from, this.tmpPos, closestRayResultCallback);
        if (closestRayResultCallback.hasHit()) {
            outResult.hasHit = true;
            closestRayResultCallback.getHitPointWorld(outResult.hitPoint);
            closestRayResultCallback.getHitNormalWorld(outResult.hitNormal);
            btCollisionObject collisionObject = closestRayResultCallback.getCollisionObject();
            outResult.hitObjectId = "";
            for (Map.Entry<String, btRigidBody> entry : this.physicsBodies.entrySet()) {
                if (entry.getValue().equals(collisionObject)) {
                    outResult.hitObjectId = entry.getKey();
                    break;
                }
            }
            outResult.hitDistance = from.dst(outResult.hitPoint);
        } else {
            outResult.hasHit = false;
            outResult.hitObjectId = "";
            outResult.hitDistance = -1.0f;
        }
        closestRayResultCallback.dispose();
    }

    public void castRay(String rayName, Vector3 from, Vector3 direction) {
        this.tmpPos.set(from).add(direction.x * this.camera.far, direction.y * this.camera.far, direction.z * this.camera.far);
        ClosestRayResultCallback closestRayResultCallback = new ClosestRayResultCallback(from, this.tmpPos);
        closestRayResultCallback.setFlags(0L);
        this.dynamicsWorld.rayTest(from, this.tmpPos, closestRayResultCallback);
        RayCastResult rayCastResult = this.rayCastResults.get(rayName);
        if (rayCastResult == null) {
            rayCastResult = new RayCastResult();
            this.rayCastResults.put(rayName, rayCastResult);
        }
        if (closestRayResultCallback.hasHit()) {
            rayCastResult.hasHit = true;
            closestRayResultCallback.getHitPointWorld(rayCastResult.hitPoint);
            closestRayResultCallback.getHitNormalWorld(rayCastResult.hitNormal);
            btCollisionObject collisionObject = closestRayResultCallback.getCollisionObject();
            for (Map.Entry<String, btRigidBody> entry : this.physicsBodies.entrySet()) {
                if (entry.getValue().equals(collisionObject)) {
                    rayCastResult.hitObjectId = entry.getKey();
                    break;
                }
            }
            rayCastResult.hitDistance = from.dst(rayCastResult.hitPoint);
        } else {
            rayCastResult.hasHit = false;
            rayCastResult.hitObjectId = "";
            rayCastResult.hitDistance = -1.0f;
        }
        this.rayCastResults.put(rayName, rayCastResult);
        closestRayResultCallback.dispose();
    }

    public boolean getRayDidHit(String rayName) {
        RayCastResult rayCastResult = this.rayCastResults.get(rayName);
        return rayCastResult != null && rayCastResult.hasHit;
    }

    public float getRayHitPointX(String rayName) {
        RayCastResult rayCastResult = this.rayCastResults.get(rayName);
        if (rayCastResult == null || !rayCastResult.hasHit) {
            return 0.0f;
        }
        return rayCastResult.hitPoint.x;
    }

    public float getRayHitPointY(String rayName) {
        RayCastResult rayCastResult = this.rayCastResults.get(rayName);
        if (rayCastResult == null || !rayCastResult.hasHit) {
            return 0.0f;
        }
        return rayCastResult.hitPoint.y;
    }

    public float getRayHitPointZ(String rayName) {
        RayCastResult rayCastResult = this.rayCastResults.get(rayName);
        if (rayCastResult == null || !rayCastResult.hasHit) {
            return 0.0f;
        }
        return rayCastResult.hitPoint.z;
    }

    public float getRayHitNormalX(String rayName) {
        RayCastResult rayCastResult = this.rayCastResults.get(rayName);
        if (rayCastResult == null || !rayCastResult.hasHit) {
            return 0.0f;
        }
        return rayCastResult.hitNormal.x;
    }

    public float getRayHitNormalY(String rayName) {
        RayCastResult rayCastResult = this.rayCastResults.get(rayName);
        if (rayCastResult == null || !rayCastResult.hasHit) {
            return 0.0f;
        }
        return rayCastResult.hitNormal.y;
    }

    public float getRayHitNormalZ(String rayName) {
        RayCastResult rayCastResult = this.rayCastResults.get(rayName);
        if (rayCastResult == null || !rayCastResult.hasHit) {
            return 0.0f;
        }
        return rayCastResult.hitNormal.z;
    }

    public float getRaycastDistance(String rayName) {
        RayCastResult rayCastResult = this.rayCastResults.get(rayName);
        if (rayCastResult != null) {
            return rayCastResult.hitDistance;
        }
        return -1.0f;
    }

    public void setModelRotationOffset(String objectId, float yaw, float pitch, float roll) {
        this.modelRotationOffsets.put(objectId, new Quaternion().setEulerAngles(yaw, pitch, roll));
        ModelInstance modelInstance = this.sceneObjects.get(objectId);
        if (modelInstance != null) {
            Vector3 vector3 = new Vector3();
            Quaternion quaternion = new Quaternion();
            Vector3 vector4 = new Vector3();
            modelInstance.transform.getTranslation(vector3);
            modelInstance.transform.getScale(vector4);
            btRigidBody btrigidbody = this.physicsBodies.get(objectId);
            if (btrigidbody != null) {
                btrigidbody.getWorldTransform().getRotation(quaternion, true);
            } else {
                modelInstance.transform.getRotation(quaternion, true);
            }
            Quaternion quaternion2 = this.modelRotationOffsets.get(objectId);
            if (quaternion2 != null) {
                quaternion.mul(quaternion2);
            }
            modelInstance.transform.set(vector3, quaternion, vector4);
            modelInstance.calculateTransforms();
        }
    }

    public void setRotation(String objectId, Quaternion newRotation) {
        ModelInstance modelInstance = this.sceneObjects.get(objectId);
        if (modelInstance == null) {
            return;
        }
        Vector3 vector3 = new Vector3();
        modelInstance.transform.getTranslation(vector3);
        Vector3 vector4 = new Vector3();
        modelInstance.transform.getScale(vector4);
        modelInstance.transform.set(vector3, newRotation, vector4);
        btRigidBody btrigidbody = this.physicsBodies.get(objectId);
        if (btrigidbody == null || this.editorMode) {
            return;
        }
        Matrix4 worldTransform = btrigidbody.getWorldTransform();
        worldTransform.getTranslation(vector3);
        worldTransform.set(vector3, newRotation);
        btrigidbody.setWorldTransform(worldTransform);
        btrigidbody.getMotionState().setWorldTransform(worldTransform);
        btrigidbody.activate();
        if (btrigidbody.isStaticObject() || btrigidbody.isKinematicObject()) {
            this.dynamicsWorld.updateSingleAabb(btrigidbody);
        }
    }

    public String getRaycastHitObjectId(String rayName) {
        RayCastResult rayCastResult = this.rayCastResults.get(rayName);
        return rayCastResult != null ? rayCastResult.hitObjectId : "";
    }

    public boolean createObject(String objectId, String modelPath) {
        if (this.sceneObjects.containsKey(objectId)) {
            return false;
        }
        try {
            FileHandle fileHandleAbsolute = modelPath.startsWith("/") ? Gdx.files.absolute(modelPath) : Gdx.files.internal("models/" + modelPath);
            if (!fileHandleAbsolute.exists()) {
                Gdx.f82app.error("3DManager", "Model file not found: " + fileHandleAbsolute.path());
                return false;
            }
            String lowerCase = modelPath.toLowerCase();
            if (!lowerCase.endsWith(".glb") && !lowerCase.endsWith(".gltf")) {
                Model modelLoadModel = this.loadedModels.get(modelPath);
                if (modelLoadModel == null) {
                    try {
                        FileHandle fileHandleAbsolute2 = modelPath.startsWith("/") ? Gdx.files.absolute(modelPath) : Gdx.files.internal("models/" + modelPath);
                        if (!fileHandleAbsolute2.exists()) {
                            Gdx.f82app.error("3DManager", "Model file does not exist: " + modelPath);
                            return false;
                        }
                        final FileHandle fileHandleProcess = ModelPathProcessor.process(fileHandleAbsolute2);
                        Gdx.f82app.log("3DManager", "--- Verification before loading model ---");
                        FileHandle fileHandleLocal = Gdx.files.local("Lowpoly_Laptop_Nor_2.jpg");
                        Gdx.f82app.log("3DManager", "Verifying texture path: " + fileHandleLocal.path());
                        Gdx.f82app.log("3DManager", "Does texture exist at path? -> " + fileHandleLocal.exists());
                        Gdx.f82app.log("3DManager", "--- Verification finished ---");
                        modelLoadModel = new ObjLoader(new FileHandleResolver() { // from class: org.catrobat.catroid.raptor.ThreeDManager$$ExternalSyntheticLambda21
                            @Override // com.badlogic.gdx.assets.loaders.FileHandleResolver
                            public final FileHandle resolve(String str) {
                                return fileHandleProcess.parent().child(str);
                            }
                        }).loadModel(fileHandleProcess, true);
                        this.loadedModels.put(modelPath, modelLoadModel);
                    } catch (Exception e) {
                        Gdx.f82app.error("3DManager", "Could not load model: " + modelPath, e);
                        return false;
                    }
                }
                ModelInstance modelInstance = new ModelInstance(modelLoadModel);
                this.sceneObjects.put(objectId, modelInstance);
                if (this.realisticMode) {
                    PBRColorAttribute pBRColorAttributeCreateBaseColorFactor = PBRColorAttribute.createBaseColorFactor(Color.WHITE);
                    Array.ArrayIterator<Material> it = modelInstance.materials.iterator();
                    while (it.hasNext()) {
                        Material next = it.next();
                        next.set(pBRColorAttributeCreateBaseColorFactor);
                        next.set(PBRFloatAttribute.createMetallic(0.0f));
                        next.set(PBRFloatAttribute.createRoughness(1.0f));
                    }
                    this.sceneManager.addScene(new Scene(modelInstance));
                }
                if (this.hiddenSpawnIds.contains(objectId)) {
                    this.hiddenSpawnIds.remove(objectId);
                    setObjectVisibility(objectId, false);
                }
                return true;
            }
            SceneAsset sceneAssetLoad = this.loadedGltfAssets.get(modelPath);
            if (sceneAssetLoad == null) {
                sceneAssetLoad = lowerCase.endsWith(".glb") ? new GLBLoader().load(fileHandleAbsolute, true) : new GLTFLoader().load(fileHandleAbsolute, true);
                if (sceneAssetLoad != null) {
                    this.loadedGltfAssets.put(modelPath, sceneAssetLoad);
                }
            }
            if (sceneAssetLoad == null) {
                return false;
            }
            Scene scene = new Scene(sceneAssetLoad.scene);
            this.sceneManager.addScene(scene);
            this.sceneObjects.put(objectId, scene.modelInstance);
            this.gltfObjectIds.add(objectId);
            if (scene.modelInstance.animations.size > 0) {
                this.animationControllers.put(objectId, new AnimationController(scene.modelInstance));
            }
            return true;
        } catch (Exception e2) {
            Gdx.f82app.error("3DManager_PBR", "Failed to load GLTF model: " + modelPath, e2);
            e2.printStackTrace();
            return false;
        }
    }

    public void playAnimation(String objectId, String animationName, int loops, float speed, float transitionTime) {
        AnimationController animationController = this.animationControllers.get(objectId);
        if (animationController != null) {
            animationController.animate(animationName, -1, speed, new AnimationController.AnimationListener(loops) { // from class: org.catrobat.catroid.raptor.ThreeDManager.4
                private int loopsRun = 0;
                private final int targetLoops;
                final /* synthetic */ int val$loops;

                @Override // com.badlogic.gdx.graphics.g3d.utils.AnimationController.AnimationListener
                public void onEnd(AnimationController.AnimationDesc animation) {
                }

                {
                    this.val$loops = loops;
                    this.targetLoops = loops <= 0 ? -1 : loops;
                }

                @Override // com.badlogic.gdx.graphics.g3d.utils.AnimationController.AnimationListener
                public void onLoop(AnimationController.AnimationDesc animation) {
                    int i = this.loopsRun + 1;
                    this.loopsRun = i;
                    int i2 = this.targetLoops;
                    if (i2 == -1 || i < i2) {
                        return;
                    }
                    animation.speed = 0.0f;
                    animation.time = animation.animation.duration;
                }
            }, transitionTime);
        }
    }

    public void stopAnimation(String objectId) {
        AnimationController animationController = this.animationControllers.get(objectId);
        if (animationController != null) {
            animationController.setAnimation((String) null);
        }
    }

    public void setObjectEmissive(String objectId, Color color, float intensity, String texturePath) {
        ModelInstance modelInstance = this.sceneObjects.get(objectId);
        if (modelInstance == null) {
            return;
        }
        Array.ArrayIterator<Material> it = modelInstance.materials.iterator();
        while (it.hasNext()) {
            Material next = it.next();
            next.set(PBRFloatAttribute.createEmissiveIntensity(intensity));
            applyTextureOrColor(next, texturePath, PBRTextureAttribute.EmissiveTexture, PBRColorAttribute.Emissive, color);
        }
    }

    private void setupDefaultLighting() {
        if (this.pbrLight != null) {
            this.sceneManager.environment.remove((DirectionalLight) this.pbrLight);
            this.pbrLight = null;
        }
        DirectionalShadowLight directionalShadowLight = new DirectionalShadowLight(2048, 2048);
        directionalShadowLight.direction.set(1.0f, -1.5f, 1.0f).nor();
        directionalShadowLight.color.set(Color.WHITE);
        directionalShadowLight.intensity = 5.0f;
        directionalShadowLight.getCamera().far = 1000.0f;
        this.sceneManager.environment.add((DirectionalLight) directionalShadowLight);
        this.pbrLight = directionalShadowLight;
        this.sceneManager.environment.set(new PBRTextureAttribute(PBRTextureAttribute.BRDFLUTTexture, this.brdfLUT));
        updateProceduralIBL();
    }

    public void setRealisticSunLight(float dirX, float dirY, float dirZ, float intensity) {
        DirectionalLightEx directionalLightEx = this.pbrLight;
        if (directionalLightEx instanceof DirectionalShadowLight) {
            DirectionalShadowLight directionalShadowLight = (DirectionalShadowLight) directionalLightEx;
            if (dirX == 0.0f && dirY == 0.0f && dirZ == 0.0f) {
                directionalShadowLight.direction.set(0.0f, -1.0f, 0.0f);
            } else {
                directionalShadowLight.direction.set(dirX, dirY, dirZ).nor();
            }
            directionalShadowLight.intensity = intensity;
            if (intensity <= 0.001f) {
                directionalShadowLight.color.set(0.0f, 0.0f, 0.0f, 1.0f);
                return;
            } else {
                directionalShadowLight.color.set(1.0f, 1.0f, 1.0f, 1.0f);
                return;
            }
        }
        Gdx.f82app.error("ThreeDManager", "setRealisticSunLight called, but no DirectionalShadowLight was found!");
    }

    public void setSunLightColor(float r, float g, float b) {
        DirectionalLightEx directionalLightEx = this.pbrLight;
        if (directionalLightEx != null) {
            directionalLightEx.color.set(r, g, b, 1.0f);
        }
    }

    public void applyForce(String objectId, float forceX, float forceY, float forceZ) {
        btRigidBody btrigidbody = this.physicsBodies.get(objectId);
        if (btrigidbody != null) {
            btrigidbody.activate();
            btrigidbody.applyCentralForce(new Vector3(forceX, forceY, forceZ));
        }
    }

    public void setPhysicsState(String objectId, PhysicsState state, PhysicsShape shape, float mass) {
        ModelInstance modelInstance = this.sceneObjects.get(objectId);
        if (modelInstance == null) {
            return;
        }
        removePhysicsBody(objectId);
        if (state == PhysicsState.NONE) {
            return;
        }
        float f = state == PhysicsState.DYNAMIC ? mass : 0.0f;
        if (state == PhysicsState.DYNAMIC && mass <= 0.0f) {
            Gdx.f82app.error("3DManager", "Dynamic body must have mass > 0.");
            return;
        }
        if (state == PhysicsState.MESH_STATIC) {
            if (this.gltfObjectIds.contains(objectId)) {
                createGltfMeshPhysicsBody(objectId, modelInstance);
                return;
            } else {
                createMeshPhysicsBody(objectId, modelInstance);
                return;
            }
        }
        createPrimitivePhysicsBody(objectId, modelInstance, shape, f, state);
    }

    public void setPhysicsState(String objectId, PhysicsState state, float mass) {
        setPhysicsState(objectId, state, PhysicsShape.BOX, mass);
    }

    private void createPrimitivePhysicsBody(String objectId, ModelInstance instance, PhysicsShape shapeType, float mass, PhysicsState state) {
        btCollisionShape btsphereshape;
        BoundingBox boundingBox = new BoundingBox();
        instance.calculateBoundingBox(boundingBox);
        Vector3 dimensions = boundingBox.getDimensions(new Vector3());
        Vector3 center = boundingBox.getCenter(new Vector3());
        int i = AnonymousClass6.$SwitchMap$org$catrobat$catroid$raptor$ThreeDManager$PhysicsShape[shapeType.ordinal()];
        if (i == 1) {
            btsphereshape = new btSphereShape(Math.max(dimensions.x, Math.max(dimensions.y, dimensions.z)) / 2.0f);
        } else if (i == 2) {
            float fMax = Math.max(dimensions.x, dimensions.z) / 2.0f;
            btsphereshape = new btCapsuleShape(fMax, Math.max(0.0f, dimensions.y - (2.0f * fMax)));
        } else {
            btsphereshape = new btBoxShape(dimensions.cpy().scl(0.5f));
        }
        btCompoundShape btcompoundshape = new btCompoundShape();
        btcompoundshape.addChildShape(new Matrix4().setToTranslation(center), btsphereshape);
        btcompoundshape.setLocalScaling(instance.transform.getScale(new Vector3()));
        Vector3 vector3 = new Vector3();
        if (mass > 0.0f) {
            btcompoundshape.calculateLocalInertia(mass, vector3);
        }
        Matrix4 matrix4 = new Matrix4();
        matrix4.set(instance.transform.getRotation(new Quaternion()));
        matrix4.setTranslation(instance.transform.getTranslation(new Vector3()));
        btRigidBody.btRigidBodyConstructionInfo btrigidbodyconstructioninfo = new btRigidBody.btRigidBodyConstructionInfo(mass, new btDefaultMotionState(matrix4), btcompoundshape, vector3);
        btRigidBody btrigidbody = new btRigidBody(btrigidbodyconstructioninfo);
        if (state == PhysicsState.KINEMATIC) {
            btrigidbody.setCollisionFlags(btrigidbody.getCollisionFlags() | 2);
            btrigidbody.setActivationState(4);
        }
        if (mass > 0.0f) {
            btrigidbody.setAngularFactor(1.0f);
            btrigidbody.setDamping(0.5f, 0.5f);
        }
        this.dynamicsWorld.addRigidBody(btrigidbody);
        this.physicsBodies.put(objectId, btrigidbody);
        Array<Disposable> array = new Array<>();
        array.add(btsphereshape);
        array.add(btcompoundshape);
        btrigidbody.userData = array;
        this.physicsResources.put(objectId, array);
        btrigidbodyconstructioninfo.dispose();
    }

    private void createGltfMeshPhysicsBody(String objectId, ModelInstance instance) {
        Gdx.f82app.log("PhysicsDebug", "============================================================");
        Gdx.f82app.log("PhysicsDebug", "=== STARTING MESH BODY CREATION (BAKING METHOD) for object: '" + objectId + FormatHelper.QUOTE);
        Gdx.f82app.log("PhysicsDebug", "============================================================");
        instance.calculateTransforms();
        btCompoundShape btcompoundshape = new btCompoundShape();
        Array<Disposable> array = new Array<>();
        if (instance.nodes.size == 0) {
            Gdx.f82app.error("PhysicsDebug", "FATAL: ModelInstance has NO nodes!");
        } else {
            Gdx.f82app.log("PhysicsDebug", "Model has " + instance.nodes.size + " root nodes. Starting recursion...");
            addPartsToCompoundShapeRecursive(instance.nodes, instance.transform, btcompoundshape, array, "  ");
        }
        int numChildShapes = btcompoundshape.getNumChildShapes();
        Gdx.f82app.log("PhysicsDebug", "RECURSION FINISHED. Total child shapes in CompoundShape: " + numChildShapes);
        if (numChildShapes == 0) {
            Gdx.f82app.error("PhysicsDebug", "FATAL: CompoundShape is EMPTY!");
            btcompoundshape.dispose();
            Array.ArrayIterator<Disposable> it = array.iterator();
            while (it.hasNext()) {
                it.next().dispose();
            }
            Gdx.f82app.log("PhysicsDebug", "=================== CREATION FAILED ===================");
            return;
        }
        Matrix4 matrix4Idt = new Matrix4().idt();
        Gdx.f82app.log("PhysicsDebug", "RigidBody initial transform is IDENTITY because geometry is pre-transformed.");
        btRigidBody.btRigidBodyConstructionInfo btrigidbodyconstructioninfo = new btRigidBody.btRigidBodyConstructionInfo(0.0f, new btDefaultMotionState(matrix4Idt), btcompoundshape, new Vector3(0.0f, 0.0f, 0.0f));
        btRigidBody btrigidbody = new btRigidBody(btrigidbodyconstructioninfo);
        btrigidbody.setCollisionFlags(btrigidbody.getCollisionFlags() | 1);
        btrigidbody.setActivationState(4);
        this.dynamicsWorld.addRigidBody(btrigidbody);
        this.dynamicsWorld.updateSingleAabb(btrigidbody);
        this.physicsBodies.put(objectId, btrigidbody);
        array.add(btcompoundshape);
        btrigidbody.userData = array;
        this.physicsResources.put(objectId, array);
        Gdx.f82app.log("PhysicsDebug", "SUCCESS: RigidBody added to dynamicsWorld. AABB was manually updated.");
        Gdx.f82app.log("PhysicsDebug", "=================== CREATION FINISHED for: '" + objectId + "' ===================");
        btrigidbodyconstructioninfo.dispose();
    }

    public void createEditorProxy(String ownerId) {
        if (this.editorProxies.containsKey(ownerId) || this.lightProxyModel == null) {
            return;
        }
        this.editorProxies.put(ownerId, new ModelInstance(this.lightProxyModel));
    }

    public void removeEditorProxy(String ownerId) {
        this.editorProxies.remove(ownerId);
    }

    public void updateEditorProxyPosition(String ownerId, Vector3 position) {
        ModelInstance modelInstance = this.editorProxies.get(ownerId);
        if (modelInstance != null) {
            modelInstance.transform.setTranslation(position);
        }
    }

    public Map<String, ModelInstance> getEditorProxies() {
        return this.editorProxies;
    }

    private void addPartsToCompoundShapeRecursive(Iterable<Node> nodes, Matrix4 rootTransform, btCompoundShape compoundShape, Array<Disposable> disposables, String indent) {
        for (Node node : nodes) {
            Gdx.f82app.log("PhysicsDebug", indent + "-> Processing Node: '" + node.f104id + FormatHelper.QUOTE);
            Matrix4 matrix4Mul = new Matrix4(rootTransform).mul(node.globalTransform);
            if (node.parts.size > 0) {
                Array.ArrayIterator<NodePart> it = node.parts.iterator();
                while (it.hasNext()) {
                    MeshPart meshPart = it.next().meshPart;
                    if (meshPart != null && meshPart.mesh != null && meshPart.mesh.getNumVertices() > 0) {
                        Mesh meshCopy = meshPart.mesh.copy(true);
                        meshCopy.transform(matrix4Mul);
                        disposables.add(meshCopy);
                        btTriangleIndexVertexArray bttriangleindexvertexarray = new btTriangleIndexVertexArray(new MeshPart("baked_part_" + node.f104id, meshCopy, 0, meshCopy.getNumIndices(), 4));
                        btBvhTriangleMeshShape btbvhtrianglemeshshape = new btBvhTriangleMeshShape((btStridingMeshInterface) bttriangleindexvertexarray, true);
                        btbvhtrianglemeshshape.setMargin(0.04f);
                        disposables.add(bttriangleindexvertexarray);
                        disposables.add(btbvhtrianglemeshshape);
                        compoundShape.addChildShape(new Matrix4(), btbvhtrianglemeshshape);
                        Gdx.f82app.log("PhysicsDebug", indent + "  SUCCESS: Baked and added child shape.");
                    }
                }
            }
            if (node.hasChildren()) {
                addPartsToCompoundShapeRecursive(node.getChildren(), rootTransform, compoundShape, disposables, indent + "  ");
            }
        }
    }

    Boolean objectExists(String id2) {
        return Boolean.valueOf(this.sceneObjects.containsKey(id2));
    }

    public Vector3 getVelocity(String objectId) {
        btRigidBody btrigidbody = this.physicsBodies.get(objectId);
        if (btrigidbody != null && btrigidbody.getInvMass() > 0.0f) {
            return btrigidbody.getLinearVelocity();
        }
        return Vector3.Zero;
    }

    public Vector3 getCameraRotation() {
        Quaternion quaternion = new Quaternion();
        quaternion.setFromMatrix(this.camera.view);
        quaternion.conjugate();
        return new Vector3(quaternion.getPitch(), quaternion.getYaw(), quaternion.getRoll());
    }

    public ModelInstance getModelInstance(String objectId) {
        ModelInstance modelInstance = this.sceneObjects.get(objectId);
        return modelInstance == null ? this.editorProxies.get(objectId) : modelInstance;
    }

    public boolean createCylinder(String objectId) {
        net.mgsx.gltf.scene3d.scene.SceneManager sceneManager;
        if (this.sceneObjects.containsKey(objectId)) {
            return false;
        }
        Model modelEnd = this.loadedModels.get("__PRIMITIVE_CYLINDER__");
        if (modelEnd == null) {
            Material material = new Material(PBRColorAttribute.createBaseColorFactor(Color.WHITE));
            ModelBuilder modelBuilder = new ModelBuilder();
            modelBuilder.begin();
            Node node = modelBuilder.node();
            node.f104id = "cylinder_node";
            node.rotation.set(Vector3.X, 90.0f);
            CylinderShapeBuilder.build(modelBuilder.part("cylinder", 4, 25L, material), 1.0f, 1.0f, 1.0f, 16);
            modelEnd = modelBuilder.end();
            this.loadedModels.put("__PRIMITIVE_CYLINDER__", modelEnd);
        }
        ModelInstance modelInstance = new ModelInstance(modelEnd);
        this.sceneObjects.put(objectId, modelInstance);
        if (this.realisticMode && (sceneManager = this.sceneManager) != null) {
            sceneManager.addScene(new Scene(modelInstance));
        }
        if (this.hiddenSpawnIds.contains(objectId)) {
            this.hiddenSpawnIds.remove(objectId);
            setObjectVisibility(objectId, false);
        }
        return true;
    }

    public boolean createSphere(String objectId) {
        net.mgsx.gltf.scene3d.scene.SceneManager sceneManager;
        if (this.sceneObjects.containsKey(objectId)) {
            return false;
        }
        Model modelCreateSphere = this.loadedModels.get("__PRIMITIVE_SPHERE__");
        if (modelCreateSphere == null) {
            modelCreateSphere = this.modelBuilder.createSphere(50.0f, 50.0f, 50.0f, 16, 16, new Material(PBRColorAttribute.createBaseColorFactor(Color.WHITE)), 153L);
            this.loadedModels.put("__PRIMITIVE_SPHERE__", modelCreateSphere);
        }
        ModelInstance modelInstance = new ModelInstance(modelCreateSphere);
        this.sceneObjects.put(objectId, modelInstance);
        if (this.realisticMode && (sceneManager = this.sceneManager) != null) {
            sceneManager.addScene(new Scene(modelInstance));
        }
        if (this.hiddenSpawnIds.contains(objectId)) {
            this.hiddenSpawnIds.remove(objectId);
            setObjectVisibility(objectId, false);
        }
        return true;
    }

    private void createPhysicsBody(String objectId, ModelInstance instance, float mass) {
        BoundingBox boundingBox = new BoundingBox();
        instance.model.calculateBoundingBox(boundingBox);
        Vector3 vector3 = new Vector3();
        boundingBox.getDimensions(vector3);
        Vector3 vector4 = new Vector3();
        boundingBox.getCenter(vector4);
        btBoxShape btboxshape = new btBoxShape(vector3.scl(0.5f));
        btCompoundShape btcompoundshape = new btCompoundShape();
        Matrix4 matrix4 = new Matrix4();
        matrix4.setToTranslation(vector4);
        btcompoundshape.addChildShape(matrix4, btboxshape);
        Vector3 vector5 = new Vector3();
        instance.transform.getScale(vector5);
        btcompoundshape.setLocalScaling(vector5);
        Vector3 vector6 = new Vector3();
        if (mass > 0.0f) {
            btcompoundshape.calculateLocalInertia(mass, vector6);
        }
        Matrix4 matrix5 = new Matrix4();
        Vector3 vector7 = new Vector3();
        instance.transform.getTranslation(vector7);
        Quaternion quaternion = new Quaternion();
        instance.transform.getRotation(quaternion);
        matrix5.set(vector7, quaternion);
        btRigidBody.btRigidBodyConstructionInfo btrigidbodyconstructioninfo = new btRigidBody.btRigidBodyConstructionInfo(mass, new btDefaultMotionState(matrix5), btcompoundshape, vector6);
        btRigidBody btrigidbody = new btRigidBody(btrigidbodyconstructioninfo);
        this.dynamicsWorld.addRigidBody(btrigidbody);
        this.physicsBodies.put(objectId, btrigidbody);
        btrigidbodyconstructioninfo.dispose();
    }

    private void addNodePartsToCompoundShape(Node node, btCompoundShape compoundShape) {
        if (node.parts.size > 0) {
            Gdx.f82app.log("PhysicsDebug", "Processing Node: " + node.f104id + " with " + node.parts.size + " parts.");
            Array array = new Array();
            Array.ArrayIterator<NodePart> it = node.parts.iterator();
            while (it.hasNext()) {
                MeshPart meshPart = it.next().meshPart;
                if (meshPart.size > 0) {
                    btTriangleIndexVertexArray bttriangleindexvertexarray = new btTriangleIndexVertexArray(meshPart);
                    array.add(bttriangleindexvertexarray);
                    btBvhTriangleMeshShape btbvhtrianglemeshshape = new btBvhTriangleMeshShape((btStridingMeshInterface) bttriangleindexvertexarray, true);
                    array.add(btbvhtrianglemeshshape);
                    compoundShape.addChildShape(node.globalTransform, btbvhtrianglemeshshape);
                }
            }
            Array.ArrayIterator it2 = array.iterator();
            while (it2.hasNext()) {
                ((Disposable) it2.next()).dispose();
            }
        }
        Iterator<Node> it3 = node.getChildren().iterator();
        while (it3.hasNext()) {
            addNodePartsToCompoundShape(it3.next(), compoundShape);
        }
    }

    void removePhysicsBody(String objectId) {
        btRigidBody btrigidbodyRemove = this.physicsBodies.remove(objectId);
        if (btrigidbodyRemove != null) {
            this.dynamicsWorld.removeRigidBody(btrigidbodyRemove);
            Array<Disposable> arrayRemove = this.physicsResources.remove(objectId);
            if (arrayRemove != null) {
                Array.ArrayIterator<Disposable> it = arrayRemove.iterator();
                while (it.hasNext()) {
                    try {
                        it.next().dispose();
                    } catch (Exception unused) {
                    }
                }
            }
            if (btrigidbodyRemove.userData instanceof Array) {
                Array.ArrayIterator it2 = ((Array) btrigidbodyRemove.userData).iterator();
                while (it2.hasNext()) {
                    try {
                        ((Disposable) it2.next()).dispose();
                    } catch (Exception unused2) {
                    }
                }
            }
            if (btrigidbodyRemove.getMotionState() != null) {
                btrigidbodyRemove.getMotionState().dispose();
            }
            if (btrigidbodyRemove.getCollisionShape() != null) {
                btrigidbodyRemove.getCollisionShape().dispose();
            }
            btrigidbodyRemove.dispose();
        }
    }

    public boolean checkIntersection(String objectId1, String objectId2) {
        ModelInstance modelInstance = this.sceneObjects.get(objectId1);
        ModelInstance modelInstance2 = this.sceneObjects.get(objectId2);
        if (modelInstance == null || modelInstance2 == null) {
            return false;
        }
        modelInstance.calculateBoundingBox(this.bounds1);
        this.bounds1.mul(modelInstance.transform);
        modelInstance2.calculateBoundingBox(this.bounds2);
        this.bounds2.mul(modelInstance2.transform);
        return this.bounds1.intersects(this.bounds2);
    }

    public void removeObjectsByPrefix(String prefix) {
        if (prefix == null || prefix.isEmpty()) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (String str : this.sceneObjects.keySet()) {
            if (str.startsWith(prefix)) {
                arrayList.add(str);
            }
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            removeObject((String) it.next());
        }
    }

    public boolean removeObject(String objectId) {
        net.mgsx.gltf.scene3d.scene.SceneManager sceneManager;
        GameObject gameObjectFindGameObject;
        ModelInstance modelInstanceRemove = this.sceneObjects.remove(objectId);
        SceneManager sceneManager2 = this.manager;
        if (sceneManager2 != null && (gameObjectFindGameObject = sceneManager2.findGameObject(objectId)) != null) {
            this.manager.removeGameObject(gameObjectFindGameObject);
        }
        removePhysicsBody(objectId);
        this.animationControllers.remove(objectId);
        removeParticleEffect3D(objectId);
        this.gltfObjectIds.remove(objectId);
        this.inactiveRenderObjects.remove(objectId);
        this.inactivePbrScenes.remove(objectId);
        this.aiLastKnownPositions.remove(objectId);
        this.aiHasLostTarget.remove(objectId);
        this.aiSmoothedDirection.remove(objectId);
        if (modelInstanceRemove == null) {
            return false;
        }
        if (!this.realisticMode || (sceneManager = this.sceneManager) == null) {
            return true;
        }
        Array.ArrayIterator<RenderableProvider> it = sceneManager.getRenderableProviders().iterator();
        while (it.hasNext()) {
            RenderableProvider next = it.next();
            if (next instanceof Scene) {
                Scene scene = (Scene) next;
                if (scene.modelInstance == modelInstanceRemove) {
                    this.sceneManager.removeScene(scene);
                    return true;
                }
            }
        }
        return true;
    }

    public boolean removeModelOnly(String objectId) {
        net.mgsx.gltf.scene3d.scene.SceneManager sceneManager;
        ModelInstance modelInstanceRemove = this.sceneObjects.remove(objectId);
        this.animationControllers.remove(objectId);
        this.gltfObjectIds.remove(objectId);
        this.inactiveRenderObjects.remove(objectId);
        this.inactivePbrScenes.remove(objectId);
        if (modelInstanceRemove == null) {
            return false;
        }
        if (!this.realisticMode || (sceneManager = this.sceneManager) == null) {
            return true;
        }
        Array.ArrayIterator<RenderableProvider> it = sceneManager.getRenderableProviders().iterator();
        while (it.hasNext()) {
            RenderableProvider next = it.next();
            if (next instanceof Scene) {
                Scene scene = (Scene) next;
                if (scene.modelInstance == modelInstanceRemove) {
                    this.sceneManager.removeScene(scene);
                    return true;
                }
            }
        }
        return true;
    }

    public void replaceModel(final String objectId, final String modelFileName) {
        Gdx.f82app.postRunnable(new Runnable() { // from class: org.catrobat.catroid.raptor.ThreeDManager.5
            @Override // java.lang.Runnable
            public void run() {
                String absolutePath;
                GameObject gameObjectFindGameObject;
                GameObject gameObjectFindGameObject2;
                if (ThreeDManager.this.manager != null && (gameObjectFindGameObject2 = ThreeDManager.this.manager.findGameObject(objectId)) != null) {
                    RenderComponent renderComponent = (RenderComponent) gameObjectFindGameObject2.getComponent(RenderComponent.class);
                    if (renderComponent == null) {
                        renderComponent = new RenderComponent();
                        gameObjectFindGameObject2.addComponent(renderComponent);
                    }
                    renderComponent.modelFileName = modelFileName;
                }
                ThreeDManager.this.removeModelOnly(objectId);
                if (modelFileName.startsWith("assets://")) {
                    absolutePath = modelFileName.substring("assets://".length());
                } else {
                    File file = ProjectManager.getInstance().getCurrentProject().getFile(modelFileName);
                    if (file != null && file.exists()) {
                        absolutePath = file.getAbsolutePath();
                    } else {
                        Gdx.f82app.error("ThreeDManager", "replaceModel failed: Model file not found: " + modelFileName);
                        return;
                    }
                }
                ThreeDManager.this.createObject(objectId, absolutePath);
                if (ThreeDManager.this.manager == null || (gameObjectFindGameObject = ThreeDManager.this.manager.findGameObject(objectId)) == null) {
                    return;
                }
                ThreeDManager.this.manager.updateWorldTransforms();
                ThreeDManager.this.setWorldTransform(gameObjectFindGameObject.f591id, gameObjectFindGameObject.transform.worldTransform);
                MaterialComponent materialComponent = (MaterialComponent) gameObjectFindGameObject.getComponent(MaterialComponent.class);
                if (materialComponent != null) {
                    ThreeDManager.this.applyPBRMaterial(gameObjectFindGameObject.f591id, materialComponent);
                }
                PhysicsComponent physicsComponent = (PhysicsComponent) gameObjectFindGameObject.getComponent(PhysicsComponent.class);
                if (physicsComponent != null) {
                    ThreeDManager.this.setPhysicsStateFromComponent(gameObjectFindGameObject.f591id, physicsComponent, gameObjectFindGameObject.transform.worldTransform);
                }
            }
        });
    }

    public boolean removeLight(String id2) {
        return this.directionalLights.remove(id2) != null;
    }

    public void setPosition(String objectId, float x, float y, float z) {
        SceneManager sceneManager = this.manager;
        if (sceneManager != null && sceneManager.findGameObject(objectId) != null) {
            this.manager.setPosition(this.manager.findGameObject(objectId), new Vector3(x, y, z));
        }
        ModelInstance modelInstance = this.sceneObjects.get(objectId);
        if (modelInstance == null) {
            return;
        }
        modelInstance.transform.setTranslation(x, y, z);
        btRigidBody btrigidbody = this.physicsBodies.get(objectId);
        if (btrigidbody == null || this.editorMode) {
            return;
        }
        Matrix4 worldTransform = btrigidbody.getWorldTransform();
        worldTransform.set(new Vector3(x, y, z), worldTransform.getRotation(new Quaternion()));
        btrigidbody.setWorldTransform(worldTransform);
        btrigidbody.getMotionState().setWorldTransform(worldTransform);
        btrigidbody.activate();
        if (btrigidbody.isStaticObject() || btrigidbody.isKinematicObject()) {
            this.dynamicsWorld.updateSingleAabb(btrigidbody);
        }
    }

    public void setRotation(String objectId, float yaw, float pitch, float roll) {
        Quaternion eulerAngles = new Quaternion().setEulerAngles(yaw, pitch, roll);
        SceneManager sceneManager = this.manager;
        if (sceneManager != null && sceneManager.findGameObject(objectId) != null) {
            this.manager.setRotation(this.manager.findGameObject(objectId), eulerAngles);
        }
        ModelInstance modelInstance = this.sceneObjects.get(objectId);
        if (modelInstance == null) {
            return;
        }
        Vector3 vector3 = new Vector3();
        modelInstance.transform.getTranslation(vector3);
        Vector3 vector4 = new Vector3();
        modelInstance.transform.getScale(vector4);
        modelInstance.transform.set(vector3, eulerAngles, vector4);
        btRigidBody btrigidbody = this.physicsBodies.get(objectId);
        if (btrigidbody == null || this.editorMode) {
            return;
        }
        Matrix4 worldTransform = btrigidbody.getWorldTransform();
        worldTransform.getTranslation(vector3);
        worldTransform.set(vector3, eulerAngles);
        btrigidbody.setWorldTransform(worldTransform);
        btrigidbody.getMotionState().setWorldTransform(worldTransform);
        btrigidbody.activate();
        if (btrigidbody.isStaticObject() || btrigidbody.isKinematicObject()) {
            this.dynamicsWorld.updateSingleAabb(btrigidbody);
        }
    }

    public Quaternion getRotationQuaternion(String objectId) {
        ModelInstance modelInstance = this.sceneObjects.get(objectId);
        if (modelInstance == null) {
            return null;
        }
        Quaternion quaternion = new Quaternion();
        modelInstance.transform.getRotation(quaternion, true);
        return quaternion;
    }

    public void setCameraPosition(float x, float y, float z) {
        this.camera.position.set(x, y, z);
        this.camera.update();
    }

    public void cameraLookAt(float x, float y, float z) {
        this.camera.lookAt(x, y, z);
        this.camera.update();
    }

    public void objectLookAtCorrect(String id2, float x, float y, float z) {
        GameObject gameObjectFindGameObject;
        GameObject gameObjectFindGameObject2;
        ModelInstance modelInstance = this.sceneObjects.get(id2);
        if (modelInstance == null) {
            return;
        }
        SceneManager sceneManager = this.manager;
        if (sceneManager != null) {
            sceneManager.updateWorldTransforms();
            gameObjectFindGameObject = this.manager.findGameObject(id2);
        } else {
            gameObjectFindGameObject = null;
        }
        Vector3 vector3 = new Vector3();
        Vector3 vector4 = new Vector3(1.0f, 1.0f, 1.0f);
        if (gameObjectFindGameObject != null) {
            gameObjectFindGameObject.transform.worldTransform.getTranslation(vector3);
            gameObjectFindGameObject.transform.worldTransform.getScale(vector4);
        } else {
            modelInstance.transform.getTranslation(vector3);
            modelInstance.transform.getScale(vector4);
        }
        Vector3 vector3Nor = new Vector3(new Vector3(x, y, z)).sub(vector3).nor();
        if (vector3Nor.isZero()) {
            return;
        }
        Vector3 vector5 = new Vector3(Vector3.Y);
        if (Math.abs(vector3Nor.y) > 0.999f) {
            vector5.set(Vector3.Z);
        }
        Vector3 vector3Nor2 = new Vector3(vector5).crs(vector3Nor).nor();
        Vector3 vector3Nor3 = new Vector3(vector3Nor).crs(vector3Nor2).nor();
        Matrix4 matrix4Idt = new Matrix4().idt();
        matrix4Idt.val[0] = vector3Nor2.x;
        matrix4Idt.val[1] = vector3Nor2.y;
        matrix4Idt.val[2] = vector3Nor2.z;
        matrix4Idt.val[4] = vector3Nor3.x;
        matrix4Idt.val[5] = vector3Nor3.y;
        matrix4Idt.val[6] = vector3Nor3.z;
        matrix4Idt.val[8] = vector3Nor.x;
        matrix4Idt.val[9] = vector3Nor.y;
        matrix4Idt.val[10] = vector3Nor.z;
        Quaternion quaternion = new Quaternion();
        matrix4Idt.getRotation(quaternion, true);
        if (gameObjectFindGameObject != null) {
            if (gameObjectFindGameObject.parentId != null && (gameObjectFindGameObject2 = this.manager.findGameObject(gameObjectFindGameObject.parentId)) != null) {
                Quaternion quaternion2 = new Quaternion();
                gameObjectFindGameObject2.transform.worldTransform.getRotation(quaternion2, true);
                this.manager.setRotation(gameObjectFindGameObject, quaternion2.conjugate().mul(quaternion));
            } else {
                this.manager.setRotation(gameObjectFindGameObject, quaternion);
            }
            this.manager.updateWorldTransforms();
            setWorldTransform(id2, gameObjectFindGameObject.transform.worldTransform);
            return;
        }
        modelInstance.transform.set(vector3, quaternion, vector4);
        btRigidBody btrigidbody = this.physicsBodies.get(id2);
        if (btrigidbody != null) {
            Matrix4 worldTransform = btrigidbody.getWorldTransform();
            worldTransform.getTranslation(vector3);
            worldTransform.set(vector3, quaternion);
            btrigidbody.setWorldTransform(worldTransform);
            if (btrigidbody.getMotionState() != null) {
                btrigidbody.getMotionState().setWorldTransform(worldTransform);
            }
            btrigidbody.activate();
        }
    }

    public void objectLookAt(String id2, float x, float y, float z) {
        GameObject gameObjectFindGameObject;
        ModelInstance modelInstance = this.sceneObjects.get(id2);
        if (modelInstance == null) {
            return;
        }
        Vector3 vector3 = new Vector3();
        modelInstance.transform.getTranslation(vector3);
        Vector3 vector4 = new Vector3();
        modelInstance.transform.getScale(vector4);
        Matrix4 toLookAt = new Matrix4().setToLookAt(new Vector3(x, y, z), Vector3.Y);
        Quaternion quaternion = new Quaternion();
        toLookAt.getRotation(quaternion);
        SceneManager sceneManager = this.manager;
        if (sceneManager != null && sceneManager.findGameObject(id2) != null) {
            GameObject gameObjectFindGameObject2 = this.manager.findGameObject(id2);
            if (gameObjectFindGameObject2.parentId != null && (gameObjectFindGameObject = this.manager.findGameObject(gameObjectFindGameObject2.parentId)) != null) {
                Quaternion quaternion2 = new Quaternion();
                gameObjectFindGameObject.transform.worldTransform.getRotation(quaternion2, true);
                this.manager.setRotation(gameObjectFindGameObject2, quaternion2.conjugate().mul(quaternion));
            } else {
                this.manager.setRotation(gameObjectFindGameObject2, quaternion);
            }
            this.manager.updateWorldTransforms();
            setWorldTransform(id2, gameObjectFindGameObject2.transform.worldTransform);
            return;
        }
        modelInstance.transform.set(vector3, quaternion, vector4);
        btRigidBody btrigidbody = this.physicsBodies.get(id2);
        if (btrigidbody != null) {
            Matrix4 worldTransform = btrigidbody.getWorldTransform();
            worldTransform.getTranslation(vector3);
            worldTransform.set(vector3, quaternion);
            btrigidbody.setWorldTransform(worldTransform);
            if (btrigidbody.getMotionState() != null) {
                btrigidbody.getMotionState().setWorldTransform(worldTransform);
            }
            btrigidbody.activate();
        }
    }

    public void alignObjectToNormal(String objectId, float nx, float ny, float nz) {
        GameObject gameObjectFindGameObject;
        GameObject gameObjectFindGameObject2;
        ModelInstance modelInstance = this.sceneObjects.get(objectId);
        if (modelInstance == null) {
            return;
        }
        Vector3 vector3Nor = new Vector3(nx, ny, nz).nor();
        Vector3 vector3 = new Vector3(0.0f, 0.0f, 1.0f);
        Quaternion quaternion = new Quaternion();
        if (vector3.isCollinearOpposite(vector3Nor, 0.01f)) {
            quaternion.setEulerAngles(180.0f, 0.0f, 0.0f);
        } else {
            quaternion.setFromCross(vector3, vector3Nor);
        }
        SceneManager sceneManager = this.manager;
        if (sceneManager != null && (gameObjectFindGameObject = sceneManager.findGameObject(objectId)) != null) {
            if (gameObjectFindGameObject.parentId != null && (gameObjectFindGameObject2 = this.manager.findGameObject(gameObjectFindGameObject.parentId)) != null) {
                Quaternion quaternion2 = new Quaternion();
                gameObjectFindGameObject2.transform.worldTransform.getRotation(quaternion2, true);
                this.manager.setRotation(gameObjectFindGameObject, quaternion2.conjugate().mul(quaternion));
            } else {
                this.manager.setRotation(gameObjectFindGameObject, quaternion);
            }
            this.manager.updateWorldTransforms();
            setWorldTransform(objectId, gameObjectFindGameObject.transform.worldTransform);
            return;
        }
        Vector3 vector4 = new Vector3();
        modelInstance.transform.getTranslation(vector4);
        Vector3 vector5 = new Vector3();
        modelInstance.transform.getScale(vector5);
        modelInstance.transform.set(vector4, quaternion, vector5);
        btRigidBody btrigidbody = this.physicsBodies.get(objectId);
        if (btrigidbody == null || this.editorMode) {
            return;
        }
        Matrix4 worldTransform = btrigidbody.getWorldTransform();
        worldTransform.set(vector4, quaternion);
        btrigidbody.setWorldTransform(worldTransform);
        if (btrigidbody.getMotionState() != null) {
            btrigidbody.getMotionState().setWorldTransform(worldTransform);
        }
        btrigidbody.activate();
    }

    public boolean createFixedConstraintWeld(String constraintId, String objectIdA, String objectIdB) {
        if (this.physicsConstraints.containsKey(constraintId)) {
            return false;
        }
        btRigidBody btrigidbody = this.physicsBodies.get(objectIdA);
        btRigidBody btrigidbody2 = this.physicsBodies.get(objectIdB);
        if (btrigidbody == null || btrigidbody2 == null) {
            return false;
        }
        btrigidbody.activate();
        btrigidbody2.activate();
        btFixedConstraint btfixedconstraint = new btFixedConstraint(btrigidbody, btrigidbody2, new Matrix4().idt(), new Matrix4(btrigidbody2.getWorldTransform()).inv().mul(btrigidbody.getWorldTransform()));
        this.dynamicsWorld.addConstraint(btfixedconstraint, true);
        this.physicsConstraints.put(constraintId, btfixedconstraint);
        return true;
    }

    public boolean createFixedConstraintManual(String constraintId, String objectIdA, String objectIdB, float ax, float ay, float az, float bx, float by, float bz) {
        if (this.physicsConstraints.containsKey(constraintId)) {
            return false;
        }
        btRigidBody btrigidbody = this.physicsBodies.get(objectIdA);
        btRigidBody btrigidbody2 = this.physicsBodies.get(objectIdB);
        if (btrigidbody == null || btrigidbody2 == null) {
            return false;
        }
        btrigidbody.activate();
        btrigidbody2.activate();
        btFixedConstraint btfixedconstraint = new btFixedConstraint(btrigidbody, btrigidbody2, new Matrix4().setToTranslation(ax, ay, az), new Matrix4().setToTranslation(bx, by, bz));
        this.dynamicsWorld.addConstraint(btfixedconstraint, true);
        this.physicsConstraints.put(constraintId, btfixedconstraint);
        return true;
    }

    public void setCameraRotation(float yaw, float pitch, float roll) {
        Quaternion quaternion = new Quaternion();
        quaternion.setEulerAngles(yaw, pitch, roll);
        this.camera.direction.set(0.0f, 0.0f, -1.0f);
        quaternion.transform(this.camera.direction);
        this.camera.up.set(0.0f, 1.0f, 0.0f);
        quaternion.transform(this.camera.up);
        this.camera.update();
    }

    public void setAmbientLight(float r, float g, float b) {
        this.environment.set(new ColorAttribute(ColorAttribute.AmbientLight, r, g, b, 1.0f));
    }

    public void setDirectionalLight(String lightId, float r, float g, float b, float dirX, float dirY, float dirZ) {
        DirectionalLight directionalLight = this.directionalLights.get(lightId);
        if (directionalLight == null) {
            directionalLight = new DirectionalLight();
            this.directionalLights.put(lightId, directionalLight);
            this.environment.add(directionalLight);
        }
        directionalLight.set(r, g, b, dirX, dirY, dirZ);
    }

    public Camera getCamera() {
        return this.camera;
    }

    public void setScale(String objectId, float scaleX, float scaleY, float scaleZ) {
        ModelInstance modelInstance = this.sceneObjects.get(objectId);
        if (modelInstance == null) {
            return;
        }
        Quaternion quaternion = new Quaternion();
        modelInstance.transform.getRotation(quaternion, true);
        Vector3 vector3 = new Vector3();
        modelInstance.transform.getTranslation(vector3);
        modelInstance.transform.set(vector3, quaternion, new Vector3(scaleX, scaleY, scaleZ));
        btRigidBody btrigidbody = this.physicsBodies.get(objectId);
        if (btrigidbody == null || this.editorMode) {
            return;
        }
        float invMass = btrigidbody.getInvMass() > 0.0f ? 1.0f / btrigidbody.getInvMass() : 0.0f;
        float friction = btrigidbody.getFriction();
        float restitution = btrigidbody.getRestitution();
        Vector3 linearVelocity = btrigidbody.getLinearVelocity();
        PhysicsState physicsState = invMass > 0.0f ? PhysicsState.DYNAMIC : PhysicsState.STATIC;
        setPhysicsState(objectId, physicsState, invMass);
        btRigidBody btrigidbody2 = this.physicsBodies.get(objectId);
        if (btrigidbody2 != null) {
            Matrix4 worldTransform = btrigidbody2.getWorldTransform();
            worldTransform.getTranslation(vector3);
            worldTransform.set(vector3, quaternion);
            btrigidbody2.setWorldTransform(worldTransform);
            btrigidbody2.getMotionState().setWorldTransform(worldTransform);
            btrigidbody2.setFriction(friction);
            btrigidbody2.setRestitution(restitution);
            if (physicsState == PhysicsState.DYNAMIC) {
                btrigidbody2.setLinearVelocity(linearVelocity);
            }
        }
    }

    public void createGrid(float size, int divisions) {
        Scene scene = this.gridScene;
        if (scene != null) {
            net.mgsx.gltf.scene3d.scene.SceneManager sceneManager = this.sceneManager;
            if (sceneManager != null) {
                sceneManager.removeScene(scene);
            }
            this.gridScene = null;
        }
        ModelInstance modelInstance = this.gridInstance;
        if (modelInstance != null) {
            modelInstance.model.dispose();
            this.gridInstance = null;
        }
        float f = size / divisions;
        this.gridInstance = new ModelInstance(new ModelBuilder().createLineGrid(divisions, divisions, f, f, new Material(), 3L));
        Scene scene2 = new Scene(this.gridInstance);
        this.gridScene = scene2;
        net.mgsx.gltf.scene3d.scene.SceneManager sceneManager2 = this.sceneManager;
        if (sceneManager2 != null) {
            sceneManager2.addScene(scene2);
        }
    }

    public float getDeltaTime() {
        return Gdx.graphics.getDeltaTime();
    }

    public void setShadowsEnabled(boolean enabled) {
        DirectionalLightEx directionalLightEx;
        if (this.sceneManager == null || (directionalLightEx = this.pbrLight) == null) {
            return;
        }
        boolean z = directionalLightEx instanceof DirectionalShadowLight;
        if (enabled && !z) {
            Color colorCpy = directionalLightEx.color.cpy();
            Vector3 vector3Cpy = this.pbrLight.direction.cpy();
            float f = this.pbrLight.intensity;
            this.sceneManager.environment.remove((DirectionalLight) this.pbrLight);
            recreateShadowLight();
            DirectionalLightEx directionalLightEx2 = this.pbrLight;
            if (directionalLightEx2 != null) {
                directionalLightEx2.direction.set(vector3Cpy);
                this.pbrLight.color.set(colorCpy);
                this.pbrLight.intensity = f;
                return;
            }
            return;
        }
        if (enabled || !z) {
            return;
        }
        DirectionalShadowLight directionalShadowLight = (DirectionalShadowLight) directionalLightEx;
        DirectionalLightEx directionalLightEx3 = new DirectionalLightEx();
        directionalLightEx3.direction.set(directionalShadowLight.direction);
        directionalLightEx3.color.set(directionalShadowLight.color);
        directionalLightEx3.intensity = directionalShadowLight.intensity;
        this.sceneManager.environment.remove((DirectionalLight) directionalShadowLight);
        directionalShadowLight.dispose();
        this.sceneManager.environment.add((DirectionalLight) directionalLightEx3);
        this.pbrLight = directionalLightEx3;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void bakeObjectsByPrefix(String prefix, String newId) {
        btRigidBody btrigidbody;
        ModelBuilder modelBuilder = new ModelBuilder();
        modelBuilder.begin();
        Array array = new Array();
        int i = 0;
        for (Map.Entry<String, ModelInstance> entry : this.sceneObjects.entrySet()) {
            String key = entry.getKey();
            if (key.startsWith(prefix) && !key.equals("player") && (btrigidbody = this.physicsBodies.get(key)) != null && btrigidbody.getInvMass() == 0.0f) {
                ModelInstance value = entry.getValue();
                Array.ArrayIterator<Node> it = value.nodes.iterator();
                while (it.hasNext()) {
                    Array.ArrayIterator<NodePart> it2 = it.next().parts.iterator();
                    while (it2.hasNext()) {
                        NodePart next = it2.next();
                        int i2 = i + 1;
                        MeshPartBuilder meshPartBuilderPart = modelBuilder.part("p" + i, next.meshPart.primitiveType, next.meshPart.mesh.getVertexAttributes(), next.material);
                        meshPartBuilderPart.setVertexTransform(value.transform);
                        meshPartBuilderPart.addMesh(next.meshPart);
                        i = i2;
                    }
                }
                array.add(key);
            }
        }
        if (i == 0) {
            modelBuilder.end();
            Gdx.f82app.log("Bake", "No static objects found with prefix: " + prefix);
            return;
        }
        ModelInstance modelInstance = new ModelInstance(modelBuilder.end());
        Array.ArrayIterator it3 = array.iterator();
        while (it3.hasNext()) {
            removeObject((String) it3.next());
        }
        this.sceneObjects.put(newId, modelInstance);
        if (this.realisticMode) {
            this.sceneManager.addScene(new Scene(modelInstance));
        }
        createGltfMeshPhysicsBody(newId, modelInstance);
        Gdx.f82app.log("Bake", "Optimized " + array.size + " objects into " + newId);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void bakeObjectsList(String newId, List<String> idsToBake) {
        ModelBuilder modelBuilder = new ModelBuilder();
        modelBuilder.begin();
        Array array = new Array();
        int i = 0;
        for (String str : idsToBake) {
            ModelInstance modelInstance = this.sceneObjects.get(str);
            if (modelInstance != null) {
                Array.ArrayIterator<Node> it = modelInstance.nodes.iterator();
                while (it.hasNext()) {
                    Array.ArrayIterator<NodePart> it2 = it.next().parts.iterator();
                    while (it2.hasNext()) {
                        NodePart next = it2.next();
                        int i2 = i + 1;
                        MeshPartBuilder meshPartBuilderPart = modelBuilder.part("p" + i, next.meshPart.primitiveType, next.meshPart.mesh.getVertexAttributes(), next.material);
                        meshPartBuilderPart.setVertexTransform(modelInstance.transform);
                        meshPartBuilderPart.addMesh(next.meshPart);
                        i = i2;
                    }
                }
                array.add(str);
            }
        }
        if (i == 0) {
            modelBuilder.end();
            return;
        }
        ModelInstance modelInstance2 = new ModelInstance(modelBuilder.end());
        Array.ArrayIterator it3 = array.iterator();
        while (it3.hasNext()) {
            removeObject((String) it3.next());
        }
        this.sceneObjects.put(newId, modelInstance2);
        if (this.realisticMode) {
            this.sceneManager.addScene(new Scene(modelInstance2));
        }
        createGltfMeshPhysicsBody(newId, modelInstance2);
    }

    public boolean createCube(String objectId) {
        net.mgsx.gltf.scene3d.scene.SceneManager sceneManager;
        if (this.sceneObjects.containsKey(objectId)) {
            return false;
        }
        Model modelCreateBox = this.loadedModels.get("__PRIMITIVE_CUBE__");
        if (modelCreateBox == null) {
            modelCreateBox = this.modelBuilder.createBox(50.0f, 50.0f, 50.0f, new Material(PBRColorAttribute.createBaseColorFactor(Color.WHITE)), 153L);
            this.loadedModels.put("__PRIMITIVE_CUBE__", modelCreateBox);
        }
        ModelInstance modelInstance = new ModelInstance(modelCreateBox);
        this.sceneObjects.put(objectId, modelInstance);
        if (this.realisticMode && (sceneManager = this.sceneManager) != null) {
            sceneManager.addScene(new Scene(modelInstance));
        }
        if (this.hiddenSpawnIds.contains(objectId)) {
            this.hiddenSpawnIds.remove(objectId);
            setObjectVisibility(objectId, false);
        }
        return true;
    }

    public void applyPBRMaterial(String objectId, MaterialComponent materialData) {
        ModelInstance modelInstance = this.sceneObjects.get(objectId);
        if (modelInstance == null || materialData == null) {
            return;
        }
        Array.ArrayIterator<Material> it = modelInstance.materials.iterator();
        while (it.hasNext()) {
            Material next = it.next();
            PBRColorAttribute pBRColorAttribute = (PBRColorAttribute) next.get(PBRColorAttribute.BaseColorFactor);
            if (pBRColorAttribute != null) {
                pBRColorAttribute.color.set(materialData.baseColor);
            } else {
                next.set(PBRColorAttribute.createBaseColorFactor(materialData.baseColor));
            }
            if (materialData.baseColor.a < 1.0f) {
                next.set(new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA));
            } else {
                next.remove(BlendingAttribute.Type);
            }
            if (materialData.baseColor.a < 0.05f) {
                next.set(new DepthTestAttribute(false));
            } else {
                next.set(new DepthTestAttribute(515, true));
            }
            applyTextureOrColor(next, materialData.baseColorTexturePath, PBRTextureAttribute.BaseColorTexture, PBRColorAttribute.BaseColorFactor, materialData.baseColor);
            next.set(PBRFloatAttribute.createMetallic(materialData.metallic));
            next.set(PBRFloatAttribute.createRoughness(materialData.roughness));
            applyTexture(next, materialData.metallicRoughnessTexturePath, PBRTextureAttribute.MetallicRoughnessTexture);
            applyTexture(next, materialData.normalTexturePath, PBRTextureAttribute.NormalTexture);
            applyTexture(next, materialData.occlusionTexturePath, PBRTextureAttribute.OcclusionTexture);
            applyTextureOrColor(next, materialData.emissiveTexturePath, PBRTextureAttribute.EmissiveTexture, PBRColorAttribute.Emissive, materialData.emissiveColor);
            for (Attribute attribute : next) {
                if (attribute instanceof TextureAttribute) {
                    TextureAttribute textureAttribute = (TextureAttribute) attribute;
                    textureAttribute.scaleU = materialData.uvScaleX;
                    textureAttribute.scaleV = materialData.uvScaleY;
                }
            }
        }
    }

    public void setTextureTiling(String objectId, float scaleU, float scaleV) {
        ModelInstance modelInstance = this.sceneObjects.get(objectId);
        if (modelInstance == null) {
            return;
        }
        Array.ArrayIterator<Material> it = modelInstance.materials.iterator();
        while (it.hasNext()) {
            for (Attribute attribute : it.next()) {
                if (attribute instanceof TextureAttribute) {
                    TextureAttribute textureAttribute = (TextureAttribute) attribute;
                    textureAttribute.scaleU = scaleU;
                    textureAttribute.scaleV = scaleV;
                }
            }
        }
    }

    private void applyTextureOrColor(Material material, String texturePath, long textureType, long colorType, Color color) {
        if (texturePath != null && !texturePath.isEmpty()) {
            Texture textureLoadTexture = loadTexture(texturePath);
            if (textureLoadTexture != null) {
                material.remove(colorType);
                material.set(new PBRTextureAttribute(textureType, textureLoadTexture));
                return;
            }
            return;
        }
        material.remove(textureType);
        material.set(new PBRColorAttribute(colorType, color));
    }

    private void applyTexture(Material material, String texturePath, long textureType) {
        if (texturePath != null && !texturePath.isEmpty()) {
            Texture textureLoadTexture = loadTexture(texturePath);
            if (textureLoadTexture != null) {
                material.set(new PBRTextureAttribute(textureType, textureLoadTexture));
                return;
            }
            return;
        }
        material.remove(textureType);
    }

    private Texture loadTexture(String textureFileName) {
        if (textureFileName == null || textureFileName.isEmpty()) {
            return null;
        }
        if (textureFileName.startsWith("buffer://")) {
            TextureRegion textureRegion = RenderTextureManager.INSTANCE.getTextureRegion(textureFileName.substring(9));
            if (textureRegion != null) {
                return textureRegion.getTexture();
            }
            return null;
        }
        Texture texture = this.loadedTextures.get(textureFileName);
        if (texture != null) {
            return texture;
        }
        try {
            File file = ProjectManager.getInstance().getCurrentProject().getFile(textureFileName);
            if (file == null || !file.exists()) {
                Gdx.f82app.error("3DManager", "Texture file not found in project: " + textureFileName);
                return null;
            }
            Texture texture2 = new Texture(Gdx.files.absolute(file.getAbsolutePath()));
            texture2.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
            this.loadedTextures.put(textureFileName, texture2);
            return texture2;
        } catch (Exception e) {
            Gdx.f82app.error("3DManager", "Could not load texture: " + textureFileName, e);
            return null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void disposeManagedBatches() {
        for (BillboardParticleBatch billboardParticleBatch : this.managedBatches.values()) {
            try {
                Field declaredField = BillboardParticleBatch.class.getDeclaredField("renderablePool");
                declaredField.setAccessible(true);
                Pool pool = (Pool) declaredField.get(billboardParticleBatch);
                Array array = new Array();
                if (pool == null) {
                    return;
                }
                pool.freeAll(array);
                Array.ArrayIterator it = array.iterator();
                while (it.hasNext()) {
                    Renderable renderable = (Renderable) it.next();
                    if (renderable.meshPart.mesh != null) {
                        renderable.meshPart.mesh.dispose();
                    }
                }
                pool.clear();
            } catch (Exception e) {
                Gdx.f82app.error("Dispose", "Failed to dispose meshes in BillboardParticleBatch via reflection", e);
            }
        }
        this.managedBatches.clear();
    }

    public Vector3 getPosition(String objectId) {
        ModelInstance modelInstance = this.sceneObjects.get(objectId);
        if (modelInstance == null) {
            return null;
        }
        Vector3 vector3 = new Vector3();
        modelInstance.transform.getTranslation(vector3);
        return vector3;
    }

    public Vector3 getRotation(String objectId) {
        ModelInstance modelInstance = this.sceneObjects.get(objectId);
        if (modelInstance == null) {
            return null;
        }
        Quaternion quaternion = new Quaternion();
        modelInstance.transform.getRotation(quaternion);
        return new Vector3(quaternion.getPitch(), quaternion.getYaw(), quaternion.getRoll());
    }

    public Vector3 getScale(String objectId) {
        ModelInstance modelInstance = this.sceneObjects.get(objectId);
        if (modelInstance == null) {
            return null;
        }
        Vector3 vector3 = new Vector3();
        modelInstance.transform.getScale(vector3);
        return vector3;
    }

    public Float getDistance(String id1, String id2) {
        if (getPosition(id1) == null || getPosition(id2) == null) {
            return Float.valueOf(-1.0f);
        }
        return Float.valueOf(getPosition(id1).dst(getPosition(id2)));
    }

    public void setFriction(String objectId, float friction) {
        btRigidBody btrigidbody = this.physicsBodies.get(objectId);
        if (btrigidbody != null) {
            if (friction > 9999.0f) {
                float f = friction - 10000.0f;
                btrigidbody.setDamping(f, f);
            } else {
                btrigidbody.setFriction(friction);
            }
            btrigidbody.activate();
        }
    }

    public void setRestitution(String objectId, float restitution) {
        btRigidBody btrigidbody = this.physicsBodies.get(objectId);
        if (btrigidbody != null) {
            btrigidbody.setRestitution(restitution);
            btrigidbody.activate();
        }
    }

    public void setContinuousCollisionDetection(String objectId, boolean enabled) {
        btRigidBody btrigidbody = this.physicsBodies.get(objectId);
        ModelInstance modelInstance = this.sceneObjects.get(objectId);
        if (btrigidbody == null || modelInstance == null || btrigidbody.isStaticObject()) {
            return;
        }
        if (enabled) {
            BoundingBox boundingBox = new BoundingBox();
            modelInstance.model.calculateBoundingBox(boundingBox);
            Vector3 vector3 = new Vector3();
            boundingBox.getDimensions(vector3);
            float fMin = Math.min(Math.min(vector3.x, vector3.y), vector3.z) * 0.5f;
            btrigidbody.setCcdMotionThreshold(fMin);
            btrigidbody.setCcdSweptSphereRadius(fMin);
            return;
        }
        btrigidbody.setCcdMotionThreshold(0.0f);
        btrigidbody.setCcdSweptSphereRadius(0.0f);
    }

    public void setCustomScreenShader(final String vertexCode, final String fragmentCode) {
        Gdx.f82app.postRunnable(new Runnable() { // from class: org.catrobat.catroid.raptor.ThreeDManager$$ExternalSyntheticLambda19
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5475xca23e692(vertexCode, fragmentCode);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$setCustomScreenShader$19$org-catrobat-catroid-raptor-ThreeDManager, reason: not valid java name */
    /* synthetic */ void m5475xca23e692(String str, String str2) {
        VfxManager vfxManager = this.vfxManager;
        if (vfxManager != null) {
            CustomScreenShaderEffect customScreenShaderEffect = this.customScreenEffect;
            if (customScreenShaderEffect != null) {
                vfxManager.removeEffect(customScreenShaderEffect);
                this.customScreenEffect.dispose();
                this.customScreenEffect = null;
            }
            if (str == null || str.isEmpty() || str2 == null || str2.isEmpty()) {
                PostProcessingComponent postProcessingComponent = this.currentConfig;
                if (postProcessingComponent == null || !postProcessingComponent.isActive) {
                    this.postprocessingEnabled = false;
                    return;
                }
                return;
            }
            try {
                CustomScreenShaderEffect customScreenShaderEffect2 = new CustomScreenShaderEffect(str, str2);
                this.customScreenEffect = customScreenShaderEffect2;
                this.vfxManager.addEffect(customScreenShaderEffect2);
                this.postprocessingEnabled = true;
            } catch (Exception e) {
                Gdx.f82app.error("CustomScreenShader", "Failed to compile: " + e.getMessage());
            }
        }
    }

    public void setShaderCode(final String vertexCode, final String fragmentCode) {
        Gdx.f82app.postRunnable(new Runnable() { // from class: org.catrobat.catroid.raptor.ThreeDManager$$ExternalSyntheticLambda13
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5476x2018fe38(vertexCode, fragmentCode);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$setShaderCode$20$org-catrobat-catroid-raptor-ThreeDManager, reason: not valid java name */
    /* synthetic */ void m5476x2018fe38(String str, String str2) {
        Gdx.f82app.log("ShaderDebug", "--- setShaderCode CALLED ---");
        if (str == null || str.isEmpty() || str2 == null || str2.isEmpty()) {
            resetSceneShader();
            return;
        }
        try {
            DefaultShader.Config config = new DefaultShader.Config(str, str2);
            ShaderProgram shaderProgram = new ShaderProgram(str, str2);
            if (!shaderProgram.isCompiled()) {
                throw new Exception("Shader compilation failed: " + shaderProgram.getLog());
            }
            shaderProgram.dispose();
            ShaderProvider shaderProvider = this.customShaderProvider;
            if (shaderProvider != null) {
                shaderProvider.dispose();
            }
            ModelBatch modelBatch = this.modelBatch;
            if (modelBatch != null) {
                modelBatch.dispose();
            }
            this.customShaderProvider = new CustomShaderProvider(config, this.customUniforms);
            this.modelBatch = new ModelBatch(this.customShaderProvider);
            Gdx.f82app.log("ShaderDebug", "--- CustomShaderProvider and new ModelBatch CREATED successfully ---");
        } catch (Exception e) {
            Gdx.f82app.error("3DManager", "Shader setup failed: " + e.getMessage());
            resetSceneShader();
        }
    }

    public void resetSceneShader() {
        Gdx.f82app.postRunnable(new Runnable() { // from class: org.catrobat.catroid.raptor.ThreeDManager$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5474x2ebee751();
            }
        });
    }

    /* JADX INFO: renamed from: lambda$resetSceneShader$21$org-catrobat-catroid-raptor-ThreeDManager, reason: not valid java name */
    /* synthetic */ void m5474x2ebee751() {
        ShaderProvider shaderProvider = this.customShaderProvider;
        if (shaderProvider != null) {
            shaderProvider.dispose();
            this.customShaderProvider = null;
        }
        ModelBatch modelBatch = this.modelBatch;
        if (modelBatch != null) {
            modelBatch.dispose();
        }
        this.customUniforms.clear();
        this.modelBatch = new ModelBatch(this.defaultShaderProvider);
    }

    public void setShaderUniform(String name2, float value) {
        if (name2 == null || name2.isEmpty()) {
            return;
        }
        this.customUniforms.put("u_" + name2, Float.valueOf(value));
    }

    public void setShaderUniform(String name2, float x, float y, float z) {
        if (name2 == null || name2.isEmpty()) {
            return;
        }
        this.customUniforms.put("u_" + name2, new Vector3(x, y, z));
    }

    public void setShaderUniform(String name2, int value) {
        if (name2 == null || name2.isEmpty()) {
            return;
        }
        this.customUniforms.put("u_" + name2, Integer.valueOf(value));
    }

    public void setShaderUniform(String name2, float x, float y) {
        if (name2 == null || name2.isEmpty()) {
            return;
        }
        this.customUniforms.put("u_" + name2, new Vector2(x, y));
    }

    public void setShaderUniform(String name2, float x, float y, float z, float w) {
        if (name2 == null || name2.isEmpty()) {
            return;
        }
        this.customUniforms.put("u_" + name2, new Vector4(x, y, z, w));
    }

    public Vector3 getSunLightDirection() {
        DirectionalLightEx directionalLightEx = this.pbrLight;
        if (directionalLightEx != null) {
            return directionalLightEx.direction;
        }
        return new Vector3(1.0f, -1.5f, 1.0f);
    }

    public Color getSunLightColor() {
        DirectionalLightEx directionalLightEx = this.pbrLight;
        if (directionalLightEx != null) {
            return directionalLightEx.color;
        }
        return Color.WHITE;
    }

    public float getSunLightIntensity() {
        DirectionalLightEx directionalLightEx = this.pbrLight;
        if (directionalLightEx != null) {
            return directionalLightEx.intensity;
        }
        return 0.0f;
    }

    public void setWorldTransform(String objectId, Matrix4 worldTransform) {
        ModelInstance modelInstance = this.sceneObjects.get(objectId);
        if (modelInstance == null) {
            modelInstance = this.editorProxies.get(objectId);
        }
        if (modelInstance != null) {
            modelInstance.transform.set(worldTransform);
            modelInstance.calculateTransforms();
        }
        btRigidBody btrigidbody = this.physicsBodies.get(objectId);
        if (btrigidbody == null || this.editorMode) {
            return;
        }
        Matrix4 worldTransform2 = btrigidbody.getWorldTransform();
        Vector3 vector3 = new Vector3();
        Quaternion quaternion = new Quaternion();
        worldTransform.getTranslation(vector3);
        worldTransform.getRotation(quaternion, true);
        worldTransform2.set(vector3, quaternion);
        btrigidbody.setWorldTransform(worldTransform2);
        if (btrigidbody.getMotionState() != null) {
            btrigidbody.getMotionState().setWorldTransform(worldTransform2);
        }
        btrigidbody.activate();
        if (btrigidbody.isStaticObject() || btrigidbody.isKinematicObject()) {
            this.dynamicsWorld.updateSingleAabb(btrigidbody);
        }
    }

    private void applyUniformsToScreenShader() {
        if (this.customScreenEffect == null) {
            return;
        }
        try {
            Field declaredField = ShaderVfxEffect.class.getDeclaredField(ConfigConstants.CONFIG_KEY_PROGRAM);
            declaredField.setAccessible(true);
            ShaderProgram shaderProgram = (ShaderProgram) declaredField.get(this.customScreenEffect);
            if (shaderProgram == null || !shaderProgram.isCompiled()) {
                return;
            }
            shaderProgram.begin();
            for (Map.Entry<String, Object> entry : this.customUniforms.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (value instanceof Float) {
                    shaderProgram.setUniformf(key, ((Float) value).floatValue());
                } else if (value instanceof Vector2) {
                    Vector2 vector2 = (Vector2) value;
                    shaderProgram.setUniformf(key, vector2.x, vector2.y);
                } else if (value instanceof Vector3) {
                    Vector3 vector3 = (Vector3) value;
                    shaderProgram.setUniformf(key, vector3.x, vector3.y, vector3.z);
                } else if (value instanceof Integer) {
                    shaderProgram.setUniformi(key, ((Integer) value).intValue());
                }
            }
        } catch (Exception e) {
            Log.e("ThreeDManager", "Failed to apply uniforms to custom screen shader", e);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void clearScene() {
        Array array = new Array();
        Iterator<String> it = this.sceneObjects.keySet().iterator();
        while (it.hasNext()) {
            array.add(it.next());
        }
        Array.ArrayIterator it2 = array.iterator();
        while (it2.hasNext()) {
            removeObject((String) it2.next());
        }
        this.sceneObjects.clear();
        for (SceneAsset sceneAsset : this.loadedGltfAssets.values()) {
            if (sceneAsset != null) {
                sceneAsset.dispose();
            }
        }
        this.loadedGltfAssets.clear();
        Array array2 = new Array();
        Iterator<String> it3 = this.physicsBodies.keySet().iterator();
        while (it3.hasNext()) {
            array2.add(it3.next());
        }
        Array.ArrayIterator it4 = array2.iterator();
        while (it4.hasNext()) {
            removePhysicsBody((String) it4.next());
        }
        this.physicsBodies.clear();
        this.physicsResources.clear();
        Iterator<ParticleSystem3DRuntime> it5 = this.activeParticleRuntimes3D.values().iterator();
        while (it5.hasNext()) {
            it5.next().dispose();
        }
        this.activeParticleRuntimes3D.clear();
        SceneSkybox sceneSkybox = this.skybox;
        if (sceneSkybox != null) {
            sceneSkybox.dispose();
            this.skybox = null;
        }
        Cubemap cubemap = this.skyboxCubemap;
        if (cubemap != null) {
            cubemap.dispose();
            this.skyboxCubemap = null;
        }
        Cubemap cubemap2 = this.diffuseCubemap;
        if (cubemap2 != null) {
            cubemap2.dispose();
            this.diffuseCubemap = null;
        }
        Cubemap cubemap3 = this.specularCubemap;
        if (cubemap3 != null) {
            cubemap3.dispose();
            this.specularCubemap = null;
        }
        Scene scene = this.gridScene;
        if (scene != null) {
            net.mgsx.gltf.scene3d.scene.SceneManager sceneManager = this.sceneManager;
            if (sceneManager != null) {
                sceneManager.removeScene(scene);
            }
            this.gridScene = null;
        }
        ModelInstance modelInstance = this.gridInstance;
        if (modelInstance != null) {
            modelInstance.model.dispose();
            this.gridInstance = null;
        }
        Array array3 = new Array();
        Iterator<String> it6 = this.physicsConstraints.keySet().iterator();
        while (it6.hasNext()) {
            array3.add(it6.next());
        }
        Array.ArrayIterator it7 = array3.iterator();
        while (it7.hasNext()) {
            removeConstraint((String) it7.next());
        }
        if (this.particleSystemInitialized) {
            disposeManagedBatches();
            this.particleSystem = new ParticleSystem();
        }
        this.pointLights.clear();
        this.editorProxies.clear();
        this.spotLights.clear();
        this.directionalLights.clear();
        net.mgsx.gltf.scene3d.scene.SceneManager sceneManager2 = this.sceneManager;
        if (sceneManager2 != null) {
            sceneManager2.environment.clear();
            try {
                setupDefaultLighting();
            } catch (Exception e) {
                Log.e("ThreeDManager", "Couldn't setup lightning: " + e);
            }
        }
        this.aiLastKnownPositions.clear();
        this.aiHasLostTarget.clear();
        this.aiSmoothedDirection.clear();
        this.animationControllers.clear();
        this.gltfObjectIds.clear();
        this.rayCastResults.clear();
        this.cameraAttachments.clear();
        this.aiPathHistory.clear();
        this.aiDeadEnds.clear();
        this.aiLastTargetPos.clear();
        this.aiBacktrackTarget.clear();
        this.modelRotationOffsets.clear();
        this.aiSlideDirs.clear();
        this.aiCurrentYaws.clear();
        this.cameraTargetId = null;
        this.cameraYaw = 0.0f;
        this.cameraPitch = 0.0f;
        this.cameraDistance = 10.0f;
        this.cameraOffset.set(0.0f, 0.0f, 0.0f);
        this.cameraTrackTargetId = null;
        this.cameraTrackMode = 0;
        this.cameraTrackPosOffset.set(0.0f, 0.0f, 0.0f);
        this.cameraTrackRotOffset.idt();
        this.activePointerId = -1;
        this.touchRotationEnabled = false;
        this.shakeTimer = 0.0f;
        this.shakeIntensity = 0.0f;
        this.currentShakeOffset.set(0.0f, 0.0f, 0.0f);
        PerspectiveCamera perspectiveCamera = this.camera;
        if (perspectiveCamera != null) {
            perspectiveCamera.position.set(0.0f, 0.0f, 0.0f);
            this.camera.direction.set(0.0f, 0.0f, -1.0f);
            this.camera.up.set(0.0f, 1.0f, 0.0f);
            this.camera.fieldOfView = 67.0f;
            this.camera.update();
        }
    }

    @Override // com.badlogic.gdx.utils.Disposable
    public void dispose() {
        VfxManager vfxManager;
        if (this.disposed) {
            return;
        }
        this.disposed = true;
        clearScene();
        this.physicsConstraints.clear();
        FrameBuffer frameBuffer = this.depthFbo;
        if (frameBuffer != null) {
            frameBuffer.dispose();
            this.depthFbo = null;
        }
        FrameBuffer frameBuffer2 = this.materialFbo;
        if (frameBuffer2 != null) {
            frameBuffer2.dispose();
            this.materialFbo = null;
        }
        ModelBatch modelBatch = this.materialBatch;
        if (modelBatch != null) {
            modelBatch.dispose();
            this.materialBatch = null;
        }
        for (BufferPipeline bufferPipeline : this.bufferPipelines.values()) {
            if (bufferPipeline != null) {
                bufferPipeline.dispose();
            }
        }
        this.bufferPipelines.clear();
        if (this.isVfxCapturing && (vfxManager = this.vfxManager) != null) {
            try {
                vfxManager.endInputCapture();
            } catch (Exception unused) {
            }
            this.isVfxCapturing = false;
        }
        SsaoEffect ssaoEffect = this.ssaoEffect;
        if (ssaoEffect != null) {
            ssaoEffect.dispose();
            this.ssaoEffect = null;
        }
        SsrRayTracingEffect ssrRayTracingEffect = this.rayTracingEffect;
        if (ssrRayTracingEffect != null) {
            ssrRayTracingEffect.dispose();
            this.rayTracingEffect = null;
        }
        SsgiEffect ssgiEffect = this.ssgiEffect;
        if (ssgiEffect != null) {
            ssgiEffect.dispose();
            this.ssgiEffect = null;
        }
        GodRaysEffect godRaysEffect = this.godRaysEffect;
        if (godRaysEffect != null) {
            godRaysEffect.dispose();
            this.godRaysEffect = null;
        }
        HeightFogEffect heightFogEffect = this.heightFogEffect;
        if (heightFogEffect != null) {
            heightFogEffect.dispose();
            this.heightFogEffect = null;
        }
        VolumetricFogEffect volumetricFogEffect = this.volumetricFogEffect;
        if (volumetricFogEffect != null) {
            volumetricFogEffect.dispose();
            this.volumetricFogEffect = null;
        }
        DepthOfFieldEffect depthOfFieldEffect = this.dofEffect;
        if (depthOfFieldEffect != null) {
            depthOfFieldEffect.dispose();
            this.dofEffect = null;
        }
        LinearizeEffect linearizeEffect = this.linearizeEffect;
        if (linearizeEffect != null) {
            linearizeEffect.dispose();
            this.linearizeEffect = null;
        }
        CustomScreenShaderEffect customScreenShaderEffect = this.customScreenEffect;
        if (customScreenShaderEffect != null) {
            customScreenShaderEffect.dispose();
            this.customScreenEffect = null;
        }
        Model model = this.particleProxyModel;
        if (model != null) {
            model.dispose();
        }
        ModelBatch modelBatch2 = this.modelBatch;
        if (modelBatch2 != null) {
            modelBatch2.dispose();
        }
        Texture texture = this.defaultParticleTexture;
        if (texture != null) {
            texture.dispose();
        }
        ModelBatch modelBatch3 = this.particleModelBatch;
        if (modelBatch3 != null) {
            modelBatch3.dispose();
        }
        if (this.particleSystemInitialized) {
            disposeManagedBatches();
        }
        SpriteBatch spriteBatch = this.blitBatch;
        if (spriteBatch != null) {
            spriteBatch.dispose();
        }
        IBLBuilderCompat iBLBuilderCompat = this.iblBuilderCompat;
        if (iBLBuilderCompat != null) {
            iBLBuilderCompat.dispose();
        }
        EyeAdaptationManager eyeAdaptationManager = this.eyeAdaptationManager;
        if (eyeAdaptationManager != null) {
            eyeAdaptationManager.dispose();
        }
        ExposureEffect exposureEffect = this.exposureEffect;
        if (exposureEffect != null) {
            exposureEffect.dispose();
        }
        TonemappingEffect tonemappingEffect = this.tonemappingEffect;
        if (tonemappingEffect != null) {
            tonemappingEffect.dispose();
        }
        ShaderProvider shaderProvider = this.defaultShaderProvider;
        if (shaderProvider != null) {
            shaderProvider.dispose();
        }
        ShaderProvider shaderProvider2 = this.customShaderProvider;
        if (shaderProvider2 != null) {
            shaderProvider2.dispose();
        }
        net.mgsx.gltf.scene3d.scene.SceneManager sceneManager = this.sceneManager;
        if (sceneManager != null) {
            sceneManager.dispose();
        }
        Model model2 = this.lightProxyModel;
        if (model2 != null) {
            model2.dispose();
        }
        Model model3 = this.cameraProxyModel;
        if (model3 != null) {
            model3.dispose();
        }
        Model model4 = this.wireframeBoxModel;
        if (model4 != null) {
            model4.dispose();
        }
        Model model5 = this.wireframeSphereModel;
        if (model5 != null) {
            model5.dispose();
        }
        Model model6 = this.wireframeCylinderModel;
        if (model6 != null) {
            model6.dispose();
        }
        ModelBatch modelBatch4 = this.wireframeBatch;
        if (modelBatch4 != null) {
            modelBatch4.dispose();
        }
        Texture texture2 = this.brdfLUT;
        if (texture2 != null) {
            texture2.dispose();
        }
        PanoramicConverter panoramicConverter = this.panoramicConverter;
        if (panoramicConverter != null) {
            panoramicConverter.dispose();
        }
        DebugDrawer debugDrawer = this.debugDrawer;
        if (debugDrawer != null) {
            debugDrawer.dispose();
        }
        btConstraintSolver btconstraintsolver = this.solver;
        if (btconstraintsolver != null) {
            btconstraintsolver.dispose();
        }
        btBroadphaseInterface btbroadphaseinterface = this.broadphase;
        if (btbroadphaseinterface != null) {
            btbroadphaseinterface.dispose();
        }
        btDispatcher btdispatcher = this.dispatcher;
        if (btdispatcher != null) {
            btdispatcher.dispose();
        }
        btCollisionConfiguration btcollisionconfiguration = this.collisionConfig;
        if (btcollisionconfiguration != null) {
            btcollisionconfiguration.dispose();
        }
        CollisionCallback collisionCallback = this.collisionCallback;
        if (collisionCallback != null) {
            collisionCallback.dispose();
        }
        FrameBuffer frameBuffer3 = this.sceneFbo2;
        if (frameBuffer3 != null) {
            frameBuffer3.dispose();
        }
        VfxManager vfxManager2 = this.vfxManager;
        if (vfxManager2 != null) {
            vfxManager2.dispose();
        }
        CascadeShadowMap cascadeShadowMap = this.csm;
        if (cascadeShadowMap != null) {
            cascadeShadowMap.dispose();
        }
        AsyncExecutor asyncExecutor = this.voxelExecutor;
        if (asyncExecutor != null) {
            asyncExecutor.dispose();
        }
        ShaderProgram shaderProgram = this.fsrShader;
        if (shaderProgram != null) {
            shaderProgram.dispose();
        }
        ShaderProgram shaderProgram2 = this.depthShader;
        if (shaderProgram2 != null) {
            shaderProgram2.dispose();
        }
        BloomEffect bloomEffect = this.cachedBloomEffect;
        if (bloomEffect != null) {
            bloomEffect.dispose();
            this.cachedBloomEffect = null;
        }
        GaussianBlurEffect gaussianBlurEffect = this.cachedGaussianBlurEffect;
        if (gaussianBlurEffect != null) {
            gaussianBlurEffect.dispose();
            this.cachedGaussianBlurEffect = null;
        }
        MotionBlurEffect motionBlurEffect = this.cachedMotionBlurEffect;
        if (motionBlurEffect != null) {
            motionBlurEffect.dispose();
            this.cachedMotionBlurEffect = null;
        }
        VignettingEffect vignettingEffect = this.cachedVignetteEffect;
        if (vignettingEffect != null) {
            vignettingEffect.dispose();
            this.cachedVignetteEffect = null;
        }
        LevelsEffect levelsEffect = this.cachedLevelsEffect;
        if (levelsEffect != null) {
            levelsEffect.dispose();
            this.cachedLevelsEffect = null;
        }
        SsgiEffect ssgiEffect2 = this.ssgiEffect;
        if (ssgiEffect2 != null) {
            ssgiEffect2.dispose();
            this.ssgiEffect = null;
        }
        btDiscreteDynamicsWorld btdiscretedynamicsworld = this.dynamicsWorld;
        if (btdiscretedynamicsworld != null) {
            btdiscretedynamicsworld.dispose();
            this.dynamicsWorld = null;
        }
        Iterator<ParticleSystem3DRuntime> it = this.activeParticleRuntimes3D.values().iterator();
        while (it.hasNext()) {
            it.next().dispose();
        }
        this.activeParticleRuntimes3D.clear();
        for (ParticleEffect particleEffect : this.activeParticleEffects.values()) {
            if (particleEffect != null) {
                particleEffect.dispose();
            }
        }
        for (Model model7 : this.loadedModels.values()) {
            if (model7 != null) {
                model7.dispose();
            }
        }
        for (Texture texture3 : this.loadedTextures.values()) {
            if (texture3 != null) {
                texture3.dispose();
            }
        }
        for (PlayableAudio playableAudio : this.active3DSounds) {
            if (playableAudio != null) {
                playableAudio.stop();
                playableAudio.dispose();
            }
        }
        for (AudioAsset audioAsset : this.loadedAudioAssets.values()) {
            if (audioAsset != null) {
                audioAsset.dispose();
            }
        }
        this.managedBatches.clear();
        this.activeParticleEffects.clear();
        this.effectsNormal.clear();
        this.effectsAdditive.clear();
        this.loadedModels.clear();
        this.loadedTextures.clear();
        this.active3DSounds.clear();
        this.loadedAudioAssets.clear();
        this.aiLastKnownPositions.clear();
        this.aiHasLostTarget.clear();
        this.aiSmoothedDirection.clear();
        this.modelBatch = null;
        this.dynamicsWorld = null;
        this.sceneManager = null;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/ThreeDManager$CollisionCallback.class
      classes14.dex
     */
    private class CollisionCallback extends ContactResultCallback {
        public boolean collided;

        private CollisionCallback() {
            this.collided = false;
        }

        @Override // com.badlogic.gdx.physics.bullet.collision.ContactResultCallback
        public float addSingleResult(btManifoldPoint cp, btCollisionObjectWrapper colObj0Wrap, int partId0, int index0, btCollisionObjectWrapper colObj1Wrap, int partId1, int index1) {
            this.collided = true;
            return 0.0f;
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/ThreeDManager$NameAccumulatingContactCallback.class
      classes14.dex
     */
    private class NameAccumulatingContactCallback extends ContactResultCallback {
        public StringBuilder namesBuilder;
        private String targetId;

        private NameAccumulatingContactCallback() {
            this.namesBuilder = new StringBuilder();
        }

        public void setTargetId(String targetId) {
            this.targetId = targetId;
            this.namesBuilder.setLength(0);
        }

        @Override // com.badlogic.gdx.physics.bullet.collision.ContactResultCallback
        public float addSingleResult(btManifoldPoint cp, btCollisionObjectWrapper colObj0Wrap, int partId0, int index0, btCollisionObjectWrapper colObj1Wrap, int partId1, int index1) {
            String str;
            btCollisionObject collisionObject = colObj0Wrap.getCollisionObject();
            btCollisionObject collisionObject2 = colObj1Wrap.getCollisionObject();
            Iterator it = ThreeDManager.this.physicsBodies.entrySet().iterator();
            while (true) {
                if (!it.hasNext()) {
                    str = null;
                    break;
                }
                Map.Entry entry = (Map.Entry) it.next();
                if (((btRigidBody) entry.getValue()).equals(collisionObject) && !((String) entry.getKey()).equals(this.targetId)) {
                    str = (String) entry.getKey();
                    break;
                }
            }
            if (str == null) {
                for (Map.Entry entry2 : ThreeDManager.this.physicsBodies.entrySet()) {
                    if (((btRigidBody) entry2.getValue()).equals(collisionObject2) && !((String) entry2.getKey()).equals(this.targetId)) {
                        str = (String) entry2.getKey();
                        break;
                    }
                }
            }
            if (str == null || this.namesBuilder.indexOf(str) != -1) {
                return 0.0f;
            }
            if (this.namesBuilder.length() > 0) {
                this.namesBuilder.append("\n");
            }
            this.namesBuilder.append(str);
            return 0.0f;
        }
    }

    public String getPhysicsCollisionsList(String objectId) {
        btRigidBody btrigidbody = this.physicsBodies.get(objectId);
        if (btrigidbody == null) {
            return "";
        }
        this.contactListCallback.setTargetId(objectId);
        this.dynamicsWorld.contactTest(btrigidbody, this.contactListCallback);
        return this.contactListCallback.namesBuilder.toString();
    }

    public String getIntersectionCollisionsList(String objectId) {
        ModelInstance modelInstance = this.sceneObjects.get(objectId);
        if (modelInstance == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        modelInstance.calculateBoundingBox(this.bounds1);
        this.bounds1.mul(modelInstance.transform);
        for (Map.Entry<String, ModelInstance> entry : this.sceneObjects.entrySet()) {
            String key = entry.getKey();
            if (!key.equals(objectId)) {
                ModelInstance value = entry.getValue();
                value.calculateBoundingBox(this.bounds2);
                this.bounds2.mul(value.transform);
                if (this.bounds1.intersects(this.bounds2)) {
                    if (sb.length() > 0) {
                        sb.append("\n");
                    }
                    sb.append(key);
                }
            }
        }
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/ThreeDManager$BoneOverride.class
      classes14.dex
     */
    static class BoneOverride {
        String boneName;
        String modelId;
        String targetId;

        public BoneOverride(String m, String b, String t) {
            this.modelId = m;
            this.boneName = b;
            this.targetId = t;
        }
    }

    static /* synthetic */ boolean lambda$bindBoneToObject$22(String str, String str2, BoneOverride boneOverride) {
        return boneOverride.modelId.equals(str) && boneOverride.boneName.equals(str2);
    }

    public void bindBoneToObject(final String modelId, final String boneName, String targetId) {
        this.boneOverrides.removeIf(new Predicate() { // from class: org.catrobat.catroid.raptor.ThreeDManager$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ThreeDManager.lambda$bindBoneToObject$22(modelId, boneName, (ThreeDManager.BoneOverride) obj);
            }
        });
        if (targetId == null || targetId.isEmpty()) {
            return;
        }
        this.boneOverrides.add(new BoneOverride(modelId, boneName, targetId));
    }

    /* JADX WARN: Code duplicated, block: B:87:0x0289 A[PHI: r0
      0x0289: PHI (r0v11 com.badlogic.gdx.graphics.OrthographicCamera) = (r0v7 com.badlogic.gdx.graphics.OrthographicCamera), (r0v19 com.badlogic.gdx.graphics.OrthographicCamera) binds: [B:86:0x0287, B:72:0x0248] A[DONT_GENERATE, DONT_INLINE]] */
    public void renderSceneForCustomCamera(PerspectiveCamera customCamera, FrameBuffer targetFbo, boolean useVfx) {
        net.mgsx.gltf.scene3d.scene.SceneManager sceneManager;
        OrthographicCamera orthographicCamera;
        net.mgsx.gltf.scene3d.scene.SceneManager sceneManager2;
        BufferPipeline bufferPipeline;
        net.mgsx.gltf.scene3d.scene.SceneManager sceneManager3;
        net.mgsx.gltf.scene3d.scene.SceneManager sceneManager4;
        net.mgsx.gltf.scene3d.scene.SceneManager sceneManager5;
        net.mgsx.gltf.scene3d.scene.SceneManager sceneManager6;
        PerspectiveCamera perspectiveCamera = this.camera;
        this.camera = customCamera;
        if (this.realisticMode && (sceneManager6 = this.sceneManager) != null) {
            sceneManager6.setCamera(customCamera);
            this.sceneManager.updateViewport(targetFbo.getWidth(), targetFbo.getHeight());
            this.sceneManager.update(0.0f);
        }
        customCamera.update();
        try {
            try {
                updateShadowLight();
                if (this.realisticMode && (sceneManager5 = this.sceneManager) != null) {
                    sceneManager5.renderShadows();
                }
                if (useVfx) {
                    try {
                        bufferPipeline = this.bufferPipelines.get(targetFbo);
                    } catch (Exception e) {
                        e = e;
                        Log.e("ThreeDManager", "Critical error in buffer rendering", e);
                        try {
                            targetFbo.end();
                        } catch (Exception unused) {
                        }
                        this.camera = perspectiveCamera;
                        if (this.realisticMode && (sceneManager2 = this.sceneManager) != null) {
                            sceneManager2.setCamera(perspectiveCamera);
                            this.sceneManager.updateViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
                            this.sceneManager.update(0.0f);
                        }
                        updateShadowLight();
                        orthographicCamera = this.blitCamera;
                        if (orthographicCamera != null) {
                            orthographicCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
                            this.blitCamera.update();
                        }
                    }
                } else {
                    bufferPipeline = null;
                }
                if (bufferPipeline != null && bufferPipeline.vfxManager != null && this.postprocessingEnabled) {
                    renderDepthAndMaterial(customCamera, bufferPipeline.depthFbo, bufferPipeline.materialFbo);
                    Texture colorBufferTexture = bufferPipeline.depthFbo.getColorBufferTexture();
                    Texture colorBufferTexture2 = bufferPipeline.materialFbo.getColorBufferTexture();
                    if (bufferPipeline.ssaoEffect != null) {
                        bufferPipeline.ssaoEffect.setCamera(customCamera);
                        bufferPipeline.ssaoEffect.setDepthTexture(colorBufferTexture);
                    }
                    if (bufferPipeline.rayTracingEffect != null) {
                        bufferPipeline.rayTracingEffect.setCamera(customCamera);
                        bufferPipeline.rayTracingEffect.setDepthTexture(colorBufferTexture);
                        bufferPipeline.rayTracingEffect.setMaterialTexture(colorBufferTexture2);
                    }
                    if (bufferPipeline.ssgiEffect != null) {
                        bufferPipeline.ssgiEffect.setCamera(customCamera);
                        bufferPipeline.ssgiEffect.setDepthTexture(colorBufferTexture);
                    }
                    if (bufferPipeline.godRaysEffect != null) {
                        bufferPipeline.godRaysEffect.setCamera(customCamera);
                        bufferPipeline.godRaysEffect.setDepthTexture(colorBufferTexture);
                    }
                    if (bufferPipeline.heightFogEffect != null) {
                        bufferPipeline.heightFogEffect.setCamera(customCamera);
                        bufferPipeline.heightFogEffect.setDepthTexture(colorBufferTexture);
                    }
                    if (bufferPipeline.volumetricFogEffect != null) {
                        bufferPipeline.volumetricFogEffect.setCamera(customCamera);
                        bufferPipeline.volumetricFogEffect.setDepthTexture(colorBufferTexture);
                    }
                    if (bufferPipeline.dofEffect != null) {
                        bufferPipeline.dofEffect.setCamera(customCamera);
                        bufferPipeline.dofEffect.setDepthTexture(colorBufferTexture);
                    }
                    targetFbo.begin();
                    Gdx.gl.glViewport(0, 0, targetFbo.getWidth(), targetFbo.getHeight());
                    Gdx.gl.glClearColor(this.skyColor.r, this.skyColor.g, this.skyColor.b, this.skyColor.a);
                    Gdx.gl.glClear(16640);
                    renderColorsOnly(targetFbo.getWidth(), targetFbo.getHeight());
                    targetFbo.end();
                    bufferPipeline.vfxManager.cleanUpBuffers();
                    bufferPipeline.vfxManager.useAsInput(targetFbo.getColorBufferTexture());
                    bufferPipeline.vfxManager.update(Gdx.graphics.getDeltaTime());
                    bufferPipeline.vfxManager.applyEffects();
                    targetFbo.begin();
                    Gdx.gl.glViewport(0, 0, targetFbo.getWidth(), targetFbo.getHeight());
                    Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
                    Gdx.gl.glClear(16640);
                    Texture texture = bufferPipeline.vfxManager.getResultBuffer().getTexture();
                    this.blitBatch.begin();
                    this.blitBatch.draw(texture, 0.0f, 0.0f, targetFbo.getWidth(), targetFbo.getHeight(), 0, 0, texture.getWidth(), texture.getHeight(), false, true);
                    this.blitBatch.end();
                    targetFbo.end();
                } else {
                    targetFbo.begin();
                    Gdx.gl.glViewport(0, 0, targetFbo.getWidth(), targetFbo.getHeight());
                    Gdx.gl.glClearColor(this.skyColor.r, this.skyColor.g, this.skyColor.b, this.skyColor.a);
                    Gdx.gl.glClear(16640);
                    if (this.realisticMode && (sceneManager3 = this.sceneManager) != null) {
                        sceneManager3.renderMirror();
                        this.sceneManager.renderTransmission();
                        this.sceneManager.renderColors();
                    } else {
                        this.modelBatch.begin(customCamera);
                        for (Map.Entry<String, ModelInstance> entry : this.sceneObjects.entrySet()) {
                            if (!this.inactiveRenderObjects.contains(entry.getKey())) {
                                this.modelBatch.render(entry.getValue(), this.environment);
                            }
                        }
                        this.modelBatch.end();
                    }
                    if (this.particleSystemInitialized && !this.activeParticleEffects.isEmpty()) {
                        this.particleModelBatch.begin(customCamera);
                        this.particleModelBatch.render(this.particleSystem);
                        this.particleModelBatch.end();
                    }
                    targetFbo.end();
                }
                this.camera = perspectiveCamera;
                if (this.realisticMode && (sceneManager4 = this.sceneManager) != null) {
                    sceneManager4.setCamera(perspectiveCamera);
                    this.sceneManager.updateViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
                    this.sceneManager.update(0.0f);
                }
                updateShadowLight();
                orthographicCamera = this.blitCamera;
                if (orthographicCamera != null) {
                    orthographicCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
                    this.blitCamera.update();
                }
            } catch (Exception e2) {
                e = e2;
            }
            Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        } catch (Throwable th) {
            this.camera = perspectiveCamera;
            if (this.realisticMode && (sceneManager = this.sceneManager) != null) {
                sceneManager.setCamera(perspectiveCamera);
                this.sceneManager.updateViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
                this.sceneManager.update(0.0f);
            }
            updateShadowLight();
            OrthographicCamera orthographicCamera2 = this.blitCamera;
            if (orthographicCamera2 != null) {
                orthographicCamera2.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
                this.blitCamera.update();
            }
            Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            throw th;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:16:0x009a  */
    public void updateVoxelMesh(final String objectId, final VoxelManager.VoxelBuffer buffer, final String texturePath, final int atlasWidth, final int atlasHeight) {
        ModelBuilder modelBuilder = new ModelBuilder();
        modelBuilder.begin();
        int i = 0;
        Material material = new Material(PBRTextureAttribute.createBaseColorTexture(loadTexture(texturePath)));
        material.set(FloatAttribute.createAlphaTest(0.5f));
        material.set(PBRFloatAttribute.createMetallic(0.0f));
        float f = 1.0f;
        material.set(PBRFloatAttribute.createRoughness(1.0f));
        MeshPartBuilder meshPartBuilderPart = modelBuilder.part("voxel_part", 4, 25L, material);
        float f2 = 1.0f / atlasWidth;
        float f3 = 1.0f / atlasHeight;
        int i2 = 0;
        while (i2 < buffer.getSizeX()) {
            int i3 = i;
            while (i3 < buffer.getSizeY()) {
                int i4 = i;
                while (i4 < buffer.getSizeZ()) {
                    short s = buffer.get(i2, i3, i4);
                    if (s > 0) {
                        VoxelManager.BlockConfig config = VoxelManager.INSTANCE.getConfig(s);
                        float texX = config.getTexX() * f2;
                        float texY = (f - (config.getTexY() * f3)) - f3;
                        meshPartBuilderPart.setUVRange(texX, texY, texX + f2, texY + f3);
                        switch (AnonymousClass6.$SwitchMap$org$catrobat$catroid$raptor$VoxelManager$VoxelShape[config.getShape().ordinal()]) {
                            case 1:
                                int i5 = i3 + 1;
                                if (buffer.get(i2, i5, i4) == 0) {
                                    float f4 = i2;
                                    float f5 = i5;
                                    float f6 = i4 + 1;
                                    float f7 = i2 + 1;
                                    float f8 = i4;
                                    meshPartBuilderPart.rect(f4, f5, f6, f7, f5, f6, f7, f5, f8, f4, f5, f8, 0.0f, 1.0f, 0.0f);
                                }
                                if (buffer.get(i2, i3 - 1, i4) == 0) {
                                    float f9 = i2;
                                    float f10 = i3;
                                    float f11 = i4;
                                    float f12 = i2 + 1;
                                    float f13 = i4 + 1;
                                    meshPartBuilderPart.rect(f9, f10, f11, f12, f10, f11, f12, f10, f13, f9, f10, f13, 0.0f, -1.0f, 0.0f);
                                }
                                int i6 = i4 + 1;
                                if (buffer.get(i2, i3, i6) == 0) {
                                    float f14 = i2;
                                    float f15 = i3;
                                    float f16 = i6;
                                    float f17 = i2 + 1;
                                    float f18 = i5;
                                    meshPartBuilderPart.rect(f14, f15, f16, f17, f15, f16, f17, f18, f16, f14, f18, f16, 0.0f, 0.0f, 1.0f);
                                }
                                if (buffer.get(i2, i3, i4 - 1) == 0) {
                                    float f19 = i2 + 1;
                                    float f20 = i3;
                                    float f21 = i4;
                                    float f22 = i2;
                                    float f23 = i5;
                                    meshPartBuilderPart.rect(f19, f20, f21, f22, f20, f21, f22, f23, f21, f19, f23, f21, 0.0f, 0.0f, -1.0f);
                                }
                                if (buffer.get(i2 - 1, i3, i4) == 0) {
                                    float f24 = i2;
                                    float f25 = i3;
                                    float f26 = i4;
                                    float f27 = i6;
                                    float f28 = i5;
                                    meshPartBuilderPart.rect(f24, f25, f26, f24, f25, f27, f24, f28, f27, f24, f28, f26, -1.0f, 0.0f, 0.0f);
                                }
                                int i7 = i2 + 1;
                                if (buffer.get(i7, i3, i4) == 0) {
                                    float f29 = i7;
                                    float f30 = i3;
                                    float f31 = i6;
                                    float f32 = i4;
                                    float f33 = i5;
                                    meshPartBuilderPart.rect(f29, f30, f31, f29, f30, f32, f29, f33, f32, f29, f33, f31, 1.0f, 0.0f, 0.0f);
                                }
                                break;
                            case 2:
                                float f34 = i2;
                                float f35 = i3;
                                float f36 = i4;
                                float f37 = i2 + 1;
                                float f38 = i4 + 1;
                                float f39 = i3 + 1;
                                meshPartBuilderPart.rect(f34, f35, f36, f37, f35, f38, f37, f39, f38, f34, f39, f36, 1.0f, 0.0f, 1.0f);
                                meshPartBuilderPart.rect(f34, f39, f36, f37, f39, f38, f37, f35, f38, f34, f35, f36, -1.0f, 0.0f, -1.0f);
                                meshPartBuilderPart.rect(f37, f35, f36, f34, f35, f38, f34, f39, f38, f37, f39, f36, -1.0f, 0.0f, 1.0f);
                                meshPartBuilderPart.rect(f37, f39, f36, f34, f39, f38, f34, f35, f38, f37, f35, f36, 1.0f, 0.0f, -1.0f);
                                f2 = f2;
                                break;
                            case 3:
                                float f40 = i2;
                                float f41 = i3 + 0.01f;
                                float f42 = i4 + 1;
                                float f43 = i2 + 1;
                                float f44 = i4;
                                meshPartBuilderPart.rect(f40, f41, f42, f43, f41, f42, f43, f41, f44, f40, f41, f44, 0.0f, 1.0f, 0.0f);
                                f2 = f2;
                                break;
                            case 4:
                                float f45 = i2;
                                float f46 = (i3 + 1) - 0.01f;
                                float f47 = i4;
                                float f48 = i2 + 1;
                                float f49 = i4 + 1;
                                meshPartBuilderPart.rect(f45, f46, f47, f48, f46, f47, f48, f46, f49, f45, f46, f49, 0.0f, -1.0f, 0.0f);
                                f2 = f2;
                                break;
                            case 5:
                                float f50 = i2 + 1;
                                float f51 = i3;
                                float f52 = i4 + 0.01f;
                                float f53 = i2;
                                float f54 = i3 + 1;
                                meshPartBuilderPart.rect(f50, f51, f52, f53, f51, f52, f53, f54, f52, f50, f54, f52, 0.0f, 0.0f, 1.0f);
                                f2 = f2;
                                break;
                            case 6:
                                float f55 = i2;
                                float f56 = i3;
                                float f57 = (i4 + 1) - 0.01f;
                                float f58 = i2 + 1;
                                float f59 = i3 + 1;
                                meshPartBuilderPart.rect(f55, f56, f57, f58, f56, f57, f58, f59, f57, f55, f59, f57, 0.0f, 0.0f, -1.0f);
                                f2 = f2;
                                break;
                            case 7:
                                float f60 = i2 + 0.01f;
                                float f61 = i3;
                                float f62 = i4;
                                float f63 = i4 + 1;
                                float f64 = i3 + 1;
                                meshPartBuilderPart.rect(f60, f61, f62, f60, f61, f63, f60, f64, f63, f60, f64, f62, 1.0f, 0.0f, 0.0f);
                                f2 = f2;
                                break;
                            case 8:
                                float f65 = (i2 + 1) - 0.01f;
                                float f66 = i3;
                                float f67 = i4 + 1;
                                float f68 = i4;
                                float f69 = i3 + 1;
                                meshPartBuilderPart.rect(f65, f66, f67, f65, f66, f68, f65, f69, f68, f65, f69, f67, -1.0f, 0.0f, 0.0f);
                                f2 = f2;
                                break;
                            default:
                                f2 = f2;
                                break;
                        }
                    } else {
                        f2 = f2;
                    }
                    i4++;
                    f2 = f2;
                    f = 1.0f;
                }
                i3++;
                i = 0;
                f = 1.0f;
            }
            i2++;
            i = 0;
            f = 1.0f;
        }
        final Model modelEnd = modelBuilder.end();
        this.voxelExecutor.submit(new AsyncTask() { // from class: org.catrobat.catroid.raptor.ThreeDManager$$ExternalSyntheticLambda24
            @Override // com.badlogic.gdx.utils.async.AsyncTask
            public final Object call() {
                return this.f$0.m5485xc56b8526(modelEnd, objectId);
            }
        });
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/ThreeDManager$6.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.raptor.ThreeDManager$6, reason: invalid class name */
    static /* synthetic */ class AnonymousClass6 {
        static final /* synthetic */ int[] $SwitchMap$org$catrobat$catroid$raptor$ColliderShapeData$ShapeType;
        static final /* synthetic */ int[] $SwitchMap$org$catrobat$catroid$raptor$ParticleComponent$SpawnShape;
        static final /* synthetic */ int[] $SwitchMap$org$catrobat$catroid$raptor$ThreeDManager$PhysicsShape;
        static final /* synthetic */ int[] $SwitchMap$org$catrobat$catroid$raptor$VoxelManager$VoxelShape;

        static {
            int[] iArr = new int[VoxelManager.VoxelShape.values().length];
            $SwitchMap$org$catrobat$catroid$raptor$VoxelManager$VoxelShape = iArr;
            try {
                iArr[VoxelManager.VoxelShape.CUBE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$raptor$VoxelManager$VoxelShape[VoxelManager.VoxelShape.CROSS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$raptor$VoxelManager$VoxelShape[VoxelManager.VoxelShape.FLOOR.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$raptor$VoxelManager$VoxelShape[VoxelManager.VoxelShape.CEILING.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$raptor$VoxelManager$VoxelShape[VoxelManager.VoxelShape.WALL_NORTH.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$raptor$VoxelManager$VoxelShape[VoxelManager.VoxelShape.WALL_SOUTH.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$raptor$VoxelManager$VoxelShape[VoxelManager.VoxelShape.WALL_WEST.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$raptor$VoxelManager$VoxelShape[VoxelManager.VoxelShape.WALL_EAST.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            int[] iArr2 = new int[PhysicsShape.values().length];
            $SwitchMap$org$catrobat$catroid$raptor$ThreeDManager$PhysicsShape = iArr2;
            try {
                iArr2[PhysicsShape.SPHERE.ordinal()] = 1;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$raptor$ThreeDManager$PhysicsShape[PhysicsShape.CAPSULE.ordinal()] = 2;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$raptor$ThreeDManager$PhysicsShape[PhysicsShape.BOX.ordinal()] = 3;
            } catch (NoSuchFieldError unused11) {
            }
            int[] iArr3 = new int[ColliderShapeData.ShapeType.values().length];
            $SwitchMap$org$catrobat$catroid$raptor$ColliderShapeData$ShapeType = iArr3;
            try {
                iArr3[ColliderShapeData.ShapeType.SPHERE.ordinal()] = 1;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$raptor$ColliderShapeData$ShapeType[ColliderShapeData.ShapeType.CAPSULE.ordinal()] = 2;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$raptor$ColliderShapeData$ShapeType[ColliderShapeData.ShapeType.BOX.ordinal()] = 3;
            } catch (NoSuchFieldError unused14) {
            }
            int[] iArr4 = new int[ParticleComponent.SpawnShape.values().length];
            $SwitchMap$org$catrobat$catroid$raptor$ParticleComponent$SpawnShape = iArr4;
            try {
                iArr4[ParticleComponent.SpawnShape.POINT.ordinal()] = 1;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$raptor$ParticleComponent$SpawnShape[ParticleComponent.SpawnShape.BOX.ordinal()] = 2;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$raptor$ParticleComponent$SpawnShape[ParticleComponent.SpawnShape.SPHERE.ordinal()] = 3;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$raptor$ParticleComponent$SpawnShape[ParticleComponent.SpawnShape.CYLINDER.ordinal()] = 4;
            } catch (NoSuchFieldError unused18) {
            }
        }
    }

    /* JADX INFO: renamed from: lambda$updateVoxelMesh$24$org-catrobat-catroid-raptor-ThreeDManager, reason: not valid java name */
    /* synthetic */ Object m5485xc56b8526(final Model model, final String str) throws Exception {
        final btBvhTriangleMeshShape btbvhtrianglemeshshape = new btBvhTriangleMeshShape((btStridingMeshInterface) new btTriangleIndexVertexArray(model.meshParts), false);
        Gdx.f82app.postRunnable(new Runnable() { // from class: org.catrobat.catroid.raptor.ThreeDManager$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5484xe02a1665(str, model, btbvhtrianglemeshshape);
            }
        });
        return null;
    }

    /* JADX INFO: renamed from: lambda$updateVoxelMesh$23$org-catrobat-catroid-raptor-ThreeDManager, reason: not valid java name */
    /* synthetic */ void m5484xe02a1665(String str, Model model, btCollisionShape btcollisionshape) {
        net.mgsx.gltf.scene3d.scene.SceneManager sceneManager;
        ModelInstance modelInstance = this.sceneObjects.get(str);
        ModelInstance modelInstance2 = new ModelInstance(model);
        if (modelInstance != null) {
            modelInstance2.transform.set(modelInstance.transform);
            if (this.realisticMode && (sceneManager = this.sceneManager) != null) {
                Array.ArrayIterator<RenderableProvider> it = sceneManager.getRenderableProviders().iterator();
                while (it.hasNext()) {
                    RenderableProvider next = it.next();
                    if (next instanceof Scene) {
                        Scene scene = (Scene) next;
                        if (scene.modelInstance == modelInstance) {
                            this.sceneManager.removeScene(scene);
                            break;
                        }
                    }
                }
            }
        }
        modelInstance2.calculateTransforms();
        modelInstance2.calculateBoundingBox(new BoundingBox());
        if (this.realisticMode) {
            this.sceneManager.addScene(new Scene(modelInstance2));
        }
        this.sceneObjects.put(str, modelInstance2);
        btRigidBody btrigidbody = this.physicsBodies.get(str);
        if (btrigidbody != null) {
            btCollisionShape collisionShape = btrigidbody.getCollisionShape();
            Vector3 vector3 = new Vector3();
            modelInstance2.transform.getScale(vector3);
            btcollisionshape.setLocalScaling(vector3);
            btrigidbody.setCollisionShape(btcollisionshape);
            this.dynamicsWorld.updateSingleAabb(btrigidbody);
            if (collisionShape != null) {
                collisionShape.dispose();
                return;
            }
            return;
        }
        createMeshPhysicsBody(str, modelInstance2);
        btRigidBody btrigidbody2 = this.physicsBodies.get(str);
        if (btrigidbody2 != null) {
            btrigidbody2.setCollisionFlags(btrigidbody2.getCollisionFlags() | 1);
            btrigidbody2.setCollisionShape(btcollisionshape);
        }
    }

    public String getVoxelData(String worldId, float x, float y, float z) {
        return VoxelManager.INSTANCE.getBlockInfo(worldId, (int) x, (int) y, (int) z);
    }

    public void applyShaderToImage(final String filename, final String vertexCode, final String fragmentCode) {
        Gdx.f82app.postRunnable(new Runnable() { // from class: org.catrobat.catroid.raptor.ThreeDManager$$ExternalSyntheticLambda23
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f$0.m5472xc9ae2b0a(filename, vertexCode, fragmentCode);
            }
        });
    }

    /* JADX WARN: Code duplicated, block: B:113:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:70:0x0185  */
    /* JADX WARN: Code duplicated, block: B:72:0x018a  */
    /* JADX WARN: Code duplicated, block: B:74:0x018f  */
    /* JADX WARN: Code duplicated, block: B:76:0x0194  */
    /* JADX WARN: Code duplicated, block: B:82:0x01a0  */
    /* JADX WARN: Code duplicated, block: B:84:0x01a5  */
    /* JADX WARN: Code duplicated, block: B:86:0x01aa  */
    /* JADX WARN: Code duplicated, block: B:88:0x01af  */
    /* JADX WARN: Code duplicated, block: B:90:0x01b4  */
    /* JADX INFO: renamed from: lambda$applyShaderToImage$25$org-catrobat-catroid-raptor-ThreeDManager, reason: not valid java name */
    /* synthetic */ void m5472xc9ae2b0a(String str, String str2, String str3) throws Throwable {
        Pixmap frameBufferPixmap;
        Texture texture;
        SpriteBatch spriteBatch;
        FrameBuffer frameBuffer;
        ShaderProgram shaderProgram;
        Texture textureRemove;
        String str4 = "Error when processing image with shader: ";
        File file = ProjectManager.getInstance().getCurrentProject().getFile(str);
        if (file == null || !file.exists()) {
            Gdx.f82app.error("ThreeDManager", "ShaderImage Error: file not found: " + str);
            return;
        }
        FileHandle fileHandleAbsolute = Gdx.files.absolute(file.getAbsolutePath());
        try {
            try {
                Texture texture2 = new Texture(fileHandleAbsolute);
                try {
                    int width = texture2.getWidth();
                    int height = texture2.getHeight();
                    FrameBuffer frameBuffer2 = new FrameBuffer(Pixmap.Format.RGBA8888, width, height, false);
                    try {
                        ShaderProgram shaderProgram2 = new ShaderProgram(str2, str3);
                        try {
                            if (shaderProgram2.isCompiled()) {
                                frameBuffer2.begin();
                                Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
                                Gdx.gl.glClear(16384);
                                float f = width;
                                float f2 = height;
                                OrthographicCamera orthographicCamera = new OrthographicCamera(f, f2);
                                orthographicCamera.setToOrtho(true, f, f2);
                                SpriteBatch spriteBatch2 = new SpriteBatch();
                                try {
                                    spriteBatch2.setProjectionMatrix(orthographicCamera.combined);
                                    spriteBatch2.setShader(shaderProgram2);
                                    spriteBatch2.begin();
                                    shaderProgram = shaderProgram2;
                                    frameBuffer = frameBuffer2;
                                    str4 = "Error when processing image with shader: ";
                                    try {
                                        spriteBatch2.draw(texture2, 0.0f, 0.0f, f, f2);
                                        spriteBatch2.end();
                                        frameBufferPixmap = ScreenUtils.getFrameBufferPixmap(0, 0, width, height);
                                        try {
                                            frameBuffer.end();
                                            PixmapIO.writePNG(fileHandleAbsolute, frameBufferPixmap);
                                            Gdx.f82app.log("ThreeDManager", "Shader successfully applyed to file: " + str);
                                            if (this.loadedTextures.containsKey(str) && (textureRemove = this.loadedTextures.remove(str)) != null) {
                                                textureRemove.dispose();
                                            }
                                            texture2.dispose();
                                            frameBuffer.dispose();
                                            shaderProgram.dispose();
                                            spriteBatch2.dispose();
                                            if (frameBufferPixmap == null) {
                                                return;
                                            }
                                        } catch (Exception e) {
                                            e = e;
                                            spriteBatch = spriteBatch2;
                                            texture = texture2;
                                            Gdx.f82app.error("ThreeDManager", str4 + e.getMessage(), e);
                                            if (texture != null) {
                                                texture.dispose();
                                            }
                                            if (frameBuffer != null) {
                                                frameBuffer.dispose();
                                            }
                                            if (shaderProgram != null) {
                                                shaderProgram.dispose();
                                            }
                                            if (spriteBatch != null) {
                                                spriteBatch.dispose();
                                            }
                                            if (frameBufferPixmap == null) {
                                                return;
                                            }
                                        } catch (Throwable th) {
                                            th = th;
                                            spriteBatch = spriteBatch2;
                                            texture = texture2;
                                            if (texture != null) {
                                                texture.dispose();
                                            }
                                            if (frameBuffer != null) {
                                                frameBuffer.dispose();
                                            }
                                            if (shaderProgram != null) {
                                                shaderProgram.dispose();
                                            }
                                            if (spriteBatch != null) {
                                                spriteBatch.dispose();
                                            }
                                            if (frameBufferPixmap != null) {
                                                frameBufferPixmap.dispose();
                                            }
                                            throw th;
                                        }
                                    } catch (Exception e2) {
                                        e = e2;
                                        spriteBatch = spriteBatch2;
                                        texture = texture2;
                                        frameBufferPixmap = null;
                                    } catch (Throwable th2) {
                                        th = th2;
                                        spriteBatch = spriteBatch2;
                                        texture = texture2;
                                        frameBufferPixmap = null;
                                        if (texture != null) {
                                            texture.dispose();
                                        }
                                        if (frameBuffer != null) {
                                            frameBuffer.dispose();
                                        }
                                        if (shaderProgram != null) {
                                            shaderProgram.dispose();
                                        }
                                        if (spriteBatch != null) {
                                            spriteBatch.dispose();
                                        }
                                        if (frameBufferPixmap != null) {
                                            frameBufferPixmap.dispose();
                                        }
                                        throw th;
                                    }
                                } catch (Exception e3) {
                                    e = e3;
                                    str4 = "Error when processing image with shader: ";
                                    shaderProgram = shaderProgram2;
                                    frameBuffer = frameBuffer2;
                                } catch (Throwable th3) {
                                    th = th3;
                                    shaderProgram = shaderProgram2;
                                    frameBuffer = frameBuffer2;
                                }
                                frameBufferPixmap.dispose();
                            }
                            shaderProgram = shaderProgram2;
                            frameBuffer = frameBuffer2;
                            try {
                                throw new Exception("Shader cannot be compiled:\n" + shaderProgram.getLog());
                            } catch (Exception e4) {
                                e = e4;
                            } catch (Throwable th4) {
                                th = th4;
                                texture = texture2;
                                frameBufferPixmap = null;
                                spriteBatch = null;
                                if (texture != null) {
                                    texture.dispose();
                                }
                                if (frameBuffer != null) {
                                    frameBuffer.dispose();
                                }
                                if (shaderProgram != null) {
                                    shaderProgram.dispose();
                                }
                                if (spriteBatch != null) {
                                    spriteBatch.dispose();
                                }
                                if (frameBufferPixmap != null) {
                                    frameBufferPixmap.dispose();
                                }
                                throw th;
                            }
                        } catch (Exception e5) {
                            e = e5;
                            shaderProgram = shaderProgram2;
                            frameBuffer = frameBuffer2;
                        } catch (Throwable th5) {
                            th = th5;
                            shaderProgram = shaderProgram2;
                            frameBuffer = frameBuffer2;
                        }
                        texture = texture2;
                        frameBufferPixmap = null;
                        spriteBatch = null;
                    } catch (Exception e6) {
                        e = e6;
                        str4 = "Error when processing image with shader: ";
                        frameBuffer = frameBuffer2;
                        texture = texture2;
                        frameBufferPixmap = null;
                        spriteBatch = null;
                        shaderProgram = null;
                    } catch (Throwable th6) {
                        th = th6;
                        frameBuffer = frameBuffer2;
                        texture = texture2;
                        frameBufferPixmap = null;
                        spriteBatch = null;
                        shaderProgram = null;
                        if (texture != null) {
                            texture.dispose();
                        }
                        if (frameBuffer != null) {
                            frameBuffer.dispose();
                        }
                        if (shaderProgram != null) {
                            shaderProgram.dispose();
                        }
                        if (spriteBatch != null) {
                            spriteBatch.dispose();
                        }
                        if (frameBufferPixmap != null) {
                            frameBufferPixmap.dispose();
                        }
                        throw th;
                    }
                } catch (Exception e7) {
                    e = e7;
                    texture = texture2;
                    frameBufferPixmap = null;
                    spriteBatch = null;
                    frameBuffer = null;
                    shaderProgram = null;
                    Gdx.f82app.error("ThreeDManager", str4 + e.getMessage(), e);
                    if (texture != null) {
                        texture.dispose();
                    }
                    if (frameBuffer != null) {
                        frameBuffer.dispose();
                    }
                    if (shaderProgram != null) {
                        shaderProgram.dispose();
                    }
                    if (spriteBatch != null) {
                        spriteBatch.dispose();
                    }
                    if (frameBufferPixmap == null) {
                        return;
                    }
                    frameBufferPixmap.dispose();
                } catch (Throwable th7) {
                    th = th7;
                    texture = texture2;
                    frameBufferPixmap = null;
                    spriteBatch = null;
                    frameBuffer = null;
                    shaderProgram = null;
                    if (texture != null) {
                        texture.dispose();
                    }
                    if (frameBuffer != null) {
                        frameBuffer.dispose();
                    }
                    if (shaderProgram != null) {
                        shaderProgram.dispose();
                    }
                    if (spriteBatch != null) {
                        spriteBatch.dispose();
                    }
                    if (frameBufferPixmap != null) {
                        frameBufferPixmap.dispose();
                    }
                    throw th;
                }
            } catch (Exception e8) {
                e = e8;
                frameBufferPixmap = null;
                texture = null;
            } catch (Throwable th8) {
                th = th8;
                frameBufferPixmap = null;
                texture = null;
            }
            Gdx.f82app.error("ThreeDManager", str4 + e.getMessage(), e);
            if (texture != null) {
                texture.dispose();
            }
            if (frameBuffer != null) {
                frameBuffer.dispose();
            }
            if (shaderProgram != null) {
                shaderProgram.dispose();
            }
            if (spriteBatch != null) {
                spriteBatch.dispose();
            }
            if (frameBufferPixmap == null) {
                return;
            }
            frameBufferPixmap.dispose();
        } catch (Throwable th9) {
            th = th9;
            if (texture != null) {
                texture.dispose();
            }
            if (frameBuffer != null) {
                frameBuffer.dispose();
            }
            if (shaderProgram != null) {
                shaderProgram.dispose();
            }
            if (spriteBatch != null) {
                spriteBatch.dispose();
            }
            if (frameBufferPixmap != null) {
                frameBufferPixmap.dispose();
            }
            throw th;
        }
    }
}
