package com.android.tools.r8.internal;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class KC extends OC implements XC {
    final /* synthetic */ QC g;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KC(QC qc) {
        super(qc, 0);
        this.g = qc;
    }

    @Override // com.android.tools.r8.internal.XC
    public final long c() {
        return this.g.a[a()];
    }

    @Override // java.util.Iterator
    public final Object next() {
        return Long.valueOf(this.g.a[a()]);
    }
}
