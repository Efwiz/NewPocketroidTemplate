package org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles;

import java.util.Objects;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes5.dex */
public interface DoubleIterable extends Iterable<Double> {
    @Override // java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleSet, java.util.Set
    DoubleIterator iterator();

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleSet, java.util.Set
    default DoubleSpliterator spliterator() {
        return DoubleSpliterators.asSpliteratorUnknownSize(iterator(), 0);
    }

    default void forEach(java.util.function.DoubleConsumer doubleConsumer) {
        Objects.requireNonNull(doubleConsumer);
        iterator().forEachRemaining(doubleConsumer);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.doubles.DoubleIterable
    @Deprecated
    default void forEach(Consumer<? super Double> consumer) {
        java.util.function.DoubleConsumer doubleIterable$$ExternalSyntheticLambda0;
        Objects.requireNonNull(consumer);
        if (consumer instanceof java.util.function.DoubleConsumer) {
            doubleIterable$$ExternalSyntheticLambda0 = (java.util.function.DoubleConsumer) consumer;
        } else {
            Objects.requireNonNull(consumer);
            doubleIterable$$ExternalSyntheticLambda0 = new DoubleIterable$$ExternalSyntheticLambda0(consumer);
        }
        forEach(doubleIterable$$ExternalSyntheticLambda0);
    }
}
