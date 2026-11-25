package com.android.tools.r8.dex;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class O {
    static final /* synthetic */ boolean c = true;
    private final HashMap a;
    private final Set b;

    O(C0702m c0702m, Set set) {
        HashMap map = new HashMap();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            C0729q2 c0729q2 = (C0729q2) it.next();
            a(c0702m, set, map, c0729q2.f, c0729q2);
            for (C0767v2 c0767v2 : c0729q2.g.a) {
                a(c0702m, set, map, c0767v2, c0729q2);
            }
        }
        this.b = set;
        this.a = map;
    }

    private static void a(C0702m c0702m, Set set, HashMap map, C0767v2 c0767v2, C0729q2 c0729q2) {
        c0702m.getClass();
        C0729q2 c0729q2A = C0729q2.a(c0767v2 == c0729q2.d ? c0729q2 : c0702m.a(c0767v2));
        if (c0729q2A == null || !set.contains(c0729q2A)) {
            return;
        }
        ((Collection) map.computeIfAbsent(c0729q2A, new Function() { // from class: com.android.tools.r8.dex.O$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return O.b((C0729q2) obj);
            }
        })).add(c0729q2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Collection b(C0729q2 c0729q2) {
        return new ArrayList();
    }

    final Collection a(C0729q2 c0729q2) {
        if (c || this.b.contains(c0729q2)) {
            return (Collection) this.a.getOrDefault(c0729q2, Collections.emptyList());
        }
        throw new AssertionError();
    }
}
