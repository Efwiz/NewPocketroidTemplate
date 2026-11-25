package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0744s2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0753u;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.ir.optimize.C3550d0;
import com.android.tools.r8.naming.AbstractC3649o0;
import java.util.ListIterator;

/* JADX INFO: renamed from: com.android.tools.r8.internal.h7, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2134h7 extends B7 {
    static final /* synthetic */ boolean d = true;
    private final AbstractC0744s2 b;
    private final AbstractC2525mG c;

    public C2134h7(AbstractC0744s2 abstractC0744s2, AbstractC2525mG abstractC2525mG) {
        this.b = abstractC0744s2;
        this.c = abstractC2525mG;
    }

    public final AbstractC0744s2 T() {
        return this.b;
    }

    public final AbstractC2525mG U() {
        return this.c;
    }

    @Override // com.android.tools.r8.internal.B7
    public final int a(B7 b7, com.android.tools.r8.utils.structural.a aVar, com.android.tools.r8.graph.C c) {
        return aVar.a(this.b, ((C2134h7) b7).b);
    }

    @Override // com.android.tools.r8.internal.B7
    public final C2134h7 f() {
        return this;
    }

    @Override // com.android.tools.r8.internal.B7
    public final boolean x() {
        return true;
    }

    @Override // com.android.tools.r8.internal.B7
    public final int z() {
        return 202;
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(com.android.tools.r8.utils.structural.o oVar) {
        oVar.a(this.b);
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, C0722p1 c0722p1, AbstractC1476Wj abstractC1476Wj, AbstractC2331jn abstractC2331jn, AbstractC3649o0 abstractC3649o0, C2363kB c2363kB, AbstractC1899eF abstractC1899eF) {
        throw new C3405y10("CfDexItemBasedConstString instructions should always be rewritten into CfConstString");
    }

    @Override // com.android.tools.r8.internal.B7
    public final com.android.tools.r8.ir.optimize.V a(C3550d0 c3550d0, C0753u c0753u, com.android.tools.r8.graph.o5 o5Var) {
        return com.android.tools.r8.ir.optimize.V.d;
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(C2757p8 c2757p8) {
        c2757p8.a(this);
    }

    @Override // com.android.tools.r8.internal.B7
    final void a(com.android.tools.r8.graph.J5 j5, ListIterator listIterator) {
        if (this.c.f()) {
            if (!d) {
                AbstractC0744s2 abstractC0744s2 = this.b;
                abstractC0744s2.getClass();
                if (!(abstractC0744s2 instanceof C0767v2)) {
                    throw new AssertionError();
                }
            }
            j5.f(this.b.w0());
        }
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(C2102gl c2102gl, N8 n8, A8 a8) {
        int i = n8.a(c2102gl.o.a().X1).a;
        c2102gl.a(new C2477lf(c2102gl.a(i, AbstractC3472z00.b((C0702m<?>) c2102gl.o, WH.b()), 2), this.b, this.c));
    }

    @Override // com.android.tools.r8.internal.B7
    public final AbstractC2359k7 a(AbstractC2359k7 abstractC2359k7, C0702m c0702m, InterfaceC3107u6 interfaceC3107u6) {
        return abstractC2359k7.a(interfaceC3107u6, c0702m.a().X1);
    }
}
