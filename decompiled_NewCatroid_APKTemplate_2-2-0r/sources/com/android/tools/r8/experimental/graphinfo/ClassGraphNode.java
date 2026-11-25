package com.android.tools.r8.experimental.graphinfo;

import com.android.tools.r8.references.ClassReference;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class ClassGraphNode extends GraphNode {
    static final /* synthetic */ boolean d = true;
    private final ClassReference c;

    public ClassGraphNode(boolean z, ClassReference classReference) {
        super(z);
        if (!d && classReference == null) {
            throw new AssertionError();
        }
        this.c = classReference;
    }

    @Override // com.android.tools.r8.experimental.graphinfo.GraphNode
    public boolean equals(Object obj) {
        return this == obj || ((obj instanceof ClassGraphNode) && ((ClassGraphNode) obj).c.equals(this.c));
    }

    public ClassReference getReference() {
        return this.c;
    }

    @Override // com.android.tools.r8.experimental.graphinfo.GraphNode
    public int hashCode() {
        return this.c.hashCode();
    }

    @Override // com.android.tools.r8.experimental.graphinfo.GraphNode
    public String toString() {
        return this.c.getDescriptor();
    }
}
