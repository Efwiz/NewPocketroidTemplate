package com.android.tools.r8.dex;

import com.android.tools.r8.dex.code.B1;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class S {
    static final /* synthetic */ boolean d = true;
    private B1 a;
    private B1 b;
    private final boolean c;

    S(B1 b1, B1 b2, boolean z) {
        boolean z2 = d;
        if (!z2 && b1 == null) {
            throw new AssertionError();
        }
        if (!z2 && b2 == null) {
            throw new AssertionError();
        }
        this.a = b1;
        this.b = b2;
        this.c = z;
    }
}
