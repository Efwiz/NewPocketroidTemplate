package org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects;

import java.util.function.ToDoubleFunction;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface Reference2DoubleFunction<K> extends Function<K, Double>, ToDoubleFunction<K> {
    default double defaultReturnValue() {
        return 0.0d;
    }

    double getDouble(Object obj);

    @Override // java.util.function.ToDoubleFunction
    default double applyAsDouble(K k) {
        return getDouble(k);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    @Deprecated
    default Double get(Object obj) {
        double d = getDouble(obj);
        if (d != defaultReturnValue() || containsKey(obj)) {
            return Double.valueOf(d);
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<K, T> andThen(java.util.function.Function<? super Double, ? extends T> function) {
        return super.andThen(function);
    }
}
