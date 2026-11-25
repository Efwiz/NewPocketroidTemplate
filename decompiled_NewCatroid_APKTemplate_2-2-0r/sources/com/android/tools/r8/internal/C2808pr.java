package com.android.tools.r8.internal;

import java.util.NoSuchElementException;

/* JADX INFO: renamed from: com.android.tools.r8.internal.pr, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2808pr extends K {
    int a = 0;
    final /* synthetic */ C2879qr b;

    C2808pr(C2879qr c2879qr) {
        this.b = c2879qr;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.a < this.b.b;
    }

    @Override // com.android.tools.r8.internal.K, java.util.Iterator
    public final void remove() {
        int iB = C2879qr.b(this.b);
        int i = this.a;
        this.a = i - 1;
        System.arraycopy(this.b.a, this.a + 1, this.b.a, this.a, iB - i);
    }

    @Override // com.android.tools.r8.internal.InterfaceC0841Ar
    public final int t() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        int[] iArr = this.b.a;
        int i = this.a;
        this.a = i + 1;
        return iArr[i];
    }
}
