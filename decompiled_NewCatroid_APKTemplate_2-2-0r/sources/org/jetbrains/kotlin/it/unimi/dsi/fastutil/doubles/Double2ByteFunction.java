package org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles;

import java.util.function.DoubleToIntFunction;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface Double2ByteFunction extends Function<Double, Byte>, DoubleToIntFunction {
    default boolean containsKey(double d) {
        return true;
    }

    default byte defaultReturnValue() {
        return (byte) 0;
    }

    byte get(double d);

    @Override // java.util.function.DoubleToIntFunction
    default int applyAsInt(double d) {
        return get(d);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    @Deprecated
    default Byte get(Object obj) {
        if (obj == null) {
            return null;
        }
        double dDoubleValue = ((Double) obj).doubleValue();
        byte b = get(dDoubleValue);
        if (b != defaultReturnValue() || containsKey(dDoubleValue)) {
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
        return containsKey(((Double) obj).doubleValue());
    }

    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<T, Byte> compose(java.util.function.Function<? super T, ? extends Double> function) {
        return super.compose(function);
    }

    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<Double, T> andThen(java.util.function.Function<? super Byte, ? extends T> function) {
        return super.andThen(function);
    }
}
