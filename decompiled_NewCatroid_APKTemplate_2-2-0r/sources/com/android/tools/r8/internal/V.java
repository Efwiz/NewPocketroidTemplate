package com.android.tools.r8.internal;

import java.util.ListIterator;
import java.util.NoSuchElementException;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class V extends U implements ListIterator {
    final /* synthetic */ X c;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public V(X x, int i) {
        super(x);
        this.c = x;
        T.a(i, x.size());
        a(i);
    }

    @Override // java.util.ListIterator
    public final void add(Object obj) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.ListIterator
    public final boolean hasPrevious() {
        return a() > 0;
    }

    @Override // java.util.ListIterator
    public final Object previous() {
        if (!hasPrevious()) {
            throw new NoSuchElementException();
        }
        X x = this.c;
        a(a() - 1);
        return x.get(a());
    }

    @Override // java.util.ListIterator
    public final int previousIndex() {
        return a() - 1;
    }

    @Override // java.util.ListIterator
    public final void set(Object obj) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }
}
