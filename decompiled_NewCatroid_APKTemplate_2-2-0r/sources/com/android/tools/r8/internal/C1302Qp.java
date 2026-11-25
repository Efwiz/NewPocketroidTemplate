package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Qp, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1302Qp extends C1245Op implements InterfaceC2376kK {
    private C1302Qp(C1329Rp c1329Rp) {
        super(c1329Rp);
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

    /* synthetic */ C1302Qp(C1329Rp c1329Rp, int i) {
        this(c1329Rp);
    }
}
