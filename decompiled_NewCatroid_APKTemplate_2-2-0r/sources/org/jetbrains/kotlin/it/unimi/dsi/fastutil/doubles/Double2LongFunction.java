package org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles;

import java.util.function.DoubleToLongFunction;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface Double2LongFunction extends Function<Double, Long>, DoubleToLongFunction {
    default boolean containsKey(double d) {
        return true;
    }

    default long defaultReturnValue() {
        return 0L;
    }

    long get(double d);

    @Override // java.util.function.DoubleToLongFunction
    default long applyAsLong(double d) {
        return get(d);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    @Deprecated
    default Long get(Object obj) {
        if (obj == null) {
            return null;
        }
        double dDoubleValue = ((Double) obj).doubleValue();
        long j = get(dDoubleValue);
        if (j != defaultReturnValue() || containsKey(dDoubleValue)) {
            return Long.valueOf(j);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    @Deprecated
    default boolean containsKey(Object obj) {
        if (obj == null) {
            return false;
        }
        return containsKey(((Double) obj).doubleValue());
    }

    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<T, Long> compose(java.util.function.Function<? super T, ? extends Double> function) {
        return super.compose(function);
    }

    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<Double, T> andThen(java.util.function.Function<? super Long, ? extends T> function) {
        return super.andThen(function);
    }
}
