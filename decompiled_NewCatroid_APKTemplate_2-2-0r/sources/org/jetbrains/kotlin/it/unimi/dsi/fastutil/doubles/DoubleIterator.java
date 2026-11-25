package org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles;

import java.util.Objects;
import java.util.PrimitiveIterator;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes5.dex */
public interface DoubleIterator extends PrimitiveIterator.OfDouble {
    @Override // java.util.PrimitiveIterator.OfDouble
    double nextDouble();

    @Override // java.util.PrimitiveIterator.OfDouble, java.util.Iterator
    @Deprecated
    default Double next() {
        return Double.valueOf(nextDouble());
    }

    @Override // java.util.PrimitiveIterator.OfDouble, java.util.Iterator
    @Deprecated
    default void forEachRemaining(Consumer<? super Double> consumer) {
        java.util.function.DoubleConsumer doubleIterable$$ExternalSyntheticLambda0;
        if (consumer instanceof java.util.function.DoubleConsumer) {
            doubleIterable$$ExternalSyntheticLambda0 = (java.util.function.DoubleConsumer) consumer;
        } else {
            Objects.requireNonNull(consumer);
            doubleIterable$$ExternalSyntheticLambda0 = new DoubleIterable$$ExternalSyntheticLambda0(consumer);
        }
        forEachRemaining(doubleIterable$$ExternalSyntheticLambda0);
    }
}
