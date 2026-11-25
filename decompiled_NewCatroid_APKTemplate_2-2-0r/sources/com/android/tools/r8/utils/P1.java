package com.android.tools.r8.utils;

import java.util.function.Supplier;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public final class P1 extends O {
    private final Supplier b;

    public P1(Supplier supplier) {
        this.b = supplier;
    }

    public final Object d() {
        return a(this.b);
    }
}
