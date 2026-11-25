package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0753u;
import com.android.tools.r8.graph.C0767v2;

/* JADX INFO: renamed from: com.android.tools.r8.internal.vb, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC3221vb {
    public static C0753u a(int i, C0722p1 c0722p1, C0684j2 c0684j2) {
        return a(c0722p1, c0684j2, i, c0722p1.K2);
    }

    public static C0753u b(int i, C0722p1 c0722p1, C0684j2 c0684j2) {
        return a(c0722p1, c0684j2, i, c0722p1.L2);
    }

    private static C0753u a(C0722p1 c0722p1, C0684j2 c0684j2, int i, C0767v2 c0767v2) {
        C1507Xl c1507XlG = AbstractC1639am.g();
        c1507XlG.a((Object[]) new B7[]{new C1818d7(i, EnumC1812d20.INT), new W7(c0722p1.c2)});
        for (int i2 = 0; i2 < i; i2++) {
            c1507XlG.a((Object[]) new B7[]{new D8(D8.a.Dup), new C1818d7(i2, EnumC1812d20.INT), new O7(EnumC1812d20.OBJECT, i2), new D6(EnumC2523mE.OBJECT)});
        }
        c1507XlG.a((Object[]) new B7[]{new I7(184, c0722p1.a(c0767v2, c0722p1.b(c0767v2, c0722p1.c2), c0722p1.c("of")), false), new C3109u8(EnumC1812d20.OBJECT)});
        return new C0753u(c0684j2.e, 4, i, c1507XlG.a());
    }
}
