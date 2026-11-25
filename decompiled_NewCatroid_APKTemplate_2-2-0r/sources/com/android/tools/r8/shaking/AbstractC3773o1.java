package com.android.tools.r8.shaking;

import com.android.tools.r8.experimental.graphinfo.GraphNode;

/* JADX INFO: renamed from: com.android.tools.r8.shaking.o1, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class AbstractC3773o1 extends AbstractC3837z1 {
    private final com.android.tools.r8.graph.W0 a;

    private AbstractC3773o1(com.android.tools.r8.graph.W0 w0) {
        this.a = w0;
    }

    @Override // com.android.tools.r8.shaking.AbstractC3837z1
    public GraphNode a(M0 m0) {
        return m0.a(this.a.getReference());
    }
}
