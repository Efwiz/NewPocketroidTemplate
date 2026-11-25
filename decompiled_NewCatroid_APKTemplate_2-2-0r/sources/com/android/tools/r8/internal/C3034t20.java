package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import java.util.ArrayList;

/* JADX INFO: renamed from: com.android.tools.r8.internal.t20, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3034t20 {
    private final ArrayList a = new ArrayList();

    public final void a(com.android.tools.r8.graph.o5 o5Var) {
        this.a.add(o5Var);
    }

    /* JADX WARN: Code duplicated, block: B:14:0x006f  */
    public final C3180v20 a(C0702m c0702m, C2688oE c2688oE) {
        C3103u20 c3103u20;
        if (this.a.size() < c2688oE.size()) {
            com.android.tools.r8.graph.I4.c<?> cVarO = ((C0680j) c0702m.f()).a(c2688oE.e(), ((com.android.tools.r8.graph.o5) this.a.iterator().next()).getReference()).o();
            if (cVarO == null || cVarO.r().s1()) {
                c3103u20 = null;
            } else {
                C0684j2 reference = cVarO.r().getReference();
                c3103u20 = new C3103u20(cVarO.d().isInterface() ? cVarO.r().getReference().b(c2688oE.e(), c0702m.a()) : reference, reference);
            }
        } else {
            c3103u20 = null;
        }
        return new C3180v20(c0702m, c2688oE, this.a, c3103u20);
    }
}
