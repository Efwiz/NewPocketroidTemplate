package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Do, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C0932Do extends AbstractC0904Co implements InterfaceC0993Fr {
    final /* synthetic */ C0964Eo f;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0932Do(C0964Eo c0964Eo) {
        super(c0964Eo, 0);
        this.f = c0964Eo;
    }

    @Override // java.util.ListIterator
    public final void add(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.tools.r8.internal.InterfaceC3018sr
    public final int i() {
        return this.f.c[d()];
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public final Object next() {
        return Integer.valueOf(this.f.c[b()]);
    }

    @Override // java.util.ListIterator, com.android.tools.r8.internal.YJ
    public final Object previous() {
        return Integer.valueOf(this.f.c[d()]);
    }

    @Override // java.util.ListIterator
    public final void set(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // com.android.tools.r8.internal.InterfaceC0841Ar
    public final int t() {
        return this.f.c[b()];
    }
}
