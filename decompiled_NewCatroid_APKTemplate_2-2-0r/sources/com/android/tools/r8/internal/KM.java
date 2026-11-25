package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class KM extends GM {
    final /* synthetic */ NM c;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private KM(NM nm) {
        super(nm);
        this.c = nm;
    }

    @Override // com.android.tools.r8.internal.MM
    final void a(C2720of c2720of, AbstractC0727q0 abstractC0727q0) {
        AbstractC0727q0 abstractC0727q0C;
        c2720of.a(abstractC0727q0.M1());
        if (abstractC0727q0.n1() == null || (abstractC0727q0C = this.c.b.c(abstractC0727q0.n1())) == null) {
            return;
        }
        c2720of.a(a(abstractC0727q0C));
    }
}
