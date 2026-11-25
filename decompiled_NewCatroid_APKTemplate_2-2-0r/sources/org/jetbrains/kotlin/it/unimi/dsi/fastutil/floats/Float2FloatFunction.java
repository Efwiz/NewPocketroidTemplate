package org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats;

import java.util.function.DoubleUnaryOperator;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.SafeMath;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface Float2FloatFunction extends Function<Float, Float>, DoubleUnaryOperator {
    default boolean containsKey(float f) {
        return true;
    }

    default float defaultReturnValue() {
        return 0.0f;
    }

    float get(float f);

    @Override // java.util.function.DoubleUnaryOperator
    @Deprecated
    default double applyAsDouble(double d) {
        return get(SafeMath.safeDoubleToFloat(d));
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    @Deprecated
    default Float get(Object obj) {
        if (obj == null) {
            return null;
        }
        float fFloatValue = ((Float) obj).floatValue();
        float f = get(fFloatValue);
        if (f != defaultReturnValue() || containsKey(fFloatValue)) {
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
        return containsKey(((Float) obj).floatValue());
    }

    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<T, Float> compose(java.util.function.Function<? super T, ? extends Float> function) {
        return super.compose(function);
    }

    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<Float, T> andThen(java.util.function.Function<? super Float, ? extends T> function) {
        return super.andThen(function);
    }
}
