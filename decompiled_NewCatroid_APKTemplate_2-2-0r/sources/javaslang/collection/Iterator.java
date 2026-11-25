package javaslang.collection;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import javaslang.Tuple;
import javaslang.Tuple2;
import javaslang.control.Option;

/* JADX INFO: loaded from: classes11.dex */
public interface Iterator<T> extends java.util.Iterator<T>, Traversable<T> {

    @Deprecated
    public static final Iterator<Object> EMPTY = IteratorModule.EmptyIterator.INSTANCE;

    @Override // javaslang.collection.Traversable
    default boolean isTraversableAgain() {
        return false;
    }

    @Override // javaslang.collection.Traversable, javaslang.Value, java.lang.Iterable
    default Iterator<T> iterator() {
        return this;
    }

    @SafeVarargs
    static <T> Iterator<T> concat(Iterable<? extends T>... iterableArr) {
        Objects.requireNonNull(iterableArr, "iterables is null");
        if (iterableArr.length == 0) {
            return empty();
        }
        return new IteratorModule.ConcatIterator(Stream.of((Object[]) iterableArr).map(new Function() { // from class: javaslang.collection.Iterator$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Iterator.ofAll((Iterable) obj);
            }
        }).iterator());
    }

    static <T> Iterator<T> empty() {
        return IteratorModule.EmptyIterator.INSTANCE;
    }

    static <T> Iterator<T> of(final T t) {
        return new AbstractIterator<T>() { // from class: javaslang.collection.Iterator.1
            boolean hasNext = true;

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.hasNext;
            }

            @Override // javaslang.collection.AbstractIterator
            public T getNext() {
                this.hasNext = false;
                return (T) t;
            }
        };
    }

    static <T> Iterator<T> ofAll(Iterable<? extends T> iterable) {
        Objects.requireNonNull(iterable, "iterable is null");
        if (iterable instanceof Iterator) {
            return (Iterator) iterable;
        }
        return ofAll(iterable.iterator());
    }

    static <T> Iterator<T> ofAll(final java.util.Iterator<? extends T> it) {
        Objects.requireNonNull(it, "iterator is null");
        if (it instanceof Iterator) {
            return (Iterator) it;
        }
        return new AbstractIterator<T>() { // from class: javaslang.collection.Iterator.3
            @Override // java.util.Iterator
            public boolean hasNext() {
                return it.hasNext();
            }

            @Override // javaslang.collection.AbstractIterator
            public T getNext() {
                return (T) it.next();
            }
        };
    }

    default Iterator<T> intersperse(final T t) {
        if (!hasNext()) {
            return empty();
        }
        return new AbstractIterator<T>() { // from class: javaslang.collection.Iterator.25
            boolean insertElement = false;

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.hasNext();
            }

            @Override // javaslang.collection.AbstractIterator
            public T getNext() {
                if (this.insertElement) {
                    this.insertElement = false;
                    return (T) t;
                }
                this.insertElement = true;
                return this.next();
            }
        };
    }

    default Iterator<T> distinctBy(Comparator<? super T> comparator) {
        Objects.requireNonNull(comparator, "comparator is null");
        if (!hasNext()) {
            return empty();
        }
        return new IteratorModule.DistinctIterator(this, TreeSet.empty(comparator), Function.identity());
    }

    default <U> Iterator<T> distinctBy(Function<? super T, ? extends U> function) {
        Objects.requireNonNull(function, "keyExtractor is null");
        if (!hasNext()) {
            return empty();
        }
        return new IteratorModule.DistinctIterator(this, HashSet.empty(), function);
    }

    default Iterator<T> drop(long j) {
        if (j <= 0) {
            return this;
        }
        if (!hasNext()) {
            return empty();
        }
        return new AbstractIterator<T>(j, this) { // from class: javaslang.collection.Iterator.29
            long count;
            final /* synthetic */ long val$n;
            final /* synthetic */ Iterator val$that;

            {
                this.val$n = j;
                this.val$that = this;
                this.count = j;
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                while (this.count > 0 && this.val$that.hasNext()) {
                    this.val$that.next();
                    this.count--;
                }
                return this.val$that.hasNext();
            }

            @Override // javaslang.collection.AbstractIterator
            public T getNext() {
                return this.val$that.next();
            }
        };
    }

    default Iterator<T> dropRight(final long j) {
        if (j <= 0) {
            return this;
        }
        if (!hasNext()) {
            return empty();
        }
        return new AbstractIterator<T>() { // from class: javaslang.collection.Iterator.30
            private Queue<T> queue = Queue.empty();

            @Override // java.util.Iterator
            public boolean hasNext() {
                while (this.queue.length() < j && this.hasNext()) {
                    this.queue = this.queue.append(this.next());
                }
                return ((long) this.queue.length()) == j && this.hasNext();
            }

            @Override // javaslang.collection.AbstractIterator
            public T getNext() {
                Tuple2<T, Queue<T>> tuple2Dequeue = this.queue.append(this.next()).dequeue();
                this.queue = tuple2Dequeue._2;
                return tuple2Dequeue._1;
            }
        };
    }

    default Iterator<T> dropWhile(final Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate, "predicate is null");
        if (!hasNext()) {
            return empty();
        }
        return new AbstractIterator<T>() { // from class: javaslang.collection.Iterator.31
            private boolean cached = false;
            private boolean first = true;
            private T next;

            @Override // java.util.Iterator
            public boolean hasNext() {
                if (this.cached) {
                    return true;
                }
                if (this.first) {
                    this.first = false;
                    while (this.hasNext()) {
                        T next = this.next();
                        this.next = next;
                        if (!predicate.test(next)) {
                            this.cached = true;
                            return true;
                        }
                    }
                    return false;
                }
                if (!this.hasNext()) {
                    return false;
                }
                this.next = this.next();
                this.cached = true;
                return true;
            }

            @Override // javaslang.collection.AbstractIterator
            public T getNext() {
                this.cached = false;
                return this.next;
            }
        };
    }

    default Iterator<T> filter(final Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate, "predicate is null");
        if (!hasNext()) {
            return empty();
        }
        return new AbstractIterator<T>() { // from class: javaslang.collection.Iterator.32
            Option<T> next = Option.none();

            @Override // java.util.Iterator
            public boolean hasNext() {
                while (this.next.isEmpty() && this.hasNext()) {
                    T next = this.next();
                    if (predicate.test(next)) {
                        this.next = Option.some(next);
                    }
                }
                return this.next.isDefined();
            }

            @Override // javaslang.collection.AbstractIterator
            public T getNext() {
                T t = this.next.get();
                this.next = Option.none();
                return t;
            }
        };
    }

    @Override // javaslang.collection.Traversable, javaslang.Value
    default T get() {
        return head();
    }

    @Override // javaslang.collection.Traversable
    default T head() {
        if (!hasNext()) {
            throw new NoSuchElementException("head() on empty iterator");
        }
        return next();
    }

    @Override // javaslang.collection.Traversable, javaslang.Value
    default boolean isEmpty() {
        return !hasNext();
    }

    @Override // javaslang.collection.Traversable
    default int length() {
        return ((Integer) foldLeft(0, new BiFunction() { // from class: javaslang.collection.Iterator$$ExternalSyntheticLambda1
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return Integer.valueOf(((Integer) obj).intValue() + 1);
            }
        })).intValue();
    }

    default <U> Iterator<U> map(final Function<? super T, ? extends U> function) {
        Objects.requireNonNull(function, "mapper is null");
        if (!hasNext()) {
            return empty();
        }
        return new AbstractIterator<U>() { // from class: javaslang.collection.Iterator.34
            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.hasNext();
            }

            @Override // javaslang.collection.AbstractIterator
            public U getNext() {
                return (U) function.apply(this.next());
            }
        };
    }

    default Tuple2<Iterator<T>, Iterator<T>> partition(Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate, "predicate is null");
        if (!hasNext()) {
            return Tuple.of(empty(), empty());
        }
        Stream streamOfAll = Stream.ofAll(this);
        return Tuple.of(streamOfAll.iterator().filter(predicate), streamOfAll.iterator().filter(predicate.negate()));
    }

    @Override // javaslang.collection.Traversable, javaslang.collection.Foldable
    default T reduceLeft(BiFunction<? super T, ? super T, ? extends T> biFunction) {
        Objects.requireNonNull(biFunction, "op is null");
        if (isEmpty()) {
            throw new NoSuchElementException("reduceLeft on Nil");
        }
        Stream streamOfAll = Stream.ofAll(this);
        return (T) streamOfAll.tail().foldLeft(streamOfAll.head(), biFunction);
    }

    default Iterator<Seq<T>> sliding(long j, long j2) {
        return new IteratorModule.GroupedIterator(this, (int) j, (int) j2);
    }

    default Tuple2<Iterator<T>, Iterator<T>> span(Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate, "predicate is null");
        if (!hasNext()) {
            return Tuple.of(empty(), empty());
        }
        Stream streamOfAll = Stream.ofAll(this);
        return Tuple.of(streamOfAll.iterator().takeWhile(predicate), streamOfAll.iterator().dropWhile(predicate));
    }

    @Override // java.lang.Iterable
    default Spliterator<T> spliterator() {
        Stream streamOfAll = Stream.ofAll(this);
        return Spliterators.spliterator(streamOfAll.iterator(), streamOfAll.length(), 1040);
    }

    @Override // javaslang.Value
    default String stringPrefix() {
        return "Iterator";
    }

    @Override // javaslang.collection.Traversable
    default Iterator<T> tail() {
        if (!hasNext()) {
            throw new UnsupportedOperationException();
        }
        next();
        return this;
    }

    default Iterator<T> take(long j) {
        if (j <= 0 || !hasNext()) {
            return empty();
        }
        return new AbstractIterator<T>(j, this) { // from class: javaslang.collection.Iterator.39
            long count;
            final /* synthetic */ long val$n;
            final /* synthetic */ Iterator val$that;

            {
                this.val$n = j;
                this.val$that = this;
                this.count = j;
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.count > 0 && this.val$that.hasNext();
            }

            @Override // javaslang.collection.AbstractIterator
            public T getNext() {
                this.count--;
                return this.val$that.next();
            }
        };
    }

    default Iterator<T> takeRight(final long j) {
        if (j <= 0) {
            return empty();
        }
        return new AbstractIterator<T>() { // from class: javaslang.collection.Iterator.40
            private Queue<T> queue = Queue.empty();

            @Override // java.util.Iterator
            public boolean hasNext() {
                while (this.hasNext()) {
                    Queue<T> queueEnqueue = this.queue.enqueue(this.next());
                    this.queue = queueEnqueue;
                    if (queueEnqueue.length() > j) {
                        this.queue = this.queue.dequeue()._2;
                    }
                }
                return this.queue.length() > 0;
            }

            @Override // javaslang.collection.AbstractIterator
            public T getNext() {
                Tuple2<T, Queue<T>> tuple2Dequeue = this.queue.dequeue();
                this.queue = tuple2Dequeue._2;
                return tuple2Dequeue._1;
            }
        };
    }

    default Iterator<T> takeWhile(final Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate, "predicate is null");
        if (!hasNext()) {
            return empty();
        }
        return new AbstractIterator<T>() { // from class: javaslang.collection.Iterator.41
            private boolean cached = false;
            private boolean finished = false;
            private T next;

            @Override // java.util.Iterator
            public boolean hasNext() {
                if (this.cached) {
                    return true;
                }
                if (this.finished) {
                    return false;
                }
                if (this.hasNext()) {
                    T next = this.next();
                    this.next = next;
                    if (predicate.test(next)) {
                        this.cached = true;
                        return true;
                    }
                }
                this.finished = true;
                return false;
            }

            @Override // javaslang.collection.AbstractIterator
            public T getNext() {
                this.cached = false;
                return this.next;
            }
        };
    }
}
