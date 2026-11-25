package com.android.tools.r8.internal;

import java.util.ArrayList;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class I5 {
    static final /* synthetic */ boolean a = true;

    public static M5 a(com.android.tools.r8.graph.W0 w0, C2409kl c2409kl) {
        if (c2409kl.d.size() > 1) {
            return null;
        }
        AbstractC1332Rs abstractC1332RsC0 = null;
        C2972s9 c2972s9 = null;
        for (AbstractC2412ko abstractC2412ko : c2409kl.j().p()) {
            int iG2 = abstractC2412ko.G2();
            if (iG2 != 5) {
                if (iG2 == 33 || iG2 == 40) {
                    if (abstractC1332RsC0 != null) {
                        return null;
                    }
                    abstractC1332RsC0 = abstractC2412ko.c0();
                    for (int i = 0; i < ((ArrayList) abstractC1332RsC0.J2()).size(); i++) {
                        S10 s10C = abstractC1332RsC0.c(i);
                        if (s10C.H() && i != s10C.q().q().J2()) {
                            return null;
                        }
                    }
                } else {
                    if (iG2 == 56) {
                        C1616aU c1616aUB0 = abstractC2412ko.B0();
                        if (abstractC1332RsC0 != null) {
                            if (c1616aUB0.K2()) {
                                continue;
                            } else if (c1616aUB0.L2().m() == (c2972s9 != null ? c2972s9 : abstractC1332RsC0).b()) {
                            }
                        }
                        return null;
                    }
                    if (iG2 == 9) {
                        continue;
                    } else {
                        if (iG2 != 10) {
                            return null;
                        }
                        C2972s9 c2972s9Y = abstractC2412ko.y();
                        if (!a(w0, c2972s9Y, abstractC1332RsC0)) {
                            return null;
                        }
                        if (abstractC1332RsC0 == null) {
                            continue;
                        } else {
                            if (c2972s9 != null) {
                                return null;
                            }
                            c2972s9 = c2972s9Y;
                        }
                    }
                }
            }
        }
        boolean z = a;
        if (!z && abstractC1332RsC0 == null) {
            throw new AssertionError();
        }
        if (!z && !abstractC1332RsC0.W1() && !abstractC1332RsC0.f2()) {
            throw new AssertionError();
        }
        if (!abstractC1332RsC0.W1()) {
            return new C2823q20(abstractC1332RsC0.S2());
        }
        abstractC1332RsC0.S2();
        return new C3302wf();
    }

    /* JADX WARN: Code duplicated, block: B:49:0x00c2 A[RETURN, SYNTHETIC] */
    private static boolean a(com.android.tools.r8.graph.W0 w0, C2972s9 c2972s9, AbstractC1332Rs abstractC1332Rs) {
        AbstractC1305Qs abstractC1305QsB0;
        int iA;
        if (abstractC1332Rs == null) {
            S10 s10M = c2972s9.e().m();
            if (s10M.H()) {
                int iJ2 = s10M.b.q().J2();
                S10 s10B = c2972s9.b();
                if (!a && s10B.C()) {
                    throw new AssertionError();
                }
                if (!s10B.z() && s10B.D() && (abstractC1305QsB0 = s10B.f0().b0()) != null && ((ArrayList) abstractC1305QsB0.J2()).size() > iJ2 && (iA = iJ2 - com.android.tools.r8.utils.M.a(abstractC1305QsB0.Z1())) != -1 && c2972s9.J2() == abstractC1305QsB0.S2().h.k(iA) && abstractC1305QsB0.c(iJ2) == s10B) {
                    return true;
                }
            }
            return false;
        }
        S10 s10B2 = abstractC1332Rs.b();
        S10 s10M2 = c2972s9.e().m();
        S10 s10B3 = c2972s9.b();
        if (!a && s10B3.C()) {
            throw new AssertionError();
        }
        if (s10M2 == s10B2 && c2972s9.J2() == w0.V1() && !s10B3.z() && s10B3.D() && s10B3.f0().u2()) {
            return true;
        }
        return false;
    }
}
