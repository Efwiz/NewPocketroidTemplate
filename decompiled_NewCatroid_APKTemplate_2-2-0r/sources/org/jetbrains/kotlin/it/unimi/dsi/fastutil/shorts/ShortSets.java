package org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Stream;

/* JADX INFO: loaded from: classes5.dex */
public final class ShortSets {
    public static final EmptySet EMPTY_SET = new EmptySet();
    static final ShortSet UNMODIFIABLE_EMPTY_SET = unmodifiable(new ShortArraySet(ShortArrays.EMPTY_ARRAY));

    public static class EmptySet extends ShortCollections.EmptyCollection implements ShortSet, Serializable, Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptySet() {
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortSet
        public boolean remove(short s) {
            throw new UnsupportedOperationException();
        }

        public Object clone() {
            return ShortSets.EMPTY_SET;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollections.EmptyCollection, java.util.Collection
        public boolean equals(Object obj) {
            return (obj instanceof Set) && ((Set) obj).isEmpty();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.AbstractShortCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollection
        @Deprecated
        public boolean rem(short s) {
            return super.rem(s);
        }

        private Object readResolve() {
            return ShortSets.EMPTY_SET;
        }
    }

    public static class UnmodifiableSet extends ShortCollections.UnmodifiableCollection implements ShortSet, Serializable {
        private static final long serialVersionUID = -7046029254386353129L;

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollections.UnmodifiableCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollection
        @Deprecated
        public /* bridge */ /* synthetic */ boolean add(Short sh) {
            return super.add(sh);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollections.UnmodifiableCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollection
        public /* bridge */ /* synthetic */ boolean add(short s) {
            return super.add(s);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollections.UnmodifiableCollection, java.util.Collection
        public /* bridge */ /* synthetic */ boolean addAll(Collection<? extends Short> collection) {
            return super.addAll(collection);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollections.UnmodifiableCollection, java.util.Collection
        public /* bridge */ /* synthetic */ void clear() {
            super.clear();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollections.UnmodifiableCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollection, java.util.Collection
        @Deprecated
        public /* bridge */ /* synthetic */ boolean contains(Object obj) {
            return super.contains(obj);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollections.UnmodifiableCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollection
        public /* bridge */ /* synthetic */ boolean contains(short s) {
            return super.contains(s);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollections.UnmodifiableCollection, java.util.Collection
        public /* bridge */ /* synthetic */ boolean containsAll(Collection collection) {
            return super.containsAll(collection);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollections.UnmodifiableCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortIterable
        public /* bridge */ /* synthetic */ void forEach(ShortConsumer shortConsumer) {
            super.forEach(shortConsumer);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollections.UnmodifiableCollection, java.util.Collection
        public /* bridge */ /* synthetic */ boolean isEmpty() {
            return super.isEmpty();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollections.UnmodifiableCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortIterable
        public /* bridge */ /* synthetic */ ShortIterator iterator() {
            return super.iterator();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollections.UnmodifiableCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollection, java.util.Collection
        @Deprecated
        public /* bridge */ /* synthetic */ Stream<Short> parallelStream() {
            return super.parallelStream();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollections.UnmodifiableCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollection, java.util.Collection
        @Deprecated
        public /* bridge */ /* synthetic */ boolean remove(Object obj) {
            return super.remove(obj);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollections.UnmodifiableCollection, java.util.Collection
        public /* bridge */ /* synthetic */ boolean removeAll(Collection collection) {
            return super.removeAll(collection);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollections.UnmodifiableCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollection
        public /* bridge */ /* synthetic */ boolean removeIf(ShortPredicate shortPredicate) {
            return super.removeIf(shortPredicate);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollections.UnmodifiableCollection, java.util.Collection
        public /* bridge */ /* synthetic */ boolean retainAll(Collection collection) {
            return super.retainAll(collection);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollections.UnmodifiableCollection, java.util.Collection
        public /* bridge */ /* synthetic */ int size() {
            return super.size();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollections.UnmodifiableCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortIterable
        public /* bridge */ /* synthetic */ ShortSpliterator spliterator() {
            return super.spliterator();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollections.UnmodifiableCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollection, java.util.Collection
        @Deprecated
        public /* bridge */ /* synthetic */ Stream<Short> stream() {
            return super.stream();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollections.UnmodifiableCollection, java.util.Collection
        public /* bridge */ /* synthetic */ Object[] toArray() {
            return super.toArray();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollections.UnmodifiableCollection, java.util.Collection
        public /* bridge */ /* synthetic */ Object[] toArray(Object[] objArr) {
            return super.toArray(objArr);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollections.UnmodifiableCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollection
        public /* bridge */ /* synthetic */ short[] toShortArray() {
            return super.toShortArray();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollections.UnmodifiableCollection
        public /* bridge */ /* synthetic */ String toString() {
            return super.toString();
        }

        protected UnmodifiableSet(ShortSet shortSet) {
            super(shortSet);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortSet
        public boolean remove(short s) {
            throw new UnsupportedOperationException();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollections.UnmodifiableCollection, java.util.Collection
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            return this.collection.equals(obj);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollections.UnmodifiableCollection, java.util.Collection
        public int hashCode() {
            return this.collection.hashCode();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollections.UnmodifiableCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts.ShortCollection
        @Deprecated
        public boolean rem(short s) {
            return super.rem(s);
        }
    }

    public static ShortSet unmodifiable(ShortSet shortSet) {
        return new UnmodifiableSet(shortSet);
    }
}
