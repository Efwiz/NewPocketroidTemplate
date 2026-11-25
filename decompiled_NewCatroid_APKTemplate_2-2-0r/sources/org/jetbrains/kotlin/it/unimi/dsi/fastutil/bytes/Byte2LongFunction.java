package org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes;

import java.util.function.IntToLongFunction;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.SafeMath;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface Byte2LongFunction extends Function<Byte, Long>, IntToLongFunction {
    default boolean containsKey(byte b) {
        return true;
    }

    default long defaultReturnValue() {
        return 0L;
    }

    long get(byte b);

    @Override // java.util.function.IntToLongFunction
    @Deprecated
    default long applyAsLong(int i) {
        return get(SafeMath.safeIntToByte(i));
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    @Deprecated
    default Long get(Object obj) {
        if (obj == null) {
            return null;
        }
        byte bByteValue = ((Byte) obj).byteValue();
        long j = get(bByteValue);
        if (j != defaultReturnValue() || containsKey(bByteValue)) {
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
        return containsKey(((Byte) obj).byteValue());
    }

    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<T, Long> compose(java.util.function.Function<? super T, ? extends Byte> function) {
        return super.compose(function);
    }

    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<Byte, T> andThen(java.util.function.Function<? super Long, ? extends T> function) {
        return super.andThen(function);
    }
}
