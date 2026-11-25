package org.jf.util.collection;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: loaded from: classes6.dex */
public class Iterables {
    public static <T> T getLast(List<T> list) {
        if (list.size() > 0) {
            return list.get(list.size() - 1);
        }
        return null;
    }

    public static <T> T getLast(Iterator<T> it) {
        T next = null;
        while (it.hasNext()) {
            next = it.next();
        }
        return next;
    }

    public static <T> T getFirst(Iterable<? extends T> iterable, T t) {
        return (T) getNext(iterable.iterator(), t);
    }

    public static <T> T getNext(Iterator<? extends T> it, T t) {
        return it.hasNext() ? it.next() : t;
    }

    public static <E, T extends E> Iterable<T> filter(Iterable<? extends T> iterable, Predicate<E> predicate) {
        return new FilterIterable(iterable, predicate);
    }

    public static <T> Iterator<T> filter(Iterator<? extends T> it, Predicate<T> predicate) {
        return new FilterIterator(it, predicate);
    }

    public static <T> Iterator<T> forArray(T... tArr) {
        return new ArrayIterator(tArr);
    }

    public static <T> boolean any(Iterable<T> iterable, Predicate<? super T> predicate) {
        if (iterable == null) {
            return false;
        }
        for (T t : iterable) {
            if (predicate == null) {
                if (t != null) {
                    return true;
                }
            } else if (predicate.test(t)) {
                return true;
            }
        }
        return false;
    }

    public static <T> Iterator<T> concat(Iterator<? extends T> it, Iterator<? extends T> it2) {
        return new CombiningIterator(it, it2);
    }

    public static <T> Iterable<T> concat(Iterable<? extends T> iterable, Iterable<? extends T> iterable2) {
        return new CombiningIterable(iterable, iterable2);
    }

    public static <T> Iterable<T> concat(Iterable<? extends Iterable<? extends T>> iterable) {
        return new ConcatIterables(iterable);
    }

    public static <E, T> Iterable<T> transform(Iterable<E> iterable, Function<? super E, ? extends T> function) {
        return new ComputeIterable(iterable, function);
    }

    public static <E, T> Iterator<T> transform(Iterator<E> it, Function<? super E, ? extends T> function) {
        return new ComputeIterator(it, function);
    }

    public static boolean isEmpty(Iterable<?> iterable) {
        if (iterable == null) {
            return true;
        }
        return isEmpty(iterable.iterator());
    }

    public static boolean isEmpty(Iterator<?> it) {
        if (it == null) {
            return true;
        }
        return !it.hasNext();
    }

    public static int size(Iterable<?> iterable) {
        return size(iterable.iterator());
    }

    public static int size(Iterator<?> it) {
        int i = 0;
        while (it.hasNext()) {
            it.next();
            i++;
        }
        return i;
    }
}
