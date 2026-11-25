package javaslang.collection;

import java.util.NoSuchElementException;
import java.util.function.Function;

/* JADX INFO: compiled from: Iterator.java */
/* JADX INFO: loaded from: classes11.dex */
interface IteratorModule {

    /* JADX INFO: compiled from: Iterator.java */
    public static final class ConcatIterator<T> extends AbstractIterator<T> {
        private Iterator<? extends T> current = Iterator.empty();
        private final Iterator<? extends Iterator<? extends T>> iterators;

        ConcatIterator(Iterator<? extends Iterator<? extends T>> iterator) {
            this.iterators = iterator;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            while (!this.current.hasNext() && !this.iterators.isEmpty()) {
                this.current = this.iterators.next();
            }
            return this.current.hasNext();
        }

        @Override // javaslang.collection.AbstractIterator
        public T getNext() {
            return this.current.next();
        }
    }

    /* JADX INFO: compiled from: Iterator.java */
    public static final class DistinctIterator<T, U> extends AbstractIterator<T> {
        private final Function<? super T, ? extends U> keyExtractor;
        private Set<U> known;
        private T next = null;
        private final Iterator<? extends T> that;

        DistinctIterator(Iterator<? extends T> iterator, Set<U> set, Function<? super T, ? extends U> function) {
            this.that = iterator;
            this.known = set;
            this.keyExtractor = function;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            while (this.next == null && this.that.hasNext()) {
                T next = this.that.next();
                U uApply = this.keyExtractor.apply(next);
                if (!this.known.contains(uApply)) {
                    this.known = this.known.add(uApply);
                    this.next = next;
                }
            }
            return this.next != null;
        }

        @Override // javaslang.collection.AbstractIterator
        public T getNext() {
            T t = this.next;
            this.next = null;
            return t;
        }
    }

    /* JADX INFO: compiled from: Iterator.java */
    public static final class EmptyIterator implements Iterator<Object> {
        static final EmptyIterator INSTANCE = new EmptyIterator();

        @Override // java.util.Iterator
        public boolean hasNext() {
            return false;
        }

        @Override // java.util.Iterator
        public Object next() {
            throw new NoSuchElementException(stringPrefix() + ".next()");
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new IllegalStateException(stringPrefix() + ".remove()");
        }

        @Override // javaslang.collection.Iterator, javaslang.Value
        public String stringPrefix() {
            return "EmptyIterator";
        }

        @Override // javaslang.Value
        public String toString() {
            return stringPrefix() + "()";
        }
    }

    /* JADX INFO: compiled from: Iterator.java */
    public static final class GroupedIterator<T> implements Iterator<Seq<T>> {
        private Object[] buffer;
        private final int gap;
        private final int preserve;
        private final int size;
        private final int step;
        private final Iterator<T> that;

        GroupedIterator(Iterator<T> iterator, int i, int i2) {
            if (i < 1 || i2 < 1) {
                throw new IllegalArgumentException("size (" + i + ") and step (" + i2 + ") must both be positive");
            }
            this.that = iterator;
            this.size = i;
            this.step = i2;
            this.gap = Math.max(i2 - i, 0);
            this.preserve = Math.max(i - i2, 0);
            this.buffer = take(iterator, new Object[i], 0, i);
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.buffer.length > 0;
        }

        @Override // java.util.Iterator
        public Seq<T> next() {
            Object[] objArr = this.buffer;
            if (objArr.length == 0) {
                throw new NoSuchElementException();
            }
            if (this.that.hasNext()) {
                Object[] objArr2 = new Object[this.size];
                this.buffer = objArr2;
                int i = this.preserve;
                if (i > 0) {
                    System.arraycopy(objArr, this.step, objArr2, 0, i);
                }
                int i2 = this.gap;
                if (i2 > 0) {
                    drop(this.that, i2);
                    this.buffer = take(this.that, this.buffer, this.preserve, this.size);
                } else {
                    this.buffer = take(this.that, this.buffer, this.preserve, this.step);
                }
            } else {
                this.buffer = new Object[0];
            }
            return Array.wrap(objArr);
        }

        private static void drop(Iterator<?> iterator, int i) {
            for (int i2 = 0; i2 < i && iterator.hasNext(); i2++) {
                iterator.next();
            }
        }

        private static Object[] take(Iterator<?> iterator, Object[] objArr, int i, int i2) {
            int i3 = i;
            while (i3 < i2 + i && iterator.hasNext()) {
                objArr[i3] = iterator.next();
                i3++;
            }
            if (i3 >= objArr.length) {
                return objArr;
            }
            Object[] objArr2 = new Object[i3];
            System.arraycopy(objArr, 0, objArr2, 0, i3);
            return objArr2;
        }
    }
}
