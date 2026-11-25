package org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans;

import java.util.AbstractCollection;
import java.util.Arrays;
import java.util.Collection;

/* JADX INFO: loaded from: classes5.dex */
public abstract class AbstractBooleanCollection extends AbstractCollection<Boolean> implements BooleanCollection {
    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanIterable
    public abstract BooleanIterator iterator();

    protected AbstractBooleanCollection() {
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanCollection
    public boolean add(boolean z) {
        throw new UnsupportedOperationException();
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanCollection
    public boolean contains(boolean z) {
        BooleanIterator it = iterator();
        while (it.hasNext()) {
            if (z == it.nextBoolean()) {
                return true;
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanCollection
    public boolean rem(boolean z) {
        BooleanIterator it = iterator();
        while (it.hasNext()) {
            if (z == it.nextBoolean()) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanCollection
    @Deprecated
    public boolean add(Boolean bool) {
        return super.add(bool);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanCollection
    @Deprecated
    public boolean contains(Object obj) {
        return super.contains(obj);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanCollection
    @Deprecated
    public boolean remove(Object obj) {
        return super.remove(obj);
    }

    public boolean[] toArray(boolean[] zArr) {
        int size = size();
        if (zArr == null) {
            zArr = new boolean[size];
        } else if (zArr.length < size) {
            zArr = Arrays.copyOf(zArr, size);
        }
        BooleanIterators.unwrap(iterator(), zArr);
        return zArr;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanCollection
    public boolean[] toBooleanArray() {
        return toArray((boolean[]) null);
    }

    public boolean addAll(BooleanCollection booleanCollection) {
        BooleanIterator it = booleanCollection.iterator();
        boolean z = false;
        while (it.hasNext()) {
            if (add(it.nextBoolean())) {
                z = true;
            }
        }
        return z;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public boolean addAll(Collection<? extends Boolean> collection) {
        if (collection instanceof BooleanCollection) {
            return addAll((BooleanCollection) collection);
        }
        return super.addAll(collection);
    }

    public boolean containsAll(BooleanCollection booleanCollection) {
        BooleanIterator it = booleanCollection.iterator();
        while (it.hasNext()) {
            if (!contains(it.nextBoolean())) {
                return false;
            }
        }
        return true;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public boolean containsAll(Collection<?> collection) {
        if (collection instanceof BooleanCollection) {
            return containsAll((BooleanCollection) collection);
        }
        return super.containsAll(collection);
    }

    public boolean removeAll(BooleanCollection booleanCollection) {
        BooleanIterator it = booleanCollection.iterator();
        boolean z = false;
        while (it.hasNext()) {
            if (rem(it.nextBoolean())) {
                z = true;
            }
        }
        return z;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public boolean removeAll(Collection<?> collection) {
        if (collection instanceof BooleanCollection) {
            return removeAll((BooleanCollection) collection);
        }
        return super.removeAll(collection);
    }

    public boolean retainAll(BooleanCollection booleanCollection) {
        BooleanIterator it = iterator();
        boolean z = false;
        while (it.hasNext()) {
            if (!booleanCollection.contains(it.nextBoolean())) {
                it.remove();
                z = true;
            }
        }
        return z;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public boolean retainAll(Collection<?> collection) {
        if (collection instanceof BooleanCollection) {
            return retainAll((BooleanCollection) collection);
        }
        return super.retainAll(collection);
    }

    @Override // java.util.AbstractCollection
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        BooleanIterator it = iterator();
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
                sb.append(String.valueOf(it.nextBoolean()));
                size = i;
            } else {
                sb.append("}");
                return sb.toString();
            }
        }
    }
}
