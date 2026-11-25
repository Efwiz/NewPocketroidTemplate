package com.android.tools.r8.utils;

import com.android.tools.r8.internal.AbstractC1915eM;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public final class D0 {
    private final AbstractC1915eM a;
    private final int b;

    private D0(int i, AbstractC1915eM abstractC1915eM) {
        this.a = abstractC1915eM;
        this.b = i;
    }

    public final AbstractC1915eM a() {
        return this.a;
    }

    public final int b() {
        return this.b;
    }

    public final boolean c() {
        return this.a != null;
    }

    public final boolean d() {
        return this.b > -1;
    }
}
