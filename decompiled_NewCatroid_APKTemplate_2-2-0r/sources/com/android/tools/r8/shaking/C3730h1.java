package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.m5;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.graph.u5;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.AbstractC1699bX;
import com.android.tools.r8.internal.DH;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;

/* JADX INFO: renamed from: com.android.tools.r8.shaking.h1, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3730h1 extends AbstractC3735i1 {
    static final /* synthetic */ boolean h = true;
    private final Map b;
    private final Map c;
    private final Map d;
    private final Map e;
    private final Map f;
    private final Map g;

    /* JADX INFO: renamed from: $r8$lambda$sUNbYakYT-YAr-fyNOkgYGRoyhc, reason: not valid java name */
    public static /* synthetic */ IdentityHashMap m2279$r8$lambda$sUNbYakYTYArfyNOkgYGRoyhc(int i) {
        return new IdentityHashMap(i);
    }

    C3730h1() {
        this(new IdentityHashMap(), new IdentityHashMap(), new IdentityHashMap(), new IdentityHashMap(), new IdentityHashMap(), new IdentityHashMap());
    }

    public final void a(o5 o5Var, Consumer consumer) {
        C3762m1 c3762m1A = a(o5Var.a(), o5Var.e());
        if (c3762m1A.v()) {
            if (!h && c3762m1A != C3762m1.y()) {
                throw new AssertionError();
            }
        } else {
            C3754l1 c3754l1W = c3762m1A.w();
            consumer.accept(c3754l1W);
            C3762m1 c3762m1 = (C3762m1) c3754l1W.n();
            if (c3762m1A.equals(c3762m1)) {
                return;
            }
            this.c.put(o5Var.getReference(), c3762m1);
        }
    }

    public final void b(final u5 u5Var) {
        if (u5Var.h()) {
            this.b.keySet().removeAll(u5Var.e());
        }
        if (u5Var.h() || u5Var.i()) {
            this.d.keySet().removeIf(new Predicate() { // from class: com.android.tools.r8.shaking.h1$$ExternalSyntheticLambda16
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return u5Var.a((com.android.tools.r8.graph.Y0) obj);
                }
            });
        }
        if (u5Var.h() || u5Var.i() || u5Var.j()) {
            this.c.keySet().removeIf(new Predicate() { // from class: com.android.tools.r8.shaking.h1$$ExternalSyntheticLambda17
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return u5Var.a((C0684j2) obj);
                }
            });
        }
    }

    @Override // com.android.tools.r8.shaking.AbstractC3735i1
    public final void c(final Consumer consumer, final com.android.tools.r8.utils.A1 a1) {
        this.b.forEach(new BiConsumer() { // from class: com.android.tools.r8.shaking.h1$$ExternalSyntheticLambda20
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C3730h1.a(a1, consumer, (C0767v2) obj, (Y0) obj2);
            }
        });
    }

    private C3730h1(IdentityHashMap identityHashMap, IdentityHashMap identityHashMap2, IdentityHashMap identityHashMap3, Map map, Map map2, Map map3) {
        this.b = identityHashMap;
        this.c = identityHashMap2;
        this.d = identityHashMap3;
        this.e = map;
        this.f = map2;
        this.g = map3;
    }

    public final void b(C0729q2 c0729q2) {
        a(new Consumer() { // from class: com.android.tools.r8.shaking.h1$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((X0) obj).q();
            }
        }, c0729q2);
    }

    public final void a(m5 m5Var, Consumer consumer) {
        C3704d1 c3704d1A = a(m5Var.e(), m5Var.a());
        if (c3704d1A.n()) {
            if (!h && c3704d1A != C3704d1.q()) {
                throw new AssertionError();
            }
        } else {
            C3698c1 c3698c1O = c3704d1A.o();
            consumer.accept(c3698c1O);
            C3704d1 c3704d1 = (C3704d1) c3698c1O.n();
            if (c3704d1A.equals(c3704d1)) {
                return;
            }
            this.d.put(m5Var.getReference(), c3704d1);
        }
    }

    public final void b(o5 o5Var) {
        a(o5Var, new Consumer() { // from class: com.android.tools.r8.shaking.h1$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((C3754l1) obj).q();
            }
        });
    }

    @Override // com.android.tools.r8.shaking.AbstractC3735i1
    public final void b(final Consumer consumer, final com.android.tools.r8.utils.A1 a1) {
        this.c.forEach(new BiConsumer() { // from class: com.android.tools.r8.shaking.h1$$ExternalSyntheticLambda19
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C3730h1.a(a1, consumer, (C0684j2) obj, (C3762m1) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(DH dh, com.android.tools.r8.utils.A1 a1, Map map, com.android.tools.r8.graph.Y0 y0, C3704d1 c3704d1) {
        com.android.tools.r8.graph.Y0 y0B = dh.b((AbstractC1476Wj) null, y0);
        boolean z = h;
        if (!z && y0B.f != y0.f && c3704d1.c((K0) a1) && !c3704d1.a((K0) a1)) {
            throw new AssertionError();
        }
        C3704d1 c3704d2 = (C3704d1) map.put(y0B, c3704d1);
        if (!z && c3704d2 != null) {
            throw new AssertionError();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(DH dh, com.android.tools.r8.utils.A1 a1, Map map, C0684j2 c0684j2, C3762m1 c3762m1) {
        C0684j2 c0684j2C = dh.c((AbstractC1476Wj) null, c0684j2);
        boolean z = h;
        if (!z && c3762m1.c((K0) a1) && !c3762m1.a((K0) a1) && c0684j2C.f != c0684j2.f) {
            throw new AssertionError();
        }
        if (!z && c3762m1.c((K0) a1) && c0684j2C.F0() != c0684j2.F0()) {
            throw new AssertionError();
        }
        if (!z && c3762m1.c((K0) a1) && !AbstractC1699bX.a(c0684j2C.G0().stream(), c0684j2.G0().stream().map(new g4$$ExternalSyntheticLambda0(dh)), new BiFunction() { // from class: com.android.tools.r8.shaking.h1$$ExternalSyntheticLambda4
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return Boolean.valueOf(((C0767v2) obj).equals((C0767v2) obj2));
            }
        }).allMatch(new Predicate() { // from class: com.android.tools.r8.shaking.h1$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((Boolean) obj).booleanValue();
            }
        })) {
            throw new AssertionError();
        }
        if (!z && c3762m1.c((K0) a1)) {
            if (c0684j2C.I0() != dh.a(AbstractC1476Wj.e(), c0684j2.I0())) {
                throw new AssertionError();
            }
        }
    }

    public final void a(u5 u5Var) {
        if (u5Var.h()) {
            this.b.keySet().removeAll(u5Var.e());
        }
        if (u5Var.i()) {
            this.d.keySet().removeAll(u5Var.f());
        }
        if (u5Var.i() || u5Var.j()) {
            this.c.keySet().removeAll(u5Var.g());
        }
    }

    @Override // com.android.tools.r8.shaking.AbstractC3735i1
    public final C3730h1 a(final DH dh, final com.android.tools.r8.utils.A1 a1) {
        final IdentityHashMap identityHashMap = new IdentityHashMap(this.b.size());
        this.b.forEach(new BiConsumer() { // from class: com.android.tools.r8.shaking.h1$$ExternalSyntheticLambda7
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C3730h1.a(dh, a1, identityHashMap, (C0767v2) obj, (Y0) obj2);
            }
        });
        final IdentityHashMap identityHashMap2 = new IdentityHashMap(this.c.size());
        this.c.forEach(new BiConsumer() { // from class: com.android.tools.r8.shaking.h1$$ExternalSyntheticLambda8
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C3730h1.a(dh, a1, identityHashMap2, (C0684j2) obj, (C3762m1) obj2);
            }
        });
        final IdentityHashMap identityHashMap3 = new IdentityHashMap(this.d.size());
        this.d.forEach(new BiConsumer() { // from class: com.android.tools.r8.shaking.h1$$ExternalSyntheticLambda9
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C3730h1.a(dh, a1, identityHashMap3, (com.android.tools.r8.graph.Y0) obj, (C3704d1) obj2);
            }
        });
        Map mapA = a(this.e, new Function() { // from class: com.android.tools.r8.shaking.h1$$ExternalSyntheticLambda11
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C3730h1.a(dh, (C0767v2) obj);
            }
        }, new Supplier() { // from class: com.android.tools.r8.shaking.h1$$ExternalSyntheticLambda12
            @Override // java.util.function.Supplier
            public final Object get() {
                return Y0.p();
            }
        });
        Map map = this.f;
        Objects.requireNonNull(dh);
        return new C3730h1(identityHashMap, identityHashMap2, identityHashMap3, mapA, a(map, new Function() { // from class: com.android.tools.r8.shaking.h1$$ExternalSyntheticLambda13
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return dh.b((com.android.tools.r8.graph.Y0) obj);
            }
        }, new Supplier() { // from class: com.android.tools.r8.shaking.h1$$ExternalSyntheticLambda14
            @Override // java.util.function.Supplier
            public final Object get() {
                return C3704d1.p();
            }
        }), a(this.g, new Function() { // from class: com.android.tools.r8.shaking.h1$$ExternalSyntheticLambda15
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return dh.c((C0684j2) obj);
            }
        }, new O$$ExternalSyntheticLambda3()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(DH dh, com.android.tools.r8.utils.A1 a1, Map map, C0767v2 c0767v2, Y0 y0) {
        dh.getClass();
        C0767v2 c0767v2A = dh.a(AbstractC1476Wj.e(), c0767v2);
        boolean z = h;
        if (!z && c0767v2A != c0767v2 && y0.c((K0) a1) && !y0.a((K0) a1) && !y0.e(a1)) {
            throw new AssertionError();
        }
        Y0 y1 = (Y0) map.put(c0767v2A, y0);
        if (!z && y1 != null) {
            throw new AssertionError();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static C0767v2 a(DH dh, C0767v2 c0767v2) {
        dh.getClass();
        C0767v2 c0767v2A = dh.a(AbstractC1476Wj.e(), c0767v2);
        if (c0767v2A.O0()) {
            return c0767v2A;
        }
        if (h || c0767v2A.R0()) {
            return null;
        }
        throw new AssertionError();
    }

    private static Map a(Map map, Function function, final Supplier supplier) {
        return com.android.tools.r8.utils.Z1.a(map, new IntFunction() { // from class: com.android.tools.r8.shaking.h1$$ExternalSyntheticLambda23
            @Override // java.util.function.IntFunction
            public final Object apply(int i) {
                return C3730h1.m2279$r8$lambda$sUNbYakYTYArfyNOkgYGRoyhc(i);
            }
        }, function, Function.identity(), new com.android.tools.r8.utils.G3() { // from class: com.android.tools.r8.shaking.h1$$ExternalSyntheticLambda24
            @Override // com.android.tools.r8.utils.G3
            public final Object a(Object obj, Object obj2, Object obj3) {
                return C3730h1.a(supplier, obj, (AbstractC3716f1) obj2, (AbstractC3716f1) obj3);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ AbstractC3716f1 a(Supplier supplier, Object obj, AbstractC3716f1 abstractC3716f1, AbstractC3716f1 abstractC3716f2) {
        return ((AbstractC3716f1) supplier.get()).a(abstractC3716f1).a(abstractC3716f2);
    }

    @Override // com.android.tools.r8.shaking.AbstractC3735i1
    final void a(final C0702m c0702m, final BiConsumer biConsumer, final BiConsumer biConsumer2, final BiConsumer biConsumer3) {
        this.e.forEach(new BiConsumer() { // from class: com.android.tools.r8.shaking.h1$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C3730h1.a(c0702m, biConsumer, (C0767v2) obj, (X0) obj2);
            }
        });
        this.f.forEach(new BiConsumer() { // from class: com.android.tools.r8.shaking.h1$$ExternalSyntheticLambda10
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C3730h1.a(c0702m, biConsumer2, (com.android.tools.r8.graph.Y0) obj, (C3698c1) obj2);
            }
        });
        this.g.forEach(new BiConsumer() { // from class: com.android.tools.r8.shaking.h1$$ExternalSyntheticLambda18
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C3730h1.a(c0702m, biConsumer3, (C0684j2) obj, (C3754l1) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C0702m c0702m, BiConsumer biConsumer, C0767v2 c0767v2, X0 x0) {
        C0729q2 c0729q2A = C0729q2.a(c0702m.c(c0767v2));
        if (c0729q2A != null) {
            biConsumer.accept(c0729q2A, x0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C0702m c0702m, BiConsumer biConsumer, com.android.tools.r8.graph.Y0 y0, C3698c1 c3698c1) {
        m5 m5VarD = C0729q2.a(c0702m.c(y0.B0())).d(y0);
        if (m5VarD != null) {
            biConsumer.accept(m5VarD, c3698c1);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C0702m c0702m, BiConsumer biConsumer, C0684j2 c0684j2, C3754l1 c3754l1) {
        o5 o5VarF = C0729q2.a(c0702m.c(c0684j2.B0())).f(c0684j2);
        if (o5VarF != null) {
            biConsumer.accept(o5VarF, c3754l1);
        }
    }

    final void a(o5 o5Var, final C3754l1 c3754l1) {
        if (c3754l1.j()) {
            return;
        }
        a(o5Var, new Consumer() { // from class: com.android.tools.r8.shaking.h1$$ExternalSyntheticLambda22
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((C3754l1) obj).a(c3754l1);
            }
        });
        ((C3754l1) this.g.computeIfAbsent(o5Var.getReference(), com.android.tools.r8.utils.Z1.a(new O$$ExternalSyntheticLambda3()))).a(c3754l1);
    }

    @Override // com.android.tools.r8.shaking.AbstractC3735i1
    public final Y0 a(C0729q2 c0729q2) {
        return (Y0) this.b.getOrDefault(c0729q2.d, Y0.i());
    }

    public final void a(Consumer consumer, C0729q2 c0729q2) {
        Y0 y0 = (Y0) this.b.getOrDefault(c0729q2.d, Y0.i());
        if (y0.n()) {
            if (!h && y0 != Y0.q()) {
                throw new AssertionError();
            }
        } else {
            X0 x0O = y0.o();
            consumer.accept(x0O);
            Y0 y1 = (Y0) x0O.n();
            if (y0.equals(y1)) {
                return;
            }
            this.b.put(c0729q2.d, y1);
        }
    }

    @Override // com.android.tools.r8.shaking.AbstractC3735i1
    public final C3762m1 a(C0729q2 c0729q2, com.android.tools.r8.graph.W0 w0) {
        if (h || w0.J0() == c0729q2.d) {
            return (C3762m1) this.c.getOrDefault(w0.getReference(), C3762m1.i());
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.shaking.AbstractC3735i1
    public final C3704d1 a(com.android.tools.r8.graph.T0 t0, C0729q2 c0729q2) {
        if (h || t0.J0() == c0729q2.d) {
            return (C3704d1) this.d.getOrDefault(t0.getReference(), C3704d1.i());
        }
        throw new AssertionError();
    }

    public final void a(m5 m5Var) {
        a(m5Var, new Consumer() { // from class: com.android.tools.r8.shaking.h1$$ExternalSyntheticLambda21
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((C3698c1) obj).q();
            }
        });
    }

    @Override // com.android.tools.r8.shaking.AbstractC3735i1
    public final C3730h1 a(Consumer consumer) {
        consumer.accept(this);
        return this;
    }

    @Override // com.android.tools.r8.shaking.AbstractC3735i1
    public final void a(final com.android.tools.r8.utils.A1 a1, final Set set) {
        this.b.forEach(new BiConsumer() { // from class: com.android.tools.r8.shaking.h1$$ExternalSyntheticLambda2
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C3730h1.a(a1, set, (C0767v2) obj, (Y0) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(com.android.tools.r8.utils.A1 a1, Set set, C0767v2 c0767v2, Y0 y0) {
        if (!h && y0.c((K0) a1) && !set.contains(c0767v2)) {
            throw new AssertionError();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(com.android.tools.r8.utils.A1 a1, Consumer consumer, C0767v2 c0767v2, Y0 y0) {
        if (y0.c((K0) a1)) {
            consumer.accept(c0767v2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(com.android.tools.r8.utils.A1 a1, Consumer consumer, C0684j2 c0684j2, C3762m1 c3762m1) {
        if (c3762m1.c((K0) a1)) {
            consumer.accept(c0684j2);
        }
    }

    @Override // com.android.tools.r8.shaking.AbstractC3735i1
    public final void a(final Consumer consumer, final com.android.tools.r8.utils.A1 a1) {
        this.d.forEach(new BiConsumer() { // from class: com.android.tools.r8.shaking.h1$$ExternalSyntheticLambda6
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C3730h1.a(a1, consumer, (com.android.tools.r8.graph.Y0) obj, (C3704d1) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(com.android.tools.r8.utils.A1 a1, Consumer consumer, com.android.tools.r8.graph.Y0 y0, C3704d1 c3704d1) {
        if (c3704d1.c((K0) a1)) {
            consumer.accept(y0);
        }
    }
}
