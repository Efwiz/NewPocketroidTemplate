package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0725p4;
import com.android.tools.r8.graph.C0737r2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.shaking.C3804u;
import com.android.tools.r8.utils.AbstractC3896g;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Yn, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1536Yn {
    static final /* synthetic */ boolean d = true;
    private final C0702m a;
    private final C0722p1 b;
    private final ArrayList c = new ArrayList();

    public C1536Yn(C0702m c0702m) {
        this.a = c0702m;
        this.b = c0702m.a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(DG dg, AbstractC2412ko abstractC2412ko) {
        return abstractC2412ko == dg;
    }

    public final ArrayList a() {
        return this.c;
    }

    public final void a(C2409kl c2409kl, com.android.tools.r8.graph.o5 o5Var, TE te, C1230Ob c1230Ob) {
        DG dgT0;
        if (o5Var.e().N0()) {
            return;
        }
        IdentityHashMap identityHashMap = new IdentityHashMap();
        com.android.tools.r8.androidapi.h hVarO = this.a.o();
        C2561ml c2561ml = new C2561ml(c2409kl);
        while (c2561ml.hasNext()) {
            AbstractC2412ko abstractC2412ko = (AbstractC2412ko) c2561ml.next();
            C1166Ls c1166LsZ = abstractC2412ko.Z();
            if (c1166LsZ != null) {
                C0684j2 c0684j2S2 = c1166LsZ.S2();
                if (c0684j2S2.b(this.b)) {
                    S10 s10T0 = c1166LsZ.T0();
                    if (!s10T0.i() && (dgT0 = s10T0.q().t0()) != null) {
                        com.android.tools.r8.androidapi.h hVarA = this.a.d().a(c0684j2S2, hVarO);
                        if (!hVarO.c(hVarA) && !AbstractC3896g.b(o5Var.a(), hVarA)) {
                            com.android.tools.r8.graph.W0 w0A = a(c1166LsZ.S2(), hVarA, te, c1230Ob);
                            List listH1 = abstractC2412ko.h1();
                            int i = C1721bt.m;
                            ArrayList arrayList = (ArrayList) listH1;
                            C1721bt c1721btB = ((C1646at) ((C1646at) ((C1646at) ((C1646at) new C1646at().a(w0A.getReference())).b(abstractC2412ko)).a(c2409kl, dgT0.a(), null)).a(arrayList.subList(1, arrayList.size()))).b();
                            c2561ml.a(c1721btB, (Set) null);
                            identityHashMap.put(dgT0, c1721btB.b());
                        }
                    }
                }
            }
        }
        if (identityHashMap.isEmpty()) {
            return;
        }
        C2561ml c2561ml2 = new C2561ml(c2409kl);
        Set setC = OU.c();
        while (c2561ml2.hasNext()) {
            AbstractC2412ko abstractC2412ko2 = (AbstractC2412ko) c2561ml2.next();
            if (abstractC2412ko2.p2()) {
                DG dgT1 = abstractC2412ko2.t0();
                S10 s10 = (S10) identityHashMap.get(dgT1);
                if (s10 == null) {
                    continue;
                } else {
                    dgT1.b().g(s10);
                    setC.add(s10);
                    if (a(c2561ml2, dgT1, s10)) {
                        c2561ml2.s();
                    } else {
                        com.android.tools.r8.androidapi.h hVarA2 = this.a.d().a(dgT1.J2(), hVarO);
                        if (!d && !hVarA2.y()) {
                            throw new AssertionError();
                        }
                        com.android.tools.r8.graph.W0 w0A2 = a(dgT1.J2(), hVarA2, te, c1230Ob);
                        int i2 = C1721bt.m;
                        c2561ml2.a(((C1646at) ((C1646at) new C1646at().a(w0A2.getReference())).b(abstractC2412ko2)).b(), (Set) null);
                    }
                }
            }
        }
        boolean z = d;
        if (!z && setC.isEmpty()) {
            throw new AssertionError();
        }
        new C3030t00(this.a, false).a(setC);
        if (this.a.q()) {
            com.android.tools.r8.graph.W0 w0E = o5Var.e();
            if (w0E.Y0().y()) {
                C0702m c0702m = this.a;
                C3804u c3804u = new C3804u(c0702m, o5Var, c0702m.d());
                c2409kl.a(o5Var, c3804u);
                com.android.tools.r8.androidapi.h hVarF = c3804u.f();
                if (!z && !hVarF.y()) {
                    throw new AssertionError();
                }
                w0E.g(hVarF);
            }
        }
    }

    private boolean a(C2561ml c2561ml, final DG dg, S10 s10) {
        C1721bt c1721btG0 = s10.q().g0();
        boolean z = d;
        if (!z && c1721btG0 == null) {
            throw new AssertionError();
        }
        final AbstractC1915eM abstractC1915eMY0 = dg.Y0();
        AbstractC2412ko abstractC2412ko = (AbstractC2412ko) c2561ml.a(new Predicate() { // from class: com.android.tools.r8.internal.Yn$$ExternalSyntheticLambda6
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.a(abstractC1915eMY0, (AbstractC2412ko) obj);
            }
        });
        c2561ml.c(new Predicate() { // from class: com.android.tools.r8.internal.Yn$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C1536Yn.a(dg, (AbstractC2412ko) obj);
            }
        });
        AbstractC2412ko abstractC2412ko2 = (AbstractC2412ko) c2561ml.next();
        if (!z && abstractC2412ko2 != dg) {
            throw new AssertionError();
        }
        if (abstractC2412ko == c1721btG0) {
            AbstractC1915eM abstractC1915eMY1 = abstractC2412ko.Y0();
            if (abstractC1915eMY1.o() || abstractC1915eMY0.e() == abstractC1915eMY1.e()) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(AbstractC1915eM abstractC1915eM, AbstractC2412ko abstractC2412ko) {
        if (!abstractC2412ko.y1() && !(abstractC2412ko instanceof C1232Od)) {
            return true;
        }
        AbstractC1915eM abstractC1915eMY0 = abstractC2412ko.Y0();
        return (abstractC1915eMY0.o() || abstractC1915eM.e() == abstractC1915eMY0.e()) ? false : true;
    }

    private com.android.tools.r8.graph.W0 a(final C0767v2 c0767v2, final com.android.tools.r8.androidapi.h hVar, TE te, C1230Ob c1230Ob) {
        final C0737r2 c0737r2B = this.a.a().b(this.b.D1, new C0767v2[0]);
        com.android.tools.r8.graph.o5 o5VarB = this.a.D().b(new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.internal.Yn$$ExternalSyntheticLambda4
            @Override // com.android.tools.r8.synthesis.C
            public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
                return l.R;
            }
        }, c1230Ob.a(), this.a, new Consumer() { // from class: com.android.tools.r8.internal.Yn$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(c0737r2B, hVar, c0767v2, (com.android.tools.r8.synthesis.H) obj);
            }
        });
        te.b().a(o5VarB, c1230Ob.b());
        synchronized (this.c) {
            this.c.add(o5VarB);
        }
        return o5VarB.e();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C0737r2 c0737r2, com.android.tools.r8.androidapi.h hVar, final C0767v2 c0767v2, com.android.tools.r8.synthesis.H h) {
        h.a(C0725p4.Q()).a(c0737r2).b(this.a.o()).a(hVar).a(new com.android.tools.r8.synthesis.G() { // from class: com.android.tools.r8.internal.Yn$$ExternalSyntheticLambda2
            @Override // com.android.tools.r8.synthesis.G
            public final com.android.tools.r8.graph.W a(C0684j2 c0684j2) {
                return this.f$0.a(c0767v2, c0684j2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ com.android.tools.r8.graph.W a(C0767v2 c0767v2, C0684j2 c0684j2) {
        return EG.a(this.a, c0684j2.B0(), c0767v2).b();
    }

    private com.android.tools.r8.graph.W0 a(final C0684j2 c0684j2, final com.android.tools.r8.androidapi.h hVar, TE te, C1230Ob c1230Ob) {
        final C0737r2 c0737r2A = this.a.a().a(c0684j2.B0(), c0684j2.G0());
        com.android.tools.r8.graph.o5 o5VarB = this.a.D().b(new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.internal.Yn$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.synthesis.C
            public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
                return l.R;
            }
        }, c1230Ob.a(), this.a, new Consumer() { // from class: com.android.tools.r8.internal.Yn$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(c0737r2A, hVar, c0684j2, (com.android.tools.r8.synthesis.H) obj);
            }
        });
        te.b().a(o5VarB, c1230Ob.b());
        synchronized (this.c) {
            this.c.add(o5VarB);
            C0767v2 c0767v2B0 = c0684j2.B0();
            C0702m c0702m = this.a;
            WH whB = WH.b();
            c0767v2B0.getClass();
            C1229Oa c1229OaB = AbstractC3472z00.a(c0767v2B0, whB, (C0702m<?>) c0702m).b();
            com.android.tools.r8.ir.optimize.info.r rVarA = com.android.tools.r8.ir.optimize.info.n.a();
            C0702m c0702m2 = this.a;
            C2557mh c2557mhA = AbstractC1154Lf.a(c1229OaB);
            rVarA.getClass();
            rVarA.a(c0702m2, o5VarB.e(), c2557mhA);
        }
        return o5VarB.e();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C0737r2 c0737r2, com.android.tools.r8.androidapi.h hVar, final C0684j2 c0684j2, com.android.tools.r8.synthesis.H h) {
        h.a(C0725p4.Q()).a(c0737r2).b(this.a.o()).a(hVar).a(new com.android.tools.r8.synthesis.G() { // from class: com.android.tools.r8.internal.Yn$$ExternalSyntheticLambda3
            @Override // com.android.tools.r8.synthesis.G
            public final com.android.tools.r8.graph.W a(C0684j2 c0684j3) {
                return this.f$0.a(c0684j2, c0684j3);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ com.android.tools.r8.graph.W a(C0684j2 c0684j2, C0684j2 c0684j3) {
        return C1071Ii.a(this.a.a()).a(c0684j2).d(c0684j3).a();
    }
}
