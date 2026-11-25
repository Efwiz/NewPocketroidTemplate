package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.shaking.C3733i;
import java.util.ArrayDeque;
import java.util.BitSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class T9 {
    static final /* synthetic */ boolean a = true;

    public static boolean a(C2179hn c2179hn, C0767v2 c0767v2, C0702m c0702m, U9 u9, S9 s9) {
        AbstractC0727q0 abstractC0727q0C;
        return (s9 == S9.b || (abstractC0727q0C = c0702m.c(c2179hn.K2())) == null || !a(c2179hn, c0767v2, abstractC0727q0C, c0702m, u9)) ? false : true;
    }

    public static boolean a(C1189Mn c1189Mn, C0767v2 c0767v2, C0702m c0702m, U9 u9, S9 s9) {
        return a((AbstractC1182Mh) c1189Mn, c0767v2, c0702m, u9, s9);
    }

    public static boolean a(C1793co c1793co, C0767v2 c0767v2, C0702m c0702m, U9 u9, S9 s9) {
        return a((AbstractC1182Mh) c1793co, c0767v2, c0702m, u9, s9);
    }

    private static boolean a(AbstractC1182Mh abstractC1182Mh, C0767v2 c0767v2, C0702m c0702m, U9 u9, S9 s9) {
        S10 s10E;
        if (!a) {
            abstractC1182Mh.getClass();
            if (!(abstractC1182Mh instanceof C1189Mn) && !abstractC1182Mh.S1()) {
                throw new AssertionError();
            }
        }
        if (s9 == S9.b) {
            abstractC1182Mh.getClass();
            if (abstractC1182Mh instanceof C1189Mn) {
                s10E = abstractC1182Mh.T().e();
            } else {
                s10E = abstractC1182Mh.V().e();
            }
            if (s10E.w().F()) {
                return false;
            }
        }
        com.android.tools.r8.graph.T0 t0S = ((C3733i) c0702m.f()).c(abstractC1182Mh.getField()).s();
        return t0S != null && a(abstractC1182Mh, c0767v2, t0S, c0702m, u9);
    }

    public static boolean a(C1166Ls c1166Ls, C0767v2 c0767v2, C0702m c0702m, U9 u9, S9 s9) {
        if (s9 == S9.b && c1166Ls.U2().w().F()) {
            return false;
        }
        C0684j2 c0684j2S2 = c1166Ls.S2();
        c0702m.getClass();
        com.android.tools.r8.graph.W0 w0C = c0684j2S2.c(c0702m.c(c0684j2S2.e));
        return w0C != null && a(c1166Ls, c0767v2, w0C, c0702m, u9);
    }

    public static boolean a(C1248Os c1248Os, C0767v2 c0767v2, com.android.tools.r8.graph.o5 o5Var, C0702m c0702m, U9 u9, S9 s9) {
        if ((s9 == S9.b && c1248Os.U2().w().F()) || u9 == U9.a) {
            return false;
        }
        ((C3733i) c0702m.f()).i();
        AbstractC0748t0 abstractC0748t0H = c1248Os.h(c0702m.i0(), o5Var);
        if (abstractC0748t0H != null) {
            return a(c1248Os, c0767v2, abstractC0748t0H.e(), c0702m, u9);
        }
        C0684j2 c0684j2S2 = c1248Os.S2();
        com.android.tools.r8.graph.I4 i4B = ((C3733i) c0702m.f()).b(c0684j2S2.e, c0684j2S2);
        if (i4B.x()) {
            return c0702m.a(i4B.t().J0(), c0767v2).d();
        }
        return false;
    }

    public static boolean a(C1721bt c1721bt, C0767v2 c0767v2, com.android.tools.r8.graph.o5 o5Var, C0702m c0702m, U9 u9, S9 s9) {
        AbstractC0748t0 abstractC0748t0H;
        return (s9 == S9.b || (abstractC0748t0H = c1721bt.h(c0702m, o5Var)) == null || !a(c1721bt, c0767v2, abstractC0748t0H.e(), c0702m, u9)) ? false : true;
    }

    public static boolean a(C1798ct c1798ct, C0767v2 c0767v2, com.android.tools.r8.graph.o5 o5Var, C0702m c0702m, U9 u9, S9 s9) {
        C0767v2 c0767v3;
        if ((s9 == S9.b && c1798ct.U2().w().F()) || u9 == U9.a) {
            return false;
        }
        ((C3733i) c0702m.f()).i();
        AbstractC0748t0 abstractC0748t0H = c1798ct.h(c0702m.i0(), o5Var);
        if (abstractC0748t0H != null) {
            return a(c1798ct, c0767v2, abstractC0748t0H.e(), c0702m, u9);
        }
        C0684j2 c0684j2S2 = c1798ct.S2();
        AbstractC0727q0 abstractC0727q0C = c0702m.c(c0684j2S2.e);
        if (abstractC0727q0C == null || (c0767v3 = abstractC0727q0C.f) == null) {
            return false;
        }
        com.android.tools.r8.graph.I4 i4B = ((C3733i) c0702m.f()).b(c0767v3, c0684j2S2, c1798ct.l);
        if (i4B.x()) {
            return c0702m.a(i4B.t().J0(), c0767v2).d();
        }
        return false;
    }

    public static boolean a(C2115gt c2115gt, C0767v2 c0767v2, com.android.tools.r8.graph.o5 o5Var, C0702m c0702m, U9 u9, S9 s9) {
        if ((s9 == S9.b && c2115gt.U2().w().F()) || u9 == U9.a) {
            return false;
        }
        ((C3733i) c0702m.f()).i();
        AbstractC0748t0 abstractC0748t0H = c2115gt.h(c0702m.i0(), o5Var);
        if (abstractC0748t0H != null) {
            return a(c2115gt, c0767v2, abstractC0748t0H.e(), c0702m, u9);
        }
        C0684j2 c0684j2S2 = c2115gt.S2();
        com.android.tools.r8.graph.I4 i4A = ((C3733i) c0702m.f()).a(c0684j2S2.e, c0684j2S2);
        if (i4A.x()) {
            return c0702m.a(i4A.t().J0(), c0767v2).d();
        }
        return false;
    }

    public static boolean a(DG dg, C0767v2 c0767v2, C0702m c0702m, U9 u9, S9 s9) {
        AbstractC0727q0 abstractC0727q0C;
        return (s9 == S9.b || (abstractC0727q0C = c0702m.c(dg.h)) == null || !a(dg, c0767v2, abstractC0727q0C, c0702m, u9)) ? false : true;
    }

    public static boolean a(QW qw, C0767v2 c0767v2, C0702m c0702m, U9 u9, S9 s9) {
        com.android.tools.r8.graph.T0 t0S;
        if (!a) {
            qw.getClass();
        }
        return (s9 == S9.b || (t0S = ((C3733i) c0702m.f()).c(qw.getField()).s()) == null || !a(qw, c0767v2, t0S, c0702m, u9)) ? false : true;
    }

    public static boolean a(RW rw, C0767v2 c0767v2, C0702m c0702m, U9 u9, S9 s9) {
        com.android.tools.r8.graph.T0 t0S;
        if (!a) {
            rw.getClass();
            boolean z = rw instanceof QW;
        }
        return (s9 == S9.b || (t0S = ((C3733i) c0702m.f()).c(rw.getField()).s()) == null || !a(rw, c0767v2, t0S, c0702m, u9)) ? false : true;
    }

    private static boolean a(AbstractC2412ko abstractC2412ko, C0767v2 c0767v2, com.android.tools.r8.graph.O0 o0, C0702m c0702m, U9 u9) {
        com.android.tools.r8.graph.W0 w0Y0;
        if (u9 == U9.a) {
            if (o0.A0()) {
                return o0.t0().d == c0767v2;
            }
            if (o0.C0()) {
                return o0.v0().getReference().e == c0767v2;
            }
            throw new C3405y10();
        }
        Set setC = OU.c();
        ArrayDeque arrayDeque = new ArrayDeque();
        if (o0.A0()) {
            a(o0.t0().d, setC, arrayDeque);
        } else if (o0 instanceof com.android.tools.r8.graph.T0) {
            a(o0.u0().J0(), setC, arrayDeque);
        } else if (o0.D0()) {
            if (!a && !abstractC2412ko.Y1()) {
                throw new AssertionError();
            }
            com.android.tools.r8.graph.W0 w0 = o0.w0();
            a(w0.J0(), setC, arrayDeque);
            a(w0, abstractC2412ko.h1(), setC, arrayDeque);
        } else if (!a) {
            throw new AssertionError();
        }
        while (!arrayDeque.isEmpty()) {
            C0767v2 c0767v3 = (C0767v2) arrayDeque.removeFirst();
            if (!a && !setC.contains(c0767v3)) {
                throw new AssertionError();
            }
            if (c0702m.a(c0767v3, c0767v2).d()) {
                return true;
            }
            AbstractC0727q0 abstractC0727q0C = c0702m.c(c0767v3);
            if (abstractC0727q0C != null && (w0Y0 = abstractC0727q0C.Y0()) != null) {
                a(w0Y0, AbstractC1639am.i(), setC, arrayDeque);
            }
        }
        return false;
    }

    private static void a(C0767v2 c0767v2, Set set, ArrayDeque arrayDeque) {
        if (c0767v2.O0() && set.add(c0767v2)) {
            arrayDeque.add(c0767v2);
        }
    }

    private static void a(com.android.tools.r8.graph.W0 w0, List list, Set set, ArrayDeque arrayDeque) {
        Iterator it = w0.i1().o().iterator();
        while (it.hasNext()) {
            a((C0767v2) it.next(), set, arrayDeque);
        }
        if (!w0.E0()) {
            if (!a && list.size() <= 0) {
                throw new AssertionError();
            }
            AbstractC3472z00 abstractC3472z00W = ((S10) list.get(0)).w();
            if (abstractC3472z00W.w()) {
                a(abstractC3472z00W.b().R(), set, arrayDeque);
            }
        }
        BitSet bitSetQ = w0.i1().q();
        if (bitSetQ != null) {
            for (int i = 0; i < list.size(); i++) {
                if (bitSetQ.get(i)) {
                    AbstractC3472z00 abstractC3472z00W2 = ((S10) list.get(i)).w();
                    if (abstractC3472z00W2.w()) {
                        a(abstractC3472z00W2.b().R(), set, arrayDeque);
                    }
                }
            }
        }
    }
}
