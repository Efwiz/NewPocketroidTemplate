package org.jf.util.collection;

import java.util.Collection;
import java.util.Iterator;

/* JADX INFO: loaded from: classes6.dex */
public class EmptySet<T> extends ArraySet<T> implements Empty {
    public static final EmptySet<?> INS = new EmptySet<>();

    @Override // org.jf.util.collection.ArraySet, java.util.Set, java.util.Collection
    public void clear() {
    }

    @Override // org.jf.util.collection.ArraySet, java.util.Set, java.util.Collection
    public boolean contains(Object obj) {
        return false;
    }

    @Override // org.jf.util.collection.ArraySet, java.util.Set, java.util.Collection
    public boolean containsAll(Collection<?> collection) {
        return false;
    }

    @Override // org.jf.util.collection.ArraySet, java.util.Set, java.util.Collection
    public boolean isEmpty() {
        return true;
    }

    @Override // org.jf.util.collection.ArraySet, java.util.Set, java.util.Collection
    public boolean remove(Object obj) {
        return false;
    }

    @Override // org.jf.util.collection.ArraySet, java.util.Set, java.util.Collection
    public boolean removeAll(Collection<?> collection) {
        return false;
    }

    @Override // org.jf.util.collection.ArraySet, java.util.Set, java.util.Collection
    public int size() {
        return 0;
    }

    @Override // org.jf.util.collection.ArraySet, java.util.Set, java.util.Collection
    public <T1> T1[] toArray(T1[] t1Arr) {
        return t1Arr;
    }

    @Override // org.jf.util.collection.ArraySet, java.util.Set, java.util.Collection, java.lang.Iterable
    public Iterator<T> iterator() {
        return EmptyIterator.of();
    }

    @Override // org.jf.util.collection.ArraySet, java.util.Set, java.util.Collection
    public Object[] toArray() {
        return new Object[0];
    }

    @Override // org.jf.util.collection.ArraySet, java.util.Set, java.util.Collection
    public boolean add(T t) {
        throw new IllegalArgumentException("Empty set");
    }

    @Override // org.jf.util.collection.ArraySet, java.util.Set, java.util.Collection
    public boolean addAll(Collection<? extends T> collection) {
        throw new IllegalArgumentException("Empty set");
    }

    @Override // org.jf.util.collection.ArraySet, java.util.Set, java.util.Collection
    public boolean retainAll(Collection<?> collection) {
        throw new IllegalArgumentException("Empty set");
    }

    public static <T1> EmptySet<T1> of() {
        return (EmptySet<T1>) INS;
    }
}
