package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.dJ, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1832dJ extends AbstractC2223iJ implements InterfaceC2376kK {
    private C2064gJ f;
    final /* synthetic */ C2375kJ g;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C1832dJ(C2375kJ c2375kJ) {
        super(c2375kJ, 0);
        this.g = c2375kJ;
    }

    @Override // java.util.ListIterator
    public final void add(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public final Object next() {
        C2064gJ c2064gJ = new C2064gJ(this.g, b());
        this.f = c2064gJ;
        return c2064gJ;
    }

    @Override // java.util.ListIterator, com.android.tools.r8.internal.YJ
    public final Object previous() {
        C2064gJ c2064gJ = new C2064gJ(this.g, d());
        this.f = c2064gJ;
        return c2064gJ;
    }

    @Override // com.android.tools.r8.internal.AbstractC2223iJ, java.util.ListIterator, java.util.Iterator
    public final void remove() {
        super.remove();
        this.f.a = -1;
    }

    @Override // java.util.ListIterator
    public final void set(Object obj) {
        throw new UnsupportedOperationException();
    }
}
