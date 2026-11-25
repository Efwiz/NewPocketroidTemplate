package org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.RandomAccess;
import java.util.function.UnaryOperator;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Size64;

/* JADX INFO: loaded from: classes5.dex */
public interface ByteList extends ByteCollection, Comparable<List<? extends Byte>>, List<Byte> {
    void add(int i, byte b);

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteCollection
    boolean add(byte b);

    byte getByte(int i);

    void getElements(int i, byte[] bArr, int i2, int i3);

    int indexOf(byte b);

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteIterable
    ByteListIterator iterator();

    int lastIndexOf(byte b);

    @Override // java.util.List
    ByteListIterator listIterator();

    @Override // java.util.List
    ByteListIterator listIterator(int i);

    byte removeByte(int i);

    void removeElements(int i, int i2);

    byte set(int i, byte b);

    @Override // java.util.List
    ByteList subList(int i, int i2);

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteList, java.util.List
    default ByteSpliterator spliterator() {
        if (this instanceof RandomAccess) {
            return new AbstractByteList.IndexBasedSpliterator(this, 0);
        }
        return ByteSpliterators.asSpliterator(iterator(), Size64.sizeOf(this), 16720);
    }

    default void setElements(byte[] bArr) {
        setElements(0, bArr);
    }

    default void setElements(int i, byte[] bArr) {
        setElements(i, bArr, 0, bArr.length);
    }

    default void setElements(int i, byte[] bArr, int i2, int i3) {
        if (i < 0) {
            throw new IndexOutOfBoundsException("Index (" + i + ") is negative");
        }
        if (i > size()) {
            throw new IndexOutOfBoundsException("Index (" + i + ") is greater than list size (" + size() + Constant.AFTER_QUTO);
        }
        ByteArrays.ensureOffsetLength(bArr, i2, i3);
        int i4 = i + i3;
        if (i4 > size()) {
            throw new IndexOutOfBoundsException("End index (" + i4 + ") is greater than list size (" + size() + Constant.AFTER_QUTO);
        }
        ByteListIterator byteListIteratorListIterator = listIterator(i);
        for (int i5 = 0; i5 < i3; i5++) {
            byteListIteratorListIterator.nextByte();
            byteListIteratorListIterator.set(bArr[i5 + i2]);
        }
    }

    @Override // java.util.List
    @Deprecated
    default void add(int i, Byte b) {
        add(i, b.byteValue());
    }

    default void replaceAll(ByteUnaryOperator byteUnaryOperator) {
        ByteListIterator byteListIteratorListIterator = listIterator();
        while (byteListIteratorListIterator.hasNext()) {
            byteListIteratorListIterator.set(byteUnaryOperator.apply(byteListIteratorListIterator.nextByte()));
        }
    }

    @Override // java.util.List
    @Deprecated
    default void replaceAll(final UnaryOperator<Byte> unaryOperator) {
        ByteUnaryOperator byteUnaryOperator;
        Objects.requireNonNull(unaryOperator);
        if (unaryOperator instanceof ByteUnaryOperator) {
            byteUnaryOperator = (ByteUnaryOperator) unaryOperator;
        } else {
            Objects.requireNonNull(unaryOperator);
            byteUnaryOperator = new ByteUnaryOperator() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteList$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteUnaryOperator
                public final byte apply(byte b) {
                    return ((Byte) unaryOperator.apply(Byte.valueOf(b))).byteValue();
                }
            };
        }
        replaceAll(byteUnaryOperator);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteCollection, java.util.Collection
    @Deprecated
    default boolean contains(Object obj) {
        return super.contains(obj);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.List
    @Deprecated
    default Byte get(int i) {
        return Byte.valueOf(getByte(i));
    }

    @Override // java.util.List
    @Deprecated
    default int indexOf(Object obj) {
        return indexOf(((Byte) obj).byteValue());
    }

    @Override // java.util.List
    @Deprecated
    default int lastIndexOf(Object obj) {
        return lastIndexOf(((Byte) obj).byteValue());
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteCollection, java.util.Collection
    @Deprecated
    default boolean add(Byte b) {
        return add(b.byteValue());
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes.ByteCollection, java.util.Collection
    @Deprecated
    default boolean remove(Object obj) {
        return super.remove(obj);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.List
    @Deprecated
    default Byte remove(int i) {
        return Byte.valueOf(removeByte(i));
    }

    @Override // java.util.List
    @Deprecated
    default Byte set(int i, Byte b) {
        return Byte.valueOf(set(i, b.byteValue()));
    }

    @Override // java.util.List
    @Deprecated
    default void sort(Comparator<? super Byte> comparator) {
        sort(ByteComparators.asByteComparator(comparator));
    }

    default void sort(ByteComparator byteComparator) {
        if (byteComparator == null) {
            unstableSort(byteComparator);
            return;
        }
        byte[] byteArray = toByteArray();
        ByteArrays.stableSort(byteArray, byteComparator);
        setElements(byteArray);
    }

    default void unstableSort(ByteComparator byteComparator) {
        byte[] byteArray = toByteArray();
        if (byteComparator == null) {
            ByteArrays.unstableSort(byteArray);
        } else {
            ByteArrays.unstableSort(byteArray, byteComparator);
        }
        setElements(byteArray);
    }
}
