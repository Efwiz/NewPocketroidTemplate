package org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats;

import java.util.AbstractCollection;
import java.util.Arrays;
import java.util.Collection;

/* JADX INFO: loaded from: classes5.dex */
public abstract class AbstractFloatCollection extends AbstractCollection<Float> implements FloatCollection {
    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatIterable
    public abstract FloatIterator iterator();

    protected AbstractFloatCollection() {
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatCollection
    public boolean add(float f) {
        throw new UnsupportedOperationException();
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatCollection
    public boolean contains(float f) {
        FloatIterator it = iterator();
        while (it.hasNext()) {
            if (f == it.nextFloat()) {
                return true;
            }
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatCollection
    public boolean rem(float f) {
        FloatIterator it = iterator();
        while (it.hasNext()) {
            if (f == it.nextFloat()) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatCollection
    @Deprecated
    public boolean add(Float f) {
        return super.add(f);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatCollection
    @Deprecated
    public boolean contains(Object obj) {
        return super.contains(obj);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatCollection
    @Deprecated
    public boolean remove(Object obj) {
        return super.remove(obj);
    }

    public float[] toArray(float[] fArr) {
        int size = size();
        if (fArr == null) {
            fArr = new float[size];
        } else if (fArr.length < size) {
            fArr = Arrays.copyOf(fArr, size);
        }
        FloatIterators.unwrap(iterator(), fArr);
        return fArr;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatCollection
    public float[] toFloatArray() {
        return toArray((float[]) null);
    }

    public boolean addAll(FloatCollection floatCollection) {
        FloatIterator it = floatCollection.iterator();
        boolean z = false;
        while (it.hasNext()) {
            if (add(it.nextFloat())) {
                z = true;
            }
        }
        return z;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public boolean addAll(Collection<? extends Float> collection) {
        if (collection instanceof FloatCollection) {
            return addAll((FloatCollection) collection);
        }
        return super.addAll(collection);
    }

    public boolean containsAll(FloatCollection floatCollection) {
        FloatIterator it = floatCollection.iterator();
        while (it.hasNext()) {
            if (!contains(it.nextFloat())) {
                return false;
            }
        }
        return true;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public boolean containsAll(Collection<?> collection) {
        if (collection instanceof FloatCollection) {
            return containsAll((FloatCollection) collection);
        }
        return super.containsAll(collection);
    }

    public boolean removeAll(FloatCollection floatCollection) {
        FloatIterator it = floatCollection.iterator();
        boolean z = false;
        while (it.hasNext()) {
            if (rem(it.nextFloat())) {
                z = true;
            }
        }
        return z;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public boolean removeAll(Collection<?> collection) {
        if (collection instanceof FloatCollection) {
            return removeAll((FloatCollection) collection);
        }
        return super.removeAll(collection);
    }

    public boolean retainAll(FloatCollection floatCollection) {
        FloatIterator it = iterator();
        boolean z = false;
        while (it.hasNext()) {
            if (!floatCollection.contains(it.nextFloat())) {
                it.remove();
                z = true;
            }
        }
        return z;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public boolean retainAll(Collection<?> collection) {
        if (collection instanceof FloatCollection) {
            return retainAll((FloatCollection) collection);
        }
        return super.retainAll(collection);
    }

    @Override // java.util.AbstractCollection
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        FloatIterator it = iterator();
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
                sb.append(String.valueOf(it.nextFloat()));
                size = i;
            } else {
                sb.append("}");
                return sb.toString();
            }
        }
    }
}
