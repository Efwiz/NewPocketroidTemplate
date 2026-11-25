package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0767v2;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class O00 implements P00 {
    static final /* synthetic */ boolean c = true;
    final C0767v2 a;
    public final C3406y2 b;

    public O00(C3406y2 c3406y2, C0767v2 c0767v2) {
        boolean z = c;
        if (!z && c3406y2 == null) {
            throw new AssertionError();
        }
        if (!z && c0767v2 == null) {
            throw new AssertionError();
        }
        this.b = c3406y2;
        this.a = c0767v2;
    }

    @Override // com.android.tools.r8.internal.P00
    public final C0767v2 a() {
        return this.a;
    }

    public final String toString() {
        return "this:" + this.a;
    }
}
