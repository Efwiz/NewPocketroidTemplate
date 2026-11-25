package com.android.tools.r8.shaking;

import com.android.tools.r8.experimental.graphinfo.GraphNode;
import com.android.tools.r8.internal.C1349Sj;

/* JADX INFO: renamed from: com.android.tools.r8.shaking.n1, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3767n1 extends AbstractC3837z1 {
    static final /* synthetic */ boolean b = true;
    private final com.android.tools.r8.graph.O0 a;

    private C3767n1(com.android.tools.r8.graph.O0 o0) {
        this.a = o0;
    }

    @Override // com.android.tools.r8.shaking.AbstractC3837z1
    public final C1349Sj.a a() {
        return C1349Sj.a.AnnotatedOn;
    }

    @Override // com.android.tools.r8.shaking.AbstractC3837z1
    public final GraphNode a(M0 m0) {
        if (this.a.A0()) {
            return m0.a(this.a.t0().d);
        }
        com.android.tools.r8.graph.O0 o0 = this.a;
        o0.getClass();
        if (o0 instanceof com.android.tools.r8.graph.T0) {
            return m0.a(this.a.u0().getReference());
        }
        if (b || this.a.D0()) {
            return m0.a(this.a.w0().getReference());
        }
        throw new AssertionError();
    }
}
