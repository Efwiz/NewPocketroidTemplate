package org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.RandomAccess;
import java.util.function.UnaryOperator;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Size64;

/* JADX INFO: loaded from: classes5.dex */
public interface IntList extends IntCollection, Comparable<List<? extends Integer>>, List<Integer> {
    void add(int i, int i2);

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection
    boolean add(int i);

    void getElements(int i, int[] iArr, int i2, int i3);

    int getInt(int i);

    int indexOf(int i);

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSet, java.util.Set
    IntListIterator iterator();

    int lastIndexOf(int i);

    @Override // java.util.List
    IntListIterator listIterator();

    @Override // java.util.List
    IntListIterator listIterator(int i);

    void removeElements(int i, int i2);

    int removeInt(int i);

    int set(int i, int i2);

    @Override // java.util.List
    IntList subList(int i, int i2);

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSet, java.util.Set
    default IntSpliterator spliterator() {
        if (this instanceof RandomAccess) {
            return new AbstractIntList.IndexBasedSpliterator(this, 0);
        }
        return IntSpliterators.asSpliterator(iterator(), Size64.sizeOf(this), 16720);
    }

    default void setElements(int[] iArr) {
        setElements(0, iArr);
    }

    default void setElements(int i, int[] iArr) {
        setElements(i, iArr, 0, iArr.length);
    }

    default void setElements(int i, int[] iArr, int i2, int i3) {
        if (i < 0) {
            throw new IndexOutOfBoundsException("Index (" + i + ") is negative");
        }
        if (i > size()) {
            throw new IndexOutOfBoundsException("Index (" + i + ") is greater than list size (" + size() + Constant.AFTER_QUTO);
        }
        IntArrays.ensureOffsetLength(iArr, i2, i3);
        int i4 = i + i3;
        if (i4 > size()) {
            throw new IndexOutOfBoundsException("End index (" + i4 + ") is greater than list size (" + size() + Constant.AFTER_QUTO);
        }
        IntListIterator intListIteratorListIterator = listIterator(i);
        for (int i5 = 0; i5 < i3; i5++) {
            intListIteratorListIterator.nextInt();
            intListIteratorListIterator.set(iArr[i5 + i2]);
        }
    }

    @Override // java.util.List
    @Deprecated
    default void add(int i, Integer num) {
        add(i, num.intValue());
    }

    default void replaceAll(java.util.function.IntUnaryOperator intUnaryOperator) {
        IntListIterator intListIteratorListIterator = listIterator();
        while (intListIteratorListIterator.hasNext()) {
            intListIteratorListIterator.set(intUnaryOperator.applyAsInt(intListIteratorListIterator.nextInt()));
        }
    }

    @Override // java.util.List
    @Deprecated
    default void replaceAll(final UnaryOperator<Integer> unaryOperator) {
        java.util.function.IntUnaryOperator intUnaryOperator;
        Objects.requireNonNull(unaryOperator);
        if (unaryOperator instanceof java.util.function.IntUnaryOperator) {
            intUnaryOperator = (java.util.function.IntUnaryOperator) unaryOperator;
        } else {
            Objects.requireNonNull(unaryOperator);
            intUnaryOperator = new java.util.function.IntUnaryOperator() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntList$$ExternalSyntheticLambda0
                @Override // java.util.function.IntUnaryOperator
                public final int applyAsInt(int i) {
                    return ((Integer) unaryOperator.apply(Integer.valueOf(i))).intValue();
                }
            };
        }
        replaceAll(intUnaryOperator);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection, java.util.Collection
    @Deprecated
    default boolean contains(Object obj) {
        return super.contains(obj);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.List
    @Deprecated
    default Integer get(int i) {
        return Integer.valueOf(getInt(i));
    }

    @Override // java.util.List
    @Deprecated
    default int indexOf(Object obj) {
        return indexOf(((Integer) obj).intValue());
    }

    @Override // java.util.List
    @Deprecated
    default int lastIndexOf(Object obj) {
        return lastIndexOf(((Integer) obj).intValue());
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection, java.util.Collection
    @Deprecated
    default boolean add(Integer num) {
        return add(num.intValue());
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection, java.util.Collection
    @Deprecated
    default boolean remove(Object obj) {
        return super.remove(obj);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.List
    @Deprecated
    default Integer remove(int i) {
        return Integer.valueOf(removeInt(i));
    }

    @Override // java.util.List
    @Deprecated
    default Integer set(int i, Integer num) {
        return Integer.valueOf(set(i, num.intValue()));
    }

    @Override // java.util.List
    @Deprecated
    default void sort(Comparator<? super Integer> comparator) {
        sort(IntComparators.asIntComparator(comparator));
    }

    default void sort(IntComparator intComparator) {
        if (intComparator == null) {
            unstableSort(intComparator);
            return;
        }
        int[] intArray = toIntArray();
        IntArrays.stableSort(intArray, intComparator);
        setElements(intArray);
    }

    default void unstableSort(IntComparator intComparator) {
        int[] intArray = toIntArray();
        if (intComparator == null) {
            IntArrays.unstableSort(intArray);
        } else {
            IntArrays.unstableSort(intArray, intComparator);
        }
        setElements(intArray);
    }
}
