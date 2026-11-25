package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class S8 extends H6 {
    public S8(C0702m c0702m) {
        super(c0702m);
    }

    @Override // com.android.tools.r8.internal.H6
    final boolean a(C0767v2 c0767v2, C0767v2 c0767v3) {
        if (c0767v2 == c0767v3 || c0767v3 == this.b.Z1 || c0767v2.c(this.a).a(c0767v3.c(this.a), this.a)) {
            return true;
        }
        AbstractC0727q0 abstractC0727q0C = this.a.c(c0767v3);
        return abstractC0727q0C != null && abstractC0727q0C.isInterface();
    }
}
