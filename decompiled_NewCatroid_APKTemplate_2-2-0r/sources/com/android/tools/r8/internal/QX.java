package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import java.util.ArrayList;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class QX implements RX {
    static final /* synthetic */ boolean b = true;
    private final C0722p1 a;

    QX(C0722p1 c0722p1) {
        this.a = c0722p1;
    }

    public final boolean a(Predicate predicate, AbstractC2412ko abstractC2412ko) {
        if (abstractC2412ko.p2()) {
            return a(abstractC2412ko.t0().J2());
        }
        if (!abstractC2412ko.Y1()) {
            return false;
        }
        C0684j2 c0684j2S2 = abstractC2412ko.b0().S2();
        com.android.tools.r8.graph.Z1 z1 = this.a.W3;
        if (!z1.a(c0684j2S2) && !z1.u.contains(c0684j2S2) && c0684j2S2 != z1.r && c0684j2S2 != z1.m) {
            com.android.tools.r8.graph.Z1 z2 = this.a.X3;
            if (!z2.a(c0684j2S2) && !z2.u.contains(c0684j2S2) && c0684j2S2 != z2.r && c0684j2S2 != z2.m) {
                C0722p1 c0722p1 = this.a;
                return (c0684j2S2 == c0722p1.g4.i || c0684j2S2 == c0722p1.f4.j) && predicate.test(abstractC2412ko.T0());
            }
        }
        return true;
    }

    public final boolean b(AbstractC2412ko abstractC2412ko) {
        if (!abstractC2412ko.Y1()) {
            return false;
        }
        C0684j2 c0684j2S2 = abstractC2412ko.b0().S2();
        return this.a.W3.a(c0684j2S2) || this.a.X3.a(c0684j2S2);
    }

    public final boolean c(AbstractC2412ko abstractC2412ko) {
        if (!abstractC2412ko.Y1()) {
            return false;
        }
        C0684j2 c0684j2S2 = abstractC2412ko.b0().S2();
        C0722p1 c0722p1 = this.a;
        return c0684j2S2 == c0722p1.W3.l || c0684j2S2 == c0722p1.X3.l;
    }

    public final boolean d(AbstractC2412ko abstractC2412ko) {
        if (!abstractC2412ko.W1()) {
            return false;
        }
        C0684j2 c0684j2S2 = abstractC2412ko.b0().S2();
        return this.a.W3.u.contains(c0684j2S2) || this.a.X3.u.contains(c0684j2S2);
    }

    public final boolean e(AbstractC2412ko abstractC2412ko) {
        if (!abstractC2412ko.Z1()) {
            return false;
        }
        C0684j2 c0684j2S2 = abstractC2412ko.c0().S2();
        C0722p1 c0722p1 = this.a;
        return c0722p1.W3.m == c0684j2S2 || c0722p1.X3.m == c0684j2S2;
    }

    public final boolean f(AbstractC2412ko abstractC2412ko) {
        if (!abstractC2412ko.Y1()) {
            return false;
        }
        C0684j2 c0684j2S2 = abstractC2412ko.b0().S2();
        C0722p1 c0722p1 = this.a;
        return c0684j2S2 == c0722p1.W3.q || c0684j2S2 == c0722p1.X3.q;
    }

    public final boolean b(AbstractC1332Rs abstractC1332Rs) {
        if (!abstractC1332Rs.a(this.a)) {
            return false;
        }
        C0684j2 c0684j2S2 = abstractC1332Rs.b0().S2();
        C0767v2 c0767v2B0 = c0684j2S2.B0();
        C0722p1 c0722p1 = this.a;
        if (c0767v2B0 == c0722p1.v2) {
            return c0722p1.W3.a(c0684j2S2, abstractC1332Rs.h1());
        }
        if (b || c0684j2S2.B0() == this.a.w2) {
            return this.a.X3.a(c0684j2S2, abstractC1332Rs.h1());
        }
        throw new AssertionError();
    }

    public final boolean a(C0767v2 c0767v2) {
        C0722p1 c0722p1 = this.a;
        return c0767v2 == c0722p1.v2 || c0767v2 == c0722p1.w2;
    }

    public final boolean a(AbstractC2412ko abstractC2412ko, S10 s10) {
        if (!abstractC2412ko.Y1() || abstractC2412ko.h1().isEmpty() || abstractC2412ko.T0() != s10) {
            return false;
        }
        C0684j2 c0684j2S2 = abstractC2412ko.b0().S2();
        C0722p1 c0722p1 = this.a;
        return c0722p1.W3.r == c0684j2S2 || c0722p1.X3.r == c0684j2S2 || c0722p1.g4.i == c0684j2S2 || c0722p1.f4.j == c0684j2S2;
    }

    public final String a(AbstractC2412ko abstractC2412ko) {
        if (!abstractC2412ko.Z1()) {
            return null;
        }
        if (b(abstractC2412ko)) {
            C0684j2 c0684j2S2 = abstractC2412ko.c0().b0().S2();
            C0722p1 c0722p1 = this.a;
            com.android.tools.r8.graph.Z1 z1 = c0722p1.W3;
            if (z1.d != c0684j2S2 && z1.f != c0684j2S2) {
                com.android.tools.r8.graph.Z1 z2 = c0722p1.X3;
                if (z2.d != c0684j2S2 && z2.f != c0684j2S2) {
                    C2115gt c2115gtI0 = abstractC2412ko.i0();
                    C0684j2 c0684j2S3 = c2115gtI0.S2();
                    C0767v2 c0767v2K = (this.a.W3.a(c0684j2S3) || this.a.X3.a(c0684j2S3)) ? c0684j2S3.k(0) : null;
                    S10 s10M = c2115gtI0.Q2().m();
                    if (c0767v2K != null) {
                        return AbstractC2996sX.a(this.a, c2115gtI0.S2(), s10M, c0767v2K);
                    }
                    return null;
                }
            }
        }
        if (!d(abstractC2412ko)) {
            return null;
        }
        C1166Ls c1166LsZ = abstractC2412ko.Z();
        if (!b) {
            if (!c1166LsZ.S2().b(this.a)) {
                throw new AssertionError();
            }
        }
        ArrayList arrayList = (ArrayList) c1166LsZ.h1();
        if (arrayList.size() == 1) {
            return "";
        }
        if (arrayList.size() != 2 || c1166LsZ.c(1).w().H()) {
            return null;
        }
        S10 s10M2 = c1166LsZ.c(1).m();
        C0767v2 c0767v2K2 = c1166LsZ.S2().k(0);
        if (c0767v2K2 != null) {
            return AbstractC2996sX.a(this.a, c1166LsZ.S2(), s10M2, c0767v2K2);
        }
        return null;
    }

    /* JADX WARN: Code duplicated, block: B:43:0x0071  */
    public final boolean a(AbstractC1332Rs abstractC1332Rs) {
        boolean zA;
        if (!abstractC1332Rs.Y1()) {
            if (b) {
                return true;
            }
            throw new AssertionError("Expecting a call to string builder");
        }
        C0684j2 c0684j2S2 = abstractC1332Rs.b0().S2();
        C0722p1 c0722p1 = this.a;
        com.android.tools.r8.graph.Z1 z1 = c0722p1.W3;
        if (c0684j2S2 != z1.k && c0684j2S2 != z1.e && c0684j2S2 != z1.f) {
            com.android.tools.r8.graph.Z1 z2 = c0722p1.X3;
            if (c0684j2S2 != z2.k && c0684j2S2 != z2.e && c0684j2S2 != z2.f) {
                if (c0684j2S2.b(c0722p1)) {
                    zA = b(abstractC1332Rs);
                } else {
                    C0722p1 c0722p2 = this.a;
                    com.android.tools.r8.graph.Z1 z3 = c0722p2.W3;
                    if (c0684j2S2 != z3.c && c0684j2S2 != z3.d) {
                        com.android.tools.r8.graph.Z1 z4 = c0722p2.X3;
                        if (c0684j2S2 != z4.c && c0684j2S2 != z4.d) {
                            return false;
                        }
                    }
                    zA = abstractC1332Rs.i0().Q2().P();
                }
            } else {
                AbstractC3472z00 abstractC3472z00W = ((S10) ((ArrayList) abstractC1332Rs.h1()).get(1)).w();
                C0722p1 c0722p3 = this.a;
                abstractC3472z00W.getClass();
                zA = abstractC3472z00W.a(c0722p3.X1);
            }
        } else {
            AbstractC3472z00 abstractC3472z00W2 = ((S10) ((ArrayList) abstractC1332Rs.h1()).get(1)).w();
            C0722p1 c0722p4 = this.a;
            abstractC3472z00W2.getClass();
            zA = abstractC3472z00W2.a(c0722p4.X1);
        }
        return !zA;
    }
}
