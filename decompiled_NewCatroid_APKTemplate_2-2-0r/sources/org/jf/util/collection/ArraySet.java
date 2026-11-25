package org.jf.util.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;

/* JADX INFO: loaded from: classes6.dex */
public class ArraySet<T> implements Set<T>, Comparator<T> {
    private Comparator<? super T> comparator;
    private final ArrayList<T> items;
    private int mHashCode;
    private int mHashCodeStamp;

    public ArraySet() {
        this.mHashCodeStamp = -1;
        this.items = new ArrayList<>();
    }

    public ArraySet(int i) {
        this.mHashCodeStamp = -1;
        this.items = new ArrayList<>(i);
    }

    public ArraySet(Collection<? extends T> collection) {
        this.mHashCodeStamp = -1;
        this.items = new ArrayList<>(collection.size());
        addAll((Collection) collection);
    }

    public Comparator<? super T> comparator() {
        return this.comparator;
    }

    public void setComparator(Comparator<? super T> comparator) {
        this.comparator = comparator;
        if (comparator != null) {
            sort(comparator);
        }
    }

    public void trimToSize() {
        this.items.trimToSize();
    }

    public boolean addAll(Iterable<? extends T> iterable) {
        if (iterable == null) {
            return false;
        }
        if (iterable instanceof Collection) {
            return addAll((Collection) iterable);
        }
        return addAll(iterable.iterator());
    }

    public boolean addAll(Iterator<? extends T> it) {
        if (it == null || !it.hasNext()) {
            return false;
        }
        while (it.hasNext()) {
            add(it.next());
        }
        trimToSize();
        return true;
    }

    public boolean addAll(T... tArr) {
        boolean z;
        if (tArr == null || tArr.length == 0) {
            return false;
        }
        synchronized (this.items) {
            ArrayList<T> arrayList = this.items;
            arrayList.ensureCapacity(arrayList.size() + tArr.length);
            z = false;
            for (T t : tArr) {
                if (t != null && !arrayList.contains(t)) {
                    arrayList.add(t);
                    z = true;
                }
            }
        }
        return z;
    }

    @Override // java.util.Set, java.util.Collection
    public int size() {
        return this.items.size();
    }

    @Override // java.util.Set, java.util.Collection
    public boolean isEmpty() {
        return this.items.isEmpty();
    }

    @Override // java.util.Set, java.util.Collection
    public boolean contains(Object obj) {
        if (obj == null) {
            return false;
        }
        return this.items.contains(obj);
    }

    @Override // java.util.Set, java.util.Collection, java.lang.Iterable
    public Iterator<T> iterator() {
        return this.items.iterator();
    }

    @Override // java.util.Set, java.util.Collection
    public Object[] toArray() {
        return this.items.toArray();
    }

    @Override // java.util.Set, java.util.Collection
    public <T1> T1[] toArray(T1[] t1Arr) {
        return (T1[]) this.items.toArray(t1Arr);
    }

    @Override // java.util.Set, java.util.Collection
    public boolean add(T t) {
        synchronized (t) {
            try {
                if (t == null) {
                    return false;
                }
                if (this.items.contains(t)) {
                    return !this.items.contains(t);
                }
                this.items.add(t);
                return true;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    private T getByHash(int i) {
        for (T t : this.items) {
            if (t.hashCode() == i) {
                return t;
            }
        }
        return null;
    }

    @Override // java.util.Set, java.util.Collection
    public boolean remove(Object obj) {
        boolean zRemove;
        if (obj == null) {
            return false;
        }
        synchronized (this.items) {
            zRemove = this.items.remove(obj);
        }
        return zRemove;
    }

    @Override // java.util.Set, java.util.Collection
    public boolean containsAll(Collection<?> collection) {
        return this.items.containsAll(collection);
    }

    @Override // java.util.Set, java.util.Collection
    public boolean addAll(Collection<? extends T> collection) {
        boolean z = false;
        if (collection == null || collection.size() == 0 || collection == this) {
            return false;
        }
        synchronized (this.items) {
            ArrayList<T> arrayList = this.items;
            arrayList.ensureCapacity(arrayList.size() + collection.size());
            for (T t : collection) {
                if (t != null && !arrayList.contains(t)) {
                    arrayList.add(t);
                    z = true;
                }
            }
            if (z) {
                trimToSize();
            }
        }
        return z;
    }

    @Override // java.util.Set, java.util.Collection
    public boolean retainAll(Collection<?> collection) {
        return this.items.retainAll(collection);
    }

    @Override // java.util.Set, java.util.Collection
    public boolean removeAll(Collection<?> collection) {
        boolean zRemoveAll = this.items.removeAll(collection);
        trimToSize();
        return zRemoveAll;
    }

    @Override // java.util.Set, java.util.Collection
    public void clear() {
        this.items.clear();
        trimToSize();
    }

    @Override // java.util.Comparator
    public int compare(T t, T t2) {
        if (t != t2 && (t instanceof Comparable) && (t2 instanceof Comparable)) {
            return ((Comparable) t).compareTo(t2);
        }
        return 0;
    }

    public ArraySet<T> sort() {
        return sort(this);
    }

    public ArraySet<T> sort(Comparator<? super T> comparator) {
        this.items.sort(comparator);
        return this;
    }

    @Override // java.util.Set, java.util.Collection, java.util.Comparator
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Set)) {
            return false;
        }
        Set set = (Set) obj;
        int size = size();
        if (set.size() != size) {
            return false;
        }
        if (size == 0) {
            return true;
        }
        if (getHashCode(set) != hashCode()) {
            return false;
        }
        Iterator<T> it = iterator();
        Iterator it2 = set.iterator();
        while (it.hasNext()) {
            if (!it2.hasNext() || !it.next().equals(it2.next())) {
                return false;
            }
        }
        return true;
    }

    @Override // java.util.Set, java.util.Collection
    public int hashCode() {
        if (this.mHashCodeStamp != this.items.size()) {
            this.mHashCode = hashCode(this);
            this.mHashCodeStamp = this.items.size();
        }
        return this.mHashCode;
    }

    private static int getHashCode(Set<?> set) {
        if (set instanceof ArraySet) {
            return set.hashCode();
        }
        return hashCode(set);
    }

    public static int hashCode(Set<?> set) {
        int i = 1;
        for (int i2 : sortedHashCode(set)) {
            i = (i * 31) + i2;
        }
        return i;
    }

    private static int[] sortedHashCode(Set<?> set) {
        int[] iArr = new int[set.size()];
        int i = 0;
        for (Object obj : set) {
            if (obj != null) {
                iArr[i] = obj.hashCode();
            }
            i++;
        }
        Arrays.sort(iArr);
        return iArr;
    }

    public static <E> ArraySet<E> sortedCopy(Iterator<? extends E> it) {
        ArraySet<E> arraySetCopyOf = copyOf(it);
        arraySetCopyOf.sort();
        return arraySetCopyOf;
    }

    public static <E> ArraySet<E> copyOf(Iterable<? extends E> iterable) {
        if (iterable == null) {
            return EmptySet.of();
        }
        ArraySet<E> arraySet = new ArraySet<>();
        arraySet.addAll(iterable);
        return arraySet;
    }

    public static <E> ArraySet<E> copyOf(Collection<? extends E> collection) {
        if (collection == null) {
            return EmptySet.of();
        }
        ArraySet<E> arraySet = new ArraySet<>();
        arraySet.addAll(collection);
        return arraySet;
    }

    public static <E> ArraySet<E> copyOf(Iterator<? extends E> it) {
        if (!it.hasNext()) {
            return EmptySet.of();
        }
        ArraySet<E> arraySet = new ArraySet<>();
        arraySet.addAll(it);
        return arraySet;
    }

    public static <E> ArraySet<E> copyOfElements(E... eArr) {
        if (eArr == null || eArr.length == 0) {
            return EmptySet.of();
        }
        ArraySet<E> arraySet = new ArraySet<>();
        arraySet.addAll(eArr);
        return arraySet;
    }

    public static <E> ArraySet<E> of() {
        return EmptySet.of();
    }

    public static <E> ArraySet<E> of(E... eArr) {
        if (eArr == null || eArr.length == 0) {
            return EmptySet.of();
        }
        ArraySet<E> arraySet = new ArraySet<>();
        arraySet.addAll(eArr);
        return arraySet;
    }
}
