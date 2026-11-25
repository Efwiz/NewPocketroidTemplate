package org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts;

import java.util.function.IntUnaryOperator;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.SafeMath;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface Short2IntFunction extends Function<Short, Integer>, IntUnaryOperator {
    default boolean containsKey(short s) {
        return true;
    }

    default int defaultReturnValue() {
        return 0;
    }

    int get(short s);

    @Override // java.util.function.IntUnaryOperator
    @Deprecated
    default int applyAsInt(int i) {
        return get(SafeMath.safeIntToShort(i));
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    @Deprecated
    default Integer get(Object obj) {
        if (obj == null) {
            return null;
        }
        short sShortValue = ((Short) obj).shortValue();
        int i = get(sShortValue);
        if (i != defaultReturnValue() || containsKey(sShortValue)) {
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
        return containsKey(((Short) obj).shortValue());
    }

    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<T, Integer> compose(java.util.function.Function<? super T, ? extends Short> function) {
        return super.compose(function);
    }

    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<Short, T> andThen(java.util.function.Function<? super Integer, ? extends T> function) {
        return super.andThen(function);
    }
}
