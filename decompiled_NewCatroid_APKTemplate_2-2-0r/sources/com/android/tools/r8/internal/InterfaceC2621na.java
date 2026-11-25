package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.na, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public interface InterfaceC2621na {
    static void a(C0702m c0702m, C0729q2 c0729q2, C0729q2 c0729q3, BiConsumer biConsumer) {
        LinkedHashMap linkedHashMapA = a(c0729q3);
        ArrayList<com.android.tools.r8.graph.T0> arrayList = new ArrayList();
        for (com.android.tools.r8.graph.T0 t0 : c0729q2.w1()) {
            C3360xU c3360xUA = C3360xU.a(t0);
            LinkedList linkedList = (LinkedList) linkedHashMapA.get(c3360xUA);
            if (linkedList == null || linkedList.isEmpty()) {
                arrayList.add(t0);
            } else {
                biConsumer.accept(t0, (com.android.tools.r8.graph.T0) linkedList.removeFirst());
                if (linkedList.isEmpty()) {
                    linkedHashMapA.remove(c3360xUA);
                }
            }
        }
        LinkedHashMap linkedHashMapA2 = a(c0702m, linkedHashMapA);
        for (com.android.tools.r8.graph.T0 t1 : arrayList) {
            boolean z = AbstractC2472la.a;
            if (!z && !t1.getType().X0()) {
                throw new AssertionError();
            }
            com.android.tools.r8.graph.T0 t2 = (com.android.tools.r8.graph.T0) ((LinkedList) linkedHashMapA2.get(C3360xU.a(t1, c0702m.a()))).removeFirst();
            if (!z && t2 == null) {
                throw new AssertionError();
            }
            if (!z && !t2.getType().X0()) {
                throw new AssertionError();
            }
            biConsumer.accept(t1, t2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static /* synthetic */ LinkedList b(C3360xU c3360xU) {
        return new LinkedList();
    }

    void a(com.android.tools.r8.graph.T0 t0);

    com.android.tools.r8.graph.T0[] a();

    static LinkedHashMap a(C0729q2 c0729q2) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (com.android.tools.r8.graph.T0 t0 : c0729q2.w1()) {
            ((LinkedList) linkedHashMap.computeIfAbsent(C3360xU.a(t0), new Function() { // from class: com.android.tools.r8.internal.na$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return InterfaceC2621na.a((C3360xU) obj);
                }
            })).add(t0);
        }
        return linkedHashMap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static /* synthetic */ LinkedList a(C3360xU c3360xU) {
        return new LinkedList();
    }

    static LinkedHashMap a(final C0702m c0702m, LinkedHashMap linkedHashMap) {
        final LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        linkedHashMap.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.na$$ExternalSyntheticLambda2
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                InterfaceC2621na.a(linkedHashMap2, c0702m, (C3360xU) obj, (LinkedList) obj2);
            }
        });
        return linkedHashMap2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static /* synthetic */ void a(Map map, C0702m c0702m, C3360xU c3360xU, LinkedList linkedList) {
        ((LinkedList) map.computeIfAbsent(c3360xU.a(c0702m.a()), new Function() { // from class: com.android.tools.r8.internal.na$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return InterfaceC2621na.b((C3360xU) obj);
            }
        })).addAll(linkedList);
    }

    static InterfaceC2621na a(C0702m c0702m, C0986Fk c0986Fk, C2688oE c2688oE) {
        if (c0702m.f().h()) {
            return new C2546ma(c0702m.h0(), c0986Fk, c2688oE);
        }
        boolean z = AbstractC2472la.a;
        if (!z && !c2688oE.c().isEmpty()) {
            throw new AssertionError();
        }
        if (z || c0702m.Y().O0().g()) {
            return new C2395ka();
        }
        throw new AssertionError();
    }
}
