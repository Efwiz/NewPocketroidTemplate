package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0696l0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0725p4;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0737r2;
import com.android.tools.r8.graph.C0767v2;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class EQ implements C7, N6, InterfaceC2135h8 {
    static final /* synthetic */ boolean e = true;
    private final C0702m a;
    private final C0722p1 b;
    private final C0737r2 c;
    private final C0737r2 d;

    private EQ(C0702m c0702m) {
        this.a = c0702m;
        C0722p1 c0722p1A = c0702m.a();
        this.b = c0722p1A;
        C0767v2 c0767v2 = c0722p1A.X1;
        this.c = c0722p1A.b(c0767v2, c0722p1A.c2, c0722p1A.m2, c0767v2);
        this.d = c0722p1A.b(c0722p1A.A1, c0722p1A.m2, c0722p1A.c2);
    }

    public static EQ a(C0702m c0702m) {
        if (c0702m.Y().C1()) {
            return new EQ(c0702m);
        }
        return null;
    }

    public static void b(C0722p1 c0722p1) {
        c0722p1.d("Ljava/util/Arrays;");
        c0722p1.d("[Ljava/lang/Object;");
        c0722p1.d("[Ljava/lang/String;");
        C3434yQ.a(c0722p1);
        c0722p1.d("[Ljava/lang/Object;");
        c0722p1.d("[Ljava/util/Arrays;");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C2142hF c() {
        return new C2142hF("Record desugaring");
    }

    @Override // com.android.tools.r8.internal.C7
    public final void a(G7 g7, C3124uN c3124uN, com.android.tools.r8.graph.o5 o5Var) {
        for (B7 b7 : o5Var.e().a1().K().I0()) {
            b7.getClass();
            if ((b7 instanceof J7) && a(o5Var, b7).b()) {
                NQ nqA = OQ.a(b7.n(), this.a, o5Var);
                if (nqA.d() == this.b.h0 || nqA.d() == this.b.V) {
                    b(nqA, c3124uN, o5Var, g7);
                } else {
                    if (nqA.d() != this.b.U) {
                        throw new C3405y10("Invoke dynamic needs record desugaring but could not be desugared.");
                    }
                    a(nqA, c3124uN, o5Var, g7);
                }
            }
        }
    }

    private C0684j2 b(final NQ nq, C3124uN c3124uN, com.android.tools.r8.graph.o5 o5Var, GQ gq) {
        final C0729q2 c0729q2F = nq.f();
        C0767v2 c0767v2 = c0729q2F.d;
        C0722p1 c0722p1 = this.b;
        final C0684j2 c0684j2A = c0722p1.a(c0767v2, c0722p1.b(c0722p1.c2, new C0767v2[0]), "$record$getFieldsAsObjects");
        if (!e && c0729q2F.f(c0684j2A) != null) {
            throw new AssertionError();
        }
        gq.u(c3124uN.a(new Supplier() { // from class: com.android.tools.r8.internal.EQ$$ExternalSyntheticLambda3
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.a(c0729q2F, nq, c0684j2A);
            }
        }, c0684j2A), o5Var);
        return c0684j2A;
    }

    private ArrayList b(NQ nq, AC ac, GQ gq, com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob) {
        ac.a(2);
        C0767v2 c0767v2G = nq.g();
        C0722p1 c0722p1 = this.b;
        C0684j2 c0684j2A = c0722p1.a(c0767v2G, c0722p1.b(c0722p1.c2, new C0767v2[0]), "$record$getFieldsAsObjects");
        if (!e && nq.f().f(c0684j2A) == null) {
            throw new AssertionError();
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(new I7(183, c0684j2A, false));
        arrayList.add(new X6(nq.g(), true));
        if (this.a.Y().n1.u0 && this.a.q()) {
            arrayList.add(new C2134h7(nq.g(), nq.a()));
        } else {
            arrayList.add(new C1891e7(nq.b()));
        }
        com.android.tools.r8.graph.o5 o5VarA = a(this.c, new BiFunction() { // from class: com.android.tools.r8.internal.EQ$$ExternalSyntheticLambda2
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return AbstractC3504zQ.b((C0722p1) obj, (C0684j2) obj2);
            }
        }, c1230Ob);
        gq.g(o5VarA, o5Var);
        arrayList.add(new I7(184, o5VarA.getReference(), false));
        return arrayList;
    }

    @Override // com.android.tools.r8.internal.C7
    public final void a(com.android.tools.r8.graph.o5 o5Var, G7 g7) {
        for (B7 b7 : o5Var.e().a1().K().I0()) {
            if (!e) {
                b7.getClass();
                if (b7 instanceof C3335x7) {
                    throw new AssertionError();
                }
            }
            if (b7.H()) {
                if (a(this.b, b7.l().T())) {
                    g7.a(a(g7, AbstractC1639am.c(o5Var)), o5Var);
                }
            } else if (b7.E()) {
                if (a(b7.h().getField(), this.b)) {
                    g7.a(a(g7, AbstractC1639am.c(o5Var)), o5Var);
                }
            } else if (b7.S() && b7.w().getType() == this.b.a2) {
                g7.a(a(g7, AbstractC1639am.c(o5Var)), o5Var);
            }
        }
    }

    @Override // com.android.tools.r8.internal.C7
    public final C3224ve a(com.android.tools.r8.graph.o5 o5Var, B7 b7) {
        b7.getClass();
        if (b7 instanceof J7) {
            J7 j7N = b7.n();
            if (OQ.a(j7N.T(), this.a, o5Var)) {
                return a(b7);
            }
            return C3224ve.c();
        }
        if (b7.H()) {
            I7 i7L = b7.l();
            C0684j2 c0684j2T = i7L.T();
            if (a(c0684j2T, i7L.b(o5Var.t())) != c0684j2T) {
                C0684j2 c0684j2A = a(i7L.T(), i7L.b(o5Var.t()));
                if (e || c0684j2A != i7L.T()) {
                    return a(i7L, c0684j2A);
                }
                throw new AssertionError();
            }
            return C3224ve.c();
        }
        return C3224ve.c();
    }

    private C3224ve a(final B7 b7) {
        return C3224ve.a().a(new InterfaceC2864qe() { // from class: com.android.tools.r8.internal.EQ$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.internal.InterfaceC2864qe
            public final Collection a(InterfaceC1374Ti interfaceC1374Ti, AC ac, G7 g7, com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob, D7 d7, C0722p1 c0722p1) {
                return this.f$0.a(b7, interfaceC1374Ti, ac, g7, o5Var, c1230Ob, d7, c0722p1);
            }
        }).a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Collection a(B7 b7, InterfaceC1374Ti interfaceC1374Ti, AC ac, G7 g7, com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob, D7 d7, C0722p1 c0722p1) {
        NQ nqA = OQ.a(b7.n(), this.a, o5Var);
        if (nqA.d() == this.b.h0) {
            return b(nqA, ac, g7, o5Var, c1230Ob);
        }
        if (nqA.d() == this.b.V) {
            return a(nqA, ac, g7, o5Var, c1230Ob);
        }
        if (nqA.d() == this.b.U) {
            C0767v2 c0767v2G = nqA.g();
            C0722p1 c0722p2 = this.b;
            C0684j2 c0684j2A = c0722p2.a(c0767v2G, c0722p2.b(c0722p2.v1, c0722p2.Z1), "$record$equals");
            if (e || nqA.f().f(c0684j2A) != null) {
                return Collections.singletonList(new I7(183, c0684j2A, false));
            }
            throw new AssertionError();
        }
        throw new C3405y10("Invoke dynamic needs record desugaring but could not be desugared.");
    }

    private static C3224ve a(final I7 i7, final C0684j2 c0684j2) {
        return C3224ve.a().a(new InterfaceC2864qe() { // from class: com.android.tools.r8.internal.EQ$$ExternalSyntheticLambda4
            @Override // com.android.tools.r8.internal.InterfaceC2864qe
            public final Collection a(InterfaceC1374Ti interfaceC1374Ti, AC ac, G7 g7, com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob, D7 d7, C0722p1 c0722p1) {
                return EQ.a(i7, c0684j2, interfaceC1374Ti, ac, g7, o5Var, c1230Ob, d7, c0722p1);
            }
        }).a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Collection a(I7 i7, C0684j2 c0684j2, InterfaceC1374Ti interfaceC1374Ti, AC ac, G7 g7, com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob, D7 d7, C0722p1 c0722p1) {
        return Collections.singletonList(new I7(i7.U(), c0684j2, i7.V()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.tools.r8.graph.o5 a(C0729q2 c0729q2, C0684j2 c0684j2, C0684j2 c0684j3) {
        C3356xQ c3356xQ = new C3356xQ(this.a, c0684j2, c0729q2.d);
        com.android.tools.r8.graph.o5 o5Var = new com.android.tools.r8.graph.o5(c0729q2, com.android.tools.r8.graph.W0.Y1().a(c0684j3).a(C0725p4.b(4098, false)).c().a());
        o5Var.a(c3356xQ.b(), this.a);
        return o5Var;
    }

    private void a(NQ nq, C3124uN c3124uN, com.android.tools.r8.graph.o5 o5Var, GQ gq) {
        final C0684j2 c0684j2B = b(nq, c3124uN, o5Var, gq);
        final C0729q2 c0729q2F = nq.f();
        C0767v2 c0767v2 = c0729q2F.d;
        C0722p1 c0722p1 = this.b;
        final C0684j2 c0684j2A = c0722p1.a(c0767v2, c0722p1.b(c0722p1.v1, c0722p1.Z1), "$record$equals");
        if (!e && c0729q2F.f(c0684j2A) != null) {
            throw new AssertionError();
        }
        gq.o(c3124uN.a(new Supplier() { // from class: com.android.tools.r8.internal.EQ$$ExternalSyntheticLambda9
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.a(c0729q2F, c0684j2B, c0684j2A);
            }
        }, c0684j2A), o5Var);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.tools.r8.graph.o5 a(C0729q2 c0729q2, NQ nq, C0684j2 c0684j2) {
        C3434yQ c3434yQ = new C3434yQ(this.a, this.b.b2, nq.c());
        com.android.tools.r8.graph.o5 o5Var = new com.android.tools.r8.graph.o5(c0729q2, com.android.tools.r8.graph.W0.Y1().a(c0684j2).a(C0725p4.b(4098, false)).c().a());
        o5Var.a(c3434yQ.b(), this.a);
        return o5Var;
    }

    private com.android.tools.r8.graph.o5 a(final C0737r2 c0737r2, final BiFunction biFunction, C1230Ob c1230Ob) {
        return this.a.D().b(new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.internal.EQ$$ExternalSyntheticLambda12
            @Override // com.android.tools.r8.synthesis.C
            public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
                return l.A;
            }
        }, c1230Ob.a(), this.a, new Consumer() { // from class: com.android.tools.r8.internal.EQ$$ExternalSyntheticLambda13
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(c0737r2, biFunction, (com.android.tools.r8.synthesis.H) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C0737r2 c0737r2, final BiFunction biFunction, com.android.tools.r8.synthesis.H h) {
        h.a(c0737r2).a(C0725p4.Q()).a(new com.android.tools.r8.synthesis.G() { // from class: com.android.tools.r8.internal.EQ$$ExternalSyntheticLambda10
            @Override // com.android.tools.r8.synthesis.G
            public final com.android.tools.r8.graph.W a(C0684j2 c0684j2) {
                return this.f$0.a(biFunction, c0684j2);
            }
        }).b();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ com.android.tools.r8.graph.W a(BiFunction biFunction, C0684j2 c0684j2) {
        return (com.android.tools.r8.graph.W) biFunction.apply(this.a.a(), c0684j2);
    }

    private ArrayList a(NQ nq, AC ac, GQ gq, com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob) {
        ac.a(1);
        C0767v2 c0767v2G = nq.g();
        C0722p1 c0722p1 = this.b;
        C0684j2 c0684j2A = c0722p1.a(c0767v2G, c0722p1.b(c0722p1.c2, new C0767v2[0]), "$record$getFieldsAsObjects");
        if (!e && nq.f().f(c0684j2A) == null) {
            throw new AssertionError();
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(new D8(D8.a.Dup));
        arrayList.add(new I7(182, this.b.g4.e, false));
        arrayList.add(new D8(D8.a.Swap));
        arrayList.add(new I7(183, c0684j2A, false));
        com.android.tools.r8.graph.o5 o5VarA = a(this.d, new BiFunction() { // from class: com.android.tools.r8.internal.EQ$$ExternalSyntheticLambda1
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return AbstractC3504zQ.a((C0722p1) obj, (C0684j2) obj2);
            }
        }, c1230Ob);
        gq.j(o5VarA, o5Var);
        arrayList.add(new I7(184, o5VarA.getReference(), false));
        return arrayList;
    }

    private void a(final FQ fq, ArrayList arrayList) {
        final C0729q2 c0729q2A = a((HQ) fq, (AbstractCollection) arrayList);
        arrayList.forEach(new Consumer() { // from class: com.android.tools.r8.internal.EQ$$ExternalSyntheticLambda11
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                fq.a(c0729q2A, (C0729q2) obj);
            }
        });
    }

    private C0729q2 a(final HQ hq, AbstractCollection abstractCollection) {
        C0722p1 c0722p1A = this.a.a();
        a(c0722p1A);
        com.android.tools.r8.synthesis.D D = this.a.D();
        Supplier supplier = new Supplier() { // from class: com.android.tools.r8.internal.EQ$$ExternalSyntheticLambda5
            @Override // java.util.function.Supplier
            public final Object get() {
                return EQ.c();
            }
        };
        com.android.tools.r8.synthesis.C c = new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.internal.EQ$$ExternalSyntheticLambda6
            @Override // com.android.tools.r8.synthesis.C
            public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
                return l.b;
            }
        };
        C0767v2 c0767v2 = c0722p1A.a2;
        C0702m c0702m = this.a;
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.internal.EQ$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((com.android.tools.r8.synthesis.P) obj);
            }
        };
        Objects.requireNonNull(hq);
        return D.a(supplier, c, c0767v2, abstractCollection, c0702m, consumer, new Consumer() { // from class: com.android.tools.r8.internal.EQ$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                hq.a((C0729q2) obj);
            }
        });
    }

    private void a(C0722p1 c0722p1) {
        AbstractC0727q0 abstractC0727q0D = this.a.f().d(c0722p1.b2);
        if (abstractC0727q0D == null || !abstractC0727q0D.e0()) {
            return;
        }
        this.a.Y().e.a("D8/R8 is compiling a mix of desugared and non desugared input using java.lang.Record, but the application reader did not import correctly " + c0722p1.b2);
    }

    public static boolean a(com.android.tools.r8.graph.Y0 y0, C0722p1 c0722p1) {
        if (e || y0.e != c0722p1.a2) {
            return y0.h == c0722p1.a2;
        }
        throw new AssertionError("The java.lang.Record class has no fields.");
    }

    public static boolean a(C0722p1 c0722p1, C0684j2 c0684j2) {
        if (c0684j2.e == c0722p1.a2) {
            return true;
        }
        return a(c0684j2.h, c0722p1);
    }

    private static boolean a(C0737r2 c0737r2, C0722p1 c0722p1) {
        if (c0737r2.e == c0722p1.a2) {
            return true;
        }
        for (C0767v2 c0767v2 : c0737r2.f.a) {
            if (c0767v2 == c0722p1.a2) {
                return true;
            }
        }
        return false;
    }

    private C0684j2 a(C0684j2 c0684j2, boolean z) {
        C0722p1 c0722p1 = this.b;
        com.android.tools.r8.graph.W1 w1 = c0722p1.i4;
        C0684j2 c0684j3 = w1.b;
        if (c0684j2 != c0684j3 && c0684j2 != w1.c && c0684j2 != w1.d) {
            return c0684j2;
        }
        if (z) {
            throw new C1367Tb("Rewrite invoke-super to abstract method error.");
        }
        if (c0684j2 == c0684j3) {
            return c0722p1.g4.d;
        }
        if (c0684j2 == w1.d) {
            return c0722p1.g4.i;
        }
        if (e || c0684j2 == w1.c) {
            return c0722p1.g4.i;
        }
        throw new AssertionError();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(com.android.tools.r8.synthesis.P p) {
        ((com.android.tools.r8.synthesis.P) p.g()).a((AbstractCollection) AbstractC1639am.c(com.android.tools.r8.graph.W0.Y1().a(this.b.i4.a).a(C0725p4.b(4100, true)).a(new C2515m6(this.a, this.b.b2).b()).c().a()));
    }

    @Override // com.android.tools.r8.internal.N6
    public final String a() {
        return "$record";
    }

    @Override // com.android.tools.r8.internal.N6
    public final void a(C1150Lb c1150Lb, S6 s6) {
        C0696l0 c0696l0F = this.a.f().b().f();
        if (c0696l0F.h()) {
            ArrayList arrayList = new ArrayList(c0696l0F.c().size());
            Iterator it = c0696l0F.c().iterator();
            while (it.hasNext()) {
                AbstractC0727q0 abstractC0727q0A = this.a.a((C0767v2) it.next());
                boolean z = e;
                if (!z && abstractC0727q0A == null) {
                    throw new AssertionError();
                }
                if (!z && !abstractC0727q0A.e0()) {
                    throw new AssertionError();
                }
                arrayList.add(abstractC0727q0A.a0());
            }
            a((FQ) s6, arrayList);
        }
    }

    @Override // com.android.tools.r8.internal.InterfaceC2135h8
    public final void a(Collection collection, AbstractC2591n8 abstractC2591n8, ExecutorService executorService) {
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            C0729q2 c0729q2 = (C0729q2) it.next();
            if (c0729q2.K1()) {
                if (!e && c0729q2.f != this.b.a2) {
                    throw new AssertionError();
                }
                c0729q2.e.f0();
            }
        }
    }
}
