package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class XP extends LP {
    private final C0767v2 a;

    public XP(C0767v2 c0767v2) {
        this.a = c0767v2;
    }

    @Override // com.android.tools.r8.internal.LP
    public final AbstractC2412ko a(C0702m c0702m, C2409kl c2409kl) {
        C0767v2 c0767v2 = this.a;
        c2409kl.getClass();
        return new C3446yc(c2409kl.a(AbstractC3472z00.a(c0767v2, WH.b(), (C0702m<?>) c0702m)), c0767v2);
    }

    @Override // com.android.tools.r8.internal.LP
    public final XP d() {
        return this;
    }

    public final C0767v2 g() {
        return this.a;
    }
}
