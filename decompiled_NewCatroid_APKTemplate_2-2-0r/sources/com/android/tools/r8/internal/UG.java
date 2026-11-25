package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0717o2;
import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.q2$$ExternalSyntheticLambda28;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class UG extends TF {
    private final C0702m b;
    private final EnumC0928Dk c;

    public UG(C0702m c0702m, EnumC0928Dk enumC0928Dk) {
        this.b = c0702m;
        this.c = enumC0928Dk;
    }

    private IdentityHashMap b(Collection collection) {
        final IdentityHashMap identityHashMap = new IdentityHashMap();
        ((com.android.tools.r8.graph.G5) com.android.tools.r8.graph.G5.a(this.b).a()).a(collection, new Consumer() { // from class: com.android.tools.r8.internal.UG$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                UG.a(identityHashMap, (AbstractC0727q0) obj);
            }
        });
        identityHashMap.keySet().removeIf(new Predicate() { // from class: com.android.tools.r8.internal.UG$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.c((C0767v2) obj);
            }
        });
        return identityHashMap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean c(C0767v2 c0767v2) {
        return C0729q2.a(this.b.c(c0767v2)) == null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean d(C0767v2 c0767v2) {
        return C0729q2.a(this.b.c(c0767v2)) == null;
    }

    @Override // com.android.tools.r8.internal.TF
    public final Collection a(C2688oE c2688oE, Object obj) {
        Map map = (Map) obj;
        if (!c2688oE.l()) {
            return AbstractC1639am.c(c2688oE);
        }
        C2688oE c2688oE2 = new C2688oE();
        Iterator it = c2688oE.iterator();
        while (it.hasNext()) {
            C0729q2 c0729q2 = (C0729q2) it.next();
            Set setA = a(c0729q2, c2688oE, map);
            Map map2 = ((TG) map.get(c0729q2.getType())).b;
            Iterator it2 = setA.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    c2688oE2.a(c0729q2);
                    break;
                }
                C0684j2 c0684j2 = (C0684j2) it2.next();
                Iterator it3 = ((Set) map2.getOrDefault(c0684j2.J0(), Collections.emptySet())).iterator();
                while (it3.hasNext()) {
                    if (((C0684j2) it3.next()) != c0684j2) {
                        break;
                    }
                }
            }
        }
        if (c2688oE2.m()) {
            return Collections.emptyList();
        }
        LinkedList linkedList = new LinkedList();
        linkedList.add(c2688oE2);
        return linkedList;
    }

    @Override // com.android.tools.r8.internal.WL
    public final String e() {
        return "NoDefaultInterfaceMethodCollisions";
    }

    @Override // com.android.tools.r8.internal.WL
    public final boolean j() {
        return !this.b.Y().O0().b(this.c);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Set c(AbstractC0717o2 abstractC0717o2) {
        return OU.c();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Set b(AbstractC0717o2 abstractC0717o2) {
        return OU.c();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(Map map, AbstractC0717o2 abstractC0717o2, Set set) {
        ((Set) map.computeIfAbsent(abstractC0717o2, new Function() { // from class: com.android.tools.r8.internal.UG$$ExternalSyntheticLambda18
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return UG.b((AbstractC0717o2) obj);
            }
        })).addAll(set);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(Collection collection, final IdentityHashMap identityHashMap) {
        AbstractC2734ot.c(collection, new InterfaceC3205vM() { // from class: com.android.tools.r8.internal.UG$$ExternalSyntheticLambda4
            @Override // com.android.tools.r8.internal.InterfaceC3205vM
            public final boolean apply(Object obj) {
                return ((C2688oE) obj).k();
            }
        }).forEach(new Consumer() { // from class: com.android.tools.r8.internal.UG$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                UG.a(identityHashMap, (C2688oE) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean b(C0767v2 c0767v2) {
        return C0729q2.a(this.b.c(c0767v2)) == null;
    }

    private static Set a(C0729q2 c0729q2, C2688oE c2688oE, Map map) {
        final Set setC = OU.c();
        Iterator it = c2688oE.iterator();
        while (it.hasNext()) {
            C0729q2 c0729q3 = (C0729q2) it.next();
            if (c0729q3 != c0729q2) {
                Collection collectionValues = ((TG) map.get(c0729q3.getType())).a.values();
                Objects.requireNonNull(setC);
                collectionValues.forEach(new Consumer() { // from class: com.android.tools.r8.internal.UG$$ExternalSyntheticLambda16
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        setC.addAll((Set) obj);
                    }
                });
            }
        }
        return setC;
    }

    @Override // com.android.tools.r8.internal.TF
    public final Object a(Collection collection, ExecutorService executorService) {
        com.android.tools.r8.graph.B5 b5A = com.android.tools.r8.graph.B5.a((C0702m<? extends C0680j>) this.b);
        Collection<C0729q2> collectionD = ((C0680j) this.b.f()).d();
        IdentityHashMap identityHashMapA = a(collectionD, b(collectionD));
        Map mapA = a(collectionD, identityHashMapA, collection, b5A);
        IdentityHashMap identityHashMap = new IdentityHashMap();
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            C2688oE<C0729q2> c2688oE = (C2688oE) it.next();
            if (c2688oE.l()) {
                for (C0729q2 c0729q2 : c2688oE) {
                    identityHashMap.put(c0729q2.getType(), new TG((Map) identityHashMapA.getOrDefault(c0729q2.getType(), Collections.emptyMap()), (Map) ((HashMap) mapA).getOrDefault(c0729q2.getType(), Collections.emptyMap())));
                }
            }
        }
        return identityHashMap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Map map, AbstractC0727q0 abstractC0727q0) {
        C2720of c2720ofC = C2720of.c((C2720of) map.getOrDefault(abstractC0727q0.n1(), C2720of.d()));
        Iterator<com.android.tools.r8.graph.W0> it = abstractC0727q0.P1().iterator();
        while (it.hasNext()) {
            c2720ofC.a(it.next().o1());
        }
        map.put(abstractC0727q0.getType(), c2720ofC);
    }

    private IdentityHashMap a(Collection collection, final IdentityHashMap identityHashMap) {
        final IdentityHashMap identityHashMap2 = new IdentityHashMap();
        com.android.tools.r8.graph.G5.a(this.b).a(collection, new Consumer() { // from class: com.android.tools.r8.internal.UG$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                UG.a(identityHashMap2, identityHashMap, (AbstractC0727q0) obj);
            }
        });
        identityHashMap2.keySet().removeIf(new Predicate() { // from class: com.android.tools.r8.internal.UG$$ExternalSyntheticLambda9
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.d((C0767v2) obj);
            }
        });
        return identityHashMap2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Map map, Map map2, AbstractC0727q0 abstractC0727q0) {
        final HashMap map3 = new HashMap();
        Iterator it = abstractC0727q0.H0().iterator();
        while (it.hasNext()) {
            ((Map) map.getOrDefault((C0767v2) it.next(), Collections.emptyMap())).forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.UG$$ExternalSyntheticLambda11
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    UG.b(map3, (AbstractC0717o2) obj, (Set) obj2);
                }
            });
        }
        if (abstractC0727q0.isInterface()) {
            for (com.android.tools.r8.graph.W0 w0 : abstractC0727q0.m(new q2$$ExternalSyntheticLambda28())) {
                ((Set) map3.computeIfAbsent(w0.o1(), new Function() { // from class: com.android.tools.r8.internal.UG$$ExternalSyntheticLambda14
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return UG.c((AbstractC0717o2) obj);
                    }
                })).add(w0.getReference());
            }
        }
        map3.keySet().removeAll((Collection) map2.getOrDefault(abstractC0727q0.getType(), C2720of.d()));
        if (map3.isEmpty()) {
            return;
        }
        map.put(abstractC0727q0.getType(), map3);
    }

    private Map a(Collection collection, IdentityHashMap identityHashMap, final Collection collection2, final com.android.tools.r8.graph.B5 b5) {
        final IdentityHashMap identityHashMapA = com.android.tools.r8.utils.Z1.a(new Consumer() { // from class: com.android.tools.r8.internal.UG$$ExternalSyntheticLambda19
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                UG.b(collection2, (IdentityHashMap) obj);
            }
        });
        final Map mapA = com.android.tools.r8.utils.Z1.a(identityHashMap, new HashMap(), new Function() { // from class: com.android.tools.r8.internal.UG$$ExternalSyntheticLambda20
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return UG.a((Map) obj);
            }
        });
        com.android.tools.r8.graph.r.a(this.b, new Function() { // from class: com.android.tools.r8.internal.UG$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return UG.a(b5, identityHashMapA, (C0767v2) obj);
            }
        }).a(collection, new Consumer() { // from class: com.android.tools.r8.internal.UG$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                UG.a(mapA, identityHashMapA, (C0729q2) obj);
            }
        });
        ((HashMap) mapA).keySet().removeIf(new Predicate() { // from class: com.android.tools.r8.internal.UG$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.b((C0767v2) obj);
            }
        });
        return mapA;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(IdentityHashMap identityHashMap, C2688oE c2688oE, C0729q2 c0729q2) {
        identityHashMap.put(c0729q2.getType(), c2688oE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(final IdentityHashMap identityHashMap, final C2688oE c2688oE) {
        c2688oE.forEach(new Consumer() { // from class: com.android.tools.r8.internal.UG$$ExternalSyntheticLambda15
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                UG.a(identityHashMap, c2688oE, (C0729q2) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Map a(Map map) {
        return com.android.tools.r8.utils.Z1.a(map, new HashMap(), new Function() { // from class: com.android.tools.r8.internal.UG$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return com.android.tools.r8.utils.T2.a((Iterable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Iterable a(com.android.tools.r8.graph.B5 b5, Map map, C0767v2 c0767v2) {
        final Set setC = OU.c();
        for (C0767v2 c0767v3 : b5.c(c0767v2)) {
            Iterable iterable = (Iterable) map.get(c0767v3);
            if (iterable != null) {
                iterable.forEach(new Consumer() { // from class: com.android.tools.r8.internal.UG$$ExternalSyntheticLambda13
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        UG.a(setC, (C0729q2) obj);
                    }
                });
            } else {
                setC.add(c0767v3);
            }
        }
        return setC;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Set set, C0729q2 c0729q2) {
        set.add(c0729q2.getType());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Map map, Map map2, C0729q2 c0729q2) {
        Map map3 = (Map) map.getOrDefault(c0729q2.getType(), Collections.emptyMap());
        Iterator it = ((Iterable) map2.getOrDefault(c0729q2.getType(), com.android.tools.r8.utils.I1.b(c0729q2))).iterator();
        while (it.hasNext()) {
            Iterator it2 = ((C0729q2) it.next()).H0().iterator();
            while (it2.hasNext()) {
                final Map map4 = (Map) map.computeIfAbsent((C0767v2) it2.next(), new Function() { // from class: com.android.tools.r8.internal.UG$$ExternalSyntheticLambda10
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return UG.a((C0767v2) obj);
                    }
                });
                map3.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.UG$$ExternalSyntheticLambda12
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        UG.a(map4, (AbstractC0717o2) obj, (Set) obj2);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Map a(C0767v2 c0767v2) {
        return new HashMap();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Set a(AbstractC0717o2 abstractC0717o2) {
        return OU.c();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Map map, AbstractC0717o2 abstractC0717o2, Set set) {
        ((Set) map.computeIfAbsent(abstractC0717o2, new Function() { // from class: com.android.tools.r8.internal.UG$$ExternalSyntheticLambda17
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return UG.a((AbstractC0717o2) obj);
            }
        })).addAll(set);
    }
}
