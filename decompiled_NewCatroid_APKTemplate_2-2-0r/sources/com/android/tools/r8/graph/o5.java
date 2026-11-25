package com.android.tools.r8.graph;

import com.android.tools.r8.internal.C2409kl;
import com.android.tools.r8.internal.CE;
import com.android.tools.r8.internal.InterfaceC0992Fq;
import com.android.tools.r8.internal.InterfaceC1285Pz;
import com.android.tools.r8.shaking.C3733i;
import java.util.BitSet;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class o5 extends AbstractC0748t0 implements n5 {
    static final /* synthetic */ boolean e = true;

    public o5(C0729q2 c0729q2, W0 w0) {
        super(c0729q2, w0);
    }

    @Override // com.android.tools.r8.graph.InterfaceC0593a0
    public final boolean X() {
        return true;
    }

    @Override // com.android.tools.r8.graph.n5
    public final InterfaceC1285Pz Z() {
        return e().f1();
    }

    public final boolean b(C0702m c0702m) {
        c0702m.Y().getClass();
        return ((!a().x1() && !a().isInterface()) || getAccessFlags().Z() || getAccessFlags().j() || getAccessFlags().o() || e().C1() || ((C3733i) c0702m.f()).j(getReference())) ? false : true;
    }

    @Override // com.android.tools.r8.graph.AbstractC0748t0, com.android.tools.r8.graph.InterfaceC0593a0
    public final o5 c() {
        return this;
    }

    @Override // com.android.tools.r8.graph.AbstractC0748t0, com.android.tools.r8.graph.InterfaceC0593a0
    public final AbstractC0748t0 c() {
        return this;
    }

    public final void d(C0702m c0702m) {
        if (c(c0702m)) {
            return;
        }
        e(c0702m);
    }

    public final void e(C0702m c0702m) {
        getAccessFlags().c(1024);
        e().g(c0702m.o());
        a(F5.H0(), c0702m);
        com.android.tools.r8.ir.optimize.info.r rVarA = com.android.tools.r8.ir.optimize.info.n.a();
        W0 w0E = e();
        com.android.tools.r8.ir.optimize.V v = com.android.tools.r8.ir.optimize.V.d;
        rVarA.getClass();
        w0E.a(v);
        com.android.tools.r8.ir.optimize.info.r rVarA2 = com.android.tools.r8.ir.optimize.info.n.a();
        rVarA2.getClass();
        if (B().d()) {
            rVarA2.e(this);
            rVarA2.g(e());
            rVarA2.f(this);
            rVarA2.g(this);
            rVarA2.h(this);
            rVarA2.a(this);
            rVarA2.b(this);
            rVarA2.i(this);
            rVarA2.j(this);
            rVarA2.l(this);
            rVarA2.m(this);
            rVarA2.n(this);
            rVarA2.o(this);
            rVarA2.p(this);
            rVarA2.r(this);
            rVarA2.q(this);
            rVarA2.s(this);
            rVarA2.t(this);
        }
        rVarA2.c(this);
        int iH1 = e().h1();
        BitSet bitSet = new BitSet(iH1);
        for (int i = 0; i < iH1; i++) {
            bitSet.set(i, true);
        }
        rVarA2.a(this, bitSet);
    }

    public final boolean f(C0702m c0702m) {
        return a().f(c0702m);
    }

    @Override // com.android.tools.r8.graph.InterfaceC0593a0
    public final o5 g0() {
        return this;
    }

    @Override // com.android.tools.r8.graph.l5
    public final InterfaceC0593a0 getContext() {
        return this;
    }

    @Override // com.android.tools.r8.graph.InterfaceC0593a0
    public final n5 q() {
        return this;
    }

    public C2409kl a(C0702m<?> c0702m) {
        CE.a aVar = new CE.a(c0702m.Y());
        W0 w0E = e();
        if (w0E.q1()) {
            return w0E.a1().a(this, c0702m, getOrigin(), aVar);
        }
        return null;
    }

    private boolean c(C0702m c0702m) {
        boolean zB = b(c0702m);
        if (zB) {
            C0725p4 c0725p4X = getAccessFlags();
            c0725p4X.a();
            c0725p4X.c(2048);
            c0725p4X.c(32);
            c0725p4X.b |= 1024;
            e().U0();
            e().Z1();
            com.android.tools.r8.ir.optimize.info.r rVarA = com.android.tools.r8.ir.optimize.info.n.a();
            rVarA.getClass();
            if (B().d()) {
                rVarA.e(this);
                rVarA.g(e());
                rVarA.f(this);
                rVarA.g(this);
                rVarA.h(this);
                rVarA.a(this);
                rVarA.b(this);
                rVarA.i(this);
                rVarA.j(this);
                rVarA.l(this);
                rVarA.m(this);
                rVarA.n(this);
                rVarA.o(this);
                rVarA.p(this);
                rVarA.r(this);
                rVarA.q(this);
                rVarA.s(this);
                rVarA.t(this);
            }
        }
        return zB;
    }

    public final void a(J5 j5) {
        W wA1 = e().a1();
        if (wA1 != null) {
            wA1.a(this, j5);
        }
    }

    @Override // com.android.tools.r8.graph.AbstractC0741s0
    public final C0729q2 a() {
        AbstractC0727q0 abstractC0727q0A = super.a();
        if (e || abstractC0727q0A.e0()) {
            return abstractC0727q0A.a0();
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.graph.AbstractC0741s0, com.android.tools.r8.graph.InterfaceC0593a0
    public final AbstractC0727q0 b() {
        return a();
    }

    @Override // com.android.tools.r8.graph.AbstractC0741s0, com.android.tools.r8.graph.InterfaceC0593a0
    public final C0729q2 b() {
        return a();
    }

    public final void a(W w, C0702m c0702m) {
        W wA1 = e().a1();
        InterfaceC0992Fq interfaceC0992FqK1 = e().k1();
        if (wA1 != null && wA1.z0() && !e().r1() && (c0702m.Y().n1.V || (!c0702m.Y().W0 && !a().f(c0702m)))) {
            interfaceC0992FqK1 = wA1.a(c0702m, e());
        }
        e().a(w, interfaceC0992FqK1);
    }
}
