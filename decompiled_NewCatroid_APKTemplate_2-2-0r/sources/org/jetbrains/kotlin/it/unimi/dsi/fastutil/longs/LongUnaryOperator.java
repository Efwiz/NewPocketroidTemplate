package org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs;

import java.util.function.UnaryOperator;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface LongUnaryOperator extends java.util.function.LongUnaryOperator, UnaryOperator<Long> {
    long apply(long j);

    @Override // java.util.function.LongUnaryOperator
    @Deprecated
    default long applyAsLong(long j) {
        return apply(j);
    }

    @Override // java.util.function.Function
    @Deprecated
    default Long apply(Long l) {
        return Long.valueOf(apply(l.longValue()));
    }
}
