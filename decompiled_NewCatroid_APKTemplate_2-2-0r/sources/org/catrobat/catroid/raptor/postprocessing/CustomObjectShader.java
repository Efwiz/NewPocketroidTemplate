package org.catrobat.catroid.raptor.postprocessing;

import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import java.util.Map;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/postprocessing/CustomObjectShader.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class CustomObjectShader extends DefaultShader {
    private final CustomShaderAttribute attribute;

    public CustomObjectShader(Renderable renderable, CustomShaderAttribute attribute) {
        super(renderable, new DefaultShader.Config(), attribute.program);
        this.attribute = attribute;
    }

    @Override // com.badlogic.gdx.graphics.g3d.shaders.BaseShader, com.badlogic.gdx.graphics.g3d.Shader
    public void render(Renderable renderable) {
        int uniformLocation = this.program.getUniformLocation("u_time");
        if (uniformLocation != -1) {
            this.program.setUniformf(uniformLocation, (float) ((System.currentTimeMillis() % 100000) / 1000.0d));
        }
        int uniformLocation2 = this.program.getUniformLocation("u_customCameraPosition");
        if (uniformLocation2 != -1 && this.camera != null) {
            this.program.setUniformf(uniformLocation2, this.camera.position.x, this.camera.position.y, this.camera.position.z);
        }
        for (Map.Entry<String, Object> entry : this.attribute.uniforms.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            int uniformLocation3 = this.program.getUniformLocation(key);
            if (uniformLocation3 != -1) {
                if (value instanceof Float) {
                    this.program.setUniformf(uniformLocation3, ((Float) value).floatValue());
                } else if (value instanceof Vector2) {
                    Vector2 vector2 = (Vector2) value;
                    this.program.setUniformf(uniformLocation3, vector2.x, vector2.y);
                } else if (value instanceof Vector3) {
                    Vector3 vector3 = (Vector3) value;
                    this.program.setUniformf(uniformLocation3, vector3.x, vector3.y, vector3.z);
                }
            }
        }
        super.render(renderable);
    }
}
