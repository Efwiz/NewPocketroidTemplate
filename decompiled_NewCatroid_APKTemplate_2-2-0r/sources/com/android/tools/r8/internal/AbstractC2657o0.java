package com.android.tools.r8.internal;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Spliterator;

/* JADX INFO: renamed from: com.android.tools.r8.internal.o0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class AbstractC2657o0 extends AbstractCollection {
    final Object a;
    Collection b;
    final AbstractC2657o0 c;
    final Collection d;
    final /* synthetic */ AbstractC2889r0 e;

    AbstractC2657o0(AbstractC2889r0 abstractC2889r0, Object obj, Collection collection, AbstractC2657o0 abstractC2657o0) {
        this.e = abstractC2889r0;
        this.a = obj;
        this.b = collection;
        this.c = abstractC2657o0;
        this.d = abstractC2657o0 == null ? null : abstractC2657o0.b();
    }

    final void a() {
        AbstractC2657o0 abstractC2657o0 = this.c;
        if (abstractC2657o0 != null) {
            abstractC2657o0.a();
        } else {
            this.e.e.put(this.a, this.b);
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final boolean add(Object obj) {
        c();
        boolean zIsEmpty = this.b.isEmpty();
        boolean zAdd = this.b.add(obj);
        if (zAdd) {
            AbstractC2889r0.b(this.e);
            if (zIsEmpty) {
                a();
            }
        }
        return zAdd;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final boolean addAll(Collection collection) {
        if (collection.isEmpty()) {
            return false;
        }
        int size = size();
        boolean zAddAll = this.b.addAll(collection);
        if (zAddAll) {
            AbstractC2889r0.a(this.e, this.b.size() - size);
            if (size == 0) {
                a();
            }
        }
        return zAddAll;
    }

    final Collection b() {
        return this.b;
    }

    final void c() {
        Collection collection;
        AbstractC2657o0 abstractC2657o0 = this.c;
        if (abstractC2657o0 != null) {
            abstractC2657o0.c();
            if (this.c.b != this.d) {
                throw new ConcurrentModificationException();
            }
        } else {
            if (!this.b.isEmpty() || (collection = (Collection) this.e.e.get(this.a)) == null) {
                return;
            }
            this.b = collection;
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final void clear() {
        int size = size();
        if (size == 0) {
            return;
        }
        this.b.clear();
        AbstractC2889r0.b(this.e, size);
        d();
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final boolean contains(Object obj) {
        c();
        return this.b.contains(obj);
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final boolean containsAll(Collection collection) {
        c();
        return this.b.containsAll(collection);
    }

    final void d() {
        AbstractC2657o0 abstractC2657o0 = this.c;
        if (abstractC2657o0 != null) {
            abstractC2657o0.d();
        } else if (this.b.isEmpty()) {
            this.e.e.remove(this.a);
        }
    }

    @Override // java.util.Collection
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        c();
        return this.b.equals(obj);
    }

    @Override // java.util.Collection
    public final int hashCode() {
        c();
        return this.b.hashCode();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public final Iterator iterator() {
        c();
        return new C2579n0(this);
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final boolean remove(Object obj) {
        c();
        boolean zRemove = this.b.remove(obj);
        if (zRemove) {
            AbstractC2889r0.c(this.e);
            d();
        }
        return zRemove;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final boolean removeAll(Collection collection) {
        if (collection.isEmpty()) {
            return false;
        }
        int size = size();
        boolean zRemoveAll = this.b.removeAll(collection);
        if (zRemoveAll) {
            AbstractC2889r0.a(this.e, this.b.size() - size);
            d();
        }
        return zRemoveAll;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final boolean retainAll(Collection collection) {
        int i = AbstractC3123uM.a;
        collection.getClass();
        int size = size();
        boolean zRetainAll = this.b.retainAll(collection);
        if (zRetainAll) {
            AbstractC2889r0.a(this.e, this.b.size() - size);
            d();
        }
        return zRetainAll;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final int size() {
        c();
        return this.b.size();
    }

    @Override // java.util.Collection, java.lang.Iterable
    public final Spliterator spliterator() {
        c();
        return this.b.spliterator();
    }

    @Override // java.util.AbstractCollection
    public final String toString() {
        c();
        return this.b.toString();
    }
}
