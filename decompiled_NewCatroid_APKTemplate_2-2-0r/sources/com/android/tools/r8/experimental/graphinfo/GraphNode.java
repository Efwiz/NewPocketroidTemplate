package com.android.tools.r8.experimental.graphinfo;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class GraphNode {
    private static final GraphNode b = new a();
    private final boolean a;

    public GraphNode(boolean z) {
        this.a = z;
    }

    public static GraphNode cycle() {
        return b;
    }

    public abstract boolean equals(Object obj);

    public abstract int hashCode();

    public final boolean isCycle() {
        return this == cycle();
    }

    public boolean isLibraryNode() {
        return this.a;
    }

    public abstract String toString();
}
