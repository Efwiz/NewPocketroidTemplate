package com.android.tools.r8.graph;

import com.android.tools.r8.internal.AbstractC1048Hm;

/* JADX INFO: renamed from: com.android.tools.r8.graph.u1, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0757u1 {
    public final C0684j2 a;
    public final C0684j2 b;
    public final C0684j2 c;
    public final C0684j2 d;
    public final C0684j2 e;
    public final C0684j2 f;
    public final C0684j2 g;
    public final C0684j2 h;
    public final C0684j2 i;
    public final C0684j2 j;
    public final C0684j2 k;
    public final C0684j2 l;
    private final AbstractC1048Hm m;
    public final AbstractC1048Hm n;

    private C0757u1(C0722p1 c0722p1) {
        this.d = c0722p1.a(c0722p1.m2, c0722p1.b(c0722p1.o2, new C0767v2[0]), "getClassLoader");
        this.k = c0722p1.a(c0722p1.m2, c0722p1.b(c0722p1.n2, new C0767v2[0]), "getPackage");
        C0759u2 c0759u2 = c0722p1.J0;
        C0759u2 c0759u3 = c0722p1.p0;
        C0759u2 c0759u4 = c0722p1.w;
        C0759u2[] c0759u2Arr = C0759u2.f;
        this.a = c0722p1.a(c0759u2, c0759u3, c0759u4, c0759u2Arr);
        C0759u2 c0759u5 = c0722p1.J0;
        this.b = c0722p1.a(c0759u5, c0722p1.q0, c0759u5, new C0759u2[]{c0722p1.G0});
        C0759u2 c0759u6 = c0722p1.J0;
        this.c = c0722p1.a(c0759u6, c0722p1.q0, c0759u6, new C0759u2[]{c0722p1.G0, c0722p1.w, c0722p1.K0});
        C0684j2 c0684j2A = c0722p1.a(c0722p1.J0, c0722p1.r0, c0722p1.G0, c0759u2Arr);
        this.e = c0684j2A;
        C0684j2 c0684j2A2 = c0722p1.a(c0722p1.J0, c0722p1.s0, c0722p1.G0, c0759u2Arr);
        this.f = c0684j2A2;
        C0684j2 c0684j2A3 = c0722p1.a(c0722p1.J0, c0722p1.t0, c0722p1.G0, c0759u2Arr);
        this.g = c0684j2A3;
        C0684j2 c0684j2A4 = c0722p1.a(c0722p1.J0, c0722p1.u0, c0722p1.G0, c0759u2Arr);
        this.h = c0684j2A4;
        this.i = c0722p1.a(c0722p1.m2, c0722p1.b(c0722p1.p2, c0722p1.d2), "getConstructor");
        this.j = c0722p1.a(c0722p1.J0, c0722p1.v0, c0722p1.M0, new C0759u2[]{c0722p1.L0});
        C0684j2 c0684j2A5 = c0722p1.a(c0722p1.J0, c0722p1.w0, c0722p1.N0, new C0759u2[]{c0722p1.G0});
        C0684j2 c0684j2A6 = c0722p1.a(c0722p1.J0, c0722p1.x0, c0722p1.N0, new C0759u2[]{c0722p1.G0});
        C0684j2 c0684j2A7 = c0722p1.a(c0722p1.J0, c0722p1.y0, c0722p1.O0, new C0759u2[]{c0722p1.G0, c0722p1.L0});
        C0684j2 c0684j2A8 = c0722p1.a(c0722p1.J0, c0722p1.z0, c0722p1.O0, new C0759u2[]{c0722p1.G0, c0722p1.L0});
        this.l = c0722p1.a(c0722p1.J0, c0722p1.A0, c0722p1.H0, c0759u2Arr);
        this.m = AbstractC1048Hm.a(c0684j2A5, c0684j2A6, c0684j2A7, c0684j2A8);
        this.n = AbstractC1048Hm.a(c0684j2A, c0684j2A2, c0684j2A3, c0684j2A4);
    }

    public final boolean a(C0684j2 c0684j2) {
        return this.m.contains(c0684j2);
    }
}
