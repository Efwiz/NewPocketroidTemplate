package org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs;

import java.util.function.LongToIntFunction;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface Long2ShortFunction extends Function<Long, Short>, LongToIntFunction {
    default boolean containsKey(long j) {
        return true;
    }

    default short defaultReturnValue() {
        return (short) 0;
    }

    short get(long j);

    @Override // java.util.function.LongToIntFunction
    default int applyAsInt(long j) {
        return get(j);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    @Deprecated
    default Short get(Object obj) {
        if (obj == null) {
            return null;
        }
        long jLongValue = ((Long) obj).longValue();
        short s = get(jLongValue);
        if (s != defaultReturnValue() || containsKey(jLongValue)) {
            return Short.valueOf(s);
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

    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<T, Short> compose(java.util.function.Function<? super T, ? extends Long> function) {
        return super.compose(function);
    }

    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<Long, T> andThen(java.util.function.Function<? super Short, ? extends T> function) {
        return super.andThen(function);
    }
}
