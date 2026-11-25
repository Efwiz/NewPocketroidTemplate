package com.android.tools.r8.synthesis;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.H5;
import com.android.tools.r8.graph.Y0;

/* JADX INFO: renamed from: com.android.tools.r8.synthesis.o, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3857o extends H5 {
    final /* synthetic */ C3858p f;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    C3857o(C0702m c0702m, C3858p c3858p) {
        super(c0702m);
        this.f = c3858p;
    }

    @Override // com.android.tools.r8.graph.H5
    public final void a(Y0 y0, Y0 y1) {
        Y0 y2 = (Y0) this.f.a.a(y0, y1);
        if (!C3858p.d && y2 != null && y2 != y1) {
            throw new AssertionError();
        }
    }

    @Override // com.android.tools.r8.graph.H5
    public final C0767v2 b(C0767v2 c0767v2) {
        return (C0767v2) this.f.c.getOrDefault(c0767v2, c0767v2);
    }

    @Override // com.android.tools.r8.graph.H5
    public final void a(C0684j2 c0684j2, C0684j2 c0684j3) {
        this.f.b.a(c0684j2, c0684j3);
    }

    @Override // com.android.tools.r8.graph.H5
    public final void a(C0767v2 c0767v2, C0767v2 c0767v3) {
        C0767v2 c0767v4 = (C0767v2) this.f.c.put(c0767v2, c0767v3);
        if (!C3858p.d && c0767v4 != null && c0767v4 != c0767v3) {
            throw new AssertionError();
        }
    }
}
