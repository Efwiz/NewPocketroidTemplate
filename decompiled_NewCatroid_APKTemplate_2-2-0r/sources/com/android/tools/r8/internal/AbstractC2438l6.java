package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.l6, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC2438l6 {
    protected final C0702m a;
    protected final ConcurrentHashMap b = new ConcurrentHashMap();
    protected final ConcurrentHashMap c = new ConcurrentHashMap();

    public AbstractC2438l6(C0702m c0702m) {
        this.a = c0702m;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ AbstractC2839qH a(com.android.tools.r8.graph.o5 o5Var, C0684j2 c0684j2) {
        return a(o5Var);
    }

    protected abstract AbstractC2839qH a(com.android.tools.r8.graph.o5 o5Var);

    protected final AbstractC2839qH b(final com.android.tools.r8.graph.o5 o5Var) {
        return (AbstractC2839qH) this.b.computeIfAbsent(o5Var.getReference(), new Function() { // from class: com.android.tools.r8.internal.l6$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a(o5Var, (C0684j2) obj);
            }
        });
    }
}
