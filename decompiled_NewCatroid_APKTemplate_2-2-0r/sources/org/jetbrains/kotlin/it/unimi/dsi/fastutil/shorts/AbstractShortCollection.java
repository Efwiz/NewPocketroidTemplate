package org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts;

import java.util.AbstractCollection;
import java.util.Arrays;
import java.util.Collection;

/* JADX INFO: loaded from: classes5.dex */
public abstract class AbstractShortCollection extends AbstractCollection<Short> implements ShortCollection {
    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortIterable
    public abstract ShortIterator iterator();

    protected AbstractShortCollection() {
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollection
    public boolean add(short s) {
        throw new UnsupportedOperationException();
    }

    public boolean contains(short s) {
        ShortIterator it = iterator();
        while (it.hasNext()) {
            if (s == it.nextShort()) {
                return true;
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollection
    public boolean rem(short s) {
        ShortIterator it = iterator();
        while (it.hasNext()) {
            if (s == it.nextShort()) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollection
    @Deprecated
    public boolean add(Short sh) {
        return super.add(sh);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollection
    @Deprecated
    public boolean contains(Object obj) {
        return super.contains(obj);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollection
    @Deprecated
    public boolean remove(Object obj) {
        return super.remove(obj);
    }

    public short[] toArray(short[] sArr) {
        int size = size();
        if (sArr == null) {
            sArr = new short[size];
        } else if (sArr.length < size) {
            sArr = Arrays.copyOf(sArr, size);
        }
        ShortIterators.unwrap(iterator(), sArr);
        return sArr;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollection
    public short[] toShortArray() {
        return toArray((short[]) null);
    }

    public boolean addAll(ShortCollection shortCollection) {
        ShortIterator it = shortCollection.iterator();
        boolean z = false;
        while (it.hasNext()) {
            if (add(it.nextShort())) {
                z = true;
            }
        }
        return z;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public boolean addAll(Collection<? extends Short> collection) {
        if (collection instanceof ShortCollection) {
            return addAll((ShortCollection) collection);
        }
        return super.addAll(collection);
    }

    public boolean containsAll(ShortCollection shortCollection) {
        ShortIterator it = shortCollection.iterator();
        while (it.hasNext()) {
            if (!contains(it.nextShort())) {
                return false;
            }
        }
        return true;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public boolean containsAll(Collection<?> collection) {
        if (collection instanceof ShortCollection) {
            return containsAll((ShortCollection) collection);
        }
        return super.containsAll(collection);
    }

    public boolean removeAll(ShortCollection shortCollection) {
        ShortIterator it = shortCollection.iterator();
        boolean z = false;
        while (it.hasNext()) {
            if (rem(it.nextShort())) {
                z = true;
            }
        }
        return z;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public boolean removeAll(Collection<?> collection) {
        if (collection instanceof ShortCollection) {
            return removeAll((ShortCollection) collection);
        }
        return super.removeAll(collection);
    }

    public boolean retainAll(ShortCollection shortCollection) {
        ShortIterator it = iterator();
        boolean z = false;
        while (it.hasNext()) {
            if (!shortCollection.contains(it.nextShort())) {
                it.remove();
                z = true;
            }
        }
        return z;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public boolean retainAll(Collection<?> collection) {
        if (collection instanceof ShortCollection) {
            return retainAll((ShortCollection) collection);
        }
        return super.retainAll(collection);
    }

    @Override // java.util.AbstractCollection
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        ShortIterator it = iterator();
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
                sb.append(String.valueOf((int) it.nextShort()));
                size = i;
            } else {
                sb.append("}");
                return sb.toString();
            }
        }
    }
}
