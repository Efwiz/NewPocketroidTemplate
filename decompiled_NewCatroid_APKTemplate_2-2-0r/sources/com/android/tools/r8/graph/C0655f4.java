package com.android.tools.r8.graph;

import com.android.tools.r8.internal.OU;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.graph.f4, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0655f4 {
    static final /* synthetic */ boolean d = true;
    private final InterfaceC0679i5 a;
    private Set b;
    private Set c;

    C0655f4(InterfaceC0679i5 interfaceC0679i5) {
        this.a = interfaceC0679i5;
    }

    final void a(AbstractC0727q0 abstractC0727q0) {
        if (this.a.a(abstractC0727q0)) {
            if (this.b == null) {
                this.b = OU.c();
            }
            this.b.add(abstractC0727q0.d);
        }
    }

    final void a(InterfaceC0669h4 interfaceC0669h4) {
        a(interfaceC0669h4.a());
        W0 w0E = interfaceC0669h4.e();
        if (this.a.a(w0E)) {
            if (this.c == null) {
                this.c = OU.c();
            }
            this.c.add(w0E.getReference());
        }
    }

    final int a(C0684j2 c0684j2, C0680j c0680j) {
        Set set;
        if (!d && (set = this.b) != null && set.isEmpty()) {
            throw new AssertionError();
        }
        Set set2 = this.b;
        if (set2 == null) {
            return 1;
        }
        if (this.c != null) {
            return 2;
        }
        com.android.tools.r8.utils.O3 o3D = com.android.tools.r8.utils.O3.d((Iterable) set2);
        while (o3D.c()) {
            while (o3D.c()) {
                AbstractC0727q0 abstractC0727q0C = c0680j.c((C0767v2) o3D.f());
                if (abstractC0727q0C != null) {
                    W0 w0C = abstractC0727q0C.k.c(c0684j2);
                    if (w0C != null && (abstractC0727q0C.I1() || this.a.a(w0C))) {
                        return 2;
                    }
                    C0767v2 c0767v2 = abstractC0727q0C.f;
                    if (c0767v2 != null) {
                        o3D.c(c0767v2);
                    }
                    for (C0767v2 c0767v3 : abstractC0727q0C.g.a) {
                        o3D.c(c0767v3);
                    }
                }
            }
        }
        return 1;
    }
}
