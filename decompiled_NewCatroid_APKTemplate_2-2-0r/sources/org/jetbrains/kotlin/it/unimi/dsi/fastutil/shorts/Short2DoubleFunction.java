package org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts;

import java.util.function.IntToDoubleFunction;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.SafeMath;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface Short2DoubleFunction extends Function<Short, Double>, IntToDoubleFunction {
    default boolean containsKey(short s) {
        return true;
    }

    default double defaultReturnValue() {
        return 0.0d;
    }

    double get(short s);

    @Override // java.util.function.IntToDoubleFunction
    @Deprecated
    default double applyAsDouble(int i) {
        return get(SafeMath.safeIntToShort(i));
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    @Deprecated
    default Double get(Object obj) {
        if (obj == null) {
            return null;
        }
        short sShortValue = ((Short) obj).shortValue();
        double d = get(sShortValue);
        if (d != defaultReturnValue() || containsKey(sShortValue)) {
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
        return containsKey(((Short) obj).shortValue());
    }

    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<T, Double> compose(java.util.function.Function<? super T, ? extends Short> function) {
        return super.compose(function);
    }

    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<Short, T> andThen(java.util.function.Function<? super Double, ? extends T> function) {
        return super.andThen(function);
    }
}
