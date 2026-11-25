package org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs;

import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes5.dex */
public interface LongSpliterator extends Spliterator.OfLong {
    @Override // java.util.Spliterator.OfLong, java.util.Spliterator.OfPrimitive, java.util.Spliterator
    LongSpliterator trySplit();

    @Override // java.util.Spliterator.OfLong, java.util.Spliterator
    @Deprecated
    default boolean tryAdvance(Consumer<? super Long> consumer) {
        java.util.function.LongConsumer longIterable$$ExternalSyntheticLambda0;
        if (consumer instanceof java.util.function.LongConsumer) {
            longIterable$$ExternalSyntheticLambda0 = (java.util.function.LongConsumer) consumer;
        } else {
            Objects.requireNonNull(consumer);
            longIterable$$ExternalSyntheticLambda0 = new LongIterable$$ExternalSyntheticLambda0(consumer);
        }
        return tryAdvance(longIterable$$ExternalSyntheticLambda0);
    }

    @Override // java.util.Spliterator.OfLong, java.util.Spliterator
    @Deprecated
    default void forEachRemaining(Consumer<? super Long> consumer) {
        java.util.function.LongConsumer longIterable$$ExternalSyntheticLambda0;
        if (consumer instanceof java.util.function.LongConsumer) {
            longIterable$$ExternalSyntheticLambda0 = (java.util.function.LongConsumer) consumer;
        } else {
            Objects.requireNonNull(consumer);
            longIterable$$ExternalSyntheticLambda0 = new LongIterable$$ExternalSyntheticLambda0(consumer);
        }
        forEachRemaining(longIterable$$ExternalSyntheticLambda0);
    }

    @Override // java.util.Spliterator
    default LongComparator getComparator() {
        throw new IllegalStateException();
    }
}
