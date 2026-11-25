package org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles;

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
public class DoubleArrayList extends AbstractDoubleList implements Serializable, Cloneable, RandomAccess {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final long serialVersionUID = -7046029254386353130L;
    protected transient double[] a;
    protected int size;

    private static final double[] copyArraySafe(double[] dArr, int i) {
        if (i == 0) {
            return DoubleArrays.EMPTY_ARRAY;
        }
        return Arrays.copyOf(dArr, i);
    }

    protected DoubleArrayList(double[] dArr, boolean z) {
        this.a = dArr;
    }

    private void initArrayFromCapacity(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("Initial capacity (" + i + ") is negative");
        }
        if (i == 0) {
            this.a = DoubleArrays.EMPTY_ARRAY;
        } else {
            this.a = new double[i];
        }
    }

    public DoubleArrayList(int i) {
        initArrayFromCapacity(i);
    }

    public DoubleArrayList() {
        this.a = DoubleArrays.DEFAULT_EMPTY_ARRAY;
    }

    private void grow(int i) {
        double[] dArr = this.a;
        if (i <= dArr.length) {
            return;
        }
        if (dArr != DoubleArrays.DEFAULT_EMPTY_ARRAY) {
            double[] dArr2 = this.a;
            i = (int) Math.max(Math.min(((long) dArr2.length) + ((long) (dArr2.length >> 1)), 2147483639L), i);
        } else if (i < 10) {
            i = 10;
        }
        this.a = DoubleArrays.forceCapacity(this.a, i, this.size);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleList
    public void add(int i, double d) {
        ensureIndex(i);
        grow(this.size + 1);
        int i2 = this.size;
        if (i != i2) {
            double[] dArr = this.a;
            System.arraycopy(dArr, i, dArr, i + 1, i2 - i);
        }
        this.a[i] = d;
        this.size++;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection
    public boolean add(double d) {
        grow(this.size + 1);
        double[] dArr = this.a;
        int i = this.size;
        this.size = i + 1;
        dArr[i] = d;
        return true;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleList
    public double getDouble(int i) {
        if (i >= this.size) {
            throw new IndexOutOfBoundsException("Index (" + i + ") is greater than or equal to list size (" + this.size + Constant.AFTER_QUTO);
        }
        return this.a[i];
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleList
    public int indexOf(double d) {
        for (int i = 0; i < this.size; i++) {
            if (Double.doubleToLongBits(d) == Double.doubleToLongBits(this.a[i])) {
                return i;
            }
        }
        return -1;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleList
    public int lastIndexOf(double d) {
        int i = this.size;
        while (true) {
            int i2 = i - 1;
            if (i == 0) {
                return -1;
            }
            if (Double.doubleToLongBits(d) == Double.doubleToLongBits(this.a[i2])) {
                return i2;
            }
            i = i2;
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleList
    public double removeDouble(int i) {
        int i2 = this.size;
        if (i >= i2) {
            throw new IndexOutOfBoundsException("Index (" + i + ") is greater than or equal to list size (" + this.size + Constant.AFTER_QUTO);
        }
        double[] dArr = this.a;
        double d = dArr[i];
        int i3 = i2 - 1;
        this.size = i3;
        if (i != i3) {
            System.arraycopy(dArr, i + 1, dArr, i, i3 - i);
        }
        return d;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection
    public boolean rem(double d) {
        int iIndexOf = indexOf(d);
        if (iIndexOf == -1) {
            return false;
        }
        removeDouble(iIndexOf);
        return true;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleList
    public double set(int i, double d) {
        if (i >= this.size) {
            throw new IndexOutOfBoundsException("Index (" + i + ") is greater than or equal to list size (" + this.size + Constant.AFTER_QUTO);
        }
        double[] dArr = this.a;
        double d2 = dArr[i];
        dArr[i] = d;
        return d2;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleList, java.util.AbstractCollection, java.util.Collection, java.util.List
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

    private class SubList extends AbstractDoubleList.DoubleRandomAccessSubList {
        private static final long serialVersionUID = -3185226345314976296L;

        protected SubList(int i, int i2) {
            super(DoubleArrayList.this, i, i2);
        }

        private double[] getParentArray() {
            return DoubleArrayList.this.a;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleList.DoubleSubList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleList
        public double getDouble(int i) {
            ensureRestrictedIndex(i);
            return DoubleArrayList.this.a[i + this.from];
        }

        private final class SubListIterator extends DoubleIterators.AbstractIndexBasedListIterator {
            SubListIterator(int i) {
                super(0, i);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleIterators.AbstractIndexBasedIterator
            protected final double get(int i) {
                return DoubleArrayList.this.a[SubList.this.from + i];
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleIterators.AbstractIndexBasedListIterator
            protected final void add(int i, double d) {
                SubList.this.add(i, d);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleIterators.AbstractIndexBasedListIterator
            protected final void set(int i, double d) {
                SubList.this.set(i, d);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleIterators.AbstractIndexBasedIterator
            protected final void remove(int i) {
                SubList.this.removeDouble(i);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleIterators.AbstractIndexBasedIterator
            protected final int getMaxPos() {
                return SubList.this.to - SubList.this.from;
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleIterators.AbstractIndexBasedIterator, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleIterator, java.util.PrimitiveIterator.OfDouble
            public double nextDouble() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                double[] dArr = DoubleArrayList.this.a;
                int i = SubList.this.from;
                int i2 = this.pos;
                this.pos = i2 + 1;
                this.lastReturned = i2;
                return dArr[i + i2];
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleIterators.AbstractIndexBasedListIterator, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleBidirectionalIterator
            public double previousDouble() {
                if (!hasPrevious()) {
                    throw new NoSuchElementException();
                }
                double[] dArr = DoubleArrayList.this.a;
                int i = SubList.this.from;
                int i2 = this.pos - 1;
                this.pos = i2;
                this.lastReturned = i2;
                return dArr[i + i2];
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleIterators.AbstractIndexBasedIterator, java.util.PrimitiveIterator
            public void forEachRemaining(java.util.function.DoubleConsumer doubleConsumer) {
                int i = SubList.this.to - SubList.this.from;
                while (this.pos < i) {
                    double[] dArr = DoubleArrayList.this.a;
                    int i2 = SubList.this.from;
                    int i3 = this.pos;
                    this.pos = i3 + 1;
                    this.lastReturned = i3;
                    doubleConsumer.accept(dArr[i2 + i3]);
                }
            }
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleList.DoubleSubList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleList, java.util.List
        public DoubleListIterator listIterator(int i) {
            return new SubListIterator(i);
        }

        private final class SubListSpliterator extends DoubleSpliterators.LateBindingSizeIndexBasedSpliterator {
            SubListSpliterator() {
                super(SubList.this.from);
            }

            private SubListSpliterator(int i, int i2) {
                super(i, i2);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleSpliterators.LateBindingSizeIndexBasedSpliterator
            protected final int getMaxPosFromBackingStore() {
                return SubList.this.to;
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleSpliterators.AbstractIndexBasedSpliterator
            protected final double get(int i) {
                return DoubleArrayList.this.a[i];
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleSpliterators.AbstractIndexBasedSpliterator
            public final SubListSpliterator makeForSplit(int i, int i2) {
                return SubList.this.new SubListSpliterator(i, i2);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleSpliterators.AbstractIndexBasedSpliterator, java.util.Spliterator.OfPrimitive
            public boolean tryAdvance(java.util.function.DoubleConsumer doubleConsumer) {
                if (this.pos >= getMaxPos()) {
                    return false;
                }
                double[] dArr = DoubleArrayList.this.a;
                int i = this.pos;
                this.pos = i + 1;
                doubleConsumer.accept(dArr[i]);
                return true;
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleSpliterators.AbstractIndexBasedSpliterator, java.util.Spliterator.OfPrimitive
            public void forEachRemaining(java.util.function.DoubleConsumer doubleConsumer) {
                int maxPos = getMaxPos();
                while (this.pos < maxPos) {
                    double[] dArr = DoubleArrayList.this.a;
                    int i = this.pos;
                    this.pos = i + 1;
                    doubleConsumer.accept(dArr[i]);
                }
            }
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleList.DoubleSubList, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleSet, java.util.Set
        public DoubleSpliterator spliterator() {
            return new SubListSpliterator();
        }

        boolean contentsEquals(double[] dArr, int i, int i2) {
            if (DoubleArrayList.this.a == dArr && this.from == i && this.to == i2) {
                return true;
            }
            if (i2 - i != size()) {
                return false;
            }
            int i3 = this.from;
            while (i3 < this.to) {
                int i4 = i3 + 1;
                double d = DoubleArrayList.this.a[i3];
                int i5 = i + 1;
                if (d != dArr[i]) {
                    return false;
                }
                i = i5;
                i3 = i4;
            }
            return true;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleList, java.util.Collection, java.util.List
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj == null || !(obj instanceof List)) {
                return false;
            }
            if (obj instanceof DoubleArrayList) {
                DoubleArrayList doubleArrayList = (DoubleArrayList) obj;
                return contentsEquals(doubleArrayList.a, 0, doubleArrayList.size());
            }
            if (obj instanceof SubList) {
                SubList subList = (SubList) obj;
                return contentsEquals(subList.getParentArray(), subList.from, subList.to);
            }
            return super.equals(obj);
        }

        int contentsCompareTo(double[] dArr, int i, int i2) {
            if (DoubleArrayList.this.a == dArr && this.from == i && this.to == i2) {
                return 0;
            }
            int i3 = this.from;
            while (i3 < this.to && i3 < i2) {
                int iCompare = Double.compare(DoubleArrayList.this.a[i3], dArr[i]);
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

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleList.DoubleSubList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleList, java.lang.Comparable
        public int compareTo(List<? extends Double> list) {
            if (list instanceof DoubleArrayList) {
                DoubleArrayList doubleArrayList = (DoubleArrayList) list;
                return contentsCompareTo(doubleArrayList.a, 0, doubleArrayList.size());
            }
            if (list instanceof SubList) {
                SubList subList = (SubList) list;
                return contentsCompareTo(subList.getParentArray(), subList.from, subList.to);
            }
            return super.compareTo(list);
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleList, java.util.List
    public DoubleList subList(int i, int i2) {
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

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleList
    public void getElements(int i, double[] dArr, int i2, int i3) {
        DoubleArrays.ensureOffsetLength(dArr, i2, i3);
        System.arraycopy(this.a, i, dArr, i2, i3);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleList
    public void removeElements(int i, int i2) {
        org.jetbrains.kotlin.it.unimi.dsi.fastutil.Arrays.ensureFromTo(this.size, i, i2);
        double[] dArr = this.a;
        System.arraycopy(dArr, i2, dArr, i, this.size - i2);
        this.size -= i2 - i;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleList
    public void setElements(int i, double[] dArr, int i2, int i3) {
        ensureIndex(i);
        DoubleArrays.ensureOffsetLength(dArr, i2, i3);
        int i4 = i + i3;
        if (i4 > this.size) {
            throw new IndexOutOfBoundsException("End index (" + i4 + ") is greater than list size (" + this.size + Constant.AFTER_QUTO);
        }
        System.arraycopy(dArr, i2, this.a, i, i3);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleIterable
    public void forEach(java.util.function.DoubleConsumer doubleConsumer) {
        for (int i = 0; i < this.size; i++) {
            doubleConsumer.accept(this.a[i]);
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleList
    public boolean addAll(int i, DoubleCollection doubleCollection) {
        if (doubleCollection instanceof DoubleList) {
            return addAll(i, (DoubleList) doubleCollection);
        }
        ensureIndex(i);
        int size = doubleCollection.size();
        if (size == 0) {
            return false;
        }
        grow(this.size + size);
        double[] dArr = this.a;
        System.arraycopy(dArr, i, dArr, i + size, this.size - i);
        DoubleIterator it = doubleCollection.iterator();
        this.size += size;
        while (true) {
            int i2 = size - 1;
            if (size == 0) {
                return true;
            }
            this.a[i] = it.nextDouble();
            size = i2;
            i++;
        }
    }

    public boolean addAll(int i, DoubleList doubleList) {
        ensureIndex(i);
        int size = doubleList.size();
        if (size == 0) {
            return false;
        }
        grow(this.size + size);
        double[] dArr = this.a;
        System.arraycopy(dArr, i, dArr, i + size, this.size - i);
        doubleList.getElements(0, this.a, i, size);
        this.size += size;
        return true;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection
    public boolean removeAll(DoubleCollection doubleCollection) {
        int i;
        double[] dArr = this.a;
        int i2 = 0;
        int i3 = 0;
        while (true) {
            i = this.size;
            if (i2 >= i) {
                break;
            }
            if (!doubleCollection.contains(dArr[i2])) {
                dArr[i3] = dArr[i2];
                i3++;
            }
            i2++;
        }
        boolean z = i != i3;
        this.size = i3;
        return z;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection
    public double[] toArray(double[] dArr) {
        if (dArr == null || dArr.length < this.size) {
            dArr = Arrays.copyOf(dArr, this.size);
        }
        System.arraycopy(this.a, 0, dArr, 0, this.size);
        return dArr;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleList, java.util.List
    public DoubleListIterator listIterator(int i) {
        ensureIndex(i);
        return new DoubleListIterator(i) { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleArrayList.1
            int last = -1;
            int pos;
            final /* synthetic */ int val$index;

            {
                this.val$index = i;
                this.pos = i;
            }

            @Override // java.util.Iterator, java.util.ListIterator
            public boolean hasNext() {
                return this.pos < DoubleArrayList.this.size;
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.BidirectionalIterator, java.util.ListIterator
            public boolean hasPrevious() {
                return this.pos > 0;
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleIterator, java.util.PrimitiveIterator.OfDouble
            public double nextDouble() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                double[] dArr = DoubleArrayList.this.a;
                int i2 = this.pos;
                this.pos = i2 + 1;
                this.last = i2;
                return dArr[i2];
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleBidirectionalIterator
            public double previousDouble() {
                if (!hasPrevious()) {
                    throw new NoSuchElementException();
                }
                double[] dArr = DoubleArrayList.this.a;
                int i2 = this.pos - 1;
                this.pos = i2;
                this.last = i2;
                return dArr[i2];
            }

            @Override // java.util.ListIterator
            public int nextIndex() {
                return this.pos;
            }

            @Override // java.util.ListIterator
            public int previousIndex() {
                return this.pos - 1;
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleListIterator
            public void add(double d) {
                DoubleArrayList doubleArrayList = DoubleArrayList.this;
                int i2 = this.pos;
                this.pos = i2 + 1;
                doubleArrayList.add(i2, d);
                this.last = -1;
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleListIterator
            public void set(double d) {
                int i2 = this.last;
                if (i2 == -1) {
                    throw new IllegalStateException();
                }
                DoubleArrayList.this.set(i2, d);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleListIterator, java.util.Iterator, java.util.ListIterator
            public void remove() {
                int i2 = this.last;
                if (i2 == -1) {
                    throw new IllegalStateException();
                }
                DoubleArrayList.this.removeDouble(i2);
                int i3 = this.last;
                int i4 = this.pos;
                if (i3 < i4) {
                    this.pos = i4 - 1;
                }
                this.last = -1;
            }

            @Override // java.util.PrimitiveIterator
            public void forEachRemaining(java.util.function.DoubleConsumer doubleConsumer) {
                while (this.pos < DoubleArrayList.this.size) {
                    double[] dArr = DoubleArrayList.this.a;
                    int i2 = this.pos;
                    this.pos = i2 + 1;
                    this.last = i2;
                    doubleConsumer.accept(dArr[i2]);
                }
            }
        };
    }

    private final class Spliterator implements DoubleSpliterator {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        boolean hasSplit;
        int max;
        int pos;

        @Override // java.util.Spliterator
        public int characteristics() {
            return 16720;
        }

        public Spliterator(DoubleArrayList doubleArrayList) {
            this(0, doubleArrayList.size, false);
        }

        private Spliterator(int i, int i2, boolean z) {
            this.pos = i;
            this.max = i2;
            this.hasSplit = z;
        }

        private int getWorkingMax() {
            return this.hasSplit ? this.max : DoubleArrayList.this.size;
        }

        @Override // java.util.Spliterator
        public long estimateSize() {
            return getWorkingMax() - this.pos;
        }

        @Override // java.util.Spliterator.OfPrimitive
        public boolean tryAdvance(java.util.function.DoubleConsumer doubleConsumer) {
            if (this.pos >= getWorkingMax()) {
                return false;
            }
            double[] dArr = DoubleArrayList.this.a;
            int i = this.pos;
            this.pos = i + 1;
            doubleConsumer.accept(dArr[i]);
            return true;
        }

        @Override // java.util.Spliterator.OfPrimitive
        public void forEachRemaining(java.util.function.DoubleConsumer doubleConsumer) {
            int workingMax = getWorkingMax();
            while (this.pos < workingMax) {
                doubleConsumer.accept(DoubleArrayList.this.a[this.pos]);
                this.pos++;
            }
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleSpliterator, java.util.Spliterator.OfDouble, java.util.Spliterator.OfPrimitive, java.util.Spliterator
        public DoubleSpliterator trySplit() {
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
            return DoubleArrayList.this.new Spliterator(i, i3, true);
        }
    }

    @Override // java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleSet, java.util.Set
    public DoubleSpliterator spliterator() {
        return new Spliterator(this);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleList
    public void sort(DoubleComparator doubleComparator) {
        if (doubleComparator == null) {
            DoubleArrays.stableSort(this.a, 0, this.size);
        } else {
            DoubleArrays.stableSort(this.a, 0, this.size, doubleComparator);
        }
    }

    public DoubleArrayList clone() {
        if (getClass() == DoubleArrayList.class) {
            DoubleArrayList doubleArrayList = new DoubleArrayList(copyArraySafe(this.a, this.size), false);
            doubleArrayList.size = this.size;
            return doubleArrayList;
        }
        try {
            DoubleArrayList doubleArrayList2 = (DoubleArrayList) super.clone();
            doubleArrayList2.a = copyArraySafe(this.a, this.size);
            return doubleArrayList2;
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
    }

    public boolean equals(DoubleArrayList doubleArrayList) {
        if (doubleArrayList == this) {
            return true;
        }
        int size = size();
        if (size != doubleArrayList.size()) {
            return false;
        }
        double[] dArr = this.a;
        double[] dArr2 = doubleArrayList.a;
        if (dArr == dArr2 && size == doubleArrayList.size()) {
            return true;
        }
        while (true) {
            int i = size - 1;
            if (size == 0) {
                return true;
            }
            if (dArr[i] != dArr2[i]) {
                return false;
            }
            size = i;
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleList, java.util.Collection, java.util.List
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || !(obj instanceof List)) {
            return false;
        }
        if (obj instanceof DoubleArrayList) {
            return equals((DoubleArrayList) obj);
        }
        if (obj instanceof SubList) {
            return ((SubList) obj).equals(this);
        }
        return super.equals(obj);
    }

    public int compareTo(DoubleArrayList doubleArrayList) {
        int size = size();
        int size2 = doubleArrayList.size();
        double[] dArr = this.a;
        double[] dArr2 = doubleArrayList.a;
        if (dArr == dArr2 && size == size2) {
            return 0;
        }
        int i = 0;
        while (i < size && i < size2) {
            int iCompare = Double.compare(dArr[i], dArr2[i]);
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

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleList, java.lang.Comparable
    public int compareTo(List<? extends Double> list) {
        if (list instanceof DoubleArrayList) {
            return compareTo((DoubleArrayList) list);
        }
        if (list instanceof SubList) {
            return -((SubList) list).compareTo((List<? extends Double>) this);
        }
        return super.compareTo(list);
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; i++) {
            objectOutputStream.writeDouble(this.a[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        this.a = new double[this.size];
        for (int i = 0; i < this.size; i++) {
            this.a[i] = objectInputStream.readDouble();
        }
    }
}
