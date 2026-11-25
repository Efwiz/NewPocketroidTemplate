package com.android.tools.r8.internal;

import java.util.NoSuchElementException;

/* JADX INFO: renamed from: com.android.tools.r8.internal.nr, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2644nr extends O {
    int a;
    int b = -1;
    final /* synthetic */ C2732or c;

    C2644nr(C2732or c2732or, int i) {
        this.c = c2732or;
        this.a = i;
    }

    @Override // com.android.tools.r8.internal.O
    public final void a(int i) {
        C2732or c2732or = this.c;
        int i2 = this.a;
        this.a = i2 + 1;
        c2732or.b(i2, i);
        this.b = -1;
    }

    @Override // com.android.tools.r8.internal.O
    public final void b(int i) {
        int i2 = this.b;
        if (i2 == -1) {
            throw new IllegalStateException();
        }
        this.c.c(i2, i);
    }

    @Override // java.util.Iterator, java.util.ListIterator
    public final boolean hasNext() {
        return this.a < this.c.b;
    }

    @Override // java.util.ListIterator
    public final boolean hasPrevious() {
        return this.a > 0;
    }

    @Override // com.android.tools.r8.internal.InterfaceC3018sr
    public final int i() {
        if (!hasPrevious()) {
            throw new NoSuchElementException();
        }
        int[] iArr = this.c.a;
        int i = this.a - 1;
        this.a = i;
        this.b = i;
        return iArr[i];
    }

    @Override // java.util.ListIterator
    public final int nextIndex() {
        return this.a;
    }

    @Override // java.util.ListIterator
    public final int previousIndex() {
        return this.a - 1;
    }

    @Override // com.android.tools.r8.internal.K, java.util.Iterator
    public final void remove() {
        int i = this.b;
        if (i == -1) {
            throw new IllegalStateException();
        }
        this.c.h(i);
        int i2 = this.b;
        int i3 = this.a;
        if (i2 < i3) {
            this.a = i3 - 1;
        }
        this.b = -1;
    }

    @Override // com.android.tools.r8.internal.InterfaceC0841Ar
    public final int t() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        int[] iArr = this.c.a;
        int i = this.a;
        this.a = i + 1;
        this.b = i;
        return iArr[i];
    }
}
