package org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Stream;

/* JADX INFO: loaded from: classes5.dex */
public final class LongSets {
    public static final EmptySet EMPTY_SET = new EmptySet();
    static final LongSet UNMODIFIABLE_EMPTY_SET = unmodifiable(new LongArraySet(LongArrays.EMPTY_ARRAY));

    public static class EmptySet extends LongCollections.EmptyCollection implements LongSet, Serializable, Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptySet() {
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongSet
        public boolean remove(long j) {
            throw new UnsupportedOperationException();
        }

        public Object clone() {
            return LongSets.EMPTY_SET;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollections.EmptyCollection, java.util.Collection
        public boolean equals(Object obj) {
            return (obj instanceof Set) && ((Set) obj).isEmpty();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.AbstractLongCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection
        @Deprecated
        public boolean rem(long j) {
            return super.rem(j);
        }

        private Object readResolve() {
            return LongSets.EMPTY_SET;
        }
    }

    public static class UnmodifiableSet extends LongCollections.UnmodifiableCollection implements LongSet, Serializable {
        private static final long serialVersionUID = -7046029254386353129L;

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollections.UnmodifiableCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection
        public /* bridge */ /* synthetic */ boolean add(long j) {
            return super.add(j);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollections.UnmodifiableCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection
        @Deprecated
        public /* bridge */ /* synthetic */ boolean add(Long l) {
            return super.add(l);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollections.UnmodifiableCollection, java.util.Collection
        public /* bridge */ /* synthetic */ boolean addAll(Collection<? extends Long> collection) {
            return super.addAll(collection);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollections.UnmodifiableCollection, java.util.Collection
        public /* bridge */ /* synthetic */ void clear() {
            super.clear();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollections.UnmodifiableCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection
        public /* bridge */ /* synthetic */ boolean contains(long j) {
            return super.contains(j);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollections.UnmodifiableCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection, java.util.Collection
        @Deprecated
        public /* bridge */ /* synthetic */ boolean contains(Object obj) {
            return super.contains(obj);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollections.UnmodifiableCollection, java.util.Collection
        public /* bridge */ /* synthetic */ boolean containsAll(Collection collection) {
            return super.containsAll(collection);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollections.UnmodifiableCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongIterable
        public /* bridge */ /* synthetic */ void forEach(java.util.function.LongConsumer longConsumer) {
            super.forEach(longConsumer);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollections.UnmodifiableCollection, java.util.Collection
        public /* bridge */ /* synthetic */ boolean isEmpty() {
            return super.isEmpty();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollections.UnmodifiableCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongIterable
        public /* bridge */ /* synthetic */ LongIterator iterator() {
            return super.iterator();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollections.UnmodifiableCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection, java.util.Collection
        @Deprecated
        public /* bridge */ /* synthetic */ Stream<Long> parallelStream() {
            return super.parallelStream();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollections.UnmodifiableCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection, java.util.Collection
        @Deprecated
        public /* bridge */ /* synthetic */ boolean remove(Object obj) {
            return super.remove(obj);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollections.UnmodifiableCollection, java.util.Collection
        public /* bridge */ /* synthetic */ boolean removeAll(Collection collection) {
            return super.removeAll(collection);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollections.UnmodifiableCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection
        public /* bridge */ /* synthetic */ boolean removeIf(java.util.function.LongPredicate longPredicate) {
            return super.removeIf(longPredicate);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollections.UnmodifiableCollection, java.util.Collection
        public /* bridge */ /* synthetic */ boolean retainAll(Collection collection) {
            return super.retainAll(collection);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollections.UnmodifiableCollection, java.util.Collection
        public /* bridge */ /* synthetic */ int size() {
            return super.size();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollections.UnmodifiableCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongList, java.util.List
        public /* bridge */ /* synthetic */ LongSpliterator spliterator() {
            return super.spliterator();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollections.UnmodifiableCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection, java.util.Collection
        @Deprecated
        public /* bridge */ /* synthetic */ Stream<Long> stream() {
            return super.stream();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollections.UnmodifiableCollection, java.util.Collection
        public /* bridge */ /* synthetic */ Object[] toArray() {
            return super.toArray();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollections.UnmodifiableCollection, java.util.Collection
        public /* bridge */ /* synthetic */ Object[] toArray(Object[] objArr) {
            return super.toArray(objArr);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollections.UnmodifiableCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection
        public /* bridge */ /* synthetic */ long[] toLongArray() {
            return super.toLongArray();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollections.UnmodifiableCollection
        public /* bridge */ /* synthetic */ String toString() {
            return super.toString();
        }

        protected UnmodifiableSet(LongSet longSet) {
            super(longSet);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongSet
        public boolean remove(long j) {
            throw new UnsupportedOperationException();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollections.UnmodifiableCollection, java.util.Collection
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            return this.collection.equals(obj);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollections.UnmodifiableCollection, java.util.Collection
        public int hashCode() {
            return this.collection.hashCode();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollections.UnmodifiableCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection
        @Deprecated
        public boolean rem(long j) {
            return super.rem(j);
        }
    }

    public static LongSet unmodifiable(LongSet longSet) {
        return new UnmodifiableSet(longSet);
    }
}
