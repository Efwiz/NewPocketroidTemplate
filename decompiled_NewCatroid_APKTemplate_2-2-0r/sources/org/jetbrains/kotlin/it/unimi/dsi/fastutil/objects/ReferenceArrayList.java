package org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.RandomAccess;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collector;

/* JADX INFO: loaded from: classes5.dex */
public class ReferenceArrayList<K> extends AbstractReferenceList<K> implements Serializable, Cloneable, RandomAccess {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Collector<Object, ?, ReferenceArrayList<Object>> TO_LIST_COLLECTOR = Collector.of(new Supplier() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ReferenceArrayList$$ExternalSyntheticLambda0
        @Override // java.util.function.Supplier
        public final Object get() {
            return new ReferenceArrayList();
        }
    }, new BiConsumer() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ReferenceArrayList$$ExternalSyntheticLambda1
        @Override // java.util.function.BiConsumer
        public final void accept(Object obj, Object obj2) {
            ((ReferenceArrayList) obj).add(obj2);
        }
    }, new BinaryOperator() { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ReferenceArrayList$$ExternalSyntheticLambda2
        @Override // java.util.function.BiFunction
        public final Object apply(Object obj, Object obj2) {
            return ((ReferenceArrayList) obj).combine((ReferenceArrayList) obj2);
        }
    }, new Collector.Characteristics[0]);
    private static final long serialVersionUID = -7046029254386353131L;
    protected transient K[] a;
    protected int size;
    protected final boolean wrapped;

    private static final <K> K[] copyArraySafe(K[] kArr, int i) {
        if (i == 0) {
            return (K[]) ObjectArrays.EMPTY_ARRAY;
        }
        return (K[]) Arrays.copyOf(kArr, i, Object[].class);
    }

    protected ReferenceArrayList(K[] kArr, boolean z) {
        this.a = kArr;
        this.wrapped = z;
    }

    private void initArrayFromCapacity(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("Initial capacity (" + i + ") is negative");
        }
        if (i == 0) {
            this.a = (K[]) ObjectArrays.EMPTY_ARRAY;
        } else {
            this.a = (K[]) new Object[i];
        }
    }

    public ReferenceArrayList(int i) {
        initArrayFromCapacity(i);
        this.wrapped = false;
    }

    public ReferenceArrayList() {
        this.a = (K[]) ObjectArrays.DEFAULT_EMPTY_ARRAY;
        this.wrapped = false;
    }

    ReferenceArrayList<K> combine(ReferenceArrayList<? extends K> referenceArrayList) {
        addAll((ReferenceList) referenceArrayList);
        return this;
    }

    private void grow(int i) {
        K[] kArr = this.a;
        if (i <= kArr.length) {
            return;
        }
        if (kArr != ObjectArrays.DEFAULT_EMPTY_ARRAY) {
            K[] kArr2 = this.a;
            i = (int) Math.max(Math.min(((long) kArr2.length) + ((long) (kArr2.length >> 1)), 2147483639L), i);
        } else if (i < 10) {
            i = 10;
        }
        if (this.wrapped) {
            this.a = (K[]) ObjectArrays.forceCapacity(this.a, i, this.size);
            return;
        }
        K[] kArr3 = (K[]) new Object[i];
        System.arraycopy(this.a, 0, kArr3, 0, this.size);
        this.a = kArr3;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractReferenceList, java.util.List
    public void add(int i, K k) {
        ensureIndex(i);
        grow(this.size + 1);
        int i2 = this.size;
        if (i != i2) {
            K[] kArr = this.a;
            System.arraycopy(kArr, i, kArr, i + 1, i2 - i);
        }
        this.a[i] = k;
        this.size++;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractReferenceList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean add(K k) {
        grow(this.size + 1);
        K[] kArr = this.a;
        int i = this.size;
        this.size = i + 1;
        kArr[i] = k;
        return true;
    }

    @Override // java.util.List
    public K get(int i) {
        if (i >= this.size) {
            throw new IndexOutOfBoundsException("Index (" + i + ") is greater than or equal to list size (" + this.size + Constant.AFTER_QUTO);
        }
        return this.a[i];
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractReferenceList, java.util.List
    public int indexOf(Object obj) {
        for (int i = 0; i < this.size; i++) {
            if (obj == this.a[i]) {
                return i;
            }
        }
        return -1;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractReferenceList, java.util.List
    public int lastIndexOf(Object obj) {
        int i = this.size;
        while (true) {
            int i2 = i - 1;
            if (i == 0) {
                return -1;
            }
            if (obj == this.a[i2]) {
                return i2;
            }
            i = i2;
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractReferenceList, java.util.List
    public K remove(int i) {
        int i2 = this.size;
        if (i >= i2) {
            throw new IndexOutOfBoundsException("Index (" + i + ") is greater than or equal to list size (" + this.size + Constant.AFTER_QUTO);
        }
        K[] kArr = this.a;
        K k = kArr[i];
        int i3 = i2 - 1;
        this.size = i3;
        if (i != i3) {
            System.arraycopy(kArr, i + 1, kArr, i, i3 - i);
        }
        this.a[this.size] = null;
        return k;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean remove(Object obj) {
        int iIndexOf = indexOf(obj);
        if (iIndexOf == -1) {
            return false;
        }
        remove(iIndexOf);
        return true;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractReferenceList, java.util.List
    public K set(int i, K k) {
        if (i >= this.size) {
            throw new IndexOutOfBoundsException("Index (" + i + ") is greater than or equal to list size (" + this.size + Constant.AFTER_QUTO);
        }
        K[] kArr = this.a;
        K k2 = kArr[i];
        kArr[i] = k;
        return k2;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractReferenceList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public void clear() {
        Arrays.fill(this.a, 0, this.size, (Object) null);
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

    private class SubList extends AbstractReferenceList.ReferenceRandomAccessSubList<K> {
        private static final long serialVersionUID = -3185226345314976296L;

        protected SubList(int i, int i2) {
            super(ReferenceArrayList.this, i, i2);
        }

        private K[] getParentArray() {
            return ReferenceArrayList.this.a;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractReferenceList.ReferenceSubList, java.util.List
        public K get(int i) {
            ensureRestrictedIndex(i);
            return ReferenceArrayList.this.a[i + this.from];
        }

        private final class SubListIterator extends ObjectIterators.AbstractIndexBasedListIterator<K> {
            SubListIterator(int i) {
                super(0, i);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterators.AbstractIndexBasedIterator
            protected final K get(int i) {
                return ReferenceArrayList.this.a[SubList.this.from + i];
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterators.AbstractIndexBasedListIterator
            protected final void add(int i, K k) {
                SubList.this.add(i, k);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterators.AbstractIndexBasedListIterator
            protected final void set(int i, K k) {
                SubList.this.set(i, k);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterators.AbstractIndexBasedIterator
            protected final void remove(int i) {
                SubList.this.remove(i);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterators.AbstractIndexBasedIterator
            protected final int getMaxPos() {
                return SubList.this.to - SubList.this.from;
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterators.AbstractIndexBasedIterator, java.util.Iterator, java.util.ListIterator
            public K next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                K[] kArr = ReferenceArrayList.this.a;
                int i = SubList.this.from;
                int i2 = this.pos;
                this.pos = i2 + 1;
                this.lastReturned = i2;
                return kArr[i + i2];
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterators.AbstractIndexBasedListIterator, org.jetbrains.kotlin.it.unimi.dsi.fastutil.BidirectionalIterator
            public K previous() {
                if (!hasPrevious()) {
                    throw new NoSuchElementException();
                }
                K[] kArr = ReferenceArrayList.this.a;
                int i = SubList.this.from;
                int i2 = this.pos - 1;
                this.pos = i2;
                this.lastReturned = i2;
                return kArr[i + i2];
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterators.AbstractIndexBasedIterator, java.util.Iterator
            public void forEachRemaining(Consumer<? super K> consumer) {
                int i = SubList.this.to - SubList.this.from;
                while (this.pos < i) {
                    K[] kArr = ReferenceArrayList.this.a;
                    int i2 = SubList.this.from;
                    int i3 = this.pos;
                    this.pos = i3 + 1;
                    this.lastReturned = i3;
                    consumer.accept(kArr[i2 + i3]);
                }
            }
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractReferenceList.ReferenceSubList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractReferenceList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ReferenceList, java.util.List
        public ObjectListIterator<K> listIterator(int i) {
            return new SubListIterator(i);
        }

        private final class SubListSpliterator extends ObjectSpliterators.LateBindingSizeIndexBasedSpliterator<K> {
            SubListSpliterator() {
                super(SubList.this.from);
            }

            private SubListSpliterator(int i, int i2) {
                super(i, i2);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectSpliterators.LateBindingSizeIndexBasedSpliterator
            protected final int getMaxPosFromBackingStore() {
                return SubList.this.to;
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectSpliterators.AbstractIndexBasedSpliterator
            protected final K get(int i) {
                return ReferenceArrayList.this.a[i];
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectSpliterators.AbstractIndexBasedSpliterator
            public final ReferenceArrayList<K>.SubList.SubListSpliterator makeForSplit(int i, int i2) {
                return SubList.this.new SubListSpliterator(i, i2);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectSpliterators.AbstractIndexBasedSpliterator, java.util.Spliterator
            public boolean tryAdvance(Consumer<? super K> consumer) {
                if (this.pos >= getMaxPos()) {
                    return false;
                }
                K[] kArr = ReferenceArrayList.this.a;
                int i = this.pos;
                this.pos = i + 1;
                consumer.accept(kArr[i]);
                return true;
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectSpliterators.AbstractIndexBasedSpliterator, java.util.Spliterator
            public void forEachRemaining(Consumer<? super K> consumer) {
                int maxPos = getMaxPos();
                while (this.pos < maxPos) {
                    K[] kArr = ReferenceArrayList.this.a;
                    int i = this.pos;
                    this.pos = i + 1;
                    consumer.accept(kArr[i]);
                }
            }
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractReferenceList.ReferenceSubList, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ReferenceCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollection
        public ObjectSpliterator<K> spliterator() {
            return new SubListSpliterator();
        }

        boolean contentsEquals(K[] kArr, int i, int i2) {
            if (ReferenceArrayList.this.a == kArr && this.from == i && this.to == i2) {
                return true;
            }
            if (i2 - i != size()) {
                return false;
            }
            int i3 = this.from;
            while (i3 < this.to) {
                int i4 = i3 + 1;
                int i5 = i + 1;
                if (ReferenceArrayList.this.a[i3] != kArr[i]) {
                    return false;
                }
                i = i5;
                i3 = i4;
            }
            return true;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractReferenceList, java.util.Collection, java.util.List
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj == null || !(obj instanceof List)) {
                return false;
            }
            if (obj instanceof ReferenceArrayList) {
                ReferenceArrayList referenceArrayList = (ReferenceArrayList) obj;
                return contentsEquals(referenceArrayList.a, 0, referenceArrayList.size());
            }
            if (obj instanceof SubList) {
                SubList subList = (SubList) obj;
                return contentsEquals(subList.getParentArray(), subList.from, subList.to);
            }
            return super.equals(obj);
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractReferenceList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ReferenceList, java.util.List
    public ReferenceList<K> subList(int i, int i2) {
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

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractReferenceList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ReferenceList
    public void getElements(int i, Object[] objArr, int i2, int i3) {
        ObjectArrays.ensureOffsetLength(objArr, i2, i3);
        System.arraycopy(this.a, i, objArr, i2, i3);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractReferenceList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ReferenceList
    public void removeElements(int i, int i2) {
        org.jetbrains.kotlin.it.unimi.dsi.fastutil.Arrays.ensureFromTo(this.size, i, i2);
        K[] kArr = this.a;
        System.arraycopy(kArr, i2, kArr, i, this.size - i2);
        int i3 = i2 - i;
        this.size -= i3;
        while (true) {
            int i4 = i3 - 1;
            if (i3 == 0) {
                return;
            }
            this.a[this.size + i4] = null;
            i3 = i4;
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractReferenceList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ReferenceList
    public void setElements(int i, K[] kArr, int i2, int i3) {
        ensureIndex(i);
        ObjectArrays.ensureOffsetLength(kArr, i2, i3);
        int i4 = i + i3;
        if (i4 > this.size) {
            throw new IndexOutOfBoundsException("End index (" + i4 + ") is greater than list size (" + this.size + Constant.AFTER_QUTO);
        }
        System.arraycopy(kArr, i2, this.a, i, i3);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractReferenceList, java.lang.Iterable
    public void forEach(Consumer<? super K> consumer) {
        for (int i = 0; i < this.size; i++) {
            consumer.accept(this.a[i]);
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractReferenceList, java.util.List
    public boolean addAll(int i, Collection<? extends K> collection) {
        if (collection instanceof ReferenceList) {
            return addAll(i, (ReferenceList) collection);
        }
        ensureIndex(i);
        int size = collection.size();
        if (size == 0) {
            return false;
        }
        grow(this.size + size);
        K[] kArr = this.a;
        System.arraycopy(kArr, i, kArr, i + size, this.size - i);
        Iterator<? extends K> it = collection.iterator();
        this.size += size;
        while (true) {
            int i2 = size - 1;
            if (size == 0) {
                return true;
            }
            this.a[i] = it.next();
            size = i2;
            i++;
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ReferenceList
    public boolean addAll(int i, ReferenceList<? extends K> referenceList) {
        ensureIndex(i);
        int size = referenceList.size();
        if (size == 0) {
            return false;
        }
        grow(this.size + size);
        K[] kArr = this.a;
        System.arraycopy(kArr, i, kArr, i + size, this.size - i);
        referenceList.getElements(0, this.a, i, size);
        this.size += size;
        return true;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean removeAll(Collection<?> collection) {
        int i;
        K[] kArr = this.a;
        int i2 = 0;
        int i3 = 0;
        while (true) {
            i = this.size;
            if (i2 >= i) {
                break;
            }
            if (!collection.contains(kArr[i2])) {
                kArr[i3] = kArr[i2];
                i3++;
            }
            i2++;
        }
        Arrays.fill(kArr, i3, i, (Object) null);
        boolean z = this.size != i3;
        this.size = i3;
        return z;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractReferenceList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public Object[] toArray() {
        return Arrays.copyOf(this.a, size(), Object[].class);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractReferenceList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public <K> K[] toArray(K[] kArr) {
        if (kArr == null) {
            kArr = (K[]) new Object[size()];
        } else if (kArr.length < size()) {
            kArr = (K[]) ((Object[]) Array.newInstance(kArr.getClass().getComponentType(), size()));
        }
        System.arraycopy(this.a, 0, kArr, 0, size());
        if (kArr.length > size()) {
            kArr[size()] = null;
        }
        return kArr;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractReferenceList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ReferenceList, java.util.List
    public ObjectListIterator<K> listIterator(int i) {
        ensureIndex(i);
        return new ObjectListIterator<K>(i) { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ReferenceArrayList.1
            int last = -1;
            int pos;
            final /* synthetic */ int val$index;

            {
                this.val$index = i;
                this.pos = i;
            }

            @Override // java.util.Iterator, java.util.ListIterator
            public boolean hasNext() {
                return this.pos < ReferenceArrayList.this.size;
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.BidirectionalIterator, java.util.ListIterator
            public boolean hasPrevious() {
                return this.pos > 0;
            }

            @Override // java.util.Iterator, java.util.ListIterator
            public K next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                K[] kArr = ReferenceArrayList.this.a;
                int i2 = this.pos;
                this.pos = i2 + 1;
                this.last = i2;
                return kArr[i2];
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.BidirectionalIterator
            public K previous() {
                if (!hasPrevious()) {
                    throw new NoSuchElementException();
                }
                K[] kArr = ReferenceArrayList.this.a;
                int i2 = this.pos - 1;
                this.pos = i2;
                this.last = i2;
                return kArr[i2];
            }

            @Override // java.util.ListIterator
            public int nextIndex() {
                return this.pos;
            }

            @Override // java.util.ListIterator
            public int previousIndex() {
                return this.pos - 1;
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectListIterator, java.util.ListIterator
            public void add(K k) {
                ReferenceArrayList referenceArrayList = ReferenceArrayList.this;
                int i2 = this.pos;
                this.pos = i2 + 1;
                referenceArrayList.add(i2, k);
                this.last = -1;
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectListIterator, java.util.ListIterator
            public void set(K k) {
                int i2 = this.last;
                if (i2 == -1) {
                    throw new IllegalStateException();
                }
                ReferenceArrayList.this.set(i2, k);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectListIterator, java.util.Iterator, java.util.ListIterator
            public void remove() {
                int i2 = this.last;
                if (i2 == -1) {
                    throw new IllegalStateException();
                }
                ReferenceArrayList.this.remove(i2);
                int i3 = this.last;
                int i4 = this.pos;
                if (i3 < i4) {
                    this.pos = i4 - 1;
                }
                this.last = -1;
            }

            @Override // java.util.Iterator
            public void forEachRemaining(Consumer<? super K> consumer) {
                while (this.pos < ReferenceArrayList.this.size) {
                    K[] kArr = ReferenceArrayList.this.a;
                    int i2 = this.pos;
                    this.pos = i2 + 1;
                    this.last = i2;
                    consumer.accept(kArr[i2]);
                }
            }
        };
    }

    private final class Spliterator implements ObjectSpliterator<K> {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        boolean hasSplit;
        int max;
        int pos;

        @Override // java.util.Spliterator
        public int characteristics() {
            return 16464;
        }

        public Spliterator(ReferenceArrayList referenceArrayList) {
            this(0, referenceArrayList.size, false);
        }

        private Spliterator(int i, int i2, boolean z) {
            this.pos = i;
            this.max = i2;
            this.hasSplit = z;
        }

        private int getWorkingMax() {
            return this.hasSplit ? this.max : ReferenceArrayList.this.size;
        }

        @Override // java.util.Spliterator
        public long estimateSize() {
            return getWorkingMax() - this.pos;
        }

        @Override // java.util.Spliterator
        public boolean tryAdvance(Consumer<? super K> consumer) {
            if (this.pos >= getWorkingMax()) {
                return false;
            }
            K[] kArr = ReferenceArrayList.this.a;
            int i = this.pos;
            this.pos = i + 1;
            consumer.accept(kArr[i]);
            return true;
        }

        @Override // java.util.Spliterator
        public void forEachRemaining(Consumer<? super K> consumer) {
            int workingMax = getWorkingMax();
            while (this.pos < workingMax) {
                consumer.accept(ReferenceArrayList.this.a[this.pos]);
                this.pos++;
            }
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectSpliterator, java.util.Spliterator
        public ObjectSpliterator<K> trySplit() {
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
            return new Spliterator(i, i3, true);
        }
    }

    @Override // java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ReferenceCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollection
    public ObjectSpliterator<K> spliterator() {
        return new Spliterator(this);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ReferenceList, java.util.List
    public void sort(Comparator<? super K> comparator) {
        if (comparator == null) {
            ObjectArrays.stableSort(this.a, 0, this.size);
        } else {
            ObjectArrays.stableSort(this.a, 0, this.size, comparator);
        }
    }

    public ReferenceArrayList<K> clone() {
        if (getClass() == ReferenceArrayList.class) {
            ReferenceArrayList<K> referenceArrayList = new ReferenceArrayList<>(copyArraySafe(this.a, this.size), false);
            referenceArrayList.size = this.size;
            return referenceArrayList;
        }
        try {
            ReferenceArrayList<K> referenceArrayList2 = (ReferenceArrayList) super.clone();
            referenceArrayList2.a = (K[]) copyArraySafe(this.a, this.size);
            return referenceArrayList2;
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
    }

    public boolean equals(ReferenceArrayList<K> referenceArrayList) {
        if (referenceArrayList == this) {
            return true;
        }
        int size = size();
        if (size != referenceArrayList.size()) {
            return false;
        }
        K[] kArr = this.a;
        K[] kArr2 = referenceArrayList.a;
        if (kArr == kArr2 && size == referenceArrayList.size()) {
            return true;
        }
        while (true) {
            int i = size - 1;
            if (size == 0) {
                return true;
            }
            if (kArr[i] != kArr2[i]) {
                return false;
            }
            size = i;
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractReferenceList, java.util.Collection, java.util.List
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || !(obj instanceof List)) {
            return false;
        }
        if (obj instanceof ReferenceArrayList) {
            return equals((ReferenceArrayList) obj);
        }
        if (obj instanceof SubList) {
            return ((SubList) obj).equals(this);
        }
        return super.equals(obj);
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; i++) {
            objectOutputStream.writeObject(this.a[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        this.a = (K[]) new Object[this.size];
        for (int i = 0; i < this.size; i++) {
            ((K[]) this.a)[i] = objectInputStream.readObject();
        }
    }
}
