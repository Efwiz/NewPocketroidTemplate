package org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles;

import java.util.function.DoubleToIntFunction;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface Double2ShortFunction extends Function<Double, Short>, DoubleToIntFunction {
    default boolean containsKey(double d) {
        return true;
    }

    default short defaultReturnValue() {
        return (short) 0;
    }

    short get(double d);

    @Override // java.util.function.DoubleToIntFunction
    default int applyAsInt(double d) {
        return get(d);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    @Deprecated
    default Short get(Object obj) {
        if (obj == null) {
            return null;
        }
        double dDoubleValue = ((Double) obj).doubleValue();
        short s = get(dDoubleValue);
        if (s != defaultReturnValue() || containsKey(dDoubleValue)) {
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
        return containsKey(((Double) obj).doubleValue());
    }

    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<T, Short> compose(java.util.function.Function<? super T, ? extends Double> function) {
        return super.compose(function);
    }

    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<Double, T> andThen(java.util.function.Function<? super Short, ? extends T> function) {
        return super.andThen(function);
    }
}
