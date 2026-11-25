package org.jetbrains.kotlin.it.unimi.dsi.fastutil.chars;

import java.util.function.IntFunction;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.SafeMath;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface Char2ReferenceFunction<V> extends Function<Character, V>, IntFunction<V> {
    default boolean containsKey(char c) {
        return true;
    }

    default V defaultReturnValue() {
        return null;
    }

    V get(char c);

    @Override // java.util.function.IntFunction
    @Deprecated
    default V apply(int i) {
        return get(SafeMath.safeIntToChar(i));
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    @Deprecated
    default V get(Object obj) {
        if (obj == null) {
            return null;
        }
        char cCharValue = ((Character) obj).charValue();
        V v = get(cCharValue);
        if (v != defaultReturnValue() || containsKey(cCharValue)) {
            return v;
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

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<T, V> compose(java.util.function.Function<? super T, ? extends Character> function) {
        return super.compose(function);
    }
}
