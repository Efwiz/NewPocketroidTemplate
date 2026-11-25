package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import java.util.IdentityHashMap;
import java.util.Map;

/* JADX INFO: renamed from: com.android.tools.r8.internal.kn, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2411kn {
    static final /* synthetic */ boolean c = true;
    private final C0702m a;
    private final Map b;

    private C2411kn(C0702m c0702m, IdentityHashMap identityHashMap) {
        this.a = c0702m;
        this.b = identityHashMap;
    }

    public final boolean a(C0729q2 c0729q2, com.android.tools.r8.graph.o5 o5Var) {
        if (!c && o5Var.e().E0()) {
            throw new AssertionError();
        }
        C0680j c0680j = (C0680j) this.a.f();
        if (!(c0680j.i() && c0680j.o().g(c0729q2)) && c0680j.c((C0767v2) this.b.getOrDefault(o5Var.t(), this.a.a().Z1), c0729q2.d)) {
            return !c0729q2.isInterface();
        }
        return false;
    }
}
