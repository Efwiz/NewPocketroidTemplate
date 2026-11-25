package javaslang.collection;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import javaslang.Value;
import javaslang.control.Option;

/* JADX INFO: loaded from: classes11.dex */
public interface Traversable<T> extends Value<T>, Foldable<T> {
    T head();

    @Override // javaslang.Value
    default boolean isSingleValued() {
        return false;
    }

    boolean isTraversableAgain();

    int length();

    Traversable<T> tail();

    default Option<T> find(Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate, "predicate is null");
        Iterator<T> it = iterator();
        while (it.hasNext()) {
            T next = it.next();
            if (predicate.test(next)) {
                return Option.some(next);
            }
        }
        return Option.none();
    }

    default <U> U foldLeft(U u, BiFunction<? super U, ? super T, ? extends U> biFunction) {
        Objects.requireNonNull(biFunction, "f is null");
        Iterator<T> it = iterator();
        while (it.hasNext()) {
            u = biFunction.apply(u, it.next());
        }
        return u;
    }

    @Override // javaslang.Value
    default T get() {
        return iterator().next();
    }

    @Override // javaslang.Value
    default boolean isEmpty() {
        return length() == 0;
    }

    @Override // javaslang.Value, java.lang.Iterable
    default Iterator<T> iterator() {
        return new AbstractIterator<T>(this) { // from class: javaslang.collection.Traversable.1
            Traversable<T> traversable;
            final /* synthetic */ Traversable val$that;

            {
                this.val$that = this;
                this.traversable = this;
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                return !this.traversable.isEmpty();
            }

            @Override // javaslang.collection.AbstractIterator
            public T getNext() {
                T tHead = this.traversable.head();
                this.traversable = this.traversable.tail();
                return tHead;
            }
        };
    }

    default T last() {
        if (isEmpty()) {
            throw new NoSuchElementException("last of empty Traversable");
        }
        Iterator<T> it = iterator();
        T next = null;
        while (it.hasNext()) {
            next = it.next();
        }
        return next;
    }

    default Option<T> max() {
        Traversable<T> stream = isTraversableAgain() ? this : toStream();
        if (isEmpty()) {
            return Option.none();
        }
        return stream.maxBy(Comparators.naturalComparator());
    }

    default Option<T> maxBy(final Comparator<? super T> comparator) {
        Objects.requireNonNull(comparator, "comparator is null");
        if (isEmpty()) {
            return Option.none();
        }
        return Option.some(reduce(new BiFunction() { // from class: javaslang.collection.Traversable$$ExternalSyntheticLambda2
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return Traversable.lambda$maxBy$1(comparator, obj, obj2);
            }
        }));
    }

    static /* synthetic */ Object lambda$maxBy$1(Comparator comparator, Object obj, Object obj2) {
        return comparator.compare(obj, obj2) >= 0 ? obj : obj2;
    }

    default String mkString(CharSequence charSequence) {
        return mkString("", charSequence, "");
    }

    default String mkString(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3) {
        final StringBuilder sb = new StringBuilder(charSequence);
        iterator().map(new Function() { // from class: javaslang.collection.Traversable$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return String.valueOf(obj);
            }
        }).intersperse(String.valueOf(charSequence2)).forEach(new Consumer() { // from class: javaslang.collection.Traversable$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                sb.append((String) obj);
            }
        });
        return sb.append(charSequence3).toString();
    }

    /* JADX WARN: Multi-variable type inference failed */
    default T reduceLeft(BiFunction<? super T, ? super T, ? extends T> biFunction) {
        Objects.requireNonNull(biFunction, "op is null");
        if (isEmpty()) {
            throw new NoSuchElementException("reduceLeft on Nil");
        }
        return (T) tail().foldLeft(head(), biFunction);
    }

    default int size() {
        return length();
    }
}
