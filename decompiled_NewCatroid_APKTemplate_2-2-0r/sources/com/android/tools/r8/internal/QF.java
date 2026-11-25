package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0702m;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class QF extends AbstractC1221Ns {
    QF(C0702m c0702m, RF rf, Function function, ConcurrentHashMap concurrentHashMap) {
        super(c0702m, rf, function, concurrentHashMap, CM.b());
    }

    @Override // com.android.tools.r8.internal.AbstractC1221Ns
    protected final void a(EnumC1953et enumC1953et, AbstractC0748t0 abstractC0748t0, com.android.tools.r8.graph.o5 o5Var) {
        if ((abstractC0748t0 instanceof com.android.tools.r8.graph.o5) && abstractC0748t0.e().D1().b()) {
            super.a(enumC1953et, abstractC0748t0, o5Var);
        }
    }

    @Override // com.android.tools.r8.graph.J5
    public final AbstractC1476Wj c() {
        return this.e.E();
    }
}
