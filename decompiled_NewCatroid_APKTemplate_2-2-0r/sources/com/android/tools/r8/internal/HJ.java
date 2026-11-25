package com.android.tools.r8.internal;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class HJ extends MJ implements InterfaceC2376kK {
    private KJ f;
    final /* synthetic */ OJ g;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public HJ(OJ oj) {
        super(oj, 0);
        this.g = oj;
    }

    @Override // java.util.ListIterator
    public final void add(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public final Object next() {
        KJ kj = new KJ(this.g, b());
        this.f = kj;
        return kj;
    }

    @Override // java.util.ListIterator, com.android.tools.r8.internal.YJ
    public final Object previous() {
        KJ kj = new KJ(this.g, d());
        this.f = kj;
        return kj;
    }

    @Override // com.android.tools.r8.internal.MJ, java.util.ListIterator, java.util.Iterator
    public final void remove() {
        super.remove();
        this.f.a = -1;
    }

    @Override // java.util.ListIterator
    public final void set(Object obj) {
        throw new UnsupportedOperationException();
    }
}
