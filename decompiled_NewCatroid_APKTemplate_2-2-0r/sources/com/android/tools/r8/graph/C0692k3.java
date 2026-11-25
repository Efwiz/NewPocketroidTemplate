package com.android.tools.r8.graph;

import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.graph.k3, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0692k3 extends AbstractC0677i3.a {
    C0692k3(AbstractC0727q0 abstractC0727q0, C0729q2 c0729q2, T0 t0) {
        super(abstractC0727q0, c0729q2, t0);
    }

    @Override // com.android.tools.r8.graph.AbstractC0677i3
    public final void a(Consumer consumer, Consumer consumer2, Consumer consumer3) {
        consumer.accept(this);
    }

    @Override // com.android.tools.r8.graph.AbstractC0677i3
    public final C0692k3 m() {
        return this;
    }

    @Override // com.android.tools.r8.graph.AbstractC0677i3
    public final m5 q() {
        return u();
    }

    @Override // com.android.tools.r8.graph.AbstractC0677i3
    public final m5 u() {
        return new m5(((AbstractC0677i3.a) this).d, (C0729q2) d());
    }

    @Override // com.android.tools.r8.graph.AbstractC0677i3
    public final boolean w() {
        return true;
    }

    @Override // com.android.tools.r8.graph.AbstractC0677i3
    public final boolean x() {
        return true;
    }
}
