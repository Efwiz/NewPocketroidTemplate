package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0702m;
import java.util.IdentityHashMap;
import java.util.Set;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class PQ {
    private final Set a = OU.c();
    private final IdentityHashMap b = new IdentityHashMap();

    public final synchronized void a(AbstractC0748t0 abstractC0748t0, com.android.tools.r8.graph.o5 o5Var) {
        this.b.put(o5Var.getReference(), abstractC0748t0.getReference());
        if (abstractC0748t0.a().isInterface()) {
            this.a.add(abstractC0748t0.t());
        }
    }

    public final boolean a() {
        return this.b.isEmpty();
    }

    public final QQ a(C0702m c0702m) {
        return new QQ(c0702m, this.b, this.a);
    }
}
