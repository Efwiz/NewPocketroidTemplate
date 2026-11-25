package com.android.tools.r8.internal;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class A9 extends AbstractC1899eF {
    private final int j;
    private final C1549Za k;

    A9(int i, int i2, C1549Za c1549Za, AbstractC1899eF abstractC1899eF) {
        super(i, abstractC1899eF);
        this.j = i2;
        this.k = c1549Za;
    }

    final boolean d() {
        return this.k.a(2);
    }

    final boolean e() {
        return this.k.a(1) || this.k.a(2);
    }

    final boolean f() {
        return (this.j & 65535) >= 51;
    }
}
