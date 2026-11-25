package org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes;

import java.util.function.IntUnaryOperator;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.SafeMath;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface Byte2IntFunction extends Function<Byte, Integer>, IntUnaryOperator {
    default boolean containsKey(byte b) {
        return true;
    }

    default int defaultReturnValue() {
        return 0;
    }

    int get(byte b);

    @Override // java.util.function.IntUnaryOperator
    @Deprecated
    default int applyAsInt(int i) {
        return get(SafeMath.safeIntToByte(i));
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    @Deprecated
    default Integer get(Object obj) {
        if (obj == null) {
            return null;
        }
        byte bByteValue = ((Byte) obj).byteValue();
        int i = get(bByteValue);
        if (i != defaultReturnValue() || containsKey(bByteValue)) {
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
        return containsKey(((Byte) obj).byteValue());
    }

    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<T, Integer> compose(java.util.function.Function<? super T, ? extends Byte> function) {
        return super.compose(function);
    }

    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<Byte, T> andThen(java.util.function.Function<? super Integer, ? extends T> function) {
        return super.andThen(function);
    }
}
