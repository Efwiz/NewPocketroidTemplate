package javaslang.collection;

import java.util.Objects;
import java.util.function.BiFunction;

/* JADX INFO: loaded from: classes11.dex */
public interface Foldable<T> {
    T reduceLeft(BiFunction<? super T, ? super T, ? extends T> biFunction);

    default T reduce(BiFunction<? super T, ? super T, ? extends T> biFunction) {
        Objects.requireNonNull(biFunction, "op is null");
        return reduceLeft(biFunction);
    }
}
