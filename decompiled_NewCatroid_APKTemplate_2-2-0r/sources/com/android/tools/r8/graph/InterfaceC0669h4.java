package com.android.tools.r8.graph;

import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.graph.h4, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public interface InterfaceC0669h4 extends InterfaceC0707m4 {
    AbstractC0727q0 a();

    @Override // com.android.tools.r8.graph.InterfaceC0707m4
    default void a(Consumer consumer, Consumer consumer2) {
        consumer.accept(this);
    }

    W0 e();

    @Override // com.android.tools.r8.graph.InterfaceC0707m4
    default InterfaceC0669h4 f() {
        return this;
    }

    C0684j2 getReference();

    @Override // com.android.tools.r8.graph.InterfaceC0707m4
    default boolean h() {
        return true;
    }

    AbstractC0748t0 p();
}
