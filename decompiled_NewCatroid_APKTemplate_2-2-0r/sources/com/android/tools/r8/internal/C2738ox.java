package com.android.tools.r8.internal;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.ox, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C2738ox {
    static final /* synthetic */ boolean b = true;
    private final Consumer a;

    public C2738ox(Consumer<String> consumer) {
        this.a = consumer;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static C2498lx a(C2264iv c2264iv, String str) {
        boolean z = C2574mx.c;
        C0846Aw c0846AwA = c2264iv.a(str).a();
        if (C2574mx.c || c0846AwA.i()) {
            return new C2498lx(new C2574mx(c0846AwA, a(c0846AwA, c2264iv)));
        }
        throw new AssertionError();
    }

    public void b(C2736ov c2736ov) {
        ArrayList arrayListA = a(C3465yv.a(c2736ov));
        StringBuilder sb = new StringBuilder();
        Iterator it = arrayListA.iterator();
        while (it.hasNext()) {
            ((LL) it.next()).b(sb);
            sb.append("\n");
        }
        this.a.accept(sb.toString());
    }

    private static ArrayList a(final C2736ov c2736ov) {
        final ArrayList arrayList = new ArrayList();
        final C2264iv c2264ivB = c2736ov.b();
        final HashMap map = new HashMap();
        c2736ov.e().a(new Consumer() { // from class: com.android.tools.r8.internal.ox$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C2738ox.a(c2264ivB, map, (C2572mv) obj);
            }
        });
        c2736ov.c().a(new Consumer() { // from class: com.android.tools.r8.internal.ox$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C2738ox.a(c2264ivB, map, (C2814px) obj);
            }
        });
        map.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.ox$$ExternalSyntheticLambda6
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C2738ox.a(arrayList, c2736ov, c2264ivB, map, (String) obj, (C2498lx) obj2);
            }
        });
        if (b || !arrayList.isEmpty()) {
            return arrayList;
        }
        throw new AssertionError();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static C2498lx b(C2264iv c2264iv, String str) {
        boolean z = C2574mx.c;
        C0846Aw c0846AwA = c2264iv.a(str).a();
        if (C2574mx.c || c0846AwA.i()) {
            return new C2498lx(new C2574mx(c0846AwA, a(c0846AwA, c2264iv)));
        }
        throw new AssertionError();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(final C2264iv c2264iv, Map map, C2572mv c2572mv) {
        String strA = a(c2572mv.b(), c2264iv);
        if (!b && strA == null) {
            throw new AssertionError();
        }
        C2498lx c2498lx = (C2498lx) map.computeIfAbsent(strA, new Function() { // from class: com.android.tools.r8.internal.ox$$ExternalSyntheticLambda10
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C2738ox.a(c2264iv, (String) obj);
            }
        });
        if (!C2498lx.d) {
            c2498lx.getClass();
            if (!c2572mv.b().c()) {
                throw new AssertionError();
            }
        }
        c2498lx.b.add(c2572mv.b().a());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(final C2264iv c2264iv, Map map, C2814px c2814px) {
        String strA = a(c2814px.b(), c2264iv);
        if (!b && strA == null) {
            throw new AssertionError();
        }
        ((C2498lx) map.computeIfAbsent(strA, new Function() { // from class: com.android.tools.r8.internal.ox$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C2738ox.b(c2264iv, (String) obj);
            }
        })).a(c2814px);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(final List list, final C2736ov c2736ov, final C2264iv c2264iv, final Map map, String str, final C2498lx c2498lx) {
        final C2574mx c2574mx = c2498lx.a;
        if (!c2498lx.b.isEmpty() && !c2498lx.c.isEmpty()) {
            c2498lx.c.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.ox$$ExternalSyntheticLambda12
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    C2738ox.a(list, c2574mx, c2736ov, c2264iv, c2498lx, (C1651ax) obj, (Set) obj2);
                }
            });
        } else {
            if (c2498lx.c.isEmpty()) {
                return;
            }
            if (c2736ov.e().c()) {
                c2498lx.c.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.ox$$ExternalSyntheticLambda1
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        C2738ox.a(list, c2574mx, c2736ov, c2264iv, (C1651ax) obj, (Set) obj2);
                    }
                });
            } else {
                c2498lx.c.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.ox$$ExternalSyntheticLambda2
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        C2738ox.a(map, list, c2736ov, c2574mx, c2264iv, (C1651ax) obj, (Set) obj2);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(List list, C2574mx c2574mx, C2736ov c2736ov, C2264iv c2264iv, C2498lx c2498lx, C1651ax c1651ax, Set set) {
        a(list, c2574mx, c2736ov.d(), c2264iv, c1651ax, c2498lx.b, set);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(List list, C2574mx c2574mx, C2736ov c2736ov, C2264iv c2264iv, C1651ax c1651ax, Set set) {
        a(list, c2574mx, c2736ov.d(), c2264iv, c1651ax, set);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Map map, final List list, final C2736ov c2736ov, final C2574mx c2574mx, final C2264iv c2264iv, final C1651ax c1651ax, final Set set) {
        map.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.ox$$ExternalSyntheticLambda8
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C2738ox.a(list, c2736ov, c2574mx, c2264iv, c1651ax, set, (String) obj, (C2498lx) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(List list, C2736ov c2736ov, C2574mx c2574mx, C2264iv c2264iv, C1651ax c1651ax, Set set, String str, C2498lx c2498lx) {
        if (c2498lx.b.isEmpty()) {
            return;
        }
        a(list, c2736ov.d(), c2498lx.a, c2574mx, c2264iv, c1651ax, c2498lx.b, set);
    }

    private static ArrayList a(HashSet hashSet, final C2264iv c2264iv, final HashMap map) {
        final ArrayList arrayList = new ArrayList();
        hashSet.forEach(new Consumer() { // from class: com.android.tools.r8.internal.ox$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C2738ox.a(c2264iv, map, arrayList, (String) obj);
            }
        });
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C2264iv c2264iv, Map map, List list, String str) {
        C0846Aw c0846AwA = c2264iv.a(str).a();
        if (c0846AwA.i()) {
            return;
        }
        AbstractC1171Lw abstractC1171Lw = (AbstractC1171Lw) map.put(str, c0846AwA.g());
        list.add(str);
        if (!b && abstractC1171Lw != null) {
            throw new AssertionError();
        }
    }

    private static void a(Set set, C2264iv c2264iv, HashMap map, InterfaceC2652nx interfaceC2652nx) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        Iterator it = set.iterator();
        boolean z = false;
        while (it.hasNext()) {
            String str = (String) it.next();
            C0846Aw c0846AwA = c2264iv.a(str).a();
            if (c2264iv.a(c0846AwA)) {
                map.put(str, c0846AwA.g());
                interfaceC2652nx.a(map, Collections.singletonList(str), KL.c);
                return;
            } else if (c0846AwA.i()) {
                z = true;
            } else {
                map.putIfAbsent(str, c0846AwA.g());
                if (c0846AwA.h()) {
                    arrayList2.add(str);
                } else {
                    if (!b && !c0846AwA.j()) {
                        throw new AssertionError();
                    }
                    arrayList.add(str);
                }
            }
        }
        if (z) {
            arrayList.addAll(arrayList2);
            arrayList2.clear();
        }
        if (!arrayList.isEmpty()) {
            interfaceC2652nx.a(map, arrayList, z ? KL.c : KL.b);
        } else if (z) {
            interfaceC2652nx.a(Collections.emptyMap(), Collections.emptyList(), KL.c);
        }
        if (arrayList2.isEmpty()) {
            return;
        }
        if (!b && z) {
            throw new AssertionError();
        }
        Iterator it2 = arrayList2.iterator();
        while (it2.hasNext()) {
            interfaceC2652nx.a(map, Collections.singletonList((String) it2.next()), KL.d);
        }
    }

    private static void a(final List list, final C2574mx c2574mx, final C3395xv c3395xv, C2264iv c2264iv, final C1651ax c1651ax, Set set) {
        a(set, c2264iv, new HashMap(), new InterfaceC2652nx() { // from class: com.android.tools.r8.internal.ox$$ExternalSyntheticLambda9
            @Override // com.android.tools.r8.internal.InterfaceC2652nx
            public final void a(Map map, List list2, KL kl) {
                C2738ox.a(list, c3395xv, c2574mx, c1651ax, map, list2, kl);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(List list, C3395xv c3395xv, C2574mx c2574mx, C1651ax c1651ax, Map map, List list2, KL kl) {
        if (kl.equals(KL.b)) {
            list.add(new IL(Collections.emptyList(), c3395xv, c2574mx, c1651ax, map, list2, kl));
        } else {
            list.add(new JL(c3395xv, c2574mx, c1651ax, map, list2, kl));
        }
    }

    private static void a(final List list, final C3395xv c3395xv, final C2574mx c2574mx, final C2574mx c2574mx2, C2264iv c2264iv, final C1651ax c1651ax, HashSet hashSet, Set set) {
        final HashMap map = new HashMap();
        final ArrayList arrayListA = a(hashSet, c2264iv, map);
        a(set, c2264iv, map, new InterfaceC2652nx() { // from class: com.android.tools.r8.internal.ox$$ExternalSyntheticLambda11
            @Override // com.android.tools.r8.internal.InterfaceC2652nx
            public final void a(Map map2, List list2, KL kl) {
                C2738ox.a(list, c3395xv, c1651ax, c2574mx, c2574mx2, map, arrayListA, map2, list2, kl);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(List list, C3395xv c3395xv, C1651ax c1651ax, C2574mx c2574mx, C2574mx c2574mx2, Map map, List list2, Map map2, List list3, KL kl) {
        list.add(new HL(c3395xv, c1651ax, c2574mx, c2574mx2, map, list2, list3, kl));
    }

    private static void a(final List list, final C2574mx c2574mx, final C3395xv c3395xv, C2264iv c2264iv, final C1651ax c1651ax, HashSet hashSet, Set set) {
        final HashMap map = new HashMap();
        final ArrayList arrayListA = a(hashSet, c2264iv, map);
        a(set, c2264iv, map, new InterfaceC2652nx() { // from class: com.android.tools.r8.internal.ox$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.internal.InterfaceC2652nx
            public final void a(Map map2, List list2, KL kl) {
                C2738ox.a(map, arrayListA, list, c3395xv, c2574mx, c1651ax, map2, list2, kl);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(Map map, List list, List list2, C3395xv c3395xv, C2574mx c2574mx, C1651ax c1651ax, Map map2, List list3, KL kl) {
        ArrayList arrayList = new ArrayList(list3.size());
        Iterator it = list3.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            AbstractC1171Lw abstractC1171Lw = (AbstractC1171Lw) map.get(str);
            if (abstractC1171Lw.g() && list.contains(str)) {
                HashMap map3 = new HashMap(map);
                map3.put(str, C1463Vw.k().a(((C1198Mw) C1225Nw.i().a(abstractC1171Lw.d())).f()).a());
                list2.add(new IL(list, c3395xv, c2574mx, c1651ax, map3, Collections.singletonList(str), kl));
                HashMap map4 = new HashMap(map);
                map4.put(str, C3319ww.k().a(((C2954rw) C3023sw.i().a(abstractC1171Lw.d())).f()).a());
                list2.add(new IL(list, c3395xv, c2574mx, c1651ax, map4, Collections.singletonList(str), kl));
            } else {
                arrayList.add(str);
            }
        }
        if (kl.equals(KL.b) && arrayList.isEmpty()) {
            return;
        }
        list2.add(new IL(list, c3395xv, c2574mx, c1651ax, map, arrayList, kl));
    }

    private static C2424kx a(C0846Aw c0846Aw, C2264iv c2264iv) {
        if (c0846Aw.d().b() != null) {
            return c0846Aw.d().b();
        }
        return a(c2264iv.a(c0846Aw.d().a()).a(), c2264iv);
    }

    private static String a(AbstractC0940Dw abstractC0940Dw, C2264iv c2264iv) {
        List listEmptyList;
        List listEmptyList2;
        HashSet<String> hashSet = new HashSet(2);
        ArrayDeque arrayDeque = new ArrayDeque();
        if (abstractC0940Dw.c()) {
            listEmptyList = Collections.singletonList(abstractC0940Dw.a());
        } else {
            C0846Aw c0846AwB = abstractC0940Dw.b();
            if (c0846AwB.d().d()) {
                listEmptyList = Collections.singletonList(c0846AwB.d().a());
            } else {
                listEmptyList = Collections.emptyList();
            }
        }
        arrayDeque.addAll(listEmptyList);
        while (!arrayDeque.isEmpty()) {
            String str = (String) arrayDeque.pop();
            if (hashSet.add(str)) {
                C0846Aw c0846AwA = c2264iv.a(str).a();
                if (c0846AwA.d().d()) {
                    listEmptyList2 = Collections.singletonList(c0846AwA.d().a());
                } else {
                    listEmptyList2 = Collections.emptyList();
                }
                arrayDeque.addAll(listEmptyList2);
            }
        }
        String str2 = null;
        for (String str3 : hashSet) {
            if (c2264iv.a(str3).a().i()) {
                if (str2 != null) {
                    throw new C2883qv("Unexpected reference to multiple class bindings");
                }
                str2 = str3;
            }
        }
        return str2;
    }
}
