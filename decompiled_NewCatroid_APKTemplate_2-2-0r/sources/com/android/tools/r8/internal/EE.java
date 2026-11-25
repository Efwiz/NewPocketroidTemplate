package com.android.tools.r8.internal;

import com.android.tools.r8.references.ClassReference;
import java.util.Objects;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class EE extends GE {
    private final ClassReference a;
    private final String b;

    private EE(ClassReference classReference, String str) {
        this.a = classReference;
        this.b = str;
    }

    @Override // com.android.tools.r8.internal.GE
    final GE a(ClassReference classReference) {
        return new EE(classReference, this.b);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || EE.class != obj.getClass()) {
            return false;
        }
        EE ee = (EE) obj;
        return this.a.equals(ee.a) && this.b.equals(ee.b);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2249ie
    public final ClassReference getHolderClass() {
        return this.a;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2249ie
    public final String getName() {
        return this.b;
    }

    public final int hashCode() {
        return Objects.hash(this.a, this.b);
    }
}
