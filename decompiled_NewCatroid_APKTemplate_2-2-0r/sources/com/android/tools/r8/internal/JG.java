package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class JG extends UF {
    private final C0702m b;

    public JG(C0702m c0702m) {
        this.b = c0702m;
    }

    @Override // com.android.tools.r8.internal.UF
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public final IG a(C0729q2 c0729q2) {
        return this.b.D().a(c0729q2.getType(), new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.internal.JG$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.synthesis.C
            public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
                return l.R;
            }
        }) ? IG.a : IG.b;
    }

    @Override // com.android.tools.r8.internal.WL
    public final String e() {
        return "NoApiOutlineWithNonApiOutline";
    }
}
