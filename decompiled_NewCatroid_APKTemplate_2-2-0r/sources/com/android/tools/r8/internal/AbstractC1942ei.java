package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.ei, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC1942ei {
    public static void a(final C0702m c0702m) {
        C0722p1 c0722p1A = c0702m.a();
        AbstractC0727q0 abstractC0727q0C = c0702m.c(c0722p1A.e2);
        final com.android.tools.r8.graph.T tN = abstractC0727q0C != null ? abstractC0727q0C.N() : null;
        if (tN != null) {
            c0722p1A.r4.a(new Consumer() { // from class: com.android.tools.r8.internal.ei$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    AbstractC1942ei.a(tN, c0702m, (com.android.tools.r8.graph.Y0) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void a(com.android.tools.r8.graph.T t, C0702m c0702m, com.android.tools.r8.graph.Y0 y0) {
        AbstractC0727q0 abstractC0727q0 = (AbstractC0727q0) t;
        if (abstractC0727q0.a(y0) == null) {
            abstractC0727q0.a(com.android.tools.r8.graph.T0.d1().a(y0).a(com.android.tools.r8.graph.Q2.g(18)).a(c0702m.o()).d().a());
        }
    }
}
