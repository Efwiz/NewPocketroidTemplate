package org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs;

import java.util.function.LongToIntFunction;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface Long2ByteFunction extends Function<Long, Byte>, LongToIntFunction {
    default boolean containsKey(long j) {
        return true;
    }

    default byte defaultReturnValue() {
        return (byte) 0;
    }

    byte get(long j);

    @Override // java.util.function.LongToIntFunction
    default int applyAsInt(long j) {
        return get(j);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    @Deprecated
    default Byte get(Object obj) {
        if (obj == null) {
            return null;
        }
        long jLongValue = ((Long) obj).longValue();
        byte b = get(jLongValue);
        if (b != defaultReturnValue() || containsKey(jLongValue)) {
            return Byte.valueOf(b);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    @Deprecated
    default boolean containsKey(Object obj) {
        if (obj == null) {
            return false;
        }
        return containsKey(((Long) obj).longValue());
    }

    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<T, Byte> compose(java.util.function.Function<? super T, ? extends Long> function) {
        return super.compose(function);
    }

    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<Long, T> andThen(java.util.function.Function<? super Byte, ? extends T> function) {
        return super.andThen(function);
    }
}
