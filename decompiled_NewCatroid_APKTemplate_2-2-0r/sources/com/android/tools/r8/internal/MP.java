package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class MP extends LP {
    private final C0684j2 a;

    public MP(C0684j2 c0684j2) {
        this.a = c0684j2;
    }

    @Override // com.android.tools.r8.internal.LP
    public final AbstractC2412ko a(C0702m c0702m, C2409kl c2409kl) {
        return new C1721bt(this.a, c2409kl.a(AbstractC3472z00.a(this.a.h.e, WH.i(), (C0702m<?>) c0702m)), AbstractC1639am.i());
    }
}
