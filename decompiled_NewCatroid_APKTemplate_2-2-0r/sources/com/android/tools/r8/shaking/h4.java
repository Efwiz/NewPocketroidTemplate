package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.C0725p4;
import com.android.tools.r8.internal.C2173hh;
import com.android.tools.r8.utils.C3894f2;
import java.util.HashMap;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class h4 {
    private static final C3894f2 c = C3894f2.a();
    private h4 a;
    private final HashMap b;

    public h4() {
        this(null);
    }

    private com.android.tools.r8.graph.W0 a(C2173hh c2173hh) {
        com.android.tools.r8.graph.W0 w0 = (com.android.tools.r8.graph.W0) this.b.get(c2173hh);
        if (w0 != null) {
            return w0;
        }
        h4 h4Var = this.a;
        if (h4Var != null) {
            return h4Var.a(c2173hh);
        }
        return null;
    }

    public final h4 b() {
        return new h4(this);
    }

    private h4(h4 h4Var) {
        this.b = new HashMap();
        this.a = h4Var;
    }

    public final int a(com.android.tools.r8.graph.W0 w0) {
        C2173hh c2173hhC = c.c(w0.getReference());
        com.android.tools.r8.graph.W0 w0A = a(c2173hhC);
        if (w0A == null) {
            this.b.put(c2173hhC, w0);
            return 2;
        }
        C0725p4 c0725p4 = w0.f;
        C0725p4 c0725p5 = w0A.f;
        String strI0 = w0.J0().I0();
        String strI1 = w0A.J0().I0();
        int iG = c0725p4.g();
        if (iG <= c0725p5.g() && !(iG == c0725p5.g() && c0725p4.r() && !strI0.equals(strI1))) {
            return 1;
        }
        this.b.put(c2173hhC, w0);
        return 3;
    }

    public final h4 a() {
        return this.a;
    }

    public final void a(h4 h4Var) {
        this.a = h4Var;
    }
}
