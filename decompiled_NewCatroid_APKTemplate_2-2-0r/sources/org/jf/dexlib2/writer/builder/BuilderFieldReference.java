package org.jf.dexlib2.writer.builder;

import org.jf.dexlib2.base.reference.BaseFieldReference;

/* JADX INFO: loaded from: classes6.dex */
public class BuilderFieldReference extends BaseFieldReference implements BuilderReference {
    final BuilderTypeReference definingClass;
    final BuilderTypeReference fieldType;
    int index = -1;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    final BuilderStringReference f1035name;

    BuilderFieldReference(BuilderTypeReference builderTypeReference, BuilderStringReference builderStringReference, BuilderTypeReference builderTypeReference2) {
        this.definingClass = builderTypeReference;
        this.f1035name = builderStringReference;
        this.fieldType = builderTypeReference2;
    }

    @Override // org.jf.dexlib2.iface.reference.FieldReference, org.jf.dexlib2.iface.Field, org.jf.dexlib2.iface.Member
    public String getDefiningClass() {
        return this.definingClass.getType();
    }

    @Override // org.jf.dexlib2.iface.reference.FieldReference, org.jf.dexlib2.iface.Field, org.jf.dexlib2.iface.Member
    public String getName() {
        return this.f1035name.getString();
    }

    @Override // org.jf.dexlib2.iface.reference.FieldReference, org.jf.dexlib2.iface.Field
    public String getType() {
        return this.fieldType.getType();
    }

    @Override // org.jf.dexlib2.writer.builder.BuilderReference
    public int getIndex() {
        return this.index;
    }

    @Override // org.jf.dexlib2.writer.builder.BuilderReference
    public void setIndex(int i) {
        this.index = i;
    }
}
