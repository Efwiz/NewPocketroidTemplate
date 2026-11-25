package javaslang.collection;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.Serializable;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import javaslang.Lazy;

/* JADX INFO: loaded from: classes11.dex */
public interface Stream<T> extends LinearSeq<T> {
    public static final long serialVersionUID = 1;

    static /* synthetic */ Stream lambda$prepend$14(Stream _this) {
        return _this;
    }

    @Override // javaslang.collection.Traversable
    default boolean isTraversableAgain() {
        return true;
    }

    @Override // javaslang.collection.LinearSeq, javaslang.collection.Seq, javaslang.collection.Traversable
    Stream<T> tail();

    static <T> Stream<T> cons(T t, Supplier<? extends Stream<? extends T>> supplier) {
        Objects.requireNonNull(supplier, "tailSupplier is null");
        return new StreamModule.ConsImpl(t, supplier);
    }

    static <T> Stream<T> empty() {
        return Empty.instance();
    }

    static <T> Stream<T> of(T t) {
        return cons(t, new Supplier() { // from class: javaslang.collection.Stream$$ExternalSyntheticLambda4
            @Override // java.util.function.Supplier
            public final Object get() {
                return Stream.Empty.instance();
            }
        });
    }

    @SafeVarargs
    static <T> Stream<T> of(final T... tArr) {
        Objects.requireNonNull(tArr, "elements is null");
        return ofAll(new Iterator<T>() { // from class: javaslang.collection.Stream.1
            int i = 0;

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.i < tArr.length;
            }

            @Override // java.util.Iterator
            public T next() {
                Object[] objArr = tArr;
                int i = this.i;
                this.i = i + 1;
                return (T) objArr[i];
            }
        });
    }

    static <T> Stream<T> ofAll(Iterable<? extends T> iterable) {
        Objects.requireNonNull(iterable, "elements is null");
        if (iterable instanceof Stream) {
            return (Stream) iterable;
        }
        return StreamModule.StreamFactory.create(iterable.iterator());
    }

    default Stream<T> append(T t) {
        return isEmpty() ? of(t) : new StreamModule.AppendElements(head(), Queue.of(t), new Stream$$ExternalSyntheticLambda1(this));
    }

    default Stream<T> appendAll(Iterable<? extends T> iterable) {
        Objects.requireNonNull(iterable, "elements is null");
        if (!isEmpty()) {
            iterable = Iterator.concat(this, iterable);
        }
        return ofAll(iterable);
    }

    @Override // javaslang.collection.Seq
    default T get(int i) {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException("get(" + i + ") on Nil");
        }
        if (i < 0) {
            throw new IndexOutOfBoundsException("get(" + i + Constant.AFTER_QUTO);
        }
        Stream<T> streamTail = this;
        for (int i2 = i - 1; i2 >= 0; i2--) {
            streamTail = streamTail.tail();
            if (streamTail.isEmpty()) {
                throw new IndexOutOfBoundsException(String.format("get(%s) on Stream of size %s", Integer.valueOf(i), Integer.valueOf(i - i2)));
            }
        }
        return streamTail.head();
    }

    @Override // javaslang.collection.Traversable
    default int length() {
        return ((Integer) foldLeft(0, new BiFunction() { // from class: javaslang.collection.Stream$$ExternalSyntheticLambda5
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return Integer.valueOf(((Integer) obj).intValue() + 1);
            }
        })).intValue();
    }

    @Override // javaslang.collection.LinearSeq, javaslang.collection.Seq
    default <U> Stream<U> map(final Function<? super T, ? extends U> function) {
        Objects.requireNonNull(function, "mapper is null");
        if (isEmpty()) {
            return Empty.instance();
        }
        return cons(function.apply(head()), new Supplier() { // from class: javaslang.collection.Stream$$ExternalSyntheticLambda2
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.tail().map(function);
            }
        });
    }

    default Stream<T> prepend(T t) {
        return cons(t, new Supplier() { // from class: javaslang.collection.Stream$$ExternalSyntheticLambda3
            @Override // java.util.function.Supplier
            public final Object get() {
                return Stream.lambda$prepend$14(this.f$0);
            }
        });
    }

    default Stream<T> prependAll(Iterable<? extends T> iterable) {
        Objects.requireNonNull(iterable, "elements is null");
        return ofAll(iterable).appendAll(this);
    }

    default Stream<T> reverse() {
        return isEmpty() ? this : (Stream) foldLeft(empty(), new BiFunction() { // from class: javaslang.collection.Stream$$ExternalSyntheticLambda0
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return ((Stream) obj).prepend(obj2);
            }
        });
    }

    @Override // java.lang.Iterable
    default Spliterator<T> spliterator() {
        return Spliterators.spliterator(iterator(), length(), 1040);
    }

    @Override // javaslang.Value
    default String stringPrefix() {
        return "Stream";
    }

    public static final class Empty<T> implements Serializable, Stream<T> {
        private static final Empty<?> INSTANCE = new Empty<>();
        private static final long serialVersionUID = 1;

        public boolean equals(Object obj) {
            return obj == this;
        }

        public int hashCode() {
            return 1;
        }

        @Override // javaslang.collection.Traversable, javaslang.Value
        public boolean isEmpty() {
            return true;
        }

        private Empty() {
        }

        public static <T> Empty<T> instance() {
            return (Empty<T>) INSTANCE;
        }

        @Override // javaslang.collection.Traversable
        public T head() {
            throw new NoSuchElementException("head of empty stream");
        }

        @Override // javaslang.collection.Traversable, javaslang.Value, java.lang.Iterable
        public Iterator<T> iterator() {
            return Iterator.empty();
        }

        @Override // javaslang.collection.Stream, javaslang.collection.LinearSeq, javaslang.collection.Seq, javaslang.collection.Traversable
        public Stream<T> tail() {
            throw new UnsupportedOperationException("tail of empty stream");
        }

        @Override // javaslang.Value
        public String toString() {
            return stringPrefix() + "()";
        }

        private Object readResolve() {
            return INSTANCE;
        }
    }

    public static abstract class Cons<T> implements Stream<T> {
        private static final long serialVersionUID = 1;
        final T head;
        final Lazy<Stream<T>> tail;

        @Override // javaslang.collection.Traversable, javaslang.Value
        public boolean isEmpty() {
            return false;
        }

        Cons(T t, Supplier<Stream<T>> supplier) {
            Objects.requireNonNull(supplier, "tail is null");
            this.head = t;
            this.tail = Lazy.of(supplier);
        }

        @Override // javaslang.collection.Traversable
        public T head() {
            return this.head;
        }

        @Override // javaslang.collection.Traversable, javaslang.Value, java.lang.Iterable
        public Iterator<T> iterator() {
            return new StreamModule.StreamIterator(this);
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Stream)) {
                return false;
            }
            Stream streamTail = (Stream) obj;
            Stream streamTail2 = this;
            while (!streamTail2.isEmpty() && !streamTail.isEmpty()) {
                if (!Objects.equals(streamTail2.head(), streamTail.head())) {
                    return false;
                }
                streamTail2 = streamTail2.tail();
                streamTail = streamTail.tail();
            }
            return streamTail2.isEmpty() && streamTail.isEmpty();
        }

        public int hashCode() {
            return Collections.hash(this);
        }

        @Override // javaslang.Value
        public String toString() {
            StringBuilder sbAppend = new StringBuilder(stringPrefix()).append("(");
            Stream streamTail = this;
            while (streamTail != null && !streamTail.isEmpty()) {
                Cons cons = (Cons) streamTail;
                sbAppend.append(cons.head);
                if (cons.tail.isEvaluated()) {
                    streamTail = streamTail.tail();
                    if (!streamTail.isEmpty()) {
                        sbAppend.append(", ");
                    }
                } else {
                    sbAppend.append(", ?");
                    streamTail = null;
                }
            }
            return sbAppend.append(Constant.AFTER_QUTO).toString();
        }
    }
}
