package com.android.tools.r8.synthesis;

import com.android.tools.r8.graph.AbstractC0744s2;
import com.android.tools.r8.graph.C0767v2;

/* JADX INFO: renamed from: com.android.tools.r8.synthesis.h, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class AbstractC3850h extends U {
    final C0767v2 d;

    AbstractC3850h(L.b bVar, C3846e c3846e, C0767v2 c0767v2) {
        super(bVar, c3846e);
        this.d = c0767v2;
    }

    @Override // com.android.tools.r8.synthesis.U, com.android.tools.r8.synthesis.InterfaceC3845d
    public final C0767v2 a() {
        return this.d;
    }

    @Override // com.android.tools.r8.synthesis.U
    final AbstractC0744s2 d() {
        return this.d;
    }
}
