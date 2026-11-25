package com.android.tools.r8.internal;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class Y20 extends AbstractC3416yA implements InterfaceC1475Wi {
    final /* synthetic */ C1583a30 a;

    Y20(C1583a30 c1583a30) {
        this.a = c1583a30;
    }

    @Override // com.android.tools.r8.internal.InterfaceC1475Wi
    public final Object a(Object obj) {
        C2987sO c2987sO = (C2987sO) obj;
        AbstractC0936Ds.c(c2987sO, "it");
        C2380kO c2380kOD = this.a.d();
        C3125uO c3125uOG = c2987sO.g();
        if (!c3125uOG.a()) {
            throw new C1659b10();
        }
        c2380kOD.a(c3125uOG);
        return C2042g10.a;
    }
}
