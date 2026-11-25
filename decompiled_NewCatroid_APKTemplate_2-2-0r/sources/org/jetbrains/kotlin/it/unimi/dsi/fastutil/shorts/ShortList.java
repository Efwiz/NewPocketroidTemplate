package org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.RandomAccess;
import java.util.function.UnaryOperator;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Size64;

/* JADX INFO: loaded from: classes5.dex */
public interface ShortList extends ShortCollection, Comparable<List<? extends Short>>, List<Short> {
    void add(int i, short s);

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollection
    boolean add(short s);

    void getElements(int i, short[] sArr, int i2, int i3);

    short getShort(int i);

    int indexOf(short s);

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortIterable
    ShortListIterator iterator();

    int lastIndexOf(short s);

    @Override // java.util.List
    ShortListIterator listIterator();

    @Override // java.util.List
    ShortListIterator listIterator(int i);

    void removeElements(int i, int i2);

    short removeShort(int i);

    short set(int i, short s);

    @Override // java.util.List
    ShortList subList(int i, int i2);

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortIterable
    default ShortSpliterator spliterator() {
        if (this instanceof RandomAccess) {
            return new AbstractShortList.IndexBasedSpliterator(this, 0);
        }
        return ShortSpliterators.asSpliterator(iterator(), Size64.sizeOf(this), 16720);
    }

    default void setElements(short[] sArr) {
        setElements(0, sArr);
    }

    default void setElements(int i, short[] sArr) {
        setElements(i, sArr, 0, sArr.length);
    }

    default void setElements(int i, short[] sArr, int i2, int i3) {
        if (i < 0) {
            throw new IndexOutOfBoundsException("Index (" + i + ") is negative");
        }
        if (i > size()) {
            throw new IndexOutOfBoundsException("Index (" + i + ") is greater than list size (" + size() + Constant.AFTER_QUTO);
        }
        ShortArrays.ensureOffsetLength(sArr, i2, i3);
        int i4 = i + i3;
        if (i4 > size()) {
            throw new IndexOutOfBoundsException("End index (" + i4 + ") is greater than list size (" + size() + Constant.AFTER_QUTO);
        }
        ShortListIterator shortListIteratorListIterator = listIterator(i);
        for (int i5 = 0; i5 < i3; i5++) {
            shortListIteratorListIterator.nextShort();
            shortListIteratorListIterator.set(sArr[i5 + i2]);
        }
    }

    @Override // java.util.List
    @Deprecated
    default void add(int i, Short sh) {
        add(i, sh.shortValue());
    }

    default void replaceAll(ShortUnaryOperator shortUnaryOperator) {
        ShortListIterator shortListIteratorListIterator = listIterator();
        while (shortListIteratorListIterator.hasNext()) {
            shortListIteratorListIterator.set(shortUnaryOperator.apply(shortListIteratorListIterator.nextShort()));
        }
    }

    @Override // java.util.List
    @Deprecated
    default void replaceAll(final UnaryOperator<Short> unaryOperator) {
        ShortUnaryOperator shortUnaryOperator;
        Objects.requireNonNull(unaryOperator);
        if (unaryOperator instanceof ShortUnaryOperator) {
            shortUnaryOperator = (ShortUnaryOperator) unaryOperator;
        } else {
            Objects.requireNonNull(unaryOperator);
            shortUnaryOperator = new ShortUnaryOperator() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortList$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortUnaryOperator
                public final short apply(short s) {
                    return ((Short) unaryOperator.apply(Short.valueOf(s))).shortValue();
                }
            };
        }
        replaceAll(shortUnaryOperator);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollection, java.util.Collection
    @Deprecated
    default boolean contains(Object obj) {
        return super.contains(obj);
    }

    @Override // java.util.List
    @Deprecated
    default Short get(int i) {
        return Short.valueOf(getShort(i));
    }

    @Override // java.util.List
    @Deprecated
    default int indexOf(Object obj) {
        return indexOf(((Short) obj).shortValue());
    }

    @Override // java.util.List
    @Deprecated
    default int lastIndexOf(Object obj) {
        return lastIndexOf(((Short) obj).shortValue());
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollection, java.util.Collection
    @Deprecated
    default boolean add(Short sh) {
        return add(sh.shortValue());
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollection, java.util.Collection
    @Deprecated
    default boolean remove(Object obj) {
        return super.remove(obj);
    }

    @Override // java.util.List
    @Deprecated
    default Short remove(int i) {
        return Short.valueOf(removeShort(i));
    }

    @Override // java.util.List
    @Deprecated
    default Short set(int i, Short sh) {
        return Short.valueOf(set(i, sh.shortValue()));
    }

    @Override // java.util.List
    @Deprecated
    default void sort(Comparator<? super Short> comparator) {
        sort(ShortComparators.asShortComparator(comparator));
    }

    default void sort(ShortComparator shortComparator) {
        if (shortComparator == null) {
            unstableSort(shortComparator);
            return;
        }
        short[] shortArray = toShortArray();
        ShortArrays.stableSort(shortArray, shortComparator);
        setElements(shortArray);
    }

    default void unstableSort(ShortComparator shortComparator) {
        short[] shortArray = toShortArray();
        if (shortComparator == null) {
            ShortArrays.unstableSort(shortArray);
        } else {
            ShortArrays.unstableSort(shortArray, shortComparator);
        }
        setElements(shortArray);
    }
}
