package com.android.tools.r8.internal;

import com.android.tools.r8.references.ClassReference;
import com.android.tools.r8.references.FieldReference;
import com.android.tools.r8.references.Reference;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Hh, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1043Hh extends AbstractC1070Ih {
    private final FieldReference a;

    private C1043Hh(FieldReference fieldReference) {
        this.a = fieldReference;
    }

    @Override // com.android.tools.r8.internal.AbstractC1070Ih
    public final C1043Hh a() {
        return this;
    }

    public final FieldReference b() {
        return this.a;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || C1043Hh.class != obj.getClass()) {
            return false;
        }
        return this.a.equals(((C1043Hh) obj).a);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2249ie
    public final ClassReference getHolderClass() {
        return this.a.getHolderClass();
    }

    @Override // com.android.tools.r8.internal.InterfaceC2249ie
    public final String getName() {
        return this.a.getFieldName();
    }

    public final int hashCode() {
        return this.a.hashCode();
    }

    @Override // com.android.tools.r8.internal.AbstractC1070Ih
    final AbstractC1070Ih a(ClassReference classReference) {
        return new C1043Hh(Reference.field(classReference, this.a.getFieldName(), this.a.getFieldType()));
    }
}
