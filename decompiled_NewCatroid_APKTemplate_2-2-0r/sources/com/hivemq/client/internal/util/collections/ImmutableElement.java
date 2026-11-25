package com.hivemq.client.internal.util.collections;

import com.hivemq.client.internal.util.Checks;
import java.lang.reflect.Array;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes10.dex */
class ImmutableElement<E> implements ImmutableList<E> {
    private final E element;

    @Override // java.util.List, java.util.Collection
    public int size() {
        return 1;
    }

    static <E> ImmutableList<E> of(E e) {
        return of((Object) e, "Immutable list");
    }

    static <E> ImmutableList<E> of(E e, String str) {
        return new ImmutableElement(Checks.elementNotNull(e, str, 0));
    }

    ImmutableElement(E e) {
        this.element = e;
    }

    @Override // com.hivemq.client.internal.util.collections.ImmutableList, java.util.List
    public E get(int i) {
        Checks.index(i, 1);
        return this.element;
    }

    @Override // java.util.List, java.util.Collection
    public Object[] toArray() {
        return new Object[]{this.element};
    }

    @Override // java.util.List, java.util.Collection
    public <T> T[] toArray(T[] tArr) {
        Checks.notNull(tArr, "Array");
        if (tArr.length < 1) {
            tArr = (T[]) ((Object[]) Array.newInstance(tArr.getClass().getComponentType(), 1));
        } else if (tArr.length > 1) {
            tArr[1] = null;
        }
        tArr[0] = this.element;
        return tArr;
    }

    @Override // java.util.List
    public int indexOf(Object obj) {
        return this.element.equals(obj) ? 0 : -1;
    }

    @Override // java.util.List
    public int lastIndexOf(Object obj) {
        return indexOf(obj);
    }

    @Override // com.hivemq.client.internal.util.collections.ImmutableList, java.util.List
    public ImmutableList.ImmutableListIterator<E> listIterator(int i) {
        return new ElementIterator(Checks.cursorIndex(i, 1));
    }

    @Override // java.util.List, java.util.Collection, java.lang.Iterable
    public Spliterator<E> spliterator() {
        return new ElementSpliterator();
    }

    @Override // java.lang.Iterable
    public void forEach(Consumer<? super E> consumer) {
        Checks.notNull(consumer, "Consumer");
        consumer.accept(this.element);
    }

    @Override // com.hivemq.client.internal.util.collections.ImmutableList, java.util.List
    public ImmutableList<E> subList(int i, int i2) {
        Checks.indexRange(i, i2, 1);
        return i2 == i ? ImmutableList.of() : this;
    }

    @Override // java.util.List, java.util.Collection
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof List)) {
            return false;
        }
        List list = (List) obj;
        return list.size() == 1 && this.element.equals(list.get(0));
    }

    @Override // java.util.List, java.util.Collection
    public int hashCode() {
        return this.element.hashCode() + 31;
    }

    public String toString() {
        return "[" + this.element + "]";
    }

    private class ElementIterator implements ImmutableList.ImmutableListIterator<E> {
        private int index;

        ElementIterator(int i) {
            this.index = i;
        }

        @Override // java.util.ListIterator, java.util.Iterator
        public boolean hasNext() {
            return this.index == 0;
        }

        @Override // java.util.ListIterator, java.util.Iterator
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            this.index = 1;
            return (E) ImmutableElement.this.element;
        }

        @Override // java.util.ListIterator
        public int nextIndex() {
            return this.index;
        }

        @Override // java.util.ListIterator
        public boolean hasPrevious() {
            return this.index == 1;
        }

        @Override // java.util.ListIterator
        public E previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            this.index = 0;
            return (E) ImmutableElement.this.element;
        }

        @Override // java.util.ListIterator
        public int previousIndex() {
            return this.index - 1;
        }

        @Override // java.util.Iterator
        public void forEachRemaining(Consumer<? super E> consumer) {
            Checks.notNull(consumer, "Consumer");
            if (hasNext()) {
                consumer.accept((Object) next());
            }
        }
    }

    private class ElementSpliterator implements Spliterator<E> {
        private int size;

        @Override // java.util.Spliterator
        public int characteristics() {
            return 17745;
        }

        @Override // java.util.Spliterator
        public Spliterator<E> trySplit() {
            return null;
        }

        private ElementSpliterator() {
            this.size = 1;
        }

        @Override // java.util.Spliterator
        public boolean tryAdvance(Consumer<? super E> consumer) {
            Checks.notNull(consumer, "Consumer");
            if (this.size != 1) {
                return false;
            }
            consumer.accept((Object) ImmutableElement.this.element);
            this.size = 0;
            return true;
        }

        @Override // java.util.Spliterator
        public long estimateSize() {
            return this.size;
        }

        @Override // java.util.Spliterator
        public long getExactSizeIfKnown() {
            return this.size;
        }

        @Override // java.util.Spliterator
        public void forEachRemaining(Consumer<? super E> consumer) {
            Checks.notNull(consumer, "Consumer");
            tryAdvance(consumer);
        }
    }
}
