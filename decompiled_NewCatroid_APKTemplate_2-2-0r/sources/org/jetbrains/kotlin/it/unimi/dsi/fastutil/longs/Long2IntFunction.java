package org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs;

import java.util.function.LongToIntFunction;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface Long2IntFunction extends Function<Long, Integer>, LongToIntFunction {
    default boolean containsKey(long j) {
        return true;
    }

    default int defaultReturnValue() {
        return 0;
    }

    int get(long j);

    @Override // java.util.function.LongToIntFunction
    default int applyAsInt(long j) {
        return get(j);
    }

    default int put(long j, int i) {
        throw new UnsupportedOperationException();
    }

    default int remove(long j) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    default Integer put(Long l, Integer num) {
        long jLongValue = l.longValue();
        boolean zContainsKey = containsKey(jLongValue);
        int iPut = put(jLongValue, num.intValue());
        if (zContainsKey) {
            return Integer.valueOf(iPut);
        }
        return null;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    @Deprecated
    default Integer get(Object obj) {
        if (obj == null) {
            return null;
        }
        long jLongValue = ((Long) obj).longValue();
        int i = get(jLongValue);
        if (i != defaultReturnValue() || containsKey(jLongValue)) {
            return Integer.valueOf(i);
        }
        return null;
    }

    @Deprecated
    default Integer remove(Object obj) {
        if (obj == null) {
            return null;
        }
        long jLongValue = ((Long) obj).longValue();
        if (containsKey(jLongValue)) {
            return Integer.valueOf(remove(jLongValue));
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    @Deprecated
    default boolean containsKey(Object obj) {
        if (obj == null) {
            return false;
        }
        return containsKey(((Long) obj).longValue());
    }

    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<T, Integer> compose(java.util.function.Function<? super T, ? extends Long> function) {
        return super.compose(function);
    }

    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<Long, T> andThen(java.util.function.Function<? super Integer, ? extends T> function) {
        return super.andThen(function);
    }
}
