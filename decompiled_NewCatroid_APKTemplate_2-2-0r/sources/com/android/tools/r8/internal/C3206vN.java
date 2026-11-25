package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.vN, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3206vN extends H4 {
    private final C0702m a;
    private final com.android.tools.r8.graph.H3 b;

    public C3206vN(C0702m c0702m, com.android.tools.r8.graph.H3 h3) {
        this.a = c0702m;
        this.b = h3;
    }

    @Override // com.android.tools.r8.internal.H4
    public final void a(Consumer consumer, Object obj) {
        C0729q2 c0729q2 = (C0729q2) obj;
        com.android.tools.r8.graph.H3 h3 = this.b;
        h3.getClass();
        h3.a(CM.b(), consumer, c0729q2);
        this.b.a(c0729q2).forEach(consumer);
    }

    @Override // com.android.tools.r8.internal.H4
    public final void a(Consumer consumer) {
        ((C0680j) this.a.f()).d().forEach(consumer);
    }
}
