package com.android.tools.r8.ir.optimize;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.internal.AbstractC1954eu;
import com.android.tools.r8.internal.AbstractC2412ko;
import com.android.tools.r8.internal.AbstractC3472z00;
import com.android.tools.r8.internal.C1015Gl;
import com.android.tools.r8.internal.C1296Qj;
import com.android.tools.r8.internal.C1484Wr;
import com.android.tools.r8.internal.C2409kl;
import com.android.tools.r8.internal.C3218vY;
import com.android.tools.r8.internal.C3332x4;
import com.android.tools.r8.internal.C3405y10;
import com.android.tools.r8.internal.ML;
import com.android.tools.r8.internal.OU;
import com.android.tools.r8.internal.S10;
import com.android.tools.r8.utils.O3;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.ir.optimize.n0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3574n0 {
    static final /* synthetic */ boolean a = true;

    /* JADX WARN: Code duplicated, block: B:156:0x0270  */
    /* JADX WARN: Code duplicated, block: B:226:0x044d  */
    public static void a(C0702m c0702m, C2409kl c2409kl) {
        AbstractC2412ko next;
        boolean z;
        boolean z2;
        Set setB;
        C3332x4 c3332x4K2;
        C3332x4 c3332x4J2;
        C3332x4 c3332x4;
        boolean z3;
        if (c0702m.Y().X) {
            boolean z4 = false;
            for (C3332x4 c3332x5 : c2409kl.d) {
                if (c3332x5.m().N1() && !c3332x5.m().Q().N2()) {
                    Iterator<AbstractC2412ko> it = c3332x5.p().iterator();
                    do {
                        if (!it.hasNext()) {
                            throw new C3405y10();
                        }
                        next = it.next();
                        if (next.N1()) {
                            C1015Gl c1015GlQ = c3332x5.m().Q();
                            C3332x4 c3332x6 = null;
                            ML mlL = (c1015GlQ.P2().L() && c1015GlQ.O2().i()) ? c1015GlQ.O2().l() : (c1015GlQ.O2().L() && c1015GlQ.P2().i()) ? c1015GlQ.P2().l() : null;
                            if (mlL == null || mlL.k0().size() != 2 || mlL.h() != c1015GlQ.h()) {
                                mlL = null;
                            }
                            if (mlL == null) {
                                z = false;
                            } else {
                                C3332x4 c3332x7 = null;
                                C3332x4 c3332x8 = null;
                                int i = 0;
                                int i2 = 0;
                                int iN2 = 0;
                                while (true) {
                                    if (i < mlL.k0().size()) {
                                        S10 s10C = mlL.c(i);
                                        if (!s10C.i()) {
                                            C3332x4 c3332x9 = c1015GlQ.h().y().get(i);
                                            if (s10C.J()) {
                                                AbstractC3472z00 abstractC3472z00W = s10C.w();
                                                abstractC3472z00W.getClass();
                                                if ((abstractC3472z00W instanceof C1484Wr) && c3332x7 == null) {
                                                    iN2 = s10C.b.E().N2();
                                                    c3332x7 = c3332x9;
                                                    i++;
                                                }
                                            } else if (!s10C.b.k1()) {
                                                AbstractC2412ko abstractC2412ko = s10C.b;
                                                abstractC2412ko.getClass();
                                                if ((abstractC2412ko instanceof C3218vY) && c3332x8 == null) {
                                                    C3218vY c3218vYJ0 = s10C.b.J0();
                                                    if (c3218vYJ0.L2() == mlL) {
                                                        S10 s10N2 = c3218vYJ0.N2();
                                                        if (s10N2.J()) {
                                                            AbstractC3472z00 abstractC3472z00W2 = s10N2.w();
                                                            abstractC3472z00W2.getClass();
                                                            if (abstractC3472z00W2 instanceof C1484Wr) {
                                                                if (!a && i2 != 0) {
                                                                    throw new AssertionError();
                                                                }
                                                                int i3 = -s10N2.b.E().N2();
                                                                if (i3 != 0) {
                                                                    i2 = i3;
                                                                    c3332x8 = c3332x9;
                                                                    i++;
                                                                }
                                                            }
                                                        }
                                                    }
                                                    c3332x8 = c3332x9;
                                                }
                                            } else if (c3332x8 == null) {
                                                Iterator it2 = ((ArrayList) s10C.b.h1()).iterator();
                                                boolean z5 = false;
                                                while (true) {
                                                    if (it2.hasNext()) {
                                                        S10 s10 = (S10) it2.next();
                                                        if (s10.J()) {
                                                            AbstractC3472z00 abstractC3472z00W3 = s10.w();
                                                            abstractC3472z00W3.getClass();
                                                            if (abstractC3472z00W3 instanceof C1484Wr) {
                                                                int iN3 = s10.b.E().N2();
                                                                if (iN3 == 0 || i2 != 0) {
                                                                    c3332x8 = c3332x9;
                                                                } else {
                                                                    i2 = iN3;
                                                                }
                                                            }
                                                        }
                                                        if (s10 != mlL || z5) {
                                                            c3332x8 = c3332x9;
                                                        } else {
                                                            z5 = true;
                                                        }
                                                    } else {
                                                        c3332x8 = c3332x9;
                                                        i++;
                                                    }
                                                }
                                            }
                                        }
                                        z2 = false;
                                    } else {
                                        boolean z6 = a;
                                        if (!z6 && c3332x7 == null) {
                                            throw new AssertionError();
                                        }
                                        if (!z6 && !c3332x7.m().M1()) {
                                            throw new AssertionError();
                                        }
                                        if (!z6 && c3332x8 == null) {
                                            throw new AssertionError();
                                        }
                                        if (!z6 && !c3332x8.m().M1()) {
                                            throw new AssertionError();
                                        }
                                        if (!z6 && i2 == 0) {
                                            throw new AssertionError();
                                        }
                                        z2 = true;
                                    }
                                    if (z2) {
                                        C3332x4 c3332x4H = c1015GlQ.h();
                                        O3 o3E = O3.e();
                                        o3E.c(c3332x8);
                                        o3E.f(c3332x4H);
                                        while (true) {
                                            if (!o3E.c()) {
                                                setB = o3E.b();
                                                break;
                                            }
                                            C3332x4 c3332x10 = (C3332x4) o3E.f();
                                            if (c3332x10.y().isEmpty()) {
                                                setB = null;
                                                break;
                                            } else {
                                                Iterator<C3332x4> it3 = c3332x10.y().iterator();
                                                while (it3.hasNext()) {
                                                    o3E.c(it3.next());
                                                }
                                            }
                                        }
                                        if (setB != null) {
                                            if (!setB.contains(c3332x7)) {
                                                if (setB.contains(c1015GlQ.K2())) {
                                                    if (setB.contains(c1015GlQ.J2())) {
                                                        c3332x4 = null;
                                                        z3 = false;
                                                    } else {
                                                        c3332x4K2 = c1015GlQ.J2();
                                                        c3332x4J2 = c1015GlQ.K2();
                                                        C3332x4 c3332x11 = c3332x4J2;
                                                        c3332x6 = c3332x4K2;
                                                        c3332x4 = c3332x11;
                                                        z3 = true;
                                                    }
                                                } else if (setB.contains(c1015GlQ.J2())) {
                                                    c3332x4K2 = c1015GlQ.K2();
                                                    c3332x4J2 = c1015GlQ.J2();
                                                    C3332x4 c3332x12 = c3332x4J2;
                                                    c3332x6 = c3332x4K2;
                                                    c3332x4 = c3332x12;
                                                    z3 = true;
                                                } else {
                                                    c3332x4 = null;
                                                    z3 = false;
                                                }
                                                if (z3) {
                                                    Set setC = OU.c();
                                                    Iterator it4 = setB.iterator();
                                                    while (it4.hasNext()) {
                                                        setC.addAll(((C3332x4) it4.next()).z());
                                                    }
                                                    setC.removeAll(setB);
                                                    if (setC.size() != 1) {
                                                        Iterator<ML> it5 = c1015GlQ.h().x().iterator();
                                                        while (true) {
                                                            if (it5.hasNext()) {
                                                                ML next2 = it5.next();
                                                                Iterator<AbstractC2412ko> it6 = next2.h0().iterator();
                                                                while (true) {
                                                                    if (!it6.hasNext()) {
                                                                        Iterator it7 = next2.g0().iterator();
                                                                        while (true) {
                                                                            if (it7.hasNext()) {
                                                                                if (!setB.contains(((ML) it7.next()).h())) {
                                                                                }
                                                                            }
                                                                        }
                                                                    } else if (!setB.contains(it6.next().h())) {
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } else if (!a && setC.iterator().next() != c3332x6) {
                                                        throw new AssertionError();
                                                    }
                                                    if ((c1015GlQ.P2().J() ? c1015GlQ.c(Integer.signum(iN2 - c1015GlQ.P2().q().E().N2())) : c1015GlQ.c(Integer.signum(c1015GlQ.O2().q().E().N2() - iN2))) == c3332x4) {
                                                        int i4 = iN2 + i2;
                                                        if ((c1015GlQ.P2().J() ? c1015GlQ.c(Integer.signum(i4 - c1015GlQ.P2().q().E().N2())) : c1015GlQ.c(Integer.signum(c1015GlQ.O2().q().E().N2() - i4))) == c3332x6) {
                                                            C3332x4 c3332x4H2 = c1015GlQ.h();
                                                            int iIndexOf = c3332x4H2.y().indexOf(c3332x8);
                                                            for (ML ml : c3332x4H2.x()) {
                                                                S10 s10C2 = ml.c(1 - iIndexOf);
                                                                S10 s10C3 = ml.c(iIndexOf);
                                                                for (AbstractC2412ko abstractC2412ko2 : ml.h0()) {
                                                                    if (setB.contains(abstractC2412ko2.h())) {
                                                                        abstractC2412ko2.b(ml, s10C2);
                                                                    } else {
                                                                        abstractC2412ko2.b(ml, s10C3);
                                                                    }
                                                                }
                                                                for (ML ml2 : ml.g0()) {
                                                                    if (setB.contains(ml2.h())) {
                                                                        ml2.a(ml, s10C2);
                                                                    } else {
                                                                        ml2.a(ml, s10C3);
                                                                    }
                                                                }
                                                            }
                                                            if (!AbstractC3572m0.a && !c3332x6.x().isEmpty()) {
                                                                throw new AssertionError();
                                                            }
                                                            C1296Qj c1296Qj = new C1296Qj();
                                                            c1296Qj.a(c3332x4);
                                                            c3332x4H2.a(c2409kl, (AbstractC1954eu) c1296Qj);
                                                            c3332x4H2.k(c3332x6);
                                                            c3332x8.b(c3332x4H2, c3332x6);
                                                            C1296Qj c1296Qj2 = new C1296Qj();
                                                            c1296Qj2.a(c3332x6);
                                                            c3332x8.a(c2409kl, (AbstractC1954eu) c1296Qj2);
                                                            c3332x4H2.a(c3332x8, OU.c());
                                                            c3332x6.a(c3332x4H2, c3332x8);
                                                            z = true;
                                                        }
                                                    }
                                                }
                                            } else if (!a) {
                                                throw new AssertionError();
                                            }
                                            z = false;
                                        } else {
                                            z = false;
                                        }
                                    } else {
                                        z = false;
                                    }
                                }
                            }
                            z4 |= z;
                            break;
                        }
                    } while (next.B1());
                }
            }
            if (z4) {
                c2409kl.y();
                if (!a && !c2409kl.b((C0702m<?>) c0702m)) {
                    throw new AssertionError();
                }
            }
        }
    }
}
