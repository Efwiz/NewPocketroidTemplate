package net.mgsx.gltf.scene3d.scene;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cubemap;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Attributes;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.RenderableProvider;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.CubemapAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.DepthTestAttribute;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.badlogic.gdx.graphics.g3d.utils.DefaultShaderProvider;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ShaderProvider;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pool;
import net.mgsx.gltf.scene3d.attributes.PBRMatrixAttribute;
import net.mgsx.gltf.scene3d.shaders.PBRShaderConfig;

/* JADX INFO: loaded from: classes12.dex */
public class SceneSkybox implements RenderableProvider, Updatable, Disposable {
    private final Matrix4 directionInverse;
    private final Matrix4 envRotationInverse;
    public final Environment environment;
    public float lodBias;
    private boolean lodEnabled;
    private boolean ownShaderProvider;
    private Renderable quad;
    private Model quadModel;
    private ShaderProvider shaderProvider;

    public static void enableMipmaps(Cubemap cubemap) {
        cubemap.bind();
        Gdx.gl.glGenerateMipmap(GL20.GL_TEXTURE_CUBE_MAP);
        cubemap.setFilter(Texture.TextureFilter.MipMap, Texture.TextureFilter.Linear);
    }

    public SceneSkybox(Cubemap cubemap) {
        this(cubemap, null);
    }

    public SceneSkybox(Cubemap cubemap, PBRShaderConfig.SRGB srgb, Float f, boolean z) {
        this.lodBias = 0.0f;
        this.environment = new Environment();
        this.directionInverse = new Matrix4();
        this.envRotationInverse = new Matrix4();
        this.lodEnabled = z;
        createShaderProvider(srgb, f);
        createQuad(cubemap);
    }

    public SceneSkybox(Cubemap cubemap, PBRShaderConfig.SRGB srgb, boolean z, boolean z2) {
        this.lodBias = 0.0f;
        this.environment = new Environment();
        this.directionInverse = new Matrix4();
        this.envRotationInverse = new Matrix4();
        this.lodEnabled = z2;
        createShaderProvider(srgb, z ? Float.valueOf(2.2f) : null);
        createQuad(cubemap);
    }

    public SceneSkybox(Cubemap cubemap, ShaderProvider shaderProvider) {
        this.lodBias = 0.0f;
        this.environment = new Environment();
        this.directionInverse = new Matrix4();
        this.envRotationInverse = new Matrix4();
        if (shaderProvider == null) {
            createShaderProvider(PBRShaderConfig.SRGB.NONE, null);
        } else {
            this.shaderProvider = shaderProvider;
        }
        createQuad(cubemap);
    }

    private void createShaderProvider(PBRShaderConfig.SRGB srgb, Float f) {
        String strConcat;
        String str;
        if (this.lodEnabled) {
            if (!Gdx.graphics.getGLVersion().isVersionEqualToOrHigher(3, 0)) {
                throw new IllegalArgumentException("GDX-GLTF Skybox LOD requires GLES 3+");
            }
            if (Gdx.f82app.getType() == Application.ApplicationType.Desktop) {
                str = "#version 130\n";
            } else {
                str = "#version 300 es\n";
            }
            strConcat = str.concat("#define GLSL3\n");
        } else {
            strConcat = "";
        }
        if (srgb != PBRShaderConfig.SRGB.NONE) {
            strConcat = strConcat + "#define MANUAL_SRGB\n";
            if (srgb == PBRShaderConfig.SRGB.FAST) {
                strConcat = strConcat + "#define SRGB_FAST_APPROXIMATION\n";
            }
        }
        if (f != null) {
            strConcat = strConcat + "#define GAMMA_CORRECTION " + f + "\n";
        }
        if (this.lodEnabled) {
            strConcat = strConcat + "#define ENV_LOD\n";
        }
        DefaultShader.Config config = new DefaultShader.Config();
        config.vertexShader = Gdx.files.classpath("net/mgsx/gltf/shaders/skybox.vs.glsl").readString();
        config.fragmentShader = Gdx.files.classpath("net/mgsx/gltf/shaders/skybox.fs.glsl").readString();
        this.ownShaderProvider = true;
        this.shaderProvider = new SkyboxShaderProvider(config, strConcat);
    }

    private void createQuad(Cubemap cubemap) {
        Model modelCreateRect = new ModelBuilder().createRect(-1.0f, -1.0f, 0.0f, 1.0f, -1.0f, 0.0f, 1.0f, 1.0f, 0.0f, -1.0f, 1.0f, 0.0f, 0.0f, 0.0f, -1.0f, new Material(), 1L);
        this.quadModel = modelCreateRect;
        this.quad = modelCreateRect.nodes.first().parts.first().setRenderable(new Renderable());
        this.environment.set(new CubemapAttribute(CubemapAttribute.EnvironmentMap, cubemap));
        this.quad.environment = this.environment;
        this.quad.userData = SceneRenderableSorter.Hints.OPAQUE_LAST;
        this.quad.material = new Material(ColorAttribute.createDiffuse(Color.WHITE));
        this.quad.material.set(new DepthTestAttribute(false));
    }

    private class SkyboxShader extends DefaultShader {
        private int u_lod;

        public SkyboxShader(Renderable renderable, DefaultShader.Config config) {
            super(renderable, config);
        }

        @Override // com.badlogic.gdx.graphics.g3d.shaders.DefaultShader, com.badlogic.gdx.graphics.g3d.Shader
        public void init() {
            super.init();
            this.u_lod = Gdx.gl.glGetUniformLocation(this.program.getHandle(), "u_lod");
        }

        @Override // com.badlogic.gdx.graphics.g3d.shaders.DefaultShader
        protected void bindMaterial(Attributes attributes) {
            super.bindMaterial(attributes);
            if (this.u_lod >= 0) {
                this.program.setUniformf(this.u_lod, SceneSkybox.this.lodBias);
            }
        }
    }

    private class SkyboxShaderProvider extends DefaultShaderProvider {
        private String fsPrefix;

        public SkyboxShaderProvider(DefaultShader.Config config, String str) {
            super(config);
            this.fsPrefix = str;
        }

        @Override // com.badlogic.gdx.graphics.g3d.utils.DefaultShaderProvider, com.badlogic.gdx.graphics.g3d.utils.BaseShaderProvider
        protected Shader createShader(Renderable renderable) {
            String str = ShaderProgram.prependFragmentCode;
            ShaderProgram.prependFragmentCode = this.fsPrefix;
            SkyboxShader skyboxShader = SceneSkybox.this.new SkyboxShader(renderable, this.config);
            ShaderProgram.prependFragmentCode = str;
            return skyboxShader;
        }
    }

    public SceneSkybox set(Cubemap cubemap) {
        this.quad.environment.set(new CubemapAttribute(CubemapAttribute.EnvironmentMap, cubemap));
        return this;
    }

    public Color getColor() {
        return ((ColorAttribute) this.quad.material.get(ColorAttribute.class, ColorAttribute.Diffuse)).color;
    }

    @Override // net.mgsx.gltf.scene3d.scene.Updatable
    public void update(Camera camera, float f) {
        this.directionInverse.set(camera.view);
        this.directionInverse.setTranslation(0.0f, 0.0f, 1.0E-30f);
        PBRMatrixAttribute pBRMatrixAttribute = (PBRMatrixAttribute) this.quad.environment.get(PBRMatrixAttribute.class, PBRMatrixAttribute.EnvRotation);
        if (pBRMatrixAttribute != null) {
            this.directionInverse.mul(this.envRotationInverse.set(pBRMatrixAttribute.matrix).tra());
        }
        this.quad.worldTransform.set(camera.projection).mul(this.directionInverse).inv();
    }

    @Override // com.badlogic.gdx.graphics.g3d.RenderableProvider
    public void getRenderables(Array<Renderable> array, Pool<Renderable> pool) {
        Renderable renderable = this.quad;
        renderable.shader = this.shaderProvider.getShader(renderable);
        array.add(this.quad);
    }

    @Override // com.badlogic.gdx.utils.Disposable
    public void dispose() {
        ShaderProvider shaderProvider = this.shaderProvider;
        if (shaderProvider != null && this.ownShaderProvider) {
            shaderProvider.dispose();
        }
        this.quadModel.dispose();
    }

    public void setRotation(float f) {
        PBRMatrixAttribute pBRMatrixAttribute = (PBRMatrixAttribute) this.quad.environment.get(PBRMatrixAttribute.class, PBRMatrixAttribute.EnvRotation);
        if (pBRMatrixAttribute != null) {
            pBRMatrixAttribute.set(f);
        } else {
            this.quad.environment.set(PBRMatrixAttribute.createEnvRotation(f));
        }
    }

    public void setRotation(Matrix4 matrix4) {
        PBRMatrixAttribute pBRMatrixAttribute = (PBRMatrixAttribute) this.quad.environment.get(PBRMatrixAttribute.class, PBRMatrixAttribute.EnvRotation);
        if (matrix4 == null) {
            if (pBRMatrixAttribute != null) {
                this.quad.environment.remove(PBRMatrixAttribute.EnvRotation);
            }
        } else if (pBRMatrixAttribute != null) {
            pBRMatrixAttribute.matrix.set(matrix4);
        } else {
            this.quad.environment.set(PBRMatrixAttribute.createEnvRotation(matrix4));
        }
    }
}
