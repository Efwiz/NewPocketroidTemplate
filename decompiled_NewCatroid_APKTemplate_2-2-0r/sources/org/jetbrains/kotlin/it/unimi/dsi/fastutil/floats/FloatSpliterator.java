package org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats;

import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes5.dex */
public interface FloatSpliterator extends Spliterator.OfPrimitive<Float, FloatConsumer, FloatSpliterator> {
    @Override // java.util.Spliterator.OfPrimitive, java.util.Spliterator
    FloatSpliterator trySplit();

    @Override // java.util.Spliterator
    @Deprecated
    default boolean tryAdvance(Consumer<? super Float> consumer) {
        FloatConsumer floatIterable$$ExternalSyntheticLambda0;
        if (consumer instanceof FloatConsumer) {
            floatIterable$$ExternalSyntheticLambda0 = (FloatConsumer) consumer;
        } else {
            Objects.requireNonNull(consumer);
            floatIterable$$ExternalSyntheticLambda0 = new FloatIterable$$ExternalSyntheticLambda0(consumer);
        }
        return tryAdvance(floatIterable$$ExternalSyntheticLambda0);
    }

    @Override // java.util.Spliterator
    @Deprecated
    default void forEachRemaining(Consumer<? super Float> consumer) {
        FloatConsumer floatIterable$$ExternalSyntheticLambda0;
        if (consumer instanceof FloatConsumer) {
            floatIterable$$ExternalSyntheticLambda0 = (FloatConsumer) consumer;
        } else {
            Objects.requireNonNull(consumer);
            floatIterable$$ExternalSyntheticLambda0 = new FloatIterable$$ExternalSyntheticLambda0(consumer);
        }
        forEachRemaining(floatIterable$$ExternalSyntheticLambda0);
    }

    @Override // java.util.Spliterator
    default FloatComparator getComparator() {
        throw new IllegalStateException();
    }
}
