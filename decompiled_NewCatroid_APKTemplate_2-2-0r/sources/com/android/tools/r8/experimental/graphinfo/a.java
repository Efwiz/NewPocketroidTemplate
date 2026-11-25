package com.android.tools.r8.experimental.graphinfo;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class a extends GraphNode {
    a() {
        super(false);
    }

    @Override // com.android.tools.r8.experimental.graphinfo.GraphNode
    public final boolean equals(Object obj) {
        return obj == this;
    }

    @Override // com.android.tools.r8.experimental.graphinfo.GraphNode
    public final int hashCode() {
        return 0;
    }

    @Override // com.android.tools.r8.experimental.graphinfo.GraphNode
    public final String toString() {
        return "cycle";
    }
}
