package org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects;

import java.util.function.ToLongFunction;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface Reference2LongFunction<K> extends Function<K, Long>, ToLongFunction<K> {
    default long defaultReturnValue() {
        return 0L;
    }

    long getLong(Object obj);

    @Override // java.util.function.ToLongFunction
    default long applyAsLong(K k) {
        return getLong(k);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    @Deprecated
    default Long get(Object obj) {
        long j = getLong(obj);
        if (j != defaultReturnValue() || containsKey(obj)) {
            return Long.valueOf(j);
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<K, T> andThen(java.util.function.Function<? super Long, ? extends T> function) {
        return super.andThen(function);
    }
}
