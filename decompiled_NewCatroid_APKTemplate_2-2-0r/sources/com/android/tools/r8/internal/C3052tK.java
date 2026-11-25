package com.android.tools.r8.internal;

import java.util.IdentityHashMap;

/* JADX INFO: renamed from: com.android.tools.r8.internal.tK, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3052tK {
    static final /* synthetic */ boolean b = true;
    private final IdentityHashMap a = new IdentityHashMap();

    public final void a(com.android.tools.r8.graph.T0 t0, B1 b1) {
        if (b1.isUnknown()) {
            return;
        }
        if (!b && this.a.containsKey(t0.getReference())) {
            throw new AssertionError();
        }
        this.a.put(t0.getReference(), b1);
    }

    public final AbstractC3121uK a() {
        if (this.a.isEmpty()) {
            return C2096gg.f();
        }
        return new C3270wH(this.a);
    }
}
