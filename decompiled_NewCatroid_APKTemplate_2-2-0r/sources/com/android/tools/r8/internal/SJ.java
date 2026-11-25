package com.android.tools.r8.internal;

import java.util.NoSuchElementException;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class SJ extends Z0 {
    int a;
    int b = -1;
    final /* synthetic */ TJ c;

    SJ(TJ tj, int i) {
        this.c = tj;
        this.a = i;
    }

    @Override // com.android.tools.r8.internal.Z0, java.util.ListIterator
    public final void add(Object obj) {
        TJ tj = this.c;
        int i = this.a;
        this.a = i + 1;
        tj.add(i, obj);
        this.b = -1;
    }

    @Override // java.util.Iterator, java.util.ListIterator
    public final boolean hasNext() {
        return this.a < this.c.b;
    }

    @Override // java.util.ListIterator
    public final boolean hasPrevious() {
        return this.a > 0;
    }

    @Override // java.util.Iterator, java.util.ListIterator
    public final Object next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        Object[] objArr = this.c.a;
        int i = this.a;
        this.a = i + 1;
        this.b = i;
        return objArr[i];
    }

    @Override // java.util.ListIterator
    public final int nextIndex() {
        return this.a;
    }

    @Override // java.util.ListIterator, com.android.tools.r8.internal.YJ
    public final Object previous() {
        if (!hasPrevious()) {
            throw new NoSuchElementException();
        }
        Object[] objArr = this.c.a;
        int i = this.a - 1;
        this.a = i;
        this.b = i;
        return objArr[i];
    }

    @Override // java.util.ListIterator
    public final int previousIndex() {
        return this.a - 1;
    }

    @Override // com.android.tools.r8.internal.V0, java.util.Iterator, java.util.ListIterator
    public final void remove() {
        int i = this.b;
        if (i == -1) {
            throw new IllegalStateException();
        }
        this.c.remove(i);
        int i2 = this.b;
        int i3 = this.a;
        if (i2 < i3) {
            this.a = i3 - 1;
        }
        this.b = -1;
    }

    @Override // com.android.tools.r8.internal.Z0, java.util.ListIterator
    public final void set(Object obj) {
        int i = this.b;
        if (i == -1) {
            throw new IllegalStateException();
        }
        this.c.set(i, obj);
    }
}
