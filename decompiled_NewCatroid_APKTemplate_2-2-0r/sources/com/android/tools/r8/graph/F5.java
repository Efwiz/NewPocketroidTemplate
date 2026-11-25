package com.android.tools.r8.graph;

import com.android.tools.r8.AbstractC0403b;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.AbstractC1899eF;
import com.android.tools.r8.internal.AbstractC1915eM;
import com.android.tools.r8.internal.C2102gl;
import com.android.tools.r8.internal.C2145hI;
import com.android.tools.r8.internal.C2363kB;
import com.android.tools.r8.internal.C2409kl;
import com.android.tools.r8.internal.CE;
import com.android.tools.r8.internal.DE;
import com.android.tools.r8.internal.Z8;
import com.android.tools.r8.naming.AbstractC3649o0;
import com.android.tools.r8.origin.Origin;
import java.nio.ShortBuffer;
import java.util.Iterator;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class F5 extends W implements D, M2 {
    private static final F5 d = new F5();

    private F5() {
    }

    public static F5 H0() {
        return d;
    }

    @Override // com.android.tools.r8.graph.W
    public final boolean A0() {
        return true;
    }

    @Override // com.android.tools.r8.graph.M2
    public final C0759u2 B() {
        return null;
    }

    @Override // com.android.tools.r8.graph.W
    public final boolean C0() {
        return true;
    }

    @Override // com.android.tools.r8.graph.M2
    public final int D() {
        return 0;
    }

    @Override // com.android.tools.r8.graph.M2
    public final M0 F() {
        return null;
    }

    @Override // com.android.tools.r8.graph.W
    public final boolean F0() {
        return true;
    }

    @Override // com.android.tools.r8.graph.M2
    public final int V() {
        return 2;
    }

    @Override // com.android.tools.r8.graph.M2
    public final M2 a(o5 o5Var, C0645e5 c0645e5, C0722p1 c0722p1, boolean z) {
        return this;
    }

    @Override // com.android.tools.r8.graph.W, com.android.tools.r8.graph.AbstractC0594a1
    public final void a(com.android.tools.r8.dex.V v) {
    }

    @Override // com.android.tools.r8.graph.M2
    public final void a(com.android.tools.r8.dex.r rVar) {
    }

    @Override // com.android.tools.r8.graph.W
    public final void a(S s, J5 j5) {
    }

    @Override // com.android.tools.r8.graph.M2
    public final void a(C0702m c0702m, com.android.tools.r8.dex.L l, C2363kB c2363kB, o5 o5Var) {
    }

    @Override // com.android.tools.r8.graph.W
    public final void a(o5 o5Var, J5 j5) {
    }

    @Override // com.android.tools.r8.graph.M2
    public final int b(o5 o5Var) {
        int iJ0 = !o5Var.getAccessFlags().o() ? 1 : 0;
        Iterator<C0767v2> it = o5Var.D().iterator();
        while (it.hasNext()) {
            iJ0 += it.next().J0();
        }
        return iJ0;
    }

    @Override // com.android.tools.r8.graph.M2
    public final void c(o5 o5Var) {
    }

    @Override // com.android.tools.r8.graph.AbstractC0740s
    protected final boolean c(Object obj) {
        return this == obj;
    }

    @Override // com.android.tools.r8.graph.D
    public final int n0() {
        return 3;
    }

    @Override // com.android.tools.r8.graph.M2
    public final L2 o() {
        return L2.THROW_NULL;
    }

    @Override // com.android.tools.r8.graph.M2
    public final C0772w0.a[] s() {
        return new C0772w0.a[0];
    }

    @Override // com.android.tools.r8.graph.AbstractC0740s
    protected final int s0() {
        return System.identityHashCode(this);
    }

    @Override // com.android.tools.r8.graph.M2
    public final W t() {
        return this;
    }

    @Override // com.android.tools.r8.graph.W
    public final D t0() {
        return this;
    }

    @Override // com.android.tools.r8.graph.W
    public final String toString() {
        return "ThrowNullCode";
    }

    @Override // com.android.tools.r8.graph.W
    public final M2 u0() {
        return this;
    }

    @Override // com.android.tools.r8.graph.W
    public final int w0() {
        return 2;
    }

    @Override // com.android.tools.r8.graph.M2
    public final C0772w0.b[] z() {
        return new C0772w0.b[0];
    }

    @Override // com.android.tools.r8.graph.D, com.android.tools.r8.graph.M2
    public final void a(com.android.tools.r8.utils.structural.o oVar) {
        oVar.a(AbstractC0403b.b(3));
    }

    @Override // com.android.tools.r8.graph.W
    public final C2409kl a(o5 o5Var, C0702m c0702m, Origin origin, CE.a aVar) {
        return C2102gl.a(o5Var, c0702m, new E5(o5Var, null), origin).a(o5Var, aVar);
    }

    @Override // com.android.tools.r8.graph.W
    public final C2409kl a(o5 o5Var, o5 o5Var2, C0702m c0702m, AbstractC1476Wj abstractC1476Wj, C2145hI c2145hI, AbstractC1915eM abstractC1915eM, Origin origin, com.android.tools.r8.graph.proto.k kVar) {
        return C2102gl.a(c0702m, o5Var2, abstractC1476Wj, kVar, c2145hI, new E5(o5Var2, abstractC1915eM), origin).a(o5Var, new DE(c0702m.Y()));
    }

    @Override // com.android.tools.r8.graph.M2
    public final int a(o5 o5Var) {
        int iJ0 = !o5Var.getAccessFlags().o() ? 1 : 0;
        Iterator<C0767v2> it = o5Var.D().iterator();
        while (it.hasNext()) {
            iJ0 += it.next().J0();
        }
        return Math.max(iJ0, 1);
    }

    @Override // com.android.tools.r8.graph.D
    public final void a(o5 o5Var, Z8 z8, C0702m c0702m, AbstractC3649o0 abstractC3649o0, C2363kB c2363kB, AbstractC1899eF abstractC1899eF) {
        abstractC1899eF.a(1);
        abstractC1899eF.a(191);
        int iJ0 = !o5Var.getAccessFlags().o() ? 1 : 0;
        Iterator<C0767v2> it = o5Var.D().iterator();
        while (it.hasNext()) {
            iJ0 += it.next().J0();
        }
        abstractC1899eF.c(1, iJ0);
    }

    @Override // com.android.tools.r8.graph.M2
    public final void a(C0645e5 c0645e5, o5 o5Var, AbstractC1476Wj abstractC1476Wj, AbstractC1476Wj abstractC1476Wj2, C2363kB c2363kB, ShortBuffer shortBuffer) {
        new com.android.tools.r8.dex.code.Y(0, 0).a(c0645e5, o5Var, abstractC1476Wj, abstractC1476Wj2, c2363kB, shortBuffer);
        new com.android.tools.r8.dex.code.e4(0).a(c0645e5, o5Var, abstractC1476Wj, abstractC1476Wj2, c2363kB, shortBuffer);
    }

    @Override // com.android.tools.r8.graph.W
    public final String a(W0 w0, com.android.tools.r8.utils.P2 p2) {
        return "ThrowNullCode";
    }
}
