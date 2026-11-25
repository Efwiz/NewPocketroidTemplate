package com.hivemq.client.internal.util.collections;

import com.hivemq.client.internal.util.Checks;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.function.Consumer;
import okhttp3.HttpUrl;

/* JADX INFO: loaded from: classes10.dex */
class ImmutableEmptyList implements ImmutableList<Object> {
    private static final ImmutableEmptyList INSTANCE = new ImmutableEmptyList();
    private static final Object[] EMPTY = new Object[0];

    @Override // java.util.List, java.util.Collection
    public int hashCode() {
        return 1;
    }

    @Override // java.util.List
    public int indexOf(Object obj) {
        return -1;
    }

    @Override // java.util.List
    public int lastIndexOf(Object obj) {
        return -1;
    }

    @Override // java.util.List, java.util.Collection
    public int size() {
        return 0;
    }

    static <E> ImmutableList<E> of() {
        return INSTANCE;
    }

    private ImmutableEmptyList() {
    }

    @Override // com.hivemq.client.internal.util.collections.ImmutableList, java.util.List
    public Object get(int i) {
        throw new IndexOutOfBoundsException("Empty list");
    }

    @Override // java.util.List, java.util.Collection
    public Object[] toArray() {
        return EMPTY;
    }

    @Override // java.util.List, java.util.Collection
    public <T> T[] toArray(T[] tArr) {
        Checks.notNull(tArr, "Array");
        if (tArr.length > 0) {
            tArr[0] = null;
        }
        return tArr;
    }

    @Override // com.hivemq.client.internal.util.collections.ImmutableList, java.util.List
    public ImmutableList.ImmutableListIterator<Object> listIterator(int i) {
        Checks.cursorIndex(i, 0);
        return EmptyIterator.of();
    }

    @Override // java.util.List, java.util.Collection, java.lang.Iterable
    public Spliterator<Object> spliterator() {
        return EmptySpliterator.of();
    }

    @Override // java.lang.Iterable
    public void forEach(Consumer<? super Object> consumer) {
        Checks.notNull(consumer, "Consumer");
    }

    @Override // com.hivemq.client.internal.util.collections.ImmutableList, java.util.List
    public ImmutableList<Object> subList(int i, int i2) {
        Checks.indexRange(i, i2, 0);
        return this;
    }

    @Override // java.util.List, java.util.Collection
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof List) && ((List) obj).size() == 0;
    }

    public String toString() {
        return HttpUrl.PATH_SEGMENT_ENCODE_SET_URI;
    }

    private static class EmptyIterator implements ImmutableList.ImmutableListIterator<Object> {
        private static final EmptyIterator INSTANCE = new EmptyIterator();

        @Override // java.util.ListIterator, java.util.Iterator
        public boolean hasNext() {
            return false;
        }

        @Override // java.util.ListIterator
        public boolean hasPrevious() {
            return false;
        }

        @Override // java.util.ListIterator
        public int nextIndex() {
            return 0;
        }

        @Override // java.util.ListIterator
        public int previousIndex() {
            return -1;
        }

        static <E> ImmutableList.ImmutableListIterator<E> of() {
            return INSTANCE;
        }

        private EmptyIterator() {
        }

        @Override // java.util.ListIterator, java.util.Iterator
        public Object next() {
            throw new NoSuchElementException();
        }

        @Override // java.util.ListIterator
        public Object previous() {
            throw new NoSuchElementException();
        }

        @Override // java.util.Iterator
        public void forEachRemaining(Consumer<? super Object> consumer) {
            Checks.notNull(consumer, "Consumer");
        }
    }

    private static class EmptySpliterator implements Spliterator<Object> {
        private static final EmptySpliterator INSTANCE = new EmptySpliterator();

        @Override // java.util.Spliterator
        public int characteristics() {
            return 17745;
        }

        @Override // java.util.Spliterator
        public long estimateSize() {
            return 0L;
        }

        @Override // java.util.Spliterator
        public long getExactSizeIfKnown() {
            return 0L;
        }

        @Override // java.util.Spliterator
        public Spliterator<Object> trySplit() {
            return null;
        }

        static <E> Spliterator<E> of() {
            return INSTANCE;
        }

        private EmptySpliterator() {
        }

        @Override // java.util.Spliterator
        public boolean tryAdvance(Consumer<? super Object> consumer) {
            Checks.notNull(consumer, "Consumer");
            return false;
        }

        @Override // java.util.Spliterator
        public void forEachRemaining(Consumer<? super Object> consumer) {
            Checks.notNull(consumer, "Consumer");
        }
    }
}
