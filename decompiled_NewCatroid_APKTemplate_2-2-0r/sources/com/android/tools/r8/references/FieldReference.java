package com.android.tools.r8.references;

import java.util.Objects;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class FieldReference {
    static final /* synthetic */ boolean d = true;
    private final ClassReference a;
    private final String b;
    private final TypeReference c;

    FieldReference(ClassReference classReference, String str, TypeReference typeReference) {
        boolean z = d;
        if (!z && classReference == null) {
            throw new AssertionError();
        }
        if (!z && str == null) {
            throw new AssertionError();
        }
        if (!z && typeReference == null) {
            throw new AssertionError();
        }
        this.a = classReference;
        this.b = str;
        this.c = typeReference;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FieldReference)) {
            return false;
        }
        FieldReference fieldReference = (FieldReference) obj;
        return this.a.equals(fieldReference.a) && this.b.equals(fieldReference.b) && this.c.equals(fieldReference.c);
    }

    public String getFieldName() {
        return this.b;
    }

    public TypeReference getFieldType() {
        return this.c;
    }

    public ClassReference getHolderClass() {
        return this.a;
    }

    public int hashCode() {
        return Objects.hash(this.a, this.b, this.c);
    }

    public String toSourceString() {
        return getFieldType().getTypeName() + " " + getHolderClass().getTypeName() + "." + getFieldName();
    }

    public String toString() {
        return getHolderClass() + getFieldName() + ":" + getFieldType().getDescriptor();
    }
}
