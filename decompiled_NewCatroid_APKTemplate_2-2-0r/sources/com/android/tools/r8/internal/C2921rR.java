package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.rR, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2921rR extends J {
    final /* synthetic */ C3505zR a;

    C2921rR(C3505zR c3505zR) {
        this.a = c3505zR;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final void clear() {
        this.a.clear();
    }

    @Override // com.android.tools.r8.internal.InterfaceC3088tr
    public final boolean f(int i) {
        return this.a.d(i);
    }

    @Override // com.android.tools.r8.internal.J, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.android.tools.r8.internal.InterfaceC3088tr
    public final InterfaceC0841Ar iterator() {
        return new C3435yR(this.a);
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final int size() {
        return this.a.k;
    }
}
