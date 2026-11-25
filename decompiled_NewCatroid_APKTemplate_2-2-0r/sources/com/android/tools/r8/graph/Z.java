package com.android.tools.r8.graph;

import com.android.tools.r8.AbstractC0403b;
import com.android.tools.r8.dex.code.C0488p3;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.AbstractC1899eF;
import com.android.tools.r8.internal.AbstractC1915eM;
import com.android.tools.r8.internal.B7;
import com.android.tools.r8.internal.C2051g8;
import com.android.tools.r8.internal.C2102gl;
import com.android.tools.r8.internal.C2145hI;
import com.android.tools.r8.internal.C2363kB;
import com.android.tools.r8.internal.C2409kl;
import com.android.tools.r8.internal.C3186v8;
import com.android.tools.r8.internal.CE;
import com.android.tools.r8.internal.DE;
import com.android.tools.r8.internal.EnumC1812d20;
import com.android.tools.r8.internal.EnumC1953et;
import com.android.tools.r8.internal.I7;
import com.android.tools.r8.internal.M7;
import com.android.tools.r8.internal.O7;
import com.android.tools.r8.internal.Z8;
import com.android.tools.r8.naming.AbstractC3649o0;
import com.android.tools.r8.origin.Origin;
import java.nio.ShortBuffer;
import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class Z extends W implements D, M2 {
    private static final Z d = new Z();
    static final /* synthetic */ boolean e = true;
    public static final /* synthetic */ int f = 0;

    private Z() {
    }

    private static boolean b(C0702m c0702m, o5 o5Var) {
        I7 i7L;
        if (!o5Var.e().C1()) {
            return false;
        }
        W wA1 = o5Var.e().a1();
        if (!wA1.z0()) {
            return false;
        }
        C0753u c0753uK = wA1.K();
        if (!o5Var.e().C1() || !c0753uK.J0().isEmpty() || !c0753uK.Q0().isEmpty() || c0753uK.I0().size() > 6) {
            return false;
        }
        C0722p1 c0722p1A = c0702m.a();
        Iterator<B7> it = c0753uK.I0().iterator();
        O7 o7R = ((B7) com.android.tools.r8.utils.L1.b(it, o5Var.a().n1() == c0722p1A.Z1 ? new Predicate() { // from class: com.android.tools.r8.graph.Z$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return Z.a((B7) obj);
            }
        } : new Predicate() { // from class: com.android.tools.r8.graph.Z$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return Z.b((B7) obj);
            }
        })).r();
        if (o7R != null && o7R.U() == 0 && (i7L = it.next().l()) != null && i7L.T().b(c0722p1A) && i7L.T() == c0722p1A.a(o5Var.a().n1(), new C0767v2[0])) {
            B7 next = it.next();
            next.getClass();
            return next instanceof C3186v8;
        }
        return false;
    }

    private static int d(o5 o5Var) {
        int iJ0 = !o5Var.getAccessFlags().o() ? 1 : 0;
        Iterator<C0767v2> it = o5Var.D().iterator();
        while (it.hasNext()) {
            iJ0 += it.next().J0();
        }
        return iJ0;
    }

    @Override // com.android.tools.r8.graph.M2
    public final C0759u2 B() {
        return null;
    }

    @Override // com.android.tools.r8.graph.M2
    public final int D() {
        return 1;
    }

    @Override // com.android.tools.r8.graph.M2
    public final M0 F() {
        return null;
    }

    @Override // com.android.tools.r8.graph.M2
    public final int V() {
        return 4;
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

    @Override // com.android.tools.r8.graph.M2
    public final void c(o5 o5Var) {
    }

    @Override // com.android.tools.r8.graph.AbstractC0740s
    protected final boolean c(Object obj) {
        return this == obj;
    }

    @Override // com.android.tools.r8.graph.D
    public final int n0() {
        return 2;
    }

    @Override // com.android.tools.r8.graph.M2
    public final L2 o() {
        return L2.DEFAULT_INSTANCE_INITIALIZER;
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
        return "DefaultInstanceInitializerCode";
    }

    @Override // com.android.tools.r8.graph.W
    public final M2 u0() {
        return this;
    }

    @Override // com.android.tools.r8.graph.W
    public final int w0() {
        return 4;
    }

    @Override // com.android.tools.r8.graph.M2
    public final C0772w0.b[] z() {
        return new C0772w0.b[0];
    }

    @Override // com.android.tools.r8.graph.W
    public final void a(o5 o5Var, J5 j5) {
        j5.a(j5.a.a().a(o5Var.a().n1(), new C0767v2[0]));
    }

    @Override // com.android.tools.r8.graph.W
    public final void a(S s, J5 j5) {
        j5.a(j5.a.a().a(s.a().n1(), new C0767v2[0]));
    }

    public static void a(C0702m c0702m, o5 o5Var) {
        if (b(c0702m, o5Var)) {
            o5Var.a(d, c0702m);
        }
    }

    public static void a(C0702m c0702m, C0767v2 c0767v2, o5 o5Var) {
        W0 w0E = o5Var.e();
        if (!e) {
            W wA1 = w0E.a1();
            wA1.getClass();
            if (!(wA1 instanceof Z)) {
                throw new AssertionError();
            }
        }
        o5Var.a(new C0753u(o5Var.t(), 1, d(o5Var), Arrays.asList(new O7(EnumC1812d20.OBJECT, 0), new I7(183, c0702m.a().a(c0767v2, new C0767v2[0]), false), new C3186v8())), c0702m);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean a(B7 b7) {
        b7.getClass();
        return ((b7 instanceof M7) || (b7 instanceof C2051g8)) ? false : true;
    }

    @Override // com.android.tools.r8.graph.D, com.android.tools.r8.graph.M2
    public final void a(com.android.tools.r8.utils.structural.o oVar) {
        oVar.a(AbstractC0403b.b(2));
    }

    @Override // com.android.tools.r8.graph.W
    public final C2409kl a(o5 o5Var, C0702m c0702m, Origin origin, CE.a aVar) {
        return C2102gl.a(o5Var, c0702m, new Y(c0702m.E().a((AbstractC1476Wj) null, o5Var.getReference()), null), origin).a(o5Var, aVar);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean b(B7 b7) {
        b7.getClass();
        return !(b7 instanceof M7);
    }

    @Override // com.android.tools.r8.graph.M2
    public final int b(o5 o5Var) {
        return d(o5Var);
    }

    @Override // com.android.tools.r8.graph.W
    public final C2409kl a(o5 o5Var, o5 o5Var2, C0702m c0702m, AbstractC1476Wj abstractC1476Wj, C2145hI c2145hI, AbstractC1915eM abstractC1915eM, Origin origin, com.android.tools.r8.graph.proto.k kVar) {
        return C2102gl.a(c0702m, o5Var2, abstractC1476Wj, kVar, c2145hI, new Y(c0702m.E().a((AbstractC1476Wj) null, o5Var2.getReference()), abstractC1915eM), origin).a(o5Var, new DE(c0702m.Y()));
    }

    @Override // com.android.tools.r8.graph.M2
    public final void a(C0702m c0702m, com.android.tools.r8.dex.L l, C2363kB c2363kB, o5 o5Var) {
        C0684j2 c0684j2A = c2363kB.a().a(o5Var.a().n1(), new C0767v2[0]);
        AbstractC1476Wj abstractC1476WjE = c0702m.E();
        abstractC1476WjE.getClass();
        C0684j2 c0684j2 = (C0684j2) abstractC1476WjE.a(c0684j2A, o5Var.getReference(), EnumC1953et.c, (AbstractC1476Wj) null).b();
        if (c0684j2.b(c0702m, l)) {
            C0759u2 c0759u2A = c0702m.z().a(c0684j2);
            c0759u2A.getClass();
            l.a(c0759u2A);
        }
    }

    @Override // com.android.tools.r8.graph.M2
    public final int a(o5 o5Var) {
        return d(o5Var);
    }

    @Override // com.android.tools.r8.graph.D
    public final void a(o5 o5Var, Z8 z8, C0702m c0702m, AbstractC3649o0 abstractC3649o0, C2363kB c2363kB, AbstractC1899eF abstractC1899eF) {
        abstractC1899eF.d(25, 0);
        abstractC1899eF.a(183, abstractC3649o0.d(o5Var.a().n1()), "<init>", "()V", false);
        abstractC1899eF.a(177);
        abstractC1899eF.c(1, d(o5Var));
    }

    @Override // com.android.tools.r8.graph.M2
    public final void a(C0645e5 c0645e5, o5 o5Var, AbstractC1476Wj abstractC1476Wj, AbstractC1476Wj abstractC1476Wj2, C2363kB c2363kB, ShortBuffer shortBuffer) {
        C0684j2 c0684j2A = c0645e5.a().a(o5Var.a().n1(), new C0767v2[0]);
        abstractC1476Wj.getClass();
        new com.android.tools.r8.dex.code.M1(1, (C0684j2) abstractC1476Wj.a(c0684j2A, o5Var.getReference(), EnumC1953et.c, (AbstractC1476Wj) null).b(), 0, 0, 0, 0, 0).a(c0645e5, o5Var, abstractC1476Wj, abstractC1476Wj2, c2363kB, shortBuffer);
        new C0488p3().a(c0645e5, o5Var, abstractC1476Wj, abstractC1476Wj2, c2363kB, shortBuffer);
    }

    @Override // com.android.tools.r8.graph.W
    public final String a(W0 w0, com.android.tools.r8.utils.P2 p2) {
        return "DefaultInstanceInitializerCode";
    }
}
