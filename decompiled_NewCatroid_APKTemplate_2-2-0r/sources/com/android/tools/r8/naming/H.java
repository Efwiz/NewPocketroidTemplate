package com.android.tools.r8.naming;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.B5;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0721p0;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.W0;
import com.android.tools.r8.internal.AbstractC1048Hm;
import com.android.tools.r8.internal.AbstractC2251ih;
import com.android.tools.r8.internal.C2173hh;
import com.android.tools.r8.internal.JA;
import com.android.tools.r8.internal.NN$$ExternalSyntheticLambda0;
import com.android.tools.r8.internal.OU;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.utils.C3884d2;
import com.android.tools.r8.utils.y3;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class H {
    static final /* synthetic */ boolean h = true;
    private final C0702m a;
    private final B5 b;
    private final C3884d2 c;
    private final AbstractC2251ih d;
    private final U e;
    private final HashMap f = new HashMap();
    private final HashMap g = new HashMap();

    H(C0702m c0702m, U u, B5 b5) {
        this.a = c0702m;
        this.e = u;
        this.b = b5;
        c0702m.Y().D0().getClass();
        this.c = C3884d2.a();
        this.d = new E(this);
    }

    private Comparator a() {
        final HashMap map = this.f;
        Objects.requireNonNull(map);
        return Comparator.comparing(new Function() { // from class: com.android.tools.r8.naming.H$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return (F) map.get((C2173hh) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ F b(C2173hh c2173hh) {
        return new F(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean b(F f, C0759u2 c0759u2, C0684j2 c0684j2) {
        return f.a(c0759u2);
    }

    final void a(y3 y3Var, List list) {
        y3Var.a("Interface minification");
        y3Var.a("Reserve direct and compute hierarchy");
        Iterator it = list.iterator();
        while (it.hasNext()) {
            AbstractC0727q0 abstractC0727q0 = (AbstractC0727q0) it.next();
            if (!h && !abstractC0727q0.isInterface()) {
                throw new AssertionError();
            }
            U u = this.e;
            C0767v2 c0767v2 = abstractC0727q0.d;
            u.a(c0767v2, c0767v2);
            G g = new G(this, abstractC0727q0);
            g.c.add(abstractC0727q0.d);
            this.g.put(abstractC0727q0.d, g);
        }
        for (Map.Entry entry : this.g.entrySet()) {
            for (C0767v2 c0767v3 : ((G) entry.getValue()).a.g.a) {
                G g2 = (G) this.g.get(c0767v3);
                if (g2 != null) {
                    g2.b.add((C0767v2) entry.getKey());
                }
            }
        }
        y3Var.c();
        y3Var.a("Compute map");
        a(list);
        Iterator it2 = list.iterator();
        while (it2.hasNext()) {
            AbstractC0727q0 abstractC0727q1 = (AbstractC0727q0) it2.next();
            G g3 = (G) this.g.get(abstractC0727q1.d);
            if (!h && g3 == null) {
                throw new AssertionError();
            }
            for (W0 w0 : abstractC0727q1.M1()) {
                ((F) this.f.computeIfAbsent(this.d.c(w0), new Function() { // from class: com.android.tools.r8.naming.H$$ExternalSyntheticLambda3
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return this.f$0.a((C2173hh) obj);
                    }
                })).a(w0, g3);
            }
        }
        y3Var.c();
        Set setKeySet = ((C3733i) this.a.f()).v.keySet();
        y3Var.a("Union-find");
        final com.android.tools.r8.utils.J0 j0 = new com.android.tools.r8.utils.J0();
        setKeySet.forEach(new Consumer() { // from class: com.android.tools.r8.naming.H$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(j0, (C0721p0) obj);
            }
        });
        y3Var.c();
        y3Var.a("States for union");
        Map mapA = j0.a();
        for (C2173hh c2173hh : mapA.keySet()) {
            F f = (F) this.f.get(c2173hh);
            if (!h && f == null) {
                throw new AssertionError();
            }
            for (C2173hh c2173hh2 : (Set) mapA.get(c2173hh)) {
                W0 w1 = (W0) c2173hh2.a();
                if (!h && w1 == null) {
                    throw new AssertionError();
                }
                f.a((F) this.f.get(c2173hh2));
            }
        }
        y3Var.c();
        y3Var.a("Sort");
        List<C2173hh> list2 = (List) this.f.keySet().stream().filter(new Predicate() { // from class: com.android.tools.r8.naming.H$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return j0.c((C2173hh) obj);
            }
        }).sorted(this.a.Y().n1.D0.a(a())).collect(Collectors.toList());
        y3Var.c();
        if (!h) {
            HashSet hashSet = new HashSet(list2);
            Set setC = OU.c();
            Set setC2 = OU.c();
            for (Map.Entry entry2 : this.f.entrySet()) {
                for (W0 w2 : ((F) entry2.getValue()).b.keySet()) {
                    setC2.add(w2);
                    if (hashSet.contains(entry2.getKey())) {
                        boolean zAdd = setC.add(w2);
                        if (!h && !zAdd) {
                            throw new AssertionError();
                        }
                    }
                }
            }
            boolean z = h;
            if (!z && setC2.size() != setC.size()) {
                throw new AssertionError();
            }
            if (!z && !setC.containsAll(setC2)) {
                throw new AssertionError();
            }
        }
        if (!h) {
            HashSet hashSet2 = new HashSet(list2);
            HashSet hashSet3 = new HashSet();
            HashSet hashSet4 = new HashSet();
            for (Map.Entry entry3 : this.f.entrySet()) {
                for (C0721p0 c0721p0 : ((F) entry3.getValue()).a) {
                    hashSet4.add(c0721p0);
                    if (hashSet2.contains(entry3.getKey())) {
                        boolean zAdd2 = hashSet3.add(c0721p0);
                        if (!h && !zAdd2) {
                            throw new AssertionError();
                        }
                    }
                }
            }
            boolean z2 = h;
            if (!z2 && hashSet4.size() != hashSet3.size()) {
                throw new AssertionError();
            }
            if (!z2 && !hashSet3.containsAll(hashSet4)) {
                throw new AssertionError();
            }
        }
        y3Var.a("Reserve in groups");
        ArrayList<C2173hh> arrayList = new ArrayList();
        for (C2173hh c2173hh3 : list2) {
            F f2 = (F) this.f.get(c2173hh3);
            if (!h && f2 == null) {
                throw new AssertionError();
            }
            C0759u2 c0759u2A = f2.a();
            if (c0759u2A == null) {
                arrayList.add(c2173hh3);
            } else {
                f2.b(c0759u2A);
            }
        }
        y3Var.c();
        y3Var.a("Rename in groups");
        for (C2173hh c2173hh4 : arrayList) {
            F f3 = (F) this.f.get(c2173hh4);
            boolean z3 = h;
            if (!z3 && f3 == null) {
                throw new AssertionError();
            }
            if (!z3 && f3.a() != null) {
                throw new AssertionError();
            }
            C0759u2 c0759u2A2 = a((W0) c2173hh4.a(), f3);
            if (!z3 && c0759u2A2 == null) {
                throw new AssertionError();
            }
            AbstractC1048Hm abstractC1048Hm = this.a.Y().q0;
            if (!abstractC1048Hm.isEmpty()) {
                Set setKeySet2 = f3.b.keySet();
                if (setKeySet2.stream().map(new Function() { // from class: com.android.tools.r8.naming.H$$ExternalSyntheticLambda6
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return ((W0) obj).r0();
                    }
                }).anyMatch(new NN$$ExternalSyntheticLambda0(abstractC1048Hm))) {
                    a(((W0) c2173hh4.a()).getReference(), setKeySet2, System.out);
                }
            }
        }
        for (C2173hh c2173hh5 : arrayList) {
            F f4 = (F) this.f.get(c2173hh5);
            if (!f4.c.isEmpty()) {
                W0 w3 = (W0) c2173hh5.a();
                X xB = this.e.b(w3.J0());
                C0759u2 c0759u2A3 = xB.a(w3);
                if (!h && c0759u2A3 == null) {
                    throw new AssertionError();
                }
                for (W0 w4 : f4.c) {
                    C0759u2 c0759u2A4 = a(w4, xB, f4);
                    this.e.a(c0759u2A4, w4);
                    this.e.b(w4.getReference().e).a(c0759u2A4, w4);
                    xB.a(c0759u2A4, w4);
                }
            }
        }
        y3Var.c();
        y3Var.c();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ F a(C2173hh c2173hh) {
        return new F(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(com.android.tools.r8.utils.J0 j0, C0721p0 c0721p0) {
        HashSet<C2173hh> hashSet = new HashSet();
        Set<W0> setA = ((C3733i) this.a.f()).a(c0721p0, this.a);
        Iterator it = setA.iterator();
        while (it.hasNext()) {
            C2173hh c2173hhC = this.d.c((W0) it.next());
            F f = (F) this.f.get(c2173hhC);
            if (!h && f == null) {
                throw new AssertionError(c2173hhC);
            }
            f.a.add(c0721p0);
            hashSet.add(c2173hhC);
        }
        if (hashSet.isEmpty()) {
            return;
        }
        ArrayList arrayListA = JA.a(c0721p0, this.a);
        if (arrayListA != null) {
            for (int i = 1; i < arrayListA.size(); i++) {
                AbstractC0727q0 abstractC0727q0C = this.a.c((C0767v2) arrayListA.get(i));
                if (!h && !abstractC0727q0C.isInterface()) {
                    throw new AssertionError();
                }
                for (W0 w0 : setA) {
                    for (W0 w1 : abstractC0727q0C.P1()) {
                        if (w0.K0() != w1.K0() && C3884d2.b().b(w0.getReference(), w1.getReference())) {
                            ((F) this.f.computeIfAbsent(this.d.c(w0), new Function() { // from class: com.android.tools.r8.naming.H$$ExternalSyntheticLambda2
                                @Override // java.util.function.Function
                                public final Object apply(Object obj) {
                                    return this.f$0.b((C2173hh) obj);
                                }
                            })).c.add(w1);
                        }
                    }
                }
            }
        }
        if (hashSet.size() > 1) {
            C2173hh c2173hh = (C2173hh) hashSet.iterator().next();
            Object objB = j0.b(c2173hh);
            if (objB == null) {
                objB = j0.e(c2173hh);
            }
            C2173hh c2173hh2 = (C2173hh) objB;
            for (C2173hh c2173hh3 : hashSet) {
                if (c2173hh2 == c2173hh3) {
                    if (j0.b(c2173hh2) == null) {
                        j0.e(c2173hh2);
                    }
                } else {
                    Object objB2 = j0.b(c2173hh2);
                    if (objB2 == null) {
                        objB2 = j0.e(c2173hh2);
                    }
                    Object objB3 = j0.b(c2173hh3);
                    if (objB3 == null) {
                        objB3 = j0.e(c2173hh3);
                    }
                    j0.a(objB2, objB3);
                }
            }
        }
    }

    private C0759u2 a(W0 w0, final F f) {
        boolean z = h;
        if (!z && f.a() != null) {
            throw new AssertionError();
        }
        if (!z && !f.b.containsKey(w0)) {
            throw new AssertionError();
        }
        if (!z) {
            C0767v2 c0767v2J0 = w0.J0();
            Set set = (Set) f.b.get(w0);
            if (set != null) {
                Iterator it = set.iterator();
                while (it.hasNext()) {
                    if (((G) it.next()).c.contains(c0767v2J0)) {
                    }
                }
            }
            throw new AssertionError();
        }
        C0759u2 c0759u2A = this.e.b(w0.J0()).a(w0, new BiPredicate() { // from class: com.android.tools.r8.naming.H$$ExternalSyntheticLambda9
            @Override // java.util.function.BiPredicate
            public final boolean test(Object obj, Object obj2) {
                return H.a(f, (C0759u2) obj, (C0684j2) obj2);
            }
        });
        f.a(c0759u2A, this.e);
        return c0759u2A;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(F f, C0759u2 c0759u2, C0684j2 c0684j2) {
        return f.a(c0759u2);
    }

    private static C0759u2 a(W0 w0, X x, final F f) {
        return x.b(w0, new BiPredicate() { // from class: com.android.tools.r8.naming.H$$ExternalSyntheticLambda1
            @Override // java.util.function.BiPredicate
            public final boolean test(Object obj, Object obj2) {
                return H.b(f, (C0759u2) obj, (C0684j2) obj2);
            }
        });
    }

    private void a(List list) {
        list.forEach(new Consumer() { // from class: com.android.tools.r8.naming.H$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((AbstractC0727q0) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(final AbstractC0727q0 abstractC0727q0) {
        this.b.j(abstractC0727q0.getType()).forEach(new Consumer() { // from class: com.android.tools.r8.naming.H$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(abstractC0727q0, (C0767v2) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(AbstractC0727q0 abstractC0727q0, C0767v2 c0767v2) {
        G g;
        AbstractC0727q0 abstractC0727q0A = this.a.a(c0767v2);
        if (abstractC0727q0A == null || abstractC0727q0A.isInterface()) {
            return;
        }
        C0767v2 c0767v2A = this.e.a(c0767v2);
        if (this.e.c(c0767v2A) == null || (g = (G) this.g.get(abstractC0727q0.getType())) == null) {
            return;
        }
        g.c.add(c0767v2A);
    }

    private static void a(C0684j2 c0684j2, Set set, PrintStream printStream) {
        printStream.println("-----------------------------------------------------------------------");
        printStream.println("assignNameToInterfaceMethod(`" + c0684j2.r0() + "`)");
        printStream.println("-----------------------------------------------------------------------");
        printStream.println("Source methods:");
        Iterator it = set.iterator();
        while (it.hasNext()) {
            printStream.println("  " + ((W0) it.next()).r0());
        }
        printStream.println("States:");
        printStream.println();
    }
}
