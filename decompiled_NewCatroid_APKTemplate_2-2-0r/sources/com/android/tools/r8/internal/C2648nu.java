package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.nu, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2648nu extends AbstractC3416yA implements InterfaceC1475Wi {
    final /* synthetic */ C1610aO a;

    C2648nu(C1610aO c1610aO) {
        this.a = c1610aO;
    }

    @Override // com.android.tools.r8.internal.InterfaceC1475Wi
    public final Object a(Object obj) {
        EO eo = (EO) obj;
        AbstractC0936Ds.c(eo, "it");
        C1610aO c1610aO = this.a;
        C1012Gj c1012Gj = AbstractC1543Yu.j;
        FO foG = eo.g();
        if (!foG.a()) {
            throw new C1659b10();
        }
        c1610aO.a(c1012Gj, (AbstractC1045Hj) foG);
        return C2042g10.a;
    }
}
