package org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.RandomAccess;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.HashCommon;

/* JADX INFO: loaded from: classes5.dex */
public abstract class AbstractLongList extends AbstractLongCollection implements LongList {
    protected AbstractLongList() {
    }

    protected void ensureIndex(int i) {
        if (i < 0) {
            throw new IndexOutOfBoundsException("Index (" + i + ") is negative");
        }
        if (i > size()) {
            throw new IndexOutOfBoundsException("Index (" + i + ") is greater than list size (" + size() + Constant.AFTER_QUTO);
        }
    }

    protected void ensureRestrictedIndex(int i) {
        if (i < 0) {
            throw new IndexOutOfBoundsException("Index (" + i + ") is negative");
        }
        if (i >= size()) {
            throw new IndexOutOfBoundsException("Index (" + i + ") is greater than or equal to list size (" + size() + Constant.AFTER_QUTO);
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongList
    public void add(int i, long j) {
        throw new UnsupportedOperationException();
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection
    public boolean add(long j) {
        add(size(), j);
        return true;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongList
    public long removeLong(int i) {
        throw new UnsupportedOperationException();
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongList
    public long set(int i, long j) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List
    public boolean addAll(int i, Collection<? extends Long> collection) {
        if (collection instanceof LongCollection) {
            return addAll(i, (LongCollection) collection);
        }
        ensureIndex(i);
        Iterator<? extends Long> it = collection.iterator();
        boolean zHasNext = it.hasNext();
        while (it.hasNext()) {
            add(i, it.next().longValue());
            i++;
        }
        return zHasNext;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongCollection, java.util.AbstractCollection, java.util.Collection
    public boolean addAll(Collection<? extends Long> collection) {
        return addAll(size(), collection);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongIterable
    public LongListIterator iterator() {
        return listIterator();
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongList, java.util.List
    public LongListIterator listIterator() {
        return listIterator(0);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongList, java.util.List
    public LongListIterator listIterator(int i) {
        ensureIndex(i);
        return new LongIterators.AbstractIndexBasedListIterator(0, i) { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongList.1
            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongIterators.AbstractIndexBasedIterator
            protected final long get(int i2) {
                return AbstractLongList.this.getLong(i2);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongIterators.AbstractIndexBasedListIterator
            protected final void add(int i2, long j) {
                AbstractLongList.this.add(i2, j);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongIterators.AbstractIndexBasedListIterator
            protected final void set(int i2, long j) {
                AbstractLongList.this.set(i2, j);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongIterators.AbstractIndexBasedIterator
            protected final void remove(int i2) {
                AbstractLongList.this.removeLong(i2);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongIterators.AbstractIndexBasedIterator
            protected final int getMaxPos() {
                return AbstractLongList.this.size();
            }
        };
    }

    static final class IndexBasedSpliterator extends LongSpliterators.LateBindingSizeIndexBasedSpliterator {
        final LongList l;

        IndexBasedSpliterator(LongList longList, int i) {
            super(i);
            this.l = longList;
        }

        IndexBasedSpliterator(LongList longList, int i, int i2) {
            super(i, i2);
            this.l = longList;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongSpliterators.LateBindingSizeIndexBasedSpliterator
        protected final int getMaxPosFromBackingStore() {
            return this.l.size();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongSpliterators.AbstractIndexBasedSpliterator
        protected final long get(int i) {
            return this.l.getLong(i);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongSpliterators.AbstractIndexBasedSpliterator
        public final IndexBasedSpliterator makeForSplit(int i, int i2) {
            return new IndexBasedSpliterator(this.l, i, i2);
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection
    public boolean contains(long j) {
        return indexOf(j) >= 0;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongList
    public int indexOf(long j) {
        LongListIterator longListIteratorListIterator = listIterator();
        while (longListIteratorListIterator.hasNext()) {
            if (j == longListIteratorListIterator.nextLong()) {
                return longListIteratorListIterator.previousIndex();
            }
        }
        return -1;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongList
    public int lastIndexOf(long j) {
        LongListIterator longListIteratorListIterator = listIterator(size());
        while (longListIteratorListIterator.hasPrevious()) {
            if (j == longListIteratorListIterator.previousLong()) {
                return longListIteratorListIterator.nextIndex();
            }
        }
        return -1;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongList, java.util.List
    public LongList subList(int i, int i2) {
        ensureIndex(i);
        ensureIndex(i2);
        if (i <= i2) {
            return this instanceof RandomAccess ? new LongRandomAccessSubList(this, i, i2) : new LongSubList(this, i, i2);
        }
        throw new IndexOutOfBoundsException("Start index (" + i + ") is greater than end index (" + i2 + Constant.AFTER_QUTO);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongIterable
    public void forEach(java.util.function.LongConsumer longConsumer) {
        if (this instanceof RandomAccess) {
            int size = size();
            for (int i = 0; i < size; i++) {
                longConsumer.accept(getLong(i));
            }
            return;
        }
        super.forEach(longConsumer);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongList
    public void removeElements(int i, int i2) {
        ensureIndex(i2);
        LongListIterator longListIteratorListIterator = listIterator(i);
        int i3 = i2 - i;
        if (i3 < 0) {
            throw new IllegalArgumentException("Start index (" + i + ") is greater than end index (" + i2 + Constant.AFTER_QUTO);
        }
        while (true) {
            int i4 = i3 - 1;
            if (i3 == 0) {
                return;
            }
            longListIteratorListIterator.nextLong();
            longListIteratorListIterator.remove();
            i3 = i4;
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongList
    public void getElements(int i, long[] jArr, int i2, int i3) {
        ensureIndex(i);
        LongArrays.ensureOffsetLength(jArr, i2, i3);
        int i4 = i + i3;
        if (i4 > size()) {
            throw new IndexOutOfBoundsException("End index (" + i4 + ") is greater than list size (" + size() + Constant.AFTER_QUTO);
        }
        if (this instanceof RandomAccess) {
            while (true) {
                int i5 = i3 - 1;
                if (i3 == 0) {
                    return;
                }
                jArr[i2] = getLong(i);
                i2++;
                i3 = i5;
                i++;
            }
        } else {
            LongListIterator longListIteratorListIterator = listIterator(i);
            while (true) {
                int i6 = i3 - 1;
                if (i3 == 0) {
                    return;
                }
                jArr[i2] = longListIteratorListIterator.nextLong();
                i2++;
                i3 = i6;
            }
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongList
    public void setElements(int i, long[] jArr, int i2, int i3) {
        ensureIndex(i);
        LongArrays.ensureOffsetLength(jArr, i2, i3);
        int i4 = i + i3;
        if (i4 > size()) {
            throw new IndexOutOfBoundsException("End index (" + i4 + ") is greater than list size (" + size() + Constant.AFTER_QUTO);
        }
        int i5 = 0;
        if (this instanceof RandomAccess) {
            while (i5 < i3) {
                set(i5 + i, jArr[i5 + i2]);
                i5++;
            }
        } else {
            LongListIterator longListIteratorListIterator = listIterator(i);
            while (i5 < i3) {
                longListIteratorListIterator.nextLong();
                longListIteratorListIterator.set(jArr[i5 + i2]);
                i5++;
            }
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public void clear() {
        removeElements(0, size());
    }

    @Override // java.util.Collection, java.util.List
    public int hashCode() {
        LongListIterator it = iterator();
        int size = size();
        int iLong2int = 1;
        while (true) {
            int i = size - 1;
            if (size == 0) {
                return iLong2int;
            }
            iLong2int = (iLong2int * 31) + HashCommon.long2int(it.nextLong());
            size = i;
        }
    }

    @Override // java.util.Collection, java.util.List
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof List)) {
            return false;
        }
        List list = (List) obj;
        int size = size();
        if (size != list.size()) {
            return false;
        }
        if (list instanceof LongList) {
            LongListIterator longListIteratorListIterator = listIterator();
            LongListIterator longListIteratorListIterator2 = ((LongList) list).listIterator();
            while (true) {
                int i = size - 1;
                if (size == 0) {
                    return true;
                }
                if (longListIteratorListIterator.nextLong() != longListIteratorListIterator2.nextLong()) {
                    return false;
                }
                size = i;
            }
        } else {
            LongListIterator longListIteratorListIterator3 = listIterator();
            ListIterator listIterator = list.listIterator();
            while (true) {
                int i2 = size - 1;
                if (size == 0) {
                    return true;
                }
                if (!Objects.equals(longListIteratorListIterator3.next(), listIterator.next())) {
                    return false;
                }
                size = i2;
            }
        }
    }

    @Override // java.lang.Comparable
    public int compareTo(List<? extends Long> list) {
        if (list == this) {
            return 0;
        }
        if (list instanceof LongList) {
            LongListIterator longListIteratorListIterator = listIterator();
            LongListIterator longListIteratorListIterator2 = ((LongList) list).listIterator();
            while (longListIteratorListIterator.hasNext() && longListIteratorListIterator2.hasNext()) {
                int iCompare = Long.compare(longListIteratorListIterator.nextLong(), longListIteratorListIterator2.nextLong());
                if (iCompare != 0) {
                    return iCompare;
                }
            }
            if (longListIteratorListIterator2.hasNext()) {
                return -1;
            }
            return longListIteratorListIterator.hasNext() ? 1 : 0;
        }
        LongListIterator longListIteratorListIterator3 = listIterator();
        ListIterator<? extends Long> listIterator = list.listIterator();
        while (longListIteratorListIterator3.hasNext() && listIterator.hasNext()) {
            int iCompareTo = longListIteratorListIterator3.next().compareTo(listIterator.next());
            if (iCompareTo != 0) {
                return iCompareTo;
            }
        }
        if (listIterator.hasNext()) {
            return -1;
        }
        return longListIteratorListIterator3.hasNext() ? 1 : 0;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection
    public boolean rem(long j) {
        int iIndexOf = indexOf(j);
        if (iIndexOf == -1) {
            return false;
        }
        removeLong(iIndexOf);
        return true;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection
    public long[] toLongArray() {
        int size = size();
        long[] jArr = new long[size];
        getElements(0, jArr, 0, size);
        return jArr;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongCollection
    public long[] toArray(long[] jArr) {
        int size = size();
        if (jArr.length < size) {
            jArr = Arrays.copyOf(jArr, size);
        }
        getElements(0, jArr, 0, size);
        return jArr;
    }

    public boolean addAll(int i, LongCollection longCollection) {
        ensureIndex(i);
        LongIterator it = longCollection.iterator();
        boolean zHasNext = it.hasNext();
        while (it.hasNext()) {
            add(i, it.nextLong());
            i++;
        }
        return zHasNext;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongCollection
    public boolean addAll(LongCollection longCollection) {
        return addAll(size(), longCollection);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongCollection, java.util.AbstractCollection
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        LongListIterator it = iterator();
        int size = size();
        boolean z = true;
        while (true) {
            int i = size - 1;
            if (size != 0) {
                if (z) {
                    z = false;
                } else {
                    sb.append(", ");
                }
                sb.append(String.valueOf(it.nextLong()));
                size = i;
            } else {
                sb.append("]");
                return sb.toString();
            }
        }
    }

    public static class LongSubList extends AbstractLongList implements Serializable {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private static final long serialVersionUID = -7046029254386353129L;
        protected final int from;
        protected final LongList l;
        protected int to;

        /* JADX INFO: Access modifiers changed from: private */
        public boolean assertRange() {
            return true;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongList, java.lang.Comparable
        public /* bridge */ /* synthetic */ int compareTo(List<? extends Long> list) {
            return super.compareTo(list);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongIterable
        public /* bridge */ /* synthetic */ Iterator iterator() {
            return super.iterator();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongIterable
        public /* bridge */ /* synthetic */ LongIterator iterator() {
            return super.iterator();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongList, java.util.List
        public /* bridge */ /* synthetic */ ListIterator listIterator() {
            return super.listIterator();
        }

        public LongSubList(LongList longList, int i, int i2) {
            this.l = longList;
            this.from = i;
            this.to = i2;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection
        public boolean add(long j) {
            this.l.add(this.to, j);
            this.to++;
            return true;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongList
        public void add(int i, long j) {
            ensureIndex(i);
            this.l.add(this.from + i, j);
            this.to++;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongList, java.util.List
        public boolean addAll(int i, Collection<? extends Long> collection) {
            ensureIndex(i);
            this.to += collection.size();
            return this.l.addAll(this.from + i, collection);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongList
        public long getLong(int i) {
            ensureRestrictedIndex(i);
            return this.l.getLong(this.from + i);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongList
        public long removeLong(int i) {
            ensureRestrictedIndex(i);
            this.to--;
            return this.l.removeLong(this.from + i);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongList
        public long set(int i, long j) {
            ensureRestrictedIndex(i);
            return this.l.set(this.from + i, j);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return this.to - this.from;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongList
        public void getElements(int i, long[] jArr, int i2, int i3) {
            ensureIndex(i);
            if (i + i3 > size()) {
                throw new IndexOutOfBoundsException("End index (" + i + i3 + ") is greater than list size (" + size() + Constant.AFTER_QUTO);
            }
            this.l.getElements(this.from + i, jArr, i2, i3);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongList
        public void removeElements(int i, int i2) {
            ensureIndex(i);
            ensureIndex(i2);
            LongList longList = this.l;
            int i3 = this.from;
            longList.removeElements(i3 + i, i3 + i2);
            this.to -= i2 - i;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongList
        public void setElements(int i, long[] jArr, int i2, int i3) {
            ensureIndex(i);
            this.l.setElements(this.from + i, jArr, i2, i3);
        }

        private final class RandomAccessIter extends LongIterators.AbstractIndexBasedListIterator {
            static final /* synthetic */ boolean $assertionsDisabled = false;

            RandomAccessIter(int i) {
                super(0, i);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongIterators.AbstractIndexBasedIterator
            protected final long get(int i) {
                return LongSubList.this.l.getLong(LongSubList.this.from + i);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongIterators.AbstractIndexBasedListIterator
            protected final void add(int i, long j) {
                LongSubList.this.add(i, j);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongIterators.AbstractIndexBasedListIterator
            protected final void set(int i, long j) {
                LongSubList.this.set(i, j);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongIterators.AbstractIndexBasedIterator
            protected final void remove(int i) {
                LongSubList.this.removeLong(i);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongIterators.AbstractIndexBasedIterator
            protected final int getMaxPos() {
                return LongSubList.this.to - LongSubList.this.from;
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongIterators.AbstractIndexBasedListIterator, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongListIterator
            public void add(long j) {
                super.add(j);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongIterators.AbstractIndexBasedIterator, java.util.Iterator, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongListIterator, java.util.ListIterator
            public void remove() {
                super.remove();
            }
        }

        private class ParentWrappingIter implements LongListIterator {
            private LongListIterator parent;

            ParentWrappingIter(LongListIterator longListIterator) {
                this.parent = longListIterator;
            }

            @Override // java.util.ListIterator
            public int nextIndex() {
                return this.parent.nextIndex() - LongSubList.this.from;
            }

            @Override // java.util.ListIterator
            public int previousIndex() {
                return this.parent.previousIndex() - LongSubList.this.from;
            }

            @Override // java.util.Iterator, java.util.ListIterator
            public boolean hasNext() {
                return this.parent.nextIndex() < LongSubList.this.to;
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.BidirectionalIterator, java.util.ListIterator
            public boolean hasPrevious() {
                return this.parent.previousIndex() >= LongSubList.this.from;
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongIterator, java.util.PrimitiveIterator.OfLong
            public long nextLong() {
                if (hasNext()) {
                    return this.parent.nextLong();
                }
                throw new NoSuchElementException();
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongBidirectionalIterator
            public long previousLong() {
                if (hasPrevious()) {
                    return this.parent.previousLong();
                }
                throw new NoSuchElementException();
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongListIterator
            public void add(long j) {
                this.parent.add(j);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongListIterator
            public void set(long j) {
                this.parent.set(j);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongListIterator, java.util.Iterator, java.util.ListIterator
            public void remove() {
                this.parent.remove();
            }
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongList, java.util.List
        public LongListIterator listIterator(int i) {
            ensureIndex(i);
            return this.l instanceof RandomAccess ? new RandomAccessIter(i) : new ParentWrappingIter(this.l.listIterator(i + this.from));
        }

        @Override // java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongList, java.util.List
        public LongSpliterator spliterator() {
            return this.l instanceof RandomAccess ? new IndexBasedSpliterator(this.l, this.from, this.to) : super.spliterator();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongList, java.util.List
        public LongList subList(int i, int i2) {
            ensureIndex(i);
            ensureIndex(i2);
            if (i > i2) {
                throw new IllegalArgumentException("Start index (" + i + ") is greater than end index (" + i2 + Constant.AFTER_QUTO);
            }
            return new LongSubList(this, i, i2);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection
        public boolean rem(long j) {
            int iIndexOf = indexOf(j);
            if (iIndexOf == -1) {
                return false;
            }
            this.to--;
            this.l.removeLong(this.from + iIndexOf);
            return true;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongList
        public boolean addAll(int i, LongCollection longCollection) {
            ensureIndex(i);
            return super.addAll(i, longCollection);
        }
    }

    public static class LongRandomAccessSubList extends LongSubList implements RandomAccess {
        private static final long serialVersionUID = -107070782945191929L;

        public LongRandomAccessSubList(LongList longList, int i, int i2) {
            super(longList, i, i2);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongList.LongSubList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongList, java.util.List
        public LongList subList(int i, int i2) {
            ensureIndex(i);
            ensureIndex(i2);
            if (i > i2) {
                throw new IllegalArgumentException("Start index (" + i + ") is greater than end index (" + i2 + Constant.AFTER_QUTO);
            }
            return new LongRandomAccessSubList(this, i, i2);
        }
    }
}
