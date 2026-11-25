package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.cr, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
class C1796cr extends AbstractC2113gr {
    final /* synthetic */ C2028fr f;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    C1796cr(C2028fr c2028fr) {
        super(c2028fr.i);
        this.f = c2028fr;
        this.b = c2028fr.k();
    }

    @Override // com.android.tools.r8.internal.AbstractC2113gr
    final void d() {
        C1431Uq c1431Uq = this.b;
        C1431Uq c1431Uq2 = c1431Uq.d;
        if ((c1431Uq.e & Integer.MIN_VALUE) == 0) {
            while ((c1431Uq2.e & 1073741824) == 0) {
                c1431Uq2 = c1431Uq2.c;
            }
        }
        this.b = c1431Uq2;
        C2028fr c2028fr = this.f;
        if (c2028fr.e || c1431Uq2 == null || c2028fr.i.c(c1431Uq2.a, c2028fr.c) < 0) {
            return;
        }
        this.b = null;
    }

    @Override // com.android.tools.r8.internal.AbstractC2113gr
    final void g() {
        C1431Uq c1431UqE = this.a.e();
        this.a = c1431UqE;
        C2028fr c2028fr = this.f;
        if (c2028fr.d || c1431UqE == null || c2028fr.i.c(c1431UqE.a, c2028fr.b) >= 0) {
            return;
        }
        this.a = null;
    }
}
