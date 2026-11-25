package com.android.tools.r8.graph;

import com.android.tools.r8.references.FieldReference;

/* JADX INFO: renamed from: com.android.tools.r8.graph.r0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC0735r0 extends AbstractC0741s0<T0, Y0> {
    static final /* synthetic */ boolean d = true;

    AbstractC0735r0(AbstractC0727q0 abstractC0727q0, T0 t0) {
        super(abstractC0727q0, t0);
        boolean z = d;
        if (!z && abstractC0727q0.A1() != (this instanceof Q)) {
            throw new AssertionError();
        }
        if (!z && abstractC0727q0.f0() != (this instanceof C0637d4)) {
            throw new AssertionError();
        }
        if (!z && abstractC0727q0.e0() != (this instanceof m5)) {
            throw new AssertionError();
        }
    }

    public static AbstractC0735r0 a(AbstractC0727q0 abstractC0727q0, T0 t0) {
        if (abstractC0727q0.e0()) {
            return new m5(t0, abstractC0727q0.a0());
        }
        if (abstractC0727q0.f0()) {
            return new C0637d4(abstractC0727q0.d0(), t0);
        }
        if (d || (abstractC0727q0 instanceof C0754u0)) {
            return new Q(abstractC0727q0.m(), t0);
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.graph.InterfaceC0593a0
    public AbstractC0735r0 d() {
        return this;
    }

    public final C0767v2 getType() {
        return getReference().getType();
    }

    @Override // com.android.tools.r8.graph.InterfaceC0593a0
    public final boolean j0() {
        return true;
    }

    @Override // com.android.tools.r8.graph.InterfaceC0593a0
    public final AbstractC0741s0 o0() {
        return this;
    }

    @Override // com.android.tools.r8.graph.InterfaceC0593a0
    /* JADX INFO: renamed from: x, reason: merged with bridge method [inline-methods] */
    public Q2 getAccessFlags() {
        return e().getAccessFlags();
    }

    public final FieldReference y() {
        return getReference().E0();
    }
}
