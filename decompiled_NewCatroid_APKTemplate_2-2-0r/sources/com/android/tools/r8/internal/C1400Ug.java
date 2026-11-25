package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0725p4;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0737r2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.utils.AbstractC3938n2;
import com.android.tools.r8.utils.C3879c3;
import java.util.ArrayList;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Ug, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1400Ug {
    static final /* synthetic */ boolean h = true;
    private final C1293Qg a;
    private final C0702m b;
    private final AN c;
    private final C0722p1 d;
    private final C3226vg e;
    private final Set f;
    private final C1502Xg g;

    C1400Ug(C0702m c0702m, AN an, C3226vg c3226vg, AbstractC1048Hm abstractC1048Hm, C1502Xg c1502Xg) {
        this.b = c0702m;
        this.c = an;
        this.e = c3226vg;
        this.d = c0702m.a();
        this.a = C1319Rg.a(c0702m).a(c3226vg.b());
        this.f = abstractC1048Hm;
        this.g = c1502Xg;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(C3012sl c3012sl, EK ek, com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob) {
        c3012sl.b(o5Var, com.android.tools.r8.ir.optimize.info.r.b(), ek, c1230Ob);
    }

    private void b(final C3012sl c3012sl, ExecutorService executorService) {
        final com.android.tools.r8.graph.T0 t0S = ((C3733i) this.b.f()).c(this.b.a().r4.b).s();
        C3879c3.a(this.f, new Consumer() { // from class: com.android.tools.r8.internal.Ug$$ExternalSyntheticLambda10
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.b(c3012sl, t0S, (C0729q2) obj);
            }
        }, executorService);
    }

    final C1372Tg c(C3012sl c3012sl, ExecutorService executorService) {
        com.android.tools.r8.graph.t5 t5VarA = com.android.tools.r8.graph.u5.a();
        b(c3012sl, executorService);
        for (C0729q2 c0729q2 : ((C3733i) this.b.f()).d()) {
            C3226vg c3226vg = this.e;
            c3226vg.getClass();
            if (c3226vg.b(c0729q2.getType())) {
                C1502Xg c1502Xg = this.g;
                c1502Xg.getClass();
                C3487zC c3487zCA = c1502Xg.a(c0729q2.getType());
                Collection collectionA = a(c0729q2, c3487zCA, t5VarA);
                Collection collectionB = b(c0729q2, c3487zCA, t5VarA);
                c0729q2.O0();
                c0729q2.T0();
                c0729q2.Y().b();
                c0729q2.Y().c();
                c3487zCA.a().d(collectionB);
                c3487zCA.a().e(collectionA);
            } else {
                c0729q2.Y().d(new Function() { // from class: com.android.tools.r8.internal.Ug$$ExternalSyntheticLambda3
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return this.f$0.a((com.android.tools.r8.graph.W0) obj);
                    }
                });
                c0729q2.c1().a(new Function() { // from class: com.android.tools.r8.internal.Ug$$ExternalSyntheticLambda4
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return this.f$0.a((com.android.tools.r8.graph.T0) obj);
                    }
                });
            }
        }
        C1319Rg c1319RgA = this.a.a(this.b);
        this.b.b((DH) c1319RgA);
        c3012sl.b.b();
        return new C1372Tg(a(c3012sl, executorService), c1319RgA, t5VarA.a());
    }

    private C3011sk a(final C3012sl c3012sl, ExecutorService executorService) {
        final C3011sk c3011skL = C3011sk.l();
        final C1257Pb c1257PbP = this.b.p();
        final CK ckA = EK.a(VE.a(), c1257PbP);
        this.c.a(new BiPredicate() { // from class: com.android.tools.r8.internal.Ug$$ExternalSyntheticLambda5
            @Override // java.util.function.BiPredicate
            public final boolean test(Object obj, Object obj2) {
                return this.f$0.a((com.android.tools.r8.graph.o5) obj, (Set) obj2);
            }
        });
        this.c.a(new BiConsumer() { // from class: com.android.tools.r8.internal.Ug$$ExternalSyntheticLambda6
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(c1257PbP, c3011skL, ckA, (com.android.tools.r8.graph.o5) obj, (Set) obj2);
            }
        });
        final EK ekA = ckA.a();
        ekA.a(new DK() { // from class: com.android.tools.r8.internal.Ug$$ExternalSyntheticLambda7
            @Override // com.android.tools.r8.internal.DK
            public final void a(com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob) {
                C1400Ug.a(c3012sl, ekA, o5Var, c1230Ob);
            }
        }, executorService);
        return c3011skL;
    }

    private Collection b(C0729q2 c0729q2, final C3487zC c3487zC, final com.android.tools.r8.graph.t5 t5Var) {
        final LinkedHashMap linkedHashMap = new LinkedHashMap(c0729q2.Y().l() + c3487zC.a().Y().l());
        c3487zC.a().f(new Consumer() { // from class: com.android.tools.r8.internal.Ug$$ExternalSyntheticLambda21
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1400Ug.a(linkedHashMap, (com.android.tools.r8.graph.W0) obj);
            }
        });
        c0729q2.l(new Consumer() { // from class: com.android.tools.r8.internal.Ug$$ExternalSyntheticLambda22
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(t5Var, c3487zC, linkedHashMap, (com.android.tools.r8.graph.o5) obj);
            }
        });
        return linkedHashMap.values();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(com.android.tools.r8.graph.o5 o5Var, Set set) {
        boolean z;
        Set set2 = this.f;
        if (set2.isEmpty()) {
            z = false;
        } else {
            Iterator it = set.iterator();
            while (it.hasNext()) {
                if (set2.contains(it.next())) {
                    z = true;
                }
            }
            z = false;
        }
        return !z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C1257Pb c1257Pb, G4 g4, CK ck, final com.android.tools.r8.graph.o5 o5Var, Set set) {
        final E9 e9A = o5Var.B().n().a();
        C0722p1 c0722p1 = this.d;
        C0767v2 c0767v2 = c0722p1.D1;
        C0767v2[] c0767v2Arr = o5Var.D().a;
        int iC = e9A.c();
        C0767v2 c0767v3 = this.d.A1;
        Object[] objArr = (Object[]) c0767v2Arr.clone();
        objArr[iC] = c0767v3;
        final C0737r2 c0737r2B = c0722p1.b(c0767v2, (C0767v2[]) objArr);
        com.android.tools.r8.graph.o5 o5VarB = this.b.D().b(new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.internal.Ug$$ExternalSyntheticLambda18
            @Override // com.android.tools.r8.synthesis.C
            public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
                return l.z;
            }
        }, c1257Pb.a(o5Var).a(), this.b, new Consumer() { // from class: com.android.tools.r8.internal.Ug$$ExternalSyntheticLambda19
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(o5Var, e9A, c0737r2B, (com.android.tools.r8.synthesis.H) obj);
            }
        });
        g4.put(o5Var.getReference(), o5VarB.getReference());
        this.a.a(o5Var, o5VarB);
        ck.a(o5VarB);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(final com.android.tools.r8.graph.o5 o5Var, E9 e9, C0737r2 c0737r2, com.android.tools.r8.synthesis.H h2) {
        com.android.tools.r8.synthesis.H hA = h2.a(C0725p4.Q());
        com.android.tools.r8.graph.W0 w0E = o5Var.e();
        hA.a(w0E.p1() ? w0E.Z0() : null).b(this.b.o()).a(this.b.o()).a(new com.android.tools.r8.synthesis.G() { // from class: com.android.tools.r8.internal.Ug$$ExternalSyntheticLambda20
            @Override // com.android.tools.r8.synthesis.G
            public final com.android.tools.r8.graph.W a(C0684j2 c0684j2) {
                return C1400Ug.a(o5Var, c0684j2);
            }
        }).a(com.android.tools.r8.ir.optimize.info.d.b.F().a(e9)).a(c0737r2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.tools.r8.graph.W a(com.android.tools.r8.graph.o5 o5Var, C0684j2 c0684j2) {
        return new F9(o5Var);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:111:0x0312  */
    /* JADX WARN: Code duplicated, block: B:113:0x0322  */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r9v0 */
    /* JADX WARN: Type inference failed for: r9v1, types: [com.android.tools.r8.graph.X, com.android.tools.r8.internal.Wj] */
    /* JADX WARN: Type inference failed for: r9v13 */
    /* JADX WARN: Type inference failed for: r9v2 */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void b(C3012sl c3012sl, com.android.tools.r8.graph.T0 t0, C0729q2 c0729q2) {
        com.android.tools.r8.graph.m5 m5VarU;
        int iG;
        if (!c0729q2.p1()) {
            if (!h && !c0729q2.N1().isEmpty()) {
                throw new AssertionError();
            }
            return;
        }
        com.android.tools.r8.graph.o5 o5VarX1 = c0729q2.X1();
        C3150ug c3150ugA = this.e.a(c0729q2);
        C1502Xg c1502Xg = this.g;
        c1502Xg.getClass();
        C3487zC c3487zCA = c1502Xg.a(c0729q2.getType());
        C2409kl c2409klA = o5VarX1.a(this.b);
        B4 b4S = c2409klA.s();
        IdentityHashMap identityHashMap = new IdentityHashMap();
        while (true) {
            ?? r9 = 0;
            if (b4S.hasNext()) {
                InterfaceC2641no interfaceC2641noA = b4S.next().a(c2409klA);
                while (interfaceC2641noA.hasNext()) {
                    AbstractC2412ko next = interfaceC2641noA.next();
                    if (identityHashMap.containsKey(next)) {
                        Optional optional = (Optional) identityHashMap.remove(next);
                        if (optional.isPresent()) {
                            interfaceC2641noA.d((AbstractC2412ko) optional.get());
                            interfaceC2641noA.previous();
                        }
                        interfaceC2641noA.s();
                    } else {
                        next.getClass();
                        if (next instanceof C3446yc) {
                            C3446yc c3446ycA = next.A();
                            if (c3446ycA.J2() == c0729q2.getType()) {
                                final ArrayList arrayList = new ArrayList();
                                for (AbstractC2412ko abstractC2412ko : c3446ycA.b().a(C1552Zd.a())) {
                                    if (abstractC2412ko.f2()) {
                                        C2115gt c2115gtI0 = abstractC2412ko.i0();
                                        if (c2115gtI0.S2() == this.b.a().p4.a) {
                                            arrayList.add(c2115gtI0);
                                        }
                                    }
                                }
                                if (!arrayList.isEmpty()) {
                                    C3376xc c3376xcA = new C3376xc().a(c3487zCA.c());
                                    C0702m c0702m = this.b;
                                    C3446yc c3446ycB = ((C3376xc) ((C3376xc) c3376xcA.a(c2409klA, AbstractC3472z00.a(c0702m.a().m2, WH.b(), (C0702m<?>) c0702m).b(), r9)).a(c3446ycA.Y0())).b();
                                    interfaceC2641noA.add(c3446ycB);
                                    c3446ycA.b().a(new Predicate() { // from class: com.android.tools.r8.internal.Ug$$ExternalSyntheticLambda12
                                        @Override // java.util.function.Predicate
                                        public final boolean test(Object obj) {
                                            return arrayList.contains((AbstractC2412ko) obj);
                                        }
                                    }, c3446ycB.b());
                                }
                            }
                        } else {
                            if (next.p2()) {
                                DG dgT0 = next.t0();
                                if (AbstractC2964s2.a(this.b.E(), dgT0.J2()) == c0729q2.getType()) {
                                    C1166Ls c1166LsA = AbstractC2871ql.a(this.b.a(), dgT0.b());
                                    boolean z = h;
                                    if (!z && c1166LsA == null) {
                                        throw new AssertionError();
                                    }
                                    C0684j2 c0684j2S2 = c1166LsA.S2();
                                    AbstractC1476Wj abstractC1476WjE = this.b.E();
                                    abstractC1476WjE.getClass();
                                    KE keA = abstractC1476WjE.a(c0684j2S2, o5VarX1.getReference(), EnumC1953et.c, (AbstractC1476Wj) r9);
                                    if (keA.b() != c0684j2S2) {
                                        ArrayList arrayList2 = new ArrayList(((ArrayList) c1166LsA.J2()).size());
                                        for (int i = 0; i < ((ArrayList) c1166LsA.J2()).size(); i++) {
                                            S10 s10C = c1166LsA.c(i);
                                            if (!keA.e().a().a(i).c()) {
                                                arrayList2.add(s10C);
                                            }
                                        }
                                        C1166Ls c1166LsB = ((C1141Ks) ((C1141Ks) new C1141Ks().a(arrayList2)).a((C0684j2) keA.b())).b();
                                        identityHashMap.put(c1166LsA, Optional.of(c1166LsB));
                                        c1166LsA = c1166LsB;
                                    } else {
                                        if (!z && !keA.e().g()) {
                                            throw new AssertionError();
                                        }
                                        identityHashMap.put(c1166LsA, Optional.empty());
                                    }
                                    com.android.tools.r8.graph.o5 o5VarF = c0729q2.f((C0684j2) keA.b());
                                    boolean z2 = h;
                                    if (!z2 && o5VarF == null) {
                                        throw new AssertionError();
                                    }
                                    InterfaceC0989Fn interfaceC0989FnA = o5VarF.e().i1().a(c1166LsA).b().a(t0);
                                    if (interfaceC0989FnA.h()) {
                                        S10 s10M = c1166LsA.c(interfaceC0989FnA.f().b()).m();
                                        if (!z2 && !s10M.b(new C1406Ug$$ExternalSyntheticLambda13())) {
                                            throw new AssertionError();
                                        }
                                        iG = s10M.q().E().N2();
                                    } else {
                                        if (!z2 && !interfaceC0989FnA.g()) {
                                            throw new AssertionError();
                                        }
                                        if (!z2) {
                                            AbstractC3292wV abstractC3292wVJ = interfaceC0989FnA.j();
                                            abstractC3292wVJ.getClass();
                                            if (!(abstractC3292wVJ instanceof C2853qV)) {
                                                throw new AssertionError();
                                            }
                                        }
                                        iG = interfaceC0989FnA.j().e().G();
                                    }
                                    interfaceC2641noA.d(new FG(c0729q2.getType(), iG, c2409klA.a((AbstractC3472z00) C1229Oa.b(c0729q2.getType(), WH.b(), (C0702m<? extends C0680j>) this.b))));
                                }
                            } else if (next.c()) {
                                com.android.tools.r8.graph.Y0 y0C = this.b.E().c((AbstractC1476Wj) null, next.G0().getField());
                                if (y0C.B0() == c0729q2.getType() && (m5VarU = ((C3733i) this.b.f()).c(y0C).u()) != null) {
                                    if (m5VarU.getAccessFlags().o()) {
                                        c3150ugA.getClass();
                                        if (!c3150ugA.b.containsKey(m5VarU.getReference())) {
                                            if (c3150ugA.c.contains(m5VarU.getReference())) {
                                                if (!m5VarU.e().X0().i()) {
                                                    interfaceC2641noA.s();
                                                }
                                            }
                                        } else if (!m5VarU.e().X0().i()) {
                                            interfaceC2641noA.s();
                                        }
                                    } else {
                                        interfaceC2641noA.s();
                                    }
                                }
                            }
                            r9 = 0;
                        }
                    }
                }
            } else {
                if (!identityHashMap.isEmpty()) {
                    C2561ml c2561ml = new C2561ml(c2409klA);
                    while (c2561ml.hasNext()) {
                        AbstractC2412ko abstractC2412ko2 = (AbstractC2412ko) c2561ml.next();
                        if (identityHashMap.containsKey(abstractC2412ko2)) {
                            Optional optional2 = (Optional) identityHashMap.get(abstractC2412ko2);
                            if (optional2.isPresent()) {
                                c2561ml.a((AbstractC2412ko) optional2.get(), (Set) null);
                                c2561ml.previous();
                            }
                            c2561ml.s();
                        }
                    }
                }
                c3012sl.a(c2409klA, com.android.tools.r8.ir.optimize.info.q.b(), com.android.tools.r8.utils.y3.b());
                return;
            }
        }
    }

    private Collection a(C0729q2 c0729q2, final C3487zC c3487zC, final com.android.tools.r8.graph.t5 t5Var) {
        final C3150ug c3150ugA = this.e.a(c0729q2);
        final LinkedHashMap linkedHashMap = new LinkedHashMap(c0729q2.N1().size());
        if (!h && !c3487zC.a().N1().isEmpty()) {
            throw new AssertionError();
        }
        c0729q2.e(new Consumer() { // from class: com.android.tools.r8.internal.Ug$$ExternalSyntheticLambda14
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(c3150ugA, t5Var, c3487zC, linkedHashMap, (com.android.tools.r8.graph.m5) obj);
            }
        }, CM.b());
        return linkedHashMap.values();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(Map map, com.android.tools.r8.graph.Y0 y0) {
        return !map.containsKey(y0);
    }

    private com.android.tools.r8.graph.T0 a(com.android.tools.r8.graph.m5 m5Var, C3487zC c3487zC, Predicate predicate) {
        com.android.tools.r8.graph.Y0 y0A = this.d.a(c3487zC.c(), a(m5Var.getType()), m5Var.u().toString(), predicate);
        this.a.a(m5Var.getReference(), y0A);
        return m5Var.e().a(this.b, y0A, new Consumer() { // from class: com.android.tools.r8.internal.Ug$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1400Ug.a((com.android.tools.r8.graph.T0.a) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(com.android.tools.r8.graph.T0.a aVar) {
        aVar.b().b(new Consumer() { // from class: com.android.tools.r8.internal.Ug$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1400Ug.a((com.android.tools.r8.graph.Q2) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(com.android.tools.r8.graph.Q2 q2) {
        if (!h && !q2.o()) {
            throw new AssertionError();
        }
        q2.u();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Map map, com.android.tools.r8.graph.W0 w0) {
        map.put(w0.getReference(), w0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(com.android.tools.r8.graph.t5 t5Var, C3487zC c3487zC, final Map map, com.android.tools.r8.graph.o5 o5Var) {
        if (o5Var.e().C1()) {
            t5Var.a(o5Var.getReference());
            return;
        }
        com.android.tools.r8.graph.W0 w0A = a(o5Var, c3487zC, new Predicate() { // from class: com.android.tools.r8.internal.Ug$$ExternalSyntheticLambda15
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C1400Ug.a(map, (C0684j2) obj);
            }
        });
        if (!h && map.containsKey(w0A.getReference())) {
            throw new AssertionError();
        }
        map.put(w0A.getReference(), w0A);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(Map map, C0684j2 c0684j2) {
        return !map.containsKey(c0684j2);
    }

    private com.android.tools.r8.graph.W0 a(final com.android.tools.r8.graph.o5 o5Var, C3487zC c3487zC, Predicate predicate) {
        C0737r2 c0737r2C;
        C0684j2 c0684j2A;
        C0684j2 reference = o5Var.getReference();
        if (o5Var.e().u1()) {
            c0684j2A = this.d.a(c3487zC.c());
        } else {
            C0722p1 c0722p1 = this.d;
            String str = "_" + o5Var.u().toString();
            if (o5Var.getAccessFlags().o()) {
                c0737r2C = o5Var.F();
            } else {
                c0737r2C = this.d.c(reference);
            }
            C0767v2 c0767v2A = a(c0737r2C.e);
            C0767v2[] c0767v2Arr = c0737r2C.f.a;
            int length = c0767v2Arr.length;
            C0767v2[] c0767v2Arr2 = new C0767v2[length];
            for (int i = 0; i < length; i++) {
                c0767v2Arr2[i] = a(c0767v2Arr[i]);
            }
            c0684j2A = c0722p1.a(this.d.b(c0767v2A, c0767v2Arr2), c3487zC.c(), str, predicate);
        }
        this.a.a(reference, c0684j2A, o5Var.e().E0());
        return o5Var.e().a(c0684j2A, new Consumer() { // from class: com.android.tools.r8.internal.Ug$$ExternalSyntheticLambda16
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1400Ug.a(o5Var, (com.android.tools.r8.graph.W0.a) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(final com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.W0.a aVar) {
        aVar.b().b(new Consumer() { // from class: com.android.tools.r8.internal.Ug$$ExternalSyntheticLambda17
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1400Ug.a(o5Var, (C0725p4) obj);
            }
        }).a(o5Var.e().b1()).f();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(com.android.tools.r8.graph.o5 o5Var, C0725p4 c0725p4) {
        if (o5Var.e().u1()) {
            if (!h && !c0725p4.o()) {
                throw new AssertionError();
            }
        } else {
            c0725p4.u();
            c0725p4.v();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:8:0x0029  */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x0037, code lost:
    
        if (r7.e().X0().i() == false) goto L20;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void a(com.android.tools.r8.internal.C3150ug r3, com.android.tools.r8.graph.t5 r4, com.android.tools.r8.internal.C3487zC r5, final java.util.Map r6, com.android.tools.r8.graph.m5 r7) {
        /*
            r2 = this;
            com.android.tools.r8.graph.Q2 r0 = r7.getAccessFlags()
            boolean r0 = r0.o()
            if (r0 == 0) goto L62
            r3.getClass()
            com.android.tools.r8.graph.e2 r0 = r7.getReference()
            com.android.tools.r8.graph.Y0 r0 = (com.android.tools.r8.graph.Y0) r0
            com.android.tools.r8.internal.hm r1 = r3.b
            boolean r0 = r1.containsKey(r0)
            if (r0 != 0) goto L29
            com.android.tools.r8.graph.e2 r0 = r7.getReference()
            com.android.tools.r8.graph.Y0 r0 = (com.android.tools.r8.graph.Y0) r0
            com.android.tools.r8.internal.Hm r3 = r3.c
            boolean r3 = r3.contains(r0)
            if (r3 == 0) goto L3a
        L29:
            com.android.tools.r8.graph.U0 r3 = r7.e()
            com.android.tools.r8.graph.T0 r3 = (com.android.tools.r8.graph.T0) r3
            com.android.tools.r8.ir.optimize.info.e r3 = r3.X0()
            boolean r3 = r3.i()
            if (r3 != 0) goto L3a
            goto L62
        L3a:
            com.android.tools.r8.internal.Ug$$ExternalSyntheticLambda2 r3 = new com.android.tools.r8.internal.Ug$$ExternalSyntheticLambda2
            r3.<init>()
            com.android.tools.r8.graph.T0 r3 = r2.a(r7, r5, r3)
            boolean r4 = com.android.tools.r8.internal.C1400Ug.h
            if (r4 != 0) goto L58
            com.android.tools.r8.graph.e2 r4 = r3.getReference()
            boolean r4 = r6.containsKey(r4)
            if (r4 != 0) goto L52
            goto L58
        L52:
            java.lang.AssertionError r3 = new java.lang.AssertionError
            r3.<init>()
            throw r3
        L58:
            com.android.tools.r8.graph.e2 r4 = r3.getReference()
            com.android.tools.r8.graph.Y0 r4 = (com.android.tools.r8.graph.Y0) r4
            r6.put(r4, r3)
            return
        L62:
            com.android.tools.r8.graph.e2 r3 = r7.getReference()
            com.android.tools.r8.graph.Y0 r3 = (com.android.tools.r8.graph.Y0) r3
            r4.a(r3)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.tools.r8.internal.C1400Ug.a(com.android.tools.r8.internal.ug, com.android.tools.r8.graph.t5, com.android.tools.r8.internal.zC, java.util.Map, com.android.tools.r8.graph.m5):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.tools.r8.graph.W0 a(final com.android.tools.r8.graph.W0 w0) {
        C0737r2 c0737r2M1 = w0.m1();
        C0767v2 c0767v2A = a(c0737r2M1.e);
        C0767v2[] c0767v2Arr = c0737r2M1.f.a;
        int length = c0767v2Arr.length;
        C0767v2[] c0767v2Arr2 = new C0767v2[length];
        for (int i = 0; i < length; i++) {
            c0767v2Arr2[i] = a(c0767v2Arr[i]);
        }
        C0737r2 c0737r2B = this.d.b(c0767v2A, c0767v2Arr2);
        if (c0737r2B == w0.m1()) {
            return w0;
        }
        boolean z = h;
        if (!z && w0.u1()) {
            throw new AssertionError();
        }
        if (!z && w0.D1().d()) {
            throw new AssertionError("Enum unboxing is changing the signature of a library override in a non unboxed class.");
        }
        C0684j2 c0684j2A = a(this.d.a(w0.J0(), c0737r2B, w0.K0().toString() + (w0.H1() ? "$enumunboxing$" : "")), w0);
        List listA = C3151uh.a(w0.getReference(), c0684j2A);
        boolean zE0 = w0.E0();
        final com.android.tools.r8.graph.proto.k kVarA = this.a.a(w0.getReference(), c0684j2A, zE0, zE0, listA);
        return w0.a(c0684j2A, new Consumer() { // from class: com.android.tools.r8.internal.Ug$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(kVarA, w0, (com.android.tools.r8.graph.W0.a) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(com.android.tools.r8.graph.proto.k kVar, com.android.tools.r8.graph.W0 w0, com.android.tools.r8.graph.W0.a aVar) {
        C0702m c0702m = this.b;
        kVar.getClass();
        com.android.tools.r8.graph.W0.a aVarA = aVar.a(c0702m, new com.android.tools.r8.graph.w5(kVar)).a(w0.b1());
        boolean zH1 = w0.H1();
        AbstractC3938n2 abstractC3938n2 = AbstractC3938n2.b;
        if (zH1) {
            aVarA.a(abstractC3938n2);
        }
    }

    private C0684j2 a(C0684j2 c0684j2, com.android.tools.r8.graph.W0 w0) {
        final AbstractC0727q0 abstractC0727q0C = this.b.c(w0.J0());
        if (!h && abstractC0727q0C == null) {
            throw new AssertionError();
        }
        if (c0684j2.b(this.b.a())) {
            return this.d.a(c0684j2, this.g.a().c(), new Predicate() { // from class: com.android.tools.r8.internal.Ug$$ExternalSyntheticLambda9
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return C1400Ug.a(abstractC0727q0C, (C0684j2) obj);
                }
            });
        }
        int i = 0;
        while (abstractC0727q0C.c(c0684j2) != null) {
            c0684j2 = c0684j2.a(w0.K0().toString() + "$enumunboxing$" + i, this.b.a());
            i++;
        }
        return c0684j2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(AbstractC0727q0 abstractC0727q0, C0684j2 c0684j2) {
        return abstractC0727q0.c(c0684j2) == null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.tools.r8.graph.T0 a(com.android.tools.r8.graph.T0 t0) {
        com.android.tools.r8.graph.Y0 reference = t0.getReference();
        C0767v2 c0767v2A = a(reference.h);
        if (c0767v2A == reference.h) {
            return t0;
        }
        com.android.tools.r8.graph.Y0 y0C = reference.c(this.d, c0767v2A);
        this.a.a(reference, y0C);
        com.android.tools.r8.graph.T0 t0A = t0.a(this.b, y0C, new Consumer() { // from class: com.android.tools.r8.internal.Ug$$ExternalSyntheticLambda11
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((com.android.tools.r8.graph.T0.a) obj).c();
            }
        });
        if (t0.E0() && t0.Z0()) {
            if (!h && t0.Y0() != com.android.tools.r8.graph.I2.b) {
                throw new AssertionError();
            }
            t0A.a((com.android.tools.r8.graph.B2) com.android.tools.r8.graph.B2.g.c);
        }
        return t0A;
    }

    private C0767v2 a(C0767v2 c0767v2) {
        if (!c0767v2.M0()) {
            return (c0767v2.O0() && this.e.b(c0767v2)) ? this.d.A1 : c0767v2;
        }
        C0767v2 c0767v2B = c0767v2.b(this.d);
        C0767v2 c0767v2A = a(c0767v2B);
        return c0767v2B == c0767v2A ? c0767v2 : c0767v2.a(this.d, c0767v2A);
    }
}
