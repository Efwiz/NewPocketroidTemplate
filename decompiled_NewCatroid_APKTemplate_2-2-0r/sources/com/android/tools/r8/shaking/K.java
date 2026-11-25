package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.OU;
import java.util.Set;
import java.util.function.BiConsumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class K {
    private final Set a = OU.c();
    private final BiConsumer b;
    final /* synthetic */ L c;

    K(L l, BiConsumer biConsumer) {
        this.c = l;
        this.b = biConsumer;
    }

    final boolean a(AbstractC3837z1 abstractC3837z1, o5 o5Var) {
        com.android.tools.r8.graph.W0 w0E = o5Var.e();
        this.b.accept(w0E, abstractC3837z1);
        this.c.l(o5Var.a());
        return this.a.add(w0E);
    }

    final boolean a(o5 o5Var) {
        return this.a.contains(o5Var.e());
    }
}
