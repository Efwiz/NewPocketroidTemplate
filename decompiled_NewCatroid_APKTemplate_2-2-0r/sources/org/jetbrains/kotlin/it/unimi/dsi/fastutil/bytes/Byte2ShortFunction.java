package org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes;

import java.util.function.IntUnaryOperator;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.SafeMath;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface Byte2ShortFunction extends Function<Byte, Short>, IntUnaryOperator {
    default boolean containsKey(byte b) {
        return true;
    }

    default short defaultReturnValue() {
        return (short) 0;
    }

    short get(byte b);

    @Override // java.util.function.IntUnaryOperator
    @Deprecated
    default int applyAsInt(int i) {
        return get(SafeMath.safeIntToByte(i));
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    @Deprecated
    default Short get(Object obj) {
        if (obj == null) {
            return null;
        }
        byte bByteValue = ((Byte) obj).byteValue();
        short s = get(bByteValue);
        if (s != defaultReturnValue() || containsKey(bByteValue)) {
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
        return containsKey(((Byte) obj).byteValue());
    }

    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<T, Short> compose(java.util.function.Function<? super T, ? extends Byte> function) {
        return super.compose(function);
    }

    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<Byte, T> andThen(java.util.function.Function<? super Short, ? extends T> function) {
        return super.andThen(function);
    }
}
