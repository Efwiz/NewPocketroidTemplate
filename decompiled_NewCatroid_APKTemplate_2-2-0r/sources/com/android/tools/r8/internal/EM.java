package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0717o2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.shaking.C3733i;
import java.util.HashMap;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class EM {
    private final C2688oE a = new C2688oE();
    private final HashMap b = new HashMap();

    public final boolean a(C0702m c0702m, C0729q2 c0729q2) {
        HashMap map = new HashMap();
        for (com.android.tools.r8.graph.W0 w0 : c0729q2.h(new Predicate() { // from class: com.android.tools.r8.internal.EM$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.a((com.android.tools.r8.graph.W0) obj);
            }
        })) {
            AbstractC0717o2 abstractC0717o2O1 = w0.o1();
            DM dm = (DM) this.b.get(abstractC0717o2O1);
            DM dm2 = new DM(w0, c0702m.t().a(w0.getReference()).f(), ((C3733i) c0702m.f()).f().a(w0.getReference()));
            if (dm == null) {
                map.put(abstractC0717o2O1, dm2);
            } else if (!dm2.equals(dm)) {
                return false;
            }
        }
        this.b.putAll(map);
        this.a.a(c0729q2);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(com.android.tools.r8.graph.W0 w0) {
        if (w0.E0()) {
            return false;
        }
        return !w0.O0() || w0.C1();
    }
}
