package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.wI, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3271wI extends BI implements InterfaceC2376kK {
    private C3495zI f;
    final /* synthetic */ DI g;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C3271wI(DI di) {
        super(di, 0);
        this.g = di;
    }

    @Override // java.util.ListIterator
    public final void add(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public final Object next() {
        C3495zI c3495zI = new C3495zI(this.g, b());
        this.f = c3495zI;
        return c3495zI;
    }

    @Override // java.util.ListIterator, com.android.tools.r8.internal.YJ
    public final Object previous() {
        C3495zI c3495zI = new C3495zI(this.g, d());
        this.f = c3495zI;
        return c3495zI;
    }

    @Override // com.android.tools.r8.internal.BI, java.util.ListIterator, java.util.Iterator
    public final void remove() {
        super.remove();
        this.f.a = -1;
    }

    @Override // java.util.ListIterator
    public final void set(Object obj) {
        throw new UnsupportedOperationException();
    }
}
