package com.reandroid.utils.collection;

import com.reandroid.common.ArraySupplier;
import java.util.Iterator;

/* JADX INFO: loaded from: classes11.dex */
public class ArraySupplierIterator<T> implements Iterator<T>, SizedIterator {
    private int index;
    private final int mLength;
    private final int mStart;
    private final ArraySupplier<? extends T> supplier;

    public ArraySupplierIterator(ArraySupplier<? extends T> arraySupplier, int i, int i2) {
        this.supplier = arraySupplier;
        this.mStart = i;
        this.mLength = i2;
    }

    public ArraySupplierIterator(ArraySupplier<? extends T> arraySupplier) {
        this(arraySupplier, 0, -1);
    }

    @Override // com.reandroid.utils.collection.SizedIterator
    public int getRemainingSize() {
        return getLength() - this.index;
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        return this.index < getLength();
    }

    @Override // java.util.Iterator
    public T next() {
        int i = this.mStart;
        int i2 = this.index;
        int i3 = i + i2;
        this.index = i2 + 1;
        return this.supplier.get(i3);
    }

    private int getLength() {
        int i = this.mLength;
        return i < 0 ? this.supplier.getCount() : i;
    }

    public static <T1> Iterator<T1> of(ArraySupplier<? extends T1> arraySupplier) {
        if (arraySupplier == null || arraySupplier.getCount() == 0) {
            return EmptyIterator.of();
        }
        return new ArraySupplierIterator(arraySupplier);
    }

    public static <T1> Iterator<T1> of(ArraySupplier<? extends T1> arraySupplier, int i, int i2) {
        if (arraySupplier == null || i2 <= 0 || arraySupplier.getCount() == 0) {
            return EmptyIterator.of();
        }
        return new ArraySupplierIterator(arraySupplier, i, i2);
    }
}
