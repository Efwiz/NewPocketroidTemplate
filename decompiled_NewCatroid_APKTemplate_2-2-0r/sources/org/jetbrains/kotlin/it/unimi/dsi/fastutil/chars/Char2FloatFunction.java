package org.jetbrains.kotlin.it.unimi.dsi.fastutil.chars;

import java.util.function.IntToDoubleFunction;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.SafeMath;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface Char2FloatFunction extends Function<Character, Float>, IntToDoubleFunction {
    default boolean containsKey(char c) {
        return true;
    }

    default float defaultReturnValue() {
        return 0.0f;
    }

    float get(char c);

    @Override // java.util.function.IntToDoubleFunction
    @Deprecated
    default double applyAsDouble(int i) {
        return get(SafeMath.safeIntToChar(i));
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    @Deprecated
    default Float get(Object obj) {
        if (obj == null) {
            return null;
        }
        char cCharValue = ((Character) obj).charValue();
        float f = get(cCharValue);
        if (f != defaultReturnValue() || containsKey(cCharValue)) {
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
        return containsKey(((Character) obj).charValue());
    }

    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<T, Float> compose(java.util.function.Function<? super T, ? extends Character> function) {
        return super.compose(function);
    }

    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<Character, T> andThen(java.util.function.Function<? super Float, ? extends T> function) {
        return super.andThen(function);
    }
}
