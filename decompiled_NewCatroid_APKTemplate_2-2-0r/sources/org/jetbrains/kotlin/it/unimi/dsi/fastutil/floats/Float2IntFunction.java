package org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats;

import java.util.function.DoubleToIntFunction;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.SafeMath;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface Float2IntFunction extends Function<Float, Integer>, DoubleToIntFunction {
    default boolean containsKey(float f) {
        return true;
    }

    default int defaultReturnValue() {
        return 0;
    }

    int get(float f);

    @Override // java.util.function.DoubleToIntFunction
    @Deprecated
    default int applyAsInt(double d) {
        return get(SafeMath.safeDoubleToFloat(d));
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    @Deprecated
    default Integer get(Object obj) {
        if (obj == null) {
            return null;
        }
        float fFloatValue = ((Float) obj).floatValue();
        int i = get(fFloatValue);
        if (i != defaultReturnValue() || containsKey(fFloatValue)) {
            return Integer.valueOf(i);
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
    default <T> java.util.function.Function<T, Integer> compose(java.util.function.Function<? super T, ? extends Float> function) {
        return super.compose(function);
    }

    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<Float, T> andThen(java.util.function.Function<? super Integer, ? extends T> function) {
        return super.andThen(function);
    }
}
