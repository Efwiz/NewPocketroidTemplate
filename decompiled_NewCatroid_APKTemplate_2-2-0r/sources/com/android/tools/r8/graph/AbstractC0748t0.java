package com.android.tools.r8.graph;

import com.android.tools.r8.references.MethodReference;

/* JADX INFO: renamed from: com.android.tools.r8.graph.t0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC0748t0 extends AbstractC0741s0<W0, C0684j2> implements InterfaceC0669h4 {
    static final /* synthetic */ boolean d = true;

    AbstractC0748t0(AbstractC0727q0 abstractC0727q0, W0 w0) {
        super(abstractC0727q0, w0);
        boolean z = d;
        if (!z && abstractC0727q0.A1() != (this instanceof S)) {
            throw new AssertionError();
        }
        if (!z && abstractC0727q0.f0() != (this instanceof C0644e4)) {
            throw new AssertionError();
        }
        if (!z && abstractC0727q0.e0() != (this instanceof o5)) {
            throw new AssertionError();
        }
    }

    public static o5 a(AbstractC0748t0 abstractC0748t0) {
        if (abstractC0748t0 != null) {
            return abstractC0748t0.g0();
        }
        return null;
    }

    public com.android.tools.r8.ir.optimize.info.h B() {
        return e().i1();
    }

    public C0800x2 D() {
        return getReference().G0();
    }

    public final C0737r2 F() {
        return getReference().H0();
    }

    public C0767v2 G() {
        return getReference().I0();
    }

    public final boolean H() {
        return a().isInterface() && e().w1();
    }

    public final boolean b(AbstractC0748t0 abstractC0748t0) {
        return e() == abstractC0748t0.e() && a() == abstractC0748t0.a();
    }

    @Override // com.android.tools.r8.graph.InterfaceC0593a0
    public AbstractC0748t0 c() {
        return this;
    }

    @Override // com.android.tools.r8.graph.AbstractC0741s0, com.android.tools.r8.graph.InterfaceC0593a0
    public final /* bridge */ /* synthetic */ W0 e() {
        return (W0) super.e();
    }

    @Override // com.android.tools.r8.graph.AbstractC0741s0, com.android.tools.r8.graph.InterfaceC0593a0
    public final /* bridge */ /* synthetic */ C0684j2 getReference() {
        return (C0684j2) super.getReference();
    }

    @Override // com.android.tools.r8.graph.InterfaceC0593a0
    public final boolean h0() {
        return true;
    }

    @Override // com.android.tools.r8.graph.InterfaceC0593a0
    public final AbstractC0741s0 o0() {
        return this;
    }

    @Override // com.android.tools.r8.graph.InterfaceC0669h4
    public final AbstractC0748t0 p() {
        return this;
    }

    @Override // com.android.tools.r8.graph.InterfaceC0593a0
    /* JADX INFO: renamed from: x, reason: merged with bridge method [inline-methods] */
    public C0725p4 getAccessFlags() {
        return e().getAccessFlags();
    }

    public MethodReference y() {
        return getReference().E0();
    }

    public final AbstractC0717o2 z() {
        C0684j2 c0684j2V = getReference();
        c0684j2V.getClass();
        return new C0705m2(c0684j2V);
    }

    public static AbstractC0748t0 a(AbstractC0727q0 abstractC0727q0, W0 w0) {
        if (abstractC0727q0.e0()) {
            return new o5(abstractC0727q0.a0(), w0);
        }
        if (abstractC0727q0.f0()) {
            return new C0644e4(abstractC0727q0.d0(), w0);
        }
        if (d || (abstractC0727q0 instanceof C0754u0)) {
            return new S(abstractC0727q0.m(), w0);
        }
        throw new AssertionError();
    }

    public C0767v2 b(int i) {
        return getReference().k(i);
    }

    public final C0767v2 a(int i) {
        return e().j(i);
    }
}
