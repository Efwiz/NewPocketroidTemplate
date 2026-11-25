package com.android.tools.r8.ir.optimize;

import com.android.tools.r8.ClassFileConsumer;
import com.android.tools.r8.graph.AbstractC0639e;
import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0692k3;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0737r2;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.S1;
import com.android.tools.r8.graph.W0;
import com.android.tools.r8.graph.Y0;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.A4;
import com.android.tools.r8.internal.AF;
import com.android.tools.r8.internal.AG;
import com.android.tools.r8.internal.AbstractC0984Fi;
import com.android.tools.r8.internal.AbstractC1046Hk;
import com.android.tools.r8.internal.AbstractC1048Hm;
import com.android.tools.r8.internal.AbstractC1084Is;
import com.android.tools.r8.internal.AbstractC1154Lf;
import com.android.tools.r8.internal.AbstractC1182Mh;
import com.android.tools.r8.internal.AbstractC1305Qs;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.AbstractC1699bX;
import com.android.tools.r8.internal.AbstractC1915eM;
import com.android.tools.r8.internal.AbstractC1954eu;
import com.android.tools.r8.internal.AbstractC2412ko;
import com.android.tools.r8.internal.AbstractC2706oV;
import com.android.tools.r8.internal.AbstractC2734ot;
import com.android.tools.r8.internal.AbstractC2871ql;
import com.android.tools.r8.internal.AbstractC2967s4;
import com.android.tools.r8.internal.AbstractC3472z00;
import com.android.tools.r8.internal.B1;
import com.android.tools.r8.internal.B4;
import com.android.tools.r8.internal.BG;
import com.android.tools.r8.internal.C0827Af;
import com.android.tools.r8.internal.C0978Fc;
import com.android.tools.r8.internal.C1015Gl;
import com.android.tools.r8.internal.C1033Hc;
import com.android.tools.r8.internal.C1166Ls;
import com.android.tools.r8.internal.C1189Mn;
import com.android.tools.r8.internal.C1207Nf;
import com.android.tools.r8.internal.C1230Ob;
import com.android.tools.r8.internal.C1248Os;
import com.android.tools.r8.internal.C1289Qd;
import com.android.tools.r8.internal.C1296Qj;
import com.android.tools.r8.internal.C1301Qo;
import com.android.tools.r8.internal.C1303Qq;
import com.android.tools.r8.internal.C1316Rd;
import com.android.tools.r8.internal.C1343Sd;
import com.android.tools.r8.internal.C1367Tb;
import com.android.tools.r8.internal.C1385Ts;
import com.android.tools.r8.internal.C1458Vr;
import com.android.tools.r8.internal.C1582a3;
import com.android.tools.r8.internal.C1621aZ;
import com.android.tools.r8.internal.C1641ao;
import com.android.tools.r8.internal.C1646at;
import com.android.tools.r8.internal.C1721bt;
import com.android.tools.r8.internal.C1736c3;
import com.android.tools.r8.internal.C1969f3;
import com.android.tools.r8.internal.C2102gl;
import com.android.tools.r8.internal.C2115gt;
import com.android.tools.r8.internal.C2318jb;
import com.android.tools.r8.internal.C2332jo;
import com.android.tools.r8.internal.C2388kV;
import com.android.tools.r8.internal.C2409kl;
import com.android.tools.r8.internal.C2477lf;
import com.android.tools.r8.internal.C2561ml;
import com.android.tools.r8.internal.C2732or;
import com.android.tools.r8.internal.C2807pq;
import com.android.tools.r8.internal.C2969s6;
import com.android.tools.r8.internal.C2972s9;
import com.android.tools.r8.internal.C2993sU;
import com.android.tools.r8.internal.C3030t00;
import com.android.tools.r8.internal.C3038t6;
import com.android.tools.r8.internal.C3133uU;
import com.android.tools.r8.internal.C3155ul;
import com.android.tools.r8.internal.C3332x4;
import com.android.tools.r8.internal.C3405y10;
import com.android.tools.r8.internal.C3409y4;
import com.android.tools.r8.internal.C3446yc;
import com.android.tools.r8.internal.C3479z4;
import com.android.tools.r8.internal.C3486zB;
import com.android.tools.r8.internal.C3492zF;
import com.android.tools.r8.internal.DG;
import com.android.tools.r8.internal.EnumC1076Il;
import com.android.tools.r8.internal.EnumC1812d20;
import com.android.tools.r8.internal.EnumC2527mI;
import com.android.tools.r8.internal.IW$$ExternalSyntheticLambda0;
import com.android.tools.r8.internal.IY;
import com.android.tools.r8.internal.InterfaceC0841Ar;
import com.android.tools.r8.internal.InterfaceC0934Dq;
import com.android.tools.r8.internal.InterfaceC0967Er;
import com.android.tools.r8.internal.InterfaceC0992Fq;
import com.android.tools.r8.internal.InterfaceC1912eK;
import com.android.tools.r8.internal.InterfaceC2641no;
import com.android.tools.r8.internal.InterfaceC2729oo;
import com.android.tools.r8.internal.InterfaceC3205vM;
import com.android.tools.r8.internal.JY;
import com.android.tools.r8.internal.K00;
import com.android.tools.r8.internal.Lr;
import com.android.tools.r8.internal.M10;
import com.android.tools.r8.internal.ML;
import com.android.tools.r8.internal.MR;
import com.android.tools.r8.internal.N3;
import com.android.tools.r8.internal.OU;
import com.android.tools.r8.internal.P30;
import com.android.tools.r8.internal.QC;
import com.android.tools.r8.internal.QW;
import com.android.tools.r8.internal.Rs$$ExternalSyntheticLambda0;
import com.android.tools.r8.internal.S10;
import com.android.tools.r8.internal.T4;
import com.android.tools.r8.internal.TE;
import com.android.tools.r8.internal.VH$$ExternalSyntheticLambda0;
import com.android.tools.r8.internal.WH;
import com.android.tools.r8.internal.Z2;
import com.android.tools.r8.internal.uX$$ExternalSyntheticLambda0;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.utils.A1;
import com.android.tools.r8.utils.F1;
import com.android.tools.r8.utils.H1;
import com.android.tools.r8.utils.L1;
import com.android.tools.r8.utils.O3;
import com.android.tools.r8.utils.P1;
import com.android.tools.r8.utils.T2;
import com.android.tools.r8.utils.W1;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import okhttp3.internal.ws.WebSocketProtocol;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class F {
    static final /* synthetic */ boolean d = true;
    private final C0702m a;
    private final C0722p1 b;
    private final A1 c;

    public F(C0702m<?> c0702m) {
        this.a = c0702m;
        this.c = c0702m.Y();
        this.b = c0702m.a();
    }

    private static void a(C2409kl c2409kl, C3332x4 c3332x4, C3332x4 c3332x5, ArrayList arrayList) {
        if (AbstractC2967s4.a(c3332x4) == c3332x4) {
            return;
        }
        C3332x4 c3332x4J = c3332x4.j();
        if (c3332x4J == null) {
            c3332x4J = AbstractC2967s4.a(c3332x4);
        }
        if (c3332x4J != c3332x5) {
            if (c2409kl.j() == c3332x4) {
                return;
            }
            Iterator<C3332x4> it = c3332x4.y().iterator();
            while (it.hasNext()) {
                if (it.next().m().J2() == c3332x4) {
                    return;
                }
            }
        }
        arrayList.add(c3332x4);
        c(c3332x4, c3332x4J);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(C3332x4 c3332x4, C3332x4 c3332x5) {
        return c3332x5 == c3332x4;
    }

    public static void b(C0702m<?> c0702m, C2409kl c2409kl) {
        Set setC = OU.c();
        c2409kl.getClass();
        C2561ml c2561ml = new C2561ml(c2409kl);
        boolean z = false;
        while (c2561ml.hasNext()) {
            AbstractC2412ko abstractC2412ko = (AbstractC2412ko) c2561ml.next();
            abstractC2412ko.getClass();
            if (abstractC2412ko instanceof N3) {
                N3 n3W = abstractC2412ko.w();
                S10 s10N2 = n3W.N2();
                S10 s10B = n3W.b();
                setC.addAll(s10B.a());
                z |= s10B.a0() > 0;
                s10B.g(s10N2);
                c2561ml.remove();
            }
        }
        if (z) {
            c2409kl.a((C2102gl) null, setC);
        }
        if (!setC.isEmpty()) {
            new C3030t00(c0702m, false).a(setC);
        }
        if (!d && !AbstractC1699bX.a(c2409kl.r()).noneMatch(new VH$$ExternalSyntheticLambda0())) {
            throw new AssertionError();
        }
    }

    public static void c(C3332x4 c3332x4, C3332x4 c3332x5) {
        if (!d && !c3332x4.Q()) {
            throw new AssertionError();
        }
        Iterator<C3332x4> it = c3332x4.y().iterator();
        while (it.hasNext()) {
            it.next().b(c3332x4, c3332x5);
        }
        Iterator<C3332x4> it2 = c3332x4.z().iterator();
        while (it2.hasNext()) {
            it2.next().r().remove(c3332x4);
        }
        for (C3332x4 c3332x6 : c3332x4.y()) {
            if (!c3332x5.y().contains(c3332x6)) {
                c3332x5.r().add(c3332x6);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static C0827Af d(C2409kl c2409kl) {
        return new C0827Af(c2409kl, 1);
    }

    public static void f(C2409kl c2409kl) {
        B4 b4S = c2409kl.s();
        boolean z = false;
        while (b4S.hasNext()) {
            C3332x4 next = b4S.next();
            if (next.D()) {
                Iterator it = next.n().iterator();
                do {
                    if (!it.hasNext()) {
                        Iterator it2 = next.n().iterator();
                        while (it2.hasNext()) {
                            ((C3332x4) ((C2969s6) it2.next()).b).c0();
                        }
                        z = true;
                        break;
                    }
                } while (a(c2409kl, (C3332x4) ((C2969s6) it.next()).b));
            }
        }
        if (z) {
            c2409kl.z();
        }
    }

    private static void h(C2409kl c2409kl) {
        B4 b4S = c2409kl.s();
        if (!d && !b4S.hasNext()) {
            throw new AssertionError();
        }
        final C3332x4 next = b4S.next();
        while (true) {
            C3332x4 next2 = b4S.hasNext() ? b4S.next() : null;
            boolean z = d;
            if (!z && next.Q() && AbstractC2967s4.a(next) != next && c2409kl.j() != next && !next.y().stream().anyMatch(new Predicate() { // from class: com.android.tools.r8.ir.optimize.F$$ExternalSyntheticLambda16
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return F.b(next, (C3332x4) obj);
                }
            })) {
                throw new AssertionError();
            }
            if (!z && next.Q() && AbstractC2967s4.a(next) == next2) {
                throw new AssertionError();
            }
            if (next2 == null) {
                return;
            } else {
                next = next2;
            }
        }
    }

    public static void o(C2409kl c2409kl) {
        AbstractC2412ko abstractC2412ko;
        Iterator<C3332x4> it = c2409kl.d.iterator();
        while (it.hasNext()) {
            InterfaceC2641no interfaceC2641noA = it.next().a(c2409kl);
            while (interfaceC2641noA.hasNext()) {
                AbstractC2412ko abstractC2412koJ = interfaceC2641noA.j();
                AbstractC2412ko next = interfaceC2641noA.next();
                next.getClass();
                if (next instanceof C1289Qd) {
                    if (!d && ((ArrayList) next.h1()).size() != 1) {
                        throw new AssertionError();
                    }
                    S10 s10 = (S10) ((ArrayList) next.h1()).get(0);
                    com.android.tools.r8.graph.X xS = next.b().s();
                    C0759u2 c0759u2 = xS.a;
                    if (!s10.A() && s10.Z() == 1 && (abstractC2412ko = s10.b) != null && abstractC2412ko.h() == next.h() && (!abstractC2412ko.Y0().s() || !next.Y0().s() || abstractC2412ko.Y0().equals(next.Y0()))) {
                        Iterator it2 = abstractC2412ko.h().a(abstractC2412ko).iterator();
                        AbstractC1915eM abstractC1915eMY0 = null;
                        while (true) {
                            if (!it2.hasNext()) {
                                throw new C3405y10();
                            }
                            AbstractC2412ko abstractC2412ko2 = (AbstractC2412ko) it2.next();
                            if (abstractC1915eMY0 != null) {
                                if (abstractC2412ko2.Y0().s() && !abstractC1915eMY0.equals(abstractC2412ko2.Y0())) {
                                    break;
                                }
                            } else if (abstractC2412ko2.Y0().s()) {
                                abstractC1915eMY0 = abstractC2412ko2.Y0();
                            }
                            if (abstractC2412ko2 != next) {
                                if (abstractC2412ko2.b() != null && abstractC2412ko2.b().A() && abstractC2412ko2.b().s().a == c0759u2) {
                                    break;
                                }
                            } else {
                                s10.a(xS);
                                next.b().g(s10);
                                S10 s10A = next.a(xS);
                                if (s10A != null) {
                                    s10A.a(s10.b);
                                }
                                if (abstractC2412koJ != null && (abstractC2412koJ.b() == null || !abstractC2412koJ.b().A() || !next.S0().contains(abstractC2412koJ.b()))) {
                                    next.c(abstractC2412koJ);
                                }
                                interfaceC2641noA.s();
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:103:0x0148 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:109:0x005c A[SYNTHETIC] */
    public final void e(C2409kl c2409kl) {
        AbstractC1305Qs abstractC1305QsB0;
        AbstractC0748t0 abstractC0748t0H;
        final Set<S10> setC = OU.c();
        Set<C3332x4> setC2 = OU.c();
        B4 b4S = c2409kl.s();
        o5 o5VarH = c2409kl.h();
        final C0702m c0702mH0 = this.a.f().h() ? this.a.h0() : null;
        if (c0702mH0 != null) {
            c2409kl.d.forEach(new Consumer() { // from class: com.android.tools.r8.ir.optimize.F$$ExternalSyntheticLambda15
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    F.a(c0702mH0, setC, (C3332x4) obj);
                }
            });
        }
        boolean z = false;
        while (b4S.hasNext()) {
            C3332x4 next = b4S.next();
            if (next.v() == 0 || !next.y().isEmpty()) {
                if (!setC2.contains(next)) {
                    InterfaceC2641no interfaceC2641noA = next.a(c2409kl);
                    boolean z2 = z;
                    while (interfaceC2641noA.hasNext()) {
                        AbstractC2412ko next2 = interfaceC2641noA.next();
                        if (next2.I2() && next2.V0().b(this.a)) {
                            if (next2 instanceof N3) {
                                if (next.D() && next.c(next2)) {
                                    Iterator it = next.n().iterator();
                                    while (it.hasNext()) {
                                        ((C3332x4) ((C2969s6) it.next()).b).c0();
                                    }
                                    z2 = true;
                                }
                            } else if (next2.P1()) {
                                if (((AbstractC1182Mh) next2.S()).b(this.a, o5VarH, C2332jo.e)) {
                                    interfaceC2641noA.next();
                                }
                            } else if (next2.Z1()) {
                                if (next2.c0().a(this.a, o5VarH, C2332jo.e.d())) {
                                    interfaceC2641noA.next();
                                }
                            }
                            interfaceC2641noA.a(this.a, c2409kl, b4S, setC2, setC);
                        } else if (next2.Y1() && (abstractC0748t0H = (abstractC1305QsB0 = next2.b0()).h(this.a, c2409kl.h())) != null) {
                            com.android.tools.r8.ir.optimize.info.h hVarI1 = abstractC0748t0H.e().i1();
                            if (hVarI1.q() != null) {
                                BitSet bitSetQ = hVarI1.q();
                                int i = 0;
                                while (true) {
                                    if (i < ((ArrayList) abstractC1305QsB0.J2()).size()) {
                                        if (!((S10) ((ArrayList) abstractC1305QsB0.J2()).get(i)).b(this.a) || !bitSetQ.get(i)) {
                                            i++;
                                        }
                                    } else if (hVarI1.B()) {
                                    }
                                    interfaceC2641noA.a(abstractC1305QsB0.Y0());
                                    interfaceC2641noA.next();
                                    interfaceC2641noA.a(this.a, c2409kl, b4S, setC2, setC);
                                    interfaceC2641noA.r();
                                }
                            } else if (hVarI1.B()) {
                                interfaceC2641noA.a(abstractC1305QsB0.Y0());
                                interfaceC2641noA.next();
                                interfaceC2641noA.a(this.a, c2409kl, b4S, setC2, setC);
                                interfaceC2641noA.r();
                            }
                        }
                    }
                    z = z2;
                }
            }
        }
        c2409kl.b((Collection<C3332x4>) setC2);
        if (z) {
            setC.addAll(c2409kl.z());
        }
        boolean z3 = d;
        if (!z3 && !c2409kl.p().isEmpty()) {
            throw new AssertionError();
        }
        if (!setC.isEmpty()) {
            new C3030t00(this.a, false).a((Iterable) setC);
        }
        if (!z3 && !c2409kl.b(this.a)) {
            throw new AssertionError();
        }
    }

    public final void g(final C2409kl c2409kl) {
        C3332x4 c3332x4K2;
        C3332x4 c3332x4J2;
        boolean zA;
        if ((!this.a.Y().m() || this.a.Y().n1.O) && c2409kl.t().g()) {
            P1 p1 = new P1(new Supplier() { // from class: com.android.tools.r8.ir.optimize.F$$ExternalSyntheticLambda17
                @Override // java.util.function.Supplier
                public final Object get() {
                    return F.b(c2409kl);
                }
            });
            P1 p2 = new P1(new Supplier() { // from class: com.android.tools.r8.ir.optimize.F$$ExternalSyntheticLambda1
                @Override // java.util.function.Supplier
                public final Object get() {
                    return F.c(c2409kl);
                }
            });
            Iterator<C3332x4> it = c2409kl.d.iterator();
            int i = 0;
            boolean zA2 = false;
            while (it.hasNext()) {
                AbstractC2412ko last = it.next().p().getLast();
                if (last.N1()) {
                    C1015Gl c1015GlQ = last.Q();
                    EnumC1076Il enumC1076IlL2 = c1015GlQ.L2();
                    S10 s10 = (S10) ((ArrayList) c1015GlQ.h1()).get(i);
                    S10 s11 = !c1015GlQ.N2() ? (S10) ((ArrayList) c1015GlQ.h1()).get(1) : null;
                    if (c1015GlQ.N2() || s10.J() || s11.J()) {
                        EnumC1076Il enumC1076Il = EnumC1076Il.EQ;
                        if (enumC1076IlL2 == enumC1076Il || enumC1076IlL2 == EnumC1076Il.NE) {
                            if (enumC1076IlL2 == enumC1076Il) {
                                c3332x4J2 = c1015GlQ.K2();
                                c3332x4K2 = c1015GlQ.J2();
                            } else {
                                c3332x4K2 = c1015GlQ.K2();
                                c3332x4J2 = c1015GlQ.J2();
                            }
                            if (c1015GlQ.N2()) {
                                zA2 |= a(0L, s10, c3332x4J2, p1, c2409kl, p2);
                                if (s10.b((Set) null)) {
                                    zA = a(1L, s10, c3332x4K2, p1, c2409kl, p2);
                                    zA2 = zA | zA2;
                                }
                            } else {
                                boolean z = d;
                                if (!z && s11 == null) {
                                    throw new AssertionError();
                                }
                                if (s10.J()) {
                                    C0978Fc c0978FcE = s10.n().E();
                                    S10 s12 = s11;
                                    zA2 |= a(c0978FcE.P2(), s11, c3332x4J2, p1, c2409kl, p2);
                                    if (s10.b((Set) null) && s12.b((Set) null)) {
                                        if (!z && !c0978FcE.b().b((Set) null)) {
                                            throw new AssertionError();
                                        }
                                        zA2 |= a(c0978FcE.P2() == 0 ? 1 : 0, s12, c3332x4K2, p1, c2409kl, p2);
                                    }
                                } else {
                                    S10 s13 = s11;
                                    if (!z && !s13.J()) {
                                        throw new AssertionError();
                                    }
                                    C0978Fc c0978FcE2 = s13.n().E();
                                    zA2 |= a(c0978FcE2.P2(), s10, c3332x4J2, p1, c2409kl, p2);
                                    if (s10.b((Set) null) && s13.b((Set) null)) {
                                        if (!z && !c0978FcE2.b().b((Set) null)) {
                                            throw new AssertionError();
                                        }
                                        zA = a(c0978FcE2.P2() == 0 ? 1 : 0, s10, c3332x4K2, p1, c2409kl, p2);
                                        zA2 = zA | zA2;
                                    }
                                }
                            }
                            if (((QC) ((com.android.tools.r8.internal.Y) p1.d())).isEmpty()) {
                                break;
                            } else {
                                i = 0;
                            }
                        }
                    }
                }
            }
            if (zA2) {
                c2409kl.y();
            }
            if (!d && !c2409kl.b(this.a)) {
                throw new AssertionError();
            }
        }
    }

    public final void i(C2409kl c2409kl) {
        C2561ml c2561ml = new C2561ml(c2409kl);
        while (c2561ml.hasNext()) {
            AbstractC2412ko abstractC2412ko = (AbstractC2412ko) c2561ml.next();
            abstractC2412ko.getClass();
            if (abstractC2412ko instanceof Z2) {
                Z2 z2U = abstractC2412ko.u();
                if (!z2U.d1() || !z2U.b().A()) {
                    S10 s10M = z2U.J2().m();
                    if (!s10M.i() && z2U.J2().P() && !s10M.A()) {
                        B1 b1A = s10M.a(this.a, c2409kl.h());
                        if (b1A.u() || s10M.P()) {
                            AbstractC2412ko abstractC2412koQ = s10M.q();
                            if (!d && abstractC2412koQ == null) {
                                throw new AssertionError();
                            }
                            Set setG0 = z2U.b().g0();
                            abstractC2412koQ.getClass();
                            if (abstractC2412koQ instanceof AG) {
                                z2U.b().g(abstractC2412koQ.r0().J2());
                                c2561ml.s();
                            } else if (abstractC2412koQ.o2()) {
                                long j = abstractC2412koQ.s0().i;
                                if (j <= 2147483647L) {
                                    c2561ml.a(c2409kl, (int) j);
                                }
                            } else if (b1A.u()) {
                                c2561ml.a(c2409kl, b1A.s());
                            }
                            setG0.forEach(new uX$$ExternalSyntheticLambda0());
                        }
                    }
                }
            }
        }
        if (!d && !c2409kl.b(this.a)) {
            throw new AssertionError();
        }
    }

    public final boolean j(C2409kl c2409kl) {
        AbstractC0748t0 abstractC0748t0H;
        int iR;
        C0767v2 c0767v2;
        if ((this.c.f instanceof ClassFileConsumer) || !c2409kl.t().t()) {
            return false;
        }
        C3579q c3579q = new C3579q(this.a, c2409kl, OU.c());
        Set setC = OU.c();
        B4 b4S = c2409kl.s();
        boolean z = false;
        boolean z2 = false;
        while (b4S.hasNext()) {
            C3332x4 next = b4S.next();
            if (!setC.contains(next)) {
                InterfaceC2641no interfaceC2641noA = next.a(c2409kl);
                while (interfaceC2641noA.hasNext()) {
                    AbstractC1305Qs abstractC1305QsB0 = interfaceC2641noA.next().b0();
                    if (abstractC1305QsB0 != null && abstractC1305QsB0.d1() && !abstractC1305QsB0.b().A() && (abstractC0748t0H = abstractC1305QsB0.h(this.a, c2409kl.h())) != null) {
                        com.android.tools.r8.ir.optimize.info.h hVarI1 = abstractC0748t0H.e().i1();
                        if (hVarI1.E() && (iR = hVarI1.r()) >= 0) {
                            AbstractC3472z00 abstractC3472z00A = AbstractC3472z00.a(abstractC1305QsB0.S2().h.e, WH.i(), (C0702m<?>) this.a);
                            if (abstractC1305QsB0 instanceof C1721bt) {
                                c0767v2 = abstractC1305QsB0.S2().h.f.a[iR];
                            } else {
                                c0767v2 = iR == 0 ? abstractC1305QsB0.S2().e : abstractC1305QsB0.S2().h.f.a[iR - 1];
                            }
                            AbstractC3472z00 abstractC3472z00A2 = AbstractC3472z00.a(c0767v2, WH.i(), (C0702m<?>) this.a);
                            if (this.a.q() ? abstractC3472z00A2.a(abstractC3472z00A, this.a) : abstractC3472z00A2.equals(abstractC3472z00A)) {
                                S10 s10 = (S10) ((ArrayList) abstractC1305QsB0.J2()).get(iR);
                                S10 s10B = abstractC1305QsB0.b();
                                if (!d) {
                                    s10B.a(s10.d0());
                                }
                                if (s10.w().a(s10B.w(), this.a)) {
                                    Set setEmptySet = s10.w().equals(s10B.w()) ? Collections.emptySet() : s10B.a();
                                    c3579q.a(s10B);
                                    z |= s10B.a0() > 0;
                                    s10B.g(s10);
                                    abstractC1305QsB0.f(null);
                                    if (!setEmptySet.isEmpty()) {
                                        new C3030t00(this.a, false).a((Iterable) setEmptySet);
                                    }
                                    z2 = true;
                                }
                            }
                        }
                    }
                }
            }
        }
        c3579q.a(setC).a();
        Set setC2 = OU.c();
        if (!setC.isEmpty()) {
            c2409kl.b((Collection<C3332x4>) setC);
            c2409kl.a((C2102gl) null, setC2);
            if (!d && !c2409kl.p().isEmpty()) {
                throw new AssertionError();
            }
        } else if (z || c3579q.c()) {
            c2409kl.a((C2102gl) null, setC2);
        }
        if (!setC2.isEmpty()) {
            new C3030t00(this.a, false).a((Iterable) setC2);
        }
        if (d || c2409kl.b(this.a)) {
            return z2;
        }
        throw new AssertionError();
    }

    public final boolean k(C2409kl c2409kl) {
        A1 a1 = this.c;
        if (!a1.O || a1.W0 || !c2409kl.t().K()) {
            return false;
        }
        B4 b4S = c2409kl.s();
        boolean z = false;
        while (b4S.hasNext()) {
            C3332x4 next = b4S.next();
            InterfaceC2641no interfaceC2641noA = next.a(c2409kl);
            while (interfaceC2641noA.hasNext()) {
                AbstractC2412ko next2 = interfaceC2641noA.next();
                if (next2.A2()) {
                    IY iyK0 = next2.K0();
                    if (this.c.n1.I) {
                        C3332x4 c3332x4J2 = iyK0.J2();
                        Q0 q0 = new Q0(iyK0, interfaceC2641noA);
                        C3409y4 c3409y4 = new C3409y4(this.a, c2409kl);
                        B1 b1A = iyK0.value().a(this.a, c2409kl.h());
                        boolean z2 = false;
                        for (int i = 0; i < iyK0.L2(); i++) {
                            C3332x4 c3332x4E = iyK0.e(i);
                            if (JY.a(iyK0, i)) {
                                q0.b(i);
                                break;
                            }
                            if (JY.a(iyK0, b1A, i)) {
                                q0.c(i);
                            } else if (c3409y4.a(iyK0.value(), c3332x4E, c3332x4J2)) {
                                q0.c(i);
                                z2 = true;
                            }
                        }
                        if (q0.c() && !z2 && JY.a(iyK0, b1A)) {
                            q0.d();
                        }
                        q0.f();
                        if (q0.e()) {
                            z = true;
                        }
                        interfaceC2641noA.previous();
                        AbstractC2412ko next3 = interfaceC2641noA.next();
                        if (next3.M1()) {
                            continue;
                        } else {
                            if (!d && !next3.A2()) {
                                throw new AssertionError();
                            }
                            iyK0 = next3.K0();
                        }
                    }
                    boolean z3 = z;
                    if (iyK0.T1()) {
                        a(c2409kl, b4S, next, interfaceC2641noA, iyK0.W());
                    }
                    z = z3;
                }
            }
        }
        c2409kl.B();
        AbstractC1048Hm abstractC1048HmZ = z ? c2409kl.z() : AbstractC1048Hm.k();
        if (!abstractC1048HmZ.isEmpty()) {
            new C3030t00(this.a, false).a((Iterable) abstractC1048HmZ);
        }
        if (d || c2409kl.b(this.a)) {
            return !abstractC1048HmZ.isEmpty();
        }
        throw new AssertionError();
    }

    /* JADX WARN: Code duplicated, block: B:156:0x028d A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:179:0x028d A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:180:0x013e A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:61:0x011a  */
    /* JADX WARN: Code duplicated, block: B:64:0x0128  */
    /* JADX WARN: Code duplicated, block: B:67:0x013c A[LOOP:2: B:62:0x0122->B:67:0x013c, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:91:0x01af  */
    public final void l(C2409kl c2409kl) {
        C1166Ls c1166LsA;
        Iterator it;
        boolean z;
        int i = 0;
        boolean z2 = false;
        for (C3332x4 c3332x4 : c2409kl.d) {
            InterfaceC2641no interfaceC2641noA = c3332x4.a(c2409kl);
            while (interfaceC2641noA.hasNext()) {
                AbstractC2412ko next = interfaceC2641noA.next();
                if (next.N1()) {
                    S1 s1 = this.a.a().f4;
                    C0684j2 reference = c2409kl.u().getReference();
                    if (reference != s1.e && reference != s1.f && reference != s1.g && reference != s1.h && reference != s1.i) {
                        C1015Gl c1015GlQ = next.Q();
                        if (c1015GlQ.N2()) {
                            S10 s10O2 = c1015GlQ.O2();
                            if (s10O2.w().I()) {
                                C3332x4 c3332x4C = c1015GlQ.c(i);
                                if (c3332x4C.y().size() == 1 && c3332x4C.m().B2()) {
                                    C1621aZ c1621aZL0 = c3332x4C.m().L0();
                                    S10 s10M = c1621aZL0.K2().m();
                                    if (!(s10M.J() && s10M.b.E().T2()) && s10M.b(new IW$$ExternalSyntheticLambda0())) {
                                        DG dgT0 = s10M.b.t0();
                                        if (dgT0.h == this.b.s3 && dgT0.b().Z() == 2 && (c1166LsA = AbstractC2871ql.a(this.b, dgT0.b())) != null && c1166LsA.S2() == this.b.v4.a) {
                                            it = c3332x4C.b(c1621aZL0).iterator();
                                            while (true) {
                                                if (it.hasNext()) {
                                                    C3332x4 c3332x4C2 = c1015GlQ.c(1);
                                                    AbstractC1915eM abstractC1915eMY0 = c1621aZL0.Y0();
                                                    c3332x4C.e0();
                                                    z = d;
                                                    if (z && c1015GlQ != c3332x4.m()) {
                                                        throw new AssertionError();
                                                    }
                                                    interfaceC2641noA.previous();
                                                    C2115gt c2115gt = new C2115gt(this.a.a().g4.e, null, AbstractC1639am.c(c1015GlQ.O2()));
                                                    c2115gt.b(abstractC1915eMY0);
                                                    interfaceC2641noA.add(c2115gt);
                                                    interfaceC2641noA.next();
                                                    interfaceC2641noA.d(new C1296Qj());
                                                    if (z && !c3332x4.m().M1()) {
                                                        throw new AssertionError();
                                                    }
                                                    if (z && AbstractC2967s4.a(c3332x4) != c3332x4C2) {
                                                        throw new AssertionError();
                                                    }
                                                    z2 = true;
                                                    if (!next.p2() && next.t0().h == this.b.s3 && next.b().Z() == 2 && !next.b().A() && next.S0().isEmpty() && interfaceC2641noA.hasNext()) {
                                                        AbstractC2412ko next2 = interfaceC2641noA.next();
                                                        if (next2.W1() && next2.S0().isEmpty()) {
                                                            C1166Ls c1166LsZ = next2.Z();
                                                            if (c1166LsZ.S2() == this.b.v4.a && c1166LsZ.U2() == next.b() && ((ArrayList) c1166LsZ.J2()).size() == 1 && c1166LsZ.Y0() == next.Y0() && interfaceC2641noA.hasNext()) {
                                                                AbstractC2412ko next3 = interfaceC2641noA.next();
                                                                if (next3.B2() && next3.L0().K2() == next.b()) {
                                                                    C0978Fc c0978FcI = c2409kl.i();
                                                                    C1621aZ c1621aZ = new C1621aZ(c0978FcI.b());
                                                                    if (!d && next.Y0() != next2.Y0()) {
                                                                        throw new AssertionError();
                                                                    }
                                                                    c0978FcI.b(next.Y0());
                                                                    c1621aZ.b(next3.Y0());
                                                                    next3.c(c1621aZ);
                                                                    interfaceC2641noA.remove();
                                                                    interfaceC2641noA.previous();
                                                                    interfaceC2641noA.remove();
                                                                    interfaceC2641noA.previous();
                                                                    interfaceC2641noA.remove();
                                                                    interfaceC2641noA.add(c0978FcI);
                                                                    interfaceC2641noA.add(c1621aZ);
                                                                }
                                                            }
                                                        }
                                                    }
                                                } else if (!((AbstractC2412ko) it.next()).e(this.a, c2409kl.h())) {
                                                }
                                            }
                                        }
                                    } else if (s10M.J() && s10M.b.E().T2()) {
                                        it = c3332x4C.b(c1621aZL0).iterator();
                                        while (true) {
                                            if (it.hasNext()) {
                                                C3332x4 c3332x4C3 = c1015GlQ.c(1);
                                                AbstractC1915eM abstractC1915eMY1 = c1621aZL0.Y0();
                                                c3332x4C.e0();
                                                z = d;
                                                if (z) {
                                                }
                                                interfaceC2641noA.previous();
                                                C2115gt c2115gt2 = new C2115gt(this.a.a().g4.e, null, AbstractC1639am.c(c1015GlQ.O2()));
                                                c2115gt2.b(abstractC1915eMY1);
                                                interfaceC2641noA.add(c2115gt2);
                                                interfaceC2641noA.next();
                                                interfaceC2641noA.d(new C1296Qj());
                                                if (z) {
                                                }
                                                if (z) {
                                                }
                                                z2 = true;
                                                if (!next.p2()) {
                                                    continue;
                                                }
                                            } else if (!((AbstractC2412ko) it.next()).e(this.a, c2409kl.h())) {
                                            }
                                        }
                                    }
                                }
                            } else if (!d && !s10O2.w().H()) {
                                throw new AssertionError();
                            }
                        } else {
                            continue;
                        }
                    }
                } else if (!next.p2()) {
                    continue;
                }
                i = 0;
            }
        }
        if (z2) {
            AbstractC1048Hm abstractC1048HmZ = c2409kl.z();
            if (!abstractC1048HmZ.isEmpty()) {
                new C3030t00(this.a, false).a((Iterable) abstractC1048HmZ);
            }
        }
        if (!d && !c2409kl.b(this.a)) {
            throw new AssertionError();
        }
    }

    /* JADX WARN: Code duplicated, block: B:100:0x0160  */
    /* JADX WARN: Code duplicated, block: B:107:0x0173  */
    /* JADX WARN: Code duplicated, block: B:110:0x017a  */
    /* JADX WARN: Code duplicated, block: B:112:0x0180  */
    /* JADX WARN: Code duplicated, block: B:113:0x0182  */
    /* JADX WARN: Code duplicated, block: B:115:0x018a  */
    /* JADX WARN: Code duplicated, block: B:116:0x018c  */
    /* JADX WARN: Code duplicated, block: B:121:0x019c  */
    /* JADX WARN: Code duplicated, block: B:208:0x02ed  */
    /* JADX WARN: Code duplicated, block: B:220:0x0343 A[LOOP:10: B:218:0x033d->B:220:0x0343, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:226:0x0370  */
    /* JADX WARN: Code duplicated, block: B:228:0x0378  */
    /* JADX WARN: Code duplicated, block: B:230:0x037e  */
    /* JADX WARN: Code duplicated, block: B:232:0x038a  */
    /* JADX WARN: Code duplicated, block: B:239:0x039e  */
    /* JADX WARN: Code duplicated, block: B:241:0x03a9  */
    /* JADX WARN: Code duplicated, block: B:249:0x03c0  */
    /* JADX WARN: Code duplicated, block: B:252:0x03cc A[LOOP:4: B:248:0x03be->B:252:0x03cc, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:255:0x03d2  */
    /* JADX WARN: Code duplicated, block: B:257:0x03d9  */
    /* JADX WARN: Code duplicated, block: B:259:0x03ee  */
    /* JADX WARN: Code duplicated, block: B:262:0x040f  */
    /* JADX WARN: Code duplicated, block: B:275:0x042d  */
    /* JADX WARN: Code duplicated, block: B:277:0x043e A[LOOP:9: B:276:0x043c->B:277:0x043e, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:284:0x048b  */
    /* JADX WARN: Code duplicated, block: B:288:0x04a1  */
    /* JADX WARN: Code duplicated, block: B:310:0x0301 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:311:0x046b A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:313:0x0465 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:314:0x0465 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:318:0x0196 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:319:0x00ee A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:320:0x019c A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:322:0x00dc A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:343:0x03cf A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:344:0x03c8 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:358:0x0405 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:47:0x00a4  */
    /* JADX WARN: Code duplicated, block: B:48:0x00a6  */
    /* JADX WARN: Code duplicated, block: B:57:0x00b8  */
    /* JADX WARN: Code duplicated, block: B:66:0x00e2  */
    /* JADX WARN: Code duplicated, block: B:70:0x00fc  */
    /* JADX WARN: Code duplicated, block: B:82:0x0129  */
    /* JADX WARN: Code duplicated, block: B:85:0x0130  */
    /* JADX WARN: Code duplicated, block: B:87:0x0136  */
    /* JADX WARN: Code duplicated, block: B:91:0x014c  */
    /* JADX WARN: Code duplicated, block: B:94:0x0151  */
    public void m(C2409kl c2409kl) {
        C3595z c3595z;
        A1 a1;
        AG ag;
        C0767v2 c0767v2;
        int i;
        int i2;
        Set setB;
        boolean z;
        AbstractC2412ko abstractC2412koJ;
        C1385Ts c1385Ts;
        Iterator it;
        boolean zD;
        C3332x4 c3332x4A;
        A1 a2;
        Iterator it2;
        C3332x4 c3332x4H;
        int iC0;
        S10[] s10Arr;
        int length;
        int i3;
        int i4;
        short[] sArr;
        int i5;
        short[] sArr2;
        long jP2;
        int i6;
        short[] sArr3;
        int i7;
        short sN2;
        int i8;
        C3332x4 c3332x4A2;
        boolean z2;
        boolean zC;
        C0767v2 c0767v2A;
        Iterator<AbstractC2412ko> it3;
        AbstractC2412ko next;
        AbstractC3472z00 abstractC3472z00W;
        C0767v2 c0767v2B;
        boolean zA;
        boolean z3;
        C1969f3 c1969f3A;
        if (this.c.f instanceof ClassFileConsumer) {
            return;
        }
        O3 o3D = O3.d((Iterable) c2409kl.d);
        while (o3D.c()) {
            C3332x4 c3332x4 = (C3332x4) o3D.f();
            A1 a3 = this.c;
            F1 f1A1 = a3.A1();
            InterfaceC2641no interfaceC2641noA = c3332x4.a(c2409kl);
            while (interfaceC2641noA.hasNext()) {
                AbstractC2412ko next2 = interfaceC2641noA.next();
                AG agR0 = next2.r0();
                if (agR0 != null && next2.j() == null && agR0.J2().L()) {
                    int iN2 = agR0.J2().n().E().N2();
                    f1A1.getClass();
                    if (1 > iN2 || iN2 > 8192) {
                        c3595z = null;
                    } else {
                        C0767v2 c0767v3 = agR0.h;
                        if (iN2 < 1) {
                            zC = false;
                        } else if (c0767v3.V0()) {
                            if (c0767v3 != this.b.J1 || iN2 > 5) {
                                zC = false;
                            } else {
                                zC = true;
                            }
                        } else if (iN2 > 200) {
                            zC = false;
                        } else if (c0767v3 == this.b.Y1) {
                            zC = f1A1.c();
                        } else if (f1A1.b() && (f1A1.a() || c0767v3.G0() <= 1)) {
                            zC = true;
                        } else {
                            zC = false;
                        }
                        if (zC) {
                            if (!f1A1.d() && c0767v3 != this.b.c2 && !c0767v3.V0()) {
                                c0767v2A = c0767v3.a(this.b);
                                it3 = agR0.b().h0().iterator();
                                while (true) {
                                    if (it3.hasNext()) {
                                        next = it3.next();
                                        if (!next.r1()) {
                                            if (next.v().J2() == agR0.b()) {
                                                abstractC3472z00W = next.v().value().w();
                                                if (!(abstractC3472z00W.H() && c0767v2A == this.b.Z1) && (!abstractC3472z00W.E() || c0767v2A.W0())) {
                                                    if (!c0767v2A.M0()) {
                                                        c0767v2B = c0767v2A;
                                                    } else if (abstractC3472z00W.E()) {
                                                        zA = true;
                                                    } else {
                                                        c1969f3A = abstractC3472z00W.a();
                                                        if (c1969f3A == null && c1969f3A.U() == c0767v2A.G0()) {
                                                            abstractC3472z00W = c1969f3A.R();
                                                            c0767v2B = c0767v2A.b(this.b);
                                                        } else {
                                                            zA = false;
                                                        }
                                                    }
                                                    z3 = d;
                                                    if (z3 && abstractC3472z00W.r()) {
                                                        throw new AssertionError();
                                                    }
                                                    if (z3 && c0767v2B.M0()) {
                                                        throw new AssertionError();
                                                    }
                                                    if (!abstractC3472z00W.H() && !c0767v2B.W0()) {
                                                        zA = false;
                                                    } else if (abstractC3472z00W.H()) {
                                                        zA = true;
                                                    } else if (this.a.c(c0767v2B) == null) {
                                                        zA = false;
                                                    } else {
                                                        zA = abstractC3472z00W.a(c0767v2B);
                                                    }
                                                } else {
                                                    zA = true;
                                                }
                                                if (!zA) {
                                                    c3595z = null;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            c3595z = new C3595z(agR0, iN2, zC);
                        } else if ((iN2 < 2 || iN2 > 8192) ? false : c0767v3.V0()) {
                            if (!f1A1.d()) {
                                c0767v2A = c0767v3.a(this.b);
                                it3 = agR0.b().h0().iterator();
                                while (true) {
                                    if (it3.hasNext()) {
                                        next = it3.next();
                                        if (!next.r1()) {
                                            if (next.v().J2() == agR0.b()) {
                                                abstractC3472z00W = next.v().value().w();
                                                if (abstractC3472z00W.H()) {
                                                    if (!c0767v2A.M0()) {
                                                        c0767v2B = c0767v2A;
                                                    } else if (abstractC3472z00W.E()) {
                                                        c1969f3A = abstractC3472z00W.a();
                                                        if (c1969f3A == null) {
                                                        }
                                                        zA = false;
                                                    } else {
                                                        zA = true;
                                                    }
                                                    z3 = d;
                                                    if (z3) {
                                                    }
                                                    if (z3) {
                                                    }
                                                    if (!abstractC3472z00W.H()) {
                                                        if (abstractC3472z00W.H()) {
                                                            zA = true;
                                                        } else if (this.a.c(c0767v2B) == null) {
                                                            zA = false;
                                                        } else {
                                                            zA = abstractC3472z00W.a(c0767v2B);
                                                        }
                                                    } else if (abstractC3472z00W.H()) {
                                                        zA = true;
                                                    } else if (this.a.c(c0767v2B) == null) {
                                                        zA = false;
                                                    } else {
                                                        zA = abstractC3472z00W.a(c0767v2B);
                                                    }
                                                } else {
                                                    if (!c0767v2A.M0()) {
                                                        c0767v2B = c0767v2A;
                                                    } else if (abstractC3472z00W.E()) {
                                                        c1969f3A = abstractC3472z00W.a();
                                                        if (c1969f3A == null) {
                                                        }
                                                        zA = false;
                                                    } else {
                                                        zA = true;
                                                    }
                                                    z3 = d;
                                                    if (z3) {
                                                    }
                                                    if (z3) {
                                                    }
                                                    if (!abstractC3472z00W.H()) {
                                                        if (abstractC3472z00W.H()) {
                                                            zA = true;
                                                        } else if (this.a.c(c0767v2B) == null) {
                                                            zA = false;
                                                        } else {
                                                            zA = abstractC3472z00W.a(c0767v2B);
                                                        }
                                                    } else if (abstractC3472z00W.H()) {
                                                        zA = true;
                                                    } else if (this.a.c(c0767v2B) == null) {
                                                        zA = false;
                                                    } else {
                                                        zA = abstractC3472z00W.a(c0767v2B);
                                                    }
                                                }
                                                if (!zA) {
                                                    c3595z = null;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            c3595z = new C3595z(agR0, iN2, zC);
                        } else {
                            c3595z = null;
                        }
                    }
                } else {
                    c3595z = null;
                }
                if (c3595z == null) {
                    a1 = a3;
                    f1A1 = f1A1;
                } else {
                    C3486zB c3486zB = new C3486zB(c2409kl, c3332x4, interfaceC2641noA.nextIndex());
                    AG ag2 = c3595z.a;
                    if (!d && c3486zB.j() != ag2) {
                        throw new AssertionError();
                    }
                    S10 s10B = ag2.b();
                    int i9 = c3595z.b;
                    C0767v2 c0767v4 = ag2.h;
                    C0722p1 c0722p1 = this.b;
                    C0767v2 c0767v2B2 = c0767v4.b(c0722p1);
                    int iG0 = c0767v4.G0();
                    if (iG0 > 1) {
                        c0767v2B2 = c0722p1.a(iG0 - 1, c0767v2B2);
                    }
                    boolean z4 = (c0767v2B2.W0() || c0767v2B2 == this.b.Z1) ? false : true;
                    A a = new A(i9);
                    S10[] s10Arr2 = a.a;
                    Set<AbstractC2412ko> setH0 = ag2.b().h0();
                    while (true) {
                        if (c3486zB.hasNext()) {
                            f1A1 = f1A1;
                            AbstractC2412ko next3 = c3486zB.next();
                            C3332x4 c3332x4H2 = next3.h();
                            if (!c3332x4H2.D() || !next3.i1()) {
                                if (setH0.contains(next3)) {
                                    C1736c3 c1736c3V = next3.v();
                                    if (c1736c3V != null) {
                                        Set<AbstractC2412ko> set = setH0;
                                        if (c1736c3V.J2() == s10B && c1736c3V.K2().J()) {
                                            if (!c1736c3V.i1()) {
                                                int iN3 = c1736c3V.K2().n().E().N2();
                                                if (iN3 >= 0) {
                                                    S10 s10 = s10B;
                                                    if (iN3 < s10Arr2.length && s10Arr2[iN3] == null) {
                                                        S10 s10Value = c1736c3V.value();
                                                        if (z4) {
                                                            z2 = z4;
                                                            if (s10Value.b(this.a)) {
                                                                a1 = a3;
                                                            } else {
                                                                a1 = a3;
                                                                C0767v2 c0767v2A2 = s10Value.w().d().a(this.b);
                                                                if (c0767v2B2.M0()) {
                                                                    if (c0767v2B2 != c0767v2A2) {
                                                                    }
                                                                } else if (!c0767v2A2.M0() && (c0767v2A2.U0() || !this.a.a(c0767v2A2, c0767v2B2).b())) {
                                                                }
                                                            }
                                                        } else {
                                                            a1 = a3;
                                                            z2 = z4;
                                                        }
                                                        a.b.add(c1736c3V);
                                                        s10Arr2[iN3] = s10Value;
                                                        i9--;
                                                        if (i9 == 0) {
                                                            a.c = c3486zB;
                                                        } else {
                                                            setH0 = set;
                                                            s10B = s10;
                                                            z4 = z2;
                                                            a3 = a1;
                                                        }
                                                    }
                                                    if (a == null) {
                                                        AbstractC2412ko abstractC2412koO = interfaceC2641noA.o();
                                                        ag = c3595z.a;
                                                        c0767v2 = ag.h;
                                                        i = c3595z.b;
                                                        i2 = i + 1;
                                                        setB = T2.b(i2);
                                                        z = c3595z.c;
                                                        if (z) {
                                                            if (d && ag.j() != null) {
                                                                throw new AssertionError();
                                                            }
                                                            abstractC2412koJ = a.c.j();
                                                            S10 s10A = c2409kl.a((com.android.tools.r8.graph.X) null, ag.a());
                                                            c1385Ts = new C1385Ts(c0767v2, s10A, Arrays.asList(a.a));
                                                            c1385Ts.b(abstractC2412koJ.Y0());
                                                            it = ((ArrayList) ag.h1()).iterator();
                                                            while (it.hasNext()) {
                                                                ((S10) it.next()).e(ag);
                                                            }
                                                            ag.b().g(s10A);
                                                            setB.add(ag);
                                                            zD = c3332x4.D();
                                                            if (!abstractC2412koJ.h().D() || zD) {
                                                                c3332x4A = a.c.a(c2409kl);
                                                                if (zD) {
                                                                    if (!c3332x4A.Q()) {
                                                                        C3332x4 c3332x4A3 = c3332x4A.a(c2409kl).a(c2409kl);
                                                                        if (d && !c3332x4A.Q()) {
                                                                            throw new AssertionError();
                                                                        }
                                                                        o3D.c(c3332x4A3);
                                                                    }
                                                                    c3332x4A.i(c3332x4);
                                                                } else {
                                                                    o3D.c(c3332x4A);
                                                                }
                                                                c3332x4A.a(c2409kl).add(c1385Ts);
                                                            } else {
                                                                a.c.e(c1385Ts);
                                                            }
                                                            a2 = a1;
                                                        } else {
                                                            if (d && z) {
                                                                throw new AssertionError();
                                                            }
                                                            iC0 = c0767v2.C0();
                                                            s10Arr = a.a;
                                                            length = s10Arr.length;
                                                            i3 = 0;
                                                            while (true) {
                                                                if (i3 < length) {
                                                                    if (iC0 != 1) {
                                                                        sArr3 = new short[i2 / 2];
                                                                        for (i7 = 0; i7 < i; i7 += 2) {
                                                                            sN2 = (short) (s10Arr[i7].n().E().N2() & 255);
                                                                            i8 = i7 + 1;
                                                                            if (i8 < i) {
                                                                                sN2 = (short) (((short) ((s10Arr[i8].n().E().N2() & 255) << 8)) | sN2);
                                                                            }
                                                                            sArr3[i7 / 2] = sN2;
                                                                        }
                                                                        sArr2 = sArr3;
                                                                        break;
                                                                    }
                                                                    if (d && iC0 != 2 && iC0 != 4 && iC0 != 8) {
                                                                        throw new AssertionError();
                                                                    }
                                                                    i4 = iC0 / 2;
                                                                    sArr = new short[i * i4];
                                                                    for (i5 = 0; i5 < i; i5++) {
                                                                        jP2 = s10Arr[i5].n().E().P2();
                                                                        i6 = 0;
                                                                        while (i6 < i4) {
                                                                            sArr[(i5 * i4) + i6] = (short) ((jP2 >> (i6 * 16)) & WebSocketProtocol.PAYLOAD_SHORT_MAX);
                                                                            i6++;
                                                                            s10Arr = s10Arr;
                                                                            i4 = i4;
                                                                        }
                                                                    }
                                                                    sArr2 = sArr;
                                                                    break;
                                                                }
                                                                if (!s10Arr[i3].L()) {
                                                                    sArr2 = null;
                                                                    break;
                                                                }
                                                                i3++;
                                                            }
                                                            if (sArr2 == null) {
                                                                BG bg = new BG(ag.b(), iC0, i, sArr2);
                                                                bg.b(ag.Y0());
                                                                a2 = a1;
                                                                c3332x4A2 = interfaceC2641noA.a(c2409kl, (ListIterator<C3332x4>) null, bg, a2);
                                                                if (c3332x4A2 != null) {
                                                                    o3D.c(c3332x4A2);
                                                                }
                                                            }
                                                        }
                                                        setB.addAll(a.b);
                                                        Set setC = OU.c();
                                                        it2 = setB.iterator();
                                                        while (it2.hasNext()) {
                                                            c3332x4H = ((AbstractC2412ko) it2.next()).h();
                                                            if (c3332x4H == null && setC.add(c3332x4H)) {
                                                                InterfaceC2641no interfaceC2641noA2 = c3332x4H.a(c2409kl);
                                                                while (interfaceC2641noA2.hasNext()) {
                                                                    if (setB.contains(interfaceC2641noA2.next())) {
                                                                        interfaceC2641noA2.s();
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        interfaceC2641noA = c3332x4.a(c2409kl, abstractC2412koO);
                                                        a3 = a2;
                                                        f1A1 = f1A1;
                                                    }
                                                }
                                            } else if (!d) {
                                                throw new AssertionError();
                                            }
                                        }
                                    }
                                } else if (!c3332x4H2.D() || !next3.g()) {
                                }
                            }
                            a1 = a3;
                        } else {
                            a1 = a3;
                            f1A1 = f1A1;
                        }
                        a = null;
                        if (a == null) {
                            AbstractC2412ko abstractC2412koO2 = interfaceC2641noA.o();
                            ag = c3595z.a;
                            c0767v2 = ag.h;
                            i = c3595z.b;
                            i2 = i + 1;
                            setB = T2.b(i2);
                            z = c3595z.c;
                            if (z) {
                                if (d) {
                                }
                                abstractC2412koJ = a.c.j();
                                S10 s10A2 = c2409kl.a((com.android.tools.r8.graph.X) null, ag.a());
                                c1385Ts = new C1385Ts(c0767v2, s10A2, Arrays.asList(a.a));
                                c1385Ts.b(abstractC2412koJ.Y0());
                                it = ((ArrayList) ag.h1()).iterator();
                                while (it.hasNext()) {
                                    ((S10) it.next()).e(ag);
                                }
                                ag.b().g(s10A2);
                                setB.add(ag);
                                zD = c3332x4.D();
                                if (abstractC2412koJ.h().D()) {
                                    c3332x4A = a.c.a(c2409kl);
                                    if (zD) {
                                        if (!c3332x4A.Q()) {
                                            C3332x4 c3332x4A4 = c3332x4A.a(c2409kl).a(c2409kl);
                                            if (d) {
                                            }
                                            o3D.c(c3332x4A4);
                                        }
                                        c3332x4A.i(c3332x4);
                                    } else {
                                        o3D.c(c3332x4A);
                                    }
                                    c3332x4A.a(c2409kl).add(c1385Ts);
                                } else {
                                    c3332x4A = a.c.a(c2409kl);
                                    if (zD) {
                                        if (!c3332x4A.Q()) {
                                            C3332x4 c3332x4A5 = c3332x4A.a(c2409kl).a(c2409kl);
                                            if (d) {
                                            }
                                            o3D.c(c3332x4A5);
                                        }
                                        c3332x4A.i(c3332x4);
                                    } else {
                                        o3D.c(c3332x4A);
                                    }
                                    c3332x4A.a(c2409kl).add(c1385Ts);
                                }
                                a2 = a1;
                            } else {
                                if (d) {
                                }
                                iC0 = c0767v2.C0();
                                s10Arr = a.a;
                                length = s10Arr.length;
                                i3 = 0;
                                while (true) {
                                    if (i3 < length) {
                                        if (iC0 != 1) {
                                            if (d) {
                                            }
                                            i4 = iC0 / 2;
                                            sArr = new short[i * i4];
                                            while (i5 < i) {
                                                jP2 = s10Arr[i5].n().E().P2();
                                                i6 = 0;
                                                while (i6 < i4) {
                                                    sArr[(i5 * i4) + i6] = (short) ((jP2 >> (i6 * 16)) & WebSocketProtocol.PAYLOAD_SHORT_MAX);
                                                    i6++;
                                                    s10Arr = s10Arr;
                                                    i4 = i4;
                                                }
                                            }
                                            sArr2 = sArr;
                                            break;
                                        }
                                        sArr3 = new short[i2 / 2];
                                        while (i7 < i) {
                                            sN2 = (short) (s10Arr[i7].n().E().N2() & 255);
                                            i8 = i7 + 1;
                                            if (i8 < i) {
                                                sN2 = (short) (((short) ((s10Arr[i8].n().E().N2() & 255) << 8)) | sN2);
                                            }
                                            sArr3[i7 / 2] = sN2;
                                        }
                                        sArr2 = sArr3;
                                        break;
                                    }
                                    if (!s10Arr[i3].L()) {
                                        sArr2 = null;
                                        break;
                                    }
                                    i3++;
                                }
                                if (sArr2 == null) {
                                    BG bg2 = new BG(ag.b(), iC0, i, sArr2);
                                    bg2.b(ag.Y0());
                                    a2 = a1;
                                    c3332x4A2 = interfaceC2641noA.a(c2409kl, (ListIterator<C3332x4>) null, bg2, a2);
                                    if (c3332x4A2 != null) {
                                        o3D.c(c3332x4A2);
                                    }
                                }
                            }
                            setB.addAll(a.b);
                            Set setC2 = OU.c();
                            it2 = setB.iterator();
                            while (it2.hasNext()) {
                                c3332x4H = ((AbstractC2412ko) it2.next()).h();
                                if (c3332x4H == null) {
                                }
                            }
                            interfaceC2641noA = c3332x4.a(c2409kl, abstractC2412koO2);
                            a3 = a2;
                            f1A1 = f1A1;
                        }
                    }
                }
                f1A1 = f1A1;
                a3 = a1;
            }
        }
    }

    public final boolean n(C2409kl c2409kl) {
        return p(c2409kl).a | k(c2409kl);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:141:0x028d  */
    /* JADX WARN: Code duplicated, block: B:150:0x02a9  */
    /* JADX WARN: Code duplicated, block: B:175:0x034b  */
    /* JADX WARN: Code duplicated, block: B:224:0x0416  */
    /* JADX WARN: Code duplicated, block: B:228:0x0426  */
    /* JADX WARN: Code duplicated, block: B:230:0x0436  */
    /* JADX WARN: Code duplicated, block: B:237:0x045a  */
    /* JADX WARN: Code duplicated, block: B:239:0x0460  */
    /* JADX WARN: Code duplicated, block: B:241:0x046c  */
    /* JADX WARN: Code duplicated, block: B:242:0x0479  */
    /* JADX WARN: Code duplicated, block: B:245:0x0489  */
    /* JADX WARN: Code duplicated, block: B:247:0x048d  */
    /* JADX WARN: Code duplicated, block: B:252:0x049a  */
    /* JADX WARN: Code duplicated, block: B:254:0x04a2  */
    /* JADX WARN: Code duplicated, block: B:255:0x04a8  */
    /* JADX WARN: Code duplicated, block: B:257:0x04b1  */
    /* JADX WARN: Code duplicated, block: B:264:0x04c3  */
    /* JADX WARN: Code duplicated, block: B:268:0x04ea  */
    /* JADX WARN: Code duplicated, block: B:269:0x04ef  */
    /* JADX WARN: Code duplicated, block: B:294:0x04c0 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:296:0x04e0 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:299:0x04ff A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:77:0x016b  */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r19v0 */
    /* JADX WARN: Type inference failed for: r19v1 */
    /* JADX WARN: Type inference failed for: r19v10 */
    /* JADX WARN: Type inference failed for: r19v11 */
    /* JADX WARN: Type inference failed for: r19v12 */
    /* JADX WARN: Type inference failed for: r19v13 */
    /* JADX WARN: Type inference failed for: r19v14 */
    /* JADX WARN: Type inference failed for: r19v2 */
    /* JADX WARN: Type inference failed for: r19v3 */
    /* JADX WARN: Type inference failed for: r19v4 */
    /* JADX WARN: Type inference failed for: r19v5 */
    /* JADX WARN: Type inference failed for: r19v6 */
    /* JADX WARN: Type inference failed for: r19v7 */
    /* JADX WARN: Type inference failed for: r19v8 */
    /* JADX WARN: Type inference failed for: r19v9 */
    /* JADX WARN: Type inference failed for: r5v0 */
    /* JADX WARN: Type inference failed for: r5v1, types: [boolean] */
    /* JADX WARN: Type inference failed for: r5v19 */
    /* JADX WARN: Type inference failed for: r5v2 */
    /* JADX WARN: Type inference failed for: r5v20 */
    /* JADX WARN: Type inference failed for: r5v21 */
    /* JADX WARN: Type inference failed for: r5v23 */
    /* JADX WARN: Type inference failed for: r5v3 */
    /* JADX WARN: Type inference failed for: r5v4 */
    /* JADX WARN: Type inference failed for: r5v5 */
    /* JADX WARN: Type inference failed for: r5v6 */
    /* JADX WARN: Type inference failed for: r5v7 */
    /* JADX WARN: Type inference failed for: r5v8 */
    public final C3594y p(C2409kl c2409kl) {
        boolean zB2;
        int i;
        C3409y4 c3409y4;
        Iterator<C3332x4> it;
        ?? r19;
        boolean z;
        ?? r110;
        C3409y4 c3409y5;
        C3332x4 c3332x4J2;
        C3332x4 c3332x4K2;
        B1 b1A;
        boolean z2;
        W1 w1X;
        Iterator<C3332x4> it2;
        ?? r111;
        int i2;
        int i3;
        ?? r112;
        boolean z3;
        int i4;
        boolean zB3;
        C3409y4 c3409y6 = new C3409y4(this.a, c2409kl);
        Iterator<C3332x4> it3 = c2409kl.d.iterator();
        ?? r5 = 0;
        while (true) {
            int i5 = 1;
            if (!it3.hasNext()) {
                AbstractC1048Hm abstractC1048HmZ = c2409kl.z();
                if (!abstractC1048HmZ.isEmpty()) {
                    new C3030t00(this.a, false).a((Iterable) abstractC1048HmZ);
                }
                if (d || c2409kl.b(this.a)) {
                    return new C3594y(!abstractC1048HmZ.isEmpty(), r5);
                }
                throw new AssertionError();
            }
            C3332x4 next = it3.next();
            if ((next.v() == 0 || !next.y().isEmpty()) && next.m().N1()) {
                C1015Gl c1015GlQ = next.m().Q();
                C3332x4 c3332x4K3 = c1015GlQ.K2();
                C3332x4 c3332x4J3 = c1015GlQ.J2();
                boolean z4 = d;
                if (!z4 && c3332x4K3 == c3332x4J3) {
                    throw new AssertionError();
                }
                c3332x4J3.getClass();
                C3332x4 c3332x4 = c3332x4J3;
                C3332x4 c3332x5 = c3332x4;
                boolean z5 = false;
                while (true) {
                    List listU = c3332x4.u();
                    if (listU.size() <= i5) {
                        if (listU.size() == 0) {
                            zB2 = c3332x4.m().B2();
                        } else {
                            c3332x4 = (C3332x4) listU.get(0);
                            if (z5) {
                                c3332x5 = (C3332x4) c3332x5.u().get(0);
                            }
                            z5 = !z5;
                            if (c3332x4 != c3332x5) {
                                i5 = 1;
                            }
                        }
                    }
                    zB2 = false;
                }
                if (zB2) {
                    c3332x4K3.getClass();
                    C3332x4 c3332x6 = c3332x4K3;
                    C3332x4 c3332x7 = c3332x6;
                    boolean z6 = false;
                    while (true) {
                        List listU2 = c3332x6.u();
                        if (listU2.size() <= i5) {
                            if (listU2.size() == 0) {
                                zB3 = c3332x6.m().B2();
                            } else {
                                C3332x4 c3332x8 = (C3332x4) listU2.get(0);
                                if (z6) {
                                    c3332x7 = (C3332x4) c3332x7.u().get(0);
                                }
                                z6 = !z6;
                                if (c3332x8 != c3332x7) {
                                    c3332x6 = c3332x8;
                                }
                            }
                        }
                        zB3 = false;
                    }
                    if (!zB3) {
                        next.a(c2409kl, (AbstractC1954eu) new C1015Gl(c1015GlQ.L2().b(), c1015GlQ.h1()));
                        next.c(c3332x4K3, c3332x4J3);
                    }
                }
                C1015Gl c1015GlQ2 = next.m().Q();
                if (c1015GlQ2.N2()) {
                    i = 0;
                } else {
                    S10 s10O2 = c1015GlQ2.O2();
                    S10 s10P2 = c1015GlQ2.P2();
                    if (!s10O2.J() && !s10P2.J()) {
                        i = 0;
                    } else if (s10O2.J()) {
                        if (s10O2.n().E().T2()) {
                            C1015Gl c1015Gl = new C1015Gl(c1015GlQ2.L2().a(), s10P2);
                            next.a(c2409kl, (AbstractC1954eu) c1015Gl);
                            if (!z4 && next.m() != c1015Gl) {
                                throw new AssertionError();
                            }
                            i = i5;
                        } else {
                            i = 0;
                        }
                    } else if (s10P2.n().E().T2()) {
                        C1015Gl c1015Gl2 = new C1015Gl(c1015GlQ2.L2(), s10O2);
                        next.a(c2409kl, (AbstractC1954eu) c1015Gl2);
                        if (!z4 && next.m() != c1015Gl2) {
                            throw new AssertionError();
                        }
                        i = i5;
                    } else {
                        i = 0;
                    }
                }
                if (i != 0) {
                    r5 = i5;
                }
                C1015Gl c1015GlQ3 = next.m().Q();
                S10 s10 = (S10) ((ArrayList) c1015GlQ3.h1()).get(0);
                if (c1015GlQ3.N2() && s10.b((Set) null)) {
                    C3332x4 c3332x4K4 = c1015GlQ3.K2();
                    C3332x4 c3332x4J4 = c1015GlQ3.J2();
                    if (a(c3332x4K4) && a(c3332x4J4) && c3332x4K4.z().get(0) == c3332x4J4.z().get(0)) {
                        C3332x4 c3332x9 = c3332x4K4.z().get(0);
                        if (c3332x9.y().size() == 2) {
                            int iIndexOf = c3332x9.y().indexOf(c3332x4K4);
                            int i6 = iIndexOf == 0 ? i5 : 0;
                            Iterator<ML> it4 = c3332x9.x().iterator();
                            int i7 = 0;
                            ?? r6 = r5;
                            while (it4.hasNext()) {
                                ML next2 = it4.next();
                                S10 s10C = next2.c(iIndexOf);
                                S10 s10C2 = next2.c(i6);
                                if (s10C.J() && s10C2.J()) {
                                    C0978Fc c0978FcE = s10C.n().E();
                                    C0978Fc c0978FcE2 = s10C2.n().E();
                                    it2 = it3;
                                    EnumC1076Il enumC1076IlL2 = c1015GlQ3.L2();
                                    r112 = r6;
                                    EnumC1076Il enumC1076Il = EnumC1076Il.EQ;
                                    if (enumC1076IlL2 == enumC1076Il) {
                                        i2 = iIndexOf;
                                        if ((c0978FcE.H2() == EnumC1812d20.INT && c0978FcE.N2() == 0) && c0978FcE2.S2()) {
                                            i3 = i6;
                                        }
                                        next2.g(s10);
                                        i7++;
                                        it3 = it2;
                                        r6 = r112;
                                        iIndexOf = i2;
                                        i6 = i3;
                                    } else {
                                        i2 = iIndexOf;
                                    }
                                    EnumC1076Il enumC1076IlL3 = c1015GlQ3.L2();
                                    EnumC1076Il enumC1076Il2 = EnumC1076Il.NE;
                                    if (enumC1076IlL3 == enumC1076Il2 && c0978FcE.S2()) {
                                        i3 = i6;
                                        if (c0978FcE2.H2() == EnumC1812d20.INT && c0978FcE2.N2() == 0) {
                                            next2.g(s10);
                                            i7++;
                                        }
                                        it3 = it2;
                                        r6 = r112;
                                        iIndexOf = i2;
                                        i6 = i3;
                                    } else {
                                        i3 = i6;
                                    }
                                    if (c1015GlQ3.L2() != enumC1076Il2) {
                                        if (c1015GlQ3.L2() == enumC1076Il && c0978FcE.S2()) {
                                            if (c0978FcE2.H2() == EnumC1812d20.INT || c0978FcE2.N2() != 0) {
                                                z3 = false;
                                            } else {
                                                z3 = true;
                                            }
                                            if (z3) {
                                            }
                                            it4 = it4;
                                            c3409y6 = c3409y6;
                                            it3 = it2;
                                            r6 = r112;
                                            iIndexOf = i2;
                                            i6 = i3;
                                        }
                                        r111 = r112;
                                    } else if (!(c0978FcE.H2() == EnumC1812d20.INT && c0978FcE.N2() == 0) || !c0978FcE2.S2()) {
                                        if (c1015GlQ3.L2() == enumC1076Il) {
                                            if (c0978FcE2.H2() == EnumC1812d20.INT) {
                                                z3 = false;
                                            } else {
                                                z3 = false;
                                            }
                                            if (z3) {
                                            }
                                            it4 = it4;
                                            c3409y6 = c3409y6;
                                            it3 = it2;
                                            r6 = r112;
                                            iIndexOf = i2;
                                            i6 = i3;
                                        }
                                        r111 = r112;
                                    }
                                    S10 s10A = c2409kl.a(next2.s(), next2.w());
                                    C0978Fc c0978FcA = c0978FcE.S2() ? c0978FcE : c0978FcE2;
                                    C3332x4 c3332x4H = next2.h();
                                    AbstractC1915eM abstractC1915eMY0 = c3332x4H.k().Y0();
                                    if (c0978FcA.h() == c3332x4K4 || c0978FcA.h() == c3332x4J4) {
                                        c0978FcA = C0978Fc.a(c2409kl, c0978FcA);
                                        c0978FcA.a(c3332x4H);
                                        c0978FcA.b(abstractC1915eMY0);
                                        c3332x4H.p().add(0, c0978FcA);
                                        i4 = 1;
                                    } else {
                                        i4 = 0;
                                    }
                                    next2.g(s10A);
                                    P30 p30 = new P30(EnumC2527mI.INT, s10A, s10, c0978FcA.b());
                                    p30.a(c3332x4H);
                                    p30.b(abstractC1915eMY0);
                                    c3332x4H.a(c2409kl, i4).add(p30);
                                    i7++;
                                    r112 = r112;
                                    it4 = it4;
                                    c3409y6 = c3409y6;
                                    it3 = it2;
                                    r6 = r112;
                                    iIndexOf = i2;
                                    i6 = i3;
                                } else {
                                    it2 = it3;
                                    r111 = r6;
                                    i2 = iIndexOf;
                                    i3 = i6;
                                }
                                r112 = r111;
                                it4 = it4;
                                c3409y6 = c3409y6;
                                it3 = it2;
                                r6 = r112;
                                iIndexOf = i2;
                                i6 = i3;
                            }
                            c3409y4 = c3409y6;
                            it = it3;
                            ?? r113 = r6;
                            r19 = r113;
                            if (i7 == c3332x9.x().size()) {
                                a(c2409kl, next, c1015GlQ3, c3332x4K4, c3332x4J4);
                            } else if (i7 > 0) {
                            }
                            z = true;
                            r110 = r113;
                        } else {
                            c3409y4 = c3409y6;
                            it = it3;
                            r19 = r5 == true ? 1 : 0;
                        }
                        z = false;
                        r110 = r19;
                    } else {
                        c3409y4 = c3409y6;
                        it = it3;
                        r19 = r5 == true ? 1 : 0;
                        z = false;
                        r110 = r19;
                    }
                } else {
                    c3409y4 = c3409y6;
                    it = it3;
                    r19 = r5 == true ? 1 : 0;
                    z = false;
                    r110 = r19;
                }
                if (z) {
                    if (next.m().N1()) {
                        r5 = 1;
                    } else {
                        c3409y6 = c3409y4;
                    }
                    it3 = it;
                    r5 = 1;
                } else {
                    r5 = r110;
                }
                C1015Gl c1015GlQ4 = next.m().Q();
                if (c1015GlQ4.N2()) {
                    S10 s10O3 = c1015GlQ4.O2();
                    S10 s10M = s10O3.m();
                    if (s10M.J()) {
                        C3332x4 c3332x4A = c1015GlQ4.a(s10M.n().E());
                        a(c2409kl, next, c1015GlQ4, c3332x4A, c3332x4A == c1015GlQ4.K2() ? c1015GlQ4.J2() : c1015GlQ4.K2());
                    } else {
                        if (c1015GlQ4.N2() && c1015GlQ4.O2().w().I()) {
                            if (!d && c1015GlQ4.L2() != EnumC1076Il.EQ && c1015GlQ4.L2() != EnumC1076Il.NE) {
                                throw new AssertionError();
                            }
                            if (s10O3.b(this.a)) {
                                C3332x4 c3332x4R2 = c1015GlQ4.R2();
                                a(c2409kl, next, c1015GlQ4, c3332x4R2, c3332x4R2 == c1015GlQ4.K2() ? c1015GlQ4.J2() : c1015GlQ4.K2());
                            } else if (s10O3.P()) {
                                C3332x4 c3332x4Q2 = c1015GlQ4.Q2();
                                a(c2409kl, next, c1015GlQ4, c3332x4Q2, c3332x4Q2 == c1015GlQ4.K2() ? c1015GlQ4.J2() : c1015GlQ4.K2());
                            } else if (c1015GlQ4.L2() != EnumC1076Il.EQ) {
                                b1A = s10O3.a(this.a, c2409kl.h());
                                if (b1A.w()) {
                                    if (s10O3.G()) {
                                        w1X = s10O3.x();
                                        if (!w1X.a(0L)) {
                                            switch (AbstractC3592w.a[c1015GlQ4.L2().ordinal()]) {
                                                case 1:
                                                case 2:
                                                    if (w1X.b() == 0) {
                                                        a(c2409kl, next, c1015GlQ4, 0);
                                                    }
                                                    break;
                                                case 3:
                                                case 4:
                                                    if (w1X.a() == 0) {
                                                        a(c2409kl, next, c1015GlQ4, 0);
                                                    }
                                                    break;
                                                case 5:
                                                case 6:
                                                    if (!d) {
                                                        throw new AssertionError();
                                                    }
                                                    break;
                                            }
                                        } else {
                                            a(c2409kl, next, c1015GlQ4, Long.signum(w1X.b()));
                                        }
                                    }
                                    z2 = false;
                                } else {
                                    if (s10O3.G()) {
                                        w1X = s10O3.x();
                                        if (!w1X.a(0L)) {
                                            switch (AbstractC3592w.a[c1015GlQ4.L2().ordinal()]) {
                                                case 1:
                                                case 2:
                                                    if (w1X.b() == 0) {
                                                        a(c2409kl, next, c1015GlQ4, 0);
                                                    }
                                                    break;
                                                case 3:
                                                case 4:
                                                    if (w1X.a() == 0) {
                                                        a(c2409kl, next, c1015GlQ4, 0);
                                                    }
                                                    break;
                                                case 5:
                                                case 6:
                                                    if (!d) {
                                                        throw new AssertionError();
                                                    }
                                                    break;
                                            }
                                        } else {
                                            a(c2409kl, next, c1015GlQ4, Long.signum(w1X.b()));
                                        }
                                    }
                                    z2 = false;
                                }
                            } else {
                                b1A = s10O3.a(this.a, c2409kl.h());
                                if (b1A.w()) {
                                    if (s10O3.G()) {
                                        w1X = s10O3.x();
                                        if (!w1X.a(0L)) {
                                            switch (AbstractC3592w.a[c1015GlQ4.L2().ordinal()]) {
                                                case 1:
                                                case 2:
                                                    if (w1X.b() == 0) {
                                                        a(c2409kl, next, c1015GlQ4, 0);
                                                    }
                                                    break;
                                                case 3:
                                                case 4:
                                                    if (w1X.a() == 0) {
                                                        a(c2409kl, next, c1015GlQ4, 0);
                                                    }
                                                    break;
                                                case 5:
                                                case 6:
                                                    if (!d) {
                                                        throw new AssertionError();
                                                    }
                                                    break;
                                            }
                                        } else {
                                            a(c2409kl, next, c1015GlQ4, Long.signum(w1X.b()));
                                        }
                                    }
                                    z2 = false;
                                } else {
                                    if (s10O3.G()) {
                                        w1X = s10O3.x();
                                        if (!w1X.a(0L)) {
                                            switch (AbstractC3592w.a[c1015GlQ4.L2().ordinal()]) {
                                                case 1:
                                                case 2:
                                                    if (w1X.b() == 0) {
                                                        a(c2409kl, next, c1015GlQ4, 0);
                                                    }
                                                    break;
                                                case 3:
                                                case 4:
                                                    if (w1X.a() == 0) {
                                                        a(c2409kl, next, c1015GlQ4, 0);
                                                    }
                                                    break;
                                                case 5:
                                                case 6:
                                                    if (!d) {
                                                        throw new AssertionError();
                                                    }
                                                    break;
                                            }
                                        } else {
                                            a(c2409kl, next, c1015GlQ4, Long.signum(w1X.b()));
                                        }
                                    }
                                    z2 = false;
                                }
                            }
                        } else if (c1015GlQ4.L2() != EnumC1076Il.EQ || c1015GlQ4.L2() == EnumC1076Il.NE) {
                            b1A = s10O3.a(this.a, c2409kl.h());
                            if (b1A.w() || b1A.k().a(0)) {
                                if (s10O3.G()) {
                                    w1X = s10O3.x();
                                    if (!w1X.a(0L)) {
                                        switch (AbstractC3592w.a[c1015GlQ4.L2().ordinal()]) {
                                            case 1:
                                            case 2:
                                                if (w1X.b() == 0) {
                                                    a(c2409kl, next, c1015GlQ4, 0);
                                                }
                                                break;
                                            case 3:
                                            case 4:
                                                if (w1X.a() == 0) {
                                                    a(c2409kl, next, c1015GlQ4, 0);
                                                }
                                                break;
                                            case 5:
                                            case 6:
                                                if (!d && w1X.d()) {
                                                    throw new AssertionError();
                                                }
                                                break;
                                        }
                                    } else {
                                        a(c2409kl, next, c1015GlQ4, Long.signum(w1X.b()));
                                    }
                                }
                                z2 = false;
                            } else {
                                C3332x4 c3332x4C = c1015GlQ4.c(1);
                                a(c2409kl, next, c1015GlQ4, c3332x4C, c3332x4C == c1015GlQ4.K2() ? c1015GlQ4.J2() : c1015GlQ4.K2());
                            }
                        } else {
                            if (s10O3.G()) {
                                w1X = s10O3.x();
                                if (!w1X.a(0L)) {
                                    switch (AbstractC3592w.a[c1015GlQ4.L2().ordinal()]) {
                                        case 1:
                                        case 2:
                                            if (w1X.b() == 0) {
                                                a(c2409kl, next, c1015GlQ4, 0);
                                            }
                                            break;
                                        case 3:
                                        case 4:
                                            if (w1X.a() == 0) {
                                                a(c2409kl, next, c1015GlQ4, 0);
                                            }
                                            break;
                                        case 5:
                                        case 6:
                                            if (!d) {
                                                throw new AssertionError();
                                            }
                                            break;
                                    }
                                } else {
                                    a(c2409kl, next, c1015GlQ4, Long.signum(w1X.b()));
                                }
                            }
                            z2 = false;
                        }
                        if (z2) {
                            c3409y5 = c3409y4;
                        } else {
                            c3409y5 = c3409y4;
                            if (c3409y5.a((S10) ((ArrayList) c1015GlQ4.h1()).get(0), c1015GlQ4.K2(), c1015GlQ4.J2())) {
                                c3332x4J2 = c1015GlQ4.J2();
                                if (c3332x4J2 == c1015GlQ4.K2()) {
                                    c3332x4K2 = c1015GlQ4.J2();
                                } else {
                                    c3332x4K2 = c1015GlQ4.K2();
                                }
                                a(c2409kl, next, c1015GlQ4, c3332x4J2, c3332x4K2);
                            } else {
                                c3409y6 = c3409y5;
                                it3 = it;
                            }
                        }
                        c3409y6 = c3409y5;
                        it3 = it;
                        r5 = 1;
                    }
                    z2 = true;
                    if (z2) {
                        c3409y5 = c3409y4;
                    } else {
                        c3409y5 = c3409y4;
                        if (c3409y5.a((S10) ((ArrayList) c1015GlQ4.h1()).get(0), c1015GlQ4.K2(), c1015GlQ4.J2())) {
                            c3332x4J2 = c1015GlQ4.J2();
                            if (c3332x4J2 == c1015GlQ4.K2()) {
                                c3332x4K2 = c1015GlQ4.J2();
                            } else {
                                c3332x4K2 = c1015GlQ4.K2();
                            }
                            a(c2409kl, next, c1015GlQ4, c3332x4J2, c3332x4K2);
                        } else {
                            c3409y6 = c3409y5;
                            it3 = it;
                        }
                    }
                    c3409y6 = c3409y5;
                    it3 = it;
                    r5 = 1;
                } else {
                    if (a(c2409kl, next, c1015GlQ4)) {
                        c3409y5 = c3409y4;
                    } else {
                        c3409y5 = c3409y4;
                        if (c3409y5.a((S10) ((ArrayList) c1015GlQ4.h1()).get(0), c1015GlQ4.K2(), c1015GlQ4.J2())) {
                            c3332x4J2 = c1015GlQ4.J2();
                            if (c3332x4J2 == c1015GlQ4.K2()) {
                                c3332x4K2 = c1015GlQ4.J2();
                            } else {
                                c3332x4K2 = c1015GlQ4.K2();
                            }
                            a(c2409kl, next, c1015GlQ4, c3332x4J2, c3332x4K2);
                        } else {
                            c3409y6 = c3409y5;
                            it3 = it;
                        }
                    }
                    c3409y6 = c3409y5;
                    it3 = it;
                    r5 = 1;
                }
            } else {
                c3409y5 = c3409y6;
                it = it3;
                c3409y6 = c3409y5;
                it3 = it;
            }
        }
    }

    public final void q(C2409kl c2409kl) {
        Iterator<C3332x4> it = c2409kl.d.iterator();
        while (it.hasNext()) {
            InterfaceC2641no interfaceC2641noA = it.next().a(c2409kl);
            while (interfaceC2641noA.hasNext()) {
                AbstractC2412ko next = interfaceC2641noA.next();
                if (next.U1() && next.X().P2() > 5) {
                    AbstractC1084Is abstractC1084IsX = next.X();
                    interfaceC2641noA.previous();
                    IdentityHashMap identityHashMap = new IdentityHashMap();
                    for (int i = 0; i < ((ArrayList) abstractC1084IsX.h1()).size(); i++) {
                        S10 s10 = (S10) ((ArrayList) abstractC1084IsX.h1()).get(i);
                        if (s10.J() && s10.b0() > 1) {
                            C0978Fc c0978FcE = s10.n().E();
                            S10 s10B = c0978FcE.b();
                            C0978Fc c0978FcA = (C0978Fc) identityHashMap.get(c0978FcE);
                            if (c0978FcA == null) {
                                c0978FcA = C0978Fc.a(c2409kl, c0978FcE);
                                interfaceC2641noA.add(c0978FcA);
                                c0978FcA.b(next.Y0());
                                identityHashMap.put(c0978FcE, c0978FcA);
                            }
                            ((ArrayList) abstractC1084IsX.h1()).set(i, c0978FcA.b());
                            s10B.e(abstractC1084IsX);
                            c0978FcA.b().b(abstractC1084IsX);
                        }
                    }
                    interfaceC2641noA.next();
                }
            }
        }
        if (!d && !c2409kl.b(this.a)) {
            throw new AssertionError();
        }
    }

    public final void r(C2409kl c2409kl) {
        S10 s10L2;
        S10 s10N2;
        S10 s10L3;
        if (c2409kl.t().c()) {
            for (C3332x4 c3332x4 : c2409kl.d) {
                InterfaceC2641no interfaceC2641noA = c3332x4.a(c2409kl);
                Set setC = OU.c();
                while (interfaceC2641noA.hasNext()) {
                    AbstractC2412ko next = interfaceC2641noA.next();
                    if (a(next)) {
                        T4 t4X = next.x();
                        if (t4X.L2().J()) {
                            s10L3 = t4X.N2();
                        } else {
                            if (!t4X.N2().J()) {
                                throw new C3405y10();
                            }
                            s10L3 = t4X.L2();
                        }
                        if (!d && s10L3 == null) {
                            throw new AssertionError();
                        }
                        setC.add(s10L3);
                    }
                }
                if (!setC.isEmpty()) {
                    MR mr = new MR();
                    mr.e(-1);
                    int size = c3332x4.p().size();
                    while (interfaceC2641noA.hasPrevious()) {
                        AbstractC2412ko abstractC2412koPrevious = interfaceC2641noA.previous();
                        size--;
                        for (S10 s10 : AbstractC0984Fi.a(abstractC2412koPrevious.h1(), abstractC2412koPrevious.S0())) {
                            if (setC.contains(s10) && !mr.containsKey(s10)) {
                                mr.a(size, s10);
                            }
                        }
                    }
                    int i = size - 1;
                    if (!d && i != -1) {
                        throw new AssertionError();
                    }
                    while (interfaceC2641noA.hasNext()) {
                        AbstractC2412ko next2 = interfaceC2641noA.next();
                        i++;
                        if (a(next2)) {
                            T4 t4X2 = next2.x();
                            if (t4X2.L2().J()) {
                                s10L2 = t4X2.N2();
                            } else {
                                if (!t4X2.N2().J()) {
                                    throw new C3405y10();
                                }
                                s10L2 = t4X2.L2();
                            }
                            boolean z = d;
                            if (!z && s10L2 == null) {
                                throw new AssertionError();
                            }
                            int iA = mr.a(s10L2);
                            if (!z && iA == -1) {
                                throw new AssertionError();
                            }
                            if (iA <= i) {
                                Set setC2 = OU.c();
                                C3332x4 c3332x4H = t4X2.h();
                                Iterator it = (s10L2.g() != null ? AbstractC0984Fi.a(s10L2.h0(), s10L2.g(), s10L2.g0()) : AbstractC0984Fi.a(s10L2.h0(), s10L2.g0())).iterator();
                                while (true) {
                                    if (it.hasNext()) {
                                        C3332x4 c3332x4H2 = ((InterfaceC2729oo) it.next()).h();
                                        if (c3332x4H2 != c3332x4H && !setC2.contains(c3332x4H2)) {
                                            if (!c3332x4H.e(c3332x4H2)) {
                                                setC2.add(c3332x4H2);
                                            }
                                        }
                                    } else {
                                        continue;
                                    }
                                }
                            }
                            if (!d && !a(next2)) {
                                throw new AssertionError();
                            }
                            T4 t4X3 = next2.x();
                            if (t4X3.L2().J()) {
                                s10N2 = t4X3.L2();
                            } else {
                                if (!t4X3.N2().J()) {
                                    throw new C3405y10();
                                }
                                s10N2 = t4X3.N2();
                            }
                            if (s10N2.Z() > 1) {
                                C0978Fc c0978FcA = C0978Fc.a(c2409kl, s10N2.b.E());
                                c0978FcA.b(next2.Y0());
                                c0978FcA.a(next2.h());
                                next2.b(s10N2, c0978FcA.b());
                                s10N2.e(next2);
                                interfaceC2641noA.previous();
                                interfaceC2641noA.add(c0978FcA);
                                interfaceC2641noA.next();
                            }
                        }
                    }
                }
            }
            if (!d && !c2409kl.b(this.a)) {
                throw new AssertionError();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static C0827Af c(C2409kl c2409kl) {
        return new C0827Af(c2409kl, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean b(C3332x4 c3332x4, C3332x4 c3332x5) {
        return c3332x5.m().J2() == c3332x4;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ LinkedHashMap b(C3332x4 c3332x4) {
        return new LinkedHashMap();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static com.android.tools.r8.internal.Y b(C2409kl c2409kl) {
        QC qc = new QC();
        for (AbstractC2412ko abstractC2412ko : c2409kl.r()) {
            if (abstractC2412ko.B1()) {
                C0978Fc c0978FcE = abstractC2412ko.E();
                if (!c0978FcE.b().A()) {
                    long jP2 = c0978FcE.P2();
                    if (qc.b(jP2)) {
                        ((List) qc.c(jP2)).add(c0978FcE);
                    } else {
                        ArrayList arrayList = new ArrayList();
                        arrayList.add(c0978FcE);
                        qc.a(arrayList, jP2);
                    }
                }
            }
        }
        return qc;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean b(AbstractC2412ko abstractC2412ko) {
        return !abstractC2412ko.m1();
    }

    private static void b(S10 s10) {
        ArrayList arrayListA = new C2993sU(new Function() { // from class: com.android.tools.r8.ir.optimize.F$$ExternalSyntheticLambda11
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((S10) obj).g0();
            }
        }).a(s10);
        for (int size = arrayListA.size() - 1; size >= 0; size--) {
            Set set = (Set) arrayListA.get(size);
            if (set.size() != 1 || set.iterator().next() != s10) {
                Set<ML> setC = OU.c();
                Iterator it = set.iterator();
                while (it.hasNext()) {
                    ML mlL = ((S10) it.next()).l();
                    Iterator<S10> it2 = mlL.k0().iterator();
                    while (true) {
                        if (it2.hasNext()) {
                            S10 next = it2.next();
                            if (next != s10 && !set.contains(next)) {
                                break;
                            }
                        } else {
                            setC.add(mlL);
                            break;
                        }
                    }
                }
                for (ML ml : setC) {
                    Iterator<S10> it3 = ml.k0().iterator();
                    while (it3.hasNext()) {
                        it3.next().c(ml);
                    }
                    ml.g(s10);
                    ml.h().b(ml);
                }
            }
        }
    }

    static void a(C2409kl c2409kl, final B4 b4, C3332x4 c3332x4, InterfaceC2641no interfaceC2641no, C1458Vr c1458Vr, List list, C2732or c2732or) {
        C1015Gl c1015Gl;
        C2409kl c2409kl2 = c2409kl;
        AbstractC1915eM abstractC1915eMY0 = c1458Vr.Y0();
        C2807pq c2807pqP2 = c1458Vr.P2();
        C3332x4 c3332x4J2 = c1458Vr.J2();
        interfaceC2641no.previous();
        C3332x4 c3332x4A = interfaceC2641no.a(c2409kl2, b4);
        boolean z = d;
        if (!z && c3332x4A.D()) {
            throw new AssertionError();
        }
        if (!z && c3332x4A.p().size() != 1) {
            throw new AssertionError();
        }
        if (!z && !c3332x4.m().M1()) {
            throw new AssertionError();
        }
        c1458Vr.c(c3332x4.m());
        b4.remove();
        c1458Vr.h().i();
        C3332x4 c3332x4D0 = c1458Vr.h().d0();
        if (!z && c1458Vr.h().y().size() != 0) {
            throw new AssertionError();
        }
        if (!z && c1458Vr.h().z().size() != 0) {
            throw new AssertionError();
        }
        if (!z && c3332x4D0 != c3332x4) {
            throw new AssertionError();
        }
        LinkedList linkedList = new LinkedList();
        int size = list.size() - 1;
        while (true) {
            if (size < 0) {
                break;
            }
            E e = new E(abstractC1915eMY0);
            e.c = c1458Vr.value();
            InterfaceC0967Er interfaceC0967Er = (InterfaceC0967Er) list.get(size);
            for (int i = 0; i < interfaceC0967Er.size(); i++) {
                int i2 = interfaceC0967Er.i(i);
                e.d.a(i2, (C3332x4) c2807pqP2.get(i2));
            }
            e.e = c3332x4J2;
            e.a = c2409kl2.f.a();
            c3332x4J2 = e.a(c2409kl.t());
            linkedList.addFirst(c3332x4J2);
            size--;
        }
        int size2 = c2732or.size() - 1;
        while (size2 >= 0) {
            int i3 = c2732or.i(size2);
            C3332x4 c3332x5 = (C3332x4) c2807pqP2.get(i3);
            B b = new B(abstractC1915eMY0, c2409kl2);
            b.c = c1458Vr.value();
            b.d = i3;
            b.e = c3332x5;
            b.f = c3332x4J2;
            b.a = c2409kl2.f.a();
            boolean z2 = B.g;
            if (!z2 && b.e == null) {
                throw new AssertionError();
            }
            if (!z2 && b.f == null) {
                throw new AssertionError();
            }
            int i4 = b.d;
            if (i4 != 0) {
                C0978Fc c0978FcA = c2409kl2.a(i4, (com.android.tools.r8.graph.X) null);
                c0978FcA.b(abstractC1915eMY0);
                c1015Gl = new C1015Gl(EnumC1076Il.EQ, AbstractC1639am.a(b.c, c0978FcA.J2()));
                int i5 = b.a;
                C3155ul c3155ulT = c2409kl.t();
                AbstractC2412ko[] abstractC2412koArr = {c0978FcA};
                c3332x4J2 = new C3332x4();
                c3332x4J2.a(abstractC2412koArr[0], c3155ulT);
                c3332x4J2.a(c1015Gl, c3155ulT);
                c3332x4J2.a((C2102gl) null);
                c3332x4J2.g(i5);
            } else {
                c1015Gl = new C1015Gl(EnumC1076Il.EQ, b.c);
                int i6 = b.a;
                C3155ul c3155ulT2 = c2409kl.t();
                C3332x4 c3332x6 = new C3332x4();
                c3332x6.a(c1015Gl, c3155ulT2);
                c3332x6.a((C2102gl) null);
                c3332x6.g(i6);
                c3332x4J2 = c3332x6;
            }
            c1015Gl.b(abstractC1915eMY0);
            c3332x4J2.h(b.e);
            c3332x4J2.h(b.f);
            linkedList.addFirst(c3332x4J2);
            size2--;
            c2409kl2 = c2409kl;
        }
        c3332x4.h(c3332x4J2);
        linkedList.forEach(new Consumer() { // from class: com.android.tools.r8.ir.optimize.F$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                b4.add((C3332x4) obj);
            }
        });
    }

    private D a(ArrayList arrayList, D d2, D d3) {
        H1 h1Y0 = this.c.y0();
        int i = h1Y0.a() ? 4 : 0;
        if (d2 == null) {
            arrayList.add(d3);
            return d3;
        }
        D d4 = new D(d2.a, d3.a);
        long jA = d4.a(h1Y0);
        if (jA > 0 && jA >= (C1458Vr.a(h1Y0, d3.a.a((int[]) null)) + C1458Vr.a(h1Y0, d2.a.a((int[]) null))) - ((long) i)) {
            arrayList.set(arrayList.size() - 1, d4);
            return d4;
        }
        arrayList.add(d3);
        return d3;
    }

    private void a(HashSet hashSet, PriorityQueue priorityQueue, D d2) {
        if (!d && hashSet.contains(d2)) {
            throw new AssertionError();
        }
        long jA = d2.a(this.c.y0());
        if (jA <= 0) {
            return;
        }
        if (priorityQueue.size() < 10) {
            priorityQueue.add(d2);
            hashSet.add(d2);
        } else if (jA > ((D) priorityQueue.peek()).a(this.c.y0())) {
            priorityQueue.add(d2);
            hashSet.add(d2);
            hashSet.remove(priorityQueue.poll());
        }
    }

    private int a(EnumC1812d20 enumC1812d20, C2732or c2732or) {
        H1 h1Y0 = this.c.y0();
        int i = C1015Gl.k;
        int size = c2732or.size() * (h1Y0.a() ? 3 : 2);
        if (this.c.y0().a()) {
            size += c2732or.size() * 4;
        }
        InterfaceC0841Ar interfaceC0841ArListIterator = c2732or.listIterator(0);
        while (interfaceC0841ArListIterator.hasNext()) {
            int iIntValue = Integer.valueOf(((com.android.tools.r8.internal.K) interfaceC0841ArListIterator).t()).intValue();
            if (iIntValue != 0) {
                size += C0978Fc.a(this.c.y0(), enumC1812d20, iIntValue);
            }
        }
        return size;
    }

    static void a(C2409kl c2409kl, C3332x4 c3332x4, InterfaceC2641no interfaceC2641no, C1458Vr c1458Vr) {
        C1015Gl c1015Gl;
        int iK2 = c1458Vr.K2();
        int i = c1458Vr.M2()[0];
        if (iK2 < i) {
            c3332x4.a(iK2, i);
        }
        if (c1458Vr.T1() && c1458Vr.W().O2() == 0) {
            c1015Gl = new C1015Gl(EnumC1076Il.EQ, c1458Vr.value());
        } else {
            C0978Fc c0978FcA = c2409kl.a(c1458Vr.O2(), (com.android.tools.r8.graph.X) null);
            c0978FcA.b(c1458Vr.Y0());
            interfaceC2641no.previous();
            interfaceC2641no.add(c0978FcA);
            AbstractC2412ko next = interfaceC2641no.next();
            if (!d && next != c1458Vr) {
                throw new AssertionError();
            }
            c1015Gl = new C1015Gl(EnumC1076Il.EQ, AbstractC1639am.a(c1458Vr.value(), c0978FcA.b()));
        }
        interfaceC2641no.d(c1015Gl);
    }

    /* JADX WARN: Code duplicated, block: B:134:0x0354  */
    private void a(C2409kl c2409kl, B4 b4, C3332x4 c3332x4, InterfaceC2641no interfaceC2641no, C1458Vr c1458Vr) {
        ArrayList<D> arrayList;
        H1 h1;
        int i;
        HashSet hashSet;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        if (a(c1458Vr)) {
            return;
        }
        if (c1458Vr.L2() == 1) {
            a(c2409kl, c3332x4, interfaceC2641no, c1458Vr);
            return;
        }
        final H1 h1Y0 = this.c.y0();
        int[] iArrQ2 = c1458Vr.Q2();
        PriorityQueue priorityQueue = new PriorityQueue(new Comparator() { // from class: com.android.tools.r8.ir.optimize.F$$ExternalSyntheticLambda10
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return F.a(h1Y0, (D) obj, (D) obj2);
            }
        });
        HashSet hashSet2 = new HashSet();
        ArrayList arrayList2 = new ArrayList();
        int i7 = iArrQ2[0];
        C2732or c2732or = new C2732or(16);
        c2732or.a(i7);
        int i8 = 1;
        D d2 = null;
        while (i8 < iArrQ2.length) {
            int i9 = iArrQ2[i8];
            if (((long) i9) - ((long) i7) > 1) {
                D d3 = new D(c2732or);
                D dA = a(arrayList2, d2, d3);
                if (dA != d3 && hashSet2.contains(d2)) {
                    hashSet2.remove(d2);
                    priorityQueue.remove(d2);
                }
                a(hashSet2, priorityQueue, dA);
                c2732or = new C2732or(16);
                d2 = dA;
            }
            c2732or.a(i9);
            i8++;
            i7 = i9;
        }
        D d4 = new D(c2732or);
        D dA2 = a(arrayList2, d2, d4);
        if (dA2 != d4 && hashSet2.contains(d2)) {
            hashSet2.remove(d2);
            priorityQueue.remove(d2);
        }
        a(hashSet2, priorityQueue, dA2);
        int i10 = 10;
        if (hashSet2.size() == 10 && 10 < arrayList2.size()) {
            hashSet2.remove(priorityQueue.poll());
        }
        ArrayList<D> arrayList3 = new ArrayList(10);
        D d5 = null;
        for (int i11 = 0; i11 < arrayList2.size(); i11++) {
            D d6 = (D) arrayList2.get(i11);
            if (hashSet2.contains(d6)) {
                arrayList3.add(d6);
            } else {
                if (d5 == null) {
                    arrayList3.add(d6);
                    d5 = d6;
                } else {
                    if (!D.b) {
                        C2732or c2732or2 = d5.a;
                        if (c2732or2.i(c2732or2.size() - 1) >= d6.a.i(0)) {
                            throw new AssertionError();
                        }
                    }
                    C2732or c2732or3 = d5.a;
                    c2732or3.a(c2732or3.size(), (InterfaceC0967Er) d6.a);
                }
            }
        }
        int i12 = 0;
        C2732or c2732or4 = new C2732or(16);
        if (this.a.Y().n1.M) {
            HashSet hashSet3 = new HashSet();
            H1 h1Y1 = this.c.y0();
            int i13 = 0;
            for (D d7 : arrayList3) {
                long jA = C1458Vr.a(h1Y1, d7.a.a((int[]) null));
                int iA = a(c1458Vr.value().d0(), d7.a);
                if (d7.a.size() <= i10) {
                    HashSet hashSet4 = hashSet3;
                    if (iA < jA - ((long) (this.c.y0().a() ? 3 : 1))) {
                        i13 += iA;
                        hashSet4.add(d7);
                        c2732or4.a(c2732or4.size(), (InterfaceC0967Er) d7.a);
                        hashSet3 = hashSet4;
                        i10 = 10;
                    } else {
                        hashSet = hashSet4;
                    }
                } else {
                    hashSet = hashSet3;
                }
                C2732or c2732or5 = d7.a;
                long j = Long.MAX_VALUE;
                int i14 = i12;
                int i15 = -1;
                while (i14 < c2732or5.size()) {
                    H1 h2 = h1Y0;
                    long jAbs = Math.abs(c2732or5.i(i14));
                    if (jAbs < j) {
                        j = jAbs;
                        i15 = i14;
                    }
                    i14++;
                    h1Y0 = h2;
                }
                H1 h3 = h1Y0;
                C2732or c2732or6 = new C2732or(16);
                c2732or6.a(c2732or5.i(i15));
                long jA2 = jA - ((long) a(c1458Vr.value().d0(), c2732or6));
                C2732or c2732or7 = c2732or4;
                long size = c2732or5.size() - c2732or6.size();
                ArrayList arrayList4 = arrayList3;
                long j2 = jA2 - ((h1Y1.a() ? size * 8 : (size * 4) + 2) + ((long) (h1Y1.a() ? 12 : 3)));
                int i16 = i15 - 1;
                int i17 = i15 + 1;
                long j3 = 0;
                while (true) {
                    if (c2732or6.size() >= 10 || j2 <= j3) {
                        i2 = i17;
                        break;
                    }
                    if (i16 < 0 || i17 >= c2732or5.size()) {
                        i2 = i17;
                        if (i16 >= 0) {
                            i6 = i16 - 1;
                            c2732or6.a(c2732or5.i(i16));
                            i5 = i2;
                            i16 = i6;
                        } else {
                            if (i2 >= c2732or5.size()) {
                                break;
                            }
                            i5 = i2 + 1;
                            c2732or6.a(c2732or5.i(i2));
                        }
                    } else {
                        int i18 = i17;
                        if (Math.abs(c2732or5.i(i17)) <= Math.abs(c2732or5.i(i16))) {
                            i5 = i18 + 1;
                            c2732or6.a(c2732or5.i(i18));
                        } else {
                            i2 = i18;
                            i6 = i16 - 1;
                            c2732or6.a(c2732or5.i(i16));
                            i5 = i2;
                            i16 = i6;
                        }
                    }
                    long jA3 = jA - ((long) a(c1458Vr.value().d0(), c2732or6));
                    int i19 = i16;
                    long size2 = c2732or5.size() - c2732or6.size();
                    long j4 = jA3 - ((h1Y1.a() ? size2 * 8 : (size2 * 4) + 2) + ((long) (h1Y1.a() ? 12 : 3)));
                    j3 = j2;
                    i17 = i5;
                    j2 = j4;
                    i16 = i19;
                }
                if (j3 >= j2) {
                    i3 = 1;
                    int i20 = c2732or6.i(c2732or6.size() - 1);
                    c2732or6.h(c2732or6.size() - 1);
                    int i21 = i16 + 1;
                    if (i20 == c2732or5.i(i21)) {
                        i4 = i2;
                        i16 = i21;
                    } else {
                        i4 = i2 - 1;
                    }
                } else {
                    i3 = 1;
                    i4 = i2;
                }
                int i22 = i16 + i3;
                int i23 = i4 - 1;
                if (c2732or6.size() > 0) {
                    int iA2 = a(c1458Vr.value().d0(), c2732or6);
                    long size3 = c2732or5.size() - c2732or6.size();
                    if ((h1Y1.a() ? size3 * 8 : (size3 * 4) + 2) + ((long) (h1Y1.a() ? 12 : 3)) + ((long) iA2) + ((long) (this.c.y0().a() ? 3 : 1)) < jA) {
                        c2732or5.a(i22, i23);
                        c2732or4 = c2732or7;
                        c2732or4.a(c2732or7.size(), (InterfaceC0967Er) c2732or6);
                        i13 += iA2;
                    } else {
                        c2732or4 = c2732or7;
                    }
                } else {
                    c2732or4 = c2732or7;
                }
                hashSet3 = hashSet;
                h1Y0 = h3;
                arrayList3 = arrayList4;
                i10 = 10;
                i12 = 0;
            }
            arrayList = arrayList3;
            h1 = h1Y0;
            arrayList.removeAll(hashSet3);
            i = i13;
        } else {
            arrayList = arrayList3;
            h1 = h1Y0;
            i = 0;
        }
        ArrayList arrayList5 = new ArrayList(arrayList.size());
        long jA4 = 0;
        for (D d8 : arrayList) {
            jA4 += C1458Vr.a(h1, d8.a.a((int[]) null));
            arrayList5.add(d8.a);
        }
        if (jA4 + ((long) i) + ((long) (this.c.y0().a() ? 3 : 1)) < C1458Vr.a(h1, c1458Vr.Q2())) {
            a(c2409kl, b4, c3332x4, interfaceC2641no, c1458Vr, arrayList5, c2732or4);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int a(H1 h1, D d2, D d3) {
        return Long.compare(d3.a(h1), d2.a(h1));
    }

    private boolean a(C1458Vr c1458Vr) {
        C3733i c3733iI;
        C0692k3 c0692k3M;
        S10 s10M = c1458Vr.value().m();
        if (!s10M.b(new F$$ExternalSyntheticLambda3()) || (c3733iI = this.a.i()) == null || (c0692k3M = c3733iI.c(s10M.q().T().getField()).m()) == null) {
            return false;
        }
        return AbstractC1046Hk.a(this.a, c0692k3M.s());
    }

    public static void a(C0702m<?> c0702m, C2409kl c2409kl) {
        C3332x4 c3332x4J;
        boolean z = d;
        if (!z) {
            c2409kl.b((C0702m) c0702m, false);
        }
        ArrayList arrayList = new ArrayList();
        B4 b4S = c2409kl.s();
        if (!z && !b4S.hasNext()) {
            throw new AssertionError();
        }
        C3332x4 next = b4S.next();
        while (true) {
            C3332x4 next2 = b4S.hasNext() ? b4S.next() : null;
            if (next.Q()) {
                a(c2409kl, next, next2, arrayList);
            }
            if (next.m().N1()) {
                C1015Gl c1015GlQ = next.m().Q();
                C3332x4 c3332x4K2 = c1015GlQ.K2();
                C3332x4 c3332x4J2 = c3332x4K2.j();
                C3332x4 c3332x4J3 = c1015GlQ.J2();
                C3332x4 c3332x4J4 = c3332x4J3.j();
                if (c3332x4J2 != null && c3332x4K2 != c3332x4J2) {
                    c1015GlQ.h().b(c3332x4K2, c3332x4J2);
                    c3332x4K2.r().remove(next);
                    if (!c3332x4J2.y().contains(next)) {
                        c3332x4J2.r().add(next);
                    }
                }
                if (next.m().N1()) {
                    C1015Gl c1015GlQ2 = next.m().Q();
                    if (c1015GlQ2.K2() == c3332x4J4) {
                        next.b(c1015GlQ2.K2(), c3332x4J3);
                        boolean z2 = d;
                        if (!z2 && !next.m().M1()) {
                            throw new AssertionError();
                        }
                        if (!z2 && AbstractC2967s4.a(next) != c3332x4J3) {
                            throw new AssertionError();
                        }
                    }
                }
            }
            if (next.m().A2()) {
                IY iyK0 = next.m().K0();
                C3332x4 c3332x4J5 = iyK0.J2();
                HashSet hashSet = new HashSet();
                for (int i = 0; i < iyK0.M2().length; i++) {
                    C3332x4 c3332x4E = iyK0.e(i);
                    if (c3332x4E != c3332x4J5 && (c3332x4J = c3332x4E.j()) != null && c3332x4E != c3332x4J && !hashSet.contains(c3332x4E)) {
                        iyK0.h().b(c3332x4E, c3332x4J);
                        c3332x4E.r().remove(next);
                        if (!c3332x4J.y().contains(next)) {
                            c3332x4J.r().add(next);
                        }
                        hashSet.add(c3332x4E);
                    }
                }
            }
            if (next2 == null) {
                c2409kl.b((Collection<C3332x4>) arrayList);
                while (!arrayList.isEmpty()) {
                    arrayList = new ArrayList();
                    B4 b4S2 = c2409kl.s();
                    C3332x4 next3 = b4S2.next();
                    while (true) {
                        C3332x4 next4 = b4S2.hasNext() ? b4S2.next() : null;
                        if (next3.Q()) {
                            a(c2409kl, next3, next4, arrayList);
                        }
                        if (next4 == null) {
                            break;
                        } else {
                            next3 = next4;
                        }
                    }
                    c2409kl.b((Collection<C3332x4>) arrayList);
                }
                boolean z3 = d;
                if (!z3) {
                    h(c2409kl);
                }
                if (z3) {
                    return;
                }
                c2409kl.b((C0702m) c0702m, false);
                return;
            }
            next = next2;
        }
    }

    /* JADX WARN: Code duplicated, block: B:34:0x00b4  */
    /* JADX WARN: Code duplicated, block: B:86:0x0204  */
    public final void a(C2409kl c2409kl, o5 o5Var, TE te, C1230Ob c1230Ob) {
        C3030t00 c3030t00;
        boolean z;
        boolean z2;
        C3030t00 c3030t01;
        char c;
        AbstractC0727q0 abstractC0727q0C;
        if (this.a.q()) {
            if (!d && !this.a.f().i()) {
                throw new AssertionError();
            }
            C0702m c0702mI0 = this.a.i0();
            if (this.a.Y().n1.H) {
                C3155ul c3155ulT = c2409kl.t();
                if (c3155ulT.f() || c3155ulT.o()) {
                    boolean z3 = false;
                    C3030t00 c3030t02 = new C3030t00(this.a, false);
                    Set setC = OU.c();
                    C2561ml c2561ml = new C2561ml(c2409kl);
                    while (c2561ml.hasNext()) {
                        AbstractC2412ko abstractC2412ko = (AbstractC2412ko) c2561ml.next();
                        if (abstractC2412ko.w1()) {
                            boolean zC = abstractC2412ko.b().C();
                            C2972s9 c2972s9Y = abstractC2412ko.y();
                            S10 s10E = c2972s9Y.e();
                            S10 s10B = c2972s9Y.b();
                            C0767v2 c0767v2J2 = c2972s9Y.J2();
                            C0767v2 c0767v2B = c0767v2J2.b(this.b);
                            if ((c0767v2B.O0() && ((abstractC0727q0C = this.a.c(c0767v2B)) == null || AbstractC0639e.a(abstractC0727q0C, c2409kl.h(), c0702mI0).b())) || !this.a.A().a(c0702mI0, s10E) || (this.c.d() && s10E.w().E() && c0767v2J2.M0() && c0767v2J2.b(this.b).Q0())) {
                                c3030t01 = c3030t02;
                                z = z3;
                                zC = zC;
                                c = 1;
                            } else {
                                this.c.getClass();
                                if (c0767v2J2.M0()) {
                                    C0767v2 c0767v2B2 = c0767v2J2.b(this.b);
                                    if (c0767v2B2.O0() && c0702mI0.c(c0767v2B2).isInterface()) {
                                        c3030t01 = c3030t02;
                                        z = z3;
                                        zC = zC;
                                    }
                                    c = 1;
                                }
                                AbstractC3472z00 abstractC3472z00W = s10E.w();
                                AbstractC3472z00 abstractC3472z00W2 = s10B.w();
                                c3030t01 = c3030t02;
                                AbstractC3472z00 abstractC3472z00A = AbstractC3472z00.a(c0767v2J2, abstractC3472z00W.N(), (C0702m<?>) this.a);
                                boolean z4 = d;
                                z = z3;
                                if (!z4) {
                                    WH whN = abstractC3472z00W.N();
                                    WH whN2 = abstractC3472z00W2.N();
                                    if (whN.a(whN2) != whN2) {
                                        throw new AssertionError();
                                    }
                                }
                                if (abstractC3472z00W.a(abstractC3472z00A, this.a)) {
                                    if (!z4 && !abstractC3472z00W.a(abstractC3472z00W2, this.a)) {
                                        throw new AssertionError();
                                    }
                                    a(c2972s9Y, c2561ml, s10E, s10B);
                                    setC.addAll(s10E.a());
                                } else if (c0767v2J2.O0() && c0767v2J2.b(c0702mI0) && !s10B.z()) {
                                    c2561ml.previous();
                                    S10 s10A = c2561ml.a(c2409kl, this.c);
                                    c2561ml.next();
                                    c2972s9Y.b().g(s10A);
                                    setC.addAll(s10A.a());
                                    T0 t0A = U0.a(this.a, te.b(), c1230Ob);
                                    t0A.a(te);
                                    int i = C1721bt.m;
                                    C1721bt c1721btB = ((C1646at) ((C1646at) ((C1646at) new C1646at().a(t0A.a().getReference())).a(AbstractC1639am.c(c2972s9Y.e()))).b(c2972s9Y)).b();
                                    c2561ml.a(c1721btB, (Set) null);
                                    if (!z4 && c1721btB.h(this.a, o5Var) == null) {
                                        throw new AssertionError();
                                    }
                                } else {
                                    if (!(c2972s9Y instanceof C3133uU)) {
                                        S10 s10T0 = c2972s9Y.T0();
                                        s10T0.getClass();
                                        if (AbstractC1154Lf.a(c0702mI0, s10T0).o().b(abstractC3472z00A, this.a)) {
                                            if (abstractC3472z00W.b(K00.a(c0702mI0, o5Var, c2972s9Y.b()), this.a)) {
                                            }
                                        }
                                    } else if (abstractC3472z00W.b(K00.a(c0702mI0, o5Var, c2972s9Y.b()), this.a)) {
                                    }
                                    if (!z4 && abstractC3472z00W.N().f() && (!s10E.i() || abstractC3472z00W.E())) {
                                        throw new AssertionError();
                                    }
                                    if (!z4 && !abstractC3472z00W2.a(abstractC3472z00A)) {
                                        throw new AssertionError();
                                    }
                                    c = 1;
                                }
                                c = 2;
                            }
                            if (c == 1) {
                                c3030t00 = c3030t01;
                                c3030t02 = c3030t00;
                                z3 = z;
                            } else {
                                if (!d && c != 2) {
                                    throw new AssertionError();
                                }
                                z2 = z | zC;
                                c3030t00 = c3030t01;
                                c3030t00.a((Iterable) setC);
                                setC.clear();
                                z3 = z2;
                                c3030t02 = c3030t00;
                            }
                        } else {
                            c3030t00 = c3030t02;
                            z = z3;
                            if (abstractC2412ko instanceof C1641ao) {
                                boolean zC2 = abstractC2412ko.b().C();
                                if (a(c0702mI0, abstractC2412ko.U(), c2561ml, c2409kl)) {
                                    z2 = z | zC2;
                                    z3 = z2;
                                    c3030t02 = c3030t00;
                                }
                            }
                            c3030t02 = c3030t00;
                            z3 = z;
                        }
                    }
                    C3030t00 c3030t03 = c3030t02;
                    if (z3) {
                        c2409kl.a((C2102gl) null, setC);
                        if (!setC.isEmpty()) {
                            c3030t03.a((Iterable) setC);
                        }
                    }
                    if (!d && !c2409kl.b(this.a)) {
                        throw new AssertionError();
                    }
                }
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:53:0x0100  */
    /* JADX WARN: Code duplicated, block: B:54:0x0102  */
    private boolean a(C0702m c0702m, C1641ao c1641ao, C2561ml c2561ml, C2409kl c2409kl) {
        S10 s10A;
        AbstractC0727q0 abstractC0727q0C;
        o5 o5VarH = c2409kl.h();
        C0767v2 c0767v2B = c1641ao.J2().b(this.b);
        if (c0767v2B.O0() && ((abstractC0727q0C = this.a.c(c0767v2B)) == null || AbstractC0639e.a(abstractC0727q0C, o5VarH, c0702m).b())) {
            return false;
        }
        S10 s10Value = c1641ao.value();
        if (!this.a.A().a(c0702m, s10Value)) {
            return false;
        }
        AbstractC3472z00 abstractC3472z00W = s10Value.w();
        AbstractC3472z00 abstractC3472z00A = AbstractC3472z00.a(c1641ao.J2(), abstractC3472z00W.N(), (C0702m<?>) this.a);
        S10 s10M = s10Value.m();
        char c = 3;
        if (!abstractC3472z00W.N().f()) {
            if (abstractC3472z00W.a(abstractC3472z00A, this.a) && !abstractC3472z00W.F()) {
                c = 2;
            } else if (s10M.i() || !s10M.b.E1() || !abstractC3472z00A.c(abstractC3472z00W, this.a)) {
                if (this.a.f().i()) {
                    char c2 = (c1641ao.J2().O0() && a(c1641ao.J2())) ? (char) 3 : (char) 1;
                    if (c2 == 1 && abstractC3472z00W.w() && a(abstractC3472z00W.b().R())) {
                        c2 = 3;
                    }
                    if (c2 != 1 || (s10A = s10Value.a(new Predicate() { // from class: com.android.tools.r8.ir.optimize.F$$ExternalSyntheticLambda9
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                            return F.a((S10) obj);
                        }
                    })) == null) {
                        c = c2;
                    } else {
                        C1207Nf c1207NfA = s10A.q().w().J2().a();
                        WH whF = c1207NfA.f();
                        if (!whF.f()) {
                            if (!c1207NfA.o().a(abstractC3472z00A, this.a) || (abstractC3472z00W.F() && whF.h())) {
                                c = c2;
                            } else {
                                c = 2;
                            }
                        }
                    }
                } else {
                    c = 1;
                }
            }
        }
        if (c == 1) {
            return false;
        }
        c2561ml.a(new C0978Fc(new S10(c2409kl.e.a(), AbstractC3472z00.k(), c1641ao.b().s()), c == 2 ? 1L : 0L), (Set) null);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(S10 s10) {
        return s10.b(new Rs$$ExternalSyntheticLambda0());
    }

    private boolean a(C0767v2 c0767v2) {
        boolean z = d;
        if (!z && !this.a.f().i()) {
            throw new AssertionError();
        }
        if (!z && !c0767v2.O0()) {
            throw new AssertionError();
        }
        C0729q2 c0729q2A = C0729q2.a(this.a.c(c0767v2));
        return (c0729q2A == null || this.a.f().o().b(c0729q2A)) ? false : true;
    }

    public static void a(AbstractC2412ko abstractC2412ko, C2561ml c2561ml, S10 s10, S10 s11) {
        if (s11.A() && s11.s() != s10.s()) {
            c2561ml.a(new C1289Qd(s11, s10), (Set) null);
            return;
        }
        if (s11.A()) {
            if (!d && s11.s() != s10.s()) {
                throw new AssertionError();
            }
            abstractC2412ko.a(s11.s());
        }
        s11.g(s10);
        c2561ml.s();
    }

    private static boolean a(AbstractC2412ko abstractC2412ko) {
        if (!abstractC2412ko.n1() && !abstractC2412ko.i2()) {
            return false;
        }
        T4 t4X = abstractC2412ko.x();
        boolean z = (t4X.d(t4X.L2()) && t4X.d(t4X.N2())) ? false : true;
        if (d || !z || t4X.L2().J() || t4X.N2().J()) {
            return z;
        }
        throw new AssertionError();
    }

    public final void a(final C2409kl c2409kl, final H h) {
        if (this.c.n1.A0) {
            return;
        }
        P1 p1 = new P1(new Supplier() { // from class: com.android.tools.r8.ir.optimize.F$$ExternalSyntheticLambda6
            @Override // java.util.function.Supplier
            public final Object get() {
                return F.d(c2409kl);
            }
        });
        IdentityHashMap identityHashMap = new IdentityHashMap();
        Iterator<C3332x4> it = c2409kl.d.iterator();
        while (it.hasNext()) {
            a(c2409kl, it.next(), p1, identityHashMap, new Predicate() { // from class: com.android.tools.r8.ir.optimize.F$$ExternalSyntheticLambda7
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return h.a((AbstractC2412ko) obj);
                }
            });
        }
        B4 b4S = c2409kl.s();
        while (b4S.hasNext()) {
            C3332x4 next = b4S.next();
            Map map = (Map) identityHashMap.get(next);
            if (map != null) {
                if (!d && map.isEmpty()) {
                    throw new AssertionError();
                }
                if (!next.y().isEmpty() && map.size() > 50) {
                    map.values().removeIf(new Predicate() { // from class: com.android.tools.r8.ir.optimize.F$$ExternalSyntheticLambda8
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                            return F.a(c2409kl, (AbstractC2412ko) obj);
                        }
                    });
                }
                boolean zD = next.D();
                InterfaceC2641no interfaceC2641noA = next.a(c2409kl);
                while (interfaceC2641noA.hasNext()) {
                    AbstractC2412ko next2 = interfaceC2641noA.next();
                    if (next2.g2() || ((zD && next2.g()) || (this.c.h() && (next2 instanceof C2318jb)))) {
                        break;
                    }
                    Iterator it2 = AbstractC0984Fi.a(next2.h1(), next2.S0()).iterator();
                    InterfaceC2641no interfaceC2641noA2 = interfaceC2641noA;
                    while (it2.hasNext()) {
                        AbstractC2412ko abstractC2412ko = (AbstractC2412ko) map.remove((S10) it2.next());
                        if (abstractC2412ko != null) {
                            interfaceC2641noA2 = a(c2409kl, b4S, interfaceC2641noA2, abstractC2412ko, next2);
                        }
                    }
                    interfaceC2641noA = interfaceC2641noA2;
                }
                AbstractC2412ko abstractC2412koJ = interfaceC2641noA.j();
                Iterator it3 = map.values().iterator();
                InterfaceC2641no interfaceC2641noA3 = interfaceC2641noA;
                while (it3.hasNext()) {
                    interfaceC2641noA3 = a(c2409kl, b4S, interfaceC2641noA3, (AbstractC2412ko) it3.next(), abstractC2412koJ);
                }
            }
        }
        if (!d && !c2409kl.b(this.a)) {
            throw new AssertionError();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(C2409kl c2409kl, AbstractC2412ko abstractC2412ko) {
        if (abstractC2412ko.b().C() || !abstractC2412ko.B1()) {
            return false;
        }
        C0978Fc c0978FcE = abstractC2412ko.E();
        S10 s10B = abstractC2412ko.b();
        for (AbstractC2412ko abstractC2412ko2 : s10B.h0()) {
            C0978Fc c0978FcA = C0978Fc.a(c2409kl, c0978FcE);
            c0978FcA.b(abstractC2412ko2.Y0());
            A4 a4A = abstractC2412ko2.h().a(c2409kl, abstractC2412ko2);
            a4A.previous();
            a4A.add(c0978FcA);
            abstractC2412ko2.b(s10B, c0978FcA.b());
        }
        s10B.d();
        return true;
    }

    private InterfaceC2641no a(C2409kl c2409kl, B4 b4, InterfaceC2641no interfaceC2641no, AbstractC2412ko abstractC2412ko, AbstractC2412ko abstractC2412ko2) {
        AbstractC1915eM abstractC1915eMY0;
        AbstractC2412ko abstractC2412koPrevious = interfaceC2641no.previous();
        boolean z = d;
        if (!z && abstractC2412koPrevious != abstractC2412ko2) {
            throw new AssertionError();
        }
        if (abstractC2412ko.g() && !abstractC2412ko2.Y0().s()) {
            abstractC1915eMY0 = AbstractC1915eM.v();
        } else {
            abstractC1915eMY0 = abstractC2412ko2.Y0();
        }
        abstractC2412ko.b(abstractC1915eMY0);
        if (abstractC2412ko.g() && abstractC2412ko2.h().D()) {
            final C3332x4 c3332x4A = interfaceC2641no.a(c2409kl, b4, this.a.Y());
            C3332x4 c3332x4 = (C3332x4) L1.b((ListIterator) b4, new Predicate() { // from class: com.android.tools.r8.ir.optimize.F$$ExternalSyntheticLambda2
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return F.a(c3332x4A, (C3332x4) obj);
                }
            });
            if (!z && c3332x4 != c3332x4A) {
                throw new AssertionError();
            }
            b4.next();
            if (!z && interfaceC2641no.hasNext()) {
                throw new AssertionError();
            }
            interfaceC2641no.previous();
            interfaceC2641no.add(abstractC2412ko);
            interfaceC2641no = c3332x4A.a(c2409kl);
        } else {
            interfaceC2641no.add(abstractC2412ko);
        }
        AbstractC2412ko next = interfaceC2641no.next();
        if (z || next == abstractC2412ko2) {
            return interfaceC2641no;
        }
        throw new AssertionError();
    }

    /* JADX WARN: Code duplicated, block: B:105:0x0180 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:116:0x0112 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:41:0x00c0 A[LOOP:1: B:39:0x00ba->B:41:0x00c0, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:45:0x00e0  */
    /* JADX WARN: Code duplicated, block: B:48:0x00f5  */
    /* JADX WARN: Code duplicated, block: B:50:0x0101  */
    /* JADX WARN: Code duplicated, block: B:65:0x014e  */
    /* JADX WARN: Code duplicated, block: B:67:0x0152  */
    /* JADX WARN: Code duplicated, block: B:69:0x0156  */
    /* JADX WARN: Code duplicated, block: B:71:0x015a  */
    /* JADX WARN: Code duplicated, block: B:73:0x015e  */
    /* JADX WARN: Code duplicated, block: B:75:0x0162  */
    /* JADX WARN: Code duplicated, block: B:78:0x0186  */
    /* JADX WARN: Code duplicated, block: B:79:0x018f  */
    /* JADX WARN: Code duplicated, block: B:80:0x0198  */
    /* JADX WARN: Code duplicated, block: B:81:0x01a1  */
    /* JADX WARN: Code duplicated, block: B:82:0x01bf  */
    /* JADX WARN: Code duplicated, block: B:85:0x01ff  */
    private void a(C2409kl c2409kl, C3332x4 c3332x4, P1 p1, IdentityHashMap identityHashMap, Predicate predicate) {
        LinkedHashSet linkedHashSet;
        Iterator<AbstractC2412ko> it;
        final C3332x4 c3332x4A;
        int iG2;
        AbstractC2412ko abstractC2412koA;
        Iterator<S10> it2;
        int i;
        InterfaceC2641no interfaceC2641noA = c3332x4.a(c2409kl);
        boolean z = false;
        while (interfaceC2641noA.hasNext()) {
            AbstractC2412ko next = interfaceC2641noA.next();
            if (this.c.h()) {
                next.getClass();
                if (next instanceof C2318jb) {
                    z = true;
                }
            }
            if (!next.f1() && !next.b().A()) {
                if (predicate.test(next)) {
                    if (next.b().D() && !next.b().C()) {
                        AbstractC2412ko abstractC2412koF0 = next.b().f0();
                        AbstractC2412ko next2 = interfaceC2641noA.next();
                        if (abstractC2412koF0 == next2) {
                            interfaceC2641noA.previous();
                        } else {
                            if (next2.d1() && next2.b().D() && !next2.b().C() && interfaceC2641noA.hasNext()) {
                                AbstractC2412ko abstractC2412koO = interfaceC2641noA.o();
                                AbstractC2412ko abstractC2412koF1 = next2.b().f0();
                                if (abstractC2412koF0 == abstractC2412koO && abstractC2412koF1 == abstractC2412koO) {
                                    interfaceC2641noA.previous();
                                }
                            }
                            interfaceC2641noA.previous();
                            interfaceC2641noA.previous();
                            interfaceC2641noA.next();
                            linkedHashSet = new LinkedHashSet();
                            it = next.b().h0().iterator();
                            while (it.hasNext()) {
                                linkedHashSet.add(it.next().h());
                            }
                            for (ML ml : next.b().g0()) {
                                it2 = ml.k0().iterator();
                                i = 0;
                                while (it2.hasNext()) {
                                    if (it2.next() == next.b()) {
                                        linkedHashSet.add(ml.h().y().get(i));
                                    }
                                    i++;
                                }
                            }
                            c3332x4A = ((C0827Af) p1.d()).a(linkedHashSet);
                            if (next.g()) {
                                iG2 = next.G2();
                                if (iG2 == 12) {
                                    C3446yc c3446ycA = next.A();
                                    int i2 = C3446yc.l;
                                    abstractC2412koA = C3446yc.a(new S10(c2409kl.e.a(), c3446ycA.a(), c3446ycA.j()), c3446ycA);
                                } else if (iG2 == 20) {
                                    C2477lf c2477lfJ = next.J();
                                    int i3 = C2477lf.l;
                                    abstractC2412koA = C2477lf.a(new S10(c2409kl.e.a(), c2477lfJ.a(), c2477lfJ.j()), c2477lfJ);
                                } else if (iG2 == 28) {
                                    abstractC2412koA = C1189Mn.a(c2409kl, next.T());
                                } else if (iG2 == 59) {
                                    abstractC2412koA = QW.a(c2409kl, next.F0());
                                } else if (iG2 == 15) {
                                    abstractC2412koA = C0978Fc.a(c2409kl, next.E());
                                } else if (iG2 == 16) {
                                    C1033Hc c1033HcF = next.F();
                                    int i4 = C1033Hc.k;
                                    abstractC2412koA = C1033Hc.a(new S10(c2409kl.e.a(), c1033HcF.a(), c1033HcF.j()), c1033HcF);
                                } else {
                                    throw new C3405y10();
                                }
                                next.b().g(abstractC2412koA.b());
                                ((LinkedHashMap) identityHashMap.computeIfAbsent(c3332x4A, new Function() { // from class: com.android.tools.r8.ir.optimize.F$$ExternalSyntheticLambda14
                                    @Override // java.util.function.Function
                                    public final Object apply(Object obj) {
                                        return F.b((C3332x4) obj);
                                    }
                                })).put(abstractC2412koA.b(), abstractC2412koA);
                                if (d) {
                                }
                                interfaceC2641noA.s();
                            } else {
                                iG2 = next.G2();
                                if (iG2 == 12) {
                                    C3446yc c3446ycA2 = next.A();
                                    int i5 = C3446yc.l;
                                    abstractC2412koA = C3446yc.a(new S10(c2409kl.e.a(), c3446ycA2.a(), c3446ycA2.j()), c3446ycA2);
                                } else if (iG2 == 20) {
                                    C2477lf c2477lfJ2 = next.J();
                                    int i6 = C2477lf.l;
                                    abstractC2412koA = C2477lf.a(new S10(c2409kl.e.a(), c2477lfJ2.a(), c2477lfJ2.j()), c2477lfJ2);
                                } else if (iG2 == 28) {
                                    abstractC2412koA = C1189Mn.a(c2409kl, next.T());
                                } else if (iG2 == 59) {
                                    abstractC2412koA = QW.a(c2409kl, next.F0());
                                } else if (iG2 == 15) {
                                    abstractC2412koA = C0978Fc.a(c2409kl, next.E());
                                } else if (iG2 == 16) {
                                    C1033Hc c1033HcF2 = next.F();
                                    int i7 = C1033Hc.k;
                                    abstractC2412koA = C1033Hc.a(new S10(c2409kl.e.a(), c1033HcF2.a(), c1033HcF2.j()), c1033HcF2);
                                } else {
                                    throw new C3405y10();
                                }
                                next.b().g(abstractC2412koA.b());
                                ((LinkedHashMap) identityHashMap.computeIfAbsent(c3332x4A, new Function() { // from class: com.android.tools.r8.ir.optimize.F$$ExternalSyntheticLambda14
                                    @Override // java.util.function.Function
                                    public final Object apply(Object obj) {
                                        return F.b((C3332x4) obj);
                                    }
                                })).put(abstractC2412koA.b(), abstractC2412koA);
                                if (d) {
                                }
                                interfaceC2641noA.s();
                            }
                        }
                    } else {
                        linkedHashSet = new LinkedHashSet();
                        it = next.b().h0().iterator();
                        while (it.hasNext()) {
                            linkedHashSet.add(it.next().h());
                        }
                        while (r9.hasNext()) {
                            it2 = ml.k0().iterator();
                            i = 0;
                            while (it2.hasNext()) {
                                if (it2.next() == next.b()) {
                                    linkedHashSet.add(ml.h().y().get(i));
                                }
                                i++;
                            }
                        }
                        c3332x4A = ((C0827Af) p1.d()).a(linkedHashSet);
                        if ((next.g() || (!c3332x4.D() && !c3332x4A.D())) && (!z || !AbstractC2734ot.b(next.h1(), new InterfaceC3205vM() { // from class: com.android.tools.r8.ir.optimize.F$$ExternalSyntheticLambda13
                            @Override // com.android.tools.r8.internal.InterfaceC3205vM
                            public final boolean apply(Object obj) {
                                return F.a(c3332x4A, (S10) obj);
                            }
                        }))) {
                            iG2 = next.G2();
                            if (iG2 == 12) {
                                C3446yc c3446ycA3 = next.A();
                                int i8 = C3446yc.l;
                                abstractC2412koA = C3446yc.a(new S10(c2409kl.e.a(), c3446ycA3.a(), c3446ycA3.j()), c3446ycA3);
                            } else if (iG2 == 20) {
                                C2477lf c2477lfJ3 = next.J();
                                int i9 = C2477lf.l;
                                abstractC2412koA = C2477lf.a(new S10(c2409kl.e.a(), c2477lfJ3.a(), c2477lfJ3.j()), c2477lfJ3);
                            } else if (iG2 == 28) {
                                abstractC2412koA = C1189Mn.a(c2409kl, next.T());
                            } else if (iG2 == 59) {
                                abstractC2412koA = QW.a(c2409kl, next.F0());
                            } else if (iG2 == 15) {
                                abstractC2412koA = C0978Fc.a(c2409kl, next.E());
                            } else if (iG2 == 16) {
                                C1033Hc c1033HcF3 = next.F();
                                int i10 = C1033Hc.k;
                                abstractC2412koA = C1033Hc.a(new S10(c2409kl.e.a(), c1033HcF3.a(), c1033HcF3.j()), c1033HcF3);
                            } else {
                                throw new C3405y10();
                            }
                            next.b().g(abstractC2412koA.b());
                            ((LinkedHashMap) identityHashMap.computeIfAbsent(c3332x4A, new Function() { // from class: com.android.tools.r8.ir.optimize.F$$ExternalSyntheticLambda14
                                @Override // java.util.function.Function
                                public final Object apply(Object obj) {
                                    return F.b((C3332x4) obj);
                                }
                            })).put(abstractC2412koA.b(), abstractC2412koA);
                            if (d && interfaceC2641noA.j() != next) {
                                throw new AssertionError();
                            }
                            interfaceC2641noA.s();
                        }
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(C3332x4 c3332x4, S10 s10) {
        return s10.h() == c3332x4;
    }

    public final void a(C2409kl c2409kl) {
        boolean zEquals;
        int iA = c2409kl.A();
        loop0: for (C3332x4 c3332x4 : c2409kl.d) {
            for (AbstractC2412ko abstractC2412ko : c3332x4.p()) {
                if (abstractC2412ko.v1() || abstractC2412ko.C2() || (abstractC2412ko instanceof C1641ao) || abstractC2412ko.w1()) {
                    if (abstractC2412ko.j() == null && !abstractC2412ko.b1()) {
                        C1582a3 c1582a3J = C1582a3.j();
                        C3593x c3593x = new C3593x(this.c);
                        C0827Af c0827Af = new C0827Af(c2409kl, 1);
                        for (int i = 0; i < c0827Af.b().length; i++) {
                            C3332x4 c3332x5 = c0827Af.b()[i];
                            if (!c3332x5.c(iA)) {
                                InterfaceC2641no interfaceC2641noA = c3332x5.a(c2409kl);
                                while (interfaceC2641noA.hasNext()) {
                                    AbstractC2412ko next = interfaceC2641noA.next();
                                    if (next.v1() || next.C2() || (next instanceof C1641ao) || next.w1()) {
                                        if (next.j() == null && !next.b1()) {
                                            List listA = c1582a3J.get(c3593x.c(next));
                                            if (listA.size() > 0) {
                                                Iterator it = listA.iterator();
                                                while (true) {
                                                    if (it.hasNext()) {
                                                        S10 s10 = (S10) it.next();
                                                        if (c0827Af.a(c3332x5, s10.b.h())) {
                                                            AbstractC2412ko abstractC2412ko2 = s10.b;
                                                            if (!next.g()) {
                                                                if (!d && abstractC2412ko2.g()) {
                                                                    throw new AssertionError();
                                                                }
                                                                zEquals = true;
                                                            } else {
                                                                if (!d && !abstractC2412ko2.g()) {
                                                                    throw new AssertionError();
                                                                }
                                                                zEquals = next.h().n().equals(abstractC2412ko2.h().n());
                                                            }
                                                            if (zEquals) {
                                                                next.b().g(s10);
                                                                s10.g0().forEach(new uX$$ExternalSyntheticLambda0());
                                                                interfaceC2641noA.s();
                                                                break;
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            c1582a3J.a(c3593x.c(next), next.b());
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                        break loop0;
                    }
                }
            }
            c3332x4.e(iA);
        }
        c2409kl.b(iA);
        if (!d && !c2409kl.b(this.a)) {
            throw new AssertionError();
        }
    }

    private boolean a(C2409kl c2409kl, C3332x4 c3332x4, C1015Gl c1015Gl) {
        S10 s10O2 = c1015Gl.O2();
        S10 s10M = s10O2.m();
        S10 s10P2 = c1015Gl.P2();
        S10 s10M2 = s10P2.m();
        if (s10M == s10M2) {
            C3332x4 c3332x4C = c1015Gl.c(0);
            a(c2409kl, c3332x4, c1015Gl, c3332x4C, c3332x4C == c1015Gl.K2() ? c1015Gl.J2() : c1015Gl.K2());
            return true;
        }
        if (s10M.b(new Predicate() { // from class: com.android.tools.r8.ir.optimize.F$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((AbstractC2412ko) obj).E1();
            }
        }) && s10M2.b(new Predicate() { // from class: com.android.tools.r8.ir.optimize.F$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((AbstractC2412ko) obj).E1();
            }
        })) {
            if (!d && c1015Gl.L2() != EnumC1076Il.EQ && c1015Gl.L2() != EnumC1076Il.NE) {
                throw new AssertionError();
            }
            C3332x4 c3332x4C2 = c1015Gl.c(1);
            a(c2409kl, c3332x4, c1015Gl, c3332x4C2, c3332x4C2 == c1015Gl.K2() ? c1015Gl.J2() : c1015Gl.K2());
            return true;
        }
        if (s10M.J() && s10M2.J()) {
            C3332x4 c3332x4A = c1015Gl.a(s10M.n().E(), s10M2.n().E());
            a(c2409kl, c3332x4, c1015Gl, c3332x4A, c3332x4A == c1015Gl.K2() ? c1015Gl.J2() : c1015Gl.K2());
            return true;
        }
        EnumC1076Il enumC1076IlL2 = c1015Gl.L2();
        EnumC1076Il enumC1076Il = EnumC1076Il.EQ;
        if (enumC1076IlL2 == enumC1076Il || c1015Gl.L2() == EnumC1076Il.NE) {
            B1 b1A = s10O2.a(this.a, c2409kl.h());
            B1 b1A2 = s10P2.a(this.a, c2409kl.h());
            if (b1A.w() && b1A2.w() && !b1A.k().a(b1A2.k())) {
                a(c2409kl, c3332x4, c1015Gl, 1);
                return true;
            }
        }
        if (s10O2.G() && s10P2.G()) {
            W1 w1X = s10O2.x();
            W1 w1X2 = s10P2.x();
            if (!w1X.a(w1X2)) {
                a(c2409kl, c3332x4, c1015Gl, Long.signum(w1X.b() - w1X2.b()));
                return true;
            }
            int i = AbstractC3592w.a[c1015Gl.L2().ordinal()];
            if (i == 1 || i == 2) {
                if (w1X.b() == w1X2.a()) {
                    a(c2409kl, c3332x4, c1015Gl, 0);
                    return true;
                }
            } else if ((i == 3 || i == 4) && w1X.a() == w1X2.b()) {
                a(c2409kl, c3332x4, c1015Gl, 0);
                return true;
            }
        }
        if (c1015Gl.L2() == enumC1076Il || c1015Gl.L2() == EnumC1076Il.NE) {
            o5 o5VarH = c2409kl.h();
            B1 b1A3 = s10O2.a(this.a, o5VarH);
            b1A3.getClass();
            if (b1A3 instanceof C2388kV) {
                B1 b1A4 = s10P2.a(this.a, o5VarH);
                b1A4.getClass();
                if (!(b1A4 instanceof C2388kV)) {
                    return false;
                }
                a(c2409kl, c3332x4, c1015Gl, com.android.tools.r8.utils.M.a(b1A3.m().F() != b1A4.m().F()));
                return true;
            }
            if (b1A3.B()) {
                B1 b1A5 = s10P2.a(this.a, o5VarH);
                if (b1A5.B()) {
                    AbstractC2706oV abstractC2706oVP = b1A3.p();
                    AbstractC2706oV abstractC2706oVP2 = b1A5.p();
                    if (abstractC2706oVP.F() == abstractC2706oVP2.F()) {
                        a(c2409kl, c3332x4, c1015Gl, 0);
                        return true;
                    }
                    C0702m c0702m = this.a;
                    Y0 y0F = abstractC2706oVP.F();
                    c0702m.getClass();
                    com.android.tools.r8.graph.T0 t0C = abstractC2706oVP.F().c(c0702m.c(y0F.e));
                    if (t0C != null && t0C.f.R()) {
                        C0702m c0702m2 = this.a;
                        Y0 y0F2 = abstractC2706oVP2.F();
                        c0702m2.getClass();
                        com.android.tools.r8.graph.T0 t0C2 = abstractC2706oVP2.F().c(c0702m2.c(y0F2.e));
                        if (t0C2 != null && t0C2.f.R()) {
                            a(c2409kl, c3332x4, c1015Gl, 1);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private static void a(C2409kl c2409kl, C3332x4 c3332x4, C1015Gl c1015Gl, int i) {
        C3332x4 c3332x4C = c1015Gl.c(i);
        a(c2409kl, c3332x4, c1015Gl, c3332x4C, c3332x4C == c1015Gl.K2() ? c1015Gl.J2() : c1015Gl.K2());
    }

    private boolean a(long j, S10 s10, C3332x4 c3332x4, P1 p1, C2409kl c2409kl, P1 p2) {
        boolean z = false;
        if (s10.A()) {
            return false;
        }
        QC qc = (QC) ((com.android.tools.r8.internal.Y) p1.d());
        List list = (List) qc.c(j);
        if (list == null || list.isEmpty()) {
            return false;
        }
        ListIterator listIterator = list.listIterator();
        while (listIterator.hasNext()) {
            C0978Fc c0978Fc = (C0978Fc) listIterator.next();
            S10 s10B = c0978Fc.b();
            boolean z2 = d;
            if (!z2 && s10B.A()) {
                throw new AssertionError();
            }
            if (!z2 && c0978Fc.P2() != j) {
                throw new AssertionError();
            }
            C3332x4 c3332x4H = c0978Fc.h();
            if (c3332x4H != c3332x4 || c3332x4H.y().size() == 1) {
                if ((!s10B.b((Set) null) || s10.b((Set) null)) && ((C0827Af) p2.d()).a(c3332x4H, c3332x4)) {
                    if (s10.w().a(s10B.w(), this.a)) {
                        s10B.g(s10);
                        c3332x4H.a(c2409kl, c0978Fc).s();
                        listIterator.remove();
                        z = true;
                    } else {
                        s10B.w().getClass();
                    }
                }
            } else if (!z2) {
                throw new AssertionError();
            }
        }
        if (list.isEmpty()) {
            qc.a(j);
        }
        return z;
    }

    private static boolean a(C2409kl c2409kl, C3332x4 c3332x4) {
        ML next;
        InterfaceC2641no interfaceC2641noA = c3332x4.a(c2409kl);
        AbstractC2412ko next2 = interfaceC2641noA.next();
        next2.getClass();
        if (!(next2 instanceof AF)) {
            return false;
        }
        S10 s10B = next2.b();
        if (s10B.z() || (!s10B.E() ? s10B.a0() == 1 : !s10B.C() && s10B.D() && s10B.f0().B2())) {
            return false;
        }
        while (interfaceC2641noA.hasNext()) {
            AbstractC2412ko next3 = interfaceC2641noA.next();
            C3332x4 c3332x4H = next3.h();
            if (next3.M1()) {
                C3332x4 c3332x4K2 = next3.P().K2();
                int iIndexOf = c3332x4K2.y().indexOf(c3332x4H);
                Iterator<ML> it = c3332x4K2.x().iterator();
                do {
                    if (!it.hasNext()) {
                        next = null;
                        break;
                    }
                    next = it.next();
                } while (s10B != next.c(iIndexOf));
                if (next != null) {
                    if (!next.z()) {
                        if (next.E()) {
                            if (next.C() || !next.D() || !next.f0().B2()) {
                            }
                            s10B = next;
                        } else {
                            if (next.a0() != 1) {
                            }
                            s10B = next;
                        }
                    }
                    return false;
                }
                interfaceC2641noA = c3332x4K2.a(c2409kl);
            } else {
                if (!next3.B2()) {
                    return false;
                }
                List listH1 = next3.h1();
                if (!d && ((ArrayList) listH1).size() != 1) {
                    throw new AssertionError();
                }
                if (((ArrayList) listH1).get(0) != s10B) {
                    return false;
                }
                C3038t6 c3038t6N = c3332x4H.n();
                if (!c3038t6N.isEmpty()) {
                    Iterator it2 = c3038t6N.iterator();
                    while (it2.hasNext()) {
                        if (!a(c2409kl, (C3332x4) ((C2969s6) it2.next()).b)) {
                            return false;
                        }
                    }
                }
                return true;
            }
        }
        throw new C3405y10("Triviality check should always return before the loop terminates");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(final C0702m c0702m, final Set set, C3332x4 c3332x4) {
        c3332x4.x().forEach(new Consumer() { // from class: com.android.tools.r8.ir.optimize.F$$ExternalSyntheticLambda12
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                F.a(c0702m, set, (ML) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(C0702m c0702m, Set set, ML ml) {
        if (ml.w().N().f()) {
            return;
        }
        AbstractC3472z00 abstractC3472z00A = ml.a(c0702m);
        if (abstractC3472z00A.N().f()) {
            set.add(ml);
            ml.a(abstractC3472z00A);
        }
    }

    private static boolean a(C3332x4 c3332x4) {
        if (c3332x4.Q()) {
            return true;
        }
        int size = c3332x4.p().size();
        if (c3332x4.m().M1() && (size == 2 || size == 3)) {
            AbstractC2412ko abstractC2412ko = c3332x4.p().get(size - 2);
            if (abstractC2412ko.B1()) {
                if (!abstractC2412ko.E().S2()) {
                    C0978Fc c0978FcE = abstractC2412ko.E();
                    if (c0978FcE.H2() != EnumC1812d20.INT || c0978FcE.N2() != 0) {
                        return false;
                    }
                }
                if (size == 2) {
                    return true;
                }
                AbstractC2412ko first = c3332x4.p().getFirst();
                first.getClass();
                if (first instanceof C1343Sd) {
                    if (!d && c3332x4.y().size() != 1) {
                        throw new AssertionError();
                    }
                    C3332x4 c3332x5 = c3332x4.y().get(0);
                    C3479z4 c3479z4D = c3332x5.d(c3332x5.m());
                    AbstractC2412ko abstractC2412koPrevious = null;
                    while (c3479z4D.hasPrevious()) {
                        abstractC2412koPrevious = c3479z4D.previous();
                        abstractC2412koPrevious.getClass();
                        if (abstractC2412koPrevious instanceof C1343Sd) {
                            break;
                        }
                    }
                    return abstractC2412koPrevious != null && abstractC2412koPrevious.Y0() == first.Y0();
                }
            }
        }
        return false;
    }

    private static void a(C2409kl c2409kl, C3332x4 c3332x4, C1015Gl c1015Gl, C3332x4 c3332x5, C3332x4 c3332x6) {
        c3332x6.e0();
        boolean z = d;
        if (!z && c1015Gl != c3332x4.m()) {
            throw new AssertionError();
        }
        c3332x4.a(c2409kl, (AbstractC1954eu) new C1296Qj());
        if (!z && !c3332x4.m().M1()) {
            throw new AssertionError();
        }
        if (!z && AbstractC2967s4.a(c3332x4) != c3332x5) {
            throw new AssertionError();
        }
    }

    public static void a(C2409kl c2409kl, com.android.tools.r8.ir.regalloc.b bVar) {
        InterfaceC0992Fq interfaceC0992FqQ;
        if (bVar.b().W0) {
            for (C3332x4 c3332x4 : c2409kl.d) {
                if (c3332x4.z().isEmpty() && (interfaceC0992FqQ = c3332x4.q()) != null && !interfaceC0992FqQ.isEmpty()) {
                    C1316Rd c1316RdH = null;
                    for (AbstractC2412ko abstractC2412ko : c3332x4.p()) {
                        if (abstractC2412ko.W0() != -1 || c1316RdH != null) {
                            break;
                        } else {
                            c1316RdH = abstractC2412ko.H();
                        }
                    }
                    if (c1316RdH != null) {
                        C1303Qq c1303Qq = new C1303Qq(interfaceC0992FqQ.size());
                        c1303Qq.putAll(interfaceC0992FqQ);
                        if (c1316RdH.a(c1303Qq)) {
                            Set setC = OU.c();
                            Lr lr = new Lr(16);
                            Lr lr2 = new Lr(16);
                            C3479z4 c3479z4D = c3332x4.d(c3332x4.p().size());
                            boolean z = false;
                            while (c3479z4D.hasPrevious()) {
                                AbstractC2412ko abstractC2412koPrevious = c3479z4D.previous();
                                if (abstractC2412koPrevious == c1316RdH) {
                                    z = true;
                                }
                                if (z && abstractC2412koPrevious.l2()) {
                                    C3492zF c3492zFN0 = abstractC2412koPrevious.n0();
                                    int iA = bVar.a(c3492zFN0.J2(), c3492zFN0.W0());
                                    int iA2 = bVar.a(c3492zFN0.K2(), c3492zFN0.W0());
                                    if (!lr.f(iA) && !lr2.f(iA2)) {
                                        setC.add(c3492zFN0);
                                    }
                                }
                                if (abstractC2412koPrevious.b() != null && abstractC2412koPrevious.b().Y()) {
                                    int iA3 = bVar.a(abstractC2412koPrevious.b(), abstractC2412koPrevious.W0());
                                    lr.k(iA3);
                                    lr2.a(iA3);
                                }
                                if (!((ArrayList) abstractC2412koPrevious.h1()).isEmpty()) {
                                    for (S10 s10 : (ArrayList) abstractC2412koPrevious.h1()) {
                                        if (s10.Y()) {
                                            lr.a(bVar.a(s10, abstractC2412koPrevious.W0()));
                                        }
                                    }
                                }
                            }
                            if (!setC.isEmpty()) {
                                C1301Qo c1301Qo = new C1301Qo(16);
                                C1301Qo c1301Qo2 = new C1301Qo(16);
                                InterfaceC2641no interfaceC2641noA = c3332x4.a(c2409kl);
                                while (interfaceC2641noA.hasNext()) {
                                    AbstractC2412ko next = interfaceC2641noA.next();
                                    if (next.l2()) {
                                        C3492zF c3492zFN1 = next.n0();
                                        if (setC.contains(c3492zFN1)) {
                                            int iA4 = bVar.a(c3492zFN1.J2(), c3492zFN1.W0());
                                            int iA5 = bVar.a(c3492zFN1.K2(), c3492zFN1.W0());
                                            c1301Qo2.b(iA4, ((Integer) c1301Qo2.getOrDefault(Integer.valueOf(iA5), Integer.valueOf(iA5))).intValue());
                                            interfaceC2641noA.v();
                                        }
                                    } else if (next instanceof C1316Rd) {
                                        C1316Rd c1316RdH2 = next.H();
                                        InterfaceC0992Fq interfaceC0992FqJ2 = c1316RdH2.J2();
                                        if (!c1301Qo.isEmpty()) {
                                            C1303Qq c1303Qq2 = new C1303Qq(interfaceC0992FqJ2.size());
                                            c1303Qq2.putAll(interfaceC0992FqJ2);
                                            interfaceC0992FqJ2.clear();
                                            InterfaceC1912eK it = c1303Qq2.c().iterator();
                                            while (it.hasNext()) {
                                                InterfaceC0934Dq interfaceC0934Dq = (InterfaceC0934Dq) it.next();
                                                int iA6 = interfaceC0934Dq.a();
                                                interfaceC0992FqJ2.a(((Integer) c1301Qo.getOrDefault(Integer.valueOf(iA6), Integer.valueOf(iA6))).intValue(), (com.android.tools.r8.graph.X) interfaceC0934Dq.getValue());
                                            }
                                        }
                                        InterfaceC0992Fq interfaceC0992FqK2 = c1316RdH2.K2();
                                        if (!c1301Qo2.isEmpty()) {
                                            C1303Qq c1303Qq3 = new C1303Qq(interfaceC0992FqK2.size());
                                            c1303Qq3.putAll(interfaceC0992FqK2);
                                            interfaceC0992FqK2.clear();
                                            InterfaceC1912eK it2 = c1303Qq3.c().iterator();
                                            while (it2.hasNext()) {
                                                InterfaceC0934Dq interfaceC0934Dq2 = (InterfaceC0934Dq) it2.next();
                                                int iA7 = interfaceC0934Dq2.a();
                                                interfaceC0992FqK2.a(((Integer) c1301Qo2.getOrDefault(Integer.valueOf(iA7), Integer.valueOf(iA7))).intValue(), (com.android.tools.r8.graph.X) interfaceC0934Dq2.getValue());
                                            }
                                        }
                                        C1301Qo c1301Qo3 = c1301Qo2;
                                        c1301Qo2 = new C1301Qo(c1301Qo2);
                                        c1301Qo = c1301Qo3;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private S10 a(C2409kl c2409kl, InterfaceC2641no interfaceC2641no, String str) {
        S10 s10A = c2409kl.a((AbstractC3472z00) AbstractC3472z00.b((C0702m<?>) this.a, WH.b()));
        interfaceC2641no.add(new C1033Hc(s10A, this.b.c(str)));
        return s10A;
    }

    public final void a(W0 w0, C2409kl c2409kl) {
        S10 s10;
        C0684j2 c0684j2;
        S10 s11;
        List<S10> listC = c2409kl.c();
        C3332x4 c3332x4J = c2409kl.j();
        InterfaceC2641no interfaceC2641noA = c3332x4J.a(c2409kl);
        AbstractC1915eM.c cVarA = AbstractC1915eM.c.x().a(1).a(w0.getReference()).a();
        interfaceC2641noA.a(cVarA);
        interfaceC2641noA.a(new Predicate() { // from class: com.android.tools.r8.ir.optimize.F$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return F.b((AbstractC2412ko) obj);
            }
        });
        interfaceC2641noA.previous();
        interfaceC2641noA.a(c2409kl);
        interfaceC2641noA.previous();
        if (!d && c3332x4J.D()) {
            throw new AssertionError();
        }
        C0722p1 c0722p1 = this.b;
        C0767v2 c0767v2 = c0722p1.z2;
        C0767v2 c0767v3 = c0722p1.A2;
        S10 s10A = c2409kl.a(AbstractC3472z00.a(c0767v3, WH.b(), (C0702m<?>) this.a));
        C0722p1 c0722p2 = this.b;
        C0737r2 c0737r2B = c0722p2.b(c0722p2.D1, c0722p2.Z1);
        C0684j2 c0684j2A = this.b.a(c0767v3, c0737r2B, "print");
        C0684j2 c0684j2A2 = this.b.a(c0767v3, c0737r2B, "println");
        interfaceC2641noA.add(new QW(this.b.a(c0767v2, c0767v3, "out"), s10A));
        interfaceC2641noA.add(new C2115gt(c0684j2A, null, AbstractC1639am.a(s10A, a(c2409kl, interfaceC2641noA, "INVOKE "))));
        interfaceC2641noA.add(new C2115gt(c0684j2A, null, AbstractC1639am.a(s10A, a(c2409kl, interfaceC2641noA, w0.getReference().K0()))));
        S10 s10A2 = a(c2409kl, interfaceC2641noA, "(");
        S10 s10A3 = a(c2409kl, interfaceC2641noA, ",");
        S10 s10A4 = a(c2409kl, interfaceC2641noA, Constant.AFTER_QUTO);
        S10 s10A5 = a(c2409kl, interfaceC2641noA, "  ");
        S10 s10A6 = a(c2409kl, interfaceC2641noA, "(null)");
        S10 s10A7 = a(c2409kl, interfaceC2641noA, "(primitive)");
        S10 s10A8 = a(c2409kl, interfaceC2641noA, "");
        interfaceC2641noA.add(new C2115gt(c0684j2A2, null, AbstractC1639am.a(s10A, s10A2)));
        C3332x4 c3332x4 = c3332x4J;
        int i = 0;
        while (i < listC.size()) {
            S10 s12 = s10A3;
            S10 s13 = s10A5;
            interfaceC2641noA.add(new C2115gt(c0684j2A, null, AbstractC1639am.a(s10A, s10A5)));
            C3332x4 c3332x4A = C3332x4.a(c2409kl.f.a(), cVarA, c2409kl.t());
            c2409kl.d.add(c3332x4A);
            C3332x4 c3332x4F0 = c3332x4.f0();
            c3332x4.h(c3332x4A);
            c3332x4A.h(c3332x4F0);
            S10 s14 = listC.get(i);
            if (!s14.w().I()) {
                interfaceC2641noA.add(new C2115gt(c0684j2A, null, AbstractC1639am.a(s10A, s10A7)));
                s10 = null;
            } else {
                C3332x4 c3332x4F1 = c3332x4.f0();
                C1015Gl c1015Gl = new C1015Gl(EnumC1076Il.NE, s14);
                c1015Gl.b(cVarA);
                int iA = c2409kl.f.a();
                C3155ul c3155ulT = c2409kl.t();
                C3332x4 c3332x5 = new C3332x4();
                c3332x5.a(c1015Gl, c3155ulT);
                c3332x5.a((C2102gl) null);
                c3332x5.g(iA);
                c2409kl.d.add(c3332x5);
                C3332x4 c3332x4A2 = C3332x4.a(c2409kl.f.a(), cVarA, c2409kl.t());
                c2409kl.d.add(c3332x4A2);
                C3332x4 c3332x4A3 = C3332x4.a(c2409kl.f.a(), cVarA, c2409kl.t());
                c2409kl.d.add(c3332x4A3);
                c3332x4.h(c3332x5);
                c3332x5.h(c3332x4A3);
                c3332x5.h(c3332x4A2);
                c3332x4A3.h(c3332x4F1);
                c3332x4A2.h(c3332x4F1);
                InterfaceC2641no interfaceC2641noA2 = c3332x4A2.a(c2409kl);
                interfaceC2641noA2.a(cVarA);
                interfaceC2641noA2.add(new C2115gt(c0684j2A, null, AbstractC1639am.a(s10A, s10A6)));
                InterfaceC2641no interfaceC2641noA3 = c3332x4A3.a(c2409kl);
                interfaceC2641noA3.a(cVarA);
                C0702m c0702m = this.a;
                S10 s10A9 = c2409kl.a((AbstractC3472z00) AbstractC3472z00.a(c0702m.a().m2, WH.b(), (C0702m<?>) c0702m).b());
                interfaceC2641noA3.add(new C2115gt(this.b.g4.e, s10A9, AbstractC1639am.c(listC.get(i))));
                s10 = null;
                interfaceC2641noA3.add(new C2115gt(c0684j2A, null, AbstractC1639am.a(s10A, s10A9)));
            }
            interfaceC2641noA = c3332x4A.a(c2409kl);
            interfaceC2641noA.a(cVarA);
            if (i == listC.size() - 1) {
                c0684j2 = c0684j2A2;
                interfaceC2641noA.add(new C2115gt(c0684j2, s10, AbstractC1639am.a(s10A, s10A4)));
                s11 = s12;
            } else {
                c0684j2 = c0684j2A2;
                s11 = s12;
                interfaceC2641noA.add(new C2115gt(c0684j2, s10, AbstractC1639am.a(s10A, s11)));
            }
            i++;
            c3332x4 = c3332x4A;
            s10A3 = s11;
            c0684j2A2 = c0684j2;
            s10A5 = s13;
            s10A7 = s10A7;
        }
        interfaceC2641noA.add(new C2115gt(c0684j2A2, null, AbstractC1639am.a(s10A, s10A8)));
    }

    public static void a(M10 m10) {
        b(m10.b());
        Iterator it = m10.b().g0().iterator();
        while (it.hasNext()) {
            ((ML) it.next()).o0();
        }
        if (!d && m10.b().C()) {
            throw new AssertionError();
        }
    }

    public static void a(C2409kl c2409kl, C0722p1 c0722p1) {
        for (AbstractC2412ko abstractC2412ko : c2409kl.r()) {
            if (abstractC2412ko.W1()) {
                C1166Ls c1166LsZ = abstractC2412ko.Z();
                C0684j2 c0684j2S2 = c1166LsZ.S2();
                if (c0722p1.b(c0684j2S2) && c0684j2S2.e == c0722p1.X1 && c1166LsZ.U2().i()) {
                    ML mlL = c1166LsZ.U2().l();
                    Set setC = OU.c();
                    Set setC2 = OU.c();
                    a(mlL, setC, setC2);
                    if (setC2.size() == 1) {
                        S10 s10 = (S10) setC2.iterator().next();
                        AbstractC2412ko abstractC2412ko2 = s10.b;
                        if (abstractC2412ko2 != null && abstractC2412ko2.p2()) {
                            DG dgT0 = s10.b.t0();
                            b(dgT0.b());
                            if (!c1166LsZ.U2().i()) {
                                dgT0.L2();
                            } else {
                                throw new C1367Tb("Failed to remove trivial phis between new-instance and <init>");
                            }
                        } else {
                            throw new C1367Tb("Invalid defining value for call to <init>");
                        }
                    } else {
                        throw new C1367Tb("Failed to identify unique new-instance for <init>");
                    }
                }
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:51:0x00c4  */
    public static void a(C0702m c0702m, o5 o5Var, C2409kl c2409kl) {
        C0684j2 c0684j2;
        C0722p1 c0722p1A = c0702m.a();
        C2561ml c2561ml = new C2561ml(c2409kl);
        while (c2561ml.hasNext()) {
            AbstractC2412ko abstractC2412ko = (AbstractC2412ko) c2561ml.next();
            abstractC2412ko.getClass();
            if (abstractC2412ko instanceof C1248Os) {
                C1248Os c1248OsA0 = abstractC2412ko.a0();
                C0684j2 c0684j2S2 = c1248OsA0.S2();
                AbstractC0727q0 abstractC0727q0B = c0702m.b(o5Var.a(), c0684j2S2.B0());
                if (abstractC0727q0B == null || abstractC0727q0B.isInterface()) {
                    C0722p1.b bVar = c0722p1A.g4;
                    bVar.getClass();
                    byte b = c0684j2S2.C0().e[0];
                    if (b != 101) {
                        if (b != 110) {
                            if (b != 116) {
                                if (b != 119) {
                                    if (b != 103) {
                                        if (b == 104 && c0684j2S2.b(bVar.f)) {
                                            c0684j2 = bVar.f;
                                        } else {
                                            c0684j2 = null;
                                        }
                                    } else if (c0684j2S2.b(bVar.e)) {
                                        c0684j2 = bVar.e;
                                    } else {
                                        c0684j2 = null;
                                    }
                                } else if (c0684j2S2.b(bVar.l)) {
                                    c0684j2 = bVar.l;
                                } else if (c0684j2S2.b(bVar.m)) {
                                    c0684j2 = bVar.m;
                                } else if (c0684j2S2.b(bVar.n)) {
                                    c0684j2 = bVar.n;
                                } else {
                                    c0684j2 = null;
                                }
                            } else if (c0684j2S2.b(bVar.i)) {
                                c0684j2 = bVar.i;
                            } else {
                                c0684j2 = null;
                            }
                        } else if (c0684j2S2.b(bVar.j)) {
                            c0684j2 = bVar.j;
                        } else if (c0684j2S2.b(bVar.k)) {
                            c0684j2 = bVar.k;
                        } else {
                            c0684j2 = null;
                        }
                    } else if (c0684j2S2.b(bVar.d)) {
                        c0684j2 = bVar.d;
                    } else {
                        c0684j2 = null;
                    }
                    if (c0684j2 != null && c0702m.b(c0684j2S2) == null) {
                        c2561ml.a(new C2115gt(c0684j2, c1248OsA0.b(), c1248OsA0.J2()), (Set) null);
                    }
                }
            }
        }
    }

    private static void a(ML ml, Set set, Set set2) {
        for (S10 s10 : ml.k0()) {
            if (!s10.i()) {
                set2.add(s10);
            } else {
                ML mlL = s10.l();
                if (set.add(mlL)) {
                    a(mlL, set, set2);
                }
            }
        }
    }
}
