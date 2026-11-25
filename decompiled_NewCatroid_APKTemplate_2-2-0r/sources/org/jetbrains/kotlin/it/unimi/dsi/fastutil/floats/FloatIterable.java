package org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats;

import java.util.Objects;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes5.dex */
public interface FloatIterable extends Iterable<Float> {
    @Override // java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatIterable
    FloatIterator iterator();

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.floats.FloatList, java.util.List
    default FloatSpliterator spliterator() {
        return FloatSpliterators.asSpliteratorUnknownSize(iterator(), 0);
    }

    default void forEach(FloatConsumer floatConsumer) {
        Objects.requireNonNull(floatConsumer);
        iterator().forEachRemaining(floatConsumer);
    }

    @Override // java.lang.Iterable
    @Deprecated
    default void forEach(Consumer<? super Float> consumer) {
        FloatConsumer floatIterable$$ExternalSyntheticLambda0;
        Objects.requireNonNull(consumer);
        if (consumer instanceof FloatConsumer) {
            floatIterable$$ExternalSyntheticLambda0 = (FloatConsumer) consumer;
        } else {
            Objects.requireNonNull(consumer);
            floatIterable$$ExternalSyntheticLambda0 = new FloatIterable$$ExternalSyntheticLambda0(consumer);
        }
        forEach(floatIterable$$ExternalSyntheticLambda0);
    }
}
