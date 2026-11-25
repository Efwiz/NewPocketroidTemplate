package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.AbstractC0735r0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;

/* JADX INFO: renamed from: com.android.tools.r8.internal.mT, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2539mT extends com.android.tools.r8.shaking.r4 {
    final /* synthetic */ C2613nT h;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    C2539mT(C2613nT c2613nT, C0702m c0702m, com.android.tools.r8.graph.o5 o5Var) {
        super(c0702m, o5Var);
        this.h = c2613nT;
    }

    @Override // com.android.tools.r8.shaking.r4
    protected final boolean a(AbstractC0735r0 abstractC0735r0) {
        if (!this.h.c.b()) {
            return false;
        }
        C0767v2 c0767v2B = abstractC0735r0.getType().b(a());
        if (!c0767v2B.O0()) {
            return false;
        }
        AbstractC0727q0 abstractC0727q0C = this.h.b.c(c0767v2B);
        if (abstractC0727q0C != null && abstractC0727q0C.J1()) {
            return false;
        }
        a(Boolean.TRUE);
        return true;
    }
}
