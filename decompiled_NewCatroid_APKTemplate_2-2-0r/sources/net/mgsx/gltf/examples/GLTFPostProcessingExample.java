package net.mgsx.gltf.examples;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g3d.shaders.DepthShader;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import net.mgsx.gltf.loaders.gltf.GLTFLoader;
import net.mgsx.gltf.scene3d.scene.Scene;
import net.mgsx.gltf.scene3d.scene.SceneManager;
import net.mgsx.gltf.scene3d.shaders.PBRShaderConfig;
import net.mgsx.gltf.scene3d.shaders.PBRShaderProvider;

/* JADX INFO: loaded from: classes12.dex */
public class GLTFPostProcessingExample extends ApplicationAdapter {
    private Batch batch;
    private PerspectiveCamera camera;
    private CameraInputController cameraController;
    private ShaderProgram effectShader;
    private FrameBuffer fbo;
    private SceneManager sceneManager;
    private Viewport viewport;
    private final Color clearColor = new Color(Color.BLACK);
    private final boolean postProcessingEnabled = false;
    private final boolean customShaderEnabled = false;

    @Override // com.badlogic.gdx.ApplicationAdapter, com.badlogic.gdx.ApplicationListener
    public void create() {
        SceneManager sceneManagerCreateSceneManager = createSceneManager();
        this.sceneManager = sceneManagerCreateSceneManager;
        PerspectiveCamera perspectiveCamera = new PerspectiveCamera();
        this.camera = perspectiveCamera;
        sceneManagerCreateSceneManager.camera = perspectiveCamera;
        this.camera.fieldOfView = 50.0f;
        this.camera.near = 0.01f;
        this.camera.far = 10.0f;
        this.camera.position.set(1.0f, 1.0f, 1.0f).scl(0.1f);
        this.camera.up.set(Vector3.Y);
        this.camera.lookAt(Vector3.Zero);
        this.camera.update();
        this.sceneManager.addScene(new Scene(new GLTFLoader().load(Gdx.files.internal("models/BoomBox/glTF/BoomBox.gltf")).scene));
        CameraInputController cameraInputController = new CameraInputController(this.camera);
        this.cameraController = cameraInputController;
        cameraInputController.translateUnits = 0.1f;
        Gdx.input.setInputProcessor(this.cameraController);
        this.viewport = new FitViewport(1000.0f, 500.0f, this.camera);
    }

    private SceneManager createSceneManager() {
        PBRShaderConfig pBRShaderConfigCreateDefaultConfig = PBRShaderProvider.createDefaultConfig();
        pBRShaderConfigCreateDefaultConfig.numBones = 32;
        pBRShaderConfigCreateDefaultConfig.numDirectionalLights = 1;
        pBRShaderConfigCreateDefaultConfig.numPointLights = 0;
        pBRShaderConfigCreateDefaultConfig.numSpotLights = 0;
        DepthShader.Config config = new DepthShader.Config();
        config.numBones = pBRShaderConfigCreateDefaultConfig.numBones;
        return new SceneManager(PBRShaderProvider.createDefault(pBRShaderConfigCreateDefaultConfig), PBRShaderProvider.createDefaultDepth(config));
    }

    @Override // com.badlogic.gdx.ApplicationAdapter, com.badlogic.gdx.ApplicationListener
    public void resize(int i, int i2) {
        this.viewport.update(i, i2);
    }

    @Override // com.badlogic.gdx.ApplicationAdapter, com.badlogic.gdx.ApplicationListener
    public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        this.cameraController.update();
        this.sceneManager.update(deltaTime);
        Gdx.gl.glClearColor(this.clearColor.r, this.clearColor.g, this.clearColor.b, this.clearColor.a);
        Gdx.gl.glClear(16640);
        renderDefault();
    }

    private void renderDefault() {
        this.viewport.apply();
        this.sceneManager.render();
    }

    private void renderWithPostProcessing() {
        this.sceneManager.renderShadows();
        this.fbo.begin();
        this.sceneManager.renderColors();
        this.fbo.end();
        this.effectShader.bind();
        setEffectUniforms();
        this.viewport.apply();
        this.batch.setShader(this.effectShader);
        this.batch.getProjectionMatrix().setToOrtho2D(0.0f, 0.0f, 1.0f, 1.0f);
        this.batch.begin();
        this.batch.draw(this.fbo.getColorBufferTexture(), 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f);
        this.batch.end();
        this.batch.setShader(null);
    }

    private void setEffectUniforms() {
        this.effectShader.setUniformf("u_effectLevel", 0.9f);
    }
}
