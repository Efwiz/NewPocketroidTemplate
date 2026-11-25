package org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Stream;

/* JADX INFO: loaded from: classes5.dex */
public final class IntSets {
    public static final EmptySet EMPTY_SET = new EmptySet();
    static final IntSet UNMODIFIABLE_EMPTY_SET = unmodifiable(new IntArraySet(IntArrays.EMPTY_ARRAY));

    public static class EmptySet extends IntCollections.EmptyCollection implements IntSet, Serializable, Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptySet() {
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSet
        public boolean remove(int i) {
            throw new UnsupportedOperationException();
        }

        public Object clone() {
            return IntSets.EMPTY_SET;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollections.EmptyCollection, java.util.Collection
        public boolean equals(Object obj) {
            return (obj instanceof Set) && ((Set) obj).isEmpty();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.AbstractIntCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection
        @Deprecated
        public boolean rem(int i) {
            return super.rem(i);
        }

        private Object readResolve() {
            return IntSets.EMPTY_SET;
        }
    }

    public static class SynchronizedSet extends IntCollections.SynchronizedCollection implements IntSet, Serializable {
        private static final long serialVersionUID = -7046029254386353129L;

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollections.SynchronizedCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection
        public /* bridge */ /* synthetic */ boolean add(int i) {
            return super.add(i);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollections.SynchronizedCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection
        @Deprecated
        public /* bridge */ /* synthetic */ boolean add(Integer num) {
            return super.add(num);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollections.SynchronizedCollection, java.util.Collection
        public /* bridge */ /* synthetic */ boolean addAll(Collection<? extends Integer> collection) {
            return super.addAll(collection);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollections.SynchronizedCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection
        public /* bridge */ /* synthetic */ boolean addAll(IntCollection intCollection) {
            return super.addAll(intCollection);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollections.SynchronizedCollection, java.util.Collection
        public /* bridge */ /* synthetic */ void clear() {
            super.clear();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollections.SynchronizedCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection
        public /* bridge */ /* synthetic */ boolean contains(int i) {
            return super.contains(i);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollections.SynchronizedCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection, java.util.Collection
        @Deprecated
        public /* bridge */ /* synthetic */ boolean contains(Object obj) {
            return super.contains(obj);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollections.SynchronizedCollection, java.util.Collection
        public /* bridge */ /* synthetic */ boolean containsAll(Collection collection) {
            return super.containsAll(collection);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollections.SynchronizedCollection, java.util.Collection
        public /* bridge */ /* synthetic */ boolean equals(Object obj) {
            return super.equals(obj);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollections.SynchronizedCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterable
        public /* bridge */ /* synthetic */ void forEach(java.util.function.IntConsumer intConsumer) {
            super.forEach(intConsumer);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollections.SynchronizedCollection, java.util.Collection
        public /* bridge */ /* synthetic */ int hashCode() {
            return super.hashCode();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollections.SynchronizedCollection, java.util.Collection
        public /* bridge */ /* synthetic */ boolean isEmpty() {
            return super.isEmpty();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollections.SynchronizedCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSet, java.util.Set
        public /* bridge */ /* synthetic */ IntIterator iterator() {
            return super.iterator();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollections.SynchronizedCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection, java.util.Collection
        @Deprecated
        public /* bridge */ /* synthetic */ Stream<Integer> parallelStream() {
            return super.parallelStream();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollections.SynchronizedCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection, java.util.Collection
        @Deprecated
        public /* bridge */ /* synthetic */ boolean remove(Object obj) {
            return super.remove(obj);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollections.SynchronizedCollection, java.util.Collection
        public /* bridge */ /* synthetic */ boolean removeAll(Collection collection) {
            return super.removeAll(collection);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollections.SynchronizedCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection
        public /* bridge */ /* synthetic */ boolean removeIf(java.util.function.IntPredicate intPredicate) {
            return super.removeIf(intPredicate);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollections.SynchronizedCollection, java.util.Collection
        public /* bridge */ /* synthetic */ boolean retainAll(Collection collection) {
            return super.retainAll(collection);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollections.SynchronizedCollection, java.util.Collection
        public /* bridge */ /* synthetic */ int size() {
            return super.size();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollections.SynchronizedCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSet, java.util.Set
        public /* bridge */ /* synthetic */ IntSpliterator spliterator() {
            return super.spliterator();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollections.SynchronizedCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection, java.util.Collection
        @Deprecated
        public /* bridge */ /* synthetic */ Stream<Integer> stream() {
            return super.stream();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollections.SynchronizedCollection, java.util.Collection
        public /* bridge */ /* synthetic */ Object[] toArray() {
            return super.toArray();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollections.SynchronizedCollection, java.util.Collection
        public /* bridge */ /* synthetic */ Object[] toArray(Object[] objArr) {
            return super.toArray(objArr);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollections.SynchronizedCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection
        public /* bridge */ /* synthetic */ int[] toIntArray() {
            return super.toIntArray();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollections.SynchronizedCollection
        public /* bridge */ /* synthetic */ String toString() {
            return super.toString();
        }

        protected SynchronizedSet(IntSet intSet, Object obj) {
            super(intSet, obj);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSet
        public boolean remove(int i) {
            boolean zRem;
            synchronized (this.sync) {
                zRem = this.collection.rem(i);
            }
            return zRem;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollections.SynchronizedCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection
        @Deprecated
        public boolean rem(int i) {
            return super.rem(i);
        }
    }

    public static IntSet synchronize(IntSet intSet, Object obj) {
        return new SynchronizedSet(intSet, obj);
    }

    public static class UnmodifiableSet extends IntCollections.UnmodifiableCollection implements IntSet, Serializable {
        private static final long serialVersionUID = -7046029254386353129L;

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollections.UnmodifiableCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection
        public /* bridge */ /* synthetic */ boolean add(int i) {
            return super.add(i);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollections.UnmodifiableCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection
        @Deprecated
        public /* bridge */ /* synthetic */ boolean add(Integer num) {
            return super.add(num);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollections.UnmodifiableCollection, java.util.Collection
        public /* bridge */ /* synthetic */ boolean addAll(Collection<? extends Integer> collection) {
            return super.addAll(collection);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollections.UnmodifiableCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection
        public /* bridge */ /* synthetic */ boolean addAll(IntCollection intCollection) {
            return super.addAll(intCollection);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollections.UnmodifiableCollection, java.util.Collection
        public /* bridge */ /* synthetic */ void clear() {
            super.clear();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollections.UnmodifiableCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection
        public /* bridge */ /* synthetic */ boolean contains(int i) {
            return super.contains(i);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollections.UnmodifiableCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection, java.util.Collection
        @Deprecated
        public /* bridge */ /* synthetic */ boolean contains(Object obj) {
            return super.contains(obj);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollections.UnmodifiableCollection, java.util.Collection
        public /* bridge */ /* synthetic */ boolean containsAll(Collection collection) {
            return super.containsAll(collection);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollections.UnmodifiableCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterable
        public /* bridge */ /* synthetic */ void forEach(java.util.function.IntConsumer intConsumer) {
            super.forEach(intConsumer);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollections.UnmodifiableCollection, java.util.Collection
        public /* bridge */ /* synthetic */ boolean isEmpty() {
            return super.isEmpty();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollections.UnmodifiableCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSet, java.util.Set
        public /* bridge */ /* synthetic */ IntIterator iterator() {
            return super.iterator();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollections.UnmodifiableCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection, java.util.Collection
        @Deprecated
        public /* bridge */ /* synthetic */ Stream<Integer> parallelStream() {
            return super.parallelStream();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollections.UnmodifiableCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection, java.util.Collection
        @Deprecated
        public /* bridge */ /* synthetic */ boolean remove(Object obj) {
            return super.remove(obj);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollections.UnmodifiableCollection, java.util.Collection
        public /* bridge */ /* synthetic */ boolean removeAll(Collection collection) {
            return super.removeAll(collection);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollections.UnmodifiableCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection
        public /* bridge */ /* synthetic */ boolean removeIf(java.util.function.IntPredicate intPredicate) {
            return super.removeIf(intPredicate);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollections.UnmodifiableCollection, java.util.Collection
        public /* bridge */ /* synthetic */ boolean retainAll(Collection collection) {
            return super.retainAll(collection);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollections.UnmodifiableCollection, java.util.Collection
        public /* bridge */ /* synthetic */ int size() {
            return super.size();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollections.UnmodifiableCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSet, java.util.Set
        public /* bridge */ /* synthetic */ IntSpliterator spliterator() {
            return super.spliterator();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollections.UnmodifiableCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection, java.util.Collection
        @Deprecated
        public /* bridge */ /* synthetic */ Stream<Integer> stream() {
            return super.stream();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollections.UnmodifiableCollection, java.util.Collection
        public /* bridge */ /* synthetic */ Object[] toArray() {
            return super.toArray();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollections.UnmodifiableCollection, java.util.Collection
        public /* bridge */ /* synthetic */ Object[] toArray(Object[] objArr) {
            return super.toArray(objArr);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollections.UnmodifiableCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection
        public /* bridge */ /* synthetic */ int[] toIntArray() {
            return super.toIntArray();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollections.UnmodifiableCollection
        public /* bridge */ /* synthetic */ String toString() {
            return super.toString();
        }

        protected UnmodifiableSet(IntSet intSet) {
            super(intSet);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSet
        public boolean remove(int i) {
            throw new UnsupportedOperationException();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollections.UnmodifiableCollection, java.util.Collection
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            return this.collection.equals(obj);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollections.UnmodifiableCollection, java.util.Collection
        public int hashCode() {
            return this.collection.hashCode();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollections.UnmodifiableCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection
        @Deprecated
        public boolean rem(int i) {
            return super.rem(i);
        }
    }

    public static IntSet unmodifiable(IntSet intSet) {
        return new UnmodifiableSet(intSet);
    }
}
