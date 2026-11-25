package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.aQ, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1612aQ {
    private final Set a;

    private C1612aQ() {
        this.a = OU.c();
    }

    public final AbstractC1476Wj a(C0702m c0702m) {
        return this.a.isEmpty() ? c0702m.E() : new C1692bQ(c0702m, this.a);
    }

    public final void a(C0684j2 c0684j2) {
        this.a.add(c0684j2);
    }
}
