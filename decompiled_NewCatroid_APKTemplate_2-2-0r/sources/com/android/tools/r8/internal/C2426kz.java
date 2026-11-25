package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.kz, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2426kz extends AbstractC2502lz {
    private final AbstractC3467yx b;

    private C2426kz(AbstractC3467yx abstractC3467yx) {
        this.b = abstractC3467yx;
    }

    @Override // com.android.tools.r8.shaking.InterfaceC3680a0
    public final void a(com.android.tools.r8.graph.Q0 q0) {
    }

    @Override // com.android.tools.r8.internal.AbstractC2502lz
    final boolean a(Consumer consumer, C0702m c0702m) {
        consumer.accept(this.b);
        return false;
    }
}
