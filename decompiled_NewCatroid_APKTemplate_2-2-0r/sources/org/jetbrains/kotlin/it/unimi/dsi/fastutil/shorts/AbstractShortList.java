package org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts;

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
public abstract class AbstractShortList extends AbstractShortCollection implements ShortList {
    protected AbstractShortList() {
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

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortList
    public void add(int i, short s) {
        throw new UnsupportedOperationException();
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.AbstractShortCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollection
    public boolean add(short s) {
        add(size(), s);
        return true;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortList
    public short removeShort(int i) {
        throw new UnsupportedOperationException();
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortList
    public short set(int i, short s) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List
    public boolean addAll(int i, Collection<? extends Short> collection) {
        if (collection instanceof ShortCollection) {
            return addAll(i, (ShortCollection) collection);
        }
        ensureIndex(i);
        Iterator<? extends Short> it = collection.iterator();
        boolean zHasNext = it.hasNext();
        while (it.hasNext()) {
            add(i, it.next().shortValue());
            i++;
        }
        return zHasNext;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.AbstractShortCollection, java.util.AbstractCollection, java.util.Collection
    public boolean addAll(Collection<? extends Short> collection) {
        return addAll(size(), collection);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.AbstractShortCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortIterable
    public ShortListIterator iterator() {
        return listIterator();
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortList, java.util.List
    public ShortListIterator listIterator() {
        return listIterator(0);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortList, java.util.List
    public ShortListIterator listIterator(int i) {
        ensureIndex(i);
        return new ShortIterators.AbstractIndexBasedListIterator(0, i) { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.AbstractShortList.1
            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortIterators.AbstractIndexBasedIterator
            protected final short get(int i2) {
                return AbstractShortList.this.getShort(i2);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortIterators.AbstractIndexBasedListIterator
            protected final void add(int i2, short s) {
                AbstractShortList.this.add(i2, s);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortIterators.AbstractIndexBasedListIterator
            protected final void set(int i2, short s) {
                AbstractShortList.this.set(i2, s);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortIterators.AbstractIndexBasedIterator
            protected final void remove(int i2) {
                AbstractShortList.this.removeShort(i2);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortIterators.AbstractIndexBasedIterator
            protected final int getMaxPos() {
                return AbstractShortList.this.size();
            }
        };
    }

    static final class IndexBasedSpliterator extends ShortSpliterators.LateBindingSizeIndexBasedSpliterator {
        final ShortList l;

        IndexBasedSpliterator(ShortList shortList, int i) {
            super(i);
            this.l = shortList;
        }

        IndexBasedSpliterator(ShortList shortList, int i, int i2) {
            super(i, i2);
            this.l = shortList;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortSpliterators.LateBindingSizeIndexBasedSpliterator
        protected final int getMaxPosFromBackingStore() {
            return this.l.size();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortSpliterators.AbstractIndexBasedSpliterator
        protected final short get(int i) {
            return this.l.getShort(i);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortSpliterators.AbstractIndexBasedSpliterator
        public final IndexBasedSpliterator makeForSplit(int i, int i2) {
            return new IndexBasedSpliterator(this.l, i, i2);
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.AbstractShortCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollection
    public boolean contains(short s) {
        return indexOf(s) >= 0;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortList
    public int indexOf(short s) {
        ShortListIterator shortListIteratorListIterator = listIterator();
        while (shortListIteratorListIterator.hasNext()) {
            if (s == shortListIteratorListIterator.nextShort()) {
                return shortListIteratorListIterator.previousIndex();
            }
        }
        return -1;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortList
    public int lastIndexOf(short s) {
        ShortListIterator shortListIteratorListIterator = listIterator(size());
        while (shortListIteratorListIterator.hasPrevious()) {
            if (s == shortListIteratorListIterator.previousShort()) {
                return shortListIteratorListIterator.nextIndex();
            }
        }
        return -1;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortList, java.util.List
    public ShortList subList(int i, int i2) {
        ensureIndex(i);
        ensureIndex(i2);
        if (i <= i2) {
            return this instanceof RandomAccess ? new ShortRandomAccessSubList(this, i, i2) : new ShortSubList(this, i, i2);
        }
        throw new IndexOutOfBoundsException("Start index (" + i + ") is greater than end index (" + i2 + Constant.AFTER_QUTO);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortIterable
    public void forEach(ShortConsumer shortConsumer) {
        if (this instanceof RandomAccess) {
            int size = size();
            for (int i = 0; i < size; i++) {
                shortConsumer.accept(getShort(i));
            }
            return;
        }
        super.forEach(shortConsumer);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortList
    public void removeElements(int i, int i2) {
        ensureIndex(i2);
        ShortListIterator shortListIteratorListIterator = listIterator(i);
        int i3 = i2 - i;
        if (i3 < 0) {
            throw new IllegalArgumentException("Start index (" + i + ") is greater than end index (" + i2 + Constant.AFTER_QUTO);
        }
        while (true) {
            int i4 = i3 - 1;
            if (i3 == 0) {
                return;
            }
            shortListIteratorListIterator.nextShort();
            shortListIteratorListIterator.remove();
            i3 = i4;
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortList
    public void getElements(int i, short[] sArr, int i2, int i3) {
        ensureIndex(i);
        ShortArrays.ensureOffsetLength(sArr, i2, i3);
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
                sArr[i2] = getShort(i);
                i2++;
                i3 = i5;
                i++;
            }
        } else {
            ShortListIterator shortListIteratorListIterator = listIterator(i);
            while (true) {
                int i6 = i3 - 1;
                if (i3 == 0) {
                    return;
                }
                sArr[i2] = shortListIteratorListIterator.nextShort();
                i2++;
                i3 = i6;
            }
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortList
    public void setElements(int i, short[] sArr, int i2, int i3) {
        ensureIndex(i);
        ShortArrays.ensureOffsetLength(sArr, i2, i3);
        int i4 = i + i3;
        if (i4 > size()) {
            throw new IndexOutOfBoundsException("End index (" + i4 + ") is greater than list size (" + size() + Constant.AFTER_QUTO);
        }
        int i5 = 0;
        if (this instanceof RandomAccess) {
            while (i5 < i3) {
                set(i5 + i, sArr[i5 + i2]);
                i5++;
            }
        } else {
            ShortListIterator shortListIteratorListIterator = listIterator(i);
            while (i5 < i3) {
                shortListIteratorListIterator.nextShort();
                shortListIteratorListIterator.set(sArr[i5 + i2]);
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
        ShortListIterator it = iterator();
        int size = size();
        int iNextShort = 1;
        while (true) {
            int i = size - 1;
            if (size == 0) {
                return iNextShort;
            }
            iNextShort = (iNextShort * 31) + it.nextShort();
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
        if (list instanceof ShortList) {
            ShortListIterator shortListIteratorListIterator = listIterator();
            ShortListIterator shortListIteratorListIterator2 = ((ShortList) list).listIterator();
            while (true) {
                int i = size - 1;
                if (size == 0) {
                    return true;
                }
                if (shortListIteratorListIterator.nextShort() != shortListIteratorListIterator2.nextShort()) {
                    return false;
                }
                size = i;
            }
        } else {
            ShortListIterator shortListIteratorListIterator3 = listIterator();
            ListIterator listIterator = list.listIterator();
            while (true) {
                int i2 = size - 1;
                if (size == 0) {
                    return true;
                }
                if (!Objects.equals(shortListIteratorListIterator3.next(), listIterator.next())) {
                    return false;
                }
                size = i2;
            }
        }
    }

    @Override // java.lang.Comparable
    public int compareTo(List<? extends Short> list) {
        if (list == this) {
            return 0;
        }
        if (list instanceof ShortList) {
            ShortListIterator shortListIteratorListIterator = listIterator();
            ShortListIterator shortListIteratorListIterator2 = ((ShortList) list).listIterator();
            while (shortListIteratorListIterator.hasNext() && shortListIteratorListIterator2.hasNext()) {
                int iCompare = Short.compare(shortListIteratorListIterator.nextShort(), shortListIteratorListIterator2.nextShort());
                if (iCompare != 0) {
                    return iCompare;
                }
            }
            if (shortListIteratorListIterator2.hasNext()) {
                return -1;
            }
            return shortListIteratorListIterator.hasNext() ? 1 : 0;
        }
        ShortListIterator shortListIteratorListIterator3 = listIterator();
        ListIterator<? extends Short> listIterator = list.listIterator();
        while (shortListIteratorListIterator3.hasNext() && listIterator.hasNext()) {
            int iCompareTo = shortListIteratorListIterator3.next().compareTo(listIterator.next());
            if (iCompareTo != 0) {
                return iCompareTo;
            }
        }
        if (listIterator.hasNext()) {
            return -1;
        }
        return shortListIteratorListIterator3.hasNext() ? 1 : 0;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.AbstractShortCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollection
    public boolean rem(short s) {
        int iIndexOf = indexOf(s);
        if (iIndexOf == -1) {
            return false;
        }
        removeShort(iIndexOf);
        return true;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.AbstractShortCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollection
    public short[] toShortArray() {
        int size = size();
        short[] sArr = new short[size];
        getElements(0, sArr, 0, size);
        return sArr;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.AbstractShortCollection
    public short[] toArray(short[] sArr) {
        int size = size();
        if (sArr.length < size) {
            sArr = Arrays.copyOf(sArr, size);
        }
        getElements(0, sArr, 0, size);
        return sArr;
    }

    public boolean addAll(int i, ShortCollection shortCollection) {
        ensureIndex(i);
        ShortIterator it = shortCollection.iterator();
        boolean zHasNext = it.hasNext();
        while (it.hasNext()) {
            add(i, it.nextShort());
            i++;
        }
        return zHasNext;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.AbstractShortCollection
    public boolean addAll(ShortCollection shortCollection) {
        return addAll(size(), shortCollection);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.AbstractShortCollection, java.util.AbstractCollection
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        ShortListIterator it = iterator();
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
                sb.append(String.valueOf((int) it.nextShort()));
                size = i;
            } else {
                sb.append("]");
                return sb.toString();
            }
        }
    }

    public static class ShortSubList extends AbstractShortList implements Serializable {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private static final long serialVersionUID = -7046029254386353129L;
        protected final int from;
        protected final ShortList l;
        protected int to;

        /* JADX INFO: Access modifiers changed from: private */
        public boolean assertRange() {
            return true;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.AbstractShortList, java.lang.Comparable
        public /* bridge */ /* synthetic */ int compareTo(List<? extends Short> list) {
            return super.compareTo(list);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.AbstractShortList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.AbstractShortCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortIterable
        public /* bridge */ /* synthetic */ Iterator iterator() {
            return super.iterator();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.AbstractShortList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.AbstractShortCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortIterable
        public /* bridge */ /* synthetic */ ShortIterator iterator() {
            return super.iterator();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.AbstractShortList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortList, java.util.List
        public /* bridge */ /* synthetic */ ListIterator listIterator() {
            return super.listIterator();
        }

        public ShortSubList(ShortList shortList, int i, int i2) {
            this.l = shortList;
            this.from = i;
            this.to = i2;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.AbstractShortList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.AbstractShortCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollection
        public boolean add(short s) {
            this.l.add(this.to, s);
            this.to++;
            return true;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.AbstractShortList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortList
        public void add(int i, short s) {
            ensureIndex(i);
            this.l.add(this.from + i, s);
            this.to++;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.AbstractShortList, java.util.List
        public boolean addAll(int i, Collection<? extends Short> collection) {
            ensureIndex(i);
            this.to += collection.size();
            return this.l.addAll(this.from + i, collection);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortList
        public short getShort(int i) {
            ensureRestrictedIndex(i);
            return this.l.getShort(this.from + i);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.AbstractShortList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortList
        public short removeShort(int i) {
            ensureRestrictedIndex(i);
            this.to--;
            return this.l.removeShort(this.from + i);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.AbstractShortList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortList
        public short set(int i, short s) {
            ensureRestrictedIndex(i);
            return this.l.set(this.from + i, s);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return this.to - this.from;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.AbstractShortList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortList
        public void getElements(int i, short[] sArr, int i2, int i3) {
            ensureIndex(i);
            if (i + i3 > size()) {
                throw new IndexOutOfBoundsException("End index (" + i + i3 + ") is greater than list size (" + size() + Constant.AFTER_QUTO);
            }
            this.l.getElements(this.from + i, sArr, i2, i3);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.AbstractShortList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortList
        public void removeElements(int i, int i2) {
            ensureIndex(i);
            ensureIndex(i2);
            ShortList shortList = this.l;
            int i3 = this.from;
            shortList.removeElements(i3 + i, i3 + i2);
            this.to -= i2 - i;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.AbstractShortList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortList
        public void setElements(int i, short[] sArr, int i2, int i3) {
            ensureIndex(i);
            this.l.setElements(this.from + i, sArr, i2, i3);
        }

        private final class RandomAccessIter extends ShortIterators.AbstractIndexBasedListIterator {
            static final /* synthetic */ boolean $assertionsDisabled = false;

            RandomAccessIter(int i) {
                super(0, i);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortIterators.AbstractIndexBasedIterator
            protected final short get(int i) {
                return ShortSubList.this.l.getShort(ShortSubList.this.from + i);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortIterators.AbstractIndexBasedListIterator
            protected final void add(int i, short s) {
                ShortSubList.this.add(i, s);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortIterators.AbstractIndexBasedListIterator
            protected final void set(int i, short s) {
                ShortSubList.this.set(i, s);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortIterators.AbstractIndexBasedIterator
            protected final void remove(int i) {
                ShortSubList.this.removeShort(i);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortIterators.AbstractIndexBasedIterator
            protected final int getMaxPos() {
                return ShortSubList.this.to - ShortSubList.this.from;
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortIterators.AbstractIndexBasedListIterator, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortListIterator
            public void add(short s) {
                super.add(s);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortIterators.AbstractIndexBasedIterator, java.util.Iterator, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortListIterator, java.util.ListIterator
            public void remove() {
                super.remove();
            }
        }

        private class ParentWrappingIter implements ShortListIterator {
            private ShortListIterator parent;

            ParentWrappingIter(ShortListIterator shortListIterator) {
                this.parent = shortListIterator;
            }

            @Override // java.util.ListIterator
            public int nextIndex() {
                return this.parent.nextIndex() - ShortSubList.this.from;
            }

            @Override // java.util.ListIterator
            public int previousIndex() {
                return this.parent.previousIndex() - ShortSubList.this.from;
            }

            @Override // java.util.Iterator, java.util.ListIterator
            public boolean hasNext() {
                return this.parent.nextIndex() < ShortSubList.this.to;
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.BidirectionalIterator, java.util.ListIterator
            public boolean hasPrevious() {
                return this.parent.previousIndex() >= ShortSubList.this.from;
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortIterator
            public short nextShort() {
                if (hasNext()) {
                    return this.parent.nextShort();
                }
                throw new NoSuchElementException();
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortBidirectionalIterator
            public short previousShort() {
                if (hasPrevious()) {
                    return this.parent.previousShort();
                }
                throw new NoSuchElementException();
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortListIterator
            public void add(short s) {
                this.parent.add(s);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortListIterator
            public void set(short s) {
                this.parent.set(s);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortListIterator, java.util.Iterator, java.util.ListIterator
            public void remove() {
                this.parent.remove();
            }
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.AbstractShortList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortList, java.util.List
        public ShortListIterator listIterator(int i) {
            ensureIndex(i);
            return this.l instanceof RandomAccess ? new RandomAccessIter(i) : new ParentWrappingIter(this.l.listIterator(i + this.from));
        }

        @Override // java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortIterable
        public ShortSpliterator spliterator() {
            return this.l instanceof RandomAccess ? new IndexBasedSpliterator(this.l, this.from, this.to) : super.spliterator();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.AbstractShortList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortList, java.util.List
        public ShortList subList(int i, int i2) {
            ensureIndex(i);
            ensureIndex(i2);
            if (i > i2) {
                throw new IllegalArgumentException("Start index (" + i + ") is greater than end index (" + i2 + Constant.AFTER_QUTO);
            }
            return new ShortSubList(this, i, i2);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.AbstractShortList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.AbstractShortCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollection
        public boolean rem(short s) {
            int iIndexOf = indexOf(s);
            if (iIndexOf == -1) {
                return false;
            }
            this.to--;
            this.l.removeShort(this.from + iIndexOf);
            return true;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.AbstractShortList
        public boolean addAll(int i, ShortCollection shortCollection) {
            ensureIndex(i);
            return super.addAll(i, shortCollection);
        }
    }

    public static class ShortRandomAccessSubList extends ShortSubList implements RandomAccess {
        private static final long serialVersionUID = -107070782945191929L;

        public ShortRandomAccessSubList(ShortList shortList, int i, int i2) {
            super(shortList, i, i2);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.AbstractShortList.ShortSubList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.AbstractShortList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortList, java.util.List
        public ShortList subList(int i, int i2) {
            ensureIndex(i);
            ensureIndex(i2);
            if (i > i2) {
                throw new IllegalArgumentException("Start index (" + i + ") is greater than end index (" + i2 + Constant.AFTER_QUTO);
            }
            return new ShortRandomAccessSubList(this, i, i2);
        }
    }
}
