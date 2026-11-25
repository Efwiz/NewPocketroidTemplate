package com.android.tools.r8.internal;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class VI extends ZI implements InterfaceC1912eK {
    final /* synthetic */ C1684bJ g;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VI(C1684bJ c1684bJ) {
        super(c1684bJ, 0);
        this.g = c1684bJ;
    }

    @Override // java.util.Iterator
    public final Object next() {
        return this.g.a[a()];
    }
}
