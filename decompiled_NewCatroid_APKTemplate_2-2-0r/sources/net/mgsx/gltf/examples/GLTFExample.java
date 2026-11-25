package net.mgsx.gltf.examples;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Cubemap;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import net.mgsx.gltf.loaders.gltf.GLTFLoader;
import net.mgsx.gltf.scene3d.attributes.PBRCubemapAttribute;
import net.mgsx.gltf.scene3d.attributes.PBRTextureAttribute;
import net.mgsx.gltf.scene3d.scene.Scene;
import net.mgsx.gltf.scene3d.scene.SceneAsset;
import net.mgsx.gltf.scene3d.scene.SceneManager;
import net.mgsx.gltf.scene3d.scene.SceneSkybox;
import net.mgsx.gltf.scene3d.utils.EnvironmentUtil;
import org.catrobat.catroid.common.Constants;

/* JADX INFO: loaded from: classes12.dex */
public class GLTFExample extends ApplicationAdapter {
    private Texture brdfLUT;
    private PerspectiveCamera camera;
    private Cubemap diffuseCubemap;
    private Cubemap environmentCubemap;
    private Scene scene;
    private SceneAsset sceneAsset;
    private SceneManager sceneManager;
    private SceneSkybox skybox;
    private Cubemap specularCubemap;
    private float time;

    @Override // com.badlogic.gdx.ApplicationAdapter, com.badlogic.gdx.ApplicationListener
    public void create() {
        this.sceneAsset = new GLTFLoader().load(Gdx.files.internal("models/BoomBox/glTF/BoomBox.gltf"));
        this.scene = new Scene(this.sceneAsset.scene);
        SceneManager sceneManager = new SceneManager();
        this.sceneManager = sceneManager;
        sceneManager.addScene(this.scene);
        PerspectiveCamera perspectiveCamera = new PerspectiveCamera(60.0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.camera = perspectiveCamera;
        perspectiveCamera.near = 2.0E-5f;
        this.camera.far = 0.08f;
        this.sceneManager.setCamera(this.camera);
        this.environmentCubemap = EnvironmentUtil.createCubemap(new InternalFileHandleResolver(), "textures/environment/environment_", Constants.DEFAULT_IMAGE_EXTENSION, EnvironmentUtil.FACE_NAMES_NEG_POS);
        this.diffuseCubemap = EnvironmentUtil.createCubemap(new InternalFileHandleResolver(), "textures/diffuse/diffuse_", Constants.DEFAULT_IMAGE_EXTENSION, EnvironmentUtil.FACE_NAMES_NEG_POS);
        this.specularCubemap = EnvironmentUtil.createCubemap(new InternalFileHandleResolver(), "textures/specular/specular_", "_", Constants.DEFAULT_IMAGE_EXTENSION, 10, EnvironmentUtil.FACE_NAMES_NEG_POS);
        this.brdfLUT = new Texture(Gdx.files.classpath("net/mgsx/gltf/shaders/brdfLUT.png"));
        this.sceneManager.setAmbientLight(1.0f);
        this.sceneManager.environment.set(new PBRTextureAttribute(PBRTextureAttribute.BRDFLUTTexture, this.brdfLUT));
        this.sceneManager.environment.set(PBRCubemapAttribute.createSpecularEnv(this.specularCubemap));
        this.sceneManager.environment.set(PBRCubemapAttribute.createDiffuseEnv(this.diffuseCubemap));
        SceneSkybox sceneSkybox = new SceneSkybox(this.environmentCubemap);
        this.skybox = sceneSkybox;
        this.sceneManager.setSkyBox(sceneSkybox);
    }

    @Override // com.badlogic.gdx.ApplicationAdapter, com.badlogic.gdx.ApplicationListener
    public void resize(int i, int i2) {
        this.sceneManager.updateViewport(i, i2);
    }

    @Override // com.badlogic.gdx.ApplicationAdapter, com.badlogic.gdx.ApplicationListener
    public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        this.time += deltaTime;
        this.camera.position.setFromSpherical(0.7853982f, this.time * 0.3f).scl(0.02f);
        this.camera.up.set(Vector3.Y);
        this.camera.lookAt(Vector3.Zero);
        this.camera.update();
        Gdx.gl.glClear(16640);
        this.sceneManager.update(deltaTime);
        this.sceneManager.render();
    }

    @Override // com.badlogic.gdx.ApplicationAdapter, com.badlogic.gdx.ApplicationListener
    public void dispose() {
        this.sceneManager.dispose();
        this.sceneAsset.dispose();
        this.environmentCubemap.dispose();
        this.diffuseCubemap.dispose();
        this.specularCubemap.dispose();
        this.brdfLUT.dispose();
        this.skybox.dispose();
    }
}
