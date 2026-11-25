package com.android.tools.r8.graph;

import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.graph.x1, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0798x1 {
    static final /* synthetic */ boolean n = true;
    public final Y0 a;
    public final Y0 b;
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
    final /* synthetic */ C0722p1 m;

    private C0798x1(C0722p1 c0722p1) {
        this.m = c0722p1;
        this.a = c0722p1.a(c0722p1.e2, c0722p1.X1, "name");
        this.b = c0722p1.a(c0722p1.e2, c0722p1.A1, "ordinal");
        this.h = c0722p1.a(c0722p1.e2, c0722p1.b(c0722p1.A1, c0722p1.Z1), "compareTo");
        this.k = c0722p1.a(c0722p1.e2, c0722p1.b(c0722p1.D1, c0722p1.X1, c0722p1.A1), c0722p1.Y0);
        this.l = c0722p1.a(c0722p1.e2, c0722p1.b(c0722p1.D1, new C0767v2[0]), c0722p1.m0);
        C0759u2 c0759u2 = c0722p1.P0;
        this.c = c0722p1.a(c0759u2, c0722p1.g0, c0759u2, new C0759u2[]{c0722p1.J0, c0722p1.G0});
        C0759u2 c0759u3 = c0722p1.P0;
        C0759u2 c0759u4 = c0722p1.n0;
        C0759u2 c0759u5 = c0722p1.B;
        C0759u2[] c0759u2Arr = C0759u2.f;
        this.d = c0722p1.a(c0759u3, c0759u4, c0759u5, c0759u2Arr);
        this.e = c0722p1.a(c0722p1.P0, c0722p1.o0, c0722p1.G0, c0759u2Arr);
        this.f = c0722p1.a(c0722p1.P0, c0722p1.h0, c0722p1.G0, c0759u2Arr);
        C0759u2 c0759u6 = c0722p1.P0;
        this.g = c0722p1.a(c0759u6, c0722p1.b0, c0722p1.B, new C0759u2[]{c0759u6});
        this.i = c0722p1.a(c0722p1.P0, c0722p1.U, c0722p1.w, new C0759u2[]{c0722p1.H0});
        this.j = c0722p1.a(c0722p1.P0, c0722p1.V, c0722p1.B, c0759u2Arr);
    }

    public final void a(Consumer consumer) {
        consumer.accept(this.a);
        consumer.accept(this.b);
    }

    public final boolean b(T0 t0, C0767v2 c0767v2) {
        if (n || t0.E0()) {
            return t0.getType().M0() && t0.getType().a(this.m) == c0767v2 && t0.f.q() && t0.f.h();
        }
        throw new AssertionError();
    }

    public static boolean a(T0 t0, C0767v2 c0767v2) {
        if (n || t0.E0()) {
            return t0.getType() == c0767v2 && t0.f.R() && t0.f.h();
        }
        throw new AssertionError();
    }
}
