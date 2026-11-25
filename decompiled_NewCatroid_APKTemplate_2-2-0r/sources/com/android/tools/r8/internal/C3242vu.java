package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.vu, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3242vu extends com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.n {
    final /* synthetic */ C1691bP c;
    final /* synthetic */ Q20 d;

    C3242vu(C1691bP c1691bP, Q20 q20) {
        this.c = c1691bP;
        this.d = q20;
    }

    @Override // com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.n
    public final void a(C3321wx c3321wx) {
        AbstractC0936Ds.c(c3321wx, "annotation");
        C1691bP c1691bP = this.c;
        C1012Gj c1012Gj = AbstractC1543Yu.h;
        XN xnG = R20.a(c3321wx, this.d.a()).g();
        if (!xnG.a()) {
            throw new C1659b10();
        }
        c1691bP.a(c1012Gj, (AbstractC1045Hj) xnG);
    }
}
