package javaslang.control;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.Consumer;
import javaslang.Value;
import javaslang.collection.Iterator;

/* JADX INFO: loaded from: classes11.dex */
public interface Validation<E, T> extends Serializable, Value<T> {
    public static final long serialVersionUID = 1;

    @Override // javaslang.Value
    T get();

    boolean isInvalid();

    @Override // javaslang.Value
    default boolean isSingleValued() {
        return true;
    }

    boolean isValid();

    @Override // javaslang.Value
    String toString();

    @Override // javaslang.Value
    default boolean isEmpty() {
        return isInvalid();
    }

    @Override // javaslang.Value, java.lang.Iterable
    default void forEach(Consumer<? super T> consumer) {
        Objects.requireNonNull(consumer, "action is null");
        if (isValid()) {
            consumer.accept(get());
        }
    }

    @Override // javaslang.Value, java.lang.Iterable
    default Iterator<T> iterator() {
        return isValid() ? Iterator.of(get()) : Iterator.empty();
    }
}
