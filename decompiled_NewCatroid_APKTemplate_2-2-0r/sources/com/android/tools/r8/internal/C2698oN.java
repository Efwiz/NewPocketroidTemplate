package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0702m;

/* JADX INFO: renamed from: com.android.tools.r8.internal.oN, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2698oN implements InterfaceC1985fE {
    private final C2626nc a;
    private final InterfaceC1985fE b;

    private C2698oN(C2626nc c2626nc, C1898eE c1898eE) {
        this.a = c2626nc;
        this.b = c1898eE;
    }

    public static InterfaceC1985fE a(C0702m c0702m, C1898eE c1898eE) {
        AbstractC2151hN abstractC2151hNB = AbstractC2151hN.b(c0702m);
        abstractC2151hNB.getClass();
        return abstractC2151hNB instanceof IH ? c1898eE : new C2698oN(abstractC2151hNB.a(), c1898eE);
    }

    @Override // com.android.tools.r8.internal.InterfaceC1985fE
    public final void a(AbstractC0748t0 abstractC0748t0, com.android.tools.r8.graph.o5 o5Var) {
        this.a.a(abstractC0748t0, o5Var);
        this.b.a(abstractC0748t0, o5Var);
    }

    @Override // com.android.tools.r8.internal.InterfaceC1985fE
    public final void a(C0702m c0702m, C2449lE c2449lE) {
        this.a.a(c0702m);
        this.b.a(c0702m, c2449lE);
    }
}
