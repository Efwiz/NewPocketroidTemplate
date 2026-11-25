package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.origin.Origin;

/* JADX INFO: renamed from: com.android.tools.r8.internal.aL, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1606aL extends com.android.tools.r8.graph.W {
    private final ZK d;
    final /* synthetic */ C2225iL e;

    C1606aL(C2225iL c2225iL, ZK zk) {
        this.e = c2225iL;
        this.d = zk;
    }

    @Override // com.android.tools.r8.graph.W
    public final String a(com.android.tools.r8.graph.W0 w0, com.android.tools.r8.utils.P2 p2) {
        return null;
    }

    @Override // com.android.tools.r8.graph.AbstractC0740s
    protected final boolean c(Object obj) {
        return this.d.equals(obj);
    }

    @Override // com.android.tools.r8.graph.AbstractC0740s
    protected final int s0() {
        return this.d.hashCode();
    }

    @Override // com.android.tools.r8.graph.W
    public final String toString() {
        return this.d.toString();
    }

    @Override // com.android.tools.r8.graph.W
    public final int w0() {
        return Integer.MAX_VALUE;
    }

    @Override // com.android.tools.r8.graph.W
    public final int x0() {
        return Integer.MAX_VALUE;
    }

    @Override // com.android.tools.r8.graph.W
    public final C2409kl a(com.android.tools.r8.graph.o5 o5Var, C0702m c0702m, Origin origin, CE.a aVar) {
        return C2102gl.a(o5Var, c0702m, new C2066gL(this.e, this.d, o5Var.getReference()), origin).a(o5Var, aVar);
    }

    @Override // com.android.tools.r8.graph.W
    public final void a(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.J5 j5) {
        throw new C3405y10();
    }

    @Override // com.android.tools.r8.graph.W
    public final void a(com.android.tools.r8.graph.S s, com.android.tools.r8.graph.J5 j5) {
        throw new C3405y10();
    }
}
