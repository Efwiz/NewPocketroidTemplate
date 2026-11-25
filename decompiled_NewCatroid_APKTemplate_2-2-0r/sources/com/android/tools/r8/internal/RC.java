package com.android.tools.r8.internal;

import java.util.NoSuchElementException;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class RC extends AbstractC1880e0 {
    int a;
    int b = -1;
    final /* synthetic */ SC c;

    RC(SC sc, int i) {
        this.c = sc;
        this.a = i;
    }

    @Override // com.android.tools.r8.internal.AbstractC1880e0
    public final void a(long j) {
        SC sc = this.c;
        int i = this.a;
        this.a = i + 1;
        sc.a(i, j);
        this.b = -1;
    }

    @Override // com.android.tools.r8.internal.AbstractC1880e0
    public final void b(long j) {
        int i = this.b;
        if (i == -1) {
            throw new IllegalStateException();
        }
        this.c.b(i, j);
    }

    @Override // com.android.tools.r8.internal.XC
    public final long c() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        long[] jArr = this.c.a;
        int i = this.a;
        this.a = i + 1;
        this.b = i;
        return jArr[i];
    }

    @Override // com.android.tools.r8.internal.Z, com.android.tools.r8.internal.TC
    public final long e() {
        if (!hasPrevious()) {
            throw new NoSuchElementException();
        }
        long[] jArr = this.c.a;
        int i = this.a - 1;
        this.a = i;
        this.b = i;
        return jArr[i];
    }

    @Override // java.util.Iterator, java.util.ListIterator
    public final boolean hasNext() {
        return this.a < this.c.b;
    }

    @Override // java.util.ListIterator
    public final boolean hasPrevious() {
        return this.a > 0;
    }

    @Override // java.util.ListIterator
    public final int nextIndex() {
        return this.a;
    }

    @Override // java.util.ListIterator
    public final int previousIndex() {
        return this.a - 1;
    }

    @Override // java.util.Iterator, java.util.ListIterator
    public final void remove() {
        int i = this.b;
        if (i == -1) {
            throw new IllegalStateException();
        }
        this.c.e(i);
        int i2 = this.b;
        int i3 = this.a;
        if (i2 < i3) {
            this.a = i3 - 1;
        }
        this.b = -1;
    }
}
