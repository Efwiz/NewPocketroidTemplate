package com.android.tools.r8.graph;

import com.android.tools.r8.internal.MR;

/* JADX INFO: renamed from: com.android.tools.r8.graph.d5, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C0638d5 {
    private final C0664h a;
    private final MR b;

    C0638d5(C0664h c0664h) {
        MR mr = new MR();
        this.b = mr;
        this.a = c0664h;
        mr.e(-1);
    }

    final int a(C0729q2 c0729q2) {
        int iA;
        int iA2 = this.b.a(c0729q2);
        if (iA2 != -1) {
            return iA2;
        }
        C0767v2 c0767v2 = c0729q2.f;
        if (c0767v2 == null) {
            iA = 0;
        } else {
            C0664h c0664h = this.a;
            c0664h.getClass();
            C0729q2 c0729q2A = C0729q2.a(c0767v2 == c0729q2.d ? c0729q2 : c0664h.a(c0767v2));
            iA = c0729q2A != null ? a(c0729q2A) : 1;
        }
        C0767v2[] c0767v2Arr = c0729q2.g.a;
        int length = c0767v2Arr.length;
        for (int i = 0; i < length; i++) {
            C0767v2 c0767v3 = c0767v2Arr[i];
            C0664h c0664h2 = this.a;
            c0664h2.getClass();
            C0729q2 c0729q2A2 = C0729q2.a(c0767v3 == c0729q2.d ? c0729q2 : c0664h2.a(c0767v3));
            iA = Math.max(iA, c0729q2A2 == null ? 1 : a(c0729q2A2));
        }
        int i2 = iA + 1;
        this.b.a(i2, c0729q2);
        return i2;
    }
}
