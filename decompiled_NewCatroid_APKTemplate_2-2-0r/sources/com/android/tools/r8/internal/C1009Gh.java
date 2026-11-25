package com.android.tools.r8.internal;

import com.android.tools.r8.references.ClassReference;
import java.util.Objects;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Gh, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1009Gh extends AbstractC1070Ih {
    private final ClassReference a;
    private final String b;

    private C1009Gh(ClassReference classReference, String str) {
        this.a = classReference;
        this.b = str;
    }

    @Override // com.android.tools.r8.internal.AbstractC1070Ih
    final AbstractC1070Ih a(ClassReference classReference) {
        return new C1009Gh(classReference, this.b);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || C1009Gh.class != obj.getClass()) {
            return false;
        }
        C1009Gh c1009Gh = (C1009Gh) obj;
        return this.a.equals(c1009Gh.a) && this.b.equals(c1009Gh.b);
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
