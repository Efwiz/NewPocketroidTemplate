package org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes;

import java.util.function.IntToDoubleFunction;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.SafeMath;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface Byte2DoubleFunction extends Function<Byte, Double>, IntToDoubleFunction {
    default boolean containsKey(byte b) {
        return true;
    }

    default double defaultReturnValue() {
        return 0.0d;
    }

    double get(byte b);

    @Override // java.util.function.IntToDoubleFunction
    @Deprecated
    default double applyAsDouble(int i) {
        return get(SafeMath.safeIntToByte(i));
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    @Deprecated
    default Double get(Object obj) {
        if (obj == null) {
            return null;
        }
        byte bByteValue = ((Byte) obj).byteValue();
        double d = get(bByteValue);
        if (d != defaultReturnValue() || containsKey(bByteValue)) {
            return Double.valueOf(d);
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
    default <T> java.util.function.Function<T, Double> compose(java.util.function.Function<? super T, ? extends Byte> function) {
        return super.compose(function);
    }

    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<Byte, T> andThen(java.util.function.Function<? super Double, ? extends T> function) {
        return super.andThen(function);
    }
}
