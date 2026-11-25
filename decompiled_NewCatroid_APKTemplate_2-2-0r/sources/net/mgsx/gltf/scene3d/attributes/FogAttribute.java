package net.mgsx.gltf.scene3d.attributes;

import com.badlogic.gdx.graphics.g3d.Attribute;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

/* JADX INFO: loaded from: classes12.dex */
public class FogAttribute extends Attribute {
    public final Vector3 value;
    public static final String FogEquationAlias = "fogEquation";
    public static final long FogEquation = register(FogEquationAlias);

    public static FogAttribute createFog(float f, float f2, float f3) {
        return new FogAttribute(FogEquation).set(f, f2, f3);
    }

    public FogAttribute(long j) {
        super(j);
        this.value = new Vector3();
    }

    public Attribute set(Vector3 vector3) {
        this.value.set(vector3);
        return this;
    }

    public FogAttribute set(float f, float f2, float f3) {
        this.value.set(f, f2, f3);
        return this;
    }

    @Override // com.badlogic.gdx.graphics.g3d.Attribute
    public Attribute copy() {
        return new FogAttribute(this.type).set(this.value);
    }

    @Override // java.lang.Comparable
    public int compareTo(Attribute attribute) {
        if (this.type != attribute.type) {
            return this.type < attribute.type ? -1 : 1;
        }
        FogAttribute fogAttribute = (FogAttribute) attribute;
        if (!MathUtils.isEqual(this.value.x, fogAttribute.value.x)) {
            return this.value.x < fogAttribute.value.x ? -1 : 1;
        }
        if (!MathUtils.isEqual(this.value.y, fogAttribute.value.y)) {
            return this.value.y < fogAttribute.value.y ? -1 : 1;
        }
        if (MathUtils.isEqual(this.value.z, fogAttribute.value.z)) {
            return 0;
        }
        return this.value.z < fogAttribute.value.z ? -1 : 1;
    }
}
