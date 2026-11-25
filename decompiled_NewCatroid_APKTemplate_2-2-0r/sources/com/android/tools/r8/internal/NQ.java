package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0737r2;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class NQ {
    private final C0759u2 a;
    private final C0737r2 b;
    private final C0759u2 c;
    private final com.android.tools.r8.graph.Y0[] d;
    private final C0729q2 e;

    private NQ(C0759u2 c0759u2, C0737r2 c0737r2, C0759u2 c0759u3, com.android.tools.r8.graph.Y0[] y0Arr, C0729q2 c0729q2) {
        this.a = c0759u2;
        this.b = c0737r2;
        this.c = c0759u3;
        this.d = y0Arr;
        this.e = c0729q2;
    }

    final NQ a(C0759u2 c0759u2, com.android.tools.r8.graph.Y0[] y0Arr) {
        return new NQ(this.a, this.b, c0759u2, y0Arr, this.e);
    }

    final C0759u2 b() {
        return this.c;
    }

    final com.android.tools.r8.graph.Y0[] c() {
        return this.d;
    }

    final C0759u2 d() {
        return this.a;
    }

    final C0737r2 e() {
        return this.b;
    }

    final C0729q2 f() {
        return this.e;
    }

    final C0767v2 g() {
        return this.e.getType();
    }

    final KQ a() {
        return KQ.a(this.c, this.d);
    }
}
