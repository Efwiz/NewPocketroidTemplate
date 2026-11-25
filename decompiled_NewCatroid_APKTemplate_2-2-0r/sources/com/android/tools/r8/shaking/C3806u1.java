package com.android.tools.r8.shaking;

import com.android.tools.r8.experimental.graphinfo.GraphNode;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.internal.C1349Sj;

/* JADX INFO: renamed from: com.android.tools.r8.shaking.u1, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3806u1 extends AbstractC3837z1 {
    private final C0767v2 a;

    private C3806u1(C0767v2 c0767v2) {
        this.a = c0767v2;
    }

    @Override // com.android.tools.r8.shaking.AbstractC3837z1
    public final C1349Sj.a a() {
        return C1349Sj.a.ReachableFromLiveType;
    }

    @Override // com.android.tools.r8.shaking.AbstractC3837z1
    public final GraphNode a(M0 m0) {
        return m0.a(this.a);
    }
}
