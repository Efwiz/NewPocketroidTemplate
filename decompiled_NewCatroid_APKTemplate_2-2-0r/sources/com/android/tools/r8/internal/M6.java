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
public class M6 extends B7 implements X8 {
    private final C0767v2 b;
    private final boolean c;

    public M6(C0767v2 c0767v2) {
        this(c0767v2, false);
    }

    @Override // com.android.tools.r8.internal.B7
    public final boolean B() {
        return true;
    }

    @Override // com.android.tools.r8.internal.B7
    public final boolean S() {
        return true;
    }

    @Override // com.android.tools.r8.internal.X8
    public B7 a(C0767v2 c0767v2) {
        return new M6(c0767v2, this.c);
    }

    @Override // com.android.tools.r8.internal.B7
    public final M6 b() {
        return this;
    }

    @Override // com.android.tools.r8.internal.X8
    public C0767v2 getType() {
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
        return 192;
    }

    public M6(C0767v2 c0767v2, boolean z) {
        this.b = c0767v2;
        this.c = z;
    }

    @Override // com.android.tools.r8.internal.B7
    public final int a(B7 b7, com.android.tools.r8.utils.structural.a aVar, com.android.tools.r8.graph.C c) {
        C0767v2 c0767v2 = this.b;
        C0767v2 c0767v3 = ((M6) b7).b;
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
        C0767v2 c0767v2 = this.b;
        abstractC1476Wj.getClass();
        abstractC1899eF.c(192, abstractC3649o0.d(abstractC1476Wj.a(AbstractC1476Wj.e(), c0767v2)));
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(C2757p8 c2757p8) {
        c2757p8.a(this);
    }

    @Override // com.android.tools.r8.internal.B7
    void a(com.android.tools.r8.graph.J5 j5, ListIterator listIterator) {
        j5.a(this.b, this.c);
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(C2102gl c2102gl, N8 n8, A8 a8) {
        n8.e();
        a(c2102gl, n8.a(this.b));
    }

    void a(C2102gl c2102gl, H8 h8) {
        c2102gl.a(h8.a, this.b);
    }

    @Override // com.android.tools.r8.internal.B7
    public final com.android.tools.r8.ir.optimize.V a(C3550d0 c3550d0, C0753u c0753u, com.android.tools.r8.graph.o5 o5Var) {
        return c3550d0.a(this.b, o5Var);
    }

    @Override // com.android.tools.r8.internal.B7
    public final AbstractC2359k7 a(AbstractC2359k7 abstractC2359k7, C0702m c0702m, InterfaceC3107u6 interfaceC3107u6) {
        return abstractC2359k7.a(c0702m, interfaceC3107u6, c0702m.a().Z1).a(interfaceC3107u6, this.b);
    }
}
