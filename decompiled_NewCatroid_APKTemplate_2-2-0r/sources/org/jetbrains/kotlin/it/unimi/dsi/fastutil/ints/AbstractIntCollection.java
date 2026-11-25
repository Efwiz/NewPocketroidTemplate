package org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints;

import java.util.AbstractCollection;
import java.util.Arrays;
import java.util.Collection;

/* JADX INFO: loaded from: classes5.dex */
public abstract class AbstractIntCollection extends AbstractCollection<Integer> implements IntCollection {
    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSet, java.util.Set
    public abstract IntIterator iterator();

    protected AbstractIntCollection() {
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection
    public boolean add(int i) {
        throw new UnsupportedOperationException();
    }

    public boolean contains(int i) {
        IntIterator it = iterator();
        while (it.hasNext()) {
            if (i == it.nextInt()) {
                return true;
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection
    public boolean rem(int i) {
        IntIterator it = iterator();
        while (it.hasNext()) {
            if (i == it.nextInt()) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection
    @Deprecated
    public boolean add(Integer num) {
        return super.add(num);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection
    @Deprecated
    public boolean contains(Object obj) {
        return super.contains(obj);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection
    @Deprecated
    public boolean remove(Object obj) {
        return super.remove(obj);
    }

    public int[] toArray(int[] iArr) {
        int size = size();
        if (iArr == null) {
            iArr = new int[size];
        } else if (iArr.length < size) {
            iArr = Arrays.copyOf(iArr, size);
        }
        IntIterators.unwrap(iterator(), iArr);
        return iArr;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection
    public int[] toIntArray() {
        return toArray((int[]) null);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection
    public boolean addAll(IntCollection intCollection) {
        IntIterator it = intCollection.iterator();
        boolean z = false;
        while (it.hasNext()) {
            if (add(it.nextInt())) {
                z = true;
            }
        }
        return z;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public boolean addAll(Collection<? extends Integer> collection) {
        if (collection instanceof IntCollection) {
            return addAll((IntCollection) collection);
        }
        return super.addAll(collection);
    }

    public boolean containsAll(IntCollection intCollection) {
        IntIterator it = intCollection.iterator();
        while (it.hasNext()) {
            if (!contains(it.nextInt())) {
                return false;
            }
        }
        return true;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public boolean containsAll(Collection<?> collection) {
        if (collection instanceof IntCollection) {
            return containsAll((IntCollection) collection);
        }
        return super.containsAll(collection);
    }

    public boolean removeAll(IntCollection intCollection) {
        IntIterator it = intCollection.iterator();
        boolean z = false;
        while (it.hasNext()) {
            if (rem(it.nextInt())) {
                z = true;
            }
        }
        return z;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public boolean removeAll(Collection<?> collection) {
        if (collection instanceof IntCollection) {
            return removeAll((IntCollection) collection);
        }
        return super.removeAll(collection);
    }

    public boolean retainAll(IntCollection intCollection) {
        IntIterator it = iterator();
        boolean z = false;
        while (it.hasNext()) {
            if (!intCollection.contains(it.nextInt())) {
                it.remove();
                z = true;
            }
        }
        return z;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public boolean retainAll(Collection<?> collection) {
        if (collection instanceof IntCollection) {
            return retainAll((IntCollection) collection);
        }
        return super.retainAll(collection);
    }

    @Override // java.util.AbstractCollection
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        IntIterator it = iterator();
        int size = size();
        boolean z = true;
        while (true) {
            int i = size - 1;
            if (size != 0) {
                if (z) {
                    z = false;
                } else {
                    sb.append(", ");
                }
                sb.append(String.valueOf(it.nextInt()));
                size = i;
            } else {
                sb.append("}");
                return sb.toString();
            }
        }
    }
}
