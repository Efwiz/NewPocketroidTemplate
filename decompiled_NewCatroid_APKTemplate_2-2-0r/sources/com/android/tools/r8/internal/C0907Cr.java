package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Cr, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0907Cr extends K {
    protected final InterfaceC0841Ar a;

    public C0907Cr(InterfaceC0841Ar interfaceC0841Ar) {
        this.a = interfaceC0841Ar;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.a.hasNext();
    }

    @Override // com.android.tools.r8.internal.InterfaceC0841Ar
    public final int t() {
        return this.a.t();
    }
}
