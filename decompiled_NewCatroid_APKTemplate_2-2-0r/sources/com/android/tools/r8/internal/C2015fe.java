package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import java.util.Iterator;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.fe, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2015fe {
    private final Set a = OU.c();
    private final Set b = OU.c();

    C2015fe() {
    }

    final void a(C1937ee c1937ee) {
        this.a.addAll(c1937ee.a);
        this.b.addAll(c1937ee.b);
    }

    final void a(C0684j2 c0684j2) {
        Iterator it = this.a.iterator();
        while (it.hasNext()) {
            com.android.tools.r8.graph.W0 w0 = (com.android.tools.r8.graph.W0) it.next();
            if (c0684j2.a(w0)) {
                this.b.add(w0);
                it.remove();
            }
        }
    }

    final void a(com.android.tools.r8.graph.W0 w0) {
        this.a.add(w0);
    }

    final C1937ee a() {
        this.a.removeAll(this.b);
        if (this.a.isEmpty() && this.b.isEmpty()) {
            return C1937ee.c;
        }
        return new C1937ee(AbstractC2763pC.a(this.a), AbstractC2763pC.a(this.b));
    }
}
