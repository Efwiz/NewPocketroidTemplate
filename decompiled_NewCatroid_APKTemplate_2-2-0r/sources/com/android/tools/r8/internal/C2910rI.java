package com.android.tools.r8.internal;

import java.util.NoSuchElementException;

/* JADX INFO: renamed from: com.android.tools.r8.internal.rI, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2910rI extends V0 {
    int a = -1;
    int b = 0;
    final /* synthetic */ C2981sI c;

    C2910rI(C2981sI c2981sI) {
        this.c = c2981sI;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.b < this.c.a.d;
    }

    @Override // java.util.Iterator
    public final Object next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        Object[] objArr = this.c.a.b;
        int i = this.b;
        this.a = i;
        Object obj = objArr[i];
        int[] iArr = this.c.a.c;
        int i2 = this.b;
        this.b = i2 + 1;
        return new K0(iArr[i2], obj);
    }

    @Override // com.android.tools.r8.internal.V0, java.util.Iterator, java.util.ListIterator
    public final void remove() {
        if (this.a == -1) {
            throw new IllegalStateException();
        }
        this.a = -1;
        int iB = C3050tI.b(this.c.a);
        int i = this.b;
        this.b = i - 1;
        int i2 = iB - i;
        System.arraycopy(this.c.a.b, this.b + 1, this.c.a.b, this.b, i2);
        System.arraycopy(this.c.a.c, this.b + 1, this.c.a.c, this.b, i2);
        this.c.a.b[this.c.a.d] = null;
    }
}
