package com.android.tools.r8.internal;

import java.util.NoSuchElementException;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class L extends O {
    static final /* synthetic */ boolean d = true;
    int a;
    int b = -1;
    final /* synthetic */ M c;

    L(M m, int i) {
        this.c = m;
        this.a = i;
    }

    @Override // com.android.tools.r8.internal.O
    public final void a(int i) {
        if (this.b == -1) {
            throw new IllegalStateException();
        }
        M m = this.c;
        int i2 = this.a;
        this.a = i2 + 1;
        m.b(i2, i);
        this.b = -1;
        if (d) {
            return;
        }
        this.c.a();
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
        return this.a < this.c.size();
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
        M m = this.c;
        InterfaceC0967Er interfaceC0967Er = m.a;
        int i = m.b;
        int i2 = this.a - 1;
        this.a = i2;
        this.b = i2;
        return interfaceC0967Er.i(i + i2);
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
        if (d) {
            return;
        }
        this.c.a();
    }

    @Override // com.android.tools.r8.internal.InterfaceC0841Ar
    public final int t() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        M m = this.c;
        InterfaceC0967Er interfaceC0967Er = m.a;
        int i = m.b;
        int i2 = this.a;
        this.a = i2 + 1;
        this.b = i2;
        return interfaceC0967Er.i(i + i2);
    }
}
