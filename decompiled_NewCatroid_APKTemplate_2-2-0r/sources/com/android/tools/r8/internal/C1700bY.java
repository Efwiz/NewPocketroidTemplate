package com.android.tools.r8.internal;

import com.android.tools.r8.utils.InterfaceC3937n1;
import java.util.Objects;

/* JADX INFO: renamed from: com.android.tools.r8.internal.bY, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1700bY {
    C1700bY() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public C1773cY a(C1773cY c1773cY, C3332x4 c3332x4) {
        C3332x4 c3332x4J = c3332x4.j();
        if (c3332x4J == null) {
            if (c1773cY != null) {
                c1773cY.a = c3332x4;
            }
            return c1773cY;
        }
        int size = c3332x4J.p().size();
        if (size == 1) {
            AbstractC1954eu abstractC1954euM = c3332x4J.m();
            if (abstractC1954euM.N1()) {
                return a(c1773cY, abstractC1954euM.Q(), c3332x4);
            }
            if (abstractC1954euM.T1()) {
                return a(c1773cY, abstractC1954euM.W(), c3332x4);
            }
        }
        if (size == 2) {
            AbstractC2412ko abstractC2412koK = c3332x4J.k();
            AbstractC1954eu abstractC1954euM2 = c3332x4J.m();
            if (abstractC2412koK.B1() && abstractC2412koK.b().a(c3332x4J) && abstractC1954euM2.N1()) {
                return a(c1773cY, abstractC1954euM2.Q(), c3332x4);
            }
        }
        if (c1773cY != null) {
            c1773cY.a = c3332x4;
        }
        return c1773cY;
    }

    /* JADX WARN: Code duplicated, block: B:17:0x0037  */
    private C1773cY a(C1773cY c1773cY, C1015Gl c1015Gl, C3332x4 c3332x4) {
        ML mlL;
        int iN2;
        EnumC1076Il enumC1076IlL2 = c1015Gl.L2();
        EnumC1076Il enumC1076Il = EnumC1076Il.EQ;
        if (enumC1076IlL2 != enumC1076Il && enumC1076IlL2 != EnumC1076Il.NE) {
            if (c1773cY != null) {
                c1773cY.a = c3332x4;
            }
            return c1773cY;
        }
        S10 s10O2 = c1015Gl.O2();
        if (s10O2.i()) {
            mlL = s10O2.l();
        } else if (c1015Gl.N2()) {
            mlL = null;
        } else {
            S10 s10P2 = c1015Gl.P2();
            if (s10P2.i()) {
                mlL = s10P2.l();
            } else {
                mlL = null;
            }
        }
        if (mlL == null || !(c1773cY == null || mlL == c1773cY.b)) {
            if (c1773cY != null) {
                c1773cY.a = c3332x4;
            }
            return c1773cY;
        }
        if (c1015Gl.N2()) {
            iN2 = 0;
        } else {
            S10 s10M = (mlL == c1015Gl.O2() ? c1015Gl.P2() : c1015Gl.O2()).m();
            if (s10M.i() || !s10M.b.B1()) {
                if (c1773cY != null) {
                    c1773cY.a = c3332x4;
                }
                return c1773cY;
            }
            iN2 = s10M.b.E().N2();
        }
        if (c1773cY == null) {
            c1773cY = new C1773cY(mlL);
        }
        C1303Qq c1303Qq = c1773cY.c;
        Integer numValueOf = Integer.valueOf(iN2);
        if (!AbstractC2162hY.a && c1015Gl.L2() != enumC1076Il && c1015Gl.L2() != EnumC1076Il.NE) {
            throw new AssertionError();
        }
        c1303Qq.putIfAbsent(numValueOf, c1015Gl.L2() == enumC1076Il ? c1015Gl.K2() : c1015Gl.J2());
        return a(c1773cY, AbstractC2162hY.a(c1015Gl));
    }

    private C1773cY a(C1773cY c1773cY, C1458Vr c1458Vr, C3332x4 c3332x4) {
        S10 s10Value = c1458Vr.value();
        if (!s10Value.i() || (c1773cY != null && s10Value != c1773cY.b)) {
            if (c1773cY != null) {
                c1773cY.a = c3332x4;
            }
            return c1773cY;
        }
        ML mlL = s10Value.l();
        if (c1773cY == null) {
            c1773cY = new C1773cY(mlL);
        }
        final C1303Qq c1303Qq = c1773cY.c;
        Objects.requireNonNull(c1303Qq);
        c1458Vr.a(new InterfaceC3937n1() { // from class: com.android.tools.r8.internal.bY$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.utils.InterfaceC3937n1
            public final void a(int i, Object obj) {
                c1303Qq.putIfAbsent(Integer.valueOf(i), (C3332x4) obj);
            }
        });
        return a(c1773cY, c1458Vr.J2());
    }
}
