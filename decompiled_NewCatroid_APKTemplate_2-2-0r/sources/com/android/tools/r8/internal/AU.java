package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0729q2;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class AU extends UF {
    private final C0722p1 b;

    public AU(C0702m c0702m) {
        this.b = c0702m.a();
    }

    @Override // com.android.tools.r8.internal.UF
    public final Object a(C0729q2 c0729q2) {
        return c0729q2.D1() ? c0729q2.g1() : this.b.Z1;
    }

    @Override // com.android.tools.r8.internal.WL
    public final String e() {
        return "SameNestHost";
    }
}
