package org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects;

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
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes5.dex */
public abstract class AbstractObjectList<K> extends AbstractObjectCollection<K> implements ObjectList<K> {
    protected AbstractObjectList() {
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

    @Override // java.util.List
    public void add(int i, K k) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean add(K k) {
        add(size(), k);
        return true;
    }

    @Override // java.util.List
    public K remove(int i) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List
    public K set(int i, K k) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List
    public boolean addAll(int i, Collection<? extends K> collection) {
        ensureIndex(i);
        Iterator<? extends K> it = collection.iterator();
        boolean zHasNext = it.hasNext();
        while (it.hasNext()) {
            add(i, it.next());
            i++;
        }
        return zHasNext;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean addAll(Collection<? extends K> collection) {
        return addAll(size(), collection);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractObjectCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterable
    public ObjectListIterator<K> iterator() {
        return listIterator();
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectList, java.util.List
    public ObjectListIterator<K> listIterator() {
        return listIterator(0);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectList, java.util.List
    public ObjectListIterator<K> listIterator(int i) {
        ensureIndex(i);
        return new ObjectIterators.AbstractIndexBasedListIterator<K>(0, i) { // from class: org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractObjectList.1
            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterators.AbstractIndexBasedIterator
            protected final K get(int i2) {
                return (K) AbstractObjectList.this.get(i2);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterators.AbstractIndexBasedListIterator
            protected final void add(int i2, K k) {
                AbstractObjectList.this.add(i2, k);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterators.AbstractIndexBasedListIterator
            protected final void set(int i2, K k) {
                AbstractObjectList.this.set(i2, k);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterators.AbstractIndexBasedIterator
            protected final void remove(int i2) {
                AbstractObjectList.this.remove(i2);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterators.AbstractIndexBasedIterator
            protected final int getMaxPos() {
                return AbstractObjectList.this.size();
            }
        };
    }

    static final class IndexBasedSpliterator<K> extends ObjectSpliterators.LateBindingSizeIndexBasedSpliterator<K> {
        final ObjectList<K> l;

        IndexBasedSpliterator(ObjectList<K> objectList, int i) {
            super(i);
            this.l = objectList;
        }

        IndexBasedSpliterator(ObjectList<K> objectList, int i, int i2) {
            super(i, i2);
            this.l = objectList;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectSpliterators.LateBindingSizeIndexBasedSpliterator
        protected final int getMaxPosFromBackingStore() {
            return this.l.size();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectSpliterators.AbstractIndexBasedSpliterator
        protected final K get(int i) {
            return this.l.get(i);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectSpliterators.AbstractIndexBasedSpliterator
        public final IndexBasedSpliterator<K> makeForSplit(int i, int i2) {
            return new IndexBasedSpliterator<>(this.l, i, i2);
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean contains(Object obj) {
        return indexOf(obj) >= 0;
    }

    @Override // java.util.List
    public int indexOf(Object obj) {
        ObjectListIterator<K> objectListIteratorListIterator = listIterator();
        while (objectListIteratorListIterator.hasNext()) {
            if (Objects.equals(obj, objectListIteratorListIterator.next())) {
                return objectListIteratorListIterator.previousIndex();
            }
        }
        return -1;
    }

    @Override // java.util.List
    public int lastIndexOf(Object obj) {
        ObjectListIterator<K> objectListIteratorListIterator = listIterator(size());
        while (objectListIteratorListIterator.hasPrevious()) {
            if (Objects.equals(obj, objectListIteratorListIterator.previous())) {
                return objectListIteratorListIterator.nextIndex();
            }
        }
        return -1;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectList, java.util.List
    public ObjectList<K> subList(int i, int i2) {
        ensureIndex(i);
        ensureIndex(i2);
        if (i <= i2) {
            return this instanceof RandomAccess ? new ObjectRandomAccessSubList(this, i, i2) : new ObjectSubList(this, i, i2);
        }
        throw new IndexOutOfBoundsException("Start index (" + i + ") is greater than end index (" + i2 + Constant.AFTER_QUTO);
    }

    @Override // java.lang.Iterable
    public void forEach(Consumer<? super K> consumer) {
        if (this instanceof RandomAccess) {
            int size = size();
            for (int i = 0; i < size; i++) {
                consumer.accept((Object) get(i));
            }
            return;
        }
        super.forEach(consumer);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectList
    public void removeElements(int i, int i2) {
        ensureIndex(i2);
        ObjectListIterator<K> objectListIteratorListIterator = listIterator(i);
        int i3 = i2 - i;
        if (i3 < 0) {
            throw new IllegalArgumentException("Start index (" + i + ") is greater than end index (" + i2 + Constant.AFTER_QUTO);
        }
        while (true) {
            int i4 = i3 - 1;
            if (i3 == 0) {
                return;
            }
            objectListIteratorListIterator.next();
            objectListIteratorListIterator.remove();
            i3 = i4;
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectList
    public void getElements(int i, Object[] objArr, int i2, int i3) {
        ensureIndex(i);
        ObjectArrays.ensureOffsetLength(objArr, i2, i3);
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
                objArr[i2] = get(i);
                i2++;
                i3 = i5;
                i++;
            }
        } else {
            ObjectListIterator<K> objectListIteratorListIterator = listIterator(i);
            while (true) {
                int i6 = i3 - 1;
                if (i3 == 0) {
                    return;
                }
                objArr[i2] = objectListIteratorListIterator.next();
                i2++;
                i3 = i6;
            }
        }
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectList
    public void setElements(int i, K[] kArr, int i2, int i3) {
        ensureIndex(i);
        ObjectArrays.ensureOffsetLength(kArr, i2, i3);
        int i4 = i + i3;
        if (i4 > size()) {
            throw new IndexOutOfBoundsException("End index (" + i4 + ") is greater than list size (" + size() + Constant.AFTER_QUTO);
        }
        int i5 = 0;
        if (this instanceof RandomAccess) {
            while (i5 < i3) {
                set(i5 + i, kArr[i5 + i2]);
                i5++;
            }
        } else {
            ObjectListIterator<K> objectListIteratorListIterator = listIterator(i);
            while (i5 < i3) {
                objectListIteratorListIterator.next();
                objectListIteratorListIterator.set(kArr[i5 + i2]);
                i5++;
            }
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public void clear() {
        removeElements(0, size());
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public Object[] toArray() {
        int size = size();
        Object[] objArr = new Object[size];
        getElements(0, objArr, 0, size);
        return objArr;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public <T> T[] toArray(T[] tArr) {
        int size = size();
        if (tArr.length < size) {
            tArr = (T[]) Arrays.copyOf(tArr, size);
        }
        getElements(0, tArr, 0, size);
        if (tArr.length > size) {
            tArr[size] = null;
        }
        return tArr;
    }

    @Override // java.util.Collection, java.util.List
    public int hashCode() {
        ObjectListIterator<K> it = iterator();
        int size = size();
        int iHashCode = 1;
        while (true) {
            int i = size - 1;
            if (size == 0) {
                return iHashCode;
            }
            K next = it.next();
            iHashCode = (iHashCode * 31) + (next == null ? 0 : next.hashCode());
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
        ObjectListIterator<K> objectListIteratorListIterator = listIterator();
        ListIterator listIterator = list.listIterator();
        while (true) {
            int i = size - 1;
            if (size == 0) {
                return true;
            }
            if (!Objects.equals(objectListIteratorListIterator.next(), listIterator.next())) {
                return false;
            }
            size = i;
        }
    }

    @Override // java.lang.Comparable
    public int compareTo(List<? extends K> list) {
        if (list == this) {
            return 0;
        }
        if (list instanceof ObjectList) {
            ObjectListIterator<K> objectListIteratorListIterator = listIterator();
            ObjectListIterator<K> objectListIteratorListIterator2 = ((ObjectList) list).listIterator();
            while (objectListIteratorListIterator.hasNext() && objectListIteratorListIterator2.hasNext()) {
                int iCompareTo = ((Comparable) objectListIteratorListIterator.next()).compareTo(objectListIteratorListIterator2.next());
                if (iCompareTo != 0) {
                    return iCompareTo;
                }
            }
            if (objectListIteratorListIterator2.hasNext()) {
                return -1;
            }
            return objectListIteratorListIterator.hasNext() ? 1 : 0;
        }
        ObjectListIterator<K> objectListIteratorListIterator3 = listIterator();
        ListIterator<? extends K> listIterator = list.listIterator();
        while (objectListIteratorListIterator3.hasNext() && listIterator.hasNext()) {
            int iCompareTo2 = ((Comparable) objectListIteratorListIterator3.next()).compareTo(listIterator.next());
            if (iCompareTo2 != 0) {
                return iCompareTo2;
            }
        }
        if (listIterator.hasNext()) {
            return -1;
        }
        return objectListIteratorListIterator3.hasNext() ? 1 : 0;
    }

    public void push(K k) {
        add(k);
    }

    public K pop() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return remove(size() - 1);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractObjectCollection, java.util.AbstractCollection
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        ObjectListIterator<K> it = iterator();
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
                K next = it.next();
                if (this == next) {
                    sb.append("(this list)");
                } else {
                    sb.append(String.valueOf(next));
                }
                size = i;
            } else {
                sb.append("]");
                return sb.toString();
            }
        }
    }

    public static class ObjectSubList<K> extends AbstractObjectList<K> implements Serializable {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private static final long serialVersionUID = -7046029254386353129L;
        protected final int from;
        protected final ObjectList<K> l;
        protected int to;

        /* JADX INFO: Access modifiers changed from: private */
        public boolean assertRange() {
            return true;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractObjectList, java.lang.Comparable
        public /* bridge */ /* synthetic */ int compareTo(Object obj) {
            return super.compareTo((List) obj);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractObjectList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractObjectCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterable
        public /* bridge */ /* synthetic */ Iterator iterator() {
            return super.iterator();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractObjectList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractObjectCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterable
        public /* bridge */ /* synthetic */ ObjectIterator iterator() {
            return super.iterator();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractObjectList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectList, java.util.List
        public /* bridge */ /* synthetic */ ListIterator listIterator() {
            return super.listIterator();
        }

        public ObjectSubList(ObjectList<K> objectList, int i, int i2) {
            this.l = objectList;
            this.from = i;
            this.to = i2;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractObjectList, java.util.AbstractCollection, java.util.Collection, java.util.List
        public boolean add(K k) {
            this.l.add(this.to, k);
            this.to++;
            return true;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractObjectList, java.util.List
        public void add(int i, K k) {
            ensureIndex(i);
            this.l.add(this.from + i, k);
            this.to++;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractObjectList, java.util.List
        public boolean addAll(int i, Collection<? extends K> collection) {
            ensureIndex(i);
            this.to += collection.size();
            return this.l.addAll(this.from + i, collection);
        }

        @Override // java.util.List
        public K get(int i) {
            ensureRestrictedIndex(i);
            return this.l.get(this.from + i);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractObjectList, java.util.List
        public K remove(int i) {
            ensureRestrictedIndex(i);
            this.to--;
            return this.l.remove(this.from + i);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractObjectList, java.util.List
        public K set(int i, K k) {
            ensureRestrictedIndex(i);
            return this.l.set(this.from + i, k);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return this.to - this.from;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractObjectList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectList
        public void getElements(int i, Object[] objArr, int i2, int i3) {
            ensureIndex(i);
            if (i + i3 > size()) {
                throw new IndexOutOfBoundsException("End index (" + i + i3 + ") is greater than list size (" + size() + Constant.AFTER_QUTO);
            }
            this.l.getElements(this.from + i, objArr, i2, i3);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractObjectList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectList
        public void removeElements(int i, int i2) {
            ensureIndex(i);
            ensureIndex(i2);
            ObjectList<K> objectList = this.l;
            int i3 = this.from;
            objectList.removeElements(i3 + i, i3 + i2);
            this.to -= i2 - i;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractObjectList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectList
        public void setElements(int i, K[] kArr, int i2, int i3) {
            ensureIndex(i);
            this.l.setElements(this.from + i, kArr, i2, i3);
        }

        private final class RandomAccessIter extends ObjectIterators.AbstractIndexBasedListIterator<K> {
            static final /* synthetic */ boolean $assertionsDisabled = false;

            RandomAccessIter(int i) {
                super(0, i);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterators.AbstractIndexBasedIterator
            protected final K get(int i) {
                return ObjectSubList.this.l.get(ObjectSubList.this.from + i);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterators.AbstractIndexBasedListIterator
            protected final void add(int i, K k) {
                ObjectSubList.this.add(i, k);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterators.AbstractIndexBasedListIterator
            protected final void set(int i, K k) {
                ObjectSubList.this.set(i, k);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterators.AbstractIndexBasedIterator
            protected final void remove(int i) {
                ObjectSubList.this.remove(i);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterators.AbstractIndexBasedIterator
            protected final int getMaxPos() {
                return ObjectSubList.this.to - ObjectSubList.this.from;
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterators.AbstractIndexBasedListIterator, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectListIterator, java.util.ListIterator
            public void add(K k) {
                super.add(k);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterators.AbstractIndexBasedIterator, java.util.Iterator, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectListIterator, java.util.ListIterator
            public void remove() {
                super.remove();
            }
        }

        private class ParentWrappingIter implements ObjectListIterator<K> {
            private ObjectListIterator<K> parent;

            ParentWrappingIter(ObjectListIterator<K> objectListIterator) {
                this.parent = objectListIterator;
            }

            @Override // java.util.ListIterator
            public int nextIndex() {
                return this.parent.nextIndex() - ObjectSubList.this.from;
            }

            @Override // java.util.ListIterator
            public int previousIndex() {
                return this.parent.previousIndex() - ObjectSubList.this.from;
            }

            @Override // java.util.Iterator, java.util.ListIterator
            public boolean hasNext() {
                return this.parent.nextIndex() < ObjectSubList.this.to;
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.BidirectionalIterator, java.util.ListIterator
            public boolean hasPrevious() {
                return this.parent.previousIndex() >= ObjectSubList.this.from;
            }

            @Override // java.util.Iterator, java.util.ListIterator
            public K next() {
                if (hasNext()) {
                    return this.parent.next();
                }
                throw new NoSuchElementException();
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.BidirectionalIterator
            public K previous() {
                if (hasPrevious()) {
                    return this.parent.previous();
                }
                throw new NoSuchElementException();
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectListIterator, java.util.ListIterator
            public void add(K k) {
                this.parent.add(k);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectListIterator, java.util.ListIterator
            public void set(K k) {
                this.parent.set(k);
            }

            @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectListIterator, java.util.Iterator, java.util.ListIterator
            public void remove() {
                this.parent.remove();
            }
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractObjectList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectList, java.util.List
        public ObjectListIterator<K> listIterator(int i) {
            ensureIndex(i);
            return this.l instanceof RandomAccess ? new RandomAccessIter(i) : new ParentWrappingIter(this.l.listIterator(i + this.from));
        }

        @Override // java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterable
        public ObjectSpliterator<K> spliterator() {
            return this.l instanceof RandomAccess ? new IndexBasedSpliterator(this.l, this.from, this.to) : super.spliterator();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractObjectList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectList, java.util.List
        public ObjectList<K> subList(int i, int i2) {
            ensureIndex(i);
            ensureIndex(i2);
            if (i > i2) {
                throw new IllegalArgumentException("Start index (" + i + ") is greater than end index (" + i2 + Constant.AFTER_QUTO);
            }
            return new ObjectSubList(this, i, i2);
        }
    }

    public static class ObjectRandomAccessSubList<K> extends ObjectSubList<K> implements RandomAccess {
        private static final long serialVersionUID = -107070782945191929L;

        public ObjectRandomAccessSubList(ObjectList<K> objectList, int i, int i2) {
            super(objectList, i, i2);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractObjectList.ObjectSubList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractObjectList, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectList, java.util.List
        public ObjectList<K> subList(int i, int i2) {
            ensureIndex(i);
            ensureIndex(i2);
            if (i > i2) {
                throw new IllegalArgumentException("Start index (" + i + ") is greater than end index (" + i2 + Constant.AFTER_QUTO);
            }
            return new ObjectRandomAccessSubList(this, i, i2);
        }
    }
}
