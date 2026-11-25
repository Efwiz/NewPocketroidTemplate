package org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans;

import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface Boolean2ByteFunction extends Function<Boolean, Byte> {
    default boolean containsKey(boolean z) {
        return true;
    }

    default byte defaultReturnValue() {
        return (byte) 0;
    }

    byte get(boolean z);

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    @Deprecated
    default Byte get(Object obj) {
        if (obj == null) {
            return null;
        }
        boolean zBooleanValue = ((Boolean) obj).booleanValue();
        byte b = get(zBooleanValue);
        if (b != defaultReturnValue() || containsKey(zBooleanValue)) {
            return Byte.valueOf(b);
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
    default <T> java.util.function.Function<T, Byte> compose(java.util.function.Function<? super T, ? extends Boolean> function) {
        return super.compose(function);
    }

    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<Boolean, T> andThen(java.util.function.Function<? super Byte, ? extends T> function) {
        return super.andThen(function);
    }
}
