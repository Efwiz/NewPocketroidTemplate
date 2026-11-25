package javaslang;

import java.util.Objects;
import java.util.function.Function;

/* JADX INFO: loaded from: classes11.dex */
@FunctionalInterface
public interface Function1<T1, R> extends Function<T1, R>, InterfaceC0221<R> {
    public static final long serialVersionUID = 1;

    @Override // java.util.function.Function
    R apply(T1 t1);

    @Override // java.util.function.Function
    default <V> Function1<T1, V> andThen(Function<? super R, ? extends V> function) {
        Objects.requireNonNull(function, "after is null");
        return new Function1$$ExternalSyntheticLambda0(this, function);
    }

    @Override // java.util.function.Function
    default <V> Function1<V, R> compose(Function<? super V, ? extends T1> function) {
        Objects.requireNonNull(function, "before is null");
        return new Function1$$ExternalSyntheticLambda1(this, function);
    }
}
