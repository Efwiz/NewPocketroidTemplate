package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class YX implements InterfaceC2481lh {
    private static final YX a = new YX();

    private YX() {
    }

    @Override // com.android.tools.r8.internal.InterfaceC2481lh
    public final boolean a(C0702m c0702m, C2405kh c2405kh, AbstractC2412ko abstractC2412ko, com.android.tools.r8.graph.o5 o5Var) {
        if (abstractC2412ko.u2() || abstractC2412ko.B2() || abstractC2412ko.c()) {
            return false;
        }
        if (abstractC2412ko.Y1()) {
            C0684j2 c0684j2S2 = abstractC2412ko.b0().S2();
            return c0684j2S2 == c0702m.a().k4.r || c0684j2S2 == c0702m.a().k4.b || c0684j2S2 == c0702m.a().k4.c;
        }
        if (abstractC2412ko.r1()) {
            S10 s10M = abstractC2412ko.v().J2().m();
            return !s10M.i() && s10M.b.D1();
        }
        if (!abstractC2412ko.S1()) {
            return false;
        }
        S10 s10M2 = abstractC2412ko.V().e().m();
        return !s10M2.i() && s10M2.b.p2();
    }
}
