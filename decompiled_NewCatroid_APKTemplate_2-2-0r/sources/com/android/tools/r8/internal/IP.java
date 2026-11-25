package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0717o2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.C0800x2;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.utils.C3879c3;
import com.android.tools.r8.utils.InterfaceC3885d3;
import com.android.tools.r8.utils.InterfaceC3905h3;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Supplier;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class IP {
    static final /* synthetic */ boolean d = true;
    private final C0702m a;
    private final C0722p1 b;
    private final com.android.tools.r8.utils.A1 c;

    public static /* synthetic */ HashMap $r8$lambda$rPdXLJ2LQoxi0uHv3veuTP7atgA(int i) {
        return new HashMap(i);
    }

    public IP(C0702m c0702m) {
        this.a = c0702m;
        this.b = c0702m.a();
        this.c = c0702m.Y();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(AbstractC0717o2 abstractC0717o2, Map map, C2720of c2720of, AbstractC0717o2 abstractC0717o3) {
        a(abstractC0717o3, abstractC0717o2.b().u0(), map, c2720of);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ AbstractC0717o2 b(AbstractC0717o2 abstractC0717o2, C2720of c2720of) {
        return abstractC0717o2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(ExecutorService executorService) {
        GP gpA = a(executorService);
        JP jpA = KP.a(this.a);
        new FP(this, gpA, jpA).b(((C3733i) this.a.f()).e());
        if (jpA.b()) {
            return;
        }
        this.a.b((DH) jpA.a());
    }

    public final void a(final ExecutorService executorService, com.android.tools.r8.utils.y3 y3Var) {
        y3Var.a("Proto normalization", new InterfaceC3885d3() { // from class: com.android.tools.r8.internal.IP$$ExternalSyntheticLambda13
            @Override // com.android.tools.r8.utils.InterfaceC3885d3
            public final void a() {
                this.f$0.b(executorService);
            }
        });
    }

    private GP a(ExecutorService executorService) {
        final ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
        final ConcurrentHashMap concurrentHashMap2 = new ConcurrentHashMap();
        final C2720of c2720ofB = C2720of.b();
        C3879c3.a(((C3733i) this.a.f()).d(), new Consumer() { // from class: com.android.tools.r8.internal.IP$$ExternalSyntheticLambda10
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(concurrentHashMap, concurrentHashMap2, c2720ofB, (C0729q2) obj);
            }
        }, executorService);
        final HashSet hashSet = new HashSet();
        concurrentHashMap.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.IP$$ExternalSyntheticLambda11
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                IP.a(concurrentHashMap2, hashSet, (C0800x2) obj, (Set) obj2);
            }
        });
        C3879c3.a(this.a, new InterfaceC3905h3() { // from class: com.android.tools.r8.internal.IP$$ExternalSyntheticLambda12
            @Override // com.android.tools.r8.utils.InterfaceC3905h3
            public final void accept(Object obj) {
                this.f$0.a(hashSet, c2720ofB, (com.android.tools.r8.graph.o5) obj);
            }
        }, executorService);
        return new GP(concurrentHashMap2, c2720ofB);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(final Map map, final Map map2, final C2720of c2720of, C0729q2 c0729q2) {
        final HashMap map3 = new HashMap();
        c0729q2.l(new Consumer() { // from class: com.android.tools.r8.internal.IP$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(map, map2, c2720of, map3, (com.android.tools.r8.graph.o5) obj);
            }
        });
        map3.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.IP$$ExternalSyntheticLambda8
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(map2, c2720of, (AbstractC0717o2) obj, (C2720of) obj2);
            }
        });
    }

    final Map b(C0729q2 c0729q2, HP hp, GP gp) {
        HashMap mapA = a(c0729q2, hp, gp);
        a(mapA);
        return com.android.tools.r8.utils.Z1.a(mapA, new IntFunction() { // from class: com.android.tools.r8.internal.IP$$ExternalSyntheticLambda4
            @Override // java.util.function.IntFunction
            public final Object apply(int i) {
                return IP.$r8$lambda$rPdXLJ2LQoxi0uHv3veuTP7atgA(i);
            }
        }, new BiFunction() { // from class: com.android.tools.r8.internal.IP$$ExternalSyntheticLambda5
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return IP.a((AbstractC0717o2) obj, (C2720of) obj2);
            }
        }, new BiFunction() { // from class: com.android.tools.r8.internal.IP$$ExternalSyntheticLambda6
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return IP.b((AbstractC0717o2) obj, (C2720of) obj2);
            }
        }, new com.android.tools.r8.utils.G3() { // from class: com.android.tools.r8.internal.IP$$ExternalSyntheticLambda7
            @Override // com.android.tools.r8.utils.G3
            public final Object a(Object obj, Object obj2, Object obj3) {
                return IP.a((AbstractC0717o2) obj, (AbstractC0717o2) obj2, (AbstractC0717o2) obj3);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Map map, Map map2, C2720of c2720of, Map map3, com.android.tools.r8.graph.o5 o5Var) {
        C0800x2 c0800x2D = o5Var.D();
        C0767v2[] c0767v2Arr = c0800x2D.a;
        if (c0767v2Arr.length > 1) {
            C0767v2[] c0767v2Arr2 = (C0767v2[]) c0767v2Arr.clone();
            Arrays.sort(c0767v2Arr2);
            c0800x2D = new C0800x2(c0767v2Arr2);
        }
        a(o5Var, c0800x2D, map, map2, c2720of);
        AbstractC0717o2 abstractC0717o2Z = o5Var.z();
        ((C2720of) map3.computeIfAbsent(abstractC0717o2Z.a(c0800x2D, this.b), com.android.tools.r8.utils.Z1.a(new Supplier() { // from class: com.android.tools.r8.internal.IP$$ExternalSyntheticLambda9
            @Override // java.util.function.Supplier
            public final Object get() {
                return C2720of.c();
            }
        }))).a(abstractC0717o2Z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(final Map map, final C2720of c2720of, final AbstractC0717o2 abstractC0717o2, C2720of c2720of2) {
        if (c2720of2.size() > 1) {
            c2720of2.forEach(new Consumer() { // from class: com.android.tools.r8.internal.IP$$ExternalSyntheticLambda15
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a(abstractC0717o2, map, c2720of, (AbstractC0717o2) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Map map, Set set, C0800x2 c0800x2, Set set2) {
        int size = set2.size();
        if (size != 1) {
            if (!d && size <= 1) {
                throw new AssertionError();
            }
        } else {
            C0800x2 c0800x3 = (C0800x2) set2.iterator().next();
            Set set3 = (Set) map.getOrDefault(c0800x2, Collections.emptySet());
            if (set3.isEmpty() || set3.contains(c0800x3)) {
                set.add(c0800x3);
            }
        }
    }

    private static void a(AbstractC0717o2 abstractC0717o2, C0800x2 c0800x2, Map map, C2720of c2720of) {
        ((Set) map.computeIfAbsent(c0800x2, com.android.tools.r8.utils.Z1.a(new IP$$ExternalSyntheticLambda3()))).add(abstractC0717o2.b().u0());
        c2720of.a(abstractC0717o2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Set set, C2720of c2720of, com.android.tools.r8.graph.o5 o5Var) {
        if (set.contains(o5Var.D())) {
            c2720of.a(o5Var.z());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ AbstractC0717o2 a(AbstractC0717o2 abstractC0717o2, AbstractC0717o2 abstractC0717o3, AbstractC0717o2 abstractC0717o4) {
        throw new C3405y10();
    }

    private HashMap a(C0729q2 c0729q2, final HP hp, final GP gp) {
        final HashMap map = new HashMap();
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.internal.IP$$ExternalSyntheticLambda14
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(hp, gp, map, (com.android.tools.r8.graph.o5) obj);
            }
        };
        c0729q2.getClass();
        c0729q2.f(consumer, CM.b());
        return map;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(HP hp, GP gp, Map map, com.android.tools.r8.graph.o5 o5Var) {
        AbstractC0717o2 abstractC0717o2Z = o5Var.z();
        ((C2720of) map.computeIfAbsent(hp.a(abstractC0717o2Z, this.b, gp, false), com.android.tools.r8.utils.Z1.a(new IP$$ExternalSyntheticLambda2()))).a(abstractC0717o2Z);
    }

    private static void a(HashMap map) {
        final com.android.tools.r8.utils.O3 o3D = com.android.tools.r8.utils.O3.d();
        map.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.IP$$ExternalSyntheticLambda16
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                IP.a(o3D, (AbstractC0717o2) obj, (C2720of) obj2);
            }
        });
        while (o3D.c()) {
            AbstractC0717o2 abstractC0717o2 = (AbstractC0717o2) o3D.g();
            C2720of<AbstractC0717o2> c2720of = (C2720of) map.get(abstractC0717o2);
            if (!d && c2720of.size() <= 1) {
                throw new AssertionError();
            }
            if (!c2720of.b(abstractC0717o2)) {
                Comparator comparator = new Comparator() { // from class: com.android.tools.r8.internal.IP$$ExternalSyntheticLambda1
                    @Override // java.util.Comparator
                    public final int compare(Object obj, Object obj2) {
                        return ((AbstractC0717o2) obj).compareTo((AbstractC0717o2) obj2);
                    }
                };
                Object obj = null;
                for (Object obj2 : c2720of) {
                    if (obj == null || comparator.compare(obj2, obj) < 0) {
                        obj = obj2;
                    }
                }
                abstractC0717o2 = (AbstractC0717o2) obj;
            }
            for (AbstractC0717o2 abstractC0717o3 : c2720of) {
                if (!abstractC0717o3.equals(abstractC0717o2)) {
                    C2720of c2720of2 = (C2720of) map.computeIfAbsent(abstractC0717o3, com.android.tools.r8.utils.Z1.a(new IP$$ExternalSyntheticLambda2()));
                    c2720of2.a(abstractC0717o3);
                    if (c2720of2.size() > 1) {
                        o3D.c(abstractC0717o3);
                    }
                }
            }
            c2720of.clear();
            c2720of.a(abstractC0717o2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(com.android.tools.r8.utils.O3 o3, AbstractC0717o2 abstractC0717o2, C2720of c2720of) {
        if (c2720of.size() > 1) {
            o3.c(abstractC0717o2);
        }
    }

    private void a(com.android.tools.r8.graph.o5 o5Var, C0800x2 c0800x2, Map map, Map map2, C2720of c2720of) {
        if (this.a.a(o5Var).k(this.c) && !o5Var.e().D1().c()) {
            C3733i c3733i = (C3733i) this.a.f();
            if (!c3733i.a(o5Var) && (!o5Var.a().isInterface() || !o5Var.e().s1() || !c3733i.z().b(o5Var.a()))) {
                ((Set) map.computeIfAbsent(c0800x2, com.android.tools.r8.utils.Z1.a(new IP$$ExternalSyntheticLambda3()))).add(o5Var.D());
                return;
            }
        }
        a(o5Var.z(), c0800x2, map2, c2720of);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static AbstractC0717o2 a(AbstractC0717o2 abstractC0717o2, C2720of c2720of) {
        Iterator it = c2720of.iterator();
        return (AbstractC0717o2) (it.hasNext() ? it.next() : null);
    }
}
