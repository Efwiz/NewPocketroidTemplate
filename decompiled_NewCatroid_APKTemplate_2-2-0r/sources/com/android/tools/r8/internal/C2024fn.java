package com.android.tools.r8.internal;

import java.util.Iterator;

/* JADX INFO: renamed from: com.android.tools.r8.internal.fn, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2024fn implements Iterator, InterfaceC2032fv {
    private final Iterator a;
    private int b;

    public C2024fn(Iterator it) {
        AbstractC0936Ds.c(it, "iterator");
        this.a = it;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.a.hasNext();
    }

    @Override // java.util.Iterator
    public final Object next() {
        int i = this.b;
        this.b = i + 1;
        if (i >= 0) {
            return new C1865dn(i, this.a.next());
        }
        AbstractC0823Ab.a();
        throw null;
    }

    @Override // java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }
}
