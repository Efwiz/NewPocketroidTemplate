package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.internal.AbstractC1154Lf;
import com.android.tools.r8.internal.AbstractC3121uK;
import com.android.tools.r8.internal.C2096gg;
import com.android.tools.r8.internal.C3405y10;
import com.android.tools.r8.internal.OH;
import com.android.tools.r8.internal.WH;

/* JADX INFO: renamed from: com.android.tools.r8.shaking.q3, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C3786q3 {
    static final /* synthetic */ boolean g = true;
    private final EnumC3781p3 a;
    private final boolean b;
    private final com.android.tools.r8.utils.W1 c;
    private final C0767v2 d;
    private final C0759u2 e;
    private final WH f;

    C3786q3(boolean z) {
        this.a = EnumC3781p3.a;
        this.b = z;
        this.c = null;
        this.d = null;
        this.e = null;
        this.f = null;
    }

    private static WH a(com.android.tools.r8.utils.W1 w1) {
        if (w1.d() && w1.c() == 0) {
            return WH.c();
        }
        return !w1.a(0L) ? WH.b() : WH.i();
    }

    public C0767v2 b() {
        if (g || g()) {
            return this.d;
        }
        throw new AssertionError();
    }

    public C0759u2 c() {
        if (g || g()) {
            return this.e;
        }
        throw new AssertionError();
    }

    public WH d() {
        if (g || g() || h() || i()) {
            return this.f;
        }
        throw new AssertionError();
    }

    public com.android.tools.r8.utils.W1 e() {
        if (g || i()) {
            return this.c;
        }
        throw new AssertionError();
    }

    public boolean f() {
        return this.a == EnumC3781p3.a;
    }

    public boolean g() {
        return this.a == EnumC3781p3.b;
    }

    public boolean h() {
        return this.a == EnumC3781p3.c;
    }

    public boolean i() {
        return this.a == EnumC3781p3.d;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder(" return ");
        if (f()) {
            sb.append(this.b);
        } else if (g()) {
            if (this.f.e()) {
                sb.append("_NONNULL_ ");
            }
            sb.append(this.d.L0()).append('.').append(this.e);
        } else if (h()) {
            sb.append(this.f.f() ? "null" : "_NONNULL_");
        } else {
            if (!g && !i()) {
                throw new AssertionError();
            }
            sb.append(this.c.b());
            if (!this.c.d()) {
                sb.append("..");
                sb.append(this.c.a());
            }
        }
        return sb.toString();
    }

    C3786q3(C0767v2 c0767v2, C0759u2 c0759u2, WH wh) {
        if (!g && wh.f()) {
            throw new AssertionError();
        }
        this.a = EnumC3781p3.b;
        this.b = false;
        this.c = null;
        this.d = c0767v2;
        this.e = c0759u2;
        this.f = wh;
    }

    public boolean a() {
        if (g || f()) {
            return this.b;
        }
        throw new AssertionError();
    }

    public final com.android.tools.r8.internal.B1 a(C0702m c0702m, C0767v2 c0767v2) {
        com.android.tools.r8.graph.T0 t0B;
        com.android.tools.r8.internal.C1 c1B = c0702m.b();
        int iOrdinal = this.a.ordinal();
        if (iOrdinal == 0) {
            return c1B.a(com.android.tools.r8.utils.M.a(this.b));
        }
        if (iOrdinal == 1) {
            AbstractC0727q0 abstractC0727q0C = c0702m.c(this.d);
            if (abstractC0727q0C != null && (t0B = abstractC0727q0C.b(this.e)) != null) {
                com.android.tools.r8.graph.Y0 reference = t0B.getReference();
                C2096gg c2096ggB = AbstractC3121uK.b();
                c1B.getClass();
                return com.android.tools.r8.internal.C1.a(reference, c2096ggB);
            }
            return com.android.tools.r8.internal.B1.E();
        }
        if (iOrdinal == 2) {
            if (this.f.f()) {
                return c1B.a();
            }
            return com.android.tools.r8.internal.B1.E();
        }
        if (iOrdinal != 3) {
            throw new C3405y10("Unexpected type: " + this.a);
        }
        if (c0767v2.X0()) {
            if (this.f.f()) {
                return c1B.a();
            }
            return com.android.tools.r8.internal.B1.E();
        }
        if (this.c.d()) {
            return c1B.a(this.c.c());
        }
        long jB = this.c.b();
        long jA = this.c.a();
        c1B.getClass();
        return com.android.tools.r8.internal.C1.a(jB, jA);
    }

    C3786q3(WH wh) {
        if (!g && !wh.f() && !wh.e()) {
            throw new AssertionError();
        }
        this.a = EnumC3781p3.c;
        this.b = false;
        this.c = null;
        this.d = null;
        this.e = null;
        this.f = wh;
    }

    C3786q3(com.android.tools.r8.utils.W1 w1) {
        this.a = EnumC3781p3.d;
        this.b = false;
        this.c = w1;
        this.d = null;
        this.e = null;
        this.f = a(w1);
    }

    public final AbstractC1154Lf a(C0767v2 c0767v2) {
        if (c0767v2.X0() && ((g() || h() || i()) && d().e())) {
            int i = AbstractC1154Lf.b;
            return OH.o();
        }
        return AbstractC1154Lf.n();
    }
}
