package com.android.tools.r8.internal;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public interface F2 {
    static F2 emptyConsumer() {
        return new D2();
    }

    default F2 a(F2 f2) {
        return new E2(this, this, f2);
    }

    void a(C1678bF c1678bF);
}
