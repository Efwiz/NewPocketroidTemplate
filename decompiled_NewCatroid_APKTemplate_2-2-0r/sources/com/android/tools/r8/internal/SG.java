package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.shaking.C3733i;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class SG extends SF {
    private final C0702m b;

    public SG(C0702m c0702m) {
        this.b = c0702m;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(Collection collection, C0729q2 c0729q2) {
        boolean z;
        if (!((C3733i) this.b.f()).j(c0729q2.d)) {
            Iterator<com.android.tools.r8.graph.W0> it = c0729q2.V0().iterator();
            while (true) {
                if (!it.hasNext()) {
                    z = false;
                    break;
                }
                com.android.tools.r8.graph.W0 next = it.next();
                if (next.E0() && next.f.c0()) {
                    z = true;
                    break;
                }
            }
        } else {
            z = true;
            break;
        }
        if (z) {
            C2688oE c2688oE = new C2688oE();
            c2688oE.a(c0729q2);
            collection.add(c2688oE);
        }
        return z;
    }

    @Override // com.android.tools.r8.internal.WL
    public final String e() {
        return "NoDeadLocks";
    }

    @Override // com.android.tools.r8.internal.SF
    public final Collection a(C2688oE c2688oE) {
        final LinkedList linkedList = new LinkedList();
        c2688oE.removeIf(new Predicate() { // from class: com.android.tools.r8.internal.SG$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.a(linkedList, (C0729q2) obj);
            }
        });
        if (linkedList.isEmpty()) {
            return Collections.singletonList(c2688oE);
        }
        Iterator it = linkedList.iterator();
        Iterator it2 = c2688oE.iterator();
        while (it2.hasNext()) {
            C0729q2 c0729q2 = (C0729q2) it2.next();
            if (!it.hasNext()) {
                it = linkedList.iterator();
            }
            ((C2688oE) it.next()).a(c0729q2);
        }
        WL.a(linkedList);
        return linkedList;
    }
}
