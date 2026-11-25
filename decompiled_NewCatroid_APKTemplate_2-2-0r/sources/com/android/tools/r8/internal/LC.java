package com.android.tools.r8.internal;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class LC extends AbstractC1963f0 {
    final /* synthetic */ QC a;

    private LC(QC qc) {
        this.a = qc;
    }

    @Override // com.android.tools.r8.internal.AbstractC1576a0
    /* JADX INFO: renamed from: a */
    public final XC iterator() {
        return new KC(this.a);
    }

    @Override // com.android.tools.r8.internal.AbstractC1576a0
    public final boolean b(long j) {
        return this.a.b(j);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final void clear() {
        this.a.clear();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        return this.a.g;
    }

    /* synthetic */ LC(QC qc, int i) {
        this(qc);
    }
}
