package com.badlogic.gdx.graphics.g3d.shaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.Attributes;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.GdxRuntimeException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/shaders/DepthShader.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class DepthShader extends DefaultShader {
    private static String defaultFragmentShader;
    private static String defaultVertexShader;
    private static final Attributes tmpAttributes = new Attributes();
    private final FloatAttribute alphaTestAttribute;
    public final int numBones;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/shaders/DepthShader$Config.class
      classes8.dex
     */
    public static class Config extends DefaultShader.Config {
        public float defaultAlphaTest;
        public boolean depthBufferOnly;

        public Config() {
            this.depthBufferOnly = false;
            this.defaultAlphaTest = 0.5f;
            this.defaultCullFace = 1028;
        }

        public Config(String str, String str2) {
            super(str, str2);
            this.depthBufferOnly = false;
            this.defaultAlphaTest = 0.5f;
        }
    }

    public static final String getDefaultVertexShader() {
        if (defaultVertexShader == null) {
            defaultVertexShader = Gdx.files.classpath("com/badlogic/gdx/graphics/g3d/shaders/depth.vertex.glsl").readString();
        }
        return defaultVertexShader;
    }

    public static final String getDefaultFragmentShader() {
        if (defaultFragmentShader == null) {
            defaultFragmentShader = Gdx.files.classpath("com/badlogic/gdx/graphics/g3d/shaders/depth.fragment.glsl").readString();
        }
        return defaultFragmentShader;
    }

    public static String createPrefix(Renderable renderable, Config config) {
        String strCreatePrefix = DefaultShader.createPrefix(renderable, config);
        return !config.depthBufferOnly ? strCreatePrefix + "#define PackedDepthFlag\n" : strCreatePrefix;
    }

    public DepthShader(Renderable renderable) {
        this(renderable, new Config());
    }

    public DepthShader(Renderable renderable, Config config) {
        this(renderable, config, createPrefix(renderable, config));
    }

    public DepthShader(Renderable renderable, Config config, String str) {
        this(renderable, config, str, config.vertexShader != null ? config.vertexShader : getDefaultVertexShader(), config.fragmentShader != null ? config.fragmentShader : getDefaultFragmentShader());
    }

    public DepthShader(Renderable renderable, Config config, String str, String str2, String str3) {
        this(renderable, config, new ShaderProgram(str + str2, str + str3));
    }

    public DepthShader(Renderable renderable, Config config, ShaderProgram shaderProgram) {
        super(renderable, config, shaderProgram);
        combineAttributes(renderable);
        if (renderable.bones != null && renderable.bones.length > config.numBones) {
            throw new GdxRuntimeException("too many bones: " + renderable.bones.length + ", max configured: " + config.numBones);
        }
        this.numBones = renderable.bones == null ? 0 : config.numBones;
        int boneWeights = renderable.meshPart.mesh.getVertexAttributes().getBoneWeights();
        if (boneWeights > config.numBoneWeights) {
            throw new GdxRuntimeException("too many bone weights: " + boneWeights + ", max configured: " + config.numBoneWeights);
        }
        this.alphaTestAttribute = new FloatAttribute(FloatAttribute.AlphaTest, config.defaultAlphaTest);
    }

    @Override // com.badlogic.gdx.graphics.g3d.shaders.DefaultShader, com.badlogic.gdx.graphics.g3d.shaders.BaseShader, com.badlogic.gdx.graphics.g3d.Shader
    public void begin(Camera camera, RenderContext renderContext) {
        super.begin(camera, renderContext);
    }

    @Override // com.badlogic.gdx.graphics.g3d.shaders.DefaultShader, com.badlogic.gdx.graphics.g3d.shaders.BaseShader, com.badlogic.gdx.graphics.g3d.Shader
    public void end() {
        super.end();
    }

    @Override // com.badlogic.gdx.graphics.g3d.shaders.DefaultShader, com.badlogic.gdx.graphics.g3d.Shader
    public boolean canRender(Renderable renderable) {
        if (renderable.bones != null && (renderable.bones.length > this.config.numBones || renderable.meshPart.mesh.getVertexAttributes().getBoneWeights() > this.config.numBoneWeights)) {
            return false;
        }
        Attributes attributesCombineAttributes = combineAttributes(renderable);
        if (((this.attributesMask & BlendingAttribute.Type) == BlendingAttribute.Type && (this.attributesMask & TextureAttribute.Diffuse) == TextureAttribute.Diffuse) != (attributesCombineAttributes.has(BlendingAttribute.Type) && attributesCombineAttributes.has(TextureAttribute.Diffuse))) {
            return false;
        }
        return (renderable.bones != null) == (this.numBones > 0);
    }

    @Override // com.badlogic.gdx.graphics.g3d.shaders.DefaultShader, com.badlogic.gdx.graphics.g3d.shaders.BaseShader
    public void render(Renderable renderable, Attributes attributes) {
        if (attributes.has(BlendingAttribute.Type)) {
            BlendingAttribute blendingAttribute = (BlendingAttribute) attributes.get(BlendingAttribute.Type);
            attributes.remove(BlendingAttribute.Type);
            boolean zHas = attributes.has(FloatAttribute.AlphaTest);
            if (!zHas) {
                attributes.set(this.alphaTestAttribute);
            }
            if (blendingAttribute.opacity >= ((FloatAttribute) attributes.get(FloatAttribute.AlphaTest)).value) {
                super.render(renderable, attributes);
            }
            if (!zHas) {
                attributes.remove(FloatAttribute.AlphaTest);
            }
            attributes.set(blendingAttribute);
            return;
        }
        super.render(renderable, attributes);
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
}
