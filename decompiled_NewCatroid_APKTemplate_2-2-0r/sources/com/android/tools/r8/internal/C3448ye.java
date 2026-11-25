package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.AbstractC0744s2;
import com.android.tools.r8.graph.C0635d2;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0725p4;
import java.util.Map;
import java.util.function.BiConsumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.ye, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3448ye {
    private final com.android.tools.r8.graph.Q0 a;
    private final com.android.tools.r8.utils.O2 b;
    private final com.android.tools.r8.androidapi.h c;

    private C3448ye(com.android.tools.r8.graph.Q0 q0, com.android.tools.r8.utils.O2 o2, com.android.tools.r8.androidapi.h hVar) {
        this.a = q0;
        this.b = o2;
        this.c = hVar;
    }

    public static void a(C0702m c0702m) {
        a(c0702m.Y().C1.i(), c0702m.Y().C1.h(), c0702m, c0702m.Y().e, c0702m.o());
    }

    public static void a(Map map, Map map2, com.android.tools.r8.graph.Q0 q0, com.android.tools.r8.utils.O2 o2, com.android.tools.r8.androidapi.h hVar) {
        if (map.isEmpty() && map2.isEmpty()) {
            return;
        }
        new C3448ye(q0, o2, hVar).a(map, map2);
    }

    private void a(Map map, Map map2) {
        map.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.ye$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a((C0684j2) obj, (C0725p4) obj2);
            }
        });
        map2.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.ye$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a((com.android.tools.r8.graph.Y0) obj, (com.android.tools.r8.graph.Q2) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(com.android.tools.r8.graph.Y0 y0, com.android.tools.r8.graph.Q2 q2) {
        C0635d2 c0635d2A = a(y0);
        if (c0635d2A != null && c0635d2A.a(y0) == null) {
            com.android.tools.r8.graph.T0 t0A = com.android.tools.r8.graph.T0.d1().a(y0).a(q2).a(this.c).a();
            if (q2.o()) {
                c0635d2A.b(t0A);
            } else {
                c0635d2A.a(t0A);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C0684j2 c0684j2, C0725p4 c0725p4) {
        C0635d2 c0635d2A = a(c0684j2);
        if (c0635d2A != null && c0635d2A.c(c0684j2) == null) {
            c0635d2A.Y().b(com.android.tools.r8.graph.W0.Y1().a(c0684j2).a(c0725p4).b(this.c).a());
        }
    }

    private C0635d2 a(AbstractC0744s2 abstractC0744s2) {
        AbstractC0727q0 abstractC0727q0G;
        AbstractC0727q0 abstractC0727q0A = this.a.a(abstractC0744s2.C());
        if (abstractC0727q0A != null && abstractC0727q0A.f0()) {
            return abstractC0727q0A.d0();
        }
        com.android.tools.r8.graph.O oF = this.a.f(abstractC0744s2.C());
        if (oF.r() && (abstractC0727q0G = oF.G()) != null && abstractC0727q0G.f0()) {
            return abstractC0727q0G.d0();
        }
        this.b.c("Desugared library: Cannot amend library reference " + abstractC0744s2 + " because the holder is not a library class" + (abstractC0727q0A == null ? "(null)." : "."));
        return null;
    }
}
