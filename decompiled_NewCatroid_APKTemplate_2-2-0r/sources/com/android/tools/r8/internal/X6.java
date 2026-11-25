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
public class X6 extends B7 implements X8 {
    static final /* synthetic */ boolean d = true;
    private final C0767v2 b;
    private final boolean c;

    public X6(C0767v2 c0767v2) {
        this(c0767v2, false);
    }

    @Override // com.android.tools.r8.internal.B7
    public final boolean S() {
        return true;
    }

    @Override // com.android.tools.r8.internal.B7
    public final int a(B7 b7, com.android.tools.r8.utils.structural.a aVar, com.android.tools.r8.graph.C c) {
        C0767v2 c0767v2 = this.b;
        C0767v2 c0767v3 = ((X6) b7).b;
        c0767v2.getClass();
        return aVar.a(c0767v2, c0767v3);
    }

    @Override // com.android.tools.r8.internal.B7
    public final X6 c() {
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
        return 200;
    }

    public X6(C0767v2 c0767v2, boolean z) {
        boolean z2 = d;
        if (!z2 && c0767v2.W0()) {
            throw new AssertionError();
        }
        if (!z2 && c0767v2.Y0()) {
            throw new AssertionError();
        }
        this.b = c0767v2;
        this.c = z;
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(com.android.tools.r8.utils.structural.o oVar) {
        C0767v2 c0767v2 = this.b;
        c0767v2.getClass();
        oVar.a(c0767v2);
    }

    @Override // com.android.tools.r8.internal.X8
    public final B7 a(C0767v2 c0767v2) {
        return new X6(c0767v2, false);
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, C0722p1 c0722p1, AbstractC1476Wj abstractC1476Wj, AbstractC2331jn abstractC2331jn, AbstractC3649o0 abstractC3649o0, C2363kB c2363kB, AbstractC1899eF abstractC1899eF) {
        abstractC1899eF.a(CZ.d(a(abstractC1476Wj, abstractC3649o0)));
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(C2757p8 c2757p8) {
        c2757p8.a(this);
    }

    private String a(AbstractC1476Wj abstractC1476Wj, AbstractC3649o0 abstractC3649o0) {
        C0767v2 c0767v2D = abstractC1476Wj.d(this.b);
        char cC1 = c0767v2D.c1();
        if (cC1 != 'L' && cC1 != '[') {
            throw new C3405y10("Unexpected type in const-class: " + c0767v2D);
        }
        return abstractC3649o0.d(c0767v2D);
    }

    @Override // com.android.tools.r8.internal.B7
    final void a(com.android.tools.r8.graph.J5 j5, ListIterator listIterator) {
        j5.a(this.b, listIterator, this.c);
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(C2102gl c2102gl, N8 n8, A8 a8) {
        c2102gl.b(n8.a(c2102gl.o.a().m2).a, this.b);
    }

    @Override // com.android.tools.r8.internal.B7
    public final com.android.tools.r8.ir.optimize.V a(C3550d0 c3550d0, C0753u c0753u, com.android.tools.r8.graph.o5 o5Var) {
        return c3550d0.b(this.b, o5Var);
    }

    @Override // com.android.tools.r8.internal.B7
    public final AbstractC2359k7 a(AbstractC2359k7 abstractC2359k7, C0702m c0702m, InterfaceC3107u6 interfaceC3107u6) {
        return abstractC2359k7.a(interfaceC3107u6, c0702m.a().m2);
    }
}
