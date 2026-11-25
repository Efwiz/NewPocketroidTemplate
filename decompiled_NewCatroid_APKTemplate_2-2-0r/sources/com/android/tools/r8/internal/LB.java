package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import java.util.Iterator;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class LB {
    public static C2409kl a(com.android.tools.r8.graph.o5 o5Var, RB rb, SB sb, C0702m c0702m) {
        PB pb;
        final KB kb = new KB(rb, o5Var.getReference(), c0702m, sb);
        kb.i = kb.c(-1);
        boolean zE0 = o5Var.e().E0();
        int i = !zE0 ? 1 : 0;
        if (!KB.n && kb.d.a() != o5Var.D().size() + i) {
            throw new AssertionError();
        }
        if (!zE0) {
            kb.a(0, o5Var.t()).b().W();
        }
        Iterator<C0767v2> it = o5Var.D().iterator();
        while (it.hasNext()) {
            kb.a(i, it.next());
            i++;
        }
        if (kb.m < kb.d.e().length) {
            PB[] pbArrE = kb.d.e();
            int i2 = kb.m;
            kb.m = i2 + 1;
            pb = pbArrE[i2];
        } else {
            pb = null;
        }
        kb.l = pb;
        kb.i();
        rb.forEach(new Consumer() { // from class: com.android.tools.r8.internal.LB$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((VB) obj).a(kb);
            }
        });
        return kb.a(o5Var);
    }
}
