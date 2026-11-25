package org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans;

import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface Boolean2IntFunction extends Function<Boolean, Integer> {
    default boolean containsKey(boolean z) {
        return true;
    }

    default int defaultReturnValue() {
        return 0;
    }

    int get(boolean z);

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    @Deprecated
    default Integer get(Object obj) {
        if (obj == null) {
            return null;
        }
        boolean zBooleanValue = ((Boolean) obj).booleanValue();
        int i = get(zBooleanValue);
        if (i != defaultReturnValue() || containsKey(zBooleanValue)) {
            return Integer.valueOf(i);
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

    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<T, Integer> compose(java.util.function.Function<? super T, ? extends Boolean> function) {
        return super.compose(function);
    }

    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<Boolean, T> andThen(java.util.function.Function<? super Integer, ? extends T> function) {
        return super.andThen(function);
    }
}
