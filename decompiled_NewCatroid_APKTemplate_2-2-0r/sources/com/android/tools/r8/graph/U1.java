package com.android.tools.r8.graph;

import com.android.tools.r8.internal.AbstractC2178hm;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class U1 {
    private final AbstractC2178hm a;

    private U1(C0722p1 c0722p1) {
        Y0 y0A = c0722p1.a(c0722p1.N1, c0722p1.m2, "TYPE");
        Y0 y0A2 = c0722p1.a(c0722p1.O1, c0722p1.m2, "TYPE");
        Y0 y0A3 = c0722p1.a(c0722p1.T1, c0722p1.m2, "TYPE");
        Y0 y0A4 = c0722p1.a(c0722p1.R1, c0722p1.m2, "TYPE");
        Y0 y0A5 = c0722p1.a(c0722p1.S1, c0722p1.m2, "TYPE");
        Y0 y0A6 = c0722p1.a(c0722p1.Q1, c0722p1.m2, "TYPE");
        this.a = AbstractC2178hm.e().a(c0722p1.Y3.c, c0722p1.v1).a(y0A, c0722p1.w1).a(y0A2, c0722p1.x1).a(y0A3, c0722p1.C1).a(y0A4, c0722p1.A1).a(y0A5, c0722p1.B1).a(y0A6, c0722p1.z1).a(c0722p1.a(c0722p1.P1, c0722p1.m2, "TYPE"), c0722p1.y1).a();
    }

    public final C0767v2 a(Y0 y0) {
        return (C0767v2) this.a.get(y0);
    }
}
