package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.uu, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3168uu extends com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.m {
    final /* synthetic */ VO c;
    final /* synthetic */ Q20 d;

    C3168uu(VO vo, Q20 q20) {
        this.c = vo;
        this.d = q20;
    }

    @Override // com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.m
    public final void a(boolean z) {
        if (z) {
            this.c.a(AbstractC1543Yu.g, Boolean.TRUE);
        }
    }

    @Override // com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.m
    public final void a(C3321wx c3321wx) {
        AbstractC0936Ds.c(c3321wx, "annotation");
        VO vo = this.c;
        C1012Gj c1012Gj = AbstractC1543Yu.f;
        XN xnG = R20.a(c3321wx, this.d.a()).g();
        if (!xnG.a()) {
            throw new C1659b10();
        }
        vo.a(c1012Gj, (AbstractC1045Hj) xnG);
    }
}
