package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;

/* JADX INFO: renamed from: com.android.tools.r8.internal.aT, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1615aT extends com.android.tools.r8.graph.H5 {
    static final /* synthetic */ boolean h = true;
    private final G4 f;
    private final C2003fT g;

    public C1615aT(C0702m c0702m, C3011sk c3011sk, C2003fT c2003fT) {
        super(c0702m);
        this.f = c3011sk;
        this.g = c2003fT;
        a();
    }

    @Override // com.android.tools.r8.graph.H5
    public final void a(com.android.tools.r8.graph.Y0 y0, com.android.tools.r8.graph.Y0 y1) {
        this.g.b.put(y0, y1);
    }

    @Override // com.android.tools.r8.graph.H5
    public final C0767v2 b(C0767v2 c0767v2) {
        return (C0767v2) this.f.getOrDefault(c0767v2, c0767v2);
    }

    @Override // com.android.tools.r8.graph.H5
    public final void a(C0684j2 c0684j2, C0684j2 c0684j3) {
        this.g.c.put(c0684j2, c0684j3);
    }

    @Override // com.android.tools.r8.graph.H5
    public final void a(C0767v2 c0767v2, C0767v2 c0767v3) {
        this.g.a.put(c0767v2, c0767v3);
    }
}
