package net.mgsx.gltf.scene3d.attributes;

import com.badlogic.gdx.graphics.g3d.Attribute;
import com.badlogic.gdx.math.MathUtils;

/* JADX INFO: loaded from: classes12.dex */
public class PBRIridescenceAttribute extends Attribute {
    public static final String Alias = "iridescence";
    public static final long Type = register(Alias);
    public float factor;
    public float ior;
    public float thicknessMax;
    public float thicknessMin;

    public PBRIridescenceAttribute() {
        super(Type);
        this.factor = 1.0f;
        this.ior = 1.3f;
        this.thicknessMin = 100.0f;
        this.thicknessMax = 400.0f;
    }

    public PBRIridescenceAttribute(float f, float f2, float f3, float f4) {
        super(Type);
        this.factor = f;
        this.ior = f2;
        this.thicknessMin = f3;
        this.thicknessMax = f4;
    }

    @Override // java.lang.Comparable
    public int compareTo(Attribute attribute) {
        if (this.type != attribute.type) {
            return this.type < attribute.type ? -1 : 1;
        }
        PBRIridescenceAttribute pBRIridescenceAttribute = (PBRIridescenceAttribute) attribute;
        if (!MathUtils.isEqual(this.factor, pBRIridescenceAttribute.factor)) {
            return this.factor < pBRIridescenceAttribute.factor ? -1 : 1;
        }
        if (!MathUtils.isEqual(this.ior, pBRIridescenceAttribute.ior)) {
            return this.ior < pBRIridescenceAttribute.ior ? -1 : 1;
        }
        if (!MathUtils.isEqual(this.thicknessMin, pBRIridescenceAttribute.thicknessMin)) {
            return this.thicknessMin < pBRIridescenceAttribute.thicknessMin ? -1 : 1;
        }
        if (MathUtils.isEqual(this.thicknessMax, pBRIridescenceAttribute.thicknessMax)) {
            return 0;
        }
        return this.thicknessMax < pBRIridescenceAttribute.thicknessMax ? -1 : 1;
    }

    @Override // com.badlogic.gdx.graphics.g3d.Attribute
    public Attribute copy() {
        return new PBRIridescenceAttribute(this.factor, this.ior, this.thicknessMin, this.thicknessMax);
    }
}
