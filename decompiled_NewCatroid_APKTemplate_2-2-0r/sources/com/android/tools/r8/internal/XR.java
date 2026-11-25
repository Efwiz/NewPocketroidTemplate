package com.android.tools.r8.internal;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class XR extends WR implements XC {
    final /* synthetic */ YR g;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public XR(YR yr) {
        super(yr, 0);
        this.g = yr;
    }

    @Override // com.android.tools.r8.internal.XC
    public final long c() {
        return this.g.b[a()];
    }

    @Override // java.util.Iterator
    public final Object next() {
        return Long.valueOf(this.g.b[a()]);
    }
}
