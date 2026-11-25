package org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts;

import java.util.function.IntUnaryOperator;
import java.util.function.UnaryOperator;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.SafeMath;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface ShortUnaryOperator extends IntUnaryOperator, UnaryOperator<Short> {
    short apply(short s);

    @Override // java.util.function.IntUnaryOperator
    @Deprecated
    default int applyAsInt(int i) {
        return apply(SafeMath.safeIntToShort(i));
    }

    @Override // java.util.function.Function
    @Deprecated
    default Short apply(Short sh) {
        return Short.valueOf(apply(sh.shortValue()));
    }
}
