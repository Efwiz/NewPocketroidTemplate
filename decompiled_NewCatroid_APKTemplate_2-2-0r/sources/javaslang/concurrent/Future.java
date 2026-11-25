package javaslang.concurrent;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import javaslang.Value;
import javaslang.collection.Iterator;
import javaslang.control.Option;
import javaslang.control.Try;

/* JADX INFO: loaded from: classes11.dex */
public interface Future<T> extends Value<T> {
    public static final ExecutorService DEFAULT_EXECUTOR_SERVICE = Executors.newCachedThreadPool();

    void await();

    Option<Try<T>> getValue();

    boolean isCompleted();

    @Override // javaslang.Value
    default boolean isSingleValued() {
        return true;
    }

    Future<T> onComplete(Consumer<? super Try<T>> consumer);

    @Override // javaslang.Value, java.lang.Iterable
    default void forEach(final Consumer<? super T> consumer) {
        Objects.requireNonNull(consumer, "action is null");
        onComplete(new Consumer() { // from class: javaslang.concurrent.Future$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((Try) obj).forEach(consumer);
            }
        });
    }

    @Override // javaslang.Value
    default T get() {
        if (isEmpty()) {
            throw new NoSuchElementException("get on failed future");
        }
        return getValue().get().get();
    }

    @Override // javaslang.Value
    default boolean isEmpty() {
        if (!isCompleted()) {
            await();
        }
        return getValue().get().isEmpty();
    }

    @Override // javaslang.Value, java.lang.Iterable
    default Iterator<T> iterator() {
        return isEmpty() ? Iterator.empty() : Iterator.of(get());
    }

    @Override // javaslang.Value
    default String stringPrefix() {
        return "Future";
    }
}
