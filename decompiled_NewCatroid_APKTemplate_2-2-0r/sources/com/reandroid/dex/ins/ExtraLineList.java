package com.reandroid.dex.ins;

import com.reandroid.common.ArraySupplier;
import com.reandroid.utils.collection.ArraySort;
import com.reandroid.utils.collection.ArraySupplierIterator;
import com.reandroid.utils.collection.EmptyIterator;
import com.reandroid.utils.collection.InstanceIterator;
import java.util.Iterator;

/* JADX INFO: loaded from: classes11.dex */
public class ExtraLineList implements ArraySupplier<ExtraLine>, Iterable<ExtraLine> {
    private static final int DEFAULT_CAPACITY = 3;
    public static final ExtraLineList EMPTY = new ExtraLineList() { // from class: com.reandroid.dex.ins.ExtraLineList.1
        @Override // com.reandroid.dex.ins.ExtraLineList
        public boolean contains(Object obj) {
            return false;
        }

        @Override // com.reandroid.dex.ins.ExtraLineList, com.reandroid.common.CountSupplier
        public int getCount() {
            return 0;
        }

        @Override // com.reandroid.dex.ins.ExtraLineList
        public boolean isEmpty() {
            return true;
        }

        @Override // com.reandroid.dex.ins.ExtraLineList
        public void trimToSize() {
        }

        @Override // com.reandroid.dex.ins.ExtraLineList
        public void add(Iterator<ExtraLine> it) {
            if (it != null && it.hasNext()) {
                throw new IllegalArgumentException("Empty ExtraLineList");
            }
        }

        @Override // com.reandroid.dex.ins.ExtraLineList
        public void add(ExtraLine extraLine) {
            if (extraLine != null) {
                throw new IllegalArgumentException("Empty ExtraLineList");
            }
        }

        @Override // com.reandroid.dex.ins.ExtraLineList, java.lang.Iterable
        public Iterator<ExtraLine> iterator() {
            return EmptyIterator.of();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.reandroid.dex.ins.ExtraLineList, com.reandroid.common.ArraySupplier
        public ExtraLine get(int i) {
            throw new IllegalArgumentException("Empty ExtraLineList");
        }
    };
    private ExtraLine[] elements;
    private int size;
    private boolean sorted;

    public boolean contains(Object obj) {
        ExtraLine[] extraLineArr;
        if (obj == null || (extraLineArr = this.elements) == null) {
            return false;
        }
        for (ExtraLine extraLine : extraLineArr) {
            if (obj.equals(extraLine)) {
                return true;
            }
        }
        return false;
    }

    public boolean isEmpty() {
        return getCount() == 0;
    }

    public <T1> Iterator<T1> iterator(Class<T1> cls) {
        return InstanceIterator.of(iterator(), cls);
    }

    @Override // java.lang.Iterable
    public Iterator<ExtraLine> iterator() {
        sort();
        return ArraySupplierIterator.of(this);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.reandroid.common.ArraySupplier
    public ExtraLine get(int i) {
        ExtraLine[] extraLineArr = this.elements;
        if (extraLineArr != null) {
            return extraLineArr[i];
        }
        return null;
    }

    @Override // com.reandroid.common.CountSupplier
    public int getCount() {
        return this.size;
    }

    private void sort() {
        int i;
        if (this.sorted) {
            return;
        }
        ExtraLine[] extraLineArr = this.elements;
        if (extraLineArr == null || (i = this.size) < 2) {
            this.sorted = true;
        } else {
            ArraySort.sort(extraLineArr, 0, i, ExtraLine.COMPARATOR);
            this.sorted = true;
        }
    }

    public void add(Iterator<ExtraLine> it) {
        while (it.hasNext()) {
            add(it.next());
        }
    }

    public void add(ExtraLine extraLine) {
        if (extraLine == null || contains(extraLine)) {
            return;
        }
        ensureCapacity();
        ExtraLine[] extraLineArr = this.elements;
        int i = this.size;
        extraLineArr[i] = extraLine;
        this.size = i + 1;
        this.sorted = false;
    }

    public void trimToSize() {
        if (availableCapacity() == 0) {
            return;
        }
        int i = this.size;
        if (i == 0) {
            this.elements = null;
            return;
        }
        boolean z = this.sorted;
        this.sorted = true;
        ExtraLine[] extraLineArr = new ExtraLine[i];
        System.arraycopy(this.elements, 0, extraLineArr, 0, i);
        this.elements = extraLineArr;
        this.sorted = z;
    }

    private void ensureCapacity() {
        ensureCapacity(this.size == 0 ? 1 : 3);
    }

    private void ensureCapacity(int i) {
        if (availableCapacity() >= i) {
            return;
        }
        int i2 = this.size;
        ExtraLine[] extraLineArr = new ExtraLine[i + i2];
        ExtraLine[] extraLineArr2 = this.elements;
        if (extraLineArr2 == null || i2 == 0) {
            this.elements = extraLineArr;
            return;
        }
        boolean z = this.sorted;
        this.sorted = true;
        System.arraycopy(extraLineArr2, 0, extraLineArr, 0, i2);
        this.elements = extraLineArr;
        this.sorted = z;
    }

    private int availableCapacity() {
        ExtraLine[] extraLineArr = this.elements;
        if (extraLineArr != null) {
            return extraLineArr.length - this.size;
        }
        return 0;
    }

    public static ExtraLineList add(ExtraLineList extraLineList, Iterator<ExtraLine> it) {
        if (it == null || !it.hasNext()) {
            return (extraLineList == null || extraLineList.isEmpty()) ? EMPTY : extraLineList;
        }
        if (extraLineList == null || extraLineList == EMPTY) {
            extraLineList = new ExtraLineList();
        }
        extraLineList.add(it);
        return extraLineList;
    }

    public static ExtraLineList add(ExtraLineList extraLineList, ExtraLine extraLine) {
        if (extraLine == null) {
            return extraLineList.isEmpty() ? EMPTY : extraLineList;
        }
        if (extraLineList == EMPTY) {
            extraLineList = new ExtraLineList();
        }
        extraLineList.add(extraLine);
        return extraLineList;
    }
}
