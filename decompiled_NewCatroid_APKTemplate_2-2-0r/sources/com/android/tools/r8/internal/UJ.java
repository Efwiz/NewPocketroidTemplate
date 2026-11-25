package com.android.tools.r8.internal;

import java.util.NoSuchElementException;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class UJ extends V0 {
    int a = 0;
    final /* synthetic */ VJ b;

    UJ(VJ vj) {
        this.b = vj;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.a < this.b.b;
    }

    @Override // java.util.Iterator
    public final Object next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        Object[] objArr = this.b.a;
        int i = this.a;
        this.a = i + 1;
        return objArr[i];
    }

    @Override // com.android.tools.r8.internal.V0, java.util.Iterator, java.util.ListIterator
    public final void remove() {
        int iB = VJ.b(this.b);
        int i = this.a;
        this.a = i - 1;
        System.arraycopy(this.b.a, this.a + 1, this.b.a, this.a, iB - i);
        this.b.a[this.b.b] = null;
    }
}
