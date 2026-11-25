package org.jetbrains.kotlin.it.unimi.dsi.fastutil.bytes;

import java.util.function.IntPredicate;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.SafeMath;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface Byte2BooleanFunction extends Function<Byte, Boolean>, IntPredicate {
    default boolean containsKey(byte b) {
        return true;
    }

    default boolean defaultReturnValue() {
        return false;
    }

    boolean get(byte b);

    @Override // java.util.function.IntPredicate
    @Deprecated
    default boolean test(int i) {
        return get(SafeMath.safeIntToByte(i));
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.Function
    @Deprecated
    default Boolean get(Object obj) {
        if (obj == null) {
            return null;
        }
        byte bByteValue = ((Byte) obj).byteValue();
        boolean z = get(bByteValue);
        if (z != defaultReturnValue() || containsKey(bByteValue)) {
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
        return containsKey(((Byte) obj).byteValue());
    }

    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<T, Boolean> compose(java.util.function.Function<? super T, ? extends Byte> function) {
        return super.compose(function);
    }

    @Override // java.util.function.Function
    @Deprecated
    default <T> java.util.function.Function<Byte, T> andThen(java.util.function.Function<? super Boolean, ? extends T> function) {
        return super.andThen(function);
    }
}
