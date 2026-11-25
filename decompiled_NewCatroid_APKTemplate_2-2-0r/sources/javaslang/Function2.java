package javaslang;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

/* JADX INFO: loaded from: classes11.dex */
@FunctionalInterface
public interface Function2<T1, T2, R> extends BiFunction<T1, T2, R>, InterfaceC0221<R> {
    public static final long serialVersionUID = 1;

    @Override // java.util.function.BiFunction
    R apply(T1 t1, T2 t2);

    @Override // java.util.function.BiFunction
    default <V> Function2<T1, T2, V> andThen(Function<? super R, ? extends V> function) {
        Objects.requireNonNull(function, "after is null");
        return new Function2$$ExternalSyntheticLambda0(this, function);
    }
}
