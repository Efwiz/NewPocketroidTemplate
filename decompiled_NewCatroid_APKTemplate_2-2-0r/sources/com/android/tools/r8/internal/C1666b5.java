package com.android.tools.r8.internal;

import java.util.NoSuchElementException;

/* JADX INFO: renamed from: com.android.tools.r8.internal.b5, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1666b5 extends AbstractC2194i {
    int a = 0;
    final /* synthetic */ C1740c5 b;

    C1666b5(C1740c5 c1740c5) {
        this.b = c1740c5;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.a < this.b.b;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2282j5
    public final boolean q() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        boolean[] zArr = this.b.a;
        int i = this.a;
        this.a = i + 1;
        return zArr[i];
    }

    @Override // com.android.tools.r8.internal.AbstractC2194i, java.util.Iterator
    public final void remove() {
        int iB = C1740c5.b(this.b);
        int i = this.a;
        this.a = i - 1;
        System.arraycopy(this.b.a, this.a + 1, this.b.a, this.a, iB - i);
    }
}
