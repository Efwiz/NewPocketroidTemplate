package net.mgsx.gltf.scene3d.shaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.shaders.DepthShader;
import com.badlogic.gdx.graphics.g3d.utils.DepthShaderProvider;

/* JADX INFO: loaded from: classes12.dex */
public class PBRDepthShaderProvider extends DepthShaderProvider {
    private static String defaultFragmentShader;
    private static String defaultVertexShader;

    public static String getDefaultVertexShader() {
        if (defaultVertexShader == null) {
            defaultVertexShader = Gdx.files.classpath("net/mgsx/gltf/shaders/depth.vs.glsl").readString();
        }
        return defaultVertexShader;
    }

    public static String getDefaultFragmentShader() {
        if (defaultFragmentShader == null) {
            defaultFragmentShader = Gdx.files.classpath("net/mgsx/gltf/shaders/depth.fs.glsl").readString();
        }
        return defaultFragmentShader;
    }

    public static DepthShader.Config createDefaultConfig() {
        DepthShader.Config config = new DepthShader.Config();
        config.vertexShader = getDefaultVertexShader();
        config.fragmentShader = getDefaultFragmentShader();
        return config;
    }

    public PBRDepthShaderProvider(DepthShader.Config config) {
        super(config == null ? new DepthShader.Config() : config);
        if (config.vertexShader == null) {
            config.vertexShader = getDefaultVertexShader();
        }
        if (config.fragmentShader == null) {
            config.fragmentShader = getDefaultFragmentShader();
        }
    }

    protected String morphTargetsPrefix(Renderable renderable) {
        String str = "";
        for (VertexAttribute vertexAttribute : renderable.meshPart.mesh.getVertexAttributes()) {
            for (int i = 0; i < 8; i++) {
                if (vertexAttribute.usage == 512 && vertexAttribute.unit == i) {
                    str = str + "#define position" + i + "Flag\n";
                }
            }
        }
        return str;
    }

    @Override // com.badlogic.gdx.graphics.g3d.utils.DepthShaderProvider, com.badlogic.gdx.graphics.g3d.utils.BaseShaderProvider
    protected Shader createShader(Renderable renderable) {
        PBRCommon.checkVertexAttributes(renderable);
        return new PBRDepthShader(renderable, this.config, DepthShader.createPrefix(renderable, this.config) + morphTargetsPrefix(renderable));
    }
}
