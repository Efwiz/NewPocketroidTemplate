package com.android.tools.r8.graph;

import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.AbstractC1915eM;
import com.android.tools.r8.internal.AbstractC3472z00;
import com.android.tools.r8.internal.C0978Fc;
import com.android.tools.r8.internal.C2102gl;
import com.android.tools.r8.internal.WY;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class E5 extends WY {
    E5(o5 o5Var, AbstractC1915eM abstractC1915eM) {
        super(g(), ((AbstractC1915eM.c.a) AbstractC1915eM.c.x().a(0).a(o5Var.getReference()).a(abstractC1915eM)).a());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void d(C2102gl c2102gl) {
        c2102gl.getClass();
        c2102gl.a(new C0978Fc(c2102gl.a(0, AbstractC3472z00.m(), 1), 0L));
    }

    private static AbstractC1639am g() {
        return AbstractC1639am.a(new Consumer() { // from class: com.android.tools.r8.graph.E5$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                E5.d((C2102gl) obj);
            }
        }, new Consumer() { // from class: com.android.tools.r8.graph.E5$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((C2102gl) obj).f(0);
            }
        });
    }
}
