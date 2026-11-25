package com.android.tools.r8.internal;

import com.android.tools.r8.ClassFileConsumer;
import com.android.tools.r8.graph.AbstractC0642e2;
import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.AbstractC0735r0;
import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0644e4;
import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0762u4;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.utils.AbstractC3896g;
import com.android.tools.r8.utils.AbstractC3938n2;
import com.android.tools.r8.utils.C3879c3;
import com.android.tools.r8.utils.C3979v2;
import com.android.tools.r8.utils.InterfaceC3877c1;
import com.android.tools.r8.utils.InterfaceC3905h3;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.dE, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1826dE {
    static final /* synthetic */ boolean f = true;
    private final com.android.tools.r8.androidapi.b a;
    private final C0702m b;
    private final InterfaceC1985fE c;
    private final com.android.tools.r8.utils.A1 d;
    private final C2368kE e;

    public C1826dE(C0702m c0702m) {
        if (!f && !c0702m.E().g()) {
            throw new AssertionError();
        }
        this.a = c0702m.d();
        this.b = c0702m;
        this.c = InterfaceC1985fE.a(c0702m);
        this.d = c0702m.Y();
        this.e = C2449lE.a(c0702m);
    }

    public static com.android.tools.r8.graph.Y0 a(C0702m c0702m, AbstractC0735r0 abstractC0735r0, com.android.tools.r8.graph.Y0 y0) {
        if (abstractC0735r0 instanceof com.android.tools.r8.graph.m5) {
            return abstractC0735r0.getReference();
        }
        C0767v2 c0767v2A = a(abstractC0735r0.a(), c0702m, abstractC0735r0.getReference(), y0.B0(), new BiFunction() { // from class: com.android.tools.r8.internal.dE$$ExternalSyntheticLambda17
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return ((AbstractC0727q0) obj).a((com.android.tools.r8.graph.Y0) obj2);
            }
        });
        return c0767v2A != null ? y0.b(c0767v2A, c0702m.a()) : y0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.tools.r8.graph.I4 b(C0684j2 c0684j2) {
        return ((C3733i) this.b.f()).a(c0684j2.e, c0684j2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.tools.r8.graph.I4 c(C0684j2 c0684j2) {
        return ((C3733i) this.b.f()).b(c0684j2.e, c0684j2);
    }

    private void d(ExecutorService executorService) {
        final Set setA = a(executorService);
        ((C3733i) this.b.f()).v().a(new Consumer() { // from class: com.android.tools.r8.internal.dE$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(setA, (com.android.tools.r8.graph.R2) obj);
            }
        });
        if (!f && !setA.isEmpty()) {
            throw new AssertionError();
        }
    }

    private void b(ExecutorService executorService) {
        if (!f) {
            d(executorService);
        }
        com.android.tools.r8.graph.T2 t2V = ((C3733i) this.b.f()).v();
        final C2368kE c2368kE = this.e;
        Objects.requireNonNull(c2368kE);
        t2V.a(new Consumer() { // from class: com.android.tools.r8.internal.dE$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                c2368kE.a((com.android.tools.r8.graph.R2) obj);
            }
        });
    }

    public final void c(ExecutorService executorService) {
        C3733i c3733i = (C3733i) this.b.f();
        a(c3733i.x());
        b(executorService);
        c3733i.v().b();
        C2449lE c2449lEA = this.e.a();
        this.b.b((AbstractC1476Wj) c2449lEA);
        this.c.a(this.b, c2449lEA);
    }

    private C0684j2 a(AbstractC0748t0 abstractC0748t0, com.android.tools.r8.graph.I4.c cVar, CN cn, EnumC1953et enumC1953et, C0684j2 c0684j2) {
        AbstractC0727q0 abstractC0727q0C;
        if (!f && (abstractC0748t0 instanceof com.android.tools.r8.graph.o5)) {
            throw new AssertionError();
        }
        enumC1953et.getClass();
        if (enumC1953et == EnumC1953et.c) {
            return c0684j2;
        }
        if (enumC1953et.e() && this.d.z()) {
            C0767v2 c0767v2A = a(cVar.d(), this.b, abstractC0748t0.getReference(), c0684j2.B0(), new BiFunction() { // from class: com.android.tools.r8.internal.dE$$ExternalSyntheticLambda14
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    return ((AbstractC0727q0) obj).c((C0684j2) obj2);
                }
            });
            if (c0767v2A == null || (abstractC0727q0C = this.b.c(c0767v2A)) == null) {
                return c0684j2;
            }
            if (abstractC0727q0C == abstractC0748t0.a()) {
                return abstractC0748t0.getReference();
            }
            C0684j2 reference = abstractC0748t0.getReference();
            C0722p1 c0722p1A = this.b.a();
            reference.getClass();
            return reference.b(abstractC0727q0C.C(), c0722p1A);
        }
        C0644e4 c0644e4W = null;
        while (cVar != null) {
            AbstractC0748t0 abstractC0748t0C = cVar.c();
            com.android.tools.r8.graph.I4.c cVarA = cVar.a(cVar.d());
            if ((abstractC0748t0C instanceof C0644e4) && a(abstractC0748t0C, cVarA, cn) && ((!abstractC0748t0C.a().isInterface() || !enumC1953et.e()) && ((!abstractC0748t0C.getAccessFlags().W() || !enumC1953et.e()) && AbstractC3896g.a(abstractC0748t0C.W(), c0684j2, this.a, this.d)))) {
                c0644e4W = abstractC0748t0C.W();
            }
            if (this.b.t().a(abstractC0748t0C)) {
                break;
            }
            AbstractC0727q0 abstractC0727q0A = abstractC0748t0C.a();
            if (!abstractC0748t0.e().R0() || abstractC0727q0A.isInterface() || abstractC0727q0A.n1() == null) {
                break;
            }
            cVar = ((C3733i) this.b.f()).a(abstractC0727q0A.n1(), c0684j2).o();
        }
        if (c0644e4W != null) {
            return c0644e4W.getReference();
        }
        C0767v2 c0767v2A2 = a(abstractC0748t0.a(), this.b, abstractC0748t0.getReference(), c0684j2.B0(), new BiFunction() { // from class: com.android.tools.r8.internal.dE$$ExternalSyntheticLambda14
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return ((AbstractC0727q0) obj).c((C0684j2) obj2);
            }
        });
        return c0767v2A2 != null ? c0684j2.b(c0767v2A2, this.b.a()) : c0684j2;
    }

    private boolean a(AbstractC0748t0 abstractC0748t0, final com.android.tools.r8.graph.I4.c cVar, CN cn) {
        if (abstractC0748t0.a().J1() && abstractC0748t0.e().getAccessFlags().m()) {
            return true;
        }
        return AbstractC2734ot.a((Iterable) cn, new InterfaceC3205vM() { // from class: com.android.tools.r8.internal.dE$$ExternalSyntheticLambda13
            @Override // com.android.tools.r8.internal.InterfaceC3205vM
            public final boolean apply(Object obj) {
                return this.f$0.a(cVar, (com.android.tools.r8.graph.o5) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean a(com.android.tools.r8.graph.I4.c cVar, com.android.tools.r8.graph.o5 o5Var) {
        C0702m c0702m = this.b;
        return cVar.a(o5Var, c0702m, (C0680j) c0702m.f()).d();
    }

    private static C0767v2 a(AbstractC0727q0 abstractC0727q0, C0702m c0702m, AbstractC0642e2 abstractC0642e2, C0767v2 c0767v2, BiFunction biFunction) {
        if (abstractC0727q0.isInterface()) {
            return a(c0702m, abstractC0642e2, c0767v2, biFunction);
        }
        AbstractC0727q0 abstractC0727q0A = c0702m.a(c0767v2);
        while (abstractC0727q0A != null && abstractC0727q0A.e0()) {
            abstractC0727q0A = c0702m.b(abstractC0727q0A.a0(), abstractC0727q0A.n1());
        }
        if (abstractC0727q0A != null) {
            return abstractC0727q0A.getType();
        }
        return null;
    }

    private static C0767v2 a(C0702m c0702m, AbstractC0642e2 abstractC0642e2, C0767v2 c0767v2, BiFunction biFunction) {
        C0767v2 c0767v2A;
        AbstractC0727q0 abstractC0727q0C = c0702m.c(c0767v2);
        if (abstractC0727q0C == null) {
            return null;
        }
        if (biFunction.apply(abstractC0727q0C, abstractC0642e2) != null) {
            return c0767v2;
        }
        C0767v2 c0767v3 = abstractC0727q0C.f;
        if (c0767v3 != null && (c0767v2A = a(c0702m, abstractC0642e2, c0767v3, biFunction)) != null) {
            return abstractC0727q0C.I1() ? c0767v2 : c0767v2A;
        }
        Iterator<C0767v2> it = abstractC0727q0C.f1().iterator();
        while (it.hasNext()) {
            C0767v2 c0767v2A2 = a(c0702m, abstractC0642e2, it.next(), biFunction);
            if (c0767v2A2 != null) {
                return abstractC0727q0C.I1() ? c0767v2 : c0767v2A2;
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.tools.r8.graph.I4 a(C0684j2 c0684j2) {
        return ((C3733i) this.b.f()).h(c0684j2);
    }

    private void a(final C0762u4 c0762u4) {
        Objects.requireNonNull(c0762u4);
        a(new com.android.tools.r8.utils.F() { // from class: com.android.tools.r8.internal.dE$$ExternalSyntheticLambda3
            @Override // com.android.tools.r8.utils.F
            public final void forEach(BiConsumer biConsumer) {
                c0762u4.e(biConsumer);
            }
        }, new Function() { // from class: com.android.tools.r8.internal.dE$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.b((C0684j2) obj);
            }
        }, EnumC1953et.g);
        a(new com.android.tools.r8.utils.F() { // from class: com.android.tools.r8.internal.dE$$ExternalSyntheticLambda5
            @Override // com.android.tools.r8.utils.F
            public final void forEach(BiConsumer biConsumer) {
                c0762u4.b(biConsumer);
            }
        }, new Function() { // from class: com.android.tools.r8.internal.dE$$ExternalSyntheticLambda6
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.c((C0684j2) obj);
            }
        }, EnumC1953et.d);
        a(new com.android.tools.r8.utils.F() { // from class: com.android.tools.r8.internal.dE$$ExternalSyntheticLambda7
            @Override // com.android.tools.r8.utils.F
            public final void forEach(BiConsumer biConsumer) {
                c0762u4.d(biConsumer);
            }
        }, new Function() { // from class: com.android.tools.r8.internal.dE$$ExternalSyntheticLambda8
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a((C0684j2) obj);
            }
        }, EnumC1953et.f);
        a(new com.android.tools.r8.utils.F() { // from class: com.android.tools.r8.internal.dE$$ExternalSyntheticLambda9
            @Override // com.android.tools.r8.utils.F
            public final void forEach(BiConsumer biConsumer) {
                c0762u4.c(biConsumer);
            }
        }, new Function() { // from class: com.android.tools.r8.internal.dE$$ExternalSyntheticLambda8
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a((C0684j2) obj);
            }
        }, EnumC1953et.e);
    }

    private void a(com.android.tools.r8.utils.F f2, final Function function, final EnumC1953et enumC1953et) {
        final IdentityHashMap identityHashMap = new IdentityHashMap();
        final com.android.tools.r8.utils.F3 f3 = new com.android.tools.r8.utils.F3() { // from class: com.android.tools.r8.internal.dE$$ExternalSyntheticLambda19
            @Override // com.android.tools.r8.utils.F3
            public final void a(Object obj, Object obj2, Object obj3) {
                C1826dE.a(identityHashMap, (C0729q2) obj, (C0684j2) obj2, (AbstractC0748t0) obj3);
            }
        };
        f2.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.dE$$ExternalSyntheticLambda20
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(function, enumC1953et, f3, (C0684j2) obj, (CN) obj2);
            }
        });
        identityHashMap.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.dE$$ExternalSyntheticLambda21
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(function, (C0729q2) obj, (List) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ List a(C0729q2 c0729q2) {
        return new ArrayList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Map map, C0729q2 c0729q2, C0684j2 c0684j2, AbstractC0748t0 abstractC0748t0) {
        ((List) map.computeIfAbsent(c0729q2, new Function() { // from class: com.android.tools.r8.internal.dE$$ExternalSyntheticLambda18
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C1826dE.a((C0729q2) obj);
            }
        })).add(new C3979v2(c0684j2, abstractC0748t0));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    public void a(Function function, EnumC1953et enumC1953et, com.android.tools.r8.utils.F3 f3, C0684j2 c0684j2, CN cn) {
        com.android.tools.r8.graph.I4 i4 = (com.android.tools.r8.graph.I4) function.apply(c0684j2);
        if (i4.x()) {
            if (c0684j2.B0().M0()) {
                if (!f && i4.d().getType() != this.b.a().Z1) {
                    throw new AssertionError();
                }
                this.e.a(c0684j2, i4.r().getReference(), enumC1953et);
                return;
            }
            final AbstractC0748t0 abstractC0748t0C = i4.c();
            if (abstractC0748t0C.getReference() == c0684j2) {
                return;
            }
            AbstractC0727q0 abstractC0727q0A = i4.a();
            C0684j2 c0684j2B = null;
            c0684j2B = null;
            if (abstractC0727q0A.e0()) {
                if ((this.d.f instanceof ClassFileConsumer) && enumC1953et == EnumC1953et.f && abstractC0748t0C.a() != abstractC0727q0A && abstractC0748t0C.a().isInterface()) {
                    C0729q2 c0729q2A = a(abstractC0727q0A.a0(), abstractC0748t0C.t());
                    boolean z = f;
                    if (!z && c0729q2A == null) {
                        throw new AssertionError();
                    }
                    if (!z && c0729q2A == abstractC0748t0C.a()) {
                        throw new AssertionError();
                    }
                    f3.a(c0729q2A, c0684j2, abstractC0748t0C);
                    c0684j2B = abstractC0748t0C.getReference().b(c0729q2A.getType(), this.b.a());
                } else if (cn.stream().anyMatch(new Predicate() { // from class: com.android.tools.r8.internal.dE$$ExternalSyntheticLambda2
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return this.f$0.a(abstractC0748t0C, (com.android.tools.r8.graph.o5) obj);
                    }
                })) {
                    String strH0 = abstractC0727q0A.e.m() ? null : c0684j2.e.H0();
                    if (strH0 != null && strH0.equals(abstractC0748t0C.t().H0())) {
                        c0684j2B = abstractC0748t0C.getReference();
                    } else {
                        C0729q2 c0729q2A2 = a(abstractC0727q0A, abstractC0748t0C.a(), strH0);
                        if (!f && c0729q2A2 == null) {
                            throw new AssertionError();
                        }
                        f3.a(c0729q2A2, c0684j2, abstractC0748t0C);
                        c0684j2B = abstractC0748t0C.getReference().b(c0729q2A2.getType(), this.b.a());
                    }
                }
            }
            if (c0684j2B != null) {
                this.e.a(c0684j2, c0684j2B, enumC1953et);
            } else if (abstractC0748t0C instanceof com.android.tools.r8.graph.o5) {
                this.e.a(c0684j2, abstractC0748t0C.getReference(), enumC1953et);
            } else {
                this.e.a(c0684j2, a(abstractC0748t0C, i4.o(), cn, enumC1953et, c0684j2), enumC1953et);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Function function, C0729q2 c0729q2, List list) {
        list.sort(Comparator.comparing(new Function() { // from class: com.android.tools.r8.internal.dE$$ExternalSyntheticLambda10
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return (C0684j2) ((C3979v2) obj).a();
            }
        }));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            C3979v2 c3979v2 = (C3979v2) it.next();
            C0684j2 c0684j2 = (C0684j2) c3979v2.a();
            final AbstractC0748t0 abstractC0748t0 = (AbstractC0748t0) c3979v2.b();
            C0684j2 c0684j2B = c0684j2.b(c0729q2.getType(), this.b.a());
            if (c0729q2.Y().b(c0684j2B) == null) {
                final com.android.tools.r8.graph.W0 w0E = abstractC0748t0.e();
                com.android.tools.r8.graph.W0 w0A = w0E.a(this.b, c0729q2, new Consumer() { // from class: com.android.tools.r8.internal.dE$$ExternalSyntheticLambda12
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        this.f$0.a(w0E, abstractC0748t0, (com.android.tools.r8.graph.W0.a) obj);
                    }
                });
                c0729q2.d(w0A);
                this.c.a(abstractC0748t0, w0A.a(c0729q2));
            }
            if (!f && ((com.android.tools.r8.graph.I4) function.apply(c0684j2)).r().getReference() != c0684j2B) {
                throw new AssertionError();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(com.android.tools.r8.graph.W0 w0, AbstractC0748t0 abstractC0748t0, com.android.tools.r8.graph.W0.a aVar) {
        if (!w0.s1() && w0.Y0().J()) {
            if (!f) {
                abstractC0748t0.getClass();
                if (!(abstractC0748t0 instanceof C0644e4) && this.b.Y().a().a) {
                    throw new AssertionError();
                }
            }
            aVar.a(this.b.d().a(w0.getReference(), this.b.o()));
        }
        abstractC0748t0.getClass();
        boolean z = abstractC0748t0 instanceof C0644e4;
        AbstractC3938n2 abstractC3938n2 = AbstractC3938n2.a;
        if (z) {
            aVar.a(abstractC3938n2);
        } else {
            aVar.getClass();
        }
    }

    private C0729q2 a(C0729q2 c0729q2, C0767v2 c0767v2) {
        AbstractC0727q0 abstractC0727q0C;
        return (c0729q2.e.W() || (abstractC0727q0C = this.b.c(c0729q2.f)) == null || abstractC0727q0C.I1() || !((C3733i) this.b.f()).c(abstractC0727q0C.d, c0767v2)) ? c0729q2 : a(abstractC0727q0C.a0(), c0767v2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(AbstractC0748t0 abstractC0748t0, com.android.tools.r8.graph.o5 o5Var) {
        C0767v2 c0767v2T = abstractC0748t0.t();
        AbstractC0727q0 abstractC0727q0C = this.b.c(c0767v2T);
        if (abstractC0727q0C != null) {
            com.android.tools.r8.ir.optimize.V vA = com.android.tools.r8.ir.optimize.V.a(o5Var, c0767v2T, abstractC0727q0C.getAccessFlags(), this.b);
            com.android.tools.r8.ir.optimize.V vA2 = com.android.tools.r8.ir.optimize.V.a(o5Var, c0767v2T, abstractC0748t0.getAccessFlags(), this.b);
            com.android.tools.r8.ir.optimize.V v = com.android.tools.r8.ir.optimize.V.c;
            if (vA == v && vA2 != v) {
                return true;
            }
        }
        return false;
    }

    private C0729q2 a(AbstractC0727q0 abstractC0727q0, AbstractC0727q0 abstractC0727q1, String str) {
        C0729q2 c0729q2A;
        if (abstractC0727q0 == abstractC0727q1 || abstractC0727q0.I1()) {
            return null;
        }
        if (((C3733i) this.b.f()).c(abstractC0727q0.f, abstractC0727q1.d)) {
            c0729q2A = a(this.b.c(abstractC0727q0.f), abstractC0727q1, str);
        } else {
            C0729q2 c0729q2A2 = null;
            for (C0767v2 c0767v2 : abstractC0727q0.g.a) {
                if (((C3733i) this.b.f()).c(c0767v2, abstractC0727q1.d)) {
                    c0729q2A2 = a(this.b.c(c0767v2), abstractC0727q1, str);
                }
            }
            c0729q2A = c0729q2A2;
        }
        if (c0729q2A != null) {
            return c0729q2A;
        }
        if (abstractC0727q0.e.m() || abstractC0727q0.d.H0().equals(str)) {
            return abstractC0727q0.a0();
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(final Set set, com.android.tools.r8.graph.R2 r2) {
        com.android.tools.r8.graph.U2 u2 = (com.android.tools.r8.graph.U2) r2;
        final com.android.tools.r8.graph.Y0 y0E = u2.e();
        u2.b(new Consumer() { // from class: com.android.tools.r8.internal.dE$$ExternalSyntheticLambda11
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(y0E, set, (com.android.tools.r8.graph.Y0) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(com.android.tools.r8.graph.Y0 y0, Set set, com.android.tools.r8.graph.Y0 y1) {
        boolean z = f;
        if (!z && y0 == y1) {
            throw new AssertionError();
        }
        if (!z && y0 != ((C3733i) this.b.f()).c(y1).t()) {
            throw new AssertionError();
        }
        set.remove(y1);
    }

    private Set a(ExecutorService executorService) {
        final Set setA = OU.a();
        final C3733i c3733i = (C3733i) this.b.f();
        Objects.requireNonNull(c3733i);
        C3879c3.a(new InterfaceC3877c1() { // from class: com.android.tools.r8.internal.dE$$ExternalSyntheticLambda15
            @Override // com.android.tools.r8.utils.InterfaceC3877c1
            public final void forEach(Consumer consumer) {
                c3733i.a(consumer);
            }
        }, new InterfaceC3905h3() { // from class: com.android.tools.r8.internal.dE$$ExternalSyntheticLambda16
            @Override // com.android.tools.r8.utils.InterfaceC3905h3
            public final void accept(Object obj) {
                this.f$0.a(setA, (com.android.tools.r8.graph.o5) obj);
            }
        }, executorService);
        return setA;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Set set, com.android.tools.r8.graph.o5 o5Var) {
        if (o5Var.e().q1()) {
            o5Var.a(new C1750cE(this, this.b, o5Var, set));
        }
    }
}
