package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.gz, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2121gz extends AbstractC2502lz {
    private final C2577mz b;

    private C2121gz(C2577mz c2577mz) {
        this.b = c2577mz;
    }

    @Override // com.android.tools.r8.shaking.InterfaceC3680a0
    public final void a(com.android.tools.r8.graph.Q0 q0) {
        this.b.a(q0);
    }

    @Override // com.android.tools.r8.internal.AbstractC2502lz
    final boolean a(final Consumer consumer, C0702m c0702m) {
        return this.b.a(new Consumer() { // from class: com.android.tools.r8.internal.gz$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C2121gz.a(consumer, (C3321wx) obj);
            }
        }, c0702m);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Consumer consumer, C3321wx c3321wx) {
        if (c3321wx != null) {
            consumer.accept(new C3398xx(c3321wx));
        }
    }
}
