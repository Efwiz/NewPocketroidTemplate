package net.mgsx.gltf.scene3d.shaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.utils.GdxRuntimeException;
import net.mgsx.gltf.scene3d.attributes.PBRColorAttribute;

/* JADX INFO: loaded from: classes12.dex */
public class PBREmissiveShaderProvider extends PBRShaderProvider {
    public PBREmissiveShaderProvider(PBRShaderConfig pBRShaderConfig) {
        super(pBRShaderConfig);
    }

    @Override // net.mgsx.gltf.scene3d.shaders.PBRShaderProvider, com.badlogic.gdx.graphics.g3d.utils.DefaultShaderProvider, com.badlogic.gdx.graphics.g3d.utils.BaseShaderProvider
    protected Shader createShader(Renderable renderable) {
        TextureAttribute textureAttribute;
        PBRShaderConfig pBRShaderConfig = (PBRShaderConfig) this.config;
        int iMax = 0;
        boolean z = renderable.material.has(BlendingAttribute.Type) || renderable.material.has(FloatAttribute.AlphaTest);
        String str = (createPrefixBase(renderable, pBRShaderConfig) + morphTargetsPrefix(renderable)) + createPrefixSRGB(renderable, pBRShaderConfig);
        if (renderable.material.has(PBRColorAttribute.BaseColorFactor)) {
            str = str + "#define baseColorFactorFlag\n";
        }
        TextureAttribute textureAttribute2 = (TextureAttribute) renderable.material.get(TextureAttribute.class, TextureAttribute.Emissive);
        if (textureAttribute2 != null) {
            str = str + "#define v_emissiveUV v_texCoord" + textureAttribute2.uvIndex + "\n";
            iMax = Math.max(0, textureAttribute2.uvIndex);
        }
        if (z && (textureAttribute = (TextureAttribute) renderable.material.get(TextureAttribute.class, TextureAttribute.Diffuse)) != null) {
            str = str + "#define v_diffuseUV v_texCoord" + textureAttribute.uvIndex + "\n";
            iMax = Math.max(iMax, textureAttribute.uvIndex);
        }
        if (iMax >= 0) {
            str = str + "#define textureFlag\n";
        }
        if (iMax == 1) {
            str = str + "#define textureCoord1Flag\n";
        } else if (iMax > 1) {
            throw new GdxRuntimeException("more than 2 texture coordinates attribute not supported");
        }
        PBRShader pBRShader = new PBRShader(renderable, pBRShaderConfig, str);
        checkShaderCompilation(pBRShader.program);
        if (pBRShader.canRender(renderable)) {
            return pBRShader;
        }
        throw new GdxRuntimeException("cannot render with this shader");
    }

    public static PBRShaderConfig createConfig(int i) {
        PBRShaderConfig pBRShaderConfigCreateDefaultConfig = PBRShaderProvider.createDefaultConfig();
        pBRShaderConfigCreateDefaultConfig.numBones = i;
        pBRShaderConfigCreateDefaultConfig.vertexShader = Gdx.files.classpath("net/mgsx/gltf/shaders/gdx-pbr.vs.glsl").readString();
        pBRShaderConfigCreateDefaultConfig.fragmentShader = Gdx.files.classpath("net/mgsx/gltf/shaders/emissive-only.fs.glsl").readString();
        return pBRShaderConfigCreateDefaultConfig;
    }
}
