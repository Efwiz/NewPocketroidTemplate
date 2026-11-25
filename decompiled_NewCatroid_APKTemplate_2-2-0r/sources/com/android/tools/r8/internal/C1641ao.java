package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.ir.optimize.C3550d0;
import com.android.tools.r8.shaking.C3804u;

/* JADX INFO: renamed from: com.android.tools.r8.internal.ao, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C1641ao extends AbstractC2412ko {
    private final C0767v2 h;

    public C1641ao(S10 s10, S10 s11, C0767v2 c0767v2) {
        super(s10, s11);
        this.h = c0767v2;
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
        return 29;
    }

    public final C0767v2 J2() {
        return this.h;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final C1641ao U() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final Object a(AbstractC1855de abstractC1855de) {
        return null;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean b(AbstractC2412ko abstractC2412ko) {
        abstractC2412ko.getClass();
        return (abstractC2412ko instanceof C1641ao) && abstractC2412ko.U().h == this.h;
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
    public final String toString() {
        return super.toString() + "; " + this.h.r0();
    }

    public final S10 value() {
        return (S10) this.b.get(0);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(C2322jf c2322jf) {
        c2322jf.a(this, new com.android.tools.r8.dex.code.A1(c2322jf.a(W0(), this.a), c2322jf.a(W0(), value()), this.h));
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final com.android.tools.r8.ir.optimize.V a(C3550d0 c3550d0, com.android.tools.r8.graph.o5 o5Var) {
        return c3550d0.d(this.h, o5Var);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final AbstractC3472z00 a(C0702m c0702m) {
        return AbstractC3472z00.k();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(InterfaceC2641no interfaceC2641no, C3340xC c3340xC) {
        c3340xC.a(this, interfaceC2641no);
        c3340xC.b(this, interfaceC2641no);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(L6 l6) {
        l6.a(new A7(this.h), this);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    final void a(C3804u c3804u) {
        c3804u.c(this.h);
    }
}
