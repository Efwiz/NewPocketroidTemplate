package com.android.tools.r8.synthesis;

import com.android.tools.r8.ClassFileConsumer;
import com.android.tools.r8.graph.AbstractC0674i0;
import com.android.tools.r8.graph.AbstractC0681j0;
import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0633d0;
import com.android.tools.r8.graph.C0658g0;
import com.android.tools.r8.graph.C0664h;
import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.C0792w4;
import com.android.tools.r8.graph.H5;
import com.android.tools.r8.graph.U0;
import com.android.tools.r8.graph.Y0;
import com.android.tools.r8.graph.m5;
import com.android.tools.r8.graph.n5;
import com.android.tools.r8.graph.u5;
import com.android.tools.r8.internal.AbstractC0984Fi;
import com.android.tools.r8.internal.AbstractC1048Hm;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.AbstractC2178hm;
import com.android.tools.r8.internal.AbstractC3154uk;
import com.android.tools.r8.internal.AbstractC3424yG;
import com.android.tools.r8.internal.C1121Ka;
import com.android.tools.r8.internal.C1946em;
import com.android.tools.r8.internal.C2145hI;
import com.android.tools.r8.internal.CM;
import com.android.tools.r8.internal.DH;
import com.android.tools.r8.internal.OU;
import com.android.tools.r8.shaking.AbstractC3735i1;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.shaking.K0;
import com.android.tools.r8.shaking.L1;
import com.android.tools.r8.shaking.N1;
import com.android.tools.r8.shaking.k4$$ExternalSyntheticLambda6;
import com.android.tools.r8.utils.A1;
import com.android.tools.r8.utils.AbstractC3896g;
import com.android.tools.r8.utils.AbstractC3938n2;
import com.android.tools.r8.utils.C3970u0;
import com.android.tools.r8.utils.I1;
import com.android.tools.r8.utils.InterfaceC3877c1;
import com.android.tools.r8.utils.InterfaceC3949p3;
import com.android.tools.r8.utils.T2;
import com.android.tools.r8.utils.V1;
import com.android.tools.r8.utils.y3;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.synthesis.t, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3861t {
    static final /* synthetic */ boolean c = true;
    private final D a;
    private final C3844c b;

    C3861t(D d, C3844c c3844c) {
        this.a = d;
        this.b = c3844c;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(Set set, List list, Q q) {
        set.add((C0729q2) q.d);
        list.add((C0729q2) q.d);
    }

    public static void b(C0702m c0702m, y3 y3Var, ExecutorService executorService) {
        if (!c && ((C0680j) c0702m.f()).i()) {
            throw new AssertionError();
        }
        c0702m.Y().n1.a(c0702m);
        r rVarA = c0702m.D().a(c0702m, y3Var);
        c0702m.d(((C0680j) c0702m.f()).a(rVarA.a));
        c0702m.d(((C0680j) c0702m.f()).a(rVarA.d));
        C3860s c3860s = rVarA.b;
        if (c3860s != null) {
            c0702m.b((DH) c3860s);
        }
        c0702m.a(rVarA.c, executorService);
    }

    public static void c(C0702m c0702m, y3 y3Var, ExecutorService executorService) {
        c0702m.Y().n1.a(c0702m);
        r rVarA = c0702m.D().a(c0702m, y3Var);
        c0702m.d(((C3733i) c0702m.f()).a(rVarA.d));
        C3860s c3860s = rVarA.b;
        if (c3860s != null) {
            c0702m.a(rVarA.a.a.b(), c3860s);
        } else if (!c && rVarA.a.a != ((C3733i) c0702m.f()).b()) {
            throw new AssertionError();
        }
        c0702m.d(((C3733i) c0702m.f()).b(rVarA.a));
        c0702m.a(rVarA.c, executorService);
    }

    public static void a(C0702m c0702m, y3 y3Var, ExecutorService executorService) {
        boolean z = c;
        if (!z && c0702m.f().h()) {
            throw new AssertionError();
        }
        if (!z && c0702m.f().i()) {
            throw new AssertionError();
        }
        c0702m.Y().n1.a(c0702m);
        r rVarA = c0702m.D().a(c0702m, y3Var);
        c0702m.d(new C0664h(rVarA.a, rVarA.d));
        if (rVarA.b != null) {
            c0702m.d(c0702m.f().a(c0702m.f().f().a(c0702m.D(), rVarA.b)));
            c0702m.a((AbstractC3424yG) rVarA.b);
        }
        c0702m.a(rVarA.c, executorService);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Map b(C0702m c0702m, Map map, C3858p c3858p, y3 y3Var) {
        C1121Ka c1121KaA;
        AbstractC2178hm abstractC2178hmA = this.b.a();
        boolean z = c0702m.Y().t0;
        IdentityHashMap identityHashMapA = a(c0702m, abstractC2178hmA);
        if (c0702m.f().h()) {
            c1121KaA = c0702m.f().n().p();
        } else {
            c1121KaA = C1121Ka.a();
        }
        C1121Ka c1121Ka = c1121KaA;
        y3Var.a("Potential equivalences");
        Collection collectionA = a(identityHashMapA, z, c0702m.Y(), c0702m.E());
        y3Var.c();
        return a(collectionA, map, c0702m, z, c1121Ka, c3858p, y3Var);
    }

    final r a(final C0702m c0702m, final y3 y3Var) {
        AbstractC1048Hm abstractC1048HmB;
        boolean z = c;
        if (!z) {
            a(c0702m);
        }
        if (!z) {
            a();
        }
        final C3858p c3858p = new C3858p();
        final C1946em c1946emE = AbstractC2178hm.e();
        final C1946em c1946emE2 = AbstractC2178hm.e();
        Set setC = OU.c();
        final HashMap map = new HashMap();
        AbstractC0681j0 abstractC0681j0A = a(y3Var, c0702m, (Map) y3Var.a("Method equivalence", new InterfaceC3949p3() { // from class: com.android.tools.r8.synthesis.t$$ExternalSyntheticLambda29
            @Override // com.android.tools.r8.utils.InterfaceC3949p3
            public final Object get() {
                return this.f$0.a(c0702m, map, c3858p, y3Var);
            }
        }), (Map) y3Var.a("Class equivalence", new InterfaceC3949p3() { // from class: com.android.tools.r8.synthesis.t$$ExternalSyntheticLambda30
            @Override // com.android.tools.r8.utils.InterfaceC3949p3
            public final Object get() {
                return this.f$0.b(c0702m, map, c3858p, y3Var);
            }
        }), c3858p, new BiConsumer() { // from class: com.android.tools.r8.synthesis.t$$ExternalSyntheticLambda31
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C3861t.a(c1946emE2, (C0729q2) obj, (S) obj2);
            }
        }, new BiConsumer() { // from class: com.android.tools.r8.synthesis.t$$ExternalSyntheticLambda32
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C3861t.a(c1946emE, (C0729q2) obj, (J) obj2);
            }
        }, setC);
        final AbstractC2178hm abstractC2178hmA = c1946emE.a();
        final AbstractC2178hm abstractC2178hmA2 = c1946emE2.a();
        final Set setC2 = OU.c();
        this.b.a(new Consumer() { // from class: com.android.tools.r8.synthesis.t$$ExternalSyntheticLambda33
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C3861t.a(abstractC2178hmA, abstractC2178hmA2, setC2, (U) obj);
            }
        });
        final C3860s c3860s = (c3858p.c.isEmpty() && c3858p.a.isEmpty() && c3858p.b.isEmpty()) ? null : new C3860s(c0702m, c3858p.a, c3858p.b, c3858p.c);
        if (c3860s != null) {
            abstractC1048HmB = T2.b(new InterfaceC3877c1() { // from class: com.android.tools.r8.synthesis.t$$ExternalSyntheticLambda34
                @Override // com.android.tools.r8.utils.InterfaceC3877c1
                public final void forEach(Consumer consumer) {
                    this.f$0.a(c3860s, consumer);
                }
            });
        } else {
            abstractC1048HmB = this.b.e;
        }
        AbstractC1048Hm abstractC1048Hm = abstractC1048HmB;
        final L1 l1B = c0702m.f().f().b();
        setC.forEach(new Consumer() { // from class: com.android.tools.r8.synthesis.t$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                l1B.c((C0767v2) obj);
            }
        });
        return new r(new C3842a(2, abstractC0681j0A, new C3844c(this.a.b(), abstractC2178hmA, abstractC2178hmA2, this.b.b(), abstractC1048Hm), AbstractC1639am.i(), this.a.a()), c3860s, u5.a().a(abstractC0681j0A).b(setC2).a(), l1B.a());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(Set set, C0767v2 c0767v2, List list) {
        boolean z = c;
        if (!z && !set.add(c0767v2)) {
            throw new AssertionError();
        }
        if (!z && list.size() != 1) {
            throw new AssertionError();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(AbstractC0681j0 abstractC0681j0, C0759u2 c0759u2, BiConsumer biConsumer, C0767v2 c0767v2, C3859q c3859q) {
        C0729q2 c0729q2D = abstractC0681j0.d(c0767v2);
        if (c0729q2D.m1() == null) {
            c0729q2D.h = c0759u2;
        }
        I i = (I) c3859q.b;
        boolean z = c;
        if (!z && c0729q2D.Y().l() != 1) {
            throw new AssertionError();
        }
        if (!z) {
            C0792w4 c0792w4Y = c0729q2D.Y();
            c0792w4Y.getClass();
            if (!c0792w4Y.c(CM.b())) {
                throw new AssertionError();
            }
        }
        biConsumer.accept(c0729q2D, new J(i.f(), i.d(), c0729q2D.Y().a(CM.b()).getReference()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C2145hI b(String str) {
        return new C2145hI();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(C0702m c0702m, N1 n1, Set set, final Set set2, final List list, C0767v2 c0767v2, C3859q c3859q) {
        Q q = (Q) c3859q.b;
        q.d().a(c0702m, c0767v2);
        a(c0702m, (C0729q2) q.d, q.f());
        if (c3859q.a(n1)) {
            set.add(c0767v2);
        }
        c3859q.b(new Consumer() { // from class: com.android.tools.r8.synthesis.t$$ExternalSyntheticLambda27
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C3861t.a(set2, list, (Q) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Map a(C0702m c0702m, Map map, C3858p c3858p, y3 y3Var) {
        C1121Ka c1121KaA;
        AbstractC2178hm abstractC2178hmC = this.b.c();
        boolean z = c0702m.Y().t0;
        IdentityHashMap identityHashMapA = a(c0702m, abstractC2178hmC);
        if (c0702m.f().h()) {
            c1121KaA = c0702m.f().n().p();
        } else {
            c1121KaA = C1121Ka.a();
        }
        C1121Ka c1121Ka = c1121KaA;
        y3Var.a("Potential equivalences");
        Collection collectionA = a(identityHashMapA, z, c0702m.Y(), c0702m.E());
        y3Var.c();
        return a(collectionA, map, c0702m, z, c1121Ka, c3858p, y3Var);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C1946em c1946em, C0729q2 c0729q2, S s) {
        c1946em.a(c0729q2.getType(), AbstractC1639am.c(s));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C1946em c1946em, C0729q2 c0729q2, J j) {
        c1946em.a(c0729q2.getType(), AbstractC1639am.c(j));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(AbstractC2178hm abstractC2178hm, AbstractC2178hm abstractC2178hm2, Set set, U u) {
        C0767v2 c0767v2A = u.a();
        if (abstractC2178hm.containsKey(c0767v2A) || abstractC2178hm2.containsKey(c0767v2A)) {
            return;
        }
        set.add(c0767v2A);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(final C3860s c3860s, final Consumer consumer) {
        this.b.b(new Consumer() { // from class: com.android.tools.r8.synthesis.t$$ExternalSyntheticLambda20
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C3861t.a(consumer, c3860s, (C0767v2) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(Consumer consumer, C3860s c3860s, C0767v2 c0767v2) {
        c3860s.getClass();
        consumer.accept(c3860s.a(AbstractC1476Wj.e(), c0767v2));
    }

    private void a(final C0702m c0702m) {
        this.b.a(new Consumer() { // from class: com.android.tools.r8.synthesis.t$$ExternalSyntheticLambda19
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(c0702m, (U) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C0702m c0702m, U u) {
        if (u.c().e()) {
            return;
        }
        String strB = L.b(u.a(), u.c());
        boolean z = c;
        if (!z && strB.contains(L.a(L.a.a))) {
            throw new AssertionError();
        }
        C0767v2 c0767v2E = c0702m.a().e(C3970u0.l(strB));
        if (!z && this.b.a(c0767v2E) && !u.b().d() && !this.a.g(c0767v2E)) {
            throw new AssertionError();
        }
    }

    private void a() {
        final Set setC = OU.c();
        this.b.a().forEach(new BiConsumer() { // from class: com.android.tools.r8.synthesis.t$$ExternalSyntheticLambda15
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C3861t.a(setC, (C0767v2) obj, (List) obj2);
            }
        });
        this.b.c().forEach(new BiConsumer() { // from class: com.android.tools.r8.synthesis.t$$ExternalSyntheticLambda16
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C3861t.b(setC, (C0767v2) obj, (List) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Set set, C0767v2 c0767v2, List list) {
        boolean z = c;
        if (!z && !set.add(c0767v2)) {
            throw new AssertionError();
        }
        if (!z && list.size() != 1) {
            throw new AssertionError();
        }
    }

    private static AbstractC0681j0 a(y3 y3Var, final C0702m c0702m, Map map, Map map2, final C3858p c3858p, final BiConsumer biConsumer, final BiConsumer biConsumer2, final Set set) {
        final C0759u2 c0759u2C;
        final AbstractC0681j0 abstractC0681j0B = c0702m.f().b();
        final N1 n1F = c0702m.f().f();
        final Set setC = OU.c();
        final C3857o c3857o = new C3857o(c0702m, c3858p);
        final ArrayList arrayList = new ArrayList();
        map.forEach(new BiConsumer() { // from class: com.android.tools.r8.synthesis.t$$ExternalSyntheticLambda2
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C3861t.a(c0702m, n1F, set, setC, arrayList, (C0767v2) obj, (C3859q) obj2);
            }
        });
        map2.forEach(new BiConsumer() { // from class: com.android.tools.r8.synthesis.t$$ExternalSyntheticLambda3
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C3861t.b(c0702m, n1F, set, setC, arrayList, (C0767v2) obj, (C3859q) obj2);
            }
        });
        if (c3858p.c.isEmpty()) {
            boolean z = C3858p.d;
            if (!z && !c3858p.a.isEmpty()) {
                throw new AssertionError();
            }
            if (!z && !c3858p.b.isEmpty()) {
                throw new AssertionError();
            }
            boolean z2 = c;
            if (!z2 && !arrayList.isEmpty()) {
                throw new AssertionError();
            }
            if (!z2 && !setC.isEmpty()) {
                throw new AssertionError();
            }
        } else {
            if (!setC.isEmpty()) {
                ArrayList arrayList2 = new ArrayList();
                for (C0729q2 c0729q2 : abstractC0681j0B.d()) {
                    if (!setC.contains(c0729q2)) {
                        arrayList2.add(c0729q2);
                    }
                }
                if (!c && arrayList2.size() >= abstractC0681j0B.d().size()) {
                    throw new AssertionError();
                }
                abstractC0681j0B = abstractC0681j0B.c().a(arrayList2).b();
            }
            boolean z3 = c;
            if (!z3) {
                a(abstractC0681j0B, map2);
            }
            if (!z3) {
                a(abstractC0681j0B, map);
            }
            y3Var.a("Tree fixing");
            AbstractC0674i0 abstractC0674i0C = abstractC0681j0B.c();
            c3857o.a((Collection) arrayList);
            abstractC0674i0C.a(c3857o.a(abstractC0681j0B.d()));
            abstractC0681j0B = abstractC0674i0C.b();
            y3Var.c();
        }
        if (c0702m.q()) {
            c0759u2C = c0702m.a().c("R8$$SyntheticClass");
        } else {
            c0759u2C = c0702m.a().c("D8$$SyntheticClass");
        }
        y3Var.a("Add final synthetics");
        map2.forEach(new BiConsumer() { // from class: com.android.tools.r8.synthesis.t$$ExternalSyntheticLambda4
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C3861t.a(abstractC0681j0B, c0759u2C, biConsumer, (C0767v2) obj, (C3859q) obj2);
            }
        });
        map.forEach(new BiConsumer() { // from class: com.android.tools.r8.synthesis.t$$ExternalSyntheticLambda5
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C3861t.b(abstractC0681j0B, c0759u2C, biConsumer2, (C0767v2) obj, (C3859q) obj2);
            }
        });
        y3Var.c();
        y3Var.a("Finish lens");
        AbstractC0984Fi.a(map2.values(), map.values()).forEach(new Consumer() { // from class: com.android.tools.r8.synthesis.t$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C3861t.a(c3857o, c3858p, (C3859q) obj);
            }
        });
        y3Var.c();
        for (C0767v2 c0767v2 : map.keySet()) {
            if (!c && abstractC0681j0B.c(c0767v2) == null) {
                throw new AssertionError();
            }
        }
        for (C0767v2 c0767v3 : map2.keySet()) {
            if (!c && abstractC0681j0B.c(c0767v3) == null) {
                throw new AssertionError();
            }
        }
        return abstractC0681j0B;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Set set, List list, I i) {
        set.add(i.a());
        list.add(i.a());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(AbstractC0681j0 abstractC0681j0, C0759u2 c0759u2, BiConsumer biConsumer, C0767v2 c0767v2, C3859q c3859q) {
        C0729q2 c0729q2D = abstractC0681j0.d(c0767v2);
        if (!c && c0729q2D == null) {
            throw new AssertionError("Expected definition for " + c0767v2.L0());
        }
        if (c0729q2D.m1() == null) {
            c0729q2D.h = c0759u2;
        }
        Q q = (Q) c3859q.a();
        biConsumer.accept(c0729q2D, new S(q.f(), q.d(), c0729q2D.d));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(H5 h5, C3858p c3858p, n5 n5Var) {
        n5Var.getClass();
        if (n5Var instanceof m5) {
            Y0 reference = n5Var.Q().getReference();
            c3858p.a.b(h5.a(reference), reference);
        } else {
            C0684j2 reference2 = n5Var.g0().getReference();
            c3858p.b.b(h5.a(reference2), reference2);
        }
    }

    private static void a(final AbstractC0681j0 abstractC0681j0, Map map) {
        Iterator it = map.values().iterator();
        while (it.hasNext()) {
            ((C3859q) it.next()).b(new Consumer() { // from class: com.android.tools.r8.synthesis.t$$ExternalSyntheticLambda26
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    C3861t.a(abstractC0681j0, (AbstractC3855m) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(AbstractC0681j0 abstractC0681j0, AbstractC3855m abstractC3855m) {
        if (!c && abstractC0681j0.c(abstractC3855m.e().getType()) != null) {
            throw new AssertionError();
        }
    }

    private static void a(C0702m c0702m, C0729q2 c0729q2, L.b bVar) {
        C0658g0 c0658g0A;
        A1 a1Y = c0702m.Y();
        if (a1Y.t0 && a1Y.Y0()) {
            if (!F.d && (c0702m.Y().f instanceof ClassFileConsumer)) {
                throw new AssertionError();
            }
            C0658g0 c0658g0S0 = c0729q2.s0();
            C0722p1 c0722p1 = c0702m.Y().a;
            com.android.tools.r8.androidapi.b bVarD = c0702m.d();
            Iterable iterableH0 = c0729q2.H0();
            bVarD.getClass();
            C0633d0 c0633d0A = C0633d0.a(bVar, c0722p1, AbstractC3896g.a(c0729q2, bVarD.a(iterableH0, com.android.tools.r8.androidapi.h.f())));
            int i = 0;
            for (C0633d0 c0633d0 : c0658g0S0.c) {
                if (c0633d0.b.a == c0633d0A.b.a) {
                    C0633d0[] c0633d0Arr = (C0633d0[]) c0658g0S0.c.clone();
                    c0633d0Arr[i] = c0633d0A;
                    c0658g0A = C0658g0.a(c0633d0Arr);
                    c0729q2.a(c0658g0A);
                }
                i++;
            }
            C0633d0[] c0633d0Arr2 = c0658g0S0.c;
            C0633d0[] c0633d0Arr3 = new C0633d0[c0633d0Arr2.length + 1];
            System.arraycopy(c0633d0Arr2, 0, c0633d0Arr3, 0, c0633d0Arr2.length);
            c0633d0Arr3[c0658g0S0.c.length] = c0633d0A;
            c0658g0A = C0658g0.a(c0633d0Arr3);
            c0729q2.a(c0658g0A);
        }
    }

    private IdentityHashMap a(Collection collection, final Map map, final C0702m c0702m, final boolean z, final C1121Ka c1121Ka, final C3858p c3858p, y3 y3Var) {
        final HashMap map2 = new HashMap();
        final IdentityHashMap identityHashMap = new IdentityHashMap();
        y3Var.a("Groups");
        collection.forEach(new Consumer() { // from class: com.android.tools.r8.synthesis.t$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C3861t.a(c0702m, z, c1121Ka, identityHashMap, map2, (List) obj);
            }
        });
        y3Var.c();
        y3Var.a("External creation");
        map2.forEach(new BiConsumer() { // from class: com.android.tools.r8.synthesis.t$$ExternalSyntheticLambda8
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(c0702m, z, map, identityHashMap, (String) obj, (List) obj2);
            }
        });
        y3Var.c();
        identityHashMap.forEach(new BiConsumer() { // from class: com.android.tools.r8.synthesis.t$$ExternalSyntheticLambda9
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C3861t.a(c3858p, (C0767v2) obj, (C3859q) obj2);
            }
        });
        return identityHashMap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(C0702m c0702m, boolean z, C1121Ka c1121Ka, Map map, Map map2, List list) {
        boolean zA;
        for (C3859q c3859q : a(c0702m, list, z, c1121Ka)) {
            if (c3859q.c.d()) {
                zA = true;
            } else {
                zA = c3859q.c.a() ? false : a(c0702m, c3859q.b);
            }
            if (zA) {
                C3859q c3859q2 = (C3859q) map.put(c3859q.b.e().getType(), c3859q);
                if (!c && c3859q2 != null) {
                    throw new AssertionError();
                }
            } else {
                ((List) map2.computeIfAbsent(c3859q.b.a(c0702m), new Function() { // from class: com.android.tools.r8.synthesis.t$$ExternalSyntheticLambda25
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return C3861t.a((String) obj);
                    }
                })).add(c3859q);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ List a(String str) {
        return new ArrayList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(final C0702m c0702m, boolean z, Map map, final Map map2, String str, List list) {
        C0767v2 c0767v2A;
        Comparator comparator = new Comparator() { // from class: com.android.tools.r8.synthesis.t$$ExternalSyntheticLambda17
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return this.f$0.a((C3859q) obj, (C3859q) obj2);
            }
        };
        V1.a(list, comparator);
        for (int i = 0; i < list.size(); i++) {
            C3859q c3859q = (C3859q) list.get(i);
            boolean z2 = c;
            if (!z2 && !c3859q.b.a(c0702m).equals(str)) {
                throw new AssertionError();
            }
            if (!z2 && i != 0) {
                C3859q c3859q2 = (C3859q) list.get(i - 1);
                int iCompare = comparator.compare(c3859q2, c3859q);
                if (!z2 && iCompare >= 0) {
                    throw new AssertionError();
                }
                int iCompare2 = comparator.compare(c3859q, c3859q2);
                if (!z2 && iCompare2 <= 0) {
                    throw new AssertionError();
                }
            }
            L.b bVarF = c3859q.b.f();
            if (z && this.a.d(c3859q.b.e().a0())) {
                c0767v2A = c3859q.b.e().getType();
            } else {
                c0767v2A = a(bVarF, str, map, c0702m, new Predicate() { // from class: com.android.tools.r8.synthesis.t$$ExternalSyntheticLambda18
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return C3861t.a(map2, c0702m, (C0767v2) obj);
                    }
                });
            }
            map2.put(c0767v2A, c3859q);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(Map map, C0702m c0702m, C0767v2 c0767v2) {
        return map.containsKey(c0767v2) || c0702m.M().a(c0767v2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(final C3858p c3858p, final C0767v2 c0767v2, C3859q c3859q) {
        c3859q.a(new Consumer() { // from class: com.android.tools.r8.synthesis.t$$ExternalSyntheticLambda21
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C3861t.a(c3858p, c0767v2, (AbstractC3855m) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(C3858p c3858p, C0767v2 c0767v2, AbstractC3855m abstractC3855m) {
        C0767v2 c0767v3 = (C0767v2) c3858p.c.put(abstractC3855m.e().getType(), c0767v2);
        if (!C3858p.d && c0767v3 != null && c0767v3 != c0767v2) {
            throw new AssertionError();
        }
    }

    private static List a(C0702m c0702m, List list, boolean z, final C1121Ka c1121Ka) {
        ArrayList arrayList;
        if (list.size() == 1) {
            return AbstractC1639am.c(new C3859q((AbstractC3855m) list.get(0), AbstractC1639am.i(), AbstractC3938n2.c));
        }
        if (!c && list.isEmpty()) {
            throw new AssertionError();
        }
        final AbstractC1476Wj abstractC1476WjE = c0702m.E();
        final boolean z2 = z || c0702m.Y().F0().e();
        ArrayList arrayListB = V1.b(list, new Comparator() { // from class: com.android.tools.r8.synthesis.t$$ExternalSyntheticLambda10
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return C3861t.a(z2, abstractC1476WjE, c1121Ka, (AbstractC3855m) obj, (AbstractC3855m) obj2);
            }
        });
        ArrayList<List> arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        AbstractC3855m abstractC3855m = (AbstractC3855m) arrayListB.get(0);
        arrayList3.add(abstractC3855m);
        for (int i = 1; i < arrayListB.size(); i++) {
            AbstractC3855m abstractC3855m2 = (AbstractC3855m) arrayListB.get(i);
            if (abstractC3855m.a(abstractC3855m2, z2, abstractC1476WjE, c1121Ka) != 0) {
                arrayList2.add(arrayList3);
                arrayList3 = new ArrayList();
                abstractC3855m = abstractC3855m2;
            }
            arrayList3.add(abstractC3855m2);
        }
        arrayList2.add(arrayList3);
        final ArrayList arrayList4 = new ArrayList();
        for (List list2 : arrayList2) {
            if (!c && list2.isEmpty()) {
                throw new AssertionError();
            }
            if (list2.size() == 1) {
                arrayList4.add(new C3859q((AbstractC3855m) list2.get(0), AbstractC1639am.i(), AbstractC3938n2.c));
            } else {
                if (c0702m.q()) {
                    Iterator it = list2.iterator();
                    arrayList = null;
                    while (it.hasNext()) {
                        AbstractC3855m abstractC3855m3 = (AbstractC3855m) it.next();
                        if (a(c0702m, abstractC3855m3)) {
                            if (arrayList == null) {
                                arrayList = new ArrayList();
                            }
                            arrayList.add(abstractC3855m3);
                            it.remove();
                        }
                    }
                } else {
                    arrayList = null;
                }
                if (arrayList != null) {
                    boolean z3 = c;
                    if (!z3 && !c0702m.q()) {
                        throw new AssertionError();
                    }
                    Consumer consumer = new Consumer() { // from class: com.android.tools.r8.synthesis.t$$ExternalSyntheticLambda12
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            C3861t.a(arrayList4, (AbstractC3855m) obj);
                        }
                    };
                    if (!z3 && arrayList.isEmpty()) {
                        throw new AssertionError();
                    }
                    AbstractC3855m abstractC3855m4 = (AbstractC3855m) arrayList.get(0);
                    for (int i2 = 1; i2 < arrayList.size(); i2++) {
                        AbstractC3855m abstractC3855m5 = (AbstractC3855m) arrayList.get(i2);
                        if (abstractC3855m5.j().d().a(abstractC3855m4.j().d()) < 0) {
                            consumer.accept(abstractC3855m4);
                            abstractC3855m4 = abstractC3855m5;
                        } else {
                            consumer.accept(abstractC3855m5);
                        }
                    }
                    arrayList4.add(new C3859q(abstractC3855m4, Collections.unmodifiableList(list2), AbstractC3938n2.a));
                } else {
                    final ArrayList arrayList5 = new ArrayList(list2.size() - 1);
                    Consumer consumer2 = new Consumer() { // from class: com.android.tools.r8.synthesis.t$$ExternalSyntheticLambda13
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            arrayList5.add((AbstractC3855m) obj);
                        }
                    };
                    if (!c && list2.isEmpty()) {
                        throw new AssertionError();
                    }
                    AbstractC3855m abstractC3855m6 = (AbstractC3855m) list2.get(0);
                    for (int i3 = 1; i3 < list2.size(); i3++) {
                        AbstractC3855m abstractC3855m7 = (AbstractC3855m) list2.get(i3);
                        if (abstractC3855m7.j().d().a(abstractC3855m6.j().d()) < 0) {
                            consumer2.accept(abstractC3855m6);
                            abstractC3855m6 = abstractC3855m7;
                        } else {
                            consumer2.accept(abstractC3855m7);
                        }
                    }
                    arrayList4.add(new C3859q(abstractC3855m6, Collections.unmodifiableList(arrayList5), AbstractC3938n2.b));
                }
            }
        }
        return arrayList4;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int a(boolean z, AbstractC1476Wj abstractC1476Wj, C1121Ka c1121Ka, AbstractC3855m abstractC3855m, AbstractC3855m abstractC3855m2) {
        return abstractC3855m.a(abstractC3855m2, z, abstractC1476Wj, c1121Ka);
    }

    private static boolean a(C0702m c0702m, AbstractC3855m abstractC3855m) {
        if (!c0702m.q()) {
            return false;
        }
        if (!abstractC3855m.e().e0()) {
            return true;
        }
        C0729q2 c0729q2A0 = abstractC3855m.e().a0();
        if (!c0702m.D().a(c0729q2A0)) {
            return false;
        }
        AbstractC3735i1 abstractC3735i1V = c0702m.v();
        A1 a1Y = c0702m.Y();
        if (abstractC3735i1V.a(c0729q2A0).c((K0) a1Y)) {
            return true;
        }
        Iterator it = c0729q2A0.L1().iterator();
        while (it.hasNext()) {
            if (abstractC3735i1V.a((U0) it.next(), c0729q2A0).c((K0) a1Y)) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Code duplicated, block: B:35:0x0084  */
    private C0767v2 a(L.b bVar, String str, Map map, C0702m c0702m, Predicate predicate) {
        C0767v2 c0767v2E;
        C0722p1 c0722p1A = c0702m.a();
        if (bVar.d()) {
            if (L.V || bVar.d()) {
                return c0722p1A.e(L.a(bVar.d() ? "" : "$$ExternalSynthetic", bVar, str, ""));
            }
            throw new AssertionError();
        }
        C2145hI c2145hI = (C2145hI) map.computeIfAbsent(str, new Function() { // from class: com.android.tools.r8.synthesis.t$$ExternalSyntheticLambda14
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C3861t.b((String) obj);
            }
        });
        do {
            String string = Integer.toString(c2145hI.a());
            if (!L.V && bVar.d() != string.isEmpty()) {
                throw new AssertionError();
            }
            c0767v2E = c0722p1A.e(L.a(bVar.d() ? "" : "$$ExternalSynthetic", bVar, str, string));
            if (predicate.test(c0767v2E)) {
                c0767v2E = null;
            } else {
                AbstractC0727q0 abstractC0727q0D = c0702m.f().d(c0767v2E);
                if (abstractC0727q0D != null) {
                    if (!this.b.a(abstractC0727q0D.d)) {
                        c0767v2E = null;
                    }
                }
            }
        } while (c0767v2E == null);
        return c0767v2E;
    }

    private static Collection a(IdentityHashMap identityHashMap, boolean z, final A1 a1, final AbstractC1476Wj abstractC1476Wj) {
        final Set setKeySet;
        if (identityHashMap.isEmpty()) {
            return Collections.emptyList();
        }
        if (!a1.n1.L) {
            final ArrayList arrayList = new ArrayList(identityHashMap.size());
            identityHashMap.values().forEach(new Consumer() { // from class: com.android.tools.r8.synthesis.t$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    C3861t.a(arrayList, (AbstractC3855m) obj);
                }
            });
            return arrayList;
        }
        if (abstractC1476Wj.h()) {
            setKeySet = identityHashMap.keySet();
        } else {
            final Set setB = T2.b(identityHashMap.size() * 2);
            identityHashMap.keySet().forEach(new Consumer() { // from class: com.android.tools.r8.synthesis.t$$ExternalSyntheticLambda11
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    C3861t.a(setB, abstractC1476Wj, (C0767v2) obj);
                }
            });
            setKeySet = setB;
        }
        com.android.tools.r8.utils.structural.t tVar = new com.android.tools.r8.utils.structural.t() { // from class: com.android.tools.r8.synthesis.t$$ExternalSyntheticLambda22
            @Override // com.android.tools.r8.utils.structural.t
            public final C0767v2 a(C0767v2 c0767v2) {
                return C3861t.a(setKeySet, a1, c0767v2);
            }
        };
        HashMap map = new HashMap(identityHashMap.size());
        for (AbstractC3855m abstractC3855m : identityHashMap.values()) {
            ((List) map.computeIfAbsent(abstractC3855m.a(tVar, z), new Function() { // from class: com.android.tools.r8.synthesis.t$$ExternalSyntheticLambda28
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return C3861t.a((AbstractC3154uk) obj);
                }
            })).add(abstractC3855m);
        }
        return map.values();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Collection collection, AbstractC3855m abstractC3855m) {
        collection.add(AbstractC1639am.c(abstractC3855m));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Set set, AbstractC1476Wj abstractC1476Wj, C0767v2 c0767v2) {
        set.add(c0767v2);
        set.add(abstractC1476Wj.a(c0767v2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C0767v2 a(Set set, A1 a1, C0767v2 c0767v2) {
        return set.contains(c0767v2) ? a1.j0().D1 : c0767v2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ List a(AbstractC3154uk abstractC3154uk) {
        return new ArrayList();
    }

    private static IdentityHashMap a(C0702m c0702m, AbstractC2178hm abstractC2178hm) {
        IdentityHashMap identityHashMap = new IdentityHashMap(abstractC2178hm.size());
        for (U u : I1.a(abstractC2178hm.values())) {
            Objects.requireNonNull(c0702m);
            AbstractC3855m abstractC3855mA = u.a(new k4$$ExternalSyntheticLambda6(c0702m));
            if (abstractC3855mA == null) {
                if (!c) {
                    throw new AssertionError();
                }
            } else if (abstractC3855mA.i()) {
                identityHashMap.put(u.a(), abstractC3855mA);
            } else if (!c) {
                throw new AssertionError();
            }
        }
        return identityHashMap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(List list, AbstractC3855m abstractC3855m) {
        list.add(new C3859q(abstractC3855m, AbstractC1639am.i(), AbstractC3938n2.a));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int a(C3859q c3859q, C3859q c3859q2) {
        return c3859q.b.e().getType().compareTo(c3859q2.b.e().getType());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(C0702m c0702m, N1 n1, Set set, final Set set2, final List list, C0767v2 c0767v2, C3859q c3859q) {
        I i = (I) c3859q.b;
        i.d().a(c0702m, c0767v2);
        a(c0702m, i.a(), i.f());
        if (c3859q.a(n1)) {
            set.add(c0767v2);
        }
        c3859q.b(new Consumer() { // from class: com.android.tools.r8.synthesis.t$$ExternalSyntheticLambda23
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C3861t.a(set2, list, (I) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(final H5 h5, final C3858p c3858p, C3859q c3859q) {
        ((C0729q2) c3859q.b.e()).k(new Consumer() { // from class: com.android.tools.r8.synthesis.t$$ExternalSyntheticLambda24
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C3861t.a(h5, c3858p, (n5) obj);
            }
        });
    }
}
