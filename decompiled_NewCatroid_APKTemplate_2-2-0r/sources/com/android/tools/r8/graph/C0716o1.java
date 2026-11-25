package com.android.tools.r8.graph;

import com.android.tools.r8.internal.AbstractC1048Hm;

/* JADX INFO: renamed from: com.android.tools.r8.graph.o1, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0716o1 {
    public final C0684j2 a;
    private final AbstractC1048Hm b;

    private C0716o1(C0722p1 c0722p1) {
        C0759u2 c0759u2 = c0722p1.U0;
        C0684j2 c0684j2A = c0722p1.a(c0759u2, c0722p1.X0, c0759u2, new C0759u2[]{c0722p1.J0, c0722p1.G0});
        C0759u2 c0759u3 = c0722p1.V0;
        C0684j2 c0684j2A2 = c0722p1.a(c0759u3, c0722p1.X0, c0759u3, new C0759u2[]{c0722p1.J0, c0722p1.G0});
        C0759u2 c0759u4 = c0722p1.W0;
        C0759u2 c0759u5 = c0722p1.X0;
        C0759u2 c0759u6 = c0722p1.J0;
        C0684j2 c0684j2A3 = c0722p1.a(c0759u4, c0759u5, c0759u4, new C0759u2[]{c0759u6, c0759u6, c0722p1.G0});
        this.a = c0684j2A3;
        this.b = AbstractC1048Hm.a(c0684j2A, c0684j2A2, c0684j2A3);
    }

    public final boolean a(C0684j2 c0684j2) {
        return this.b.contains(c0684j2);
    }
}
