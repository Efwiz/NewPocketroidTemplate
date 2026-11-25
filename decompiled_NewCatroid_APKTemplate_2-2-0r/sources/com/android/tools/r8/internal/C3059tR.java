package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.tR, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3059tR extends AbstractC3357xR implements InterfaceC2376kK {
    final /* synthetic */ C3505zR f;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C3059tR(C3505zR c3505zR) {
        super(c3505zR, 0);
        this.f = c3505zR;
    }

    @Override // java.util.ListIterator
    public final void add(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public final Object next() {
        return this.f.b[b()];
    }

    @Override // java.util.ListIterator, com.android.tools.r8.internal.YJ
    public final Object previous() {
        return this.f.b[d()];
    }

    @Override // java.util.ListIterator
    public final void set(Object obj) {
        throw new UnsupportedOperationException();
    }
}
