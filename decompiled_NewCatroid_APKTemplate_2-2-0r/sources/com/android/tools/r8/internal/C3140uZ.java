package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0642e2;
import com.android.tools.r8.graph.AbstractC0717o2;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.C0800x2;
import com.android.tools.r8.shaking.C3689b;
import com.android.tools.r8.utils.AbstractC3938n2;
import com.android.tools.r8.utils.C3912j0;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.uZ, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3140uZ extends com.android.tools.r8.graph.H5 {
    static final /* synthetic */ boolean o = true;
    private final C0702m f;
    private final C1105Jk g;
    private final EnumC0928Dk h;
    private final C0986Fk i;
    private final C0722p1 j;
    private final AbstractC2151hN k;
    private final PY l;
    private final IdentityHashMap m;
    private final C3011sk n;

    public C3140uZ(C0702m c0702m, C1105Jk c1105Jk, C0986Fk c0986Fk, EnumC0928Dk enumC0928Dk, AbstractC2151hN abstractC2151hN, PY py) {
        super(c0702m);
        this.m = new IdentityHashMap();
        this.n = C3011sk.l();
        this.f = c0702m;
        this.g = c1105Jk;
        this.h = enumC0928Dk;
        this.i = c0986Fk;
        this.k = abstractC2151hN;
        this.l = py;
        this.j = c0702m.a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public G4 a(final C0729q2 c0729q2, G4 g4) {
        if (!o && c0729q2.isInterface()) {
            throw new AssertionError();
        }
        final C3011sk c3011skA = C3011sk.a(g4);
        final HashSet hashSet = new HashSet();
        c0729q2.Y().b(new Function() { // from class: com.android.tools.r8.internal.uZ$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.b(c3011skA, hashSet, (com.android.tools.r8.graph.W0) obj);
            }
        });
        c0729q2.Y().a(new Function() { // from class: com.android.tools.r8.internal.uZ$$ExternalSyntheticLambda9
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.c(hashSet, c0729q2, (com.android.tools.r8.graph.W0) obj);
            }
        });
        Set setC = OU.c();
        com.android.tools.r8.graph.T0[] t0ArrO0 = c0729q2.O0();
        com.android.tools.r8.graph.T0[] t0ArrT0 = c0729q2.T0();
        c0729q2.a(a(t0ArrO0, setC));
        c0729q2.b(a(t0ArrT0, setC));
        this.i.a();
        return c3011skA;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d(C0729q2 c0729q2) {
        C0767v2 c0767v2A = a(c0729q2.n1());
        if (c0767v2A != c0729q2.n1()) {
            this.m.put(c0729q2, c0729q2.n1());
            c0729q2.f = c0767v2A;
        }
        c0729q2.g = a(c0729q2, c0729q2.f1());
    }

    public final C1013Gk b() {
        C1013Gk c1013GkA = this.i.a(this.f, this.g);
        if (this.f.q()) {
            Collection collectionE = this.f.f().e();
            AbstractC2734ot.c(collectionE, new InterfaceC3205vM() { // from class: com.android.tools.r8.internal.uZ$$ExternalSyntheticLambda11
                @Override // com.android.tools.r8.internal.InterfaceC3205vM
                public final boolean apply(Object obj) {
                    return ((C0729q2) obj).isInterface();
                }
            }).forEach(new Consumer() { // from class: com.android.tools.r8.internal.uZ$$ExternalSyntheticLambda12
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.c((C0729q2) obj);
                }
            });
            collectionE.forEach(new Consumer() { // from class: com.android.tools.r8.internal.uZ$$ExternalSyntheticLambda13
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.b((C0729q2) obj);
                }
            });
            collectionE.forEach(new Consumer() { // from class: com.android.tools.r8.internal.uZ$$ExternalSyntheticLambda14
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.d((C0729q2) obj);
                }
            });
            C3364xY c3364xY = new C3364xY(this.f.h0());
            Iterator it = c3364xY.a().iterator();
            while (it.hasNext()) {
                c3364xY.a(C3011sk.l(), new BiFunction() { // from class: com.android.tools.r8.internal.uZ$$ExternalSyntheticLambda15
                    @Override // java.util.function.BiFunction
                    public final Object apply(Object obj, Object obj2) {
                        return this.f$0.a((C0729q2) obj, (G4) obj2);
                    }
                }, (C0729q2) it.next());
            }
            new C3689b(c1013GkA, this.f.E()).a(this.f.f().d());
        }
        return c1013GkA;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(final C0729q2 c0729q2) {
        final LinkedHashSet linkedHashSet = new LinkedHashSet();
        c0729q2.Y().c(new Function() { // from class: com.android.tools.r8.internal.uZ$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.b(linkedHashSet, c0729q2, (com.android.tools.r8.graph.W0) obj);
            }
        });
        c0729q2.Y().e(new Function() { // from class: com.android.tools.r8.internal.uZ$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.b((com.android.tools.r8.graph.W0) obj);
            }
        });
        if (!o && c0729q2.s1()) {
            throw new AssertionError();
        }
        c0729q2.b(a(c0729q2.T0(), OU.c()));
        this.i.a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(C0729q2 c0729q2) {
        if (c0729q2.r1()) {
            com.android.tools.r8.graph.O2 o2B1 = c0729q2.b1();
            if (this.g.a(o2B1.c())) {
                c0729q2.M0();
            } else {
                c0729q2.a(a(o2B1));
            }
        }
        c0729q2.a(a((List) c0729q2.e1()));
        com.android.tools.r8.graph.W4 w4H1 = c0729q2.h1();
        c0729q2.a(w4H1 != null ? new com.android.tools.r8.graph.W4(a(w4H1.a())) : null);
        List<com.android.tools.r8.graph.X4> listI1 = c0729q2.i1();
        if (!listI1.isEmpty()) {
            ArrayList arrayList = new ArrayList(listI1.size());
            Iterator<com.android.tools.r8.graph.X4> it = listI1.iterator();
            boolean z = false;
            while (it.hasNext()) {
                C0767v2 c0767v2A = it.next().a();
                C0767v2 c0767v2A2 = a(c0767v2A);
                arrayList.add(new com.android.tools.r8.graph.X4(c0767v2A2));
                z |= c0767v2A2 != c0767v2A;
            }
            if (z) {
                listI1 = arrayList;
            }
        }
        c0729q2.b((List) listI1);
    }

    private C0800x2 a(final C0729q2 c0729q2, C0800x2 c0800x2) {
        final Set setC = OU.c();
        return c0800x2.a(new Function() { // from class: com.android.tools.r8.internal.uZ$$ExternalSyntheticLambda18
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a(c0729q2, setC, (C0767v2) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public com.android.tools.r8.graph.W0 c(final Set set, C0729q2 c0729q2, com.android.tools.r8.graph.W0 w0) {
        C0684j2 reference = w0.getReference();
        C0684j2 c0684j2A = a(reference);
        if (set.contains(c0684j2A.J0())) {
            if (w0.C1()) {
                final com.android.tools.r8.utils.N n = new com.android.tools.r8.utils.N();
                c0684j2A = this.j.a(c0684j2A, this.l.a(), new Predicate() { // from class: com.android.tools.r8.internal.uZ$$ExternalSyntheticLambda4
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return C3140uZ.a(set, (C0684j2) obj);
                    }
                }, new Wn$$ExternalSyntheticLambda2(n));
                this.i.a(reference, C3151uh.a(reference, c0684j2A));
                if (n.c()) {
                    Set setA = this.i.b.a((Object) reference);
                    if (setA.isEmpty()) {
                        this.k.a(reference, new Consumer() { // from class: com.android.tools.r8.internal.uZ$$ExternalSyntheticLambda5
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                C3140uZ.a(n, (InterfaceC1997fN) obj);
                            }
                        });
                    } else {
                        Iterator it = setA.iterator();
                        while (it.hasNext()) {
                            this.k.a((C0684j2) it.next(), new Consumer() { // from class: com.android.tools.r8.internal.uZ$$ExternalSyntheticLambda6
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    C3140uZ.b(n, (InterfaceC1997fN) obj);
                                }
                            });
                        }
                    }
                }
            } else {
                c0684j2A = this.j.a(c0684j2A.h, c0684j2A.e, c0684j2A.C0().r0(), new Predicate() { // from class: com.android.tools.r8.internal.uZ$$ExternalSyntheticLambda7
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return this.f$0.b(set, (C0684j2) obj);
                    }
                });
            }
        }
        boolean zAdd = set.add(c0684j2A.J0());
        boolean z = o;
        if (!z && !zAdd) {
            throw new AssertionError();
        }
        if (this.h.b() && w0.q1()) {
            com.android.tools.r8.graph.W wA1 = w0.a1();
            wA1.getClass();
            if ((wA1 instanceof com.android.tools.r8.graph.Z) && this.g.d(c0729q2.n1())) {
                com.android.tools.r8.graph.Z.a(this.f, (C0767v2) this.m.getOrDefault(c0729q2, c0729q2.n1()), w0.a(c0729q2));
            }
        }
        C0684j2 reference2 = w0.getReference();
        if (c0684j2A == reference2) {
            return w0;
        }
        this.i.a(reference2, c0684j2A);
        com.android.tools.r8.graph.W0 w0A = w0.a(c0684j2A);
        if (w0A.H1()) {
            if (!z && w0.D1().d()) {
                throw new AssertionError();
            }
            w0A.a(AbstractC3938n2.b);
        }
        return w0A;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.tools.r8.graph.W0 b(com.android.tools.r8.graph.W0 w0) {
        String string;
        C0684j2 reference = w0.getReference();
        Iterable iterableA = reference.H0().a(this.j);
        final C1105Jk c1105Jk = this.g;
        Objects.requireNonNull(c1105Jk);
        if (!AbstractC2734ot.b(iterableA, new InterfaceC3205vM() { // from class: com.android.tools.r8.internal.uZ$$ExternalSyntheticLambda3
            @Override // com.android.tools.r8.internal.InterfaceC3205vM
            public final boolean apply(Object obj) {
                return c1105Jk.d((C0767v2) obj);
            }
        })) {
            return w0;
        }
        AbstractC0717o2 abstractC0717o2J0 = reference.J0();
        AbstractC0717o2 abstractC0717o2J1 = (AbstractC0717o2) this.n.get(abstractC0717o2J0);
        if (abstractC0717o2J1 == null) {
            abstractC0717o2J1 = a(reference).J0();
            if (this.n.containsValue(abstractC0717o2J1)) {
                C0722p1 c0722p1 = this.j;
                String strR0 = reference.C0().r0();
                int i = 0;
                while (true) {
                    int i2 = i + 1;
                    c0722p1.getClass();
                    StringBuilder sbAppend = new StringBuilder().append(strR0);
                    if (i > 0) {
                        sbAppend.append("$").append(i);
                    }
                    string = sbAppend.toString();
                    if (c0722p1.f(string) == null) {
                        break;
                    }
                    i = i2;
                }
                abstractC0717o2J1 = abstractC0717o2J1.a(c0722p1.c(string));
            }
            if (!o && this.n.containsValue(abstractC0717o2J1)) {
                throw new AssertionError();
            }
            this.n.put(abstractC0717o2J0, abstractC0717o2J1);
        }
        C0684j2 c0684j2A = this.j.a(reference.e, abstractC0717o2J1.b(), abstractC0717o2J1.a());
        this.i.a(reference, c0684j2A);
        return w0.a(c0684j2A);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(com.android.tools.r8.utils.N n, InterfaceC1997fN interfaceC1997fN) {
        Set set = (Set) n.a();
        Objects.requireNonNull(interfaceC1997fN);
        set.forEach(new Wn$$ExternalSyntheticLambda0(interfaceC1997fN));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean b(Set set, C0684j2 c0684j2) {
        return (this.n.containsValue(c0684j2.J0()) || set.contains(c0684j2.J0())) ? false : true;
    }

    @Override // com.android.tools.r8.graph.H5
    public final C0767v2 b(C0767v2 c0767v2) {
        return this.g.b(c0767v2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(Set set, C0684j2 c0684j2) {
        return !set.contains(c0684j2.J0());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(com.android.tools.r8.utils.N n, InterfaceC1997fN interfaceC1997fN) {
        Set set = (Set) n.a();
        Objects.requireNonNull(interfaceC1997fN);
        set.forEach(new Wn$$ExternalSyntheticLambda0(interfaceC1997fN));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public com.android.tools.r8.graph.W0 b(final G4 g4, final Set set, com.android.tools.r8.graph.W0 w0) {
        C0684j2 reference = w0.getReference();
        AbstractC0717o2 abstractC0717o2J0 = reference.J0();
        AbstractC0717o2 abstractC0717o2J1 = reference.J0();
        AbstractC0717o2 abstractC0717o2A = (AbstractC0717o2) g4.get(abstractC0717o2J1);
        if (abstractC0717o2A == null) {
            AbstractC0717o2 abstractC0717o2 = (AbstractC0717o2) this.n.get(abstractC0717o2J1);
            if (abstractC0717o2 != null) {
                abstractC0717o2A = abstractC0717o2;
            }
        } else if (!o && this.n.containsKey(abstractC0717o2J1)) {
            throw new AssertionError();
        }
        AbstractC0717o2 abstractC0717o2A2 = a(abstractC0717o2J0);
        if (abstractC0717o2A != null) {
            if (!o && set.contains(abstractC0717o2A)) {
                throw new AssertionError();
            }
        } else if (!this.n.containsValue(abstractC0717o2A2) && !set.contains(abstractC0717o2A2) && !g4.containsValue(abstractC0717o2A2)) {
            Iterable iterableB = abstractC0717o2A2.b().b(this.j);
            final C1105Jk c1105Jk = this.g;
            Objects.requireNonNull(c1105Jk);
            if (AbstractC2734ot.b(iterableB, new InterfaceC3205vM() { // from class: com.android.tools.r8.internal.uZ$$ExternalSyntheticLambda8
                @Override // com.android.tools.r8.internal.InterfaceC3205vM
                public final boolean apply(Object obj) {
                    return c1105Jk.e((C0767v2) obj);
                }
            })) {
                g4.put(abstractC0717o2J0, abstractC0717o2A2);
            }
            abstractC0717o2A = abstractC0717o2A2;
        } else {
            abstractC0717o2A = this.j.a(abstractC0717o2A2.b(), reference.C0().r0(), new Predicate() { // from class: com.android.tools.r8.internal.uZ$$ExternalSyntheticLambda10
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return this.f$0.a(set, g4, (AbstractC0717o2) obj);
                }
            });
            g4.put(abstractC0717o2J0, abstractC0717o2A);
        }
        boolean zAdd = set.add(abstractC0717o2A);
        boolean z = o;
        if (!z && !zAdd) {
            throw new AssertionError();
        }
        C0767v2 c0767v2A = a(reference.e);
        C0722p1 c0722p1 = this.j;
        abstractC0717o2A.getClass();
        c0767v2A.getClass();
        C0684j2 c0684j2A = c0722p1.a(c0767v2A, abstractC0717o2A.b(), abstractC0717o2A.a());
        C0684j2 reference2 = w0.getReference();
        if (c0684j2A == reference2) {
            return w0;
        }
        this.i.a(reference2, c0684j2A);
        com.android.tools.r8.graph.W0 w0A = w0.a(c0684j2A);
        if (w0A.H1()) {
            if (!z && w0.D1().d()) {
                throw new AssertionError();
            }
            w0A.a(AbstractC3938n2.b);
        }
        return w0A;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean a(Set set, G4 g4, AbstractC0717o2 abstractC0717o2) {
        return (this.n.containsValue(abstractC0717o2) || set.contains(abstractC0717o2) || g4.containsValue(abstractC0717o2)) ? false : true;
    }

    private com.android.tools.r8.graph.T0[] a(com.android.tools.r8.graph.T0[] t0Arr, final Set set) {
        if (t0Arr != null) {
            int i = com.android.tools.r8.utils.B.b;
            if (t0Arr.length != 0) {
                com.android.tools.r8.graph.T0[] t0Arr2 = new com.android.tools.r8.graph.T0[t0Arr.length];
                for (int i2 = 0; i2 < t0Arr.length; i2++) {
                    com.android.tools.r8.graph.T0 t0 = t0Arr[i2];
                    com.android.tools.r8.graph.Y0 reference = t0.getReference();
                    final com.android.tools.r8.graph.Y0 y0A = a(reference);
                    if (!set.add(y0A)) {
                        y0A = (com.android.tools.r8.graph.Y0) ((AbstractC0642e2) this.j.a(new Function() { // from class: com.android.tools.r8.internal.uZ$$ExternalSyntheticLambda16
                            @Override // java.util.function.Function
                            public final Object apply(Object obj) {
                                return this.f$0.a(y0A, set, (C0759u2) obj);
                            }
                        }, y0A.f.r0(), (C0767v2) null, 0));
                        boolean zAdd = set.add(y0A);
                        if (!o && !zAdd) {
                            throw new AssertionError();
                        }
                    }
                    if (y0A != reference) {
                        this.i.a(reference, y0A);
                        t0Arr2[i2] = t0.a(this.f, y0A, C3912j0.b());
                    } else {
                        t0Arr2[i2] = t0;
                    }
                }
                return t0Arr2;
            }
        }
        return com.android.tools.r8.graph.T0.m;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Optional a(com.android.tools.r8.graph.Y0 y0, final Set set, C0759u2 c0759u2) {
        return Optional.of(this.j.a(y0.e, y0.h, c0759u2)).filter(new Predicate() { // from class: com.android.tools.r8.internal.uZ$$ExternalSyntheticLambda17
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C3140uZ.a(set, (com.android.tools.r8.graph.Y0) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(Set set, com.android.tools.r8.graph.Y0 y0) {
        return !set.contains(y0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public C0767v2 a(C0729q2 c0729q2, Set set, C0767v2 c0767v2) {
        C0767v2 c0767v2B = this.g.b(c0767v2);
        if (!o && c0767v2B == c0729q2.getType()) {
            throw new AssertionError();
        }
        if (set.add(c0767v2B)) {
            return c0767v2B;
        }
        return null;
    }

    @Override // com.android.tools.r8.graph.H5
    public final void a(C0767v2 c0767v2, C0767v2 c0767v3) {
        throw new C3405y10();
    }

    @Override // com.android.tools.r8.graph.H5
    public final void a(com.android.tools.r8.graph.Y0 y0, com.android.tools.r8.graph.Y0 y1) {
        throw new C3405y10();
    }

    @Override // com.android.tools.r8.graph.H5
    public final void a(C0684j2 c0684j2, C0684j2 c0684j3) {
        throw new C3405y10();
    }
}
