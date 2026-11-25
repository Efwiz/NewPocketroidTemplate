package org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.RandomAccess;

/* JADX INFO: loaded from: classes5.dex */
public class IntArrayList extends AbstractIntList implements Serializable, Cloneable, RandomAccess {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final long serialVersionUID = -7046029254386353130L;
    protected transient int[] a;
    protected int size;

    private static final int[] copyArraySafe(int[] iArr, int i) {
        if (i == 0) {
            return IntArrays.EMPTY_ARRAY;
        }
        return Arrays.copyOf(iArr, i);
    }

    protected IntArrayList(int[] iArr, boolean z) {
        this.a = iArr;
    }

    private void initArrayFromCapacity(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("Initial capacity (" + i + ") is negative");
        }
        if (i == 0) {
            this.a = IntArrays.EMPTY_ARRAY;
        } else {
            this.a = new int[i];
        }
    }

    public IntArrayList(int i) {
        initArrayFromCapacity(i);
    }

    public IntArrayList() {
        this.a = IntArrays.DEFAULT_EMPTY_ARRAY;
    }

    public int[] elements() {
        return this.a;
    }

    private void grow(int i) {
        int[] iArr = this.a;
        if (i <= iArr.length) {
            return;
        }
        if (iArr != IntArrays.DEFAULT_EMPTY_ARRAY) {
            int[] iArr2 = this.a;
            i = (int) Math.max(Math.min(((long) iArr2.length) + ((long) (iArr2.length >> 1)), 2147483639L), i);
        } else if (i < 10) {
            i = 10;
        }
        this.a = IntArrays.forceCapacity(this.a, i, this.size);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntList
    public void add(int i, int i2) {
        ensureIndex(i);
        grow(this.size + 1);
        int i3 = this.size;
        if (i != i3) {
            int[] iArr = this.a;
            System.arraycopy(iArr, i, iArr, i + 1, i3 - i);
        }
        this.a[i] = i2;
        this.size++;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection
    public boolean add(int i) {
        grow(this.size + 1);
        int[] iArr = this.a;
        int i2 = this.size;
        this.size = i2 + 1;
        iArr[i2] = i;
        return true;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntList
    public int getInt(int i) {
        if (i >= this.size) {
            throw new IndexOutOfBoundsException("Index (" + i + ") is greater than or equal to list size (" + this.size + Constant.AFTER_QUTO);
        }
        return this.a[i];
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntList
    public int indexOf(int i) {
        for (int i2 = 0; i2 < this.size; i2++) {
            if (i == this.a[i2]) {
                return i2;
            }
        }
        return -1;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntList
    public int lastIndexOf(int i) {
        int i2 = this.size;
        while (true) {
            int i3 = i2 - 1;
            if (i2 == 0) {
                return -1;
            }
            if (i == this.a[i3]) {
                return i3;
            }
            i2 = i3;
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntList
    public int removeInt(int i) {
        int i2 = this.size;
        if (i >= i2) {
            throw new IndexOutOfBoundsException("Index (" + i + ") is greater than or equal to list size (" + this.size + Constant.AFTER_QUTO);
        }
        int[] iArr = this.a;
        int i3 = iArr[i];
        int i4 = i2 - 1;
        this.size = i4;
        if (i != i4) {
            System.arraycopy(iArr, i + 1, iArr, i, i4 - i);
        }
        return i3;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection
    public boolean rem(int i) {
        int iIndexOf = indexOf(i);
        if (iIndexOf == -1) {
            return false;
        }
        removeInt(iIndexOf);
        return true;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntList
    public int set(int i, int i2) {
        if (i >= this.size) {
            throw new IndexOutOfBoundsException("Index (" + i + ") is greater than or equal to list size (" + this.size + Constant.AFTER_QUTO);
        }
        int[] iArr = this.a;
        int i3 = iArr[i];
        iArr[i] = i2;
        return i3;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public void clear() {
        this.size = 0;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public int size() {
        return this.size;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean isEmpty() {
        return this.size == 0;
    }

    private class SubList extends AbstractIntList.IntRandomAccessSubList {
        private static final long serialVersionUID = -3185226345314976296L;

        protected SubList(int i, int i2) {
            super(IntArrayList.this, i, i2);
        }

        private int[] getParentArray() {
            return IntArrayList.this.a;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntList.IntSubList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntList
        public int getInt(int i) {
            ensureRestrictedIndex(i);
            return IntArrayList.this.a[i + this.from];
        }

        private final class SubListIterator extends IntIterators.AbstractIndexBasedListIterator {
            SubListIterator(int i) {
                super(0, i);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterators.AbstractIndexBasedIterator
            protected final int get(int i) {
                return IntArrayList.this.a[SubList.this.from + i];
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterators.AbstractIndexBasedListIterator
            protected final void add(int i, int i2) {
                SubList.this.add(i, i2);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterators.AbstractIndexBasedListIterator
            protected final void set(int i, int i2) {
                SubList.this.set(i, i2);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterators.AbstractIndexBasedIterator
            protected final void remove(int i) {
                SubList.this.removeInt(i);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterators.AbstractIndexBasedIterator
            protected final int getMaxPos() {
                return SubList.this.to - SubList.this.from;
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterators.AbstractIndexBasedIterator, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterator, java.util.PrimitiveIterator.OfInt
            public int nextInt() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                int[] iArr = IntArrayList.this.a;
                int i = SubList.this.from;
                int i2 = this.pos;
                this.pos = i2 + 1;
                this.lastReturned = i2;
                return iArr[i + i2];
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterators.AbstractIndexBasedListIterator, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntBidirectionalIterator
            public int previousInt() {
                if (!hasPrevious()) {
                    throw new NoSuchElementException();
                }
                int[] iArr = IntArrayList.this.a;
                int i = SubList.this.from;
                int i2 = this.pos - 1;
                this.pos = i2;
                this.lastReturned = i2;
                return iArr[i + i2];
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterators.AbstractIndexBasedIterator, java.util.PrimitiveIterator
            public void forEachRemaining(java.util.function.IntConsumer intConsumer) {
                int i = SubList.this.to - SubList.this.from;
                while (this.pos < i) {
                    int[] iArr = IntArrayList.this.a;
                    int i2 = SubList.this.from;
                    int i3 = this.pos;
                    this.pos = i3 + 1;
                    this.lastReturned = i3;
                    intConsumer.accept(iArr[i2 + i3]);
                }
            }
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntList.IntSubList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntList, java.util.List
        public IntListIterator listIterator(int i) {
            return new SubListIterator(i);
        }

        private final class SubListSpliterator extends IntSpliterators.LateBindingSizeIndexBasedSpliterator {
            SubListSpliterator() {
                super(SubList.this.from);
            }

            private SubListSpliterator(int i, int i2) {
                super(i, i2);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSpliterators.LateBindingSizeIndexBasedSpliterator
            protected final int getMaxPosFromBackingStore() {
                return SubList.this.to;
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSpliterators.AbstractIndexBasedSpliterator
            protected final int get(int i) {
                return IntArrayList.this.a[i];
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSpliterators.AbstractIndexBasedSpliterator
            public final SubListSpliterator makeForSplit(int i, int i2) {
                return SubList.this.new SubListSpliterator(i, i2);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSpliterators.AbstractIndexBasedSpliterator, java.util.Spliterator.OfPrimitive
            public boolean tryAdvance(java.util.function.IntConsumer intConsumer) {
                if (this.pos >= getMaxPos()) {
                    return false;
                }
                int[] iArr = IntArrayList.this.a;
                int i = this.pos;
                this.pos = i + 1;
                intConsumer.accept(iArr[i]);
                return true;
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSpliterators.AbstractIndexBasedSpliterator, java.util.Spliterator.OfPrimitive
            public void forEachRemaining(java.util.function.IntConsumer intConsumer) {
                int maxPos = getMaxPos();
                while (this.pos < maxPos) {
                    int[] iArr = IntArrayList.this.a;
                    int i = this.pos;
                    this.pos = i + 1;
                    intConsumer.accept(iArr[i]);
                }
            }
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntList.IntSubList, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSet, java.util.Set
        public IntSpliterator spliterator() {
            return new SubListSpliterator();
        }

        boolean contentsEquals(int[] iArr, int i, int i2) {
            if (IntArrayList.this.a == iArr && this.from == i && this.to == i2) {
                return true;
            }
            if (i2 - i != size()) {
                return false;
            }
            int i3 = this.from;
            while (i3 < this.to) {
                int i4 = i3 + 1;
                int i5 = i + 1;
                if (IntArrayList.this.a[i3] != iArr[i]) {
                    return false;
                }
                i = i5;
                i3 = i4;
            }
            return true;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntList, java.util.Collection, java.util.List
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj == null || !(obj instanceof List)) {
                return false;
            }
            if (obj instanceof IntArrayList) {
                IntArrayList intArrayList = (IntArrayList) obj;
                return contentsEquals(intArrayList.a, 0, intArrayList.size());
            }
            if (obj instanceof SubList) {
                SubList subList = (SubList) obj;
                return contentsEquals(subList.getParentArray(), subList.from, subList.to);
            }
            return super.equals(obj);
        }

        int contentsCompareTo(int[] iArr, int i, int i2) {
            if (IntArrayList.this.a == iArr && this.from == i && this.to == i2) {
                return 0;
            }
            int i3 = this.from;
            while (i3 < this.to && i3 < i2) {
                int iCompare = Integer.compare(IntArrayList.this.a[i3], iArr[i]);
                if (iCompare != 0) {
                    return iCompare;
                }
                i3++;
                i++;
            }
            if (i3 < i2) {
                return -1;
            }
            return i3 < this.to ? 1 : 0;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntList.IntSubList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntList, java.lang.Comparable
        public int compareTo(List<? extends Integer> list) {
            if (list instanceof IntArrayList) {
                IntArrayList intArrayList = (IntArrayList) list;
                return contentsCompareTo(intArrayList.a, 0, intArrayList.size());
            }
            if (list instanceof SubList) {
                SubList subList = (SubList) list;
                return contentsCompareTo(subList.getParentArray(), subList.from, subList.to);
            }
            return super.compareTo(list);
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntList, java.util.List
    public IntList subList(int i, int i2) {
        if (i == 0 && i2 == size()) {
            return this;
        }
        ensureIndex(i);
        ensureIndex(i2);
        if (i > i2) {
            throw new IndexOutOfBoundsException("Start index (" + i + ") is greater than end index (" + i2 + Constant.AFTER_QUTO);
        }
        return new SubList(i, i2);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntList
    public void getElements(int i, int[] iArr, int i2, int i3) {
        IntArrays.ensureOffsetLength(iArr, i2, i3);
        System.arraycopy(this.a, i, iArr, i2, i3);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntList
    public void removeElements(int i, int i2) {
        org.jetbrains.kotlin.it.unimi.dsi.fastutil.Arrays.ensureFromTo(this.size, i, i2);
        int[] iArr = this.a;
        System.arraycopy(iArr, i2, iArr, i, this.size - i2);
        this.size -= i2 - i;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntList
    public void setElements(int i, int[] iArr, int i2, int i3) {
        ensureIndex(i);
        IntArrays.ensureOffsetLength(iArr, i2, i3);
        int i4 = i + i3;
        if (i4 > this.size) {
            throw new IndexOutOfBoundsException("End index (" + i4 + ") is greater than list size (" + this.size + Constant.AFTER_QUTO);
        }
        System.arraycopy(iArr, i2, this.a, i, i3);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterable
    public void forEach(java.util.function.IntConsumer intConsumer) {
        for (int i = 0; i < this.size; i++) {
            intConsumer.accept(this.a[i]);
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntList
    public boolean addAll(int i, IntCollection intCollection) {
        if (intCollection instanceof IntList) {
            return addAll(i, (IntList) intCollection);
        }
        ensureIndex(i);
        int size = intCollection.size();
        if (size == 0) {
            return false;
        }
        grow(this.size + size);
        int[] iArr = this.a;
        System.arraycopy(iArr, i, iArr, i + size, this.size - i);
        IntIterator it = intCollection.iterator();
        this.size += size;
        while (true) {
            int i2 = size - 1;
            if (size == 0) {
                return true;
            }
            this.a[i] = it.nextInt();
            size = i2;
            i++;
        }
    }

    public boolean addAll(int i, IntList intList) {
        ensureIndex(i);
        int size = intList.size();
        if (size == 0) {
            return false;
        }
        grow(this.size + size);
        int[] iArr = this.a;
        System.arraycopy(iArr, i, iArr, i + size, this.size - i);
        intList.getElements(0, this.a, i, size);
        this.size += size;
        return true;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntCollection
    public boolean removeAll(IntCollection intCollection) {
        int i;
        int[] iArr = this.a;
        int i2 = 0;
        int i3 = 0;
        while (true) {
            i = this.size;
            if (i2 >= i) {
                break;
            }
            if (!intCollection.contains(iArr[i2])) {
                iArr[i3] = iArr[i2];
                i3++;
            }
            i2++;
        }
        boolean z = i != i3;
        this.size = i3;
        return z;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntCollection
    public int[] toArray(int[] iArr) {
        if (iArr == null || iArr.length < this.size) {
            iArr = Arrays.copyOf(iArr, this.size);
        }
        System.arraycopy(this.a, 0, iArr, 0, this.size);
        return iArr;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntList, java.util.List
    public IntListIterator listIterator(int i) {
        ensureIndex(i);
        return new IntListIterator(i) { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntArrayList.1
            int last = -1;
            int pos;
            final /* synthetic */ int val$index;

            {
                this.val$index = i;
                this.pos = i;
            }

            @Override // java.util.Iterator, java.util.ListIterator
            public boolean hasNext() {
                return this.pos < IntArrayList.this.size;
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.BidirectionalIterator, java.util.ListIterator
            public boolean hasPrevious() {
                return this.pos > 0;
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterator, java.util.PrimitiveIterator.OfInt
            public int nextInt() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                int[] iArr = IntArrayList.this.a;
                int i2 = this.pos;
                this.pos = i2 + 1;
                this.last = i2;
                return iArr[i2];
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntBidirectionalIterator
            public int previousInt() {
                if (!hasPrevious()) {
                    throw new NoSuchElementException();
                }
                int[] iArr = IntArrayList.this.a;
                int i2 = this.pos - 1;
                this.pos = i2;
                this.last = i2;
                return iArr[i2];
            }

            @Override // java.util.ListIterator
            public int nextIndex() {
                return this.pos;
            }

            @Override // java.util.ListIterator
            public int previousIndex() {
                return this.pos - 1;
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntListIterator
            public void add(int i2) {
                IntArrayList intArrayList = IntArrayList.this;
                int i3 = this.pos;
                this.pos = i3 + 1;
                intArrayList.add(i3, i2);
                this.last = -1;
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntListIterator
            public void set(int i2) {
                int i3 = this.last;
                if (i3 == -1) {
                    throw new IllegalStateException();
                }
                IntArrayList.this.set(i3, i2);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntListIterator, java.util.Iterator, java.util.ListIterator
            public void remove() {
                int i2 = this.last;
                if (i2 == -1) {
                    throw new IllegalStateException();
                }
                IntArrayList.this.removeInt(i2);
                int i3 = this.last;
                int i4 = this.pos;
                if (i3 < i4) {
                    this.pos = i4 - 1;
                }
                this.last = -1;
            }

            @Override // java.util.PrimitiveIterator
            public void forEachRemaining(java.util.function.IntConsumer intConsumer) {
                while (this.pos < IntArrayList.this.size) {
                    int[] iArr = IntArrayList.this.a;
                    int i2 = this.pos;
                    this.pos = i2 + 1;
                    this.last = i2;
                    intConsumer.accept(iArr[i2]);
                }
            }
        };
    }

    private final class Spliterator implements IntSpliterator {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        boolean hasSplit;
        int max;
        int pos;

        @Override // java.util.Spliterator
        public int characteristics() {
            return 16720;
        }

        public Spliterator(IntArrayList intArrayList) {
            this(0, intArrayList.size, false);
        }

        private Spliterator(int i, int i2, boolean z) {
            this.pos = i;
            this.max = i2;
            this.hasSplit = z;
        }

        private int getWorkingMax() {
            return this.hasSplit ? this.max : IntArrayList.this.size;
        }

        @Override // java.util.Spliterator
        public long estimateSize() {
            return getWorkingMax() - this.pos;
        }

        @Override // java.util.Spliterator.OfPrimitive
        public boolean tryAdvance(java.util.function.IntConsumer intConsumer) {
            if (this.pos >= getWorkingMax()) {
                return false;
            }
            int[] iArr = IntArrayList.this.a;
            int i = this.pos;
            this.pos = i + 1;
            intConsumer.accept(iArr[i]);
            return true;
        }

        @Override // java.util.Spliterator.OfPrimitive
        public void forEachRemaining(java.util.function.IntConsumer intConsumer) {
            int workingMax = getWorkingMax();
            while (this.pos < workingMax) {
                intConsumer.accept(IntArrayList.this.a[this.pos]);
                this.pos++;
            }
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSpliterator, java.util.Spliterator.OfInt, java.util.Spliterator.OfPrimitive, java.util.Spliterator
        public IntSpliterator trySplit() {
            int workingMax = getWorkingMax();
            int i = this.pos;
            int i2 = (workingMax - i) >> 1;
            if (i2 <= 1) {
                return null;
            }
            this.max = workingMax;
            int i3 = i2 + i;
            this.pos = i3;
            this.hasSplit = true;
            return IntArrayList.this.new Spliterator(i, i3, true);
        }
    }

    @Override // java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSet, java.util.Set
    public IntSpliterator spliterator() {
        return new Spliterator(this);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntList
    public void sort(IntComparator intComparator) {
        if (intComparator == null) {
            IntArrays.stableSort(this.a, 0, this.size);
        } else {
            IntArrays.stableSort(this.a, 0, this.size, intComparator);
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntList
    public void unstableSort(IntComparator intComparator) {
        if (intComparator == null) {
            IntArrays.unstableSort(this.a, 0, this.size);
        } else {
            IntArrays.unstableSort(this.a, 0, this.size, intComparator);
        }
    }

    public IntArrayList clone() {
        if (getClass() == IntArrayList.class) {
            IntArrayList intArrayList = new IntArrayList(copyArraySafe(this.a, this.size), false);
            intArrayList.size = this.size;
            return intArrayList;
        }
        try {
            IntArrayList intArrayList2 = (IntArrayList) super.clone();
            intArrayList2.a = copyArraySafe(this.a, this.size);
            return intArrayList2;
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
    }

    public boolean equals(IntArrayList intArrayList) {
        if (intArrayList == this) {
            return true;
        }
        int size = size();
        if (size != intArrayList.size()) {
            return false;
        }
        int[] iArr = this.a;
        int[] iArr2 = intArrayList.a;
        if (iArr == iArr2 && size == intArrayList.size()) {
            return true;
        }
        while (true) {
            int i = size - 1;
            if (size == 0) {
                return true;
            }
            if (iArr[i] != iArr2[i]) {
                return false;
            }
            size = i;
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntList, java.util.Collection, java.util.List
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || !(obj instanceof List)) {
            return false;
        }
        if (obj instanceof IntArrayList) {
            return equals((IntArrayList) obj);
        }
        if (obj instanceof SubList) {
            return ((SubList) obj).equals(this);
        }
        return super.equals(obj);
    }

    public int compareTo(IntArrayList intArrayList) {
        int size = size();
        int size2 = intArrayList.size();
        int[] iArr = this.a;
        int[] iArr2 = intArrayList.a;
        if (iArr == iArr2 && size == size2) {
            return 0;
        }
        int i = 0;
        while (i < size && i < size2) {
            int iCompare = Integer.compare(iArr[i], iArr2[i]);
            if (iCompare != 0) {
                return iCompare;
            }
            i++;
        }
        if (i < size2) {
            return -1;
        }
        return i < size ? 1 : 0;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntList, java.lang.Comparable
    public int compareTo(List<? extends Integer> list) {
        if (list instanceof IntArrayList) {
            return compareTo((IntArrayList) list);
        }
        if (list instanceof SubList) {
            return -((SubList) list).compareTo((List<? extends Integer>) this);
        }
        return super.compareTo(list);
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; i++) {
            objectOutputStream.writeInt(this.a[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        this.a = new int[this.size];
        for (int i = 0; i < this.size; i++) {
            this.a[i] = objectInputStream.readInt();
        }
    }
}
