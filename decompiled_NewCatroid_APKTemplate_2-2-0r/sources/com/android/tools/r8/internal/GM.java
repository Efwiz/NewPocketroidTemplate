package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0767v2;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class GM extends MM {
    final /* synthetic */ NM b;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private GM(NM nm) {
        super();
        this.b = nm;
    }

    final C2720of a(C0767v2 c0767v2) {
        AbstractC0727q0 abstractC0727q0C = this.b.b.c(c0767v2);
        return abstractC0727q0C != null ? a(abstractC0727q0C) : C2720of.a();
    }
}
