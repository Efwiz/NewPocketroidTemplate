package org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts;

import java.util.function.IntUnaryOperator;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.SafeMath;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface Short2ShortFunction extends Function<Short, Short>, IntUnaryOperator {
    default boolean containsKey(short s) {
        return true;
    }

    default short defaultReturnValue() {
        return (short) 0;
    }

    short get(short s);

    @Override // java.util.function.IntUnaryOperator
    @Deprecated
    default int applyAsInt(int i) {
        return get(SafeMath.safeIntToShort(i));
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    @Deprecated
    default Short get(Object obj) {
        if (obj == null) {
            return null;
        }
        short sShortValue = ((Short) obj).shortValue();
        short s = get(sShortValue);
        if (s != defaultReturnValue() || containsKey(sShortValue)) {
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
        return containsKey(((Short) obj).shortValue());
    }

    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<T, Short> compose(java.util.function.Function<? super T, ? extends Short> function) {
        return super.compose(function);
    }

    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<Short, T> andThen(java.util.function.Function<? super Short, ? extends T> function) {
        return super.andThen(function);
    }
}
