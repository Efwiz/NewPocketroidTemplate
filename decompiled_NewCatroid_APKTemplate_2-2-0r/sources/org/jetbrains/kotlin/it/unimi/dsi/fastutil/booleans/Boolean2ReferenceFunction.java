package org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans;

import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface Boolean2ReferenceFunction<V> extends Function<Boolean, V> {
    default boolean containsKey(boolean z) {
        return true;
    }

    default V defaultReturnValue() {
        return null;
    }

    V get(boolean z);

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    @Deprecated
    default V get(Object obj) {
        if (obj == null) {
            return null;
        }
        boolean zBooleanValue = ((Boolean) obj).booleanValue();
        V v = get(zBooleanValue);
        if (v != defaultReturnValue() || containsKey(zBooleanValue)) {
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
        return containsKey(((Boolean) obj).booleanValue());
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<T, V> compose(java.util.function.Function<? super T, ? extends Boolean> function) {
        return super.compose(function);
    }
}
