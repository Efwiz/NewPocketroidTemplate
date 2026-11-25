package com.android.tools.r8.graph;

import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class L implements O {
    protected final AbstractC0727q0 a;
    protected final C0635d2 b;

    public L(AbstractC0727q0 abstractC0727q0, C0635d2 c0635d2) {
        this.a = abstractC0727q0;
        this.b = c0635d2;
    }

    @Override // com.android.tools.r8.graph.O
    public final boolean T() {
        return true;
    }

    @Override // com.android.tools.r8.graph.O
    public final void b(Consumer consumer) {
        consumer.accept(this.a);
        consumer.accept(this.b);
    }

    @Override // com.android.tools.r8.graph.O
    public final boolean r() {
        return true;
    }
}
