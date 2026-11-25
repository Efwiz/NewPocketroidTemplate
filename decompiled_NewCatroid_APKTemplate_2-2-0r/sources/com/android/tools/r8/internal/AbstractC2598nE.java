package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import java.util.ListIterator;
import java.util.Set;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.nE, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC2598nE {
    static final /* synthetic */ boolean b = true;
    final C0702m a;

    AbstractC2598nE(C0702m c0702m) {
        this.a = c0702m;
    }

    public final void a(C2409kl c2409kl) {
        C3155ul c3155ulT = c2409kl.t();
        if (c3155ulT.l() || c3155ulT.t()) {
            Set setC = OU.c();
            a(c2409kl, c2409kl.s(), setC, CM.b());
            if (!setC.isEmpty()) {
                new C3030t00(this.a, false).a((Iterable) setC);
            }
            boolean z = b;
            if (!z && !c2409kl.b(this.a)) {
                throw new AssertionError();
            }
            if (z) {
                return;
            }
            c2409kl.d(this.a);
        }
    }

    abstract void a(C2409kl c2409kl, com.android.tools.r8.graph.o5 o5Var, Set set, B4 b4, InterfaceC2641no interfaceC2641no, AbstractC1305Qs abstractC1305Qs);

    abstract void a(C2409kl c2409kl, InterfaceC2641no interfaceC2641no, RW rw);

    abstract void a(C2409kl c2409kl, InterfaceC2641no interfaceC2641no, C1793co c1793co);

    abstract void a(C2409kl c2409kl, Set set, B4 b4, InterfaceC2641no interfaceC2641no, C1189Mn c1189Mn);

    abstract void a(C2409kl c2409kl, Set set, B4 b4, InterfaceC2641no interfaceC2641no, QW qw);

    abstract void a(C2409kl c2409kl, Set set, B4 b4, InterfaceC2641no interfaceC2641no, Y2 y2);

    public final void a(C2409kl c2409kl, B4 b4, Set set, Predicate predicate) {
        com.android.tools.r8.graph.o5 o5VarH = c2409kl.h();
        while (b4.hasNext()) {
            C3332x4 next = b4.next();
            if (predicate.test(next)) {
                InterfaceC2641no interfaceC2641noA = next.a(c2409kl);
                while (interfaceC2641noA.hasNext()) {
                    AbstractC2412ko next2 = interfaceC2641noA.next();
                    int iG2 = next2.G2();
                    if (iG2 == 6) {
                        a(c2409kl, set, b4, interfaceC2641noA, next2.t());
                    } else if (iG2 == 28) {
                        a(c2409kl, set, b4, interfaceC2641noA, next2.T());
                    } else if (iG2 != 30) {
                        if (iG2 != 33 && iG2 != 34) {
                            if (iG2 == 59) {
                                a(c2409kl, set, b4, interfaceC2641noA, next2.F0());
                            } else if (iG2 != 60) {
                                switch (iG2) {
                                    case 38:
                                    case 39:
                                    case 40:
                                        break;
                                }
                            } else {
                                a(c2409kl, interfaceC2641noA, next2.G0());
                            }
                        }
                        a(c2409kl, o5VarH, set, b4, interfaceC2641noA, next2.b0());
                    } else {
                        a(c2409kl, interfaceC2641noA, next2.V());
                    }
                }
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:23:0x006d  */
    final boolean a(C2409kl c2409kl, Set set, B4 b4, InterfaceC2641no interfaceC2641no, AbstractC2412ko abstractC2412ko, Q3 q3) {
        AbstractC2412ko abstractC2412koA;
        if (q3.f() && abstractC2412ko.f1()) {
            interfaceC2641no.s();
            return true;
        }
        if (abstractC2412ko.d1() && abstractC2412ko.a().H()) {
            B1 b1D = q3.d();
            b1D.getClass();
            if (b1D instanceof C1910eI) {
                abstractC2412ko.b().a(q3.d().d());
            }
        }
        if (q3.d().isUnknown()) {
            abstractC2412koA = null;
        } else {
            B1 b1D2 = q3.d();
            if (b1D2.g()) {
                AbstractC3292wV abstractC3292wVJ = b1D2.j();
                if (abstractC3292wVJ.b(this.a, c2409kl.h())) {
                    abstractC2412koA = abstractC3292wVJ.a(this.a, c2409kl.h(), c2409kl.e, abstractC2412ko);
                } else {
                    abstractC2412koA = null;
                }
            } else {
                abstractC2412koA = null;
            }
        }
        if (abstractC2412koA == null) {
            return false;
        }
        set.addAll(abstractC2412ko.b().a());
        if (q3.f()) {
            interfaceC2641no.d(abstractC2412koA);
            return true;
        }
        C3332x4 c3332x4H = abstractC2412ko.h();
        AbstractC1915eM abstractC1915eMY0 = abstractC2412ko.Y0();
        if (abstractC2412ko.d1()) {
            if (!b && abstractC2412koA.b() == null) {
                throw new AssertionError();
            }
            abstractC2412ko.b().g(abstractC2412koA.b());
        }
        if (abstractC2412ko instanceof C1189Mn) {
            interfaceC2641no.a(this.a, c2409kl.h());
        } else if (abstractC2412ko instanceof QW) {
            interfaceC2641no.a(this.a, c2409kl, abstractC2412ko.F0().getField().e);
        }
        abstractC2412koA.b(abstractC1915eMY0);
        if (c3332x4H.D()) {
            C3332x4 c3332x4A = interfaceC2641no.a(c2409kl, b4);
            c3332x4A.a(c2409kl).add(abstractC2412koA);
            b4.previous();
            boolean z = b;
            if (!z && interfaceC2641no.hasNext()) {
                throw new AssertionError();
            }
            if (z || com.android.tools.r8.utils.L1.a((ListIterator) b4) == c3332x4A) {
                return true;
            }
            throw new AssertionError();
        }
        interfaceC2641no.add(abstractC2412koA);
        interfaceC2641no.previous();
        if (b || interfaceC2641no.o() == abstractC2412koA) {
            return true;
        }
        throw new AssertionError();
    }
}
