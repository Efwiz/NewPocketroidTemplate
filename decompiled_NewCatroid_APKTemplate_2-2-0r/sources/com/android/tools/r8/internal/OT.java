package com.android.tools.r8.internal;

import com.android.tools.r8.references.ClassReference;
import com.android.tools.r8.retrace.RetracedClassReference;
import com.android.tools.r8.retrace.RetracedTypeReference;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class OT implements RetracedClassReference {
    static final /* synthetic */ boolean b = true;
    private final ClassReference a;

    private OT(ClassReference classReference) {
        if (!b && classReference == null) {
            throw new AssertionError();
        }
        this.a = classReference;
    }

    public static OT a(ClassReference classReference) {
        return new OT(classReference);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || OT.class != obj.getClass()) {
            return false;
        }
        return this.a.equals(((OT) obj).a);
    }

    @Override // com.android.tools.r8.retrace.RetracedClassReference
    public final String getBinaryName() {
        return this.a.getBinaryName();
    }

    @Override // com.android.tools.r8.retrace.RetracedClassReference
    public final ClassReference getClassReference() {
        return this.a;
    }

    @Override // com.android.tools.r8.retrace.RetracedClassReference
    public final String getDescriptor() {
        return this.a.getDescriptor();
    }

    @Override // com.android.tools.r8.retrace.RetracedClassReference
    public final RetracedTypeReference getRetracedType() {
        return XT.a(this.a);
    }

    @Override // com.android.tools.r8.retrace.RetracedClassReference
    public final String getTypeName() {
        return this.a.getTypeName();
    }

    public final int hashCode() {
        return this.a.hashCode();
    }
}
