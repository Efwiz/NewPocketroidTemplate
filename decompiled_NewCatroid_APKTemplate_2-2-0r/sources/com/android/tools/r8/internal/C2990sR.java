package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.sR, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2990sR extends AbstractC3357xR implements InterfaceC2376kK {
    private C3210vR f;
    final /* synthetic */ C3505zR g;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C2990sR(C3505zR c3505zR) {
        super(c3505zR, 0);
        this.g = c3505zR;
    }

    @Override // java.util.ListIterator
    public final void add(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public final Object next() {
        C3210vR c3210vR = new C3210vR(this.g, b());
        this.f = c3210vR;
        return c3210vR;
    }

    @Override // java.util.ListIterator, com.android.tools.r8.internal.YJ
    public final Object previous() {
        C3210vR c3210vR = new C3210vR(this.g, d());
        this.f = c3210vR;
        return c3210vR;
    }

    @Override // com.android.tools.r8.internal.AbstractC3357xR, java.util.ListIterator, java.util.Iterator
    public final void remove() {
        super.remove();
        this.f.a = -1;
    }

    @Override // java.util.ListIterator
    public final void set(Object obj) {
        throw new UnsupportedOperationException();
    }
}
