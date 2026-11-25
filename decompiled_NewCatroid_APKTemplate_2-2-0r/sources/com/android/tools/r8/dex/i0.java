package com.android.tools.r8.dex;

import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.internal.AbstractC3135uW;
import java.util.ArrayList;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class i0 extends g0 {
    private final Collection d;
    private final boolean e;

    i0(C0548k c0548k, Collection collection, boolean z) {
        super(c0548k);
        this.d = collection;
        this.e = z;
    }

    @Override // com.android.tools.r8.dex.g0
    public final ArrayList a() {
        final IdentityHashMap identityHashMap = new IdentityHashMap();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (C0729q2 c0729q2 : this.d) {
            if (this.e) {
                C0767v2 c0767v2A = this.a.D().a(this.a.Y(), c0729q2.getType());
                if (c0767v2A != null && c0767v2A != c0729q2.getType()) {
                    ((List) linkedHashMap.computeIfAbsent(c0767v2A, new Function() { // from class: com.android.tools.r8.dex.i0$$ExternalSyntheticLambda0
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            return i0.a((C0767v2) obj);
                        }
                    })).add(c0729q2);
                }
            }
            r0 r0Var = new r0(this.c.size(), this.a, c0729q2, null, AbstractC3135uW.b());
            this.c.add(r0Var);
            r0Var.a(c0729q2);
            identityHashMap.put(c0729q2.getType(), r0Var);
            r0Var.c.a();
        }
        linkedHashMap.forEach(new BiConsumer() { // from class: com.android.tools.r8.dex.i0$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                i0.a(identityHashMap, (C0767v2) obj, (List) obj2);
            }
        });
        return this.c;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ List a(C0767v2 c0767v2) {
        return new ArrayList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(Map map, C0767v2 c0767v2, List list) {
        r0 r0Var = (r0) map.get(c0767v2);
        Iterator it = list.iterator();
        while (it.hasNext()) {
            r0Var.a((C0729q2) it.next());
            r0Var.c.a();
        }
    }
}
