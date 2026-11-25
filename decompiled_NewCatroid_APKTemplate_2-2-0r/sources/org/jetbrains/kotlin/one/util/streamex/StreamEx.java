package org.jetbrains.kotlin.one.util.streamex;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

/* JADX INFO: loaded from: classes2.dex */
public class StreamEx<T> extends AbstractStreamEx<T, StreamEx<T>> {
    StreamEx(Stream<? extends T> stream, StreamContext streamContext) {
        super(stream, streamContext);
    }

    StreamEx(Spliterator<? extends T> spliterator, StreamContext streamContext) {
        super(spliterator, streamContext);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // org.jetbrains.kotlin.one.util.streamex.AbstractStreamEx
    public StreamEx<T> supply(Stream<T> stream) {
        return new StreamEx<>(stream, this.context);
    }

    public <TT> StreamEx<TT> select(final Class<TT> cls) {
        Objects.requireNonNull(cls);
        return (StreamEx) filter(new Predicate() { // from class: org.jetbrains.kotlin.one.util.streamex.StreamEx$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return cls.isInstance(obj);
            }
        });
    }

    public <A> A[] toArray(final A[] aArr) {
        if (aArr.length != 0) {
            throw new IllegalArgumentException("Empty array must be supplied");
        }
        return (A[]) stream().toArray(new IntFunction() { // from class: org.jetbrains.kotlin.one.util.streamex.StreamEx$$ExternalSyntheticLambda0
            @Override // java.util.function.IntFunction
            public final Object apply(int i) {
                return StreamEx.lambda$toArray$14(aArr, i);
            }
        });
    }

    static /* synthetic */ Object[] lambda$toArray$14(Object[] objArr, int i) {
        return i == 0 ? objArr : (Object[]) Array.newInstance(objArr.getClass().getComponentType(), i);
    }

    @SafeVarargs
    public static <T> StreamEx<T> of(T... tArr) {
        return of(Arrays.spliterator(tArr));
    }

    public static <T> StreamEx<T> of(Collection<? extends T> collection) {
        return of((Spliterator) collection.spliterator());
    }

    public static <T> StreamEx<T> of(Spliterator<? extends T> spliterator) {
        return new StreamEx<>(spliterator, StreamContext.SEQUENTIAL);
    }

    public static <T> StreamEx<T> iterate(T t, UnaryOperator<T> unaryOperator) {
        return iterate((Object) t, Internals.alwaysTrue(), (UnaryOperator) unaryOperator);
    }

    public static <T> StreamEx<T> iterate(final T t, final Predicate<? super T> predicate, final UnaryOperator<T> unaryOperator) {
        Objects.requireNonNull(unaryOperator);
        Objects.requireNonNull(predicate);
        return of((Spliterator) new Spliterators.AbstractSpliterator<T>(Long.MAX_VALUE, 1040) { // from class: org.jetbrains.kotlin.one.util.streamex.StreamEx.2
            boolean finished;
            T prev;
            boolean started;

            @Override // java.util.Spliterator
            public boolean tryAdvance(Consumer<? super T> consumer) {
                T t2;
                Objects.requireNonNull(consumer);
                if (this.finished) {
                    return false;
                }
                if (this.started) {
                    t2 = (T) unaryOperator.apply(this.prev);
                } else {
                    t2 = (T) t;
                    this.started = true;
                }
                if (!predicate.test(t2)) {
                    this.prev = null;
                    this.finished = true;
                    return false;
                }
                this.prev = t2;
                consumer.accept(t2);
                return true;
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Spliterator
            public void forEachRemaining(Consumer<? super T> consumer) {
                Objects.requireNonNull(consumer);
                if (this.finished) {
                    return;
                }
                this.finished = true;
                Object objApply = this.started ? unaryOperator.apply(this.prev) : t;
                this.prev = null;
                while (predicate.test(objApply)) {
                    consumer.accept(objApply);
                    objApply = unaryOperator.apply(objApply);
                }
            }
        });
    }
}
