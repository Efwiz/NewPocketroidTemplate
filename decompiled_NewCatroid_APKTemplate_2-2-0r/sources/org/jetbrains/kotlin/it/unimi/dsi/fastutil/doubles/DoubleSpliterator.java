package org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles;

import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes5.dex */
public interface DoubleSpliterator extends Spliterator.OfDouble {
    @Override // java.util.Spliterator.OfDouble, java.util.Spliterator.OfPrimitive, java.util.Spliterator
    DoubleSpliterator trySplit();

    @Override // java.util.Spliterator.OfDouble, java.util.Spliterator
    @Deprecated
    default boolean tryAdvance(Consumer<? super Double> consumer) {
        java.util.function.DoubleConsumer doubleIterable$$ExternalSyntheticLambda0;
        if (consumer instanceof java.util.function.DoubleConsumer) {
            doubleIterable$$ExternalSyntheticLambda0 = (java.util.function.DoubleConsumer) consumer;
        } else {
            Objects.requireNonNull(consumer);
            doubleIterable$$ExternalSyntheticLambda0 = new DoubleIterable$$ExternalSyntheticLambda0(consumer);
        }
        return tryAdvance(doubleIterable$$ExternalSyntheticLambda0);
    }

    @Override // java.util.Spliterator.OfDouble, java.util.Spliterator
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

    @Override // java.util.Spliterator
    default DoubleComparator getComparator() {
        throw new IllegalStateException();
    }
}
