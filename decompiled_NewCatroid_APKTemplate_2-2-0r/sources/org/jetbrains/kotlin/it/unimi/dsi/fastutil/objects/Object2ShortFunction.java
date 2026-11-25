package org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects;

import java.util.function.ToIntFunction;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface Object2ShortFunction<K> extends Function<K, Short>, ToIntFunction<K> {
    default short defaultReturnValue() {
        return (short) 0;
    }

    short getShort(Object obj);

    @Override // java.util.function.ToIntFunction
    default int applyAsInt(K k) {
        return getShort(k);
    }

    default short put(K k, short s) {
        throw new UnsupportedOperationException();
    }

    default short removeShort(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    default Short put(K k, Short sh) {
        boolean zContainsKey = containsKey(k);
        short sPut = put(k, sh.shortValue());
        if (zContainsKey) {
            return Short.valueOf(sPut);
        }
        return null;
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

    @Deprecated
    default Short remove(Object obj) {
        if (containsKey(obj)) {
            return Short.valueOf(removeShort(obj));
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
