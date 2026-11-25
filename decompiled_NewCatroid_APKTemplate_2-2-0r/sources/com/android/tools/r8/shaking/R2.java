package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.internal.C3405y10;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class R2 implements com.android.tools.r8.graph.Q0 {
    final /* synthetic */ C0702m a;
    final /* synthetic */ S2 b;

    R2(S2 s2, C0702m c0702m) {
        this.b = s2;
        this.a = c0702m;
    }

    @Override // com.android.tools.r8.graph.Q0
    public final C0722p1 a() {
        return this.a.a();
    }

    @Override // com.android.tools.r8.graph.Q0
    public final AbstractC0727q0 c(C0767v2 c0767v2) {
        return this.b.p ? ((C0680j) this.a.f()).d(c0767v2) : this.a.c(c0767v2);
    }

    @Override // com.android.tools.r8.graph.Q0
    public final com.android.tools.r8.graph.O f(C0767v2 c0767v2) {
        throw new C3405y10("Add support for multiple definitions with rule evaluation");
    }
}
