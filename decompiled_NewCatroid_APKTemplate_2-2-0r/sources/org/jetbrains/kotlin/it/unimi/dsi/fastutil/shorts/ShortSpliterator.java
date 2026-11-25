package org.jetbrains.kotlin.it.unimi.dsi.fastutil.shorts;

import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes5.dex */
public interface ShortSpliterator extends Spliterator.OfPrimitive<Short, ShortConsumer, ShortSpliterator> {
    @Override // java.util.Spliterator.OfPrimitive, java.util.Spliterator
    ShortSpliterator trySplit();

    @Override // java.util.Spliterator
    @Deprecated
    default boolean tryAdvance(Consumer<? super Short> consumer) {
        ShortConsumer shortIterable$$ExternalSyntheticLambda0;
        if (consumer instanceof ShortConsumer) {
            shortIterable$$ExternalSyntheticLambda0 = (ShortConsumer) consumer;
        } else {
            Objects.requireNonNull(consumer);
            shortIterable$$ExternalSyntheticLambda0 = new ShortIterable$$ExternalSyntheticLambda0(consumer);
        }
        return tryAdvance(shortIterable$$ExternalSyntheticLambda0);
    }

    @Override // java.util.Spliterator
    @Deprecated
    default void forEachRemaining(Consumer<? super Short> consumer) {
        ShortConsumer shortIterable$$ExternalSyntheticLambda0;
        if (consumer instanceof ShortConsumer) {
            shortIterable$$ExternalSyntheticLambda0 = (ShortConsumer) consumer;
        } else {
            Objects.requireNonNull(consumer);
            shortIterable$$ExternalSyntheticLambda0 = new ShortIterable$$ExternalSyntheticLambda0(consumer);
        }
        forEachRemaining(shortIterable$$ExternalSyntheticLambda0);
    }

    @Override // java.util.Spliterator
    default ShortComparator getComparator() {
        throw new IllegalStateException();
    }
}
