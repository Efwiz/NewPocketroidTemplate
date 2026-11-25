package com.android.tools.r8.graph;

import com.android.tools.r8.internal.C2892r10;
import com.android.tools.r8.internal.C3405y10;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2 extends B2.l {
    public C2(Y0 y0) {
        super(y0);
    }

    @Override // com.android.tools.r8.graph.B2
    public final C2 A0() {
        return this;
    }

    @Override // com.android.tools.r8.graph.B2
    public final E2 N0() {
        return E2.o;
    }

    @Override // com.android.tools.r8.graph.B2.l, com.android.tools.r8.graph.B2
    public final C0767v2 a(C0722p1 c0722p1) {
        throw new C3405y10();
    }

    @Override // com.android.tools.r8.graph.B2
    final int b(B2 b2, com.android.tools.r8.utils.structural.a aVar) {
        Y0 y0 = (Y0) this.b;
        Y0 y1 = (Y0) b2.A0().b;
        y0.getClass();
        return aVar.a(y0, y1);
    }

    @Override // com.android.tools.r8.graph.B2.l, com.android.tools.r8.graph.B2
    public final /* bridge */ /* synthetic */ boolean g1() {
        return true;
    }

    @Override // com.android.tools.r8.graph.B2.l, com.android.tools.r8.graph.B2
    public final /* bridge */ /* synthetic */ void i1() {
    }

    public final I3 j1() {
        return this.b;
    }

    @Override // com.android.tools.r8.graph.B2
    public final void a(C0702m c0702m, com.android.tools.r8.dex.L l) {
        ((Y0) this.b).a(c0702m, l);
    }

    @Override // com.android.tools.r8.graph.B2
    public final com.android.tools.r8.internal.B1 a(com.android.tools.r8.internal.C1 c1) {
        return C2892r10.F();
    }

    @Override // com.android.tools.r8.graph.B2
    final void b(com.android.tools.r8.utils.structural.o oVar) {
        ((Y0) this.b).a(oVar);
    }
}
