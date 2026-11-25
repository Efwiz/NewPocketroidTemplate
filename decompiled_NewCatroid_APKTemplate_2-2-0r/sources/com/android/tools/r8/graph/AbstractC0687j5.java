package com.android.tools.r8.graph;

import com.android.tools.r8.internal.OU;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.graph.j5, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC0687j5 {
    final C0702m a;
    private final Set b = OU.c();

    protected AbstractC0687j5(C0702m c0702m) {
        this.a = c0702m;
    }

    private void a(C0729q2 c0729q2) {
        if (this.b.add(c0729q2)) {
            if (c0729q2.f != null) {
                C0729q2 c0729q2D = this.a.e().d(c0729q2.n1());
                if (c0729q2D != null) {
                    a(c0729q2D);
                }
            }
            for (C0767v2 c0767v2 : c0729q2.f1().a) {
                C0729q2 c0729q2D2 = this.a.e().d(c0767v2);
                if (c0729q2D2 != null) {
                    a(c0729q2D2);
                }
            }
            b(c0729q2);
        }
    }

    public abstract void b(C0729q2 c0729q2);

    public final void a(C0729q2[] c0729q2Arr) {
        for (C0729q2 c0729q2 : c0729q2Arr) {
            a(c0729q2);
        }
    }
}
