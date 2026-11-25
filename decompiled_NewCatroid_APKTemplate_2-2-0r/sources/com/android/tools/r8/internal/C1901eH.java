package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0717o2;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0762u4;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.C0800x2;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.eH, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1901eH extends TF {
    static final /* synthetic */ boolean d = true;
    private final C0702m b;
    private final InterfaceC2802pl c;

    public C1901eH(C0702m c0702m, InterfaceC2802pl interfaceC2802pl, EnumC0928Dk enumC0928Dk) {
        if (!d && !enumC0928Dk.a()) {
            throw new AssertionError();
        }
        this.b = c0702m;
        this.c = interfaceC2802pl;
    }

    public final Map b(final Collection collection) {
        if (!this.b.Y().w()) {
            return Collections.emptyMap();
        }
        if (this.b.f().i()) {
            final C0722p1 c0722p1A = this.b.a();
            final C0762u4 c0762u4X = this.b.i().x();
            final AbstractC2178hm abstractC2178hmB = com.android.tools.r8.utils.Z1.b(new Consumer() { // from class: com.android.tools.r8.internal.eH$$ExternalSyntheticLambda12
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    C1901eH.a(collection, (C1946em) obj);
                }
            });
            return com.android.tools.r8.utils.Z1.a(new Consumer() { // from class: com.android.tools.r8.internal.eH$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    C1901eH.a(c0762u4X, abstractC2178hmB, c0722p1A, (IdentityHashMap) obj);
                }
            });
        }
        if (d || !this.b.Y().o1()) {
            return Collections.emptyMap();
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.WL
    public final String e() {
        return "NoInstanceInitializerMerging";
    }

    @Override // com.android.tools.r8.internal.WL
    public final boolean f() {
        return true;
    }

    @Override // com.android.tools.r8.internal.TF
    public final /* bridge */ /* synthetic */ Object a(Collection collection, ExecutorService executorService) {
        return b(collection);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C1946em c1946em, C0729q2 c0729q2) {
        c1946em.a(c0729q2.getType(), c0729q2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C0762u4 c0762u4, final Map map, final C0722p1 c0722p1, final IdentityHashMap identityHashMap) {
        c0762u4.a(new BiConsumer() { // from class: com.android.tools.r8.internal.eH$$ExternalSyntheticLambda2
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C1901eH.a(map, c0722p1, identityHashMap, (C0684j2) obj, (CN) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Map map, C0722p1 c0722p1, IdentityHashMap identityHashMap, C0684j2 c0684j2, CN cn) {
        C0729q2 c0729q2 = (C0729q2) map.get(c0684j2.B0());
        if (c0729q2 != null && c0684j2.b(c0722p1) && c0729q2.Y().b(c0684j2) == null) {
            ((Set) identityHashMap.computeIfAbsent(c0729q2, com.android.tools.r8.utils.Z1.a(new NG$$ExternalSyntheticLambda0()))).add(c0684j2);
        }
    }

    @Override // com.android.tools.r8.internal.TF
    public final Collection a(final C2688oE c2688oE, final Map map) {
        C2688oE c2688oE2;
        boolean z = d;
        if (!z && c2688oE.j()) {
            throw new AssertionError();
        }
        if (!z && c2688oE.i()) {
            throw new AssertionError();
        }
        if (c2688oE.l()) {
            LinkedList linkedList = new LinkedList();
            linkedList.add(c2688oE);
            return linkedList;
        }
        c2688oE.removeIf(new Predicate() { // from class: com.android.tools.r8.internal.eH$$ExternalSyntheticLambda9
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.b(map, (C0729q2) obj);
            }
        });
        if (c2688oE.isEmpty()) {
            return Collections.emptyList();
        }
        c2688oE.b(this.b);
        c2688oE.a(this.b);
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        final IdentityHashMap identityHashMap = new IdentityHashMap();
        Function function = new Function() { // from class: com.android.tools.r8.internal.eH$$ExternalSyntheticLambda10
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.b(c2688oE, identityHashMap, (AbstractC1243On) obj);
            }
        };
        Iterator it = c2688oE.iterator();
        while (it.hasNext()) {
            C0729q2 c0729q2 = (C0729q2) it.next();
            HashMap map2 = new HashMap();
            for (com.android.tools.r8.graph.o5 o5Var : c0729q2.h2()) {
                AbstractC1243On abstractC1243On = (AbstractC1243On) map2.put(a(o5Var.getReference()).J0(), new C1273Pn(o5Var));
                if (!d && abstractC1243On != null) {
                    throw new AssertionError();
                }
            }
            for (C0684j2 c0684j2 : (Set) map.getOrDefault(c0729q2, Collections.emptySet())) {
                AbstractC1243On abstractC1243On2 = (AbstractC1243On) map2.put(a(c0684j2).J0(), new C1216Nn(c0684j2));
                if (!d && abstractC1243On2 != null) {
                    throw new AssertionError();
                }
            }
            Iterator it2 = linkedHashMap.entrySet().iterator();
            while (true) {
                if (!it2.hasNext()) {
                    c2688oE2 = null;
                    break;
                }
                Map.Entry entry = (Map.Entry) it2.next();
                c2688oE2 = (C2688oE) entry.getKey();
                final Map map3 = (Map) entry.getValue();
                Iterator it3 = map2.entrySet().iterator();
                while (true) {
                    if (it3.hasNext()) {
                        Map.Entry entry2 = (Map.Entry) it3.next();
                        AbstractC0717o2 abstractC0717o2 = (AbstractC0717o2) entry2.getKey();
                        AbstractC1243On abstractC1243On3 = (AbstractC1243On) entry2.getValue();
                        AbstractC1243On abstractC1243On4 = (AbstractC1243On) map3.get(abstractC0717o2);
                        if (abstractC1243On4 != null) {
                            Optional optional = (Optional) function.apply(abstractC1243On3);
                            if (!optional.isPresent()) {
                                break;
                            }
                            Optional optional2 = (Optional) function.apply(abstractC1243On4);
                            if (!optional2.isPresent() || !optional.equals(optional2)) {
                                break;
                            }
                        }
                    } else {
                        Objects.requireNonNull(map3);
                        map2.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.eH$$ExternalSyntheticLambda11
                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) {
                                map3.put((AbstractC0717o2) obj, (AbstractC1243On) obj2);
                            }
                        });
                        break;
                    }
                }
            }
            if (c2688oE2 != null) {
                c2688oE2.a(c0729q2);
            } else {
                C2688oE c2688oE3 = new C2688oE();
                c2688oE3.a(c0729q2);
                linkedHashMap.put(c2688oE3, map2);
            }
        }
        Collection collectionA = WL.a(linkedHashMap.keySet());
        a(collectionA, c2688oE);
        return collectionA;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(Collection collection, final C1946em c1946em) {
        com.android.tools.r8.utils.I1.a(collection, Function.identity()).forEach(new Consumer() { // from class: com.android.tools.r8.internal.eH$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1901eH.a(c1946em, (C0729q2) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public boolean b(Map map, C0729q2 c0729q2) {
        Set setA = com.android.tools.r8.utils.T2.a(com.android.tools.r8.utils.I1.b(c0729q2.h2(), new e8$$ExternalSyntheticLambda3()));
        Set set = (Set) map.getOrDefault(c0729q2, Collections.emptySet());
        Set setC = OU.c();
        setC.addAll(setA);
        setC.addAll(set);
        if (setC.size() <= 1) {
            return false;
        }
        final Set setB = com.android.tools.r8.utils.T2.b(new C0684j2[0]);
        return AbstractC2734ot.b(setC, new InterfaceC3205vM() { // from class: com.android.tools.r8.internal.eH$$ExternalSyntheticLambda7
            @Override // com.android.tools.r8.internal.InterfaceC3205vM
            public final boolean apply(Object obj) {
                return this.f$0.a(setB, (C0684j2) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean a(Set set, C0684j2 c0684j2) {
        return !set.add(a(c0684j2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public Optional b(final C2688oE c2688oE, Map map, final AbstractC1243On abstractC1243On) {
        return (Optional) map.computeIfAbsent(abstractC1243On.b(), new Function() { // from class: com.android.tools.r8.internal.eH$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a(c2688oE, abstractC1243On, (C0684j2) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Optional a(C2688oE c2688oE, AbstractC1243On abstractC1243On, C0684j2 c0684j2) {
        return Optional.ofNullable(AbstractC1300Qn.a(this.b, this.c, c2688oE, abstractC1243On));
    }

    private C0684j2 a(C0684j2 c0684j2) {
        final C0767v2 c0767v2 = this.b.a().Z1;
        C0800x2 c0800x2G0 = c0684j2.G0();
        C0800x2 c0800x2A = c0800x2G0.a(new Function() { // from class: com.android.tools.r8.internal.eH$$ExternalSyntheticLambda6
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C1901eH.a(c0767v2, (C0767v2) obj);
            }
        });
        if (c0800x2G0 == c0800x2A) {
            return c0684j2;
        }
        C0722p1 c0722p1A = this.b.a();
        C0767v2 c0767v2B0 = c0684j2.B0();
        c0722p1A.getClass();
        return c0722p1A.a(c0767v2B0, c0800x2A.a);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C0767v2 a(C0767v2 c0767v2, C0767v2 c0767v3) {
        return c0767v3.W0() ? c0767v3 : c0767v2;
    }

    private void a(Collection collection, final C2688oE c2688oE) {
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            C2688oE c2688oE2 = (C2688oE) it.next();
            c2688oE2.b(this.b);
            final IdentityHashMap identityHashMap = new IdentityHashMap();
            if (c2688oE2.f() != c2688oE.f()) {
                InterfaceC2621na.a(this.b, c2688oE.f(), c2688oE2.f(), new BiConsumer() { // from class: com.android.tools.r8.internal.eH$$ExternalSyntheticLambda0
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        identityHashMap.put((com.android.tools.r8.graph.T0) obj, (com.android.tools.r8.graph.T0) obj2);
                    }
                });
            }
            final K4 k4C = K4.c();
            c2688oE2.a(new Consumer() { // from class: com.android.tools.r8.internal.eH$$ExternalSyntheticLambda5
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    C1901eH.a(c2688oE, identityHashMap, k4C, (C0729q2) obj);
                }
            });
            c2688oE2.a(k4C);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(final C2688oE c2688oE, final Map map, final InterfaceC2220iG interfaceC2220iG, C0729q2 c0729q2) {
        c0729q2.j(new Consumer() { // from class: com.android.tools.r8.internal.eH$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1901eH.a(c2688oE, map, interfaceC2220iG, (com.android.tools.r8.graph.m5) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C2688oE c2688oE, Map map, InterfaceC2220iG interfaceC2220iG, com.android.tools.r8.graph.m5 m5Var) {
        com.android.tools.r8.graph.T0 t0E = c2688oE.a(m5Var).e();
        K4 k4 = (K4) interfaceC2220iG;
        k4.a(m5Var.e(), (com.android.tools.r8.graph.T0) map.getOrDefault(t0E, t0E));
    }
}
