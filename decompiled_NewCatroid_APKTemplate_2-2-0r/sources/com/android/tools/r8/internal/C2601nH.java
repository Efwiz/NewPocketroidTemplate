package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0725p4;
import com.android.tools.r8.graph.C0729q2;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.nH, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2601nH extends SF {
    private final C3206vN b;
    private final com.android.tools.r8.graph.H3 c;
    private final IdentityHashMap d = new IdentityHashMap();
    private final IdentityHashMap e = new IdentityHashMap();
    private final IdentityHashMap f = new IdentityHashMap();
    private final IdentityHashMap g = new IdentityHashMap();

    public C2601nH(C0702m c0702m, com.android.tools.r8.graph.H3 h3) {
        this.b = new C3206vN(c0702m, h3);
        this.c = h3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean b(com.android.tools.r8.graph.W0 w0) {
        C0725p4 accessFlags = w0.getAccessFlags();
        return (accessFlags.m() || accessFlags.j()) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean c(AbstractC0727q0 abstractC0727q0) {
        return !abstractC0727q0.e0();
    }

    @Override // com.android.tools.r8.internal.SF
    public final Collection a(C2688oE c2688oE) {
        C2688oE<C0729q2> c2688oE2;
        C2720of c2720ofA;
        Set setA;
        Set setA2;
        LinkedList linkedList = new LinkedList();
        IdentityHashMap identityHashMap = new IdentityHashMap();
        Iterator it = c2688oE.iterator();
        while (it.hasNext()) {
            C0729q2 c0729q2 = (C0729q2) it.next();
            Iterator it2 = linkedList.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    c2688oE2 = null;
                    break;
                }
                c2688oE2 = (C2688oE) it2.next();
                C2720of c2720of = (C2720of) identityHashMap.get(c2688oE2);
                if (this.g.containsKey(c0729q2)) {
                    setA = (Set) this.g.get(c0729q2);
                } else {
                    setA = this.b.a(c0729q2);
                    Iterator it3 = setA.iterator();
                    while (it3.hasNext()) {
                        this.g.put((C0729q2) it3.next(), setA);
                    }
                }
                if (!a(setA).b(c2720of)) {
                    Set setC = OU.c();
                    for (C0729q2 c0729q3 : c2688oE2) {
                        if (this.g.containsKey(c0729q3)) {
                            setA2 = (Set) this.g.get(c0729q3);
                        } else {
                            setA2 = this.b.a(c0729q3);
                            Iterator it4 = setA2.iterator();
                            while (it4.hasNext()) {
                                this.g.put((C0729q2) it4.next(), setA2);
                            }
                        }
                        setC.add(setA2);
                    }
                    Iterator it5 = setC.iterator();
                    do {
                        if (!it5.hasNext()) {
                            break;
                        }
                    } while (!a((Set) it5.next()).b(a(c0729q2)));
                }
            }
            if (c2688oE2 == null) {
                C2688oE c2688oE3 = new C2688oE();
                c2688oE3.a(c0729q2);
                linkedList.add(c2688oE3);
                c2720ofA = C2720of.a();
                identityHashMap.put(c2688oE3, c2720ofA);
            } else {
                c2688oE2.a(c0729q2);
                c2720ofA = (C2720of) identityHashMap.get(c2688oE2);
            }
            c2720ofA.a(a(c0729q2));
        }
        return WL.a(linkedList);
    }

    @Override // com.android.tools.r8.internal.WL
    public final void d() {
        this.d.clear();
        this.e.clear();
        this.g.clear();
    }

    @Override // com.android.tools.r8.internal.WL
    public final String e() {
        return "NoWeakerAccessPriviledges";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void c(C2720of c2720of, AbstractC0727q0 abstractC0727q0) {
        c2720of.a(b(abstractC0727q0));
    }

    private C2720of b(AbstractC0727q0 abstractC0727q0) {
        if (this.f.containsKey(abstractC0727q0)) {
            return (C2720of) this.f.get(abstractC0727q0);
        }
        final C2720of c2720ofA = C2720of.a();
        abstractC0727q0.b(new nH$$ExternalSyntheticLambda1(c2720ofA), new Predicate() { // from class: com.android.tools.r8.internal.nH$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C2601nH.a((com.android.tools.r8.graph.W0) obj);
            }
        });
        this.c.a(new Consumer() { // from class: com.android.tools.r8.internal.nH$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.b(c2720ofA, (AbstractC0727q0) obj);
            }
        }, abstractC0727q0);
        this.f.put(abstractC0727q0, c2720ofA);
        return c2720ofA;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(C2720of c2720of, AbstractC0727q0 abstractC0727q0) {
        c2720of.a(b(abstractC0727q0));
    }

    private C2720of a(AbstractC0727q0 abstractC0727q0) {
        if (this.d.containsKey(abstractC0727q0)) {
            return (C2720of) this.d.get(abstractC0727q0);
        }
        final C2720of c2720ofA = C2720of.a();
        this.c.a(abstractC0727q0, new jB$$ExternalSyntheticLambda12(), new Consumer() { // from class: com.android.tools.r8.internal.nH$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(c2720ofA, (AbstractC0727q0) obj);
            }
        });
        if (abstractC0727q0.isInterface()) {
            abstractC0727q0.b(new nH$$ExternalSyntheticLambda1(c2720ofA), new O2$$ExternalSyntheticLambda4());
        }
        this.d.put(abstractC0727q0, c2720ofA);
        return c2720ofA;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C2720of c2720of, AbstractC0727q0 abstractC0727q0) {
        c2720of.a(a(abstractC0727q0));
    }

    private C2720of a(Set set) {
        if (this.e.containsKey(set)) {
            return (C2720of) this.e.get(set);
        }
        final C2720of c2720ofA = C2720of.a();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            C0729q2 c0729q2 = (C0729q2) it.next();
            c0729q2.h(new Consumer() { // from class: com.android.tools.r8.internal.nH$$ExternalSyntheticLambda4
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    c2720ofA.a((com.android.tools.r8.graph.o5) obj);
                }
            }, new Predicate() { // from class: com.android.tools.r8.internal.nH$$ExternalSyntheticLambda3
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return C2601nH.b((com.android.tools.r8.graph.W0) obj);
                }
            });
            this.c.a(c0729q2, new Predicate() { // from class: com.android.tools.r8.internal.nH$$ExternalSyntheticLambda5
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return C2601nH.c((AbstractC0727q0) obj);
                }
            }, new Consumer() { // from class: com.android.tools.r8.internal.nH$$ExternalSyntheticLambda6
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.c(c2720ofA, (AbstractC0727q0) obj);
                }
            });
        }
        this.e.put(set, c2720ofA);
        return c2720ofA;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean a(com.android.tools.r8.graph.W0 w0) {
        C0725p4 accessFlags = w0.getAccessFlags();
        return (accessFlags.m() || accessFlags.j()) ? false : true;
    }
}
