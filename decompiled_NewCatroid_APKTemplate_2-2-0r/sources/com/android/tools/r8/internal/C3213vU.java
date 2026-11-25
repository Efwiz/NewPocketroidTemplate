package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;

/* JADX INFO: renamed from: com.android.tools.r8.internal.vU, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3213vU extends UF {
    private final C0702m b;

    public C3213vU(C0702m c0702m) {
        this.b = c0702m;
    }

    @Override // com.android.tools.r8.internal.UF
    public final Object a(C0729q2 c0729q2) {
        C0702m c0702m = this.b;
        C1121Ka c1121KaP = ((C0680j) c0702m.f()).p();
        c1121KaP.getClass();
        return c1121KaP.a(c0729q2.C(), c0702m.Y(), c0702m.C(), c0702m.D());
    }

    @Override // com.android.tools.r8.internal.WL
    public final String e() {
        return "SameFeatureSplit";
    }
}
