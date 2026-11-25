package org.catrobat.catroid.raptor.postprocessing;

import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import net.mgsx.gltf.scene3d.shaders.PBRShaderConfig;
import net.mgsx.gltf.scene3d.shaders.PBRShaderProvider;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/postprocessing/UniversalPBRShaderProvider.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class UniversalPBRShaderProvider extends PBRShaderProvider {
    public UniversalPBRShaderProvider(PBRShaderConfig config) {
        super(config);
    }

    @Override // net.mgsx.gltf.scene3d.shaders.PBRShaderProvider, com.badlogic.gdx.graphics.g3d.utils.DefaultShaderProvider, com.badlogic.gdx.graphics.g3d.utils.BaseShaderProvider
    protected Shader createShader(Renderable renderable) {
        if (renderable.material.has(CustomShaderAttribute.Type)) {
            return new CustomObjectShader(renderable, (CustomShaderAttribute) renderable.material.get(CustomShaderAttribute.Type));
        }
        return super.createShader(renderable);
    }
}
