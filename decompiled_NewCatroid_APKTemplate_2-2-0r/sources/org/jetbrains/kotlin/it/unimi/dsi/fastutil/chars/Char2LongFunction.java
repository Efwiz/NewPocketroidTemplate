package org.jetbrains.kotlin.it.unimi.dsi.fastutil.chars;

import java.util.function.IntToLongFunction;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.SafeMath;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface Char2LongFunction extends Function<Character, Long>, IntToLongFunction {
    default boolean containsKey(char c) {
        return true;
    }

    default long defaultReturnValue() {
        return 0L;
    }

    long get(char c);

    @Override // java.util.function.IntToLongFunction
    @Deprecated
    default long applyAsLong(int i) {
        return get(SafeMath.safeIntToChar(i));
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    @Deprecated
    default Long get(Object obj) {
        if (obj == null) {
            return null;
        }
        char cCharValue = ((Character) obj).charValue();
        long j = get(cCharValue);
        if (j != defaultReturnValue() || containsKey(cCharValue)) {
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
        return containsKey(((Character) obj).charValue());
    }

    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<T, Long> compose(java.util.function.Function<? super T, ? extends Character> function) {
        return super.compose(function);
    }

    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<Character, T> andThen(java.util.function.Function<? super Long, ? extends T> function) {
        return super.andThen(function);
    }
}
