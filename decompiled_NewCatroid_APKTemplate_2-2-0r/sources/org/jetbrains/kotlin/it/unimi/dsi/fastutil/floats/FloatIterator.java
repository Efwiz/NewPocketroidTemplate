package org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats;

import java.util.Objects;
import java.util.PrimitiveIterator;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes5.dex */
public interface FloatIterator extends PrimitiveIterator<Float, FloatConsumer> {
    float nextFloat();

    @Override // java.util.Iterator
    @Deprecated
    default Float next() {
        return Float.valueOf(nextFloat());
    }

    @Override // java.util.PrimitiveIterator
    default void forEachRemaining(FloatConsumer floatConsumer) {
        Objects.requireNonNull(floatConsumer);
        while (hasNext()) {
            floatConsumer.accept(nextFloat());
        }
    }

    @Override // java.util.Iterator
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
}
