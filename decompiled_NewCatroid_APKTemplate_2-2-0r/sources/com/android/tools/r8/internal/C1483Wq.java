package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Wq, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1483Wq extends AbstractC2113gr implements InterfaceC0993Fr {
    public C1483Wq(C2261ir c2261ir) {
        super(c2261ir);
    }

    @Override // java.util.ListIterator
    public final void add(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.tools.r8.internal.InterfaceC3018sr
    public final int i() {
        return b().a;
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public final Object next() {
        return Integer.valueOf(a().a);
    }

    @Override // java.util.ListIterator, com.android.tools.r8.internal.YJ
    public final Object previous() {
        return Integer.valueOf(b().a);
    }

    @Override // java.util.ListIterator
    public final void set(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.tools.r8.internal.InterfaceC0841Ar
    public final int t() {
        return a().a;
    }
}
