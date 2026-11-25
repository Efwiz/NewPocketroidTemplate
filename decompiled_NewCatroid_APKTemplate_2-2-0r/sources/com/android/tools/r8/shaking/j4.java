package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.C0702m;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class j4 implements com.android.tools.r8.ir.optimize.info.m {
    final /* synthetic */ k4 a;

    j4(k4 k4Var) {
        this.a = k4Var;
    }

    @Override // com.android.tools.r8.ir.optimize.info.m
    public final void a(com.android.tools.r8.ir.optimize.info.k kVar) {
        C0702m c0702m = this.a.a;
        kVar.a(c0702m, c0702m.E(), this.a.f);
    }

    @Override // com.android.tools.r8.ir.optimize.info.m
    public final void a(com.android.tools.r8.graph.W0 w0, com.android.tools.r8.ir.optimize.info.l lVar) {
        C0702m c0702m = this.a.a;
        lVar.a(c0702m, c0702m.E(), this.a.f);
    }
}
