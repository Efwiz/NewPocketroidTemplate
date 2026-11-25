package net.mgsx.gltf.scene3d.attributes;

import com.badlogic.gdx.graphics.g3d.Attribute;

/* JADX INFO: loaded from: classes12.dex */
public class PBRHDRColorAttribute extends Attribute {
    public float b;
    public float g;
    public float r;
    public static final String SpecularAlias = "specularColorHDR";
    public static final long Specular = register(SpecularAlias);

    public PBRHDRColorAttribute(long j, float f, float f2, float f3) {
        super(j);
        this.r = f;
        this.g = f2;
        this.b = f3;
    }

    public PBRHDRColorAttribute set(float f, float f2, float f3) {
        this.r = f;
        this.g = f2;
        this.b = f3;
        return this;
    }

    @Override // com.badlogic.gdx.graphics.g3d.Attribute
    public Attribute copy() {
        return new PBRHDRColorAttribute(this.type, this.r, this.g, this.b);
    }

    @Override // java.lang.Comparable
    public int compareTo(Attribute attribute) {
        if (this.type != attribute.type) {
            return (int) (this.type - attribute.type);
        }
        PBRHDRColorAttribute pBRHDRColorAttribute = (PBRHDRColorAttribute) attribute;
        int iCompare = Float.compare(this.r, pBRHDRColorAttribute.r);
        if (iCompare != 0) {
            return iCompare;
        }
        int iCompare2 = Float.compare(this.g, pBRHDRColorAttribute.g);
        if (iCompare2 != 0) {
            return iCompare2;
        }
        int iCompare3 = Float.compare(this.b, pBRHDRColorAttribute.b);
        if (iCompare3 != 0) {
            return iCompare3;
        }
        return 0;
    }
}
