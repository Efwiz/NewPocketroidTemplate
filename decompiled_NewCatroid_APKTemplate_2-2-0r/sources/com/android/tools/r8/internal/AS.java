package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class AS extends BS {
    AS(WH wh) {
        super(wh);
    }

    @Override // com.android.tools.r8.internal.BS
    public final BS a(WH wh) {
        return wh.h() ? BS.d : BS.e;
    }

    @Override // com.android.tools.r8.internal.BS, com.android.tools.r8.internal.AbstractC3472z00
    public final boolean equals(Object obj) {
        return this == obj || (obj instanceof AS);
    }

    @Override // com.android.tools.r8.internal.BS, com.android.tools.r8.internal.AbstractC3472z00
    public final int hashCode() {
        return System.identityHashCode(this);
    }

    @Override // com.android.tools.r8.internal.AbstractC3472z00
    public final String toString() {
        return this.c.toString() + " " + C0722p1.S5.toString();
    }

    @Override // com.android.tools.r8.internal.BS
    public final C0767v2 a(C0722p1 c0722p1) {
        return C0722p1.S5;
    }

    @Override // com.android.tools.r8.internal.BS
    public final BS a(BS bs, C0702m c0702m) {
        return bs.b(this.c);
    }
}
