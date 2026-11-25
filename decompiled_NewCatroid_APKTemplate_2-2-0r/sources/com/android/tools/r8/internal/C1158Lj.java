package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0639e;
import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.utils.InterfaceC3877c1;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Lj, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C1158Lj {
    static final /* synthetic */ boolean g = true;
    private final C0702m a;
    private final C2076gQ b;
    private final C2154hQ c;
    private final VP d;
    private final AbstractC3472z00 e;
    private final C1229Oa f;

    public C1158Lj(C0702m c0702m, C2076gQ c2076gQ, VP vp) {
        this.a = c0702m;
        this.b = c2076gQ;
        this.c = new C2154hQ(c0702m.a());
        this.d = vp;
        this.e = AbstractC3472z00.a(c0702m.a().c2, WH.b(), (C0702m<?>) c0702m);
        this.f = AbstractC3472z00.b((C0702m<?>) c0702m, WH.b());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(C3332x4 c3332x4, C3332x4 c3332x5) {
        return c3332x5 == c3332x4;
    }

    public final void a(final com.android.tools.r8.shaking.A a) {
        C0729q2 c0729q2A = C0729q2.a(this.a.f().d(this.d.i));
        if (c0729q2A != null) {
            com.android.tools.r8.graph.o5 o5VarF = c0729q2A.f(this.d.E);
            if (o5VarF != null) {
                a.a(o5VarF.getReference()).c().t().v().w().x().y().z().A().B();
            }
            this.d.a(new Consumer() { // from class: com.android.tools.r8.internal.Lj$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a(a, (C0684j2) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(com.android.tools.r8.shaking.A a, C0684j2 c0684j2) {
        com.android.tools.r8.graph.o5 o5VarA = c0684j2.a(C0729q2.a(this.a.c(c0684j2.B0())));
        if (o5VarA != null) {
            a.a(o5VarA.getReference()).c().t().v().w().x().y().z().A().B();
        }
    }

    public final void a(C2409kl c2409kl) {
        com.android.tools.r8.graph.o5 o5VarH = c2409kl.h();
        if (this.d.a(o5VarH.getReference())) {
            AbstractC1305Qs abstractC1305QsA = a(c2409kl, this.d);
            if (abstractC1305QsA != null) {
                S10 s10A = ZP.a(abstractC1305QsA, this.d);
                VP vp = this.d;
                if (!ZP.a) {
                    C0684j2 c0684j2S2 = abstractC1305QsA.S2();
                    if (!c0684j2S2.b(vp.F) && c0684j2S2 != vp.G) {
                        throw new AssertionError();
                    }
                }
                int iA = com.android.tools.r8.utils.M.a(abstractC1305QsA.W1());
                S10 s10M = ((S10) ((ArrayList) abstractC1305QsA.h1()).get(iA + 2)).m();
                EP epA = this.b.a(o5VarH, s10A, s10M);
                if (epA != null) {
                    AbstractC2412ko abstractC2412ko = s10A.b;
                    abstractC2412ko.h().a(c2409kl, abstractC2412ko).a(new C1033Hc(c2409kl.a((AbstractC3472z00) this.f), this.c.a(epA)), (Set) null);
                    A4 a4A = abstractC1305QsA.h().a(c2409kl, abstractC1305QsA);
                    AbstractC2412ko abstractC2412koPrevious = a4A.previous();
                    a4A.a(abstractC1305QsA.Y0());
                    if (!g && abstractC2412koPrevious != abstractC1305QsA) {
                        throw new AssertionError();
                    }
                    this.c.getClass();
                    ArrayList arrayListB = C2154hQ.b(epA);
                    S10 s10A2 = a4A.a(c2409kl, this.a.Y(), arrayListB.size());
                    S10 s10A3 = c2409kl.a(this.e);
                    int i = 0;
                    if (this.a.Y().A1().b() && arrayListB.size() < 200) {
                        ArrayList arrayList = new ArrayList(arrayListB.size());
                        while (i < arrayListB.size()) {
                            AbstractC2412ko abstractC2412koA = ((LP) arrayListB.get(i)).a(this.a, c2409kl);
                            a4A.add(abstractC2412koA);
                            arrayList.add(abstractC2412koA.b());
                            i++;
                        }
                        a4A.add(new C1385Ts(this.a.a().c2, s10A3, arrayList));
                    } else {
                        a4A.add(new AG(s10A3, s10A2, this.a.a().c2));
                        while (i < arrayListB.size()) {
                            S10 s10A4 = a4A.a(c2409kl, this.a.Y(), i);
                            AbstractC2412ko abstractC2412koA2 = ((LP) arrayListB.get(i)).a(this.a, c2409kl);
                            a4A.add(abstractC2412koA2);
                            a4A.add(new C1736c3(EnumC2523mE.OBJECT, s10A3, s10A4, abstractC2412koA2.b()));
                            i++;
                        }
                    }
                    VP vp2 = this.d;
                    if (!ZP.a) {
                        C0684j2 c0684j2S3 = abstractC1305QsA.S2();
                        if (!c0684j2S3.b(vp2.F) && c0684j2S3 != vp2.G) {
                            throw new AssertionError();
                        }
                    }
                    abstractC1305QsA.a(com.android.tools.r8.utils.M.a(abstractC1305QsA.W1()) + 2, s10A3);
                    AbstractC2871ql.a(c2409kl, s10M.b);
                    return;
                }
                if (!g) {
                    throw new AssertionError();
                }
                return;
            }
            return;
        }
        if (this.a.f().i()) {
            a(this.a.i0(), c2409kl);
        }
    }

    /* JADX WARN: Code duplicated, block: B:17:0x0048  */
    /* JADX WARN: Code duplicated, block: B:26:0x0072  */
    /* JADX WARN: Code duplicated, block: B:36:0x00b1  */
    private void a(C0702m c0702m, C2409kl c2409kl) {
        C0767v2 c0767v2R;
        S10 s10M;
        AbstractC2412ko abstractC2412ko;
        Set<S10> setC = OU.c();
        B4 b4S = c2409kl.s();
        while (b4S.hasNext()) {
            C3332x4 next = b4S.next();
            InterfaceC2641no interfaceC2641noA = next.a(c2409kl);
            while (interfaceC2641noA.hasNext()) {
                AbstractC2412ko next2 = interfaceC2641noA.next();
                if (next2.Z1()) {
                    AbstractC1332Rs abstractC1332RsC0 = next2.c0();
                    C0684j2 c0684j2S2 = abstractC1332RsC0.S2();
                    if (!this.d.a(c0684j2S2)) {
                        TP tp = this.d.q;
                        if (c0684j2S2 != tp.b && c0684j2S2 != tp.c) {
                            c0767v2R = null;
                        } else {
                            if (g && c0684j2S2.k(0) != this.d.n) {
                                throw new AssertionError();
                            }
                            UP up = this.d.t;
                            S10 s10Q2 = abstractC1332RsC0.Q2();
                            up.getClass();
                            s10M = s10Q2.m();
                            if (s10M.i()) {
                                c0767v2R = null;
                            } else {
                                abstractC2412ko = s10M.b;
                                abstractC2412ko.getClass();
                                if ((abstractC2412ko instanceof QW) || s10M.b.F0().getField() != up.f) {
                                    c0767v2R = null;
                                } else {
                                    AbstractC3472z00 abstractC3472z00A = abstractC1332RsC0.U2().a(c0702m);
                                    if (abstractC3472z00A.w()) {
                                        c0767v2R = abstractC3472z00A.b().R();
                                        if (!((C3733i) c0702m.f()).b(c0767v2R, this.d.i)) {
                                            c0767v2R = null;
                                        }
                                    } else {
                                        c0767v2R = null;
                                    }
                                }
                            }
                        }
                    } else {
                        if (g) {
                        }
                        UP up2 = this.d.t;
                        S10 s10Q3 = abstractC1332RsC0.Q2();
                        up2.getClass();
                        s10M = s10Q3.m();
                        if (s10M.i()) {
                            abstractC2412ko = s10M.b;
                            abstractC2412ko.getClass();
                            if (abstractC2412ko instanceof QW) {
                                c0767v2R = null;
                            } else {
                                c0767v2R = null;
                            }
                        } else {
                            c0767v2R = null;
                        }
                    }
                } else {
                    c0767v2R = null;
                }
                if (c0767v2R != null) {
                    C0684j2 c0684j2A = c0702m.a().a(c0767v2R, new C0767v2[0]);
                    com.android.tools.r8.graph.o5 o5VarA = AbstractC0748t0.a(c0702m.b(c0684j2A));
                    if (o5VarA != null && !AbstractC0639e.a(o5VarA, o5VarA.a(), c2409kl.h(), c0702m).b()) {
                        int i = DG.k;
                        DG dgB = ((CG) ((CG) new CG().a(c0767v2R).a(c2409kl, AbstractC3472z00.a(c0767v2R, WH.b(), (C0702m<?>) c0702m), null)).b(next2)).b();
                        interfaceC2641noA.a(dgB, setC);
                        int i2 = C1166Ls.n;
                        C1166Ls c1166LsB = ((C1141Ks) ((C1141Ks) ((C1141Ks) new C1141Ks().a(c0684j2A)).a(AbstractC1639am.c(dgB.b()))).b(next2)).b();
                        if (next.D()) {
                            final C3332x4 c3332x4A = interfaceC2641noA.a(c2409kl, b4S, c0702m.Y());
                            InterfaceC2641no interfaceC2641noA2 = c3332x4A.a(c2409kl);
                            interfaceC2641noA2.add(c1166LsB);
                            C3332x4 c3332x4 = (C3332x4) com.android.tools.r8.utils.L1.b((ListIterator) b4S, new Predicate() { // from class: com.android.tools.r8.internal.Lj$$ExternalSyntheticLambda5
                                @Override // java.util.function.Predicate
                                public final boolean test(Object obj) {
                                    return C1158Lj.a(c3332x4A, (C3332x4) obj);
                                }
                            });
                            if (!g && c3332x4 == null) {
                                throw new AssertionError();
                            }
                            b4S.next();
                            interfaceC2641noA = interfaceC2641noA2;
                        } else {
                            interfaceC2641noA.add(c1166LsB);
                        }
                    }
                }
            }
        }
        if (setC.isEmpty()) {
            return;
        }
        new C3030t00(c0702m, false).a((Iterable) setC);
    }

    public final void a(final C3012sl c3012sl, ExecutorService executorService, com.android.tools.r8.utils.y3 y3Var) {
        y3Var.a("[Proto] Post optimize dynamic methods");
        final EK ekA = EK.a(CN.a(new InterfaceC3877c1() { // from class: com.android.tools.r8.internal.Lj$$ExternalSyntheticLambda3
            @Override // com.android.tools.r8.utils.InterfaceC3877c1
            public final void forEach(Consumer consumer) {
                this.f$0.a(consumer);
            }
        }), VE.a(), this.a);
        ekA.a(new DK() { // from class: com.android.tools.r8.internal.Lj$$ExternalSyntheticLambda4
            @Override // com.android.tools.r8.internal.DK
            public final void a(com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob) {
                C1158Lj.a(c3012sl, ekA, o5Var, c1230Ob);
            }
        }, executorService);
        y3Var.c();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C3012sl c3012sl, EK ek, com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob) {
        c3012sl.b(o5Var, com.android.tools.r8.ir.optimize.info.q.b(), ek, c1230Ob);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(final Consumer consumer) {
        final C0722p1 c0722p1A = this.a.a();
        this.a.i().a(this.d.i, new Consumer() { // from class: com.android.tools.r8.internal.Lj$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(c0722p1A, consumer, (C0729q2) obj);
            }
        }, new Consumer() { // from class: com.android.tools.r8.internal.Lj$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1158Lj.a((JA) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C0722p1 c0722p1, Consumer consumer, C0729q2 c0729q2) {
        C0767v2 c0767v2 = c0729q2.d;
        VP vp = this.d;
        com.android.tools.r8.graph.o5 o5VarF = c0729q2.f(c0722p1.a(c0767v2, vp.C, vp.x));
        if (o5VarF != null) {
            consumer.accept(o5VarF);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(JA ja) {
        if (!g) {
            throw new AssertionError();
        }
    }

    public static AbstractC1305Qs a(C2409kl c2409kl, VP vp) {
        for (AbstractC2412ko abstractC2412ko : c2409kl.r()) {
            if (abstractC2412ko.Y1()) {
                AbstractC1305Qs abstractC1305QsB0 = abstractC2412ko.b0();
                C0684j2 c0684j2S2 = abstractC1305QsB0.S2();
                if (c0684j2S2.b(vp.F) || c0684j2S2 == vp.G) {
                    return abstractC1305QsB0;
                }
            }
        }
        return null;
    }
}
