package com.android.tools.r8.internal;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class OI extends NI implements InterfaceC0841Ar {
    final /* synthetic */ PI g;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public OI(PI pi) {
        super(pi, 0);
        this.g = pi;
    }

    @Override // java.util.Iterator
    public final Object next() {
        return Integer.valueOf(this.g.c[a()]);
    }

    @Override // com.android.tools.r8.internal.InterfaceC0841Ar
    public final int t() {
        return this.g.c[a()];
    }
}
