package com.android.tools.r8.graph;

import com.android.tools.r8.internal.C2409kl;
import com.android.tools.r8.internal.C3405y10;
import com.android.tools.r8.internal.CE;
import com.android.tools.r8.origin.Origin;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class L3 extends W {
    private static final L3 d = new L3();

    private L3() {
    }

    public static L3 H0() {
        return d;
    }

    public static boolean a(W w) {
        return w == d;
    }

    @Override // com.android.tools.r8.graph.AbstractC0740s
    protected final boolean c(Object obj) {
        return this == obj;
    }

    @Override // com.android.tools.r8.graph.AbstractC0740s
    protected final int s0() {
        return System.identityHashCode(this);
    }

    @Override // com.android.tools.r8.graph.W
    public final String toString() {
        return "<invalid-code>";
    }

    @Override // com.android.tools.r8.graph.W
    public final int w0() {
        throw new C3405y10();
    }

    @Override // com.android.tools.r8.graph.W
    public final C2409kl a(o5 o5Var, C0702m c0702m, Origin origin, CE.a aVar) {
        throw new C3405y10();
    }

    @Override // com.android.tools.r8.graph.W
    public final void a(o5 o5Var, J5 j5) {
        throw new C3405y10();
    }

    @Override // com.android.tools.r8.graph.W
    public final void a(S s, J5 j5) {
        throw new C3405y10();
    }

    @Override // com.android.tools.r8.graph.W
    public final String a(W0 w0, com.android.tools.r8.utils.P2 p2) {
        StringBuilder sb = new StringBuilder();
        if (w0 != null) {
            sb.append(w0.r0()).append("\n");
        }
        return sb.append(this).toString();
    }
}
