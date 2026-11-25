package org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects;

import java.util.function.ToDoubleFunction;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface Reference2FloatFunction<K> extends Function<K, Float>, ToDoubleFunction<K> {
    default float defaultReturnValue() {
        return 0.0f;
    }

    float getFloat(Object obj);

    @Override // java.util.function.ToDoubleFunction
    default double applyAsDouble(K k) {
        return getFloat(k);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    @Deprecated
    default Float get(Object obj) {
        float f = getFloat(obj);
        if (f != defaultReturnValue() || containsKey(obj)) {
            return Float.valueOf(f);
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<K, T> andThen(java.util.function.Function<? super Float, ? extends T> function) {
        return super.andThen(function);
    }
}
