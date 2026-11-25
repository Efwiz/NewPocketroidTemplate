package org.catrobat.catroid.raptor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.badlogic.gdx.graphics.g3d.utils.DefaultShaderProvider;
import net.mgsx.gltf.scene3d.attributes.PBRFloatAttribute;
import net.mgsx.gltf.scene3d.attributes.PBRTextureAttribute;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/MaterialShaderProvider.class
  classes14.dex
 */
/* JADX INFO: compiled from: ThreeDManager.java */
/* JADX INFO: loaded from: classes14.dex */
class MaterialShaderProvider extends DefaultShaderProvider {
    MaterialShaderProvider() {
    }

    @Override // com.badlogic.gdx.graphics.g3d.utils.DefaultShaderProvider, com.badlogic.gdx.graphics.g3d.utils.BaseShaderProvider
    protected Shader createShader(Renderable renderable) {
        String str;
        if (renderable.meshPart.mesh.getVertexAttribute(16) == null) {
            str = "";
        } else {
            str = "#define HAS_UV\n";
        }
        if (renderable.bones != null) {
            str = ((((str + "#define numBones 110\n") + "#define boneWeight0Flag\n") + "#define boneWeight1Flag\n") + "#define boneWeight2Flag\n") + "#define boneWeight3Flag\n";
        }
        DefaultShader.Config config = new DefaultShader.Config(str + "attribute vec3 a_position;\n#ifdef HAS_UV\nattribute vec2 a_texCoord0;\nvarying vec2 v_uv;\n#endif\nuniform mat4 u_projViewTrans;\nuniform mat4 u_worldTrans;\nvoid main() {\n#ifdef HAS_UV\n    v_uv = a_texCoord0;\n#endif\n    gl_Position = u_projViewTrans * u_worldTrans * vec4(a_position, 1.0);\n}", str + "#ifdef GL_ES\nprecision mediump float;\n#endif\n#ifdef HAS_UV\nvarying vec2 v_uv;\n#endif\nuniform float u_metallic;\nuniform int u_hasTexture;\nuniform sampler2D u_texture;\nvoid main() {\n    float m = u_metallic;\n#ifdef HAS_UV\n    if (u_hasTexture == 1) m *= texture2D(u_texture, v_uv).b;\n#endif\n    gl_FragColor = vec4(m, 0.0, 0.0, 1.0);\n}");
        config.numBones = 110;
        return new DefaultShader(renderable, config) { // from class: org.catrobat.catroid.raptor.MaterialShaderProvider.1
            private final int u_metallic = register("u_metallic");
            private final int u_hasTexture = register("u_hasTexture");
            private final int u_texture = register("u_texture");

            @Override // com.badlogic.gdx.graphics.g3d.shaders.BaseShader, com.badlogic.gdx.graphics.g3d.Shader
            public void render(Renderable renderable2) {
                int i;
                Texture texture;
                float f = renderable2.material.has(PBRFloatAttribute.Metallic) ? ((PBRFloatAttribute) renderable2.material.get(PBRFloatAttribute.Metallic)).value : 0.0f;
                if (renderable2.material.has(PBRTextureAttribute.MetallicRoughnessTexture)) {
                    texture = (Texture) ((PBRTextureAttribute) renderable2.material.get(PBRTextureAttribute.MetallicRoughnessTexture)).textureDescription.texture;
                    i = 1;
                } else {
                    i = 0;
                    texture = null;
                }
                set(this.u_metallic, f);
                set(this.u_hasTexture, i);
                if (i != 0 && texture != null) {
                    set(this.u_texture, texture);
                }
                super.render(renderable2);
            }
        };
    }
}
