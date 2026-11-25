package org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs;

import java.util.Objects;
import java.util.PrimitiveIterator;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes5.dex */
public interface LongIterator extends PrimitiveIterator.OfLong {
    @Override // java.util.PrimitiveIterator.OfLong
    long nextLong();

    @Override // java.util.PrimitiveIterator.OfLong, java.util.Iterator
    @Deprecated
    default Long next() {
        return Long.valueOf(nextLong());
    }

    @Override // java.util.PrimitiveIterator.OfLong, java.util.Iterator
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
}
