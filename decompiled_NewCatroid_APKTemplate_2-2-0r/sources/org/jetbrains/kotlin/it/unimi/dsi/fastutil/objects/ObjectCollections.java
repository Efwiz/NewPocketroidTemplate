package org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

/* JADX INFO: loaded from: classes5.dex */
public final class ObjectCollections {

    public static abstract class EmptyCollection<K> extends AbstractObjectCollection<K> {
        @Override // java.util.AbstractCollection, java.util.Collection
        public void clear() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean contains(Object obj) {
            return false;
        }

        @Override // java.lang.Iterable
        public void forEach(Consumer<? super K> consumer) {
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

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.AbstractObjectCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterable
        public ObjectBidirectionalIterator<K> iterator() {
            return ObjectIterators.EMPTY_ITERATOR;
        }

        @Override // java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterable
        public ObjectSpliterator<K> spliterator() {
            return ObjectSpliterators.EMPTY_SPLITERATOR;
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

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean containsAll(Collection<?> collection) {
            return collection.isEmpty();
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean addAll(Collection<? extends K> collection) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean removeAll(Collection<?> collection) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean retainAll(Collection<?> collection) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Collection
        public boolean removeIf(Predicate<? super K> predicate) {
            Objects.requireNonNull(predicate);
            return false;
        }
    }

    static class SynchronizedCollection<K> implements ObjectCollection<K>, Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final ObjectCollection<K> collection;
        protected final Object sync;

        protected SynchronizedCollection(ObjectCollection<K> objectCollection, Object obj) {
            this.collection = (ObjectCollection) Objects.requireNonNull(objectCollection);
            this.sync = obj;
        }

        @Override // java.util.Collection
        public boolean add(K k) {
            boolean zAdd;
            synchronized (this.sync) {
                zAdd = this.collection.add(k);
            }
            return zAdd;
        }

        @Override // java.util.Collection
        public boolean contains(Object obj) {
            boolean zContains;
            synchronized (this.sync) {
                zContains = this.collection.contains(obj);
            }
            return zContains;
        }

        @Override // java.util.Collection
        public boolean remove(Object obj) {
            boolean zRemove;
            synchronized (this.sync) {
                zRemove = this.collection.remove(obj);
            }
            return zRemove;
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

        @Override // java.util.Collection
        public Object[] toArray() {
            Object[] array;
            synchronized (this.sync) {
                array = this.collection.toArray();
            }
            return array;
        }

        @Override // java.util.Collection
        public <T> T[] toArray(T[] tArr) {
            T[] tArr2;
            synchronized (this.sync) {
                tArr2 = (T[]) this.collection.toArray(tArr);
            }
            return tArr2;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterable
        public ObjectIterator<K> iterator() {
            return this.collection.iterator();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterable
        public ObjectSpliterator<K> spliterator() {
            return this.collection.spliterator();
        }

        @Override // java.util.Collection
        public Stream<K> stream() {
            return this.collection.stream();
        }

        @Override // java.util.Collection
        public Stream<K> parallelStream() {
            return this.collection.parallelStream();
        }

        @Override // java.lang.Iterable
        public void forEach(Consumer<? super K> consumer) {
            synchronized (this.sync) {
                this.collection.forEach(consumer);
            }
        }

        @Override // java.util.Collection
        public boolean addAll(Collection<? extends K> collection) {
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

        @Override // java.util.Collection
        public boolean removeIf(Predicate<? super K> predicate) {
            boolean zRemoveIf;
            synchronized (this.sync) {
                zRemoveIf = this.collection.removeIf(predicate);
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

    public static <K> ObjectCollection<K> synchronize(ObjectCollection<K> objectCollection, Object obj) {
        return new SynchronizedCollection(objectCollection, obj);
    }

    static class UnmodifiableCollection<K> implements ObjectCollection<K>, Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final ObjectCollection<? extends K> collection;

        protected UnmodifiableCollection(ObjectCollection<? extends K> objectCollection) {
            this.collection = (ObjectCollection) Objects.requireNonNull(objectCollection);
        }

        @Override // java.util.Collection
        public boolean add(K k) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Collection
        public boolean remove(Object obj) {
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

        @Override // java.util.Collection
        public boolean contains(Object obj) {
            return this.collection.contains(obj);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterable
        public ObjectIterator<K> iterator() {
            return ObjectIterators.unmodifiable(this.collection.iterator());
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterable
        public ObjectSpliterator<K> spliterator() {
            return this.collection.spliterator();
        }

        @Override // java.util.Collection
        public Stream<K> stream() {
            return this.collection.stream();
        }

        @Override // java.util.Collection
        public Stream<K> parallelStream() {
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

        @Override // java.lang.Iterable
        public void forEach(Consumer<? super K> consumer) {
            this.collection.forEach(consumer);
        }

        @Override // java.util.Collection
        public boolean containsAll(Collection<?> collection) {
            return this.collection.containsAll(collection);
        }

        @Override // java.util.Collection
        public boolean addAll(Collection<? extends K> collection) {
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

        @Override // java.util.Collection
        public boolean removeIf(Predicate<? super K> predicate) {
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
