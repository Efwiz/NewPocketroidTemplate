package org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects;

import java.util.function.ToIntFunction;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface Reference2ShortFunction<K> extends Function<K, Short>, ToIntFunction<K> {
    default short defaultReturnValue() {
        return (short) 0;
    }

    short getShort(Object obj);

    @Override // java.util.function.ToIntFunction
    default int applyAsInt(K k) {
        return getShort(k);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    @Deprecated
    default Short get(Object obj) {
        short s = getShort(obj);
        if (s != defaultReturnValue() || containsKey(obj)) {
            return Short.valueOf(s);
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<K, T> andThen(java.util.function.Function<? super Short, ? extends T> function) {
        return super.andThen(function);
    }
}
