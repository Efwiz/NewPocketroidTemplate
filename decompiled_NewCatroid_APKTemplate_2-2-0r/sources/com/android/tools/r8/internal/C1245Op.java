package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Op, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
class C1245Op extends AbstractC1355Sp {
    final /* synthetic */ C1329Rp f;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    C1245Op(C1329Rp c1329Rp) {
        super(c1329Rp.i);
        this.f = c1329Rp;
        this.b = c1329Rp.k();
    }

    @Override // com.android.tools.r8.internal.AbstractC1355Sp
    final void d() {
        C1020Gp c1020Gp = this.b;
        C1020Gp c1020Gp2 = c1020Gp.d;
        if ((c1020Gp.e & Integer.MIN_VALUE) == 0) {
            while ((c1020Gp2.e & 1073741824) == 0) {
                c1020Gp2 = c1020Gp2.c;
            }
        }
        this.b = c1020Gp2;
        C1329Rp c1329Rp = this.f;
        if (c1329Rp.e || c1020Gp2 == null || c1329Rp.i.c(c1020Gp2.a, c1329Rp.c) < 0) {
            return;
        }
        this.b = null;
    }

    @Override // com.android.tools.r8.internal.AbstractC1355Sp
    final void g() {
        C1020Gp c1020GpE = this.a.e();
        this.a = c1020GpE;
        C1329Rp c1329Rp = this.f;
        if (c1329Rp.d || c1020GpE == null || c1329Rp.i.c(c1020GpE.a, c1329Rp.b) >= 0) {
            return;
        }
        this.a = null;
    }
}
