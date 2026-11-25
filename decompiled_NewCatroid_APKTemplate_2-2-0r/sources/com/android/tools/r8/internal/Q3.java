package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import java.util.Objects;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class Q3 {
    private static final Q3 d;
    static final /* synthetic */ boolean e = true;
    private final AbstractC1154Lf a;
    private final B1 b;
    private final boolean c;

    private Q3(AbstractC1154Lf abstractC1154Lf, B1 b1, boolean z) {
        this.a = abstractC1154Lf;
        this.b = b1;
        this.c = z;
    }

    public static P3 a() {
        return new P3();
    }

    public static Q3 b() {
        return d;
    }

    public final AbstractC1154Lf c() {
        return this.a;
    }

    public final B1 d() {
        return this.b;
    }

    public final boolean e() {
        if (this == d) {
            return true;
        }
        if (e || !this.a.m() || !this.b.isUnknown() || this.c) {
            return false;
        }
        throw new AssertionError();
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || Q3.class != obj.getClass()) {
            return false;
        }
        Q3 q3 = (Q3) obj;
        return this.b.equals(q3.b) && this.a.equals(q3.a) && this.c == q3.c;
    }

    public final boolean f() {
        return this.c;
    }

    public final int hashCode() {
        return Objects.hash(this.b, this.a, Boolean.valueOf(this.c));
    }

    public final Q3 a(Q3 q3) {
        AbstractC1154Lf abstractC1154LfN = this.a;
        AbstractC1154Lf abstractC1154Lf = q3.a;
        if (!abstractC1154LfN.equals(abstractC1154Lf)) {
            if (abstractC1154LfN.m()) {
                abstractC1154LfN = abstractC1154Lf;
            } else if (!abstractC1154Lf.m()) {
                abstractC1154LfN = AbstractC1154Lf.n();
            }
        }
        B1 b1F = this.b;
        B1 b1 = q3.b;
        if (!b1F.equals(b1)) {
            if (b1F.isUnknown()) {
                b1F = b1;
            } else if (!b1.isUnknown()) {
                b1F = C2892r10.F();
            }
        }
        boolean z = this.c || q3.c;
        return (abstractC1154LfN.m() && b1F.isUnknown() && !z) ? d : new Q3(abstractC1154LfN, b1F, z);
    }

    static {
        C1207Nf c1207NfN = AbstractC1154Lf.n();
        int i = B1.b;
        d = new Q3(c1207NfN, C2892r10.F(), false);
    }

    public final Q3 a(C0702m c0702m, AbstractC1476Wj abstractC1476Wj) {
        com.android.tools.r8.graph.Y0 y0F;
        com.android.tools.r8.graph.Y0 y0B;
        if (!e && !this.a.k() && !this.a.m()) {
            throw new AssertionError();
        }
        if (!this.b.B() || (y0B = abstractC1476Wj.b((AbstractC1476Wj) null, (y0F = this.b.p().F()))) == y0F) {
            return this;
        }
        C1 c1B = c0702m.b();
        C2096gg c2096ggF = C2096gg.f();
        c1B.getClass();
        AbstractC2706oV abstractC2706oVA = C1.a(y0B, c2096ggF);
        AbstractC1154Lf abstractC1154Lf = this.a;
        boolean z = this.c;
        if (abstractC1154Lf.m() && abstractC2706oVA.isUnknown() && !z) {
            return d;
        }
        return new Q3(abstractC1154Lf, abstractC2706oVA, z);
    }

    public final Q3 a(com.android.tools.r8.graph.u5 u5Var) {
        if (!e && !this.a.k() && !this.a.m()) {
            throw new AssertionError();
        }
        if (!this.b.B() || !u5Var.a(this.b.p().F())) {
            return this;
        }
        AbstractC1154Lf abstractC1154Lf = this.a;
        C2892r10 c2892r10F = C2892r10.F();
        boolean z = this.c;
        if (abstractC1154Lf.m() && !z) {
            return d;
        }
        return new Q3(abstractC1154Lf, c2892r10F, z);
    }
}
