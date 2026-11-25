package org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.RandomAccess;
import java.util.function.UnaryOperator;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Size64;

/* JADX INFO: loaded from: classes5.dex */
public interface DoubleList extends DoubleCollection, Comparable<List<? extends Double>>, List<Double> {
    void add(int i, double d);

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection
    boolean add(double d);

    double getDouble(int i);

    void getElements(int i, double[] dArr, int i2, int i3);

    int indexOf(double d);

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleSet, java.util.Set
    DoubleListIterator iterator();

    int lastIndexOf(double d);

    @Override // java.util.List
    DoubleListIterator listIterator();

    @Override // java.util.List
    DoubleListIterator listIterator(int i);

    double removeDouble(int i);

    void removeElements(int i, int i2);

    double set(int i, double d);

    @Override // java.util.List
    DoubleList subList(int i, int i2);

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleSet, java.util.Set
    default DoubleSpliterator spliterator() {
        if (this instanceof RandomAccess) {
            return new AbstractDoubleList.IndexBasedSpliterator(this, 0);
        }
        return DoubleSpliterators.asSpliterator(iterator(), Size64.sizeOf(this), 16720);
    }

    default void setElements(double[] dArr) {
        setElements(0, dArr);
    }

    default void setElements(int i, double[] dArr) {
        setElements(i, dArr, 0, dArr.length);
    }

    default void setElements(int i, double[] dArr, int i2, int i3) {
        if (i < 0) {
            throw new IndexOutOfBoundsException("Index (" + i + ") is negative");
        }
        if (i > size()) {
            throw new IndexOutOfBoundsException("Index (" + i + ") is greater than list size (" + size() + Constant.AFTER_QUTO);
        }
        DoubleArrays.ensureOffsetLength(dArr, i2, i3);
        int i4 = i + i3;
        if (i4 > size()) {
            throw new IndexOutOfBoundsException("End index (" + i4 + ") is greater than list size (" + size() + Constant.AFTER_QUTO);
        }
        DoubleListIterator doubleListIteratorListIterator = listIterator(i);
        for (int i5 = 0; i5 < i3; i5++) {
            doubleListIteratorListIterator.nextDouble();
            doubleListIteratorListIterator.set(dArr[i5 + i2]);
        }
    }

    @Override // java.util.List
    @Deprecated
    default void add(int i, Double d) {
        add(i, d.doubleValue());
    }

    default void replaceAll(java.util.function.DoubleUnaryOperator doubleUnaryOperator) {
        DoubleListIterator doubleListIteratorListIterator = listIterator();
        while (doubleListIteratorListIterator.hasNext()) {
            doubleListIteratorListIterator.set(doubleUnaryOperator.applyAsDouble(doubleListIteratorListIterator.nextDouble()));
        }
    }

    @Override // java.util.List
    @Deprecated
    default void replaceAll(final UnaryOperator<Double> unaryOperator) {
        java.util.function.DoubleUnaryOperator doubleUnaryOperator;
        Objects.requireNonNull(unaryOperator);
        if (unaryOperator instanceof java.util.function.DoubleUnaryOperator) {
            doubleUnaryOperator = (java.util.function.DoubleUnaryOperator) unaryOperator;
        } else {
            Objects.requireNonNull(unaryOperator);
            doubleUnaryOperator = new java.util.function.DoubleUnaryOperator() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleList$$ExternalSyntheticLambda0
                @Override // java.util.function.DoubleUnaryOperator
                public final double applyAsDouble(double d) {
                    return ((Double) unaryOperator.apply(Double.valueOf(d))).doubleValue();
                }
            };
        }
        replaceAll(doubleUnaryOperator);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection, java.util.Collection
    @Deprecated
    default boolean contains(Object obj) {
        return super.contains(obj);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.List
    @Deprecated
    default Double get(int i) {
        return Double.valueOf(getDouble(i));
    }

    @Override // java.util.List
    @Deprecated
    default int indexOf(Object obj) {
        return indexOf(((Double) obj).doubleValue());
    }

    @Override // java.util.List
    @Deprecated
    default int lastIndexOf(Object obj) {
        return lastIndexOf(((Double) obj).doubleValue());
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection, java.util.Collection
    @Deprecated
    default boolean add(Double d) {
        return add(d.doubleValue());
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection, java.util.Collection
    @Deprecated
    default boolean remove(Object obj) {
        return super.remove(obj);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.List
    @Deprecated
    default Double remove(int i) {
        return Double.valueOf(removeDouble(i));
    }

    @Override // java.util.List
    @Deprecated
    default Double set(int i, Double d) {
        return Double.valueOf(set(i, d.doubleValue()));
    }

    @Override // java.util.List
    @Deprecated
    default void sort(Comparator<? super Double> comparator) {
        sort(DoubleComparators.asDoubleComparator(comparator));
    }

    default void sort(DoubleComparator doubleComparator) {
        double[] doubleArray = toDoubleArray();
        if (doubleComparator == null) {
            DoubleArrays.stableSort(doubleArray);
        } else {
            DoubleArrays.stableSort(doubleArray, doubleComparator);
        }
        setElements(doubleArray);
    }
}
