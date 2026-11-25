package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.ir.optimize.C3550d0;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class Z2 extends AbstractC2412ko {
    public Z2(S10 s10, S10 s11) {
        super(s10, s11);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int E2() {
        return 15;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int F2() {
        return 15;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int G2() {
        return 7;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean I2() {
        return true;
    }

    public final S10 J2() {
        return (S10) this.b.get(0);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final S10 V0() {
        return J2();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final Object a(AbstractC1855de abstractC1855de) {
        return null;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean b(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var) {
        return J2().n.F();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean c1() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean d(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var) {
        return false;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko, com.android.tools.r8.internal.InterfaceC3174v
    public final boolean g() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final Z2 u() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(C2322jf c2322jf) {
        c2322jf.a(this, new com.android.tools.r8.dex.code.I(c2322jf.a(W0(), this.a), c2322jf.a(W0(), J2())));
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean b(AbstractC2412ko abstractC2412ko) {
        abstractC2412ko.getClass();
        return abstractC2412ko instanceof Z2;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final com.android.tools.r8.ir.optimize.V a(C3550d0 c3550d0, com.android.tools.r8.graph.o5 o5Var) {
        return com.android.tools.r8.ir.optimize.V.d;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, C2332jo c2332jo) {
        return b(c0702m, o5Var);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean a(AbstractC2412ko abstractC2412ko, com.android.tools.r8.ir.regalloc.f fVar, CE ce) {
        return super.a(abstractC2412ko, fVar, ce) && J2() == abstractC2412ko.u().J2();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(InterfaceC2641no interfaceC2641no, C3340xC c3340xC) {
        c3340xC.a(this, interfaceC2641no);
        c3340xC.b(this, interfaceC2641no);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(L6 l6) {
        l6.a(new A6(), this);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final AbstractC3472z00 a(C0702m c0702m) {
        return AbstractC3472z00.k();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, S10 s10) {
        return J2() == s10;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(NB nb) {
        nb.a(J2());
    }
}
