package net.mgsx.gltf.scene3d.scene;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.Attribute;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.RenderableProvider;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.DirectionalLightsAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.PointLightsAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.SpotLightsAttribute;
import com.badlogic.gdx.graphics.g3d.environment.BaseLight;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.g3d.environment.SpotLight;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.utils.DepthShaderProvider;
import com.badlogic.gdx.graphics.g3d.utils.RenderableSorter;
import com.badlogic.gdx.graphics.g3d.utils.ShaderProvider;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ObjectMap;
import net.mgsx.gltf.scene3d.attributes.PBRMatrixAttribute;
import net.mgsx.gltf.scene3d.lights.DirectionalShadowLight;
import net.mgsx.gltf.scene3d.lights.PointLightEx;
import net.mgsx.gltf.scene3d.lights.SpotLightEx;
import net.mgsx.gltf.scene3d.shaders.PBRCommon;
import net.mgsx.gltf.scene3d.shaders.PBRShaderProvider;
import net.mgsx.gltf.scene3d.utils.EnvironmentCache;
import net.mgsx.gltf.scene3d.utils.EnvironmentUtil;

/* JADX INFO: loaded from: classes12.dex */
public class SceneManager implements Disposable {
    private ModelBatch batch;
    public Camera camera;
    private CascadeShadowMap cascadeShadowMap;
    protected final EnvironmentCache computedEnvironement;
    private ModelBatch depthBatch;
    public Environment environment;
    private MirrorSource mirrorSource;
    private PointLightsAttribute pointLights;
    private final Array<RenderableProvider> renderableProviders;
    private RenderableSorter renderableSorter;
    private SceneSkybox skyBox;
    private SpotLightsAttribute spotLights;
    private TransmissionSource transmissionSource;

    public SceneManager() {
        this(24);
    }

    public SceneManager(int i) {
        this(PBRShaderProvider.createDefault(i), PBRShaderProvider.createDefaultDepth(i));
    }

    public SceneManager(ShaderProvider shaderProvider, DepthShaderProvider depthShaderProvider) {
        this(shaderProvider, depthShaderProvider, new SceneRenderableSorter());
    }

    public SceneManager(ShaderProvider shaderProvider, DepthShaderProvider depthShaderProvider, RenderableSorter renderableSorter) {
        this.renderableProviders = new Array<>();
        this.environment = new Environment();
        this.computedEnvironement = new EnvironmentCache();
        this.pointLights = new PointLightsAttribute();
        this.spotLights = new SpotLightsAttribute();
        this.renderableSorter = renderableSorter;
        this.batch = new ModelBatch(shaderProvider, renderableSorter);
        this.depthBatch = new ModelBatch(depthShaderProvider);
        this.environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 1.0f, 1.0f, 1.0f, 1.0f));
    }

    public void setEnvironmentRotation(float f) {
        PBRMatrixAttribute pBRMatrixAttribute = (PBRMatrixAttribute) this.environment.get(PBRMatrixAttribute.class, PBRMatrixAttribute.EnvRotation);
        if (pBRMatrixAttribute != null) {
            pBRMatrixAttribute.set(f);
        } else {
            this.environment.set(PBRMatrixAttribute.createEnvRotation(f));
        }
    }

    public void removeEnvironmentRotation() {
        this.environment.remove(PBRMatrixAttribute.EnvRotation);
    }

    public ModelBatch getBatch() {
        return this.batch;
    }

    public void setBatch(ModelBatch modelBatch) {
        this.batch = modelBatch;
    }

    public void setDepthBatch(ModelBatch modelBatch) {
        this.depthBatch = modelBatch;
    }

    public ModelBatch getDepthBatch() {
        return this.depthBatch;
    }

    public void setShaderProvider(ShaderProvider shaderProvider) {
        this.batch.dispose();
        this.batch = new ModelBatch(shaderProvider, this.renderableSorter);
    }

    public void setDepthShaderProvider(DepthShaderProvider depthShaderProvider) {
        this.depthBatch.dispose();
        this.depthBatch = new ModelBatch(depthShaderProvider);
    }

    public void setTransmissionSource(TransmissionSource transmissionSource) {
        TransmissionSource transmissionSource2 = this.transmissionSource;
        if (transmissionSource2 != transmissionSource) {
            if (transmissionSource2 != null) {
                transmissionSource2.dispose();
            }
            this.transmissionSource = transmissionSource;
        }
    }

    public void setMirrorSource(MirrorSource mirrorSource) {
        MirrorSource mirrorSource2 = this.mirrorSource;
        if (mirrorSource2 != mirrorSource) {
            if (mirrorSource2 != null) {
                mirrorSource2.dispose();
            }
            this.mirrorSource = mirrorSource;
        }
    }

    public void setCascadeShadowMap(CascadeShadowMap cascadeShadowMap) {
        CascadeShadowMap cascadeShadowMap2 = this.cascadeShadowMap;
        if (cascadeShadowMap2 != cascadeShadowMap) {
            if (cascadeShadowMap2 != null) {
                cascadeShadowMap2.dispose();
            }
            this.cascadeShadowMap = cascadeShadowMap;
        }
    }

    public void addScene(Scene scene) {
        addScene(scene, true);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void addScene(Scene scene, boolean z) {
        this.renderableProviders.add(scene);
        if (z) {
            ObjectMap.Entries<Node, BaseLight> it = scene.lights.iterator();
            while (it.hasNext()) {
                this.environment.add((BaseLight) it.next().value);
            }
        }
    }

    public void update(float f) {
        if (this.camera != null) {
            updateEnvironment();
            Array.ArrayIterator<RenderableProvider> it = this.renderableProviders.iterator();
            while (it.hasNext()) {
                RenderableProvider next = it.next();
                if (next instanceof Updatable) {
                    ((Updatable) next).update(this.camera, f);
                }
            }
            SceneSkybox sceneSkybox = this.skyBox;
            if (sceneSkybox != null) {
                sceneSkybox.update(this.camera, f);
            }
        }
    }

    protected void updateSkyboxRotation() {
        PBRMatrixAttribute pBRMatrixAttribute;
        if (this.skyBox == null || (pBRMatrixAttribute = (PBRMatrixAttribute) this.environment.get(PBRMatrixAttribute.class, PBRMatrixAttribute.EnvRotation)) == null) {
            return;
        }
        this.skyBox.setRotation(pBRMatrixAttribute.matrix);
    }

    protected void updateEnvironment() {
        updateSkyboxRotation();
        this.computedEnvironement.setCache(this.environment);
        this.pointLights.lights.clear();
        this.spotLights.lights.clear();
        Environment environment = this.environment;
        if (environment != null) {
            for (Attribute attribute : environment) {
                if (attribute instanceof PointLightsAttribute) {
                    this.pointLights.lights.addAll(((PointLightsAttribute) attribute).lights);
                    this.computedEnvironement.replaceCache(this.pointLights);
                } else if (attribute instanceof SpotLightsAttribute) {
                    this.spotLights.lights.addAll(((SpotLightsAttribute) attribute).lights);
                    this.computedEnvironement.replaceCache(this.spotLights);
                } else {
                    this.computedEnvironement.set(attribute);
                }
            }
        }
        cullLights();
    }

    protected void cullLights() {
        PointLightsAttribute pointLightsAttribute = (PointLightsAttribute) this.environment.get(PointLightsAttribute.class, PointLightsAttribute.Type);
        if (pointLightsAttribute != null) {
            Array.ArrayIterator<PointLight> it = pointLightsAttribute.lights.iterator();
            while (it.hasNext()) {
                PointLight next = it.next();
                if (next instanceof PointLightEx) {
                    PointLightEx pointLightEx = (PointLightEx) next;
                    if (pointLightEx.range != null && !this.camera.frustum.sphereInFrustum(pointLightEx.position, pointLightEx.range.floatValue())) {
                        this.pointLights.lights.removeValue(pointLightEx, true);
                    }
                }
            }
        }
        SpotLightsAttribute spotLightsAttribute = (SpotLightsAttribute) this.environment.get(SpotLightsAttribute.class, SpotLightsAttribute.Type);
        if (spotLightsAttribute != null) {
            Array.ArrayIterator<SpotLight> it2 = spotLightsAttribute.lights.iterator();
            while (it2.hasNext()) {
                SpotLight next2 = it2.next();
                if (next2 instanceof SpotLightEx) {
                    SpotLightEx spotLightEx = (SpotLightEx) next2;
                    if (spotLightEx.range != null && !this.camera.frustum.sphereInFrustum(spotLightEx.position, spotLightEx.range.floatValue())) {
                        this.spotLights.lights.removeValue(spotLightEx, true);
                    }
                }
            }
        }
    }

    public void render() {
        if (this.camera == null) {
            return;
        }
        PBRCommon.enableSeamlessCubemaps();
        renderShadows();
        renderMirror();
        renderTransmission();
        renderColors();
    }

    public void renderMirror() {
        MirrorSource mirrorSource = this.mirrorSource;
        if (mirrorSource != null) {
            mirrorSource.begin(this.camera, this.computedEnvironement, this.skyBox);
            renderColors();
            this.mirrorSource.end();
        }
    }

    public void renderTransmission() {
        TransmissionSource transmissionSource = this.transmissionSource;
        if (transmissionSource != null) {
            transmissionSource.begin(this.camera);
            this.transmissionSource.render(this.renderableProviders, this.environment);
            SceneSkybox sceneSkybox = this.skyBox;
            if (sceneSkybox != null) {
                this.transmissionSource.render(sceneSkybox);
            }
            this.transmissionSource.end();
            this.computedEnvironement.set(this.transmissionSource.attribute);
        }
    }

    public void renderShadows() {
        DirectionalShadowLight firstDirectionalShadowLight = getFirstDirectionalShadowLight();
        if (firstDirectionalShadowLight != null) {
            firstDirectionalShadowLight.begin();
            renderDepth(firstDirectionalShadowLight.getCamera());
            firstDirectionalShadowLight.end();
            this.environment.shadowMap = firstDirectionalShadowLight;
        } else {
            this.environment.shadowMap = null;
        }
        this.computedEnvironement.shadowMap = this.environment.shadowMap;
        CascadeShadowMap cascadeShadowMap = this.cascadeShadowMap;
        if (cascadeShadowMap != null) {
            Array.ArrayIterator<DirectionalShadowLight> it = cascadeShadowMap.lights.iterator();
            while (it.hasNext()) {
                DirectionalShadowLight next = it.next();
                next.begin();
                renderDepth(next.getCamera());
                next.end();
            }
            this.computedEnvironement.set(this.cascadeShadowMap.attribute);
        }
    }

    public void renderDepth() {
        renderDepth(this.camera);
    }

    public void renderDepth(Camera camera) {
        this.depthBatch.begin(camera);
        this.depthBatch.render(this.renderableProviders);
        this.depthBatch.end();
    }

    public void renderColors() {
        this.batch.begin(this.camera);
        this.batch.render(this.renderableProviders, this.computedEnvironement);
        SceneSkybox sceneSkybox = this.skyBox;
        if (sceneSkybox != null) {
            this.batch.render(sceneSkybox);
        }
        this.batch.end();
    }

    public DirectionalLight getFirstDirectionalLight() {
        DirectionalLightsAttribute directionalLightsAttribute = (DirectionalLightsAttribute) this.environment.get(DirectionalLightsAttribute.class, DirectionalLightsAttribute.Type);
        if (directionalLightsAttribute == null) {
            return null;
        }
        Array.ArrayIterator<DirectionalLight> it = directionalLightsAttribute.lights.iterator();
        while (it.hasNext()) {
            DirectionalLight next = it.next();
            if (next instanceof DirectionalLight) {
                return next;
            }
        }
        return null;
    }

    public DirectionalShadowLight getFirstDirectionalShadowLight() {
        DirectionalLightsAttribute directionalLightsAttribute = (DirectionalLightsAttribute) this.environment.get(DirectionalLightsAttribute.class, DirectionalLightsAttribute.Type);
        if (directionalLightsAttribute == null) {
            return null;
        }
        Array.ArrayIterator<DirectionalLight> it = directionalLightsAttribute.lights.iterator();
        while (it.hasNext()) {
            DirectionalLight next = it.next();
            if (next instanceof DirectionalShadowLight) {
                return (DirectionalShadowLight) next;
            }
        }
        return null;
    }

    public void setSkyBox(SceneSkybox sceneSkybox) {
        this.skyBox = sceneSkybox;
    }

    public SceneSkybox getSkyBox() {
        return this.skyBox;
    }

    public void setAmbientLight(float f) {
        ((ColorAttribute) this.environment.get(ColorAttribute.class, ColorAttribute.AmbientLight)).color.set(f, f, f, 1.0f);
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void removeScene(Scene scene) {
        this.renderableProviders.removeValue(scene, true);
        ObjectMap.Entries<Node, BaseLight> it = scene.lights.iterator();
        while (it.hasNext()) {
            this.environment.remove((BaseLight) it.next().value);
        }
    }

    public Array<RenderableProvider> getRenderableProviders() {
        return this.renderableProviders;
    }

    public void updateViewport(float f, float f2) {
        Camera camera = this.camera;
        if (camera != null) {
            camera.viewportWidth = f;
            this.camera.viewportHeight = f2;
            this.camera.update(true);
        }
    }

    public int getActiveLightsCount() {
        return EnvironmentUtil.getLightCount(this.computedEnvironement);
    }

    public int getTotalLightsCount() {
        return EnvironmentUtil.getLightCount(this.environment);
    }

    @Override // com.badlogic.gdx.utils.Disposable
    public void dispose() {
        this.batch.dispose();
        this.depthBatch.dispose();
        TransmissionSource transmissionSource = this.transmissionSource;
        if (transmissionSource != null) {
            transmissionSource.dispose();
        }
        MirrorSource mirrorSource = this.mirrorSource;
        if (mirrorSource != null) {
            mirrorSource.dispose();
        }
        CascadeShadowMap cascadeShadowMap = this.cascadeShadowMap;
        if (cascadeShadowMap != null) {
            cascadeShadowMap.dispose();
        }
    }
}
