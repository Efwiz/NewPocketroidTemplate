package com.android.tools.r8.utils;

import com.android.tools.r8.graph.C0684j2;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
final class C0 extends com.android.tools.r8.graph.N0 {
    final /* synthetic */ N f;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    C0(int i, C0684j2 c0684j2, N n) {
        super(i, c0684j2);
        this.f = n;
    }

    @Override // com.android.tools.r8.graph.N0, com.android.tools.r8.graph.I0
    public final void a(com.android.tools.r8.graph.F0 f0) {
        super.a(f0);
        this.f.a(f0.x0());
    }
}
