package com.android.tools.r8.internal;

import com.android.tools.r8.dex.code.C0501s2;
import com.android.tools.r8.dex.code.C0506t2;
import com.android.tools.r8.graph.AbstractC0639e;
import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.ir.optimize.C3550d0;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.shaking.C3804u;

/* JADX INFO: renamed from: com.android.tools.r8.internal.s9, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C2972s9 extends AbstractC2412ko {
    static final /* synthetic */ boolean j = true;
    public static final /* synthetic */ int k = 0;
    private final C0767v2 h;
    private final boolean i;

    public C2972s9(S10 s10, S10 s11, C0767v2 c0767v2) {
        this(s10, s11, c0767v2, false);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int E2() {
        return 255;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int F2() {
        return 255;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int G2() {
        return 10;
    }

    public final C0767v2 J2() {
        return this.h;
    }

    public final boolean K2() {
        return this.i;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final Object a(AbstractC1855de abstractC1855de) {
        return null;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean b(AbstractC2412ko abstractC2412ko) {
        return abstractC2412ko.w1() && abstractC2412ko.y().h == this.h;
    }

    com.android.tools.r8.dex.code.P c(int i) {
        return new com.android.tools.r8.dex.code.P(i, this.h, this.i);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean c1() {
        return false;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean d(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var) {
        return false;
    }

    public final S10 e() {
        return (S10) this.b.get(0);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko, com.android.tools.r8.internal.InterfaceC3174v
    public final boolean g() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final String toString() {
        return super.toString() + "; " + this.h;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean w1() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final C2972s9 y() {
        return this;
    }

    public C2972s9(S10 s10, S10 s11, C0767v2 c0767v2, boolean z) {
        super(s10, s11);
        this.h = c0767v2;
        this.i = z;
    }

    public final boolean a(com.android.tools.r8.utils.A1 a1) {
        AbstractC3472z00 abstractC3472z00W = e().w();
        if (abstractC3472z00W.E()) {
            return a1.d() && this.h.M0() && this.h.b(a1.j0()).Q0();
        }
        if (abstractC3472z00W.w()) {
            return this.h != abstractC3472z00W.b().R();
        }
        if (j || abstractC3472z00W.r()) {
            return true;
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public boolean b(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var) {
        AbstractC0727q0 abstractC0727q0C;
        if (c0702m.Y().W0 || !c0702m.f().i() || this.h.W0()) {
            return true;
        }
        C0702m<C3733i> c0702mI0 = c0702m.i0();
        C0767v2 c0767v2B = this.h.b(c0702m.a());
        if (!(c0767v2B.O0() && ((abstractC0727q0C = c0702m.c(c0767v2B)) == null || !abstractC0727q0C.d(c0702m) || AbstractC0639e.a(abstractC0727q0C, o5Var, c0702mI0).b())) && c0702m.A().a(c0702mI0, e())) {
            return !e().a(c0702mI0).b(AbstractC3472z00.a(this.h, WH.b(), (C0702m<?>) c0702m), (C0702m<?>) c0702m);
        }
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(C2322jf c2322jf) {
        int iA = c2322jf.a(W0(), (S10) this.b.get(0));
        S10 s10 = this.a;
        if (s10 == null) {
            c2322jf.a(this, c(iA));
            return;
        }
        int iA2 = c2322jf.a(W0(), s10);
        if (iA == iA2) {
            c2322jf.a(this, c(iA2));
            return;
        }
        com.android.tools.r8.dex.code.P pC = c(iA2);
        if (iA2 <= 15 && iA <= 15) {
            c2322jf.a(this, new C0501s2(iA2, iA), pC);
        } else {
            c2322jf.a(this, new C0506t2(iA2, iA), pC);
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, C2332jo c2332jo) {
        return b(c0702m, o5Var);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final com.android.tools.r8.ir.optimize.V a(C3550d0 c3550d0, com.android.tools.r8.graph.o5 o5Var) {
        return c3550d0.a(this.h, o5Var);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final AbstractC3472z00 a(C0702m c0702m) {
        return AbstractC3472z00.a(this.h, e().w().N(), (C0702m<?>) c0702m);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(C0702m c0702m, C2433l20 c2433l20) {
        boolean z = j;
        if (!z) {
            super.a(c0702m, c2433l20);
        }
        AbstractC3472z00 abstractC3472z00W = e().w();
        if (!z && !abstractC3472z00W.G()) {
            throw new AssertionError();
        }
        AbstractC3472z00 abstractC3472z00A = a();
        AbstractC3472z00 abstractC3472z00A2 = AbstractC3472z00.a(this.h, abstractC3472z00W.N(), (C0702m<?>) c0702m);
        if (abstractC3472z00W.a(abstractC3472z00A2, (C0702m<?>) c0702m)) {
            if (!z && !abstractC3472z00W.a(abstractC3472z00A, (C0702m<?>) c0702m)) {
                throw new AssertionError();
            }
        } else {
            if (!z && !abstractC3472z00A.a(abstractC3472z00A2)) {
                throw new AssertionError();
            }
            if (!z && abstractC3472z00W.N() != abstractC3472z00A.N()) {
                throw new AssertionError();
            }
            if (!z && abstractC3472z00W.E()) {
                throw new AssertionError();
            }
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(InterfaceC2641no interfaceC2641no, C3340xC c3340xC) {
        c3340xC.a(this, interfaceC2641no);
        c3340xC.b(this, interfaceC2641no);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final C0767v2 a(C0702m c0702m, Q00 q00) {
        return this.h;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public void a(L6 l6) {
        l6.a(new M6(this.h, false), this);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    final void a(C3804u c3804u) {
        c3804u.a(this.h, this.i);
    }
}
