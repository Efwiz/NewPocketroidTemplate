package com.android.tools.r8.graph;

import com.android.tools.r8.internal.AbstractC1048Hm;
import com.android.tools.r8.internal.AbstractC3472z00;
import com.android.tools.r8.internal.S10;
import java.util.List;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class Z1 {
    static final /* synthetic */ boolean w = true;
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
    public final C0684j2 m;
    public final C0684j2 n;
    public final C0684j2 o;
    public final C0684j2 p;
    public final C0684j2 q;
    public final C0684j2 r;
    private final AbstractC1048Hm s;
    private final AbstractC1048Hm t;
    public final AbstractC1048Hm u;
    final /* synthetic */ C0722p1 v;

    private Z1(C0722p1 c0722p1, C0767v2 c0767v2) {
        this.v = c0722p1;
        C0759u2 c0759u2C = c0722p1.c("append");
        C0684j2 c0684j2A = c0722p1.a(c0767v2, c0722p1.b(c0767v2, c0722p1.v1), c0759u2C);
        this.a = c0684j2A;
        C0684j2 c0684j2A2 = c0722p1.a(c0767v2, c0722p1.b(c0767v2, c0722p1.x1), c0759u2C);
        this.b = c0684j2A2;
        C0684j2 c0684j2A3 = c0722p1.a(c0767v2, c0722p1.b(c0767v2, c0722p1.G1), c0759u2C);
        this.c = c0684j2A3;
        C0767v2 c0767v3 = c0722p1.A1;
        C0684j2 c0684j2A4 = c0722p1.a(c0767v2, c0722p1.b(c0767v2, c0722p1.G1, c0767v3, c0767v3), c0759u2C);
        this.d = c0684j2A4;
        C0684j2 c0684j2A5 = c0722p1.a(c0767v2, c0722p1.b(c0767v2, c0722p1.V1), c0759u2C);
        this.e = c0684j2A5;
        C0767v2 c0767v4 = c0722p1.A1;
        C0684j2 c0684j2A6 = c0722p1.a(c0767v2, c0722p1.b(c0767v2, c0722p1.V1, c0767v4, c0767v4), c0759u2C);
        this.f = c0684j2A6;
        C0684j2 c0684j2A7 = c0722p1.a(c0767v2, c0722p1.b(c0767v2, c0722p1.A1), c0759u2C);
        this.g = c0684j2A7;
        C0684j2 c0684j2A8 = c0722p1.a(c0767v2, c0722p1.b(c0767v2, c0722p1.y1), c0759u2C);
        this.h = c0684j2A8;
        C0684j2 c0684j2A9 = c0722p1.a(c0767v2, c0722p1.b(c0767v2, c0722p1.z1), c0759u2C);
        this.i = c0684j2A9;
        C0684j2 c0684j2A10 = c0722p1.a(c0767v2, c0722p1.b(c0767v2, c0722p1.B1), c0759u2C);
        this.j = c0684j2A10;
        C0684j2 c0684j2A11 = c0722p1.a(c0767v2, c0722p1.b(c0767v2, c0722p1.Z1), c0759u2C);
        this.k = c0684j2A11;
        C0684j2 c0684j2A12 = c0722p1.a(c0767v2, c0722p1.b(c0767v2, c0722p1.X1), c0759u2C);
        this.l = c0684j2A12;
        C0684j2 c0684j2A13 = c0722p1.a(c0767v2, c0722p1.b(c0767v2, c0722p1.w2), c0759u2C);
        this.m = c0722p1.a(c0767v2, c0722p1.b(c0722p1.A1, new C0767v2[0]), c0722p1.c("capacity"));
        C0684j2 c0684j2A14 = c0722p1.a(c0767v2, c0722p1.b(c0722p1.D1, c0722p1.V1), c0722p1.Y0);
        this.n = c0684j2A14;
        C0684j2 c0684j2A15 = c0722p1.a(c0767v2, c0722p1.b(c0722p1.D1, new C0767v2[0]), c0722p1.Y0);
        this.o = c0684j2A15;
        C0684j2 c0684j2A16 = c0722p1.a(c0767v2, c0722p1.b(c0722p1.D1, c0722p1.A1), c0722p1.Y0);
        this.p = c0684j2A16;
        C0684j2 c0684j2A17 = c0722p1.a(c0767v2, c0722p1.b(c0722p1.D1, c0722p1.X1), c0722p1.Y0);
        this.q = c0684j2A17;
        this.r = c0722p1.a(c0767v2, c0722p1.b(c0722p1.X1, new C0767v2[0]), c0722p1.h0);
        this.s = AbstractC1048Hm.a(c0684j2A, c0684j2A2, c0684j2A3, c0684j2A4, c0684j2A5, c0684j2A6, c0684j2A7, c0684j2A8, c0684j2A9, c0684j2A10, c0684j2A11, c0684j2A12, c0684j2A13);
        this.t = AbstractC1048Hm.a(c0684j2A, c0684j2A2, c0684j2A7, c0684j2A8, c0684j2A9, c0684j2A10, new C0684j2[0]);
        this.u = AbstractC1048Hm.a(c0684j2A14, c0684j2A15, c0684j2A16, c0684j2A17);
    }

    public final boolean a(C0684j2 c0684j2) {
        return this.s.contains(c0684j2);
    }

    public final boolean b(C0684j2 c0684j2) {
        return this.t.contains(c0684j2);
    }

    public final boolean a(C0684j2 c0684j2, List list) {
        if (c0684j2 == this.o) {
            return true;
        }
        if (c0684j2 == this.n) {
            AbstractC3472z00 abstractC3472z00W = ((S10) list.get(1)).w();
            return abstractC3472z00W.w() && abstractC3472z00W.b().R() == this.v.X1;
        }
        if (c0684j2 == this.p) {
            S10 s10 = (S10) list.get(1);
            return s10.G() && s10.x().b() >= 0;
        }
        if (c0684j2 == this.q) {
            return !((S10) list.get(1)).w().F();
        }
        if (w) {
            return false;
        }
        throw new AssertionError("Unexpected invoke targeting `" + c0684j2.r0() + "`");
    }
}
