package org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles;

import java.util.function.DoubleToIntFunction;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface Double2CharFunction extends Function<Double, Character>, DoubleToIntFunction {
    default boolean containsKey(double d) {
        return true;
    }

    default char defaultReturnValue() {
        return (char) 0;
    }

    char get(double d);

    @Override // java.util.function.DoubleToIntFunction
    default int applyAsInt(double d) {
        return get(d);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    @Deprecated
    default Character get(Object obj) {
        if (obj == null) {
            return null;
        }
        double dDoubleValue = ((Double) obj).doubleValue();
        char c = get(dDoubleValue);
        if (c != defaultReturnValue() || containsKey(dDoubleValue)) {
            return Character.valueOf(c);
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
    default <T> java.util.function.Function<T, Character> compose(java.util.function.Function<? super T, ? extends Double> function) {
        return super.compose(function);
    }

    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<Double, T> andThen(java.util.function.Function<? super Character, ? extends T> function) {
        return super.andThen(function);
    }
}
