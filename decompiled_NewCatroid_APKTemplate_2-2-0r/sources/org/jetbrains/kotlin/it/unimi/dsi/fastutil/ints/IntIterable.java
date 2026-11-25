package org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints;

import java.util.Objects;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes5.dex */
public interface IntIterable extends Iterable<Integer> {
    @Override // java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSet, java.util.Set
    IntIterator iterator();

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSet, java.util.Set
    default IntSpliterator spliterator() {
        return IntSpliterators.asSpliteratorUnknownSize(iterator(), 0);
    }

    default void forEach(java.util.function.IntConsumer intConsumer) {
        Objects.requireNonNull(intConsumer);
        iterator().forEachRemaining(intConsumer);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntIterable
    @Deprecated
    default void forEach(Consumer<? super Integer> consumer) {
        java.util.function.IntConsumer intIterable$$ExternalSyntheticLambda0;
        Objects.requireNonNull(consumer);
        if (consumer instanceof java.util.function.IntConsumer) {
            intIterable$$ExternalSyntheticLambda0 = (java.util.function.IntConsumer) consumer;
        } else {
            Objects.requireNonNull(consumer);
            intIterable$$ExternalSyntheticLambda0 = new IntIterable$$ExternalSyntheticLambda0(consumer);
        }
        forEach(intIterable$$ExternalSyntheticLambda0);
    }
}
