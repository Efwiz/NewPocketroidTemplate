package org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles;

import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectArrays;

/* JADX INFO: loaded from: classes5.dex */
public final class DoubleCollections {

    public static abstract class EmptyCollection extends AbstractDoubleCollection {
        @Override // java.util.AbstractCollection, java.util.Collection
        public void clear() {
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection
        public boolean contains(double d) {
            return false;
        }

        @Override // java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleIterable
        @Deprecated
        public void forEach(Consumer<? super Double> consumer) {
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleIterable
        public void forEach(java.util.function.DoubleConsumer doubleConsumer) {
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

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleSet, java.util.Set
        public DoubleBidirectionalIterator iterator() {
            return DoubleIterators.EMPTY_ITERATOR;
        }

        @Override // java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleSet, java.util.Set
        public DoubleSpliterator spliterator() {
            return DoubleSpliterators.EMPTY_SPLITERATOR;
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

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection, java.util.AbstractCollection, java.util.Collection
        public boolean containsAll(Collection<?> collection) {
            return collection.isEmpty();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection, java.util.AbstractCollection, java.util.Collection
        public boolean addAll(Collection<? extends Double> collection) {
            throw new UnsupportedOperationException();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection, java.util.AbstractCollection, java.util.Collection
        public boolean removeAll(Collection<?> collection) {
            throw new UnsupportedOperationException();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection, java.util.AbstractCollection, java.util.Collection
        public boolean retainAll(Collection<?> collection) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Collection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection
        @Deprecated
        public boolean removeIf(Predicate<? super Double> predicate) {
            Objects.requireNonNull(predicate);
            return false;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection
        public double[] toDoubleArray() {
            return DoubleArrays.EMPTY_ARRAY;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection
        public boolean containsAll(DoubleCollection doubleCollection) {
            return doubleCollection.isEmpty();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection
        public boolean addAll(DoubleCollection doubleCollection) {
            throw new UnsupportedOperationException();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection
        public boolean removeAll(DoubleCollection doubleCollection) {
            throw new UnsupportedOperationException();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection
        public boolean retainAll(DoubleCollection doubleCollection) {
            throw new UnsupportedOperationException();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection
        public boolean removeIf(java.util.function.DoublePredicate doublePredicate) {
            Objects.requireNonNull(doublePredicate);
            return false;
        }
    }

    static class UnmodifiableCollection implements DoubleCollection, Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final DoubleCollection collection;

        protected UnmodifiableCollection(DoubleCollection doubleCollection) {
            this.collection = (DoubleCollection) Objects.requireNonNull(doubleCollection);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection
        public boolean add(double d) {
            throw new UnsupportedOperationException();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection
        public boolean rem(double d) {
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

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection
        public boolean contains(double d) {
            return this.collection.contains(d);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleSet, java.util.Set
        public DoubleIterator iterator() {
            return DoubleIterators.unmodifiable(this.collection.iterator());
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleSet, java.util.Set
        public DoubleSpliterator spliterator() {
            return this.collection.spliterator();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection, java.util.Collection
        @Deprecated
        public Stream<Double> stream() {
            return this.collection.stream();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection, java.util.Collection
        @Deprecated
        public Stream<Double> parallelStream() {
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

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleIterable
        public void forEach(java.util.function.DoubleConsumer doubleConsumer) {
            this.collection.forEach(doubleConsumer);
        }

        @Override // java.util.Collection
        public boolean containsAll(Collection<?> collection) {
            return this.collection.containsAll(collection);
        }

        @Override // java.util.Collection
        public boolean addAll(Collection<? extends Double> collection) {
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

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection
        public boolean removeIf(java.util.function.DoublePredicate doublePredicate) {
            throw new UnsupportedOperationException();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection, java.util.Collection
        @Deprecated
        public boolean add(Double d) {
            throw new UnsupportedOperationException();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection, java.util.Collection
        @Deprecated
        public boolean contains(Object obj) {
            return this.collection.contains(obj);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection, java.util.Collection
        @Deprecated
        public boolean remove(Object obj) {
            throw new UnsupportedOperationException();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection
        public double[] toDoubleArray() {
            return this.collection.toDoubleArray();
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
