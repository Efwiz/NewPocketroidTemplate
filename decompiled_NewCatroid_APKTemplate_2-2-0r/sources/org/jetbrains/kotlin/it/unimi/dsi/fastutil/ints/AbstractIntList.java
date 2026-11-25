package org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints;

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

/* JADX INFO: loaded from: classes5.dex */
public abstract class AbstractIntList extends AbstractIntCollection implements IntList, IntStack {
    protected AbstractIntList() {
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

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntList
    public void add(int i, int i2) {
        throw new UnsupportedOperationException();
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection
    public boolean add(int i) {
        add(size(), i);
        return true;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntList
    public int removeInt(int i) {
        throw new UnsupportedOperationException();
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntList
    public int set(int i, int i2) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List
    public boolean addAll(int i, Collection<? extends Integer> collection) {
        if (collection instanceof IntCollection) {
            return addAll(i, (IntCollection) collection);
        }
        ensureIndex(i);
        Iterator<? extends Integer> it = collection.iterator();
        boolean zHasNext = it.hasNext();
        while (it.hasNext()) {
            add(i, it.next().intValue());
            i++;
        }
        return zHasNext;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntCollection, java.util.AbstractCollection, java.util.Collection
    public boolean addAll(Collection<? extends Integer> collection) {
        return addAll(size(), collection);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSet, java.util.Set
    public IntListIterator iterator() {
        return listIterator();
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntList, java.util.List
    public IntListIterator listIterator() {
        return listIterator(0);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntList, java.util.List
    public IntListIterator listIterator(int i) {
        ensureIndex(i);
        return new IntIterators.AbstractIndexBasedListIterator(0, i) { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntList.1
            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterators.AbstractIndexBasedIterator
            protected final int get(int i2) {
                return AbstractIntList.this.getInt(i2);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterators.AbstractIndexBasedListIterator
            protected final void add(int i2, int i3) {
                AbstractIntList.this.add(i2, i3);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterators.AbstractIndexBasedListIterator
            protected final void set(int i2, int i3) {
                AbstractIntList.this.set(i2, i3);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterators.AbstractIndexBasedIterator
            protected final void remove(int i2) {
                AbstractIntList.this.removeInt(i2);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterators.AbstractIndexBasedIterator
            protected final int getMaxPos() {
                return AbstractIntList.this.size();
            }
        };
    }

    static final class IndexBasedSpliterator extends IntSpliterators.LateBindingSizeIndexBasedSpliterator {
        final IntList l;

        IndexBasedSpliterator(IntList intList, int i) {
            super(i);
            this.l = intList;
        }

        IndexBasedSpliterator(IntList intList, int i, int i2) {
            super(i, i2);
            this.l = intList;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSpliterators.LateBindingSizeIndexBasedSpliterator
        protected final int getMaxPosFromBackingStore() {
            return this.l.size();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSpliterators.AbstractIndexBasedSpliterator
        protected final int get(int i) {
            return this.l.getInt(i);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSpliterators.AbstractIndexBasedSpliterator
        public final IndexBasedSpliterator makeForSplit(int i, int i2) {
            return new IndexBasedSpliterator(this.l, i, i2);
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection
    public boolean contains(int i) {
        return indexOf(i) >= 0;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntList
    public int indexOf(int i) {
        IntListIterator intListIteratorListIterator = listIterator();
        while (intListIteratorListIterator.hasNext()) {
            if (i == intListIteratorListIterator.nextInt()) {
                return intListIteratorListIterator.previousIndex();
            }
        }
        return -1;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntList
    public int lastIndexOf(int i) {
        IntListIterator intListIteratorListIterator = listIterator(size());
        while (intListIteratorListIterator.hasPrevious()) {
            if (i == intListIteratorListIterator.previousInt()) {
                return intListIteratorListIterator.nextIndex();
            }
        }
        return -1;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntList, java.util.List
    public IntList subList(int i, int i2) {
        ensureIndex(i);
        ensureIndex(i2);
        if (i <= i2) {
            return this instanceof RandomAccess ? new IntRandomAccessSubList(this, i, i2) : new IntSubList(this, i, i2);
        }
        throw new IndexOutOfBoundsException("Start index (" + i + ") is greater than end index (" + i2 + Constant.AFTER_QUTO);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterable
    public void forEach(java.util.function.IntConsumer intConsumer) {
        if (this instanceof RandomAccess) {
            int size = size();
            for (int i = 0; i < size; i++) {
                intConsumer.accept(getInt(i));
            }
            return;
        }
        super.forEach(intConsumer);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntList
    public void removeElements(int i, int i2) {
        ensureIndex(i2);
        IntListIterator intListIteratorListIterator = listIterator(i);
        int i3 = i2 - i;
        if (i3 < 0) {
            throw new IllegalArgumentException("Start index (" + i + ") is greater than end index (" + i2 + Constant.AFTER_QUTO);
        }
        while (true) {
            int i4 = i3 - 1;
            if (i3 == 0) {
                return;
            }
            intListIteratorListIterator.nextInt();
            intListIteratorListIterator.remove();
            i3 = i4;
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntList
    public void getElements(int i, int[] iArr, int i2, int i3) {
        ensureIndex(i);
        IntArrays.ensureOffsetLength(iArr, i2, i3);
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
                iArr[i2] = getInt(i);
                i2++;
                i3 = i5;
                i++;
            }
        } else {
            IntListIterator intListIteratorListIterator = listIterator(i);
            while (true) {
                int i6 = i3 - 1;
                if (i3 == 0) {
                    return;
                }
                iArr[i2] = intListIteratorListIterator.nextInt();
                i2++;
                i3 = i6;
            }
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntList
    public void setElements(int i, int[] iArr, int i2, int i3) {
        ensureIndex(i);
        IntArrays.ensureOffsetLength(iArr, i2, i3);
        int i4 = i + i3;
        if (i4 > size()) {
            throw new IndexOutOfBoundsException("End index (" + i4 + ") is greater than list size (" + size() + Constant.AFTER_QUTO);
        }
        int i5 = 0;
        if (this instanceof RandomAccess) {
            while (i5 < i3) {
                set(i5 + i, iArr[i5 + i2]);
                i5++;
            }
        } else {
            IntListIterator intListIteratorListIterator = listIterator(i);
            while (i5 < i3) {
                intListIteratorListIterator.nextInt();
                intListIteratorListIterator.set(iArr[i5 + i2]);
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
        IntListIterator it = iterator();
        int size = size();
        int iNextInt = 1;
        while (true) {
            int i = size - 1;
            if (size == 0) {
                return iNextInt;
            }
            iNextInt = (iNextInt * 31) + it.nextInt();
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
        if (list instanceof IntList) {
            IntListIterator intListIteratorListIterator = listIterator();
            IntListIterator intListIteratorListIterator2 = ((IntList) list).listIterator();
            while (true) {
                int i = size - 1;
                if (size == 0) {
                    return true;
                }
                if (intListIteratorListIterator.nextInt() != intListIteratorListIterator2.nextInt()) {
                    return false;
                }
                size = i;
            }
        } else {
            IntListIterator intListIteratorListIterator3 = listIterator();
            ListIterator listIterator = list.listIterator();
            while (true) {
                int i2 = size - 1;
                if (size == 0) {
                    return true;
                }
                if (!Objects.equals(intListIteratorListIterator3.next(), listIterator.next())) {
                    return false;
                }
                size = i2;
            }
        }
    }

    @Override // java.lang.Comparable
    public int compareTo(List<? extends Integer> list) {
        if (list == this) {
            return 0;
        }
        if (list instanceof IntList) {
            IntListIterator intListIteratorListIterator = listIterator();
            IntListIterator intListIteratorListIterator2 = ((IntList) list).listIterator();
            while (intListIteratorListIterator.hasNext() && intListIteratorListIterator2.hasNext()) {
                int iCompare = Integer.compare(intListIteratorListIterator.nextInt(), intListIteratorListIterator2.nextInt());
                if (iCompare != 0) {
                    return iCompare;
                }
            }
            if (intListIteratorListIterator2.hasNext()) {
                return -1;
            }
            return intListIteratorListIterator.hasNext() ? 1 : 0;
        }
        IntListIterator intListIteratorListIterator3 = listIterator();
        ListIterator<? extends Integer> listIterator = list.listIterator();
        while (intListIteratorListIterator3.hasNext() && listIterator.hasNext()) {
            int iCompareTo = intListIteratorListIterator3.next().compareTo(listIterator.next());
            if (iCompareTo != 0) {
                return iCompareTo;
            }
        }
        if (listIterator.hasNext()) {
            return -1;
        }
        return intListIteratorListIterator3.hasNext() ? 1 : 0;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntStack
    public void push(int i) {
        add(i);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntStack
    public int popInt() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return removeInt(size() - 1);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection
    public boolean rem(int i) {
        int iIndexOf = indexOf(i);
        if (iIndexOf == -1) {
            return false;
        }
        removeInt(iIndexOf);
        return true;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection
    public int[] toIntArray() {
        int size = size();
        int[] iArr = new int[size];
        getElements(0, iArr, 0, size);
        return iArr;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntCollection
    public int[] toArray(int[] iArr) {
        int size = size();
        if (iArr.length < size) {
            iArr = Arrays.copyOf(iArr, size);
        }
        getElements(0, iArr, 0, size);
        return iArr;
    }

    public boolean addAll(int i, IntCollection intCollection) {
        ensureIndex(i);
        IntIterator it = intCollection.iterator();
        boolean zHasNext = it.hasNext();
        while (it.hasNext()) {
            add(i, it.nextInt());
            i++;
        }
        return zHasNext;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection
    public boolean addAll(IntCollection intCollection) {
        return addAll(size(), intCollection);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntCollection, java.util.AbstractCollection
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        IntListIterator it = iterator();
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
                sb.append(String.valueOf(it.nextInt()));
                size = i;
            } else {
                sb.append("]");
                return sb.toString();
            }
        }
    }

    public static class IntSubList extends AbstractIntList implements Serializable {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private static final long serialVersionUID = -7046029254386353129L;
        protected final int from;
        protected final IntList l;
        protected int to;

        /* JADX INFO: Access modifiers changed from: private */
        public boolean assertRange() {
            return true;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntList, java.lang.Comparable
        public /* bridge */ /* synthetic */ int compareTo(List<? extends Integer> list) {
            return super.compareTo(list);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSet, java.util.Set
        public /* bridge */ /* synthetic */ Iterator iterator() {
            return super.iterator();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSet, java.util.Set
        public /* bridge */ /* synthetic */ IntIterator iterator() {
            return super.iterator();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntList, java.util.List
        public /* bridge */ /* synthetic */ ListIterator listIterator() {
            return super.listIterator();
        }

        public IntSubList(IntList intList, int i, int i2) {
            this.l = intList;
            this.from = i;
            this.to = i2;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection
        public boolean add(int i) {
            this.l.add(this.to, i);
            this.to++;
            return true;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntList
        public void add(int i, int i2) {
            ensureIndex(i);
            this.l.add(this.from + i, i2);
            this.to++;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntList, java.util.List
        public boolean addAll(int i, Collection<? extends Integer> collection) {
            ensureIndex(i);
            this.to += collection.size();
            return this.l.addAll(this.from + i, collection);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntList
        public int getInt(int i) {
            ensureRestrictedIndex(i);
            return this.l.getInt(this.from + i);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntList
        public int removeInt(int i) {
            ensureRestrictedIndex(i);
            this.to--;
            return this.l.removeInt(this.from + i);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntList
        public int set(int i, int i2) {
            ensureRestrictedIndex(i);
            return this.l.set(this.from + i, i2);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return this.to - this.from;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntList
        public void getElements(int i, int[] iArr, int i2, int i3) {
            ensureIndex(i);
            if (i + i3 > size()) {
                throw new IndexOutOfBoundsException("End index (" + i + i3 + ") is greater than list size (" + size() + Constant.AFTER_QUTO);
            }
            this.l.getElements(this.from + i, iArr, i2, i3);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntList
        public void removeElements(int i, int i2) {
            ensureIndex(i);
            ensureIndex(i2);
            IntList intList = this.l;
            int i3 = this.from;
            intList.removeElements(i3 + i, i3 + i2);
            this.to -= i2 - i;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntList
        public void setElements(int i, int[] iArr, int i2, int i3) {
            ensureIndex(i);
            this.l.setElements(this.from + i, iArr, i2, i3);
        }

        private final class RandomAccessIter extends IntIterators.AbstractIndexBasedListIterator {
            static final /* synthetic */ boolean $assertionsDisabled = false;

            RandomAccessIter(int i) {
                super(0, i);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterators.AbstractIndexBasedIterator
            protected final int get(int i) {
                return IntSubList.this.l.getInt(IntSubList.this.from + i);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterators.AbstractIndexBasedListIterator
            protected final void add(int i, int i2) {
                IntSubList.this.add(i, i2);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterators.AbstractIndexBasedListIterator
            protected final void set(int i, int i2) {
                IntSubList.this.set(i, i2);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterators.AbstractIndexBasedIterator
            protected final void remove(int i) {
                IntSubList.this.removeInt(i);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterators.AbstractIndexBasedIterator
            protected final int getMaxPos() {
                return IntSubList.this.to - IntSubList.this.from;
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterators.AbstractIndexBasedListIterator, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntListIterator
            public void add(int i) {
                super.add(i);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterators.AbstractIndexBasedIterator, java.util.Iterator, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntListIterator, java.util.ListIterator
            public void remove() {
                super.remove();
            }
        }

        private class ParentWrappingIter implements IntListIterator {
            private IntListIterator parent;

            ParentWrappingIter(IntListIterator intListIterator) {
                this.parent = intListIterator;
            }

            @Override // java.util.ListIterator
            public int nextIndex() {
                return this.parent.nextIndex() - IntSubList.this.from;
            }

            @Override // java.util.ListIterator
            public int previousIndex() {
                return this.parent.previousIndex() - IntSubList.this.from;
            }

            @Override // java.util.Iterator, java.util.ListIterator
            public boolean hasNext() {
                return this.parent.nextIndex() < IntSubList.this.to;
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.BidirectionalIterator, java.util.ListIterator
            public boolean hasPrevious() {
                return this.parent.previousIndex() >= IntSubList.this.from;
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterator, java.util.PrimitiveIterator.OfInt
            public int nextInt() {
                if (hasNext()) {
                    return this.parent.nextInt();
                }
                throw new NoSuchElementException();
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntBidirectionalIterator
            public int previousInt() {
                if (hasPrevious()) {
                    return this.parent.previousInt();
                }
                throw new NoSuchElementException();
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntListIterator
            public void add(int i) {
                this.parent.add(i);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntListIterator
            public void set(int i) {
                this.parent.set(i);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntListIterator, java.util.Iterator, java.util.ListIterator
            public void remove() {
                this.parent.remove();
            }
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntList, java.util.List
        public IntListIterator listIterator(int i) {
            ensureIndex(i);
            return this.l instanceof RandomAccess ? new RandomAccessIter(i) : new ParentWrappingIter(this.l.listIterator(i + this.from));
        }

        @Override // java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSet, java.util.Set
        public IntSpliterator spliterator() {
            return this.l instanceof RandomAccess ? new IndexBasedSpliterator(this.l, this.from, this.to) : super.spliterator();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntList, java.util.List
        public IntList subList(int i, int i2) {
            ensureIndex(i);
            ensureIndex(i2);
            if (i > i2) {
                throw new IllegalArgumentException("Start index (" + i + ") is greater than end index (" + i2 + Constant.AFTER_QUTO);
            }
            return new IntSubList(this, i, i2);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection
        public boolean rem(int i) {
            int iIndexOf = indexOf(i);
            if (iIndexOf == -1) {
                return false;
            }
            this.to--;
            this.l.removeInt(this.from + iIndexOf);
            return true;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntList
        public boolean addAll(int i, IntCollection intCollection) {
            ensureIndex(i);
            return super.addAll(i, intCollection);
        }
    }

    public static class IntRandomAccessSubList extends IntSubList implements RandomAccess {
        private static final long serialVersionUID = -107070782945191929L;

        public IntRandomAccessSubList(IntList intList, int i, int i2) {
            super(intList, i, i2);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntList.IntSubList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntList, java.util.List
        public IntList subList(int i, int i2) {
            ensureIndex(i);
            ensureIndex(i2);
            if (i > i2) {
                throw new IllegalArgumentException("Start index (" + i + ") is greater than end index (" + i2 + Constant.AFTER_QUTO);
            }
            return new IntRandomAccessSubList(this, i, i2);
        }
    }
}
