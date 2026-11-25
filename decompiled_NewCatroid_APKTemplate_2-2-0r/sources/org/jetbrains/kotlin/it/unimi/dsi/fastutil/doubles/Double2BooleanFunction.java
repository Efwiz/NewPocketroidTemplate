package org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles;

import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface Double2BooleanFunction extends Function<Double, Boolean>, java.util.function.DoublePredicate {
    default boolean containsKey(double d) {
        return true;
    }

    default boolean defaultReturnValue() {
        return false;
    }

    boolean get(double d);

    @Override // java.util.function.DoublePredicate
    default boolean test(double d) {
        return get(d);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    @Deprecated
    default Boolean get(Object obj) {
        if (obj == null) {
            return null;
        }
        double dDoubleValue = ((Double) obj).doubleValue();
        boolean z = get(dDoubleValue);
        if (z != defaultReturnValue() || containsKey(dDoubleValue)) {
            return Boolean.valueOf(z);
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
    default <T> java.util.function.Function<T, Boolean> compose(java.util.function.Function<? super T, ? extends Double> function) {
        return super.compose(function);
    }

    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<Double, T> andThen(java.util.function.Function<? super Boolean, ? extends T> function) {
        return super.andThen(function);
    }
}
