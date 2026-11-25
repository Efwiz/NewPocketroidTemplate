package org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Stream;

/* JADX INFO: loaded from: classes5.dex */
public final class DoubleSets {
    public static final EmptySet EMPTY_SET = new EmptySet();
    static final DoubleSet UNMODIFIABLE_EMPTY_SET = unmodifiable(new DoubleArraySet(DoubleArrays.EMPTY_ARRAY));

    public static class EmptySet extends DoubleCollections.EmptyCollection implements DoubleSet, Serializable, Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptySet() {
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleSet
        public boolean remove(double d) {
            throw new UnsupportedOperationException();
        }

        public Object clone() {
            return DoubleSets.EMPTY_SET;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollections.EmptyCollection, java.util.Collection
        public boolean equals(Object obj) {
            return (obj instanceof Set) && ((Set) obj).isEmpty();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.AbstractDoubleCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection
        @Deprecated
        public boolean rem(double d) {
            return super.rem(d);
        }

        private Object readResolve() {
            return DoubleSets.EMPTY_SET;
        }
    }

    public static class UnmodifiableSet extends DoubleCollections.UnmodifiableCollection implements DoubleSet, Serializable {
        private static final long serialVersionUID = -7046029254386353129L;

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollections.UnmodifiableCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection
        public /* bridge */ /* synthetic */ boolean add(double d) {
            return super.add(d);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollections.UnmodifiableCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection
        @Deprecated
        public /* bridge */ /* synthetic */ boolean add(Double d) {
            return super.add(d);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollections.UnmodifiableCollection, java.util.Collection
        public /* bridge */ /* synthetic */ boolean addAll(Collection<? extends Double> collection) {
            return super.addAll(collection);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollections.UnmodifiableCollection, java.util.Collection
        public /* bridge */ /* synthetic */ void clear() {
            super.clear();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollections.UnmodifiableCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection
        public /* bridge */ /* synthetic */ boolean contains(double d) {
            return super.contains(d);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollections.UnmodifiableCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection, java.util.Collection
        @Deprecated
        public /* bridge */ /* synthetic */ boolean contains(Object obj) {
            return super.contains(obj);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollections.UnmodifiableCollection, java.util.Collection
        public /* bridge */ /* synthetic */ boolean containsAll(Collection collection) {
            return super.containsAll(collection);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollections.UnmodifiableCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleIterable
        public /* bridge */ /* synthetic */ void forEach(java.util.function.DoubleConsumer doubleConsumer) {
            super.forEach(doubleConsumer);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollections.UnmodifiableCollection, java.util.Collection
        public /* bridge */ /* synthetic */ boolean isEmpty() {
            return super.isEmpty();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollections.UnmodifiableCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleSet, java.util.Set
        public /* bridge */ /* synthetic */ DoubleIterator iterator() {
            return super.iterator();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollections.UnmodifiableCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection, java.util.Collection
        @Deprecated
        public /* bridge */ /* synthetic */ Stream<Double> parallelStream() {
            return super.parallelStream();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollections.UnmodifiableCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection, java.util.Collection
        @Deprecated
        public /* bridge */ /* synthetic */ boolean remove(Object obj) {
            return super.remove(obj);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollections.UnmodifiableCollection, java.util.Collection
        public /* bridge */ /* synthetic */ boolean removeAll(Collection collection) {
            return super.removeAll(collection);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollections.UnmodifiableCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection
        public /* bridge */ /* synthetic */ boolean removeIf(java.util.function.DoublePredicate doublePredicate) {
            return super.removeIf(doublePredicate);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollections.UnmodifiableCollection, java.util.Collection
        public /* bridge */ /* synthetic */ boolean retainAll(Collection collection) {
            return super.retainAll(collection);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollections.UnmodifiableCollection, java.util.Collection
        public /* bridge */ /* synthetic */ int size() {
            return super.size();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollections.UnmodifiableCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleSet, java.util.Set
        public /* bridge */ /* synthetic */ DoubleSpliterator spliterator() {
            return super.spliterator();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollections.UnmodifiableCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection, java.util.Collection
        @Deprecated
        public /* bridge */ /* synthetic */ Stream<Double> stream() {
            return super.stream();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollections.UnmodifiableCollection, java.util.Collection
        public /* bridge */ /* synthetic */ Object[] toArray() {
            return super.toArray();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollections.UnmodifiableCollection, java.util.Collection
        public /* bridge */ /* synthetic */ Object[] toArray(Object[] objArr) {
            return super.toArray(objArr);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollections.UnmodifiableCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection
        public /* bridge */ /* synthetic */ double[] toDoubleArray() {
            return super.toDoubleArray();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollections.UnmodifiableCollection
        public /* bridge */ /* synthetic */ String toString() {
            return super.toString();
        }

        protected UnmodifiableSet(DoubleSet doubleSet) {
            super(doubleSet);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleSet
        public boolean remove(double d) {
            throw new UnsupportedOperationException();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollections.UnmodifiableCollection, java.util.Collection
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            return this.collection.equals(obj);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollections.UnmodifiableCollection, java.util.Collection
        public int hashCode() {
            return this.collection.hashCode();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollections.UnmodifiableCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection
        @Deprecated
        public boolean rem(double d) {
            return super.rem(d);
        }
    }

    public static DoubleSet unmodifiable(DoubleSet doubleSet) {
        return new UnmodifiableSet(doubleSet);
    }
}
