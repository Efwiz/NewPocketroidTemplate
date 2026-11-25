package org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans;

import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface Boolean2LongFunction extends Function<Boolean, Long> {
    default boolean containsKey(boolean z) {
        return true;
    }

    default long defaultReturnValue() {
        return 0L;
    }

    long get(boolean z);

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    @Deprecated
    default Long get(Object obj) {
        if (obj == null) {
            return null;
        }
        boolean zBooleanValue = ((Boolean) obj).booleanValue();
        long j = get(zBooleanValue);
        if (j != defaultReturnValue() || containsKey(zBooleanValue)) {
            return Long.valueOf(j);
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
    default <T> java.util.function.Function<T, Long> compose(java.util.function.Function<? super T, ? extends Boolean> function) {
        return super.compose(function);
    }

    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<Boolean, T> andThen(java.util.function.Function<? super Long, ? extends T> function) {
        return super.andThen(function);
    }
}
