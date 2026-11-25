package com.android.tools.r8.internal;

import com.android.tools.r8.dex.code.C0538z1;
import com.android.tools.r8.graph.AbstractC0639e;
import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.ir.optimize.C3550d0;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.shaking.C3804u;

/* JADX INFO: renamed from: com.android.tools.r8.internal.hn, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2179hn extends AbstractC2412ko {
    static final /* synthetic */ boolean i = true;
    private final C0767v2 h;

    public C2179hn(C0767v2 c0767v2, S10 s10) {
        super(s10);
        boolean z = i;
        if (!z && !d1()) {
            throw new AssertionError();
        }
        if (!z && !s10.w().C()) {
            throw new AssertionError();
        }
        if (!z && !c0767v2.O0()) {
            throw new AssertionError();
        }
        this.h = c0767v2;
    }

    public static C2108gn J2() {
        return new C2108gn();
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
        return 27;
    }

    public final C0767v2 K2() {
        return this.h;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final C2179hn R() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final Object a(AbstractC1855de abstractC1855de) {
        return null;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    final void a(C3804u c3804u) {
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean b(AbstractC2412ko abstractC2412ko) {
        abstractC2412ko.getClass();
        return (abstractC2412ko instanceof C2179hn) && this.h == abstractC2412ko.R().h;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean c1() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean d(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var) {
        if (c0702m.q()) {
            return this.h.a(o5Var, c0702m);
        }
        return this.h != o5Var.t();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko, com.android.tools.r8.internal.InterfaceC3174v
    public final boolean g() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final String toString() {
        return super.toString() + "; " + this.h.r0();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final AbstractC3472z00 a(C0702m c0702m) {
        return AbstractC3472z00.k();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(C2322jf c2322jf) {
        c2322jf.a(this, new C0538z1(c2322jf.a(W0(), b()), this.h));
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean b(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var) {
        if (!i && !c0702m.q()) {
            throw new AssertionError();
        }
        C0702m<C3733i> c0702mI0 = c0702m.i0();
        AbstractC0727q0 abstractC0727q0C = c0702m.c(this.h);
        return abstractC0727q0C == null || AbstractC0639e.a(abstractC0727q0C, o5Var, c0702mI0).b() || this.h.a(o5Var, c0702m);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(L6 l6) {
        l6.a(new C3335x7(this.h), this);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean a(C0767v2 c0767v2, com.android.tools.r8.graph.o5 o5Var, C0702m c0702m, U9 u9, S9 s9) {
        return T9.a(this, c0767v2, c0702m, u9, s9);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, C2332jo c2332jo) {
        return b(c0702m, o5Var);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final com.android.tools.r8.ir.optimize.V a(C3550d0 c3550d0, com.android.tools.r8.graph.o5 o5Var) {
        return c3550d0.c(this.h, o5Var);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(InterfaceC2641no interfaceC2641no, C3340xC c3340xC) {
        c3340xC.b(this, interfaceC2641no);
    }
}
