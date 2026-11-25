package com.android.tools.r8.internal;

import java.util.ListIterator;
import java.util.NoSuchElementException;

/* JADX INFO: renamed from: com.android.tools.r8.internal.nC, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2596nC implements ListIterator {
    boolean a;
    final /* synthetic */ ListIterator b;
    final /* synthetic */ C2686oC c;

    C2596nC(C2686oC c2686oC, ListIterator listIterator) {
        this.c = c2686oC;
        this.b = listIterator;
    }

    @Override // java.util.ListIterator
    public final void add(Object obj) {
        this.b.add(obj);
        this.b.previous();
        this.a = false;
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public final boolean hasNext() {
        return this.b.hasPrevious();
    }

    @Override // java.util.ListIterator
    public final boolean hasPrevious() {
        return this.b.hasNext();
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public final Object next() {
        if (!this.b.hasPrevious()) {
            throw new NoSuchElementException();
        }
        this.a = true;
        return this.b.previous();
    }

    @Override // java.util.ListIterator
    public final int nextIndex() {
        return C2686oC.a(this.c, this.b.nextIndex());
    }

    @Override // java.util.ListIterator
    public final Object previous() {
        if (!this.b.hasNext()) {
            throw new NoSuchElementException();
        }
        this.a = true;
        return this.b.next();
    }

    @Override // java.util.ListIterator
    public final int previousIndex() {
        return nextIndex() - 1;
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public final void remove() {
        AbstractC3123uM.b("no calls to next() since the last call to remove()", this.a);
        this.b.remove();
        this.a = false;
    }

    @Override // java.util.ListIterator
    public final void set(Object obj) {
        boolean z = this.a;
        int i = AbstractC3123uM.a;
        if (!z) {
            throw new IllegalStateException();
        }
        this.b.set(obj);
    }
}
