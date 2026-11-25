package net.mgsx.gltf.scene3d.shaders;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cubemap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.badlogic.gdx.graphics.g3d.shaders.DepthShader;
import com.badlogic.gdx.graphics.g3d.utils.DefaultShaderProvider;
import com.badlogic.gdx.graphics.g3d.utils.DepthShaderProvider;
import com.badlogic.gdx.graphics.g3d.utils.TextureDescriptor;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.GdxRuntimeException;
import net.mgsx.gltf.scene3d.attributes.CascadeShadowMapAttribute;
import net.mgsx.gltf.scene3d.attributes.ClippingPlaneAttribute;
import net.mgsx.gltf.scene3d.attributes.FogAttribute;
import net.mgsx.gltf.scene3d.attributes.MirrorAttribute;
import net.mgsx.gltf.scene3d.attributes.MirrorSourceAttribute;
import net.mgsx.gltf.scene3d.attributes.PBRColorAttribute;
import net.mgsx.gltf.scene3d.attributes.PBRCubemapAttribute;
import net.mgsx.gltf.scene3d.attributes.PBRFlagAttribute;
import net.mgsx.gltf.scene3d.attributes.PBRFloatAttribute;
import net.mgsx.gltf.scene3d.attributes.PBRHDRColorAttribute;
import net.mgsx.gltf.scene3d.attributes.PBRIridescenceAttribute;
import net.mgsx.gltf.scene3d.attributes.PBRMatrixAttribute;
import net.mgsx.gltf.scene3d.attributes.PBRTextureAttribute;
import net.mgsx.gltf.scene3d.attributes.PBRVolumeAttribute;
import net.mgsx.gltf.scene3d.utils.LightUtils;
import net.mgsx.gltf.scene3d.utils.ShaderParser;

/* JADX INFO: loaded from: classes12.dex */
public class PBRShaderProvider extends DefaultShaderProvider {
    public static final String TAG = "PBRShader";
    private static final LightUtils.LightsInfo lightsInfo = new LightUtils.LightsInfo();
    private static String defaultVertexShader = null;
    private static String defaultFragmentShader = null;

    public static String getDefaultVertexShader() {
        if (defaultVertexShader == null) {
            defaultVertexShader = ShaderParser.parse(Gdx.files.classpath("net/mgsx/gltf/shaders/pbr/pbr.vs.glsl"));
        }
        return defaultVertexShader;
    }

    public static String getDefaultFragmentShader() {
        if (defaultFragmentShader == null) {
            defaultFragmentShader = ShaderParser.parse(Gdx.files.classpath("net/mgsx/gltf/shaders/pbr/pbr.fs.glsl"));
        }
        return defaultFragmentShader;
    }

    public static PBRShaderConfig createDefaultConfig() {
        PBRShaderConfig pBRShaderConfig = new PBRShaderConfig();
        pBRShaderConfig.vertexShader = getDefaultVertexShader();
        pBRShaderConfig.fragmentShader = getDefaultFragmentShader();
        return pBRShaderConfig;
    }

    public static DepthShader.Config createDefaultDepthConfig() {
        return PBRDepthShaderProvider.createDefaultConfig();
    }

    public static PBRShaderProvider createDefault(int i) {
        PBRShaderConfig pBRShaderConfigCreateDefaultConfig = createDefaultConfig();
        pBRShaderConfigCreateDefaultConfig.numBones = i;
        return createDefault(pBRShaderConfigCreateDefaultConfig);
    }

    public static PBRShaderProvider createDefault(PBRShaderConfig pBRShaderConfig) {
        return new PBRShaderProvider(pBRShaderConfig);
    }

    public static DepthShaderProvider createDefaultDepth(int i) {
        DepthShader.Config configCreateDefaultDepthConfig = createDefaultDepthConfig();
        configCreateDefaultDepthConfig.numBones = i;
        return createDefaultDepth(configCreateDefaultDepthConfig);
    }

    public static DepthShaderProvider createDefaultDepth(DepthShader.Config config) {
        return new PBRDepthShaderProvider(config);
    }

    public PBRShaderProvider(PBRShaderConfig pBRShaderConfig) {
        super(pBRShaderConfig == null ? createDefaultConfig() : pBRShaderConfig);
        if (this.config.vertexShader == null) {
            this.config.vertexShader = getDefaultVertexShader();
        }
        if (this.config.fragmentShader == null) {
            this.config.fragmentShader = getDefaultFragmentShader();
        }
    }

    public int getShaderCount() {
        return this.shaders.size;
    }

    public static String morphTargetsPrefix(Renderable renderable) {
        String str = "";
        for (VertexAttribute vertexAttribute : renderable.meshPart.mesh.getVertexAttributes()) {
            for (int i = 0; i < 8; i++) {
                if (vertexAttribute.usage == 512 && vertexAttribute.unit == i) {
                    str = str + "#define position" + i + "Flag\n";
                } else if (vertexAttribute.usage == 1024 && vertexAttribute.unit == i) {
                    str = str + "#define normal" + i + "Flag\n";
                } else if (vertexAttribute.usage == 2048 && vertexAttribute.unit == i) {
                    str = str + "#define tangent" + i + "Flag\n";
                }
            }
        }
        return str;
    }

    protected boolean isGL3() {
        return Gdx.graphics.getGLVersion().isVersionEqualToOrHigher(3, 0);
    }

    public String createPrefixBase(Renderable renderable, PBRShaderConfig pBRShaderConfig) {
        String strCreatePrefix = DefaultShader.createPrefix(renderable, pBRShaderConfig);
        String str = pBRShaderConfig.glslVersion;
        if (isGL3()) {
            if (Gdx.f82app.getType() == Application.ApplicationType.Desktop) {
                if (str == null) {
                    str = "#version 130\n#define GLSL3\n";
                }
            } else if ((Gdx.f82app.getType() == Application.ApplicationType.Android || Gdx.f82app.getType() == Application.ApplicationType.iOS || Gdx.f82app.getType() == Application.ApplicationType.WebGL) && str == null) {
                str = "#version 300 es\n#define GLSL3\n";
            }
        }
        String str2 = str != null ? "" + str : "";
        if (pBRShaderConfig.prefix != null) {
            str2 = str2 + pBRShaderConfig.prefix;
        }
        return str2 + strCreatePrefix;
    }

    public String createPrefixSRGB(Renderable renderable, PBRShaderConfig pBRShaderConfig) {
        String str;
        if (pBRShaderConfig.manualSRGB == PBRShaderConfig.SRGB.NONE) {
            str = "";
        } else if (pBRShaderConfig.manualSRGB != PBRShaderConfig.SRGB.FAST) {
            str = "#define MANUAL_SRGB\n";
        } else {
            str = "#define MANUAL_SRGB\n#define SRGB_FAST_APPROXIMATION\n";
        }
        if (pBRShaderConfig.manualGammaCorrection) {
            str = str + "#define GAMMA_CORRECTION " + pBRShaderConfig.gamma + "\n";
        }
        if (pBRShaderConfig.transmissionSRGB != PBRShaderConfig.SRGB.NONE) {
            str = str + "#define TS_MANUAL_SRGB\n";
            if (pBRShaderConfig.transmissionSRGB == PBRShaderConfig.SRGB.FAST) {
                str = str + "#define TS_SRGB_FAST_APPROXIMATION\n";
            }
        }
        if (pBRShaderConfig.mirrorSRGB == PBRShaderConfig.SRGB.NONE) {
            return str;
        }
        String str2 = str + "#define MS_MANUAL_SRGB\n";
        return pBRShaderConfig.mirrorSRGB == PBRShaderConfig.SRGB.FAST ? str2 + "#define MS_SRGB_FAST_APPROXIMATION\n" : str2;
    }

    @Override // com.badlogic.gdx.graphics.g3d.utils.DefaultShaderProvider, com.badlogic.gdx.graphics.g3d.utils.BaseShaderProvider
    protected Shader createShader(Renderable renderable) {
        boolean z;
        PBRCubemapAttribute pBRCubemapAttribute;
        boolean z2;
        PBRShaderConfig pBRShaderConfig = (PBRShaderConfig) this.config;
        String str = createPrefixBase(renderable, pBRShaderConfig) + morphTargetsPrefix(renderable);
        if (renderable.material.has(PBRColorAttribute.BaseColorFactor)) {
            str = str + "#define baseColorFactorFlag\n";
        }
        int i = renderable.meshPart.primitiveType;
        int iMax = 0;
        if (i == 0 || i == 1 || i == 2 || i == 3 || renderable.material.has(PBRFlagAttribute.Unlit) || renderable.meshPart.mesh.getVertexAttribute(8) == null) {
            str = str + "#define unlitFlag\n";
        } else {
            if (renderable.material.has(PBRTextureAttribute.MetallicRoughnessTexture)) {
                str = str + "#define metallicRoughnessTextureFlag\n";
            }
            if (renderable.material.has(PBRTextureAttribute.OcclusionTexture)) {
                str = str + "#define occlusionTextureFlag\n";
            }
            if (renderable.material.has(PBRFloatAttribute.TransmissionFactor)) {
                str = str + "#define transmissionFlag\n";
            }
            if (renderable.material.has(PBRTextureAttribute.TransmissionTexture)) {
                str = str + "#define transmissionTextureFlag\n";
            }
            if (renderable.material.has(PBRVolumeAttribute.Type)) {
                str = str + "#define volumeFlag\n";
            }
            if (renderable.material.has(PBRTextureAttribute.ThicknessTexture)) {
                str = str + "#define thicknessTextureFlag\n";
            }
            if (renderable.material.has(PBRFloatAttribute.IOR)) {
                str = str + "#define iorFlag\n";
            }
            if (renderable.material.has(PBRFloatAttribute.SpecularFactor)) {
                str = str + "#define specularFactorFlag\n";
                z = true;
            } else {
                z = false;
            }
            if (renderable.material.has(PBRHDRColorAttribute.Specular)) {
                str = str + "#define specularColorFlag\n";
                z = true;
            }
            if (renderable.material.has(PBRTextureAttribute.SpecularFactorTexture)) {
                str = str + "#define specularFactorTextureFlag\n";
                z = true;
            }
            if (renderable.material.has(PBRTextureAttribute.SpecularColorTexture)) {
                str = str + "#define specularColorTextureFlag\n";
                z = true;
            }
            if (z) {
                str = str + "#define specularFlag\n";
            }
            if (renderable.material.has(PBRIridescenceAttribute.Type)) {
                str = str + "#define iridescenceFlag\n";
            }
            if (renderable.material.has(PBRTextureAttribute.IridescenceTexture)) {
                str = str + "#define iridescenceTextureFlag\n";
            }
            if (renderable.material.has(PBRTextureAttribute.IridescenceThicknessTexture)) {
                str = str + "#define iridescenceThicknessTextureFlag\n";
            }
            if (renderable.environment.has(ClippingPlaneAttribute.Type)) {
                str = str + "#define clippingPlaneFlag\n";
            }
            CascadeShadowMapAttribute cascadeShadowMapAttribute = (CascadeShadowMapAttribute) renderable.environment.get(CascadeShadowMapAttribute.class, CascadeShadowMapAttribute.Type);
            if (cascadeShadowMapAttribute != null) {
                int i2 = cascadeShadowMapAttribute.cascadeShadowMap.lights.size;
                if (i2 <= 8) {
                    str = str + "#define numCSM " + i2 + "\n";
                } else {
                    throw new GdxRuntimeException("more than 8 cascade shadow textures not supported");
                }
            }
            if (renderable.environment != null) {
                if (renderable.environment.has(PBRTextureAttribute.TransmissionSourceTexture)) {
                    str = str + "#define transmissionSourceFlag\n";
                }
                MirrorAttribute mirrorAttribute = null;
                if (renderable.environment.has(PBRCubemapAttribute.SpecularEnv)) {
                    str = str + "#define diffuseSpecularEnvSeparateFlag\n";
                    pBRCubemapAttribute = (PBRCubemapAttribute) renderable.environment.get(PBRCubemapAttribute.class, PBRCubemapAttribute.SpecularEnv);
                } else if (renderable.environment.has(PBRCubemapAttribute.DiffuseEnv)) {
                    pBRCubemapAttribute = (PBRCubemapAttribute) renderable.environment.get(PBRCubemapAttribute.class, PBRCubemapAttribute.DiffuseEnv);
                } else {
                    pBRCubemapAttribute = renderable.environment.has(PBRCubemapAttribute.EnvironmentMap) ? (PBRCubemapAttribute) renderable.environment.get(PBRCubemapAttribute.class, PBRCubemapAttribute.EnvironmentMap) : null;
                }
                if (renderable.environment.has(MirrorSourceAttribute.Type) && renderable.material.has(MirrorAttribute.Specular)) {
                    mirrorAttribute = (MirrorAttribute) renderable.environment.get(MirrorAttribute.class, MirrorAttribute.Specular);
                    str = str + "#define mirrorSpecularFlag\n";
                }
                if (pBRCubemapAttribute != null || mirrorAttribute != null) {
                    str = str + "#define USE_IBL\n";
                    if (isGL3()) {
                        z2 = true;
                    } else if (Gdx.graphics.supportsExtension("EXT_shader_texture_lod")) {
                        str = str + "#define USE_TEXTURE_LOD_EXT\n";
                        z2 = true;
                    } else {
                        z2 = false;
                    }
                    if (pBRCubemapAttribute != null) {
                        Texture.TextureFilter textureFilter = pBRCubemapAttribute.textureDescription.minFilter;
                        TextureDescriptor<Cubemap> textureDescriptor = pBRCubemapAttribute.textureDescription;
                        Texture.TextureFilter minFilter = textureFilter != null ? textureDescriptor.minFilter : ((Cubemap) textureDescriptor.texture).getMinFilter();
                        if (z2 && minFilter.equals(Texture.TextureFilter.MipMap)) {
                            str = str + "#define USE_TEX_LOD\n";
                        }
                    }
                    if (renderable.environment.has(PBRTextureAttribute.BRDFLUTTexture)) {
                        str = str + "#define brdfLUTTexture\n";
                    }
                }
                if (renderable.environment.has(ColorAttribute.AmbientLight)) {
                    str = str + "#define ambientLightFlag\n";
                }
                if (renderable.environment.has(PBRMatrixAttribute.EnvRotation)) {
                    str = str + "#define ENV_ROTATION\n";
                }
            }
        }
        String str2 = str + createPrefixSRGB(renderable, pBRShaderConfig);
        TextureAttribute textureAttribute = (TextureAttribute) renderable.material.get(TextureAttribute.class, TextureAttribute.Diffuse);
        int iMax2 = -1;
        if (textureAttribute != null) {
            str2 = str2 + "#define v_diffuseUV v_texCoord" + textureAttribute.uvIndex + "\n";
            iMax2 = Math.max(-1, textureAttribute.uvIndex);
        }
        TextureAttribute textureAttribute2 = (TextureAttribute) renderable.material.get(TextureAttribute.class, TextureAttribute.Emissive);
        if (textureAttribute2 != null) {
            str2 = str2 + "#define v_emissiveUV v_texCoord" + textureAttribute2.uvIndex + "\n";
            iMax2 = Math.max(iMax2, textureAttribute2.uvIndex);
        }
        TextureAttribute textureAttribute3 = (TextureAttribute) renderable.material.get(TextureAttribute.class, TextureAttribute.Normal);
        if (textureAttribute3 != null) {
            str2 = str2 + "#define v_normalUV v_texCoord" + textureAttribute3.uvIndex + "\n";
            iMax2 = Math.max(iMax2, textureAttribute3.uvIndex);
        }
        TextureAttribute textureAttribute4 = (TextureAttribute) renderable.material.get(TextureAttribute.class, PBRTextureAttribute.MetallicRoughnessTexture);
        if (textureAttribute4 != null) {
            str2 = str2 + "#define v_metallicRoughnessUV v_texCoord" + textureAttribute4.uvIndex + "\n";
            iMax2 = Math.max(iMax2, textureAttribute4.uvIndex);
        }
        TextureAttribute textureAttribute5 = (TextureAttribute) renderable.material.get(TextureAttribute.class, PBRTextureAttribute.OcclusionTexture);
        if (textureAttribute5 != null) {
            str2 = str2 + "#define v_occlusionUV v_texCoord" + textureAttribute5.uvIndex + "\n";
            iMax2 = Math.max(iMax2, textureAttribute5.uvIndex);
        }
        TextureAttribute textureAttribute6 = (TextureAttribute) renderable.material.get(TextureAttribute.class, PBRTextureAttribute.TransmissionTexture);
        if (textureAttribute6 != null) {
            str2 = str2 + "#define v_transmissionUV v_texCoord" + textureAttribute6.uvIndex + "\n";
            iMax2 = Math.max(iMax2, textureAttribute6.uvIndex);
        }
        TextureAttribute textureAttribute7 = (TextureAttribute) renderable.material.get(TextureAttribute.class, PBRTextureAttribute.ThicknessTexture);
        if (textureAttribute7 != null) {
            str2 = str2 + "#define v_thicknessUV v_texCoord" + textureAttribute7.uvIndex + "\n";
            iMax2 = Math.max(iMax2, textureAttribute7.uvIndex);
        }
        TextureAttribute textureAttribute8 = (TextureAttribute) renderable.material.get(TextureAttribute.class, PBRTextureAttribute.SpecularFactorTexture);
        if (textureAttribute8 != null) {
            str2 = str2 + "#define v_specularFactorUV v_texCoord" + textureAttribute8.uvIndex + "\n";
            iMax2 = Math.max(iMax2, textureAttribute8.uvIndex);
        }
        TextureAttribute textureAttribute9 = (TextureAttribute) renderable.material.get(TextureAttribute.class, PBRTextureAttribute.Specular);
        if (textureAttribute9 != null) {
            str2 = str2 + "#define v_specularColorUV v_texCoord" + textureAttribute9.uvIndex + "\n";
            iMax2 = Math.max(iMax2, textureAttribute9.uvIndex);
        }
        TextureAttribute textureAttribute10 = (TextureAttribute) renderable.material.get(TextureAttribute.class, PBRTextureAttribute.IridescenceTexture);
        if (textureAttribute10 != null) {
            str2 = str2 + "#define v_iridescenceUV v_texCoord" + textureAttribute10.uvIndex + "\n";
            iMax2 = Math.max(iMax2, textureAttribute10.uvIndex);
        }
        TextureAttribute textureAttribute11 = (TextureAttribute) renderable.material.get(TextureAttribute.class, PBRTextureAttribute.IridescenceThicknessTexture);
        if (textureAttribute11 != null) {
            str2 = str2 + "#define v_iridescenceThicknessUV v_texCoord" + textureAttribute11.uvIndex + "\n";
            iMax2 = Math.max(iMax2, textureAttribute11.uvIndex);
        }
        if (iMax2 >= 0) {
            str2 = str2 + "#define textureFlag\n";
        }
        if (iMax2 == 1) {
            str2 = str2 + "#define textureCoord1Flag\n";
        } else if (iMax2 > 1) {
            throw new GdxRuntimeException("more than 2 texture coordinates attribute not supported");
        }
        if (renderable.environment != null && renderable.environment.has(FogAttribute.FogEquation)) {
            str2 = str2 + "#define fogEquationFlag\n";
        }
        for (VertexAttribute vertexAttribute : renderable.meshPart.mesh.getVertexAttributes()) {
            if (vertexAttribute.usage == 2) {
                str2 = str2 + "#define color" + vertexAttribute.unit + "Flag\n";
            }
        }
        int iMax3 = 0;
        int iMax4 = 0;
        for (VertexAttribute vertexAttribute2 : renderable.meshPart.mesh.getVertexAttributes()) {
            if (vertexAttribute2.usage == 4) {
                throw new GdxRuntimeException("color packed attribute not supported");
            }
            if (vertexAttribute2.usage == 2) {
                iMax4 = Math.max(iMax4, vertexAttribute2.unit + 1);
            } else if ((vertexAttribute2.usage == 512 && vertexAttribute2.unit >= 8) || ((vertexAttribute2.usage == 1024 && vertexAttribute2.unit >= 8) || (vertexAttribute2.usage == 2048 && vertexAttribute2.unit >= 8))) {
                iMax3 = Math.max(iMax3, vertexAttribute2.unit + 1);
            } else if (vertexAttribute2.usage == 64) {
                iMax = Math.max(iMax, vertexAttribute2.unit + 1);
            }
        }
        PBRCommon.checkVertexAttributes(renderable);
        if (iMax > 8) {
            Gdx.f82app.error(TAG, "more than 8 bones influence attributes not supported: " + iMax + " found.");
        }
        if (iMax3 > 8) {
            Gdx.f82app.error(TAG, "more than 8 morph target attributes not supported: " + iMax3 + " found.");
        }
        if (iMax4 > pBRShaderConfig.numVertexColors) {
            Gdx.f82app.error(TAG, "more than " + pBRShaderConfig.numVertexColors + " color attributes not supported: " + iMax4 + " found.");
        }
        if (renderable.environment != null) {
            LightUtils.LightsInfo lightsInfo2 = lightsInfo;
            LightUtils.getLightsInfo(lightsInfo2, renderable.environment);
            if (lightsInfo2.dirLights > pBRShaderConfig.numDirectionalLights) {
                Gdx.f82app.error(TAG, "too many directional lights detected: " + lightsInfo2.dirLights + "/" + pBRShaderConfig.numDirectionalLights);
            }
            if (lightsInfo2.pointLights > pBRShaderConfig.numPointLights) {
                Gdx.f82app.error(TAG, "too many point lights detected: " + lightsInfo2.pointLights + "/" + pBRShaderConfig.numPointLights);
            }
            if (lightsInfo2.spotLights > pBRShaderConfig.numSpotLights) {
                Gdx.f82app.error(TAG, "too many spot lights detected: " + lightsInfo2.spotLights + "/" + pBRShaderConfig.numSpotLights);
            }
            if (lightsInfo2.miscLights > 0) {
                Gdx.f82app.error(TAG, "unknow type lights not supported.");
            }
        }
        PBRShader pBRShaderCreateShader = createShader(renderable, pBRShaderConfig, str2);
        checkShaderCompilation(pBRShaderCreateShader.program);
        if (pBRShaderCreateShader.canRender(renderable)) {
            return pBRShaderCreateShader;
        }
        throw new GdxRuntimeException("cannot render with this shader");
    }

    protected PBRShader createShader(Renderable renderable, PBRShaderConfig pBRShaderConfig, String str) {
        return new PBRShader(renderable, pBRShaderConfig, str);
    }

    protected void checkShaderCompilation(ShaderProgram shaderProgram) {
        String log = shaderProgram.getLog();
        if (shaderProgram.isCompiled()) {
            if (log.isEmpty()) {
                Gdx.f82app.debug(TAG, "Shader compilation success");
                return;
            } else {
                Gdx.f82app.error(TAG, "Shader compilation warnings:\n" + log);
                return;
            }
        }
        throw new GdxRuntimeException("Shader compilation failed:\n" + log);
    }
}
