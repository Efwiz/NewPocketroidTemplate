package org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints;

import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface Int2ByteFunction extends Function<Integer, Byte>, java.util.function.IntUnaryOperator {
    default boolean containsKey(int i) {
        return true;
    }

    default byte defaultReturnValue() {
        return (byte) 0;
    }

    byte get(int i);

    @Override // java.util.function.IntUnaryOperator
    default int applyAsInt(int i) {
        return get(i);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    @Deprecated
    default Byte get(Object obj) {
        if (obj == null) {
            return null;
        }
        int iIntValue = ((Integer) obj).intValue();
        byte b = get(iIntValue);
        if (b != defaultReturnValue() || containsKey(iIntValue)) {
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
        return containsKey(((Integer) obj).intValue());
    }

    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<T, Byte> compose(java.util.function.Function<? super T, ? extends Integer> function) {
        return super.compose(function);
    }

    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<Integer, T> andThen(java.util.function.Function<? super Byte, ? extends T> function) {
        return super.andThen(function);
    }
}
