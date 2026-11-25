package org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles;

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
public abstract class AbstractDoubleList extends AbstractDoubleCollection implements DoubleList {
    protected AbstractDoubleList() {
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

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleList
    public void add(int i, double d) {
        throw new UnsupportedOperationException();
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection
    public boolean add(double d) {
        add(size(), d);
        return true;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleList
    public double removeDouble(int i) {
        throw new UnsupportedOperationException();
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleList
    public double set(int i, double d) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List
    public boolean addAll(int i, Collection<? extends Double> collection) {
        if (collection instanceof DoubleCollection) {
            return addAll(i, (DoubleCollection) collection);
        }
        ensureIndex(i);
        Iterator<? extends Double> it = collection.iterator();
        boolean zHasNext = it.hasNext();
        while (it.hasNext()) {
            add(i, it.next().doubleValue());
            i++;
        }
        return zHasNext;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection, java.util.AbstractCollection, java.util.Collection
    public boolean addAll(Collection<? extends Double> collection) {
        return addAll(size(), collection);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleSet, java.util.Set
    public DoubleListIterator iterator() {
        return listIterator();
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleList, java.util.List
    public DoubleListIterator listIterator() {
        return listIterator(0);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleList, java.util.List
    public DoubleListIterator listIterator(int i) {
        ensureIndex(i);
        return new DoubleIterators.AbstractIndexBasedListIterator(0, i) { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleList.1
            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleIterators.AbstractIndexBasedIterator
            protected final double get(int i2) {
                return AbstractDoubleList.this.getDouble(i2);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleIterators.AbstractIndexBasedListIterator
            protected final void add(int i2, double d) {
                AbstractDoubleList.this.add(i2, d);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleIterators.AbstractIndexBasedListIterator
            protected final void set(int i2, double d) {
                AbstractDoubleList.this.set(i2, d);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleIterators.AbstractIndexBasedIterator
            protected final void remove(int i2) {
                AbstractDoubleList.this.removeDouble(i2);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleIterators.AbstractIndexBasedIterator
            protected final int getMaxPos() {
                return AbstractDoubleList.this.size();
            }
        };
    }

    static final class IndexBasedSpliterator extends DoubleSpliterators.LateBindingSizeIndexBasedSpliterator {
        final DoubleList l;

        IndexBasedSpliterator(DoubleList doubleList, int i) {
            super(i);
            this.l = doubleList;
        }

        IndexBasedSpliterator(DoubleList doubleList, int i, int i2) {
            super(i, i2);
            this.l = doubleList;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleSpliterators.LateBindingSizeIndexBasedSpliterator
        protected final int getMaxPosFromBackingStore() {
            return this.l.size();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleSpliterators.AbstractIndexBasedSpliterator
        protected final double get(int i) {
            return this.l.getDouble(i);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleSpliterators.AbstractIndexBasedSpliterator
        public final IndexBasedSpliterator makeForSplit(int i, int i2) {
            return new IndexBasedSpliterator(this.l, i, i2);
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection
    public boolean contains(double d) {
        return indexOf(d) >= 0;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleList
    public int indexOf(double d) {
        DoubleListIterator doubleListIteratorListIterator = listIterator();
        while (doubleListIteratorListIterator.hasNext()) {
            if (Double.doubleToLongBits(d) == Double.doubleToLongBits(doubleListIteratorListIterator.nextDouble())) {
                return doubleListIteratorListIterator.previousIndex();
            }
        }
        return -1;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleList
    public int lastIndexOf(double d) {
        DoubleListIterator doubleListIteratorListIterator = listIterator(size());
        while (doubleListIteratorListIterator.hasPrevious()) {
            if (Double.doubleToLongBits(d) == Double.doubleToLongBits(doubleListIteratorListIterator.previousDouble())) {
                return doubleListIteratorListIterator.nextIndex();
            }
        }
        return -1;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleList, java.util.List
    public DoubleList subList(int i, int i2) {
        ensureIndex(i);
        ensureIndex(i2);
        if (i <= i2) {
            return this instanceof RandomAccess ? new DoubleRandomAccessSubList(this, i, i2) : new DoubleSubList(this, i, i2);
        }
        throw new IndexOutOfBoundsException("Start index (" + i + ") is greater than end index (" + i2 + Constant.AFTER_QUTO);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleIterable
    public void forEach(java.util.function.DoubleConsumer doubleConsumer) {
        if (this instanceof RandomAccess) {
            int size = size();
            for (int i = 0; i < size; i++) {
                doubleConsumer.accept(getDouble(i));
            }
            return;
        }
        super.forEach(doubleConsumer);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleList
    public void removeElements(int i, int i2) {
        ensureIndex(i2);
        DoubleListIterator doubleListIteratorListIterator = listIterator(i);
        int i3 = i2 - i;
        if (i3 < 0) {
            throw new IllegalArgumentException("Start index (" + i + ") is greater than end index (" + i2 + Constant.AFTER_QUTO);
        }
        while (true) {
            int i4 = i3 - 1;
            if (i3 == 0) {
                return;
            }
            doubleListIteratorListIterator.nextDouble();
            doubleListIteratorListIterator.remove();
            i3 = i4;
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleList
    public void getElements(int i, double[] dArr, int i2, int i3) {
        ensureIndex(i);
        DoubleArrays.ensureOffsetLength(dArr, i2, i3);
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
                dArr[i2] = getDouble(i);
                i2++;
                i3 = i5;
                i++;
            }
        } else {
            DoubleListIterator doubleListIteratorListIterator = listIterator(i);
            while (true) {
                int i6 = i3 - 1;
                if (i3 == 0) {
                    return;
                }
                dArr[i2] = doubleListIteratorListIterator.nextDouble();
                i2++;
                i3 = i6;
            }
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleList
    public void setElements(int i, double[] dArr, int i2, int i3) {
        ensureIndex(i);
        DoubleArrays.ensureOffsetLength(dArr, i2, i3);
        int i4 = i + i3;
        if (i4 > size()) {
            throw new IndexOutOfBoundsException("End index (" + i4 + ") is greater than list size (" + size() + Constant.AFTER_QUTO);
        }
        int i5 = 0;
        if (this instanceof RandomAccess) {
            while (i5 < i3) {
                set(i5 + i, dArr[i5 + i2]);
                i5++;
            }
        } else {
            DoubleListIterator doubleListIteratorListIterator = listIterator(i);
            while (i5 < i3) {
                doubleListIteratorListIterator.nextDouble();
                doubleListIteratorListIterator.set(dArr[i5 + i2]);
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
        DoubleListIterator it = iterator();
        int size = size();
        int iDouble2int = 1;
        while (true) {
            int i = size - 1;
            if (size == 0) {
                return iDouble2int;
            }
            iDouble2int = (iDouble2int * 31) + HashCommon.double2int(it.nextDouble());
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
        if (list instanceof DoubleList) {
            DoubleListIterator doubleListIteratorListIterator = listIterator();
            DoubleListIterator doubleListIteratorListIterator2 = ((DoubleList) list).listIterator();
            while (true) {
                int i = size - 1;
                if (size == 0) {
                    return true;
                }
                if (doubleListIteratorListIterator.nextDouble() != doubleListIteratorListIterator2.nextDouble()) {
                    return false;
                }
                size = i;
            }
        } else {
            DoubleListIterator doubleListIteratorListIterator3 = listIterator();
            ListIterator listIterator = list.listIterator();
            while (true) {
                int i2 = size - 1;
                if (size == 0) {
                    return true;
                }
                if (!Objects.equals(doubleListIteratorListIterator3.next(), listIterator.next())) {
                    return false;
                }
                size = i2;
            }
        }
    }

    @Override // java.lang.Comparable
    public int compareTo(List<? extends Double> list) {
        if (list == this) {
            return 0;
        }
        if (list instanceof DoubleList) {
            DoubleListIterator doubleListIteratorListIterator = listIterator();
            DoubleListIterator doubleListIteratorListIterator2 = ((DoubleList) list).listIterator();
            while (doubleListIteratorListIterator.hasNext() && doubleListIteratorListIterator2.hasNext()) {
                int iCompare = Double.compare(doubleListIteratorListIterator.nextDouble(), doubleListIteratorListIterator2.nextDouble());
                if (iCompare != 0) {
                    return iCompare;
                }
            }
            if (doubleListIteratorListIterator2.hasNext()) {
                return -1;
            }
            return doubleListIteratorListIterator.hasNext() ? 1 : 0;
        }
        DoubleListIterator doubleListIteratorListIterator3 = listIterator();
        ListIterator<? extends Double> listIterator = list.listIterator();
        while (doubleListIteratorListIterator3.hasNext() && listIterator.hasNext()) {
            int iCompareTo = doubleListIteratorListIterator3.next().compareTo(listIterator.next());
            if (iCompareTo != 0) {
                return iCompareTo;
            }
        }
        if (listIterator.hasNext()) {
            return -1;
        }
        return doubleListIteratorListIterator3.hasNext() ? 1 : 0;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection
    public boolean rem(double d) {
        int iIndexOf = indexOf(d);
        if (iIndexOf == -1) {
            return false;
        }
        removeDouble(iIndexOf);
        return true;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection
    public double[] toDoubleArray() {
        int size = size();
        double[] dArr = new double[size];
        getElements(0, dArr, 0, size);
        return dArr;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection
    public double[] toArray(double[] dArr) {
        int size = size();
        if (dArr.length < size) {
            dArr = Arrays.copyOf(dArr, size);
        }
        getElements(0, dArr, 0, size);
        return dArr;
    }

    public boolean addAll(int i, DoubleCollection doubleCollection) {
        ensureIndex(i);
        DoubleIterator it = doubleCollection.iterator();
        boolean zHasNext = it.hasNext();
        while (it.hasNext()) {
            add(i, it.nextDouble());
            i++;
        }
        return zHasNext;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection
    public boolean addAll(DoubleCollection doubleCollection) {
        return addAll(size(), doubleCollection);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection, java.util.AbstractCollection
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        DoubleListIterator it = iterator();
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
                sb.append(String.valueOf(it.nextDouble()));
                size = i;
            } else {
                sb.append("]");
                return sb.toString();
            }
        }
    }

    public static class DoubleSubList extends AbstractDoubleList implements Serializable {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private static final long serialVersionUID = -7046029254386353129L;
        protected final int from;
        protected final DoubleList l;
        protected int to;

        /* JADX INFO: Access modifiers changed from: private */
        public boolean assertRange() {
            return true;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleList, java.lang.Comparable
        public /* bridge */ /* synthetic */ int compareTo(List<? extends Double> list) {
            return super.compareTo(list);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleSet, java.util.Set
        public /* bridge */ /* synthetic */ Iterator iterator() {
            return super.iterator();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleSet, java.util.Set
        public /* bridge */ /* synthetic */ DoubleIterator iterator() {
            return super.iterator();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleList, java.util.List
        public /* bridge */ /* synthetic */ ListIterator listIterator() {
            return super.listIterator();
        }

        public DoubleSubList(DoubleList doubleList, int i, int i2) {
            this.l = doubleList;
            this.from = i;
            this.to = i2;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection
        public boolean add(double d) {
            this.l.add(this.to, d);
            this.to++;
            return true;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleList
        public void add(int i, double d) {
            ensureIndex(i);
            this.l.add(this.from + i, d);
            this.to++;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleList, java.util.List
        public boolean addAll(int i, Collection<? extends Double> collection) {
            ensureIndex(i);
            this.to += collection.size();
            return this.l.addAll(this.from + i, collection);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleList
        public double getDouble(int i) {
            ensureRestrictedIndex(i);
            return this.l.getDouble(this.from + i);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleList
        public double removeDouble(int i) {
            ensureRestrictedIndex(i);
            this.to--;
            return this.l.removeDouble(this.from + i);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleList
        public double set(int i, double d) {
            ensureRestrictedIndex(i);
            return this.l.set(this.from + i, d);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return this.to - this.from;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleList
        public void getElements(int i, double[] dArr, int i2, int i3) {
            ensureIndex(i);
            if (i + i3 > size()) {
                throw new IndexOutOfBoundsException("End index (" + i + i3 + ") is greater than list size (" + size() + Constant.AFTER_QUTO);
            }
            this.l.getElements(this.from + i, dArr, i2, i3);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleList
        public void removeElements(int i, int i2) {
            ensureIndex(i);
            ensureIndex(i2);
            DoubleList doubleList = this.l;
            int i3 = this.from;
            doubleList.removeElements(i3 + i, i3 + i2);
            this.to -= i2 - i;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleList
        public void setElements(int i, double[] dArr, int i2, int i3) {
            ensureIndex(i);
            this.l.setElements(this.from + i, dArr, i2, i3);
        }

        private final class RandomAccessIter extends DoubleIterators.AbstractIndexBasedListIterator {
            static final /* synthetic */ boolean $assertionsDisabled = false;

            RandomAccessIter(int i) {
                super(0, i);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleIterators.AbstractIndexBasedIterator
            protected final double get(int i) {
                return DoubleSubList.this.l.getDouble(DoubleSubList.this.from + i);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleIterators.AbstractIndexBasedListIterator
            protected final void add(int i, double d) {
                DoubleSubList.this.add(i, d);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleIterators.AbstractIndexBasedListIterator
            protected final void set(int i, double d) {
                DoubleSubList.this.set(i, d);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleIterators.AbstractIndexBasedIterator
            protected final void remove(int i) {
                DoubleSubList.this.removeDouble(i);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleIterators.AbstractIndexBasedIterator
            protected final int getMaxPos() {
                return DoubleSubList.this.to - DoubleSubList.this.from;
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleIterators.AbstractIndexBasedListIterator, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleListIterator
            public void add(double d) {
                super.add(d);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleIterators.AbstractIndexBasedIterator, java.util.Iterator, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleListIterator, java.util.ListIterator
            public void remove() {
                super.remove();
            }
        }

        private class ParentWrappingIter implements DoubleListIterator {
            private DoubleListIterator parent;

            ParentWrappingIter(DoubleListIterator doubleListIterator) {
                this.parent = doubleListIterator;
            }

            @Override // java.util.ListIterator
            public int nextIndex() {
                return this.parent.nextIndex() - DoubleSubList.this.from;
            }

            @Override // java.util.ListIterator
            public int previousIndex() {
                return this.parent.previousIndex() - DoubleSubList.this.from;
            }

            @Override // java.util.Iterator, java.util.ListIterator
            public boolean hasNext() {
                return this.parent.nextIndex() < DoubleSubList.this.to;
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.BidirectionalIterator, java.util.ListIterator
            public boolean hasPrevious() {
                return this.parent.previousIndex() >= DoubleSubList.this.from;
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleIterator, java.util.PrimitiveIterator.OfDouble
            public double nextDouble() {
                if (hasNext()) {
                    return this.parent.nextDouble();
                }
                throw new NoSuchElementException();
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleBidirectionalIterator
            public double previousDouble() {
                if (hasPrevious()) {
                    return this.parent.previousDouble();
                }
                throw new NoSuchElementException();
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleListIterator
            public void add(double d) {
                this.parent.add(d);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleListIterator
            public void set(double d) {
                this.parent.set(d);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleListIterator, java.util.Iterator, java.util.ListIterator
            public void remove() {
                this.parent.remove();
            }
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleList, java.util.List
        public DoubleListIterator listIterator(int i) {
            ensureIndex(i);
            return this.l instanceof RandomAccess ? new RandomAccessIter(i) : new ParentWrappingIter(this.l.listIterator(i + this.from));
        }

        @Override // java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleSet, java.util.Set
        public DoubleSpliterator spliterator() {
            return this.l instanceof RandomAccess ? new IndexBasedSpliterator(this.l, this.from, this.to) : super.spliterator();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleList, java.util.List
        public DoubleList subList(int i, int i2) {
            ensureIndex(i);
            ensureIndex(i2);
            if (i > i2) {
                throw new IllegalArgumentException("Start index (" + i + ") is greater than end index (" + i2 + Constant.AFTER_QUTO);
            }
            return new DoubleSubList(this, i, i2);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection
        public boolean rem(double d) {
            int iIndexOf = indexOf(d);
            if (iIndexOf == -1) {
                return false;
            }
            this.to--;
            this.l.removeDouble(this.from + iIndexOf);
            return true;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleList
        public boolean addAll(int i, DoubleCollection doubleCollection) {
            ensureIndex(i);
            return super.addAll(i, doubleCollection);
        }
    }

    public static class DoubleRandomAccessSubList extends DoubleSubList implements RandomAccess {
        private static final long serialVersionUID = -107070782945191929L;

        public DoubleRandomAccessSubList(DoubleList doubleList, int i, int i2) {
            super(doubleList, i, i2);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleList.DoubleSubList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleList, java.util.List
        public DoubleList subList(int i, int i2) {
            ensureIndex(i);
            ensureIndex(i2);
            if (i > i2) {
                throw new IllegalArgumentException("Start index (" + i + ") is greater than end index (" + i2 + Constant.AFTER_QUTO);
            }
            return new DoubleRandomAccessSubList(this, i, i2);
        }
    }
}
