package com.android.tools.r8.internal;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class A2 implements com.android.tools.r8.ir.optimize.info.m {
    final /* synthetic */ AbstractC1476Wj a;
    final /* synthetic */ com.android.tools.r8.graph.u5 b;
    final /* synthetic */ B2 c;

    A2(B2 b2, AbstractC1476Wj abstractC1476Wj, com.android.tools.r8.graph.u5 u5Var) {
        this.c = b2;
        this.a = abstractC1476Wj;
        this.b = u5Var;
    }

    @Override // com.android.tools.r8.ir.optimize.info.m
    public final void a(com.android.tools.r8.ir.optimize.info.k kVar) {
        B2 b2 = this.c;
        kVar.a(b2.f, b2.g, this.a);
    }

    @Override // com.android.tools.r8.ir.optimize.info.m
    public final void a(com.android.tools.r8.graph.W0 w0, com.android.tools.r8.ir.optimize.info.l lVar) {
        B2 b2 = this.c;
        com.android.tools.r8.ir.optimize.info.l lVarA = lVar.a(b2.f, b2.g, this.a);
        B2 b3 = this.c;
        lVarA.a(b3.f, b3.g, this.a, this.b);
        if (this.c.g.j(w0.getReference())) {
            com.android.tools.r8.graph.proto.k kVarI = this.c.g.i(w0.getReference());
            kVarI.getClass();
            lVar.a(this.c.f, new com.android.tools.r8.graph.w5(kVarI));
        }
    }
}
