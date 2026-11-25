package org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts;

import java.util.function.IntPredicate;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.SafeMath;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface Short2BooleanFunction extends Function<Short, Boolean>, IntPredicate {
    default boolean containsKey(short s) {
        return true;
    }

    default boolean defaultReturnValue() {
        return false;
    }

    boolean get(short s);

    @Override // java.util.function.IntPredicate
    @Deprecated
    default boolean test(int i) {
        return get(SafeMath.safeIntToShort(i));
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    @Deprecated
    default Boolean get(Object obj) {
        if (obj == null) {
            return null;
        }
        short sShortValue = ((Short) obj).shortValue();
        boolean z = get(sShortValue);
        if (z != defaultReturnValue() || containsKey(sShortValue)) {
            return Boolean.valueOf(z);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    @Deprecated
    default boolean containsKey(Object obj) {
        if (obj == null) {
            return false;
        }
        return containsKey(((Short) obj).shortValue());
    }

    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<T, Boolean> compose(java.util.function.Function<? super T, ? extends Short> function) {
        return super.compose(function);
    }

    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<Short, T> andThen(java.util.function.Function<? super Boolean, ? extends T> function) {
        return super.andThen(function);
    }
}
