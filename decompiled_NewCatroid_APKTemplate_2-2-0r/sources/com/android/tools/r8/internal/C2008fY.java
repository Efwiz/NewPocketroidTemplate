package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0759u2;
import java.io.UTFDataFormatException;
import java.util.Set;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.fY, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2008fY {
    static final /* synthetic */ boolean e = true;
    private final C3332x4 a;
    private final C0722p1 b;
    private final ML c;
    private final S10 d;

    C2008fY(C3332x4 c3332x4, C0722p1 c0722p1, ML ml, S10 s10) {
        this.a = c3332x4;
        this.b = c0722p1;
        this.c = a(ml, ml);
        this.d = s10;
    }

    private static ML a(ML ml, ML ml2) {
        if (ml.k0().size() != 2) {
            return ml2;
        }
        ML mlL = null;
        for (S10 s10 : ml.k0()) {
            if (s10.i()) {
                if (mlL != null) {
                    return ml2;
                }
                mlL = s10.l();
            }
        }
        if (e || mlL == null || mlL.k0().stream().noneMatch(new Predicate() { // from class: com.android.tools.r8.internal.fY$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((S10) obj).i();
            }
        })) {
            return mlL != null ? mlL : ml2;
        }
        throw new AssertionError();
    }

    private C2088gY b(C2088gY c2088gY, C3332x4 c3332x4) {
        for (AbstractC2412ko abstractC2412ko : c3332x4.p()) {
            if (!abstractC2412ko.B1() || !abstractC2412ko.b().a(c3332x4)) {
                if (abstractC2412ko.f2()) {
                    C2115gt c2115gtI0 = abstractC2412ko.i0();
                    if (c2115gtI0.S2() != this.b.k4.r || c2115gtI0.U2() != this.d || (c2115gtI0.d1() && !c2115gtI0.b().a(c3332x4))) {
                    }
                }
                if (!abstractC2412ko.g2()) {
                    return c2088gY;
                }
            }
        }
        if (!c3332x4.J()) {
            return c2088gY;
        }
        C3332x4 c3332x4B = c3332x4.B();
        c3332x4B.getClass();
        boolean z = false;
        C3332x4 c3332x4B2 = c3332x4B;
        while (c3332x4B.Q() && c3332x4B.J()) {
            c3332x4B = c3332x4B.B();
            if (z) {
                c3332x4B2 = c3332x4B2.B();
            }
            z = !z;
            if (c3332x4B == c3332x4B2) {
                c3332x4B = null;
                break;
            }
        }
        return a(c2088gY, c3332x4B);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:17:0x0042  */
    public C2088gY a(C2088gY c2088gY, C3332x4 c3332x4) {
        S10 s10;
        S10 s11;
        AbstractC1954eu abstractC1954euM = c3332x4.m();
        int iN2 = 0;
        if (abstractC1954euM.N1()) {
            C1015Gl c1015GlQ = abstractC1954euM.Q();
            EnumC1076Il enumC1076IlL2 = c1015GlQ.L2();
            EnumC1076Il enumC1076Il = EnumC1076Il.EQ;
            if (enumC1076IlL2 != enumC1076Il && c1015GlQ.L2() != EnumC1076Il.NE) {
                return c2088gY;
            }
            C0722p1 c0722p1 = this.b;
            S10 s10O2 = c1015GlQ.O2();
            if (!AbstractC2242iY.a(c0722p1, s10O2)) {
                if (c1015GlQ.N2()) {
                    s10O2 = null;
                } else {
                    s10O2 = c1015GlQ.P2();
                    if (!AbstractC2242iY.a(c0722p1, s10O2)) {
                        s10O2 = null;
                    }
                }
            }
            if (s10O2 == null) {
                return c2088gY;
            }
            if (c2088gY != null && s10O2 != (s11 = c2088gY.b) && s10O2.b.i0().U2() != s11.b.i0().U2()) {
                return c2088gY;
            }
            if (!c1015GlQ.N2()) {
                S10 s10M = (s10O2 == c1015GlQ.O2() ? c1015GlQ.P2() : c1015GlQ.O2()).m();
                if (s10M.i() || !s10M.b.B1()) {
                    return c2088gY;
                }
                iN2 = s10M.b.E().N2();
            }
            C3505zR c3505zR = new C3505zR(16);
            if (!AbstractC2162hY.a && c1015GlQ.L2() != enumC1076Il && c1015GlQ.L2() != EnumC1076Il.NE) {
                throw new AssertionError();
            }
            if (!a(c1015GlQ.L2() == enumC1076Il ? c1015GlQ.K2() : c1015GlQ.J2(), iN2, c3505zR, OU.c())) {
                return c2088gY;
            }
            if (c2088gY == null) {
                c2088gY = new C2088gY(this.b, s10O2);
            }
            InterfaceC1912eK it = c3505zR.keySet().iterator();
            while (it.hasNext()) {
                C0759u2 c0759u2 = (C0759u2) it.next();
                c2088gY.c.a(c3505zR.a(c0759u2), c0759u2);
            }
            c2088gY.a = c1015GlQ.h();
            return b(c2088gY, c1015GlQ.h());
        }
        if (!abstractC1954euM.T1()) {
            return c2088gY;
        }
        C1458Vr c1458VrW = abstractC1954euM.W();
        C0722p1 c0722p2 = this.b;
        S10 s10Value = c1458VrW.value();
        if (!AbstractC2242iY.a(c0722p2, s10Value)) {
            s10Value = null;
        }
        if (s10Value == null) {
            return c2088gY;
        }
        if (c2088gY != null && s10Value != (s10 = c2088gY.b) && s10Value.b.i0().U2() != s10.b.i0().U2()) {
            return c2088gY;
        }
        C3505zR c3505zR2 = new C3505zR(16);
        while (iN2 < c1458VrW.L2()) {
            if (!a(c1458VrW.e(iN2), c1458VrW.f(iN2), c3505zR2, OU.c())) {
                return c2088gY;
            }
            iN2++;
        }
        if (c2088gY == null) {
            c2088gY = new C2088gY(this.b, s10Value);
        }
        InterfaceC1912eK it2 = c3505zR2.keySet().iterator();
        while (it2.hasNext()) {
            C0759u2 c0759u3 = (C0759u2) it2.next();
            c2088gY.c.a(c3505zR2.a(c0759u3), c0759u3);
        }
        c2088gY.a = c1458VrW.h();
        return b(c2088gY, c1458VrW.h());
    }

    private boolean a(C3332x4 c3332x4, int i, C3505zR c3505zR, Set set) {
        C2115gt c2115gtI0;
        C1015Gl c1015GlQ;
        InterfaceC2488lo interfaceC2488loR = c3332x4.R();
        AbstractC2412ko next = interfaceC2488loR.next();
        C1033Hc c1033HcF = next.F();
        if (c1033HcF != null && c1033HcF.i1()) {
            return false;
        }
        if (next instanceof C1033Hc) {
            c2115gtI0 = interfaceC2488loR.next().i0();
        } else {
            c2115gtI0 = next.i0();
        }
        if (c2115gtI0 == null || c2115gtI0.S2() != this.b.k4.i || c2115gtI0.U2() != this.d) {
            return false;
        }
        if (c1033HcF != null && c2115gtI0.c(1) != c1033HcF.b()) {
            if (e) {
                return false;
            }
            throw new AssertionError();
        }
        S10 s10M = c2115gtI0.c(1).m();
        if (!s10M.b(new JY$$ExternalSyntheticLambda0()) || (c1015GlQ = interfaceC2488loR.next().Q()) == null) {
            return false;
        }
        if (c1015GlQ.L2() != EnumC1076Il.EQ && c1015GlQ.L2() != EnumC1076Il.NE) {
            return false;
        }
        try {
            C0759u2 c0759u2J2 = s10M.b.F().J2();
            if (c0759u2J2.u0() == i && !a(c1015GlQ.c(1), c0759u2J2, c3505zR)) {
                return false;
            }
            C3332x4 c3332x4J = c1015GlQ.c(0).j();
            if (c3332x4J == this.a) {
                return true;
            }
            if (set.add(c3332x4J)) {
                return a(c3332x4J, i, c3505zR, set);
            }
            return false;
        } catch (UTFDataFormatException unused) {
            throw new C3405y10();
        }
    }

    private boolean a(C3332x4 c3332x4, C0759u2 c0759u2, C3505zR c3505zR) {
        C0978Fc c0978FcE;
        C1296Qj c1296QjP;
        InterfaceC2488lo interfaceC2488loR = c3332x4.R();
        if (c3332x4.Q()) {
            if (c3332x4.A() != this.c.h()) {
                return false;
            }
            AbstractC2412ko abstractC2412ko = this.c.c(this.c.h().y().indexOf(c3332x4)).b;
            int i = C0978Fc.k;
            c0978FcE = abstractC2412ko == null ? null : abstractC2412ko.E();
        } else {
            c0978FcE = interfaceC2488loR.next().E();
        }
        if (c0978FcE == null || !this.c.k0().contains(c0978FcE.b()) || (c1296QjP = interfaceC2488loR.next().P()) == null || c1296QjP.K2().j() != this.a) {
            return false;
        }
        c3505zR.putIfAbsent(c0759u2, Integer.valueOf(c0978FcE.N2()));
        return true;
    }
}
