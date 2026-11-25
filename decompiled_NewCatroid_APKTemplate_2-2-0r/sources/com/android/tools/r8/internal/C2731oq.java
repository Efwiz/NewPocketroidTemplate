package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.oq, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2731oq extends AbstractC2643nq implements InterfaceC2376kK {
    private C2731oq(C2807pq c2807pq) {
        super(c2807pq);
    }

    @Override // java.util.ListIterator
    public final void add(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public final Object next() {
        return a().b;
    }

    @Override // java.util.ListIterator, com.android.tools.r8.internal.YJ
    public final Object previous() {
        return b().b;
    }

    @Override // java.util.ListIterator
    public final void set(Object obj) {
        throw new UnsupportedOperationException();
    }

    /* synthetic */ C2731oq(C2807pq c2807pq, int i) {
        this(c2807pq);
    }
}
