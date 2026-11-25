package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0753u;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.ir.optimize.C3550d0;
import com.android.tools.r8.naming.AbstractC3649o0;
import java.util.ListIterator;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class X7 extends B7 implements X8 {
    private final C0767v2 b;
    private final int c;

    public X7(int i, C0767v2 c0767v2) {
        this.b = c0767v2;
        this.c = i;
    }

    @Override // com.android.tools.r8.internal.B7
    public final boolean S() {
        return true;
    }

    @Override // com.android.tools.r8.internal.X8
    public final B7 a(C0767v2 c0767v2) {
        return new X7(this.c, c0767v2);
    }

    @Override // com.android.tools.r8.internal.X8
    public final C0767v2 getType() {
        return this.b;
    }

    @Override // com.android.tools.r8.internal.B7
    public final X8 w() {
        return this;
    }

    @Override // com.android.tools.r8.internal.B7
    public final boolean x() {
        return true;
    }

    @Override // com.android.tools.r8.internal.B7
    public final int z() {
        return 187;
    }

    @Override // com.android.tools.r8.internal.B7
    public final int a(B7 b7, com.android.tools.r8.utils.structural.a aVar, com.android.tools.r8.graph.C c) {
        C0767v2 c0767v2 = this.b;
        C0767v2 c0767v3 = ((X7) b7).b;
        c0767v2.getClass();
        return aVar.a(c0767v2, c0767v3);
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(com.android.tools.r8.utils.structural.o oVar) {
        C0767v2 c0767v2 = this.b;
        c0767v2.getClass();
        oVar.a(c0767v2);
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, C0722p1 c0722p1, AbstractC1476Wj abstractC1476Wj, AbstractC2331jn abstractC2331jn, AbstractC3649o0 abstractC3649o0, C2363kB c2363kB, AbstractC1899eF abstractC1899eF) {
        throw new C3405y10();
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(C2757p8 c2757p8) {
        c2757p8.a(this);
    }

    @Override // com.android.tools.r8.internal.B7
    final void a(com.android.tools.r8.graph.J5 j5, ListIterator listIterator) {
        j5.f(this.b);
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(C2102gl c2102gl, N8 n8, A8 a8) {
        c2102gl.d(n8.a(this.b).a, this.c, this.b);
    }

    @Override // com.android.tools.r8.internal.B7
    public final AbstractC2359k7 a(AbstractC2359k7 abstractC2359k7, C0702m c0702m, InterfaceC3107u6 interfaceC3107u6) {
        return abstractC2359k7.a(interfaceC3107u6, this.b);
    }

    @Override // com.android.tools.r8.internal.B7
    public final com.android.tools.r8.ir.optimize.V a(C3550d0 c3550d0, C0753u c0753u, com.android.tools.r8.graph.o5 o5Var) {
        return com.android.tools.r8.ir.optimize.V.d;
    }
}
