package com.android.tools.r8.internal;

import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;

/* JADX INFO: renamed from: com.android.tools.r8.internal.n0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
class C2579n0 implements Iterator {
    final Iterator a;
    final Collection b;
    final /* synthetic */ AbstractC2657o0 c;

    C2579n0(AbstractC2657o0 abstractC2657o0) {
        this.c = abstractC2657o0;
        Collection collection = abstractC2657o0.b;
        this.b = collection;
        this.a = AbstractC2889r0.a(collection);
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        this.c.c();
        if (this.c.b == this.b) {
            return this.a.hasNext();
        }
        throw new ConcurrentModificationException();
    }

    @Override // java.util.Iterator
    public final Object next() {
        this.c.c();
        if (this.c.b == this.b) {
            return this.a.next();
        }
        throw new ConcurrentModificationException();
    }

    @Override // java.util.Iterator
    public final void remove() {
        this.a.remove();
        AbstractC2889r0.c(this.c.e);
        this.c.d();
    }

    C2579n0(AbstractC2657o0 abstractC2657o0, ListIterator listIterator) {
        this.c = abstractC2657o0;
        this.b = abstractC2657o0.b;
        this.a = listIterator;
    }
}
