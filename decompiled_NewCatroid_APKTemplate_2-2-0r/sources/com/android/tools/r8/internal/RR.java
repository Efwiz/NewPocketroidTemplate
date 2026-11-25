package com.android.tools.r8.internal;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class RR extends WR implements InterfaceC1912eK {
    private UR g;
    final /* synthetic */ YR h;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private RR(YR yr) {
        super(yr, 0);
        this.h = yr;
    }

    @Override // java.util.Iterator
    public final Object next() {
        UR ur = new UR(this.h, a());
        this.g = ur;
        return ur;
    }

    @Override // com.android.tools.r8.internal.WR, java.util.Iterator
    public final void remove() {
        super.remove();
        this.g.a = -1;
    }

    /* synthetic */ RR(YR yr, int i) {
        this(yr);
    }
}
