package org.catrobat.catroid.raptor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.badlogic.gdx.graphics.g3d.utils.BaseShaderProvider;
import java.util.Map;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/CustomShaderProvider.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class CustomShaderProvider extends BaseShaderProvider {
    private final DefaultShader.Config config;
    private final Map<String, Object> customUniforms;

    public CustomShaderProvider(DefaultShader.Config config, Map<String, Object> customUniforms) {
        this.config = config;
        this.customUniforms = customUniforms;
    }

    @Override // com.badlogic.gdx.graphics.g3d.utils.BaseShaderProvider
    protected Shader createShader(Renderable renderable) {
        Gdx.f82app.log("ShaderDebug", ">>> CustomShaderProvider is CREATING A SHADER! <<<");
        return new CustomShader(renderable, this.config, this.customUniforms);
    }

    @Override // com.badlogic.gdx.graphics.g3d.utils.BaseShaderProvider, com.badlogic.gdx.utils.Disposable
    public void dispose() {
        super.dispose();
    }
}
