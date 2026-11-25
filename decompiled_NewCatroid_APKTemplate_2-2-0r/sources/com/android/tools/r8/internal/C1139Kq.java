package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Kq, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1139Kq extends AbstractC1246Oq implements InterfaceC0841Ar {
    final /* synthetic */ C1303Qq g;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C1139Kq(C1303Qq c1303Qq) {
        super(c1303Qq, 0);
        this.g = c1303Qq;
    }

    @Override // java.util.Iterator
    public final Object next() {
        return Integer.valueOf(this.g.b[a()]);
    }

    @Override // com.android.tools.r8.internal.InterfaceC0841Ar
    public final int t() {
        return this.g.b[a()];
    }
}
