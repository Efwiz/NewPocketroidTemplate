package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0729q2;

/* JADX INFO: renamed from: com.android.tools.r8.internal.yU, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3438yU extends UF {
    private final C0722p1 b;
    private final EnumC0928Dk c;

    public C3438yU(C0702m c0702m, EnumC0928Dk enumC0928Dk) {
        this.b = c0702m.a();
        this.c = enumC0928Dk;
    }

    @Override // com.android.tools.r8.internal.UF
    public final Object a(C0729q2 c0729q2) {
        C3454yk c3454ykF = C3454yk.f();
        for (com.android.tools.r8.graph.T0 t0 : c0729q2.w1()) {
            c3454ykF.add(this.c.b() ? C3360xU.a(t0, this.b) : C3360xU.a(t0));
        }
        return c3454ykF;
    }

    @Override // com.android.tools.r8.internal.WL
    public final String e() {
        return "SameInstanceFields";
    }
}
