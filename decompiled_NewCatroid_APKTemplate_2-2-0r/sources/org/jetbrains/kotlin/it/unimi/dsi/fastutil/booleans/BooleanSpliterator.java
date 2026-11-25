package org.jetbrains.kotlin.it.unimi.dsi.fastutil.booleans;

import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes5.dex */
public interface BooleanSpliterator extends Spliterator.OfPrimitive<Boolean, BooleanConsumer, BooleanSpliterator> {
    @Override // java.util.Spliterator.OfPrimitive, java.util.Spliterator
    BooleanSpliterator trySplit();

    @Override // java.util.Spliterator
    @Deprecated
    default boolean tryAdvance(Consumer<? super Boolean> consumer) {
        BooleanConsumer booleanIterable$$ExternalSyntheticLambda0;
        if (consumer instanceof BooleanConsumer) {
            booleanIterable$$ExternalSyntheticLambda0 = (BooleanConsumer) consumer;
        } else {
            Objects.requireNonNull(consumer);
            booleanIterable$$ExternalSyntheticLambda0 = new BooleanIterable$$ExternalSyntheticLambda0(consumer);
        }
        return tryAdvance(booleanIterable$$ExternalSyntheticLambda0);
    }

    @Override // java.util.Spliterator
    @Deprecated
    default void forEachRemaining(Consumer<? super Boolean> consumer) {
        BooleanConsumer booleanIterable$$ExternalSyntheticLambda0;
        if (consumer instanceof BooleanConsumer) {
            booleanIterable$$ExternalSyntheticLambda0 = (BooleanConsumer) consumer;
        } else {
            Objects.requireNonNull(consumer);
            booleanIterable$$ExternalSyntheticLambda0 = new BooleanIterable$$ExternalSyntheticLambda0(consumer);
        }
        forEachRemaining(booleanIterable$$ExternalSyntheticLambda0);
    }

    @Override // java.util.Spliterator
    default BooleanComparator getComparator() {
        throw new IllegalStateException();
    }
}
