package com.android.tools.r8.experimental.graphinfo;

import com.android.tools.r8.references.FieldReference;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class FieldGraphNode extends GraphNode {
    static final /* synthetic */ boolean d = true;
    private final FieldReference c;

    public FieldGraphNode(boolean z, FieldReference fieldReference) {
        super(z);
        if (!d && fieldReference == null) {
            throw new AssertionError();
        }
        this.c = fieldReference;
    }

    @Override // com.android.tools.r8.experimental.graphinfo.GraphNode
    public boolean equals(Object obj) {
        return this == obj || ((obj instanceof FieldGraphNode) && ((FieldGraphNode) obj).c.equals(this.c));
    }

    public FieldReference getReference() {
        return this.c;
    }

    @Override // com.android.tools.r8.experimental.graphinfo.GraphNode
    public int hashCode() {
        return this.c.hashCode();
    }

    @Override // com.android.tools.r8.experimental.graphinfo.GraphNode
    public String toString() {
        return this.c.toString();
    }
}
