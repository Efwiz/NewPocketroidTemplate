package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0737r2;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import java.util.Objects;

/* JADX INFO: renamed from: com.android.tools.r8.internal.ne, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2630ne implements NV {
    private final C0684j2 a;
    private final EnumC2687oD b;

    public C2630ne(C0684j2 c0684j2, EnumC2687oD enumC2687oD) {
        this.b = enumC2687oD;
        this.a = c0684j2;
    }

    public final com.android.tools.r8.synthesis.L.b a(C0702m c0702m) {
        com.android.tools.r8.synthesis.L lB = c0702m.D().b();
        EnumC2687oD enumC2687oD = this.b;
        if (enumC2687oD != null) {
            int iOrdinal = enumC2687oD.ordinal();
            if (iOrdinal == 0) {
                return lB.n;
            }
            if (iOrdinal == 1) {
                return lB.m;
            }
            if (iOrdinal == 2) {
                return lB.k;
            }
            if (iOrdinal == 3) {
                return lB.l;
            }
        }
        return null;
    }

    public final EnumC2687oD b() {
        return this.b;
    }

    public final C0684j2 c() {
        return this.a;
    }

    public final C0759u2 d() {
        return this.a.C0();
    }

    public final C0737r2 e() {
        return this.a.H0();
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof C2630ne)) {
            return false;
        }
        C2630ne c2630ne = (C2630ne) obj;
        return this.a == c2630ne.a && Objects.equals(this.b, c2630ne.b);
    }

    public final int hashCode() {
        return Objects.hash(this.a, this.b);
    }

    public final C0767v2 a() {
        return this.a.B0();
    }

    @Override // com.android.tools.r8.internal.NV
    public final Object[] a(NF nf) {
        return nf.a(this);
    }
}
