package org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts;

import java.util.function.BinaryOperator;
import java.util.function.IntBinaryOperator;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.SafeMath;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface ShortBinaryOperator extends BinaryOperator<Short>, IntBinaryOperator {
    short apply(short s, short s2);

    @Override // java.util.function.IntBinaryOperator
    @Deprecated
    default int applyAsInt(int i, int i2) {
        return apply(SafeMath.safeIntToShort(i), SafeMath.safeIntToShort(i2));
    }

    @Override // java.util.function.BiFunction
    @Deprecated
    default Short apply(Short sh, Short sh2) {
        return Short.valueOf(apply(sh.shortValue(), sh2.shortValue()));
    }
}
