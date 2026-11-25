package org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts;

import java.util.function.IntToLongFunction;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.SafeMath;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface Short2LongFunction extends Function<Short, Long>, IntToLongFunction {
    default boolean containsKey(short s) {
        return true;
    }

    default long defaultReturnValue() {
        return 0L;
    }

    long get(short s);

    @Override // java.util.function.IntToLongFunction
    @Deprecated
    default long applyAsLong(int i) {
        return get(SafeMath.safeIntToShort(i));
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    @Deprecated
    default Long get(Object obj) {
        if (obj == null) {
            return null;
        }
        short sShortValue = ((Short) obj).shortValue();
        long j = get(sShortValue);
        if (j != defaultReturnValue() || containsKey(sShortValue)) {
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
        return containsKey(((Short) obj).shortValue());
    }

    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<T, Long> compose(java.util.function.Function<? super T, ? extends Short> function) {
        return super.compose(function);
    }

    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<Short, T> andThen(java.util.function.Function<? super Long, ? extends T> function) {
        return super.andThen(function);
    }
}
