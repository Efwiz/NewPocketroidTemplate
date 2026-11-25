package org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs;

import java.util.AbstractCollection;
import java.util.Arrays;
import java.util.Collection;

/* JADX INFO: loaded from: classes5.dex */
public abstract class AbstractLongCollection extends AbstractCollection<Long> implements LongCollection {
    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongIterable
    public abstract LongIterator iterator();

    protected AbstractLongCollection() {
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection
    public boolean add(long j) {
        throw new UnsupportedOperationException();
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection
    public boolean contains(long j) {
        LongIterator it = iterator();
        while (it.hasNext()) {
            if (j == it.nextLong()) {
                return true;
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection
    public boolean rem(long j) {
        LongIterator it = iterator();
        while (it.hasNext()) {
            if (j == it.nextLong()) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection
    @Deprecated
    public boolean add(Long l) {
        return super.add(l);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection
    @Deprecated
    public boolean contains(Object obj) {
        return super.contains(obj);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection
    @Deprecated
    public boolean remove(Object obj) {
        return super.remove(obj);
    }

    public long[] toArray(long[] jArr) {
        int size = size();
        if (jArr == null) {
            jArr = new long[size];
        } else if (jArr.length < size) {
            jArr = Arrays.copyOf(jArr, size);
        }
        LongIterators.unwrap(iterator(), jArr);
        return jArr;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection
    public long[] toLongArray() {
        return toArray((long[]) null);
    }

    public boolean addAll(LongCollection longCollection) {
        LongIterator it = longCollection.iterator();
        boolean z = false;
        while (it.hasNext()) {
            if (add(it.nextLong())) {
                z = true;
            }
        }
        return z;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public boolean addAll(Collection<? extends Long> collection) {
        if (collection instanceof LongCollection) {
            return addAll((LongCollection) collection);
        }
        return super.addAll(collection);
    }

    public boolean containsAll(LongCollection longCollection) {
        LongIterator it = longCollection.iterator();
        while (it.hasNext()) {
            if (!contains(it.nextLong())) {
                return false;
            }
        }
        return true;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public boolean containsAll(Collection<?> collection) {
        if (collection instanceof LongCollection) {
            return containsAll((LongCollection) collection);
        }
        return super.containsAll(collection);
    }

    public boolean removeAll(LongCollection longCollection) {
        LongIterator it = longCollection.iterator();
        boolean z = false;
        while (it.hasNext()) {
            if (rem(it.nextLong())) {
                z = true;
            }
        }
        return z;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public boolean removeAll(Collection<?> collection) {
        if (collection instanceof LongCollection) {
            return removeAll((LongCollection) collection);
        }
        return super.removeAll(collection);
    }

    public boolean retainAll(LongCollection longCollection) {
        LongIterator it = iterator();
        boolean z = false;
        while (it.hasNext()) {
            if (!longCollection.contains(it.nextLong())) {
                it.remove();
                z = true;
            }
        }
        return z;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public boolean retainAll(Collection<?> collection) {
        if (collection instanceof LongCollection) {
            return retainAll((LongCollection) collection);
        }
        return super.retainAll(collection);
    }

    @Override // java.util.AbstractCollection
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        LongIterator it = iterator();
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
                sb.append(String.valueOf(it.nextLong()));
                size = i;
            } else {
                sb.append("}");
                return sb.toString();
            }
        }
    }
}
