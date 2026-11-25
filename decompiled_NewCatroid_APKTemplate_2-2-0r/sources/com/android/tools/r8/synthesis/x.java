package com.android.tools.r8.synthesis;

import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.internal.C3405y10;
import java.util.Collection;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class x extends w {
    x() {
        super();
    }

    @Override // com.android.tools.r8.synthesis.w, com.android.tools.r8.synthesis.InterfaceC3862u
    public final void a(C0767v2 c0767v2, Collection collection) {
        throw new C3405y10("Unexpected attempt to add globals to non-desugaring build.");
    }
}
