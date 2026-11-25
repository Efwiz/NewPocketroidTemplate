package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import java.util.IdentityHashMap;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class MM {
    private final IdentityHashMap a;

    private MM() {
        this.a = new IdentityHashMap();
    }

    public final C2720of a(final AbstractC0727q0 abstractC0727q0) {
        return (C2720of) this.a.computeIfAbsent(abstractC0727q0, new Function() { // from class: com.android.tools.r8.internal.MM$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a(abstractC0727q0, (AbstractC0727q0) obj);
            }
        });
    }

    abstract void a(C2720of c2720of, AbstractC0727q0 abstractC0727q0);

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ C2720of a(AbstractC0727q0 abstractC0727q0, AbstractC0727q0 abstractC0727q1) {
        C2720of c2720ofC = C2720of.c();
        a(c2720ofC, abstractC0727q0);
        return c2720ofC;
    }
}
