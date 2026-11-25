package org.xmlunit.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/* JADX INFO: loaded from: classes3.dex */
public final class Linqy {
    private Linqy() {
    }

    public static <E> List<E> asList(Iterable<E> iterable) {
        if (iterable instanceof Collection) {
            return new ArrayList((Collection) iterable);
        }
        ArrayList arrayList = new ArrayList();
        Iterator<E> it = iterable.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next());
        }
        return arrayList;
    }

    public static <E> Iterable<E> cast(Iterable iterable) {
        return map(iterable, new Mapper<Object, E>() { // from class: org.xmlunit.util.Linqy.1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // org.xmlunit.util.Mapper
            public E apply(Object obj) {
                return obj;
            }
        });
    }

    public static <E> Iterable<E> singleton(final E e) {
        return new Iterable<E>() { // from class: org.xmlunit.util.Linqy.2
            @Override // java.lang.Iterable
            public Iterator<E> iterator() {
                return new OnceOnlyIterator(e);
            }
        };
    }

    public static <F, T> Iterable<T> map(final Iterable<F> iterable, final Mapper<? super F, T> mapper) {
        return new Iterable<T>() { // from class: org.xmlunit.util.Linqy.3
            @Override // java.lang.Iterable
            public Iterator<T> iterator() {
                return new MappingIterator(iterable.iterator(), mapper);
            }
        };
    }

    public static <T> Iterable<T> filter(final Iterable<T> iterable, final Predicate<? super T> predicate) {
        return new Iterable<T>() { // from class: org.xmlunit.util.Linqy.4
            @Override // java.lang.Iterable
            public Iterator<T> iterator() {
                return new FilteringIterator(iterable.iterator(), predicate);
            }
        };
    }

    public static int count(Iterable iterable) {
        if (iterable instanceof Collection) {
            return ((Collection) iterable).size();
        }
        Iterator it = iterable.iterator();
        int i = 0;
        while (it.hasNext()) {
            i++;
            it.next();
        }
        return i;
    }

    public static <T> boolean any(Iterable<T> iterable, Predicate<? super T> predicate) {
        Iterator<T> it = iterable.iterator();
        while (it.hasNext()) {
            if (predicate.test(it.next())) {
                return true;
            }
        }
        return false;
    }

    public static <T> boolean all(Iterable<T> iterable, Predicate<? super T> predicate) {
        Iterator<T> it = iterable.iterator();
        while (it.hasNext()) {
            if (!predicate.test(it.next())) {
                return false;
            }
        }
        return true;
    }

    private static class OnceOnlyIterator<E> implements Iterator<E> {
        private final E element;
        private boolean iterated;

        private OnceOnlyIterator(E e) {
            this.iterated = false;
            this.element = e;
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Iterator
        public E next() {
            if (this.iterated) {
                throw new NoSuchElementException();
            }
            this.iterated = true;
            return this.element;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return !this.iterated;
        }
    }

    private static class MappingIterator<F, T> implements Iterator<T> {
        private final Iterator<F> i;
        private final Mapper<? super F, T> mapper;

        private MappingIterator(Iterator<F> it, Mapper<? super F, T> mapper) {
            this.i = it;
            this.mapper = mapper;
        }

        @Override // java.util.Iterator
        public void remove() {
            this.i.remove();
        }

        @Override // java.util.Iterator
        public T next() {
            return this.mapper.apply(this.i.next());
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.i.hasNext();
        }
    }

    private static class FilteringIterator<T> implements Iterator<T> {
        private final Predicate<? super T> filter;
        private final Iterator<T> i;
        private T lookAhead;

        private FilteringIterator(Iterator<T> it, Predicate<? super T> predicate) {
            this.lookAhead = null;
            this.i = it;
            this.filter = predicate;
            hasNext();
        }

        @Override // java.util.Iterator
        public void remove() {
            this.i.remove();
        }

        @Override // java.util.Iterator
        public T next() {
            T t = this.lookAhead;
            if (t == null) {
                throw new NoSuchElementException();
            }
            this.lookAhead = null;
            return t;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            while (this.lookAhead == null && this.i.hasNext()) {
                T next = this.i.next();
                if (this.filter.test(next)) {
                    this.lookAhead = next;
                }
            }
            return this.lookAhead != null;
        }
    }
}
