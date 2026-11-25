package com.android.tools.r8.internal;

import com.android.tools.r8.dex.code.C0487p2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.ir.optimize.C3550d0;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class AF extends AbstractC2412ko {
    static final /* synthetic */ boolean j = true;
    private final C0767v2 h;
    private final com.android.tools.r8.utils.A1 i;

    public AF(S10 s10, C0767v2 c0767v2, com.android.tools.r8.utils.A1 a1) {
        super(s10);
        this.h = c0767v2;
        this.i = a1;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int E2() {
        if (j) {
            return 0;
        }
        throw new AssertionError("MoveException has no register arguments.");
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int F2() {
        return 255;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int G2() {
        return 44;
    }

    public final C0767v2 J2() {
        return this.h;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final Object a(AbstractC1855de abstractC1855de) {
        return null;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(L6 l6) {
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean b(AbstractC2412ko abstractC2412ko) {
        abstractC2412ko.getClass();
        if (abstractC2412ko instanceof AF) {
            return !this.i.q() || abstractC2412ko.o0().h == this.h;
        }
        return false;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean c1() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean d(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var) {
        return false;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final AF o0() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(C2322jf c2322jf) {
        c2322jf.a(this, new C0487p2(c2322jf.a(W0(), this.a)));
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final com.android.tools.r8.ir.optimize.L a(C0702m c0702m, C2409kl c2409kl) {
        if (!c0702m.Y().W0 && !c2409kl.h().a().f(c0702m) && !c2409kl.k().a()) {
            return com.android.tools.r8.ir.optimize.L.a();
        }
        return com.android.tools.r8.ir.optimize.L.e();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(InterfaceC2641no interfaceC2641no, C3340xC c3340xC) {
        c3340xC.b(this, interfaceC2641no);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final C0767v2 a(C0702m c0702m, Q00 q00) {
        return this.h;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final AbstractC3472z00 a(C0702m c0702m) {
        return AbstractC3472z00.a(this.h, WH.b(), (C0702m<?>) c0702m);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(NB nb) {
        nb.a(this.h);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final com.android.tools.r8.ir.optimize.V a(C3550d0 c3550d0, com.android.tools.r8.graph.o5 o5Var) {
        return com.android.tools.r8.ir.optimize.V.d;
    }
}
