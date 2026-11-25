package org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans;

import java.util.function.UnaryOperator;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface BooleanUnaryOperator extends UnaryOperator<Boolean> {
    boolean apply(boolean z);

    @Override // java.util.function.Function
    @Deprecated
    default Boolean apply(Boolean bool) {
        return Boolean.valueOf(apply(bool.booleanValue()));
    }
}
