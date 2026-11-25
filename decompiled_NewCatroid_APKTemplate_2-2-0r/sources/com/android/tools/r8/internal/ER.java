package com.android.tools.r8.internal;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class ER extends JR implements InterfaceC1912eK {
    private HR g;
    final /* synthetic */ MR h;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private ER(MR mr) {
        super(mr, 0);
        this.h = mr;
    }

    @Override // java.util.Iterator
    public final Object next() {
        HR hr = new HR(this.h, a());
        this.g = hr;
        return hr;
    }

    @Override // com.android.tools.r8.internal.JR, java.util.Iterator
    public final void remove() {
        super.remove();
        this.g.a = -1;
    }

    /* synthetic */ ER(MR mr, int i) {
        this(mr);
    }
}
