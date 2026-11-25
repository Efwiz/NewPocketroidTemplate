package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0642e2;
import com.android.tools.r8.graph.AbstractC0713n4;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.InterfaceC0694k5;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Ce, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0891Ce {
    private final C0702m a;

    public C0891Ce(C0702m c0702m) {
        this.a = c0702m;
    }

    final C0684j2 a(boolean z, com.android.tools.r8.graph.o5 o5Var, C0684j2 c0684j2) {
        C0684j2 c0684j2A = C3378xe.a(this.a, c0684j2, a(c0684j2.B0()));
        if (c0684j2A == c0684j2) {
            return null;
        }
        a(this.a.g().b(c0684j2, z), c0684j2, o5Var);
        return c0684j2A;
    }

    final com.android.tools.r8.graph.Y0 a(com.android.tools.r8.graph.Y0 y0, InterfaceC0694k5 interfaceC0694k5) {
        C0767v2 c0767v2B0 = y0.B0();
        C0702m c0702m = this.a;
        if (c0702m.t.a(c0702m, c0767v2B0)) {
            this.a.Y().e.a("Cannot prevent the desugaring of " + y0 + " in " + interfaceC0694k5);
            return null;
        }
        C0767v2 c0767v2A = a(y0.getType());
        if (c0767v2A == y0.getType()) {
            return null;
        }
        a(this.a.g().c(y0), y0, interfaceC0694k5);
        return y0.c(this.a.a(), c0767v2A);
    }

    private void a(AbstractC0713n4 abstractC0713n4, AbstractC0642e2 abstractC0642e2, InterfaceC0694k5 interfaceC0694k5) {
        if (abstractC0713n4 != null && abstractC0713n4.j()) {
            com.android.tools.r8.graph.C5 c5G = abstractC0713n4.g();
            if (c5G.d().f0()) {
                return;
            }
            if (this.a.a().f3.contains(c5G.d().getType()) && c5G.b().O0()) {
                return;
            }
        }
        this.a.a0().c("Preventing the desugaring of " + abstractC0642e2 + " in " + interfaceC0694k5 + " which could be an invalid escape into the program. ");
    }

    final C0767v2 a(C0767v2 c0767v2) {
        C0702m c0702m = this.a;
        return c0702m.t.a(c0702m, c0767v2) ? C3378xe.a(this.a, c0767v2) : c0767v2;
    }
}
