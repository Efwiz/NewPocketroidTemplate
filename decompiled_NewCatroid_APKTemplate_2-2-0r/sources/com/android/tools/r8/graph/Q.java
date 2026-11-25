package com.android.tools.r8.graph;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class Q extends AbstractC0735r0 implements P {
    static final /* synthetic */ boolean e = true;

    public Q(C0754u0 c0754u0, T0 t0) {
        super(c0754u0, t0);
    }

    @Override // com.android.tools.r8.graph.AbstractC0741s0
    public final AbstractC0727q0 a() {
        AbstractC0727q0 abstractC0727q0A = super.a();
        if (!e) {
            abstractC0727q0A.getClass();
            if (!(abstractC0727q0A instanceof C0754u0)) {
                throw new AssertionError();
            }
        }
        return abstractC0727q0A.m();
    }
}
