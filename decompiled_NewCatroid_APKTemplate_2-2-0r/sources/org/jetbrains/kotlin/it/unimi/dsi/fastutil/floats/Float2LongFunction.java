package org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats;

import java.util.function.DoubleToLongFunction;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.SafeMath;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface Float2LongFunction extends Function<Float, Long>, DoubleToLongFunction {
    default boolean containsKey(float f) {
        return true;
    }

    default long defaultReturnValue() {
        return 0L;
    }

    long get(float f);

    @Override // java.util.function.DoubleToLongFunction
    @Deprecated
    default long applyAsLong(double d) {
        return get(SafeMath.safeDoubleToFloat(d));
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    @Deprecated
    default Long get(Object obj) {
        if (obj == null) {
            return null;
        }
        float fFloatValue = ((Float) obj).floatValue();
        long j = get(fFloatValue);
        if (j != defaultReturnValue() || containsKey(fFloatValue)) {
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
        return containsKey(((Float) obj).floatValue());
    }

    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<T, Long> compose(java.util.function.Function<? super T, ? extends Float> function) {
        return super.compose(function);
    }

    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<Float, T> andThen(java.util.function.Function<? super Long, ? extends T> function) {
        return super.andThen(function);
    }
}
