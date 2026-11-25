package org.catrobat.catroid.raptor.postprocessing;

import com.badlogic.gdx.graphics.g3d.Attribute;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import java.util.HashMap;
import java.util.Map;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/raptor/postprocessing/CustomShaderAttribute.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class CustomShaderAttribute extends Attribute {
    public static final String Alias = "CustomShader";
    public static final long Type = register(Alias);
    public ShaderProgram program;
    public final Map<String, Object> uniforms;

    public CustomShaderAttribute(ShaderProgram program) {
        super(Type);
        this.uniforms = new HashMap();
        this.program = program;
    }

    @Override // com.badlogic.gdx.graphics.g3d.Attribute
    public Attribute copy() {
        CustomShaderAttribute customShaderAttribute = new CustomShaderAttribute(this.program);
        customShaderAttribute.uniforms.putAll(this.uniforms);
        return customShaderAttribute;
    }

    @Override // java.lang.Comparable
    public int compareTo(Attribute other) {
        if (this.type != other.type) {
            return (int) (this.type - other.type);
        }
        return 0;
    }
}
