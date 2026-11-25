package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.o5;
import java.util.Objects;

/* JADX INFO: renamed from: com.android.tools.r8.shaking.w0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3817w0 extends AbstractC3805u0 {
    C3817w0(com.android.tools.r8.graph.Y0 y0, o5 o5Var, I i) {
        super(y0, o5Var, i);
    }

    @Override // com.android.tools.r8.shaking.AbstractC3709e0
    public final void a(L l) {
        l.b(this.a, this.b, this.c);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || C3817w0.class != obj.getClass()) {
            return false;
        }
        return a((C3817w0) obj);
    }

    @Override // com.android.tools.r8.shaking.AbstractC3805u0
    public final int hashCode() {
        return Objects.hash(this.a, this.b.getReference(), this.c);
    }
}
