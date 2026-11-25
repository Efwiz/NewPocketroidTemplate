package org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats;

import java.util.function.DoubleToIntFunction;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.SafeMath;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface Float2CharFunction extends Function<Float, Character>, DoubleToIntFunction {
    default boolean containsKey(float f) {
        return true;
    }

    default char defaultReturnValue() {
        return (char) 0;
    }

    char get(float f);

    @Override // java.util.function.DoubleToIntFunction
    @Deprecated
    default int applyAsInt(double d) {
        return get(SafeMath.safeDoubleToFloat(d));
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    @Deprecated
    default Character get(Object obj) {
        if (obj == null) {
            return null;
        }
        float fFloatValue = ((Float) obj).floatValue();
        char c = get(fFloatValue);
        if (c != defaultReturnValue() || containsKey(fFloatValue)) {
            return Character.valueOf(c);
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
    default <T> java.util.function.Function<T, Character> compose(java.util.function.Function<? super T, ? extends Float> function) {
        return super.compose(function);
    }

    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<Float, T> andThen(java.util.function.Function<? super Character, ? extends T> function) {
        return super.andThen(function);
    }
}
