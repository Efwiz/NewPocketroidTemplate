package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Lq, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1165Lq extends P {
    final /* synthetic */ C1303Qq a;

    private C1165Lq(C1303Qq c1303Qq) {
        this.a = c1303Qq;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final void clear() {
        this.a.clear();
    }

    @Override // com.android.tools.r8.internal.InterfaceC3088tr
    public final boolean f(int i) {
        return this.a.a(i);
    }

    @Override // com.android.tools.r8.internal.J, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.android.tools.r8.internal.InterfaceC3088tr
    public final InterfaceC0841Ar iterator() {
        return new C1139Kq(this.a);
    }

    @Override // com.android.tools.r8.internal.P
    public final boolean k(int i) {
        C1303Qq c1303Qq = this.a;
        int i2 = c1303Qq.h;
        c1303Qq.remove(i);
        return this.a.h != i2;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        return this.a.h;
    }

    /* synthetic */ C1165Lq(C1303Qq c1303Qq, int i) {
        this(c1303Qq);
    }
}
