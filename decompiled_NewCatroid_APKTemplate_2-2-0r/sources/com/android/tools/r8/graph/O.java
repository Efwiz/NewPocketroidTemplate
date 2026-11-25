package com.android.tools.r8.graph;

import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public interface O {
    static J L() {
        return new J();
    }

    AbstractC0727q0 G();

    AbstractC0727q0 R();

    boolean T();

    void b(Consumer<AbstractC0727q0> consumer);

    default boolean r() {
        return false;
    }

    AbstractC0727q0 x();
}
