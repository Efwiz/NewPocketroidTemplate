package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0699l2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0753u;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.ir.optimize.C3550d0;
import com.android.tools.r8.naming.AbstractC3649o0;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class Y6 extends B7 implements X8 {
    static final /* synthetic */ boolean c = true;
    private final C1177Mc b;

    public Y6(int i, C0759u2 c0759u2, C0767v2 c0767v2, C0699l2 c0699l2, ArrayList arrayList) {
        boolean z = c;
        if (!z && i < 0) {
            throw new AssertionError();
        }
        if (!z && c0759u2 == null) {
            throw new AssertionError();
        }
        if (!z && c0767v2 == null) {
            throw new AssertionError();
        }
        if (!z && c0699l2 == null) {
            throw new AssertionError();
        }
        if (!z && !arrayList.isEmpty()) {
            throw new AssertionError();
        }
        this.b = new C1177Mc(i, c0759u2, c0767v2, c0699l2, arrayList);
    }

    public static Y6 a(int i, C1064Ic c1064Ic, com.android.tools.r8.graph.M3 m3, C0767v2 c0767v2) {
        String strE = c1064Ic.e();
        String strD = c1064Ic.d();
        C0699l2 c0699l2A = C0699l2.a(c1064Ic.a(), m3, c0767v2);
        int iB = c1064Ic.b();
        ArrayList arrayList = new ArrayList(iB);
        for (int i2 = 0; i2 < iB; i2++) {
            arrayList.add(com.android.tools.r8.graph.B2.a(c1064Ic.a(i2), m3, c0767v2));
        }
        return new Y6(i, m3.d(strE), m3.e(strD), c0699l2A, arrayList);
    }

    @Override // com.android.tools.r8.internal.B7
    public final boolean S() {
        return true;
    }

    public final C0699l2 T() {
        return this.b.a();
    }

    public final List U() {
        return this.b.b();
    }

    public final C1177Mc V() {
        return this.b;
    }

    @Override // com.android.tools.r8.internal.B7
    public final Y6 d() {
        return this;
    }

    @Override // com.android.tools.r8.internal.X8
    public final C0767v2 getType() {
        return this.b.d();
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
        return 206;
    }

    @Override // com.android.tools.r8.internal.B7
    public final int a(B7 b7, com.android.tools.r8.utils.structural.a aVar, com.android.tools.r8.graph.C c2) {
        return this.b.a(((Y6) b7).b, aVar);
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(com.android.tools.r8.utils.structural.o oVar) {
        this.b.a(oVar);
    }

    @Override // com.android.tools.r8.internal.X8
    public final B7 a(C0767v2 c0767v2) {
        throw new Z00();
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, C0722p1 c0722p1, AbstractC1476Wj abstractC1476Wj, AbstractC2331jn abstractC2331jn, AbstractC3649o0 abstractC3649o0, C2363kB c2363kB, AbstractC1899eF abstractC1899eF) {
        C0699l2 c0699l2A = this.b.a();
        com.android.tools.r8.graph.I5 i5 = com.android.tools.r8.graph.I5.b;
        C0699l2 c0699l2A2 = c2363kB.a(c0699l2A, i5, o5Var);
        List listA = c2363kB.a(this.b.b(), i5, o5Var);
        Object[] objArr = new Object[listA.size()];
        for (int i = 0; i < listA.size(); i++) {
            objArr[i] = J7.a((com.android.tools.r8.graph.B2) listA.get(i), abstractC3649o0);
        }
        String string = this.b.c().toString();
        C0767v2 c0767v2D = this.b.d();
        abstractC1476Wj.getClass();
        abstractC1899eF.a(new C1064Ic(string, abstractC3649o0.a(c0722p1, abstractC1476Wj.a(AbstractC1476Wj.e(), c0767v2D)).b1(), c0699l2A2.a(abstractC3649o0), objArr));
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(C2757p8 c2757p8) {
        c2757p8.a(this);
    }

    @Override // com.android.tools.r8.internal.B7
    final void a(com.android.tools.r8.graph.J5 j5, ListIterator listIterator) {
        j5.f(this.b.d());
        j5.a(this.b.a(), com.android.tools.r8.graph.I5.b);
        if (!c && !this.b.b().isEmpty()) {
            throw new AssertionError();
        }
    }

    @Override // com.android.tools.r8.internal.B7
    public final void a(C2102gl c2102gl, N8 n8, A8 a8) {
        throw new C1367Tb("Unsupported dynamic constant (not desugaring)");
    }

    @Override // com.android.tools.r8.internal.B7
    public final com.android.tools.r8.ir.optimize.V a(C3550d0 c3550d0, C0753u c0753u, com.android.tools.r8.graph.o5 o5Var) {
        throw new C3405y10();
    }

    @Override // com.android.tools.r8.internal.B7
    public final AbstractC2359k7 a(AbstractC2359k7 abstractC2359k7, C0702m c0702m, InterfaceC3107u6 interfaceC3107u6) {
        return abstractC2359k7.a(interfaceC3107u6, c0702m.a().m2);
    }
}
