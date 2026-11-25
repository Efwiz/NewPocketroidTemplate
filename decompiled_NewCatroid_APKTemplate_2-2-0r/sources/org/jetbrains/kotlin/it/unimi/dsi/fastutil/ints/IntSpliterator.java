package org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints;

import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes5.dex */
public interface IntSpliterator extends Spliterator.OfInt {
    @Override // java.util.Spliterator.OfInt, java.util.Spliterator.OfPrimitive, java.util.Spliterator
    IntSpliterator trySplit();

    @Override // java.util.Spliterator.OfInt, java.util.Spliterator
    @Deprecated
    default boolean tryAdvance(Consumer<? super Integer> consumer) {
        java.util.function.IntConsumer intIterable$$ExternalSyntheticLambda0;
        if (consumer instanceof java.util.function.IntConsumer) {
            intIterable$$ExternalSyntheticLambda0 = (java.util.function.IntConsumer) consumer;
        } else {
            Objects.requireNonNull(consumer);
            intIterable$$ExternalSyntheticLambda0 = new IntIterable$$ExternalSyntheticLambda0(consumer);
        }
        return tryAdvance(intIterable$$ExternalSyntheticLambda0);
    }

    @Override // java.util.Spliterator.OfInt, java.util.Spliterator
    @Deprecated
    default void forEachRemaining(Consumer<? super Integer> consumer) {
        java.util.function.IntConsumer intIterable$$ExternalSyntheticLambda0;
        if (consumer instanceof java.util.function.IntConsumer) {
            intIterable$$ExternalSyntheticLambda0 = (java.util.function.IntConsumer) consumer;
        } else {
            Objects.requireNonNull(consumer);
            intIterable$$ExternalSyntheticLambda0 = new IntIterable$$ExternalSyntheticLambda0(consumer);
        }
        forEachRemaining(intIterable$$ExternalSyntheticLambda0);
    }

    @Override // java.util.Spliterator
    default IntComparator getComparator() {
        throw new IllegalStateException();
    }
}
