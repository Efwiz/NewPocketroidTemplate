package com.android.tools.r8.shaking;

import com.android.tools.r8.experimental.graphinfo.GraphNode;
import com.android.tools.r8.graph.C0633d0;
import com.android.tools.r8.graph.InterfaceC0694k5;
import com.android.tools.r8.internal.C1349Sj;

/* JADX INFO: renamed from: com.android.tools.r8.shaking.w1, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3818w1 extends AbstractC3837z1 {
    private final C0633d0 a;
    private final InterfaceC0694k5 b;

    private C3818w1(C0633d0 c0633d0, InterfaceC0694k5 interfaceC0694k5) {
        this.a = c0633d0;
        this.b = interfaceC0694k5;
    }

    @Override // com.android.tools.r8.shaking.AbstractC3837z1
    public final C1349Sj.a a() {
        return C1349Sj.a.ReferencedInAnnotation;
    }

    @Override // com.android.tools.r8.shaking.AbstractC3837z1
    public final GraphNode a(M0 m0) {
        return m0.a(this.a, this.b);
    }
}
