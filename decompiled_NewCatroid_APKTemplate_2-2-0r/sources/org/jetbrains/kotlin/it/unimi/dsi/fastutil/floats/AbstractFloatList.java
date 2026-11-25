package org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats;

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
public abstract class AbstractFloatList extends AbstractFloatCollection implements FloatList {
    protected AbstractFloatList() {
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

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatList
    public void add(int i, float f) {
        throw new UnsupportedOperationException();
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.AbstractFloatCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatCollection
    public boolean add(float f) {
        add(size(), f);
        return true;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatList
    public float removeFloat(int i) {
        throw new UnsupportedOperationException();
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatList
    public float set(int i, float f) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List
    public boolean addAll(int i, Collection<? extends Float> collection) {
        if (collection instanceof FloatCollection) {
            return addAll(i, (FloatCollection) collection);
        }
        ensureIndex(i);
        Iterator<? extends Float> it = collection.iterator();
        boolean zHasNext = it.hasNext();
        while (it.hasNext()) {
            add(i, it.next().floatValue());
            i++;
        }
        return zHasNext;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.AbstractFloatCollection, java.util.AbstractCollection, java.util.Collection
    public boolean addAll(Collection<? extends Float> collection) {
        return addAll(size(), collection);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.AbstractFloatCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatIterable
    public FloatListIterator iterator() {
        return listIterator();
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatList, java.util.List
    public FloatListIterator listIterator() {
        return listIterator(0);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatList, java.util.List
    public FloatListIterator listIterator(int i) {
        ensureIndex(i);
        return new FloatIterators.AbstractIndexBasedListIterator(0, i) { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.AbstractFloatList.1
            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatIterators.AbstractIndexBasedIterator
            protected final float get(int i2) {
                return AbstractFloatList.this.getFloat(i2);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatIterators.AbstractIndexBasedListIterator
            protected final void add(int i2, float f) {
                AbstractFloatList.this.add(i2, f);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatIterators.AbstractIndexBasedListIterator
            protected final void set(int i2, float f) {
                AbstractFloatList.this.set(i2, f);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatIterators.AbstractIndexBasedIterator
            protected final void remove(int i2) {
                AbstractFloatList.this.removeFloat(i2);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatIterators.AbstractIndexBasedIterator
            protected final int getMaxPos() {
                return AbstractFloatList.this.size();
            }
        };
    }

    static final class IndexBasedSpliterator extends FloatSpliterators.LateBindingSizeIndexBasedSpliterator {
        final FloatList l;

        IndexBasedSpliterator(FloatList floatList, int i) {
            super(i);
            this.l = floatList;
        }

        IndexBasedSpliterator(FloatList floatList, int i, int i2) {
            super(i, i2);
            this.l = floatList;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatSpliterators.LateBindingSizeIndexBasedSpliterator
        protected final int getMaxPosFromBackingStore() {
            return this.l.size();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatSpliterators.AbstractIndexBasedSpliterator
        protected final float get(int i) {
            return this.l.getFloat(i);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatSpliterators.AbstractIndexBasedSpliterator
        public final IndexBasedSpliterator makeForSplit(int i, int i2) {
            return new IndexBasedSpliterator(this.l, i, i2);
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.AbstractFloatCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatCollection
    public boolean contains(float f) {
        return indexOf(f) >= 0;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatList
    public int indexOf(float f) {
        FloatListIterator floatListIteratorListIterator = listIterator();
        while (floatListIteratorListIterator.hasNext()) {
            if (Float.floatToIntBits(f) == Float.floatToIntBits(floatListIteratorListIterator.nextFloat())) {
                return floatListIteratorListIterator.previousIndex();
            }
        }
        return -1;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatList
    public int lastIndexOf(float f) {
        FloatListIterator floatListIteratorListIterator = listIterator(size());
        while (floatListIteratorListIterator.hasPrevious()) {
            if (Float.floatToIntBits(f) == Float.floatToIntBits(floatListIteratorListIterator.previousFloat())) {
                return floatListIteratorListIterator.nextIndex();
            }
        }
        return -1;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatList, java.util.List
    public FloatList subList(int i, int i2) {
        ensureIndex(i);
        ensureIndex(i2);
        if (i <= i2) {
            return this instanceof RandomAccess ? new FloatRandomAccessSubList(this, i, i2) : new FloatSubList(this, i, i2);
        }
        throw new IndexOutOfBoundsException("Start index (" + i + ") is greater than end index (" + i2 + Constant.AFTER_QUTO);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatIterable
    public void forEach(FloatConsumer floatConsumer) {
        if (this instanceof RandomAccess) {
            int size = size();
            for (int i = 0; i < size; i++) {
                floatConsumer.accept(getFloat(i));
            }
            return;
        }
        super.forEach(floatConsumer);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatList
    public void removeElements(int i, int i2) {
        ensureIndex(i2);
        FloatListIterator floatListIteratorListIterator = listIterator(i);
        int i3 = i2 - i;
        if (i3 < 0) {
            throw new IllegalArgumentException("Start index (" + i + ") is greater than end index (" + i2 + Constant.AFTER_QUTO);
        }
        while (true) {
            int i4 = i3 - 1;
            if (i3 == 0) {
                return;
            }
            floatListIteratorListIterator.nextFloat();
            floatListIteratorListIterator.remove();
            i3 = i4;
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatList
    public void getElements(int i, float[] fArr, int i2, int i3) {
        ensureIndex(i);
        FloatArrays.ensureOffsetLength(fArr, i2, i3);
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
                fArr[i2] = getFloat(i);
                i2++;
                i3 = i5;
                i++;
            }
        } else {
            FloatListIterator floatListIteratorListIterator = listIterator(i);
            while (true) {
                int i6 = i3 - 1;
                if (i3 == 0) {
                    return;
                }
                fArr[i2] = floatListIteratorListIterator.nextFloat();
                i2++;
                i3 = i6;
            }
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatList
    public void setElements(int i, float[] fArr, int i2, int i3) {
        ensureIndex(i);
        FloatArrays.ensureOffsetLength(fArr, i2, i3);
        int i4 = i + i3;
        if (i4 > size()) {
            throw new IndexOutOfBoundsException("End index (" + i4 + ") is greater than list size (" + size() + Constant.AFTER_QUTO);
        }
        int i5 = 0;
        if (this instanceof RandomAccess) {
            while (i5 < i3) {
                set(i5 + i, fArr[i5 + i2]);
                i5++;
            }
        } else {
            FloatListIterator floatListIteratorListIterator = listIterator(i);
            while (i5 < i3) {
                floatListIteratorListIterator.nextFloat();
                floatListIteratorListIterator.set(fArr[i5 + i2]);
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
        FloatListIterator it = iterator();
        int size = size();
        int iFloat2int = 1;
        while (true) {
            int i = size - 1;
            if (size == 0) {
                return iFloat2int;
            }
            iFloat2int = (iFloat2int * 31) + HashCommon.float2int(it.nextFloat());
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
        if (list instanceof FloatList) {
            FloatListIterator floatListIteratorListIterator = listIterator();
            FloatListIterator floatListIteratorListIterator2 = ((FloatList) list).listIterator();
            while (true) {
                int i = size - 1;
                if (size == 0) {
                    return true;
                }
                if (floatListIteratorListIterator.nextFloat() != floatListIteratorListIterator2.nextFloat()) {
                    return false;
                }
                size = i;
            }
        } else {
            FloatListIterator floatListIteratorListIterator3 = listIterator();
            ListIterator listIterator = list.listIterator();
            while (true) {
                int i2 = size - 1;
                if (size == 0) {
                    return true;
                }
                if (!Objects.equals(floatListIteratorListIterator3.next(), listIterator.next())) {
                    return false;
                }
                size = i2;
            }
        }
    }

    @Override // java.lang.Comparable
    public int compareTo(List<? extends Float> list) {
        if (list == this) {
            return 0;
        }
        if (list instanceof FloatList) {
            FloatListIterator floatListIteratorListIterator = listIterator();
            FloatListIterator floatListIteratorListIterator2 = ((FloatList) list).listIterator();
            while (floatListIteratorListIterator.hasNext() && floatListIteratorListIterator2.hasNext()) {
                int iCompare = Float.compare(floatListIteratorListIterator.nextFloat(), floatListIteratorListIterator2.nextFloat());
                if (iCompare != 0) {
                    return iCompare;
                }
            }
            if (floatListIteratorListIterator2.hasNext()) {
                return -1;
            }
            return floatListIteratorListIterator.hasNext() ? 1 : 0;
        }
        FloatListIterator floatListIteratorListIterator3 = listIterator();
        ListIterator<? extends Float> listIterator = list.listIterator();
        while (floatListIteratorListIterator3.hasNext() && listIterator.hasNext()) {
            int iCompareTo = floatListIteratorListIterator3.next().compareTo(listIterator.next());
            if (iCompareTo != 0) {
                return iCompareTo;
            }
        }
        if (listIterator.hasNext()) {
            return -1;
        }
        return floatListIteratorListIterator3.hasNext() ? 1 : 0;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.AbstractFloatCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatCollection
    public boolean rem(float f) {
        int iIndexOf = indexOf(f);
        if (iIndexOf == -1) {
            return false;
        }
        removeFloat(iIndexOf);
        return true;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.AbstractFloatCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatCollection
    public float[] toFloatArray() {
        int size = size();
        float[] fArr = new float[size];
        getElements(0, fArr, 0, size);
        return fArr;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.AbstractFloatCollection
    public float[] toArray(float[] fArr) {
        int size = size();
        if (fArr.length < size) {
            fArr = Arrays.copyOf(fArr, size);
        }
        getElements(0, fArr, 0, size);
        return fArr;
    }

    public boolean addAll(int i, FloatCollection floatCollection) {
        ensureIndex(i);
        FloatIterator it = floatCollection.iterator();
        boolean zHasNext = it.hasNext();
        while (it.hasNext()) {
            add(i, it.nextFloat());
            i++;
        }
        return zHasNext;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.AbstractFloatCollection
    public boolean addAll(FloatCollection floatCollection) {
        return addAll(size(), floatCollection);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.AbstractFloatCollection, java.util.AbstractCollection
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        FloatListIterator it = iterator();
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
                sb.append(String.valueOf(it.nextFloat()));
                size = i;
            } else {
                sb.append("]");
                return sb.toString();
            }
        }
    }

    public static class FloatSubList extends AbstractFloatList implements Serializable {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private static final long serialVersionUID = -7046029254386353129L;
        protected final int from;
        protected final FloatList l;
        protected int to;

        /* JADX INFO: Access modifiers changed from: private */
        public boolean assertRange() {
            return true;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.AbstractFloatList, java.lang.Comparable
        public /* bridge */ /* synthetic */ int compareTo(List<? extends Float> list) {
            return super.compareTo(list);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.AbstractFloatList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.AbstractFloatCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatIterable
        public /* bridge */ /* synthetic */ Iterator iterator() {
            return super.iterator();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.AbstractFloatList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.AbstractFloatCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatIterable
        public /* bridge */ /* synthetic */ FloatIterator iterator() {
            return super.iterator();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.AbstractFloatList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatList, java.util.List
        public /* bridge */ /* synthetic */ ListIterator listIterator() {
            return super.listIterator();
        }

        public FloatSubList(FloatList floatList, int i, int i2) {
            this.l = floatList;
            this.from = i;
            this.to = i2;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.AbstractFloatList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.AbstractFloatCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatCollection
        public boolean add(float f) {
            this.l.add(this.to, f);
            this.to++;
            return true;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.AbstractFloatList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatList
        public void add(int i, float f) {
            ensureIndex(i);
            this.l.add(this.from + i, f);
            this.to++;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.AbstractFloatList, java.util.List
        public boolean addAll(int i, Collection<? extends Float> collection) {
            ensureIndex(i);
            this.to += collection.size();
            return this.l.addAll(this.from + i, collection);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatList
        public float getFloat(int i) {
            ensureRestrictedIndex(i);
            return this.l.getFloat(this.from + i);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.AbstractFloatList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatList
        public float removeFloat(int i) {
            ensureRestrictedIndex(i);
            this.to--;
            return this.l.removeFloat(this.from + i);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.AbstractFloatList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatList
        public float set(int i, float f) {
            ensureRestrictedIndex(i);
            return this.l.set(this.from + i, f);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return this.to - this.from;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.AbstractFloatList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatList
        public void getElements(int i, float[] fArr, int i2, int i3) {
            ensureIndex(i);
            if (i + i3 > size()) {
                throw new IndexOutOfBoundsException("End index (" + i + i3 + ") is greater than list size (" + size() + Constant.AFTER_QUTO);
            }
            this.l.getElements(this.from + i, fArr, i2, i3);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.AbstractFloatList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatList
        public void removeElements(int i, int i2) {
            ensureIndex(i);
            ensureIndex(i2);
            FloatList floatList = this.l;
            int i3 = this.from;
            floatList.removeElements(i3 + i, i3 + i2);
            this.to -= i2 - i;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.AbstractFloatList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatList
        public void setElements(int i, float[] fArr, int i2, int i3) {
            ensureIndex(i);
            this.l.setElements(this.from + i, fArr, i2, i3);
        }

        private final class RandomAccessIter extends FloatIterators.AbstractIndexBasedListIterator {
            static final /* synthetic */ boolean $assertionsDisabled = false;

            RandomAccessIter(int i) {
                super(0, i);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatIterators.AbstractIndexBasedIterator
            protected final float get(int i) {
                return FloatSubList.this.l.getFloat(FloatSubList.this.from + i);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatIterators.AbstractIndexBasedListIterator
            protected final void add(int i, float f) {
                FloatSubList.this.add(i, f);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatIterators.AbstractIndexBasedListIterator
            protected final void set(int i, float f) {
                FloatSubList.this.set(i, f);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatIterators.AbstractIndexBasedIterator
            protected final void remove(int i) {
                FloatSubList.this.removeFloat(i);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatIterators.AbstractIndexBasedIterator
            protected final int getMaxPos() {
                return FloatSubList.this.to - FloatSubList.this.from;
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatIterators.AbstractIndexBasedListIterator, org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatListIterator
            public void add(float f) {
                super.add(f);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatIterators.AbstractIndexBasedIterator, java.util.Iterator, org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatListIterator, java.util.ListIterator
            public void remove() {
                super.remove();
            }
        }

        private class ParentWrappingIter implements FloatListIterator {
            private FloatListIterator parent;

            ParentWrappingIter(FloatListIterator floatListIterator) {
                this.parent = floatListIterator;
            }

            @Override // java.util.ListIterator
            public int nextIndex() {
                return this.parent.nextIndex() - FloatSubList.this.from;
            }

            @Override // java.util.ListIterator
            public int previousIndex() {
                return this.parent.previousIndex() - FloatSubList.this.from;
            }

            @Override // java.util.Iterator, java.util.ListIterator
            public boolean hasNext() {
                return this.parent.nextIndex() < FloatSubList.this.to;
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.BidirectionalIterator, java.util.ListIterator
            public boolean hasPrevious() {
                return this.parent.previousIndex() >= FloatSubList.this.from;
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatIterator
            public float nextFloat() {
                if (hasNext()) {
                    return this.parent.nextFloat();
                }
                throw new NoSuchElementException();
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatBidirectionalIterator
            public float previousFloat() {
                if (hasPrevious()) {
                    return this.parent.previousFloat();
                }
                throw new NoSuchElementException();
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatListIterator
            public void add(float f) {
                this.parent.add(f);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatListIterator
            public void set(float f) {
                this.parent.set(f);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatListIterator, java.util.Iterator, java.util.ListIterator
            public void remove() {
                this.parent.remove();
            }
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.AbstractFloatList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatList, java.util.List
        public FloatListIterator listIterator(int i) {
            ensureIndex(i);
            return this.l instanceof RandomAccess ? new RandomAccessIter(i) : new ParentWrappingIter(this.l.listIterator(i + this.from));
        }

        @Override // java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatList, java.util.List
        public FloatSpliterator spliterator() {
            return this.l instanceof RandomAccess ? new IndexBasedSpliterator(this.l, this.from, this.to) : super.spliterator();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.AbstractFloatList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatList, java.util.List
        public FloatList subList(int i, int i2) {
            ensureIndex(i);
            ensureIndex(i2);
            if (i > i2) {
                throw new IllegalArgumentException("Start index (" + i + ") is greater than end index (" + i2 + Constant.AFTER_QUTO);
            }
            return new FloatSubList(this, i, i2);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.AbstractFloatList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.AbstractFloatCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatCollection
        public boolean rem(float f) {
            int iIndexOf = indexOf(f);
            if (iIndexOf == -1) {
                return false;
            }
            this.to--;
            this.l.removeFloat(this.from + iIndexOf);
            return true;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.AbstractFloatList
        public boolean addAll(int i, FloatCollection floatCollection) {
            ensureIndex(i);
            return super.addAll(i, floatCollection);
        }
    }

    public static class FloatRandomAccessSubList extends FloatSubList implements RandomAccess {
        private static final long serialVersionUID = -107070782945191929L;

        public FloatRandomAccessSubList(FloatList floatList, int i, int i2) {
            super(floatList, i, i2);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.AbstractFloatList.FloatSubList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.AbstractFloatList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatList, java.util.List
        public FloatList subList(int i, int i2) {
            ensureIndex(i);
            ensureIndex(i2);
            if (i > i2) {
                throw new IllegalArgumentException("Start index (" + i + ") is greater than end index (" + i2 + Constant.AFTER_QUTO);
            }
            return new FloatRandomAccessSubList(this, i, i2);
        }
    }
}
