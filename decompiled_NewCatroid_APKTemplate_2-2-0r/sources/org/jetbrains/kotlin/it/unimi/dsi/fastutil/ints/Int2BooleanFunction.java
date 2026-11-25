package org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints;

import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface Int2BooleanFunction extends Function<Integer, Boolean>, java.util.function.IntPredicate {
    default boolean containsKey(int i) {
        return true;
    }

    default boolean defaultReturnValue() {
        return false;
    }

    boolean get(int i);

    @Override // java.util.function.IntPredicate
    default boolean test(int i) {
        return get(i);
    }

    default boolean put(int i, boolean z) {
        throw new UnsupportedOperationException();
    }

    default boolean remove(int i) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    default Boolean put(Integer num, Boolean bool) {
        int iIntValue = num.intValue();
        boolean zContainsKey = containsKey(iIntValue);
        boolean zPut = put(iIntValue, bool.booleanValue());
        if (zContainsKey) {
            return Boolean.valueOf(zPut);
        }
        return null;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    @Deprecated
    default Boolean get(Object obj) {
        if (obj == null) {
            return null;
        }
        int iIntValue = ((Integer) obj).intValue();
        boolean z = get(iIntValue);
        if (z != defaultReturnValue() || containsKey(iIntValue)) {
            return Boolean.valueOf(z);
        }
        return null;
    }

    @Deprecated
    default Boolean remove(Object obj) {
        if (obj == null) {
            return null;
        }
        int iIntValue = ((Integer) obj).intValue();
        if (containsKey(iIntValue)) {
            return Boolean.valueOf(remove(iIntValue));
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
    default <T> java.util.function.Function<T, Boolean> compose(java.util.function.Function<? super T, ? extends Integer> function) {
        return super.compose(function);
    }

    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<Integer, T> andThen(java.util.function.Function<? super Boolean, ? extends T> function) {
        return super.andThen(function);
    }
}
