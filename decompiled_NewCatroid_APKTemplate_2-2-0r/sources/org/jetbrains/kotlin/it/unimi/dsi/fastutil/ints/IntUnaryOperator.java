package org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints;

import java.util.function.UnaryOperator;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface IntUnaryOperator extends java.util.function.IntUnaryOperator, UnaryOperator<Integer> {
    int apply(int i);

    @Override // java.util.function.IntUnaryOperator
    @Deprecated
    default int applyAsInt(int i) {
        return apply(i);
    }

    @Override // java.util.function.Function
    @Deprecated
    default Integer apply(Integer num) {
        return Integer.valueOf(apply(num.intValue()));
    }
}
