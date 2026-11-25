package com.android.tools.r8.ir.optimize;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.C0800x2;
import com.android.tools.r8.internal.A4;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.AbstractC2412ko;
import com.android.tools.r8.internal.AbstractC3472z00;
import com.android.tools.r8.internal.B4;
import com.android.tools.r8.internal.C1458Vr;
import com.android.tools.r8.internal.C1721bt;
import com.android.tools.r8.internal.C2409kl;
import com.android.tools.r8.internal.C2732or;
import com.android.tools.r8.internal.C2979sG;
import com.android.tools.r8.internal.C3218vY;
import com.android.tools.r8.internal.C3332x4;
import com.android.tools.r8.internal.CF;
import com.android.tools.r8.internal.EnumC2527mI;
import com.android.tools.r8.internal.InterfaceC2641no;
import com.android.tools.r8.internal.M1;
import com.android.tools.r8.internal.O1;
import com.android.tools.r8.internal.S10;
import com.android.tools.r8.utils.A1;
import com.android.tools.r8.utils.P1;
import java.util.Collections;
import java.util.Iterator;
import java.util.function.Predicate;
import java.util.function.Supplier;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class E0 {
    static final /* synthetic */ boolean a = true;

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(AbstractC2412ko abstractC2412ko, AbstractC2412ko abstractC2412ko2) {
        return abstractC2412ko2 == abstractC2412ko;
    }

    public static void b(C2409kl c2409kl, final A1 a1) {
        if (a1.r() && c2409kl.u().C1()) {
            C0800x2 c0800x2 = c2409kl.u().getReference().h.f;
            if (c0800x2.size() == 3) {
                C0767v2[] c0767v2Arr = c0800x2.a;
                C0767v2 c0767v2 = c0767v2Arr[0];
                C0767v2 c0767v3 = a1.a.y1;
                if (c0767v2 == c0767v3 && c0767v2Arr[1] == c0767v3 && c0767v2Arr[2].O0()) {
                    Iterator<C3332x4> it = c2409kl.d.iterator();
                    while (it.hasNext()) {
                        InterfaceC2641no interfaceC2641noA = it.next().a(c2409kl);
                        AbstractC2412ko abstractC2412ko = (AbstractC2412ko) interfaceC2641noA.a(new Predicate() { // from class: com.android.tools.r8.ir.optimize.E0$$ExternalSyntheticLambda4
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                return E0.a(a1, (AbstractC2412ko) obj);
                            }
                        });
                        if (abstractC2412ko != null) {
                            AbstractC2412ko abstractC2412koPrevious = interfaceC2641noA.previous();
                            if (!a && abstractC2412ko != abstractC2412koPrevious) {
                                throw new AssertionError();
                            }
                            S10 s10A = c2409kl.a((AbstractC3472z00) AbstractC3472z00.k());
                            M1 m1 = new M1(s10A);
                            m1.a(abstractC2412ko.h());
                            m1.b(abstractC2412ko.Y0());
                            interfaceC2641noA.add(m1);
                            O1 o1 = new O1(s10A);
                            o1.a(abstractC2412ko.h());
                            o1.b(abstractC2412ko.Y0());
                            interfaceC2641noA.add(o1);
                            return;
                        }
                    }
                }
            }
        }
    }

    public static void c(C2409kl c2409kl, A1 a1) {
        if (a1.x()) {
            C0722p1 c0722p1J0 = a1.j0();
            B4 b4S = c2409kl.s();
            while (b4S.hasNext()) {
                C3332x4 next = b4S.next();
                InterfaceC2641no interfaceC2641noA = next.a(c2409kl);
                while (interfaceC2641noA.hasNext()) {
                    AbstractC2412ko next2 = interfaceC2641noA.next();
                    if (next2.n1() || (next2 instanceof C2979sG)) {
                        for (S10 s10 : next2.h1()) {
                            if (!s10.i() && s10.b.q2() && s10.b.w0().i == EnumC2527mI.DOUBLE) {
                                C1721bt c1721bt = new C1721bt(c0722p1J0.m4.b, null, AbstractC1639am.c(s10));
                                c1721bt.b(next2.Y0());
                                interfaceC2641noA.previous();
                                C3332x4 c3332x4A = next.D() ? interfaceC2641noA.a(c2409kl, b4S) : next;
                                if (c3332x4A != next) {
                                    InterfaceC2641no interfaceC2641noA2 = next.a(c2409kl, next.p().size());
                                    interfaceC2641noA2.previous();
                                    interfaceC2641noA2.add(c1721bt);
                                    interfaceC2641noA = c3332x4A.a(c2409kl);
                                    next = c3332x4A;
                                } else {
                                    interfaceC2641noA.add(c1721bt);
                                }
                                AbstractC2412ko next3 = interfaceC2641noA.next();
                                if (!a && next3 != next2) {
                                    throw new AssertionError();
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static void a(C0702m c0702m, C2409kl c2409kl) {
        if (c0702m.Y().n()) {
            Iterator<C3332x4> it = c2409kl.d.iterator();
            while (it.hasNext()) {
                if (it.next().D()) {
                    return;
                }
            }
            int i = 0;
            AbstractC2412ko abstractC2412ko = null;
            for (AbstractC2412ko abstractC2412ko2 : c2409kl.r()) {
                if (abstractC2412ko2.Y1() && abstractC2412ko2.b0().S2() == c2409kl.u().getReference()) {
                    i++;
                    abstractC2412ko = abstractC2412ko2;
                }
            }
            if (i > 4) {
                if (!a && abstractC2412ko == null) {
                    throw new AssertionError();
                }
                A4 a4A = abstractC2412ko.h().a(c2409kl, abstractC2412ko);
                a4A.previous();
                C3332x4 c3332x4B = a4A.b(c2409kl, 1);
                C0767v2 c0767v2 = c0702m.a().m3;
                C3332x4 c3332x4A = C3332x4.a(c2409kl, abstractC2412ko.Y0(), c0767v2, c0702m);
                c2409kl.d.add(c3332x4A);
                c3332x4B.a(c3332x4A, c0767v2);
            }
        }
    }

    public static void a(C2409kl c2409kl, A1 a1) {
        AbstractC2412ko abstractC2412ko;
        if (a1.o()) {
            final C0722p1 c0722p1 = a1.a;
            P1 p1 = new P1(new Supplier() { // from class: com.android.tools.r8.ir.optimize.E0$$ExternalSyntheticLambda0
                @Override // java.util.function.Supplier
                public final Object get() {
                    return E0.a(c0722p1);
                }
            });
            for (C3332x4 c3332x4 : c2409kl.d) {
                InterfaceC2641no interfaceC2641noA = c3332x4.a(c2409kl);
                final AbstractC2412ko abstractC2412ko2 = (AbstractC2412ko) interfaceC2641noA.a(new Predicate() { // from class: com.android.tools.r8.ir.optimize.E0$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return E0.a((AbstractC2412ko) obj);
                    }
                });
                if (abstractC2412ko2 != null && (abstractC2412ko2 instanceof CF) && abstractC2412ko2.x().J2() == EnumC2527mI.LONG && abstractC2412ko2.b() != null && (abstractC2412ko = (AbstractC2412ko) interfaceC2641noA.a(new Predicate() { // from class: com.android.tools.r8.ir.optimize.E0$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return E0.a((AbstractC2412ko) obj);
                    }
                })) != null && (abstractC2412ko.k1() || (abstractC2412ko instanceof C3218vY))) {
                    if (abstractC2412ko.x().J2() == EnumC2527mI.LONG) {
                        Iterator<C3332x4> it = c3332x4.y().iterator();
                        do {
                            if (!it.hasNext()) {
                                S10 s10B = abstractC2412ko2.b();
                                for (S10 s10K2 : abstractC2412ko.h1()) {
                                    while (s10K2 != s10B) {
                                        AbstractC2412ko abstractC2412ko3 = s10K2.b;
                                        if (abstractC2412ko3 != null && abstractC2412ko3.l2()) {
                                            s10K2 = abstractC2412ko3.n0().K2();
                                        }
                                    }
                                    InterfaceC2641no interfaceC2641noA2 = c3332x4.a(c2409kl);
                                    interfaceC2641noA2.a(new Predicate() { // from class: com.android.tools.r8.ir.optimize.E0$$ExternalSyntheticLambda2
                                        @Override // java.util.function.Predicate
                                        public final boolean test(Object obj) {
                                            return E0.a(abstractC2412ko2, (AbstractC2412ko) obj);
                                        }
                                    });
                                    C1721bt c1721bt = new C1721bt((C0684j2) p1.d(), null, Collections.singletonList((S10) abstractC2412ko2.h1().get(0)));
                                    AbstractC2412ko abstractC2412koPrevious = interfaceC2641noA2.previous();
                                    boolean z = a;
                                    if (!z && abstractC2412ko2 != abstractC2412koPrevious) {
                                        throw new AssertionError();
                                    }
                                    C3332x4 c3332x4H = abstractC2412koPrevious.h();
                                    if (c3332x4H.D()) {
                                        C3332x4 c3332x4A = interfaceC2641noA2.a(c2409kl);
                                        if (!z && !c3332x4A.D()) {
                                            throw new AssertionError();
                                        }
                                        if (!z && c3332x4H.D()) {
                                            throw new AssertionError();
                                        }
                                        interfaceC2641noA2 = c3332x4H.a(c2409kl, c3332x4H.p().size() - 1);
                                    }
                                    c1721bt.b(abstractC2412ko2.Y0());
                                    interfaceC2641noA2.add(c1721bt);
                                    return;
                                }
                                break;
                            }
                        } while (it.next().m().J2() != c3332x4);
                    } else {
                        continue;
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C0684j2 a(C0722p1 c0722p1) {
        return c0722p1.a(c0722p1.c("Ljava/lang/Long;"), c0722p1.c("signum"), c0722p1.B, new C0759u2[]{c0722p1.C});
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(A1 a1, AbstractC2412ko abstractC2412ko) {
        return abstractC2412ko.W1() && abstractC2412ko.Z().S2().f == a1.a.Y0 && abstractC2412ko.Z().J2().size() == 4 && abstractC2412ko.Z().J2().stream().allMatch(new Predicate() { // from class: com.android.tools.r8.ir.optimize.E0$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((S10) obj).H();
            }
        });
    }

    public static void a(C2409kl c2409kl, F f, A1 a1) {
        if (a1.A() && c2409kl.t().K()) {
            B4 b4S = c2409kl.s();
            boolean z = false;
            while (b4S.hasNext()) {
                C3332x4 next = b4S.next();
                InterfaceC2641no interfaceC2641noA = next.a(c2409kl);
                while (interfaceC2641noA.hasNext()) {
                    AbstractC2412ko next2 = interfaceC2641noA.next();
                    if (!a && next2.y2()) {
                        throw new AssertionError();
                    }
                    if (next2.T1()) {
                        C1458Vr c1458VrW = next2.W();
                        if (c1458VrW.f(c1458VrW.L2() - 1) == Integer.MAX_VALUE) {
                            if (c1458VrW.L2() == 1) {
                                f.getClass();
                                F.a(c2409kl, next, interfaceC2641noA, c1458VrW);
                            } else {
                                C2732or c2732or = new C2732or(c1458VrW.L2() - 1);
                                for (int i = 0; i < c1458VrW.L2() - 1; i++) {
                                    c2732or.a(c1458VrW.f(i));
                                }
                                C2732or c2732or2 = new C2732or(1);
                                c2732or2.a(Integer.MAX_VALUE);
                                AbstractC1639am abstractC1639amC = AbstractC1639am.c(c2732or);
                                f.getClass();
                                F.a(c2409kl, b4S, next, interfaceC2641noA, c1458VrW, abstractC1639amC, c2732or2);
                            }
                            z = true;
                        }
                    }
                }
            }
            if (z) {
                c2409kl.B();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean a(AbstractC2412ko abstractC2412ko) {
        return (abstractC2412ko.F1() || abstractC2412ko.l2()) ? false : true;
    }
}
