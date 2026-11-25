package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.iz, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2270iz extends AbstractC2502lz {
    private final C2973sA b;
    private final int c;

    private C2270iz(C2973sA c2973sA, int i) {
        this.b = c2973sA;
        this.c = i;
    }

    @Override // com.android.tools.r8.shaking.InterfaceC3680a0
    public final void a(com.android.tools.r8.graph.Q0 q0) {
        this.b.a(q0);
    }

    @Override // com.android.tools.r8.internal.AbstractC2502lz
    final boolean a(final Consumer consumer, C0702m c0702m) {
        return this.b.a(new Consumer() { // from class: com.android.tools.r8.internal.iz$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(consumer, (String) obj);
            }
        }, c0702m, "kotlin/Any");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Consumer consumer, String str) {
        consumer.accept(new C1028Gx(str, this.c));
    }
}
