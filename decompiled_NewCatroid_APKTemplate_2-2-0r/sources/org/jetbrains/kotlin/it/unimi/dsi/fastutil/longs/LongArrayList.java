package org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs;

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
public class LongArrayList extends AbstractLongList implements Serializable, Cloneable, RandomAccess {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final long serialVersionUID = -7046029254386353130L;
    protected transient long[] a;
    protected int size;

    private static final long[] copyArraySafe(long[] jArr, int i) {
        if (i == 0) {
            return LongArrays.EMPTY_ARRAY;
        }
        return Arrays.copyOf(jArr, i);
    }

    protected LongArrayList(long[] jArr, boolean z) {
        this.a = jArr;
    }

    private void initArrayFromCapacity(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("Initial capacity (" + i + ") is negative");
        }
        if (i == 0) {
            this.a = LongArrays.EMPTY_ARRAY;
        } else {
            this.a = new long[i];
        }
    }

    public LongArrayList(int i) {
        initArrayFromCapacity(i);
    }

    public LongArrayList() {
        this.a = LongArrays.DEFAULT_EMPTY_ARRAY;
    }

    private void grow(int i) {
        long[] jArr = this.a;
        if (i <= jArr.length) {
            return;
        }
        if (jArr != LongArrays.DEFAULT_EMPTY_ARRAY) {
            long[] jArr2 = this.a;
            i = (int) Math.max(Math.min(((long) jArr2.length) + ((long) (jArr2.length >> 1)), 2147483639L), i);
        } else if (i < 10) {
            i = 10;
        }
        this.a = LongArrays.forceCapacity(this.a, i, this.size);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongList
    public void add(int i, long j) {
        ensureIndex(i);
        grow(this.size + 1);
        int i2 = this.size;
        if (i != i2) {
            long[] jArr = this.a;
            System.arraycopy(jArr, i, jArr, i + 1, i2 - i);
        }
        this.a[i] = j;
        this.size++;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection
    public boolean add(long j) {
        grow(this.size + 1);
        long[] jArr = this.a;
        int i = this.size;
        this.size = i + 1;
        jArr[i] = j;
        return true;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongList
    public long getLong(int i) {
        if (i >= this.size) {
            throw new IndexOutOfBoundsException("Index (" + i + ") is greater than or equal to list size (" + this.size + Constant.AFTER_QUTO);
        }
        return this.a[i];
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongList
    public int indexOf(long j) {
        for (int i = 0; i < this.size; i++) {
            if (j == this.a[i]) {
                return i;
            }
        }
        return -1;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongList
    public int lastIndexOf(long j) {
        int i = this.size;
        while (true) {
            int i2 = i - 1;
            if (i == 0) {
                return -1;
            }
            if (j == this.a[i2]) {
                return i2;
            }
            i = i2;
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongList
    public long removeLong(int i) {
        int i2 = this.size;
        if (i >= i2) {
            throw new IndexOutOfBoundsException("Index (" + i + ") is greater than or equal to list size (" + this.size + Constant.AFTER_QUTO);
        }
        long[] jArr = this.a;
        long j = jArr[i];
        int i3 = i2 - 1;
        this.size = i3;
        if (i != i3) {
            System.arraycopy(jArr, i + 1, jArr, i, i3 - i);
        }
        return j;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection
    public boolean rem(long j) {
        int iIndexOf = indexOf(j);
        if (iIndexOf == -1) {
            return false;
        }
        removeLong(iIndexOf);
        return true;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongList
    public long set(int i, long j) {
        if (i >= this.size) {
            throw new IndexOutOfBoundsException("Index (" + i + ") is greater than or equal to list size (" + this.size + Constant.AFTER_QUTO);
        }
        long[] jArr = this.a;
        long j2 = jArr[i];
        jArr[i] = j;
        return j2;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongList, java.util.AbstractCollection, java.util.Collection, java.util.List
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

    private class SubList extends AbstractLongList.LongRandomAccessSubList {
        private static final long serialVersionUID = -3185226345314976296L;

        protected SubList(int i, int i2) {
            super(LongArrayList.this, i, i2);
        }

        private long[] getParentArray() {
            return LongArrayList.this.a;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongList.LongSubList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongList
        public long getLong(int i) {
            ensureRestrictedIndex(i);
            return LongArrayList.this.a[i + this.from];
        }

        private final class SubListIterator extends LongIterators.AbstractIndexBasedListIterator {
            SubListIterator(int i) {
                super(0, i);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongIterators.AbstractIndexBasedIterator
            protected final long get(int i) {
                return LongArrayList.this.a[SubList.this.from + i];
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongIterators.AbstractIndexBasedListIterator
            protected final void add(int i, long j) {
                SubList.this.add(i, j);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongIterators.AbstractIndexBasedListIterator
            protected final void set(int i, long j) {
                SubList.this.set(i, j);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongIterators.AbstractIndexBasedIterator
            protected final void remove(int i) {
                SubList.this.removeLong(i);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongIterators.AbstractIndexBasedIterator
            protected final int getMaxPos() {
                return SubList.this.to - SubList.this.from;
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongIterators.AbstractIndexBasedIterator, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongIterator, java.util.PrimitiveIterator.OfLong
            public long nextLong() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                long[] jArr = LongArrayList.this.a;
                int i = SubList.this.from;
                int i2 = this.pos;
                this.pos = i2 + 1;
                this.lastReturned = i2;
                return jArr[i + i2];
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongIterators.AbstractIndexBasedListIterator, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongBidirectionalIterator
            public long previousLong() {
                if (!hasPrevious()) {
                    throw new NoSuchElementException();
                }
                long[] jArr = LongArrayList.this.a;
                int i = SubList.this.from;
                int i2 = this.pos - 1;
                this.pos = i2;
                this.lastReturned = i2;
                return jArr[i + i2];
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongIterators.AbstractIndexBasedIterator, java.util.PrimitiveIterator
            public void forEachRemaining(java.util.function.LongConsumer longConsumer) {
                int i = SubList.this.to - SubList.this.from;
                while (this.pos < i) {
                    long[] jArr = LongArrayList.this.a;
                    int i2 = SubList.this.from;
                    int i3 = this.pos;
                    this.pos = i3 + 1;
                    this.lastReturned = i3;
                    longConsumer.accept(jArr[i2 + i3]);
                }
            }
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongList.LongSubList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongList, java.util.List
        public LongListIterator listIterator(int i) {
            return new SubListIterator(i);
        }

        private final class SubListSpliterator extends LongSpliterators.LateBindingSizeIndexBasedSpliterator {
            SubListSpliterator() {
                super(SubList.this.from);
            }

            private SubListSpliterator(int i, int i2) {
                super(i, i2);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongSpliterators.LateBindingSizeIndexBasedSpliterator
            protected final int getMaxPosFromBackingStore() {
                return SubList.this.to;
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongSpliterators.AbstractIndexBasedSpliterator
            protected final long get(int i) {
                return LongArrayList.this.a[i];
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongSpliterators.AbstractIndexBasedSpliterator
            public final SubListSpliterator makeForSplit(int i, int i2) {
                return SubList.this.new SubListSpliterator(i, i2);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongSpliterators.AbstractIndexBasedSpliterator, java.util.Spliterator.OfPrimitive
            public boolean tryAdvance(java.util.function.LongConsumer longConsumer) {
                if (this.pos >= getMaxPos()) {
                    return false;
                }
                long[] jArr = LongArrayList.this.a;
                int i = this.pos;
                this.pos = i + 1;
                longConsumer.accept(jArr[i]);
                return true;
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongSpliterators.AbstractIndexBasedSpliterator, java.util.Spliterator.OfPrimitive
            public void forEachRemaining(java.util.function.LongConsumer longConsumer) {
                int maxPos = getMaxPos();
                while (this.pos < maxPos) {
                    long[] jArr = LongArrayList.this.a;
                    int i = this.pos;
                    this.pos = i + 1;
                    longConsumer.accept(jArr[i]);
                }
            }
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongList.LongSubList, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongList, java.util.List
        public LongSpliterator spliterator() {
            return new SubListSpliterator();
        }

        boolean contentsEquals(long[] jArr, int i, int i2) {
            if (LongArrayList.this.a == jArr && this.from == i && this.to == i2) {
                return true;
            }
            if (i2 - i != size()) {
                return false;
            }
            int i3 = this.from;
            while (i3 < this.to) {
                int i4 = i3 + 1;
                long j = LongArrayList.this.a[i3];
                int i5 = i + 1;
                if (j != jArr[i]) {
                    return false;
                }
                i = i5;
                i3 = i4;
            }
            return true;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongList, java.util.Collection, java.util.List
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj == null || !(obj instanceof List)) {
                return false;
            }
            if (obj instanceof LongArrayList) {
                LongArrayList longArrayList = (LongArrayList) obj;
                return contentsEquals(longArrayList.a, 0, longArrayList.size());
            }
            if (obj instanceof SubList) {
                SubList subList = (SubList) obj;
                return contentsEquals(subList.getParentArray(), subList.from, subList.to);
            }
            return super.equals(obj);
        }

        int contentsCompareTo(long[] jArr, int i, int i2) {
            if (LongArrayList.this.a == jArr && this.from == i && this.to == i2) {
                return 0;
            }
            int i3 = this.from;
            while (i3 < this.to && i3 < i2) {
                int iCompare = Long.compare(LongArrayList.this.a[i3], jArr[i]);
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

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongList.LongSubList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongList, java.lang.Comparable
        public int compareTo(List<? extends Long> list) {
            if (list instanceof LongArrayList) {
                LongArrayList longArrayList = (LongArrayList) list;
                return contentsCompareTo(longArrayList.a, 0, longArrayList.size());
            }
            if (list instanceof SubList) {
                SubList subList = (SubList) list;
                return contentsCompareTo(subList.getParentArray(), subList.from, subList.to);
            }
            return super.compareTo(list);
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongList, java.util.List
    public LongList subList(int i, int i2) {
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

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongList
    public void getElements(int i, long[] jArr, int i2, int i3) {
        LongArrays.ensureOffsetLength(jArr, i2, i3);
        System.arraycopy(this.a, i, jArr, i2, i3);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongList
    public void removeElements(int i, int i2) {
        org.jetbrains.kotlin.it.unimi.dsi.fastutil.Arrays.ensureFromTo(this.size, i, i2);
        long[] jArr = this.a;
        System.arraycopy(jArr, i2, jArr, i, this.size - i2);
        this.size -= i2 - i;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongList
    public void setElements(int i, long[] jArr, int i2, int i3) {
        ensureIndex(i);
        LongArrays.ensureOffsetLength(jArr, i2, i3);
        int i4 = i + i3;
        if (i4 > this.size) {
            throw new IndexOutOfBoundsException("End index (" + i4 + ") is greater than list size (" + this.size + Constant.AFTER_QUTO);
        }
        System.arraycopy(jArr, i2, this.a, i, i3);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongIterable
    public void forEach(java.util.function.LongConsumer longConsumer) {
        for (int i = 0; i < this.size; i++) {
            longConsumer.accept(this.a[i]);
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongList
    public boolean addAll(int i, LongCollection longCollection) {
        if (longCollection instanceof LongList) {
            return addAll(i, (LongList) longCollection);
        }
        ensureIndex(i);
        int size = longCollection.size();
        if (size == 0) {
            return false;
        }
        grow(this.size + size);
        long[] jArr = this.a;
        System.arraycopy(jArr, i, jArr, i + size, this.size - i);
        LongIterator it = longCollection.iterator();
        this.size += size;
        while (true) {
            int i2 = size - 1;
            if (size == 0) {
                return true;
            }
            this.a[i] = it.nextLong();
            size = i2;
            i++;
        }
    }

    public boolean addAll(int i, LongList longList) {
        ensureIndex(i);
        int size = longList.size();
        if (size == 0) {
            return false;
        }
        grow(this.size + size);
        long[] jArr = this.a;
        System.arraycopy(jArr, i, jArr, i + size, this.size - i);
        longList.getElements(0, this.a, i, size);
        this.size += size;
        return true;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongCollection
    public boolean removeAll(LongCollection longCollection) {
        int i;
        long[] jArr = this.a;
        int i2 = 0;
        int i3 = 0;
        while (true) {
            i = this.size;
            if (i2 >= i) {
                break;
            }
            if (!longCollection.contains(jArr[i2])) {
                jArr[i3] = jArr[i2];
                i3++;
            }
            i2++;
        }
        boolean z = i != i3;
        this.size = i3;
        return z;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongCollection
    public long[] toArray(long[] jArr) {
        if (jArr == null || jArr.length < this.size) {
            jArr = Arrays.copyOf(jArr, this.size);
        }
        System.arraycopy(this.a, 0, jArr, 0, this.size);
        return jArr;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongList, java.util.List
    public LongListIterator listIterator(int i) {
        ensureIndex(i);
        return new LongListIterator(i) { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongArrayList.1
            int last = -1;
            int pos;
            final /* synthetic */ int val$index;

            {
                this.val$index = i;
                this.pos = i;
            }

            @Override // java.util.Iterator, java.util.ListIterator
            public boolean hasNext() {
                return this.pos < LongArrayList.this.size;
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.BidirectionalIterator, java.util.ListIterator
            public boolean hasPrevious() {
                return this.pos > 0;
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongIterator, java.util.PrimitiveIterator.OfLong
            public long nextLong() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                long[] jArr = LongArrayList.this.a;
                int i2 = this.pos;
                this.pos = i2 + 1;
                this.last = i2;
                return jArr[i2];
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongBidirectionalIterator
            public long previousLong() {
                if (!hasPrevious()) {
                    throw new NoSuchElementException();
                }
                long[] jArr = LongArrayList.this.a;
                int i2 = this.pos - 1;
                this.pos = i2;
                this.last = i2;
                return jArr[i2];
            }

            @Override // java.util.ListIterator
            public int nextIndex() {
                return this.pos;
            }

            @Override // java.util.ListIterator
            public int previousIndex() {
                return this.pos - 1;
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongListIterator
            public void add(long j) {
                LongArrayList longArrayList = LongArrayList.this;
                int i2 = this.pos;
                this.pos = i2 + 1;
                longArrayList.add(i2, j);
                this.last = -1;
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongListIterator
            public void set(long j) {
                int i2 = this.last;
                if (i2 == -1) {
                    throw new IllegalStateException();
                }
                LongArrayList.this.set(i2, j);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongListIterator, java.util.Iterator, java.util.ListIterator
            public void remove() {
                int i2 = this.last;
                if (i2 == -1) {
                    throw new IllegalStateException();
                }
                LongArrayList.this.removeLong(i2);
                int i3 = this.last;
                int i4 = this.pos;
                if (i3 < i4) {
                    this.pos = i4 - 1;
                }
                this.last = -1;
            }

            @Override // java.util.PrimitiveIterator
            public void forEachRemaining(java.util.function.LongConsumer longConsumer) {
                while (this.pos < LongArrayList.this.size) {
                    long[] jArr = LongArrayList.this.a;
                    int i2 = this.pos;
                    this.pos = i2 + 1;
                    this.last = i2;
                    longConsumer.accept(jArr[i2]);
                }
            }
        };
    }

    private final class Spliterator implements LongSpliterator {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        boolean hasSplit;
        int max;
        int pos;

        @Override // java.util.Spliterator
        public int characteristics() {
            return 16720;
        }

        public Spliterator(LongArrayList longArrayList) {
            this(0, longArrayList.size, false);
        }

        private Spliterator(int i, int i2, boolean z) {
            this.pos = i;
            this.max = i2;
            this.hasSplit = z;
        }

        private int getWorkingMax() {
            return this.hasSplit ? this.max : LongArrayList.this.size;
        }

        @Override // java.util.Spliterator
        public long estimateSize() {
            return getWorkingMax() - this.pos;
        }

        @Override // java.util.Spliterator.OfPrimitive
        public boolean tryAdvance(java.util.function.LongConsumer longConsumer) {
            if (this.pos >= getWorkingMax()) {
                return false;
            }
            long[] jArr = LongArrayList.this.a;
            int i = this.pos;
            this.pos = i + 1;
            longConsumer.accept(jArr[i]);
            return true;
        }

        @Override // java.util.Spliterator.OfPrimitive
        public void forEachRemaining(java.util.function.LongConsumer longConsumer) {
            int workingMax = getWorkingMax();
            while (this.pos < workingMax) {
                longConsumer.accept(LongArrayList.this.a[this.pos]);
                this.pos++;
            }
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongSpliterator, java.util.Spliterator.OfLong, java.util.Spliterator.OfPrimitive, java.util.Spliterator
        public LongSpliterator trySplit() {
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
            return LongArrayList.this.new Spliterator(i, i3, true);
        }
    }

    @Override // java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongList, java.util.List
    public LongSpliterator spliterator() {
        return new Spliterator(this);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongList
    public void sort(LongComparator longComparator) {
        if (longComparator == null) {
            LongArrays.stableSort(this.a, 0, this.size);
        } else {
            LongArrays.stableSort(this.a, 0, this.size, longComparator);
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongList
    public void unstableSort(LongComparator longComparator) {
        if (longComparator == null) {
            LongArrays.unstableSort(this.a, 0, this.size);
        } else {
            LongArrays.unstableSort(this.a, 0, this.size, longComparator);
        }
    }

    public LongArrayList clone() {
        if (getClass() == LongArrayList.class) {
            LongArrayList longArrayList = new LongArrayList(copyArraySafe(this.a, this.size), false);
            longArrayList.size = this.size;
            return longArrayList;
        }
        try {
            LongArrayList longArrayList2 = (LongArrayList) super.clone();
            longArrayList2.a = copyArraySafe(this.a, this.size);
            return longArrayList2;
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
    }

    public boolean equals(LongArrayList longArrayList) {
        if (longArrayList == this) {
            return true;
        }
        int size = size();
        if (size != longArrayList.size()) {
            return false;
        }
        long[] jArr = this.a;
        long[] jArr2 = longArrayList.a;
        if (jArr == jArr2 && size == longArrayList.size()) {
            return true;
        }
        while (true) {
            int i = size - 1;
            if (size == 0) {
                return true;
            }
            if (jArr[i] != jArr2[i]) {
                return false;
            }
            size = i;
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongList, java.util.Collection, java.util.List
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || !(obj instanceof List)) {
            return false;
        }
        if (obj instanceof LongArrayList) {
            return equals((LongArrayList) obj);
        }
        if (obj instanceof SubList) {
            return ((SubList) obj).equals(this);
        }
        return super.equals(obj);
    }

    public int compareTo(LongArrayList longArrayList) {
        int size = size();
        int size2 = longArrayList.size();
        long[] jArr = this.a;
        long[] jArr2 = longArrayList.a;
        if (jArr == jArr2 && size == size2) {
            return 0;
        }
        int i = 0;
        while (i < size && i < size2) {
            int iCompare = Long.compare(jArr[i], jArr2[i]);
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

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongList, java.lang.Comparable
    public int compareTo(List<? extends Long> list) {
        if (list instanceof LongArrayList) {
            return compareTo((LongArrayList) list);
        }
        if (list instanceof SubList) {
            return -((SubList) list).compareTo((List<? extends Long>) this);
        }
        return super.compareTo(list);
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; i++) {
            objectOutputStream.writeLong(this.a[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        this.a = new long[this.size];
        for (int i = 0; i < this.size; i++) {
            this.a[i] = objectInputStream.readLong();
        }
    }
}
