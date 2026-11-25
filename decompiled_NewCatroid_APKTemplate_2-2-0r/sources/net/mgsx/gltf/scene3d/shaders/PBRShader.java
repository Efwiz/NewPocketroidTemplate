package net.mgsx.gltf.scene3d.shaders;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cubemap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Attributes;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.DirectionalLightsAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.shaders.BaseShader;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import net.mgsx.gltf.scene3d.attributes.CascadeShadowMapAttribute;
import net.mgsx.gltf.scene3d.attributes.ClippingPlaneAttribute;
import net.mgsx.gltf.scene3d.attributes.FogAttribute;
import net.mgsx.gltf.scene3d.attributes.MirrorSourceAttribute;
import net.mgsx.gltf.scene3d.attributes.PBRColorAttribute;
import net.mgsx.gltf.scene3d.attributes.PBRCubemapAttribute;
import net.mgsx.gltf.scene3d.attributes.PBRFloatAttribute;
import net.mgsx.gltf.scene3d.attributes.PBRHDRColorAttribute;
import net.mgsx.gltf.scene3d.attributes.PBRIridescenceAttribute;
import net.mgsx.gltf.scene3d.attributes.PBRMatrixAttribute;
import net.mgsx.gltf.scene3d.attributes.PBRTextureAttribute;
import net.mgsx.gltf.scene3d.attributes.PBRVolumeAttribute;
import net.mgsx.gltf.scene3d.lights.DirectionalLightEx;
import net.mgsx.gltf.scene3d.lights.DirectionalShadowLight;
import net.mgsx.gltf.scene3d.model.WeightVector;

/* JADX INFO: loaded from: classes12.dex */
public class PBRShader extends DefaultShader {
    private int cascadeCount;
    private float[] csmPCFClip;
    private float[] csmTransforms;
    private long morphTargetsMask;
    private long textureCoordinateMapMask;
    public final int u_BaseColorFactor;
    public final int u_BaseColorTexture;
    public final int u_DiffuseEnvSampler;
    public final int u_EmissiveTexture;
    public final int u_FogEquation;
    public final int u_NormalScale;
    public final int u_NormalTexture;
    public final int u_ShadowBias;
    public final int u_SpecularEnvSampler;
    private int u_ambientLight;
    public final int u_brdfLUTTexture;
    public int u_clippingPlane;
    public int u_csmPCFClip;
    public int[] u_csmSamplers;
    public int u_csmTransforms;
    public int u_emissive;
    public final int u_envRotation;
    public int u_ior;
    public int u_iridescenceFactor;
    public int u_iridescenceIOR;
    public int u_iridescenceTexture;
    public int u_iridescenceThicknessMax;
    public int u_iridescenceThicknessMin;
    public int u_iridescenceThicknessTexture;
    public final int u_metallicRoughness;
    public final int u_metallicRoughnessTexture;
    private int u_mipmapScale;
    private int u_morphTargets1;
    private int u_morphTargets2;
    public final int u_occlusionStrength;
    public final int u_occlusionTexture;
    public int u_specularColorFactor;
    public int u_specularColorTexture;
    public int u_specularFactor;
    public int u_specularFactorTexture;
    public int u_specularMirrorMipmapScale;
    public int u_specularMirrorNormal;
    public int u_specularMirrorSampler;
    private int u_texCoord0Transform;
    private int u_texCoord1Transform;
    public int u_thicknessFactor;
    public int u_thicknessTexture;
    public int u_transmissionFactor;
    public int u_transmissionSourceMipmap;
    public int u_transmissionSourceTexture;
    public int u_transmissionTexture;
    public int u_viewportInv;
    public int u_volumeColor;
    public int u_volumeDistance;
    private int vertexColorLayers;
    private static final Vector2 v2 = new Vector2();
    public static final BaseShader.Uniform baseColorTextureUniform = new BaseShader.Uniform("u_diffuseTexture", PBRTextureAttribute.BaseColorTexture);
    public static final BaseShader.Setter baseColorTextureSetter = new BaseShader.LocalSetter() { // from class: net.mgsx.gltf.scene3d.shaders.PBRShader.1
        @Override // com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Setter
        public void set(BaseShader baseShader, int i, Renderable renderable, Attributes attributes) {
            baseShader.set(i, baseShader.context.textureBinder.bind(((TextureAttribute) attributes.get(PBRTextureAttribute.BaseColorTexture)).textureDescription));
        }
    };
    public static final BaseShader.Uniform baseColorFactorUniform = new BaseShader.Uniform("u_BaseColorFactor", PBRColorAttribute.BaseColorFactor);
    public static final BaseShader.Setter baseColorFactorSetter = new BaseShader.LocalSetter() { // from class: net.mgsx.gltf.scene3d.shaders.PBRShader.2
        @Override // com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Setter
        public void set(BaseShader baseShader, int i, Renderable renderable, Attributes attributes) {
            ColorAttribute colorAttribute = (ColorAttribute) attributes.get(ColorAttribute.class, PBRColorAttribute.BaseColorFactor);
            baseShader.set(i, colorAttribute == null ? Color.WHITE : colorAttribute.color);
        }
    };
    public static final BaseShader.Uniform emissiveTextureUniform = new BaseShader.Uniform("u_emissiveTexture", PBRTextureAttribute.EmissiveTexture);
    public static final BaseShader.Setter emissiveTextureSetter = new BaseShader.LocalSetter() { // from class: net.mgsx.gltf.scene3d.shaders.PBRShader.3
        @Override // com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Setter
        public void set(BaseShader baseShader, int i, Renderable renderable, Attributes attributes) {
            baseShader.set(i, baseShader.context.textureBinder.bind(((TextureAttribute) attributes.get(PBRTextureAttribute.EmissiveTexture)).textureDescription));
        }
    };
    public static final BaseShader.Uniform normalTextureUniform = new BaseShader.Uniform("u_normalTexture", PBRTextureAttribute.NormalTexture);
    public static final BaseShader.Setter normalTextureSetter = new BaseShader.LocalSetter() { // from class: net.mgsx.gltf.scene3d.shaders.PBRShader.4
        @Override // com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Setter
        public void set(BaseShader baseShader, int i, Renderable renderable, Attributes attributes) {
            baseShader.set(i, baseShader.context.textureBinder.bind(((TextureAttribute) attributes.get(PBRTextureAttribute.NormalTexture)).textureDescription));
        }
    };
    public static final BaseShader.Uniform metallicRoughnessTextureUniform = new BaseShader.Uniform("u_MetallicRoughnessSampler", PBRTextureAttribute.MetallicRoughnessTexture);
    public static final BaseShader.Setter metallicRoughnessTextureSetter = new BaseShader.LocalSetter() { // from class: net.mgsx.gltf.scene3d.shaders.PBRShader.5
        @Override // com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Setter
        public void set(BaseShader baseShader, int i, Renderable renderable, Attributes attributes) {
            baseShader.set(i, baseShader.context.textureBinder.bind(((TextureAttribute) attributes.get(PBRTextureAttribute.MetallicRoughnessTexture)).textureDescription));
        }
    };
    public static final BaseShader.Uniform metallicRoughnessUniform = new BaseShader.Uniform("u_MetallicRoughnessValues");
    public static final BaseShader.Setter metallicRoughnessSetter = new BaseShader.LocalSetter() { // from class: net.mgsx.gltf.scene3d.shaders.PBRShader.6
        @Override // com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Setter
        public void set(BaseShader baseShader, int i, Renderable renderable, Attributes attributes) {
            PBRFloatAttribute pBRFloatAttribute = (PBRFloatAttribute) attributes.get(PBRFloatAttribute.class, PBRFloatAttribute.Metallic);
            PBRFloatAttribute pBRFloatAttribute2 = (PBRFloatAttribute) attributes.get(PBRFloatAttribute.class, PBRFloatAttribute.Roughness);
            baseShader.set(i, PBRShader.v2.set(pBRFloatAttribute == null ? 1.0f : pBRFloatAttribute.value, pBRFloatAttribute2 != null ? pBRFloatAttribute2.value : 1.0f));
        }
    };
    public static final BaseShader.Uniform normalScaleUniform = new BaseShader.Uniform("u_NormalScale");
    public static final BaseShader.Setter normalScaleSetter = new BaseShader.LocalSetter() { // from class: net.mgsx.gltf.scene3d.shaders.PBRShader.7
        @Override // com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Setter
        public void set(BaseShader baseShader, int i, Renderable renderable, Attributes attributes) {
            PBRFloatAttribute pBRFloatAttribute = (PBRFloatAttribute) attributes.get(PBRFloatAttribute.class, PBRFloatAttribute.NormalScale);
            baseShader.set(i, pBRFloatAttribute == null ? 1.0f : pBRFloatAttribute.value);
        }
    };
    public static final BaseShader.Uniform occlusionStrengthUniform = new BaseShader.Uniform("u_OcclusionStrength");
    public static final BaseShader.Setter occlusionStrengthSetter = new BaseShader.LocalSetter() { // from class: net.mgsx.gltf.scene3d.shaders.PBRShader.8
        @Override // com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Setter
        public void set(BaseShader baseShader, int i, Renderable renderable, Attributes attributes) {
            PBRFloatAttribute pBRFloatAttribute = (PBRFloatAttribute) attributes.get(PBRFloatAttribute.class, PBRFloatAttribute.OcclusionStrength);
            baseShader.set(i, pBRFloatAttribute == null ? 1.0f : pBRFloatAttribute.value);
        }
    };
    public static final BaseShader.Uniform occlusionTextureUniform = new BaseShader.Uniform("u_OcclusionSampler", PBRTextureAttribute.OcclusionTexture);
    public static final BaseShader.Setter occlusionTextureSetter = new BaseShader.LocalSetter() { // from class: net.mgsx.gltf.scene3d.shaders.PBRShader.9
        @Override // com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Setter
        public void set(BaseShader baseShader, int i, Renderable renderable, Attributes attributes) {
            baseShader.set(i, baseShader.context.textureBinder.bind(((TextureAttribute) attributes.get(PBRTextureAttribute.OcclusionTexture)).textureDescription));
        }
    };
    public static final BaseShader.Uniform diffuseEnvTextureUniform = new BaseShader.Uniform("u_DiffuseEnvSampler", PBRCubemapAttribute.DiffuseEnv);
    public static final BaseShader.Setter diffuseEnvTextureSetter = new BaseShader.LocalSetter() { // from class: net.mgsx.gltf.scene3d.shaders.PBRShader.10
        @Override // com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Setter
        public void set(BaseShader baseShader, int i, Renderable renderable, Attributes attributes) {
            baseShader.set(i, baseShader.context.textureBinder.bind(((PBRCubemapAttribute) attributes.get(PBRCubemapAttribute.class, PBRCubemapAttribute.DiffuseEnv)).textureDescription));
        }
    };
    public static final BaseShader.Uniform specularEnvTextureUniform = new BaseShader.Uniform("u_SpecularEnvSampler", PBRCubemapAttribute.SpecularEnv);
    public static final BaseShader.Setter specularEnvTextureSetter = new BaseShader.LocalSetter() { // from class: net.mgsx.gltf.scene3d.shaders.PBRShader.11
        @Override // com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Setter
        public void set(BaseShader baseShader, int i, Renderable renderable, Attributes attributes) {
            baseShader.set(i, baseShader.context.textureBinder.bind(((PBRCubemapAttribute) attributes.get(PBRCubemapAttribute.class, PBRCubemapAttribute.SpecularEnv)).textureDescription));
        }
    };
    public static final BaseShader.Uniform envRotationUniform = new BaseShader.Uniform("u_envRotation", PBRMatrixAttribute.EnvRotation);
    public static final BaseShader.Setter envRotationSetter = new BaseShader.LocalSetter() { // from class: net.mgsx.gltf.scene3d.shaders.PBRShader.12
        private final Matrix3 mat3 = new Matrix3();

        @Override // com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Setter
        public void set(BaseShader baseShader, int i, Renderable renderable, Attributes attributes) {
            baseShader.set(i, this.mat3.set(((PBRMatrixAttribute) attributes.get(PBRMatrixAttribute.class, PBRMatrixAttribute.EnvRotation)).matrix));
        }
    };
    public static final BaseShader.Uniform brdfLUTTextureUniform = new BaseShader.Uniform("u_brdfLUT");
    public static final BaseShader.Setter brdfLUTTextureSetter = new BaseShader.LocalSetter() { // from class: net.mgsx.gltf.scene3d.shaders.PBRShader.13
        @Override // com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Setter
        public void set(BaseShader baseShader, int i, Renderable renderable, Attributes attributes) {
            PBRTextureAttribute pBRTextureAttribute = (PBRTextureAttribute) attributes.get(PBRTextureAttribute.class, PBRTextureAttribute.BRDFLUTTexture);
            if (pBRTextureAttribute != null) {
                baseShader.set(i, baseShader.context.textureBinder.bind(pBRTextureAttribute.textureDescription));
            }
        }
    };
    public static final BaseShader.Uniform shadowBiasUniform = new BaseShader.Uniform("u_shadowBias");
    public static final BaseShader.Setter shadowBiasSetter = new BaseShader.LocalSetter() { // from class: net.mgsx.gltf.scene3d.shaders.PBRShader.14
        @Override // com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Setter
        public void set(BaseShader baseShader, int i, Renderable renderable, Attributes attributes) {
            PBRFloatAttribute pBRFloatAttribute = (PBRFloatAttribute) attributes.get(PBRFloatAttribute.class, PBRFloatAttribute.ShadowBias);
            baseShader.set(i, pBRFloatAttribute == null ? 0.0f : pBRFloatAttribute.value);
        }
    };
    public static final BaseShader.Uniform fogEquationUniform = new BaseShader.Uniform("u_fogEquation");
    public static final BaseShader.Setter fogEquationSetter = new BaseShader.LocalSetter() { // from class: net.mgsx.gltf.scene3d.shaders.PBRShader.15
        @Override // com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Setter
        public void set(BaseShader baseShader, int i, Renderable renderable, Attributes attributes) {
            FogAttribute fogAttribute = (FogAttribute) attributes.get(FogAttribute.class, FogAttribute.FogEquation);
            baseShader.set(i, fogAttribute == null ? Vector3.Zero : fogAttribute.value);
        }
    };
    public static final BaseShader.Setter emissiveScaledColor = new BaseShader.LocalSetter() { // from class: net.mgsx.gltf.scene3d.shaders.PBRShader.16
        @Override // com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Setter
        public void set(BaseShader baseShader, int i, Renderable renderable, Attributes attributes) {
            ColorAttribute colorAttribute = (ColorAttribute) attributes.get(ColorAttribute.class, ColorAttribute.Emissive);
            PBRFloatAttribute pBRFloatAttribute = (PBRFloatAttribute) attributes.get(PBRFloatAttribute.class, PBRFloatAttribute.EmissiveIntensity);
            if (pBRFloatAttribute != null) {
                baseShader.set(i, colorAttribute.color.r * pBRFloatAttribute.value, colorAttribute.color.g * pBRFloatAttribute.value, colorAttribute.color.b * pBRFloatAttribute.value, colorAttribute.color.a * pBRFloatAttribute.value);
            } else {
                baseShader.set(i, colorAttribute.color);
            }
        }
    };
    public static final BaseShader.Uniform transmissionFactorUniform = new BaseShader.Uniform("u_transmissionFactor");
    public static final BaseShader.Setter transmissionFactorSetter = new BaseShader.LocalSetter() { // from class: net.mgsx.gltf.scene3d.shaders.PBRShader.17
        @Override // com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Setter
        public void set(BaseShader baseShader, int i, Renderable renderable, Attributes attributes) {
            PBRFloatAttribute pBRFloatAttribute = (PBRFloatAttribute) attributes.get(PBRFloatAttribute.class, PBRFloatAttribute.TransmissionFactor);
            baseShader.set(i, pBRFloatAttribute == null ? 0.0f : pBRFloatAttribute.value);
        }
    };
    public static final BaseShader.Uniform transmissionTextureUniform = new BaseShader.Uniform("u_transmissionSampler", PBRTextureAttribute.TransmissionTexture);
    public static final BaseShader.Setter transmissionTextureSetter = new BaseShader.LocalSetter() { // from class: net.mgsx.gltf.scene3d.shaders.PBRShader.18
        @Override // com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Setter
        public void set(BaseShader baseShader, int i, Renderable renderable, Attributes attributes) {
            baseShader.set(i, baseShader.context.textureBinder.bind(((TextureAttribute) attributes.get(PBRTextureAttribute.TransmissionTexture)).textureDescription));
        }
    };
    public static final BaseShader.Uniform iorUniform = new BaseShader.Uniform("u_ior");
    public static final BaseShader.Setter iorSetter = new BaseShader.LocalSetter() { // from class: net.mgsx.gltf.scene3d.shaders.PBRShader.19
        @Override // com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Setter
        public void set(BaseShader baseShader, int i, Renderable renderable, Attributes attributes) {
            baseShader.set(i, ((PBRFloatAttribute) attributes.get(PBRFloatAttribute.class, PBRFloatAttribute.IOR)).value);
        }
    };
    public static final BaseShader.Uniform thicknessFactorUniform = new BaseShader.Uniform("u_thicknessFactor");
    public static final BaseShader.Setter thicknessFactorSetter = new BaseShader.LocalSetter() { // from class: net.mgsx.gltf.scene3d.shaders.PBRShader.20
        @Override // com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Setter
        public void set(BaseShader baseShader, int i, Renderable renderable, Attributes attributes) {
            baseShader.set(i, ((PBRVolumeAttribute) attributes.get(PBRVolumeAttribute.class, PBRVolumeAttribute.Type)).thicknessFactor);
        }
    };
    public static final BaseShader.Uniform volumeDistanceUniform = new BaseShader.Uniform("u_attenuationDistance");
    public static final BaseShader.Setter volumeDistanceSetter = new BaseShader.LocalSetter() { // from class: net.mgsx.gltf.scene3d.shaders.PBRShader.21
        @Override // com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Setter
        public void set(BaseShader baseShader, int i, Renderable renderable, Attributes attributes) {
            baseShader.set(i, ((PBRVolumeAttribute) attributes.get(PBRVolumeAttribute.class, PBRVolumeAttribute.Type)).attenuationDistance);
        }
    };
    public static final BaseShader.Uniform volumeColorUniform = new BaseShader.Uniform("u_attenuationColor");
    public static final BaseShader.Setter volumeColorSetter = new BaseShader.LocalSetter() { // from class: net.mgsx.gltf.scene3d.shaders.PBRShader.22
        @Override // com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Setter
        public void set(BaseShader baseShader, int i, Renderable renderable, Attributes attributes) {
            PBRVolumeAttribute pBRVolumeAttribute = (PBRVolumeAttribute) attributes.get(PBRVolumeAttribute.class, PBRVolumeAttribute.Type);
            baseShader.set(i, pBRVolumeAttribute.attenuationColor.r, pBRVolumeAttribute.attenuationColor.g, pBRVolumeAttribute.attenuationColor.b);
        }
    };
    public static final BaseShader.Uniform thicknessTextureUniform = new BaseShader.Uniform("u_thicknessSampler", PBRTextureAttribute.ThicknessTexture);
    public static final BaseShader.Setter thicknessTextureSetter = new BaseShader.LocalSetter() { // from class: net.mgsx.gltf.scene3d.shaders.PBRShader.23
        @Override // com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Setter
        public void set(BaseShader baseShader, int i, Renderable renderable, Attributes attributes) {
            baseShader.set(i, baseShader.context.textureBinder.bind(((TextureAttribute) attributes.get(PBRTextureAttribute.ThicknessTexture)).textureDescription));
        }
    };
    public static final BaseShader.Uniform specularFactorUniform = new BaseShader.Uniform("u_specularFactor");
    public static final BaseShader.Setter specularFactorSetter = new BaseShader.LocalSetter() { // from class: net.mgsx.gltf.scene3d.shaders.PBRShader.24
        @Override // com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Setter
        public void set(BaseShader baseShader, int i, Renderable renderable, Attributes attributes) {
            baseShader.set(i, ((PBRFloatAttribute) attributes.get(PBRFloatAttribute.class, PBRFloatAttribute.SpecularFactor)).value);
        }
    };
    public static final BaseShader.Uniform specularColorFactorUniform = new BaseShader.Uniform("u_specularColorFactor");
    public static final BaseShader.Setter specularColorFactorSetter = new BaseShader.LocalSetter() { // from class: net.mgsx.gltf.scene3d.shaders.PBRShader.25
        @Override // com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Setter
        public void set(BaseShader baseShader, int i, Renderable renderable, Attributes attributes) {
            PBRHDRColorAttribute pBRHDRColorAttribute = (PBRHDRColorAttribute) attributes.get(PBRHDRColorAttribute.class, PBRHDRColorAttribute.Specular);
            baseShader.set(i, pBRHDRColorAttribute.r, pBRHDRColorAttribute.g, pBRHDRColorAttribute.b);
        }
    };
    public static final BaseShader.Uniform specularFactorTextureUniform = new BaseShader.Uniform("u_specularFactorSampler", PBRTextureAttribute.SpecularFactorTexture);
    public static final BaseShader.Setter specularFactorTextureSetter = new BaseShader.LocalSetter() { // from class: net.mgsx.gltf.scene3d.shaders.PBRShader.26
        @Override // com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Setter
        public void set(BaseShader baseShader, int i, Renderable renderable, Attributes attributes) {
            baseShader.set(i, baseShader.context.textureBinder.bind(((TextureAttribute) attributes.get(PBRTextureAttribute.SpecularFactorTexture)).textureDescription));
        }
    };
    public static final BaseShader.Uniform specularColorTextureUniform = new BaseShader.Uniform("u_specularColorSampler", PBRTextureAttribute.Specular);
    public static final BaseShader.Setter specularColorTextureSetter = new BaseShader.LocalSetter() { // from class: net.mgsx.gltf.scene3d.shaders.PBRShader.27
        @Override // com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Setter
        public void set(BaseShader baseShader, int i, Renderable renderable, Attributes attributes) {
            baseShader.set(i, baseShader.context.textureBinder.bind(((TextureAttribute) attributes.get(PBRTextureAttribute.Specular)).textureDescription));
        }
    };
    public static final BaseShader.Uniform iridescenceFactorUniform = new BaseShader.Uniform("u_iridescenceFactor");
    public static final BaseShader.Setter iridescenceFactorSetter = new BaseShader.LocalSetter() { // from class: net.mgsx.gltf.scene3d.shaders.PBRShader.28
        @Override // com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Setter
        public void set(BaseShader baseShader, int i, Renderable renderable, Attributes attributes) {
            baseShader.set(i, ((PBRIridescenceAttribute) attributes.get(PBRIridescenceAttribute.class, PBRIridescenceAttribute.Type)).factor);
        }
    };
    public static final BaseShader.Uniform iridescenceIORUniform = new BaseShader.Uniform("u_iridescenceIOR");
    public static final BaseShader.Setter iridescenceIORSetter = new BaseShader.LocalSetter() { // from class: net.mgsx.gltf.scene3d.shaders.PBRShader.29
        @Override // com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Setter
        public void set(BaseShader baseShader, int i, Renderable renderable, Attributes attributes) {
            baseShader.set(i, ((PBRIridescenceAttribute) attributes.get(PBRIridescenceAttribute.class, PBRIridescenceAttribute.Type)).ior);
        }
    };
    public static final BaseShader.Uniform iridescenceThicknessMinUniform = new BaseShader.Uniform("u_iridescenceThicknessMin");
    public static final BaseShader.Setter iridescenceThicknessMinSetter = new BaseShader.LocalSetter() { // from class: net.mgsx.gltf.scene3d.shaders.PBRShader.30
        @Override // com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Setter
        public void set(BaseShader baseShader, int i, Renderable renderable, Attributes attributes) {
            baseShader.set(i, ((PBRIridescenceAttribute) attributes.get(PBRIridescenceAttribute.class, PBRIridescenceAttribute.Type)).thicknessMin);
        }
    };
    public static final BaseShader.Uniform iridescenceThicknessMaxUniform = new BaseShader.Uniform("u_iridescenceThicknessMax");
    public static final BaseShader.Setter iridescenceThicknessMaxSetter = new BaseShader.LocalSetter() { // from class: net.mgsx.gltf.scene3d.shaders.PBRShader.31
        @Override // com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Setter
        public void set(BaseShader baseShader, int i, Renderable renderable, Attributes attributes) {
            baseShader.set(i, ((PBRIridescenceAttribute) attributes.get(PBRIridescenceAttribute.class, PBRIridescenceAttribute.Type)).thicknessMax);
        }
    };
    public static final BaseShader.Uniform iridescenceTextureUniform = new BaseShader.Uniform("u_iridescenceSampler", PBRTextureAttribute.IridescenceTexture);
    public static final BaseShader.Setter iridescenceTextureSetter = new BaseShader.LocalSetter() { // from class: net.mgsx.gltf.scene3d.shaders.PBRShader.32
        @Override // com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Setter
        public void set(BaseShader baseShader, int i, Renderable renderable, Attributes attributes) {
            baseShader.set(i, baseShader.context.textureBinder.bind(((TextureAttribute) attributes.get(PBRTextureAttribute.IridescenceTexture)).textureDescription));
        }
    };
    public static final BaseShader.Uniform iridescenceThicknessTextureUniform = new BaseShader.Uniform("u_iridescenceThicknessSampler", PBRTextureAttribute.IridescenceThicknessTexture);
    public static final BaseShader.Setter iridescenceThicknessTextureSetter = new BaseShader.LocalSetter() { // from class: net.mgsx.gltf.scene3d.shaders.PBRShader.33
        @Override // com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Setter
        public void set(BaseShader baseShader, int i, Renderable renderable, Attributes attributes) {
            baseShader.set(i, baseShader.context.textureBinder.bind(((TextureAttribute) attributes.get(PBRTextureAttribute.IridescenceThicknessTexture)).textureDescription));
        }
    };
    public static final BaseShader.Uniform transmissionSourceTextureUniform = new BaseShader.Uniform("u_transmissionSourceSampler", PBRTextureAttribute.TransmissionSourceTexture);
    public static final BaseShader.Setter transmissionSourceTextureSetter = new BaseShader.LocalSetter() { // from class: net.mgsx.gltf.scene3d.shaders.PBRShader.34
        @Override // com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Setter
        public void set(BaseShader baseShader, int i, Renderable renderable, Attributes attributes) {
            baseShader.set(i, baseShader.context.textureBinder.bind(((TextureAttribute) attributes.get(PBRTextureAttribute.TransmissionSourceTexture)).textureDescription));
        }
    };
    public static final BaseShader.Uniform transmissionSourceMipmapUniform = new BaseShader.Uniform("u_transmissionSourceMipmapScale");
    public static final BaseShader.Setter transmissionSourceMipmapSetter = new BaseShader.LocalSetter() { // from class: net.mgsx.gltf.scene3d.shaders.PBRShader.35
        @Override // com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Setter
        public void set(BaseShader baseShader, int i, Renderable renderable, Attributes attributes) {
            TextureAttribute textureAttribute = (TextureAttribute) attributes.get(PBRTextureAttribute.class, PBRTextureAttribute.TransmissionSourceTexture);
            baseShader.set(i, textureAttribute != null ? (float) (Math.log(((Texture) textureAttribute.textureDescription.texture).getWidth()) / Math.log(2.0d)) : 1.0f);
        }
    };
    public static final BaseShader.Uniform specularMirrorTextureUniform = new BaseShader.Uniform("u_mirrorSpecularSampler", MirrorSourceAttribute.Type);
    public static final BaseShader.Setter specularMirrorTextureSetter = new BaseShader.LocalSetter() { // from class: net.mgsx.gltf.scene3d.shaders.PBRShader.36
        @Override // com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Setter
        public void set(BaseShader baseShader, int i, Renderable renderable, Attributes attributes) {
            baseShader.set(i, baseShader.context.textureBinder.bind(((MirrorSourceAttribute) attributes.get(MirrorSourceAttribute.Type)).textureDescription));
        }
    };
    public static final BaseShader.Uniform specularMirrorMipmapUniform = new BaseShader.Uniform("u_mirrorMipmapScale");
    public static final BaseShader.Setter specularMirrorMipmapSetter = new BaseShader.LocalSetter() { // from class: net.mgsx.gltf.scene3d.shaders.PBRShader.37
        @Override // com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Setter
        public void set(BaseShader baseShader, int i, Renderable renderable, Attributes attributes) {
            MirrorSourceAttribute mirrorSourceAttribute = (MirrorSourceAttribute) attributes.get(MirrorSourceAttribute.class, MirrorSourceAttribute.Type);
            baseShader.set(i, mirrorSourceAttribute != null ? ((float) (Math.log(((Texture) mirrorSourceAttribute.textureDescription.texture).getWidth()) / Math.log(2.0d))) + 0.0f : 1.0f);
        }
    };
    public static final BaseShader.Uniform mirrorNormal = new BaseShader.Uniform("u_mirrorNormal");
    public static final BaseShader.Setter mirrorNormalSetter = new BaseShader.LocalSetter() { // from class: net.mgsx.gltf.scene3d.shaders.PBRShader.38
        @Override // com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Setter
        public void set(BaseShader baseShader, int i, Renderable renderable, Attributes attributes) {
            baseShader.set(i, ((MirrorSourceAttribute) attributes.get(MirrorSourceAttribute.class, MirrorSourceAttribute.Type)).normal);
        }
    };
    public static final BaseShader.Uniform projViewTransUniform = new BaseShader.Uniform("u_projViewTrans");
    public static final BaseShader.Setter projViewTransSetter = new BaseShader.LocalSetter() { // from class: net.mgsx.gltf.scene3d.shaders.PBRShader.39
        @Override // com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Setter
        public void set(BaseShader baseShader, int i, Renderable renderable, Attributes attributes) {
            baseShader.set(i, baseShader.camera.combined);
        }
    };
    public static final BaseShader.Uniform viewportInvUniform = new BaseShader.Uniform("u_viewportInv");
    public static final BaseShader.Setter viewportInvSetter = new BaseShader.LocalSetter() { // from class: net.mgsx.gltf.scene3d.shaders.PBRShader.40
        @Override // com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Setter
        public void set(BaseShader baseShader, int i, Renderable renderable, Attributes attributes) {
            baseShader.set(i, 1.0f / baseShader.camera.viewportWidth, 1.0f / baseShader.camera.viewportHeight);
        }
    };
    public static final BaseShader.Uniform clippingPlaneUniform = new BaseShader.Uniform("u_clippingPlane");
    public static final BaseShader.Setter clippingPlaneSetter = new BaseShader.LocalSetter() { // from class: net.mgsx.gltf.scene3d.shaders.PBRShader.41
        @Override // com.badlogic.gdx.graphics.g3d.shaders.BaseShader.Setter
        public void set(BaseShader baseShader, int i, Renderable renderable, Attributes attributes) {
            ClippingPlaneAttribute clippingPlaneAttribute = (ClippingPlaneAttribute) attributes.get(ClippingPlaneAttribute.class, ClippingPlaneAttribute.Type);
            baseShader.set(i, clippingPlaneAttribute.plane.normal.x, clippingPlaneAttribute.plane.normal.y, clippingPlaneAttribute.plane.normal.z, clippingPlaneAttribute.plane.d);
        }
    };
    private static final PBRTextureAttribute[] transformTexture = {null, null};
    private static final Matrix3 textureTransform = new Matrix3();
    private static long[] allTextureTypes = {PBRTextureAttribute.BaseColorTexture, PBRTextureAttribute.EmissiveTexture, PBRTextureAttribute.NormalTexture, PBRTextureAttribute.MetallicRoughnessTexture, PBRTextureAttribute.OcclusionTexture, PBRTextureAttribute.TransmissionTexture, PBRTextureAttribute.ThicknessTexture, PBRTextureAttribute.IridescenceTexture, PBRTextureAttribute.IridescenceThicknessTexture};

    public PBRShader(Renderable renderable, DefaultShader.Config config, String str) {
        super(renderable, config, str);
        this.textureCoordinateMapMask = getTextureCoordinateMapMask(renderable.material);
        this.morphTargetsMask = computeMorphTargetsMask(renderable);
        this.vertexColorLayers = computeVertexColorLayers(renderable);
        int iCountShadowCascades = countShadowCascades(renderable);
        this.cascadeCount = iCountShadowCascades;
        this.u_csmSamplers = new int[iCountShadowCascades];
        this.csmTransforms = new float[iCountShadowCascades * 16];
        this.csmPCFClip = new float[iCountShadowCascades * 2];
        this.u_BaseColorTexture = register(baseColorTextureUniform, baseColorTextureSetter);
        this.u_BaseColorFactor = register(baseColorFactorUniform, baseColorFactorSetter);
        this.u_EmissiveTexture = register(emissiveTextureUniform, emissiveTextureSetter);
        this.u_DiffuseEnvSampler = register(diffuseEnvTextureUniform, diffuseEnvTextureSetter);
        this.u_SpecularEnvSampler = register(specularEnvTextureUniform, specularEnvTextureSetter);
        this.u_envRotation = register(envRotationUniform, envRotationSetter);
        this.u_metallicRoughness = register(metallicRoughnessUniform, metallicRoughnessSetter);
        this.u_metallicRoughnessTexture = register(metallicRoughnessTextureUniform, metallicRoughnessTextureSetter);
        this.u_occlusionTexture = register(occlusionTextureUniform, occlusionTextureSetter);
        this.u_occlusionStrength = register(occlusionStrengthUniform, occlusionStrengthSetter);
        this.u_brdfLUTTexture = register(brdfLUTTextureUniform, brdfLUTTextureSetter);
        this.u_NormalTexture = register(normalTextureUniform, normalTextureSetter);
        this.u_NormalScale = register(normalScaleUniform, normalScaleSetter);
        this.u_ShadowBias = register(shadowBiasUniform, shadowBiasSetter);
        this.u_FogEquation = register(fogEquationUniform, fogEquationSetter);
        this.u_emissive = register(DefaultShader.Inputs.emissiveColor, emissiveScaledColor);
        this.u_transmissionFactor = register(transmissionFactorUniform, transmissionFactorSetter);
        this.u_transmissionTexture = register(transmissionTextureUniform, transmissionTextureSetter);
        this.u_ior = register(iorUniform, iorSetter);
        this.u_thicknessFactor = register(thicknessFactorUniform, thicknessFactorSetter);
        this.u_volumeDistance = register(volumeDistanceUniform, volumeDistanceSetter);
        this.u_volumeColor = register(volumeColorUniform, volumeColorSetter);
        this.u_thicknessTexture = register(thicknessTextureUniform, thicknessTextureSetter);
        this.u_transmissionSourceTexture = register(transmissionSourceTextureUniform, transmissionSourceTextureSetter);
        this.u_transmissionSourceMipmap = register(transmissionSourceMipmapUniform, transmissionSourceMipmapSetter);
        this.u_specularFactor = register(specularFactorUniform, specularFactorSetter);
        this.u_specularColorFactor = register(specularColorFactorUniform, specularColorFactorSetter);
        this.u_specularFactorTexture = register(specularFactorTextureUniform, specularFactorTextureSetter);
        this.u_specularColorTexture = register(specularColorTextureUniform, specularColorTextureSetter);
        this.u_iridescenceFactor = register(iridescenceFactorUniform, iridescenceFactorSetter);
        this.u_iridescenceIOR = register(iridescenceIORUniform, iridescenceIORSetter);
        this.u_iridescenceThicknessMin = register(iridescenceThicknessMinUniform, iridescenceThicknessMinSetter);
        this.u_iridescenceThicknessMax = register(iridescenceThicknessMaxUniform, iridescenceThicknessMaxSetter);
        this.u_iridescenceTexture = register(iridescenceTextureUniform, iridescenceTextureSetter);
        this.u_iridescenceThicknessTexture = register(iridescenceThicknessTextureUniform, iridescenceThicknessTextureSetter);
        this.u_specularMirrorSampler = register(specularMirrorTextureUniform, specularMirrorTextureSetter);
        this.u_specularMirrorMipmapScale = register(specularMirrorMipmapUniform, specularMirrorMipmapSetter);
        this.u_specularMirrorNormal = register(mirrorNormal, mirrorNormalSetter);
        this.u_viewportInv = register(viewportInvUniform, viewportInvSetter);
        this.u_clippingPlane = register(clippingPlaneUniform, clippingPlaneSetter);
    }

    private int computeVertexColorLayers(Renderable renderable) {
        VertexAttributes vertexAttributes = renderable.meshPart.mesh.getVertexAttributes();
        int size = vertexAttributes.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            if (vertexAttributes.get(i2).usage == 2) {
                i++;
            }
        }
        return i;
    }

    private int countShadowCascades(Renderable renderable) {
        CascadeShadowMapAttribute cascadeShadowMapAttribute;
        if (renderable.environment == null || (cascadeShadowMapAttribute = (CascadeShadowMapAttribute) renderable.environment.get(CascadeShadowMapAttribute.class, CascadeShadowMapAttribute.Type)) == null) {
            return 0;
        }
        return cascadeShadowMapAttribute.cascadeShadowMap.lights.size;
    }

    @Override // com.badlogic.gdx.graphics.g3d.shaders.DefaultShader, com.badlogic.gdx.graphics.g3d.Shader
    public boolean canRender(Renderable renderable) {
        if (getTextureCoordinateMapMask(renderable.material) == this.textureCoordinateMapMask && this.morphTargetsMask == computeMorphTargetsMask(renderable) && this.vertexColorLayers == computeVertexColorLayers(renderable) && this.cascadeCount == countShadowCascades(renderable)) {
            return super.canRender(renderable);
        }
        return false;
    }

    public long computeMorphTargetsMask(Renderable renderable) {
        VertexAttributes vertexAttributes = renderable.meshPart.mesh.getVertexAttributes();
        int size = vertexAttributes.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            VertexAttribute vertexAttribute = vertexAttributes.get(i2);
            if (vertexAttribute.usage == 512) {
                i |= 1 << vertexAttribute.unit;
            }
            if (vertexAttribute.usage == 1024) {
                i |= 1 << (vertexAttribute.unit + 8);
            }
            if (vertexAttribute.usage == 2048) {
                i |= 1 << (vertexAttribute.unit + 16);
            }
        }
        return i;
    }

    private static long getTextureCoordinateMapMask(Attributes attributes) {
        long j = 0;
        int i = 0;
        for (long j2 : allTextureTypes) {
            PBRTextureAttribute pBRTextureAttribute = (PBRTextureAttribute) attributes.get(PBRTextureAttribute.class, j2);
            if (pBRTextureAttribute != null) {
                j |= (long) ((pBRTextureAttribute.uvIndex & 1) << i);
            }
            i++;
        }
        return j;
    }

    @Override // com.badlogic.gdx.graphics.g3d.shaders.BaseShader
    public void init(ShaderProgram shaderProgram, Renderable renderable) {
        super.init(shaderProgram, renderable);
        this.u_mipmapScale = shaderProgram.fetchUniformLocation("u_mipmapScale", false);
        this.u_texCoord0Transform = shaderProgram.fetchUniformLocation("u_texCoord0Transform", false);
        this.u_texCoord1Transform = shaderProgram.fetchUniformLocation("u_texCoord1Transform", false);
        this.u_morphTargets1 = shaderProgram.fetchUniformLocation("u_morphTargets1", false);
        this.u_morphTargets2 = shaderProgram.fetchUniformLocation("u_morphTargets2", false);
        this.u_ambientLight = shaderProgram.fetchUniformLocation("u_ambientLight", false);
        this.u_csmPCFClip = shaderProgram.fetchUniformLocation("u_csmPCFClip", false);
        this.u_csmTransforms = shaderProgram.fetchUniformLocation("u_csmTransforms", false);
        for (int i = 0; i < this.cascadeCount; i++) {
            this.u_csmSamplers[i] = shaderProgram.fetchUniformLocation("u_csmSamplers" + i, false);
        }
    }

    @Override // com.badlogic.gdx.graphics.g3d.shaders.DefaultShader
    protected void bindMaterial(Attributes attributes) {
        super.bindMaterial(attributes);
        PBRTextureAttribute[] pBRTextureAttributeArr = transformTexture;
        pBRTextureAttributeArr[0] = null;
        pBRTextureAttributeArr[1] = null;
        for (long j : allTextureTypes) {
            PBRTextureAttribute pBRTextureAttribute = (PBRTextureAttribute) attributes.get(PBRTextureAttribute.class, j);
            if (pBRTextureAttribute != null) {
                transformTexture[pBRTextureAttribute.uvIndex] = pBRTextureAttribute;
            }
        }
        if (this.u_texCoord0Transform >= 0) {
            PBRTextureAttribute pBRTextureAttribute2 = transformTexture[0];
            if (pBRTextureAttribute2 != null) {
                Matrix3 matrix3 = textureTransform;
                matrix3.idt();
                matrix3.translate(pBRTextureAttribute2.offsetU, pBRTextureAttribute2.offsetV);
                matrix3.rotateRad(-pBRTextureAttribute2.rotationUV);
                matrix3.scale(pBRTextureAttribute2.scaleU, pBRTextureAttribute2.scaleV);
            } else {
                textureTransform.idt();
            }
            this.program.setUniformMatrix(this.u_texCoord0Transform, textureTransform);
        }
        if (this.u_texCoord1Transform >= 0) {
            PBRTextureAttribute pBRTextureAttribute3 = transformTexture[1];
            if (pBRTextureAttribute3 != null) {
                Matrix3 matrix4 = textureTransform;
                matrix4.setToTranslation(pBRTextureAttribute3.offsetU, pBRTextureAttribute3.offsetV);
                matrix4.rotateRad(-pBRTextureAttribute3.rotationUV);
                matrix4.scale(pBRTextureAttribute3.scaleU, pBRTextureAttribute3.scaleV);
            } else {
                textureTransform.idt();
            }
            this.program.setUniformMatrix(this.u_texCoord1Transform, textureTransform);
        }
    }

    @Override // com.badlogic.gdx.graphics.g3d.shaders.DefaultShader, com.badlogic.gdx.graphics.g3d.shaders.BaseShader
    public void render(Renderable renderable, Attributes attributes) {
        if (this.u_mipmapScale >= 0) {
            PBRCubemapAttribute pBRCubemapAttribute = (PBRCubemapAttribute) attributes.get(PBRCubemapAttribute.class, PBRCubemapAttribute.SpecularEnv);
            this.program.setUniformf(this.u_mipmapScale, pBRCubemapAttribute != null ? (float) (Math.log(((Cubemap) pBRCubemapAttribute.textureDescription.texture).getWidth()) / Math.log(2.0d)) : 1.0f);
        }
        if (this.u_morphTargets1 >= 0) {
            if (renderable.userData instanceof WeightVector) {
                WeightVector weightVector = (WeightVector) renderable.userData;
                this.program.setUniformf(this.u_morphTargets1, weightVector.get(0), weightVector.get(1), weightVector.get(2), weightVector.get(3));
            } else {
                this.program.setUniformf(this.u_morphTargets1, 0.0f, 0.0f, 0.0f, 0.0f);
            }
        }
        if (this.u_morphTargets2 >= 0) {
            if (renderable.userData instanceof WeightVector) {
                WeightVector weightVector2 = (WeightVector) renderable.userData;
                this.program.setUniformf(this.u_morphTargets2, weightVector2.get(4), weightVector2.get(5), weightVector2.get(6), weightVector2.get(7));
            } else {
                this.program.setUniformf(this.u_morphTargets2, 0.0f, 0.0f, 0.0f, 0.0f);
            }
        }
        super.render(renderable, attributes);
    }

    @Override // com.badlogic.gdx.graphics.g3d.shaders.DefaultShader
    protected void bindLights(Renderable renderable, Attributes attributes) {
        DirectionalLightsAttribute directionalLightsAttribute = (DirectionalLightsAttribute) attributes.get(DirectionalLightsAttribute.class, DirectionalLightsAttribute.Type);
        if (directionalLightsAttribute != null) {
            Array.ArrayIterator<DirectionalLight> it = directionalLightsAttribute.lights.iterator();
            while (it.hasNext()) {
                DirectionalLight next = it.next();
                if (next instanceof DirectionalLightEx) {
                    ((DirectionalLightEx) next).updateColor();
                }
            }
        }
        super.bindLights(renderable, attributes);
        ColorAttribute colorAttribute = (ColorAttribute) attributes.get(ColorAttribute.class, ColorAttribute.AmbientLight);
        if (colorAttribute != null) {
            this.program.setUniformf(this.u_ambientLight, colorAttribute.color.r, colorAttribute.color.g, colorAttribute.color.b);
        }
        CascadeShadowMapAttribute cascadeShadowMapAttribute = (CascadeShadowMapAttribute) attributes.get(CascadeShadowMapAttribute.class, CascadeShadowMapAttribute.Type);
        if (cascadeShadowMapAttribute != null) {
            int[] iArr = this.u_csmSamplers;
            if (iArr.length <= 0 || iArr[0] < 0) {
                return;
            }
            Array<DirectionalShadowLight> array = cascadeShadowMapAttribute.cascadeShadowMap.lights;
            for (int i = 0; i < array.size; i++) {
                DirectionalShadowLight directionalShadowLight = array.get(i);
                float width = directionalShadowLight.getDepthMap().texture.getWidth() * 2.0f;
                float f = 1.0f / width;
                float f2 = 3.0f / width;
                this.program.setUniformi(this.u_csmSamplers[i], this.context.textureBinder.bind(directionalShadowLight.getDepthMap()));
                System.arraycopy(directionalShadowLight.getProjViewTrans().val, 0, this.csmTransforms, i * 16, 16);
                float[] fArr = this.csmPCFClip;
                int i2 = i * 2;
                fArr[i2] = f;
                fArr[i2 + 1] = f2;
            }
            ShaderProgram shaderProgram = this.program;
            int i3 = this.u_csmTransforms;
            float[] fArr2 = this.csmTransforms;
            shaderProgram.setUniformMatrix4fv(i3, fArr2, 0, fArr2.length);
            ShaderProgram shaderProgram2 = this.program;
            int i4 = this.u_csmPCFClip;
            float[] fArr3 = this.csmPCFClip;
            shaderProgram2.setUniform2fv(i4, fArr3, 0, fArr3.length);
        }
    }
}
