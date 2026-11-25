package org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints;

import java.util.Objects;
import java.util.PrimitiveIterator;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes5.dex */
public interface IntIterator extends PrimitiveIterator.OfInt {
    @Override // java.util.PrimitiveIterator.OfInt
    int nextInt();

    @Override // java.util.PrimitiveIterator.OfInt, java.util.Iterator
    @Deprecated
    default Integer next() {
        return Integer.valueOf(nextInt());
    }

    @Override // java.util.PrimitiveIterator.OfInt, java.util.Iterator
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
}
