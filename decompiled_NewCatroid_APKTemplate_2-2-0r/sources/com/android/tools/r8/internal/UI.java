package com.android.tools.r8.internal;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class UI extends ZI implements InterfaceC1912eK {
    private XI g;
    final /* synthetic */ C1684bJ h;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private UI(C1684bJ c1684bJ) {
        super(c1684bJ, 0);
        this.h = c1684bJ;
    }

    @Override // java.util.Iterator
    public final Object next() {
        XI xi = new XI(this.h, a());
        this.g = xi;
        return xi;
    }

    @Override // com.android.tools.r8.internal.ZI, java.util.Iterator
    public final void remove() {
        super.remove();
        this.g.a = -1;
    }

    /* synthetic */ UI(C1684bJ c1684bJ, int i) {
        this(c1684bJ);
    }
}
