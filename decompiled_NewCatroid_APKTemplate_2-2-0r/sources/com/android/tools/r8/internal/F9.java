package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.origin.Origin;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.Set;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class F9 extends com.android.tools.r8.graph.W {
    private final com.android.tools.r8.graph.o5 d;

    public F9(com.android.tools.r8.graph.o5 o5Var) {
        this.d = o5Var;
    }

    @Override // com.android.tools.r8.graph.W
    public final C2409kl a(com.android.tools.r8.graph.o5 o5Var, C0702m c0702m, Origin origin, CE.a aVar) {
        C2409kl c2409klA = this.d.a((C0702m<?>) c0702m);
        c2409klA.getClass();
        C2561ml c2561ml = new C2561ml(c2409klA);
        int iC = o5Var.B().n().a().c();
        for (int i = 0; i < iC; i++) {
            c2561ml.next();
        }
        C3406y2 c3406y2Q = ((AbstractC2412ko) c2561ml.next()).q();
        int i2 = C3406y2.k;
        c2561ml.a(((C3329x2) new C3329x2().a(c2409klA, AbstractC3472z00.k(), null)).a(c3406y2Q.J2()).b(), (Set) null);
        while (c2561ml.hasNext()) {
            AbstractC2412ko abstractC2412ko = (AbstractC2412ko) c2561ml.next();
            abstractC2412ko.getClass();
            if (abstractC2412ko instanceof N3) {
                abstractC2412ko.b().g(abstractC2412ko.T0());
                c2561ml.s();
            } else if (abstractC2412ko.u2() && !abstractC2412ko.B0().K2()) {
                c2561ml.a(new C1616aU(), (Set) null);
            }
        }
        return new C2409kl(c0702m.Y(), o5Var, c2409klA.c, c2409klA.d, c2409klA.e, c2409klA.f, c2409klA.t(), o5Var.getOrigin(), aVar);
    }

    @Override // com.android.tools.r8.graph.AbstractC0740s
    protected final boolean c(Object obj) {
        throw new C3405y10();
    }

    @Override // com.android.tools.r8.graph.AbstractC0740s
    protected final int s0() {
        throw new C3405y10();
    }

    @Override // com.android.tools.r8.graph.W
    public final String toString() {
        return "CheckNotZeroCode(" + this.d.w() + Constant.AFTER_QUTO;
    }

    @Override // com.android.tools.r8.graph.W
    public final int w0() {
        throw new C3405y10();
    }

    @Override // com.android.tools.r8.graph.W
    public final void a(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.J5 j5) {
        throw new C3405y10();
    }

    @Override // com.android.tools.r8.graph.W
    public final void a(com.android.tools.r8.graph.S s, com.android.tools.r8.graph.J5 j5) {
        throw new C3405y10();
    }

    @Override // com.android.tools.r8.graph.W
    public final String a(com.android.tools.r8.graph.W0 w0, com.android.tools.r8.utils.P2 p2) {
        return toString();
    }
}
