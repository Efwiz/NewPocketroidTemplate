package net.mgsx.gltf.demo;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.AbsoluteFileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cubemap;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.DirectionalLightsAttribute;
import com.badlogic.gdx.graphics.g3d.environment.BaseLight;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.DefaultShaderProvider;
import com.badlogic.gdx.graphics.g3d.utils.ShaderProvider;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.profiling.GLErrorListener;
import com.badlogic.gdx.graphics.profiling.GLProfiler;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import net.mgsx.gltf.demo.data.ModelEntry;
import net.mgsx.gltf.demo.events.FileOpenEvent;
import net.mgsx.gltf.demo.events.FileSaveEvent;
import net.mgsx.gltf.demo.events.IBLFolderChangeEvent;
import net.mgsx.gltf.demo.events.ModelSelectedEvent;
import net.mgsx.gltf.demo.model.IBLStudio;
import net.mgsx.gltf.demo.ui.GLTFDemoUI;
import net.mgsx.gltf.demo.util.GLTFInspector;
import net.mgsx.gltf.demo.util.NodeUtil;
import net.mgsx.gltf.exporters.GLTFExporter;
import net.mgsx.gltf.loaders.glb.GLBAssetLoader;
import net.mgsx.gltf.loaders.glb.GLBLoader;
import net.mgsx.gltf.loaders.gltf.GLTFAssetLoader;
import net.mgsx.gltf.loaders.gltf.GLTFLoader;
import net.mgsx.gltf.loaders.shared.SceneAssetLoaderParameters;
import net.mgsx.gltf.scene3d.attributes.FogAttribute;
import net.mgsx.gltf.scene3d.attributes.PBRCubemapAttribute;
import net.mgsx.gltf.scene3d.attributes.PBRFloatAttribute;
import net.mgsx.gltf.scene3d.attributes.PBRTextureAttribute;
import net.mgsx.gltf.scene3d.lights.DirectionalLightEx;
import net.mgsx.gltf.scene3d.lights.DirectionalShadowLight;
import net.mgsx.gltf.scene3d.scene.CascadeShadowMap;
import net.mgsx.gltf.scene3d.scene.MirrorSource;
import net.mgsx.gltf.scene3d.scene.Scene;
import net.mgsx.gltf.scene3d.scene.SceneAsset;
import net.mgsx.gltf.scene3d.scene.SceneManager;
import net.mgsx.gltf.scene3d.scene.SceneModel;
import net.mgsx.gltf.scene3d.scene.SceneSkybox;
import net.mgsx.gltf.scene3d.scene.TransmissionSource;
import net.mgsx.gltf.scene3d.shaders.PBRShaderConfig;
import net.mgsx.gltf.scene3d.shaders.PBRShaderProvider;
import net.mgsx.gltf.scene3d.utils.EnvironmentUtil;
import net.mgsx.gltf.scene3d.utils.IBLBuilder;
import net.mgsx.gltf.scene3d.utils.LightUtils;
import net.mgsx.gltf.scene3d.utils.MaterialConverter;
import org.catrobat.catroid.common.Constants;

/* JADX INFO: loaded from: classes12.dex */
public class GLTFDemo extends ApplicationAdapter {
    public static String AUTOLOAD_ENTRY = null;
    public static String AUTOLOAD_VARIANT = null;
    private static final int SHADOW_MAP_SIZE = 2048;
    private static final String TAG = "GLTFDemo";
    private static boolean USE_ASSET_MANAGER = true;
    public static String alternateMaps = null;
    public static int defaultUIScale = 1;
    private static final Vector3 v1 = new Vector3();
    private AssetManager assetManager;
    private Texture brdfLUT;
    private CameraInputController cameraControl;
    private CascadeShadowMap csm;
    private DirectionalLight defaultLight;
    private FrameBuffer depthFbo;
    private Cubemap diffuseCubemap;
    private Array<ModelEntry> entries;
    private Cubemap environmentCubemap;
    private String lastFileName;
    private MirrorSource mirror;
    private ShaderProgram outlineShader;
    private boolean outlineShaderValid;
    private FileHandle rootFolder;
    private SceneAsset rootModel;
    private String samplesPath;
    private Scene scene;
    private final BoundingBox sceneBox;
    private SceneManager sceneManager;
    private ShaderMode shaderMode;
    private boolean shadersValid;
    private ShapeRenderer shapeRenderer;
    private Skin skin;
    private SceneSkybox skybox;
    private boolean skyboxValid;
    private Cubemap specularCubemap;
    private SpriteBatch spriteBatch;
    private Stage stage;
    private GLTFDemoUI ui;
    private ScreenViewport viewport;

    public enum ShaderMode {
        GOURAUD,
        PBR_MR,
        CeilShading
    }

    public GLTFDemo() {
        this(null);
    }

    public GLTFDemo(String str) {
        this.shaderMode = ShaderMode.PBR_MR;
        this.sceneBox = new BoundingBox();
        this.skyboxValid = true;
        this.samplesPath = str;
    }

    @Override // com.badlogic.gdx.ApplicationAdapter, com.badlogic.gdx.ApplicationListener
    public void create() {
        GLProfiler gLProfiler = new GLProfiler(Gdx.graphics);
        gLProfiler.setListener(new GLErrorListener() { // from class: net.mgsx.gltf.demo.GLTFDemo.1
            @Override // com.badlogic.gdx.graphics.profiling.GLErrorListener
            public void onError(int i) {
                Gdx.f82app.error("OpenGL", String.valueOf(i));
            }
        });
        gLProfiler.enable();
        AssetManager assetManager = new AssetManager();
        this.assetManager = assetManager;
        Texture.setAssetManager(assetManager);
        this.assetManager.setLoader(SceneAsset.class, ".gltf", new GLTFAssetLoader());
        this.assetManager.setLoader(SceneAsset.class, ".glb", new GLBAssetLoader());
        this.shapeRenderer = new ShapeRenderer();
        this.spriteBatch = new SpriteBatch();
        createSceneManager();
        if (this.samplesPath != null) {
            this.rootFolder = Gdx.files.internal(this.samplesPath);
            createUI();
            loadModelIndex();
        } else {
            createUI();
            loadEmptyScene();
        }
    }

    private void createDefaultMaps() {
        if (alternateMaps != null) {
            this.diffuseCubemap = EnvironmentUtil.createCubemap(new InternalFileHandleResolver(), "textures/" + alternateMaps + "/diffuse/diffuse_", Constants.JPEG_IMAGE_EXTENSION, EnvironmentUtil.FACE_NAMES_NEG_POS);
            this.environmentCubemap = EnvironmentUtil.createCubemap(new InternalFileHandleResolver(), "textures/" + alternateMaps + "/environment/environment_", Constants.JPEG_IMAGE_EXTENSION, EnvironmentUtil.FACE_NAMES_NEG_POS);
            this.specularCubemap = EnvironmentUtil.createCubemap(new InternalFileHandleResolver(), "textures/" + alternateMaps + "/specular/specular_", "_", Constants.JPEG_IMAGE_EXTENSION, 10, EnvironmentUtil.FACE_NAMES_NEG_POS);
        } else {
            this.diffuseCubemap = EnvironmentUtil.createCubemap(new InternalFileHandleResolver(), "textures/diffuse/diffuse_", Constants.DEFAULT_IMAGE_EXTENSION, EnvironmentUtil.FACE_NAMES_NEG_POS);
            this.environmentCubemap = EnvironmentUtil.createCubemap(new InternalFileHandleResolver(), "textures/environment/environment_", Constants.DEFAULT_IMAGE_EXTENSION, EnvironmentUtil.FACE_NAMES_NEG_POS);
            this.specularCubemap = EnvironmentUtil.createCubemap(new InternalFileHandleResolver(), "textures/specular/specular_", "_", Constants.DEFAULT_IMAGE_EXTENSION, 10, EnvironmentUtil.FACE_NAMES_NEG_POS);
        }
    }

    private void createSceneManager() {
        this.defaultLight = new DirectionalLightEx();
        resetDefaultLight();
        createDefaultMaps();
        this.brdfLUT = new Texture(Gdx.files.classpath("net/mgsx/gltf/shaders/brdfLUT.png"));
        SceneManager sceneManager = new SceneManager();
        this.sceneManager = sceneManager;
        sceneManager.environment.add(this.defaultLight);
        SceneManager sceneManager2 = this.sceneManager;
        SceneSkybox sceneSkybox = new SceneSkybox(this.environmentCubemap);
        this.skybox = sceneSkybox;
        sceneManager2.setSkyBox(sceneSkybox);
        setEnvironment();
    }

    private void setEnvironment() {
        this.sceneManager.environment.set(PBRCubemapAttribute.createDiffuseEnv(this.diffuseCubemap));
        this.sceneManager.environment.set(PBRCubemapAttribute.createSpecularEnv(this.specularCubemap));
        if (this.brdfLUT != null) {
            this.sceneManager.environment.set(new PBRTextureAttribute(PBRTextureAttribute.BRDFLUTTexture, this.brdfLUT));
        }
        this.sceneManager.environment.set(new PBRFloatAttribute(PBRFloatAttribute.ShadowBias, 0.0f));
        this.sceneManager.setAmbientLight(1.0f);
        this.sceneManager.setEnvironmentRotation(180.0f);
        GLTFDemoUI gLTFDemoUI = this.ui;
        if (gLTFDemoUI != null) {
            gLTFDemoUI.ambiantSlider.setValue(1.0f);
        }
    }

    protected void loadIBL(IBLStudio.IBLPreset iBLPreset) {
        Cubemap cubemap = this.diffuseCubemap;
        if (cubemap != null) {
            cubemap.dispose();
            this.diffuseCubemap = null;
        }
        Cubemap cubemap2 = this.specularCubemap;
        if (cubemap2 != null) {
            cubemap2.dispose();
            this.specularCubemap = null;
        }
        Cubemap cubemap3 = this.environmentCubemap;
        if (cubemap3 != null) {
            cubemap3.dispose();
            this.environmentCubemap = null;
        }
        IBLBuilder iBLBuilderCreateBuilder = iBLPreset.createBuilder(this.defaultLight);
        if (iBLBuilderCreateBuilder != null) {
            this.environmentCubemap = iBLBuilderCreateBuilder.buildEnvMap(1024);
            this.diffuseCubemap = iBLBuilderCreateBuilder.buildIrradianceMap(256);
            this.specularCubemap = iBLBuilderCreateBuilder.buildRadianceMap(10);
            iBLBuilderCreateBuilder.dispose();
        } else {
            createDefaultMaps();
        }
        this.skybox.set(this.environmentCubemap);
        changeIBLOptions();
    }

    protected void loadIBL(FileHandle fileHandle) {
        try {
            Cubemap cubemapCreateCubemap = EnvironmentUtil.createCubemap(new AbsoluteFileHandleResolver(), fileHandle.path() + "/environment/environment_", Constants.JPEG_IMAGE_EXTENSION, EnvironmentUtil.FACE_NAMES_NEG_POS);
            Cubemap cubemapCreateCubemap2 = EnvironmentUtil.createCubemap(new AbsoluteFileHandleResolver(), fileHandle.path() + "/diffuse/diffuse_", Constants.JPEG_IMAGE_EXTENSION, EnvironmentUtil.FACE_NAMES_NEG_POS);
            Cubemap cubemapCreateCubemap3 = EnvironmentUtil.createCubemap(new AbsoluteFileHandleResolver(), fileHandle.path() + "/specular/specular_", "_", Constants.JPEG_IMAGE_EXTENSION, 10, EnvironmentUtil.FACE_NAMES_NEG_POS);
            Cubemap cubemap = this.diffuseCubemap;
            if (cubemap != null) {
                cubemap.dispose();
                this.diffuseCubemap = null;
            }
            Cubemap cubemap2 = this.specularCubemap;
            if (cubemap2 != null) {
                cubemap2.dispose();
                this.specularCubemap = null;
            }
            Cubemap cubemap3 = this.environmentCubemap;
            if (cubemap3 != null) {
                cubemap3.dispose();
                this.environmentCubemap = null;
            }
            this.diffuseCubemap = cubemapCreateCubemap2;
            this.specularCubemap = cubemapCreateCubemap3;
            this.environmentCubemap = cubemapCreateCubemap;
            this.skybox.set(cubemapCreateCubemap);
            changeIBLOptions();
        } catch (GdxRuntimeException e) {
            e.printStackTrace();
        }
    }

    protected void changeIBLOptions() {
        this.sceneManager.environment.remove(PBRCubemapAttribute.DiffuseEnv);
        this.sceneManager.environment.remove(PBRCubemapAttribute.SpecularEnv);
        this.sceneManager.environment.remove(PBRTextureAttribute.BRDFLUTTexture);
        if (this.ui.IBLEnabled.isOn()) {
            this.sceneManager.environment.set(PBRCubemapAttribute.createDiffuseEnv(this.diffuseCubemap));
            if (this.ui.IBLSpecular.isOn()) {
                this.sceneManager.environment.set(PBRCubemapAttribute.createSpecularEnv(this.specularCubemap));
            }
            if (this.ui.IBLLookup.isOn()) {
                this.sceneManager.environment.set(new PBRTextureAttribute(PBRTextureAttribute.BRDFLUTTexture, this.brdfLUT));
            }
        }
        invalidateShaders();
    }

    private void loadModelIndex() {
        Array<ModelEntry> array = (Array) new Json().fromJson(Array.class, ModelEntry.class, this.rootFolder.child("model-index.json"));
        this.entries = array;
        this.ui.setEntries(array, AUTOLOAD_ENTRY, AUTOLOAD_VARIANT);
    }

    private void loadEmptyScene() {
        this.sceneBox.set(new Vector3(-1.0f, -1.0f, -1.0f), new Vector3(1.0f, 1.0f, 1.0f));
        setCamera("");
    }

    private void createUI() {
        ScreenViewport screenViewport = new ScreenViewport();
        this.viewport = screenViewport;
        this.stage = new Stage(screenViewport);
        this.viewport.setUnitsPerPixel(1.0f / defaultUIScale);
        Gdx.input.setInputProcessor(this.stage);
        this.skin = new Skin(Gdx.files.internal("skins/uiskin.json"));
        GLTFDemoUI gLTFDemoUI = new GLTFDemoUI(this.sceneManager, this.skin, this.rootFolder);
        this.ui = gLTFDemoUI;
        gLTFDemoUI.setFillParent(true);
        this.stage.addActor(this.ui);
        this.ui.shaderSelector.setSelected(this.shaderMode);
        this.ui.IBLSelector.setItems(IBLStudio.presets);
        this.ui.addListener(new ChangeListener() { // from class: net.mgsx.gltf.demo.GLTFDemo.2
            @Override // com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
            public void changed(ChangeListener.ChangeEvent changeEvent, Actor actor) {
                if (changeEvent instanceof FileOpenEvent) {
                    GLTFDemo.this.load(((FileOpenEvent) changeEvent).file);
                    return;
                }
                if (changeEvent instanceof IBLFolderChangeEvent) {
                    GLTFDemo.this.loadIBL(((IBLFolderChangeEvent) changeEvent).file);
                } else if (changeEvent instanceof FileSaveEvent) {
                    GLTFDemo.this.save(((FileSaveEvent) changeEvent).file);
                } else if (changeEvent instanceof ModelSelectedEvent) {
                    GLTFDemo.this.load(((ModelSelectedEvent) changeEvent).glFile);
                }
            }
        });
        this.ui.animationSelector.addListener(new ChangeListener() { // from class: net.mgsx.gltf.demo.GLTFDemo.3
            @Override // com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
            public void changed(ChangeListener.ChangeEvent changeEvent, Actor actor) {
                GLTFDemo gLTFDemo = GLTFDemo.this;
                gLTFDemo.setAnimation(gLTFDemo.ui.animationSelector.getSelected());
            }
        });
        this.ui.cameraSelector.addListener(new ChangeListener() { // from class: net.mgsx.gltf.demo.GLTFDemo.4
            @Override // com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
            public void changed(ChangeListener.ChangeEvent changeEvent, Actor actor) {
                GLTFDemo gLTFDemo = GLTFDemo.this;
                gLTFDemo.setCamera(gLTFDemo.ui.cameraSelector.getSelected());
            }
        });
        this.ui.shaderSelector.addListener(new ChangeListener() { // from class: net.mgsx.gltf.demo.GLTFDemo.5
            @Override // com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
            public void changed(ChangeListener.ChangeEvent changeEvent, Actor actor) {
                GLTFDemo gLTFDemo = GLTFDemo.this;
                gLTFDemo.setShader(gLTFDemo.ui.shaderSelector.getSelected());
            }
        });
        ChangeListener changeListener = new ChangeListener() { // from class: net.mgsx.gltf.demo.GLTFDemo.6
            @Override // com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
            public void changed(ChangeListener.ChangeEvent changeEvent, Actor actor) {
                GLTFDemo.this.invalidateShaders();
            }
        };
        this.ui.shaderSRGB.addListener(changeListener);
        this.ui.shaderGammaCorrection.addListener(changeListener);
        ChangeListener changeListener2 = new ChangeListener() { // from class: net.mgsx.gltf.demo.GLTFDemo.7
            @Override // com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
            public void changed(ChangeListener.ChangeEvent changeEvent, Actor actor) {
                GLTFDemo.this.invalidateSkybox();
            }
        };
        this.ui.skyboxSRGB.addListener(changeListener2);
        this.ui.skyboxGammaCorrection.addListener(changeListener2);
        this.ui.envRotation.addListener(new ChangeListener() { // from class: net.mgsx.gltf.demo.GLTFDemo.8
            @Override // com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
            public void changed(ChangeListener.ChangeEvent changeEvent, Actor actor) {
                GLTFDemo.this.sceneManager.setEnvironmentRotation(GLTFDemo.this.ui.envRotation.getValue() * 360.0f);
            }
        });
        this.ui.transmissionPassEnabled.addListener(new ChangeListener() { // from class: net.mgsx.gltf.demo.GLTFDemo.9
            @Override // com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
            public void changed(ChangeListener.ChangeEvent changeEvent, Actor actor) {
                GLTFDemo.this.invalidateShaders();
            }
        });
        this.ui.transmissionSRGB.addListener(new ChangeListener() { // from class: net.mgsx.gltf.demo.GLTFDemo.10
            @Override // com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
            public void changed(ChangeListener.ChangeEvent changeEvent, Actor actor) {
                GLTFDemo.this.invalidateShaders();
            }
        });
        this.ui.mirror.addListener(new ChangeListener() { // from class: net.mgsx.gltf.demo.GLTFDemo.11
            @Override // com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
            public void changed(ChangeListener.ChangeEvent changeEvent, Actor actor) {
                if (!GLTFDemo.this.ui.mirror.isOn()) {
                    GLTFDemo.this.sceneManager.setMirrorSource(null);
                    GLTFDemo.this.mirror = null;
                } else {
                    GLTFDemo.this.mirror = new MirrorSource();
                    GLTFDemo.this.updateMirror();
                    GLTFDemo.this.sceneManager.setMirrorSource(GLTFDemo.this.mirror);
                }
            }
        });
        this.ui.mirrorClip.addListener(new ChangeListener() { // from class: net.mgsx.gltf.demo.GLTFDemo.12
            @Override // com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
            public void changed(ChangeListener.ChangeEvent changeEvent, Actor actor) {
                GLTFDemo.this.updateMirror();
            }
        });
        this.ui.mirrorNormal.addListener(new ChangeListener() { // from class: net.mgsx.gltf.demo.GLTFDemo.13
            @Override // com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
            public void changed(ChangeListener.ChangeEvent changeEvent, Actor actor) {
                GLTFDemo.this.updateMirror();
            }
        });
        this.ui.mirrorOrigin.addListener(new ChangeListener() { // from class: net.mgsx.gltf.demo.GLTFDemo.14
            @Override // com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
            public void changed(ChangeListener.ChangeEvent changeEvent, Actor actor) {
                GLTFDemo.this.updateMirror();
            }
        });
        this.ui.mirrorSRGB.addListener(new ChangeListener() { // from class: net.mgsx.gltf.demo.GLTFDemo.15
            @Override // com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
            public void changed(ChangeListener.ChangeEvent changeEvent, Actor actor) {
                GLTFDemo.this.invalidateShaders();
            }
        });
        this.ui.sceneSelector.addListener(new ChangeListener() { // from class: net.mgsx.gltf.demo.GLTFDemo.16
            @Override // com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
            public void changed(ChangeListener.ChangeEvent changeEvent, Actor actor) {
                GLTFDemo gLTFDemo = GLTFDemo.this;
                gLTFDemo.load(gLTFDemo.ui.sceneSelector.getSelected());
            }
        });
        this.ui.lightShadow.addListener(new ChangeListener() { // from class: net.mgsx.gltf.demo.GLTFDemo.17
            @Override // com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
            public void changed(ChangeListener.ChangeEvent changeEvent, Actor actor) {
                GLTFDemo gLTFDemo = GLTFDemo.this;
                gLTFDemo.setShadow(gLTFDemo.ui.lightShadow.isOn());
            }
        });
        this.ui.shadowCascade.addListener(new ChangeListener() { // from class: net.mgsx.gltf.demo.GLTFDemo.18
            @Override // com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
            public void changed(ChangeListener.ChangeEvent changeEvent, Actor actor) {
                if (GLTFDemo.this.ui.shadowCascade.isOn()) {
                    GLTFDemo.this.sceneManager.setCascadeShadowMap(GLTFDemo.this.csm = new CascadeShadowMap(3));
                } else {
                    GLTFDemo.this.sceneManager.setCascadeShadowMap(GLTFDemo.this.csm = null);
                }
                GLTFDemo.this.invalidateShaders();
            }
        });
        this.ui.btAllAnimations.addListener(new ChangeListener() { // from class: net.mgsx.gltf.demo.GLTFDemo.19
            @Override // com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
            public void changed(ChangeListener.ChangeEvent changeEvent, Actor actor) {
                if (GLTFDemo.this.ui.btAllAnimations.isChecked()) {
                    GLTFDemo.this.scene.animations.playAll();
                } else {
                    GLTFDemo.this.scene.animations.stopAll();
                }
            }
        });
        this.ui.fogEnabled.addListener(new ChangeListener() { // from class: net.mgsx.gltf.demo.GLTFDemo.20
            @Override // com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
            public void changed(ChangeListener.ChangeEvent changeEvent, Actor actor) {
                if (GLTFDemo.this.ui.fogEnabled.isOn()) {
                    GLTFDemo.this.sceneManager.environment.set(new ColorAttribute(ColorAttribute.Fog, GLTFDemo.this.ui.fogColor.value));
                    GLTFDemo.this.sceneManager.environment.set(new FogAttribute(FogAttribute.FogEquation));
                } else {
                    GLTFDemo.this.sceneManager.environment.remove(ColorAttribute.Fog);
                    GLTFDemo.this.sceneManager.environment.remove(FogAttribute.FogEquation);
                }
                GLTFDemo.this.invalidateShaders();
            }
        });
        this.ui.skyBoxEnabled.addListener(new ChangeListener() { // from class: net.mgsx.gltf.demo.GLTFDemo.21
            @Override // com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
            public void changed(ChangeListener.ChangeEvent changeEvent, Actor actor) {
                if (GLTFDemo.this.ui.skyBoxEnabled.isOn()) {
                    GLTFDemo.this.sceneManager.setSkyBox(GLTFDemo.this.skybox);
                } else {
                    GLTFDemo.this.sceneManager.setSkyBox(null);
                }
            }
        });
        this.ui.IBLEnabled.addListener(new ChangeListener() { // from class: net.mgsx.gltf.demo.GLTFDemo.22
            @Override // com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
            public void changed(ChangeListener.ChangeEvent changeEvent, Actor actor) {
                GLTFDemo.this.changeIBLOptions();
            }
        });
        this.ui.IBLSpecular.addListener(new ChangeListener() { // from class: net.mgsx.gltf.demo.GLTFDemo.23
            @Override // com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
            public void changed(ChangeListener.ChangeEvent changeEvent, Actor actor) {
                GLTFDemo.this.changeIBLOptions();
            }
        });
        this.ui.IBLLookup.addListener(new ChangeListener() { // from class: net.mgsx.gltf.demo.GLTFDemo.24
            @Override // com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
            public void changed(ChangeListener.ChangeEvent changeEvent, Actor actor) {
                GLTFDemo.this.changeIBLOptions();
            }
        });
        this.ui.IBLSelector.addListener(new ChangeListener() { // from class: net.mgsx.gltf.demo.GLTFDemo.25
            @Override // com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
            public void changed(ChangeListener.ChangeEvent changeEvent, Actor actor) {
                GLTFDemo gLTFDemo = GLTFDemo.this;
                gLTFDemo.loadIBL(gLTFDemo.ui.IBLSelector.getSelected());
            }
        });
        this.ui.outlineDistFalloffOption.addListener(new ChangeListener() { // from class: net.mgsx.gltf.demo.GLTFDemo.26
            @Override // com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
            public void changed(ChangeListener.ChangeEvent changeEvent, Actor actor) {
                GLTFDemo.this.invalidateOutlineShaders();
            }
        });
        this.ui.outlinesEnabled.addListener(new ChangeListener() { // from class: net.mgsx.gltf.demo.GLTFDemo.27
            @Override // com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
            public void changed(ChangeListener.ChangeEvent changeEvent, Actor actor) {
                GLTFDemo.this.invalidateOutlineShaders();
            }
        });
        this.ui.uiScaleSlider.addListener(new ChangeListener() { // from class: net.mgsx.gltf.demo.GLTFDemo.28
            @Override // com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
            public void changed(ChangeListener.ChangeEvent changeEvent, Actor actor) {
                if (GLTFDemo.this.ui.uiScaleSlider.isDragging()) {
                    return;
                }
                GLTFDemo.this.viewport.setUnitsPerPixel(1.0f / GLTFDemo.this.ui.uiScaleSlider.getValue());
                GLTFDemo.this.viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateMirror() {
        if (this.mirror != null) {
            Vector3 vector3 = this.ui.mirrorNormal.value;
            this.mirror.set(vector3.x, vector3.y, vector3.z, this.ui.mirrorOrigin.getValue(), this.ui.mirrorClip.isOn());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void save(FileHandle fileHandle) {
        if (this.scene != null) {
            new GLTFExporter().export(this.scene, fileHandle);
        }
    }

    protected void setShadow(boolean z) {
        if (z) {
            DirectionalLight firstDirectionalLight = this.sceneManager.getFirstDirectionalLight();
            if (firstDirectionalLight != null && !(firstDirectionalLight instanceof DirectionalShadowLight)) {
                DirectionalLight directionalLight = new DirectionalShadowLight(2048, 2048).setBounds(this.sceneBox).set(firstDirectionalLight);
                this.sceneManager.environment.remove(firstDirectionalLight);
                this.sceneManager.environment.add(directionalLight);
                if (firstDirectionalLight == this.defaultLight) {
                    this.defaultLight = directionalLight;
                }
            }
        } else {
            DirectionalLightsAttribute directionalLightsAttribute = (DirectionalLightsAttribute) this.sceneManager.environment.get(DirectionalLightsAttribute.class, DirectionalLightsAttribute.Type);
            if (directionalLightsAttribute != null) {
                Array<BaseLight> array = new Array<>();
                Array<BaseLight> array2 = new Array<>();
                Array.ArrayIterator<DirectionalLight> it = directionalLightsAttribute.lights.iterator();
                while (it.hasNext()) {
                    DirectionalLight next = it.next();
                    if (next instanceof DirectionalShadowLight) {
                        ((DirectionalShadowLight) next).dispose();
                        array.add(next);
                        DirectionalLight directionalLight2 = new DirectionalLightEx().set(next);
                        array2.add(directionalLight2);
                        if (next == this.defaultLight) {
                            this.defaultLight = directionalLight2;
                        }
                    }
                }
                this.sceneManager.environment.remove(array);
                this.sceneManager.environment.add(array2);
            }
        }
        invalidateShaders();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void invalidateShaders() {
        this.shadersValid = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void invalidateOutlineShaders() {
        this.outlineShaderValid = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void invalidateSkybox() {
        this.skyboxValid = false;
    }

    private void validateSkybox() {
        if (this.skyboxValid) {
            return;
        }
        this.skyboxValid = true;
        this.skybox.dispose();
        this.skybox = new SceneSkybox(this.environmentCubemap, this.ui.skyboxSRGB.getSelected(), this.ui.skyboxGammaCorrection.isOn(), false);
        if (this.ui.skyBoxEnabled.isOn()) {
            this.sceneManager.setSkyBox(this.skybox);
        }
    }

    private void validateShaders() {
        String str;
        SceneAsset sceneAsset = this.rootModel;
        if (sceneAsset != null && !this.shadersValid) {
            this.shadersValid = true;
            ShaderProvider shaderProviderCreateShaderProvider = createShaderProvider(this.shaderMode, sceneAsset.maxBones);
            this.sceneManager.setShaderProvider(shaderProviderCreateShaderProvider);
            this.sceneManager.setDepthShaderProvider(PBRShaderProvider.createDefaultDepth(this.rootModel.maxBones));
            this.sceneManager.setTransmissionSource(this.ui.transmissionPassEnabled.isOn() ? new TransmissionSource(shaderProviderCreateShaderProvider) : null);
        }
        if (this.outlineShaderValid) {
            return;
        }
        this.outlineShaderValid = true;
        ShaderProgram shaderProgram = this.outlineShader;
        if (shaderProgram != null) {
            shaderProgram.dispose();
        }
        if (this.ui.outlinesEnabled.isOn()) {
            if (!this.ui.outlineDistFalloffOption.isOn()) {
                str = "";
            } else {
                str = "#define DISTANCE_FALLOFF\n";
            }
            ShaderProgram shaderProgram2 = new ShaderProgram(Gdx.files.classpath("net/mgsx/gltf/demo/shaders/outline.vs.glsl").readString(), str + Gdx.files.classpath("net/mgsx/gltf/demo/shaders/outline.fs.glsl").readString());
            this.outlineShader = shaderProgram2;
            if (!shaderProgram2.isCompiled()) {
                throw new GdxRuntimeException("Outline Shader failed: " + this.outlineShader.getLog());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setShader(ShaderMode shaderMode) {
        this.shaderMode = shaderMode;
        invalidateShaders();
    }

    private ShaderProvider createShaderProvider(ShaderMode shaderMode, int i) {
        LightUtils.LightsInfo lightsInfo = LightUtils.getLightsInfo(new LightUtils.LightsInfo(), this.sceneManager.environment);
        Gdx.f82app.log(TAG, "Reset shaders. Lights( dirs: " + lightsInfo.dirLights + ", points: " + lightsInfo.pointLights + ", spots: " + lightsInfo.spotLights + " )");
        int i2 = AnonymousClass29.$SwitchMap$net$mgsx$gltf$demo$GLTFDemo$ShaderMode[shaderMode.ordinal()];
        if (i2 == 2) {
            PBRShaderConfig pBRShaderConfigCreateDefaultConfig = PBRShaderProvider.createDefaultConfig();
            pBRShaderConfigCreateDefaultConfig.manualSRGB = this.ui.shaderSRGB.getSelected();
            pBRShaderConfigCreateDefaultConfig.manualGammaCorrection = this.ui.shaderGammaCorrection.isOn();
            pBRShaderConfigCreateDefaultConfig.transmissionSRGB = this.ui.transmissionSRGB.getSelected();
            pBRShaderConfigCreateDefaultConfig.mirrorSRGB = this.ui.mirrorSRGB.getSelected();
            pBRShaderConfigCreateDefaultConfig.numBones = i;
            pBRShaderConfigCreateDefaultConfig.numDirectionalLights = lightsInfo.dirLights;
            pBRShaderConfigCreateDefaultConfig.numPointLights = lightsInfo.pointLights;
            pBRShaderConfigCreateDefaultConfig.numSpotLights = lightsInfo.spotLights;
            return PBRShaderProvider.createDefault(pBRShaderConfigCreateDefaultConfig);
        }
        if (i2 != 3) {
            DefaultShader.Config config = new DefaultShader.Config();
            config.numBones = i;
            config.numDirectionalLights = lightsInfo.dirLights;
            config.numPointLights = lightsInfo.pointLights;
            config.numSpotLights = lightsInfo.spotLights;
            Scene scene = this.scene;
            if (scene != null) {
                MaterialConverter.makeCompatible(scene);
            }
            return new DefaultShaderProvider(config);
        }
        PBRShaderConfig pBRShaderConfigCreateDefaultConfig2 = PBRShaderProvider.createDefaultConfig();
        pBRShaderConfigCreateDefaultConfig2.vertexShader = Gdx.files.classpath("net/mgsx/gltf/demo/shaders/gltf-ceil-shading.vs.glsl").readString();
        pBRShaderConfigCreateDefaultConfig2.fragmentShader = Gdx.files.classpath("net/mgsx/gltf/demo/shaders/gltf-ceil-shading.fs.glsl").readString();
        pBRShaderConfigCreateDefaultConfig2.manualSRGB = this.ui.shaderSRGB.getSelected();
        pBRShaderConfigCreateDefaultConfig2.manualGammaCorrection = this.ui.shaderGammaCorrection.isOn();
        pBRShaderConfigCreateDefaultConfig2.numBones = i;
        pBRShaderConfigCreateDefaultConfig2.numDirectionalLights = lightsInfo.dirLights;
        pBRShaderConfigCreateDefaultConfig2.numPointLights = lightsInfo.pointLights;
        pBRShaderConfigCreateDefaultConfig2.numSpotLights = lightsInfo.spotLights;
        return PBRShaderProvider.createDefault(pBRShaderConfigCreateDefaultConfig2);
    }

    /* JADX INFO: renamed from: net.mgsx.gltf.demo.GLTFDemo$29, reason: invalid class name */
    static /* synthetic */ class AnonymousClass29 {
        static final /* synthetic */ int[] $SwitchMap$net$mgsx$gltf$demo$GLTFDemo$ShaderMode;

        static {
            int[] iArr = new int[ShaderMode.values().length];
            $SwitchMap$net$mgsx$gltf$demo$GLTFDemo$ShaderMode = iArr;
            try {
                iArr[ShaderMode.GOURAUD.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$net$mgsx$gltf$demo$GLTFDemo$ShaderMode[ShaderMode.PBR_MR.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$net$mgsx$gltf$demo$GLTFDemo$ShaderMode[ShaderMode.CeilShading.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setAnimation(String str) {
        Scene scene = this.scene;
        if (scene == null || scene.animationController == null) {
            return;
        }
        if (str == null || str.isEmpty()) {
            this.scene.animationController.setAnimation((String) null);
        } else {
            this.scene.animationController.animate(str, -1, 1.0f, null, 0.0f);
        }
    }

    private void clearScene() {
        Scene scene = this.scene;
        if (scene != null) {
            this.sceneManager.removeScene(scene);
            this.scene = null;
        }
        this.sceneManager.environment.remove(this.defaultLight);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void load(FileHandle fileHandle) {
        clearScene();
        SceneAsset sceneAsset = this.rootModel;
        if (sceneAsset != null) {
            if (USE_ASSET_MANAGER) {
                String str = this.lastFileName;
                if (str != null) {
                    this.assetManager.unload(str);
                }
            } else {
                sceneAsset.dispose();
            }
            this.rootModel = null;
        }
        Gdx.f82app.log(TAG, "loading " + fileHandle.name());
        this.lastFileName = fileHandle.path();
        if (USE_ASSET_MANAGER) {
            SceneAssetLoaderParameters sceneAssetLoaderParameters = new SceneAssetLoaderParameters();
            sceneAssetLoaderParameters.withData = true;
            this.assetManager.load(this.lastFileName, SceneAsset.class, sceneAssetLoaderParameters);
            this.assetManager.finishLoading();
            this.rootModel = (SceneAsset) this.assetManager.get(this.lastFileName, SceneAsset.class);
        } else if (fileHandle.extension().equalsIgnoreCase("glb")) {
            this.rootModel = new GLBLoader().load(fileHandle, true);
        } else if (fileHandle.extension().equalsIgnoreCase("gltf")) {
            this.rootModel = new GLTFLoader().load(fileHandle, true);
        }
        load();
        Gdx.f82app.log(TAG, "loaded " + fileHandle.path());
        new GLTFInspector().inspect(this.rootModel);
    }

    private void load() {
        if (this.rootModel.scenes.size > 1) {
            this.ui.setScenes(this.rootModel.scenes);
            this.ui.sceneSelector.setSelectedIndex(this.rootModel.scenes.indexOf(this.rootModel.scene, true));
        } else {
            this.ui.setScenes(null);
            load(new Scene(this.rootModel.scene));
        }
    }

    protected void load(SceneModel sceneModel) {
        if (sceneModel == null) {
            return;
        }
        load(new Scene(sceneModel));
    }

    private void load(Scene scene) {
        clearScene();
        this.scene = scene;
        BoundingBox boundingBox = new BoundingBox();
        scene.modelInstance.calculateBoundingBox(boundingBox);
        if (boundingBox.isValid()) {
            this.sceneBox.set(boundingBox);
        }
        this.ui.setMaterials(scene.modelInstance.materials);
        this.ui.setAnimations(scene.modelInstance.animations);
        this.ui.setNodes(NodeUtil.getAllNodes((Array<Node>) new Array(), scene.modelInstance));
        this.ui.setCameras(scene.cameras);
        this.ui.setLights(scene.lights);
        if (scene.getDirectionalLightCount() == 0) {
            resetDefaultLight();
            this.sceneManager.environment.add(this.defaultLight);
        }
        this.sceneManager.addScene(scene, true);
        setShadow(this.ui.lightShadow.isOn());
        DirectionalShadowLight firstDirectionalShadowLight = this.sceneManager.getFirstDirectionalShadowLight();
        if (firstDirectionalShadowLight != null) {
            firstDirectionalShadowLight.setBounds(this.sceneBox);
        }
        invalidateShaders();
    }

    private void resetDefaultLight() {
        this.defaultLight.direction.set(0.5f, -0.5f, -0.7f).nor();
        this.defaultLight.color.set(Color.WHITE);
        DirectionalLight directionalLight = this.defaultLight;
        if (directionalLight instanceof DirectionalLightEx) {
            DirectionalLightEx directionalLightEx = (DirectionalLightEx) directionalLight;
            directionalLightEx.intensity = 1.0f;
            directionalLightEx.updateColor();
        }
        GLTFDemoUI gLTFDemoUI = this.ui;
        if (gLTFDemoUI != null) {
            gLTFDemoUI.lightDirectionControl.set(this.defaultLight.direction);
        }
    }

    protected void setCamera(String str) {
        if (str == null) {
            return;
        }
        if (str.isEmpty()) {
            PerspectiveCamera perspectiveCamera = new PerspectiveCamera(60.0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            perspectiveCamera.up.set(Vector3.Y);
            BoundingBox boundingBox = this.sceneBox;
            Vector3 center = boundingBox.getCenter(new Vector3());
            perspectiveCamera.position.set(boundingBox.max).sub(center).scl(3.0f).add(center);
            perspectiveCamera.lookAt(center);
            float fMax = Math.max(boundingBox.getWidth(), Math.max(boundingBox.getHeight(), boundingBox.getDepth()));
            perspectiveCamera.near = fMax / 1000.0f;
            perspectiveCamera.far = fMax * 10.0f;
            perspectiveCamera.update(true);
            CameraInputController cameraInputController = new CameraInputController(perspectiveCamera);
            this.cameraControl = cameraInputController;
            cameraInputController.translateUnits = boundingBox.max.dst(boundingBox.min);
            this.cameraControl.target.set(center);
            this.cameraControl.pinchZoomFactor = boundingBox.max.dst(boundingBox.min);
            this.sceneManager.setCamera(perspectiveCamera);
        } else {
            Camera camera = this.scene.getCamera(str);
            this.cameraControl = new CameraInputController(camera);
            this.sceneManager.setCamera(camera);
        }
        Gdx.input.setInputProcessor(new InputMultiplexer(this.stage, this.cameraControl));
    }

    @Override // com.badlogic.gdx.ApplicationAdapter, com.badlogic.gdx.ApplicationListener
    public void resize(int i, int i2) {
        this.stage.getViewport().update(i, i2, true);
        this.sceneManager.updateViewport(i, i2);
    }

    @Override // com.badlogic.gdx.ApplicationAdapter, com.badlogic.gdx.ApplicationListener
    public void render() {
        if (Gdx.input.isKeyPressed(129) || Gdx.input.isKeyPressed(130)) {
            if (Gdx.input.isKeyJustPressed(81)) {
                this.ui.uiScaleSlider.setValue(this.ui.uiScaleSlider.getValue() * 2.0f);
            } else if (Gdx.input.isKeyJustPressed(69)) {
                this.ui.uiScaleSlider.setValue(this.ui.uiScaleSlider.getValue() / 2.0f);
            }
        }
        float deltaTime = Gdx.graphics.getDeltaTime();
        this.stage.act();
        validateShaders();
        validateSkybox();
        PBRFloatAttribute pBRFloatAttribute = (PBRFloatAttribute) this.sceneManager.environment.get(PBRFloatAttribute.class, PBRFloatAttribute.EmissiveIntensity);
        if (pBRFloatAttribute == null) {
            this.sceneManager.environment.set(PBRFloatAttribute.createEmissiveIntensity(this.ui.emissiveSlider.getValue()));
        } else {
            pBRFloatAttribute.value = this.ui.emissiveSlider.getValue();
        }
        CameraInputController cameraInputController = this.cameraControl;
        if (cameraInputController != null) {
            cameraInputController.update();
        }
        DirectionalShadowLight firstDirectionalShadowLight = this.sceneManager.getFirstDirectionalShadowLight();
        if (firstDirectionalShadowLight != null) {
            if (this.csm != null) {
                this.csm.setCascades(this.cameraControl.camera, firstDirectionalShadowLight, Math.max(this.sceneBox.getWidth(), Math.max(this.sceneBox.getHeight(), this.sceneBox.getDepth())), 4.0f);
            } else {
                firstDirectionalShadowLight.setBounds(this.sceneBox);
            }
        }
        this.sceneManager.update(deltaTime);
        Gdx.gl.glClearColor(this.ui.fogColor.value.r, this.ui.fogColor.value.g, this.ui.fogColor.value.b, 0.0f);
        Gdx.gl.glClear(16640);
        this.sceneManager.setAmbientLight(this.ui.ambiantSlider.getValue());
        ColorAttribute colorAttribute = (ColorAttribute) this.sceneManager.environment.get(ColorAttribute.class, ColorAttribute.Fog);
        if (colorAttribute != null) {
            colorAttribute.color.set(this.ui.fogColor.value);
        }
        FogAttribute fogAttribute = (FogAttribute) this.sceneManager.environment.get(FogAttribute.class, FogAttribute.FogEquation);
        if (fogAttribute != null) {
            fogAttribute.value.set(MathUtils.lerp(this.sceneManager.camera.near, this.sceneManager.camera.far, (this.ui.fogEquation.value.x + 1.0f) / 2.0f), MathUtils.lerp(this.sceneManager.camera.near, this.sceneManager.camera.far, (this.ui.fogEquation.value.y + 1.0f) / 2.0f), ((this.ui.fogEquation.value.z + 1.0f) * 10.0f) / 2.0f);
        }
        this.skybox.getColor().set(this.ui.skyBoxColor.value);
        DirectionalLight firstDirectionalLight = this.sceneManager.getFirstDirectionalLight();
        if (firstDirectionalLight != null) {
            float value = this.ui.lightSlider.getValue();
            if (firstDirectionalLight instanceof DirectionalLightEx) {
                DirectionalLightEx directionalLightEx = (DirectionalLightEx) firstDirectionalLight;
                directionalLightEx.intensity = value;
                directionalLightEx.updateColor();
            }
            firstDirectionalLight.direction.set(this.ui.lightDirectionControl.value).nor();
            ((PBRFloatAttribute) this.sceneManager.environment.get(PBRFloatAttribute.class, PBRFloatAttribute.ShadowBias)).value = this.ui.shadowBias.getValue() / 50.0f;
        }
        this.sceneManager.render();
        if (this.ui.outlinesEnabled.isOn()) {
            captureDepth();
            this.outlineShader.bind();
            float value2 = 1.0f - this.ui.outlinesWidth.getValue();
            float fPow = (float) Math.pow(this.ui.outlineDepthMin.getValue(), 10.0d);
            float fPow2 = (float) Math.pow(this.ui.outlineDepthMax.getValue(), 10.0d);
            this.outlineShader.setUniformf("u_size", Gdx.graphics.getWidth() * value2, Gdx.graphics.getHeight() * value2);
            this.outlineShader.setUniformf("u_depth_min", fPow);
            this.outlineShader.setUniformf("u_depth_max", fPow2);
            this.outlineShader.setUniformf("u_inner_color", this.ui.outlineInnerColor.getValue());
            this.outlineShader.setUniformf("u_outer_color", this.ui.outlineOuterColor.getValue());
            if (this.ui.outlineDistFalloffOption.isOn()) {
                float value3 = this.ui.outlineDistFalloff.getValue();
                if (value3 <= 0.0f) {
                    value3 = 0.001f;
                }
                this.outlineShader.setUniformf("u_depthRange", this.sceneManager.camera.far / (this.sceneManager.camera.near * value3));
            }
            this.spriteBatch.enableBlending();
            this.spriteBatch.getProjectionMatrix().setToOrtho2D(0.0f, 0.0f, 1.0f, 1.0f);
            this.spriteBatch.setShader(this.outlineShader);
            this.spriteBatch.begin();
            this.spriteBatch.draw(this.depthFbo.getColorBufferTexture(), 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f);
            this.spriteBatch.end();
            this.spriteBatch.setShader(null);
        }
        renderOverlays();
        ShaderProvider shaderProvider = this.sceneManager.getBatch().getShaderProvider();
        this.ui.shaderCount.setText(String.valueOf(shaderProvider instanceof PBRShaderProvider ? ((PBRShaderProvider) shaderProvider).getShaderCount() : 0));
        this.stage.draw();
    }

    protected void captureDepth() {
        FrameBuffer frameBufferEnsureFBO = ensureFBO(this.depthFbo, true);
        this.depthFbo = frameBufferEnsureFBO;
        frameBufferEnsureFBO.begin();
        Gdx.gl.glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
        Gdx.gl.glClear(16640);
        this.sceneManager.renderDepth();
        this.depthFbo.end();
    }

    private FrameBuffer ensureFBO(FrameBuffer frameBuffer, boolean z) {
        int backBufferWidth = Gdx.graphics.getBackBufferWidth();
        int backBufferHeight = Gdx.graphics.getBackBufferHeight();
        if (frameBuffer != null && frameBuffer.getWidth() == backBufferWidth && frameBuffer.getHeight() == backBufferHeight) {
            return frameBuffer;
        }
        if (frameBuffer != null) {
            frameBuffer.dispose();
        }
        return new FrameBuffer(Pixmap.Format.RGBA8888, backBufferWidth, backBufferHeight, z);
    }

    private void renderOverlays() {
        if (!this.ui.skeletonButton.isChecked() || this.scene == null) {
            return;
        }
        this.shapeRenderer.setProjectionMatrix(this.sceneManager.camera.combined);
        this.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        drawSkeleton(this.scene.modelInstance.nodes);
        this.shapeRenderer.end();
    }

    private void drawSkeleton(Iterable<Node> iterable) {
        for (Node node : iterable) {
            if (node.parts == null || node.parts.size == 0) {
                float f = this.cameraControl.translateUnits / 100.0f;
                this.shapeRenderer.setColor(Color.WHITE);
                Matrix4 matrix4 = node.globalTransform;
                Vector3 vector3 = v1;
                matrix4.getTranslation(vector3);
                this.shapeRenderer.box(vector3.x, vector3.y, vector3.z, f, f, f);
            }
            drawSkeleton(node.getChildren());
        }
    }
}
