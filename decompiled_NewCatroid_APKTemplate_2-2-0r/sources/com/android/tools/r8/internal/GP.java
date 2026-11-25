package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0717o2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.C0800x2;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class GP {
    HashMap a;
    C2720of b;

    GP(ConcurrentHashMap concurrentHashMap, C2720of c2720of) {
        this.a = a(concurrentHashMap);
        this.b = c2720of;
    }

    final C0800x2 a(AbstractC0717o2 abstractC0717o2) {
        C0800x2 c0800x2U0 = abstractC0717o2.b().u0();
        C0767v2[] c0767v2Arr = c0800x2U0.a;
        if (c0767v2Arr.length > 1) {
            C0767v2[] c0767v2Arr2 = (C0767v2[]) c0767v2Arr.clone();
            Arrays.sort(c0767v2Arr2);
            c0800x2U0 = new C0800x2(c0767v2Arr2);
        }
        return (C0800x2) this.a.getOrDefault(c0800x2U0, c0800x2U0);
    }

    final boolean b(AbstractC0717o2 abstractC0717o2) {
        return this.b.b(abstractC0717o2);
    }

    private static HashMap a(ConcurrentHashMap concurrentHashMap) {
        final HashMap map = new HashMap();
        concurrentHashMap.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.GP$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                GP.a(map, (C0800x2) obj, (Set) obj2);
            }
        });
        return map;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Map map, C0800x2 c0800x2, Set set) {
        Iterator it = set.iterator();
        C0800x2 c0800x3 = (C0800x2) it.next();
        while (it.hasNext()) {
            C0800x2 c0800x4 = (C0800x2) it.next();
            if (c0800x4.compareTo(c0800x3) < 0) {
                c0800x3 = c0800x4;
            }
        }
        map.put(c0800x2, c0800x3);
    }
}
