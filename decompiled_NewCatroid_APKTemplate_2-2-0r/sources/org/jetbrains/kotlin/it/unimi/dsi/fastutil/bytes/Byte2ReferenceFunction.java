package org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes;

import java.util.function.IntFunction;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.SafeMath;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface Byte2ReferenceFunction<V> extends Function<Byte, V>, IntFunction<V> {
    default boolean containsKey(byte b) {
        return true;
    }

    default V defaultReturnValue() {
        return null;
    }

    V get(byte b);

    @Override // java.util.function.IntFunction
    @Deprecated
    default V apply(int i) {
        return get(SafeMath.safeIntToByte(i));
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    @Deprecated
    default V get(Object obj) {
        if (obj == null) {
            return null;
        }
        byte bByteValue = ((Byte) obj).byteValue();
        V v = get(bByteValue);
        if (v != defaultReturnValue() || containsKey(bByteValue)) {
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
        return containsKey(((Byte) obj).byteValue());
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<T, V> compose(java.util.function.Function<? super T, ? extends Byte> function) {
        return super.compose(function);
    }
}
