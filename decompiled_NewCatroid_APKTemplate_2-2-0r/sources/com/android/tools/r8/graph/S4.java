package com.android.tools.r8.graph;

import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class S4 extends I4.c {
    public S4(AbstractC0727q0 abstractC0727q0, C0754u0 c0754u0, W0 w0) {
        super(abstractC0727q0, c0754u0, w0);
    }

    @Override // com.android.tools.r8.graph.I4.c
    public final I4.c a(AbstractC0727q0 abstractC0727q0) {
        return abstractC0727q0 != ((I4.c) this).b ? new S4(abstractC0727q0, (C0754u0) d(), r()) : this;
    }

    @Override // com.android.tools.r8.graph.I4
    public final S4 m() {
        return this;
    }

    @Override // com.android.tools.r8.graph.I4
    public final void a(Consumer consumer, Consumer consumer2, Consumer consumer3, Consumer consumer4) {
        consumer.accept(this);
    }
}
