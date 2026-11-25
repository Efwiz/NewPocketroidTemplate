package com.android.tools.r8.shaking;

import com.android.tools.r8.experimental.graphinfo.GraphNode;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C4 {
    static final /* synthetic */ boolean c = true;
    final GraphNode a;
    final C4 b;

    public C4(GraphNode graphNode, C4 c4) {
        if (!c && graphNode == null) {
            throw new AssertionError();
        }
        this.a = graphNode;
        this.b = c4;
    }
}
