package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0664h;
import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.origin.Origin;

/* JADX INFO: renamed from: com.android.tools.r8.internal.ol, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2726ol implements InterfaceC2802pl {
    private final C0702m a;

    private C2726ol(C0702m c0702m) {
        C0702m c0702mC = C0702m.c(C0664h.a(((C0680j) c0702m.f()).b(), com.android.tools.r8.synthesis.y.d()));
        c0702mC.b(c0702m.E());
        c0702mC.a(c0702m.n());
        this.a = c0702mC;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2802pl
    public final C2409kl a(com.android.tools.r8.graph.o5 o5Var) {
        com.android.tools.r8.graph.W wA1 = o5Var.e().a1();
        C0702m c0702m = this.a;
        Origin origin = o5Var.getOrigin();
        wA1.getClass();
        return wA1.a(o5Var, c0702m, origin, new CE.a(c0702m.Y()));
    }

    @Override // com.android.tools.r8.internal.InterfaceC2802pl
    public final void a(C1013Gk c1013Gk) {
        this.a.b((AbstractC1476Wj) c1013Gk);
    }
}
