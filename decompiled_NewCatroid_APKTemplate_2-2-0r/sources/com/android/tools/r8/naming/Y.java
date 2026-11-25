package com.android.tools.r8.naming;

import com.android.tools.r8.graph.C0684j2;
import java.util.HashMap;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class Y {
    final HashMap a = new HashMap();
    final Function b;

    Y(Function function) {
        this.b = function;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Object a(C0684j2 c0684j2, Object obj) {
        return a(c0684j2);
    }

    abstract Object a(C0684j2 c0684j2);

    final Object b(C0684j2 c0684j2) {
        return this.a.get(this.b.apply(c0684j2));
    }

    final Object c(final C0684j2 c0684j2) {
        return this.a.computeIfAbsent(this.b.apply(c0684j2), new Function() { // from class: com.android.tools.r8.naming.Y$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a(c0684j2, obj);
            }
        });
    }
}
