package com.android.tools.r8.internal;

import com.reandroid.archive.PathTree;
import java.util.Collection;
import java.util.Iterator;

/* JADX INFO: renamed from: com.android.tools.r8.internal.n, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC2578n implements Collection, InterfaceC2032fv {
    protected AbstractC2578n() {
    }

    public abstract int a();

    @Override // java.util.Collection
    public final boolean add(Object obj) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Collection
    public final boolean addAll(Collection collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Collection
    public final void clear() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Collection, java.util.List
    public boolean contains(Object obj) {
        if (!isEmpty()) {
            Iterator<E> it = iterator();
            while (it.hasNext()) {
                if (AbstractC0936Ds.a(it.next(), obj)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override // java.util.Collection
    public final boolean containsAll(Collection collection) {
        AbstractC0936Ds.c(collection, PathTree.NAME_elements);
        if (!collection.isEmpty()) {
            Iterator it = collection.iterator();
            while (it.hasNext()) {
                if (!contains(it.next())) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override // java.util.Collection, java.util.List
    public boolean isEmpty() {
        return a() == 0;
    }

    @Override // java.util.Collection
    public final boolean remove(Object obj) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Collection
    public final boolean removeAll(Collection collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Collection
    public final boolean retainAll(Collection collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Collection
    public final /* bridge */ int size() {
        return a();
    }

    @Override // java.util.Collection
    public final Object[] toArray() {
        return AbstractC3375xb.a(this);
    }

    public final String toString() {
        return AbstractC0823Ab.a(this, ", ", "[", "]", new C2504m(this), 24);
    }

    @Override // java.util.Collection
    public final Object[] toArray(Object[] objArr) {
        AbstractC0936Ds.c(objArr, "array");
        return AbstractC3375xb.a(this, objArr);
    }
}
