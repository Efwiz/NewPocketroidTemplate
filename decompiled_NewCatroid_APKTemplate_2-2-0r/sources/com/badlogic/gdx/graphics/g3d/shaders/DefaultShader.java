package com.badlogic.gdx.graphics.g3d.shaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.g3d.Attribute;
import com.badlogic.gdx.graphics.g3d.Attributes;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.CubemapAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.DepthTestAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.DirectionalLightsAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.IntAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.PointLightsAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.SpotLightsAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.environment.AmbientCubemap;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.g3d.environment.SpotLight;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.huawei.hms.ml.language.common.utils.Constant;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/shaders/DefaultShader.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class DefaultShader extends BaseShader {
    private static String defaultFragmentShader;
    private static String defaultVertexShader;
    protected final AmbientCubemap ambientCubemap;
    protected final long attributesMask;
    private int[] boneWeightsLocations;
    protected final Config config;
    protected int dirLightsColorOffset;
    protected int dirLightsDirectionOffset;
    protected int dirLightsLoc;
    protected int dirLightsSize;
    protected final DirectionalLight[] directionalLights;
    protected final boolean environmentCubemap;
    protected final boolean lighting;
    private boolean lightsSet;
    private final Matrix3 normalMatrix;
    protected final PointLight[] pointLights;
    protected int pointLightsColorOffset;
    protected int pointLightsIntensityOffset;
    protected int pointLightsLoc;
    protected int pointLightsPositionOffset;
    protected int pointLightsSize;
    private Renderable renderable;
    protected final boolean shadowMap;
    protected final SpotLight[] spotLights;
    protected int spotLightsColorOffset;
    protected int spotLightsCutoffAngleOffset;
    protected int spotLightsDirectionOffset;
    protected int spotLightsExponentOffset;
    protected int spotLightsIntensityOffset;
    protected int spotLightsLoc;
    protected int spotLightsPositionOffset;
    protected int spotLightsSize;
    private final int textureCoordinates;
    private float time;
    private final Vector3 tmpV1;
    public final int u_alphaTest;
    protected final int u_ambientCubemap;
    public final int u_ambientTexture;
    public final int u_ambientUVTransform;
    public final int u_bones;
    public final int u_cameraDirection;
    public final int u_cameraNearFar;
    public final int u_cameraPosition;
    public final int u_cameraUp;
    public final int u_diffuseColor;
    public final int u_diffuseTexture;
    public final int u_diffuseUVTransform;
    protected final int u_dirLights0color;
    protected final int u_dirLights0direction;
    protected final int u_dirLights1color;
    public final int u_emissiveColor;
    public final int u_emissiveTexture;
    public final int u_emissiveUVTransform;
    protected final int u_environmentCubemap;
    protected final int u_fogColor;
    public final int u_normalMatrix;
    public final int u_normalTexture;
    public final int u_normalUVTransform;
    public final int u_opacity;
    protected final int u_pointLights0color;
    protected final int u_pointLights0intensity;
    protected final int u_pointLights0position;
    protected final int u_pointLights1color;
    public final int u_projTrans;
    public final int u_projViewTrans;
    public final int u_projViewWorldTrans;
    public final int u_reflectionColor;
    public final int u_reflectionTexture;
    public final int u_reflectionUVTransform;
    protected final int u_shadowMapProjViewTrans;
    protected final int u_shadowPCFOffset;
    protected final int u_shadowTexture;
    public final int u_shininess;
    public final int u_specularColor;
    public final int u_specularTexture;
    public final int u_specularUVTransform;
    protected final int u_spotLights0color;
    protected final int u_spotLights0cutoffAngle;
    protected final int u_spotLights0direction;
    protected final int u_spotLights0exponent;
    protected final int u_spotLights0intensity;
    protected final int u_spotLights0position;
    protected final int u_spotLights1color;
    public final int u_time;
    public final int u_viewTrans;
    public final int u_viewWorldTrans;
    public final int u_worldTrans;
    private final long vertexMask;
    protected static long implementedFlags = (((BlendingAttribute.Type | TextureAttribute.Diffuse) | ColorAttribute.Diffuse) | ColorAttribute.Specular) | FloatAttribute.Shininess;

    @Deprecated
    public static int defaultCullFace = GL20.GL_BACK;

    @Deprecated
    public static int defaultDepthFunc = 515;
    private static final long optionalAttributes = IntAttribute.CullFace | DepthTestAttribute.Type;
    private static final Attributes tmpAttributes = new Attributes();

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/shaders/DefaultShader$Inputs.class
      classes8.dex
     */
    public static class Inputs {
        public static final BaseShader.Uniform projTrans = new BaseShader.Uniform("u_projTrans");
        public static final BaseShader.Uniform viewTrans = new BaseShader.Uniform("u_viewTrans");
        public static final BaseShader.Uniform projViewTrans = new BaseShader.Uniform("u_projViewTrans");
        public static final BaseShader.Uniform cameraPosition = new BaseShader.Uniform("u_cameraPosition");
        public static final BaseShader.Uniform cameraDirection = new BaseShader.Uniform("u_cameraDirection");
        public static final BaseShader.Uniform cameraUp = new BaseShader.Uniform("u_cameraUp");
        public static final BaseShader.Uniform cameraNearFar = new BaseShader.Uniform("u_cameraNearFar");
        public static final BaseShader.Uniform worldTrans = new BaseShader.Uniform("u_worldTrans");
        public static final BaseShader.Uniform viewWorldTrans = new BaseShader.Uniform("u_viewWorldTrans");
        public static final BaseShader.Uniform projViewWorldTrans = new BaseShader.Uniform("u_projViewWorldTrans");
        public static final BaseShader.Uniform normalMatrix = new BaseShader.Uniform("u_normalMatrix");
        public static final BaseShader.Uniform bones = new BaseShader.Uniform("u_bones");
        public static final BaseShader.Uniform shininess = new BaseShader.Uniform("u_shininess", FloatAttribute.Shininess);
        public static final BaseShader.Uniform opacity = new BaseShader.Uniform("u_opacity", BlendingAttribute.Type);
        public static final BaseShader.Uniform diffuseColor = new BaseShader.Uniform("u_diffuseColor", ColorAttribute.Diffuse);
        public static final BaseShader.Uniform diffuseTexture = new BaseShader.Uniform("u_diffuseTexture", TextureAttribute.Diffuse);
        public static final BaseShader.Uniform diffuseUVTransform = new BaseShader.Uniform("u_diffuseUVTransform", TextureAttribute.Diffuse);
        public static final BaseShader.Uniform specularColor = new BaseShader.Uniform("u_specularColor", ColorAttribute.Specular);
        public static final BaseShader.Uniform specularTexture = new BaseShader.Uniform("u_specularTexture", TextureAttribute.Specular);
        public static final BaseShader.Uniform specularUVTransform = new BaseShader.Uniform("u_specularUVTransform", TextureAttribute.Specular);
        public static final BaseShader.Uniform emissiveColor = new BaseShader.Uniform("u_emissiveColor", ColorAttribute.Emissive);
        public static final BaseShader.Uniform emissiveTexture = new BaseShader.Uniform("u_emissiveTexture", TextureAttribute.Emissive);
        public static final BaseShader.Uniform emissiveUVTransform = new BaseShader.Uniform("u_emissiveUVTransform", TextureAttribute.Emissive);
        public static final BaseShader.Uniform reflectionColor = new BaseShader.Uniform("u_reflectionColor", ColorAttribute.Reflection);
        public static final BaseShader.Uniform reflectionTexture = new BaseShader.Uniform("u_reflectionTexture", TextureAttribute.Reflection);
        public static final BaseShader.Uniform reflectionUVTransform = new BaseShader.Uniform("u_reflectionUVTransform", TextureAttribute.Reflection);
        public static final BaseShader.Uniform normalTexture = new BaseShader.Uniform("u_normalTexture", TextureAttribute.Normal);
        public static final BaseShader.Uniform normalUVTransform = new BaseShader.Uniform("u_normalUVTransform", TextureAttribute.Normal);
        public static final BaseShader.Uniform ambientTexture = new BaseShader.Uniform("u_ambientTexture", TextureAttribute.Ambient);
        public static final BaseShader.Uniform ambientUVTransform = new BaseShader.Uniform("u_ambientUVTransform", TextureAttribute.Ambient);
        public static final BaseShader.Uniform alphaTest = new BaseShader.Uniform("u_alphaTest");
        public static final BaseShader.Uniform ambientCube = new BaseShader.Uniform("u_ambientCubemap");
        public static final BaseShader.Uniform dirLights = new BaseShader.Uniform("u_dirLights");
        public static final BaseShader.Uniform pointLights = new BaseShader.Uniform("u_pointLights");
        public static final BaseShader.Uniform spotLights = new BaseShader.Uniform("u_spotLights");
        public static final BaseShader.Uniform environmentCubemap = new BaseShader.Uniform("u_environmentCubemap");
    }

    private static final boolean and(long j, long j2) {
        return (j & j2) == j2;
    }

    private static final boolean or(long j, long j2) {
        return (j & j2) != 0;
    }

    @Override // com.badlogic.gdx.graphics.g3d.Shader
    public int compareTo(Shader shader) {
        return shader == null ? -1 : 0;
    }

    public boolean equals(DefaultShader defaultShader) {
        return defaultShader == this;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/shaders/DefaultShader$Config.class
      classes8.dex
     */
    public static class Config {
        public int defaultCullFace;
        public int defaultDepthFunc;
        public String fragmentShader;
        public boolean ignoreUnimplemented;
        public int numBoneWeights;
        public int numBones;
        public int numDirectionalLights;
        public int numPointLights;
        public int numSpotLights;
        public String vertexShader;

        public Config() {
            this.vertexShader = null;
            this.fragmentShader = null;
            this.numDirectionalLights = 2;
            this.numPointLights = 5;
            this.numSpotLights = 0;
            this.numBones = 12;
            this.numBoneWeights = 4;
            this.ignoreUnimplemented = true;
            this.defaultCullFace = -1;
            this.defaultDepthFunc = -1;
        }

        public Config(String str, String str2) {
            this.numDirectionalLights = 2;
            this.numPointLights = 5;
            this.numSpotLights = 0;
            this.numBones = 12;
            this.numBoneWeights = 4;
            this.ignoreUnimplemented = true;
            this.defaultCullFace = -1;
            this.defaultDepthFunc = -1;
            this.vertexShader = str;
            this.fragmentShader = str2;
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/shaders/DefaultShader$Setters.class
      classes8.dex
     */
    public static class Setters {
        public static final BaseShader.Setter projTrans = new BaseShader.GlobalSetter() { // from class: com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Setters.1
            @Override // com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Setter
            public void set(BaseShader baseShader, int i, Renderable renderable, Attributes attributes) {
                baseShader.set(i, baseShader.camera.projection);
            }
        };
        public static final BaseShader.Setter viewTrans = new BaseShader.GlobalSetter() { // from class: com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Setters.2
            @Override // com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Setter
            public void set(BaseShader baseShader, int i, Renderable renderable, Attributes attributes) {
                baseShader.set(i, baseShader.camera.view);
            }
        };
        public static final BaseShader.Setter projViewTrans = new BaseShader.GlobalSetter() { // from class: com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Setters.3
            @Override // com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Setter
            public void set(BaseShader baseShader, int i, Renderable renderable, Attributes attributes) {
                baseShader.set(i, baseShader.camera.combined);
            }
        };
        public static final BaseShader.Setter cameraPosition = new BaseShader.GlobalSetter() { // from class: com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Setters.4
            @Override // com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Setter
            public void set(BaseShader baseShader, int i, Renderable renderable, Attributes attributes) {
                baseShader.set(i, baseShader.camera.position.x, baseShader.camera.position.y, baseShader.camera.position.z, 1.1881f / (baseShader.camera.far * baseShader.camera.far));
            }
        };
        public static final BaseShader.Setter cameraDirection = new BaseShader.GlobalSetter() { // from class: com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Setters.5
            @Override // com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Setter
            public void set(BaseShader baseShader, int i, Renderable renderable, Attributes attributes) {
                baseShader.set(i, baseShader.camera.direction);
            }
        };
        public static final BaseShader.Setter cameraUp = new BaseShader.GlobalSetter() { // from class: com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Setters.6
            @Override // com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Setter
            public void set(BaseShader baseShader, int i, Renderable renderable, Attributes attributes) {
                baseShader.set(i, baseShader.camera.up);
            }
        };
        public static final BaseShader.Setter cameraNearFar = new BaseShader.GlobalSetter() { // from class: com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Setters.7
            @Override // com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Setter
            public void set(BaseShader baseShader, int i, Renderable renderable, Attributes attributes) {
                baseShader.set(i, baseShader.camera.near, baseShader.camera.far);
            }
        };
        public static final BaseShader.Setter worldTrans = new BaseShader.LocalSetter() { // from class: com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Setters.8
            @Override // com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Setter
            public void set(BaseShader baseShader, int i, Renderable renderable, Attributes attributes) {
                baseShader.set(i, renderable.worldTransform);
            }
        };
        public static final BaseShader.Setter viewWorldTrans = new BaseShader.LocalSetter() { // from class: com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Setters.9
            final Matrix4 temp = new Matrix4();

            @Override // com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Setter
            public void set(BaseShader baseShader, int i, Renderable renderable, Attributes attributes) {
                baseShader.set(i, this.temp.set(baseShader.camera.view).mul(renderable.worldTransform));
            }
        };
        public static final BaseShader.Setter projViewWorldTrans = new BaseShader.LocalSetter() { // from class: com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Setters.10
            final Matrix4 temp = new Matrix4();

            @Override // com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Setter
            public void set(BaseShader baseShader, int i, Renderable renderable, Attributes attributes) {
                baseShader.set(i, this.temp.set(baseShader.camera.combined).mul(renderable.worldTransform));
            }
        };
        public static final BaseShader.Setter normalMatrix = new BaseShader.LocalSetter() { // from class: com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Setters.11
            private final Matrix3 tmpM = new Matrix3();

            @Override // com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Setter
            public void set(BaseShader baseShader, int i, Renderable renderable, Attributes attributes) {
                baseShader.set(i, this.tmpM.set(renderable.worldTransform).inv().transpose());
            }
        };
        public static final BaseShader.Setter shininess = new BaseShader.LocalSetter() { // from class: com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Setters.12
            @Override // com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Setter
            public void set(BaseShader baseShader, int i, Renderable renderable, Attributes attributes) {
                baseShader.set(i, ((FloatAttribute) attributes.get(FloatAttribute.Shininess)).value);
            }
        };
        public static final BaseShader.Setter diffuseColor = new BaseShader.LocalSetter() { // from class: com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Setters.13
            @Override // com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Setter
            public void set(BaseShader baseShader, int i, Renderable renderable, Attributes attributes) {
                baseShader.set(i, ((ColorAttribute) attributes.get(ColorAttribute.Diffuse)).color);
            }
        };
        public static final BaseShader.Setter diffuseTexture = new BaseShader.LocalSetter() { // from class: com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Setters.14
            @Override // com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Setter
            public void set(BaseShader baseShader, int i, Renderable renderable, Attributes attributes) {
                baseShader.set(i, baseShader.context.textureBinder.bind(((TextureAttribute) attributes.get(TextureAttribute.Diffuse)).textureDescription));
            }
        };
        public static final BaseShader.Setter diffuseUVTransform = new BaseShader.LocalSetter() { // from class: com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Setters.15
            @Override // com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Setter
            public void set(BaseShader baseShader, int i, Renderable renderable, Attributes attributes) {
                TextureAttribute textureAttribute = (TextureAttribute) attributes.get(TextureAttribute.Diffuse);
                baseShader.set(i, textureAttribute.offsetU, textureAttribute.offsetV, textureAttribute.scaleU, textureAttribute.scaleV);
            }
        };
        public static final BaseShader.Setter specularColor = new BaseShader.LocalSetter() { // from class: com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Setters.16
            @Override // com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Setter
            public void set(BaseShader baseShader, int i, Renderable renderable, Attributes attributes) {
                baseShader.set(i, ((ColorAttribute) attributes.get(ColorAttribute.Specular)).color);
            }
        };
        public static final BaseShader.Setter specularTexture = new BaseShader.LocalSetter() { // from class: com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Setters.17
            @Override // com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Setter
            public void set(BaseShader baseShader, int i, Renderable renderable, Attributes attributes) {
                baseShader.set(i, baseShader.context.textureBinder.bind(((TextureAttribute) attributes.get(TextureAttribute.Specular)).textureDescription));
            }
        };
        public static final BaseShader.Setter specularUVTransform = new BaseShader.LocalSetter() { // from class: com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Setters.18
            @Override // com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Setter
            public void set(BaseShader baseShader, int i, Renderable renderable, Attributes attributes) {
                TextureAttribute textureAttribute = (TextureAttribute) attributes.get(TextureAttribute.Specular);
                baseShader.set(i, textureAttribute.offsetU, textureAttribute.offsetV, textureAttribute.scaleU, textureAttribute.scaleV);
            }
        };
        public static final BaseShader.Setter emissiveColor = new BaseShader.LocalSetter() { // from class: com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Setters.19
            @Override // com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Setter
            public void set(BaseShader baseShader, int i, Renderable renderable, Attributes attributes) {
                baseShader.set(i, ((ColorAttribute) attributes.get(ColorAttribute.Emissive)).color);
            }
        };
        public static final BaseShader.Setter emissiveTexture = new BaseShader.LocalSetter() { // from class: com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Setters.20
            @Override // com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Setter
            public void set(BaseShader baseShader, int i, Renderable renderable, Attributes attributes) {
                baseShader.set(i, baseShader.context.textureBinder.bind(((TextureAttribute) attributes.get(TextureAttribute.Emissive)).textureDescription));
            }
        };
        public static final BaseShader.Setter emissiveUVTransform = new BaseShader.LocalSetter() { // from class: com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Setters.21
            @Override // com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Setter
            public void set(BaseShader baseShader, int i, Renderable renderable, Attributes attributes) {
                TextureAttribute textureAttribute = (TextureAttribute) attributes.get(TextureAttribute.Emissive);
                baseShader.set(i, textureAttribute.offsetU, textureAttribute.offsetV, textureAttribute.scaleU, textureAttribute.scaleV);
            }
        };
        public static final BaseShader.Setter reflectionColor = new BaseShader.LocalSetter() { // from class: com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Setters.22
            @Override // com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Setter
            public void set(BaseShader baseShader, int i, Renderable renderable, Attributes attributes) {
                baseShader.set(i, ((ColorAttribute) attributes.get(ColorAttribute.Reflection)).color);
            }
        };
        public static final BaseShader.Setter reflectionTexture = new BaseShader.LocalSetter() { // from class: com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Setters.23
            @Override // com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Setter
            public void set(BaseShader baseShader, int i, Renderable renderable, Attributes attributes) {
                baseShader.set(i, baseShader.context.textureBinder.bind(((TextureAttribute) attributes.get(TextureAttribute.Reflection)).textureDescription));
            }
        };
        public static final BaseShader.Setter reflectionUVTransform = new BaseShader.LocalSetter() { // from class: com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Setters.24
            @Override // com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Setter
            public void set(BaseShader baseShader, int i, Renderable renderable, Attributes attributes) {
                TextureAttribute textureAttribute = (TextureAttribute) attributes.get(TextureAttribute.Reflection);
                baseShader.set(i, textureAttribute.offsetU, textureAttribute.offsetV, textureAttribute.scaleU, textureAttribute.scaleV);
            }
        };
        public static final BaseShader.Setter normalTexture = new BaseShader.LocalSetter() { // from class: com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Setters.25
            @Override // com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Setter
            public void set(BaseShader baseShader, int i, Renderable renderable, Attributes attributes) {
                baseShader.set(i, baseShader.context.textureBinder.bind(((TextureAttribute) attributes.get(TextureAttribute.Normal)).textureDescription));
            }
        };
        public static final BaseShader.Setter normalUVTransform = new BaseShader.LocalSetter() { // from class: com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Setters.26
            @Override // com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Setter
            public void set(BaseShader baseShader, int i, Renderable renderable, Attributes attributes) {
                TextureAttribute textureAttribute = (TextureAttribute) attributes.get(TextureAttribute.Normal);
                baseShader.set(i, textureAttribute.offsetU, textureAttribute.offsetV, textureAttribute.scaleU, textureAttribute.scaleV);
            }
        };
        public static final BaseShader.Setter ambientTexture = new BaseShader.LocalSetter() { // from class: com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Setters.27
            @Override // com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Setter
            public void set(BaseShader baseShader, int i, Renderable renderable, Attributes attributes) {
                baseShader.set(i, baseShader.context.textureBinder.bind(((TextureAttribute) attributes.get(TextureAttribute.Ambient)).textureDescription));
            }
        };
        public static final BaseShader.Setter ambientUVTransform = new BaseShader.LocalSetter() { // from class: com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Setters.28
            @Override // com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Setter
            public void set(BaseShader baseShader, int i, Renderable renderable, Attributes attributes) {
                TextureAttribute textureAttribute = (TextureAttribute) attributes.get(TextureAttribute.Ambient);
                baseShader.set(i, textureAttribute.offsetU, textureAttribute.offsetV, textureAttribute.scaleU, textureAttribute.scaleV);
            }
        };
        public static final BaseShader.Setter environmentCubemap = new BaseShader.LocalSetter() { // from class: com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Setters.29
            @Override // com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Setter
            public void set(BaseShader baseShader, int i, Renderable renderable, Attributes attributes) {
                if (attributes.has(CubemapAttribute.EnvironmentMap)) {
                    baseShader.set(i, baseShader.context.textureBinder.bind(((CubemapAttribute) attributes.get(CubemapAttribute.EnvironmentMap)).textureDescription));
                }
            }
        };

        /* JADX WARN: Classes with same name are omitted, all sources:
          NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/shaders/DefaultShader$Setters$Bones.class
          classes8.dex
         */
        public static class Bones extends BaseShader.LocalSetter {
            private static final Matrix4 idtMatrix = new Matrix4();
            public final float[] bones;

            public Bones(int i) {
                this.bones = new float[i * 16];
            }

            @Override // com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Setter
            public void set(BaseShader baseShader, int i, Renderable renderable, Attributes attributes) {
                for (int i2 = 0; i2 < this.bones.length; i2 += 16) {
                    int i3 = i2 / 16;
                    if (renderable.bones == null || i3 >= renderable.bones.length || renderable.bones[i3] == null) {
                        System.arraycopy(idtMatrix.val, 0, this.bones, i2, 16);
                    } else {
                        System.arraycopy(renderable.bones[i3].val, 0, this.bones, i2, 16);
                    }
                }
                ShaderProgram shaderProgram = baseShader.program;
                int iLoc = baseShader.loc(i);
                float[] fArr = this.bones;
                shaderProgram.setUniformMatrix4fv(iLoc, fArr, 0, fArr.length);
            }
        }

        /* JADX WARN: Classes with same name are omitted, all sources:
          NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/shaders/DefaultShader$Setters$ACubemap.class
          classes8.dex
         */
        public static class ACubemap extends BaseShader.LocalSetter {
            private static final float[] ones = {1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f};
            private static final Vector3 tmpV1 = new Vector3();
            private final AmbientCubemap cacheAmbientCubemap = new AmbientCubemap();
            public final int dirLightsOffset;
            public final int pointLightsOffset;

            public ACubemap(int i, int i2) {
                this.dirLightsOffset = i;
                this.pointLightsOffset = i2;
            }

            @Override // com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Setter
            public void set(BaseShader baseShader, int i, Renderable renderable, Attributes attributes) {
                if (renderable.environment == null) {
                    ShaderProgram shaderProgram = baseShader.program;
                    int iLoc = baseShader.loc(i);
                    float[] fArr = ones;
                    shaderProgram.setUniform3fv(iLoc, fArr, 0, fArr.length);
                    return;
                }
                renderable.worldTransform.getTranslation(tmpV1);
                if (attributes.has(ColorAttribute.AmbientLight)) {
                    this.cacheAmbientCubemap.set(((ColorAttribute) attributes.get(ColorAttribute.AmbientLight)).color);
                }
                if (attributes.has(DirectionalLightsAttribute.Type)) {
                    Array<DirectionalLight> array = ((DirectionalLightsAttribute) attributes.get(DirectionalLightsAttribute.Type)).lights;
                    for (int i2 = this.dirLightsOffset; i2 < array.size; i2++) {
                        this.cacheAmbientCubemap.add(array.get(i2).color, array.get(i2).direction);
                    }
                }
                if (attributes.has(PointLightsAttribute.Type)) {
                    Array<PointLight> array2 = ((PointLightsAttribute) attributes.get(PointLightsAttribute.Type)).lights;
                    for (int i3 = this.pointLightsOffset; i3 < array2.size; i3++) {
                        this.cacheAmbientCubemap.add(array2.get(i3).color, array2.get(i3).position, tmpV1, array2.get(i3).intensity);
                    }
                }
                this.cacheAmbientCubemap.clamp();
                baseShader.program.setUniform3fv(baseShader.loc(i), this.cacheAmbientCubemap.data, 0, this.cacheAmbientCubemap.data.length);
            }
        }
    }

    public static String getDefaultVertexShader() {
        if (defaultVertexShader == null) {
            defaultVertexShader = Gdx.files.classpath("com/badlogic/gdx/graphics/g3d/shaders/default.vertex.glsl").readString();
        }
        return defaultVertexShader;
    }

    public static String getDefaultFragmentShader() {
        if (defaultFragmentShader == null) {
            defaultFragmentShader = Gdx.files.classpath("com/badlogic/gdx/graphics/g3d/shaders/default.fragment.glsl").readString();
        }
        return defaultFragmentShader;
    }

    public DefaultShader(Renderable renderable) {
        this(renderable, new Config());
    }

    public DefaultShader(Renderable renderable, Config config) {
        this(renderable, config, createPrefix(renderable, config));
    }

    public DefaultShader(Renderable renderable, Config config, String str) {
        this(renderable, config, str, config.vertexShader != null ? config.vertexShader : getDefaultVertexShader(), config.fragmentShader != null ? config.fragmentShader : getDefaultFragmentShader());
    }

    public DefaultShader(Renderable renderable, Config config, String str, String str2, String str3) {
        this(renderable, config, new ShaderProgram(str + str2, str + str3));
    }

    public DefaultShader(Renderable renderable, Config config, ShaderProgram shaderProgram) {
        this.u_dirLights0color = register(new BaseShader.Uniform("u_dirLights[0].color"));
        this.u_dirLights0direction = register(new BaseShader.Uniform("u_dirLights[0].direction"));
        this.u_dirLights1color = register(new BaseShader.Uniform("u_dirLights[1].color"));
        this.u_pointLights0color = register(new BaseShader.Uniform("u_pointLights[0].color"));
        this.u_pointLights0position = register(new BaseShader.Uniform("u_pointLights[0].position"));
        this.u_pointLights0intensity = register(new BaseShader.Uniform("u_pointLights[0].intensity"));
        this.u_pointLights1color = register(new BaseShader.Uniform("u_pointLights[1].color"));
        this.u_spotLights0color = register(new BaseShader.Uniform("u_spotLights[0].color"));
        this.u_spotLights0position = register(new BaseShader.Uniform("u_spotLights[0].position"));
        this.u_spotLights0intensity = register(new BaseShader.Uniform("u_spotLights[0].intensity"));
        this.u_spotLights0direction = register(new BaseShader.Uniform("u_spotLights[0].direction"));
        this.u_spotLights0cutoffAngle = register(new BaseShader.Uniform("u_spotLights[0].cutoffAngle"));
        this.u_spotLights0exponent = register(new BaseShader.Uniform("u_spotLights[0].exponent"));
        this.u_spotLights1color = register(new BaseShader.Uniform("u_spotLights[1].color"));
        this.u_fogColor = register(new BaseShader.Uniform("u_fogColor"));
        this.u_shadowMapProjViewTrans = register(new BaseShader.Uniform("u_shadowMapProjViewTrans"));
        this.u_shadowTexture = register(new BaseShader.Uniform("u_shadowTexture"));
        this.u_shadowPCFOffset = register(new BaseShader.Uniform("u_shadowPCFOffset"));
        this.ambientCubemap = new AmbientCubemap();
        this.normalMatrix = new Matrix3();
        this.tmpV1 = new Vector3();
        Attributes attributesCombineAttributes = combineAttributes(renderable);
        this.config = config;
        this.program = shaderProgram;
        int i = 0;
        boolean z = renderable.environment != null;
        this.lighting = z;
        this.environmentCubemap = attributesCombineAttributes.has(CubemapAttribute.EnvironmentMap) || (z && attributesCombineAttributes.has(CubemapAttribute.EnvironmentMap));
        this.shadowMap = z && renderable.environment.shadowMap != null;
        this.renderable = renderable;
        this.attributesMask = attributesCombineAttributes.getMask() | optionalAttributes;
        this.vertexMask = renderable.meshPart.mesh.getVertexAttributes().getMaskWithSizePacked();
        this.textureCoordinates = renderable.meshPart.mesh.getVertexAttributes().getTextureCoordinates();
        this.directionalLights = new DirectionalLight[(!z || config.numDirectionalLights <= 0) ? 0 : config.numDirectionalLights];
        int i2 = 0;
        while (true) {
            DirectionalLight[] directionalLightArr = this.directionalLights;
            if (i2 >= directionalLightArr.length) {
                break;
            }
            directionalLightArr[i2] = new DirectionalLight();
            i2++;
        }
        this.pointLights = new PointLight[(!this.lighting || config.numPointLights <= 0) ? 0 : config.numPointLights];
        int i3 = 0;
        while (true) {
            PointLight[] pointLightArr = this.pointLights;
            if (i3 >= pointLightArr.length) {
                break;
            }
            pointLightArr[i3] = new PointLight();
            i3++;
        }
        this.spotLights = new SpotLight[(!this.lighting || config.numSpotLights <= 0) ? 0 : config.numSpotLights];
        while (true) {
            SpotLight[] spotLightArr = this.spotLights;
            if (i >= spotLightArr.length) {
                break;
            }
            spotLightArr[i] = new SpotLight();
            i++;
        }
        if (!config.ignoreUnimplemented) {
            long j = implementedFlags;
            long j2 = this.attributesMask;
            if ((j & j2) != j2) {
                throw new GdxRuntimeException("Some attributes not implemented yet (" + this.attributesMask + Constant.AFTER_QUTO);
            }
        }
        if (renderable.bones != null && renderable.bones.length > config.numBones) {
            throw new GdxRuntimeException("too many bones: " + renderable.bones.length + ", max configured: " + config.numBones);
        }
        int boneWeights = renderable.meshPart.mesh.getVertexAttributes().getBoneWeights();
        if (boneWeights > config.numBoneWeights) {
            throw new GdxRuntimeException("too many bone weights: " + boneWeights + ", max configured: " + config.numBoneWeights);
        }
        if (renderable.bones != null) {
            this.boneWeightsLocations = new int[config.numBoneWeights];
        }
        this.u_projTrans = register(Inputs.projTrans, Setters.projTrans);
        this.u_viewTrans = register(Inputs.viewTrans, Setters.viewTrans);
        this.u_projViewTrans = register(Inputs.projViewTrans, Setters.projViewTrans);
        this.u_cameraPosition = register(Inputs.cameraPosition, Setters.cameraPosition);
        this.u_cameraDirection = register(Inputs.cameraDirection, Setters.cameraDirection);
        this.u_cameraUp = register(Inputs.cameraUp, Setters.cameraUp);
        this.u_cameraNearFar = register(Inputs.cameraNearFar, Setters.cameraNearFar);
        this.u_time = register(new BaseShader.Uniform("u_time"));
        this.u_worldTrans = register(Inputs.worldTrans, Setters.worldTrans);
        this.u_viewWorldTrans = register(Inputs.viewWorldTrans, Setters.viewWorldTrans);
        this.u_projViewWorldTrans = register(Inputs.projViewWorldTrans, Setters.projViewWorldTrans);
        this.u_normalMatrix = register(Inputs.normalMatrix, Setters.normalMatrix);
        this.u_bones = (renderable.bones == null || config.numBones <= 0) ? -1 : register(Inputs.bones, new Setters.Bones(config.numBones));
        this.u_shininess = register(Inputs.shininess, Setters.shininess);
        this.u_opacity = register(Inputs.opacity);
        this.u_diffuseColor = register(Inputs.diffuseColor, Setters.diffuseColor);
        this.u_diffuseTexture = register(Inputs.diffuseTexture, Setters.diffuseTexture);
        this.u_diffuseUVTransform = register(Inputs.diffuseUVTransform, Setters.diffuseUVTransform);
        this.u_specularColor = register(Inputs.specularColor, Setters.specularColor);
        this.u_specularTexture = register(Inputs.specularTexture, Setters.specularTexture);
        this.u_specularUVTransform = register(Inputs.specularUVTransform, Setters.specularUVTransform);
        this.u_emissiveColor = register(Inputs.emissiveColor, Setters.emissiveColor);
        this.u_emissiveTexture = register(Inputs.emissiveTexture, Setters.emissiveTexture);
        this.u_emissiveUVTransform = register(Inputs.emissiveUVTransform, Setters.emissiveUVTransform);
        this.u_reflectionColor = register(Inputs.reflectionColor, Setters.reflectionColor);
        this.u_reflectionTexture = register(Inputs.reflectionTexture, Setters.reflectionTexture);
        this.u_reflectionUVTransform = register(Inputs.reflectionUVTransform, Setters.reflectionUVTransform);
        this.u_normalTexture = register(Inputs.normalTexture, Setters.normalTexture);
        this.u_normalUVTransform = register(Inputs.normalUVTransform, Setters.normalUVTransform);
        this.u_ambientTexture = register(Inputs.ambientTexture, Setters.ambientTexture);
        this.u_ambientUVTransform = register(Inputs.ambientUVTransform, Setters.ambientUVTransform);
        this.u_alphaTest = register(Inputs.alphaTest);
        this.u_ambientCubemap = this.lighting ? register(Inputs.ambientCube, new Setters.ACubemap(config.numDirectionalLights, config.numPointLights)) : -1;
        this.u_environmentCubemap = this.environmentCubemap ? register(Inputs.environmentCubemap, Setters.environmentCubemap) : -1;
    }

    @Override // com.badlogic.gdx.graphics.g3d.Shader
    public void init() {
        ShaderProgram shaderProgram = this.program;
        this.program = null;
        init(shaderProgram, this.renderable);
        this.renderable = null;
        this.dirLightsLoc = loc(this.u_dirLights0color);
        this.dirLightsColorOffset = loc(this.u_dirLights0color) - this.dirLightsLoc;
        this.dirLightsDirectionOffset = loc(this.u_dirLights0direction) - this.dirLightsLoc;
        int iLoc = loc(this.u_dirLights1color) - this.dirLightsLoc;
        this.dirLightsSize = iLoc;
        int i = 0;
        if (iLoc < 0) {
            this.dirLightsSize = 0;
        }
        this.pointLightsLoc = loc(this.u_pointLights0color);
        this.pointLightsColorOffset = loc(this.u_pointLights0color) - this.pointLightsLoc;
        this.pointLightsPositionOffset = loc(this.u_pointLights0position) - this.pointLightsLoc;
        this.pointLightsIntensityOffset = has(this.u_pointLights0intensity) ? loc(this.u_pointLights0intensity) - this.pointLightsLoc : -1;
        int iLoc2 = loc(this.u_pointLights1color) - this.pointLightsLoc;
        this.pointLightsSize = iLoc2;
        if (iLoc2 < 0) {
            this.pointLightsSize = 0;
        }
        this.spotLightsLoc = loc(this.u_spotLights0color);
        this.spotLightsColorOffset = loc(this.u_spotLights0color) - this.spotLightsLoc;
        this.spotLightsPositionOffset = loc(this.u_spotLights0position) - this.spotLightsLoc;
        this.spotLightsDirectionOffset = loc(this.u_spotLights0direction) - this.spotLightsLoc;
        this.spotLightsIntensityOffset = has(this.u_spotLights0intensity) ? loc(this.u_spotLights0intensity) - this.spotLightsLoc : -1;
        this.spotLightsCutoffAngleOffset = loc(this.u_spotLights0cutoffAngle) - this.spotLightsLoc;
        this.spotLightsExponentOffset = loc(this.u_spotLights0exponent) - this.spotLightsLoc;
        int iLoc3 = loc(this.u_spotLights1color) - this.spotLightsLoc;
        this.spotLightsSize = iLoc3;
        if (iLoc3 < 0) {
            this.spotLightsSize = 0;
        }
        if (this.boneWeightsLocations == null) {
            return;
        }
        while (true) {
            int[] iArr = this.boneWeightsLocations;
            if (i >= iArr.length) {
                return;
            }
            iArr[i] = shaderProgram.getAttributeLocation(ShaderProgram.BONEWEIGHT_ATTRIBUTE + i);
            i++;
        }
    }

    private static final Attributes combineAttributes(Renderable renderable) {
        Attributes attributes = tmpAttributes;
        attributes.clear();
        if (renderable.environment != null) {
            attributes.set(renderable.environment);
        }
        if (renderable.material != null) {
            attributes.set(renderable.material);
        }
        return attributes;
    }

    private static final long combineAttributeMasks(Renderable renderable) {
        long mask = renderable.environment != null ? renderable.environment.getMask() : 0L;
        return renderable.material != null ? mask | renderable.material.getMask() : mask;
    }

    public static String createPrefix(Renderable renderable, Config config) {
        Attributes attributesCombineAttributes = combineAttributes(renderable);
        long mask = attributesCombineAttributes.getMask();
        long mask2 = renderable.meshPart.mesh.getVertexAttributes().getMask();
        String str = and(mask2, 1L) ? "#define positionFlag\n" : "";
        if (or(mask2, 6L)) {
            str = str + "#define colorFlag\n";
        }
        if (and(mask2, 256L)) {
            str = str + "#define binormalFlag\n";
        }
        if (and(mask2, 128L)) {
            str = str + "#define tangentFlag\n";
        }
        if (and(mask2, 8L)) {
            str = str + "#define normalFlag\n";
        }
        if ((and(mask2, 8L) || and(mask2, 384L)) && renderable.environment != null) {
            String str2 = ((((str + "#define lightingFlag\n") + "#define ambientCubemapFlag\n") + "#define numDirectionalLights " + config.numDirectionalLights + "\n") + "#define numPointLights " + config.numPointLights + "\n") + "#define numSpotLights " + config.numSpotLights + "\n";
            if (attributesCombineAttributes.has(ColorAttribute.Fog)) {
                str2 = str2 + "#define fogFlag\n";
            }
            if (renderable.environment.shadowMap != null) {
                str2 = str2 + "#define shadowMapFlag\n";
            }
            str = str2;
            if (attributesCombineAttributes.has(CubemapAttribute.EnvironmentMap)) {
                str = str + "#define environmentCubemapFlag\n";
            }
        }
        int size = renderable.meshPart.mesh.getVertexAttributes().size();
        for (int i = 0; i < size; i++) {
            VertexAttribute vertexAttribute = renderable.meshPart.mesh.getVertexAttributes().get(i);
            if (vertexAttribute.usage == 16) {
                str = str + "#define texCoord" + vertexAttribute.unit + "Flag\n";
            }
        }
        if (renderable.bones != null) {
            for (int i2 = 0; i2 < config.numBoneWeights; i2++) {
                str = str + "#define boneWeight" + i2 + "Flag\n";
            }
        }
        if ((BlendingAttribute.Type & mask) == BlendingAttribute.Type) {
            str = str + "#define blendedFlag\n";
        }
        if ((TextureAttribute.Diffuse & mask) == TextureAttribute.Diffuse) {
            str = (str + "#define diffuseTextureFlag\n") + "#define diffuseTextureCoord texCoord0\n";
        }
        if ((TextureAttribute.Specular & mask) == TextureAttribute.Specular) {
            str = (str + "#define specularTextureFlag\n") + "#define specularTextureCoord texCoord0\n";
        }
        if ((TextureAttribute.Normal & mask) == TextureAttribute.Normal) {
            str = (str + "#define normalTextureFlag\n") + "#define normalTextureCoord texCoord0\n";
        }
        if ((TextureAttribute.Emissive & mask) == TextureAttribute.Emissive) {
            str = (str + "#define emissiveTextureFlag\n") + "#define emissiveTextureCoord texCoord0\n";
        }
        if ((TextureAttribute.Reflection & mask) == TextureAttribute.Reflection) {
            str = (str + "#define reflectionTextureFlag\n") + "#define reflectionTextureCoord texCoord0\n";
        }
        if ((TextureAttribute.Ambient & mask) == TextureAttribute.Ambient) {
            str = (str + "#define ambientTextureFlag\n") + "#define ambientTextureCoord texCoord0\n";
        }
        if ((ColorAttribute.Diffuse & mask) == ColorAttribute.Diffuse) {
            str = str + "#define diffuseColorFlag\n";
        }
        if ((ColorAttribute.Specular & mask) == ColorAttribute.Specular) {
            str = str + "#define specularColorFlag\n";
        }
        if ((ColorAttribute.Emissive & mask) == ColorAttribute.Emissive) {
            str = str + "#define emissiveColorFlag\n";
        }
        if ((ColorAttribute.Reflection & mask) == ColorAttribute.Reflection) {
            str = str + "#define reflectionColorFlag\n";
        }
        if ((FloatAttribute.Shininess & mask) == FloatAttribute.Shininess) {
            str = str + "#define shininessFlag\n";
        }
        if ((mask & FloatAttribute.AlphaTest) == FloatAttribute.AlphaTest) {
            str = str + "#define alphaTestFlag\n";
        }
        return (renderable.bones == null || config.numBones <= 0) ? str : str + "#define numBones " + config.numBones + "\n";
    }

    @Override // com.badlogic.gdx.graphics.g3d.Shader
    public boolean canRender(Renderable renderable) {
        if ((renderable.bones != null && (renderable.bones.length > this.config.numBones || renderable.meshPart.mesh.getVertexAttributes().getBoneWeights() > this.config.numBoneWeights)) || renderable.meshPart.mesh.getVertexAttributes().getTextureCoordinates() != this.textureCoordinates) {
            return false;
        }
        if (this.attributesMask == (combineAttributeMasks(renderable) | optionalAttributes) && this.vertexMask == renderable.meshPart.mesh.getVertexAttributes().getMaskWithSizePacked()) {
            return (renderable.environment != null) == this.lighting;
        }
        return false;
    }

    public boolean equals(Object obj) {
        return (obj instanceof DefaultShader) && equals((DefaultShader) obj);
    }

    @Override // com.badlogic.gdx.graphics.g3d.shaders.BaseShader, com.badlogic.gdx.graphics.g3d.Shader
    public void begin(Camera camera, RenderContext renderContext) {
        super.begin(camera, renderContext);
        for (DirectionalLight directionalLight : this.directionalLights) {
            directionalLight.set(0.0f, 0.0f, 0.0f, 0.0f, -1.0f, 0.0f);
        }
        for (PointLight pointLight : this.pointLights) {
            pointLight.set(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
        }
        for (SpotLight spotLight : this.spotLights) {
            spotLight.set(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, 1.0f, 0.0f);
        }
        this.lightsSet = false;
        if (has(this.u_time)) {
            int i = this.u_time;
            float deltaTime = this.time + Gdx.graphics.getDeltaTime();
            this.time = deltaTime;
            set(i, deltaTime);
        }
        int[] iArr = this.boneWeightsLocations;
        if (iArr != null) {
            for (int i2 : iArr) {
                if (i2 >= 0) {
                    Gdx.gl.glVertexAttrib2f(i2, 0.0f, 0.0f);
                }
            }
        }
    }

    @Override // com.badlogic.gdx.graphics.g3d.shaders.BaseShader
    public void render(Renderable renderable, Attributes attributes) {
        if (!attributes.has(BlendingAttribute.Type)) {
            this.context.setBlending(false, GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        }
        bindMaterial(attributes);
        if (this.lighting) {
            bindLights(renderable, attributes);
        }
        super.render(renderable, attributes);
    }

    @Override // com.badlogic.gdx.graphics.g3d.shaders.BaseShader, com.badlogic.gdx.graphics.g3d.Shader
    public void end() {
        super.end();
    }

    protected void bindMaterial(Attributes attributes) {
        int i = this.config.defaultCullFace == -1 ? defaultCullFace : this.config.defaultCullFace;
        int i2 = this.config.defaultDepthFunc == -1 ? defaultDepthFunc : this.config.defaultDepthFunc;
        float f = 0.0f;
        float f2 = 1.0f;
        boolean z = true;
        for (Attribute attribute : attributes) {
            long j = attribute.type;
            if (BlendingAttribute.is(j)) {
                BlendingAttribute blendingAttribute = (BlendingAttribute) attribute;
                this.context.setBlending(true, blendingAttribute.sourceFunction, blendingAttribute.destFunction);
                set(this.u_opacity, blendingAttribute.opacity);
            } else if ((IntAttribute.CullFace & j) == IntAttribute.CullFace) {
                i = ((IntAttribute) attribute).value;
            } else if ((FloatAttribute.AlphaTest & j) == FloatAttribute.AlphaTest) {
                set(this.u_alphaTest, ((FloatAttribute) attribute).value);
            } else if ((j & DepthTestAttribute.Type) == DepthTestAttribute.Type) {
                DepthTestAttribute depthTestAttribute = (DepthTestAttribute) attribute;
                i2 = depthTestAttribute.depthFunc;
                f = depthTestAttribute.depthRangeNear;
                f2 = depthTestAttribute.depthRangeFar;
                z = depthTestAttribute.depthMask;
            } else if (!this.config.ignoreUnimplemented) {
                throw new GdxRuntimeException("Unknown material attribute: " + attribute.toString());
            }
        }
        this.context.setCullFace(i);
        this.context.setDepthTest(i2, f, f2);
        this.context.setDepthMask(z);
    }

    /* JADX WARN: Code duplicated, block: B:107:0x00ec A[EDGE_INSN: B:107:0x00ec->B:43:0x00ec BREAK  A[LOOP:0: B:17:0x003b->B:42:0x00e8], SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:109:0x00e8 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:112:0x01ac A[EDGE_INSN: B:112:0x01ac->B:71:0x01ac BREAK  A[LOOP:1: B:46:0x00f1->B:70:0x01a8], SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:114:0x01a8 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:118:0x0284 A[EDGE_INSN: B:118:0x0284->B:98:0x0284 BREAK  A[LOOP:2: B:73:0x01b0->B:97:0x0280], SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:119:0x0280 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:66:0x0195  */
    /* JADX WARN: Code duplicated, block: B:93:0x026d  */
    protected void bindLights(Renderable renderable, Attributes attributes) {
        int i;
        int i2;
        Environment environment = renderable.environment;
        DirectionalLightsAttribute directionalLightsAttribute = (DirectionalLightsAttribute) attributes.get(DirectionalLightsAttribute.class, DirectionalLightsAttribute.Type);
        Array<DirectionalLight> array = directionalLightsAttribute == null ? null : directionalLightsAttribute.lights;
        PointLightsAttribute pointLightsAttribute = (PointLightsAttribute) attributes.get(PointLightsAttribute.class, PointLightsAttribute.Type);
        Array<PointLight> array2 = pointLightsAttribute == null ? null : pointLightsAttribute.lights;
        SpotLightsAttribute spotLightsAttribute = (SpotLightsAttribute) attributes.get(SpotLightsAttribute.class, SpotLightsAttribute.Type);
        Array<SpotLight> array3 = spotLightsAttribute != null ? spotLightsAttribute.lights : null;
        if (this.dirLightsLoc >= 0) {
            for (int i3 = 0; i3 < this.directionalLights.length; i3++) {
                if (array == null || i3 >= array.size) {
                    if (!this.lightsSet || this.directionalLights[i3].color.r != 0.0f || this.directionalLights[i3].color.g != 0.0f || this.directionalLights[i3].color.b != 0.0f) {
                        this.directionalLights[i3].color.set(0.0f, 0.0f, 0.0f, 1.0f);
                        int i4 = this.dirLightsLoc + (this.dirLightsSize * i3);
                        this.program.setUniformf(this.dirLightsColorOffset + i4, this.directionalLights[i3].color.r, this.directionalLights[i3].color.g, this.directionalLights[i3].color.b);
                        this.program.setUniformf(i4 + this.dirLightsDirectionOffset, this.directionalLights[i3].direction.x, this.directionalLights[i3].direction.y, this.directionalLights[i3].direction.z);
                        if (this.dirLightsSize <= 0) {
                            break;
                        }
                    }
                } else if (!this.lightsSet || !this.directionalLights[i3].equals(array.get(i3))) {
                    this.directionalLights[i3].set(array.get(i3));
                    int i5 = this.dirLightsLoc + (this.dirLightsSize * i3);
                    this.program.setUniformf(this.dirLightsColorOffset + i5, this.directionalLights[i3].color.r, this.directionalLights[i3].color.g, this.directionalLights[i3].color.b);
                    this.program.setUniformf(i5 + this.dirLightsDirectionOffset, this.directionalLights[i3].direction.x, this.directionalLights[i3].direction.y, this.directionalLights[i3].direction.z);
                    if (this.dirLightsSize <= 0) {
                        break;
                        break;
                    }
                }
            }
        }
        if (this.pointLightsLoc >= 0) {
            for (int i6 = 0; i6 < this.pointLights.length; i6++) {
                if (array2 == null || i6 >= array2.size) {
                    if (!this.lightsSet || this.pointLights[i6].intensity != 0.0f) {
                        this.pointLights[i6].intensity = 0.0f;
                        i2 = this.pointLightsLoc + (this.pointLightsSize * i6);
                        this.program.setUniformf(this.pointLightsColorOffset + i2, this.pointLights[i6].color.r * this.pointLights[i6].intensity, this.pointLights[i6].color.g * this.pointLights[i6].intensity, this.pointLights[i6].color.b * this.pointLights[i6].intensity);
                        this.program.setUniformf(this.pointLightsPositionOffset + i2, this.pointLights[i6].position.x, this.pointLights[i6].position.y, this.pointLights[i6].position.z);
                        if (this.pointLightsIntensityOffset >= 0) {
                            this.program.setUniformf(i2 + this.pointLightsIntensityOffset, this.pointLights[i6].intensity);
                        }
                        if (this.pointLightsSize <= 0) {
                            break;
                        }
                    }
                } else if (!this.lightsSet || !this.pointLights[i6].equals(array2.get(i6))) {
                    this.pointLights[i6].set(array2.get(i6));
                    i2 = this.pointLightsLoc + (this.pointLightsSize * i6);
                    this.program.setUniformf(this.pointLightsColorOffset + i2, this.pointLights[i6].color.r * this.pointLights[i6].intensity, this.pointLights[i6].color.g * this.pointLights[i6].intensity, this.pointLights[i6].color.b * this.pointLights[i6].intensity);
                    this.program.setUniformf(this.pointLightsPositionOffset + i2, this.pointLights[i6].position.x, this.pointLights[i6].position.y, this.pointLights[i6].position.z);
                    if (this.pointLightsIntensityOffset >= 0) {
                        this.program.setUniformf(i2 + this.pointLightsIntensityOffset, this.pointLights[i6].intensity);
                    }
                    if (this.pointLightsSize <= 0) {
                        break;
                        break;
                    }
                }
            }
        }
        if (this.spotLightsLoc >= 0) {
            for (int i7 = 0; i7 < this.spotLights.length; i7++) {
                if (array3 == null || i7 >= array3.size) {
                    if (!this.lightsSet || this.spotLights[i7].intensity != 0.0f) {
                        this.spotLights[i7].intensity = 0.0f;
                        i = this.spotLightsLoc + (this.spotLightsSize * i7);
                        this.program.setUniformf(this.spotLightsColorOffset + i, this.spotLights[i7].color.r * this.spotLights[i7].intensity, this.spotLights[i7].color.g * this.spotLights[i7].intensity, this.spotLights[i7].color.b * this.spotLights[i7].intensity);
                        this.program.setUniformf(this.spotLightsPositionOffset + i, this.spotLights[i7].position);
                        this.program.setUniformf(this.spotLightsDirectionOffset + i, this.spotLights[i7].direction);
                        this.program.setUniformf(this.spotLightsCutoffAngleOffset + i, this.spotLights[i7].cutoffAngle);
                        this.program.setUniformf(this.spotLightsExponentOffset + i, this.spotLights[i7].exponent);
                        if (this.spotLightsIntensityOffset >= 0) {
                            this.program.setUniformf(i + this.spotLightsIntensityOffset, this.spotLights[i7].intensity);
                        }
                        if (this.spotLightsSize <= 0) {
                            break;
                        }
                    }
                } else if (!this.lightsSet || !this.spotLights[i7].equals(array3.get(i7))) {
                    this.spotLights[i7].set(array3.get(i7));
                    i = this.spotLightsLoc + (this.spotLightsSize * i7);
                    this.program.setUniformf(this.spotLightsColorOffset + i, this.spotLights[i7].color.r * this.spotLights[i7].intensity, this.spotLights[i7].color.g * this.spotLights[i7].intensity, this.spotLights[i7].color.b * this.spotLights[i7].intensity);
                    this.program.setUniformf(this.spotLightsPositionOffset + i, this.spotLights[i7].position);
                    this.program.setUniformf(this.spotLightsDirectionOffset + i, this.spotLights[i7].direction);
                    this.program.setUniformf(this.spotLightsCutoffAngleOffset + i, this.spotLights[i7].cutoffAngle);
                    this.program.setUniformf(this.spotLightsExponentOffset + i, this.spotLights[i7].exponent);
                    if (this.spotLightsIntensityOffset >= 0) {
                        this.program.setUniformf(i + this.spotLightsIntensityOffset, this.spotLights[i7].intensity);
                    }
                    if (this.spotLightsSize <= 0) {
                        break;
                        break;
                    }
                }
            }
        }
        if (attributes.has(ColorAttribute.Fog)) {
            set(this.u_fogColor, ((ColorAttribute) attributes.get(ColorAttribute.Fog)).color);
        }
        if (environment != null && environment.shadowMap != null) {
            set(this.u_shadowMapProjViewTrans, environment.shadowMap.getProjViewTrans());
            set(this.u_shadowTexture, environment.shadowMap.getDepthMap());
            set(this.u_shadowPCFOffset, 1.0f / (environment.shadowMap.getDepthMap().texture.getWidth() * 2.0f));
        }
        this.lightsSet = true;
    }

    @Override // com.badlogic.gdx.graphics.g3d.shaders.BaseShader, com.badlogic.gdx.utils.Disposable
    public void dispose() {
        this.program.dispose();
        super.dispose();
    }

    public int getDefaultCullFace() {
        return this.config.defaultCullFace == -1 ? defaultCullFace : this.config.defaultCullFace;
    }

    public void setDefaultCullFace(int i) {
        this.config.defaultCullFace = i;
    }

    public int getDefaultDepthFunc() {
        return this.config.defaultDepthFunc == -1 ? defaultDepthFunc : this.config.defaultDepthFunc;
    }

    public void setDefaultDepthFunc(int i) {
        this.config.defaultDepthFunc = i;
    }
}
