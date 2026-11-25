package org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

/* JADX INFO: loaded from: classes5.dex */
public final class ObjectSets {
    public static final EmptySet EMPTY_SET = new EmptySet();
    static final ObjectSet UNMODIFIABLE_EMPTY_SET = unmodifiable(new ObjectArraySet(ObjectArrays.EMPTY_ARRAY));

    public static class EmptySet<K> extends ObjectCollections.EmptyCollection<K> implements ObjectSet<K>, Serializable, Cloneable {
        private static final long serialVersionUID = -7046029254386353129L;

        protected EmptySet() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            throw new UnsupportedOperationException();
        }

        public Object clone() {
            return ObjectSets.EMPTY_SET;
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollections.EmptyCollection, java.util.Collection
        public boolean equals(Object obj) {
            return (obj instanceof Set) && ((Set) obj).isEmpty();
        }

        private Object readResolve() {
            return ObjectSets.EMPTY_SET;
        }
    }

    public static class SynchronizedSet<K> extends ObjectCollections.SynchronizedCollection<K> implements ObjectSet<K>, Serializable {
        private static final long serialVersionUID = -7046029254386353129L;

        /* JADX WARN: Multi-variable type inference failed */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollections.SynchronizedCollection, java.util.Collection
        public /* bridge */ /* synthetic */ boolean add(Object obj) {
            return super.add(obj);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollections.SynchronizedCollection, java.util.Collection
        public /* bridge */ /* synthetic */ boolean addAll(Collection collection) {
            return super.addAll(collection);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollections.SynchronizedCollection, java.util.Collection
        public /* bridge */ /* synthetic */ void clear() {
            super.clear();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollections.SynchronizedCollection, java.util.Collection
        public /* bridge */ /* synthetic */ boolean contains(Object obj) {
            return super.contains(obj);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollections.SynchronizedCollection, java.util.Collection
        public /* bridge */ /* synthetic */ boolean containsAll(Collection collection) {
            return super.containsAll(collection);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollections.SynchronizedCollection, java.util.Collection
        public /* bridge */ /* synthetic */ boolean equals(Object obj) {
            return super.equals(obj);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollections.SynchronizedCollection, java.lang.Iterable
        public /* bridge */ /* synthetic */ void forEach(Consumer consumer) {
            super.forEach(consumer);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollections.SynchronizedCollection, java.util.Collection
        public /* bridge */ /* synthetic */ int hashCode() {
            return super.hashCode();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollections.SynchronizedCollection, java.util.Collection
        public /* bridge */ /* synthetic */ boolean isEmpty() {
            return super.isEmpty();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollections.SynchronizedCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterable
        public /* bridge */ /* synthetic */ ObjectIterator iterator() {
            return super.iterator();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollections.SynchronizedCollection, java.util.Collection
        public /* bridge */ /* synthetic */ Stream parallelStream() {
            return super.parallelStream();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollections.SynchronizedCollection, java.util.Collection
        public /* bridge */ /* synthetic */ boolean removeAll(Collection collection) {
            return super.removeAll(collection);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollections.SynchronizedCollection, java.util.Collection
        public /* bridge */ /* synthetic */ boolean removeIf(Predicate predicate) {
            return super.removeIf(predicate);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollections.SynchronizedCollection, java.util.Collection
        public /* bridge */ /* synthetic */ boolean retainAll(Collection collection) {
            return super.retainAll(collection);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollections.SynchronizedCollection, java.util.Collection
        public /* bridge */ /* synthetic */ int size() {
            return super.size();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollections.SynchronizedCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterable
        public /* bridge */ /* synthetic */ ObjectSpliterator spliterator() {
            return super.spliterator();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollections.SynchronizedCollection, java.util.Collection
        public /* bridge */ /* synthetic */ Stream stream() {
            return super.stream();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollections.SynchronizedCollection, java.util.Collection
        public /* bridge */ /* synthetic */ Object[] toArray() {
            return super.toArray();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollections.SynchronizedCollection, java.util.Collection
        public /* bridge */ /* synthetic */ Object[] toArray(Object[] objArr) {
            return super.toArray(objArr);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollections.SynchronizedCollection
        public /* bridge */ /* synthetic */ String toString() {
            return super.toString();
        }

        protected SynchronizedSet(ObjectSet<K> objectSet, Object obj) {
            super(objectSet, obj);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollections.SynchronizedCollection, java.util.Collection
        public boolean remove(Object obj) {
            boolean zRemove;
            synchronized (this.sync) {
                zRemove = this.collection.remove(obj);
            }
            return zRemove;
        }
    }

    public static <K> ObjectSet<K> synchronize(ObjectSet<K> objectSet, Object obj) {
        return new SynchronizedSet(objectSet, obj);
    }

    public static class UnmodifiableSet<K> extends ObjectCollections.UnmodifiableCollection<K> implements ObjectSet<K>, Serializable {
        private static final long serialVersionUID = -7046029254386353129L;

        /* JADX WARN: Multi-variable type inference failed */
        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollections.UnmodifiableCollection, java.util.Collection
        public /* bridge */ /* synthetic */ boolean add(Object obj) {
            return super.add(obj);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollections.UnmodifiableCollection, java.util.Collection
        public /* bridge */ /* synthetic */ boolean addAll(Collection collection) {
            return super.addAll(collection);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollections.UnmodifiableCollection, java.util.Collection
        public /* bridge */ /* synthetic */ void clear() {
            super.clear();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollections.UnmodifiableCollection, java.util.Collection
        public /* bridge */ /* synthetic */ boolean contains(Object obj) {
            return super.contains(obj);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollections.UnmodifiableCollection, java.util.Collection
        public /* bridge */ /* synthetic */ boolean containsAll(Collection collection) {
            return super.containsAll(collection);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollections.UnmodifiableCollection, java.lang.Iterable
        public /* bridge */ /* synthetic */ void forEach(Consumer consumer) {
            super.forEach(consumer);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollections.UnmodifiableCollection, java.util.Collection
        public /* bridge */ /* synthetic */ boolean isEmpty() {
            return super.isEmpty();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollections.UnmodifiableCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollection, java.util.Collection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterable
        public /* bridge */ /* synthetic */ ObjectIterator iterator() {
            return super.iterator();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollections.UnmodifiableCollection, java.util.Collection
        public /* bridge */ /* synthetic */ Stream parallelStream() {
            return super.parallelStream();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollections.UnmodifiableCollection, java.util.Collection
        public /* bridge */ /* synthetic */ boolean removeAll(Collection collection) {
            return super.removeAll(collection);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollections.UnmodifiableCollection, java.util.Collection
        public /* bridge */ /* synthetic */ boolean removeIf(Predicate predicate) {
            return super.removeIf(predicate);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollections.UnmodifiableCollection, java.util.Collection
        public /* bridge */ /* synthetic */ boolean retainAll(Collection collection) {
            return super.retainAll(collection);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollections.UnmodifiableCollection, java.util.Collection
        public /* bridge */ /* synthetic */ int size() {
            return super.size();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollections.UnmodifiableCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollection, java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectIterable
        public /* bridge */ /* synthetic */ ObjectSpliterator spliterator() {
            return super.spliterator();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollections.UnmodifiableCollection, java.util.Collection
        public /* bridge */ /* synthetic */ Stream stream() {
            return super.stream();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollections.UnmodifiableCollection, java.util.Collection
        public /* bridge */ /* synthetic */ Object[] toArray() {
            return super.toArray();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollections.UnmodifiableCollection, java.util.Collection
        public /* bridge */ /* synthetic */ Object[] toArray(Object[] objArr) {
            return super.toArray(objArr);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollections.UnmodifiableCollection
        public /* bridge */ /* synthetic */ String toString() {
            return super.toString();
        }

        protected UnmodifiableSet(ObjectSet<? extends K> objectSet) {
            super(objectSet);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollections.UnmodifiableCollection, java.util.Collection
        public boolean remove(Object obj) {
            throw new UnsupportedOperationException();
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollections.UnmodifiableCollection, java.util.Collection
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            return this.collection.equals(obj);
        }

        @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectCollections.UnmodifiableCollection, java.util.Collection
        public int hashCode() {
            return this.collection.hashCode();
        }
    }

    public static <K> ObjectSet<K> unmodifiable(ObjectSet<? extends K> objectSet) {
        return new UnmodifiableSet(objectSet);
    }
}
