package org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles;

import java.util.AbstractCollection;
import java.util.Arrays;
import java.util.Collection;

/* JADX INFO: loaded from: classes5.dex */
public abstract class AbstractDoubleCollection extends AbstractCollection<Double> implements DoubleCollection {
    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleSet, java.util.Set
    public abstract DoubleIterator iterator();

    protected AbstractDoubleCollection() {
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection
    public boolean add(double d) {
        throw new UnsupportedOperationException();
    }

    public boolean contains(double d) {
        DoubleIterator it = iterator();
        while (it.hasNext()) {
            if (d == it.nextDouble()) {
                return true;
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection
    public boolean rem(double d) {
        DoubleIterator it = iterator();
        while (it.hasNext()) {
            if (d == it.nextDouble()) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection
    @Deprecated
    public boolean add(Double d) {
        return super.add(d);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection
    @Deprecated
    public boolean contains(Object obj) {
        return super.contains(obj);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection
    @Deprecated
    public boolean remove(Object obj) {
        return super.remove(obj);
    }

    public double[] toArray(double[] dArr) {
        int size = size();
        if (dArr == null) {
            dArr = new double[size];
        } else if (dArr.length < size) {
            dArr = Arrays.copyOf(dArr, size);
        }
        DoubleIterators.unwrap(iterator(), dArr);
        return dArr;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection
    public double[] toDoubleArray() {
        return toArray((double[]) null);
    }

    public boolean addAll(DoubleCollection doubleCollection) {
        DoubleIterator it = doubleCollection.iterator();
        boolean z = false;
        while (it.hasNext()) {
            if (add(it.nextDouble())) {
                z = true;
            }
        }
        return z;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public boolean addAll(Collection<? extends Double> collection) {
        if (collection instanceof DoubleCollection) {
            return addAll((DoubleCollection) collection);
        }
        return super.addAll(collection);
    }

    public boolean containsAll(DoubleCollection doubleCollection) {
        DoubleIterator it = doubleCollection.iterator();
        while (it.hasNext()) {
            if (!contains(it.nextDouble())) {
                return false;
            }
        }
        return true;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public boolean containsAll(Collection<?> collection) {
        if (collection instanceof DoubleCollection) {
            return containsAll((DoubleCollection) collection);
        }
        return super.containsAll(collection);
    }

    public boolean removeAll(DoubleCollection doubleCollection) {
        DoubleIterator it = doubleCollection.iterator();
        boolean z = false;
        while (it.hasNext()) {
            if (rem(it.nextDouble())) {
                z = true;
            }
        }
        return z;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public boolean removeAll(Collection<?> collection) {
        if (collection instanceof DoubleCollection) {
            return removeAll((DoubleCollection) collection);
        }
        return super.removeAll(collection);
    }

    public boolean retainAll(DoubleCollection doubleCollection) {
        DoubleIterator it = iterator();
        boolean z = false;
        while (it.hasNext()) {
            if (!doubleCollection.contains(it.nextDouble())) {
                it.remove();
                z = true;
            }
        }
        return z;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public boolean retainAll(Collection<?> collection) {
        if (collection instanceof DoubleCollection) {
            return retainAll((DoubleCollection) collection);
        }
        return super.retainAll(collection);
    }

    @Override // java.util.AbstractCollection
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        DoubleIterator it = iterator();
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
                sb.append(String.valueOf(it.nextDouble()));
                size = i;
            } else {
                sb.append("}");
                return sb.toString();
            }
        }
    }
}
