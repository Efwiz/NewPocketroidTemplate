package com.android.tools.r8.internal;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class IU extends S {
    int c = -1;
    final /* synthetic */ JU d;

    IU(JU ju) {
        this.d = ju;
    }

    @Override // com.android.tools.r8.internal.S
    protected final Object a() {
        int iNextSetBit = this.d.a.nextSetBit(this.c + 1);
        this.c = iNextSetBit;
        if (iNextSetBit != -1) {
            return this.d.b.d.b.keySet().a().get(this.c);
        }
        b();
        return null;
    }
}
