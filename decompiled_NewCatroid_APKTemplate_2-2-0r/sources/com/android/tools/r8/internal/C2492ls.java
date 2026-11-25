package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import java.util.IdentityHashMap;

/* JADX INFO: renamed from: com.android.tools.r8.internal.ls, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2492ls {
    private IdentityHashMap a;
    private IdentityHashMap b;
    private boolean c;
    private boolean d;

    C2492ls() {
    }

    public final void a(com.android.tools.r8.graph.W0 w0, com.android.tools.r8.graph.W0 w1) {
        if (this.a == null) {
            this.a = new IdentityHashMap();
        }
        this.a.put(w0, w1);
    }

    public final void a(C0684j2 c0684j2, C0684j2 c0684j3) {
        if (this.b == null) {
            this.b = new IdentityHashMap();
        }
        this.b.put(c0684j2, c0684j3);
    }

    final void a() {
        this.c = true;
    }
}
