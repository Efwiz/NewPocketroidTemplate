package com.android.tools.r8.graph;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class S extends AbstractC0748t0 implements P {
    static final /* synthetic */ boolean e = true;

    public S(C0754u0 c0754u0, W0 w0) {
        super(c0754u0, w0);
    }

    @Override // com.android.tools.r8.graph.AbstractC0741s0
    /* JADX INFO: renamed from: I, reason: merged with bridge method [inline-methods] */
    public final C0754u0 a() {
        AbstractC0727q0 abstractC0727q0A = super.a();
        if (!e) {
            abstractC0727q0A.getClass();
            if (!(abstractC0727q0A instanceof C0754u0)) {
                throw new AssertionError();
            }
        }
        return abstractC0727q0A.m();
    }

    @Override // com.android.tools.r8.graph.InterfaceC0593a0
    public final S m0() {
        return this;
    }
}
