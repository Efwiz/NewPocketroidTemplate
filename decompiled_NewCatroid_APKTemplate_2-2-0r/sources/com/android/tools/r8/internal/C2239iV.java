package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import java.util.IdentityHashMap;
import java.util.Map;

/* JADX INFO: renamed from: com.android.tools.r8.internal.iV, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2239iV {
    private final AN a;
    private final Map b;

    C2239iV(AN an, IdentityHashMap identityHashMap) {
        this.a = an;
        this.b = identityHashMap;
    }

    public static C2159hV a(C0702m c0702m) {
        return new C2159hV(c0702m);
    }

    public final com.android.tools.r8.graph.o5 a(com.android.tools.r8.graph.o5 o5Var) {
        return (com.android.tools.r8.graph.o5) this.a.b(o5Var);
    }

    public final com.android.tools.r8.graph.o5 a(C0729q2 c0729q2) {
        return (com.android.tools.r8.graph.o5) this.b.get(c0729q2);
    }
}
