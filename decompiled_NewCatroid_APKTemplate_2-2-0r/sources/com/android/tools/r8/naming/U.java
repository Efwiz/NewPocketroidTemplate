package com.android.tools.r8.naming;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.W0;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class U {
    final /* synthetic */ V a;

    U(V v) {
        this.a = v;
    }

    final void a(C0759u2 c0759u2, W0 w0) {
        if (c0759u2 != w0.K0()) {
            this.a.c.put(w0.getReference(), c0759u2);
        }
    }

    final X b(C0767v2 c0767v2) {
        return this.a.b(c0767v2);
    }

    final C3604a0 c(C0767v2 c0767v2) {
        return (C3604a0) this.a.e.get(c0767v2);
    }

    final void a(C0767v2 c0767v2, C0767v2 c0767v3) {
        V v = this.a;
        v.a(c0767v2, c0767v3, v.i);
    }

    final C0767v2 a(C0767v2 c0767v2) {
        return (C0767v2) this.a.g.getOrDefault(c0767v2, c0767v2);
    }

    final C0759u2 a(AbstractC0727q0 abstractC0727q0, W0 w0) {
        return this.a.b.a(abstractC0727q0, w0);
    }
}
