package org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans;

import com.gaurav.avnc.vnc.XKeySym;
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
public abstract class AbstractBooleanList extends AbstractBooleanCollection implements BooleanList {
    protected AbstractBooleanList() {
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

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanList
    public void add(int i, boolean z) {
        throw new UnsupportedOperationException();
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanCollection
    public boolean add(boolean z) {
        add(size(), z);
        return true;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanList
    public boolean removeBoolean(int i) {
        throw new UnsupportedOperationException();
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanList
    public boolean set(int i, boolean z) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List
    public boolean addAll(int i, Collection<? extends Boolean> collection) {
        if (collection instanceof BooleanCollection) {
            return addAll(i, (BooleanCollection) collection);
        }
        ensureIndex(i);
        Iterator<? extends Boolean> it = collection.iterator();
        boolean zHasNext = it.hasNext();
        while (it.hasNext()) {
            add(i, it.next().booleanValue());
            i++;
        }
        return zHasNext;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection, java.util.AbstractCollection, java.util.Collection
    public boolean addAll(Collection<? extends Boolean> collection) {
        return addAll(size(), collection);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanIterable
    public BooleanListIterator iterator() {
        return listIterator();
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanList, java.util.List
    public BooleanListIterator listIterator() {
        return listIterator(0);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanList, java.util.List
    public BooleanListIterator listIterator(int i) {
        ensureIndex(i);
        return new BooleanIterators.AbstractIndexBasedListIterator(0, i) { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.AbstractBooleanList.1
            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanIterators.AbstractIndexBasedIterator
            protected final boolean get(int i2) {
                return AbstractBooleanList.this.getBoolean(i2);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanIterators.AbstractIndexBasedListIterator
            protected final void add(int i2, boolean z) {
                AbstractBooleanList.this.add(i2, z);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanIterators.AbstractIndexBasedListIterator
            protected final void set(int i2, boolean z) {
                AbstractBooleanList.this.set(i2, z);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanIterators.AbstractIndexBasedIterator
            protected final void remove(int i2) {
                AbstractBooleanList.this.removeBoolean(i2);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanIterators.AbstractIndexBasedIterator
            protected final int getMaxPos() {
                return AbstractBooleanList.this.size();
            }
        };
    }

    static final class IndexBasedSpliterator extends BooleanSpliterators.LateBindingSizeIndexBasedSpliterator {
        final BooleanList l;

        IndexBasedSpliterator(BooleanList booleanList, int i) {
            super(i);
            this.l = booleanList;
        }

        IndexBasedSpliterator(BooleanList booleanList, int i, int i2) {
            super(i, i2);
            this.l = booleanList;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanSpliterators.LateBindingSizeIndexBasedSpliterator
        protected final int getMaxPosFromBackingStore() {
            return this.l.size();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanSpliterators.AbstractIndexBasedSpliterator
        protected final boolean get(int i) {
            return this.l.getBoolean(i);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanSpliterators.AbstractIndexBasedSpliterator
        public final IndexBasedSpliterator makeForSplit(int i, int i2) {
            return new IndexBasedSpliterator(this.l, i, i2);
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanCollection
    public boolean contains(boolean z) {
        return indexOf(z) >= 0;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanList
    public int indexOf(boolean z) {
        BooleanListIterator booleanListIteratorListIterator = listIterator();
        while (booleanListIteratorListIterator.hasNext()) {
            if (z == booleanListIteratorListIterator.nextBoolean()) {
                return booleanListIteratorListIterator.previousIndex();
            }
        }
        return -1;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanList
    public int lastIndexOf(boolean z) {
        BooleanListIterator booleanListIteratorListIterator = listIterator(size());
        while (booleanListIteratorListIterator.hasPrevious()) {
            if (z == booleanListIteratorListIterator.previousBoolean()) {
                return booleanListIteratorListIterator.nextIndex();
            }
        }
        return -1;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanList, java.util.List
    public BooleanList subList(int i, int i2) {
        ensureIndex(i);
        ensureIndex(i2);
        if (i <= i2) {
            return this instanceof RandomAccess ? new BooleanRandomAccessSubList(this, i, i2) : new BooleanSubList(this, i, i2);
        }
        throw new IndexOutOfBoundsException("Start index (" + i + ") is greater than end index (" + i2 + Constant.AFTER_QUTO);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanIterable
    public void forEach(BooleanConsumer booleanConsumer) {
        if (this instanceof RandomAccess) {
            int size = size();
            for (int i = 0; i < size; i++) {
                booleanConsumer.accept(getBoolean(i));
            }
            return;
        }
        super.forEach(booleanConsumer);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanList
    public void removeElements(int i, int i2) {
        ensureIndex(i2);
        BooleanListIterator booleanListIteratorListIterator = listIterator(i);
        int i3 = i2 - i;
        if (i3 < 0) {
            throw new IllegalArgumentException("Start index (" + i + ") is greater than end index (" + i2 + Constant.AFTER_QUTO);
        }
        while (true) {
            int i4 = i3 - 1;
            if (i3 == 0) {
                return;
            }
            booleanListIteratorListIterator.nextBoolean();
            booleanListIteratorListIterator.remove();
            i3 = i4;
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanList
    public void getElements(int i, boolean[] zArr, int i2, int i3) {
        ensureIndex(i);
        BooleanArrays.ensureOffsetLength(zArr, i2, i3);
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
                zArr[i2] = getBoolean(i);
                i2++;
                i3 = i5;
                i++;
            }
        } else {
            BooleanListIterator booleanListIteratorListIterator = listIterator(i);
            while (true) {
                int i6 = i3 - 1;
                if (i3 == 0) {
                    return;
                }
                zArr[i2] = booleanListIteratorListIterator.nextBoolean();
                i2++;
                i3 = i6;
            }
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanList
    public void setElements(int i, boolean[] zArr, int i2, int i3) {
        ensureIndex(i);
        BooleanArrays.ensureOffsetLength(zArr, i2, i3);
        int i4 = i + i3;
        if (i4 > size()) {
            throw new IndexOutOfBoundsException("End index (" + i4 + ") is greater than list size (" + size() + Constant.AFTER_QUTO);
        }
        int i5 = 0;
        if (this instanceof RandomAccess) {
            while (i5 < i3) {
                set(i5 + i, zArr[i5 + i2]);
                i5++;
            }
        } else {
            BooleanListIterator booleanListIteratorListIterator = listIterator(i);
            while (i5 < i3) {
                booleanListIteratorListIterator.nextBoolean();
                booleanListIteratorListIterator.set(zArr[i5 + i2]);
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
        BooleanListIterator it = iterator();
        int size = size();
        int i = 1;
        while (true) {
            int i2 = size - 1;
            if (size == 0) {
                return i;
            }
            i = (i * 31) + (it.nextBoolean() ? XKeySym.XK_kana_MA : XKeySym.XK_kana_YU);
            size = i2;
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
        if (list instanceof BooleanList) {
            BooleanListIterator booleanListIteratorListIterator = listIterator();
            BooleanListIterator booleanListIteratorListIterator2 = ((BooleanList) list).listIterator();
            while (true) {
                int i = size - 1;
                if (size == 0) {
                    return true;
                }
                if (booleanListIteratorListIterator.nextBoolean() != booleanListIteratorListIterator2.nextBoolean()) {
                    return false;
                }
                size = i;
            }
        } else {
            BooleanListIterator booleanListIteratorListIterator3 = listIterator();
            ListIterator listIterator = list.listIterator();
            while (true) {
                int i2 = size - 1;
                if (size == 0) {
                    return true;
                }
                if (!Objects.equals(booleanListIteratorListIterator3.next(), listIterator.next())) {
                    return false;
                }
                size = i2;
            }
        }
    }

    @Override // java.lang.Comparable
    public int compareTo(List<? extends Boolean> list) {
        if (list == this) {
            return 0;
        }
        if (list instanceof BooleanList) {
            BooleanListIterator booleanListIteratorListIterator = listIterator();
            BooleanListIterator booleanListIteratorListIterator2 = ((BooleanList) list).listIterator();
            while (booleanListIteratorListIterator.hasNext() && booleanListIteratorListIterator2.hasNext()) {
                int iCompare = Boolean.compare(booleanListIteratorListIterator.nextBoolean(), booleanListIteratorListIterator2.nextBoolean());
                if (iCompare != 0) {
                    return iCompare;
                }
            }
            if (booleanListIteratorListIterator2.hasNext()) {
                return -1;
            }
            return booleanListIteratorListIterator.hasNext() ? 1 : 0;
        }
        BooleanListIterator booleanListIteratorListIterator3 = listIterator();
        ListIterator<? extends Boolean> listIterator = list.listIterator();
        while (booleanListIteratorListIterator3.hasNext() && listIterator.hasNext()) {
            int iCompareTo = booleanListIteratorListIterator3.next().compareTo(listIterator.next());
            if (iCompareTo != 0) {
                return iCompareTo;
            }
        }
        if (listIterator.hasNext()) {
            return -1;
        }
        return booleanListIteratorListIterator3.hasNext() ? 1 : 0;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanCollection
    public boolean rem(boolean z) {
        int iIndexOf = indexOf(z);
        if (iIndexOf == -1) {
            return false;
        }
        removeBoolean(iIndexOf);
        return true;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanCollection
    public boolean[] toBooleanArray() {
        int size = size();
        boolean[] zArr = new boolean[size];
        getElements(0, zArr, 0, size);
        return zArr;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection
    public boolean[] toArray(boolean[] zArr) {
        int size = size();
        if (zArr.length < size) {
            zArr = Arrays.copyOf(zArr, size);
        }
        getElements(0, zArr, 0, size);
        return zArr;
    }

    public boolean addAll(int i, BooleanCollection booleanCollection) {
        ensureIndex(i);
        BooleanIterator it = booleanCollection.iterator();
        boolean zHasNext = it.hasNext();
        while (it.hasNext()) {
            add(i, it.nextBoolean());
            i++;
        }
        return zHasNext;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection
    public boolean addAll(BooleanCollection booleanCollection) {
        return addAll(size(), booleanCollection);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection, java.util.AbstractCollection
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        BooleanListIterator it = iterator();
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
                sb.append(String.valueOf(it.nextBoolean()));
                size = i;
            } else {
                sb.append("]");
                return sb.toString();
            }
        }
    }

    public static class BooleanSubList extends AbstractBooleanList implements Serializable {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private static final long serialVersionUID = -7046029254386353129L;
        protected final int from;
        protected final BooleanList l;
        protected int to;

        /* JADX INFO: Access modifiers changed from: private */
        public boolean assertRange() {
            return true;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.AbstractBooleanList, java.lang.Comparable
        public /* bridge */ /* synthetic */ int compareTo(List<? extends Boolean> list) {
            return super.compareTo(list);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.AbstractBooleanList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanIterable
        public /* bridge */ /* synthetic */ Iterator iterator() {
            return super.iterator();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.AbstractBooleanList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanIterable
        public /* bridge */ /* synthetic */ BooleanIterator iterator() {
            return super.iterator();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.AbstractBooleanList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanList, java.util.List
        public /* bridge */ /* synthetic */ ListIterator listIterator() {
            return super.listIterator();
        }

        public BooleanSubList(BooleanList booleanList, int i, int i2) {
            this.l = booleanList;
            this.from = i;
            this.to = i2;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.AbstractBooleanList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanCollection
        public boolean add(boolean z) {
            this.l.add(this.to, z);
            this.to++;
            return true;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.AbstractBooleanList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanList
        public void add(int i, boolean z) {
            ensureIndex(i);
            this.l.add(this.from + i, z);
            this.to++;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.AbstractBooleanList, java.util.List
        public boolean addAll(int i, Collection<? extends Boolean> collection) {
            ensureIndex(i);
            this.to += collection.size();
            return this.l.addAll(this.from + i, collection);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanList
        public boolean getBoolean(int i) {
            ensureRestrictedIndex(i);
            return this.l.getBoolean(this.from + i);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.AbstractBooleanList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanList
        public boolean removeBoolean(int i) {
            ensureRestrictedIndex(i);
            this.to--;
            return this.l.removeBoolean(this.from + i);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.AbstractBooleanList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanList
        public boolean set(int i, boolean z) {
            ensureRestrictedIndex(i);
            return this.l.set(this.from + i, z);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return this.to - this.from;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.AbstractBooleanList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanList
        public void getElements(int i, boolean[] zArr, int i2, int i3) {
            ensureIndex(i);
            if (i + i3 > size()) {
                throw new IndexOutOfBoundsException("End index (" + i + i3 + ") is greater than list size (" + size() + Constant.AFTER_QUTO);
            }
            this.l.getElements(this.from + i, zArr, i2, i3);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.AbstractBooleanList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanList
        public void removeElements(int i, int i2) {
            ensureIndex(i);
            ensureIndex(i2);
            BooleanList booleanList = this.l;
            int i3 = this.from;
            booleanList.removeElements(i3 + i, i3 + i2);
            this.to -= i2 - i;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.AbstractBooleanList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanList
        public void setElements(int i, boolean[] zArr, int i2, int i3) {
            ensureIndex(i);
            this.l.setElements(this.from + i, zArr, i2, i3);
        }

        private final class RandomAccessIter extends BooleanIterators.AbstractIndexBasedListIterator {
            static final /* synthetic */ boolean $assertionsDisabled = false;

            RandomAccessIter(int i) {
                super(0, i);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanIterators.AbstractIndexBasedIterator
            protected final boolean get(int i) {
                return BooleanSubList.this.l.getBoolean(BooleanSubList.this.from + i);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanIterators.AbstractIndexBasedListIterator
            protected final void add(int i, boolean z) {
                BooleanSubList.this.add(i, z);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanIterators.AbstractIndexBasedListIterator
            protected final void set(int i, boolean z) {
                BooleanSubList.this.set(i, z);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanIterators.AbstractIndexBasedIterator
            protected final void remove(int i) {
                BooleanSubList.this.removeBoolean(i);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanIterators.AbstractIndexBasedIterator
            protected final int getMaxPos() {
                return BooleanSubList.this.to - BooleanSubList.this.from;
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanIterators.AbstractIndexBasedListIterator, org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanListIterator
            public void add(boolean z) {
                super.add(z);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanIterators.AbstractIndexBasedIterator, java.util.Iterator, org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanListIterator, java.util.ListIterator
            public void remove() {
                super.remove();
            }
        }

        private class ParentWrappingIter implements BooleanListIterator {
            private BooleanListIterator parent;

            ParentWrappingIter(BooleanListIterator booleanListIterator) {
                this.parent = booleanListIterator;
            }

            @Override // java.util.ListIterator
            public int nextIndex() {
                return this.parent.nextIndex() - BooleanSubList.this.from;
            }

            @Override // java.util.ListIterator
            public int previousIndex() {
                return this.parent.previousIndex() - BooleanSubList.this.from;
            }

            @Override // java.util.Iterator, java.util.ListIterator
            public boolean hasNext() {
                return this.parent.nextIndex() < BooleanSubList.this.to;
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.BidirectionalIterator, java.util.ListIterator
            public boolean hasPrevious() {
                return this.parent.previousIndex() >= BooleanSubList.this.from;
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanIterator
            public boolean nextBoolean() {
                if (hasNext()) {
                    return this.parent.nextBoolean();
                }
                throw new NoSuchElementException();
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanBidirectionalIterator
            public boolean previousBoolean() {
                if (hasPrevious()) {
                    return this.parent.previousBoolean();
                }
                throw new NoSuchElementException();
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanListIterator
            public void add(boolean z) {
                this.parent.add(z);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanListIterator
            public void set(boolean z) {
                this.parent.set(z);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanListIterator, java.util.Iterator, java.util.ListIterator
            public void remove() {
                this.parent.remove();
            }
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.AbstractBooleanList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanList, java.util.List
        public BooleanListIterator listIterator(int i) {
            ensureIndex(i);
            return this.l instanceof RandomAccess ? new RandomAccessIter(i) : new ParentWrappingIter(this.l.listIterator(i + this.from));
        }

        @Override // java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanList, java.util.List
        public BooleanSpliterator spliterator() {
            return this.l instanceof RandomAccess ? new IndexBasedSpliterator(this.l, this.from, this.to) : super.spliterator();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.AbstractBooleanList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanList, java.util.List
        public BooleanList subList(int i, int i2) {
            ensureIndex(i);
            ensureIndex(i2);
            if (i > i2) {
                throw new IllegalArgumentException("Start index (" + i + ") is greater than end index (" + i2 + Constant.AFTER_QUTO);
            }
            return new BooleanSubList(this, i, i2);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.AbstractBooleanList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.AbstractBooleanCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanCollection
        public boolean rem(boolean z) {
            int iIndexOf = indexOf(z);
            if (iIndexOf == -1) {
                return false;
            }
            this.to--;
            this.l.removeBoolean(this.from + iIndexOf);
            return true;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.AbstractBooleanList
        public boolean addAll(int i, BooleanCollection booleanCollection) {
            ensureIndex(i);
            return super.addAll(i, booleanCollection);
        }
    }

    public static class BooleanRandomAccessSubList extends BooleanSubList implements RandomAccess {
        private static final long serialVersionUID = -107070782945191929L;

        public BooleanRandomAccessSubList(BooleanList booleanList, int i, int i2) {
            super(booleanList, i, i2);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.AbstractBooleanList.BooleanSubList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.AbstractBooleanList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans.BooleanList, java.util.List
        public BooleanList subList(int i, int i2) {
            ensureIndex(i);
            ensureIndex(i2);
            if (i > i2) {
                throw new IllegalArgumentException("Start index (" + i + ") is greater than end index (" + i2 + Constant.AFTER_QUTO);
            }
            return new BooleanRandomAccessSubList(this, i, i2);
        }
    }
}
