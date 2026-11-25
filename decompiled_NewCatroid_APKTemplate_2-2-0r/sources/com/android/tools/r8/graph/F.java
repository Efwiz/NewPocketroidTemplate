package com.android.tools.r8.graph;

import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public interface F extends InterfaceC0593a0 {
    C0792w4 Y();

    void a(Consumer consumer);

    void c(Consumer consumer);

    C0635d2 d0();

    boolean f0();

    C0767v2 getType();

    @Override // com.android.tools.r8.graph.InterfaceC0593a0
    default boolean isClass() {
        return true;
    }

    boolean isInterface();

    C0754u0 m();
}
