package com.android.tools.r8.graph;

import java.util.function.BiFunction;

/* JADX INFO: renamed from: com.android.tools.r8.graph.k5, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public interface InterfaceC0694k5 extends InterfaceC0593a0, l5, q5 {
    default void M() {
        e().M();
    }

    @Override // com.android.tools.r8.graph.InterfaceC0593a0
    default l5 a(l5 l5Var) {
        return this;
    }

    @Override // com.android.tools.r8.graph.InterfaceC0593a0
    C0729q2 b();

    @Override // com.android.tools.r8.graph.InterfaceC0593a0
    default o5 c() {
        return null;
    }

    @Override // com.android.tools.r8.graph.InterfaceC0593a0
    default m5 d() {
        return null;
    }

    @Override // com.android.tools.r8.graph.InterfaceC0593a0
    default InterfaceC0694k5 i() {
        return this;
    }

    @Override // com.android.tools.r8.graph.InterfaceC0593a0
    default boolean j() {
        return true;
    }

    default void a(BiFunction biFunction) {
        e().a(biFunction);
    }
}
