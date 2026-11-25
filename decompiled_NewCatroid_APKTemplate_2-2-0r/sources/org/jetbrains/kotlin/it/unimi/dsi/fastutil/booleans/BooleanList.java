package org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.RandomAccess;
import java.util.function.UnaryOperator;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Size64;

/* JADX INFO: loaded from: classes5.dex */
public interface BooleanList extends BooleanCollection, Comparable<List<? extends Boolean>>, List<Boolean> {
    void add(int i, boolean z);

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanCollection
    boolean add(boolean z);

    boolean getBoolean(int i);

    void getElements(int i, boolean[] zArr, int i2, int i3);

    int indexOf(boolean z);

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanIterable
    BooleanListIterator iterator();

    int lastIndexOf(boolean z);

    @Override // java.util.List
    BooleanListIterator listIterator();

    @Override // java.util.List
    BooleanListIterator listIterator(int i);

    boolean removeBoolean(int i);

    void removeElements(int i, int i2);

    boolean set(int i, boolean z);

    @Override // java.util.List
    BooleanList subList(int i, int i2);

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanList, java.util.List
    default BooleanSpliterator spliterator() {
        if (this instanceof RandomAccess) {
            return new AbstractBooleanList.IndexBasedSpliterator(this, 0);
        }
        return BooleanSpliterators.asSpliterator(iterator(), Size64.sizeOf(this), 16720);
    }

    default void setElements(boolean[] zArr) {
        setElements(0, zArr);
    }

    default void setElements(int i, boolean[] zArr) {
        setElements(i, zArr, 0, zArr.length);
    }

    default void setElements(int i, boolean[] zArr, int i2, int i3) {
        if (i < 0) {
            throw new IndexOutOfBoundsException("Index (" + i + ") is negative");
        }
        if (i > size()) {
            throw new IndexOutOfBoundsException("Index (" + i + ") is greater than list size (" + size() + Constant.AFTER_QUTO);
        }
        BooleanArrays.ensureOffsetLength(zArr, i2, i3);
        int i4 = i + i3;
        if (i4 > size()) {
            throw new IndexOutOfBoundsException("End index (" + i4 + ") is greater than list size (" + size() + Constant.AFTER_QUTO);
        }
        BooleanListIterator booleanListIteratorListIterator = listIterator(i);
        for (int i5 = 0; i5 < i3; i5++) {
            booleanListIteratorListIterator.nextBoolean();
            booleanListIteratorListIterator.set(zArr[i5 + i2]);
        }
    }

    @Override // java.util.List
    @Deprecated
    default void add(int i, Boolean bool) {
        add(i, bool.booleanValue());
    }

    default void replaceAll(BooleanUnaryOperator booleanUnaryOperator) {
        BooleanListIterator booleanListIteratorListIterator = listIterator();
        while (booleanListIteratorListIterator.hasNext()) {
            booleanListIteratorListIterator.set(booleanUnaryOperator.apply(booleanListIteratorListIterator.nextBoolean()));
        }
    }

    @Override // java.util.List
    @Deprecated
    default void replaceAll(final UnaryOperator<Boolean> unaryOperator) {
        BooleanUnaryOperator booleanUnaryOperator;
        Objects.requireNonNull(unaryOperator);
        if (unaryOperator instanceof BooleanUnaryOperator) {
            booleanUnaryOperator = (BooleanUnaryOperator) unaryOperator;
        } else {
            Objects.requireNonNull(unaryOperator);
            booleanUnaryOperator = new BooleanUnaryOperator() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanList$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanUnaryOperator
                public final boolean apply(boolean z) {
                    return ((Boolean) unaryOperator.apply(Boolean.valueOf(z))).booleanValue();
                }
            };
        }
        replaceAll(booleanUnaryOperator);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanCollection, java.util.Collection
    @Deprecated
    default boolean contains(Object obj) {
        return super.contains(obj);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.List
    @Deprecated
    default Boolean get(int i) {
        return Boolean.valueOf(getBoolean(i));
    }

    @Override // java.util.List
    @Deprecated
    default int indexOf(Object obj) {
        return indexOf(((Boolean) obj).booleanValue());
    }

    @Override // java.util.List
    @Deprecated
    default int lastIndexOf(Object obj) {
        return lastIndexOf(((Boolean) obj).booleanValue());
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanCollection, java.util.Collection
    @Deprecated
    default boolean add(Boolean bool) {
        return add(bool.booleanValue());
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanCollection, java.util.Collection
    @Deprecated
    default boolean remove(Object obj) {
        return super.remove(obj);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.List
    @Deprecated
    default Boolean remove(int i) {
        return Boolean.valueOf(removeBoolean(i));
    }

    @Override // java.util.List
    @Deprecated
    default Boolean set(int i, Boolean bool) {
        return Boolean.valueOf(set(i, bool.booleanValue()));
    }

    @Override // java.util.List
    @Deprecated
    default void sort(Comparator<? super Boolean> comparator) {
        sort(BooleanComparators.asBooleanComparator(comparator));
    }

    default void sort(BooleanComparator booleanComparator) {
        if (booleanComparator == null) {
            unstableSort(booleanComparator);
            return;
        }
        boolean[] booleanArray = toBooleanArray();
        BooleanArrays.stableSort(booleanArray, booleanComparator);
        setElements(booleanArray);
    }

    default void unstableSort(BooleanComparator booleanComparator) {
        boolean[] booleanArray = toBooleanArray();
        if (booleanComparator == null) {
            BooleanArrays.unstableSort(booleanArray);
        } else {
            BooleanArrays.unstableSort(booleanArray, booleanComparator);
        }
        setElements(booleanArray);
    }
}
