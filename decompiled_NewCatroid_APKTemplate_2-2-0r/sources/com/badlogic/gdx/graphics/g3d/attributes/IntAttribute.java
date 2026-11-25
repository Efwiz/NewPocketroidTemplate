package com.badlogic.gdx.graphics.g3d.attributes;

import com.badlogic.gdx.graphics.g3d.Attribute;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/graphics/g3d/attributes/IntAttribute.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class IntAttribute extends Attribute {
    public int value;
    public static final String CullFaceAlias = "cullface";
    public static final long CullFace = register(CullFaceAlias);

    public static IntAttribute createCullFace(int i) {
        return new IntAttribute(CullFace, i);
    }

    public IntAttribute(long j) {
        super(j);
    }

    public IntAttribute(long j, int i) {
        super(j);
        this.value = i;
    }

    @Override // com.badlogic.gdx.graphics.g3d.Attribute
    public Attribute copy() {
        return new IntAttribute(this.type, this.value);
    }

    @Override // com.badlogic.gdx.graphics.g3d.Attribute
    public int hashCode() {
        return (super.hashCode() * 983) + this.value;
    }

    @Override // java.lang.Comparable
    public int compareTo(Attribute attribute) {
        return this.type != attribute.type ? (int) (this.type - attribute.type) : this.value - ((IntAttribute) attribute).value;
    }
}
