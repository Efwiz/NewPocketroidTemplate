package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0759u2;

/* JADX INFO: renamed from: com.android.tools.r8.internal.nB, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2595nB {
    private final C0702m a;
    private final C0759u2 b;

    C2595nB(C0702m c0702m) {
        this.a = c0702m;
        this.b = c0702m.a().H;
    }

    public final boolean a(C0729q2 c0729q2) {
        C2520mB c2520mB = new C2520mB(this.a, this.b, c0729q2);
        c2520mB.a(c0729q2);
        return c2520mB.c;
    }
}
