package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;

/* JADX INFO: renamed from: com.android.tools.r8.internal.hs, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2184hs {
    private final C0702m a;
    private final C2416ks b;

    C2184hs(C0702m c0702m, C2416ks c2416ks) {
        this.a = c0702m;
        this.b = c2416ks;
    }

    final void a() {
        com.android.tools.r8.graph.J3 j3D1;
        for (C0729q2 c0729q2 : this.a.f().d()) {
            if (c0729q2.b1() != null && c0729q2.b1().b() != null && (j3D1 = c0729q2.d1()) != null && j3D1.d() == null) {
                com.android.tools.r8.graph.O2 o2B1 = c0729q2.b1();
                C0684j2 c0684j2B = o2B1.b();
                C0684j2 c0684j2C = null;
                if (c0684j2B != null) {
                    C0684j2 c0684j2 = (C0684j2) this.b.m().b(c0684j2B);
                    c0684j2C = c0684j2 != null ? c0684j2 : this.b.c((AbstractC1476Wj) null, c0684j2B);
                }
                if (c0684j2C != c0684j2B) {
                    o2B1 = new com.android.tools.r8.graph.O2(c0684j2C);
                }
                c0729q2.a(o2B1);
            }
        }
    }
}
