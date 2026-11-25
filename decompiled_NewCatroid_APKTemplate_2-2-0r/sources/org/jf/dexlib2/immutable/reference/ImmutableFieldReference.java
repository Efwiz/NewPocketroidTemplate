package org.jf.dexlib2.immutable.reference;

import org.jf.dexlib2.base.reference.BaseFieldReference;
import org.jf.dexlib2.iface.reference.FieldReference;

/* JADX INFO: loaded from: classes6.dex */
public class ImmutableFieldReference extends BaseFieldReference implements ImmutableReference {
    protected final String definingClass;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    protected final String f1031name;
    protected final String type;

    public ImmutableFieldReference(String str, String str2, String str3) {
        this.definingClass = str;
        this.f1031name = str2;
        this.type = str3;
    }

    public static ImmutableFieldReference of(FieldReference fieldReference) {
        if (fieldReference instanceof ImmutableFieldReference) {
            return (ImmutableFieldReference) fieldReference;
        }
        return new ImmutableFieldReference(fieldReference.getDefiningClass(), fieldReference.getName(), fieldReference.getType());
    }

    @Override // org.jf.dexlib2.iface.reference.FieldReference, org.jf.dexlib2.iface.Field, org.jf.dexlib2.iface.Member
    public String getDefiningClass() {
        return this.definingClass;
    }

    @Override // org.jf.dexlib2.iface.reference.FieldReference, org.jf.dexlib2.iface.Field, org.jf.dexlib2.iface.Member
    public String getName() {
        return this.f1031name;
    }

    @Override // org.jf.dexlib2.iface.reference.FieldReference, org.jf.dexlib2.iface.Field
    public String getType() {
        return this.type;
    }
}
