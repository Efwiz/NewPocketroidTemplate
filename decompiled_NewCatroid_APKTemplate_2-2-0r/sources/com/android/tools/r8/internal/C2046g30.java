package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.g30, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2046g30 extends AbstractC3416yA implements InterfaceC1475Wi {
    final /* synthetic */ C2586n30 a;

    C2046g30(C2586n30 c2586n30) {
        this.a = c2586n30;
    }

    @Override // com.android.tools.r8.internal.InterfaceC1475Wi
    public final Object a(Object obj) {
        C2152hO c2152hO = (C2152hO) obj;
        AbstractC0936Ds.c(c2152hO, "it");
        C3285wO c3285wOD = this.a.d();
        C2232iO c2232iOG = c2152hO.g();
        if (!c2232iOG.a()) {
            throw new C1659b10();
        }
        c3285wOD.a(c2232iOG);
        return C2042g10.a;
    }
}
