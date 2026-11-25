package com.android.tools.r8.internal;

import com.android.tools.r8.references.ClassReference;
import com.android.tools.r8.references.MethodReference;
import com.android.tools.r8.references.Reference;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class FE extends GE {
    private final MethodReference a;

    private FE(MethodReference methodReference) {
        this.a = methodReference;
    }

    @Override // com.android.tools.r8.internal.GE
    final FE a() {
        return this;
    }

    final MethodReference b() {
        return this.a;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || FE.class != obj.getClass()) {
            return false;
        }
        return this.a.equals(((FE) obj).a);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2249ie
    public final ClassReference getHolderClass() {
        return this.a.getHolderClass();
    }

    @Override // com.android.tools.r8.internal.InterfaceC2249ie
    public final String getName() {
        return this.a.getMethodName();
    }

    public final int hashCode() {
        return this.a.hashCode();
    }

    @Override // com.android.tools.r8.internal.GE
    final GE a(ClassReference classReference) {
        return new FE(Reference.method(classReference, this.a.getMethodName(), this.a.getFormalTypes(), this.a.getReturnType()));
    }
}
