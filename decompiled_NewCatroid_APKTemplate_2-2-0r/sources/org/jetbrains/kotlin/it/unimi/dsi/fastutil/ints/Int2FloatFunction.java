package org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints;

import java.util.function.IntToDoubleFunction;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface Int2FloatFunction extends Function<Integer, Float>, IntToDoubleFunction {
    default boolean containsKey(int i) {
        return true;
    }

    default float defaultReturnValue() {
        return 0.0f;
    }

    float get(int i);

    @Override // java.util.function.IntToDoubleFunction
    default double applyAsDouble(int i) {
        return get(i);
    }

    default float put(int i, float f) {
        throw new UnsupportedOperationException();
    }

    default float remove(int i) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    default Float put(Integer num, Float f) {
        int iIntValue = num.intValue();
        boolean zContainsKey = containsKey(iIntValue);
        float fPut = put(iIntValue, f.floatValue());
        if (zContainsKey) {
            return Float.valueOf(fPut);
        }
        return null;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    @Deprecated
    default Float get(Object obj) {
        if (obj == null) {
            return null;
        }
        int iIntValue = ((Integer) obj).intValue();
        float f = get(iIntValue);
        if (f != defaultReturnValue() || containsKey(iIntValue)) {
            return Float.valueOf(f);
        }
        return null;
    }

    @Deprecated
    default Float remove(Object obj) {
        if (obj == null) {
            return null;
        }
        int iIntValue = ((Integer) obj).intValue();
        if (containsKey(iIntValue)) {
            return Float.valueOf(remove(iIntValue));
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
    default <T> java.util.function.Function<T, Float> compose(java.util.function.Function<? super T, ? extends Integer> function) {
        return super.compose(function);
    }

    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<Integer, T> andThen(java.util.function.Function<? super Float, ? extends T> function) {
        return super.andThen(function);
    }
}
