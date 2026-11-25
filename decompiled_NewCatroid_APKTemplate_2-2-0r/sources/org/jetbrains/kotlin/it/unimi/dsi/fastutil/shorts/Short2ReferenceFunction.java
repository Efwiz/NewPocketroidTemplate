package org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts;

import java.util.function.IntFunction;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.SafeMath;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface Short2ReferenceFunction<V> extends Function<Short, V>, IntFunction<V> {
    default boolean containsKey(short s) {
        return true;
    }

    default V defaultReturnValue() {
        return null;
    }

    V get(short s);

    @Override // java.util.function.IntFunction
    @Deprecated
    default V apply(int i) {
        return get(SafeMath.safeIntToShort(i));
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    @Deprecated
    default V get(Object obj) {
        if (obj == null) {
            return null;
        }
        short sShortValue = ((Short) obj).shortValue();
        V v = get(sShortValue);
        if (v != defaultReturnValue() || containsKey(sShortValue)) {
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
        return containsKey(((Short) obj).shortValue());
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<T, V> compose(java.util.function.Function<? super T, ? extends Short> function) {
        return super.compose(function);
    }
}
