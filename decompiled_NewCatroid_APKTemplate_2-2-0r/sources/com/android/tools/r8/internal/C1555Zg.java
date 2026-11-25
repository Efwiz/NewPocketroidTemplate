package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0737r2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.shaking.C3733i;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.IntPredicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Zg, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1555Zg {
    static final /* synthetic */ boolean c = true;
    private final C0702m a;
    private final C0722p1 b;

    public C1555Zg(C0702m c0702m) {
        this.a = c0702m;
        this.b = c0702m.a();
    }

    /* JADX WARN: Code duplicated, block: B:114:0x02cd A[LOOP:7: B:112:0x02ca->B:114:0x02cd, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:117:0x02fb  */
    /* JADX WARN: Code duplicated, block: B:120:0x031c  */
    /* JADX WARN: Code duplicated, block: B:139:0x0228 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:48:0x00ed  */
    /* JADX WARN: Code duplicated, block: B:72:0x0183  */
    /* JADX WARN: Code duplicated, block: B:7:0x0026  */
    public final void a(C2409kl c2409kl) {
        C1529Yg c1529Yg;
        int[] iArrA;
        int[] iArr;
        int i;
        final AbstractC2412ko abstractC2412ko;
        AbstractC2412ko abstractC2412ko2;
        B1 b1A;
        AbstractC2412ko abstractC2412ko3;
        Set setC = OU.c();
        Iterator<C3332x4> it = c2409kl.d.iterator();
        int i2 = 0;
        boolean z = false;
        while (it.hasNext()) {
            C3332x4 next = it.next();
            final C1458Vr c1458VrW = next.m().W();
            if (c1458VrW != null) {
                AbstractC2412ko abstractC2412ko4 = ((S10) ((ArrayList) c1458VrW.h1()).get(i2)).b;
                if (abstractC2412ko4 == null || !abstractC2412ko4.p1()) {
                    c1529Yg = null;
                    break;
                }
                Y2 y2T = abstractC2412ko4.t();
                AbstractC2412ko abstractC2412ko5 = y2T.K2().b;
                if (abstractC2412ko5 == null || !abstractC2412ko5.f2()) {
                    c1529Yg = null;
                    break;
                }
                C2115gt c2115gtI0 = abstractC2412ko5.i0();
                C0684j2 c0684j2S2 = c2115gtI0.S2();
                AbstractC0727q0 abstractC0727q0C = this.a.c(c0684j2S2.e);
                C0722p1 c0722p1A = this.a.a();
                if (abstractC0727q0C == null || ((!abstractC0727q0C.e.V() && abstractC0727q0C.d != c0722p1A.e2) || c0684j2S2.f != c0722p1A.n0)) {
                    c1529Yg = null;
                    break;
                }
                C0737r2 c0737r2 = c0684j2S2.h;
                if (c0737r2.e != c0722p1A.A1 || !c0737r2.f.isEmpty() || (abstractC2412ko3 = y2T.J2().b) == null || !(abstractC2412ko3 instanceof QW)) {
                    c1529Yg = null;
                    break;
                }
                QW qwF0 = abstractC2412ko3.F0();
                InterfaceC0992Fq interfaceC0992FqD = ((C3733i) this.a.f()).d(qwF0.getField());
                if (interfaceC0992FqD == null || interfaceC0992FqD.isEmpty()) {
                    c1529Yg = null;
                    break;
                }
                int[] iArrQ2 = c1458VrW.Q2();
                int length = iArrQ2.length;
                int i3 = i2;
                while (true) {
                    if (i3 >= length) {
                        c1529Yg = new C1529Yg(((com.android.tools.r8.graph.Y0) interfaceC0992FqD.values().iterator().next()).e, c2115gtI0, y2T, qwF0, interfaceC0992FqD);
                        break;
                    } else {
                        if (!interfaceC0992FqD.a(iArrQ2[i3])) {
                            c1529Yg = null;
                            break;
                        }
                        i3++;
                    }
                }
                if (c1529Yg == null) {
                    it = it;
                } else {
                    C3085to c3085to = new C3085to(c1458VrW.L2());
                    int i4 = 0;
                    while (true) {
                        if (i4 >= c1458VrW.L2()) {
                            it = it;
                            break;
                        }
                        if (!c && c1458VrW.M2()[i4] == c1458VrW.K2()) {
                            throw new AssertionError();
                        }
                        com.android.tools.r8.graph.Y0 y0 = (com.android.tools.r8.graph.Y0) c1529Yg.e.get(c1458VrW.f(i4));
                        C3733i c3733i = (C3733i) this.a.f();
                        com.android.tools.r8.graph.o5 o5VarH = c2409kl.h();
                        c3733i.getClass();
                        com.android.tools.r8.graph.T0 t0S = c3733i.a(y0.e, y0, o5VarH).s();
                        if (t0S == null) {
                            it = it;
                        } else {
                            B1 b1F = t0S.X0().f();
                            com.android.tools.r8.graph.Y0 y1 = this.b.r4.b;
                            b1F.getClass();
                            if (b1F instanceof UH) {
                                b1F = b1F.l().F();
                            }
                            if (b1F.B()) {
                                C3733i c3733i2 = (C3733i) this.a.f();
                                com.android.tools.r8.graph.o5 o5VarH2 = c2409kl.h();
                                c3733i2.getClass();
                                com.android.tools.r8.graph.T0 t0S2 = c3733i2.a(y1.e, y1, o5VarH2).s();
                                if (t0S2 == null) {
                                    b1A = null;
                                } else {
                                    b1A = b1F.p().t().a(t0S2);
                                }
                            } else {
                                b1A = null;
                            }
                            C2853qV c2853qVE = b1A == null ? null : b1A.e();
                            if (c2853qVE == null && this.a.Y().x1().c()) {
                                C3286wP c3286wP = this.a.Z().g;
                                C0702m c0702m = this.a;
                                C0767v2 c0767v2 = c1529Yg.a;
                                com.android.tools.r8.graph.o5 o5VarH3 = c2409kl.h();
                                c0702m.getClass();
                                C0729q2 c0729q2A = C0729q2.a(c0702m.b(o5VarH3.a(), c0767v2));
                                C3733i c3733i3 = (C3733i) this.a.f();
                                com.android.tools.r8.graph.Y0 y2 = this.b.r4.b;
                                com.android.tools.r8.graph.o5 o5VarH4 = c2409kl.h();
                                c3733i3.getClass();
                                c2853qVE = c3286wP.a(c0729q2A, t0S, c3733i3.a(y2.e, y2, o5VarH4).s());
                            }
                            if (c2853qVE == null) {
                                c3085to = null;
                                break;
                            }
                            c3085to.b(c2853qVE.G(), c1458VrW.M2()[i4]);
                        }
                        i4++;
                        it = it;
                    }
                    if (c3085to == null) {
                        continue;
                    } else {
                        int iK2 = c1458VrW.K2();
                        if (c3085to.size() >= c1458VrW.L2()) {
                            iArrA = ((J) c3085to.keySet()).a((int[]) null);
                            Arrays.sort(iArrA);
                            iArr = new int[iArrA.length];
                            for (i = 0; i < iArrA.length; i++) {
                                iArr[i] = c3085to.get(iArrA[i]);
                            }
                            c1458VrW.h().a(c2409kl, (AbstractC2412ko) c1458VrW).a(new C1458Vr(iK2, c1529Yg.b.b(), iArrA, iArr), (Set) null);
                            abstractC2412ko = c1529Yg.c;
                            if (!abstractC2412ko.b().E()) {
                                ((ArrayList) abstractC2412ko.h1()).forEach(new Consumer() { // from class: com.android.tools.r8.internal.Zg$$ExternalSyntheticLambda1
                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        ((S10) obj).e(abstractC2412ko);
                                    }
                                });
                                abstractC2412ko.h().g(abstractC2412ko);
                            }
                            abstractC2412ko2 = c1529Yg.d;
                            if (abstractC2412ko2.b().E()) {
                                continue;
                            } else {
                                if (c && !((ArrayList) abstractC2412ko2.h1()).isEmpty()) {
                                    throw new AssertionError();
                                }
                                abstractC2412ko2.h().g(abstractC2412ko2);
                            }
                        } else if (next.W() != c1458VrW.L2() + 1) {
                            continue;
                        } else {
                            int iL2 = c1458VrW.L2() + 1;
                            final int iV = next.V();
                            final Lr lr = new Lr((C3238vr) c3085to.values());
                            IntPredicate intPredicate = new IntPredicate() { // from class: com.android.tools.r8.internal.Zg$$ExternalSyntheticLambda0
                                @Override // java.util.function.IntPredicate
                                public final boolean test(int i5) {
                                    return C1555Zg.a(iV, lr, c1458VrW, i5);
                                }
                            };
                            int i5 = com.android.tools.r8.utils.B.b;
                            int[] iArr2 = new int[iL2];
                            for (int i6 = 0; i6 < iL2; i6++) {
                                iArr2[i6] = com.android.tools.r8.utils.M.a(intPredicate.test(i6));
                            }
                            C2732or c2732or = new C2732or(iL2);
                            for (int i7 = 0; i7 < iL2; i7++) {
                                if (iArr2[i7] == 1) {
                                    next.z().get(i7 + iV).a(next, setC);
                                    c2732or.a(i7);
                                }
                            }
                            next.a(c2732or);
                            for (int i8 = 1; i8 < iL2; i8++) {
                                iArr2[i8] = iArr2[i8] + iArr2[i8 - 1];
                            }
                            InterfaceC1912eK it2 = c3085to.i().iterator();
                            while (it2.hasNext()) {
                                InterfaceC0990Fo interfaceC0990Fo = (InterfaceC0990Fo) it2.next();
                                c3085to.b(interfaceC0990Fo.a(), interfaceC0990Fo.getIntValue() - iArr2[interfaceC0990Fo.getIntValue()]);
                            }
                            iK2 -= iArr2[iK2];
                            z = true;
                            iArrA = ((J) c3085to.keySet()).a((int[]) null);
                            Arrays.sort(iArrA);
                            iArr = new int[iArrA.length];
                            while (i < iArrA.length) {
                                iArr[i] = c3085to.get(iArrA[i]);
                            }
                            c1458VrW.h().a(c2409kl, (AbstractC2412ko) c1458VrW).a(new C1458Vr(iK2, c1529Yg.b.b(), iArrA, iArr), (Set) null);
                            abstractC2412ko = c1529Yg.c;
                            if (!abstractC2412ko.b().E()) {
                                ((ArrayList) abstractC2412ko.h1()).forEach(new Consumer() { // from class: com.android.tools.r8.internal.Zg$$ExternalSyntheticLambda1
                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        ((S10) obj).e(abstractC2412ko);
                                    }
                                });
                                abstractC2412ko.h().g(abstractC2412ko);
                            }
                            abstractC2412ko2 = c1529Yg.d;
                            if (abstractC2412ko2.b().E()) {
                                continue;
                            } else {
                                if (c) {
                                }
                                abstractC2412ko2.h().g(abstractC2412ko2);
                            }
                        }
                    }
                }
            } else {
                it = it;
            }
            it = it;
            i2 = 0;
        }
        if (z) {
            setC.addAll(c2409kl.z());
        }
        if (setC.isEmpty()) {
            return;
        }
        new C3030t00(this.a, false).a((Iterable) setC);
    }

    /* JADX WARN: Code duplicated, block: B:58:0x0134  */
    /* JADX WARN: Code duplicated, block: B:70:0x017a  */
    /* JADX WARN: Code duplicated, block: B:76:0x019d  */
    public final void b(C2409kl c2409kl) {
        QW qwF0;
        B1 b1A;
        B1 b1F;
        B1 b1A2;
        C3155ul c3155ulT = c2409kl.t();
        if (c3155ulT.u() || (c3155ulT.w() && c3155ulT.e())) {
            Set setC = OU.c();
            C2561ml c2561ml = new C2561ml(c2409kl);
            while (c2561ml.hasNext()) {
                AbstractC2412ko abstractC2412ko = (AbstractC2412ko) c2561ml.next();
                if (abstractC2412ko.Z1()) {
                    AbstractC1332Rs abstractC1332RsC0 = abstractC2412ko.c0();
                    S10 s10M = abstractC1332RsC0.U2().m();
                    if (s10M.w().w() && ((C3733i) this.a.f()).c(s10M.w().b().R(), this.b.e2)) {
                        C0684j2 c0684j2S2 = abstractC1332RsC0.S2();
                        boolean zB = c0684j2S2.b(this.b.r4.d);
                        boolean zB2 = c0684j2S2.b(this.b.r4.e);
                        boolean zB3 = c0684j2S2.b(this.b.r4.f);
                        if (zB || zB2 || zB3) {
                            if (!s10M.i() && (qwF0 = s10M.q().F0()) != null) {
                                com.android.tools.r8.graph.Y0 field = qwF0.getField();
                                C0702m c0702m = this.a;
                                c0702m.getClass();
                                com.android.tools.r8.graph.T0 t0C = field.c(c0702m.c(field.e));
                                if (t0C == null) {
                                    continue;
                                } else {
                                    com.android.tools.r8.ir.optimize.info.e eVarX0 = t0C.X0();
                                    B1 b1F2 = eVarX0.f();
                                    if (!abstractC1332RsC0.f1()) {
                                        S10 s10B = abstractC1332RsC0.b();
                                        if (zB) {
                                            boolean zP = abstractC1332RsC0.U2().P();
                                            com.android.tools.r8.graph.Y0 y0 = this.b.r4.b;
                                            if (zP) {
                                                b1F2.getClass();
                                                if (b1F2 instanceof UH) {
                                                    b1F2 = b1F2.l().F();
                                                }
                                            }
                                            if (b1F2.B()) {
                                                C3733i c3733i = (C3733i) this.a.f();
                                                com.android.tools.r8.graph.o5 o5VarH = c2409kl.h();
                                                c3733i.getClass();
                                                com.android.tools.r8.graph.T0 t0S = c3733i.a(y0.e, y0, o5VarH).s();
                                                if (t0S == null) {
                                                    b1A = null;
                                                } else {
                                                    b1A = b1F2.p().t().a(t0S);
                                                }
                                            } else {
                                                b1A = null;
                                            }
                                            C2853qV c2853qVE = b1A == null ? null : b1A.e();
                                            if (c2853qVE != null) {
                                                c2561ml.a(new C0978Fc(s10B, c2853qVE.H()), (Set) null);
                                            }
                                        } else {
                                            boolean zP2 = abstractC1332RsC0.U2().P();
                                            com.android.tools.r8.graph.Y0 y1 = this.b.r4.a;
                                            if (zP2) {
                                                b1F2.getClass();
                                                if (b1F2 instanceof UH) {
                                                    b1F = b1F2.l().F();
                                                } else {
                                                    b1F = b1F2;
                                                }
                                            } else {
                                                b1F = b1F2;
                                            }
                                            if (b1F.B()) {
                                                C3733i c3733i2 = (C3733i) this.a.f();
                                                com.android.tools.r8.graph.o5 o5VarH2 = c2409kl.h();
                                                c3733i2.getClass();
                                                com.android.tools.r8.graph.T0 t0S2 = c3733i2.a(y1.e, y1, o5VarH2).s();
                                                if (t0S2 == null) {
                                                    b1A2 = null;
                                                } else {
                                                    b1A2 = b1F.p().t().a(t0S2);
                                                }
                                            } else {
                                                b1A2 = null;
                                            }
                                            C3214vV c3214vVQ = b1A2 == null ? null : b1A2.q();
                                            if (c3214vVQ == null) {
                                                continue;
                                            } else if (zB2) {
                                                S10 s10A = c2409kl.a((AbstractC3472z00) AbstractC3472z00.b((C0702m<?>) this.a, WH.b()));
                                                c2561ml.a(new C1033Hc(s10A, c3214vVQ.F()), (Set) null);
                                                s10A.a(setC);
                                            } else {
                                                boolean z = c;
                                                if (!z && !zB3) {
                                                    throw new AssertionError();
                                                }
                                                if (!((C3733i) this.a.f()).c(field.h).e.h()) {
                                                    continue;
                                                } else {
                                                    if (!z && !b1F2.B()) {
                                                        throw new AssertionError();
                                                    }
                                                    C1229Oa c1229OaE = eVarX0.g().e();
                                                    if (c1229OaE != null) {
                                                        com.android.tools.r8.graph.W0 w0T = ((C3733i) this.a.f()).a(c1229OaE.R(), this.b.g4.i).t();
                                                        if (w0T == null || w0T.getReference() == this.b.r4.f) {
                                                            S10 s10A2 = c2409kl.a((AbstractC3472z00) AbstractC3472z00.b((C0702m<?>) this.a, WH.b()));
                                                            c2561ml.a(new C1033Hc(s10A2, c3214vVQ.F()), (Set) null);
                                                            s10A2.a(setC);
                                                        }
                                                    } else if (!z) {
                                                        throw new AssertionError("Expected to have an exact dynamic type for enum instance");
                                                    }
                                                }
                                            }
                                        }
                                    } else if (abstractC1332RsC0.U2().w().x() && !zB3) {
                                        if (!c && !zB2 && !zB) {
                                            throw new AssertionError();
                                        }
                                        c2561ml.s();
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (!setC.isEmpty()) {
                new C3030t00(this.a, false).a((Iterable) setC);
            }
            if (!c && !c2409kl.b(this.a)) {
                throw new AssertionError();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(int i, InterfaceC1277Pr interfaceC1277Pr, C1458Vr c1458Vr, int i2) {
        int i3 = i2 + i;
        return (interfaceC1277Pr.f(i3) || i3 == c1458Vr.K2()) ? false : true;
    }
}
