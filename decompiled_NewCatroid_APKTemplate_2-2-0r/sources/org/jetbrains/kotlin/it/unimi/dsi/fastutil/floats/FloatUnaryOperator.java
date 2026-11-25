package org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats;

import java.util.function.DoubleUnaryOperator;
import java.util.function.UnaryOperator;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.SafeMath;

/* JADX INFO: loaded from: classes5.dex */
@FunctionalInterface
public interface FloatUnaryOperator extends DoubleUnaryOperator, UnaryOperator<Float> {
    float apply(float f);

    @Override // java.util.function.DoubleUnaryOperator
    @Deprecated
    default double applyAsDouble(double d) {
        return apply(SafeMath.safeDoubleToFloat(d));
    }

    @Override // java.util.function.Function
    @Deprecated
    default Float apply(Float f) {
        return Float.valueOf(apply(f.floatValue()));
    }
}
