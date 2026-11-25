package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.ep, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
class C1949ep extends AbstractC2259ip {
    final /* synthetic */ C2181hp f;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    C1949ep(C2181hp c2181hp) {
        super(c2181hp.i);
        this.f = c2181hp;
        this.b = c2181hp.k();
    }

    @Override // com.android.tools.r8.internal.AbstractC2259ip
    final void d() {
        C1481Wo c1481Wo = this.b;
        C1481Wo c1481Wo2 = c1481Wo.d;
        if ((c1481Wo.e & Integer.MIN_VALUE) == 0) {
            while ((c1481Wo2.e & 1073741824) == 0) {
                c1481Wo2 = c1481Wo2.c;
            }
        }
        this.b = c1481Wo2;
        C2181hp c2181hp = this.f;
        if (c2181hp.e || c1481Wo2 == null || c2181hp.i.c(c1481Wo2.a, c2181hp.c) < 0) {
            return;
        }
        this.b = null;
    }

    @Override // com.android.tools.r8.internal.AbstractC2259ip
    final void g() {
        C1481Wo c1481WoD = this.a.d();
        this.a = c1481WoD;
        C2181hp c2181hp = this.f;
        if (c2181hp.d || c1481WoD == null || c2181hp.i.c(c1481WoD.a, c2181hp.b) >= 0) {
            return;
        }
        this.a = null;
    }
}
