package com.android.tools.r8.internal;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/* JADX INFO: renamed from: com.android.tools.r8.internal.pk, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class AbstractC2801pk implements Iterator {
    C2328jk a;
    C2328jk b = null;
    int c;
    int d;
    final /* synthetic */ C3011sk e;

    AbstractC2801pk(C3011sk c3011sk) {
        this.e = c3011sk;
        this.a = c3011sk.c;
        this.c = c3011sk.g;
        this.d = c3011sk.size();
    }

    abstract Object a(C2328jk c2328jk);

    @Override // java.util.Iterator
    public final boolean hasNext() {
        if (this.e.g == this.c) {
            return this.a != null && this.d > 0;
        }
        throw new ConcurrentModificationException();
    }

    @Override // java.util.Iterator
    public final Object next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        C2328jk c2328jk = this.a;
        this.a = c2328jk.g;
        this.b = c2328jk;
        this.d--;
        return a(c2328jk);
    }

    @Override // java.util.Iterator
    public final void remove() {
        if (this.e.g != this.c) {
            throw new ConcurrentModificationException();
        }
        AbstractC3123uM.b("no calls to next() since the last call to remove()", this.b != null);
        this.e.a(this.b);
        this.c = this.e.g;
        this.b = null;
    }
}
