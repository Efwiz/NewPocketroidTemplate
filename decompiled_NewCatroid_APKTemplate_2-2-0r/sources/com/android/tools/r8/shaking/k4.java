package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.C0800x2;
import com.android.tools.r8.graph.T0$$ExternalSyntheticLambda1;
import com.android.tools.r8.graph.X4;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.graph.u5;
import com.android.tools.r8.graph.v5;
import com.android.tools.r8.internal.CM;
import com.android.tools.r8.internal.OU;
import com.android.tools.r8.utils.C3892f0;
import com.android.tools.r8.utils.C3988x2;
import com.android.tools.r8.utils.InterfaceC3949p3;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class k4 {
    static final /* synthetic */ boolean h = true;
    private final C0702m a;
    private final l4 b;
    private final o4 c;
    private final Set d;
    private final Set e;
    private final Set f;
    private final Set g;

    public k4(C0702m c0702m) {
        this(c0702m, C3829y.a());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(com.android.tools.r8.utils.A1 a1, String str) {
        com.android.tools.r8.utils.R0.a(a1.e, a1.y1, str);
    }

    private void b(C0767v2 c0767v2, LinkedHashSet linkedHashSet) {
        if (a(c0767v2)) {
            linkedHashSet.add(c0767v2);
            return;
        }
        C0729q2 c0729q2B = this.a.b(c0767v2);
        boolean z = h;
        if (!z && c0729q2B == null) {
            throw new AssertionError();
        }
        if (!z && !c0729q2B.isInterface()) {
            throw new AssertionError();
        }
        for (C0767v2 c0767v3 : c0729q2B.g.a) {
            b(c0767v3, linkedHashSet);
        }
    }

    public k4(C0702m c0702m, l4 l4Var) {
        this.d = OU.c();
        this.e = OU.c();
        this.f = OU.c();
        this.g = OU.c();
        final com.android.tools.r8.utils.A1 a1Y = c0702m.Y();
        this.a = c0702m;
        this.b = l4Var;
        this.c = a1Y.N0() ? new o4(new Consumer() { // from class: com.android.tools.r8.shaking.k4$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                k4.a(a1Y, (String) obj);
            }
        }) : o4.e;
    }

    public final u5 a(ExecutorService executorService, com.android.tools.r8.utils.y3 y3Var) {
        return a(y3Var, Collections.emptySet(), executorService);
    }

    public final u5 a(com.android.tools.r8.utils.y3 y3Var, final Set set, final ExecutorService executorService) {
        return (u5) y3Var.a("Pruning application...", new InterfaceC3949p3() { // from class: com.android.tools.r8.shaking.k4$$ExternalSyntheticLambda7
            @Override // com.android.tools.r8.utils.InterfaceC3949p3
            public final Object get() {
                return this.f$0.a(executorService, set);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public u5 a(ExecutorService executorService, Set set) {
        com.android.tools.r8.graph.N2 n2B = ((C3733i) this.a.f()).b().b();
        com.android.tools.r8.graph.N2.a aVarC = n2B.c();
        Collection<C0729q2> collectionE = n2B.e();
        C3733i c3733i = (C3733i) this.a.f();
        com.android.tools.r8.utils.A1 a1Y = this.a.Y();
        ArrayList arrayList = new ArrayList();
        for (C0729q2 c0729q2 : collectionE) {
            if (a1Y.U0) {
                arrayList.add(c0729q2);
                a(c0729q2);
            } else if (c3733i.e(c0729q2)) {
                arrayList.add(c0729q2);
                if (!c3733i.z().c(c0729q2) && !a1Y.S0) {
                    if (c0729q2.e.h()) {
                        c0729q2.e.a();
                    }
                    c0729q2.e.Z();
                }
                LinkedHashSet linkedHashSet = new LinkedHashSet();
                Iterator<C0767v2> it = c0729q2.f1().iterator();
                while (it.hasNext()) {
                    b(it.next(), linkedHashSet);
                }
                if (!linkedHashSet.isEmpty()) {
                    a(c0729q2.f, linkedHashSet);
                }
                if (linkedHashSet.isEmpty()) {
                    c0729q2.g = C0800x2.s0();
                } else {
                    c0729q2.g = new C0800x2((C0767v2[]) linkedHashSet.toArray(C0767v2.g));
                }
                a(c0729q2);
            } else {
                this.f.add(c0729q2.d);
                if (c0729q2.m1() != null) {
                    this.a.a(c0729q2.m1().toString(), c0729q2.d);
                }
                this.c.a(c0729q2);
            }
        }
        this.c.a();
        com.android.tools.r8.graph.N2.a aVar = (com.android.tools.r8.graph.N2.a) aVarC.a((AbstractCollection) arrayList);
        if (!this.f.isEmpty() || this.a.Y().U0) {
            n2B = aVar.b();
        }
        this.a.a().c();
        com.android.tools.r8.ir.optimize.info.n.a(n2B.d(), executorService, new j4(this));
        if (!h) {
            this.a.a().k();
        }
        return u5.a().a(n2B).b(C3892f0.a(set, this.f)).c(this.d).d(this.e).a(this.g).a();
    }

    private void b(C0729q2 c0729q2) {
        for (com.android.tools.r8.graph.T0 t0 : c0729q2.W0()) {
            if (!h && t0.X0().i() && !((C3733i) this.a.f()).b((com.android.tools.r8.graph.O0) t0)) {
                throw new AssertionError("Expected field `" + t0.getReference().r0() + "` to be absent");
            }
        }
    }

    private void a(C0767v2 c0767v2, LinkedHashSet linkedHashSet) {
        AbstractC0727q0 abstractC0727q0C = this.a.c(c0767v2);
        if (abstractC0727q0C == null || abstractC0727q0C.f0()) {
            return;
        }
        Iterator<C0767v2> it = abstractC0727q0C.g.iterator();
        while (it.hasNext()) {
            if (linkedHashSet.remove(it.next()) && linkedHashSet.isEmpty()) {
                return;
            }
        }
        if (abstractC0727q0C.f != null) {
            if (!h && linkedHashSet.isEmpty()) {
                throw new AssertionError();
            }
            a(abstractC0727q0C.f, linkedHashSet);
        }
    }

    private void a(C0729q2 c0729q2) {
        List listC;
        List listC2;
        this.c.b(c0729q2);
        Iterable<com.android.tools.r8.graph.W0> iterableV0 = c0729q2.V0();
        if (iterableV0 instanceof List) {
            listC = (List) iterableV0;
        } else {
            listC = com.android.tools.r8.utils.I1.c(iterableV0);
        }
        com.android.tools.r8.graph.W0[] w0ArrA = a(c0729q2, Collections.unmodifiableList(listC));
        if (w0ArrA != null) {
            c0729q2.a(w0ArrA);
        }
        Iterable<com.android.tools.r8.graph.W0> iterableP1 = c0729q2.P1();
        if (iterableP1 instanceof List) {
            listC2 = (List) iterableP1;
        } else {
            listC2 = com.android.tools.r8.utils.I1.c(iterableP1);
        }
        com.android.tools.r8.graph.W0[] w0ArrA2 = a(c0729q2, Collections.unmodifiableList(listC2));
        if (w0ArrA2 != null) {
            c0729q2.b(w0ArrA2);
        }
        com.android.tools.r8.graph.T0[] t0ArrA = a(c0729q2.w1());
        if (t0ArrA != null) {
            c0729q2.a(t0ArrA);
        }
        com.android.tools.r8.graph.T0[] t0ArrA2 = a(c0729q2.N1());
        if (t0ArrA2 != null) {
            c0729q2.b(t0ArrA2);
        }
        c0729q2.j(new Predicate() { // from class: com.android.tools.r8.shaking.k4$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.a((com.android.tools.r8.graph.J3) obj);
            }
        });
        c0729q2.i(new Predicate() { // from class: com.android.tools.r8.shaking.k4$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.a((com.android.tools.r8.graph.O2) obj);
            }
        });
        Predicate predicate = new Predicate() { // from class: com.android.tools.r8.shaking.k4$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.a((C0767v2) obj);
            }
        };
        C0702m c0702m = this.a;
        Objects.requireNonNull(c0702m);
        a(c0729q2, predicate, (Function) new k4$$ExternalSyntheticLambda6(c0702m));
        if (t0ArrA != null && !c0729q2.k1().isEmpty()) {
            c0729q2.l(C3988x2.a(a(t0ArrA)));
        }
        this.c.b();
        if (h) {
            return;
        }
        b(c0729q2);
    }

    public static void a(C0729q2 c0729q2, Predicate predicate, Function function) {
        AbstractC0727q0 abstractC0727q0;
        if (c0729q2.D1() && predicate.test(c0729q2.d)) {
            if (c0729q2.G1()) {
                a((AbstractC0727q0) c0729q2, predicate, function);
                return;
            }
            if (!h && !c0729q2.H1()) {
                throw new AssertionError();
            }
            if (predicate.test(c0729q2.g1()) || (abstractC0727q0 = (AbstractC0727q0) function.apply(c0729q2.g1())) == null) {
                return;
            }
            c0729q2.P0();
            for (X4 x4 : abstractC0727q0.i1()) {
                if (x4.a() != c0729q2.d && predicate.test(x4.a())) {
                    AbstractC0727q0 abstractC0727q1 = (AbstractC0727q0) function.apply(x4.a());
                    if (abstractC0727q1 != null) {
                        abstractC0727q1.a(c0729q2.d);
                    }
                    c0729q2.i1().add(new X4(x4.a()));
                }
            }
        }
    }

    public static Predicate a(final com.android.tools.r8.graph.T0[] t0Arr) {
        if (t0Arr.length == 0) {
            return CM.a();
        }
        if (t0Arr.length < 10) {
            return new Predicate() { // from class: com.android.tools.r8.shaking.k4$$ExternalSyntheticLambda9
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return k4.a(t0Arr, (v5) obj);
                }
            };
        }
        final Set setC = OU.c();
        for (com.android.tools.r8.graph.T0 t0 : t0Arr) {
            setC.add(t0.getReference());
        }
        return new Predicate() { // from class: com.android.tools.r8.shaking.k4$$ExternalSyntheticLambda10
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return k4.a(setC, (v5) obj);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(com.android.tools.r8.graph.T0[] t0Arr, v5 v5Var) {
        return com.android.tools.r8.utils.B.a(t0Arr, new T0$$ExternalSyntheticLambda1(), v5Var.b());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(Set set, v5 v5Var) {
        return set.contains(v5Var.b());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(C0767v2 c0767v2) {
        return ((C3733i) this.a.f()).n(c0767v2);
    }

    private static void a(AbstractC0727q0 abstractC0727q0, final Predicate predicate, final Function function) {
        abstractC0727q0.i1().removeIf(new Predicate() { // from class: com.android.tools.r8.shaking.k4$$ExternalSyntheticLambda8
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return k4.a(function, predicate, (X4) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(Function function, Predicate predicate, X4 x4) {
        return (function.apply(x4.a()) == null || predicate.test(x4.a())) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(com.android.tools.r8.graph.O2 o2) {
        return ((o2.a() == null || a(o2.a())) && (o2.b() == null || ((C3733i) this.a.f()).k(o2.b()))) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(com.android.tools.r8.graph.J3 j3) {
        AbstractC0727q0 abstractC0727q0C;
        if (((C3733i) this.a.f()).q().a(j3.b()) || !a(j3.b())) {
            return true;
        }
        C0702m c0702m = this.a;
        C0767v2 c0767v2D = j3.d();
        if (c0767v2D == null && (abstractC0727q0C = c0702m.c(j3.b())) != null && abstractC0727q0C.b1() != null) {
            com.android.tools.r8.graph.O2 o2B1 = abstractC0727q0C.b1();
            if (o2B1.a() != null) {
                c0767v2D = o2B1.a();
            } else {
                C0684j2 c0684j2B = o2B1.b();
                c0767v2D = !((C3733i) c0702m.f()).k(c0684j2B) ? null : c0684j2B.e;
            }
        }
        return c0767v2D == null || ((C3733i) this.a.f()).q().a(c0767v2D) || !a(c0767v2D);
    }

    private com.android.tools.r8.graph.W0[] a(C0729q2 c0729q2, List list) {
        final C3733i c3733i = (C3733i) this.a.f();
        com.android.tools.r8.utils.A1 a1Y = this.a.Y();
        Predicate predicate = new Predicate() { // from class: com.android.tools.r8.shaking.k4$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return k4.a(c3733i, (com.android.tools.r8.graph.W0) obj);
            }
        };
        int i = 0;
        while (true) {
            if (i >= list.size()) {
                i = -1;
                break;
            }
            if (!predicate.test((com.android.tools.r8.graph.U0) list.get(i))) {
                break;
            }
            i++;
        }
        if (i == -1) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                o5 o5VarA = ((com.android.tools.r8.graph.W0) it.next()).a(c0729q2);
                if (o5VarA.e().q1()) {
                    com.android.tools.r8.graph.Z.a(this.a, o5VarA);
                }
            }
            return null;
        }
        ArrayList arrayList = new ArrayList(list.size());
        for (int i2 = 0; i2 < list.size(); i2++) {
            com.android.tools.r8.graph.W0 w0 = (com.android.tools.r8.graph.W0) list.get(i2);
            if (c3733i.k(w0.getReference())) {
                o5 o5VarA2 = w0.a(c0729q2);
                if (o5VarA2.e().q1()) {
                    com.android.tools.r8.graph.Z.a(this.a, o5VarA2);
                }
                arrayList.add(w0);
            } else if (a1Y.U0) {
                arrayList.add((!w0.X1() || w0.q1()) ? w0.b(this.a) : w0);
                this.g.add(w0.getReference());
            } else if (c3733i.l(w0.getReference())) {
                if (w0.X1() && !w0.q1()) {
                    arrayList.add(w0);
                } else {
                    new o5(c0729q2, w0).d(this.a);
                    arrayList.add(w0);
                }
            } else {
                this.c.b(w0);
                this.e.add(w0.getReference());
            }
        }
        if (arrayList.isEmpty()) {
            return com.android.tools.r8.graph.W0.s;
        }
        return (com.android.tools.r8.graph.W0[]) arrayList.toArray(com.android.tools.r8.graph.W0.s);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(C3733i c3733i, com.android.tools.r8.graph.W0 w0) {
        return c3733i.k(w0.getReference());
    }

    private com.android.tools.r8.graph.T0[] a(List list) {
        final C3733i c3733i = (C3733i) this.a.f();
        Predicate predicate = new Predicate() { // from class: com.android.tools.r8.shaking.k4$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.a(c3733i, (com.android.tools.r8.graph.T0) obj);
            }
        };
        int i = 0;
        while (true) {
            if (i >= list.size()) {
                i = -1;
                break;
            }
            if (!predicate.test((com.android.tools.r8.graph.U0) list.get(i))) {
                break;
            }
            i++;
        }
        if (i == -1) {
            return null;
        }
        com.android.tools.r8.graph.T0 t0 = (com.android.tools.r8.graph.T0) list.get(i);
        this.c.b(t0);
        this.d.add(t0.getReference());
        ArrayList arrayList = new ArrayList(list.size());
        for (int i2 = 0; i2 < i; i2++) {
            arrayList.add((com.android.tools.r8.graph.T0) list.get(i2));
        }
        while (true) {
            i++;
            if (i >= list.size()) {
                break;
            }
            com.android.tools.r8.graph.T0 t1 = (com.android.tools.r8.graph.T0) list.get(i);
            if (predicate.test(t1)) {
                arrayList.add(t1);
            } else {
                this.c.b(t1);
                this.d.add(t1.getReference());
            }
        }
        if (arrayList.isEmpty()) {
            return com.android.tools.r8.graph.T0.m;
        }
        return (com.android.tools.r8.graph.T0[]) arrayList.toArray(com.android.tools.r8.graph.T0.m);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean a(C3733i c3733i, com.android.tools.r8.graph.T0 t0) {
        return this.b.a(c3733i, t0);
    }
}
