package org.jetbrains.kotlin.it.unimi.dsi.fastutil.chars;

import java.util.function.IntUnaryOperator;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.SafeMath;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface Char2CharFunction extends Function<Character, Character>, IntUnaryOperator {
    default boolean containsKey(char c) {
        return true;
    }

    default char defaultReturnValue() {
        return (char) 0;
    }

    char get(char c);

    @Override // java.util.function.IntUnaryOperator
    @Deprecated
    default int applyAsInt(int i) {
        return get(SafeMath.safeIntToChar(i));
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    @Deprecated
    default Character get(Object obj) {
        if (obj == null) {
            return null;
        }
        char cCharValue = ((Character) obj).charValue();
        char c = get(cCharValue);
        if (c != defaultReturnValue() || containsKey(cCharValue)) {
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
        return containsKey(((Character) obj).charValue());
    }

    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<T, Character> compose(java.util.function.Function<? super T, ? extends Character> function) {
        return super.compose(function);
    }

    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<Character, T> andThen(java.util.function.Function<? super Character, ? extends T> function) {
        return super.andThen(function);
    }
}
