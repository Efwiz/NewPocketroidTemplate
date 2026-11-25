package org.catrobat.catroid.raptor;

import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Vector4;
import java.util.Map;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/CustomShader.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class CustomShader extends DefaultShader {
    private final Map<String, Object> customUniforms;

    public CustomShader(Renderable renderable, DefaultShader.Config config, Map<String, Object> customUniforms) {
        super(renderable, config);
        this.customUniforms = customUniforms;
    }

    @Override // com.badlogic.gdx.graphics.g3d.shaders.BaseShader, com.badlogic.gdx.graphics.g3d.Shader
    public void render(Renderable renderable) {
        if (!this.customUniforms.isEmpty()) {
            for (Map.Entry<String, Object> entry : this.customUniforms.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (value instanceof Float) {
                    this.program.setUniformf(key, ((Float) value).floatValue());
                } else if (value instanceof Integer) {
                    this.program.setUniformi(key, ((Integer) value).intValue());
                } else if (value instanceof Vector2) {
                    this.program.setUniformf(key, (Vector2) value);
                } else if (value instanceof Vector3) {
                    this.program.setUniformf(key, (Vector3) value);
                } else if (value instanceof Vector4) {
                    this.program.setUniformf(key, (Vector4) value);
                } else if (value instanceof Matrix4) {
                    this.program.setUniformMatrix(key, (Matrix4) value);
                }
            }
        }
        super.render(renderable);
    }
}
