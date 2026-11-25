package com.android.tools.r8.graph;

import com.android.tools.r8.dex.code.InterfaceC0414b;
import com.android.tools.r8.dex.code.InterfaceC0424d;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.EnumC1953et;
import java.util.ListIterator;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class J5 {
    static final /* synthetic */ boolean d = true;
    protected final C0702m a;
    private final InterfaceC0593a0 b;
    private com.android.tools.r8.utils.D3 c = com.android.tools.r8.utils.D3.e();

    public J5(C0702m c0702m, InterfaceC0593a0 interfaceC0593a0) {
        this.a = c0702m;
        this.b = interfaceC0593a0;
    }

    public final C0722p1 a() {
        return this.a.a();
    }

    public abstract void a(Y0 y0);

    public abstract void a(C0684j2 c0684j2);

    public abstract void b(C0684j2 c0684j2);

    protected final void b(C0721p0 c0721p0) {
        C0722p1 c0722p1A = this.a.a();
        C0684j2 c0684j2U0 = c0721p0.f.u0();
        if (c0684j2U0 != c0722p1A.I5 && c0684j2U0 != c0722p1A.J5) {
            a(c0721p0.f, I5.b);
        }
        f(c0721p0.e.e);
    }

    public abstract void b(C0767v2 c0767v2);

    public AbstractC1476Wj c() {
        boolean z = d;
        if (!z && !this.b.h0()) {
            throw new AssertionError();
        }
        if (z || this.b.h0()) {
            return this.b.c().e().a1().a(this.a);
        }
        throw new AssertionError();
    }

    public abstract void c(Y0 y0);

    public final InterfaceC0593a0 d() {
        return this.b;
    }

    public final com.android.tools.r8.utils.D3 e() {
        return this.c;
    }

    public abstract void e(Y0 y0);

    public abstract void e(C0684j2 c0684j2);

    public void f(C0684j2 c0684j2) {
        e(c0684j2);
    }

    public abstract void f(C0767v2 c0767v2);

    public abstract void g(Y0 y0);

    public abstract void g(C0684j2 c0684j2);

    public void h(Y0 y0) {
        g(y0);
    }

    public abstract void h(C0684j2 c0684j2);

    public void a(Y0[] y0Arr) {
        f(this.a.a().c2);
    }

    public void d(C0684j2 c0684j2) {
        boolean z = d;
        if (!z && !this.b.h0()) {
            throw new AssertionError();
        }
        EnumC1953et enumC1953etA = EnumC1953et.a(c0684j2, this.b.c(), this.a, c());
        if (enumC1953etA == EnumC1953et.c) {
            a(c0684j2);
        } else {
            if (!z && !enumC1953etA.e()) {
                throw new AssertionError();
            }
            g(c0684j2);
        }
    }

    public void e(C0767v2 c0767v2) {
        a(c0767v2, true);
    }

    public void f(Y0 y0) {
        e(y0);
    }

    public void a(InterfaceC0414b interfaceC0414b) {
        a(interfaceC0414b.getField());
    }

    public void a(InterfaceC0424d interfaceC0424d) {
        e(interfaceC0424d.getField());
    }

    public void a(C0767v2 c0767v2, ListIterator listIterator, boolean z) {
        f(c0767v2);
    }

    public final void c(C0684j2 c0684j2) {
        d(c0684j2);
    }

    public void a(C0767v2 c0767v2, boolean z) {
        f(c0767v2);
    }

    public void c(C0767v2 c0767v2) {
        f(c0767v2);
    }

    public void a(C0767v2 c0767v2) {
        f(c0767v2);
    }

    public void a(C0699l2 c0699l2, I5 i5) {
        switch (c0699l2.d.ordinal()) {
            case 0:
                h(c0699l2.t0());
                return;
            case 1:
                f(c0699l2.t0());
                return;
            case 2:
                d(c0699l2.t0());
                return;
            case 3:
                b(c0699l2.t0());
                return;
            case 4:
                e(c0699l2.u0());
                return;
            case 5:
                h(c0699l2.u0());
                return;
            case 6:
                C0684j2 c0684j2U0 = c0699l2.u0();
                d(c0684j2U0.e);
                a(c0684j2U0);
                return;
            case 7:
                a(c0699l2.u0());
                return;
            case 8:
                b(c0699l2.u0());
                return;
            case 9:
                g(c0699l2.u0());
                return;
            default:
                throw new AssertionError();
        }
    }

    public void d(Y0 y0) {
        c(y0);
    }

    public void d(C0767v2 c0767v2) {
        f(c0767v2);
    }

    public final void b() {
        if (!d && !this.c.g()) {
            throw new AssertionError();
        }
        this.c = com.android.tools.r8.utils.D3.d();
    }

    public void b(Y0 y0) {
        a(y0);
    }

    protected final void a(int i, C0721p0 c0721p0) {
        I5 i5;
        C0722p1 c0722p1A = this.a.a();
        C0684j2 c0684j2U0 = c0721p0.f.u0();
        boolean z = c0684j2U0 == c0722p1A.I5 || c0684j2U0 == c0722p1A.J5;
        if (!d && i > c0721p0.g.size()) {
            throw new AssertionError();
        }
        for (int i2 = 0; i2 < i; i2++) {
            B2 b2 = (B2) c0721p0.g.get(i2);
            int iOrdinal = b2.N0().ordinal();
            if (iOrdinal == 7) {
                C0737r2 c0737r2 = (C0737r2) b2.H0().b;
                f(c0737r2.e);
                for (C0767v2 c0767v2 : c0737r2.f.a) {
                    f(c0767v2);
                }
            } else if (iOrdinal == 8) {
                C0699l2 c0699l2 = (C0699l2) b2.G0().b;
                if (z) {
                    i5 = I5.a;
                } else {
                    i5 = I5.b;
                }
                a(c0699l2, i5);
            } else if (iOrdinal != 10) {
                if (!d && !b2.X0() && !b2.Y0() && !b2.W0() && !b2.T0() && !b2.e1()) {
                    throw new AssertionError();
                }
            } else {
                f((C0767v2) b2.L0().b);
            }
        }
    }

    public void a(C0721p0 c0721p0) {
        b(c0721p0);
        a(c0721p0.g.size(), c0721p0);
    }
}
