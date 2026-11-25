package com.android.tools.r8.utils.structural;

import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public final class f extends c {
    private final t b;

    public f(t tVar) {
        this.b = tVar;
    }

    @Override // com.android.tools.r8.utils.structural.a
    public final int a(C0767v2 c0767v2, C0767v2 c0767v3) {
        if (c0767v2 == c0767v3) {
            return 0;
        }
        C0767v2 c0767v2A = this.b.a(c0767v2);
        C0767v2 c0767v2A2 = this.b.a(c0767v3);
        C0759u2 c0759u2D0 = c0767v2A.D0();
        C0759u2 c0759u2D1 = c0767v2A2.D0();
        c0759u2D0.getClass();
        return c0759u2D0.a(c0759u2D1);
    }
}
