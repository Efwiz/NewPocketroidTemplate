package org.jf.util.collection;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/* JADX INFO: loaded from: classes6.dex */
public class EmptyList<T> implements List<T>, Empty {
    public static final EmptyList<?> INS = new EmptyList<>();

    @Override // java.util.List, java.util.Collection
    public void clear() {
    }

    @Override // java.util.List, java.util.Collection
    public boolean contains(Object obj) {
        return false;
    }

    @Override // java.util.List, java.util.Collection
    public boolean containsAll(Collection<?> collection) {
        return false;
    }

    @Override // java.util.List
    public int indexOf(Object obj) {
        return -1;
    }

    @Override // java.util.List, java.util.Collection
    public boolean isEmpty() {
        return true;
    }

    @Override // java.util.List
    public int lastIndexOf(Object obj) {
        return -1;
    }

    @Override // java.util.List, java.util.Collection
    public boolean remove(Object obj) {
        return false;
    }

    @Override // java.util.List, java.util.Collection
    public boolean removeAll(Collection<?> collection) {
        return false;
    }

    @Override // java.util.List, java.util.Collection
    public int size() {
        return 0;
    }

    @Override // java.util.List, java.util.Collection
    public <T1> T1[] toArray(T1[] t1Arr) {
        return t1Arr;
    }

    @Override // java.util.List, java.util.Collection, java.lang.Iterable
    public Iterator<T> iterator() {
        return EmptyIterator.of();
    }

    @Override // java.util.List, java.util.Collection
    public Object[] toArray() {
        return new Object[0];
    }

    @Override // java.util.List, java.util.Collection
    public boolean add(T t) {
        throw new IllegalArgumentException("Empty list");
    }

    @Override // java.util.List, java.util.Collection
    public boolean addAll(Collection<? extends T> collection) {
        throw new IllegalArgumentException("Empty list");
    }

    @Override // java.util.List
    public boolean addAll(int i, Collection<? extends T> collection) {
        throw new IllegalArgumentException("Empty list");
    }

    @Override // java.util.List, java.util.Collection
    public boolean retainAll(Collection<?> collection) {
        throw new IllegalArgumentException("Empty list");
    }

    @Override // java.util.List
    public T get(int i) {
        throw new IllegalArgumentException("Empty list");
    }

    @Override // java.util.List
    public T set(int i, T t) {
        throw new IllegalArgumentException("Empty list");
    }

    @Override // java.util.List
    public void add(int i, T t) {
        throw new IllegalArgumentException("Empty list");
    }

    @Override // java.util.List
    public T remove(int i) {
        throw new IllegalArgumentException("Empty list");
    }

    @Override // java.util.List
    public ListIterator<T> listIterator() {
        return EmptyIterator.of();
    }

    @Override // java.util.List
    public ListIterator<T> listIterator(int i) {
        return EmptyIterator.of();
    }

    @Override // java.util.List
    public List<T> subList(int i, int i2) {
        throw new IllegalArgumentException("Empty list");
    }

    public static <T1> EmptyList<T1> of() {
        return (EmptyList<T1>) INS;
    }
}
