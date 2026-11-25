package org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs;

import java.util.function.LongFunction;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface Long2ReferenceFunction<V> extends Function<Long, V>, LongFunction<V> {
    default boolean containsKey(long j) {
        return true;
    }

    default V defaultReturnValue() {
        return null;
    }

    V get(long j);

    @Override // java.util.function.LongFunction
    default V apply(long j) {
        return get(j);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    @Deprecated
    default V get(Object obj) {
        if (obj == null) {
            return null;
        }
        long jLongValue = ((Long) obj).longValue();
        V v = get(jLongValue);
        if (v != defaultReturnValue() || containsKey(jLongValue)) {
            return v;
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    @Deprecated
    default boolean containsKey(Object obj) {
        if (obj == null) {
            return false;
        }
        return containsKey(((Long) obj).longValue());
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<T, V> compose(java.util.function.Function<? super T, ? extends Long> function) {
        return super.compose(function);
    }
}
