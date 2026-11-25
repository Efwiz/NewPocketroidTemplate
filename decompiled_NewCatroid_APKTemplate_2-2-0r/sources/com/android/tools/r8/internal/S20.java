package com.android.tools.r8.internal;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class S20 extends AbstractC3416yA implements InterfaceC1475Wi {
    final /* synthetic */ U20 a;

    S20(U20 u20) {
        this.a = u20;
    }

    @Override // com.android.tools.r8.internal.InterfaceC1475Wi
    public final Object a(Object obj) {
        C2233iP c2233iP = (C2233iP) obj;
        AbstractC0936Ds.c(c2233iP, "it");
        C1921eO c1921eOC = this.a.c();
        C2303jP c2303jPG = c2233iP.g();
        if (!c2303jPG.a()) {
            throw new C1659b10();
        }
        c1921eOC.a(c2303jPG);
        return C2042g10.a;
    }
}
