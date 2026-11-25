package com.android.tools.r8.graph;

import com.android.tools.r8.dex.C0583u;
import com.android.tools.r8.internal.AbstractC1476Wj;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class A0 extends B0 {
    final int c;

    A0(int i) {
        this.c = i;
    }

    @Override // com.android.tools.r8.graph.B0, com.android.tools.r8.utils.structural.x
    public final com.android.tools.r8.utils.structural.x U() {
        return this;
    }

    @Override // com.android.tools.r8.graph.B0, com.android.tools.r8.utils.structural.x
    public final /* bridge */ /* synthetic */ int a(com.android.tools.r8.utils.structural.x xVar, com.android.tools.r8.utils.structural.a aVar) {
        return a((B0) xVar, aVar);
    }

    @Override // com.android.tools.r8.graph.B0
    final int b(B0 b0, com.android.tools.r8.utils.structural.a aVar) {
        return aVar.a(this.c, ((A0) b0).c);
    }

    public final int hashCode() {
        return (this.c * 7) + 2;
    }

    @Override // com.android.tools.r8.graph.B0
    public final A0 s0() {
        return this;
    }

    public final String toString() {
        return "ADVANCE_LINE " + this.c;
    }

    @Override // com.android.tools.r8.graph.B0
    final int v0() {
        return 2;
    }

    @Override // com.android.tools.r8.graph.B0
    public final void a(C0583u c0583u, AbstractC1476Wj abstractC1476Wj) {
        c0583u.a(2);
        c0583u.b(this.c);
    }

    @Override // com.android.tools.r8.graph.B0
    final void b(com.android.tools.r8.utils.structural.o oVar) {
        oVar.a(this.c);
    }

    @Override // com.android.tools.r8.graph.B0
    public final void a(I0 i0) {
        i0.a(this);
    }
}
