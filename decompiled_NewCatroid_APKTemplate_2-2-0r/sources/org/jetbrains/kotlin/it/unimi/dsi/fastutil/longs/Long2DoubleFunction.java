package org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs;

import java.util.function.LongToDoubleFunction;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface Long2DoubleFunction extends Function<Long, Double>, LongToDoubleFunction {
    default boolean containsKey(long j) {
        return true;
    }

    default double defaultReturnValue() {
        return 0.0d;
    }

    double get(long j);

    @Override // java.util.function.LongToDoubleFunction
    default double applyAsDouble(long j) {
        return get(j);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    @Deprecated
    default Double get(Object obj) {
        if (obj == null) {
            return null;
        }
        long jLongValue = ((Long) obj).longValue();
        double d = get(jLongValue);
        if (d != defaultReturnValue() || containsKey(jLongValue)) {
            return Double.valueOf(d);
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
    default <T> java.util.function.Function<T, Double> compose(java.util.function.Function<? super T, ? extends Long> function) {
        return super.compose(function);
    }

    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<Long, T> andThen(java.util.function.Function<? super Double, ? extends T> function) {
        return super.andThen(function);
    }
}
