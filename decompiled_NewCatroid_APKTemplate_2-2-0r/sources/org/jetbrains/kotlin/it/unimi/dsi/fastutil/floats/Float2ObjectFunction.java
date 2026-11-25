package org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats;

import java.util.function.DoubleFunction;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.SafeMath;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface Float2ObjectFunction<V> extends Function<Float, V>, DoubleFunction<V> {
    default boolean containsKey(float f) {
        return true;
    }

    default V defaultReturnValue() {
        return null;
    }

    V get(float f);

    @Override // java.util.function.DoubleFunction
    @Deprecated
    default V apply(double d) {
        return get(SafeMath.safeDoubleToFloat(d));
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    @Deprecated
    default V get(Object obj) {
        if (obj == null) {
            return null;
        }
        float fFloatValue = ((Float) obj).floatValue();
        V v = get(fFloatValue);
        if (v != defaultReturnValue() || containsKey(fFloatValue)) {
            return v;
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    @Deprecated
    default boolean containsKey(Object obj) {
        if (obj == null) {
            return false;
        }
        return containsKey(((Float) obj).floatValue());
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<T, V> compose(java.util.function.Function<? super T, ? extends Float> function) {
        return super.compose(function);
    }
}
