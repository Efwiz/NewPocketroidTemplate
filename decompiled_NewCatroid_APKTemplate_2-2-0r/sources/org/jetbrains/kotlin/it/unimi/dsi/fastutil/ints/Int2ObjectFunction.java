package org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints;

import java.util.function.IntFunction;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface Int2ObjectFunction<V> extends Function<Integer, V>, IntFunction<V> {
    default boolean containsKey(int i) {
        return true;
    }

    default V defaultReturnValue() {
        return null;
    }

    V get(int i);

    @Override // java.util.function.IntFunction
    default V apply(int i) {
        return get(i);
    }

    default V put(int i, V v) {
        throw new UnsupportedOperationException();
    }

    default V remove(int i) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    default V put(Integer num, V v) {
        int iIntValue = num.intValue();
        boolean zContainsKey = containsKey(iIntValue);
        V vPut = put(iIntValue, v);
        if (zContainsKey) {
            return vPut;
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    @Deprecated
    default V get(Object obj) {
        if (obj == null) {
            return null;
        }
        int iIntValue = ((Integer) obj).intValue();
        V v = get(iIntValue);
        if (v != defaultReturnValue() || containsKey(iIntValue)) {
            return v;
        }
        return null;
    }

    @Deprecated
    default V getOrDefault(Object obj, V v) {
        if (obj == null) {
            return v;
        }
        int iIntValue = ((Integer) obj).intValue();
        V v2 = get(iIntValue);
        return (v2 != defaultReturnValue() || containsKey(iIntValue)) ? v2 : v;
    }

    @Deprecated
    default V remove(Object obj) {
        if (obj == null) {
            return null;
        }
        int iIntValue = ((Integer) obj).intValue();
        if (containsKey(iIntValue)) {
            return remove(iIntValue);
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

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<T, V> compose(java.util.function.Function<? super T, ? extends Integer> function) {
        return super.compose(function);
    }
}
