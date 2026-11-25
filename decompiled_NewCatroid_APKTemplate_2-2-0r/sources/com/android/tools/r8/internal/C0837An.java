package com.android.tools.r8.internal;

import java.util.ListIterator;
import java.util.NoSuchElementException;

/* JADX INFO: renamed from: com.android.tools.r8.internal.An, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C0837An implements ListIterator {
    AbstractC3097u a;
    AbstractC3097u b;
    AbstractC3097u c;
    final /* synthetic */ C0863Bn d;

    C0837An(C0863Bn c0863Bn) {
        this.d = c0863Bn;
        if (c0863Bn.size() < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (c0863Bn.size() == 0) {
            this.a = null;
            this.b = c0863Bn.b();
        } else {
            AbstractC3097u abstractC3097uA = c0863Bn.a();
            this.a = abstractC3097uA;
            this.b = abstractC3097uA.d;
        }
    }

    @Override // java.util.ListIterator
    public final void add(Object obj) {
        AbstractC3097u abstractC3097u = this.a;
        if (abstractC3097u != null) {
            this.d.b(abstractC3097u, (AbstractC3097u) obj);
        } else {
            AbstractC3097u abstractC3097u2 = this.b;
            if (abstractC3097u2 != null) {
                this.d.a(abstractC3097u2, (AbstractC3097u) obj);
            } else {
                this.d.a((AbstractC3097u) obj);
            }
        }
        this.b = (AbstractC3097u) obj;
        this.c = null;
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public final boolean hasNext() {
        return this.a != null;
    }

    @Override // java.util.ListIterator
    public final boolean hasPrevious() {
        return this.b != null;
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public final Object next() {
        AbstractC3097u abstractC3097u = this.a;
        if (abstractC3097u == null) {
            throw new NoSuchElementException();
        }
        this.b = abstractC3097u;
        this.a = abstractC3097u.e;
        this.c = abstractC3097u;
        return abstractC3097u;
    }

    @Override // java.util.ListIterator
    public final int nextIndex() {
        if (this.a == null) {
            return this.d.size();
        }
        C0863Bn c0863Bn = this.d;
        if (c0863Bn.d == null) {
            c0863Bn.d = c0863Bn.e();
        }
        return this.a.f;
    }

    @Override // java.util.ListIterator
    public final Object previous() {
        AbstractC3097u abstractC3097u = this.b;
        if (abstractC3097u == null) {
            throw new NoSuchElementException();
        }
        this.a = abstractC3097u;
        this.b = abstractC3097u.d;
        this.c = abstractC3097u;
        return abstractC3097u;
    }

    @Override // java.util.ListIterator
    public final int previousIndex() {
        if (this.b == null) {
            return -1;
        }
        C0863Bn c0863Bn = this.d;
        if (c0863Bn.d == null) {
            c0863Bn.d = c0863Bn.e();
        }
        return this.b.f;
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public final void remove() {
        AbstractC3097u abstractC3097u = this.c;
        if (abstractC3097u == null) {
            throw new IllegalStateException();
        }
        AbstractC3097u abstractC3097u2 = this.a;
        if (abstractC3097u == abstractC3097u2) {
            this.a = abstractC3097u2.e;
        } else {
            this.b = this.b.d;
        }
        this.d.c(abstractC3097u);
        this.c = null;
    }

    @Override // java.util.ListIterator
    public final void set(Object obj) {
        AbstractC3097u abstractC3097u = this.c;
        if (abstractC3097u == null) {
            throw new IllegalStateException();
        }
        AbstractC3097u abstractC3097u2 = (AbstractC3097u) obj;
        this.d.c(abstractC3097u, abstractC3097u2);
        if (this.c == this.b) {
            this.b = abstractC3097u2;
        } else {
            this.a = abstractC3097u2;
        }
    }
}
