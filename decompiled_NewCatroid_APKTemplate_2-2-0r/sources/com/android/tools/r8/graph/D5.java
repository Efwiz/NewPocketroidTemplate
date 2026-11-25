package com.android.tools.r8.graph;

import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.AbstractC1915eM;
import com.android.tools.r8.internal.C2145hI;
import com.android.tools.r8.internal.C2363kB;
import com.android.tools.r8.internal.C2409kl;
import com.android.tools.r8.internal.C3405y10;
import com.android.tools.r8.internal.CE;
import com.android.tools.r8.origin.Origin;
import java.nio.ShortBuffer;
import java.util.Objects;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class D5 extends W implements M2 {
    private final C0767v2 d;

    private D5(C0767v2 c0767v2) {
        this.d = c0767v2;
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
        return 1;
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
        return 6;
    }

    @Override // com.android.tools.r8.graph.M2
    public final int a(o5 o5Var) {
        return 1;
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
    public final int b(o5 o5Var) {
        return 0;
    }

    @Override // com.android.tools.r8.graph.M2
    public final void c(o5 o5Var) {
    }

    @Override // com.android.tools.r8.graph.M2
    public final L2 o() {
        return L2.THROW_EXCEPTION;
    }

    @Override // com.android.tools.r8.graph.M2
    public final C0772w0.a[] s() {
        return new C0772w0.a[0];
    }

    @Override // com.android.tools.r8.graph.AbstractC0740s
    protected final int s0() {
        return Objects.hash(L2.THROW_EXCEPTION, Integer.valueOf(this.d.hashCode()));
    }

    @Override // com.android.tools.r8.graph.M2
    public final W t() {
        return this;
    }

    @Override // com.android.tools.r8.graph.W
    public final String toString() {
        return "ThrowExceptionCode";
    }

    @Override // com.android.tools.r8.graph.W
    public final M2 u0() {
        return this;
    }

    @Override // com.android.tools.r8.graph.W, com.android.tools.r8.graph.M2
    public final D5 v() {
        return this;
    }

    @Override // com.android.tools.r8.graph.W
    public final int w0() {
        return 6;
    }

    @Override // com.android.tools.r8.graph.M2
    public final C0772w0.b[] z() {
        return new C0772w0.b[0];
    }

    @Override // com.android.tools.r8.graph.AbstractC0740s
    protected final boolean c(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof D5) {
            return Objects.equals(this.d, ((D5) obj).d);
        }
        return false;
    }

    public static D5 a(C0767v2 c0767v2) {
        return new D5(c0767v2);
    }

    @Override // com.android.tools.r8.graph.M2
    public final void a(com.android.tools.r8.utils.structural.o oVar) {
        oVar.a(L2.THROW_EXCEPTION.hashCode());
        oVar.a(this.d);
    }

    @Override // com.android.tools.r8.graph.W
    public final C2409kl a(o5 o5Var, C0702m c0702m, Origin origin, CE.a aVar) {
        throw new C3405y10("Should not be called");
    }

    @Override // com.android.tools.r8.graph.W
    public final C2409kl a(o5 o5Var, o5 o5Var2, C0702m c0702m, AbstractC1476Wj abstractC1476Wj, C2145hI c2145hI, AbstractC1915eM abstractC1915eM, Origin origin, com.android.tools.r8.graph.proto.k kVar) {
        throw new C3405y10("Should not be called");
    }

    @Override // com.android.tools.r8.graph.M2
    public final void a(C0702m c0702m, com.android.tools.r8.dex.L l, C2363kB c2363kB, o5 o5Var) {
        C0684j2 c0684j2A = c2363kB.a().a(this.d, new C0767v2[0]);
        if (c0684j2A.b(c0702m, l)) {
            C0759u2 c0759u2A = c0702m.z().a(c0684j2A);
            c0759u2A.getClass();
            l.a(c0759u2A);
        }
    }

    @Override // com.android.tools.r8.graph.W
    public final void a(o5 o5Var, J5 j5) {
        throw new C3405y10("Should not be called");
    }

    @Override // com.android.tools.r8.graph.W
    public final void a(S s, J5 j5) {
        throw new C3405y10("Should not be called");
    }

    @Override // com.android.tools.r8.graph.M2
    public final void a(C0645e5 c0645e5, o5 o5Var, AbstractC1476Wj abstractC1476Wj, AbstractC1476Wj abstractC1476Wj2, C2363kB c2363kB, ShortBuffer shortBuffer) {
        new com.android.tools.r8.dex.code.P2(0, this.d).a(c0645e5, o5Var, abstractC1476Wj, abstractC1476Wj2, c2363kB, shortBuffer);
        new com.android.tools.r8.dex.code.M1(1, c2363kB.a().a(this.d, new C0767v2[0]), 0, 0, 0, 0, 0).a(c0645e5, o5Var, abstractC1476Wj, abstractC1476Wj2, c2363kB, shortBuffer);
        new com.android.tools.r8.dex.code.e4(0).a(c0645e5, o5Var, abstractC1476Wj, abstractC1476Wj2, c2363kB, shortBuffer);
    }

    @Override // com.android.tools.r8.graph.W
    public final String a(W0 w0, com.android.tools.r8.utils.P2 p2) {
        return "ThrowExceptionCode";
    }
}
