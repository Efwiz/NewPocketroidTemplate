package com.android.tools.r8.graph;

import com.android.tools.r8.dex.C0586x;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class J2 extends B2 {
    @Override // com.android.tools.r8.graph.B2
    public final J2 I0() {
        return this;
    }

    @Override // com.android.tools.r8.graph.B2, com.android.tools.r8.utils.structural.x
    public final com.android.tools.r8.utils.structural.x U() {
        return this;
    }

    @Override // com.android.tools.r8.graph.B2, com.android.tools.r8.utils.structural.x
    public final /* bridge */ /* synthetic */ int a(com.android.tools.r8.utils.structural.x xVar, com.android.tools.r8.utils.structural.a aVar) {
        return a((B2) xVar, aVar);
    }

    @Override // com.android.tools.r8.graph.B2
    public final boolean c1() {
        return true;
    }

    @Override // com.android.tools.r8.graph.B2
    public final /* bridge */ /* synthetic */ boolean h1() {
        return false;
    }

    @Override // com.android.tools.r8.graph.B2
    public final /* bridge */ /* synthetic */ void i1() {
    }

    public abstract long j1();

    static void a(E2 e2, long j, int i, C0586x c0586x) {
        c0586x.d(1);
        int iB = com.android.tools.r8.utils.P0.b(c0586x, j, i);
        c0586x.d(-(iB + 1));
        c0586x.a((byte) (e2.a | ((iB - 1) << 5)));
        c0586x.d(iB);
    }

    @Override // com.android.tools.r8.graph.B2
    public final com.android.tools.r8.internal.B1 a(com.android.tools.r8.internal.C1 c1) {
        return c1.a(j1());
    }
}
