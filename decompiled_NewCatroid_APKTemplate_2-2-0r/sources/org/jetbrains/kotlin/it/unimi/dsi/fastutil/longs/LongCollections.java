package org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs;

import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectArrays;

/* JADX INFO: loaded from: classes5.dex */
public final class LongCollections {

    public static abstract class EmptyCollection extends AbstractLongCollection {
        @Override // java.util.AbstractCollection, java.util.Collection
        public void clear() {
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection
        public boolean contains(long j) {
            return false;
        }

        @Override // java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongIterable
        @Deprecated
        public void forEach(Consumer<? super Long> consumer) {
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongIterable
        public void forEach(java.util.function.LongConsumer longConsumer) {
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

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongIterable
        public LongBidirectionalIterator iterator() {
            return LongIterators.EMPTY_ITERATOR;
        }

        @Override // java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongList, java.util.List
        public LongSpliterator spliterator() {
            return LongSpliterators.EMPTY_SPLITERATOR;
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

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongCollection, java.util.AbstractCollection, java.util.Collection
        public boolean containsAll(Collection<?> collection) {
            return collection.isEmpty();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongCollection, java.util.AbstractCollection, java.util.Collection
        public boolean addAll(Collection<? extends Long> collection) {
            throw new UnsupportedOperationException();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongCollection, java.util.AbstractCollection, java.util.Collection
        public boolean removeAll(Collection<?> collection) {
            throw new UnsupportedOperationException();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongCollection, java.util.AbstractCollection, java.util.Collection
        public boolean retainAll(Collection<?> collection) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Collection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection
        @Deprecated
        public boolean removeIf(Predicate<? super Long> predicate) {
            Objects.requireNonNull(predicate);
            return false;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection
        public long[] toLongArray() {
            return LongArrays.EMPTY_ARRAY;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongCollection
        public boolean containsAll(LongCollection longCollection) {
            return longCollection.isEmpty();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongCollection
        public boolean addAll(LongCollection longCollection) {
            throw new UnsupportedOperationException();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongCollection
        public boolean removeAll(LongCollection longCollection) {
            throw new UnsupportedOperationException();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongCollection
        public boolean retainAll(LongCollection longCollection) {
            throw new UnsupportedOperationException();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection
        public boolean removeIf(java.util.function.LongPredicate longPredicate) {
            Objects.requireNonNull(longPredicate);
            return false;
        }
    }

    static class UnmodifiableCollection implements LongCollection, Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final LongCollection collection;

        protected UnmodifiableCollection(LongCollection longCollection) {
            this.collection = (LongCollection) Objects.requireNonNull(longCollection);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection
        public boolean add(long j) {
            throw new UnsupportedOperationException();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection
        public boolean rem(long j) {
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

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection
        public boolean contains(long j) {
            return this.collection.contains(j);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongIterable
        public LongIterator iterator() {
            return LongIterators.unmodifiable(this.collection.iterator());
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongList, java.util.List
        public LongSpliterator spliterator() {
            return this.collection.spliterator();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection, java.util.Collection
        @Deprecated
        public Stream<Long> stream() {
            return this.collection.stream();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection, java.util.Collection
        @Deprecated
        public Stream<Long> parallelStream() {
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

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongIterable
        public void forEach(java.util.function.LongConsumer longConsumer) {
            this.collection.forEach(longConsumer);
        }

        @Override // java.util.Collection
        public boolean containsAll(Collection<?> collection) {
            return this.collection.containsAll(collection);
        }

        @Override // java.util.Collection
        public boolean addAll(Collection<? extends Long> collection) {
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

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection
        public boolean removeIf(java.util.function.LongPredicate longPredicate) {
            throw new UnsupportedOperationException();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection, java.util.Collection
        @Deprecated
        public boolean add(Long l) {
            throw new UnsupportedOperationException();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection, java.util.Collection
        @Deprecated
        public boolean contains(Object obj) {
            return this.collection.contains(obj);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection, java.util.Collection
        @Deprecated
        public boolean remove(Object obj) {
            throw new UnsupportedOperationException();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection
        public long[] toLongArray() {
            return this.collection.toLongArray();
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
