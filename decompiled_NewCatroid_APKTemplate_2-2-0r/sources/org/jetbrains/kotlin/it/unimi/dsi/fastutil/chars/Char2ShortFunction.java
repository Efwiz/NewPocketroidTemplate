package org.jetbrains.kotlin.it.unimi.dsi.fastutil.chars;

import java.util.function.IntUnaryOperator;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.SafeMath;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface Char2ShortFunction extends Function<Character, Short>, IntUnaryOperator {
    default boolean containsKey(char c) {
        return true;
    }

    default short defaultReturnValue() {
        return (short) 0;
    }

    short get(char c);

    @Override // java.util.function.IntUnaryOperator
    @Deprecated
    default int applyAsInt(int i) {
        return get(SafeMath.safeIntToChar(i));
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    @Deprecated
    default Short get(Object obj) {
        if (obj == null) {
            return null;
        }
        char cCharValue = ((Character) obj).charValue();
        short s = get(cCharValue);
        if (s != defaultReturnValue() || containsKey(cCharValue)) {
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
        return containsKey(((Character) obj).charValue());
    }

    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<T, Short> compose(java.util.function.Function<? super T, ? extends Character> function) {
        return super.compose(function);
    }

    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<Character, T> andThen(java.util.function.Function<? super Short, ? extends T> function) {
        return super.andThen(function);
    }
}
