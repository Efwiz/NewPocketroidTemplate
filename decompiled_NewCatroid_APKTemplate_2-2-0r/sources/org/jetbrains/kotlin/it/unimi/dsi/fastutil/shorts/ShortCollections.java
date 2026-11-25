package org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts;

import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectArrays;

/* JADX INFO: loaded from: classes5.dex */
public final class ShortCollections {

    public static abstract class EmptyCollection extends AbstractShortCollection {
        @Override // java.util.AbstractCollection, java.util.Collection
        public void clear() {
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.AbstractShortCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollection
        public boolean contains(short s) {
            return false;
        }

        @Override // java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortIterable
        @Deprecated
        public void forEach(Consumer<? super Short> consumer) {
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortIterable
        public void forEach(ShortConsumer shortConsumer) {
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

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.AbstractShortCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortIterable
        public ShortBidirectionalIterator iterator() {
            return ShortIterators.EMPTY_ITERATOR;
        }

        @Override // java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortIterable
        public ShortSpliterator spliterator() {
            return ShortSpliterators.EMPTY_SPLITERATOR;
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

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.AbstractShortCollection, java.util.AbstractCollection, java.util.Collection
        public boolean containsAll(Collection<?> collection) {
            return collection.isEmpty();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.AbstractShortCollection, java.util.AbstractCollection, java.util.Collection
        public boolean addAll(Collection<? extends Short> collection) {
            throw new UnsupportedOperationException();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.AbstractShortCollection, java.util.AbstractCollection, java.util.Collection
        public boolean removeAll(Collection<?> collection) {
            throw new UnsupportedOperationException();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.AbstractShortCollection, java.util.AbstractCollection, java.util.Collection
        public boolean retainAll(Collection<?> collection) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Collection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollection
        @Deprecated
        public boolean removeIf(Predicate<? super Short> predicate) {
            Objects.requireNonNull(predicate);
            return false;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.AbstractShortCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollection
        public short[] toShortArray() {
            return ShortArrays.EMPTY_ARRAY;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.AbstractShortCollection
        public boolean containsAll(ShortCollection shortCollection) {
            return shortCollection.isEmpty();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.AbstractShortCollection
        public boolean addAll(ShortCollection shortCollection) {
            throw new UnsupportedOperationException();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.AbstractShortCollection
        public boolean removeAll(ShortCollection shortCollection) {
            throw new UnsupportedOperationException();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.AbstractShortCollection
        public boolean retainAll(ShortCollection shortCollection) {
            throw new UnsupportedOperationException();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollection
        public boolean removeIf(ShortPredicate shortPredicate) {
            Objects.requireNonNull(shortPredicate);
            return false;
        }
    }

    static class UnmodifiableCollection implements ShortCollection, Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final ShortCollection collection;

        protected UnmodifiableCollection(ShortCollection shortCollection) {
            this.collection = (ShortCollection) Objects.requireNonNull(shortCollection);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollection
        public boolean add(short s) {
            throw new UnsupportedOperationException();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollection
        public boolean rem(short s) {
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

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollection
        public boolean contains(short s) {
            return this.collection.contains(s);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortIterable
        public ShortIterator iterator() {
            return ShortIterators.unmodifiable(this.collection.iterator());
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortIterable
        public ShortSpliterator spliterator() {
            return this.collection.spliterator();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollection, java.util.Collection
        @Deprecated
        public Stream<Short> stream() {
            return this.collection.stream();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollection, java.util.Collection
        @Deprecated
        public Stream<Short> parallelStream() {
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

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortIterable
        public void forEach(ShortConsumer shortConsumer) {
            this.collection.forEach(shortConsumer);
        }

        @Override // java.util.Collection
        public boolean containsAll(Collection<?> collection) {
            return this.collection.containsAll(collection);
        }

        @Override // java.util.Collection
        public boolean addAll(Collection<? extends Short> collection) {
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

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollection
        public boolean removeIf(ShortPredicate shortPredicate) {
            throw new UnsupportedOperationException();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollection, java.util.Collection
        @Deprecated
        public boolean add(Short sh) {
            throw new UnsupportedOperationException();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollection, java.util.Collection
        @Deprecated
        public boolean contains(Object obj) {
            return this.collection.contains(obj);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollection, java.util.Collection
        @Deprecated
        public boolean remove(Object obj) {
            throw new UnsupportedOperationException();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollection
        public short[] toShortArray() {
            return this.collection.toShortArray();
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
