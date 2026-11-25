package com.android.tools.r8.internal;

import com.android.tools.r8.dex.C0564k$$ExternalSyntheticLambda22;
import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.utils.C3894f2;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Wk, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1477Wk {
    static final /* synthetic */ boolean e = true;
    private final C0680j b;
    private final C3894f2 a = C3894f2.a();
    private final Set c = OU.c();
    private final Set d = OU.c();

    public C1477Wk(C0680j c0680j) {
        this.b = c0680j;
    }

    public final void a(C1297Qk c1297Qk, C2522mD c2522mD, BiConsumer biConsumer) {
        IdentityHashMap identityHashMap = new IdentityHashMap();
        Iterator<C0767v2> it = c1297Qk.t().keySet().iterator();
        while (it.hasNext()) {
            identityHashMap.put(it.next(), new C1451Vk());
        }
        a(c1297Qk, identityHashMap);
        LinkedHashMap linkedHashMapA = a(identityHashMap);
        Map<C0767v2, Set<C0684j2>> mapT = c1297Qk.t();
        for (C1451Vk c1451Vk : linkedHashMapA.values()) {
            ArrayList arrayList = new ArrayList();
            for (C0767v2 c0767v2 : c1451Vk.b) {
                if (!mapT.get(c0767v2).isEmpty()) {
                    arrayList.add(c0767v2);
                }
            }
            if (!arrayList.isEmpty()) {
                c1451Vk.b.removeAll(arrayList);
            }
        }
        a(linkedHashMapA, c2522mD);
        biConsumer.accept("The following types to wrap are missing: ", this.c);
        biConsumer.accept("The following methods cannot be handled by the wrappers due to their flags: ", this.d);
    }

    private void a(C1297Qk c1297Qk, final IdentityHashMap identityHashMap) {
        c1297Qk.t().forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.Wk$$ExternalSyntheticLambda2
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(identityHashMap, (C0767v2) obj, (Set) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Map map, C0767v2 c0767v2, Set set) {
        AbstractC0727q0 abstractC0727q0C = this.b.c(c0767v2);
        if (abstractC0727q0C == null) {
            this.c.add(c0767v2);
            map.remove(c0767v2);
        } else {
            a(abstractC0727q0C, set, (C1451Vk) map.get(c0767v2), map);
        }
    }

    private static LinkedHashMap a(IdentityHashMap identityHashMap) {
        final LinkedHashMap linkedHashMap = new LinkedHashMap();
        ArrayList arrayList = new ArrayList(identityHashMap.keySet());
        arrayList.sort(new C0564k$$ExternalSyntheticLambda22());
        LinkedList linkedList = new LinkedList(arrayList);
        while (!linkedList.isEmpty()) {
            C0767v2 c0767v2 = (C0767v2) linkedList.removeFirst();
            C1451Vk c1451Vk = (C1451Vk) identityHashMap.get(c0767v2);
            if (AbstractC2734ot.a((Iterable) c1451Vk.b, new InterfaceC3205vM() { // from class: com.android.tools.r8.internal.Wk$$ExternalSyntheticLambda0
                @Override // com.android.tools.r8.internal.InterfaceC3205vM
                public final boolean apply(Object obj) {
                    return linkedHashMap.containsKey((C0767v2) obj);
                }
            })) {
                linkedHashMap.put(c0767v2, c1451Vk);
            } else {
                linkedList.addLast(c0767v2);
            }
        }
        return linkedHashMap;
    }

    private static void a(final LinkedHashMap linkedHashMap, final C2522mD c2522mD) {
        linkedHashMap.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.Wk$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C1477Wk.a(linkedHashMap, c2522mD, (C0767v2) obj, (C1451Vk) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(LinkedHashMap linkedHashMap, C2522mD c2522mD, C0767v2 c0767v2, C1451Vk c1451Vk) {
        LinkedList linkedList = new LinkedList(c1451Vk.b);
        while (!linkedList.isEmpty()) {
            ArrayList arrayList = ((C1451Vk) linkedHashMap.get((C0767v2) linkedList.removeFirst())).b;
            c1451Vk.b.removeAll(arrayList);
            linkedList.addAll(arrayList);
        }
        c2522mD.a(c0767v2, c1451Vk.c());
    }

    private void a(AbstractC0727q0 abstractC0727q0, Set set, C1451Vk c1451Vk, Map map) {
        HashSet hashSet = new HashSet();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            hashSet.add(this.a.c((C0684j2) it.next()));
        }
        LinkedList linkedList = new LinkedList();
        ArrayList arrayListA = c1451Vk.a();
        linkedList.add(abstractC0727q0);
        while (!linkedList.isEmpty()) {
            AbstractC0727q0 abstractC0727q1 = (AbstractC0727q0) linkedList.removeFirst();
            if (abstractC0727q1 != abstractC0727q0 && map.containsKey(abstractC0727q1.d)) {
                ((C1451Vk) map.get(abstractC0727q1.d)).a(abstractC0727q0.d);
            }
            if (!abstractC0727q0.B1()) {
                for (com.android.tools.r8.graph.W0 w0 : abstractC0727q1.P1()) {
                    if (!w0.L1() && !this.b.a().g4.f.a(w0) && !this.b.a().g4.d.a(w0)) {
                        if (!e && !w0.N1() && !w0.O1()) {
                            throw new AssertionError();
                        }
                        boolean zContains = hashSet.contains(this.a.c(w0.getReference()));
                        if (!zContains) {
                            Iterator it2 = arrayListA.iterator();
                            while (it2.hasNext()) {
                                if (((C0684j2) it2.next()).b(w0.getReference())) {
                                    zContains = true;
                                    break;
                                }
                            }
                        }
                        if (!zContains) {
                            if (!w0.z1() && !w0.L1()) {
                                if (!w0.P0()) {
                                    c1451Vk.b();
                                }
                                arrayListA.add(w0.getReference());
                            } else {
                                this.d.add(w0.getReference());
                            }
                        }
                    }
                }
            }
            for (C0767v2 c0767v2 : abstractC0727q1.g.a) {
                AbstractC0727q0 abstractC0727q0C = this.b.c(c0767v2);
                if (abstractC0727q0C != null) {
                    linkedList.add(abstractC0727q0C);
                }
            }
            if (abstractC0727q1.f != this.b.a().Z1) {
                AbstractC0727q0 abstractC0727q0C2 = this.b.c(abstractC0727q1.f);
                if (!e && abstractC0727q0C2 == null) {
                    throw new AssertionError("Missing supertype " + abstractC0727q1.f + " while wrapping " + abstractC0727q0);
                }
                linkedList.add(abstractC0727q0C2);
            }
        }
    }
}
