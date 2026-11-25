package org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects;

import java.util.function.ToIntFunction;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface Reference2IntFunction<K> extends Function<K, Integer>, ToIntFunction<K> {
    default int defaultReturnValue() {
        return 0;
    }

    int getInt(Object obj);

    @Override // java.util.function.ToIntFunction
    default int applyAsInt(K k) {
        return getInt(k);
    }

    default int put(K k, int i) {
        throw new UnsupportedOperationException();
    }

    default int removeInt(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    default Integer put(K k, Integer num) {
        boolean zContainsKey = containsKey(k);
        int iPut = put(k, num.intValue());
        if (zContainsKey) {
            return Integer.valueOf(iPut);
        }
        return null;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    @Deprecated
    default Integer get(Object obj) {
        int i = getInt(obj);
        if (i != defaultReturnValue() || containsKey(obj)) {
            return Integer.valueOf(i);
        }
        return null;
    }

    @Deprecated
    default Integer remove(Object obj) {
        if (containsKey(obj)) {
            return Integer.valueOf(removeInt(obj));
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<K, T> andThen(java.util.function.Function<? super Integer, ? extends T> function) {
        return super.andThen(function);
    }
}
