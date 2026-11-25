package org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.RandomAccess;
import java.util.function.UnaryOperator;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Size64;

/* JADX INFO: loaded from: classes5.dex */
public interface LongList extends LongCollection, Comparable<List<? extends Long>>, List<Long> {
    void add(int i, long j);

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection
    boolean add(long j);

    void getElements(int i, long[] jArr, int i2, int i3);

    long getLong(int i);

    int indexOf(long j);

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongIterable
    LongListIterator iterator();

    int lastIndexOf(long j);

    @Override // java.util.List
    LongListIterator listIterator();

    @Override // java.util.List
    LongListIterator listIterator(int i);

    void removeElements(int i, int i2);

    long removeLong(int i);

    long set(int i, long j);

    @Override // java.util.List
    LongList subList(int i, int i2);

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongList, java.util.List
    default LongSpliterator spliterator() {
        if (this instanceof RandomAccess) {
            return new AbstractLongList.IndexBasedSpliterator(this, 0);
        }
        return LongSpliterators.asSpliterator(iterator(), Size64.sizeOf(this), 16720);
    }

    default void setElements(long[] jArr) {
        setElements(0, jArr);
    }

    default void setElements(int i, long[] jArr) {
        setElements(i, jArr, 0, jArr.length);
    }

    default void setElements(int i, long[] jArr, int i2, int i3) {
        if (i < 0) {
            throw new IndexOutOfBoundsException("Index (" + i + ") is negative");
        }
        if (i > size()) {
            throw new IndexOutOfBoundsException("Index (" + i + ") is greater than list size (" + size() + Constant.AFTER_QUTO);
        }
        LongArrays.ensureOffsetLength(jArr, i2, i3);
        int i4 = i + i3;
        if (i4 > size()) {
            throw new IndexOutOfBoundsException("End index (" + i4 + ") is greater than list size (" + size() + Constant.AFTER_QUTO);
        }
        LongListIterator longListIteratorListIterator = listIterator(i);
        for (int i5 = 0; i5 < i3; i5++) {
            longListIteratorListIterator.nextLong();
            longListIteratorListIterator.set(jArr[i5 + i2]);
        }
    }

    @Override // java.util.List
    @Deprecated
    default void add(int i, Long l) {
        add(i, l.longValue());
    }

    default void replaceAll(java.util.function.LongUnaryOperator longUnaryOperator) {
        LongListIterator longListIteratorListIterator = listIterator();
        while (longListIteratorListIterator.hasNext()) {
            longListIteratorListIterator.set(longUnaryOperator.applyAsLong(longListIteratorListIterator.nextLong()));
        }
    }

    @Override // java.util.List
    @Deprecated
    default void replaceAll(final UnaryOperator<Long> unaryOperator) {
        java.util.function.LongUnaryOperator longUnaryOperator;
        Objects.requireNonNull(unaryOperator);
        if (unaryOperator instanceof java.util.function.LongUnaryOperator) {
            longUnaryOperator = (java.util.function.LongUnaryOperator) unaryOperator;
        } else {
            Objects.requireNonNull(unaryOperator);
            longUnaryOperator = new java.util.function.LongUnaryOperator() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongList$$ExternalSyntheticLambda0
                @Override // java.util.function.LongUnaryOperator
                public final long applyAsLong(long j) {
                    return ((Long) unaryOperator.apply(Long.valueOf(j))).longValue();
                }
            };
        }
        replaceAll(longUnaryOperator);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection, java.util.Collection
    @Deprecated
    default boolean contains(Object obj) {
        return super.contains(obj);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.List
    @Deprecated
    default Long get(int i) {
        return Long.valueOf(getLong(i));
    }

    @Override // java.util.List
    @Deprecated
    default int indexOf(Object obj) {
        return indexOf(((Long) obj).longValue());
    }

    @Override // java.util.List
    @Deprecated
    default int lastIndexOf(Object obj) {
        return lastIndexOf(((Long) obj).longValue());
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection, java.util.Collection
    @Deprecated
    default boolean add(Long l) {
        return add(l.longValue());
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection, java.util.Collection
    @Deprecated
    default boolean remove(Object obj) {
        return super.remove(obj);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.List
    @Deprecated
    default Long remove(int i) {
        return Long.valueOf(removeLong(i));
    }

    @Override // java.util.List
    @Deprecated
    default Long set(int i, Long l) {
        return Long.valueOf(set(i, l.longValue()));
    }

    @Override // java.util.List
    @Deprecated
    default void sort(Comparator<? super Long> comparator) {
        sort(LongComparators.asLongComparator(comparator));
    }

    default void sort(LongComparator longComparator) {
        if (longComparator == null) {
            unstableSort(longComparator);
            return;
        }
        long[] longArray = toLongArray();
        LongArrays.stableSort(longArray, longComparator);
        setElements(longArray);
    }

    default void unstableSort(LongComparator longComparator) {
        long[] longArray = toLongArray();
        if (longComparator == null) {
            LongArrays.unstableSort(longArray);
        } else {
            LongArrays.unstableSort(longArray, longComparator);
        }
        setElements(longArray);
    }
}
