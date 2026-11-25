package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.ir.optimize.C3550d0;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class ZL extends AbstractC2412ko {
    static final /* synthetic */ boolean h = true;

    public ZL(S10 s10) {
        super((S10) null, s10);
        if (!h && !s10.T()) {
            throw new AssertionError();
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int E2() {
        throw new C3405y10();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int F2() {
        throw new C3405y10();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int G2() {
        return 54;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final Object a(AbstractC1855de abstractC1855de) {
        return null;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean b(AbstractC2412ko abstractC2412ko) {
        abstractC2412ko.getClass();
        if (!(abstractC2412ko instanceof ZL)) {
            return false;
        }
        ZL zlY0 = abstractC2412ko.y0();
        if (!T0().b(new ZL$$ExternalSyntheticLambda0())) {
            return !zlY0.T0().b(new ZL$$ExternalSyntheticLambda0());
        }
        C2179hn c2179hnR = T0().q().R();
        if (zlY0.T0().b(new ZL$$ExternalSyntheticLambda0())) {
            return c2179hnR.K2() == zlY0.T0().q().R().K2();
        }
        return false;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    protected final void c(S10 s10) {
        if (s10.F()) {
            super.c(s10);
        } else {
            this.b.add(s10);
        }
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
    public final ZL y0() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(C2322jf c2322jf) {
        throw new C3405y10("This classfile-specific IR should not be inserted in the Dex backend.");
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(L6 l6) {
        l6.a(new D8(((S10) this.b.get(0)).d0().b() ? D8.a.Pop2 : D8.a.Pop), this);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(InterfaceC2641no interfaceC2641no, C3340xC c3340xC) {
        throw new C3405y10("This IR must not be inserted before load and store insertion.");
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final com.android.tools.r8.ir.optimize.L a(C0702m c0702m, C2409kl c2409kl) {
        return com.android.tools.r8.ir.optimize.L.e();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final com.android.tools.r8.ir.optimize.V a(C3550d0 c3550d0, com.android.tools.r8.graph.o5 o5Var) {
        return com.android.tools.r8.ir.optimize.V.d;
    }
}
