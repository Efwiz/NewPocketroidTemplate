package com.android.tools.r8.graph;

/* JADX INFO: renamed from: com.android.tools.r8.graph.i2, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC0676i2 extends AbstractC0594a1 {
    public final AbstractC0642e2 a;
    public final AbstractC0594a1 b;

    public AbstractC0676i2(AbstractC0642e2 abstractC0642e2, AbstractC0594a1 abstractC0594a1) {
        this.a = abstractC0642e2;
        this.b = abstractC0594a1;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.tools.r8.graph.AbstractC0594a1
    public final void a(com.android.tools.r8.dex.V v) {
        this.b.a(v);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AbstractC0676i2)) {
            return false;
        }
        AbstractC0676i2 abstractC0676i2 = (AbstractC0676i2) obj;
        return this.a.equals(abstractC0676i2.a) && this.b.equals(abstractC0676i2.b);
    }

    public final int hashCode() {
        return this.b.hashCode() + (this.a.hashCode() * 7);
    }
}
