package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.ru, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2952ru extends AbstractC3416yA implements InterfaceC1475Wi {
    final /* synthetic */ BO a;

    C2952ru(BO bo) {
        this.a = bo;
    }

    @Override // com.android.tools.r8.internal.InterfaceC1475Wi
    public final Object a(Object obj) {
        EO eo = (EO) obj;
        AbstractC0936Ds.c(eo, "it");
        BO bo = this.a;
        C1012Gj c1012Gj = AbstractC1543Yu.n;
        FO foG = eo.g();
        if (!foG.a()) {
            throw new C1659b10();
        }
        bo.a(c1012Gj, (AbstractC1045Hj) foG);
        return C2042g10.a;
    }
}
