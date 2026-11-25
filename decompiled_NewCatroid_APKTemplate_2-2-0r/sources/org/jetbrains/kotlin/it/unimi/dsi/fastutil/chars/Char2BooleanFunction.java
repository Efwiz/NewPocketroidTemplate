package org.jetbrains.kotlin.it.unimi.dsi.fastutil.chars;

import java.util.function.IntPredicate;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.SafeMath;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface Char2BooleanFunction extends Function<Character, Boolean>, IntPredicate {
    default boolean containsKey(char c) {
        return true;
    }

    default boolean defaultReturnValue() {
        return false;
    }

    boolean get(char c);

    @Override // java.util.function.IntPredicate
    @Deprecated
    default boolean test(int i) {
        return get(SafeMath.safeIntToChar(i));
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    @Deprecated
    default Boolean get(Object obj) {
        if (obj == null) {
            return null;
        }
        char cCharValue = ((Character) obj).charValue();
        boolean z = get(cCharValue);
        if (z != defaultReturnValue() || containsKey(cCharValue)) {
            return Boolean.valueOf(z);
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
    default <T> java.util.function.Function<T, Boolean> compose(java.util.function.Function<? super T, ? extends Character> function) {
        return super.compose(function);
    }

    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<Character, T> andThen(java.util.function.Function<? super Boolean, ? extends T> function) {
        return super.andThen(function);
    }
}
