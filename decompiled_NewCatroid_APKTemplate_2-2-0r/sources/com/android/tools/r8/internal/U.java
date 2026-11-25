package com.android.tools.r8.internal;

import java.util.Iterator;
import java.util.NoSuchElementException;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
class U implements Iterator, InterfaceC2032fv {
    private int a;
    final /* synthetic */ X b;

    public U(X x) {
        this.b = x;
    }

    protected final int a() {
        return this.a;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.a < this.b.a();
    }

    @Override // java.util.Iterator
    public final Object next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        X x = this.b;
        int i = this.a;
        this.a = i + 1;
        return x.get(i);
    }

    public int nextIndex() {
        return a();
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    protected final void a(int i) {
        this.a = i;
    }
}
