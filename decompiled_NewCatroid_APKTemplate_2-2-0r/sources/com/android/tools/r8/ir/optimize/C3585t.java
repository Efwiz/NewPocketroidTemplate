package com.android.tools.r8.ir.optimize;

import com.android.tools.r8.graph.B2;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/* JADX INFO: renamed from: com.android.tools.r8.ir.optimize.t, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3585t {
    private final Map a;
    static final /* synthetic */ boolean c = true;
    private static final C3585t b = new C3585t(null);

    C3585t(IdentityHashMap identityHashMap) {
        this.a = identityHashMap;
    }

    public static C3585t a() {
        return b;
    }

    public final boolean b(com.android.tools.r8.graph.T0 t0) {
        if (!t0.E0()) {
            return false;
        }
        Map map = this.a;
        return (map != null && map.containsKey(t0)) || t0.Y0() != null;
    }

    public final void a(BiConsumer biConsumer) {
        Map map = this.a;
        if (map != null) {
            map.forEach(biConsumer);
        }
    }

    public final B2 a(com.android.tools.r8.graph.T0 t0) {
        boolean z = c;
        if (!z && !b(t0)) {
            throw new AssertionError();
        }
        if (!z && !t0.E0()) {
            throw new AssertionError();
        }
        Map map = this.a;
        if (map != null && map.containsKey(t0)) {
            return (B2) this.a.get(t0);
        }
        return t0.Y0();
    }
}
