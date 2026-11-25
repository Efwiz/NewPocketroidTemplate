package org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectArrays;

/* JADX INFO: loaded from: classes5.dex */
public final class IntCollections {

    public static abstract class EmptyCollection extends AbstractIntCollection {
        @Override // java.util.AbstractCollection, java.util.Collection
        public void clear() {
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection
        public boolean contains(int i) {
            return false;
        }

        @Override // java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterable
        @Deprecated
        public void forEach(Consumer<? super Integer> consumer) {
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterable
        public void forEach(java.util.function.IntConsumer intConsumer) {
        }

        @Override // java.util.Collection
        public int hashCode() {
            return 0;
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public int size() {
            return 0;
        }

        protected EmptyCollection() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public Object[] toArray() {
            return ObjectArrays.EMPTY_ARRAY;
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public <T> T[] toArray(T[] tArr) {
            if (tArr.length > 0) {
                tArr[0] = null;
            }
            return tArr;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSet, java.util.Set
        public IntBidirectionalIterator iterator() {
            return IntIterators.EMPTY_ITERATOR;
        }

        @Override // java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSet, java.util.Set
        public IntSpliterator spliterator() {
            return IntSpliterators.EMPTY_SPLITERATOR;
        }

        @Override // java.util.Collection
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj instanceof Collection) {
                return ((Collection) obj).isEmpty();
            }
            return false;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntCollection, java.util.AbstractCollection, java.util.Collection
        public boolean containsAll(Collection<?> collection) {
            return collection.isEmpty();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntCollection, java.util.AbstractCollection, java.util.Collection
        public boolean addAll(Collection<? extends Integer> collection) {
            throw new UnsupportedOperationException();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntCollection, java.util.AbstractCollection, java.util.Collection
        public boolean removeAll(Collection<?> collection) {
            throw new UnsupportedOperationException();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntCollection, java.util.AbstractCollection, java.util.Collection
        public boolean retainAll(Collection<?> collection) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Collection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection
        @Deprecated
        public boolean removeIf(Predicate<? super Integer> predicate) {
            Objects.requireNonNull(predicate);
            return false;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection
        public int[] toIntArray() {
            return IntArrays.EMPTY_ARRAY;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntCollection
        public boolean containsAll(IntCollection intCollection) {
            return intCollection.isEmpty();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection
        public boolean addAll(IntCollection intCollection) {
            throw new UnsupportedOperationException();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntCollection
        public boolean removeAll(IntCollection intCollection) {
            throw new UnsupportedOperationException();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntCollection
        public boolean retainAll(IntCollection intCollection) {
            throw new UnsupportedOperationException();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection
        public boolean removeIf(java.util.function.IntPredicate intPredicate) {
            Objects.requireNonNull(intPredicate);
            return false;
        }
    }

    static class SynchronizedCollection implements IntCollection, Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final IntCollection collection;
        protected final Object sync;

        protected SynchronizedCollection(IntCollection intCollection, Object obj) {
            this.collection = (IntCollection) Objects.requireNonNull(intCollection);
            this.sync = obj;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection
        public boolean add(int i) {
            boolean zAdd;
            synchronized (this.sync) {
                zAdd = this.collection.add(i);
            }
            return zAdd;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection
        public boolean contains(int i) {
            boolean zContains;
            synchronized (this.sync) {
                zContains = this.collection.contains(i);
            }
            return zContains;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection
        public boolean rem(int i) {
            boolean zRem;
            synchronized (this.sync) {
                zRem = this.collection.rem(i);
            }
            return zRem;
        }

        @Override // java.util.Collection
        public int size() {
            int size;
            synchronized (this.sync) {
                size = this.collection.size();
            }
            return size;
        }

        @Override // java.util.Collection
        public boolean isEmpty() {
            boolean zIsEmpty;
            synchronized (this.sync) {
                zIsEmpty = this.collection.isEmpty();
            }
            return zIsEmpty;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection
        public int[] toIntArray() {
            int[] intArray;
            synchronized (this.sync) {
                intArray = this.collection.toIntArray();
            }
            return intArray;
        }

        @Override // java.util.Collection
        public Object[] toArray() {
            Object[] array;
            synchronized (this.sync) {
                array = this.collection.toArray();
            }
            return array;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection
        public boolean addAll(IntCollection intCollection) {
            boolean zAddAll;
            synchronized (this.sync) {
                zAddAll = this.collection.addAll(intCollection);
            }
            return zAddAll;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection, java.util.Collection
        @Deprecated
        public boolean add(Integer num) {
            boolean zAdd;
            synchronized (this.sync) {
                zAdd = this.collection.add(num);
            }
            return zAdd;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection, java.util.Collection
        @Deprecated
        public boolean contains(Object obj) {
            boolean zContains;
            synchronized (this.sync) {
                zContains = this.collection.contains(obj);
            }
            return zContains;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection, java.util.Collection
        @Deprecated
        public boolean remove(Object obj) {
            boolean zRemove;
            synchronized (this.sync) {
                zRemove = this.collection.remove(obj);
            }
            return zRemove;
        }

        @Override // java.util.Collection
        public <T> T[] toArray(T[] tArr) {
            T[] tArr2;
            synchronized (this.sync) {
                tArr2 = (T[]) this.collection.toArray(tArr);
            }
            return tArr2;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSet, java.util.Set
        public IntIterator iterator() {
            return this.collection.iterator();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSet, java.util.Set
        public IntSpliterator spliterator() {
            return this.collection.spliterator();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection, java.util.Collection
        @Deprecated
        public Stream<Integer> stream() {
            return this.collection.stream();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection, java.util.Collection
        @Deprecated
        public Stream<Integer> parallelStream() {
            return this.collection.parallelStream();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterable
        public void forEach(java.util.function.IntConsumer intConsumer) {
            synchronized (this.sync) {
                this.collection.forEach(intConsumer);
            }
        }

        @Override // java.util.Collection
        public boolean addAll(Collection<? extends Integer> collection) {
            boolean zAddAll;
            synchronized (this.sync) {
                zAddAll = this.collection.addAll(collection);
            }
            return zAddAll;
        }

        @Override // java.util.Collection
        public boolean containsAll(Collection<?> collection) {
            boolean zContainsAll;
            synchronized (this.sync) {
                zContainsAll = this.collection.containsAll(collection);
            }
            return zContainsAll;
        }

        @Override // java.util.Collection
        public boolean removeAll(Collection<?> collection) {
            boolean zRemoveAll;
            synchronized (this.sync) {
                zRemoveAll = this.collection.removeAll(collection);
            }
            return zRemoveAll;
        }

        @Override // java.util.Collection
        public boolean retainAll(Collection<?> collection) {
            boolean zRetainAll;
            synchronized (this.sync) {
                zRetainAll = this.collection.retainAll(collection);
            }
            return zRetainAll;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection
        public boolean removeIf(java.util.function.IntPredicate intPredicate) {
            boolean zRemoveIf;
            synchronized (this.sync) {
                zRemoveIf = this.collection.removeIf(intPredicate);
            }
            return zRemoveIf;
        }

        @Override // java.util.Collection
        public void clear() {
            synchronized (this.sync) {
                this.collection.clear();
            }
        }

        public String toString() {
            String string;
            synchronized (this.sync) {
                string = this.collection.toString();
            }
            return string;
        }

        @Override // java.util.Collection
        public int hashCode() {
            int iHashCode;
            synchronized (this.sync) {
                iHashCode = this.collection.hashCode();
            }
            return iHashCode;
        }

        @Override // java.util.Collection
        public boolean equals(Object obj) {
            boolean zEquals;
            if (obj == this) {
                return true;
            }
            synchronized (this.sync) {
                zEquals = this.collection.equals(obj);
            }
            return zEquals;
        }

        private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
            synchronized (this.sync) {
                objectOutputStream.defaultWriteObject();
            }
        }
    }

    static class UnmodifiableCollection implements IntCollection, Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final IntCollection collection;

        protected UnmodifiableCollection(IntCollection intCollection) {
            this.collection = (IntCollection) Objects.requireNonNull(intCollection);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection
        public boolean add(int i) {
            throw new UnsupportedOperationException();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection
        public boolean rem(int i) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Collection
        public int size() {
            return this.collection.size();
        }

        @Override // java.util.Collection
        public boolean isEmpty() {
            return this.collection.isEmpty();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection
        public boolean contains(int i) {
            return this.collection.contains(i);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSet, java.util.Set
        public IntIterator iterator() {
            return IntIterators.unmodifiable(this.collection.iterator());
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSet, java.util.Set
        public IntSpliterator spliterator() {
            return this.collection.spliterator();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection, java.util.Collection
        @Deprecated
        public Stream<Integer> stream() {
            return this.collection.stream();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection, java.util.Collection
        @Deprecated
        public Stream<Integer> parallelStream() {
            return this.collection.parallelStream();
        }

        @Override // java.util.Collection
        public void clear() {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Collection
        public <T> T[] toArray(T[] tArr) {
            return (T[]) this.collection.toArray(tArr);
        }

        @Override // java.util.Collection
        public Object[] toArray() {
            return this.collection.toArray();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterable
        public void forEach(java.util.function.IntConsumer intConsumer) {
            this.collection.forEach(intConsumer);
        }

        @Override // java.util.Collection
        public boolean containsAll(Collection<?> collection) {
            return this.collection.containsAll(collection);
        }

        @Override // java.util.Collection
        public boolean addAll(Collection<? extends Integer> collection) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Collection
        public boolean removeAll(Collection<?> collection) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Collection
        public boolean retainAll(Collection<?> collection) {
            throw new UnsupportedOperationException();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection
        public boolean removeIf(java.util.function.IntPredicate intPredicate) {
            throw new UnsupportedOperationException();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection, java.util.Collection
        @Deprecated
        public boolean add(Integer num) {
            throw new UnsupportedOperationException();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection, java.util.Collection
        @Deprecated
        public boolean contains(Object obj) {
            return this.collection.contains(obj);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection, java.util.Collection
        @Deprecated
        public boolean remove(Object obj) {
            throw new UnsupportedOperationException();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection
        public int[] toIntArray() {
            return this.collection.toIntArray();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection
        public boolean addAll(IntCollection intCollection) {
            throw new UnsupportedOperationException();
        }

        public String toString() {
            return this.collection.toString();
        }

        @Override // java.util.Collection
        public int hashCode() {
            return this.collection.hashCode();
        }

        @Override // java.util.Collection
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            return this.collection.equals(obj);
        }
    }
}
