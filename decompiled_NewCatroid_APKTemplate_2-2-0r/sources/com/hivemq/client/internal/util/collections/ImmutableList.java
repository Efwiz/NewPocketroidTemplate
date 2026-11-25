package com.hivemq.client.internal.util.collections;

import com.hivemq.client.internal.util.Checks;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/* JADX INFO: loaded from: classes10.dex */
public interface ImmutableList<E> extends List<E>, RandomAccess {
    @Override // java.util.List
    E get(int i);

    @Override // java.util.List
    ImmutableListIterator<E> listIterator(int i);

    @Override // java.util.List
    ImmutableList<E> subList(int i, int i2);

    default ImmutableList<E> trim() {
        return this;
    }

    static <E> ImmutableList<E> of() {
        return ImmutableEmptyList.of();
    }

    static <E> ImmutableList<E> of(E e) {
        return ImmutableElement.of((Object) e);
    }

    static <E> ImmutableList<E> of(E e, E e2) {
        return ImmutableArray.of(e, e2);
    }

    static <E> ImmutableList<E> of(E e, E e2, E e3) {
        return ImmutableArray.of(e, e2, e3);
    }

    @SafeVarargs
    static <E> ImmutableList<E> of(E e, E e2, E e3, E... eArr) {
        Checks.notNull(eArr, "Immutable list elements");
        Object[] objArr = new Object[eArr.length + 3];
        objArr[0] = e;
        objArr[1] = e2;
        objArr[2] = e3;
        System.arraycopy(eArr, 0, objArr, 3, eArr.length);
        return ImmutableArray.of(objArr);
    }

    static <E> ImmutableList<E> copyOf(E[] eArr) {
        return copyOf(eArr, "Immutable list");
    }

    static <E> ImmutableList<E> copyOf(E[] eArr, String str) {
        Checks.notNull(eArr, str);
        int length = eArr.length;
        if (length == 0) {
            return ImmutableEmptyList.of();
        }
        if (length == 1) {
            return ImmutableElement.of((Object) eArr[0], str);
        }
        return ImmutableArray.of(Arrays.copyOf(eArr, eArr.length, Object[].class), str);
    }

    static <E> ImmutableList<E> copyOf(Collection<? extends E> collection) {
        return copyOf(collection, "Immutable list");
    }

    static <E> ImmutableList<E> copyOf(Collection<? extends E> collection, String str) {
        Checks.notNull(collection, str);
        if (collection instanceof ImmutableList) {
            return ((ImmutableList) collection).trim();
        }
        int size = collection.size();
        if (size == 0) {
            return ImmutableEmptyList.of();
        }
        if (size == 1) {
            return ImmutableElement.of(Builder.first(collection), str);
        }
        return ImmutableArray.of(collection.toArray(), str);
    }

    static <E> Builder<E> builder() {
        return new Builder<>();
    }

    static <E> Builder<E> builder(int i) {
        return new Builder<>(i);
    }

    @Override // java.util.List, java.util.Collection
    default boolean isEmpty() {
        return size() == 0;
    }

    @Override // java.util.List, java.util.Collection
    default boolean contains(Object obj) {
        return indexOf(obj) >= 0;
    }

    @Override // java.util.List, java.util.Collection
    default boolean containsAll(Collection<?> collection) {
        Checks.notNull(collection, "Collection");
        Iterator<?> it = collection.iterator();
        while (it.hasNext()) {
            if (!contains(it.next())) {
                return false;
            }
        }
        return true;
    }

    @Override // java.util.List, java.util.Collection, java.lang.Iterable
    default ImmutableListIterator<E> iterator() {
        return listIterator();
    }

    @Override // java.util.List
    default ImmutableListIterator<E> listIterator() {
        return listIterator(0);
    }

    @Override // java.util.List, java.util.Collection
    @Deprecated
    default boolean add(E e) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List, java.util.Collection
    @Deprecated
    default boolean remove(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List, java.util.Collection
    @Deprecated
    default boolean addAll(Collection<? extends E> collection) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List, java.util.Collection
    @Deprecated
    default boolean removeAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List, java.util.Collection
    @Deprecated
    default boolean retainAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Collection
    @Deprecated
    default boolean removeIf(Predicate<? super E> predicate) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List, java.util.Collection
    @Deprecated
    default void clear() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List
    @Deprecated
    default void add(int i, E e) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List
    @Deprecated
    default E remove(int i) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List
    @Deprecated
    default E set(int i, E e) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List
    @Deprecated
    default boolean addAll(int i, Collection<? extends E> collection) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List
    @Deprecated
    default void replaceAll(UnaryOperator<E> unaryOperator) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List
    @Deprecated
    default void sort(Comparator<? super E> comparator) {
        throw new UnsupportedOperationException();
    }

    public interface ImmutableListIterator<E> extends ListIterator<E> {
        @Override // java.util.ListIterator, java.util.Iterator
        @Deprecated
        default void remove() {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.ListIterator
        @Deprecated
        default void set(E e) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.ListIterator
        @Deprecated
        default void add(E e) {
            throw new UnsupportedOperationException();
        }
    }

    public static class Builder<E> {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private static final int INITIAL_CAPACITY = 4;
        private Object[] array;
        private E e;
        private int size;

        private int newCapacity(int i) {
            return i + (i >> 1);
        }

        private Builder() {
        }

        private Builder(int i) {
            if (i > 1) {
                this.array = new Object[i];
            }
        }

        private Object[] ensureCapacity(int i) {
            Object[] objArr = this.array;
            if (objArr == null) {
                this.array = new Object[Math.max(4, i)];
            } else if (i > objArr.length) {
                this.array = Arrays.copyOf(objArr, Math.max(newCapacity(objArr.length), i), Object[].class);
            }
            E e = this.e;
            if (e != null) {
                this.array[0] = e;
                this.e = null;
            }
            return this.array;
        }

        public void ensureFree(int i) {
            int i2 = this.size + i;
            if (i2 > 1) {
                ensureCapacity(i2);
            }
        }

        public Builder<E> add(E e) {
            Checks.notNull(e, "Immutable list element");
            int i = this.size;
            if (i == 0) {
                this.e = e;
                this.size = 1;
            } else {
                int i2 = i + 1;
                ensureCapacity(i2)[this.size] = e;
                this.size = i2;
            }
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public Builder<E> addAll(Collection<? extends E> collection) {
            Checks.notNull(collection, "Immutable list elements");
            int size = collection.size();
            if (size != 0) {
                if (size == 1) {
                    add(first(collection));
                } else {
                    int i = this.size + size;
                    Object[] objArrEnsureCapacity = ensureCapacity(i);
                    if ((collection instanceof List) && (collection instanceof RandomAccess)) {
                        List list = (List) collection;
                        for (int i2 = 0; i2 < size; i2++) {
                            objArrEnsureCapacity[this.size + i2] = Checks.notNull(list.get(i2), "Immutable list");
                        }
                    } else {
                        int i3 = this.size;
                        Iterator<? extends E> it = collection.iterator();
                        while (it.hasNext()) {
                            objArrEnsureCapacity[i3] = Checks.notNull(it.next(), "Immutable list");
                            i3++;
                        }
                    }
                    this.size = i;
                }
            }
            return this;
        }

        public int getSize() {
            return this.size;
        }

        public ImmutableList<E> build() {
            int i = this.size;
            if (i == 0) {
                return ImmutableEmptyList.of();
            }
            if (i == 1) {
                return new ImmutableElement(this.e);
            }
            if (this.array.length == i) {
                return new ImmutableArray(this.array);
            }
            return new ImmutableArray(Arrays.copyOfRange(this.array, 0, this.size, Object[].class));
        }

        static <E> E first(Collection<E> collection) {
            return collection instanceof List ? (E) ((List) collection).get(0) : collection.iterator().next();
        }
    }
}
