package org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans;

import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface Boolean2ShortFunction extends Function<Boolean, Short> {
    default boolean containsKey(boolean z) {
        return true;
    }

    default short defaultReturnValue() {
        return (short) 0;
    }

    short get(boolean z);

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    @Deprecated
    default Short get(Object obj) {
        if (obj == null) {
            return null;
        }
        boolean zBooleanValue = ((Boolean) obj).booleanValue();
        short s = get(zBooleanValue);
        if (s != defaultReturnValue() || containsKey(zBooleanValue)) {
            return Short.valueOf(s);
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
    default <T> java.util.function.Function<T, Short> compose(java.util.function.Function<? super T, ? extends Boolean> function) {
        return super.compose(function);
    }

    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<Boolean, T> andThen(java.util.function.Function<? super Short, ? extends T> function) {
        return super.andThen(function);
    }
}
