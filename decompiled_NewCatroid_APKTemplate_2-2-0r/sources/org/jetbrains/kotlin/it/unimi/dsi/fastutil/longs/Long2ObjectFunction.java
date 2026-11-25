package org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs;

import java.util.function.LongFunction;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface Long2ObjectFunction<V> extends Function<Long, V>, LongFunction<V> {
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

    default V put(long j, V v) {
        throw new UnsupportedOperationException();
    }

    default V remove(long j) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    default V put(Long l, V v) {
        long jLongValue = l.longValue();
        boolean zContainsKey = containsKey(jLongValue);
        V vPut = put(jLongValue, v);
        if (zContainsKey) {
            return vPut;
        }
        return null;
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

    @Deprecated
    default V remove(Object obj) {
        if (obj == null) {
            return null;
        }
        long jLongValue = ((Long) obj).longValue();
        if (containsKey(jLongValue)) {
            return remove(jLongValue);
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
