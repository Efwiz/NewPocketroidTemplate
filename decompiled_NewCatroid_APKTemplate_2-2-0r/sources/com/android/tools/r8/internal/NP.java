package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class NP extends LP {
    private final com.android.tools.r8.graph.Y0 a;

    public NP(com.android.tools.r8.graph.Y0 y0) {
        this.a = y0;
    }

    @Override // com.android.tools.r8.internal.LP
    public final AbstractC2412ko a(C0702m c0702m, C2409kl c2409kl) {
        return new QW(this.a, c2409kl.a(AbstractC3472z00.a(this.a.h, WH.i(), (C0702m<?>) c0702m)));
    }

    @Override // com.android.tools.r8.internal.LP
    public final NP c() {
        return this;
    }

    public final com.android.tools.r8.graph.Y0 g() {
        return this.a;
    }
}
