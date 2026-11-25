package com.android.tools.r8.ir.optimize;

import com.android.tools.r8.internal.EnumC2523mE;
import com.android.tools.r8.internal.S10;
import java.util.Objects;

/* JADX INFO: renamed from: com.android.tools.r8.ir.optimize.u0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3589u0 extends AbstractC3584s0 {
    private final S10 c;

    private C3589u0(S10 s10, S10 s11, EnumC2523mE enumC2523mE) {
        super(s10, enumC2523mE);
        this.c = s11;
    }

    @Override // com.android.tools.r8.ir.optimize.AbstractC3584s0
    public final boolean a(int i) {
        return true;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || C3589u0.class != obj.getClass()) {
            return false;
        }
        C3589u0 c3589u0 = (C3589u0) obj;
        return this.c == c3589u0.c && this.a == c3589u0.a && this.b == c3589u0.b;
    }

    public final int hashCode() {
        return Objects.hash(this.a, this.c, this.b);
    }
}
