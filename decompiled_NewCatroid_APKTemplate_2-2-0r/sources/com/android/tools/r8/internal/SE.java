package com.android.tools.r8.internal;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class SE {
    private final ConcurrentHashMap a = new ConcurrentHashMap();

    public final RE a(com.android.tools.r8.graph.o5 o5Var, int i) {
        return (RE) this.a.computeIfAbsent(new RE(i, o5Var.getReference()), Function.identity());
    }
}
