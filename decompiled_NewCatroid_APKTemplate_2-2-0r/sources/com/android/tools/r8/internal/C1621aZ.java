package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.ir.optimize.C3550d0;

/* JADX INFO: renamed from: com.android.tools.r8.internal.aZ, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C1621aZ extends AbstractC1954eu {
    static final /* synthetic */ boolean i = true;

    public C1621aZ(S10 s10) {
        super(s10);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean B2() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int E2() {
        return 255;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int F2() {
        if (i) {
            return 0;
        }
        throw new AssertionError("Throw defines no values.");
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int G2() {
        return 65;
    }

    public final S10 K2() {
        return (S10) this.b.get(0);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final C1621aZ L0() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final Object a(AbstractC1855de abstractC1855de) {
        return null;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean b(AbstractC2412ko abstractC2412ko) {
        return abstractC2412ko.B2();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko, com.android.tools.r8.internal.InterfaceC3174v
    public final boolean g() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(C2322jf c2322jf) {
        c2322jf.a(this, new com.android.tools.r8.dex.code.e4(c2322jf.a(W0(), K2())));
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(InterfaceC2641no interfaceC2641no, C3340xC c3340xC) {
        c3340xC.a(this, interfaceC2641no);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(L6 l6) {
        l6.a(new V8(), this);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(NB nb) {
        nb.d(K2());
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, S10 s10) {
        if (K2() == s10) {
            return true;
        }
        AbstractC3472z00 abstractC3472z00W = K2().w();
        if (abstractC3472z00W.E() || abstractC3472z00W.N().f()) {
            return true;
        }
        S10 s10M = K2().m();
        if (s10M.i()) {
            return false;
        }
        AbstractC2412ko abstractC2412koQ = s10M.q();
        return abstractC2412koQ.p2() && abstractC2412koQ.t0().h == c0702m.a().s3;
    }

    @Override // com.android.tools.r8.internal.AbstractC1954eu, com.android.tools.r8.internal.AbstractC2412ko
    public final com.android.tools.r8.ir.optimize.V a(C3550d0 c3550d0, com.android.tools.r8.graph.o5 o5Var) {
        return com.android.tools.r8.ir.optimize.V.d;
    }
}
