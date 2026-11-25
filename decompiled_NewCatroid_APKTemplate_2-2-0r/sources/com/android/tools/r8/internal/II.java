package com.android.tools.r8.internal;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class II extends NI implements InterfaceC1912eK {
    private LI g;
    final /* synthetic */ PI h;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private II(PI pi) {
        super(pi, 0);
        this.h = pi;
    }

    @Override // java.util.Iterator
    public final Object next() {
        LI li = new LI(this.h, a());
        this.g = li;
        return li;
    }

    @Override // com.android.tools.r8.internal.NI, java.util.Iterator
    public final void remove() {
        super.remove();
        this.g.a = -1;
    }

    /* synthetic */ II(PI pi, int i) {
        this(pi);
    }
}
