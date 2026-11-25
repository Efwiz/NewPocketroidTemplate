package org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs;

import java.util.Objects;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes5.dex */
public interface LongIterable extends Iterable<Long> {
    @Override // java.lang.Iterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongIterable
    LongIterator iterator();

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongCollection, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongIterable, org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongList, java.util.List
    default LongSpliterator spliterator() {
        return LongSpliterators.asSpliteratorUnknownSize(iterator(), 0);
    }

    default void forEach(java.util.function.LongConsumer longConsumer) {
        Objects.requireNonNull(longConsumer);
        iterator().forEachRemaining(longConsumer);
    }

    @Override // org.jetbrains.kotlin.it.unimi.dsi.fastutil.longs.LongIterable
    @Deprecated
    default void forEach(Consumer<? super Long> consumer) {
        java.util.function.LongConsumer longIterable$$ExternalSyntheticLambda0;
        Objects.requireNonNull(consumer);
        if (consumer instanceof java.util.function.LongConsumer) {
            longIterable$$ExternalSyntheticLambda0 = (java.util.function.LongConsumer) consumer;
        } else {
            Objects.requireNonNull(consumer);
            longIterable$$ExternalSyntheticLambda0 = new LongIterable$$ExternalSyntheticLambda0(consumer);
        }
        forEach(longIterable$$ExternalSyntheticLambda0);
    }
}
