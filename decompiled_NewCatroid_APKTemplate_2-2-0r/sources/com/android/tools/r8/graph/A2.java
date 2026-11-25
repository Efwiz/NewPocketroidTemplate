package com.android.tools.r8.graph;

import com.android.tools.r8.dex.C0586x;
import com.android.tools.r8.internal.C2892r10;
import com.android.tools.r8.internal.C3405y10;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class A2 extends B2 {
    public final R0 b;

    public A2(R0 r0) {
        this.b = r0;
    }

    @Override // com.android.tools.r8.graph.B2
    public final Object M0() {
        throw new C3405y10("No boxed value for DexValueAnnotation");
    }

    @Override // com.android.tools.r8.graph.B2
    public final E2 N0() {
        return E2.q;
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
    final int b(B2 b2, com.android.tools.r8.utils.structural.a aVar) {
        return this.b.a(b2.u0().b, aVar);
    }

    @Override // com.android.tools.r8.graph.B2
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof A2) {
            return ((A2) obj).b.equals(this.b);
        }
        return false;
    }

    @Override // com.android.tools.r8.graph.B2
    public final int hashCode() {
        return this.b.hashCode() * 7;
    }

    @Override // com.android.tools.r8.graph.B2
    public final void i1() {
        this.b.t0();
    }

    @Override // com.android.tools.r8.graph.B2
    public final Object s0() {
        throw new C3405y10("No ASM conversion for DexValueAnnotation");
    }

    @Override // com.android.tools.r8.graph.B2
    public final String toString() {
        return "Annotation " + this.b;
    }

    @Override // com.android.tools.r8.graph.B2
    public final A2 u0() {
        return this;
    }

    @Override // com.android.tools.r8.graph.B2
    public final void a(C0702m c0702m, com.android.tools.r8.dex.L l) {
        this.b.a(c0702m, l);
    }

    @Override // com.android.tools.r8.graph.B2
    final void b(com.android.tools.r8.utils.structural.o oVar) {
        this.b.a(oVar);
    }

    @Override // com.android.tools.r8.graph.B2
    public final com.android.tools.r8.internal.B1 a(com.android.tools.r8.internal.C1 c1) {
        return C2892r10.F();
    }

    @Override // com.android.tools.r8.graph.B2
    public final void a(C0586x c0586x, C0645e5 c0645e5) {
        B2.a(E2.q, 0, c0586x);
        com.android.tools.r8.dex.K.a(this.b, c0586x, c0645e5);
    }

    @Override // com.android.tools.r8.graph.B2
    public final C0767v2 a(C0722p1 c0722p1) {
        throw new C3405y10();
    }
}
