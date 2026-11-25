package com.android.tools.r8.ir.optimize;

import com.android.tools.r8.ClassFileConsumer;
import com.android.tools.r8.FeatureSplit;
import com.android.tools.r8.graph.AbstractC0639e;
import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.AbstractC0744s2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.AbstractC1305Qs;
import com.android.tools.r8.internal.AbstractC2412ko;
import com.android.tools.r8.internal.AbstractC3135uW;
import com.android.tools.r8.internal.AbstractC3472z00;
import com.android.tools.r8.internal.B4;
import com.android.tools.r8.internal.C1033Hc;
import com.android.tools.r8.internal.C1121Ka;
import com.android.tools.r8.internal.C1721bt;
import com.android.tools.r8.internal.C2108gn;
import com.android.tools.r8.internal.C2115gt;
import com.android.tools.r8.internal.C2179hn;
import com.android.tools.r8.internal.C2409kl;
import com.android.tools.r8.internal.C2477lf;
import com.android.tools.r8.internal.C3030t00;
import com.android.tools.r8.internal.C3332x4;
import com.android.tools.r8.internal.InterfaceC2641no;
import com.android.tools.r8.internal.OU;
import com.android.tools.r8.internal.S10;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.utils.A1;
import com.android.tools.r8.utils.C3970u0;
import java.util.ArrayList;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class C0 {
    static final /* synthetic */ boolean a = true;

    public static void a(C0702m c0702m, C2409kl c2409kl) {
        C0729q2 c0729q2A;
        if (((C3733i) c0702m.f()).a(c0702m.Y())) {
            Set setC = OU.c();
            o5 o5VarH = c2409kl.h();
            B4 b4S = c2409kl.s();
            while (b4S.hasNext()) {
                InterfaceC2641no interfaceC2641noA = b4S.next().a(c2409kl);
                while (interfaceC2641noA.hasNext()) {
                    AbstractC1305Qs abstractC1305Qs = (AbstractC1305Qs) interfaceC2641noA.a(new Predicate() { // from class: com.android.tools.r8.ir.optimize.C0$$ExternalSyntheticLambda0
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                            return C0.a((AbstractC2412ko) obj);
                        }
                    });
                    if (abstractC1305Qs != null) {
                        if (abstractC1305Qs instanceof C1721bt) {
                            C1721bt c1721btG0 = abstractC1305Qs.g0();
                            BiConsumer biConsumerA = a(c0702m, c2409kl, b4S, interfaceC2641noA, abstractC1305Qs, setC);
                            C0722p1 c0722p1A = c0702m.a();
                            if (c1721btG0.S2() == c0722p1A.p4.b) {
                                boolean z = a;
                                if (!z && ((ArrayList) c1721btG0.J2()).size() != 1) {
                                    throw new AssertionError();
                                }
                                S10 s10M = c1721btG0.c(0).m();
                                if (!s10M.A() && !s10M.i() && (!c1721btG0.d1() || !c1721btG0.b().A())) {
                                    AbstractC2412ko abstractC2412ko = s10M.b;
                                    abstractC2412ko.getClass();
                                    C0767v2 c0767v2W0 = null;
                                    if (abstractC2412ko instanceof C2477lf) {
                                        AbstractC0744s2 abstractC0744s2J2 = s10M.b.J().J2();
                                        abstractC0744s2J2.getClass();
                                        if (abstractC0744s2J2 instanceof C0767v2) {
                                            c0767v2W0 = s10M.b.J().J2().w0();
                                        }
                                    } else {
                                        AbstractC2412ko abstractC2412ko2 = s10M.b;
                                        abstractC2412ko2.getClass();
                                        if (abstractC2412ko2 instanceof C1033Hc) {
                                            String string = s10M.b.F().J2().toString();
                                            String strH = C3970u0.E(string) ? C3970u0.H(string) : null;
                                            if (strH == null && string.startsWith("[") && string.endsWith(";")) {
                                                strH = string.replace('.', '/');
                                            }
                                            if (strH != null && strH.indexOf(46) <= 0) {
                                                c0767v2W0 = c0722p1A.e(strH);
                                                if (!c0767v2W0.X0()) {
                                                    continue;
                                                }
                                            }
                                        } else {
                                            continue;
                                        }
                                    }
                                    if (c0767v2W0 == null) {
                                        continue;
                                    } else {
                                        AbstractC0727q0 abstractC0727q0D = ((C3733i) c0702m.f()).d(c0767v2W0.b(c0722p1A));
                                        if (abstractC0727q0D != null && abstractC0727q0D.d(c0702m)) {
                                            C1121Ka c1121KaP = ((C3733i) c0702m.f()).p();
                                            if (AbstractC0639e.a(abstractC0727q0D, o5VarH, c0702m).b()) {
                                                continue;
                                            } else {
                                                if (!z && abstractC0727q0D.e0()) {
                                                    C0729q2 c0729q2A0 = abstractC0727q0D.a0();
                                                    c1121KaP.getClass();
                                                    A1 a1Y = c0702m.Y();
                                                    AbstractC3135uW abstractC3135uWC = c0702m.C();
                                                    com.android.tools.r8.synthesis.D D = c0702m.D();
                                                    FeatureSplit featureSplitA = c1121KaP.a(c0729q2A0.getType(), a1Y, abstractC3135uWC, D);
                                                    if (!featureSplitA.isBase() && featureSplitA != c1121KaP.a(o5VarH.C(), a1Y, abstractC3135uWC, D)) {
                                                        throw new AssertionError();
                                                    }
                                                }
                                                biConsumerA.accept(c0767v2W0, abstractC0727q0D);
                                            }
                                        }
                                    }
                                }
                            } else {
                                continue;
                            }
                        } else {
                            C2115gt c2115gtI0 = abstractC1305Qs.i0();
                            BiConsumer biConsumerA2 = a(c0702m, c2409kl, b4S, interfaceC2641noA, abstractC1305Qs, setC);
                            C0722p1 c0722p1A2 = c0702m.a();
                            if (c2115gtI0.S2() == c0722p1A2.g4.e) {
                                S10 s10U2 = c2115gtI0.U2();
                                if (!s10U2.A()) {
                                    AbstractC3472z00 abstractC3472z00W = s10U2.w();
                                    if (abstractC3472z00W.w() || abstractC3472z00W.r()) {
                                        if (!abstractC3472z00W.F()) {
                                            C0767v2 c0767v2R = abstractC3472z00W.w() ? abstractC3472z00W.b().R() : abstractC3472z00W.a().a(c0722p1A2);
                                            C0767v2 c0767v2B = c0767v2R.b(c0722p1A2);
                                            if (c0767v2B.O0() && (c0729q2A = C0729q2.a(c0702m.c(c0767v2B))) != null && (c0729q2A.c(c0702m) || (!s10U2.i() && s10U2.b.E1()))) {
                                                if (V.a(c0702m, c0767v2B, o5VarH) != V.c) {
                                                    biConsumerA2.accept(c0767v2R, c0729q2A);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (!setC.isEmpty()) {
                new C3030t00(c0702m, false).a((Iterable) setC);
            }
            if (!a && !c2409kl.b((C0702m<?>) c0702m)) {
                throw new AssertionError();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean a(AbstractC2412ko abstractC2412ko) {
        abstractC2412ko.getClass();
        return (abstractC2412ko instanceof C1721bt) || abstractC2412ko.f2();
    }

    private static BiConsumer a(final C0702m c0702m, final C2409kl c2409kl, final B4 b4, final InterfaceC2641no interfaceC2641no, final AbstractC1305Qs abstractC1305Qs, final Set set) {
        return new BiConsumer() { // from class: com.android.tools.r8.ir.optimize.C0$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C0.a(abstractC1305Qs, c0702m, c2409kl, interfaceC2641no, set, b4, (C0767v2) obj, (AbstractC0727q0) obj2);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(AbstractC1305Qs abstractC1305Qs, C0702m c0702m, C2409kl c2409kl, InterfaceC2641no interfaceC2641no, Set set, B4 b4, C0767v2 c0767v2, AbstractC0727q0 abstractC0727q0) {
        C2179hn c2179hnB = null;
        if (abstractC1305Qs.S2().b(c0702m.a().p4.b)) {
            if (abstractC0727q0.e0() && !((C3733i) c0702m.f()).f().a(c2409kl.h(), abstractC0727q0.getType(), c0702m.D())) {
                return;
            }
            if (c0767v2.O0() && abstractC0727q0.a(c0702m, c2409kl.h())) {
                if (!abstractC0727q0.e0() || !c0702m.k()) {
                    return;
                } else {
                    c2179hnB = ((C2108gn) ((C2108gn) C2179hn.J2().a(c2409kl, AbstractC3472z00.k(), null)).a(c0767v2).b(abstractC1305Qs)).b();
                }
            }
        }
        if (!abstractC1305Qs.d1() || !abstractC1305Qs.b().y()) {
            if (c2179hnB != null) {
                interfaceC2641no.d(c2179hnB);
                return;
            } else {
                interfaceC2641no.s();
                return;
            }
        }
        C3332x4 c3332x4H = abstractC1305Qs.h();
        set.addAll(abstractC1305Qs.b().a());
        interfaceC2641no.a((C0702m<?>) c0702m, c2409kl, c0767v2, abstractC1305Qs.j());
        if (c2179hnB != null) {
            if (c3332x4H.D()) {
                interfaceC2641no.a(c2409kl, b4, c0702m.Y()).a(c2409kl).add(c2179hnB);
            } else {
                interfaceC2641no.add(c2179hnB);
            }
        }
        if (c0702m.Y().f instanceof ClassFileConsumer) {
            c2409kl.u().b(c0702m.Y().y1());
        }
    }
}
