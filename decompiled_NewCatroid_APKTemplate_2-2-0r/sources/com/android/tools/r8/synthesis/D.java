package com.android.tools.r8.synthesis;

import com.android.tools.r8.ClassFileConsumer;
import com.android.tools.r8.Diagnostic;
import com.android.tools.r8.FeatureSplit;
import com.android.tools.r8.SyntheticInfoConsumer;
import com.android.tools.r8.graph.AbstractC0674i0;
import com.android.tools.r8.graph.AbstractC0681j0;
import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0664h;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0737r2;
import com.android.tools.r8.graph.C0754u0;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.C0792w4;
import com.android.tools.r8.graph.N2;
import com.android.tools.r8.graph.W0;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.graph.q5;
import com.android.tools.r8.graph.u5;
import com.android.tools.r8.internal.AbstractC0968Es;
import com.android.tools.r8.internal.AbstractC0984Fi;
import com.android.tools.r8.internal.AbstractC1048Hm;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.AbstractC2178hm;
import com.android.tools.r8.internal.AbstractC2734ot;
import com.android.tools.r8.internal.AbstractC3135uW;
import com.android.tools.r8.internal.C1121Ka;
import com.android.tools.r8.internal.C1314Rb;
import com.android.tools.r8.internal.C1507Xl;
import com.android.tools.r8.internal.C1549Za;
import com.android.tools.r8.internal.DH;
import com.android.tools.r8.internal.EnumC0928Dk;
import com.android.tools.r8.internal.InterfaceC3205vM;
import com.android.tools.r8.internal.OU;
import com.android.tools.r8.internal.T3;
import com.android.tools.r8.naming.AbstractC3649o0;
import com.android.tools.r8.utils.A1;
import com.android.tools.r8.utils.C3912j0;
import com.android.tools.r8.utils.I1;
import com.android.tools.r8.utils.InterfaceC3877c1;
import com.android.tools.r8.utils.StringDiagnostic;
import com.android.tools.r8.utils.T2;
import com.android.tools.r8.utils.V1;
import com.android.tools.r8.utils.y3;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class D implements InterfaceC3856n {
    static final /* synthetic */ boolean g = true;
    private final int a;
    private final L b;
    private final C3844c c;
    private final z d;
    private final InterfaceC3862u e;
    private final y f;

    D(C3842a c3842a) {
        this(c3842a.b, c3842a.c, c3842a.e);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(boolean z, U u) {
        return z == u.c().h();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(final C0767v2 c0767v2, final Set set, final C0767v2 c0767v3, List list) {
        list.forEach(new Consumer() { // from class: com.android.tools.r8.synthesis.D$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                D.a(c0767v2, set, c0767v3, (S) obj);
            }
        });
    }

    public final boolean c(C0729q2 c0729q2) {
        AbstractC3855m abstractC3855m = (AbstractC3855m) this.d.a.get(c0729q2.d);
        if (abstractC3855m != null) {
            return abstractC3855m.f().h();
        }
        Iterable iterableB = this.c.b(c0729q2.d);
        Iterator it = iterableB.iterator();
        if (!it.hasNext()) {
            return false;
        }
        final boolean zH = ((U) it.next()).c().h();
        if (g || AbstractC2734ot.a(iterableB, new InterfaceC3205vM() { // from class: com.android.tools.r8.synthesis.D$$ExternalSyntheticLambda13
            @Override // com.android.tools.r8.internal.InterfaceC3205vM
            public final boolean apply(Object obj) {
                return D.a(zH, (U) obj);
            }
        })) {
            return zH;
        }
        throw new AssertionError();
    }

    public final boolean d() {
        return !this.d.a.isEmpty();
    }

    public final boolean e() {
        return this.a == 2;
    }

    public final boolean f(C0767v2 c0767v2) {
        return this.c.a(c0767v2);
    }

    public final boolean g(C0767v2 c0767v2) {
        AbstractC3855m abstractC3855m = (AbstractC3855m) this.d.a.get(c0767v2);
        if (abstractC3855m != null) {
            return abstractC3855m.f().e();
        }
        List<S> list = (List) this.c.a().get(c0767v2);
        if (list == null) {
            return false;
        }
        if (list.size() == 1 && ((S) list.get(0)).c().e()) {
            return true;
        }
        if (g) {
            return false;
        }
        for (S s : list) {
            if (!g && s.c().e()) {
                throw new AssertionError();
            }
        }
        return false;
    }

    public final boolean h(C0767v2 c0767v2) {
        return this.d.a.containsKey(c0767v2);
    }

    public final boolean i(C0767v2 c0767v2) {
        return this.c.a(c0767v2) || this.d.a.containsKey(c0767v2);
    }

    private D(int i, C3844c c3844c, y yVar) {
        this.d = new z();
        this.a = i;
        this.c = c3844c;
        this.b = c3844c.d();
        this.e = yVar.a();
        this.f = yVar;
    }

    public final Set a(final C0767v2 c0767v2) {
        final Set setC = OU.c();
        this.c.c().forEach(new BiConsumer() { // from class: com.android.tools.r8.synthesis.D$$ExternalSyntheticLambda19
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                D.a(c0767v2, setC, (C0767v2) obj, (List) obj2);
            }
        });
        this.c.a().forEach(new BiConsumer() { // from class: com.android.tools.r8.synthesis.D$$ExternalSyntheticLambda20
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                D.b(c0767v2, setC, (C0767v2) obj, (List) obj2);
            }
        });
        return setC;
    }

    public final L b() {
        return this.b;
    }

    public final AbstractC0984Fi e(C0767v2 c0767v2) {
        Iterable iterableB = I1.b(this.c.b(c0767v2), new Function() { // from class: com.android.tools.r8.synthesis.D$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((U) obj).c();
            }
        });
        AbstractC3855m abstractC3855m = (AbstractC3855m) this.d.a.get(c0767v2);
        if (abstractC3855m != null) {
            iterableB = AbstractC0984Fi.a(iterableB, I1.b(abstractC3855m.f()));
        }
        return (AbstractC0984Fi) iterableB;
    }

    public final IdentityHashMap b(C0702m c0702m) {
        if (!g && !e()) {
            throw new AssertionError();
        }
        final C0722p1 c0722p1A = c0702m.a();
        AbstractC2178hm abstractC2178hmB = this.c.b();
        final AbstractC3649o0 abstractC3649o0Z = c0702m.z();
        final IdentityHashMap identityHashMap = new IdentityHashMap(abstractC2178hmB.size());
        abstractC2178hmB.forEach(new BiConsumer() { // from class: com.android.tools.r8.synthesis.D$$ExternalSyntheticLambda12
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                D.a(identityHashMap, abstractC3649o0Z, c0722p1A, (C0767v2) obj, (Set) obj2);
            }
        });
        return identityHashMap;
    }

    final boolean d(C0729q2 c0729q2) {
        C3844c c3844c = this.c;
        return c3844c.e.contains(c0729q2.getType());
    }

    public final List d(C0767v2 c0767v2) {
        if (!i(c0767v2)) {
            return Collections.emptyList();
        }
        final C1507Xl c1507XlG = AbstractC1639am.g();
        b(c0767v2, new Consumer() { // from class: com.android.tools.r8.synthesis.D$$ExternalSyntheticLambda21
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                D.b(c1507XlG, (C3846e) obj);
            }
        });
        return c1507XlG.a();
    }

    public final boolean b(C0729q2 c0729q2) {
        return i(c0729q2.d);
    }

    public final List c() {
        z zVar = this.d;
        zVar.getClass();
        ArrayList arrayList = new ArrayList(zVar.a.size());
        for (AbstractC3855m abstractC3855m : zVar.a.values()) {
            if (abstractC3855m.h()) {
                arrayList.add(abstractC3855m.c().a());
            }
        }
        return Collections.unmodifiableList(arrayList);
    }

    public final AbstractC1639am b(C0767v2 c0767v2) {
        final C1507Xl c1507XlG = AbstractC1639am.g();
        b(c0767v2, new Consumer() { // from class: com.android.tools.r8.synthesis.D$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                D.a(c1507XlG, (C3846e) obj);
            }
        });
        return c1507XlG.a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(C1507Xl c1507Xl, C3846e c3846e) {
        c1507Xl.a(c3846e.b());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(final C0767v2 c0767v2, final Set set, final C0767v2 c0767v3, List list) {
        list.forEach(new Consumer() { // from class: com.android.tools.r8.synthesis.D$$ExternalSyntheticLambda9
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                D.a(c0767v2, set, c0767v3, (J) obj);
            }
        });
    }

    private C0729q2 b(final L.b bVar, final Consumer consumer, Consumer consumer2, C3846e c3846e, final C0702m c0702m) {
        C0729q2 c0729q2A0;
        Function function = new Function() { // from class: com.android.tools.r8.synthesis.D$$ExternalSyntheticLambda14
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return D.c(bVar, c0702m, (C3846e) obj);
            }
        };
        C0767v2 c0767v2 = (C0767v2) function.apply(c3846e);
        AbstractC0727q0 abstractC0727q0C = c0702m.c(c0767v2);
        C0729q2 c0729q2A1 = null;
        if (abstractC0727q0C == null) {
            c0729q2A0 = null;
        } else if (abstractC0727q0C.e0()) {
            c0729q2A0 = abstractC0727q0C.a0();
        } else {
            if (!abstractC0727q0C.f0() || !bVar.e()) {
                a(abstractC0727q0C, ConfigConstants.CONFIG_KEY_PROGRAM, c0702m);
                throw null;
            }
            c0729q2A0 = null;
        }
        if (c0729q2A0 != null) {
            return c0729q2A0;
        }
        synchronized (c0767v2) {
            AbstractC0727q0 abstractC0727q0C2 = c0702m.c(c0767v2);
            if (abstractC0727q0C2 != null) {
                if (abstractC0727q0C2.e0()) {
                    c0729q2A1 = abstractC0727q0C2.a0();
                } else if (!abstractC0727q0C2.f0() || !bVar.e()) {
                    a(abstractC0727q0C2, ConfigConstants.CONFIG_KEY_PROGRAM, c0702m);
                    throw null;
                }
            }
            if (c0729q2A1 != null) {
                return c0729q2A1;
            }
            if (!g && i(c0767v2)) {
                throw new AssertionError();
            }
            Consumer consumer3 = new Consumer() { // from class: com.android.tools.r8.synthesis.D$$ExternalSyntheticLambda15
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    D.a(consumer, (P) obj);
                }
            };
            C0767v2 c0767v2A = c0702m.t.a(c3846e.c());
            if (c0767v2A != null) {
                c0702m.t.a(c0767v2, (C0767v2) function.apply(C3846e.a(c0767v2A)));
            }
            P p = new P(c0767v2, bVar, c3846e, c0702m.a());
            consumer3.accept(p);
            C0729q2 c0729q2 = (C0729q2) p.a();
            if (!P.t) {
                com.android.tools.r8.graph.E e = c0729q2.e;
                if (!e.q() || e.V()) {
                    throw new AssertionError();
                }
            }
            Q q = new Q(bVar, c3846e, c0729q2);
            this.d.a.put(q.e().getType(), q);
            consumer2.accept(c0729q2);
            return c0729q2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C0767v2 c0767v2, Set set, C0767v2 c0767v3, J j) {
        if (j.b().c() == c0767v2) {
            set.add(c0767v3);
        }
    }

    private AbstractC1639am c(final C0767v2 c0767v2) {
        return V1.b(new InterfaceC3877c1() { // from class: com.android.tools.r8.synthesis.D$$ExternalSyntheticLambda6
            @Override // com.android.tools.r8.utils.InterfaceC3877c1
            public final void forEach(Consumer consumer) {
                this.f$0.b(c0767v2, consumer);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C0767v2 c(L.b bVar, C0702m c0702m, C3846e c3846e) {
        return L.a(bVar, c3846e, c0702m.a());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C0767v2 c0767v2, Set set, C0767v2 c0767v3, S s) {
        if (s.b().c() == c0767v2) {
            set.add(c0767v3);
        }
    }

    public final y a() {
        return this.f;
    }

    public static C3842a a(AbstractC0681j0 abstractC0681j0, y yVar) {
        return new C3842a(1, abstractC0681j0, new C3844c(abstractC0681j0.e.j(), AbstractC2178hm.s(), AbstractC2178hm.s(), AbstractC2178hm.s(), AbstractC1048Hm.k()), AbstractC1639am.i(), yVar);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Map map, final AbstractC3649o0 abstractC3649o0, final C0722p1 c0722p1, C0767v2 c0767v2, Set set) {
        Set set2 = (Set) map.put(abstractC3649o0.a(c0722p1, c0767v2), T2.a(set, new Function() { // from class: com.android.tools.r8.synthesis.D$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return abstractC3649o0.a(c0722p1, (C0767v2) obj);
            }
        }));
        if (!g && set2 != null) {
            throw new AssertionError();
        }
    }

    public static void a(C0702m c0702m) {
        AbstractC0727q0 abstractC0727q0D;
        D D = c0702m.D();
        boolean z = g;
        if (!z && !D.c.e()) {
            throw new AssertionError();
        }
        if (!z && !D.d.a.isEmpty()) {
            throw new AssertionError();
        }
        C3844c c3844c = D.c;
        c3844c.getClass();
        final C3843b c3843b = new C3843b(c3844c);
        for (C0729q2 c0729q2 : c0702m.f().d()) {
            final F fB = F.b(c0702m, c0729q2);
            if (!c0702m.Y().t0 && fB.a() != null && ((abstractC0727q0D = c0702m.f().d(fB.a().c())) == null || abstractC0727q0D.I1())) {
                c0702m.Y().e.error(new StringDiagnostic("Attempt at compiling intermediate artifact without its context", c0729q2.c));
            }
            if (fB.d()) {
                c0729q2.l(new Consumer() { // from class: com.android.tools.r8.synthesis.D$$ExternalSyntheticLambda23
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        D.a(c3843b, fB, (o5) obj);
                    }
                });
            } else if (fB.c()) {
                Q q = new Q(fB.b(), fB.a(), c0729q2);
                c3843b.a(new S(q.f(), q.d(), ((C0729q2) q.d).getType()));
            }
        }
        C3844c c3844cA = c3843b.b().a();
        if (c3844cA.e()) {
            return;
        }
        C3842a c3842a = new C3842a(D.a, c0702m.f().b(), c3844cA, AbstractC1639am.i(), D.f);
        if (c0702m.f().h()) {
            c0702m.h0().d(c0702m.f().n().a(c3842a));
        } else {
            c0702m.j0().d(new C0664h(c3842a, c0702m.f().f()));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C0767v2 b(L.b bVar, C0702m c0702m, C3846e c3846e) {
        return L.a(bVar, c3846e, c0702m.a());
    }

    public final o5 b(C c, final C1314Rb c1314Rb, C0702m c0702m, Consumer consumer) {
        return a(c, c1314Rb.a(), c0702m, consumer, new Supplier() { // from class: com.android.tools.r8.synthesis.D$$ExternalSyntheticLambda22
            @Override // java.util.function.Supplier
            public final Object get() {
                return c1314Rb.b();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(C3843b c3843b, F f, o5 o5Var) {
        I i = new I(f.b(), f.a(), o5Var);
        c3843b.getClass();
        c3843b.a(i.j());
    }

    @Override // com.android.tools.r8.synthesis.InterfaceC3856n
    public final com.android.tools.r8.graph.O a(Function function, C0767v2 c0767v2) {
        AbstractC0727q0 abstractC0727q0E;
        L.b bVarF;
        AbstractC3855m abstractC3855m = (AbstractC3855m) this.d.a.get(c0767v2);
        if (abstractC3855m != null) {
            abstractC0727q0E = abstractC3855m.e();
            bVarF = abstractC3855m.f();
            boolean z = g;
            if (!z && abstractC0727q0E.e0() != abstractC3855m.h()) {
                throw new AssertionError();
            }
            if (!z && abstractC0727q0E.A1() != abstractC3855m.g()) {
                throw new AssertionError();
            }
        } else {
            abstractC0727q0E = null;
            bVarF = null;
        }
        if (abstractC0727q0E != null) {
            boolean z2 = g;
            if (!z2 && bVarF == null) {
                throw new AssertionError();
            }
            if (z2 || !((com.android.tools.r8.graph.O) function.apply(c0767v2)).T() || bVarF.g()) {
                return abstractC0727q0E;
            }
            throw new AssertionError("Pending synthetic definition also present in the active program: " + c0767v2);
        }
        return (com.android.tools.r8.graph.O) function.apply(c0767v2);
    }

    public final boolean a(C0729q2 c0729q2, EnumC0928Dk enumC0928Dk) {
        int iA;
        boolean z = g;
        if (!z && !i(c0729q2.d)) {
            throw new AssertionError();
        }
        if (enumC0928Dk.a()) {
            return true;
        }
        if (i(c0729q2.d)) {
            Iterable iterableB = this.c.b(c0729q2.getType());
            if (!AbstractC2734ot.c(iterableB)) {
                if (!z) {
                    if (iterableB instanceof Collection) {
                        iA = ((Collection) iterableB).size();
                    } else {
                        Iterator it = iterableB.iterator();
                        long j = 0;
                        while (it.hasNext()) {
                            it.next();
                            j++;
                        }
                        iA = AbstractC0968Es.a(j);
                    }
                    if (iA != 1) {
                        throw new AssertionError();
                    }
                }
                if (((U) iterableB.iterator().next()).c() == this.b.v) {
                    return true;
                }
            } else {
                AbstractC3855m abstractC3855m = (AbstractC3855m) this.d.a.get(c0729q2.getType());
                if (abstractC3855m != null) {
                    if (abstractC3855m.f() == this.b.v) {
                        return true;
                    }
                } else if (!z) {
                    throw new AssertionError();
                }
            }
        }
        return false;
    }

    public final boolean a(C0729q2 c0729q2) {
        if (!g && !i(c0729q2.d)) {
            throw new AssertionError();
        }
        C3844c c3844c = this.c;
        return c3844c.e.contains(c0729q2.getType());
    }

    public final boolean a(C0767v2 c0767v2, C c) {
        L.b bVarA = c.a(this.b);
        AbstractC3855m abstractC3855m = (AbstractC3855m) this.d.a.get(c0767v2);
        return (abstractC3855m != null && abstractC3855m.f() == bVarA) || this.c.a(c0767v2, bVarA);
    }

    public final FeatureSplit a(C0767v2 c0767v2, FeatureSplit featureSplit) {
        boolean z = g;
        if (!z && !i(c0767v2)) {
            throw new AssertionError();
        }
        if (a(c0767v2, new C() { // from class: com.android.tools.r8.synthesis.D$$ExternalSyntheticLambda25
            @Override // com.android.tools.r8.synthesis.C
            public final L.b a(L l) {
                return l.j;
            }
        })) {
            return FeatureSplit.BASE;
        }
        AbstractC1639am abstractC1639amC = c(c0767v2);
        if (abstractC1639amC.isEmpty()) {
            if (z) {
                return featureSplit;
            }
            throw new AssertionError("Expected synthetic to have at least one synthesizing context: " + c0767v2.L0());
        }
        if (!z) {
            Function function = new Function() { // from class: com.android.tools.r8.synthesis.D$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ((C3846e) obj).a();
                }
            };
            if (!z && abstractC1639amC.isEmpty()) {
                throw new AssertionError();
            }
            FeatureSplit featureSplit2 = (FeatureSplit) function.apply(abstractC1639amC.get(0));
            for (int i = 1; i < abstractC1639amC.size(); i++) {
                if (!g && featureSplit2 != function.apply(abstractC1639amC.get(i))) {
                    throw new AssertionError();
                }
            }
        }
        return ((C3846e) abstractC1639amC.get(0)).a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void b(C0767v2 c0767v2, Consumer consumer) {
        Iterator it = this.c.b(c0767v2).iterator();
        while (it.hasNext()) {
            consumer.accept(((U) it.next()).b());
        }
        AbstractC3855m abstractC3855m = (AbstractC3855m) this.d.a.get(c0767v2);
        if (abstractC3855m != null) {
            consumer.accept(abstractC3855m.d());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C1507Xl c1507Xl, C3846e c3846e) {
        c1507Xl.a(c3846e.c());
    }

    public final Set a(C0729q2 c0729q2, A a) {
        if (!g && !i(c0729q2.d)) {
            throw new AssertionError();
        }
        return a.a(c0729q2);
    }

    public final C0767v2 a(final A1 a1, C0767v2 c0767v2) {
        if (!i(c0767v2)) {
            return null;
        }
        final com.android.tools.r8.utils.N n = new com.android.tools.r8.utils.N(null);
        b(c0767v2, new Consumer() { // from class: com.android.tools.r8.synthesis.D$$ExternalSyntheticLambda17
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                D.a(n, a1, (C3846e) obj);
            }
        });
        return (C0767v2) n.a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(com.android.tools.r8.utils.N n, A1 a1, C3846e c3846e) {
        if (!g && n.a() != null) {
            throw new AssertionError();
        }
        n.a(c3846e.a(a1.t0));
    }

    public final boolean a(o5 o5Var) {
        AbstractC2178hm abstractC2178hmC = this.c.c();
        C0767v2 c0767v2T = o5Var.t();
        Object objEmptyList = Collections.emptyList();
        Object obj = abstractC2178hmC.get(c0767v2T);
        if (obj != null) {
            objEmptyList = obj;
        }
        Iterator it = ((List) objEmptyList).iterator();
        while (it.hasNext()) {
            if (((J) it.next()).c().equals(this.b.D)) {
                return true;
            }
        }
        AbstractC3855m abstractC3855m = (AbstractC3855m) this.d.a.get(o5Var.t());
        if (abstractC3855m != null) {
            return abstractC3855m.f().equals(this.b.D);
        }
        return false;
    }

    public final void a(C0729q2 c0729q2, Predicate predicate, Predicate predicate2) {
        Iterable iterableB = this.c.b(c0729q2.getType());
        AbstractC3855m abstractC3855m = (AbstractC3855m) this.d.a.get(c0729q2.getType());
        if (abstractC3855m != null) {
            iterableB = AbstractC0984Fi.a(iterableB, I1.b(abstractC3855m.j()));
        }
        if (AbstractC2734ot.b(iterableB, new InterfaceC3205vM() { // from class: com.android.tools.r8.synthesis.D$$ExternalSyntheticLambda24
            @Override // com.android.tools.r8.internal.InterfaceC3205vM
            public final boolean apply(Object obj) {
                return this.f$0.a((U) obj);
            }
        })) {
            if (!g && !predicate.test(c0729q2)) {
                throw new AssertionError();
            }
        } else if (!g && !predicate2.test(c0729q2)) {
            throw new AssertionError();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean a(U u) {
        return u.c().equals(this.b.v);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int a(U u, U u2) {
        return u.d().a(u2.d());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Consumer consumer, P p) {
        p.i();
        consumer.accept(p);
    }

    public final C0729q2 a(C c, final C1314Rb c1314Rb, final C0702m c0702m, Consumer consumer) {
        C3846e c3846eA;
        final L.b bVarA = c.a(this.b);
        C0729q2 c0729q2A = c1314Rb.a();
        A1 a1Y = c0702m.Y();
        if (c0702m.f().h()) {
            c3846eA = a(c0729q2A, c0702m.h().p(), a1Y, c0702m.C());
        } else {
            c3846eA = a(c0729q2A, C1121Ka.a(), a1Y, AbstractC3135uW.b());
        }
        Function function = new Function() { // from class: com.android.tools.r8.synthesis.D$$ExternalSyntheticLambda11
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return D.a(bVarA, c1314Rb, c0702m, (C3846e) obj);
            }
        };
        C0767v2 c0767v2 = (C0767v2) function.apply(c3846eA);
        C0767v2 c0767v2A = c0702m.t.a(c3846eA.c());
        if (c0767v2A != null) {
            c0702m.t.a(c0767v2, (C0767v2) function.apply(C3846e.a(c0767v2A)));
        }
        P p = new P(c0767v2, bVarA, c3846eA, c0702m.a());
        consumer.accept(p);
        C0729q2 c0729q2 = (C0729q2) p.a();
        if (!P.t) {
            com.android.tools.r8.graph.E e = c0729q2.e;
            if (!e.q() || e.V()) {
                throw new AssertionError();
            }
        }
        Q q = new Q(bVarA, c3846eA, c0729q2);
        this.d.a.put(q.e().getType(), q);
        return c0729q2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C0767v2 a(L.b bVar, C1314Rb c1314Rb, C0702m c0702m, C3846e c3846e) {
        return L.a(bVar, c3846e, c1314Rb.b(), c0702m);
    }

    public final C0729q2 a(C c, C0729q2 c0729q2, final C0702m c0702m, Consumer consumer) {
        C3846e c3846eA;
        final L.b bVarA = c.a(this.b);
        if (c0729q2.e0()) {
            C0729q2 c0729q2A0 = c0729q2.a0();
            A1 a1Y = c0702m.Y();
            if (c0702m.f().h()) {
                c3846eA = a(c0729q2A0, c0702m.h().p(), a1Y, c0702m.C());
            } else {
                c3846eA = a(c0729q2A0, C1121Ka.a(), a1Y, AbstractC3135uW.b());
            }
        } else {
            c3846eA = C3846e.a((com.android.tools.r8.graph.T) null);
        }
        Function function = new Function() { // from class: com.android.tools.r8.synthesis.D$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return D.a(bVarA, c0702m, (C3846e) obj);
            }
        };
        C0767v2 c0767v2 = (C0767v2) function.apply(c3846eA);
        C0767v2 c0767v2A = c0702m.t.a(c3846eA.c());
        if (c0767v2A != null) {
            c0702m.t.a(c0767v2, (C0767v2) function.apply(C3846e.a(c0767v2A)));
        }
        P p = new P(c0767v2, bVarA, c3846eA, c0702m.a());
        consumer.accept(p);
        C0729q2 c0729q3 = (C0729q2) p.a();
        if (!P.t) {
            com.android.tools.r8.graph.E e = c0729q3.e;
            if (!e.q() || e.V()) {
                throw new AssertionError();
            }
        }
        Q q = new Q(bVarA, c3846eA, c0729q3);
        this.d.a.put(q.e().getType(), q);
        return c0729q3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C0767v2 a(L.b bVar, C0702m c0702m, C3846e c3846e) {
        return L.a(bVar, c3846e, c0702m.a());
    }

    public final C0729q2 a(C c, AbstractC0727q0 abstractC0727q0, C0702m c0702m) {
        C3846e c3846eA;
        L.b bVarA = c.a(this.b);
        boolean z = g;
        if (!z && !bVarA.d()) {
            throw new AssertionError();
        }
        if (abstractC0727q0.e0()) {
            C0729q2 c0729q2A0 = abstractC0727q0.a0();
            A1 a1Y = c0702m.Y();
            if (c0702m.f().h()) {
                c3846eA = a(c0729q2A0, c0702m.h().p(), a1Y, c0702m.C());
            } else {
                c3846eA = a(c0729q2A0, C1121Ka.a(), a1Y, AbstractC3135uW.b());
            }
        } else {
            c3846eA = C3846e.a(abstractC0727q0.N());
        }
        C0767v2 c0767v2A = L.a(bVarA, c3846eA, c0702m.a());
        AbstractC0727q0 abstractC0727q0C = c0702m.c(c0767v2A);
        if (!z && abstractC0727q0C == null) {
            throw new AssertionError("Missing existing fixed class " + c0767v2A);
        }
        if (!z && !i(c0767v2A)) {
            throw new AssertionError();
        }
        if (z || abstractC0727q0C.e0()) {
            return abstractC0727q0C.a0();
        }
        throw new AssertionError();
    }

    private C3846e a(C0729q2 c0729q2, C1121Ka c1121Ka, A1 a1, AbstractC3135uW abstractC3135uW) {
        C0767v2 type = c0729q2.getType();
        AbstractC3855m abstractC3855m = (AbstractC3855m) this.d.a.get(type);
        if (abstractC3855m != null) {
            return abstractC3855m.d();
        }
        Iterable iterableB = this.c.b(type);
        if (!AbstractC2734ot.c(iterableB)) {
            Comparator comparator = new Comparator() { // from class: com.android.tools.r8.synthesis.D$$ExternalSyntheticLambda8
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return D.a((U) obj, (U) obj2);
                }
            };
            Object obj = null;
            for (Object obj2 : iterableB) {
                if (obj == null || comparator.compare(obj2, obj) < 0) {
                    obj = obj2;
                }
            }
            return ((U) obj).b();
        }
        c1121Ka.getClass();
        return C3846e.a(c0729q2, c1121Ka.a(c0729q2.getType(), a1, abstractC3135uW, this));
    }

    public final C0729q2 a(C c, AbstractC0727q0 abstractC0727q0, C0702m c0702m, Consumer consumer, Consumer consumer2) {
        C3846e c3846eA;
        L.b bVarA = c.a(this.b);
        if (!g && !bVarA.d()) {
            throw new AssertionError();
        }
        if (abstractC0727q0.e0()) {
            C0729q2 c0729q2A0 = abstractC0727q0.a0();
            A1 a1Y = c0702m.Y();
            if (c0702m.f().h()) {
                c3846eA = a(c0729q2A0, c0702m.h().p(), a1Y, c0702m.C());
            } else {
                c3846eA = a(c0729q2A0, C1121Ka.a(), a1Y, AbstractC3135uW.b());
            }
        } else {
            c3846eA = C3846e.a(abstractC0727q0.N());
        }
        return b(bVarA, consumer, consumer2, c3846eA, c0702m);
    }

    public final o5 a(C0759u2 c0759u2, C0737r2 c0737r2, C c, q5 q5Var, C0702m c0702m, Consumer consumer, Consumer consumer2, Consumer consumer3) {
        W0 w0B;
        L.b bVarA = c.a(this.b);
        C0729q2 c0729q2A = a(c, q5Var.b(), c0702m, consumer, C3912j0.b());
        C0684j2 c0684j2A = c0702m.a().a(c0729q2A.getType(), c0737r2, c0759u2);
        C0792w4 c0792w4Y = c0729q2A.Y();
        synchronized (c0792w4Y) {
            w0B = c0792w4Y.b(c0684j2A);
            if (w0B == null) {
                H h = new H(c0702m.a(), c0729q2A.getType(), bVarA);
                h.a(c0684j2A.C0());
                h.a(c0684j2A.H0());
                consumer2.accept(h);
                w0B = h.a();
                c0792w4Y.b(w0B);
                consumer3.accept(AbstractC0748t0.a(c0729q2A, w0B));
            }
        }
        return new o5(c0729q2A, w0B);
    }

    private static void a(AbstractC0727q0 abstractC0727q0, String str, C0702m c0702m) {
        String str2;
        if (!abstractC0727q0.e0()) {
            str2 = abstractC0727q0.A1() ? ModuleXmlParser.CLASSPATH : "library";
        } else {
            str2 = ConfigConstants.CONFIG_KEY_PROGRAM;
        }
        throw c0702m.a0().b("Cannot ensure " + abstractC0727q0.d + " as a synthetic " + str + " class, because it is already a " + str2 + " class.");
    }

    private C0754u0 a(final L.b bVar, Consumer consumer, Consumer consumer2, C3846e c3846e, final C0702m c0702m) {
        Function function = new Function() { // from class: com.android.tools.r8.synthesis.D$$ExternalSyntheticLambda16
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return D.b(bVar, c0702m, (C3846e) obj);
            }
        };
        C0767v2 c0767v2 = (C0767v2) function.apply(c3846e);
        synchronized (c0767v2) {
            AbstractC0727q0 abstractC0727q0C = c0702m.c(c0767v2);
            if (abstractC0727q0C != null) {
                if (abstractC0727q0C instanceof C0754u0) {
                    return abstractC0727q0C.m();
                }
                a(abstractC0727q0C, ModuleXmlParser.CLASSPATH, c0702m);
                throw null;
            }
            C0767v2 c0767v2A = c0702m.t.a(c3846e.c());
            if (c0767v2A != null) {
                c0702m.t.a(c0767v2, (C0767v2) function.apply(C3846e.a(c0767v2A)));
            }
            C3851i c3851i = new C3851i(c0767v2, bVar, c3846e, c0702m.a());
            consumer.accept(c3851i);
            C0754u0 c0754u0 = (C0754u0) c3851i.a();
            C3852j c3852j = new C3852j(bVar, c3846e, c0754u0);
            this.d.a.put(c3852j.e().getType(), c3852j);
            consumer2.accept(c0754u0);
            return c0754u0;
        }
    }

    public final C0754u0 a(C c, C0767v2 c0767v2, C0702m c0702m, Consumer consumer, Consumer consumer2) {
        return a(c.a(this.b), consumer, consumer2, C3846e.a(c0767v2), c0702m);
    }

    public final com.android.tools.r8.graph.S a(C0759u2 c0759u2, C0737r2 c0737r2, C c, C0767v2 c0767v2, C0702m c0702m, Consumer consumer, Consumer consumer2, Consumer consumer3) {
        return a(c0759u2, c0737r2, c.a(this.b), c0702m, consumer3, a(c.a(this.b), consumer, consumer2, C3846e.a(c0767v2), c0702m));
    }

    public final C0754u0 a(C c, com.android.tools.r8.graph.T t, C0702m c0702m, Consumer consumer, Consumer consumer2) {
        return a(c.a(this.b), consumer, consumer2, C3846e.a(t), c0702m);
    }

    public final com.android.tools.r8.graph.S a(C0759u2 c0759u2, C0737r2 c0737r2, C c, com.android.tools.r8.graph.T t, C0702m c0702m, Consumer consumer, Consumer consumer2, Consumer consumer3) {
        return a(c0759u2, c0737r2, c.a(this.b), c0702m, consumer3, a(c, t, c0702m, consumer, consumer2));
    }

    private static com.android.tools.r8.graph.S a(C0759u2 c0759u2, C0737r2 c0737r2, L.b bVar, C0702m c0702m, final Consumer consumer, C0754u0 c0754u0) {
        W0 w0B;
        C0684j2 c0684j2A = c0702m.a().a(c0754u0.getType(), c0737r2, c0759u2);
        Consumer consumer2 = new Consumer() { // from class: com.android.tools.r8.synthesis.D$$ExternalSyntheticLambda18
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                D.a(consumer, (H) obj);
            }
        };
        Consumer consumerB = C3912j0.b();
        C0792w4 c0792w4Y = c0754u0.Y();
        synchronized (c0792w4Y) {
            w0B = c0792w4Y.b(c0684j2A);
            if (w0B == null) {
                H h = new H(c0702m.a(), c0754u0.getType(), bVar);
                h.a(c0684j2A.C0());
                h.a(c0684j2A.H0());
                consumer2.accept(h);
                w0B = h.a();
                c0792w4Y.b(w0B);
                consumerB.accept(AbstractC0748t0.a(c0754u0, w0B));
            }
        }
        return new com.android.tools.r8.graph.S(c0754u0, w0B);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Consumer consumer, H h) {
        consumer.accept(h.b());
    }

    public final C0729q2 a(Supplier supplier, C c, C0767v2 c0767v2, Collection collection, C0702m c0702m, Consumer consumer, Consumer consumer2) {
        L.b bVarA = c.a(this.b);
        boolean z = g;
        if (!z && !bVarA.e()) {
            throw new AssertionError();
        }
        if (!z && collection.isEmpty()) {
            throw new AssertionError();
        }
        if (c0702m.Y().t0 && !c0702m.Y().K0()) {
            c0702m.Y().e.a((Diagnostic) supplier.get());
            throw null;
        }
        C0729q2 c0729q2B = b(bVarA, consumer, consumer2, C3846e.a(c0767v2), c0702m);
        this.e.a(c0729q2B.getType(), collection);
        return c0729q2B;
    }

    private o5 a(C c, C0729q2 c0729q2, C0702m c0702m, Consumer consumer, Supplier supplier) {
        C3846e c3846eA;
        if (!g && e()) {
            throw new AssertionError();
        }
        A1 a1Y = c0702m.Y();
        if (c0702m.f().h()) {
            c3846eA = a(c0729q2, c0702m.h().p(), a1Y, c0702m.C());
        } else {
            c3846eA = a(c0729q2, C1121Ka.a(), a1Y, AbstractC3135uW.b());
        }
        L.b bVarA = c.a(this.b);
        C0729q2 c0729q3 = (C0729q2) ((P) new P(L.a(bVarA, c3846eA, (String) supplier.get(), c0702m), bVarA, c3846eA, c0702m.a()).a(consumer.andThen(new Consumer() { // from class: com.android.tools.r8.synthesis.D$$ExternalSyntheticLambda10
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((H) obj).c();
            }
        }))).a();
        if (!P.t) {
            com.android.tools.r8.graph.E e = c0729q3.e;
            if (!e.q() || e.V()) {
                throw new AssertionError();
            }
        }
        o5 o5Var = new o5(c0729q3, c0729q3.M1().iterator().next());
        I i = new I(bVarA, c3846eA, o5Var);
        this.d.a.put(i.e().getType(), i);
        return o5Var;
    }

    public final C3842a a(AbstractC0681j0 abstractC0681j0) {
        return a(u5.a(abstractC0681j0), this.d, this.e, this.c, this.a, this.f);
    }

    public final C3842a a(u5 u5Var) {
        return a(u5Var, this.d, this.e, this.c, this.a, this.f);
    }

    public final C3842a a(AbstractC0681j0 abstractC0681j0, DH dh) {
        if (!g) {
            this.d.a(dh);
        }
        return a(u5.a(abstractC0681j0), this.d, this.e, this.c.a(dh), this.a, this.f);
    }

    private static C3842a a(u5 u5Var, z zVar, InterfaceC3862u interfaceC3862u, C3844c c3844c, int i, y yVar) {
        AbstractC1639am abstractC1639amA;
        AbstractC0681j0 abstractC0681j0D = u5Var.d();
        Set setC = u5Var.c();
        c3844c.getClass();
        C3843b c3843b = new C3843b(c3844c);
        if (zVar.a.isEmpty()) {
            abstractC1639amA = AbstractC1639am.i();
        } else {
            AbstractC0674i0 abstractC0674i0C = abstractC0681j0D.c();
            C1507Xl c1507XlG = AbstractC1639am.g();
            for (AbstractC3855m abstractC3855m : zVar.a.values()) {
                if (!setC.contains(abstractC3855m.e().getType())) {
                    if (abstractC3855m.h()) {
                        c1507XlG.a(abstractC3855m.e().getType());
                        if (abstractC3855m.f().g()) {
                            abstractC0674i0C.b(abstractC3855m.c().a());
                        } else {
                            abstractC0674i0C.a(abstractC3855m.c().a());
                        }
                    } else {
                        abstractC0674i0C.getClass();
                        if (abstractC0674i0C instanceof N2.a) {
                            if (!g && !abstractC3855m.g()) {
                                throw new AssertionError();
                            }
                            abstractC0674i0C.a().a((C0754u0) ((C3852j) abstractC3855m.b()).d);
                        }
                    }
                    c3843b.a(abstractC3855m);
                }
            }
            c3843b.a(interfaceC3862u);
            abstractC1639amA = c1507XlG.a();
            abstractC0681j0D = abstractC0674i0C.b();
        }
        return new C3842a(i, abstractC0681j0D, c3843b.a().a(u5Var), abstractC1639amA, yVar);
    }

    public final void a(C1549Za c1549Za, C0729q2 c0729q2, C0702m c0702m) {
        if (c0702m.Y().t0 && (c0702m.Y().f instanceof ClassFileConsumer)) {
            Iterator it = this.c.b(c0729q2.getType()).iterator();
            if (it.hasNext()) {
                L.b bVarC = ((U) it.next()).c();
                if (!g && it.hasNext()) {
                    throw new AssertionError();
                }
                L l = c0702m.D().b;
                c1549Za.a((T3) new E(bVarC, l.a(), l));
            }
        }
    }

    final r a(C0702m c0702m, y3 y3Var) {
        if (!g && d()) {
            throw new AssertionError();
        }
        c0702m.Y();
        return new C3861t(this, this.c).a(c0702m, y3Var);
    }

    public final void a(final SyntheticInfoConsumer syntheticInfoConsumer) {
        if (!g && !e()) {
            throw new AssertionError();
        }
        final IdentityHashMap identityHashMap = new IdentityHashMap();
        this.c.a(new Consumer() { // from class: com.android.tools.r8.synthesis.D$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                D.a(identityHashMap, syntheticInfoConsumer, (U) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Map map, SyntheticInfoConsumer syntheticInfoConsumer, U u) {
        C0767v2 c0767v2A = u.a();
        C0767v2 c0767v2C = u.b().c();
        C0767v2 c0767v2 = (C0767v2) map.put(c0767v2A, c0767v2C);
        if (!g && c0767v2 != null && c0767v2 != c0767v2C) {
            throw new AssertionError();
        }
        if (c0767v2 == null) {
            syntheticInfoConsumer.acceptSyntheticInfo(new B(c0767v2A, c0767v2C));
        }
    }
}
