package com.android.tools.r8.internal;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class NX {
    private final Map a;
    private final RX b;
    private final Set c;
    private final Set d;
    private final Set e;
    private final Map f;
    private final Map g;
    private final IdentityHashMap h = new IdentityHashMap();
    private final Supplier i;

    NX(IdentityHashMap identityHashMap, Set set, Set set2, Set set3, IdentityHashMap identityHashMap2, IdentityHashMap identityHashMap3, QX qx, Supplier supplier) {
        this.a = identityHashMap;
        this.c = set;
        this.d = set2;
        this.e = set3;
        this.f = identityHashMap2;
        this.g = identityHashMap3;
        this.b = qx;
        this.i = supplier;
    }

    public final S10 a() {
        return (S10) this.i.get();
    }
}
