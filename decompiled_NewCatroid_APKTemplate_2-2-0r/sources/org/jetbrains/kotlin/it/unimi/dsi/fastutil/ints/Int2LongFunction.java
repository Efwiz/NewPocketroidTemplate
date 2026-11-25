package org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints;

import java.util.function.IntToLongFunction;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface Int2LongFunction extends Function<Integer, Long>, IntToLongFunction {
    default boolean containsKey(int i) {
        return true;
    }

    default long defaultReturnValue() {
        return 0L;
    }

    long get(int i);

    @Override // java.util.function.IntToLongFunction
    default long applyAsLong(int i) {
        return get(i);
    }

    default long put(int i, long j) {
        throw new UnsupportedOperationException();
    }

    default long remove(int i) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    default Long put(Integer num, Long l) {
        int iIntValue = num.intValue();
        boolean zContainsKey = containsKey(iIntValue);
        long jPut = put(iIntValue, l.longValue());
        if (zContainsKey) {
            return Long.valueOf(jPut);
        }
        return null;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    @Deprecated
    default Long get(Object obj) {
        if (obj == null) {
            return null;
        }
        int iIntValue = ((Integer) obj).intValue();
        long j = get(iIntValue);
        if (j != defaultReturnValue() || containsKey(iIntValue)) {
            return Long.valueOf(j);
        }
        return null;
    }

    @Deprecated
    default Long remove(Object obj) {
        if (obj == null) {
            return null;
        }
        int iIntValue = ((Integer) obj).intValue();
        if (containsKey(iIntValue)) {
            return Long.valueOf(remove(iIntValue));
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
    default <T> java.util.function.Function<T, Long> compose(java.util.function.Function<? super T, ? extends Integer> function) {
        return super.compose(function);
    }

    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<Integer, T> andThen(java.util.function.Function<? super Long, ? extends T> function) {
        return super.andThen(function);
    }
}
