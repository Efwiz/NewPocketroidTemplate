package org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs;

import java.util.function.LongToDoubleFunction;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface Long2FloatFunction extends Function<Long, Float>, LongToDoubleFunction {
    default boolean containsKey(long j) {
        return true;
    }

    default float defaultReturnValue() {
        return 0.0f;
    }

    float get(long j);

    @Override // java.util.function.LongToDoubleFunction
    default double applyAsDouble(long j) {
        return get(j);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    @Deprecated
    default Float get(Object obj) {
        if (obj == null) {
            return null;
        }
        long jLongValue = ((Long) obj).longValue();
        float f = get(jLongValue);
        if (f != defaultReturnValue() || containsKey(jLongValue)) {
            return Float.valueOf(f);
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
    default <T> java.util.function.Function<T, Float> compose(java.util.function.Function<? super T, ? extends Long> function) {
        return super.compose(function);
    }

    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<Long, T> andThen(java.util.function.Function<? super Float, ? extends T> function) {
        return super.andThen(function);
    }
}
