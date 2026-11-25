package com.android.tools.r8.internal;

import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Pl, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC1271Pl extends AbstractCollection implements Serializable {
    private static final Object[] a = new Object[0];

    AbstractC1271Pl() {
    }

    public AbstractC1639am a() {
        int size = size();
        if (size == 0) {
            return MS.d;
        }
        if (size == 1) {
            return new C3509zV(iterator().next());
        }
        Object[] array = toArray(a);
        return new HS(this, AbstractC1639am.b(array.length, array));
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final boolean add(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final boolean addAll(Collection collection) {
        throw new UnsupportedOperationException();
    }

    Object[] b() {
        return null;
    }

    int c() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final void clear() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public abstract boolean contains(Object obj);

    int d() {
        throw new UnsupportedOperationException();
    }

    abstract boolean e();

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
    /* JADX INFO: renamed from: f, reason: merged with bridge method [inline-methods] */
    public abstract AbstractC2963s10 iterator();

    @Override // java.util.AbstractCollection, java.util.Collection
    public final boolean remove(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final boolean removeAll(Collection collection) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Collection
    public final boolean removeIf(Predicate predicate) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final boolean retainAll(Collection collection) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Collection, java.lang.Iterable, java.util.Set
    public Spliterator spliterator() {
        return Spliterators.spliterator(this, 1296);
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final Object[] toArray() {
        return toArray(a);
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final Object[] toArray(Object[] objArr) {
        int i = AbstractC3123uM.a;
        objArr.getClass();
        int size = size();
        if (objArr.length < size) {
            Object[] objArrB = b();
            if (objArrB != null) {
                return TL.a(d(), c(), objArrB, objArr);
            }
            objArr = XJ.b(size, objArr);
        } else if (objArr.length > size) {
            objArr[size] = null;
        }
        a(0, objArr);
        return objArr;
    }

    int a(int i, Object[] objArr) {
        AbstractC2963s10 it = iterator();
        while (it.hasNext()) {
            objArr[i] = it.next();
            i++;
        }
        return i;
    }
}
