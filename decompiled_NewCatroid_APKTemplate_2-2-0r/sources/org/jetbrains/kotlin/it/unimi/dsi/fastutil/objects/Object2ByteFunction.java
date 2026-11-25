package org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects;

import java.util.function.ToIntFunction;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface Object2ByteFunction<K> extends Function<K, Byte>, ToIntFunction<K> {
    default byte defaultReturnValue() {
        return (byte) 0;
    }

    byte getByte(Object obj);

    @Override // java.util.function.ToIntFunction
    default int applyAsInt(K k) {
        return getByte(k);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    @Deprecated
    default Byte get(Object obj) {
        byte b = getByte(obj);
        if (b != defaultReturnValue() || containsKey(obj)) {
            return Byte.valueOf(b);
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<K, T> andThen(java.util.function.Function<? super Byte, ? extends T> function) {
        return super.andThen(function);
    }
}
