package org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles;

import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface Double2FloatFunction extends Function<Double, Float>, java.util.function.DoubleUnaryOperator {
    default boolean containsKey(double d) {
        return true;
    }

    default float defaultReturnValue() {
        return 0.0f;
    }

    float get(double d);

    @Override // java.util.function.DoubleUnaryOperator
    default double applyAsDouble(double d) {
        return get(d);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    @Deprecated
    default Float get(Object obj) {
        if (obj == null) {
            return null;
        }
        double dDoubleValue = ((Double) obj).doubleValue();
        float f = get(dDoubleValue);
        if (f != defaultReturnValue() || containsKey(dDoubleValue)) {
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
        return containsKey(((Double) obj).doubleValue());
    }

    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<T, Float> compose(java.util.function.Function<? super T, ? extends Double> function) {
        return super.compose(function);
    }

    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<Double, T> andThen(java.util.function.Function<? super Float, ? extends T> function) {
        return super.andThen(function);
    }
}
