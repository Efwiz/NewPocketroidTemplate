package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.origin.Origin;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.z1, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC3473z1 extends com.android.tools.r8.graph.W {
    public abstract Consumer H0();

    public abstract InterfaceC3404y1 I0();

    @Override // com.android.tools.r8.graph.W
    public final C2409kl a(com.android.tools.r8.graph.o5 o5Var, C0702m c0702m, Origin origin, CE.a aVar) {
        return C2102gl.a(o5Var, c0702m, I0().a(o5Var, null), origin).a(o5Var, aVar);
    }

    @Override // com.android.tools.r8.graph.AbstractC0740s
    protected final boolean c(Object obj) {
        throw new C3405y10();
    }

    @Override // com.android.tools.r8.graph.AbstractC0740s
    protected final int s0() {
        throw new C3405y10();
    }

    @Override // com.android.tools.r8.graph.W
    public final String toString() {
        return getClass().getSimpleName();
    }

    @Override // com.android.tools.r8.graph.W
    public final int w0() {
        return Integer.MAX_VALUE;
    }

    @Override // com.android.tools.r8.graph.W
    public final C2409kl a(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2, C0702m c0702m, AbstractC1476Wj abstractC1476Wj, C2145hI c2145hI, AbstractC1915eM abstractC1915eM, Origin origin, com.android.tools.r8.graph.proto.k kVar) {
        return C2102gl.a(c0702m, o5Var2, abstractC1476Wj, kVar, c2145hI, I0().a(o5Var, abstractC1915eM), origin).a(o5Var, new DE(c0702m.Y()));
    }

    @Override // com.android.tools.r8.graph.W
    public final void a(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.J5 j5) {
        H0().accept(j5);
    }

    @Override // com.android.tools.r8.graph.W
    public final void a(com.android.tools.r8.graph.S s, com.android.tools.r8.graph.J5 j5) {
        H0().accept(j5);
    }

    @Override // com.android.tools.r8.graph.W
    public final String a(com.android.tools.r8.graph.W0 w0, com.android.tools.r8.utils.P2 p2) {
        return getClass().getSimpleName();
    }
}
