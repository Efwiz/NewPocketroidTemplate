package org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints;

import java.util.function.IntToDoubleFunction;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface Int2DoubleFunction extends Function<Integer, Double>, IntToDoubleFunction {
    default boolean containsKey(int i) {
        return true;
    }

    default double defaultReturnValue() {
        return 0.0d;
    }

    double get(int i);

    @Override // java.util.function.IntToDoubleFunction
    default double applyAsDouble(int i) {
        return get(i);
    }

    default double put(int i, double d) {
        throw new UnsupportedOperationException();
    }

    default double remove(int i) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    default Double put(Integer num, Double d) {
        int iIntValue = num.intValue();
        boolean zContainsKey = containsKey(iIntValue);
        double dPut = put(iIntValue, d.doubleValue());
        if (zContainsKey) {
            return Double.valueOf(dPut);
        }
        return null;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    @Deprecated
    default Double get(Object obj) {
        if (obj == null) {
            return null;
        }
        int iIntValue = ((Integer) obj).intValue();
        double d = get(iIntValue);
        if (d != defaultReturnValue() || containsKey(iIntValue)) {
            return Double.valueOf(d);
        }
        return null;
    }

    @Deprecated
    default Double remove(Object obj) {
        if (obj == null) {
            return null;
        }
        int iIntValue = ((Integer) obj).intValue();
        if (containsKey(iIntValue)) {
            return Double.valueOf(remove(iIntValue));
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    @Deprecated
    default boolean containsKey(Object obj) {
        if (obj == null) {
            return false;
        }
        return containsKey(((Integer) obj).intValue());
    }

    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<T, Double> compose(java.util.function.Function<? super T, ? extends Integer> function) {
        return super.compose(function);
    }

    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<Integer, T> andThen(java.util.function.Function<? super Double, ? extends T> function) {
        return super.andThen(function);
    }
}
