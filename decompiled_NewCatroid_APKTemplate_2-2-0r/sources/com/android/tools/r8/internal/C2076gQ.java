package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0744s2;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.utils.AbstractC3918j3;
import com.android.tools.r8.utils.AbstractC3934m3;
import com.android.tools.r8.utils.InterfaceC3923k3;
import com.android.tools.r8.utils.InterfaceC3949p3;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.OptionalInt;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.gQ, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C2076gQ {
    static final /* synthetic */ boolean c = true;
    private final AP a;
    private final VP b;

    C2076gQ(AP ap, VP vp) {
        this.a = ap;
        this.b = vp;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(AG ag, AbstractC2412ko abstractC2412ko) {
        return abstractC2412ko == ag;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public S10 b() throws C2000fQ {
        throw new C2000fQ();
    }

    private static AbstractC3934m3 b(S10 s10) throws C2000fQ {
        if (s10.i()) {
            throw new C2000fQ();
        }
        final AG agR0 = s10.b.r0();
        C1385Ts c1385TsE0 = s10.b.e0();
        if (agR0 == null && c1385TsE0 == null) {
            throw new C2000fQ();
        }
        if (c1385TsE0 != null) {
            if (s10.D()) {
                return AbstractC3934m3.a(c1385TsE0.h1().iterator());
            }
            throw new C2000fQ();
        }
        S10 s10M = agR0.J2().m();
        if (s10M.i() || !s10M.b.B1()) {
            throw new C2000fQ();
        }
        if (s10M.b.E().N2() != s10.h0().size() - 1) {
            throw new C2000fQ();
        }
        InterfaceC2488lo interfaceC2488loR = agR0.h().R();
        interfaceC2488loR.a(new Predicate() { // from class: com.android.tools.r8.internal.gQ$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C2076gQ.a(agR0, (AbstractC2412ko) obj);
            }
        });
        return new C1923eQ(interfaceC2488loR, s10);
    }

    public final EP a(com.android.tools.r8.graph.o5 o5Var, AbstractC1305Qs abstractC1305Qs) {
        if (!c) {
            VP vp = this.b;
            C0684j2 c0684j2S2 = abstractC1305Qs.S2();
            if (!c0684j2S2.b(vp.F) && c0684j2S2 != vp.G) {
                throw new AssertionError();
            }
        }
        S10 s10A = ZP.a(abstractC1305Qs, this.b);
        VP vp2 = this.b;
        if (!ZP.a) {
            C0684j2 c0684j2S3 = abstractC1305Qs.S2();
            if (!c0684j2S3.b(vp2.F) && c0684j2S3 != vp2.G) {
                throw new AssertionError();
            }
        }
        int iA = com.android.tools.r8.utils.M.a(abstractC1305Qs.W1());
        return a(o5Var, s10A, ((S10) ((ArrayList) abstractC1305Qs.h1()).get(iA + 2)).m());
    }

    public final EP a(com.android.tools.r8.graph.o5 o5Var, S10 s10, S10 s11) {
        OptionalInt optionalIntEmpty;
        try {
            CP cpA = EP.a(o5Var);
            AbstractC3918j3<C2000fQ> abstractC3918j3A = a(s10);
            int iA = abstractC3918j3A.a(new InterfaceC3923k3() { // from class: com.android.tools.r8.internal.gQ$$ExternalSyntheticLambda0
                @Override // com.android.tools.r8.utils.InterfaceC3923k3
                public final int a() {
                    return this.f$0.a();
                }
            });
            cpA.a(iA);
            int iA2 = abstractC3918j3A.a(new InterfaceC3923k3() { // from class: com.android.tools.r8.internal.gQ$$ExternalSyntheticLambda0
                @Override // com.android.tools.r8.utils.InterfaceC3923k3
                public final int a() {
                    return this.f$0.a();
                }
            });
            if (iA2 == 0) {
                return cpA.a();
            }
            int iA3 = abstractC3918j3A.a(new InterfaceC3923k3() { // from class: com.android.tools.r8.internal.gQ$$ExternalSyntheticLambda0
                @Override // com.android.tools.r8.utils.InterfaceC3923k3
                public final int a() {
                    return this.f$0.a();
                }
            });
            int iA4 = abstractC3918j3A.a(new InterfaceC3923k3() { // from class: com.android.tools.r8.internal.gQ$$ExternalSyntheticLambda0
                @Override // com.android.tools.r8.utils.InterfaceC3923k3
                public final int a() {
                    return this.f$0.a();
                }
            });
            for (int i = 4; i < 10; i++) {
                abstractC3918j3A.a(new InterfaceC3923k3() { // from class: com.android.tools.r8.internal.gQ$$ExternalSyntheticLambda0
                    @Override // com.android.tools.r8.utils.InterfaceC3923k3
                    public final int a() {
                        return this.f$0.a();
                    }
                });
            }
            AbstractC3934m3 abstractC3934m3B = b(s11);
            for (int i2 = 0; i2 < iA3; i2++) {
                LP lpA = a((S10) abstractC3934m3B.a(new InterfaceC3949p3() { // from class: com.android.tools.r8.internal.gQ$$ExternalSyntheticLambda1
                    @Override // com.android.tools.r8.utils.InterfaceC3949p3
                    public final Object get() {
                        return this.f$0.b();
                    }
                }), o5Var);
                if (lpA.f()) {
                    LP lpA2 = a((S10) abstractC3934m3B.a(new InterfaceC3949p3() { // from class: com.android.tools.r8.internal.gQ$$ExternalSyntheticLambda1
                        @Override // com.android.tools.r8.utils.InterfaceC3949p3
                        public final Object get() {
                            return this.f$0.b();
                        }
                    }), o5Var);
                    if (lpA2.f()) {
                        cpA.a(lpA.b(), lpA2.b());
                    } else {
                        throw new C2000fQ();
                    }
                } else {
                    throw new C2000fQ();
                }
            }
            for (int i3 = 0; i3 < iA4; i3++) {
                LP lpA3 = a((S10) abstractC3934m3B.a(new InterfaceC3949p3() { // from class: com.android.tools.r8.internal.gQ$$ExternalSyntheticLambda1
                    @Override // com.android.tools.r8.utils.InterfaceC3949p3
                    public final Object get() {
                        return this.f$0.b();
                    }
                }), o5Var);
                if (lpA3.f()) {
                    cpA.a(lpA3.b());
                } else {
                    throw new C2000fQ();
                }
            }
            boolean zA = ZP.a(iA);
            for (int i4 = 0; i4 < iA2; i4++) {
                int iA5 = abstractC3918j3A.a(new InterfaceC3923k3() { // from class: com.android.tools.r8.internal.gQ$$ExternalSyntheticLambda0
                    @Override // com.android.tools.r8.utils.InterfaceC3923k3
                    public final int a() {
                        return this.f$0.a();
                    }
                });
                int iA6 = abstractC3918j3A.a(new InterfaceC3923k3() { // from class: com.android.tools.r8.internal.gQ$$ExternalSyntheticLambda0
                    @Override // com.android.tools.r8.utils.InterfaceC3923k3
                    public final int a() {
                        return this.f$0.a();
                    }
                });
                C3503zP c3503zPA = this.a.a(iA6);
                if (c3503zPA.n() == iA6) {
                    if (c3503zPA.a(zA)) {
                        optionalIntEmpty = OptionalInt.of(abstractC3918j3A.a(new InterfaceC3923k3() { // from class: com.android.tools.r8.internal.gQ$$ExternalSyntheticLambda0
                            @Override // com.android.tools.r8.utils.InterfaceC3923k3
                            public final int a() {
                                return this.f$0.a();
                            }
                        }));
                    } else {
                        optionalIntEmpty = OptionalInt.empty();
                    }
                    int iA7 = c3503zPA.a(zA, this.a);
                    try {
                        ArrayList arrayList = new ArrayList(iA7);
                        Iterator it = abstractC3934m3B.a(iA7).iterator();
                        while (it.hasNext()) {
                            arrayList.add(a((S10) it.next(), o5Var));
                        }
                        cpA.a(new C3355xP(iA5, c3503zPA, optionalIntEmpty, arrayList));
                    } catch (NoSuchElementException unused) {
                        throw new C2000fQ();
                    }
                } else {
                    throw new C1367Tb("Unexpected proto field type `" + iA6 + "`");
                }
            }
            if (!abstractC3918j3A.a() && !abstractC3934m3B.a()) {
                return cpA.a();
            }
            throw new C2000fQ();
        } catch (DP | C2000fQ unused2) {
            if (c) {
                return null;
            }
            throw new AssertionError();
        }
    }

    private static LP a(S10 s10, com.android.tools.r8.graph.o5 o5Var) throws C2000fQ {
        S10 s10M = s10.m();
        if (!s10M.i()) {
            AbstractC2412ko abstractC2412ko = s10M.b;
            abstractC2412ko.getClass();
            if (abstractC2412ko instanceof C3446yc) {
                return new XP(abstractC2412ko.A().K2());
            }
            if (abstractC2412ko instanceof C1033Hc) {
                C1033Hc c1033HcF = abstractC2412ko.F();
                com.android.tools.r8.graph.T0 t0A = o5Var.a().a(c1033HcF.J2());
                if (t0A != null) {
                    return new C2975sC(t0A.getReference());
                }
                return new C1205Nd(c1033HcF.J2(), o5Var.t());
            }
            if (abstractC2412ko instanceof C2477lf) {
                C2477lf c2477lfJ = abstractC2412ko.J();
                AbstractC0744s2 abstractC0744s2J2 = c2477lfJ.J2();
                AbstractC2525mG abstractC2525mGK2 = c2477lfJ.K2();
                if (abstractC0744s2J2.x0()) {
                    abstractC2525mGK2.getClass();
                    if (abstractC2525mGK2 instanceof C1294Qh) {
                        com.android.tools.r8.graph.Y0 y0T0 = abstractC0744s2J2.t0();
                        if (o5Var.a().b(y0T0) != null) {
                            return new C2975sC(y0T0);
                        }
                        return new C1205Nd(y0T0.f, o5Var.t());
                    }
                }
            } else if (abstractC2412ko instanceof C1721bt) {
                C1721bt c1721btG0 = abstractC2412ko.g0();
                if (((ArrayList) c1721btG0.J2()).isEmpty()) {
                    return new MP(c1721btG0.S2());
                }
            } else if (abstractC2412ko instanceof QW) {
                return new NP(abstractC2412ko.F0().getField());
            }
        }
        throw new C2000fQ();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int a() throws C2000fQ {
        throw new C2000fQ();
    }

    public static AbstractC3918j3<C2000fQ> a(S10 s10) throws C2000fQ {
        if (!s10.i()) {
            AbstractC2412ko abstractC2412ko = s10.b;
            abstractC2412ko.getClass();
            if (abstractC2412ko instanceof C1033Hc) {
                return new C1841dQ(s10.b.F().J2());
            }
        }
        throw new C2000fQ();
    }
}
