package com.android.tools.r8.internal;

import java.util.NoSuchElementException;

/* JADX INFO: renamed from: com.android.tools.r8.internal.s1, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2962s1 extends Z0 {
    static final /* synthetic */ boolean d = true;
    int a;
    int b = -1;
    final /* synthetic */ C3031t1 c;

    C2962s1(C3031t1 c3031t1, int i) {
        this.c = c3031t1;
        this.a = i;
    }

    @Override // com.android.tools.r8.internal.Z0, java.util.ListIterator
    public final void add(Object obj) {
        if (this.b == -1) {
            throw new IllegalStateException();
        }
        C3031t1 c3031t1 = this.c;
        int i = this.a;
        this.a = i + 1;
        c3031t1.add(i, obj);
        this.b = -1;
        if (d) {
            return;
        }
        this.c.a();
    }

    @Override // java.util.Iterator, java.util.ListIterator
    public final boolean hasNext() {
        return this.a < this.c.size();
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
        C3031t1 c3031t1 = this.c;
        InterfaceC2991sS interfaceC2991sS = c3031t1.a;
        int i = c3031t1.b;
        int i2 = this.a;
        this.a = i2 + 1;
        this.b = i2;
        return interfaceC2991sS.get(i + i2);
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
        C3031t1 c3031t1 = this.c;
        InterfaceC2991sS interfaceC2991sS = c3031t1.a;
        int i = c3031t1.b;
        int i2 = this.a - 1;
        this.a = i2;
        this.b = i2;
        return interfaceC2991sS.get(i + i2);
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
        if (d) {
            return;
        }
        this.c.a();
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
